package com.xulei.auth.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xulei.auth.entity.Member;
import com.xulei.auth.entity.Role;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface RoleMapper extends BaseMapper<Role> {

    /**
     * 根据用户id查找角色列表
     * @param memberId 用户id
     * @return 角色列表
     */
    List<Role> findByMemberId(Integer memberId);
}
