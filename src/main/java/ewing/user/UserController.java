package ewing.user;

import ewing.application.Result;
import ewing.entity.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 用户控制器。
 **/
@RestController
@RequestMapping("user")
@Api(description = "用户模块的方法")
public class UserController {

    @Autowired
    private UserService userService;

    @ApiOperation("查找用户")
    @PostMapping("findUser")
    public Result<List<User>> findUsers(
            @RequestParam(required = false) String userName,
            @RequestParam(required = false) String companyName) {
        return new Result<>(userService.findUsers(userName, companyName));
    }

    @ApiOperation("添加用户")
    @PostMapping("addUser")
    public Result<User> addUser(User user) {
        return new Result<>(userService.addUser(user));
    }

    @ApiOperation("更新用户")
    @PostMapping("updateUser")
    public Result updateUser(User user) {
        userService.updateUser(user);
        return new Result();
    }

    @ApiOperation("根据ID获取用户")
    @GetMapping("getUser")
    public Result<User> getUser(@RequestParam String userId) {
        return new Result<>(userService.getUser(userId));
    }

    @ApiOperation("根据ID删除用户")
    @GetMapping("deleteUser")
    public Result deleteUser(@RequestParam String userId) {
        userService.deleteUser(userId);
        return new Result();
    }

    @ApiOperation("删除所有用户")
    @GetMapping("clearUsers")
    public Result clearUsers() {
        userService.clearUsers();
        return new Result();
    }

}
