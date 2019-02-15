/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.trade.util;

import java.util.Objects;

/**
 * 资产属性
 * @author dangzw
 * @version AssetAttributesEnum, v0.1 2019/2/14 15:07
 */
public enum AssetAttributesEnum {
    ONE(1,"抵押标"),
    TWO(2,"质押标"),
    THREE(3,"信用标");

    private Integer key;//编码
    private String asset;//属性

    AssetAttributesEnum(Integer key,String asset){
        this.key = key;
        this.asset = asset;
    }

    public static String getAsset(Integer key){

        for (AssetAttributesEnum e:AssetAttributesEnum.values()){

//            if(key.equals(e.key)){
            if(Objects.equals(key, e.key)){
                return e.asset;
            }
        }
        return null;
    }

    public Integer getKey() {
        return key;
    }

    public void setKey(Integer key) {
        this.key = key;
    }

    public String getAsset() {
        return asset;
    }

    public void setAsset(String asset) {
        this.asset = asset;
    }
}
