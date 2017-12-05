package ewing.application;

import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.JpaEntityInformationSupport;
import org.springframework.data.jpa.repository.support.QueryDslJpaRepository;

import javax.persistence.EntityManager;
import java.io.Serializable;

/**
 * 所有Repository的父类。
 */
public abstract class BeanRepository<E> extends QueryDslJpaRepository<E, Serializable> implements BaseRepository<E> {

    public BeanRepository(Class<E> domainClass, EntityManager entityManager) {
        super((JpaEntityInformation<E, Serializable>) JpaEntityInformationSupport
                .getEntityInformation(domainClass, entityManager), entityManager);
    }

}
