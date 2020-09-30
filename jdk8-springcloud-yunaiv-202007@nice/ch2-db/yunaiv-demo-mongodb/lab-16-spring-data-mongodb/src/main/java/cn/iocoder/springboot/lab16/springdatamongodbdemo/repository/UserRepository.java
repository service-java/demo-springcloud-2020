package cn.iocoder.springboot.lab16.springdatamongodbdemo.repository;

import cn.iocoder.springboot.lab16.springdatamongodbdemo.dataobject.UserDO;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<UserDO, Integer> {
}
