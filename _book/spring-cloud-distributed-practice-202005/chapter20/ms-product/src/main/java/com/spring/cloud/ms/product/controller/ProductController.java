package com.spring.cloud.ms.product.controller;

import com.spring.cloud.ms.common.vo.SuccessOrFailureMessage;
import com.spring.cloud.ms.product.database.DataSourcesContentHolder;
import com.spring.cloud.ms.common.pojo.ProductPojo;
import com.spring.cloud.ms.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class ProductController {

    @Autowired
    private ProductService productService = null;

    // 获取产品信息，可能从Redis缓存中读取
    @GetMapping("/product/{id}")
    public ProductPojo getProduct(@PathVariable("id") Long id) {
        // 设置ID，让多数据源按照其算法寻找对应的数据库
        DataSourcesContentHolder.setId(id);
        return productService.getProduct(id);
    }

    // 获取最新产品信息，不从Redis缓存读取
    @GetMapping("/product/latest/{id}")
    public ProductPojo getLatestProduct(@PathVariable("id") Long id) {
        // 设置ID，让多数据源按照其算法寻找对应的数据库
        DataSourcesContentHolder.setId(id);
        return productService.getLatestProduct(id);
    }

    /**
     *  因服务调用存在重试机制，这里需要注意幂等性的问题
     * @param xid —— 业务序列号
     * @param id ——产品编号
     * @param quantity —— 购买数量
     * @return 是否成功
     */
    @GetMapping("/product/stock/{xid}/{id}/{quantity}")
    public SuccessOrFailureMessage reduceStock(@PathVariable("xid") Long xid,
            @PathVariable("id") Long id, @PathVariable("quantity") Integer quantity) {
        DataSourcesContentHolder.setId(id);
        Integer result = productService.reduceStock(xid, quantity,id);
        if (result ==-1) {
            return new SuccessOrFailureMessage(false, "库存不足");
        } else if (result == 0){
            return new SuccessOrFailureMessage(false, "重复扣减");
        } else {
            return new SuccessOrFailureMessage(true, "扣减库存成功");
        }
    }



}
