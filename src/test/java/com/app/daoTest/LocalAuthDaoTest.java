package com.app.daoTest;

import com.app.BaseTest;
import com.app.dao.LocalAuthDao;
import com.app.entity.LocalAuth;
import com.app.entity.PersonInfo;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import static org.junit.Assert.assertEquals;

public class LocalAuthDaoTest extends BaseTest {

    @Autowired
    private LocalAuthDao localAuthDao;

    @Test
    public void testInsertLocalAuth(){

        LocalAuth localAuth = new LocalAuth();
        PersonInfo owner = new PersonInfo();
        owner.setUserId(2);
        localAuth.setOwner(owner);

        localAuth.setCreateTime(new Date());
        localAuth.setPassword("test");


        Random r = new Random();
        int num = r.nextInt(100) + 899;
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        String dateStr = format.format(new Date());
//        System.out.println(dateStr);
        String username = dateStr+num;
//        System.out.println(username);


        localAuth.setUsername(username);
        localAuth.setLastEditTime(new Date());
        localAuth.setStatus(1);

        assertEquals(1,localAuthDao.insertLocalAuth(localAuth));
    }

    @Test
    public void testQueryLocalAuthByUserName(){
        LocalAuth localAuth = localAuthDao.queryLocalAuthByUserName("20200805914");
        System.out.println(localAuth.getOwner().getName());
    }

    @Test
    public void testUpdateLocalAuth(){
        LocalAuth localAuth = new LocalAuth();
        localAuth.setLastEditTime(new Date());
        PersonInfo owner = new PersonInfo();
        owner.setUserId(2);
        localAuth.setOwner(owner);
        assertEquals(1,localAuthDao.updatePersonInfo(localAuth,"123456"));

    }
}
