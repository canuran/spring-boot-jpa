package ewing.config;

import java.lang.annotation.*;

@Inherited
@Documented
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Authorize {
    // 权限校验类型
    String TYPE_SESSION = "Session";
    String TYPE_TOKEN = "Authorization";
    // 授权验证信息存储KEY
    String USER_KEY = "user";
    String PERMISSION_KEY = "permissions";

    // 鉴权类型（必须指定才有效）
    String type() default TYPE_SESSION;

    // 需要的权限数组（可选）
    String[] value() default {};
}