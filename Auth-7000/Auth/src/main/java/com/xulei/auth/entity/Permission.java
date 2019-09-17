package com.xulei.auth.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author ashura1110
 * @ClassName XULEI
 * @description TODO
 * @Date 2019/7/28 18:33
 * @Version 1.0
 */
@Data
@TableName(value="xl_permission")
public class Permission extends Model<Permission> implements Serializable {


    @TableId(type = IdType.AUTO)
    private int id;
    private String zuulPrefix;
    private String servicePrefix;
    private String method;
    private String uri;
    private Date createTime;
    private Date updateTime;

    @Override
    protected Serializable pkVal() {
        return id;
    }
}
