package com.xulei.auth.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xulei.auth.entity.Member;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MemberMapper extends BaseMapper<Member> {
}
