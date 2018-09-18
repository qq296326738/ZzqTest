package com.zzq.zzq.service;

import com.baomidou.mybatisplus.mapper.Condition;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.zzq.zzq.mapper.IMember;
import com.zzq.zzq.mapper.UserMapper;
import com.zzq.zzq.model.GdsMember;
import com.zzq.zzq.model.Person;
import com.zzq.zzq.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonService {

    @Autowired
    private IMember iMember;

    @Autowired
    private UserMapper userMapper;

    public int testInterFace() {
        return 0;
    }

    public Person testUser() {
        return new Person();
    }

    public GdsMember findByName(String name) {
        GdsMember byName = iMember.findByName(name);
        return byName;
    }

    public List<User> getUser() {
        List<User> byAll = userMapper.findByAll();
        return byAll;

    }


    public List<User> findByAllUser() {

        List<User> byAll = userMapper.findByAllUser();
        return byAll;

    }

    public User findBy() {
        Long id = 1026299808026607618L;
        User user = userMapper.selectById(id);
        return user;

    }

    public List<User> selectPage() {
        Long id = 1026299808026607618L;
        List<User> users = userMapper.selectPage(new Page<User>(1, 10), new EntityWrapper<>());
        return users;

    }
}
