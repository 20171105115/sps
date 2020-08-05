package com.app.dao;

import com.app.entity.PersonInfo;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonInfoDao {

    /**
     * 注册用户信息
     * @param personInfo
     * @return
     */
    int insertPersonInfo(PersonInfo personInfo);


}
