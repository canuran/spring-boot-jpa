package ewing.user;

import ewing.config.BaseRepository;
import ewing.entity.User;
import org.springframework.stereotype.Repository;

/**
 * 用户数据库访问接口。
 **/
@Repository
public interface UserDao extends BaseRepository<User> {

    /**
     * 根据名称查询用户。
     */
    User findFirstByName(String name);

}
