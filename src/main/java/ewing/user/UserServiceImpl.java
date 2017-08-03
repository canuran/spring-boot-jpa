package ewing.user;

import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import ewing.application.AppException;
import ewing.entity.QCompany;
import ewing.entity.QUser;
import ewing.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;

/**
 * 用户服务实现。
 **/
@Service
@CacheConfig(cacheNames = "UserCache")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private JPAQueryFactory queryFactory;

    private QUser qUser = QUser.user;
    private QCompany qCompany = QCompany.company;

    @Override
    public User addUser(User user) {
        if (!StringUtils.hasText(user.getName()))
            throw new AppException("用户名不能为空！");
        if (userDao.findFirstByName(user.getName()) != null)
            throw new AppException("用户名已被使用！");
        if (!StringUtils.hasText(user.getPassword()))
            throw new AppException("密码不能为空！");
        user.setCreateTime(new Date());
        return userDao.save(user);
    }

    @Override
    @Cacheable(unless = "#result==null")
    public User getUser(String id) {
        return userDao.findOne(id);
    }

    @Override
    public List<User> findUsers(String userName, String companyName) {
        JPAQuery<User> query = queryFactory.selectDistinct(qUser)
                .from(qUser)
                .leftJoin(qUser.company, qCompany);
        if (StringUtils.hasText(userName))
            query.where(qUser.name.contains(userName));
        if (StringUtils.hasText(companyName))
            query.where(qCompany.name.contains(companyName));
        return query.fetch();
    }

    @Override
    @CacheEvict
    public void deleteUser(String id) {
        userDao.delete(id);
    }

    @Override
    public void clearUsers() {
        userDao.deleteAll();
    }

}
