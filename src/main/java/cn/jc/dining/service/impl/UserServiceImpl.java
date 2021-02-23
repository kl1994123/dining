package cn.jc.dining.service.impl;

import cn.jc.dining.controller.VO.UserVo;
import cn.jc.dining.domain.Page;
import cn.jc.dining.domain.User;
import cn.jc.dining.mapper.UserMapper;
import cn.jc.dining.service.UserService;
import cn.jc.dining.util.Result;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public Result getAllUser(Page page, String username) {
        Result result = new Result();
        PageHelper.startPage(page.getPage(), page.getRows());
        List<User> list =  userMapper.getAllUser(username);
        result.setData(list);
        result.setMsg(userMapper.getAllCount());
        return result;
    }

    @Override
    public Result addUser(User user) {
        Result result = new Result();
        if(user.getId()==null){
//            新增用户
            user.setPassword("123456");
            if(userMapper.addUser(user)!=0){
                result.setSuccess(true);
            }else {
                result.setSuccess(false);
            }
        }else {
//            修改用户
            if(userMapper.editUser(user)!=0){
                result.setSuccess(true);
            }else {
                result.setSuccess(false);
            }
        }
        return result;
    }

    @Override
    public Result deleteUser(String id) {
        Result result = new Result();
        if(userMapper.deleteUser(id)!=0){
            result.setSuccess(true);
        }else {
            result.setSuccess(false);
        }
        return result;
    }

    @Override
    public Result getUserById(String id) {
        Result result = new Result();
        try {
            User user = userMapper.getUserById(id);
            UserVo userVo = new UserVo();
            BeanUtils.copyProperties(user,userVo);
            result.setData(userVo);
            result.setSuccess(true);
        }catch (Exception e){
            result.setSuccess(false);
        }
        return result;
    }

    @Override
    public User findByUsername(String username) {
        return userMapper.findByUsername(username);
    }
}
