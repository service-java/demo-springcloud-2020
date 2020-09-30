package cn.iocoder.springboot.lab16.springdatamongodbdemo.repository;

import cn.iocoder.springboot.lab16.springdatamongodbdemo.dataobject.UserDO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository02 extends MongoRepository<UserDO, Integer> {

    UserDO findByUsername(String username);

    Page<UserDO> findByUsernameLike(String username, Pageable pageable);

}
