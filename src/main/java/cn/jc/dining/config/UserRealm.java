package cn.jc.dining.config;

import cn.jc.dining.domain.User;
import cn.jc.dining.service.UserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

public class UserRealm extends AuthorizingRealm {

    @Autowired
    private UserService userService;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        // 获取用户名信息
        String username = (String) principalCollection.getPrimaryPrincipal();
        // 创建一个简单授权验证信息
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        // 给这个用户设置从 role 表获取到的角色信息
//        authorizationInfo.addRole(userMapper.queryUserByUsername(username).getRole().getRoleName());
        //给这个用户设置从 permission 表获取的权限信息
//        authorizationInfo.addStringPermission(userMapper.queryPermissionByUsername(username).getPermissionName());
        return authorizationInfo;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        System.out.println("--------------------登录-------------------");
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        String username = token.getUsername();
        User user = userService.findByUsername(username);
        String password = "";
//        员工不存在，代表这个用户名是没有的
        if(user==null){

        }else{
            password = user.getPassword();
        }
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(username, password, getName());
        return authenticationInfo;
    }
}