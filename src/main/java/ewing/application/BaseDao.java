package ewing.application;

import com.querydsl.jpa.impl.JPAQueryFactory;
import ewing.entity.*;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * 基本的数据访问类。
 */
public abstract class BaseDao {

    @Autowired
    protected JPAQueryFactory queryFactory;

    @PersistenceContext
    protected EntityManager entityManager;

    // 所有实体类查询对象集中定义管理
    protected static final QUser qUser = QUser.user;
    protected static final QUserRole qUserRole = QUserRole.userRole;
    protected static final QRole qRole = QRole.role;
    protected static final QUserPermission qUserPermission = QUserPermission.userPermission;
    protected static final QRolePermission qRolePermission = QRolePermission.rolePermission;
    protected static final QPermission qPermission = QPermission.permission;

}
