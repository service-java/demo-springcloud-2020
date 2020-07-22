package com.spring.cloud.chapter2.test;

import com.spring.cloud.chapter2.pojo.Account;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

public class RestTemplateTest {

    public static void main(String[] args) {
        get();
        post();
        put();
        delete();
        post2();
    }

    public static void get() {
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:8002/fund/account/{id}";
        // GET请求，返回对象
        Account account = restTemplate.getForObject(url, Account.class, 1L);
        System.out.println(account.getAccountName());
    }

    public static void post() {
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:8002/fund/account";
        // 请求头
        HttpHeaders headers = new HttpHeaders();
        // 设置请求体为JSON
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        Account account = new Account();
        account.setAccountName("account_xxx");
        account.setBalance(12345.60);
        account.setNote("account_note_xxx");
        // 封装请求实体对象，将账户对象设置为请求体
        HttpEntity<Account> request = new HttpEntity<>(account, headers);
        // 发送POST请求，返回对象
        Account result = restTemplate.postForObject(url, request, Account.class);
        System.out.println(result.getId());
    }

    public static void put() {
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:8002/fund/account";
        // 请求头
        HttpHeaders headers = new HttpHeaders();
        // 设置请求体媒体类型为JSON
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        Account account = new Account();
        account.setAccountName("account_xxx");
        account.setBalance(12345.60);
        account.setNote("account_note_xxx");
        // 封装请求对象
        HttpEntity<Account> request = new HttpEntity<>(account, headers);
        // 发送请求
        restTemplate.put(url, request);
    }

    public static void delete() {
        RestTemplate restTemplate = new RestTemplate();
        // {id}是占位
        String url = "http://localhost:8002/fund/account/{id}";
        // DELETE请求没有返回值
        restTemplate.delete(url, 123L);
    }

    public static void post2() {
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:8002/fund/account2";
        // 请求头
        HttpHeaders headers = new HttpHeaders();
        // 设置请求体为JSON
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        Account account = new Account();
        account.setAccountName("account_xxx");
        account.setBalance(12345.60);
        account.setNote("account_note_xxx");
        // 封装请求对象
        HttpEntity<Account> request = new HttpEntity<>(account, headers);
        // 发送请求
        ResponseEntity<Account> result
                = restTemplate.postForEntity(url, request, Account.class);
        // 获取响应码
        HttpStatus status = result.getStatusCode();
        // 获取响应头
        String success = result.getHeaders().get("success").get(0);
        // 获取响应头成功标识信息
        if ("true".equals(success)) { // 响应成功
            Account accountResult = result.getBody();// 获取响应体
            System.out.println(accountResult.getId());
        } else { // 响应失败处理
            // 获取响应头消息
            String message = result.getHeaders().get("message").get(0);
            System.out.println(message);
        }
    }
}
