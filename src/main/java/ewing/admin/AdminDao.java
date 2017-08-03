package ewing.admin;

import ewing.config.BaseRepository;
import ewing.entity.Admin;
import org.springframework.stereotype.Repository;

/**
 * 管理员数据库访问接口。
 **/
@Repository
public interface AdminDao extends BaseRepository<Admin> {

    /**
     * 根据名称查询管理员。
     */
    Admin findFirstByName(String name);

}
