package ewing.user;

import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import ewing.application.BaseDao;
import ewing.application.QueryHelper;
import ewing.application.paging.Pager;
import ewing.application.paging.Pages;
import ewing.entity.*;
import ewing.security.RoleAsAuthority;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.math.BigInteger;
import java.util.List;

/**
 * User数据库访问实现类，非必须，仅当逻辑非常复杂时才需要。
 */
@Repository
public class UserDaoImpl extends BaseDao implements UserDao {

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
    public List<RoleAsAuthority> findUserRoles(BigInteger userId) {
        return queryFactory.selectDistinct(
                QueryHelper.fitBean(RoleAsAuthority.class, qRole))
                .from(qUser)
                .leftJoin(qUser.userRoles, qUserRole)
                .leftJoin(qUserRole.role, qRole)
                .where(qUser.id.eq(userId))
                .fetch();
    }

    @Override
    public List<Permission> findUserPermissions(BigInteger userId) {
        List<Permission> userPermissions = queryFactory.selectDistinct(
                QueryHelper.fitBean(Permission.class, qPermission))
                .from(qPermission)
                .innerJoin(qPermission.userPermissions, qUserPermission)
                .innerJoin(qUserPermission.user, qUser)
                .where(qUser.id.eq(userId))
                .fetch();
        List<Permission> rolePermissions = queryFactory.selectDistinct(
                QueryHelper.fitBean(Permission.class, qPermission))
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
