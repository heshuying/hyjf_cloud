package com.hyjf.callcenter.controller.balance;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.hyjf.am.vo.callcenter.CallCenterBankAccountManageVO;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.callcenter.beans.ResultListBean;
import com.hyjf.callcenter.beans.SrchBalanceInfoBean;
import com.hyjf.callcenter.beans.UserBean;
import com.hyjf.callcenter.controller.base.CallcenterBaseController;
import com.hyjf.callcenter.result.BaseResultBean;
import com.hyjf.callcenter.service.SrchBalanceInfoService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 呼叫中心:按照用户名/手机号查询账户余额Controller
 * @author LIBIN
 * @version hyjf 1.0
 * @since hyjf 1.0 2017年07月15日 
 */
@Api(value = "查询账户余额")
@RestController
@RequestMapping("/hyjf-callcenter/balance")
public class SrchBalanceInfoServer extends CallcenterBaseController {
	
	@Autowired
	private SrchBalanceInfoService srchBalanceInfoService;
	/**
	 * 呼叫中心接口调用入口
	 * @param request
	 * @param response
	 * @param bean
	 * @return
	 */
    @ApiOperation(value = "查询账户余额", notes = "查询账户余额")
    @ResponseBody
    @PostMapping(value = "/srchBalanceInfo", produces = "application/json; charset=utf-8")
    public ResultListBean getContentOfBalanceInfo(HttpServletRequest request, HttpServletResponse response, @RequestBody UserBean bean) {
    	//初始化結果bean
    	ResultListBean result = new ResultListBean();
		//初始化返回bean
		SrchBalanceInfoBean returnBean = new SrchBalanceInfoBean();
        //根据用户名或手机号取得用户信息
        UserVO user = this.getUser(bean, result);
        if (user == null) {
            if (result.getStatus()!=BaseResultBean.STATUS_FAIL) {
                result.statusMessage(BaseResultBean.STATUS_FAIL,"该用户不存在！");
            }
            return result;
        }
        if (user.getUsername() != null && !"".equals(user.getUsername().trim())) {
        	//1.银行资金查询(一个用户只有一条记录)
        	List<CallCenterBankAccountManageVO> bankAccountInfos = this.srchBalanceInfoService.queryBankAccountInfos(user,bean.getLimitStart(),bean.getLimitSize());
			if (bankAccountInfos == null) {
				result.statusMessage(BaseResultBean.STATUS_FAIL,"该用户没有资金信息！");
				return result;
			}
        	for (CallCenterBankAccountManageVO customize : bankAccountInfos) {
        		//用户名
				returnBean.setUsername(user.getUsername());
				//手机号
				returnBean.setMobile(user.getMobile());
				//资产总额
				returnBean.setBankTotal(customize.getBankTotal());
				//待收金额
				returnBean.setBankAwait(customize.getBankAwait());
				//待还金额
				returnBean.setBankWaitRepay(customize.getBankWaitRepay());
				//江西银行总可用金额
				returnBean.setBankBalance(customize.getBankBalance());
				//江西银行可用金额
				returnBean.setBankBalanceCash(customize.getBankBalanceCash());
				//江西银行总冻结金额
				returnBean.setBankFrost(customize.getBankFrost());
				//江西银行冻结金额
				returnBean.setBankFrostCash(customize.getBankFrostCash());
				//汇付可用金额
				returnBean.setBalanceTotal(customize.getBalanceTotal());
				//汇付冻结金额
				returnBean.setFrostTotal(customize.getFrostTotal());
			}
        } else {
			result.statusMessage(BaseResultBean.STATUS_FAIL,"该用户无用户名！");
		}
		result.statusMessage(BaseResultBean.STATUS_SUCCESS, BaseResultBean.STATUS_SUCCESS_DESC);
		return result;
    }
}
