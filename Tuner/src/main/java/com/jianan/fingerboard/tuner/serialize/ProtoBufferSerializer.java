package com.jianan.fingerboard.tuner.serialize;

import java.io.UnsupportedEncodingException;

import com.google.protobuf.ByteString;
import com.google.protobuf.InvalidProtocolBufferException;
import com.jianan.fingerboard.tuner.bean.CountryItem;

/**
 * @author by jianan.liu on 16/12/6.
 */
public class ProtoBufferSerializer {

    public static void main(String[] args) throws InvalidProtocolBufferException, UnsupportedEncodingException {
        CountryItem.Country.Builder builder = CountryItem.Country.newBuilder();
        builder.setCode("CH");
        builder.setNameZh(ByteString.copyFrom("中国", "utf-8"));
        byte[] bytes = builder.build().toByteArray();
        System.out.println(bytes.length);
        CountryItem.Country country = CountryItem.Country.parseFrom(bytes);
        System.out.println(country.getCode());
        System.out.println(country.getNameZh().toString("utf-8"));
    }

}
