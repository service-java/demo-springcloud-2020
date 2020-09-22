package com.forezp.service;

import com.forezp.dao.SysLogDao;
import com.forezp.entity.SysLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by fangzhipeng on 2017/7/12.
 */
@Service
public class SysLogService {

    @Autowired
    SysLogDao sysLogDao;

    public void saveLogger(SysLog sysLog){
        sysLogDao.save(sysLog);
    }
}
