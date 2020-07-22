package com.spring.cloud.chapter2.controller;

import com.spring.cloud.chapter2.pojo.Account;
import com.spring.cloud.chapter2.response.utils.ResponseUtils;
import com.spring.cloud.chapter2.vo.ResultMessage;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 *  imports 
 **/
@Controller
@RequestMapping("/fund")
public class AccountController {

    // 返回账户thymeleaf页面
    @GetMapping("/account/page")
    public String page() {
        return "account";
    }

    @GetMapping("/account/{id}") // @GetMapping代表GET请求
    @ResponseBody // 结果转换为JSON
    public Account getAccount(@PathVariable("id") Long id) {
        Account account = new Account();
        account.setId(id);
        account.setAccountName("account_" + id);
        double balance = id % 10 * 10000.0 * Math.random();
        account.setBalance(balance);
        account.setNote("note_" + id);
        return account;
    }

    @PostMapping("/account") // POST请求
    @ResponseBody
    public Account createAccount(@RequestBody Account account) {
        long id = (long) (10000.0 * Math.random());
        account.setId(id);
        return account;
    }

    @PutMapping("/account") // HTTP PUT请求
    @ResponseBody
    public ResultMessage updateAccount(@RequestBody Account account) {
        System.out.println("更新账户");
        return new ResultMessage(true, "更新账户成功");
    }

    @DeleteMapping("/account/{id}") // DELETE请求
    @ResponseBody
    public ResultMessage deleteAccount(@PathVariable("id") Long id) {
        System.out.println("删除账户");
        return new ResultMessage(true, "删除账户成功");
    }

//    @PostMapping("/account2") // POST请求
//    @ResponseBody
//    public ResponseEntity<Account> createAccount2(@RequestBody Account account) {
//        ResponseEntity<Account> response = null;
//        HttpStatus status = null;
//        // 响应头
//        HttpHeaders headers = new HttpHeaders();
//        // 异常标志
//        boolean exFlag = false;
//        try {
//            long id = (long)(10000.0*Math.random());
//            account.setId(id);
//            // 测试时可自己加入异常测试异常情况
//            throw new RuntimeException();
//        } catch(Exception ex) {
//            // 设置异常标志为true
//            exFlag = true;
//        }
//        if (exFlag) { // 异常处理
//            // 加入请求头消息
//            headers.add("message", "create account error，plz check ur input!!");
//            headers.add("success", "false");
//            // 设置状态码（200-请求成功）
//            status = HttpStatus.OK;
//        } else { // 创建资源成功处理
//            // 加入请求头消息
//            headers.add("message", "create account success!!");
//            headers.add("success", "true");
//            // 设置状态码（201-创建资源成功）
//            status = HttpStatus.CREATED;
//        }
//        // 创建应答实体对象返回
//        return new ResponseEntity<Account>(account, headers, status);
//    }

    @PostMapping("/account2") // POST请求
    @ResponseBody
    public ResponseEntity<Account> createAccount2(@RequestBody Account account) {
        // 异常标志
        boolean exFlag = false;
        try {
            long id = (long) (10000.0 * Math.random());
            account.setId(id);
            // 测试时可自己加入异常测试异常情况
            throw new RuntimeException();
        } catch (Exception ex) {
            // 设置异常标志为true
            exFlag = true;
        }
        return exFlag ?
                ResponseUtils.generateResponseEntity(account, // 异常处理
                        HttpStatus.OK, false, "create account error，plz check ur input!!") :
                ResponseUtils.generateResponseEntity(account, // 正常返回
                        HttpStatus.CREATED, true, "create account success!!");
    }
}
