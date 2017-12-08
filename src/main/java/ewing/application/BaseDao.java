package ewing.application;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * 基本的数据访问类。
 */
public abstract class BaseDao implements AppBeans {

    @Autowired
    protected JPAQueryFactory queryFactory;

    @PersistenceContext
    protected EntityManager entityManager;

}
