/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service.impl.admin;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.resquest.admin.BorrowFireRequest;
import com.hyjf.am.resquest.admin.BorrowFirstRequest;
import com.hyjf.am.trade.dao.mapper.auto.BorrowBailMapper;
import com.hyjf.am.trade.dao.mapper.auto.BorrowConfigMapper;
import com.hyjf.am.trade.dao.mapper.auto.BorrowMapper;
import com.hyjf.am.trade.dao.mapper.customize.admin.BorrowFirstCustomizeMapper;
import com.hyjf.am.trade.dao.model.auto.*;
import com.hyjf.am.trade.dao.model.customize.admin.BorrowFirstCustomize;
import com.hyjf.am.trade.mq.BorrowFirstProducer;
import com.hyjf.am.trade.mq.Producer;
import com.hyjf.am.trade.service.admin.BorrowFirstService;
import com.hyjf.am.vo.trade.borrow.BorrowVO;
import com.hyjf.common.cache.CacheUtil;
import com.hyjf.common.cache.RedisUtils;
import com.hyjf.common.constants.MQConstant;
import com.hyjf.common.exception.MQException;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.util.GetDate;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

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
public class BorrowFirstServiceImpl implements BorrowFirstService {
    Logger logger = LoggerFactory.getLogger(BorrowFirstServiceImpl.class);

    @Autowired
    BorrowFirstCustomizeMapper borrowFirstCustomizeMapper;

    @Autowired
    BorrowMapper borrowMapper;

    @Autowired
    BorrowBailMapper borrowBailMapper;

    @Autowired
    BorrowConfigMapper borrowConfigMapper;

    @Autowired
    BorrowFirstProducer borrowFirstProducer;

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
            Map<String, String> map = CacheUtil.getParamNameMap("hyjf_param_name:VERIFY_STATUS");
            if (!CollectionUtils.isEmpty(map)) {
                for (BorrowFirstCustomize borrowFirstCustomize : list) {
                    borrowFirstCustomize.setVerifyStatusName(map.getOrDefault(borrowFirstCustomize.getVerifyStatus(), null));
                }
            }
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
            // 该借款编号没有交过保证金
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
                // 借款编号
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

    private String getBorrowConfig(String configCd) {
        BorrowConfig borrowConfig = borrowConfigMapper.selectByPrimaryKey(configCd);
        return borrowConfig.getConfigValue();
    }

    /**
     * 更新-发标
     *
     * @param borrowFireRequest
     */
    @Override
    public void updateOntimeRecord(BorrowFireRequest borrowFireRequest) {
        BorrowVO borrow = borrowFireRequest.getBorrowVO();
        if (borrow != null) {
            // 插入时间
            int systemNowDateLong = GetDate.getNowTime10();
            Date systemNowDate = GetDate.getDate(systemNowDateLong);
            // 剩余的金额
            borrow.setBorrowAccountWait(borrow.getAccount());
            int time = systemNowDateLong;
            // 当发标状态为立即发标时插入系统时间
            if (StringUtils.isNotBlank(borrowFireRequest.getVerifyStatus())) {
                // 发标方式为”暂不发标2“或者”定时发标 3“时，项目状态变为”待发布“
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
                // 发标方式为”立即发标 2“时，项目状态变为”投资中
                else if (Integer.valueOf(borrowFireRequest.getVerifyStatus()) == 4) {
                    // 是否可以进行借款
                    borrow.setBorrowStatus(1);
                    // 初审时间
                    borrow.setVerifyTime(String.valueOf(GetDate.getNowTime10()));
                    // 发标的状态
                    borrow.setVerifyStatus(Integer.valueOf(borrowFireRequest.getVerifyStatus()));
                    // 状态
                    borrow.setStatus(2);
                    // 借款到期时间
                    borrow.setBorrowEndTime(String.valueOf(time + borrow.getBorrowValidTime() * 86400));

                    // 根据此标的是否跑引擎操作redis ：0未使用 1使用
                    if (borrow.getIsEngineUsed() == 0) {
                        // borrowNid，借款的borrowNid,account借款总额
                        RedisUtils.set(borrow.getBorrowNid(), borrow.getAccount().toString());
                    }
                }
                // 更新时间
                borrow.setUpdatetime(systemNowDate);
                BorrowExample borrowExample = new BorrowExample();
                BorrowExample.Criteria borrowCra = borrowExample.createCriteria();
                borrowCra.andBorrowNidEqualTo(borrow.getBorrowNid());
                Borrow updateBorrow = new Borrow();
                BeanUtils.copyProperties(borrow, updateBorrow);
                this.borrowMapper.updateByExampleSelective(updateBorrow, borrowExample);
                // 删写redis的定时发标时间
                changeOntimeOfRedis(borrow);
            }
        }

    }

    private void changeOntimeOfRedis(BorrowVO borrow) {
        if (borrow.getVerifyStatus() == 3) {
            //定时发标 写定时发标时间 redis 有效期10天
            RedisUtils.set(borrow.getBorrowNid() + CustomConstants.UNDERLINE +
                    CustomConstants.REDIS_KEY_ONTIME, String.valueOf(borrow.getOntime()), 864000);
        } else {
            //非定时发标 删redis
            RedisUtils.del(borrow.getBorrowNid() + CustomConstants.UNDERLINE + CustomConstants.REDIS_KEY_ONTIME);
        }
    }

    /**
     * 加入计划
     *
     * @param borrowFireRequest
     */
    @Override
    public void sendToMQ(BorrowFireRequest borrowFireRequest) {
        BorrowVO borrow = borrowFireRequest.getBorrowVO();
        if (borrow != null) {
            try {
                JSONObject params = new JSONObject();
                params.put("borrowNid", borrow.getBorrowNid());
                borrowFirstProducer.messageSend(new Producer.MassageContent(MQConstant.HYJF_BORROW_ISSUE_TOPIC, UUID.randomUUID().toString(), JSONObject.toJSONBytes(params)));
            } catch (MQException e) {
                logger.error("发送【关联计划】MQ失败...");
            }
        }
    }
}
