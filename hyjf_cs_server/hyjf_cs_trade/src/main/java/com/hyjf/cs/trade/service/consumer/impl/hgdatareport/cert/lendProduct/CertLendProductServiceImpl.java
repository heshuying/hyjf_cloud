package com.hyjf.cs.trade.service.consumer.impl.hgdatareport.cert.lendProduct;

import com.alibaba.fastjson.JSONArray;
import com.hyjf.am.vo.trade.borrow.BorrowInfoVO;
import com.hyjf.am.vo.trade.borrow.RightBorrowVO;
import com.hyjf.am.vo.trade.cert.CertProductUpdateVO;
import com.hyjf.am.vo.trade.cert.CertProductVO;
import com.hyjf.am.vo.trade.hjh.HjhPlanVO;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.util.GetDate;
import com.hyjf.cs.trade.client.AmTradeClient;
import com.hyjf.cs.trade.client.AmUserClient;
import com.hyjf.cs.trade.config.SystemConfig;
import com.hyjf.cs.trade.mq.consumer.hgdatareport.cert.common.CertCallConstant;
import com.hyjf.cs.trade.mq.consumer.hgdatareport.cert.common.CertCallUtil;
import com.hyjf.cs.trade.service.consumer.hgdatareport.cert.lendProduct.CertLendProductService;
import com.hyjf.cs.trade.service.consumer.impl.BaseHgCertReportServiceImpl;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * @author nxl
 */

@Service
public class CertLendProductServiceImpl extends BaseHgCertReportServiceImpl implements CertLendProductService {

    Logger logger = LoggerFactory.getLogger(CertLendProductServiceImpl.class);
    private String thisMessName = "产品信息推送";
    private String logHeader = "【" + CustomConstants.HG_DATAREPORT + CustomConstants.UNDERLINE + CustomConstants.HG_DATAREPORT_CERT + " " + thisMessName + "】";

    @Autowired
    AmTradeClient amTradeClient;
    @Autowired
    AmUserClient amUserClient;
    @Autowired
    SystemConfig systemConfig;

    /**
     * 日期转换
     *
     * @param repayTime
     * @return
     */
    public String dateFormatTransformationDate(Date repayTime, String flg) {
        if (flg.equals("H")) {
            //代表获取有时分秒
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String dateStr = sdf.format(repayTime);
            return dateStr;
        }
        if (flg.equals("Y")) {
            //代表只有年与日
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String dateStr = sdf.format(repayTime);
            return dateStr;
        }
        return null;
    }
    /**
     * 查询计划产品信息,组装上报信息
     * @param planNid
     * @return
     */
    @Override
    public JSONArray getPlanProdouct(String planNid,Boolean isOld,String isPlan,JSONArray json){
        Map<String, Object> param =getParam(planNid,isOld,isPlan);
        if(!param.isEmpty()&&param.size()>0){
            json.add(param);
        }
        return json;
    }

    //

    public Map<String,Object> getParam(String planNid,Boolean isOld,String isPlan) {
        Map<String, Object> param = new HashMap<String, Object>();
        String sourceFinancingCode = "";
        String financingStartTime = "";
        String productName = "";
        String rate = "";
        int term = 0;
        String groupByDateStr="";
        try {
            if (isPlan.equals("1")) {
                //计划信息
                HjhPlanVO hjhPlanVO = amTradeClient.getHjhPlan(planNid);
                if (null == hjhPlanVO) {
                    throw new Exception("产品信息推送,获取计划信息为空！！计划编码:" + planNid);
                }
                if (null != hjhPlanVO) {
                    //产品信息编号
                    sourceFinancingCode = hjhPlanVO.getPlanNid();
                    //发布时间
                    financingStartTime = dateFormatTransformationDate(hjhPlanVO.getCreateTime(), "H");
                    //产品名称
                    productName = hjhPlanVO.getPlanName();
                    //预期年化利率（参考回报率）
                    rate = CertCallUtil.convertLoanRate(hjhPlanVO.getExpectApr(), 0, "");
                    //产品期限（服务期限）(天)
                    term = hjhPlanVO.getLockPeriod();
                    if (hjhPlanVO.getIsMonth() == 1) {
                        //月标（转换为天数）
                        term = hjhPlanVO.getLockPeriod() * 30;
                    }
                    param.put("term", String.valueOf(term));
                    groupByDateStr = dateFormatTransformation(hjhPlanVO.getCreateTime());
                }
            } else if (isPlan.equals("0")) {
                //散标信息
                BorrowInfoVO borrowInfoVO = amTradeClient.getBorrowInfoByNid(planNid);
                RightBorrowVO borrowVO = amTradeClient.getRightBorrowByNid(planNid);
                if (null == borrowInfoVO || null == borrowVO) {
                    throw new Exception("产品信息推送,获取散标信息为空！！散标编码:" + planNid);
                }
                //产品信息编号
                sourceFinancingCode = borrowInfoVO.getBorrowNid();
                //发布时间
                financingStartTime = GetDate.timestamptoStrYYYYMMDDHHMMSS(borrowVO.getVerifyTime().toString());
                //产品名称
                productName = borrowInfoVO.getName();
                //预期年化利率（参考回报率）
                rate = CertCallUtil.convertLoanRate(borrowVO.getBorrowApr(), 0, "");
                //产品期限（服务期限）(天)
                term = borrowVO.getBorrowPeriod();
                if (borrowVO.getIsMonth() == 1) {
                    term = borrowVO.getBorrowPeriod() * 30;
                }
                groupByDateStr = dateFormatTransformation(borrowVO.getCreateTime());
            }
            //接口版本号
            param.put("version", CertCallConstant.CERT_CALL_VERSION);
            //平台编号
            param.put("sourceCode", systemConfig.getCertSourceCode());
            //产品信息编号
            param.put("sourceFinancingCode", sourceFinancingCode);
            //发布时间
            param.put("financingStartTime", financingStartTime);
            //产品名称
            param.put("productName", productName);
            //预期年化利率（参考回报率）
            param.put("rate", rate);
            //最小预期年化利率
            param.put("minRate", "-1");
            //最大预期年化利率
            param.put("maxRate", "-1");
            //产品期限（服务期限）(天)
            param.put("term", String.valueOf(term));
            if(isOld) {
                param.put("groupByDate", groupByDateStr);
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return param;
    }

    /**
     * 格式化日期，年月日
     * @param date
     * @return
     */
    private String dateFormatTransformation(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        String strDate = sdf.format(date);
        return strDate;
    }


    /**
     * 获取线上所有计划信息
     * @return
     */
    @Override
    public List<HjhPlanVO> getAllPlanInfo(){
        List<HjhPlanVO> hjhPlanVOList = amTradeClient.selectAllPlan();
        return hjhPlanVOList;
    }

    /**
     * 查找未上报的产品信息
     * @return
     */
    @Override
    public List<CertProductVO> selectCertProductList(){
        return amTradeClient.selectCertProductList();
    }
    /**
     * 批量更新
     *
     * @param updateVO
     * @return
     */
    @Override
    public Integer updateCertProductBatch(CertProductUpdateVO updateVO) {
        return amTradeClient.updateCertProductBatch(updateVO);
    }

    /**
     * 组装产品信息历史数据
     * @return
     */
    @Override
    public JSONArray getHistoryDateProduct() {
        JSONArray jsonArray = new JSONArray();
        try {
            List<CertProductVO> certProductVOList = selectCertProductList();
            if(CollectionUtils.isEmpty(certProductVOList)){
                logger.info("产品信息历史数据推送,暂无报送的产品信息");
                return null;
            }
            for(CertProductVO certProductVO:certProductVOList){
                //是否是智投 1：散标,2：智投
                jsonArray = getPlanProdouct(certProductVO.getProductNid(),true,certProductVO.getIsPlan().toString(),jsonArray);
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return jsonArray;
    }

}
