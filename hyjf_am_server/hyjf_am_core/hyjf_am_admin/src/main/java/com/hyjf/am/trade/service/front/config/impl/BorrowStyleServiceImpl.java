package com.hyjf.am.trade.service.front.config.impl;

import com.hyjf.am.resquest.admin.AdminBorrowStyleRequest;
import com.hyjf.am.trade.dao.mapper.auto.BorrowStyleMapper;
import com.hyjf.am.trade.dao.model.auto.BorrowStyle;
import com.hyjf.am.trade.dao.model.auto.BorrowStyleExample;
import com.hyjf.am.trade.dao.model.auto.BorrowStyleWithBLOBs;
import com.hyjf.am.trade.service.front.config.BorrowStyleService;
import com.hyjf.common.util.CustomConstants;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author by xiehuili on 2018/7/12.
 */
@Service
public class BorrowStyleServiceImpl implements BorrowStyleService {
    @Autowired
    private BorrowStyleMapper borrowStyleMapper;
    /**
     * 查询还款方式总数
     * @param
     */
    @Override
    public Integer getBorrowStyleCount(){
        BorrowStyleExample example = new BorrowStyleExample();
        return borrowStyleMapper.countByExample(example);
    }
    /**
     * 查询还款方式分页记录
     * @param limitStart,limitEnd
     */
    @Override
    public List<BorrowStyleWithBLOBs> selectBorrowStyleListByPage(AdminBorrowStyleRequest request,int limitStart, int limitEnd){
        BorrowStyleExample example = new BorrowStyleExample();
        if (limitStart != -1) {
            example.setLimitStart(limitStart);
            example.setLimitEnd(limitEnd);
        }
        return borrowStyleMapper.selectByExampleWithBLOBs(example);
    }
    /**
     * 根据id查询还款方式
     * @param id
     */
    @Override
    public BorrowStyleWithBLOBs searchBorrowStyleInfoById(Integer id){
        return borrowStyleMapper.selectByPrimaryKey(id);
    }

    /**
     * 根据主键判断权限维护中权限是否存在
     *
     * @return
     */
    @Override
    public boolean isExistsPermission(BorrowStyle record) {
        BorrowStyleExample example = new BorrowStyleExample();
        BorrowStyleExample.Criteria cra = example.createCriteria();
        cra.andNameEqualTo(record.getName());
        if (StringUtils.isNotEmpty(record.getNid())) {
            cra.andNidEqualTo(record.getNid());
        }
        List<BorrowStyle> BorrowStyleList = borrowStyleMapper.selectByExample(example);
        if (BorrowStyleList != null && BorrowStyleList.size() > 0) {
            return true;
        }
        return false;
    }
    /**
     *  添加还款方式
     * @param adminRequest
     */
    @Override
    public void insertBorrowStyle(AdminBorrowStyleRequest adminRequest){
        BorrowStyleWithBLOBs borrowStyle = new BorrowStyleWithBLOBs();
        BeanUtils.copyProperties(adminRequest,borrowStyle);
        borrowStyleMapper.insertSelective(borrowStyle);
    }
    /**
     *  修改还款方式
     * @param adminRequest
     */
    @Override
    public void updateBorrowStyleById(AdminBorrowStyleRequest adminRequest){
        BorrowStyleWithBLOBs borrowStyle = new BorrowStyleWithBLOBs();
        BeanUtils.copyProperties(adminRequest,borrowStyle);
        borrowStyleMapper.updateByPrimaryKeySelective(borrowStyle);
    }
    /**
     * 根据id删除还款方式
     * @param id
     */
    @Override
    public void deleteBorrowStyleById(Integer id){
        BorrowStyleWithBLOBs record = new BorrowStyleWithBLOBs();
        record.setId(id);
        record.setStatus(CustomConstants.FLAG_STATUS_ENABLE);
        borrowStyleMapper.updateByPrimaryKeySelective(record);
    }

    /**
     * 获取还款方式
     * @param borrowStyle
     * @return
     */
    @Override
    public BorrowStyle getBorrowStyle(String borrowStyle) {
        BorrowStyleExample example = new BorrowStyleExample();
        example.createCriteria().andNidEqualTo(borrowStyle);
        List<BorrowStyle> borrowStyles = this.borrowStyleMapper.selectByExample(example);
        if (CollectionUtils.isNotEmpty(borrowStyles)){
            return borrowStyles.get(0);
        }
        return null;
    }

}