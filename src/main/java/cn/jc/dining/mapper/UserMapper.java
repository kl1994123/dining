package cn.jc.dining.mapper;

import cn.jc.dining.domain.User;

import java.util.List;

public interface UserMapper {

    List<User> getAllUser(String username);

    int addUser(User user);

    int deleteUser(String id);

    String getAllCount();

    User getUserById(String id);

    int editUser(User user);

    User findByUsername(String username);

}
