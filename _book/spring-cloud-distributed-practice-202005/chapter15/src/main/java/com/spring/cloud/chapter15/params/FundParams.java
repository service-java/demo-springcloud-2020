package com.spring.cloud.chapter15.params;
import java.io.Serializable;
public class FundParams implements Serializable {
    // 序列化版本号
    public static final long serialVersionUID = 989878441231256478L;
    private Long xid; // 业务流水号
    private Long userId; // 用户编号
    private Double amount; // 交易金额

    public FundParams() {
    }

    public FundParams(Long xid, Long userId, Double amount) {
        this.xid = xid;
        this.userId = userId;
        this.amount = amount;
    }

    public Long getXid() {
        return xid;
    }

    public void setXid(Long xid) {
        this.xid = xid;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }
}