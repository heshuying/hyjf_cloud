package com.hyjf.cs.trade.handle;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.resquest.trade.*;
import com.hyjf.am.resquest.user.CertificateAuthorityRequest;
import com.hyjf.am.resquest.user.LoanSubjectCertificateAuthorityRequest;
import com.hyjf.am.vo.message.MailMessage;
import com.hyjf.am.vo.trade.*;
import com.hyjf.am.vo.trade.borrow.*;
import com.hyjf.am.vo.trade.hjh.HjhAccedeVO;
import com.hyjf.am.vo.trade.hjh.HjhDebtCreditTenderVO;
import com.hyjf.am.vo.trade.hjh.HjhDebtCreditVO;
import com.hyjf.am.vo.user.*;
import com.hyjf.common.constants.FddGenerateContractConstant;
import com.hyjf.common.constants.MQConstant;
import com.hyjf.common.constants.MessageConstant;
import com.hyjf.common.exception.MQException;
import com.hyjf.common.file.FavFTPUtil;
import com.hyjf.common.file.FileUtil;
import com.hyjf.common.file.SFTPParameter;
import com.hyjf.common.pdf.ImageUtil;
import com.hyjf.common.pdf.PDFToImage;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.util.GetDate;
import com.hyjf.common.validator.Validator;
import com.hyjf.cs.trade.bean.CreditAssignedBean;
import com.hyjf.cs.trade.bean.fdd.FddDessenesitizationBean;
import com.hyjf.cs.trade.bean.fdd.FddGenerateContractBean;
import com.hyjf.cs.trade.client.*;
import com.hyjf.cs.trade.mq.FddProducer;
import com.hyjf.cs.trade.mq.MailProducer;
import com.hyjf.cs.trade.mq.Producer;
import com.hyjf.pay.lib.fadada.bean.DzqzCallBean;
import com.hyjf.pay.lib.fadada.util.DzqzCallUtil;
import com.hyjf.pay.lib.fadada.util.DzqzConstant;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.*;

/**
 * @Description 更新huiyingdai_utm_reg的首投信息
 * @Author sunss
 * @Date 2018/7/7 14:54
 */
@Component
public class UtmRegHandle {

	private static final Logger logger = LoggerFactory.getLogger(UtmRegHandle.class);
	@Autowired
	private AmUserClient amUserClient;

	public void sendMessage( Map<String, Object> param) {
		Integer userId = (Integer)param.get("userId");
		UtmRegVO utmReg = amUserClient.findUtmRegByUserId(userId);
		if (utmReg != null) {
			// 更新huiyingdai_utm_reg的首投信息
			boolean updateUtmFlag = amUserClient.updateFirstUtmReg(param);
		}
		logger.info("----------------------------更新huiyingdai_utm_reg的首投信息结束--------------------------------");

	}
}
