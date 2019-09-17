package com.xulei.auth.controller;

import com.xulei.auth.enums.ResultCode;
import com.xulei.auth.service.MyUserDetailService;
import com.xulei.auth.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.token.ConsumerTokenServices;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

/**
 * @author ashura1110
 * @ClassName XULEI
 * @description TODO
 * @Date 2019/7/28 20:01
 * @Version 1.0
 */
@RestController
@RequestMapping("/api")
public class MemberController {


    @Autowired
    private MyUserDetailService userDetailService;

    @Autowired
    private ConsumerTokenServices consumerTokenServices;

    @GetMapping("/member")
    public Principal user(Principal member) {
        //获取当前用户信息
        return member;
    }

    @DeleteMapping(value = "/exit")
    public Result revokeToken(String access_token) {
        //注销当前用户
        Result result = new Result();
        if (consumerTokenServices.revokeToken(access_token)) {
            result.setCode(ResultCode.SUCCESS.getCode());
            result.setMessage("注销成功");
        } else {
            result.setCode(ResultCode.FAILED.getCode());
            result.setMessage("注销失败");
        }
        return result;
    }
}
