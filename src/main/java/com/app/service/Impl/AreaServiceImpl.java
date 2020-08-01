package com.app.service.Impl;

import com.app.dao.AreaDao;
import com.app.entity.Area;
import com.app.service.AreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AreaServiceImpl implements AreaService {

    @Autowired
    private AreaDao areaDao;

    public List<Area> getAreaList() {
        return areaDao.queryAreaList();
    }
}
