package com.zzq.zzq.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.zzq.zzq.model.BaseModel;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;
import java.util.Map;

public interface IBaseMapper<T extends BaseModel> extends BaseMapper<T> {

    List<Long> selectIdPage(@Param("cm") Map<String, Object> var1);

    List<Long> selectIdPage(RowBounds var1, @Param("cm") Map<String, Object> var2);
}
