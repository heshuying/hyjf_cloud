package com.hyjf.am.trade.service.admin.borrow.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hyjf.am.resquest.admin.BorrowSendTypeRequest;
import com.hyjf.am.trade.dao.mapper.auto.BorrowSendTypeMapper;
import com.hyjf.am.trade.dao.model.auto.BorrowSendType;
import com.hyjf.am.trade.dao.model.auto.BorrowSendTypeExample;
import com.hyjf.am.trade.service.admin.borrow.SendTypeService;
import com.hyjf.common.util.GetDate;

/**
 * @author by xiehuili on 2018/8/1.
 */
@Service
public class SendTypeServiceImpl implements SendTypeService {
    @Autowired
    private BorrowSendTypeMapper borrowSendTypeMapper;
    /*
   * 分页查询配置中心发标复标
   * */
    @Override
    public List<BorrowSendType> selectSendTypeListByPage(BorrowSendType borrowSendType, int limitStart, int limitEnd){
        BorrowSendTypeExample example = new BorrowSendTypeExample();
        if (limitStart != -1) {
            example.setLimitStart(limitStart);
            example.setLimitEnd(limitEnd);
        }
        return borrowSendTypeMapper.selectByExample(example);
    }

    /*
    * 查询发标复标记录
    * */
    @Override
    public BorrowSendType selectSendTypeInfo(String sendCd){
        return borrowSendTypeMapper.selectByPrimaryKey(sendCd);
    }
    /**
     *
     * 插入操作
     * @param form
     * @return
     */
    @Override
    public int insertSendType(BorrowSendTypeRequest form){
        BorrowSendType record = new BorrowSendType();
        record.setSendCd(form.getSendCd());
        record.setSendName(form.getSendName());
        record.setAfterTime(Integer.valueOf(form.getAfterTime()));
        if (StringUtils.isEmpty(form.getRemark())) {
            record.setRemark(StringUtils.EMPTY);
        } else {
            record.setRemark(form.getRemark());
        }
        record.setCreateTime(new Date());
        record.setUpdateTime(new Date());
       return borrowSendTypeMapper.insertSelective(record);
    }
    /**
     *
     * 修改操作
     * @param form
     * @return
     */
    @Override
    public int updateSendType(BorrowSendTypeRequest form){
        int nowTime = GetDate.getNowTime10();
        BorrowSendType record = new BorrowSendType();
        record.setSendCd(form.getSendCd());
        record.setSendName(form.getSendName());
        record.setAfterTime(Integer.valueOf(form.getAfterTime()));
        if (StringUtils.isEmpty(form.getRemark())) {
            record.setRemark(StringUtils.EMPTY);
        } else {
            record.setRemark(form.getRemark());
        }
        record.setUpdateTime(new Date());
       return borrowSendTypeMapper.updateByPrimaryKeySelective(record);
    }
    /**
     *
     * 删除操作
     * @param sendCd
     * @return
     */
    @Override
    public int deleteSendType(String sendCd){
        return borrowSendTypeMapper.deleteByPrimaryKey(sendCd);
    }
}
