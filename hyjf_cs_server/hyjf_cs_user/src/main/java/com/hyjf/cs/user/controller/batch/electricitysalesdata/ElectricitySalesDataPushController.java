/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.user.controller.batch.electricitysalesdata;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.bean.electricitysalesdatapush.ElectricitySalesDataPushBean;
import com.hyjf.am.vo.config.ElectricitySalesDataPushListVO;
import com.hyjf.common.http.HttpClientUtils;
import com.hyjf.common.util.GetDate;
import com.hyjf.cs.user.config.SystemConfig;
import com.hyjf.cs.user.controller.BaseUserController;
import com.hyjf.cs.user.service.batch.ElectricitySalesDataPushService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 电销数据推送Controlle
 *
 * @author liuyang
 * @version ElectricitySalesDataPushController, v0.1 2019/6/3 13:50
 */
@ApiIgnore
@RestController
@RequestMapping("/cs-user/batch/electricitysalesdatapush")
public class ElectricitySalesDataPushController extends BaseUserController {

    @Autowired
    private SystemConfig systemConfig;

    @Autowired
    private ElectricitySalesDataPushService electricitySalesDataPushService;

    @RequestMapping("/electricitySalesDataPush")
    public void electricitySalesDataPush() {
        List<ElectricitySalesDataPushBean> electricitySalesDataPushList = new ArrayList<>();
        // 获取需要推送的数据列表
        List<ElectricitySalesDataPushListVO> list = this.electricitySalesDataPushService.selectElectricitySalesDataPushDataList();
        if (list != null && list.size() > 0) {
            // 循环数据列表组装数
            for (ElectricitySalesDataPushListVO electricitySalesDataPushListVO : list) {
                ElectricitySalesDataPushBean bean = new ElectricitySalesDataPushBean();
                // 客户唯一字段
                bean.setCustomerFlag(electricitySalesDataPushListVO.getUserId());
                // 姓名
                bean.setTrueName(StringUtils.isBlank(electricitySalesDataPushListVO.getTrueName()) ? "" : electricitySalesDataPushListVO.getTrueName());
                // 手机号
                bean.setMobile(StringUtils.isBlank(electricitySalesDataPushListVO.getMobile()) ? null : electricitySalesDataPushListVO.getMobile());
                // 性别:0未知,1男,2女
                if (electricitySalesDataPushListVO.getSex() == 1) {
                    bean.setSex("男");
                } else if (electricitySalesDataPushListVO.getSex() == 2) {
                    bean.setSex("女");
                } else {
                    bean.setSex(null);
                }
                // 年龄
                bean.setAge(electricitySalesDataPushListVO.getAge() == 0 ? null : electricitySalesDataPushListVO.getAge());
                // PC渠道来源
                bean.setPcSourceName(StringUtils.isBlank(electricitySalesDataPushListVO.getPcSourceName()) ? null : electricitySalesDataPushListVO.getPcSourceName());
                // APP渠道来源
                bean.setAppSourceName(StringUtils.isBlank(electricitySalesDataPushListVO.getAppSourceName()) ? null : electricitySalesDataPushListVO.getAppSourceName());
                // 注册时间
                bean.setRegTime(electricitySalesDataPushListVO.getRegTime() == null ? null : GetDate.formatDate(electricitySalesDataPushListVO.getRegTime(), GetDate.datetimeFormat_key));
                // 出生年月日
                bean.setBirthday(StringUtils.isBlank(electricitySalesDataPushListVO.getBirthday()) ? null : electricitySalesDataPushListVO.getBirthday());
                bean.setUserName(StringUtils.isBlank(electricitySalesDataPushListVO.getUserName()) ? null : electricitySalesDataPushListVO.getUserName());
                bean.setRechargeMoney(electricitySalesDataPushListVO.getRechargeMoney());
                bean.setRechargeTime(electricitySalesDataPushListVO.getRechargeTime() == null ? null : GetDate.formatDate(electricitySalesDataPushListVO.getRechargeTime(), GetDate.datetimeFormat_key));
                bean.setChannel(electricitySalesDataPushListVO.getChannel() == 0 ? "非渠道" : "渠道");
                bean.setOwnerUserName(StringUtils.isBlank(electricitySalesDataPushListVO.getOwnerUserName()) ? null : electricitySalesDataPushListVO.getOwnerUserName());
                electricitySalesDataPushList.add(bean);
            }
        }
        if (electricitySalesDataPushList != null && electricitySalesDataPushList.size() > 0) {
            // 获取accessToken
            String url = systemConfig.getCallCentreUrl() + "accessToken?account=" + systemConfig.getAccount() + "&appid=" + systemConfig.getAppId() + "&secret=" + systemConfig.getSecret();
            logger.info("获取accessToken请求URL:[" + url + "].");
            String resultStr = HttpClientUtils.get(url);
            JSONObject resultJson = JSONObject.parseObject(resultStr);
            if (resultJson.getBoolean("success")) {
                String accessToken = resultJson.getString("accessToken");
                if (StringUtils.isBlank(accessToken)) {
                    logger.error("获取accessToken失败");
                    return;
                }
                Map<String, Object> customerMap = new HashMap<String, Object>();
                customerMap.put("customerList", electricitySalesDataPushList);
                // 上送数据
                String customerListData = JSON.toJSONString(customerMap);
                if (StringUtils.isNotBlank(customerListData)) {
                    String postDataUrl = systemConfig.getCallCentreUrl() + "commonInte";
                    // postJsonS
                    Map<String, String> postParam = new HashMap<String, String>();
                    postParam.put("json", customerListData);
                    postParam.put("flag", "1007");
                    postParam.put("account", systemConfig.getAccount());
                    postParam.put("accessToken", accessToken);
                    String dataPushResultStr = HttpClientUtils.postJson(postDataUrl, JSON.toJSONString(postParam));
                    logger.info(dataPushResultStr);
                    // 上传数据后,更新电销数据表
                    List<ElectricitySalesDataPushListVO> updateList = new ArrayList<ElectricitySalesDataPushListVO>();
                    JSONObject dataPushResult = JSONObject.parseObject(dataPushResultStr);
                    if (dataPushResult.getBoolean("success")) {
                        // 如果是上传成功改的话,更新状态
                        for (ElectricitySalesDataPushListVO electricitySalesDataPushListVO : list) {
                            electricitySalesDataPushListVO.setStatus(1);
                            updateList.add(electricitySalesDataPushListVO);
                        }
                    } else {
                        // 上传失败的话
                        List<String> errorList = (List<String>) dataPushResult.get("errorMessages");
                        for (int i = 0; i < errorList.size(); i++) {
                            // 错误下标
                            String errorMessage = errorList.get(i);
                            ElectricitySalesDataPushListVO errorData = list.get(i);
                            if (StringUtils.isBlank(errorMessage)) {
                                // 错误信息为空,入库成功
                                errorData.setStatus(1);
                            } else {
                                errorData.setStatus(2);
                                errorData.setRemark(errorMessage);
                            }
                            updateList.add(errorData);
                        }
                    }
                    // 批量更新数据
                    this.electricitySalesDataPushService.updateElectricitySalesDataPushList(updateList);
                }
            } else {
                String message = resultJson.getString("message");
                logger.error("获取accessToken失败,错误信息:[" + message + "].");
                return;
            }
        }
    }
}
