package com.spring.cloud.user.controller;

import com.spring.cloud.common.pojo.UserInfo;
import com.spring.cloud.common.vo.ResultMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**** imports ****/
@Controller
@RequestMapping("/user")
public class UserInfoController {
    /**
     * 模拟获取用户信息
     * @param id -- 用户编号
     * @return 用户信息
     */
    @GetMapping("/info/{id}")
    @ResponseBody
    public UserInfo getUser(@PathVariable("id") Long id, HttpServletRequest request) {
        System.out.println(request.getServerPort());
        UserInfo userInfo = new UserInfo(1L, "user_name_" + id, "note_" + id);
        return userInfo;
    }

    /**
     * 模拟更新用户信息
     * @param userInfo 用户信息
     * @return 用户信息
     */
    @PutMapping("/info")
    @ResponseBody
    public UserInfo putUser(@RequestBody UserInfo userInfo) {
        return userInfo;
    }

    @GetMapping("/infoes/{ids}")
    @ResponseBody
    public ResponseEntity<List<UserInfo>> findUsers(
            @PathVariable("ids") Long []ids ) {
        List<UserInfo> userList = new ArrayList<>();
        for (Long id : ids) {
            UserInfo userInfo
                    = new UserInfo(id, "user_name_" + id, "note_" + id);
            userList.add(userInfo);
        }
        ResponseEntity<List<UserInfo>> response // 将结果封装为响应实体
                = new ResponseEntity<>(userList, HttpStatus.OK);
        return response;
    }

    @GetMapping("/header/info")
    @ResponseBody
    public UserInfo headerUser(@RequestHeader("id") Long id) {
        UserInfo userInfo
                = new UserInfo(id, "user_name_" + id, "note_" + id);
        return userInfo;
    }

    @GetMapping("/request/info")
    @ResponseBody
    public UserInfo requestUser(@RequestParam("id") Long id) {
        UserInfo userInfo
                = new UserInfo(id, "user_name_" + id, "note_" + id);
        return userInfo;
    }

    /**
     *  以url?ids=xxx的形式传递参数
     * @param ids -- 参数列表
     * @return 用户信息列表
     */
    @GetMapping("/infoes2")
    @ResponseBody
    public ResponseEntity<List<UserInfo>> findUsers2(
            @RequestParam("ids") Long []ids) {
        List<UserInfo> userList = new ArrayList<>();
        for (Long id : ids) {
            UserInfo userInfo
                    = new UserInfo(id, "user_name_" + id, "note_" + id);
            userList.add(userInfo);
        }
        ResponseEntity<List<UserInfo>> response
                = new ResponseEntity<>(userList, HttpStatus.OK);
        return response;
    }

    /**
     * 删除用户
     * @param id -- 使用请求头传递参数
     * @return 结果
     */
    @DeleteMapping("/info")
    @ResponseBody
    public ResultMessage deleteUser(@RequestHeader("id") Long id) {
        boolean success = id != null;
        String msg = success? "传递成功":"传递失败";
        return new ResultMessage(success, msg);
    }

    /**
     * 传递文件
     * @param file -- 文件
     * @return 成败结果
     */
    @PostMapping(value="/upload")
    @ResponseBody
    public ResultMessage uploadFile(@RequestPart("file") MultipartFile file) {
        boolean success = file != null && file.getSize() > 0;
        String message = success? "文件传递成功" : "文件传递失败";
        return new ResultMessage(success, message);
    }
}