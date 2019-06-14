package com.hyjf.am.user.controller.admin.anrong;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.admin.utils.AnRongDefine;
import com.hyjf.am.admin.utils.ParamUtil;
import com.hyjf.am.response.AdminResponse;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.user.MspApplytResponse;
import com.hyjf.am.resquest.user.MspApplytRequest;
import com.hyjf.am.user.dao.model.auto.*;
import com.hyjf.am.user.service.admin.anrong.MspApplyService;
import com.hyjf.am.vo.user.*;
import com.hyjf.common.paginator.Paginator;
import com.hyjf.common.util.CommonUtils;
import com.hyjf.common.util.GetDate;
import com.hyjf.pay.lib.anrong.bean.AnRongBean;
import com.hyjf.pay.lib.anrong.util.AnRongCallUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.hyjf.am.response.Response.FAIL;

/**
 * 
 * 
 * @author
 *
 */
@RestController
@RequestMapping("/am-user/mspapply")
public class MspApplyController  {

	private final Logger log = LoggerFactory.getLogger(getClass());
    
	@Autowired
	private MspApplyService mspApplyService;

	/**
	 * 列表维护画面初始化
	 * 
	 * @param
	 * @param form
	 * @return
	 */
	@RequestMapping("/init")
	public  MspApplytResponse init(@RequestBody  MspApplytRequest form) {
		MspApplytResponse mr=new MspApplytResponse();
		MspApply ma=new MspApply();
		int start=0;
		int end=0;
		ma.setName(form.getName());

		ma.setIdentityCard(form.getIdentityCard());

		ma.setCreateUser(form.getCreateUser());
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		if(form.getStartCreate() != null) {
	        Date date;
			try {
				date = simpleDateFormat.parse(form.getStartCreate());
				
				start = (int) (date.getTime()/1000);
			} catch (ParseException e) {
				log.info("安融返回日期格式化异常："+e.getMessage());
			}
	        
		}
		if(form.getEndCreate() != null) {
			Date date;
			try {
				date = simpleDateFormat.parse(form.getEndCreate());
				Calendar cal = Calendar.getInstance();
				cal.setTime(date);
				cal.add(Calendar.DATE, 1);
				
				end = (int) ((cal.getTime()).getTime()/1000);
			} catch (ParseException e) {
				log.info("安融返回日期格式化异常："+e.getMessage());
			}
			
		}
		int count = this.mspApplyService.countByExample(ma,start,end);
		mr.setRecordTotal(count);
		if (count >0) {
			Paginator paginator = new Paginator(form.getCurrPage(), count,form.getPageSize());
			List<MspApply> recordList  = this.mspApplyService.getRecordList(ma, paginator.getOffset(),
					paginator.getLimit(),start,end);
			List<MspApplyVO> adminVo= CommonUtils.convertBeanList(recordList,MspApplyVO.class);
			mr.setResultList(adminVo);
		}
		return mr;
	}



	/**
	 * 画面迁移(含有id更新，不含有id添加)
	 * 
	 * @param
	 * @param
	 * @return
	 */
	@RequestMapping("/infoAction")	
	public  MspApplytResponse infoAction() {	
		MspApplytResponse mr=new MspApplytResponse();
	    mr.setRegionList(CommonUtils.convertBeanList(this.mspApplyService.getRegionList(),MspRegionVO.class));
		mr.setConfigureList(CommonUtils.convertBeanList(this.mspApplyService.getConfigureList(),MspConfigureVO.class));
		return mr;
	}

	/**
	 * 添加活动信息
	 * 
	 * @param
	 * @param form
	 * @return
	 */
	@RequestMapping("/insertAction")	
	public MspApplytResponse insertAction(@RequestBody  MspApplytRequest form) {
		MspApplytResponse result = new MspApplytResponse();
		// 调用api 请求安融 查询方法
		String postResult =queryUser(ParamUtil.getQueryUserParm(form));
//		postResult = postResult.substring(1, postResult.length()-1);
//		postResult = postResult.replaceAll("\\\\", "");
		JSONObject postResultJson = JSONObject.parseObject(postResult);
		
		if(!validatorApiResult(postResultJson)){
		    // 失败返回
			result.setRtn(FAIL);
            result.setMessage( postResultJson.get(AnRongDefine.RESULT_JSON_KEY_MSP_MESS)+"-"+postResultJson.get(AnRongDefine.RESULT_JSON_KEY_FQZ_MESS));
            return result;
		}
		    
		form.setApplyId(postResultJson.getString(AnRongDefine.RESULT_JSON_KEY_REQID));
		form.setCreateTime(GetDate.getNowTime10());
		// 数据插入
		
		MspApply mspapply=new MspApply();
		BeanUtils.copyProperties(form,mspapply);
		mspapply.setCreateUser(form.getAdmin());
		this.mspApplyService.insertRecord(mspapply);
		result.setRtn(AdminResponse.SUCCESS);
		return result;
	}

	private boolean validatorApiResult(JSONObject postResultJson) {
	    if(postResultJson.containsKey(AnRongDefine.RESULT_JSON_KEY_SUCCESS) && postResultJson.getBooleanValue(AnRongDefine.RESULT_JSON_KEY_SUCCESS)){
	        // 成功
	        return true;
	    }
        return false;
    }

    /**
	 * 修改活动维护信息
	 * 
	 * @param
	 * @param form
	 * @return
	 */
	@RequestMapping("/updateAction")
	public MspApplytResponse updateAction(@RequestBody  MspApplytRequest form) {
		MspApplytResponse result = new MspApplytResponse();
		
		MspApply mspapply=new MspApply();
		BeanUtils.copyProperties(form,mspapply);
		mspapply.setUpdateUser(form.getAdmin());
		// 更新
		this.mspApplyService.updateRecord(mspapply);
		result.setRtn(Response.SUCCESS);
		return result;
	}

	/**
	 * 删除配置信息
	 * 
	 * @param
	 * @param form
	 * @return
	 */
	@RequestMapping("/deleteAction")
	public MspApplytResponse deleteRecordAction(@RequestBody  MspApplytRequest form) {

		List<Integer> recordList = JSONArray.parseArray(form.getIds(), Integer.class);
		this.mspApplyService.deleteRecord(recordList);
		MspApplytResponse result = new MspApplytResponse();
		result.setRtn(Response.SUCCESS);
		return result;
	}

	

	@RequestMapping("/validateBeforeAction")
	public MspApplytResponse validateBeforeAction(@RequestBody  MspApply form) {
		MspApplytResponse result = new MspApplytResponse();
		List<MspApply> list = mspApplyService.getRecordList(form, -1, -1,0,0);
		if (list != null && list.size() != 0) {
			if (form.getId() != null) {
				Boolean hasnot = true;
				for (int i = 0; i < list.size(); i++) {
					if (list.get(i).getId().equals(form.getId())) {
						hasnot = false;
						break;
					}
				}
				if (hasnot) {
					result.setRtn(FAIL);
					result.setMessage ("银行名称或银行代码不可重复添加");
				} else {
					result.setRtn(Response.SUCCESS);
				}
			} else {
				result.setRtn(FAIL);
				result.setMessage ("银行名称或银行代码不可重复添加");
			}
		} else {
			result.setRtn(Response.SUCCESS);
		}
		return result;
	}

	/**
	 * 画面迁移(含有id更新，不含有id添加)
	 * 
	 * @param
	 * @param form
	 * @return
	 */
	@RequestMapping("/applyAction")
	public MspApplytResponse applyInfo(@RequestBody  MspApplytRequest form) {
		
		MspApply ma=this.mspApplyService.getRecord( Integer.valueOf(form.getIds()));
		
		
		if(ma.getShareIdentification()==0) {
			 MspConfigure mc=new MspConfigure();
			 ma.setApplyDate( ma.getApplyDate());
			 ma.setContractBegin( ma.getApplyDate());
			 ma.setApprovalDate(ma.getApplyDate());
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");  
			Calendar   calendar   =   new   GregorianCalendar();
			try {
				calendar.setTime(formatter.parse(ma.getApplyDate()));
			} catch (ParseException e) {

			}
			 
			if(ma.getConfigureId() != null) {
				mc=this.mspApplyService.getConfigure(ma.getConfigureId());
				ma.setServiceType(mc.getServiceType());
				ma.setUnredeemedMoney(mc.getUnredeemedMoney());
				ma.setRepaymentStatus(mc.getRepaymentStatus());
				ma.setApprovalResult(mc.getApprovalResult());
				ma.setGuaranteeType(mc.getGuaranteeType());
				ma.setCreditAddress(mc.getCreditAddress());
				calendar.add(Calendar.MONTH, mc.getLoanTimeLimit());
			}else {
				calendar.add(Calendar.MONTH,ma.getLoanTimeLimit());
			}
				
			 ma.setContractEnd( formatter.format(calendar.getTime()));
		}
		MspApplytResponse result = new MspApplytResponse();
		MspApplyVO mspapply=new MspApplyVO();
		BeanUtils.copyProperties(ma,mspapply);
		result.setResult(mspapply);
		
		result.setRegionList(CommonUtils.convertBeanList(this.mspApplyService.getRegionList(),MspRegionVO.class));
		
		return result;
	}

	// 安融共享
	@RequestMapping("/shareUser")
    public MspApplytResponse shareUser(@RequestBody  MspApplytRequest form) {
		MspApplytResponse result = new MspApplytResponse();
        // 准备参数
        if(!"01".equals(form.getApprovalResult())) {
        	
        	form.setUnredeemedMoney(null);
        	form.setRepaymentStatus(null);

        }
        AnRongBean params = ParamUtil.getSendParm(form);
        log.info("要请求的参数：params"+params.toString());
        // 调用api 请求安融共享方法
        String postResult = send(params);
        JSONObject postResultJson = JSONObject.parseObject(postResult);
        
        if(!validatorApiResult(postResultJson)){
            // 失败返回
        	result.setRtn(FAIL);
            result.setMessage( (String)postResultJson.get("msg"));
            return result;
        }
            
        form.setUpdateTime(GetDate.getNowTime10());
        // 数据修改
		MspApply mspapply=new MspApply();
		BeanUtils.copyProperties(form,mspapply);
		mspapply.setUpdateUser(form.getAdmin());
        this.mspApplyService.updateRecord(mspapply);
        result.setRtn(Response.SUCCESS);
        return result;
    }
	
	
	/**
	 * 列表维护画面初始化
	 * 
	 * @param
	 * @param form
	 * @return
	 */
	@RequestMapping("/downloadFile")
	public MspApplytResponse download(@RequestBody  MspApplytRequest form) {
		String applyId=form.getApplyId();
		MspApplytResponse mar=new MspApplytResponse();
		if(form.getApplyId()!=null||!"".equals(form.getApplyId())) {
			MspApply ma=this.mspApplyService.getRecord( Integer.valueOf(form.getIds()));
			MspApplyVO mspapply=new MspApplyVO();
			BeanUtils.copyProperties(ma,mspapply);
			mar.setResult(mspapply);
			MspFqzVO fqz =new MspFqzVO();
			BeanUtils.copyProperties(this.mspApplyService.getFqz(applyId),fqz);
			mar.setMspFqz(fqz);
			mar.setMspShixinInfosList(CommonUtils.convertBeanList( this.mspApplyService.getShixinInfos(applyId),MspShixininfosVO.class));
			mar.setMspZhixingInfosList(CommonUtils.convertBeanList( this.mspApplyService.getZhixingInfos(applyId),MspZhixinginfosVO.class));
			mar.setMspAnliInfosList(CommonUtils.convertBeanList( this.mspApplyService.getAnliInfos(applyId),MspAnliinfosVO.class));
			
			
			List<MspApplydetails> applylist = this.mspApplyService.getApplyDetails(applyId);
			BigDecimal tongguo= new BigDecimal(0);
			BigDecimal jujue= new BigDecimal(0);
			BigDecimal  zongshu= new BigDecimal(0);
			BigDecimal  quxiao= new BigDecimal(0);
			int quxiaoshu=0;
			for (MspApplydetails mspApplyDetails : applylist) {
				if(mspApplyDetails.getApplyresult()!=null&& "01".equals(mspApplyDetails.getApplyresult())) {
					tongguo=tongguo.add(new BigDecimal(mspApplyDetails.getLoanmoney()));
					
				}
				if(mspApplyDetails.getApplyresult()!=null&& "02".equals(mspApplyDetails.getApplyresult())) {
					jujue=jujue.add(new BigDecimal(mspApplyDetails.getLoanmoney()));
				}
				if(mspApplyDetails.getApplyresult()!=null&& "05".equals(mspApplyDetails.getApplyresult())) {
					quxiao=quxiao.add(new BigDecimal(mspApplyDetails.getLoanmoney()));
					quxiaoshu=quxiaoshu+1;
				}
				zongshu=zongshu.add(new BigDecimal(mspApplyDetails.getLoanmoney()));
				
			}
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");  
	          
	        Calendar   calendar   =   new   GregorianCalendar(); 
	        calendar.setTime(new Date()); 
	        calendar.add(Calendar.DAY_OF_MONTH, -3);
	        Date y3 = calendar.getTime();
	        calendar.add(Calendar.DAY_OF_MONTH, -3);
	        Date y6 = calendar.getTime();
		        
			
	        List<MspQuerydetail> queryList = this.mspApplyService.getQueryDetail(applyId);
	        
	        int sangeyue=0;
	        int liugeyue=0;
	        for (Iterator iterator = queryList.iterator(); iterator.hasNext();) {
	        	MspQuerydetail mspQueryDetail = (MspQuerydetail) iterator.next();
				try {
					if(y3.before(sdf.parse(mspQueryDetail.getQuerydate()))) {
						sangeyue=sangeyue+1;
					}
					if(y6.before(sdf.parse(mspQueryDetail.getQuerydate()))) {
						liugeyue=liugeyue+1;
					}
				} catch (ParseException e) {
				    log.info("安融异常："+e.getMessage());
				}
				
			}
	        mar.setSangeyue(sangeyue);
	        mar.setLiugeyue(liugeyue);
	        mar.setZongji(queryList.size());
	        mar.setTongguo(tongguo);
	        mar.setJujue(jujue);
	        mar.setZongshu(zongshu);
	        mar.setQuxiao(quxiao);
			MspTitleVO mtv=new MspTitleVO();
			BeanUtils.copyProperties(this.mspApplyService.getTitle(applyId),mtv);
	        mar.setMspTitle(mtv);
	        mar.setMspNormalCreditDetailList(CommonUtils.convertBeanList( this.mspApplyService.getNormalCreditDetail(applyId),MspNormalcreditdetailVO.class));
	        mar.setMspApplydetailsList(CommonUtils.convertBeanList(applylist,MspApplydetailsVO.class));
	        mar.setMspQuerydetailList(CommonUtils.convertBeanList(queryList,MspQuerydetailVO.class));
			mar.setMspBlackDataList(CommonUtils.convertBeanList(this.mspApplyService.getBlackData(applyId),MspBlackdataVO.class));
			
			List<MspAbnormalcredit> mbl=this.mspApplyService.getAbnormalCredit( applyId);
			List<MspAbnormalBeanVO> mabl=new ArrayList<MspAbnormalBeanVO>();
			for (Iterator<MspAbnormalcredit> iterator = mbl.iterator(); iterator.hasNext();) {
				MspAbnormalcredit mspAbnormalCredit = iterator.next();
				List<MspAbnormalcreditdetail> mcl=this.mspApplyService.getAbnormalCreditDetail( mspAbnormalCredit.getId());
					for (Iterator<MspAbnormalcreditdetail> iterator2 = mcl.iterator(); iterator2.hasNext();) {
						MspAbnormalcreditdetail mspAbnormalCreditDetail = (MspAbnormalcreditdetail) iterator2.next();	
						MspAbnormalBeanVO mb=new MspAbnormalBeanVO();
						mb.setCreditstartdate(mspAbnormalCredit.getCreditstartdate());
						mb.setCreditenddate(mspAbnormalCredit.getCreditenddate());
						mb.setLoantype(mspAbnormalCredit.getLoantype());
						mb.setLoanmoney(mspAbnormalCredit.getLoanmoney());
						mb.setAssuretype(mspAbnormalCredit.getAssuretype());
						mb.setLoanperiods(mspAbnormalCredit.getLoanperiods());
						mb.setNum(mspAbnormalCredit.getNum());
						mb.setCheckoverduedate(mspAbnormalCreditDetail.getCheckoverduedate());
						mb.setOverduedays(mspAbnormalCreditDetail.getOverduedays());
						mb.setOverduereason(mspAbnormalCreditDetail.getOverduereason());
						mb.setOverduestate(mspAbnormalCreditDetail.getOverduestate());
						mb.setOpertime(mspAbnormalCreditDetail.getOpertime());
						mb.setRemark(mspAbnormalCreditDetail.getRemark());
						mabl.add(mb);
					}
			}
			mar.setMspAbnormalBeanList(mabl);
		}

		return mar;
	}

	
    public String queryUser(AnRongBean anRongBean){
    	log.info("请求查询接口参数："+JSONObject.toJSON(anRongBean));
        JSONObject ret = new JSONObject();
        anRongBean.setTxCode(AnRongDefine.TXCODE_QUERYUSER);
        anRongBean.setSystemParm(anRongBean.getLogUserId());
        // 调用安融接口
        String result = AnRongCallUtils.callApiBg(anRongBean);
        log.info("调用安融接口返回结果："+result);
        JSONObject resultJson = JSONObject.parseObject(result);
        JSONObject isOk = getResult(ret, resultJson,anRongBean.getLoanId());
        if(isOk.getBooleanValue(AnRongDefine.RESULT_JSON_KEY_FQZ_SUCCESS)||isOk.getBooleanValue(AnRongDefine.RESULT_JSON_KEY_MSP_SUCCESS)){
            // 插入数据
        	this.mspApplyService.insertResult(resultJson, anRongBean.getLoanId());
           }
		
      
        return isOk.toString();
    }
    

    public String send(AnRongBean anRongBean){
       
        log.info("请求共享接口参数："+JSONObject.toJSON(anRongBean));
        JSONObject ret = new JSONObject();
        anRongBean.setSystemParm(anRongBean.getLoanId(),anRongBean.getLogUserId());
        anRongBean.setTxCode(AnRongDefine.TXCODE_SENDMESS);
        // 调用安融接口
        String result = AnRongCallUtils.callApiBg(anRongBean);
        log.info("调用安融接口返回结果："+result);
        JSONObject resultJson = JSONObject.parseObject(result);
        
        return getResultForSend(ret, resultJson).toString();
    }

    // 返回客户端信息   共享接口
    private Object getResultForSend(JSONObject ret, JSONObject resultJson) {
        ret.put(AnRongDefine.RESULT_JSON_KEY_SUCCESS, true);
        // 有错误
        if (resultJson.containsKey(AnRongDefine.RESULT_KEY_ERRORS)) {
            ret.put(AnRongDefine.RESULT_JSON_KEY_SUCCESS, false);
            StringBuffer errorMess = new StringBuffer("错误信息：<br/>");
            JSONArray errors = resultJson.getJSONArray(AnRongDefine.RESULT_KEY_ERRORS);
            for (Object object : errors) {
                JSONObject obj = (JSONObject) object;
                errorMess.append(obj.get(AnRongDefine.RESULT_KEY_MSG)).append("<br/>");
            }
            ret.put(AnRongDefine.RESULT_JSON_KEY_MSG, errorMess.toString());
        }
        return ret;
    }

    // 获得返回客户端的处理结果   查询接口
    private JSONObject getResult(JSONObject ret, JSONObject resultJson,String reqId) {
        boolean fqz_success = true;
        String fqz_mess = "请求成功";
        boolean msp_success = true;
        String msp_mess = "请求成功";
        // fqz 有错误
        if(resultJson.getJSONObject(AnRongDefine.RESULT_KEY_FQZ).containsKey(AnRongDefine.RESULT_KEY_ERRORCODE)){
            // 如果有错误
            fqz_success = false;
            String errs = resultJson.getJSONObject(AnRongDefine.RESULT_KEY_FQZ).getString(AnRongDefine.RESULT_KEY_ERRORCODE);
            fqz_mess = "请求失败，返回值:"+errs;
        }
        if(resultJson.getJSONObject(AnRongDefine.RESULT_KEY_MSP).containsKey(AnRongDefine.RESULT_KEY_ERRORS)){
            // 如果有错误
            msp_success = false;
            msp_mess = "请求失败，返回值:";
            JSONArray errs = resultJson.getJSONObject(AnRongDefine.RESULT_KEY_MSP).getJSONArray(AnRongDefine.RESULT_KEY_ERRORS);
            for (Object object : errs) {
                JSONObject aError = (JSONObject) object;
                msp_mess += aError.getString(AnRongDefine.RESULT_KEY_MSG) +" <br/> ";
            }
        }
        if(fqz_success&&msp_success){
            ret.put(AnRongDefine.RESULT_JSON_KEY_SUCCESS, true);
        }else{
            ret.put(AnRongDefine.RESULT_JSON_KEY_SUCCESS, false);
        }
        ret.put(AnRongDefine.RESULT_JSON_KEY_FQZ_SUCCESS, fqz_success);
        ret.put(AnRongDefine.RESULT_JSON_KEY_FQZ_MESS, fqz_mess);
        ret.put(AnRongDefine.RESULT_JSON_KEY_MSP_SUCCESS, msp_success);
        ret.put(AnRongDefine.RESULT_JSON_KEY_MSP_MESS, msp_mess);
        ret.put(AnRongDefine.RESULT_JSON_KEY_REQID, reqId);
        return ret;
    }
	
}
