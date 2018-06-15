/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.trade.handle;

import com.hyjf.am.vo.trade.hjh.HjhAssetBorrowTypeVO;
import com.hyjf.am.vo.trade.hjh.HjhPlanAssetVO;
import com.hyjf.common.cache.RedisUtils;
import com.hyjf.common.constants.MQConstant;
import com.hyjf.cs.trade.client.ApiAssetClient;
import com.hyjf.cs.trade.client.AutoSendClient;
import com.hyjf.cs.trade.service.AutoRecordService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Random;

/**
 * @author fuqiang
 * @version AutoRecordMessageHandle, v0.1 2018/6/14 10:02
 */
@Component
public class AutoRecordMessageHandle {

    private static final Logger _log = LoggerFactory.getLogger(AutoRecordMessageHandle.class);

    @Autowired
    private AutoRecordService autoRecordService;

    @Autowired
    private AutoSendClient autoSendClient;

    @Autowired
    private ApiAssetClient apiAssetClient;

    public void sendMessage(String assetId, String instCode) {
        // 资产自动备案
        _log.info(assetId+" 开始自动备案 "+ instCode);
        HjhPlanAssetVO hjhPlanAssetVO = this.autoSendClient.selectPlanAsset(assetId, instCode);
        if(hjhPlanAssetVO == null){
            _log.info(assetId+" 该资产在表里不存在！！");
            return;
        }

        // redis 放重复检查
        String redisKey = "borrowrecord:" + hjhPlanAssetVO.getInstCode()+hjhPlanAssetVO.getAssetId();
        boolean result = RedisUtils.tranactionSet(redisKey, 300);
        if(!result){
            _log.info(hjhPlanAssetVO.getInstCode()+" 正在备案(redis) "+hjhPlanAssetVO.getAssetId());
            return;
        }

        // 业务校验
        if(hjhPlanAssetVO.getStatus() != null && hjhPlanAssetVO.getStatus().intValue() != 3 &&
                hjhPlanAssetVO.getVerifyStatus() != null && hjhPlanAssetVO.getVerifyStatus().intValue() == 1){
            _log.info(assetId+" 该资产状态不是备案状态");
            return;
        }

        /** * 网站合规改造-自动备案添加还款授权校验 start 根据产品需求：只处理 资产来源为非汇盈金服的标的 校验 */
        /**因业务需求暂时注掉各种授权校验代码 */

/*			_log.info("开始校验借款人的还款授权：" + hjhPlanAssetVO.getUserId());
			// 替换参数(短信用)
			Map<String, String> replaceMap = new HashMap<String, String>();
			if(StringUtils.isNotEmpty(hjhPlanAssetVO.getBorrowNid())){
				//通过资产中的标的查询此标的是否有担保机构
				Borrow borrow = autoRecordService.getBorrowByBorrowNid(hjhPlanAssetVO.getBorrowNid());
				if(borrow != null){
					// (1.1)担保机构id可以为空,不为空时校验，为空不校验授权
					if(borrow.getRepayOrgUserId() != null){
						HjhUserAuth hjhUserAuth = autoRecordService.getHjhUserAuthByUserID(borrow.getRepayOrgUserId());
						if(hjhUserAuth == null){
							_log.info("该资产无担保机构或者未做任何授权" + mqHjhPlanAsset.getAssetId() );
						} else {
							// 还款授权状态 DB 默认为 0
							if (hjhUserAuth.getAutoRepayStatus() == null || hjhUserAuth.getAutoRepayStatus().toString().equals("0")) {
								_log.info("该资产的担保机构未做还款授权" + mqHjhPlanAsset.getAssetId() );
							}
							// 缴费授权状态 DB 默认为 0
							if (hjhUserAuth.getAutoPaymentStatus() == null || hjhUserAuth.getAutoPaymentStatus().toString().equals("0")) {
								_log.info("该资产的担保机构未做缴费授权" + mqHjhPlanAsset.getAssetId() );
							}
						}
					}
					// (1.2)受托人id可以为空,不为空时校验，为空不校验授权
					if(borrow.getEntrustedUserId() != null){
						HjhUserAuth hjhUserAuth1 = autoRecordService.getHjhUserAuthByUserID(borrow.getEntrustedUserId());
						if(hjhUserAuth1 == null){
							_log.info("该资产无收款人或者该收款人未做任何授权" + mqHjhPlanAsset.getAssetId() );
						} else {
							// 缴费授权状态 DB 默认为 0
							if (hjhUserAuth1.getAutoPaymentStatus() == null || hjhUserAuth1.getAutoPaymentStatus().toString().equals("0")) {
								_log.info("该资产的收款人未做缴费授权" + mqHjhPlanAsset.getAssetId() );
							}
						}
					}
					// (1.3)借款人id必须非空
					if(borrow.getUserId() != null){
						HjhUserAuth hjhUserAuth2 = autoRecordService.getHjhUserAuthByUserID(borrow.getUserId());
						if(hjhUserAuth2 == null){
							_log.info("该资产无借款人或者借款未做任何授权" + mqHjhPlanAsset.getAssetId() );
						} else {
							// 缴费授权状态 DB 默认为 0
							if (hjhUserAuth2.getAutoPaymentStatus() == null || hjhUserAuth2.getAutoPaymentStatus().toString().equals("0")) {
								_log.info("该资产的借款人未做缴费授权" + mqHjhPlanAsset.getAssetId() );
							}
							// 是否可用担保机构还款(0:否,1:是) DB默认为0
							if(borrow.getIsRepayOrgFlag() != null && borrow.getIsRepayOrgFlag() == 1){
								如果是担保机构还款，还款授权可以不做
							} else {
								// 还款授权状态 DB 默认为 0
								if (hjhUserAuth2.getAutoRepayStatus() == null || hjhUserAuth2.getAutoRepayStatus().toString().equals("0")) {
									_log.info("该资产的借款人无担保机构垫付并且未做还款授权" + mqHjhPlanAsset.getAssetId() );
								}
							}
						}
					} else {
						_log.info("该资产无借款人" + mqHjhPlanAsset.getAssetId() );
					}
				} else {
					_log.info("该资产在borrow表中不存在：" + hjhPlanAssetVO.getAssetId());
				}
			} else {
				_log.info("此资产无标的编号：" + hjhPlanAssetVO.getAssetId());
			}
			_log.info("结束校验授权：" + hjhPlanAssetVO.getAssetId());*/


        /** * 网站合规改造-自动备案添加还款授权校验 end */


        //判断该资产是否可以自动备案，是否关联计划
        HjhAssetBorrowTypeVO hjhAssetBorrowTypeVO = this.apiAssetClient.selectAssetBorrowType(hjhPlanAssetVO.getInstCode(), hjhPlanAssetVO.getAssetType());
        if(hjhAssetBorrowTypeVO == null || hjhAssetBorrowTypeVO.getAutoRecord() == null || hjhAssetBorrowTypeVO.getAutoRecord() != 1){
            _log.info(hjhPlanAssetVO.getAssetId()+" 该资产不能自动备案,原因自动备案未配置");
            return;
        }

        boolean flag = this.autoRecordService.updateRecordBorrow(hjhPlanAssetVO, hjhAssetBorrowTypeVO);
        if (!flag) {
            _log.error("自动备案失败！" + "[资产编号：" + hjhPlanAssetVO.getAssetId() + "]");
        }else{
            // 成功后到初审队列
            if(hjhPlanAssetVO.getEntrustedFlg() != null && hjhPlanAssetVO.getEntrustedFlg().intValue() ==1){
                _log.info(hjhPlanAssetVO.getAssetId()+"  未推送，等待授权");
            }else{
                this.autoRecordService.sendToMQ(hjhPlanAssetVO,  MQConstant.BORROW_PREAUDIT_GROUP);
                _log.info(hjhPlanAssetVO.getAssetId()+" 成功发送到初审队列");
            }

            // 备案成功后随机睡0.2到0.5秒
            try {
                Random random = new Random();
                int rand = (random.nextInt(4)+2)*100;
                Thread.sleep(rand);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }

        _log.info(hjhPlanAssetVO.getAssetId()+" 结束自动备案");
    }
}
