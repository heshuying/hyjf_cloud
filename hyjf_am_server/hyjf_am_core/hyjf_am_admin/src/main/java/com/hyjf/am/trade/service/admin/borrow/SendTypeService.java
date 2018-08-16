package com.hyjf.am.trade.service.admin.borrow;

import com.hyjf.am.resquest.admin.BorrowSendTypeRequest;
import com.hyjf.am.trade.dao.model.auto.BorrowSendType;

import java.util.List;

/**
 * @author by xiehuili on 2018/8/1.
 */
public interface SendTypeService {

    /*
    * 分页查询配置中心发标复标
    * */
    public List<BorrowSendType> selectSendTypeListByPage(BorrowSendType borrowSendType, int limitStart, int limitEnd);

    /*
    * 查询发标复标记录
    * */
    public BorrowSendType selectSendTypeInfo(String sendCd);
    /**
     *
     * 插入操作
     * @param form
     * @return
     */
    public int insertSendType(BorrowSendTypeRequest form);
    /**
     *
     * 修改操作
     * @param form
     * @return
     */
    public int updateSendType(BorrowSendTypeRequest form);
    /**
     *
     * 删除操作
     * @param sendCd
     * @return
     */
    public int deleteSendType(String sendCd);
}
