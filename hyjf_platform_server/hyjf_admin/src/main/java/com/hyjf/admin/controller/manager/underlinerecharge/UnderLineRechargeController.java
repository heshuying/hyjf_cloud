package com.hyjf.admin.controller.manager.underlinerecharge;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.admin.beans.request.UnderLineRechargeRequestBean;
import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.common.result.ListResult;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.service.UnderLineRechargeService;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.admin.UnderLineRechargeResponse;
import com.hyjf.am.vo.admin.UnderLineRechargeVO;
import com.hyjf.common.util.CommonUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 线下充值类型配置
 * @Author : huanghui
 */
@Api(tags = "配置中心 - 线下充值类型")
@RestController
@RequestMapping("/hyjf-admin/underLineRecharge")
public class UnderLineRechargeController extends BaseController {

    @Autowired
    private UnderLineRechargeService underLineRechargeService;

    @ApiOperation( value = "线下充值类型列表", notes = "线下充值类型列表")
    @PostMapping(value = "/init")
    public AdminResult<ListResult<UnderLineRechargeVO>> init(@RequestBody UnderLineRechargeRequestBean requestBean){

        UnderLineRechargeResponse underLineRechargeResponse = this.underLineRechargeService.selectUnderLineList(requestBean);

        // 初始化列表
        List<UnderLineRechargeVO> returnList = null;
        if (underLineRechargeResponse == null){
            return new AdminResult<>(FAIL, FAIL_DESC);
        }

        if (!Response.isSuccess(underLineRechargeResponse)){
            return new AdminResult<>(FAIL, "获取列表失败!");
        }

        if (CollectionUtils.isNotEmpty(underLineRechargeResponse.getResultList())){
            returnList = CommonUtils.convertBeanList(underLineRechargeResponse.getResultList(), UnderLineRechargeVO.class);
            return new AdminResult<ListResult<UnderLineRechargeVO>>(ListResult.build(returnList, underLineRechargeResponse.getCount()));
        }else {
            return new AdminResult<ListResult<UnderLineRechargeVO>>(ListResult.build(returnList, 0));
        }
    }


    @ApiOperation(value = "线下充值类型添加", notes = "线下充值类型添加")
    @PostMapping("/add")
    public AdminResult<UnderLineRechargeVO> add(HttpServletRequest request, @RequestBody UnderLineRechargeRequestBean requestBean){

        // 当前用户的UserID
//        requestBean.setCreateUserId(Integer.valueOf(this.getUser(request).getId()));
        requestBean.setCreateUserId(1);
        // 当前用户用户名
//        requestBean.setCreateUserName(this.getUser(request).getUsername());
        requestBean.setCreateUserName("admin");

        JSONObject jsonObject = new JSONObject();
        jsonObject = this.validatorFieldCheck(jsonObject, requestBean);

        // 验证字符串
        if (jsonObject.size() > 0){
            return new AdminResult<>(FAIL, jsonObject.toString());
        }

        UnderLineRechargeResponse response = this.underLineRechargeService.insertRecord(requestBean);

        if (response == null) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
        if (!Response.isSuccess(response)) {
            return new AdminResult<>(FAIL, response.getMessage());
        }
        return new AdminResult<>();
    }

    @ApiOperation(value = "线下充值类型验证", notes = "验证当前充值类型是否已存在.")
    @PostMapping("/checkValidate")
    public AdminResult<UnderLineRechargeVO> checkValidate(@RequestBody UnderLineRechargeRequestBean requestBean){

        JSONObject jsonObject = new JSONObject();
        jsonObject = this.validatorFieldCheck(jsonObject, requestBean);

        // 验证字符串
        if (jsonObject.size() > 0){
            return new AdminResult<>(FAIL, jsonObject.toString());
        }

        boolean checkCode = this.underLineRechargeService.checkValidate(requestBean.getCode());

        if (checkCode){
            return new AdminResult<>(FAIL, "当前Code已存在!");
        }
        return new AdminResult<>();
    }

    @ApiOperation(value = "线下充值类型更新", notes = "更新当前 Code .")
    @PostMapping("/update")
    public AdminResult<UnderLineRechargeVO> update(@RequestBody UnderLineRechargeRequestBean requestBean){

        JSONObject jsonObject = new JSONObject();
        jsonObject = this.validatorFieldCheck(jsonObject, requestBean);

        // 验证字符串
        if (jsonObject.size() > 0){
            return new AdminResult<>(FAIL, jsonObject.toString());
        }

        boolean response = this.underLineRechargeService.updateUnderLineRecharge(requestBean);

        if (!response) {
            return new AdminResult<>(FAIL, "更新失败!");
        }
        return new AdminResult<>();
    }

    /**
     * 删除数据
     * @param requestBean
     * @return
     */
    @ApiOperation(value = "线下充值类型删除", notes = "删除当前 Code.")
    @PostMapping("/delete")
    public AdminResult<UnderLineRechargeVO> delete(@RequestBody UnderLineRechargeRequestBean requestBean){

        Integer id = requestBean.getId();

        boolean checkCode = this.underLineRechargeService.deleteUnderLineRecharge(id);
        if (!checkCode){
            return new AdminResult<>(FAIL, "删除失败!");
        }
        return new AdminResult<>();
    }

    /**
     * 數據校驗
     * @param jsonObject
     * @param requestBean
     * @return
     */
    private JSONObject validatorFieldCheck(JSONObject jsonObject, UnderLineRechargeRequestBean requestBean) {

        // 不可为空
        if (StringUtils.isBlank(requestBean.getCode())){
            jsonObject.put("code", "Code不能为空!");
            return jsonObject;
        }

        // 必须为数字
        if (!StringUtils.isNumeric(requestBean.getCode())){
            jsonObject.put("code", "Code必须为数字!");
            return jsonObject;
        }

        // TODO: code长度
        return jsonObject;
    }

}
