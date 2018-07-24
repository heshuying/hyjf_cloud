package com.hyjf.am.config.controller;

import com.hyjf.am.config.dao.model.auto.*;
import com.hyjf.am.config.dao.model.customize.QuestionCustomize;
import com.hyjf.am.config.service.BankConfigService;
import com.hyjf.am.config.service.QuestionService;
import com.hyjf.am.response.AdminResponse;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.admin.AdminBankConfigResponse;
import com.hyjf.am.response.admin.JxBankConfigResponse;
import com.hyjf.am.response.config.BankConfigResponse;
import com.hyjf.am.response.config.ParamNameResponse;
import com.hyjf.am.response.trade.BankCardBeanResponse;
import com.hyjf.am.response.trade.BankReturnCodeConfigResponse;
import com.hyjf.am.response.trade.BanksConfigResponse;
import com.hyjf.am.response.user.QuestionCustomizeResponse;
import com.hyjf.am.resquest.admin.AdminBankConfigRequest;
import com.hyjf.am.resquest.user.AnswerRequest;
import com.hyjf.am.vo.bank.BankCallBeanVO;
import com.hyjf.am.vo.config.ParamNameVO;
import com.hyjf.am.vo.trade.BankConfigVO;
import com.hyjf.am.vo.trade.BankReturnCodeConfigVO;
import com.hyjf.am.vo.trade.BanksConfigVO;
import com.hyjf.am.vo.trade.JxBankConfigVO;
import com.hyjf.am.vo.trade.account.AccountWithdrawVO;
import com.hyjf.am.vo.user.QuestionCustomizeVO;
import com.hyjf.common.paginator.Paginator;
import com.hyjf.common.util.CommonUtils;
import com.hyjf.common.validator.Validator;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author xiasq
 * @version BanksConfigController, v0.1 2018/6/22 14:28
 */
@RestController
@RequestMapping("/am-config/config")
public class BanksConfigController extends BaseConfigController{

    @Autowired
    private BankConfigService bankConfigService;

    @Autowired
    private QuestionService questionService;
    
    /**
     * 获取银行卡配置信息
     * @param bankId
     * @return
     */
    @GetMapping("/getBanksConfigByBankId/{bankId}")
    public BankConfigResponse getBanksConfigByBankId(@PathVariable Integer bankId){
        BankConfigResponse response = new BankConfigResponse();
        BankConfig bankConfig = bankConfigService.getBankConfigByBankId(bankId);
        if(null != bankConfig){
            BankConfigVO banksConfigVO = new BankConfigVO();
            BeanUtils.copyProperties(bankConfig,banksConfigVO);
            response.setResult(banksConfigVO);
        }
        return response;
    }

    /**
     *
     * @param retCode
     * @return
     */
    @GetMapping("/getBankReturnCodeConfig/{retCode}")
    public BankReturnCodeConfigResponse getBankReturnCodeConfig(@PathVariable String retCode){
        BankReturnCodeConfigExample example = new BankReturnCodeConfigExample();
        example.createCriteria().andRetCodeEqualTo(retCode);
        BankReturnCodeConfigResponse response = new BankReturnCodeConfigResponse();
        BankReturnCodeConfig retCodes = this.bankConfigService.selectByExample(example);
        if(null != retCodes){
            BankReturnCodeConfigVO bankReturnCodeConfigVO = new BankReturnCodeConfigVO();
            BeanUtils.copyProperties(retCodes,bankReturnCodeConfigVO);
            response.setResult(bankReturnCodeConfigVO);
        }
        return response;
    }
    
    /**
	 * 根据银行卡号获取bankId
	 * @param cardNo
	 * @return
	 */
	@RequestMapping("/queryBankIdByCardNo/{cardNo}")
	public String queryBankIdByCardNo(@PathVariable String cardNo) {
		return bankConfigService.queryBankIdByCardNo(cardNo);
	}

    @RequestMapping("/getNewQuestionList")
    public QuestionCustomizeResponse getNewQuestionList() {
        QuestionCustomizeResponse response = new QuestionCustomizeResponse();
        List<QuestionCustomize> questionCustomizes = questionService.getNewQuestionList();
        if(!CollectionUtils.isEmpty(questionCustomizes)){
            List<QuestionCustomizeVO> questionCustomizeVOS = CommonUtils.convertBeanList(questionCustomizes,QuestionCustomizeVO.class);
            response.setResultList(questionCustomizeVOS);
        }
        return response;
    }

    @RequestMapping("/countScore")
    public int countScore(@RequestBody  AnswerRequest answerList) {
        int countScore = questionService.countScore(answerList.getResultList());
        return countScore;
    }
    /**
     * 获取银行列表
     */
    @RequestMapping("/selectBankConfigList")
    public BankConfigResponse selectBankConfigList(){
        BankConfigResponse response= new BankConfigResponse();
        List<BankConfig> listBankConfig = bankConfigService.selectBankConfigList();
        if(null!=listBankConfig&&listBankConfig.size()>0){
            List<BankConfigVO> bankConfigVOList = CommonUtils.convertBeanList(listBankConfig, BankConfigVO.class);
            response.setResultList(bankConfigVOList);
            //代表成功
            response.setRtn(Response.SUCCESS);
        }
        return response;
    }
    /**
     * 获取status=1的银行列表
     */
    @RequestMapping("/getBankConfigListByStatus")
    public AdminBankConfigResponse getBankConfigListByStatus(BankConfigVO bankConfigVO){
        AdminBankConfigResponse response=new AdminBankConfigResponse();
        List<BankConfig> listBankConfig = bankConfigService.getBankConfigListByStatus(bankConfigVO);
        if(null!=listBankConfig&&listBankConfig.size()>0){
            List<BankConfigVO> listBanksConfig = CommonUtils.convertBeanList(listBankConfig, BankConfigVO.class);
            response.setResultList(listBanksConfig);
            //代表成功
            response.setRtn(Response.SUCCESS);
        }
        return response;
    }

    /**
     * 获取银行列表(快捷支付卡)
     */
    @RequestMapping("/getBankRecordListByQuickPayment")
    public List<BankConfigVO> getBankRecordListByQuickPayment(BankConfigVO bankConfigVO){
        List<BankConfigVO> listBanksConfig=null;
        List<BankConfig> listBankConfig = bankConfigService.getBankRecordListByQuickPayment(bankConfigVO);
        if(!CollectionUtils.isEmpty(listBankConfig)){
            listBanksConfig = CommonUtils.convertBeanList(listBankConfig, BankConfigVO.class);
        }
        return listBanksConfig;
    }

    @GetMapping("/getParamNameList/{nameClass}")
    public ParamNameResponse getParamNameList(@PathVariable String nameClass){
        ParamNameResponse response = new ParamNameResponse();
        List<ParamName> paramNameList =bankConfigService.getParamNameList(nameClass);
        if (CollectionUtils.isNotEmpty(paramNameList)){
            List<ParamNameVO> paramNameVOList = CommonUtils.convertBeanList(paramNameList,ParamNameVO.class);
            response.setResultList(paramNameVOList);
            response.setRtn(Response.SUCCESS);
        }
        return response;
    }
    /**
     * 分页查询银行配置列表
     */
    @RequestMapping("/selectBankConfigListByPage")
    public AdminBankConfigResponse selectBankConfigListByPage(AdminBankConfigRequest adminRequest){
        AdminBankConfigResponse response=new AdminBankConfigResponse();

        BankConfigVO vo = new BankConfigVO();
        BankConfig co = new BankConfig();
        BeanUtils.copyProperties(co,vo);
        //查询保证金配置条数
        List<BankConfig> listBankConfig = bankConfigService.selectBankConfigListByPage(vo,-1,-1);
        if(!CollectionUtils.isEmpty(listBankConfig)){
//            BeanUtils.copyProperties(listBankConfig,listRes);
//            for(BanksConfigVO banksConfig : listRes) {
//                // 不支持快捷支付
//                if (0 == banksConfig.getQuickPayment()) {
//                    banksConfig.setMonthCardQuota(new BigDecimal(0));
//                }
//            }
            Paginator paginator = new Paginator(adminRequest.getPaginatorPage(),listBankConfig.size());
//            BanksConfigVO bc=new BanksConfigVO();
//            bc.setBankName(adminRequest.getBankName());
//            bc.setPayAllianceCode(adminRequest.getPayAllianceCode());
            listBankConfig = bankConfigService.selectBankConfigListByPage(vo, paginator.getOffset(), paginator.getLimit());
            if(!CollectionUtils.isEmpty(listBankConfig)){
                List<BankConfigVO> banksConfigVOList =CommonUtils.convertBeanList(listBankConfig,BankConfigVO.class);
                response.setResultList(banksConfigVOList);
                response.setRecordTotal(banksConfigVOList.size());
                response.setRtn(Response.SUCCESS);
            }
            return response;
        }
        return null;
    }
    /**
     * 根据bankName查询银行配置
     * @param bankName
     * @return
     */
    @RequestMapping("/selectBankConfigByBankName")
    public List<BankConfigVO> selectBankConfigByBankName(@RequestBody String bankName){
        BankConfigVO bankConfig = new BankConfigVO();
        bankConfig.setName(bankName);
        List<BankConfig> list = bankConfigService.selectBankConfigByBankName(bankConfig,-1,-1);
        List<BankConfigVO> res=null;
        BeanUtils.copyProperties(list,res);
        return res;
    }

    /**
     * 添加银行配置
     * @param adminBankConfigRequest
     * @return
     */
    @RequestMapping("/insertBankConfig")
    public AdminBankConfigResponse insertBankConfig(@RequestBody AdminBankConfigRequest adminBankConfigRequest){
        AdminBankConfigResponse res =new AdminBankConfigResponse();
        int result = bankConfigService.insertBankConfig(adminBankConfigRequest);
        if(result >0){
            res.setRtn(AdminResponse.SUCCESS);
            res.setRtn(AdminResponse.SUCCESS_MSG);
            return res;
        }
        res.setRtn(AdminResponse.FAIL);
        res.setRtn(AdminResponse.FAIL_MSG);
        return res;
    }

    /**
     * 添加银行配置
     * @param adminBankConfigRequest
     * @return
     */
    @RequestMapping("/updadteBankConfig")
    public AdminBankConfigResponse updadteBankConfig(@RequestBody AdminBankConfigRequest adminBankConfigRequest){
        AdminBankConfigResponse res =new AdminBankConfigResponse();
        int result = bankConfigService.updadteBankConfig(adminBankConfigRequest);
        if(result >0){
            res.setRtn("SUCCESS");
            return res;
        }
        res.setRtn("FAIL");
        return res;
    }
    /**
     * 删除银行配置
     * @param id
     */
    @RequestMapping("/deleteBankConfigById")
    public AdminBankConfigResponse deleteBankConfigById(@RequestBody Integer id) {
        AdminBankConfigResponse res =new AdminBankConfigResponse();
        try{
            this.bankConfigService.deleteBankConfigById(id);
            res.setRtn("SUCCESS");
        }catch (Exception e){
            res.setRtn("FAIL");
        }
        return  res;
    }

    /**
     * 保存银行配置之前的字段校验
     * @param adminBankConfigRequest
     */
    @RequestMapping("/validateFeildBeforeSave")
    public AdminBankConfigResponse validateFeildBeforeSave(@RequestBody AdminBankConfigRequest adminBankConfigRequest) {
        AdminBankConfigResponse res =new AdminBankConfigResponse();
        BankConfigVO bankConfig = new BankConfigVO();
        BeanUtils.copyProperties(adminBankConfigRequest,bankConfig);
        List<BankConfig> list = this.bankConfigService.selectBankConfigByBankName(bankConfig,-1,-1);
        if(!CollectionUtils.isEmpty(list)){
            if (bankConfig.getId() != null) {
                Boolean hasnot = true;
                for (int i = 0; i < list.size(); i++) {
                    if (list.get(i).getId() == bankConfig.getId()) {
                        hasnot = false;
                        break;
                    }
                }
                if (hasnot) {
                    res.setRtn("FAIL");
                    res.setMessage("银行名称或银行代码不可重复添加");
                } else {
                    res.setRtn("SUCCESS");
                }
            } else {
                res.setRtn("FAIL");
                res.setMessage("银行名称或银行代码不可重复添加");
            }
        }
        return  res;
    }

    /**
     * 根据银行code获取银行配置
     * @auth sunpeikai
     * @param code 银行code,例如：招商银行,code是CMB
     * @return
     */
    @GetMapping(value = "/getBankConfigByCode/{code}")
    public BankConfigResponse getBankConfigByCode(@PathVariable String code){
        BankConfigResponse response = new BankConfigResponse();
        List<BankConfig> bankConfigList = bankConfigService.getBankConfigByCode(code);
        if(!CollectionUtils.isEmpty(bankConfigList)){
            BankConfigVO bankConfigVO = CommonUtils.convertBean(bankConfigList.get(0),BankConfigVO.class);
            response.setResult(bankConfigVO);
            response.setRtn(Response.SUCCESS);
        }
        return response;
    }
    /**
     * 根据bankId查找江西银行的银行卡配置表
     * @param bankId
     * @return
     */
    @RequestMapping("/getJXbankConfigByBankId")
    public JxBankConfigResponse getJXbankConfigByBankId(@PathVariable int bankId) {
        JxBankConfigResponse jxBankConfigResponse = new JxBankConfigResponse();
        JxBankConfig jxBankConfig = bankConfigService.getJxBankConfigByBankId(bankId);
        if(null!=jxBankConfig){
            JxBankConfigVO jxBankConfigVO = new JxBankConfigVO();
            BeanUtils.copyProperties(jxBankConfig,jxBankConfigVO);
            jxBankConfigResponse.setResult(jxBankConfigVO);
            jxBankConfigResponse.setRtn(Response.SUCCESS);
            jxBankConfigResponse.setMessage(Response.SUCCESS_MSG);
        }else{
            jxBankConfigResponse.setRtn(Response.FAIL);
            jxBankConfigResponse.setMessage(Response.ERROR_MSG);
        }
        return jxBankConfigResponse;
    }
}
