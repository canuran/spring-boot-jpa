package ewing.user;

import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import ewing.application.QueryHelper;
import ewing.application.paging.Pager;
import ewing.application.paging.Pages;
import ewing.entity.*;
import ewing.security.RoleAsAuthority;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * User数据库访问实现类，非必须，仅当逻辑非常复杂时才需要。
 */
@Repository
public class UserDaoImpl implements UserDao {

    @Autowired
    private JPAQueryFactory queryFactory;

    private QUser qUser = QUser.user;
    private QUserRole qUserRole = QUserRole.userRole;
    private QRole qRole = QRole.role;
    private QUserPermission qUserPermission = QUserPermission.userPermission;
    private QRolePermission qRolePermission = QRolePermission.rolePermission;
    private QPermission qPermission = QPermission.permission;

    @Override
    public Pages<User> findUsers(Pager pager, String name, String roleName) {
        JPQLQuery<User> query = queryFactory.selectDistinct(qUser)
                .from(qUser)
                .leftJoin(qUser.userRoles, qUserRole)
                .leftJoin(qUserRole.role, qRole)
                .where(StringUtils.hasText(name) ? qUser.name.contains(name) : null)
                .where(StringUtils.hasText(roleName) ? qRole.name.contains(roleName) : null);
        return QueryHelper.queryPage(pager, query);
    }

    @Override
    public List<RoleAsAuthority> findUserRoles(Long userId) {
        return queryFactory.selectDistinct(
                QueryHelper.allToBean(RoleAsAuthority.class, qRole))
                .from(qUser)
                .leftJoin(qUser.userRoles, qUserRole)
                .leftJoin(qUserRole.role, qRole)
                .where(qUser.id.eq(userId))
                .fetch();
    }

    @Override
    public List<Permission> findUserPermissions(Long userId) {
        List<Permission> userPermissions = queryFactory.selectDistinct(
                QueryHelper.allToBean(Permission.class, qPermission))
                .from(qPermission)
                .innerJoin(qPermission.userPermissions, qUserPermission)
                .innerJoin(qUserPermission.user, qUser)
                .where(qUser.id.eq(userId))
                .fetch();
        List<Permission> rolePermissions = queryFactory.selectDistinct(
                QueryHelper.allToBean(Permission.class, qPermission))
                .from(qPermission)
                .innerJoin(qPermission.rolePermissions, qRolePermission)
                .innerJoin(qRolePermission.role, qRole)
                .innerJoin(qRole.userRoles, qUserRole)
                .innerJoin(qUserRole.user, qUser)
                .where(qUser.id.eq(userId))
                .fetch();
        userPermissions.removeAll(rolePermissions);
        userPermissions.addAll(rolePermissions);
        return userPermissions;
    }
}
