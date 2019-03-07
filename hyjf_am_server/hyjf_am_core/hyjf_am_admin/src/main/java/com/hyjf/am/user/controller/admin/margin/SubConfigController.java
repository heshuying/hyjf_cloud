package com.hyjf.am.user.controller.admin.margin;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.admin.AdminSubConfigResponse;
import com.hyjf.am.response.user.UserInfoCustomizeResponse;
import com.hyjf.am.resquest.admin.AdminSubConfigRequest;
import com.hyjf.am.trade.dao.model.auto.SubCommissionListConfig;
import com.hyjf.am.trade.service.admin.TradeSubConfigService;
import com.hyjf.am.user.controller.BaseController;
import com.hyjf.am.user.service.admin.margin.SubConfigService;
import com.hyjf.am.vo.trade.SubCommissionListConfigVo;
import com.hyjf.common.paginator.Paginator;
import com.hyjf.common.util.CommonUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author by xiehuili on 2018/7/10.
 */
@RestController
@RequestMapping("/am-admin/config/subconfig")
public class SubConfigController extends BaseController {
    @Autowired
    @Qualifier("amUserSubConfigServiceImpl")
    private SubConfigService amUserSubConfigServiceImpl;
    @Autowired
    private TradeSubConfigService subConfigService;

    /**
     * 保证金配置，根据用户名称查询用户信息
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "/queryUserInfoByUserName", method = RequestMethod.POST)
    public UserInfoCustomizeResponse selectUserInfoByUserName(@RequestBody AdminSubConfigRequest req) {
        return amUserSubConfigServiceImpl.selectUserInfoByUserName(req.getUsername());
    }

    /**
     * 根据用户名查询分账名单是否存在
     * @param adminRequest
     * @return
     */
    @RequestMapping(value = "/isExist", method = RequestMethod.POST)
    public AdminSubConfigResponse subconfig(@RequestBody AdminSubConfigRequest adminRequest) {
        return amUserSubConfigServiceImpl.selectByExampleUsername(adminRequest.getUsername());
    }

    /**
     * 分页查询配置中心分账名单列表
     * @return
     */
    @RequestMapping("/list")
    public AdminSubConfigResponse selectSubConfigListByPage(@RequestBody AdminSubConfigRequest adminRequest) {
        logger.info("分账名单列表..." + JSONObject.toJSON(adminRequest));
        AdminSubConfigResponse  result=new AdminSubConfigResponse();
        // 封装查询条件
        Map<String, Object> conditionMap = setCondition(adminRequest);
        //查询配置中心条数
        int recordTotal = this.subConfigService.getSubConfigListCountByPage(conditionMap);
        if (recordTotal > 0) {
            Paginator paginator = new Paginator(adminRequest.getCurrPage(), recordTotal,adminRequest.getPageSize() == 0?10:adminRequest.getPageSize());
            //查询记录
            List<SubCommissionListConfig> recordList =subConfigService.getSubConfigListByPage(conditionMap,paginator.getOffset(), paginator.getLimit());
            if(!CollectionUtils.isEmpty(recordList)){
                List<SubCommissionListConfigVo> configList = CommonUtils.convertBeanList(recordList, SubCommissionListConfigVo.class);
                result.setResultList(configList);
                result.setRecordTotal(recordTotal);
                result.setRtn(Response.SUCCESS);
            }
        }
        return result;
    }

    /**
     * 查询详情页面
     * @return
     */
    @RequestMapping("/info")
    public AdminSubConfigResponse selectSubConfigInfoById(@RequestBody AdminSubConfigRequest adminRequest) {
        logger.info("分账名单详情页面..." + JSONObject.toJSON(adminRequest));
        AdminSubConfigResponse  result=new AdminSubConfigResponse();
        if (adminRequest.getId() != null) {
            SubCommissionListConfig record = this.subConfigService.getSubConfigRecordById(adminRequest.getId());
            SubCommissionListConfigVo recordVo = new SubCommissionListConfigVo();
            if(null != record){
                BeanUtils.copyProperties(record, recordVo);
                result.setResult(recordVo);
            }
        }
        return result;
    }
    /**
     * 添加分账名单配置
     * @param req
     */
    @RequestMapping("/insert")
    public AdminSubConfigResponse insertSubConfig(@RequestBody AdminSubConfigRequest req) {
        AdminSubConfigResponse resp = new AdminSubConfigResponse();
        // 插入
        int cot = this.subConfigService.insertSubConfigRecord(req);
        if(cot > 0 ){
            resp.setRtn(Response.SUCCESS);
        }else{
            resp.setRtn(Response.FAIL);
            resp.setMessage("添加失败！");
        }
        return resp;
    }

    /**
     * 修改分账名单配置
     * @param req
     */
    @RequestMapping("/update")
    public AdminSubConfigResponse updateSubConfig(@RequestBody AdminSubConfigRequest req) {
        AdminSubConfigResponse resp = new AdminSubConfigResponse();
        // 修改
        int cot = this.subConfigService.updateSubConfigRecord(req);
        if(cot > 0 ){
            resp.setRtn(Response.SUCCESS);
        }else{
            resp.setRtn(Response.FAIL);
            resp.setMessage("修改失败！");
        }
        return resp;
    }
    /**
     * 删除分账名单配置
     * @param req
     */
    @RequestMapping("/delete")
    public AdminSubConfigResponse deleteSubConfig(@RequestBody AdminSubConfigRequest req) {
        AdminSubConfigResponse resp = new AdminSubConfigResponse();
        if(req.getId() != null){
            this.subConfigService.deleteSubConfigRecord(req.getId());
            resp.setRtn(Response.SUCCESS);
        }else{
            resp.setRtn(Response.FAIL);
            resp.setMessage("删除失败！");
        }
        return resp;
    }


    /**
     * 封装查询条件
     *
     * @param adminRequest
     * @return
     */
    private Map<String, Object> setCondition(AdminSubConfigRequest adminRequest) {
        String userNameSrch = StringUtils.isNotEmpty(adminRequest.getUserNameSrch()) ? adminRequest.getUserNameSrch() : null;
        String trueNameSrch = StringUtils.isNotEmpty(adminRequest.getTrueNameSrch()) ? adminRequest.getTrueNameSrch() : null;
        String roleNameSrch = StringUtils.isNotEmpty(adminRequest.getRoleNameSrch()) ? adminRequest.getRoleNameSrch() : null;
        String userTypeSrch = StringUtils.isNotEmpty(adminRequest.getUserTypeSrch()) ? adminRequest.getUserTypeSrch() : null;
        String accountSrch = StringUtils.isNotEmpty(adminRequest.getAccountSrch()) ? adminRequest.getAccountSrch() : null;
        String statusSrch = StringUtils.isNotEmpty(adminRequest.getStatusSrch()) ? adminRequest.getStatusSrch() : null;
        String recieveTimeStartSrch = StringUtils.isNotEmpty(adminRequest.getRecieveTimeStartSrch()) ? adminRequest.getRecieveTimeStartSrch() : null;
        String recieveTimeEndSrch = StringUtils.isNotEmpty(adminRequest.getRecieveTimeEndSrch()) ? adminRequest.getRecieveTimeEndSrch() : null;

        Map<String, Object> conditionMap = new HashMap<String, Object>();
        conditionMap.put("userNameSrch", userNameSrch);
        conditionMap.put("trueNameSrch", trueNameSrch);
        if(StringUtils.isNotBlank(roleNameSrch)&&!("投资人".equals(roleNameSrch)||"出借人".equals(roleNameSrch)||"担保机构".equals(roleNameSrch)||"垫付机构".equals(roleNameSrch))){
            //('投资人','出借人')('担保机构','垫付机构')
            conditionMap.put("roleNameSrchS", "其他");
        }
        conditionMap.put("roleNameSrch", roleNameSrch);
        conditionMap.put("userTypeSrch", userTypeSrch);
        conditionMap.put("accountSrch", accountSrch);
        conditionMap.put("statusSrch", statusSrch);
        conditionMap.put("recieveTimeStartSrch", recieveTimeStartSrch);
        conditionMap.put("recieveTimeEndSrch", recieveTimeEndSrch);
        return conditionMap;
    }


}
