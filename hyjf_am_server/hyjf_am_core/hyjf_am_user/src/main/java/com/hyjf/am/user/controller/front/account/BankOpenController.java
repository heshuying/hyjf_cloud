package com.hyjf.am.user.controller.front.account;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.response.IntegerResponse;
import com.hyjf.am.response.trade.CorpOpenAccountRecordResponse;
import com.hyjf.am.response.user.BankCardResponse;
import com.hyjf.am.response.user.BankOpenAccountListResponse;
import com.hyjf.am.response.user.BankOpenAccountResponse;
import com.hyjf.am.response.user.UserInfoResponse;
import com.hyjf.am.resquest.user.BankCardRequest;
import com.hyjf.am.resquest.user.BankOpenRequest;
import com.hyjf.am.user.controller.BaseController;
import com.hyjf.am.user.dao.model.auto.*;
import com.hyjf.am.user.service.front.account.BankOpenService;
import com.hyjf.am.vo.trade.CorpOpenAccountRecordVO;
import com.hyjf.am.vo.user.BankCardVO;
import com.hyjf.am.vo.user.BankOpenAccountVO;
import com.hyjf.am.vo.user.UserInfoVO;
import com.hyjf.common.util.CommonUtils;
import com.hyjf.common.util.GetDate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/am-user/bankopen")
public class BankOpenController extends BaseController {

	@Autowired
	private BankOpenService bankOpenService;

	/**
	 * 插入开户日志表
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping("/updateUserAccountLog")
	public IntegerResponse updateUserAccountLog(@RequestBody @Valid BankOpenRequest request) {
		logger.info("updateUserAccountLog...param is :{}", JSONObject.toJSONString(request));

		Integer userId = request.getUserId();
		String username = request.getUsername();
		String mobile = request.getMobile();
		String orderId = request.getOrderId();
		String channel = request.getChannel();
		String trueName = request.getTrueName();
		String idNo = request.getIdNo();
		String cardNO = request.getCardNo();
		String srvAuthCode = request.getSrvAuthCode();

		boolean result = this.bankOpenService.updateUserAccountLog(userId, username, mobile, orderId, channel, trueName,idNo,cardNO,srvAuthCode);

		return new IntegerResponse(result?1:0);
	}

	/**
	 * 更新开户日志表
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping("/updateUserAccountLogStatus")
	public int updateUserAccountLogStatus(@RequestBody @Valid BankOpenRequest request) {
		logger.info("updateUserAccountLogStatus...param is :{}", JSONObject.toJSONString(request));

		Integer userId = request.getUserId();
		String orderId = request.getOrderId();
		Integer status = request.getStatus();
		String retCode = request.getRetCode();
		String retMsg = request.getRetMsg();

		this.bankOpenService.updateUserAccountLog(userId, orderId, status, retCode, retMsg);

		return 1;
	}

	/**
	 * 更新开户日志表
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping("/updateUserAccount")
	public int updateUserAccount(@RequestBody @Valid BankOpenRequest request) {
		logger.info("updateUserAccount..dd. param is ::{}", JSONObject.toJSONString(request));

		Integer userId = request.getUserId();
		String orderId = request.getOrderId();
		String accountId = request.getAccountId();
		Integer bankAccountEsb = request.getBankAccountEsb();
		String trueName = request.getTrueName();
		String idNo = request.getIdNo();
		String mobile = request.getMobile();
		Integer roleId = request.getRoleId();
		Integer isSetPassword = request.getIsSetPassword();

		boolean result = this.bankOpenService.updateUserAccount(userId, trueName, orderId, accountId, idNo, bankAccountEsb, mobile,roleId,isSetPassword);

		return result?1:0;
	}


	@RequestMapping("/findByCardId/{cardId}")
	public UserInfoResponse findByCardId(@PathVariable String cardId) {
		UserInfoResponse response = new UserInfoResponse();
		UserInfo usersInfo = bankOpenService.findUserInfoByCradId(cardId);
		if (usersInfo != null) {
			UserInfoVO userInfoVO = new UserInfoVO();
			BeanUtils.copyProperties(usersInfo, userInfoVO);
			response.setResult(userInfoVO);
		}
		return response;
	}

	@RequestMapping("/selectById/{userId}")
	public BankOpenAccountResponse selectById(@PathVariable String userId) {
		if(userId == null || "".equals(userId) || "null".equalsIgnoreCase(userId)){
			logger.error("直接返回null,原因userId为:[{}]",userId);
			return null;
		}
		BankOpenAccountExample accountExample = new BankOpenAccountExample();
		BankOpenAccountExample.Criteria crt = accountExample.createCriteria();
		crt.andUserIdEqualTo(Integer.valueOf(userId));
		BankOpenAccountResponse response = new BankOpenAccountResponse();
		BankOpenAccount bankOpenAccount = bankOpenService.selectByExample(accountExample);
		if(bankOpenAccount != null){
			BankOpenAccountVO bankOpenAccountVO = new BankOpenAccountVO();
			BeanUtils.copyProperties(bankOpenAccount, bankOpenAccountVO);
			response.setResult(bankOpenAccountVO);
		}
		return response;
	}

	@RequestMapping("/selectByAccountId/{accountId}")
	public BankOpenAccountResponse selectByAccountId(@PathVariable String accountId) {
		BankOpenAccountExample example = new BankOpenAccountExample();
		BankOpenAccountExample.Criteria cra = example.createCriteria();
		cra.andAccountEqualTo(accountId);
		BankOpenAccountResponse response = new BankOpenAccountResponse();
		BankOpenAccount bankOpenAccount = bankOpenService.selectByExample(example);
		if(bankOpenAccount != null){
			BankOpenAccountVO bankOpenAccountVO = new BankOpenAccountVO();
			BeanUtils.copyProperties(bankOpenAccount, bankOpenAccountVO);
			response.setResult(bankOpenAccountVO);
		}
		return response;
	}

	/**
	 * 根据用户ID查询企业用户信息
	 * @param userId
	 * @return
	 */
	@GetMapping("/getCorpOpenAccountRecord/{userId}")
	public CorpOpenAccountRecordResponse getCorpOpenAccountRecord(@PathVariable Integer userId){
		CorpOpenAccountRecordResponse response = new CorpOpenAccountRecordResponse();
		CorpOpenAccountRecord corpOpenAccountRecord= bankOpenService.getCorpOpenAccountRecord(userId);
		logger.info("corpOpenAccountRecord :{}", JSONObject.toJSONString(corpOpenAccountRecord));
		if(null != corpOpenAccountRecord){
			CorpOpenAccountRecordVO corpOpenAccountRecordVO = new CorpOpenAccountRecordVO();
			BeanUtils.copyProperties(corpOpenAccountRecord,corpOpenAccountRecordVO);
			response.setResult(corpOpenAccountRecordVO);
		}
		return response;
	}

	/**
	 * 开户成功后保存银行卡信息
	 * @param request
	 * @return
	 */
	@RequestMapping("/saveCardNoToBank")
	public int saveCardNoToBank(@RequestBody @Valid BankCardRequest request) {
		logger.info("saveCardNoToBank...param is :{}", JSONObject.toJSONString(request));
		boolean result = this.bankOpenService.saveCardNoToBank(request);
		return result ? 1 : 0;
	}

	/**
	 * @Description 根据订单编号查询开户失败的原因
	 * @Author sunss
	 * @Date 2018/6/22 9:07
	 */
	@GetMapping("/getBankOpenAccountFiledMess/{logOrdId}")
	public String getBankOpenAccountFiledMess(@PathVariable String logOrdId){
		return bankOpenService.getBankOpenAccountFiledMess(logOrdId);
	}

	@GetMapping("/selectBankCardByUserIdAndStatus/{userId}")
	public BankCardResponse selectBankCardByUserIdAndStatus(@PathVariable Integer userId){
		BankCardResponse response = new BankCardResponse();
		List<BankCard> bankCard = bankOpenService.selectBankCardByUserIdAndStatus(userId);
		if(null!=bankCard){
			List<BankCardVO> bankCardVO = CommonUtils.convertBeanList(bankCard,BankCardVO.class);
			response.setResultList(bankCardVO);
		}
		return response;
	}

	/**
	 *
	 * @param userId
	 * @return
	 */
	@GetMapping("/selectBankCardByUserIdAndStatus/{userId}/{status}")
	public BankCardResponse selectBankCardByUserIdAndStatus(@PathVariable Integer userId,@PathVariable Integer status){
		BankCardResponse response = new BankCardResponse();
		List<BankCard> bankCard = bankOpenService.selectBankCardByUserIdAndStatus(userId,status);
		if(null!=bankCard){
			List<BankCardVO> bankCardVO = CommonUtils.convertBeanList(bankCard,BankCardVO.class);
			response.setResultList(bankCardVO);
		}
		return response;
	}

	/**
	 * 获取用户账户信息byaccountId
	 * @auth libin
	 * @param accountId
	 * @return
	 */
	@RequestMapping("/getBankOpenAccountByAccountId/{accountId}")
	public BankOpenAccountResponse getBankOpenAccountByAccountId(@PathVariable String accountId) {
		BankOpenAccountExample accountExample = new BankOpenAccountExample();
		BankOpenAccountExample.Criteria crt = accountExample.createCriteria();
		crt.andAccountEqualTo(accountId);
		BankOpenAccountResponse response = new BankOpenAccountResponse();
		BankOpenAccount bankOpenAccount = bankOpenService.selectByExample(accountExample);
		if(bankOpenAccount != null){
			BankOpenAccountVO bankOpenAccountVO = new BankOpenAccountVO();
			BeanUtils.copyProperties(bankOpenAccount, bankOpenAccountVO);
			response.setResult(bankOpenAccountVO);
		}
		return response;
	}

	/**
	 * 根据UsetList获取用户账户信息
	 * @auth wenxin
	 * @param userId
	 * @return
	 */
	@RequestMapping("/selectByListId")
	public BankOpenAccountListResponse selectByListId(@RequestBody @Valid List<Integer> userId) {
		BankOpenAccountListResponse response = new BankOpenAccountListResponse();
		List<BankOpenAccount> bankOpenAccount = bankOpenService.selectByListExample(userId);
		if(bankOpenAccount != null && bankOpenAccount.size() > 0){
			List<BankOpenAccountVO> bankOpenAccountVO = new ArrayList<BankOpenAccountVO>();
			BankOpenAccountVO Vo = new BankOpenAccountVO();
			//BeanUtils.copyProperties(bankOpenAccountVO,bankOpenAccount);
			for(BankOpenAccount boa :bankOpenAccount){
				Vo.setAccount(boa.getAccount());
				Vo.setCreateTime(boa.getCreateTime());
				Vo.setCreateUserId(boa.getCreateUserId());
				Vo.setId(boa.getId());
				Vo.setUpdateUserId(boa.getUpdateUserId());
				Vo.setUpdateTime(boa.getUpdateTime());
				Vo.setUserName(boa.getUserName());
				Vo.setUserId(boa.getUserId());
				bankOpenAccountVO.add(Vo);
			}
			response.setResult(bankOpenAccountVO);
		}
		return response;
	}

	@RequestMapping("/getBankOpenAccountForCrmRepair")
	public void getBankOpenAccountForCrmRepair() {
		//设置条件，为上线三天的数据
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		BankOpenAccountExample accountExample = new BankOpenAccountExample();
		BankOpenAccountExample.Criteria crt = accountExample.createCriteria();
		crt.andCreateTimeBetween(GetDate.str2Date("2019-03-09 00:00:00", dateFormat), GetDate.str2Date("2019-03-11 23:59:59", dateFormat));
		bankOpenService.getBankOpenAccountForCrmRepair(accountExample);
	}
}
