package com.hyjf.am.config.controller;

import com.hyjf.am.config.dao.model.auto.*;
import com.hyjf.am.config.dao.model.customize.NewAppQuestionCustomize;
import com.hyjf.am.config.dao.model.customize.QuestionCustomize;
import com.hyjf.am.config.service.BankConfigService;
import com.hyjf.am.config.service.JxBankConfigService;
import com.hyjf.am.config.service.QuestionService;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.admin.AdminBankConfigResponse;
import com.hyjf.am.response.admin.JxBankConfigResponse;
import com.hyjf.am.response.config.BankConfigResponse;
import com.hyjf.am.response.config.ParamNameResponse;
import com.hyjf.am.response.trade.BankReturnCodeConfigResponse;
import com.hyjf.am.response.user.NewAppQuestionCustomizeResponse;
import com.hyjf.am.response.user.QuestionCustomizeResponse;
import com.hyjf.am.resquest.admin.AdminBankConfigRequest;
import com.hyjf.am.resquest.user.AnswerRequest;
import com.hyjf.am.vo.config.NewAppQuestionCustomizeVO;
import com.hyjf.am.vo.config.ParamNameVO;
import com.hyjf.am.vo.trade.BankConfigVO;
import com.hyjf.am.vo.trade.BankReturnCodeConfigVO;
import com.hyjf.am.vo.trade.JxBankConfigVO;
import com.hyjf.am.vo.user.QuestionCustomizeVO;
import com.hyjf.common.paginator.Paginator;
import com.hyjf.common.util.CommonUtils;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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
    @Autowired
    private JxBankConfigService jxBankConfigService;

    /**
     * 获取银行卡配置信息
     * @param code
     * @return
     */
    @GetMapping("/selectBankConfigByCode/{code}")
    public BankConfigResponse selectBankConfigByCode(@PathVariable String  code){
        BankConfigResponse response = new BankConfigResponse();
        BankConfig bankConfig = bankConfigService.selectBankConfigByCode(code);
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

    @RequestMapping("/getNewAppQuestionList")
    public NewAppQuestionCustomizeResponse getNewAppQuestionList() {
        NewAppQuestionCustomizeResponse response = new NewAppQuestionCustomizeResponse();
        List<NewAppQuestionCustomize> questionCustomizes = questionService.getNewAppQuestionList();
        if(!CollectionUtils.isEmpty(questionCustomizes)){
            List<NewAppQuestionCustomizeVO> questionCustomizeVOS = CommonUtils.convertBeanList(questionCustomizes,NewAppQuestionCustomizeVO.class);
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
        BankConfigResponse response = new BankConfigResponse();
        List<BankConfig> listBankConfig = bankConfigService.selectBankConfigList();
        if(null!=listBankConfig&&listBankConfig.size()>0){
            List<BankConfigVO> listBanksConfig = CommonUtils.convertBeanList(listBankConfig, BankConfigVO.class);
            response.setResultList(listBanksConfig);
            //代表成功
            response.setRtn(Response.SUCCESS);
        }
        return response;
    }


    /**
     * 获取快捷支付银行
     */
    @RequestMapping("/getRechargeQuotaLimit")
    public BankConfigResponse getRechargeQuotaLimit(){
        BankConfigResponse response = new BankConfigResponse();
        List<JxBankConfig> listBankConfig = bankConfigService.getRechargeQuotaLimit(1);
        if(null!=listBankConfig&&listBankConfig.size()>0){
            List<BankConfigVO> listBanksConfig = CommonUtils.convertBeanList(listBankConfig, BankConfigVO.class);
            response.setResultList(listBanksConfig);
        }else{
            response.setResultList(new ArrayList<BankConfigVO>());
        }
        //代表成功
        response.setRtn(Response.SUCCESS);
        return response;
    }
    /**
     * 获取status=1的银行列表
     */
    @RequestMapping("/getBankConfigListByStatus")
    public AdminBankConfigResponse getBankConfigListByStatus(BankConfigVO bankConfigVO){
        AdminBankConfigResponse response=new AdminBankConfigResponse();
        List<BankConfig> listBankConfig = bankConfigService.getBankConfigListByStatus(bankConfigVO);
        if(!CollectionUtils.isEmpty(listBankConfig)){
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
    public BankConfigResponse getBankRecordListByQuickPayment(BankConfigVO bankConfigVO){
        BankConfigResponse response = new BankConfigResponse();
        List<BankConfigVO> listBanksConfig=null;
        List<BankConfig> listBankConfig = bankConfigService.getBankRecordListByQuickPayment(bankConfigVO);
        if(!CollectionUtils.isEmpty(listBankConfig)){
            listBanksConfig = CommonUtils.convertBeanList(listBankConfig, BankConfigVO.class);
            response.setResultList(listBanksConfig);
        }
        return response;
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

    @GetMapping("/getParamName/{other1}")
    public ParamNameResponse getParamName(@PathVariable String other1) {
        ParamNameResponse response = new ParamNameResponse();
        List<ParamName> paramNameList = bankConfigService.getParamName(other1);
        if (CollectionUtils.isNotEmpty(paramNameList)) {
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
    public AdminBankConfigResponse selectBankConfigListByPage(@RequestBody AdminBankConfigRequest adminRequest){
        AdminBankConfigResponse response=new AdminBankConfigResponse();

        BankConfigVO vo = new BankConfigVO();
        BankConfig co = new BankConfig();
        BeanUtils.copyProperties(co,vo);
        //查询保证金配置条数
        int total = bankConfigService.selectBankConfigCount(vo,-1,-1);
        if(total>0){
            Paginator paginator = new Paginator(adminRequest.getCurrPage(),total,adminRequest.getPageSize() == 0 ?10:adminRequest.getPageSize() );
            List<BankConfig> listBankConfig = bankConfigService.selectBankConfigListByPage(vo, paginator.getOffset(), paginator.getLimit());
            if(!CollectionUtils.isEmpty(listBankConfig)){
                List<BankConfigVO> banksConfigVOList =CommonUtils.convertBeanList(listBankConfig,BankConfigVO.class);
                response.setResultList(banksConfigVOList);
                response.setRecordTotal(total);
                response.setRtn(Response.SUCCESS);
            }
        }
        return response;
    }
    /**
     * 根据bankName查询银行配置
     * @param bankName
     * @return
     */
    @RequestMapping("/selectBankConfigByBankName/{bankName}")
    public AdminBankConfigResponse selectBankConfigByBankName(@PathVariable String bankName){
        AdminBankConfigResponse response = new AdminBankConfigResponse();
        BankConfigVO bankConfig = new BankConfigVO();
        bankConfig.setName(bankName);
        List<BankConfig> list = bankConfigService.selectBankConfigByBankName(bankConfig,-1,-1);
        if(!CollectionUtils.isEmpty(list)){
            List<BankConfigVO> res=CommonUtils.convertBeanList(list,BankConfigVO.class);
            response.setResultList(res);
            response.setRecordTotal(res.size());
        }
        return response;
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
            res.setRtn(Response.SUCCESS);
            return res;
        }
        res.setRtn(Response.FAIL);
        res.setMessage(Response.FAIL_MSG);
        return res;
    }

    /**
     * 修改银行配置
     * @param adminBankConfigRequest
     * @return
     */
    @RequestMapping("/updadteBankConfig")
    public AdminBankConfigResponse updadteBankConfig(@RequestBody AdminBankConfigRequest adminBankConfigRequest){
        AdminBankConfigResponse res =new AdminBankConfigResponse();
        int result = bankConfigService.updadteBankConfig(adminBankConfigRequest);
        if(result >0){
            res.setRtn(Response.SUCCESS);
            return res;
        }
        res.setRtn(Response.FAIL);
        res.setMessage(Response.FAIL_MSG);
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
            res.setRtn(Response.SUCCESS);
        }catch (Exception e){
            res.setRtn(Response.FAIL);
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
                    if (list.get(i).getId().equals(bankConfig.getId())) {
                        hasnot = false;
                        break;
                    }
                }
                if (hasnot) {
                    res.setRtn(Response.FAIL);
                    res.setMessage("银行名称或银行代码不可重复添加");
                } else {
                    res.setRtn(Response.SUCCESS);
                }
            } else {
                res.setRtn(Response.FAIL);
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
     * 获取银行卡配置信息
     * @auth sunpeikai
     * @param bankId 主键id
     * @return
     */
    @GetMapping("/getBankConfigByBankId/{bankId}")
    public BankConfigResponse getBankConfigByBankId(@PathVariable Integer bankId){
        BankConfigResponse response = new BankConfigResponse();
        BankConfig bankConfig = bankConfigService.getBankConfigByBankId(bankId);
        if(null != bankConfig){
            BankConfigVO bankConfigVO = CommonUtils.convertBean(bankConfig,BankConfigVO.class);
            response.setResult(bankConfigVO);
            response.setRtn(Response.SUCCESS);
        }
        return response;
    }

    /**
     *获取银行列表（快捷卡）
     * @return
     */
    @GetMapping("/getbanklist")
    public JxBankConfigResponse getInviteList() {
        JxBankConfigResponse jxBankConfigResponse = new JxBankConfigResponse();
        String methodName = "BanksListServer";
        List<JxBankConfig> bankList = bankConfigService.getBankRecordList(1);
        if(null != bankList){
            List<JxBankConfigVO> jxBankConfigVOS = CommonUtils.convertBeanList(bankList, JxBankConfigVO.class);
            jxBankConfigResponse.setResultList(jxBankConfigVOS);
            jxBankConfigResponse.setRtn(Response.SUCCESS);
        }
        return jxBankConfigResponse;
    }

    @GetMapping("/getJxBankConfigByBankName/{bankName}")
    public JxBankConfigResponse getJxBankConfigByBankName(@PathVariable String bankName){
        JxBankConfigResponse response = new JxBankConfigResponse();
        JxBankConfig bankConfig = bankConfigService.getBankConfigByBankName(bankName);
        if(null != bankConfig){
            JxBankConfigVO jxBankConfigVO = new JxBankConfigVO();
            BeanUtils.copyProperties(bankConfig,jxBankConfigVO);
            response.setResult(jxBankConfigVO);
            response.setRtn(Response.SUCCESS);
        }
        return response;
    }
    @GetMapping("/selectBankConfigByName/{bankName}")
    public JxBankConfigResponse selectBankConfigByName(@PathVariable String bankName) {
        JxBankConfigResponse response = new JxBankConfigResponse();
        response.setRtn(Response.FAIL);
        logger.info("所属银行为:[" + bankName + "] 模糊查询银行卡配置信息");
        List<JxBankConfig> jxBankConfig = jxBankConfigService.selectBankConfigByName(bankName);

        if(null!=jxBankConfig){
            List<JxBankConfigVO> jxBankConfigVO = CommonUtils.convertBeanList(jxBankConfig,JxBankConfigVO.class);
            BeanUtils.copyProperties(jxBankConfig,jxBankConfigVO);
            response.setRtn(Response.SUCCESS);
            response.setResultList(jxBankConfigVO);
        }
        return response;
    }


}
