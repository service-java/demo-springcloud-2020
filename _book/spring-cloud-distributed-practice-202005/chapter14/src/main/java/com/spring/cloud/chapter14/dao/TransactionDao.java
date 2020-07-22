package com.spring.cloud.chapter14.dao;

import com.spring.cloud.chapter14.pojo.Transaction;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**** imports ****/
@Mapper // 标记为MyBatis的映射（Mapper）
public interface TransactionDao {

    /**
     * 根据用户编号（userId）查找交易
     * @param userId 用户编号
     * @return 交易信息
     */
    public List<Transaction> findTranctions(Long userId);
}