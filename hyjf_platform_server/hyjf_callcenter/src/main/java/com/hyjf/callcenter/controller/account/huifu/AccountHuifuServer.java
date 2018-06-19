package com.hyjf.callcenter.controller.account.huifu;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.hyjf.am.vo.callcenter.CallcenterAccountHuifuVO;
import com.hyjf.am.vo.callcenter.CallcenterBankConfigVO;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.callcenter.beans.AccountHuifuBean;
import com.hyjf.callcenter.beans.ResultListBean;
import com.hyjf.callcenter.beans.UserBean;
import com.hyjf.callcenter.controller.base.CallcenterBaseController;
import com.hyjf.callcenter.result.BaseResultBean;
import com.hyjf.callcenter.service.AccountHuifuService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author libin
 * @version AccountHuifuServer, v0.1 2018/6/6 13:38
 */
@Api(value = "查询汇付绑卡")
@RestController
@RequestMapping("/hyjf-callcenter/account/huifu")
public class AccountHuifuServer extends CallcenterBaseController {
    @Autowired
    private AccountHuifuService accountHuifuService;
  
	/**
	 * 呼叫中心接口调用入口
	 * @param request
	 * @param response
	 * @param bean
	 * @return
	 */
    @ApiOperation(value = "查询汇付绑卡", notes = "查询汇付绑卡")
    @ResponseBody
    @PostMapping(value = "/getTiedcardInfo", produces = "application/json; charset=utf-8")
	public ResultListBean getContentOfAccountHuifu(HttpServletRequest request, HttpServletResponse response,@RequestBody UserBean bean) {
    	ResultListBean result = new ResultListBean();
    	// ht_bank_config表做成需要的map 初始化
    	Map<String, String> configMap = new HashMap<String, String>(); 
        //根据用户名或手机号取得用户信息
        UserVO user = this.getUser(bean, result);
        if (user == null) {
            if (result.getStatus()!=BaseResultBean.STATUS_FAIL) {
                result.statusMessage(BaseResultBean.STATUS_FAIL,"该用户不存在！");
            }
            return result;
        }
        //*************各自业务开始***************
        //根据用户信息查询汇付绑卡关系
        AccountHuifuBean returnBean = new AccountHuifuBean();
        // (1)config库的map
        List<CallcenterBankConfigVO> bankConfigList = this.accountHuifuService.getBankConfigList();
		if (bankConfigList == null) {
			result.statusMessage(ResultListBean.STATUS_FAIL,"银行配置为空！");
			return result;
		}
        for (CallcenterBankConfigVO vo : bankConfigList) {
        	configMap.put(vo.getBankCode(), vo.getBankName()); 
        }
        // (2)AccountHuifu 的 LIST
		List<CallcenterAccountHuifuVO> recordList = this.accountHuifuService.getRecordList(user,bean.getLimitStart(),bean.getLimitSize());
		if (recordList == null) {
			result.statusMessage(ResultListBean.STATUS_FAIL,"该用户在汇付未绑卡！");
			return result;
		}
		//编辑返回信息
		for (CallcenterAccountHuifuVO recordBean : recordList) {
			if( StringUtils.isNotEmpty(recordBean.getBank())){
				if(configMap.containsKey(recordBean.getBank())){
					// 此处将从map中取出的bankName SET 回 返回的bank字段
					recordBean.setBank(configMap.get(recordBean.getBank()));
				}
			}
			//*************各自业务结束***************
			//检索bean→返回bean
			BeanUtils.copyProperties(recordBean, returnBean);
			//用户名
			returnBean.setUserName(user.getUsername());
			//手机号
			returnBean.setMobile(user.getMobile());		
			result.getDataList().add(returnBean);
		}
		result.statusMessage(ResultListBean.STATUS_SUCCESS, ResultListBean.STATUS_SUCCESS_DESC);
		return result;
    } 
}
