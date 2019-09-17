package com.xulei.auth.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

/**
 * @author ashura1110
 * @ClassName XULEI
 * @description TODO
 * @Date 2019/7/28 18:32
 * @Version 1.0
 */
@Data
@TableName(value="xl_role")
public class Role extends Model<Role> implements Serializable {

    @TableId(type = IdType.AUTO)
    private int id;
    private String roleName;
    private short valid;
    private Date createTime;
    private Date updateTime;
    private Set<Permission> permissions;
    @Override
    protected Serializable pkVal() {
        return id;
    }
}
