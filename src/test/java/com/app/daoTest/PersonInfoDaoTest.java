package com.app.daoTest;

import com.app.BaseTest;
import com.app.dao.PersonInfoDao;
import com.app.entity.Area;
import com.app.entity.PersonInfo;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

import static org.junit.Assert.assertEquals;

public class PersonInfoDaoTest extends BaseTest {

    @Autowired
    private PersonInfoDao personInfoDao;


    @Test
    public void testInsertPersonInfo(){

        PersonInfo owner = new PersonInfo();
        owner.setCreateTime(new Date());
        owner.setGender(1);
        owner.setName("朱博宇");
        owner.setLastEditTime(new Date());
        owner.setPhone("1333333333");
        Area area = new Area();
        area.setAreaId(1);
        owner.setArea(area);

        assertEquals(1,personInfoDao.insertPersonInfo(owner));
    }
}
