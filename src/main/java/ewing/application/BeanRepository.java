package ewing.application;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;

/**
 * 聚合多种方法的JPA接口。
 */
@NoRepositoryBean
public interface BeanRepository<E>
        extends JpaRepository<E, Serializable>,
        QueryDslPredicateExecutor<E> {

}
