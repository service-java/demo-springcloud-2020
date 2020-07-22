package com.spring.cloud.ms.product.dao;

import com.spring.cloud.ms.common.pojo.ProductSalesDetailsPojo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ProductSalesDetailsDao {

    public Integer insertProductSales(ProductSalesDetailsPojo productSalesDetailsPojo);
}
