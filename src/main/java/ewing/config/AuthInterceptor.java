package ewing.config;

import ewing.common.JWTUtils;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class AuthInterceptor extends HandlerInterceptorAdapter {

    private static final String failJson = "{\"code\":0,\"success\":false,\"message\":\"您没有足够的权限！\"}";
    private final Logger logger = LoggerFactory.getLogger(AuthInterceptor.class);

    /**
     * 在业务处理器处理请求之前被调用。
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (!(handler instanceof HandlerMethod))
            return true;
        HandlerMethod method = (HandlerMethod) handler;
        Authorize authorize = method.getMethodAnnotation(Authorize.class);
        String type;
        if (authorize != null) { // 方法上的注解优先。
            type = authorize.type();
        } else { // 方法上没有注解才考虑类上的注解。
            authorize = ((HandlerMethod) handler).getBeanType().getAnnotation(Authorize.class);
            if (authorize == null)
                return true;
            else
                type = authorize.type();
        }

        // 权限拦截，基于Session。
        if (Authorize.TYPE_SESSION.equals(type)) {
            Object user = request.getSession().getAttribute(Authorize.USER_KEY);
            String[] permissions = (String[]) request.getSession().getAttribute(Authorize.PERMISSION_KEY);
            if (user == null || !checkPermissions(authorize.value(), permissions)) {
                doFailResponse(request, response, method);
                return false;
            }
            // 权限拦截，基于Token。
        } else if (Authorize.TYPE_TOKEN.equals(type)) {
            // 验证Header中的token令牌。
            String token = request.getHeader(Authorize.TYPE_TOKEN);
            String userId = (String) JWTUtils.getFromToken(token, Authorize.USER_KEY);
            // 从Cache中取用户权限数据。
            Cache cache = CacheManager.getInstance().getCache("UserCache");
            String[] permissions = (String[]) cache.get(userId + Authorize.PERMISSION_KEY).getValue();
            if (!checkPermissions(authorize.value(), permissions)) {
                doFailResponse(request, response, method);
                return false;
            }
        }

        // 没有拦截注解或拦截类型不支持的默认不拦截。
        return true;
    }

    /**
     * 检查权限编码数组是否完全具有。
     *
     * @param needPermissions 需要的权限编码。
     * @param ownPermissions  拥有的权限编码。
     */
    private boolean checkPermissions(String[] needPermissions, String[] ownPermissions) throws IOException {
        if (needPermissions == null || ownPermissions == null)
            return false;
        int passCount = 0;
        for (String permissionCode : needPermissions) {
            for (String permission : ownPermissions) {
                if (permissionCode.equals(permission)) {
                    passCount++;
                    break; // 找到权限，本次循环结束。
                }
            }
        }
        return passCount == needPermissions.length;
    }

    /**
     * 处理鉴权失败的请求，根据请求的方法进行相应的处理。
     */
    private void doFailResponse(HttpServletRequest request, HttpServletResponse response, HandlerMethod method) throws Exception {
        logger.info("拦截到未授权的用户：" + request.getRemoteAddr() + " 请求：" + request.getRequestURI());
        if (AnnotationUtils.findAnnotation(method.getMethod(), ResponseBody.class) != null
                || AnnotationUtils.findAnnotation(method.getBeanType(), ResponseBody.class) != null) {
            response.setCharacterEncoding("UTF-8");
            response.setContentType("text/plain;charset=utf-8");
            response.getWriter().write(failJson);
        } else {
            request.getRequestDispatcher("/index.html").forward(request, response);
        }
    }

}

