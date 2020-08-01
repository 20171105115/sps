package com.app.dao;

import com.app.entity.Area;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AreaDao {

    List<Area> queryAreaList();
}
