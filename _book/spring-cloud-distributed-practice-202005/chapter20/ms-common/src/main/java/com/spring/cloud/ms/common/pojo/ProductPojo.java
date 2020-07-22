package com.spring.cloud.ms.common.pojo;

import org.apache.ibatis.type.Alias;
import java.io.Serializable;

@Alias("product")
public class ProductPojo implements Serializable {
    public static long serialVersionUID = 123929854756L;
    private Long id;
    private String productName;
    private Integer stock;
    private String note;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }


    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
