package com.example.one.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.one.po.User;
import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface UserMapper extends BaseMapper<User> {

}

