package com.hyjf.am.user.controller;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.user.MspApplytResponse;
import com.hyjf.am.resquest.user.AdminPreRegistListRequest;
import com.hyjf.am.resquest.user.MspApplytRequest;
import com.hyjf.am.user.dao.model.auto.MspAbnormalcredit;
import com.hyjf.am.user.dao.model.auto.MspAbnormalcreditdetail;
import com.hyjf.am.user.dao.model.auto.MspApply;
import com.hyjf.am.user.dao.model.auto.MspApplydetails;
import com.hyjf.am.user.dao.model.auto.MspConfigure;
import com.hyjf.am.user.dao.model.auto.MspQuerydetail;
import com.hyjf.am.user.service.MspApplyService;
import com.hyjf.am.user.utils.AnRongDefine;
import com.hyjf.am.user.utils.ParamUtil;
import com.hyjf.am.vo.config.AdminSystemVO;
import com.hyjf.am.vo.user.MspAbnormalBeanVO;
import com.hyjf.am.vo.user.MspAnliinfosVO;
import com.hyjf.am.vo.user.MspApplyVO;
import com.hyjf.am.vo.user.MspApplydetailsVO;
import com.hyjf.am.vo.user.MspBlackdataVO;
import com.hyjf.am.vo.user.MspConfigureVO;
import com.hyjf.am.vo.user.MspFqzVO;
import com.hyjf.am.vo.user.MspNormalcreditdetailVO;
import com.hyjf.am.vo.user.MspQuerydetailVO;
import com.hyjf.am.vo.user.MspRegionVO;
import com.hyjf.am.vo.user.MspShixininfosVO;
import com.hyjf.am.vo.user.MspTitleVO;
import com.hyjf.am.vo.user.MspZhixinginfosVO;
import com.hyjf.common.file.UploadFileUtils;
import com.hyjf.common.http.HttpClientUtils;
import com.hyjf.common.paginator.Paginator;
import com.hyjf.common.util.CommonUtils;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.util.GetDate;
import com.hyjf.common.util.StringPool;
import com.hyjf.common.validator.Validator;
import com.hyjf.pay.lib.anrong.bean.AnRongBean;
import com.hyjf.pay.lib.anrong.util.AnRongCallUtils;

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
	 * @param request
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
		List<MspApply> recordList = this.mspApplyService.getRecordList(ma, -1, -1,start,end);
		mr.setRecordTotal(recordList.size());
		if (recordList != null) {
			Paginator paginator = new Paginator(form.getCurrPage(), recordList.size());
			recordList = this.mspApplyService.getRecordList(ma, paginator.getOffset(),
					paginator.getLimit(),start,end);
			List<MspApplyVO> adminVo= CommonUtils.convertBeanList(recordList,MspApplyVO.class);
			mr.setResultList(adminVo);
		}
		return mr;
	}



	/**
	 * 画面迁移(含有id更新，不含有id添加)
	 * 
	 * @param request
	 * @param form
	 * @return
	 */
	@RequestMapping("/infoAction")	
	public  MspApplytResponse infoAction(@RequestBody  MspApplytRequest form) {	
		MspApplytResponse mr=new MspApplytResponse();
		 Date currentTime = new Date();  
	    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");  
	    form.setApplyDate(formatter.format(currentTime));
		
	    mr.setRegionList(CommonUtils.convertBeanList(this.mspApplyService.getRegionList(),MspRegionVO.class));
		mr.setConfigureList(CommonUtils.convertBeanList(this.mspApplyService.getConfigureList(),MspConfigureVO.class));
		return mr;
	}

	/**
	 * 添加活动信息
	 * 
	 * @param request
	 * @param form
	 * @return
	 */
	@RequestMapping("/insertAction")	
	public MspApplytResponse insertAction(@RequestBody  MspApplytRequest form) {
		MspApplytResponse result = new MspApplytResponse();
		// 调用api 请求安融 查询方法
		String postResult =queryUser(ParamUtil.getQueryUserParm(form));
		postResult = postResult.substring(1, postResult.length()-1);
		postResult = postResult.replaceAll("\\\\", "");
		JSONObject postResultJson = JSONObject.parseObject(postResult);
		
		if(!validatorApiResult(postResultJson)){
		    // 失败返回
			result.setRtn(result.FAIL);
            result.setMessage( postResultJson.get(AnRongDefine.RESULT_JSON_KEY_MSP_MESS)+"<br/>"+postResultJson.get(AnRongDefine.RESULT_JSON_KEY_FQZ_MESS));
            return result;
		}
		    
		form.setApplyId(postResultJson.getString(AnRongDefine.RESULT_JSON_KEY_REQID));
		//form.setApplyId("kkkkkkk");
		form.setCreateTime(GetDate.getNowTime10());
		// 数据插入
		
		MspApply mspapply=new MspApply();
		BeanUtils.copyProperties(form,mspapply);
		this.mspApplyService.insertRecord(mspapply);
		result.setRtn(result.SUCCESS);
		return result;
	}

	private boolean validatorApiResult(JSONObject postResultJson) {
	    if(postResultJson.containsKey(AnRongDefine.SUCCESS) && postResultJson.getBooleanValue(AnRongDefine.SUCCESS)){
	        // 成功
	        return true;
	    }
        return false;
    }

    /**
	 * 修改活动维护信息
	 * 
	 * @param request
	 * @param form
	 * @return
	 */
	@RequestMapping("/updateAction")
	public MspApplytResponse updateAction(@RequestBody  MspApplytRequest form) {
		MspApplytResponse result = new MspApplytResponse();
		
		MspApply mspapply=new MspApply();
		BeanUtils.copyProperties(form,mspapply);
		// 更新
		this.mspApplyService.updateRecord(mspapply);
		result.setRtn(Response.SUCCESS);
		return result;
	}

	/**
	 * 删除配置信息
	 * 
	 * @param request
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
	public MspApplytResponse validateBeforeAction(@RequestBody  MspApplytRequest form) {
		MspApplytResponse result = new MspApplytResponse();
		MspApply mspapply=new MspApply();
		BeanUtils.copyProperties(form,mspapply);
		List<MspApply> list = mspApplyService.getRecordList(mspapply, -1, -1,0,0);
		if (list != null && list.size() != 0) {
			if (form.getId() != null) {
				Boolean hasnot = true;
				for (int i = 0; i < list.size(); i++) {
					if (list.get(i).getId() == form.getId()) {
						hasnot = false;
						break;
					}
				}
				if (hasnot) {
					result.setRtn(Response.FAIL);
					result.setMessage ("银行名称或银行代码不可重复添加");
				} else {
					result.setRtn(Response.SUCCESS);
				}
			} else {
				result.setRtn(Response.FAIL);
				result.setMessage ("银行名称或银行代码不可重复添加");
			}
		} else {
			result.setRtn(Response.SUCCESS);
		}
		return result;
	}
//	/**
//	 * 导出功能
//	 * 
//	 * @param request
//	 * @param modelAndView
//	 * @param form
//	 */
//	@RequestMapping(MspApplyDefine.EXPORT_ACTION)
//	@RequiresPermissions(MspApplyDefine.PERMISSIONS_EXPORT)
//	public void exportAction(HttpServletRequest request, HttpServletResponse response, @ModelAttribute(MspApplyDefine.CONFIGBANK_FORM) MspApplyBean form) throws Exception {
//		LogUtil.startLog(MspApplyDefine.class.toString(), MspApplyDefine.EXPORT_ACTION);
//		
//		
//		
//		MspApply ma=new MspApply();
//		int start=0;
//		int end=0;
//		ma.setName(form.getName());
//
//		ma.setIdentityCard(form.getIdentityCard());
//
//		ma.setCreateUser(form.getCreateUser());
//		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
//		if(form.getStartCreate() != null) {
//	        Date date;
//			try {
//				date = simpleDateFormat.parse(form.getStartCreate());
//				start = (int) (date.getTime()/1000);
//			} catch (ParseException e) {
//			    _log.info("安融返回日期格式化异常："+e.getMessage());
//			}
//	        
//		}
//		if(form.getEndCreate() != null) {
//			Date date;
//			try {
//				date = simpleDateFormat.parse(form.getEndCreate());
//				Calendar cal = Calendar.getInstance();
//				cal.setTime(date);
//				cal.add(Calendar.DATE, 1);
//				
//				end = (int) ((cal.getTime()).getTime()/1000);
//			} catch (ParseException e) {
//			    _log.info("安融返回日期格式化异常："+e.getMessage());
//			}
//			
//		}
//		List<MspApply> recordList = this.mspApplyService.getRecordList(ma, -1, -1,start,end);
//		
//		
//		
//		// 表格sheet名称
//		String sheetName = "安融反欺诈查询";
//
//
//		String fileName = sheetName + StringPool.UNDERLINE + GetDate.getServerDateTime(8, new Date()) + CustomConstants.EXCEL_EXT;
//		String[] titles = new String[] {"序号", "姓名", "身份证号","操作人","查询时间" };
//		// 声明一个工作薄
//		HSSFWorkbook workbook = new HSSFWorkbook();
//		
//		// 生成一个表格
//		HSSFSheet sheet = ExportExcel.createHSSFWorkbookTitle(workbook, titles, sheetName + "_第1页");
//		SimpleDateFormat format =  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
//		if (recordList != null && recordList.size() > 0) {
//
//			int sheetCount = 1;
//			int rowNum = 0;
//
//			for (int i = 0; i < recordList.size(); i++) {
//				rowNum++;
//				if (i != 0 && i % 60000 == 0) {
//					sheetCount++;
//					sheet = ExportExcel.createHSSFWorkbookTitle(workbook, titles, (sheetName + "_第" + sheetCount + "页"));
//					rowNum = 1;
//				}
//
//				// 新建一行
//				Row row = sheet.createRow(rowNum);
//				// 循环数据
//				for (int celLength = 0; celLength < titles.length; celLength++) {
//					MspApply pInfo = recordList.get(i);
//
//					// 创建相应的单元格
//					Cell cell = row.createCell(celLength);
//
//					// 序号
//					if (celLength == 0) {
//						cell.setCellValue(i + 1);
//					}
//					else if (celLength == 1) {
//						cell.setCellValue(pInfo.getName());
//					}
//					else if (celLength == 2) {
//						cell.setCellValue(pInfo.getIdentityCard());
//					}
//					else if (celLength == 3) {
//						cell.setCellValue(pInfo.getCreateUser());
//					}
//					else if (celLength == 4) {
//					    Long time1=new Long(pInfo.getCreateTime());
//					    String d = format.format(time1*1000);  
//						cell.setCellValue(d);
//					}
//
//				}
//			}
//		}
//		// 导出
//		ExportExcel.writeExcelFile(response, workbook, titles, fileName);
//
//		LogUtil.endLog(MspApplyController.class.toString(), MspApplyDefine.EXPORT_ACTION);
//	}
//	
	/**
	 * 画面迁移(含有id更新，不含有id添加)
	 * 
	 * @param request
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
        if(!form.getApprovalResult().equals("01")) {
        	
        	form.setUnredeemedMoney(null);
        	form.setRepaymentStatus(null);
//        	form.setContractBegin(null);
//        	form.setContractEnd(null);

        }
        Map<String, String> params = ParamUtil.getSendParm(form);
        log.info("要请求的参数：params"+params);
        // 调用api 请求安融共享方法
        String postResult = send(ParamUtil.getQueryUserParm(form));
        postResult = postResult.substring(1, postResult.length()-1);
        postResult = postResult.replaceAll("\\\\", "");
        JSONObject postResultJson = JSONObject.parseObject(postResult);
        
        if(!validatorApiResult(postResultJson)){
            // 失败返回
        	result.setRtn(Response.FAIL);
            result.setMessage( (String)postResultJson.get("msg"));
            return result;
        }
            
        form.setUpdateTime(GetDate.getNowTime10());
        // 数据修改
		MspApply mspapply=new MspApply();
		BeanUtils.copyProperties(form,mspapply);
        this.mspApplyService.updateRecord(mspapply);
        result.setRtn(Response.SUCCESS);
        return result;
    }
	
	
	/**
	 * 列表维护画面初始化
	 * 
	 * @param request
	 * @param form
	 * @return
	 */
	@RequestMapping("/downloadFile")
	public MspApplytResponse download(@RequestBody  MspApplytRequest form) {
		String applyId=form.getApplyId();
		MspApplytResponse mar=new MspApplytResponse();
		if(form.getApplyId()==null||!form.getApplyId().equals("")) {
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
			//BigDecimal daishenhe = new BigDecimal(0);
			BigDecimal tongguo= new BigDecimal(0);
			BigDecimal jujue= new BigDecimal(0);
			BigDecimal  zongshu= new BigDecimal(0);
			BigDecimal  quxiao= new BigDecimal(0);
			int quxiaoshu=0;
			for (MspApplydetails mspApplyDetails : applylist) {
				if(mspApplyDetails.getApplyresult()!=null&&mspApplyDetails.getApplyresult().equals("01")) {
					tongguo=tongguo.add(new BigDecimal(mspApplyDetails.getLoanmoney()));
					
				}
				if(mspApplyDetails.getApplyresult()!=null&&mspApplyDetails.getApplyresult().equals("02")) {
					jujue=jujue.add(new BigDecimal(mspApplyDetails.getLoanmoney()));
				}
				if(mspApplyDetails.getApplyresult()!=null&&mspApplyDetails.getApplyresult().equals("05")) {
					quxiao=quxiao.add(new BigDecimal(mspApplyDetails.getLoanmoney()));
					quxiaoshu=quxiaoshu+1;
				}
				zongshu=zongshu.add(new BigDecimal(mspApplyDetails.getLoanmoney()));
				
			}
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");  
	          
	        Calendar   calendar   =   new   GregorianCalendar(); 
	        calendar.setTime(new Date()); 
	        calendar.add(calendar.DAY_OF_MONTH, -3);
	        Date y3 = calendar.getTime();
	        calendar.add(calendar.DAY_OF_MONTH, -3);
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
