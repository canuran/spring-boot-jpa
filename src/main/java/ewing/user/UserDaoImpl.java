package ewing.user;

import ewing.entity.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * User数据库访问实现类，非必须，仅当逻辑非常复杂时才需要。
 */
public class UserDaoImpl {

    @PersistenceContext
    private EntityManager entityManager;

    public User customFindUser(Long id) {
        System.out.println("customFindUser:" + id);
        return (User) entityManager
                .createQuery("select u from User u where u.id = :id")
                .setParameter("id", id)
                .getSingleResult();
    }
}
