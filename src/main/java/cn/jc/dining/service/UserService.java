package cn.jc.dining.service;

import cn.jc.dining.domain.Page;
import cn.jc.dining.domain.User;
import cn.jc.dining.util.Result;

public interface UserService {

    Result getAllUser(Page page, String username);

    Result addUser(User user);

    Result deleteUser(String id);

    Result getUserById(String id);

    User findByUsername(String username);
}
