package com.hyjf.am.trade.service.admin.borrow.impl;

import com.hyjf.am.resquest.admin.AdminBorrowFlowRequest;
import com.hyjf.am.trade.dao.mapper.auto.BorrowProjectTypeMapper;
import com.hyjf.am.trade.dao.mapper.auto.HjhAssetBorrowtypeMapper;
import com.hyjf.am.trade.dao.mapper.auto.HjhAssetTypeMapper;
import com.hyjf.am.trade.dao.mapper.auto.HjhInstConfigMapper;
import com.hyjf.am.trade.dao.mapper.customize.BorrowProjectTypeCustomizeMapper;
import com.hyjf.am.trade.dao.mapper.customize.HjhAssetBorrowTypeCustomizeMapper;
import com.hyjf.am.trade.dao.model.auto.*;
import com.hyjf.am.trade.service.admin.borrow.BorrowFlowService;
import com.hyjf.am.vo.admin.HjhAssetTypeVO;
import com.hyjf.am.vo.trade.borrow.BorrowProjectTypeVO;
import com.hyjf.am.vo.user.HjhInstConfigVO;
import com.hyjf.common.util.CommonUtils;
import com.hyjf.common.util.CustomConstants;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Date;
import java.util.List;

/**
 * @author by xiehuili on 2018/7/30.
 */
@Service
public class BorrowFlowServiceImpl implements BorrowFlowService {

    @Autowired
    private BorrowProjectTypeCustomizeMapper borrowProjectTypeCustomizeMapper;
    @Autowired
    private BorrowProjectTypeMapper borrowProjectTypeMapper;
    @Autowired
    private HjhInstConfigMapper hjhInstConfigMapper;
    @Autowired
    private HjhAssetBorrowtypeMapper hjhAssetBorrowtypeMapper;
    @Autowired
    private HjhAssetTypeMapper hjhAssetTypeMapper;
    @Autowired
    private HjhAssetBorrowTypeCustomizeMapper hjhAssetBorrowTypeCustomizeMapper;
    /**
     * 项目类型
     * @return
     */
    @Override
    public List<BorrowProjectTypeVO> selectBorrowProjectTypeList(String borrowTypeCd){
        BorrowProjectTypeVO vo = new BorrowProjectTypeVO();

        vo.setStatus(Integer.valueOf(CustomConstants.FLAG_NORMAL));
        if (StringUtils.isNotEmpty(borrowTypeCd)) {
            vo.setBorrowProjectType(borrowTypeCd);
        }
        // 不查询融通宝相关
        vo.setBorrowName(CustomConstants.RTB);
        List<BorrowProjectTypeVO> borrowProjectTypeVOS = borrowProjectTypeCustomizeMapper.selectProjectTypeListGroupBy(vo);

        return borrowProjectTypeVOS;
    }
    /**
     * 资金来源
     * @param instCode
     * @return
     */
    @Override
    public List<HjhInstConfigVO> hjhInstConfigList(@PathVariable String instCode){
        HjhInstConfigExample example = new HjhInstConfigExample();
        HjhInstConfigExample.Criteria cra = example.createCriteria();
//		cra.andDelFlgEqualTo(Integer.valueOf(CustomConstants.FLAG_NORMAL));
        if (StringUtils.isNotEmpty(instCode)) {
            cra.andInstCodeEqualTo(instCode);
        }
        cra.andDelFlagEqualTo(0);
        List<HjhInstConfig> hjhInstConfig =this.hjhInstConfigMapper.selectByExample(example);
        List<HjhInstConfigVO> hjhInstConfigVOS =CommonUtils.convertBeanList(hjhInstConfig,HjhInstConfigVO.class);
        return hjhInstConfigVOS;
    }

    /**
     * 根据表的类型,期数,项目类型检索管理费件数
     * @param instCode assetType
     * @return
     */
    @Override
    public int countRecordByPK(@PathVariable String instCode, @PathVariable Integer assetType){
        HjhAssetBorrowtypeExample example = new HjhAssetBorrowtypeExample();
        HjhAssetBorrowtypeExample.Criteria cra = example.createCriteria();
        cra.andInstCodeEqualTo(instCode);
        cra.andAssetTypeEqualTo(assetType);
        return hjhAssetBorrowtypeMapper.countByExample(example);
    }

    /**
     * 根据资金来源查询产品类型
     * @param instCode
     * @return
     */
    @Override
    public List<HjhAssetTypeVO> hjhAssetTypeList(@PathVariable String instCode){
        HjhAssetTypeExample example = new HjhAssetTypeExample();
        HjhAssetTypeExample.Criteria cra = example.createCriteria();
        cra.andStatusEqualTo(0);
        if (org.apache.commons.lang.StringUtils.isEmpty(instCode)) {
            return null;
        }
        cra.andInstCodeEqualTo(instCode);
        cra.andDelFlagEqualTo(0);
        List<HjhAssetType> hjhAssetType=hjhAssetTypeMapper.selectByExample(example);
        List<HjhAssetTypeVO> hjhAssetTypeVOS=CommonUtils.convertBeanList(hjhAssetType,HjhAssetTypeVO.class);
        return hjhAssetTypeVOS;
    }
    /**
     * 件数
     * @param form
     * @return
     */
    @Override
    public int countRecord(AdminBorrowFlowRequest form){
        return hjhAssetBorrowTypeCustomizeMapper.countRecord(form);
    }
    /**
     * 列表
     * @param form
     * @param limitStart
     * @param limitEnd
     * @return
     */
    @Override
    public List<HjhAssetBorrowtype> getRecordList(AdminBorrowFlowRequest form, int limitStart, int limitEnd){
        if (limitStart != -1) {
            form.setLimitStart(limitStart);
            form.setLimitEnd(limitEnd);
        }
        return hjhAssetBorrowTypeCustomizeMapper.getRecordList(form);
    }

    /**
     *详情
     * @param id
     * @return
     */
    @Override
    public HjhAssetBorrowtype selectBorrowFlowInfo( Integer id){
        return hjhAssetBorrowtypeMapper.selectByPrimaryKey(id);
    }

    /**
     * 添加
     * @param adminRequest
     * @return
     */
    @Override
    public int insertRecord(AdminBorrowFlowRequest adminRequest){
        HjhAssetBorrowtype record = new HjhAssetBorrowtype();
        BeanUtils.copyProperties(adminRequest, record);
        record.setCreateTime(new Date());
        record.setUpdateTime(new Date());
        return hjhAssetBorrowtypeMapper.insertSelective(record);
    }
    /**
     * 修改
     * @param adminRequest
     * @return
     */
    @Override
    public int updateRecord(AdminBorrowFlowRequest adminRequest){
        HjhAssetBorrowtype record = new HjhAssetBorrowtype();
        BeanUtils.copyProperties(adminRequest, record);
        //自动复审时，自动发标时间间隔和自动复审时间间隔清空
        if(adminRequest.getAutoReview() != null&&adminRequest.getAutoReview().intValue() == 1){
            record.setAutoSendMinutes(-1);
            record.setAutoReviewMinutes(-1);
        }
        // 更新时间
        record.setUpdateTime(new Date());
        return hjhAssetBorrowTypeCustomizeMapper.updateByPrimaryKeySelective(record);
    }
    /**
     * 删除
     * @param id
     * @return
     */
    @Override
    public int deleteRecord(Integer id){
        if (null != id){
            return  hjhAssetBorrowtypeMapper.deleteByPrimaryKey(id);
        }
        return 0;
    }
}
