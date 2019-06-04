package com.hyjf.cs.message.bean.hgreportdata.caijing;

import java.io.Serializable;

/**
 * @Author: yinhui
 * @Date: 2019/6/3 11:06
 * @Version 1.0
 */
public class ZeroOneResponse implements Serializable {

    private static final long serialVersionUID = 1L;

    //返回码 数字 1 标识成功，否则是请求失败
    public Integer result_code;
    //返回消息
    public String result_msg;
    //报送数据总条数
    public Integer data_num;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }
}
