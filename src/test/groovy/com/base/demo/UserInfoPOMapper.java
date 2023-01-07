package com.base.demo;

import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;

public interface UserInfoPOMapper {

    @Delete("delete from user_info where id=#{id}")
    int deleteById(Long id);

    @Select("select count(*) from user_info")
    int count();

    @Select("select * from user_info")
    List<UserInfoPO> selectAll();
}
