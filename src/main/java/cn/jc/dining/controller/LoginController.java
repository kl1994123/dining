package cn.jc.dining.controller;

import cn.jc.dining.config.UserContext;
import cn.jc.dining.controller.VO.UserVo;
import cn.jc.dining.domain.User;
import cn.jc.dining.service.UserService;
import cn.jc.dining.util.Result;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/user")
public class LoginController {
    /*
     * 使用thymeleft返回页面的时候不能够使用restcontroller：会加上@responsebody 导致返回的会直接写入http0 response body中不会解析成路径
     * */
    @RequestMapping("/login")
    public String login(User user){
        return "index";
    }

    @RequestMapping("/boss-homepage")
    public String bosshomepage(User user){
        return "/boss/boss-homepage";
    }

    @GetMapping("/{path}")
    public String foodadd(@PathVariable("path")String path){
        return path;
    }

    @GetMapping("/{path}/{boss}")
    public String boss(@PathVariable("path")String path,@PathVariable("boss")String boss){
        return path+"/"+boss;
    }

    @RequestMapping("/homepage")
    public String homepage(User user){
        return "homepage";
    }

    @RequestMapping("/welcome")
    public String welcome(User user){
        return "welcome";
    }

    @RequestMapping("/statistics")
    public String statistics(User user){
        return "statistics";
    }

    @RequestMapping("/member")
    public String memberdel(User user){
        return "member-del";
    }

    @RequestMapping("/member-list")
    public String memberlist(User user){
        return "user-list";
    }

    @RequestMapping("/member-edit")
    public String memberedit(User user){
        return "member-edit";
    }

    @RequestMapping("/member-password")
    public String memberpassword(User user){
        return "member-password";
    }

    @Autowired
    private UserService userService;

    public static final String LOGIN_CUSTOMER_IN_SESSION = "loginuser";
    //返回json：代表成功还是失败
    @RequestMapping(value="/login",method = RequestMethod.POST)
    @ResponseBody
    public Result login(@RequestParam("username") String username, @RequestParam("password") String password){
        System.out.println(username);
        //1.拿到当前用户(可能还是游客，没有登录)
        Subject currentUser = SecurityUtils.getSubject();
        String s = changepwd(password).toString();
       //2.如果这个用户没有登录,进行登录功能
        if(!currentUser.isAuthenticated()){
            try {
                UsernamePasswordToken token = new UsernamePasswordToken(username,s);
                currentUser.login(token);
            }catch (UnknownAccountException e) {
                e.printStackTrace();
                System.out.println("用户名出现了错误！");
                return new Result(false,"用户名不存在！");
            }catch (IncorrectCredentialsException e) {
                e.printStackTrace();
                System.out.println("密码出现了错误！");
                return new Result(false,"用户名或者密码出错！");
            } catch (AuthenticationException e) {
                e.printStackTrace();
                System.out.println("出现了错误！请稍候重试");
                return new Result(false,"出现了神秘错误！");
            }
        }
        User user = userService.findByUsername(username);
        UserVo userVo = new UserVo();
        UserContext.setUser(user);
        BeanUtils.copyProperties(user,userVo);
        if("1".equals(user.getRole())){
//            員工
            return new Result(true,"1",userVo);

        }else {
//            老闆
            return new Result(true,"2",userVo);
        }
    }
    //*注销登录*//*
    @ResponseBody
    @RequestMapping("/logout")
    public Result logout(){
        Result result = new Result();
        Subject currentUser = SecurityUtils.getSubject();
        currentUser.logout();
        result.setSuccess(true);
        return result;
    }


    public String changepwd(String password){
        Md5Hash md5 = new Md5Hash(password);
        return md5.toString();
    }

}
