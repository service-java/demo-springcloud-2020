package cn.iocoder.springboot.lab18.shardingdatasource.demo22.mapper;

import cn.iocoder.springboot.lab18.shardingdatasource.demo22.dataobject.OrderDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderMapper extends BaseMapper<OrderDO> {

}
