package com.xulei.auth.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xulei.auth.entity.Member;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MemberMapper extends BaseMapper<Member> {

    /**
     * 根据会员名查找会员
     * @param memberName 会员名
     * @return 会员
     */
    Member findByMemberName(String memberName);
}
