package com.jianan.fingerboard.tuner.bean;

import java.io.Serializable;

/**
 * @author by jianan.liu on 16/12/7.
 */
public class Country implements Serializable{
    private String code;
    private String nameZh;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getNameZh() {
        return nameZh;
    }

    public void setNameZh(String nameZh) {
        this.nameZh = nameZh;
    }
}
