package com.jianan.fingerboard.bow.store;

import com.jianan.fingerboard.bow.dao.CountryDao;
import com.jianan.fingerboard.bow.spring.SpringBeanFactory;
import com.jianan.fingerboard.tuner.json.JsonUtil;

import java.util.List;

/**
 * @author by jianan.liu on 16/12/2.
 */
public class DataStoreHandler {

    public static String query(){
        CountryDao countryDao = SpringBeanFactory.getBean(CountryDao.class);
        List<String> name = countryDao.queryAllCountryZh();
        return JsonUtil.serialize(name);
    }
}
