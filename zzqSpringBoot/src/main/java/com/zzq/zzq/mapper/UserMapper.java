package com.zzq.zzq.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.zzq.zzq.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserMapper extends IBaseMapper<User> {
    int deleteByPrimaryKey(Long id);

    int insertSelective(User record);

    User selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    List<User> findByAll();

    List<User> findByAllUser();
}