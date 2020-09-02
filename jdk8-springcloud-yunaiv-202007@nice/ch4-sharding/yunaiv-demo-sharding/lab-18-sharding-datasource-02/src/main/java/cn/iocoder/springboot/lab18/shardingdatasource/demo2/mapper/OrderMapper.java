package cn.iocoder.springboot.lab18.shardingdatasource.demo2.mapper;

import cn.iocoder.springboot.lab18.shardingdatasource.demo2.dataobject.OrderDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderMapper extends BaseMapper<OrderDO> {

}
