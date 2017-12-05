package ewing.user;

import ewing.application.BeanRepository;
import ewing.entity.User;
import org.springframework.stereotype.Repository;

/**
 * 用户数据访问接口。
 */
@Repository
public interface UserRepository extends BeanRepository<User> {

    /**
     * 根据名称查询用户。
     */
    User findFirstByName(String name);

}
