package com.spring.cloud.ms.product.service.impl;

import com.spring.cloud.ms.common.key.SnowFlakeWorker;
import com.spring.cloud.ms.common.pojo.ProductSalesDetailsPojo;
import com.spring.cloud.ms.product.dao.ProductDao;
import com.spring.cloud.ms.common.pojo.ProductPojo;
import com.spring.cloud.ms.product.dao.ProductSalesDetailsDao;
import com.spring.cloud.ms.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductDao productDao = null;



    /**
     * 允许从缓存中读取
     * @param id -- 产品编号
     * @return 产品对象
     */
    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED)
    @Cacheable(value = "redis-cache", key = "'product_'+#id")
    public ProductPojo getProduct(Long id) {
        return productDao.getProduct(id);
    }

    /**
     * 不从缓存中读取，从数据库读取最新值，尽可能提高一致性的可能
     * @param id -- 产品编号
     * @return 产品对象
     */
    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public ProductPojo getLatestProduct(Long id) {
        return productDao.getProduct(id);
    }


    // 分布式id生成——雪花算法
    private SnowFlakeWorker snowFlakeWorker = null;

    @Bean
    public SnowFlakeWorker snowFlakeWorker(@Value("${database.center.id}") Long dataCenterId ) {
        if (snowFlakeWorker == null) {
            snowFlakeWorker = new SnowFlakeWorker(dataCenterId);
        }
        return snowFlakeWorker ;
    }

    @Autowired
    private ProductSalesDetailsDao productSalesDetailsDao = null;

    /**
     *  减库存，且记录产品减库存信息
     * @param xid -- 业务号
     * @param quantity -- 购买数量
     * @param id 产品编号
     * @return
     */
    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Integer reduceStock(Long xid, Integer quantity, Long id) {
        // 获取产品信息
        ProductPojo product = productDao.getProduct(id);
        if (quantity > product.getStock()) { // 库存不足返回-1
            return -1;
        }
        int result = productDao.reduceStock(xid, quantity, id);
        if (result == 0) { // 因为重复扣减失败
            return 0;
        }
        // 成功扣减，记录明细
        ProductSalesDetailsPojo details = initProductSalesDetails(xid, id, quantity);
        productSalesDetailsDao.insertProductSales(details);
        return result;
    }

    /**
     * 初始化产品减库存明细信息
     * @param xid 业务序号
     * @param id 产品编号
     * @param quantity 数量
     * @return 产品减库存明细信息
     */
    private ProductSalesDetailsPojo initProductSalesDetails(Long xid, Long id, Integer quantity) {

        ProductSalesDetailsPojo details = new ProductSalesDetailsPojo();
        // 使用雪花算法生成id
        details.setId(snowFlakeWorker.nextId());
        details.setXid(xid);
        details.setSaleDate(new Date());
        details.setProductId(id);
        details.setQuantity(quantity);
        // 获取当前用户认证信息
        Authentication authentication
                = SecurityContextHolder.getContext().getAuthentication();
        // 获取用户名
        String userName = authentication.getName();
        details.setUserName(userName);
        details.setNote("购买商品");
        return details;
    }
}
