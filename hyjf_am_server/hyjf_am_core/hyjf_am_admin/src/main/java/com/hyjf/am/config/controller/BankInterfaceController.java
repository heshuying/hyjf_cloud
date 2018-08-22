package com.hyjf.am.config.controller;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.config.dao.model.auto.BankInterface;
import com.hyjf.am.config.service.BankInterfaceService;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.trade.BankInterfaceResponse;
import com.hyjf.am.resquest.admin.BankInterfaceRequest;
import com.hyjf.am.vo.trade.account.BankInterfaceVO;
import com.hyjf.common.paginator.Paginator;
import com.hyjf.common.util.CommonUtils;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author pangchengchao
 * @version BankInterfaceController, v0.1 2018/6/22 14:28
 */
@RestController
@RequestMapping("/am-config/bankInterface")
public class BankInterfaceController extends BaseConfigController{

    @Autowired
    private BankInterfaceService bankInterfaceService;
    /**
     * 获取接口切换标识
     * @param type
     * @return
     */
    @GetMapping("/getBankInterfaceFlagByType/{type}")
    public BankInterfaceResponse getBanksConfigByBankId(@PathVariable String type){
        BankInterfaceResponse response = new BankInterfaceResponse();
        Integer flag = bankInterfaceService.getBanksConfigByBankId(type);
        response.setFlag(flag);
        return response;
    }

    /**
     *查询配置中心接口切换列表
     * @return
     */
    @RequestMapping("/list")
    public BankInterfaceResponse bankInterfaceListByPage(@RequestBody BankInterfaceRequest adminRequest) {
        logger.info("查询配置中心接口切换列表..." + JSONObject.toJSON(adminRequest));
        Map<String, Object> paraMap = new HashMap<>();
        paraMap.put("interfaceName",adminRequest.getInterfaceName());
        BankInterfaceResponse  response =new BankInterfaceResponse();
        //查询接口切换列表条数
        int recordCount = this.bankInterfaceService.selectBankInterfaceListCount(paraMap);
        if (recordCount >0) {
            Paginator paginator = new Paginator(adminRequest.getPaginatorPage(), recordCount);
            paraMap.put("limitStart",paginator.getOffset());
            paraMap.put("limitEnd",paginator.getLimit());
            //查询记录
           List<BankInterface> recordList =bankInterfaceService.selectBankInterfaceListByPage(paraMap);
            if(!CollectionUtils.isEmpty(recordList)){
                List<BankInterfaceVO> bankInterfaceVO = CommonUtils.convertBeanList(recordList, BankInterfaceVO.class);
                for (int i=0; i<bankInterfaceVO.size();i++){
                      if(bankInterfaceVO.get(i).getInterfaceStatus() == 0){
                          bankInterfaceVO.get(i).setInterfacestatus("老接口");
                      }else{
                          bankInterfaceVO.get(i).setInterfacestatus("新接口");
                      }
                    if(bankInterfaceVO.get(i).getIsUsable() == 0){
                        bankInterfaceVO.get(i).setIsusable("不可用");
                    }else{
                        bankInterfaceVO.get(i).setIsusable("可用");
                    }
                }
                response.setResultList(bankInterfaceVO);
                response.setRtn(Response.SUCCESS);
//                modelAndView.addObject("pageUrl", request.getRequestURL()+"?"+request.getQueryString());
            }
        }else{
            response.setRtn(Response.FAIL);
        }
        return response;
    }
    /**
     *查询配置中心接口切换详情
     * @return
     */
    @RequestMapping("/info")
    public BankInterfaceResponse selectBankInterfaceInfo(@RequestBody BankInterfaceRequest adminRequest) {
        logger.info("查询配置中心接口切换详情..." + JSONObject.toJSON(adminRequest));
        BankInterfaceResponse response =new BankInterfaceResponse();
        BankInterface record = this.bankInterfaceService.selectBankInterfaceInfo(adminRequest.getId());
        if (null != record) {
            BankInterfaceVO bankInterfaceVO = CommonUtils.convertBean(record, BankInterfaceVO.class);
            if(bankInterfaceVO.getInterfaceStatus() == 0){
                bankInterfaceVO.setInterfacestatus("老接口");
            }else{
                bankInterfaceVO.setInterfacestatus("新接口");
            }
            if(bankInterfaceVO.getIsUsable() == 0){
                bankInterfaceVO.setIsusable("不可用");
            }else{
                bankInterfaceVO.setIsusable("可用");
            }
            response.setResult(bankInterfaceVO);
            response.setRtn(Response.SUCCESS);
        } else {
            response.setRtn(Response.FAIL);
        }
        return response;
    }

    /**
     *修改配置中心接口切换
     * @return
     */
    @RequestMapping("/update")
    public BankInterfaceResponse updateBankInterfaceInfo(@RequestBody BankInterfaceVO bankInterfaceVO) {
        logger.info("修改配置中心接口切换详情..." + JSONObject.toJSON(bankInterfaceVO));
        BankInterfaceResponse response =new BankInterfaceResponse();
        try{
            this.bankInterfaceService.updateBankInterfaceInfo(bankInterfaceVO);
            response.setRtn(Response.SUCCESS);
        }catch (Exception e){
            response.setRtn(Response.FAIL);
        }
        return response;
    }
    /**
     *删除配置中心接口切换
     * @return
     */
    @RequestMapping("/delete")
    public BankInterfaceResponse deleteBankInterfaceInfo(@RequestBody BankInterfaceVO bankInterfaceVO) {
        logger.info("删除配置中心接口切换详情..." + JSONObject.toJSON(bankInterfaceVO));
        BankInterfaceResponse response =new BankInterfaceResponse();
        try{
            this.bankInterfaceService.updateBankInterfaceInfo(bankInterfaceVO);
            response.setRtn(Response.SUCCESS);
        }catch (Exception e){
            response.setRtn(Response.FAIL);
        }
        return response;
    }

}
