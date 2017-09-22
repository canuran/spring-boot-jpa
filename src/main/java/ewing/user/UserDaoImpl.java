package ewing.user;

import ewing.config.SuperRepository;
import ewing.entity.User;

import javax.persistence.EntityManager;

/**
 * User数据库访问实现类，非必须，仅当逻辑非常复杂时才需要。
 */
public class UserDaoImpl extends SuperRepository<User> {

    private EntityManager entityManager;

    public UserDaoImpl(EntityManager entityManager) {
        super(User.class, entityManager);
        this.entityManager = entityManager;
    }

}
