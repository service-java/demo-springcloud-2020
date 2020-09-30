package cn.iocoder.springboot.lab16.springdatamongodbdemo.repository;

import cn.iocoder.springboot.lab16.springdatamongodbdemo.dataobject.ProductDO;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProductRepository extends MongoRepository<ProductDO, Integer> {
}
