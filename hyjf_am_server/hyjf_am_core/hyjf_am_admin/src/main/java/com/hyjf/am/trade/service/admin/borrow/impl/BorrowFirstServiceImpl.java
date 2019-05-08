/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service.admin.borrow.impl;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.admin.mq.base.CommonProducer;
import com.hyjf.am.admin.mq.base.MessageContent;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.admin.BorrowBailInfoResponse;
import com.hyjf.am.resquest.admin.BorrowFireRequest;
import com.hyjf.am.resquest.admin.BorrowFirstRequest;
import com.hyjf.am.trade.dao.model.auto.*;
import com.hyjf.am.trade.dao.model.customize.BorrowFirstCustomize;
import com.hyjf.am.trade.service.admin.borrow.BorrowFirstService;
import com.hyjf.am.trade.service.impl.BaseServiceImpl;
import com.hyjf.am.vo.admin.BorrowBailInfoResponseBeanVO;
import com.hyjf.common.cache.CacheUtil;
import com.hyjf.common.cache.RedisConstants;
import com.hyjf.common.cache.RedisUtils;
import com.hyjf.common.constants.MQConstant;
import com.hyjf.common.exception.MQException;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.util.GetDate;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @author wangjun
 * @version BorrowFirstServiceImpl, v0.1 2018/7/3 15:59
 */
@Service
public class BorrowFirstServiceImpl extends BaseServiceImpl implements BorrowFirstService {

    @Resource
    private CommonProducer commonProducer;

    /**
     * 借款初审总条数
     *
     * @param borrowFirstRequest
     * @return
     */
    @Override
    public Integer countBorrowFirst(BorrowFirstRequest borrowFirstRequest) {
        return borrowFirstCustomizeMapper.countBorrowFirst(borrowFirstRequest);
    }

    /**
     * 借款初审列表
     *
     * @param borrowFirstRequest
     * @return
     */
    @Override
    public List<BorrowFirstCustomize> selectBorrowFirstList(BorrowFirstRequest borrowFirstRequest) {
        List<BorrowFirstCustomize> list = borrowFirstCustomizeMapper.selectBorrowFirstList(borrowFirstRequest);
        if (!CollectionUtils.isEmpty(list)) {
            Map<String, String> map = CacheUtil.getParamNameMap("VERIFY_STATUS");
            list.forEach((borrowFirstCustomize) -> borrowFirstCustomize.setVerifyStatusName(map.getOrDefault(borrowFirstCustomize.getVerifyStatus(), null)));
        }
        return list;
    }

    /**
     * 统计页面值总和
     *
     * @param borrowFirstRequest
     * @return
     */
    @Override
    public String getSumBorrowFirstAccount(BorrowFirstRequest borrowFirstRequest) {
        return borrowFirstCustomizeMapper.sumBorrowFirstAccount(borrowFirstRequest);
    }

    /**
     * 交保证金
     *
     * @param borrowNid
     * @return
     */
    @Override
    public boolean insertBorrowBail(String borrowNid, String currUserId) {
        BorrowExample example = new BorrowExample();
        BorrowExample.Criteria cra = example.createCriteria();
        cra.andBorrowNidEqualTo(borrowNid);
        List<Borrow> borrowList = this.borrowMapper.selectByExample(example);
        if (borrowList != null && borrowList.size() == 1) {
            Borrow borrow = borrowList.get(0);
            // 该项目编号没有交过保证金
            BorrowBailExample exampleBail = new BorrowBailExample();
            BorrowBailExample.Criteria craBail = exampleBail.createCriteria();
            craBail.andBorrowNidEqualTo(borrow.getBorrowNid());
            List<BorrowBail> borrowBailList = this.borrowBailMapper.selectByExample(exampleBail);
            if (borrowBailList == null || borrowBailList.size() == 0) {
                BorrowBail borrowBail = new BorrowBail();
                // 借款人的ID
                borrowBail.setBorrowUid(borrow.getUserId());
                // 操作人的ID
                if (StringUtils.isNotBlank(currUserId)) {
                    borrowBail.setOperaterUid(Integer.valueOf(currUserId));
                }
                // 项目编号
                borrowBail.setBorrowNid(borrow.getBorrowNid());
                // 保证金数值  计算公式：保证金金额=借款金额×3％
                BigDecimal bailPercent = new BigDecimal(this.getBorrowConfig(CustomConstants.BORROW_BAIL_RATE));
                BigDecimal accountBail = (borrow.getAccount()).multiply(bailPercent).setScale(2, BigDecimal.ROUND_DOWN);
                borrowBail.setBailNum(accountBail);
                // 10位系统时间（到秒）
                borrowBail.setUpdateTime(GetDate.getNowTime());
                boolean bailFlag = this.borrowBailMapper.insertSelective(borrowBail) > 0 ? true : false;
                if (bailFlag) {
                    borrow.setVerifyStatus(1);
                    boolean borrowFlag = this.borrowMapper.updateByPrimaryKey(borrow) > 0 ? true : false;
                    if (borrowFlag) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * 更新-发标
     *
     * @param borrowFireRequest
     */
    @Override
    public Response updateOntimeRecord(BorrowFireRequest borrowFireRequest) {
        String borrowNid = borrowFireRequest.getBorrowNid();
        Borrow borrow = getBorrow(borrowNid);
        BorrowInfo borrowInfo = getBorrowInfoByNid(borrowNid);
        if (borrow != null && borrowInfo != null) {
            // 插入时间
            int systemNowDateLong = GetDate.getNowTime10();
            Date systemNowDate = GetDate.getDate(systemNowDateLong);
            // 剩余的金额
            borrow.setBorrowAccountWait(borrow.getAccount());
            // 当发标状态为立即发标时插入系统时间
            if (StringUtils.isNotBlank(borrowFireRequest.getVerifyStatus())) {
                // 发标方式为"暂不发标 2"或者"定时发标 3"时，项目状态变为 待发布
                if (Integer.valueOf(borrowFireRequest.getVerifyStatus()) == 2 || Integer.valueOf(borrowFireRequest.getVerifyStatus()) == 3) {
                    // 定时发标
                    if (Integer.valueOf(borrowFireRequest.getVerifyStatus()) == 3) {
                        // 发标时间
                        borrow.setOntime(GetDate.strYYYYMMDDHHMMSS2Timestamp(borrowFireRequest.getOntime()));
                    } else if (Integer.valueOf(borrowFireRequest.getVerifyStatus()) == 2) {
                        // 发标时间
                        borrow.setOntime(0);
                    }
                    // 状态
                    borrow.setStatus(1);
                    // 初审状态
                    borrow.setVerifyStatus(Integer.valueOf(borrowFireRequest.getVerifyStatus()));
                }
                // 发标方式为"立即发标 4"时，项目状态变为 出借中
                else if (Integer.valueOf(borrowFireRequest.getVerifyStatus()) == 4) {
                    // 是否可以进行借款
                    borrow.setBorrowStatus(1);
                    // 初审时间
                    borrow.setVerifyTime(GetDate.getNowTime10());
                    // 发标的状态
                    borrow.setVerifyStatus(Integer.valueOf(borrowFireRequest.getVerifyStatus()));
                    // 状态
                    borrow.setStatus(2);
                    // 借款到期时间
                    borrow.setBorrowEndTime(String.valueOf(systemNowDateLong + borrow.getBorrowValidTime() * 86400));

                    // 根据此标的是否跑引擎操作redis ：0未使用 1使用
                    if (borrow.getIsEngineUsed() == 0) {
                        // borrowNid，借款的borrowNid,account借款总额
                        RedisUtils.set(RedisConstants.BORROW_NID + borrow.getBorrowNid(), borrow.getAccount().toString());
                    }

                    if (!CustomConstants.INST_CODE_HYJF.equals(borrowInfo.getInstCode())) {
                        // 三方资产更新资产表状态
                        HjhPlanAsset hjhPlanAssetnew = this.selectHjhPlanAssetByBorrowNid(borrowNid);
                        if(hjhPlanAssetnew != null){
                            //7 出借中
                            hjhPlanAssetnew.setStatus(7);
                            //获取当前时间
                            hjhPlanAssetnew.setUpdateTime(GetDate.getNowTime());
                            hjhPlanAssetnew.setUpdateUserId(1);
                            int planAssetUpdCount = hjhPlanAssetMapper.updateByPrimaryKeySelective(hjhPlanAssetnew);
                            if(planAssetUpdCount == 0){
                                return new Response(Response.FAIL, "更新第三方资产失败");
                            }
                        } else {
                            return new Response(Response.FAIL, "获取第三方资产失败");
                        }
                    }
                }
                // 更新时间
                borrow.setUpdatetime(systemNowDate);
                BorrowExample borrowExample = new BorrowExample();
                BorrowExample.Criteria borrowCra = borrowExample.createCriteria();
                borrowCra.andBorrowNidEqualTo(borrow.getBorrowNid());
                int updateCount = this.borrowMapper.updateByExampleSelective(borrow, borrowExample);
                if (updateCount > 0) {
                    // 更新redis的定时发标时间
                    changeOntimeOfRedis(borrow);
                    // add by liuyang 20190415 wbs标的信息推送 start
                    try {
                        Borrow nowBorrow = this.getBorrow(borrow.getBorrowNid());
                        // 判断标的当前状态是否是投资中的状态
                        if (nowBorrow != null && nowBorrow.getStatus() == 2) {
                            logger.info("WBS系统标的信息推送MQ:标的号:[" + borrow.getBorrowNid() + "].");
                            sendWbsBorrowInfo(borrow.getBorrowNid(), "2", 0);
                        }
                    } catch (Exception e) {
                        logger.error("WBS系统标的信息推送MQ失败,[" + e + "].");
                    }
                    // add by liuyang 20190415 wbs标的信息推送 end

                    return new Response();
                } else {
                    return new Response(Response.FAIL, "标的信息更新失败");
                }
            }
        }
        return new Response(Response.FAIL,"未查询到标的信息");
    }

    /**
     * wbs标的信息推送MQ
     *
     * @param borrowNid
     * @param productStatus
     * @param productType
     */
    private void sendWbsBorrowInfo(String borrowNid, String productStatus, Integer productType) throws MQException {
        JSONObject params = new JSONObject();
        // 产品编号
        params.put("productNo", borrowNid);
        // 产品状态
        params.put("productStatus", productStatus);
        // 产品类型 0 散标类, 1 计划类
        params.put("productType", productType);
        commonProducer.messageSend(new MessageContent(MQConstant.WBS_BORROW_INFO_TOPIC, MQConstant.WBS_BORROW_INFO_TAG, UUID.randomUUID().toString(), params));
    }

    /**
     * 根据流程配置判断是否发送mq到自动初审
     *
     * @param borrowNid
     */
    @Override
    public void sendToMQAutoPreAudit(String borrowNid) {
        BorrowInfo borrowInfo = this.getBorrowInfoByNid(borrowNid);
        if (null == borrowInfo) {
            logger.error("未能获取到借款详情、无法发送MQ自动初审！项目编号：" + borrowNid);
            return;
        }
        HjhAssetBorrowtype hjhAssetBorrowType = this.selectAssetBorrowType(borrowInfo.getInstCode(), borrowInfo.getAssetType());
        if (null == hjhAssetBorrowType || null == hjhAssetBorrowType.getAutoBail()) {
            logger.error("未能获取到流程配置、无法发送MQ自动初审！项目编号：" + borrowNid);
            return;
        }
        // 判断是否设定自动初审
        if (hjhAssetBorrowType.getAutoAudit() == 1) {
            try {
                JSONObject params = new JSONObject();
                params.put("borrowNid", borrowNid);
				commonProducer.messageSend(new MessageContent(MQConstant.AUTO_BORROW_PREAUDIT_TOPIC,
						MQConstant.AUTO_BORROW_PREAUDIT_ADMIN_BAIL_TAG, borrowNid, params));
				logger.info("自动初审MQ发送成功----------标的号:{}", borrowNid);
            } catch (Exception e) {
                logger.error("发送MQ到初审失败，borrowNid：" + borrowNid);
                logger.error(e.getMessage());
            }
        }
    }

    /**
     * 修改定时发标redis
     * @param borrow
     */
    private void changeOntimeOfRedis(Borrow borrow) {
        if (borrow.getVerifyStatus() == 3) {
            //定时发标 写定时发标时间 redis 有效期10天
            RedisUtils.set(RedisConstants.ONTIME + borrow.getBorrowNid(), String.valueOf(borrow.getOntime()), 864000);
        } else {
            //非定时发标 删redis
            RedisUtils.del(RedisConstants.ONTIME + borrow.getBorrowNid());
        }
    }

    /**
     * 优化获取保证金信息
     * @param borrowNid
     * @return
     */
    @Override
    public BorrowBailInfoResponse getBailInfo(String borrowNid) {
        BorrowBailInfoResponse response = new BorrowBailInfoResponse();
        Borrow borrow = getBorrow(borrowNid);
        BorrowInfo borrowInfo = getBorrowInfoByNid(borrowNid);
        if (borrow == null || borrowInfo == null) {
            response.setRtn(Response.FAIL);
            response.setMessage("未查询到标的信息！");
        } else {
            BorrowBailInfoResponseBeanVO borrowBailInfoResponseBean = new BorrowBailInfoResponseBeanVO();
            BeanUtils.copyProperties(borrow, borrowBailInfoResponseBean);
            borrowBailInfoResponseBean.setName(borrowInfo.getName());
            borrowBailInfoResponseBean.setUserName(borrow.getBorrowUserName());

            // 计算公式：保证金金额=借款金额×3％
            BigDecimal bailPercent = new BigDecimal(getBorrowConfig(CustomConstants.BORROW_BAIL_RATE));
            double accountBail = (borrow.getAccount().multiply(bailPercent)).setScale(2, BigDecimal.ROUND_DOWN).doubleValue();
            borrowBailInfoResponseBean.setAccountBail(accountBail);
            response.setResult(borrowBailInfoResponseBean);
        }
        return response;
    }
}
