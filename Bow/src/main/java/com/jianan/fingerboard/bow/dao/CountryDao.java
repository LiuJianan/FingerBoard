package com.jianan.fingerboard.bow.dao;

import com.jianan.fingerboard.bow.annotation.AutoDao;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author by jianan.liu on 16/12/4.
 */
@AutoDao
public interface CountryDao {

    /**
     * query country name (zh) by country 2 code.
     * 
     * @param code
     * @return
     */
    String queryCountryZhNameByCode(@Param("country2code") String code);

    /**
     * query all country name
     * @return
     */
    List<String> queryAllCountryZh();
}
