package com.app.dao;

import com.app.entity.LocalAuth;
import org.apache.ibatis.annotations.Param;

public interface LocalAuthDao {

    /**
     * 注册用户账号
     * @param localAuth
     * @return
     */
    int insertLocalAuth(LocalAuth localAuth);

    /**
     * 修改密码
     *
     * @param localAuth
     * @param newPassword
     * @return
     */
    int updatePersonInfo(@Param("localAuth") LocalAuth localAuth, @Param("newPassword") String newPassword);


    /**
     * 通过账号查询用户信息
     * @param username
     * @return
     */
    LocalAuth queryLocalAuthByUserName(String username);
}
