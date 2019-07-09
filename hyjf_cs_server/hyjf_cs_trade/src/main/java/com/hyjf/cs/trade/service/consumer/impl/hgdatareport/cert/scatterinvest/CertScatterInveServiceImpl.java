package com.hyjf.cs.trade.service.consumer.impl.hgdatareport.cert.scatterinvest;

import com.hyjf.am.vo.hgreportdata.cert.CertUserVO;
import com.hyjf.am.vo.trade.borrow.BorrowAndInfoVO;
import com.hyjf.am.vo.trade.borrow.BorrowProjectTypeVO;
import com.hyjf.am.vo.trade.borrow.BorrowRecoverVO;
import com.hyjf.am.vo.trade.borrow.BorrowRepayVO;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.util.GetDate;
import com.hyjf.cs.trade.client.AmTradeClient;
import com.hyjf.cs.trade.config.SystemConfig;
import com.hyjf.cs.trade.mq.consumer.hgdatareport.cert.common.CertCallConstant;
import com.hyjf.cs.trade.mq.consumer.hgdatareport.cert.common.CertCallUtil;
import com.hyjf.cs.trade.service.consumer.hgdatareport.cert.scatterinvest.CertScatterInveService;
import com.hyjf.cs.trade.service.consumer.impl.BaseHgCertReportServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


/**
 * @author sss
 */

@Service
public class CertScatterInveServiceImpl extends BaseHgCertReportServiceImpl implements CertScatterInveService {
    Logger logger = LoggerFactory.getLogger(CertScatterInveServiceImpl.class);

    private String thisMessName = "散标数据推送";
    private String logHeader = "【" + CustomConstants.HG_DATAREPORT + CustomConstants.UNDERLINE + CustomConstants.HG_DATAREPORT_CERT + " " + thisMessName + "】";

    @Autowired
    private AmTradeClient borrowClient;
    @Autowired
    SystemConfig systemConfig;

    /**
     * 组装调用应急中心日志
     *
     * @param borrowNid
     * @return
     */
    @Override
    public Map<String, Object>  getSendData(String borrowNid,String idcardHash) throws Exception {
        // 判断是否已经上送
        Map<String, Object> param = new LinkedHashMap<String, Object>();
        // 查询数据库 start
        BorrowAndInfoVO borrow = borrowClient.selectBorrowByNid(borrowNid);
        List<BorrowProjectTypeVO> borrowProjectTypes = borrowClient.selectBorrowProjectByBorrowCd(borrow.getProjectType().toString());
        CertUserVO certUser = this.getCertUserByUserIdBorrowNid(borrow.getUserId(),borrowNid);
        if(certUser ==null){
            certUser = new CertUserVO();
            certUser.setUserIdCardHash(idcardHash);
            logger.error(logHeader+"未查找到用户信息");
        }
        if(certUser ==null || certUser.getUserIdCardHash()==null||"".equals(certUser.getUserIdCardHash())){
            // 自己生成
            certUser = new CertUserVO();
            certUser.setUserIdCardHash(getUserHashValue(borrow));
            logger.error(logHeader+"哈希值为："+certUser.getUserIdCardHash());
        }
        // 查询数据库 end

        // 开标时间
        String productStartTime = "";
        try {
            if (borrow.getVerifyStatus().equals(3)) {
                // 定时标
                productStartTime = GetDate.timestamptoStrYYYYMMDDHHMMSS(borrow.getOntime()+"");
            } else {
                productStartTime = GetDate.timestamptoStrYYYYMMDDHHMMSS(borrow.getVerifyTimeInteger()+"");
            }
            if(null==productStartTime||"".equals(productStartTime)||"0".equals(productStartTime)){
                productStartTime = GetDate.timestamptoStrYYYYMMDDHHMMSS(borrow.getAddtime());
            }
        }catch (Exception e){
            productStartTime = GetDate.timestamptoStrYYYYMMDDHHMMSS(borrow.getAddtime());
        }

        // 散标类别
       /* String productRegType = "散标";
        if (borrowProjectTypes != null && borrowProjectTypes.get(0).getInvestUserType().equals(1)) {
            productRegType = "新手标";
        }*/
        // 借款用途  个人报送1 企业报送2
        String loanUse = "1";
        if ("1".equals(borrow.getCompanyOrPersonal())) {
            loanUse = "2";
        }
        // 借款年利率  投资单个散标的年化利率，散标打包成产品时，设定为-1注意：如果平台本身记录是日利率，请乘以 365以后上传，如果公布的是月利率，请乘以 12 以后上传
        String loanRate = CertCallUtil.convertLoanRate(borrow.getBorrowApr(), borrow.getBorrowPeriod(), borrow.getBorrowStyle());
        // 投资年化收益率
        String rate = CertCallUtil.convertLoanRate(borrow.getBorrowApr(), borrow.getBorrowPeriod(), borrow.getBorrowStyle());
        // 借款期限 (天)  借款期限必须是天。注意：如果平台期限记录的是月，请乘以 30 以后上传，如果公布的是年，请乘以 365 以后上传
        // 月标转换为天  计算方式：期限*30
        String term = CertCallUtil.convertTerm(borrow.getBorrowPeriod(), borrow.getBorrowStyle());
        // 还款类型 1-等额本息／2-等额本金／3-按月付息到期还本／4-一次性还本付息5 按月还本付息／6 等本等息 0-其它
        // 等额本息 报送1  按月计息到期还本付息/按天计息到期还本付息 报送4先息后本报送3  等额本金 报送2
        String payType = CertCallUtil.convertPayType(borrow.getBorrowStyle());
        // 报送还款手续费+放款手续费金额之和
        String serviceCost = getServiceCost(borrow);
        // 借款类型 1-信用标/2-抵押标/3-担保标/4-流转标/5-净值标/6-信用+抵押/7-信用+担保/0-其他
        // 报送 资产类型值
        String loanType = CertCallUtil.convertLoanType(borrow.getAssetAttributes());
        // 借款主体信用评级  企业根据自己平台信用评级算法进行评级从高到低依次是 A-Z；如果没有评级则进行分类 A 是最好，Z 是最差。
        // AAA 报送A  AA+报送E  AA报送K    AA-报送P  A报送U  BBB报送Z
        // String loanCreditRating = CertCallUtil.convertLoanCreditRating(borrow.getBorrowLevel());
        // 担保方式 1-抵押／2-质押／3-留置／4-定金／5-第三方担保/6-保险/9-风险自担/10-保证（信用）。如果没有担保方式填写-1.
        // 根据资产属性填充值报送 1，2，10
        String securityType = CertCallUtil.convertSecurityType(borrow.getAssetAttributes());

        {
            param.put("version", CertCallConstant.CERT_CALL_VERSION);
            // 标的发布时间  标的发布时间，年-月-日 时:分:秒（24H,NTP/时间），格式 yyyy-MM-dd HH:mm:ss
            param.put("productStartTime", productStartTime);
            // 散标类别 企业可以根据平台自己的类型自行扩展。
            // 新手标报送新手标  非新手标均报送散标
            // param.put("productRegType", productRegType);
            // 散标名称  报送项目名称
            param.put("productName", borrow.getProjectName());
            param.put("sourceCode", systemConfig.certSourceCode);
            // 原散标编号  平台散标自有内部编号
            // 报送项目编号
            param.put("sourceProductCode", borrow.getBorrowNid());
            // 借款用户标示  同用户表的 userIdcardhash
            param.put("userIdcardHash", certUser.getUserIdCardHash());
            // 借款用途  个人报送1 企业报送2
            param.put("loanUse", loanUse);
            // 借款说明  报送借款用途字段值
            param.put("loanDescribe", CertCallUtil.convertLoanDescribe(borrow.getFinancePurpose()));
            // 借款年利率  此数据必须是小数，保留 6 位。注意：如果平台本身记录是日利率，-请乘以 365 以后上传，如果公布的是月利率，请乘以 12 以后上传。
            // 均报送出借利率
            param.put("loanRate", loanRate);
            // 借款金额
            param.put("amount", borrow.getAccount());
            // 投资年化收益率  投资单个散标的年化利率，散标打包成产品时，设定为-1注意：如果平台本身记录是日利率，请乘以 365以后上传，如果公布的是月利率，请乘以 12 以后上传
            // 均报送出借利率
            //param.put("rate", rate);
            // 借款期限 (天)  借款期限必须是天。注意：如果平台期限记录的是月，请乘以 30 以后上传，如果公布的是年，请乘以 365 以后上传
            // 月标转换为天  计算方式：期限*30
            param.put("term", term);
            // 还款类型 1-等额本息／2-等额本金／3-按月付息到期还本／4-一次性还本付息5 按月还本付息／6 等本等息 0-其它
            // 等额本息 报送1  按月计息到期还本付息/按天计息到期还本付息 报送4先息后本报送3  等额本金 报送2
            param.put("payType", payType);
            // 手续费（服务费）金额
            // 报送还款手续费+放款手续费金额之和
            param.put("serviceCost", serviceCost);
            // 风险保证金金额
            // 报送0
            // param.put("riskMargin", "0");
            // 借款类型 1-信用标/2-抵押标/3-担保标/4-流转标/5-净值标/6-信用+抵押/7-信用+担保/0-其他
            // 报送 资产类型值
            param.put("loanType", loanType);
            // 借款主体信用评级  企业根据自己平台信用评级算法进行评级从高到低依次是 A-Z；如果没有评级则进行分类 A 是最好，Z 是最差。
            // AAA 报送A  AA+报送E  AA报送K    AA-报送P  A报送U  BBB报送Z
            // param.put("loanCreditRating", loanCreditRating);
            // 逾期期限 固定值1
           // param.put("overdueLimmit", "1");
            // 坏账期限 固定值90
            //param.put("badDebtLimmit", "90");
            // 是否允许债 权转让 0-是/1-否
            // 是否允许转让：均报送0
            //param.put("allowTransfer", "0");
            // 封闭期（天）  1
            //param.put("closeLimmit", "1");
            // 担保方式 1-抵押／2-质押／3-留置／4-定金／5-第三方担保/6-保险/9-风险自担/10-保证（信用）。如果没有担保方式填写-1.
            // 根据资产属性填充值报送 1，2，10
            param.put("securityType", securityType);
            // 项目来源 1-平台获得/2-线下/3-合作机构/4-其它
            // 报送3
            param.put("projectSource", "3");
            // 增加surplusAmount剩余借款本金：报送0
            param.put("surplusAmount", "0.00");
            // 担保公司数量字段，报送-1
            param.put("securityCompanyAmount", "-1");
            // 原产品链接
            // 报送散标URL
            // param.put("sourceProductUrl",systemConfig.getWebHost()+ "/bank/web/borrow/getBorrowDetail.do?borrowNid=" + borrow.getBorrowNid());
            //旧数据上报分组用
            // groupByDate  旧数据上报排序 按月用yyyy-MM
            String groupByDate = "";
            try {
                if (borrow.getVerifyStatus().equals(3)) {
                    // 定时标
                    groupByDate = GetDate.times10toStrYYYYMM(borrow.getOntime()+"");
                } else {
                    groupByDate = GetDate.times10toStrYYYYMM(borrow.getVerifyTimeInteger()+"");
                }
                if(null==groupByDate||"".equals(groupByDate)||"0".equals(groupByDate)){
                    groupByDate = GetDate.times10toStrYYYYMM(borrow.getAddtime());
                }

            }catch (Exception e){
                logger.error(logHeader+"日期格式化错误");
                groupByDate = GetDate.times10toStrYYYYMM(borrow.getAddtime());
            }
            param.put("groupByDate", groupByDate);
        }
        return param;
    }

    /**
     * 报送还款手续费+放款手续费金额之和
     *
     * @param borrow
     * @return
     */
    private String getServiceCost(BorrowAndInfoVO borrow) {
        List<BorrowRecoverVO> borrowRecovers = getBrorrowRecoverByBorrowNid(borrow.getBorrowNid());
        BigDecimal serviceCost = BigDecimal.ZERO;
        if (borrowRecovers != null && borrowRecovers.size() > 0) {
            for (BorrowRecoverVO item : borrowRecovers) {
                serviceCost = serviceCost.add(item.getRecoverServiceFee());
            }
            BorrowRepayVO repay = getBorrowRepayByBorrowNid(borrow.getBorrowNid());
            if(repay!=null){
                serviceCost = serviceCost.add(repay.getRepayFee());
            }
            return serviceCost.toString();
        }
        return "-1";
    }

    /**
     * 查询还款信息
     *
     * @param borrowNid
     * @return
     */
    private BorrowRepayVO getBorrowRepayByBorrowNid(String borrowNid) {
        List<BorrowRepayVO> list = borrowClient.getBorrowRepayList(borrowNid);
        if (list != null && list.size() > 0) {
            return list.get(0);
        }
        return null;
    }

    /**
     * 查询放款记录
     *
     * @param borrowNid
     * @return
     */
    private List<BorrowRecoverVO> getBrorrowRecoverByBorrowNid(String borrowNid) {
        return borrowClient.selectBorrowRecoverByBorrowNid(borrowNid);
    }
}
