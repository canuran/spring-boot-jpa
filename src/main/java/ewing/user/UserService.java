package ewing.user;

import ewing.entity.User;

import java.util.List;

/**
 * 用户服务接口。
 **/
public interface UserService {

    User addUser(User user);

    User getUser(String id);

    List<User> findUsers(String userName, String companyName);

    void deleteUser(String id);

    void clearUsers();
}
