package com.hyjf.cs.trade.controller.batch.userportrait.screen;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.vo.screen.ScreenTransferVO;
import com.hyjf.common.constants.MQConstant;
import com.hyjf.common.exception.MQException;
import com.hyjf.cs.trade.mq.base.CommonProducer;
import com.hyjf.cs.trade.mq.base.MessageContent;
import com.hyjf.cs.trade.service.userlargescreen.UserLargeScreenService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @Author walter.limeng
 * @Description //投屏处理用户划转
 * @Date 10:50 2019-05-29
 **/
@RestController
@ApiIgnore
@RequestMapping("/cs-trade/screen")
public class ScreenDataTransferController {

    private static final Logger logger = LoggerFactory.getLogger(ScreenDataTransferController.class);
    private static int step = 100;

    @Resource
    private UserLargeScreenService userLargeScreenService;

    /**
     * 用户画像-运营部投屏处理用户划转
     */
    @GetMapping(value="/transfer", produces="application/json; charset=utf-8")
    public void operationScreenBatch() {
        try {
            logger.info("用户画像-运营部投屏处理用户划转 ==========>>> [Start]");
            int flag = 1;

            /**
             * 用户划转规则
             * 1。首先查询出所有采集到的用户，ht_user_operate_list，ht_repayment_plan
             * 2。查询这些用户的用户信息，用户ID，一级部门，三级部门，渠道，客服拥有人（用户画像中拥有人)，坐席分组（ht_customer_task_config）
             * 3。判断用户的一级部门，分三种情况，一级部门为空，一级部门为惠众商务，一级部门为其他的部门
             * 4。一级部门为空，则判断渠道是否为千乐数据，千乐渠道（349），如为千乐数据，则删除数据，如不是，则判断用户拥有人的分组，对应新客组，老客组，其他，为空则为其他。
             * 5。一级部门为惠众商务，则判断三级部门，如三级部门为电销部或者网络运营部，则为惠众数据，否则删除数据
             * 6。一级部门为其他部门，则为线下数据，直接删除
             * 坐席分组 0:其他,1:新客组,2:老客组,3:惠众
             **/
            screenTransfer(flag);
            logger.info("用户画像-运营部投屏处理用户划转 ==========>>> [End]");
        } catch (Exception e) {
            logger.error("用户画像-运营部投屏处理用户划转异常,异常详情如下:{}", e);
        }
    }

    /**
     * @Author walter.limeng
     * @Description //执行划转
     * @Date 15:01 2019-05-29
     * @Param [flag]
     * @return void
     **/
    public void screenTransfer(int flag){
        if(flag > 0){
            int start = 100 * (flag - 1);
            List<ScreenTransferVO> transferDataList = userLargeScreenService.getAllUser(start,step);
            logger.info("执行用户画像投屏数据划转，本次划转数据：{}",transferDataList);
            if(null != transferDataList && 0 < transferDataList.size()){
                List<ScreenTransferVO> updateList = new ArrayList<>();
                List<ScreenTransferVO> deleteList = new ArrayList<>();
                for (ScreenTransferVO screenTransferVO:transferDataList) {

                    //处理一级部门为空
                    if(null == screenTransferVO.getGrdfatherName()){
                        //判断用户是否为千乐数据
                        String sourceId = screenTransferVO.getSourceId();
                        if(StringUtils.isNotBlank(sourceId) && "349".equals(sourceId)){
                            //此为千乐数据，删除
                            deleteList.add(new ScreenTransferVO(screenTransferVO.getUserId(),null,null));
                        }else{
                            String owner = screenTransferVO.getCurrentOwner();
                            if(StringUtils.isNotBlank(owner)){
                                //数据为其他，设置数据拥有人为null，坐席分组为 0
                                ScreenTransferVO screenTransfer = new ScreenTransferVO(screenTransferVO.getUserId(),screenTransferVO.getCurrentOwner(),screenTransferVO.getGroups());
                                updateList.add(screenTransfer);
                            }else{
                                //数据为其他，设置数据拥有人为null，坐席分组为 0
                                ScreenTransferVO screenTransfer = new ScreenTransferVO(screenTransferVO.getUserId(),null,0);
                                updateList.add(screenTransfer);
                            }
                        }
                    }else if("惠众商务".equals(screenTransferVO.getGrdfatherName())){
                        //处理一级部门为惠众商务
                        String deptName = screenTransferVO.getDeptName();
                        if("电销部".equals(deptName) || "网络运营部".equals(deptName)){
                            //数据为其他，设置数据拥有人为null，坐席分组为 0
                            ScreenTransferVO screenTransfer = new ScreenTransferVO(screenTransferVO.getUserId(),null,3);
                            updateList.add(screenTransfer);
                        }else{
                            //此数据，删除
                            deleteList.add(new ScreenTransferVO(screenTransferVO.getUserId(),null,null));
                        }

                    }else{
                        //处理一级部门为其他部门
                        //此为线下数据，删除
                        deleteList.add(new ScreenTransferVO(screenTransferVO.getUserId(),null,null));
                    }
                }
                if(0 < updateList.size()){
                    //对ht_user_operate_list表执行更新操作
                    userLargeScreenService.updateOperatieList(updateList);
                    //对ht_repayment_plan表执行更新操作
                    userLargeScreenService.updateRepaymentPlan(updateList);
                }

                logger.info("对采集表执行内部划转的修改数据：{}",updateList);
                if(0 < deleteList.size()){
                    //对ht_user_operate_list表执行删除操作
                    userLargeScreenService.deleteOperatieList(deleteList);
                    //对ht_repayment_plan表执行更新操作
                    userLargeScreenService.deleteRepaymentPlan(deleteList);
                }

                logger.info("对采集表执行内划外部划转的删除数据：{}",deleteList);

                //采用递归调用方式，每次处理100条数据
                screenTransfer(flag + 1);
            }else{
                logger.info("投屏划转数据全部处理完成！");
            }
        }

    }
}
