package com.hyjf.cs.trade.controller.web.home;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.google.common.collect.Lists;
import com.hyjf.am.vo.hgreportdata.cert.CertOldLendProductConfigVO;
import com.hyjf.am.vo.hgreportdata.cert.CertReportEntityVO;
import com.hyjf.am.vo.trade.cert.CertClaimUpdateVO;
import com.hyjf.am.vo.trade.cert.CertBorrowVO;
import com.hyjf.am.vo.trade.cert.CertClaimVO;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.util.GetDate;
import com.hyjf.cs.common.bean.result.WebResult;
import com.hyjf.cs.common.controller.BaseController;
import com.hyjf.cs.trade.mq.consumer.hgdatareport.cert.common.CertCallConstant;
import com.hyjf.cs.trade.mq.consumer.hgdatareport.cert.common.CertCallUtil;
import com.hyjf.cs.trade.service.consumer.hgdatareport.cert.lendProductConfig.CertLendProductConfigService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Web端首页controller
 * @author zhangyk
 * @date 2018/7/4 13:47
 */
@Api(value = "web端-首页测试",tags = "web端-首页测试")
@RestController
@RequestMapping("/hyjf-web/homeTest")
public class WebHomeTestController extends BaseController {



    private String thisMessName = "产品配置信息历史数据推送";
    private String logHeader = "【" + CustomConstants.HG_DATAREPORT + CustomConstants.UNDERLINE + CustomConstants.HG_DATAREPORT_CERT + " " + thisMessName + "】";

    @Autowired
    private CertLendProductConfigService certLendProductConfigService;

    /**
     * 获取首页数据接口
     * @return
     */
    @ApiOperation(value = "获取数据", notes = "获取数据")
    @PostMapping(value = "/homeData", produces = "application/json; charset=utf-8")
    public Object getHomeData(@RequestHeader(value = "userId" , required = false )String userId){
        // controller 不做业务处理
        WebResult result =  new WebResult();
        // --> 增加防重校验（根据不同平台不同上送方式校验不同）

        List<CertClaimVO> certBorrowEntityList = certLendProductConfigService.getCertBorrowNoConfig();
        if(CollectionUtils.isEmpty(certBorrowEntityList)){
            logger.error(logHeader + "暂无未上报的产品配置信息！！！");
            return null;
        }
        logger.info(logHeader + "查询的未上报的产品配置历史数据共: " + certBorrowEntityList.size() + "条");
        // --> 调用service组装数据
        JSONArray listRepay = certLendProductConfigService.getHistoryDate();
        int intCount = listRepay == null ? 0 : listRepay.size();
        logger.info(logHeader + "查询的产品配置历史数据共: " + intCount + "条" + ",当前时间为:" + GetDate.getNowTime10());
        if (null == listRepay || listRepay.size() <= 0) {
            logger.error(logHeader + "组装参数为空！！！");
            return null;
        }
        //转换为list
        List<JSONArray> jsonArrayList = new ArrayList<JSONArray>();
        List<CertOldLendProductConfigVO> certOldRepayPlanBeans = JSONArray.parseArray(listRepay.toJSONString(), CertOldLendProductConfigVO.class);
        if (null != certOldRepayPlanBeans) {
            //拆分数据,防止数据长多过长
            List<List<CertOldLendProductConfigVO>> parts = Lists.partition(certOldRepayPlanBeans, 3000);
            for (List<CertOldLendProductConfigVO> child : parts) {
                JSONArray jsonArray = JSONArray.parseArray(JSON.toJSONString(child));
                jsonArrayList.add(jsonArray);
            }
        }logger.info(logHeader + "产品配置历史数据拆分完毕,共" + jsonArrayList.size() + "条" + ",当前时间为:" + GetDate.getNowTime10());
        if (null != jsonArrayList && jsonArrayList.size() > 0) {
            for (int i = 0; i < jsonArrayList.size(); i++) {
                JSONArray repay = jsonArrayList.get(i);
                List<CertReportEntityVO> entitys = CertCallUtil.groupByDate(repay, thisMessName, CertCallConstant.CERT_INF_TYPE_REPAY_PLAN);
                // 遍历循环上报
                for (CertReportEntityVO entity : entitys) {
                    /*try {
                        certLendProductConfigService.insertAndSendPost(entity);
                    } catch (Exception e) {
                        throw e;
                    }*/
                    // 批量修改状态  start
                    List<Integer> ids = new ArrayList<>();
                    for (CertClaimVO item : certBorrowEntityList) {
                        ids.add(item.getId());
                    }
                    if (ids.size() > 0) {
                        CertClaimUpdateVO update = new CertClaimUpdateVO();
                        update.setIds(ids);
                        CertClaimVO certBorrow = new CertClaimVO();
                        if (entity != null && CertCallConstant.CERT_RETURN_STATUS_SUCCESS.equals(entity.getReportStatus())) {
                            // 成功
                            certBorrow.setIsConfig(1);
                        } else {
                            // 失败
                            certBorrow.setIsConfig(99);
                        }
                        update.setCertClaim(certBorrow);
                        // 批量修改
                        certLendProductConfigService.updateCertBorrowStatusBatch(update);
                    }
                    // 批量修改状态  end
                }
            }
        }




        return result;
    }


}
