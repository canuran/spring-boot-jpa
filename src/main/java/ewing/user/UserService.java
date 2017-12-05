package ewing.user;

import ewing.application.paging.Pager;
import ewing.application.paging.Pages;
import ewing.entity.Permission;
import ewing.entity.User;
import ewing.security.RoleAsAuthority;
import ewing.security.SecurityUser;

import java.util.List;

/**
 * 用户服务接口。
 **/
public interface UserService {

    User addUser(User user);

    User getUser(Long userId);

    User updateUser(User user);

    Pages<User> findUsers(Pager pager, String name, String roleName);

    void deleteUser(Long userId);

    SecurityUser getByName(String name);

    List<RoleAsAuthority> getUserRoles(Long userId);

    List<Permission> getUserPermissions(Long userId);
}
