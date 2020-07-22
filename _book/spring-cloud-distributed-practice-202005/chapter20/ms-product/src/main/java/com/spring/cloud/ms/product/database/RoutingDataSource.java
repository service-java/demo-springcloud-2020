package com.spring.cloud.ms.product.database;

import org.apache.commons.dbcp2.BasicDataSourceFactory;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.util.*;

@Component
// 继承AbstractRoutingDataSource
public class RoutingDataSource extends AbstractRoutingDataSource {

    // 数据源key列表
    private List<String> keyList = new ArrayList<>();

    // 数据库连接列表
    private String []urls = {
        "jdbc:mysql://localhost:3306/sc_chapter20_product_1" +
                "?serverTimezone=UTC",
        "jdbc:mysql://localhost:3306/sc_chapter20_product_2" +
                "?serverTimezone=UTC"
    };
    // 用户
    private String username = "root";
    // 密码
    private String password = "123456";
    // 驱动
    private String driverClassName = "com.mysql.jdbc.Driver";


    @PostConstruct // 让Spring在构造方法后执行
    public void init() {
        Map<Object, Object> targetDs = new HashMap<>();
        int count = 0;
        DataSource firstDs = null;
        for (String url : urls) { // 创建数据源
            Properties props = new Properties();
            // 设置属性
            props.setProperty("url", url);
            props.setProperty("username", username);
            props.setProperty("password", password);
            props.setProperty("driverClassName", driverClassName);
            DataSource ds = null;
            try {
                ds = BasicDataSourceFactory.createDataSource(props);
                firstDs = (firstDs == null ? ds : firstDs);
                count ++;
                String key = "datasource_" + count;
                targetDs.put(key, ds);
                keyList.add(key); // 数据源key
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        if (targetDs.isEmpty()) {
           throw  new RuntimeException("初始化多数据源错误");
        }
        // 设置目标数据源
        this.setTargetDataSources(targetDs);
        // 设置默认数据库
        this.setDefaultTargetDataSource(firstDs);

    }

    /**
     * 选取数据库策略
     * @return 数据库对应的key
     */
    @Override
    protected Object determineCurrentLookupKey() {
        // 获取线程副本中的变量值
        Long id = DataSourcesContentHolder.getId();
        // 求模算法
        Long idx =id % keyList.size();
        // 获取数据源key
        return keyList.get(idx.intValue());
    }
}
