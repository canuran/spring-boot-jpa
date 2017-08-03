package ewing.admin;

import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import ewing.application.AppException;
import ewing.entity.Admin;
import ewing.entity.QAdmin;
import ewing.entity.QRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;

/**
 * 管理员服务实现。
 **/
@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminDao adminDao;

    @Autowired
    private JPAQueryFactory jpaQueryFactory;

    private QAdmin qAdmin = QAdmin.admin;
    private QRole qRole = QRole.role;

    @Override
    public Admin addAdmin(Admin admin) {
        if (!StringUtils.hasText(admin.getName()))
            throw new AppException("管理员名不能为空！");
        if (adminDao.findFirstByName(admin.getName()) != null)
            throw new AppException("管理员名已被使用！");
        if (!StringUtils.hasText(admin.getPassword()))
            throw new AppException("密码不能为空！");
        admin.setCreateTime(new Date());
        return adminDao.save(admin);
    }

    @Override
    public Admin getAdmin(String id) {
        return adminDao.findOne(id);
    }

    @Override
    public List<Admin> findAdmins(String adminName, String roleName) {
        JPAQuery<Admin> query = jpaQueryFactory.selectDistinct(qAdmin)
                .from(qAdmin)
                .leftJoin(qAdmin.roles, qRole);
        if (StringUtils.hasText(adminName))
            query.where(qAdmin.name.contains(adminName));
        if (StringUtils.hasText(roleName))
            query.where(qRole.name.contains(roleName));
        return query.fetch();
    }

    @Override
    public void deleteAdmin(String id) {
        adminDao.delete(id);
    }

    @Override
    public void clearAdmins() {
        adminDao.deleteAll();
    }

}
