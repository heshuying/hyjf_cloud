package com.hyjf.am.trade.controller.admin.underlinerecharge;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.response.AdminResponse;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.admin.UnderLineRechargeResponse;
import com.hyjf.am.resquest.admin.UnderLineRechargeRequest;
import com.hyjf.am.trade.dao.model.auto.UnderLineRecharge;
import com.hyjf.am.trade.service.admin.underlinerecharge.UnderLineRechargeService;
import com.hyjf.am.vo.admin.UnderLineRechargeVO;
import com.hyjf.common.cache.RedisConstants;
import com.hyjf.common.cache.RedisUtils;
import com.hyjf.common.paginator.Paginator;
import com.hyjf.common.util.CommonUtils;
import io.swagger.annotations.Api;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * 线下充值类型配置
 * @Author : huanghui
 */
@Api(value = "线下充值类型配置")
@RestController
@RequestMapping("/am-trade/underLineRecharge")
public class UnderLineRechargeController {

    @Autowired
    private UnderLineRechargeService underLineRechargeService;

    @RequestMapping(value = "/selectUnderLineList", method = RequestMethod.POST)
    public UnderLineRechargeResponse selectUnderLineList(@RequestBody UnderLineRechargeRequest request){
        UnderLineRechargeResponse response = new UnderLineRechargeResponse();

        Integer count = this.underLineRechargeService.getUnderLineRechaegeCount(request);

        // currPage<0 为全部,currPage>0 为具体某一页
        if(request.getCurrPage()>0){
            Paginator paginator = new Paginator(request.getCurrPage(),count);
            request.setLimitStart(paginator.getOffset());
            request.setLimitEnd(paginator.getLimit());
        }

        List<UnderLineRecharge> underLineRechargeList = this.underLineRechargeService.getUnderLineRechargeListByCode(request);

        if (!CollectionUtils.isEmpty(underLineRechargeList)){
            response.setResultList(CommonUtils.convertBeanList(underLineRechargeList, UnderLineRechargeVO.class));
            response.setCount(count);
            response.setRtn(Response.SUCCESS);
        }
        return response;
    }
    /**
     * 数据写入
     * @param request
     * @return
     */
    @RequestMapping( value = "/insterUnderRechargeCode", method = RequestMethod.POST)
    public UnderLineRechargeResponse insterUnderRechargeCode(@RequestBody UnderLineRechargeRequest request){

        UnderLineRechargeResponse response = new UnderLineRechargeResponse();

        //写入返回
        int returnCode = underLineRechargeService.insterUnderRechargeCode(request);

        if (returnCode > 0){
            //数据写入数据库成功后,将所有code写入Redis
            int count = this.underLineRechargeService.getUnderLineRechaegeCount(request);
            if (count > 0){
                List<String> codeList = new ArrayList<String>();
                // 获取充值类型列表
                List<UnderLineRecharge> underLineRechargeList = this.underLineRechargeService.getUnderLineRechargeList(request);

                for (UnderLineRecharge code : underLineRechargeList){
                    codeList.add(code.getCode());
                }

                // 将所有code 写入Redis
                String codeListString = JSONObject.toJSONString(codeList);
                RedisUtils.set(RedisConstants.UNDER_LINE_RECHARGE_TYPE, codeListString);
            }
            response.setRtn(AdminResponse.SUCCESS);
        }else {
            response.setRtn(AdminResponse.FAIL);
            response.setMessage("添加失败!");
        }
        return response;
    }

    /**
     * 获取指定 code 的数据
     * @param code
     * @return
     */
    @RequestMapping(value = "/checkValidate/{code}", method = RequestMethod.GET)
    public boolean checkValidate(@PathVariable String code){
        UnderLineRechargeResponse response = new UnderLineRechargeResponse();
        UnderLineRechargeRequest request = new UnderLineRechargeRequest();
        request.setCode(code);

        List<UnderLineRecharge> underLineRechargeList = this.underLineRechargeService.getUnderLineRechargeListByCode(request);

        // 当前 Code 存在
        if (underLineRechargeList != null && underLineRechargeList.size() > 0){
            response.setMessage("当前Code已存在!");
            return true;
        }
        return false;
    }

    /**
     * 更新数据
     * @param request
     * @return
     */
    @RequestMapping( value = "/updateUnderLineRecharge", method = RequestMethod.POST)
    public boolean updateUnderLineRecharge(@RequestBody UnderLineRechargeRequest request){
        Integer updateStatus = this.underLineRechargeService.updateAction(request);

        if (updateStatus > 0){

            List<String> codeList = new ArrayList<String>();
            // 获取充值类型列表
            List<UnderLineRecharge> underLineRechargeList = this.underLineRechargeService.getUnderLineRechargeList(request);

            for (UnderLineRecharge code : underLineRechargeList){
                codeList.add(code.getCode());
            }

            // 将所有code 写入Redis
            String codeListString = JSONObject.toJSONString(codeList);
            RedisUtils.set(RedisConstants.UNDER_LINE_RECHARGE_TYPE, codeListString);

            return true;
        }
        return false;
    }

    /**
     * 删除数据
     * @param id
     * @return
     */
    @RequestMapping(value = "/deleteUnderLineRecharge/{id}", method = RequestMethod.GET)
    public boolean deleteUnderLineRecharge(@PathVariable Integer id){

        Integer deleteStatus = this.underLineRechargeService.deleteById(id);
        if (deleteStatus > 0){

            UnderLineRechargeRequest request = new UnderLineRechargeRequest();

            List<String> codeList = new ArrayList<String>();
            // 获取充值类型列表
            List<UnderLineRecharge> underLineRechargeList = this.underLineRechargeService.getUnderLineRechargeList(request);

            for (UnderLineRecharge code : underLineRechargeList){
                codeList.add(code.getCode());
            }

            // 将所有code 写入Redis
            String codeListString = JSONObject.toJSONString(codeList);
            RedisUtils.set(RedisConstants.UNDER_LINE_RECHARGE_TYPE, codeListString);

            return true;
        }
        return false;
    }

}
