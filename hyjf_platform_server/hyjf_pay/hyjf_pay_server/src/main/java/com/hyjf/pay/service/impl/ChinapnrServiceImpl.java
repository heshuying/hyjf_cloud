package com.hyjf.pay.service.impl;

import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.util.GetDate;
import com.hyjf.common.util.GetterUtil;
import com.hyjf.pay.entity.ChinapnrExclusiveLog;
import com.hyjf.pay.entity.ChinapnrLog;
import com.hyjf.pay.entity.ChinapnrSendlog;
import com.hyjf.pay.lib.PnrApiBean;
import com.hyjf.pay.lib.chinapnr.ChinapnrBean;
import com.hyjf.pay.lib.chinapnr.util.ChinaPnrConstant;
import com.hyjf.pay.mongo.ChinapnrExclusiveLogDao;
import com.hyjf.pay.mongo.ChinapnrLogDao;
import com.hyjf.pay.mongo.ChinapnrSendLogDao;
import com.hyjf.pay.service.ChinapnrService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ChinapnrServiceImpl  implements ChinapnrService {

	@Autowired
	private ChinapnrExclusiveLogDao chinapnrExclusiveLogDao;

	@Autowired
	private ChinapnrSendLogDao chinapnrSendlogDao;

	@Autowired
	ChinapnrLogDao chinapnrLogDao;

	@Override
	public String insertChinapnrExclusiveLog(ChinapnrBean bean, String methodName) {
		// 发送前插入状态记录
		String nowTime = GetDate.getServerDateTime(9, new Date());
		ChinapnrExclusiveLog exclusiveLog = new ChinapnrExclusiveLog();
		exclusiveLog.setCmdid(bean.get(ChinaPnrConstant.PARAM_CMDID));
		exclusiveLog.setResptype(bean.get(ChinaPnrConstant.PARAM_CMDID));
		exclusiveLog.setOrdid(bean.get(ChinaPnrConstant.PARAM_ORDID));
		exclusiveLog.setContent(bean.getJson().replace("&amp;", "&"));
		exclusiveLog.setStatus(ChinaPnrConstant.STATUS_SENDING);
		exclusiveLog.setType(bean.getLogType());
		exclusiveLog.setDelFlag(CustomConstants.FLAG_NORMAL);
		exclusiveLog.setCreatetime(nowTime);
		exclusiveLog.setUpdatetime(nowTime);
		exclusiveLog.setCreateuser(methodName);
		exclusiveLog.setUpdateuser(methodName);
		chinapnrExclusiveLogDao.insert(exclusiveLog);
		return exclusiveLog.getId();

	}

	@Override
	public void insertChinapnrSendLog(ChinapnrBean bean, PnrApiBean pnrApiBean) {
		int nowTime = GetDate.getNowTime10();
		// 发送前插入日志记录
		ChinapnrSendlog sendlog = new ChinapnrSendlog();
		sendlog.setOrdid(bean.get(ChinaPnrConstant.PARAM_ORDID));
		sendlog.setBorrowNid(bean.get(ChinaPnrConstant.PARAM_BORROWNID));
		sendlog.setOrddate(bean.get(ChinaPnrConstant.PARAM_ORDDATE));
		sendlog.setUserId(bean.getLogUserId());
		sendlog.setMsgType(bean.get(ChinaPnrConstant.PARAM_CMDID));
		sendlog.setMsgdata(bean.get(ChinaPnrConstant.PARAM_MSGDATA));
		sendlog.setChkvalue(bean.get(ChinaPnrConstant.PARAM_CHKVALUE));
		sendlog.setContent(pnrApiBean.getJson());
		sendlog.setRemark(bean.get(ChinaPnrConstant.PARAM_REMARK));
		sendlog.setClient(GetterUtil.getInteger(bean.get(ChinaPnrConstant.PARAM_CLIENT)));
		sendlog.setCreateTime(nowTime);
		chinapnrSendlogDao.insert(sendlog);
	}

	@Override
	public ChinapnrExclusiveLog selectChinapnrExclusiveLog(String id) {
		Query query = new Query();
		Criteria criteria = Criteria.where("id").is(id);
		query.addCriteria(criteria);
		return chinapnrExclusiveLogDao.findOne(query);
	}

	@Override
	public void insertChinapnrLog(ChinapnrLog chinapnrLog) {
		chinapnrLogDao.insert(chinapnrLog);
	}

	@Override
	public void updateChinapnrExclusiveLog(ChinapnrExclusiveLog record) {
				chinapnrExclusiveLogDao.save(record);
	}

	@Override
	public void updateChinapnrExclusiveLog(ChinapnrBean bean, ChinapnrExclusiveLog record, String methodName, String status, String remark) {
		ChinapnrExclusiveLog exclusiveLog = this.chinapnrExclusiveLogDao.findOne(
				Query.query(Criteria.where("id").is(record.getId()).and("updatetime").is(record.getUpdatetime())));
		// 更新状态记录
		String nowTime = GetDate.getServerDateTime(9, new Date());
		exclusiveLog.setStatus(status);
		exclusiveLog.setResult(bean.getJson());
		exclusiveLog.setRemark(remark);
		exclusiveLog.setCmdid(bean.getCmdId());
		exclusiveLog.setResptype(bean.getRespType());
		exclusiveLog.setRespcode(bean.getRespCode());
		exclusiveLog.setUpdatetime(nowTime);
		exclusiveLog.setUpdateuser(methodName);
		chinapnrExclusiveLogDao.save(exclusiveLog);;
	}

	@Override
	public void insertChinapnrLog(ChinapnrBean bean, int isBig) {
		ChinapnrLog chinapnrLog = new ChinapnrLog();
		chinapnrLog.setIsbg(isBig);
		chinapnrLog.setUserId(bean.getInteger(ChinaPnrConstant.PARAM_USRID));
		chinapnrLog.setOrdid(bean.getOrdId());
		chinapnrLog.setBorrowNid(bean.getLogBorrowNid());
		chinapnrLog.setRespCode(bean.getRespCode());
		chinapnrLog.setRespDesc(bean.getRespDesc());
		chinapnrLog.setMsgType(bean.getCmdId());
		chinapnrLog.setRespType(bean.getRespType());
		chinapnrLog.setMsgdata(bean.getJson());
		chinapnrLog.setTrxid(bean.getTrxId());
		chinapnrLog.setAddtime(GetDate.getServerDateTime(8, new Date()));
		chinapnrLog.setRemark(bean.getLogRemark());
		chinapnrLog.setIp(bean.getLogIp());
		chinapnrLogDao.insert(chinapnrLog);
	}

	@Override
	public ChinapnrExclusiveLog selectChinapnrExclusiveLogByOrderId(String ordId) {
		Query query = new Query();
		Criteria criteria = Criteria.where("ordid").is(ordId);
		query.addCriteria(criteria);
		List<ChinapnrExclusiveLog> result = chinapnrExclusiveLogDao.find(query);
		if (result != null && result.size() > 0) {
			return result.get(0);
		}
		return null;
	}


}
