package com.zzq.zzq.mapper;

import com.zzq.zzq.model.GdsMember;
import com.zzq.zzq.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface IMember {

    @Select("SELECT * FROM gds_member WHERE name = #{name}")
    GdsMember findByName(@Param("name") String name);

    @Select("SELECT * FROM gds_member")
    List<User> findByAll();
}
