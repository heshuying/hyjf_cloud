package com.hyjf.am.trade.controller.front.hjh.issuerecover;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.trade.controller.BaseController;
import com.hyjf.am.trade.dao.model.auto.Borrow;
import com.hyjf.am.trade.dao.model.auto.HjhDebtCredit;
import com.hyjf.am.trade.dao.model.auto.HjhPlanAsset;
import com.hyjf.am.trade.mq.base.CommonProducer;
import com.hyjf.am.trade.mq.base.MessageContent;
import com.hyjf.am.trade.service.issuerecover.AutoIssueRecoverService;
import com.hyjf.am.vo.task.issuerecover.BorrowWithBLOBs;
import com.hyjf.common.constants.MQConstant;
import com.hyjf.common.exception.MQException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * 汇计划自动发标修复
 * @author walter.limeng
 * @version AutoIssueRecoverJob, v0.1 2018/7/11 10:30
 */
@RestController
@RequestMapping("/am-trade/hjhAutoIssueRecover")
@SuppressWarnings("unchecked")
public class AutoIssueRecoverController extends BaseController{

    @Autowired
    private AutoIssueRecoverService autoIssueRecoverService;
    @Autowired
    private CommonProducer commonProducer;

    /**
     *
     */
    @RequestMapping("/autoIssueRecover")
    public void AutoIssueRecover() {
        logger.info("------汇计划自动发标修复定时任务开始------");
        try {
            List statusList = new ArrayList();
            statusList.add(0);
            //statusList.add(1);
            // 查询待录标列表
            List<HjhPlanAsset> sendList = this.autoIssueRecoverService.selectAssetListByStatus(statusList);
            logger.info(" 待录标总数: "+sendList.size());
            for (HjhPlanAsset planAsset : sendList) {
                logger.debug(planAsset.getAssetId()+" 开始录标修复 ");
                try {
                    JSONObject params = new JSONObject();
                    params.put("assetId", planAsset.getAssetId());
                    params.put("instCode", planAsset.getInstCode());
					commonProducer.messageSend(new MessageContent(MQConstant.AUTO_ISSUE_RECOVER_TOPIC,
							MQConstant.AUTO_ISSUE_RECOVER_REPAIR_TAG, planAsset.getAssetId(), params));
                } catch (MQException e) {
                    logger.error("发送【录标修复】MQ失败...");
                }
                logger.debug(planAsset.getAssetId()+" 结束录标修复");
            }

            // 查询待备案列表
            statusList = new ArrayList();
            statusList.add(3);
            List<HjhPlanAsset> recordList = this.autoIssueRecoverService.selectAssetListByStatus(statusList);
            logger.info(" 待备案总数: "+recordList.size());

            for (HjhPlanAsset planAsset : recordList) {
                logger.debug(planAsset.getAssetId()+" 开始待备案修复 ");
                try {
                    JSONObject params = new JSONObject();
                    params.put("assetId", planAsset.getAssetId());
                    params.put("instCode", planAsset.getInstCode());
                    commonProducer.messageSend(new MessageContent(MQConstant.AUTO_BORROW_RECORD_TOPIC,
                            MQConstant.AUTO_BORROW_RECORD_REPAIR_TAG, UUID.randomUUID().toString(), params));
                } catch (MQException e) {
                    logger.error("发送【待备案修复】MQ失败...");
                }
                logger.debug(planAsset.getAssetId()+" 结束待备案修复");
            }

            // 查询待初审列表
            statusList = new ArrayList();
            statusList.add(5);
            List<HjhPlanAsset> preauditList = this.autoIssueRecoverService.selectAssetListByStatus(statusList);
            logger.info(" 待初审总数: "+preauditList.size());

            for (HjhPlanAsset planAsset : preauditList) {
                logger.debug(planAsset.getAssetId()+" 开始待初审修复 ");
				try {
					JSONObject params = new JSONObject();
					params.put("assetId", planAsset.getAssetId());
					params.put("instCode", planAsset.getInstCode());
					commonProducer.messageSend(new MessageContent(MQConstant.AUTO_BORROW_PREAUDIT_TOPIC,
							MQConstant.AUTO_BORROW_PREAUDIT_ASSET_REPAIR_TAG, planAsset.getAssetId(), params));
				} catch (MQException e) {
					logger.error("发送【待初审修复】MQ失败...");
				}
                logger.debug(planAsset.getAssetId()+" 结束待初审修复");
            }

            // =================================
            // 查询待关联计划资产列表
            List<HjhPlanAsset> issueList = this.autoIssueRecoverService.selectBorrowAssetList();
            logger.info("原始标待关联计划总数: "+issueList.size());

            for (HjhPlanAsset planAsset : issueList) {
                logger.debug(planAsset.getAssetId()+" 开始待关联计划资产修复 ");

                try {
                    JSONObject params = new JSONObject();
                    params.put("borrowNid", planAsset.getBorrowNid());
                    //modify by yangchangwei 防止队列触发太快，导致无法获得本事务变泵的数据，延时级别为2 延时5秒
					commonProducer.messageSendDelay(new MessageContent(MQConstant.AUTO_ASSOCIATE_PLAN_TOPIC,
							MQConstant.AUTO_ASSOCIATE_PLAN_BORROW_REPAIR_TAG, planAsset.getBorrowNid(), params), 2);
                } catch (MQException e) {
                    logger.error("发送【关联计划资产修复】MQ失败...");
                }
                logger.debug(planAsset.getAssetId()+" 结束待关联计划资产修复");
            }

            // 查询债转待关联计划总数
            List<HjhDebtCredit> creditissueList = this.autoIssueRecoverService.selectCreditAssetList();
            logger.info("债转待关联计划总数: "+creditissueList.size());

            for (HjhDebtCredit credit : creditissueList) {
                logger.debug(credit.getCreditNid()+" 开始债转待关联计划资产修复 ");

                try {
                    JSONObject params = new JSONObject();
                    params.put("creditNid", credit.getCreditNid());
                    //modify by yangchangwei 防止队列触发太快，导致无法获得本事务变泵的数据，延时级别为2 延时5秒
					commonProducer.messageSendDelay(new MessageContent(MQConstant.AUTO_ASSOCIATE_PLAN_TOPIC,
							MQConstant.AUTO_ASSOCIATE_PLAN_CREDIT_REPAIR_TAG, credit.getCreditNid(), params), 2);
                } catch (MQException e) {
                    logger.error("发送【债转待关联计划资产修复】MQ失败...");
                }

                logger.debug(credit.getCreditNid()+" 结束债转待关联计划资产修复");
            }

            // =================================================
            // 查询散标关联计划资产列表
            List<Borrow> borrowList = this.autoIssueRecoverService.selectBorrowList();
            logger.info("散标待关联计划总数: "+borrowList.size());

            for (Borrow borrow : borrowList) {
                logger.debug(borrow.getBorrowNid()+" 开始散标待关联计划资产修复 ");

                try {
                    JSONObject params = new JSONObject();
                    params.put("borrowNid", borrow.getBorrowNid());
                    //modify by yangchangwei 防止队列触发太快，导致无法获得本事务变泵的数据，延时级别为2 延时5秒
					commonProducer.messageSendDelay(new MessageContent(MQConstant.AUTO_ASSOCIATE_PLAN_TOPIC,
							MQConstant.AUTO_ASSOCIATE_PLAN_BORROW_REPAIR_TAG, borrow.getBorrowNid(), params), 2);
                } catch (MQException e) {
                    logger.error("发送【债转待关联计划资产修复】MQ失败...");
                }
                logger.debug(borrow.getBorrowNid()+" 结束散标待关联计划资产修复");
            }

            // =====================汇盈手动录标-各状态的标的修复 add by liushouyi HJH3============================
            List<BorrowWithBLOBs> borrowWithBLOBsList = this.autoIssueRecoverService.selectAutoBorrowNidList();
            logger.info(" 手动录标的自动备案、初审的标的总数: "+borrowWithBLOBsList.size());

            for (BorrowWithBLOBs borrow : borrowWithBLOBsList) {
                logger.debug(borrow.getBorrowNid()+" 开始自动备案、初审标的自动审核 ");

                // 校验标的状态位
                if (null == borrow.getStatus()) {
                    logger.error(borrow.getBorrowNid()+" 该标的的状态值为空 ");
                    continue;
                }
                // 自动备案的标的
                if (borrow.getStatus() == 0) {
                    logger.debug(borrow.getBorrowNid()+" 发送自动备案消息到MQ ");
                    try {
                        JSONObject params = new JSONObject();
                        params.put("borrowNid", borrow.getBorrowNid());
						commonProducer.messageSend(new MessageContent(MQConstant.AUTO_BORROW_RECORD_TOPIC,
								MQConstant.AUTO_BORROW_RECORD_ADMIN_TAG, UUID.randomUUID().toString(), params));
                    } catch (MQException e) {
                        logger.error("发送【自动备案消息到MQ】MQ失败...");
                    }
                }

                // 自动初审的标的校验发标状态位
                if (borrow.getStatus() == 1 && null == borrow.getVerifyStatus()) {
                    logger.error(borrow.getBorrowNid()+" 该标的的发标状态值为空 ");
                    continue;
                }
                // 自动审核保证金的标的
                if (borrow.getStatus() == 1 && borrow.getVerifyStatus() == 0) {
                    logger.debug(borrow.getBorrowNid()+" 发送自动审核保证金消息到MQ ");
                    try {
                        JSONObject params = new JSONObject();
                        params.put("borrowNid", borrow.getBorrowNid());
						commonProducer.messageSend(new MessageContent(MQConstant.AUTO_VERIFY_BAIL_TOPIC,
								MQConstant.AUTO_VERIFY_BAIL_REPAIR_TAG, borrow.getBorrowNid(), params));
                    } catch (MQException e) {
                        logger.error("发送【自动审核保证金消息到MQ】MQ失败...");
                    }
                }
                // 已审核保证金的标的发送到初审发标队列
                if (borrow.getStatus() == 1 && borrow.getVerifyStatus() > 0) {
                    logger.debug(borrow.getBorrowNid()+" 发送自动初审消息到MQ ");
					try {
						JSONObject params = new JSONObject();
						params.put("borrowNid", borrow.getBorrowNid());
						commonProducer.messageSend(new MessageContent(MQConstant.AUTO_BORROW_PREAUDIT_TOPIC,
								MQConstant.AUTO_BORROW_PREAUDIT_BORROW_REPAIR_TAG, UUID.randomUUID().toString(),
								params));
					} catch (MQException e) {
						logger.error("发送【自动初审消息到MQ】MQ失败...");
					}
                }
                logger.debug(borrow.getBorrowNid()+" 结束自动备案、初审标的自动审核");
            }

            logger.debug("------汇计划自动发标修复定时任务结束------");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
