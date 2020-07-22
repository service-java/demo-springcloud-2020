package com.spring.cloud.chapter14.pojo;

import com.spring.cloud.chapter14.enumeration.PaymentChannelEnum;
import org.apache.ibatis.type.Alias;

import java.io.Serializable;
import java.util.Date;

/**** imports ****/
@Alias("transaction") // 定义MyBatis别名
public class Transaction implements Serializable {

    public static final long serialVersionUID = 2323902389475832678L;
    private Long id;
    private Long userId;
    private Long productId;
    private PaymentChannelEnum paymentChannel = null; // 枚举
    private Date transDate;
    private Double amout;
    private Integer quantity;
    private Double discount;
    private String note;

    /**** setters and getters ****/


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public PaymentChannelEnum getPaymentChannel() {
        return paymentChannel;
    }

    public void setPaymentChannel(PaymentChannelEnum paymentChannel) {
        this.paymentChannel = paymentChannel;
    }

    public Date getTransDate() {
        return transDate;
    }

    public void setTransDate(Date transDate) {
        this.transDate = transDate;
    }

    public Double getAmout() {
        return amout;
    }

    public void setAmout(Double amout) {
        this.amout = amout;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}