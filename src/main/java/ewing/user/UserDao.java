package ewing.user;

import ewing.application.paging.Pager;
import ewing.application.paging.Pages;
import ewing.entity.Permission;
import ewing.entity.User;
import ewing.security.RoleAsAuthority;

import java.util.List;

/**
 * 用户数据库访问接口。
 **/
public interface UserDao {

    Pages<User> findUsers(Pager pager, String name, String roleName);

    List<RoleAsAuthority> findUserRoles(Long userId);

    List<Permission> findUserPermissions(Long userId);

}
