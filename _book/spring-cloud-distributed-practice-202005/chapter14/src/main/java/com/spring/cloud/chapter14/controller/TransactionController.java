package com.spring.cloud.chapter14.controller;

import com.spring.cloud.chapter14.dao.TransactionDao;
import com.spring.cloud.chapter14.datasource.DataSourcesContentHolder;
import com.spring.cloud.chapter14.pojo.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**** imports ****/
@RestController
public class TransactionController {

    @Autowired
    private TransactionDao transactionDao = null;

    @GetMapping("/transactions/{userId}")
    public List<Transaction> findTransaction(
            @PathVariable("userId") Long userId) {
        // 设置用户编号，这样就能够根据规则找到具体的数据库
        DataSourcesContentHolder.setId(userId);
        return transactionDao.findTranctions(userId);
    }
}