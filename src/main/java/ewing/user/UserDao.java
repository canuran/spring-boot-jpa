package ewing.user;

import ewing.application.paging.Pager;
import ewing.application.paging.Pages;
import ewing.entity.Permission;
import ewing.entity.User;
import ewing.security.RoleAsAuthority;

import java.math.BigInteger;
import java.util.List;

/**
 * 用户数据库访问接口。
 **/
public interface UserDao {

    Pages<User> findUsers(Pager pager, String name, String roleName);

    List<RoleAsAuthority> findUserRoles(BigInteger userId);

    List<Permission> findUserPermissions(BigInteger userId);

}
