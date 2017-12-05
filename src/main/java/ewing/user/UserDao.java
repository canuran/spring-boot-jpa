package ewing.user;

import ewing.application.BaseRepository;
import ewing.entity.User;

/**
 * 用户数据库访问接口。
 **/
public interface UserDao extends BaseRepository<User> {

    /**
     * 根据名称查询用户。
     */
    User findFirstByName(String name);

    User customFindUser(Long id);

}
