package com.app.daoTest;

import com.app.BaseTest;
import com.app.dao.AreaDao;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.assertEquals;

public class AreaDaoTest extends BaseTest {

    @Autowired
    private AreaDao areaDao;


    @Test
    public void testQueryAreaList(){
        assertEquals(2,areaDao.queryAreaList().size());
    }
}
