package ewing.demos;

import ewing.entity.User;
import ewing.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Thymeleaf用户管理控制器。
 **/
@Controller
public class ThymeleafUser {
    @Autowired
    private UserService userService;

    @GetMapping("user")
    public ModelAndView userPage() {
        ModelAndView modelAndView = new ModelAndView("user");
        modelAndView.addObject("user", new User());
        modelAndView.addObject("users",
                userService.findUsers(null, null));
        return modelAndView;
    }

    @PostMapping("addUser")
    public ModelAndView addUser(@ModelAttribute User user) {
        userService.addUser(user);
        return userPage();
    }

    @GetMapping("deleteUser")
    public ModelAndView deleteUser(String userId) {
        userService.deleteUser(userId);
        return userPage();
    }

}
