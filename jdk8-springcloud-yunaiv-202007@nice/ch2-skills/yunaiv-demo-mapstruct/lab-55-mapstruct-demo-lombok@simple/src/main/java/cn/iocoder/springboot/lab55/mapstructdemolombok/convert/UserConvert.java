package cn.iocoder.springboot.lab55.mapstructdemolombok.convert;

import cn.iocoder.springboot.lab55.mapstructdemolombok.bo.UserBO;
import cn.iocoder.springboot.lab55.mapstructdemolombok.dataobject.UserDO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserConvert {

    UserConvert INSTANCE = Mappers.getMapper(UserConvert.class);

    UserBO convert(UserDO userDO);

}
