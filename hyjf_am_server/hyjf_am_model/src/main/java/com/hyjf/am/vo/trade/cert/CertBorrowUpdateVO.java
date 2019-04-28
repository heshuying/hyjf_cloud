/*
 * @Copyright: 2005-2018 so_what. All rights reserved.
 */
package com.hyjf.am.vo.trade.cert;

import java.util.List;

/**
 * @Description 数据库批量操作
 */
public class CertBorrowUpdateVO {

    /**
     * 要修改的对象的ID集合
     */
    private List<Integer> ids ;
    /**
     * 自己new一个要修改哪个字段set哪个字段
     */
    private CertBorrowVO certBorrow;

    public List<Integer> getIds() {
        return ids;
    }

    public void setIds(List<Integer> ids) {
        this.ids = ids;
    }

    public CertBorrowVO getCertBorrow() {
        return certBorrow;
    }

    public void setCertBorrow(CertBorrowVO certBorrow) {
        this.certBorrow = certBorrow;
    }
}
