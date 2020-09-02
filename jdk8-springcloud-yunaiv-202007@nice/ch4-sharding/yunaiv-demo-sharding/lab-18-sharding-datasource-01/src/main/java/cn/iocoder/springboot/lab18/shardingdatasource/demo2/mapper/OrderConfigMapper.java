package cn.iocoder.springboot.lab18.shardingdatasource.demo2.mapper;

import cn.iocoder.springboot.lab18.shardingdatasource.demo2.dataobject.OrderConfigDO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderConfigMapper {

    OrderConfigDO selectById(@Param("id") Integer id);

}
