package com.spring.cloud.product.facade;

import com.netflix.hystrix.contrib.javanica.cache.annotation.CacheKey;
import com.spring.cloud.common.pojo.UserInfo;
import com.spring.cloud.common.vo.ResultMessage;
import rx.Observable;

import java.util.List;
import java.util.concurrent.Future;

public interface UserFacade {

    public ResultMessage timeout();

    public ResultMessage exp(String msg);

    public ResultMessage timeout2();

    public Future<ResultMessage> asyncTimeout();

    public List<ResultMessage> exp2(String [] params);

    public Observable<ResultMessage> asyncExp(String[] params);

    public String dealFile(String filePath);

    public UserInfo testUserInfo(Long id);

    public UserInfo updateUserInfo(UserInfo user);

    public UserInfo getUserInfo(@CacheKey Long id);

    public UserInfo getUser(Long id);

    public List<UserInfo> findUsers(Long[] ids);

    public Future<UserInfo> getUser2(Long id);

    public List<UserInfo> findUsers2(List<Long> ids);
}
