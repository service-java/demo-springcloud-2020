package com.spring.cloud.ms.common.pojo;


import org.apache.ibatis.type.Alias;

import java.io.Serializable;
import java.util.Date;

@Alias("productSalesDetails")
public class ProductSalesDetailsPojo implements Serializable {

    public static long serialVersionUID = 8956412775623L;
    private Long id;
    private Long xid;
    private Long productId;
    private String userName;
    private Integer quantity;
    private Date saleDate;
    private String note;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getXid() {
        return xid;
    }

    public void setXid(Long xid) {
        this.xid = xid;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Date getSaleDate() {
        return saleDate;
    }

    public void setSaleDate(Date saleDate) {
        this.saleDate = saleDate;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
