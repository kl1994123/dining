package cn.jc.dining.controller;

import cn.jc.dining.domain.Page;
import cn.jc.dining.domain.User;
import cn.jc.dining.mapper.UserMapper;
import cn.jc.dining.service.UserService;
import cn.jc.dining.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/user")
public class UserController {
    @RequestMapping("/user-list")
    public String userlist(){
        return "user-list";
    }

    @RequestMapping("/user-add")
    public String useradd(){
        return "user-add";
    }

    @Autowired
    private UserService userService;

    @Autowired
    private UserMapper userMapper;

    @GetMapping("/user")
    @ResponseBody
    public Result getAllUser(Page page, String username){
        return userService.getAllUser(page,username);
    }

    @GetMapping("/count")
    @ResponseBody
    public String count(){
        return userMapper.getAllCount();
    }

    @GetMapping("/user/{id}")
    @ResponseBody
    public Result getUserById(@PathVariable("id") String id){
        return userService.getUserById(id);
    }


    @PostMapping("/user")
    @ResponseBody
    public Result addUser(User user){
        return userService.addUser(user);
    }

    @DeleteMapping("/user/{id}")
    @ResponseBody
    public Result deleteUser(@PathVariable("id")String id){
        return userService.deleteUser(id);
    }
}
