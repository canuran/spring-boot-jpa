package ewing.admin;

import ewing.application.Result;
import ewing.entity.Admin;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 管理员控制器。
 **/
@RestController
@RequestMapping("admin")
@Api(description = "管理员模块的方法")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @ApiOperation("查找管理员")
    @PostMapping("findAdmin")
    public Result<List<Admin>> findAdmins(
            @RequestParam(required = false) String adminName,
            @RequestParam(required = false) String roleName) {
        return new Result<>(adminService.findAdmins(adminName, roleName));
    }

    @ApiOperation("添加管理员")
    @PostMapping("addAdmin")
    public Result<Admin> addAdmin(@RequestBody Admin admin) {
        return new Result<>(adminService.addAdmin(admin));
    }

    @ApiOperation("根据ID获取管理员")
    @GetMapping("getAdmin")
    public Result<Admin> getAdmin(@RequestParam String id) {
        return new Result<>(adminService.getAdmin(id));
    }

    @ApiOperation("根据ID删除管理员")
    @GetMapping("deleteAdmin")
    public Result deleteAdmin(@RequestParam String id) {
        adminService.deleteAdmin(id);
        return new Result();
    }

    @ApiOperation("删除所有管理员")
    @GetMapping("clearAdmins")
    public Result clearAdmins() {
        adminService.clearAdmins();
        return new Result();
    }

}
