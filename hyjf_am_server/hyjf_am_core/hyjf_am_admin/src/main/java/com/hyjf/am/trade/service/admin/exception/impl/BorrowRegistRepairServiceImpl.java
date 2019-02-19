/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service.admin.exception.impl;

import com.hyjf.am.admin.mq.base.CommonProducer;
import com.hyjf.am.admin.mq.base.MessageContent;
import com.hyjf.am.resquest.admin.BorrowRegistListRequest;
import com.hyjf.am.resquest.admin.BorrowRegistUpdateRequest;
import com.hyjf.am.trade.dao.mapper.auto.BorrowProjectTypeMapper;
import com.hyjf.am.trade.dao.mapper.auto.BorrowStyleMapper;
import com.hyjf.am.trade.dao.mapper.auto.StzhWhiteListMapper;
import com.hyjf.am.trade.dao.model.auto.*;
import com.hyjf.am.trade.dao.model.customize.BorrowRegistCustomize;
import com.hyjf.am.trade.service.admin.exception.BorrowRegistRepairService;
import com.hyjf.am.trade.service.impl.BaseServiceImpl;
import com.hyjf.am.vo.trade.borrow.BorrowAndInfoVO;
import com.hyjf.common.cache.CacheUtil;
import com.hyjf.common.constants.MQConstant;
import com.hyjf.common.exception.MQException;
import com.hyjf.common.util.CommonUtils;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.util.GetDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: sunpeikai
 * @version: BorrowRegistRepairServiceImpl, v0.1 2018/7/3 15:07
 */
@Service(value = "tradeBorrowRegistRepairServiceImpl")
public class BorrowRegistRepairServiceImpl extends BaseServiceImpl implements BorrowRegistRepairService {

    @Autowired
    private BorrowProjectTypeMapper borrowProjectTypeMapper;

    @Autowired
    private BorrowStyleMapper borrowStyleMapper;

    @Autowired
    private StzhWhiteListMapper stzhWhiteListMapper;

    @Autowired
    private CommonProducer commonProducer;

    /**
     * 获取项目类型,筛选条件展示
     * @auth sunpeikai
     * @param
     * @return 项目类型list封装
     */
    @Override
    public List<BorrowProjectType> selectBorrowProjectList() {
        BorrowProjectTypeExample example = new BorrowProjectTypeExample();
        BorrowProjectTypeExample.Criteria cra = example.createCriteria();
        cra.andStatusEqualTo(Integer.valueOf(CustomConstants.FLAG_NORMAL));
        // 不查询融通宝相关
        cra.andBorrowNameNotEqualTo(CustomConstants.RTB);

        return borrowProjectTypeMapper.selectByExample(example);
    }

    /**
     * 获取还款方式,筛选条件展示
     * @auth sunpeikai
     * @param
     * @return 还款方式list封装
     */
    @Override
    public List<BorrowStyle> selectBorrowStyleList(){
        BorrowStyleExample example = new BorrowStyleExample();
        BorrowStyleExample.Criteria cra = example.createCriteria();
        cra.andStatusEqualTo(Integer.valueOf(CustomConstants.FLAG_NORMAL));
        return borrowStyleMapper.selectByExample(example);
    }

    /**
     * 获取标的列表count,用于前端分页展示
     * @auth sunpeikai
     * @param borrowRegistListRequest 筛选条件
     * @return
     */
    @Override
    public Integer getRegistCount(BorrowRegistListRequest borrowRegistListRequest){
        return adminBorrowRegistExceptionMapper.countBorrow(borrowRegistListRequest);
    }

    /**
     * 获取标的列表
     * @auth sunpeikai
     * @param borrowRegistListRequest 筛选条件
     * @return 异常标list封装
     */
    @Override
    public List<BorrowRegistCustomize> selectBorrowRegistList(BorrowRegistListRequest borrowRegistListRequest){
        List<BorrowRegistCustomize> list = adminBorrowRegistExceptionMapper.selectBorrowList(borrowRegistListRequest);
        if(!CollectionUtils.isEmpty(list)){
            //处理标的备案状态
            Map<String, String> map = CacheUtil.getParamNameMap("REGIST_STATUS");
            if(!CollectionUtils.isEmpty(map)){
                for(BorrowRegistCustomize borrowRegistCustomize : list){
                    borrowRegistCustomize.setRegistStatusName(map.getOrDefault(borrowRegistCustomize.getRegistStatus(),null));
                }
            }
        }
        return list;
    }

    /**
     * 根据borrowNid查询borrow信息
     * @auth sunpeikai
     * @param
     * @return
     */
    @Override
    public BorrowAndInfoVO searchBorrowByBorrowNid(String borrowNid) {
        // 获取相应的标的详情
        return this.getBorrowAndInfoByNid(borrowNid);
    }

    /**
     * 根据受托支付userId查询stAccountId
     * @auth sunpeikai
     * @param entrustedUserId 受托支付userId
     * @return
     */
    @Override
    public String getStAccountIdByEntrustedUserId(Integer entrustedUserId) {
        StzhWhiteListExample stzhWhiteListExample = new StzhWhiteListExample();
        StzhWhiteListExample.Criteria sTZHCra = stzhWhiteListExample.createCriteria();
        sTZHCra.andStUserIdEqualTo(entrustedUserId);
        List<StzhWhiteList> sTZHWhiteList = stzhWhiteListMapper.selectByExample(stzhWhiteListExample);
        if (sTZHWhiteList != null && !sTZHWhiteList.isEmpty()) {
            StzhWhiteList stzf = sTZHWhiteList.get(0);
            return stzf.getStAccountId();
        }
        return "";
    }

    /**
     * 更新标
     * @auth sunpeikai
     * @param registUpdateRequest 1更新标的备案 2更新受托支付标的备案
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean updateBorrowRegistByType(BorrowRegistUpdateRequest registUpdateRequest) {
        Integer type = registUpdateRequest.getType();
        BorrowAndInfoVO borrowAndInfoVO = registUpdateRequest.getBorrowVO();
        Borrow borrow = CommonUtils.convertBean(borrowAndInfoVO,Borrow.class);
        BorrowExample example = new BorrowExample();
        if(type == 1){
            // 更新备案
            example.createCriteria().andIdEqualTo(borrow.getId()).andStatusEqualTo(borrow.getStatus()).andRegistStatusEqualTo(borrow.getRegistStatus());
        }else{
            // 更新受托支付备案
            example.createCriteria().andIdEqualTo(borrow.getId());
        }
        borrow.setStatus(registUpdateRequest.getStatus());
        borrow.setRegistStatus(registUpdateRequest.getRegistStatus());
        borrow.setRegistTime(new Date());
        return borrowMapper.updateByExampleSelective(borrow, example) > 0;
    }

    /**
     * 更新标的资产信息如果关联计划的话
     * @auth sunpeikai
     * @param status 状态  受托支付传4，非受托支付传5
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean updateBorrowAsset(BorrowAndInfoVO borrowVO, Integer status) {
        boolean borrowFlag = false;

        HjhPlanAsset hjhPlanAsset = this.selectAssetByBorrow(borrowVO);
        if(hjhPlanAsset != null){
            logger.info(borrowVO.getInstCode()+" 机构,资产类型  "+borrowVO.getAssetType()+" 更新资产表状态为初审中,标的号 "+borrowVO.getBorrowNid());
            // 更新资产表
            HjhPlanAsset hjhPlanAssetnew = new HjhPlanAsset();
            hjhPlanAssetnew.setId(hjhPlanAsset.getId());
            hjhPlanAssetnew.setStatus(status);//初审中
            //获取当前时间
            Date nowTime = GetDate.getNowTime();
            hjhPlanAssetnew.setUpdateTime(nowTime);
            hjhPlanAssetnew.setUpdateUserId(1);

            borrowFlag = this.hjhPlanAssetMapper.updateByPrimaryKeySelective(hjhPlanAssetnew)>0;

            // 受托支付备案失败推送
            if(borrowVO.getEntrustedFlg() !=1){
                // 加入到消息队列
                Map<String, String> params = new HashMap<>();
                params.put("assetId", hjhPlanAsset.getAssetId());
                params.put("instCode", hjhPlanAsset.getInstCode());
				try {
					commonProducer.messageSend(new MessageContent(MQConstant.AUTO_BORROW_PREAUDIT_TOPIC,
							MQConstant.AUTO_BORROW_PREAUDIT_ADMIN_EXCEPTION_TAG, hjhPlanAsset.getAssetId(), params));
					logger.info(hjhPlanAsset.getAssetId() + " 资产 加入队列 ");
				} catch (MQException e) {
					logger.info(hjhPlanAsset.getAssetId() + " 资产 加入队列失败");
				}
            }

        }

        return borrowFlag;
    }
    /**
     * 跟标的找资产
     *
     * @return
     */
    private HjhPlanAsset selectAssetByBorrow(BorrowAndInfoVO borrow) {
        HjhPlanAssetExample example = new HjhPlanAssetExample();
        HjhPlanAssetExample.Criteria cra = example.createCriteria();
        cra.andInstCodeEqualTo(borrow.getInstCode());
        cra.andBorrowNidEqualTo(borrow.getBorrowNid());

        List<HjhPlanAsset> list = this.hjhPlanAssetMapper.selectByExample(example);
        if (list != null && !list.isEmpty()) {
            return list.get(0);
        }

        return null;

    }
}
