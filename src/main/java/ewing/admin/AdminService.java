package ewing.admin;

import ewing.entity.Admin;

import java.util.List;

/**
 * 管理员服务接口。
 **/
public interface AdminService {

    Admin addAdmin(Admin admin);

    Admin getAdmin(String id);

    List<Admin> findAdmins(String adminName, String roleName);

    void deleteAdmin(String id);

    void clearAdmins();
}
