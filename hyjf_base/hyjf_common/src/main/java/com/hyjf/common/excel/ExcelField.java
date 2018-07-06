/*
 * Copyright (c) 2015, FPX and/or its affiliates. All rights reserved.
 * Use, Copy is subject to authorized license.
 *
 */
package com.hyjf.common.excel;

import java.io.Serializable;

/**
 * @author xiasq
 * @version ExcelField, v0.1 2018/7/4 14:35 excel列标题和列属性对应关系
 */
public class ExcelField implements Serializable {

    public ExcelField(String name, String value) {
        this.name = name;
        this.value = value;
    }
    /** 列标题名 */
    private String name;
    /** 列属性字段 */
    private String value;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}

