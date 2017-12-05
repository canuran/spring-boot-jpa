package ewing.user;

import ewing.application.AppAsserts;
import ewing.application.paging.Pager;
import ewing.application.paging.Pages;
import ewing.entity.QUser;
import ewing.entity.User;
import ewing.security.RoleAsAuthority;
import ewing.security.SecurityUser;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.Collections;
import java.util.List;

/**
 * 用户服务实现。
 **/
@Service
@Transactional(rollbackFor = Throwable.class)
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public User addUser(User user) {
        AppAsserts.notNull(user, "用户不能为空！");
        AppAsserts.hasText(user.getName(), "用户名不能为空！");
        AppAsserts.isTrue(userDao.count(
                QUser.user.name.eq(user.getName())) < 1,
                "用户名已被使用！");
        AppAsserts.hasText(user.getPassword(), "密码不能为空！");

        if (user.getBirthday() == null) {
            user.setBirthday(new Timestamp(System.currentTimeMillis()));
        }
        return userDao.save(user);
    }

    @Override
    @Cacheable(cacheNames = "UserCache", key = "#userId", unless = "#result==null")
    public User getUser(Long userId) {
        AppAsserts.notNull(userId, "用户ID不能为空！");
        return userDao.getOne(userId);
    }

    @Override
    @CacheEvict(cacheNames = "UserCache", key = "#user.userId")
    public User updateUser(User user) {
        AppAsserts.notNull(user, "用户不能为空！");
        AppAsserts.notNull(user.getId(), "用户ID不能为空！");
        return userDao.save(user);
    }

    @Override
    public Pages<User> findUsers(Pager pager, String name, String roleName) {
        Page<User> page = userDao.findAll(pager.pageable());
        return new Pages<>(page);
    }

    @Override
    @CacheEvict(cacheNames = "UserCache", key = "#userId")
    public void deleteUser(Long userId) {
        AppAsserts.notNull(userId, "用户ID不能为空！");
        userDao.delete(userId);
    }

    @Override
    public SecurityUser getByName(String name) {
        AppAsserts.hasText(name, "用户名不能为空！");
        User user = userDao.findFirstByName(name);
        SecurityUser securityUser = new SecurityUser();
        BeanUtils.copyProperties(user, securityUser);
        return securityUser;
    }

    @Override
    public List<RoleAsAuthority> getUserRoles(Long userId) {
        AppAsserts.notNull(userId, "用户ID不能为空！");
        return Collections.emptyList();
    }

    @Override
    public List<PermissionTree> getUserPermissions(Long userId) {
        AppAsserts.notNull(userId, "用户ID不能为空！");
        return Collections.emptyList();
    }

}
