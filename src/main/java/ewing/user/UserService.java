package ewing.user;

import ewing.entity.User;

import java.util.List;

/**
 * 用户服务接口。
 **/
public interface UserService {

    User addUser(User user);

    User getUser(String userId);

    List<User> findUsers(String userName, String companyName);

    void deleteUser(String userId);

    void clearUsers();

    void updateUser(User user);
}
