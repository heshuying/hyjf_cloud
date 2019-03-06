package com.hyjf.admin.service.impl;

import com.hyjf.admin.beans.BorrowRepaymentInfoBean;
import com.hyjf.admin.client.AmTradeClient;
import com.hyjf.admin.service.BorrowRepaymentInfoService;
import com.hyjf.admin.utils.Page;
import com.hyjf.am.resquest.admin.BorrowRepaymentInfoRequset;
import com.hyjf.am.vo.admin.BorrowRepaymentInfoCustomizeVO;
import com.hyjf.am.vo.user.HjhInstConfigVO;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @author pangchengchao
 * @version BorrowRepaymentInfoServiceImpl, v0.1 2018/7/7 14:23
 */
@Service
public class BorrowRepaymentInfoServiceImpl implements BorrowRepaymentInfoService {

    private static final Logger logger = LoggerFactory.getLogger(BorrowRepaymentInfoServiceImpl.class);
    @Autowired
    private AmTradeClient amTradeClient;
    /**
     * @Description
     * @Author pangchengchao
     * @Version v0.1
     * @Date 获取资产来源配置列表
     */
    @Override
    public List<HjhInstConfigVO> selectHjhInstConfigByInstCode(String instCode) {
        List<HjhInstConfigVO> hjhInstConfigVOList = amTradeClient.selectCommonHjhInstConfigList();
        return hjhInstConfigVOList;
    }
    /**
     * @Description
     * @Author pangchengchao
     * @Version v0.1
     * @Date 查询页面列表数据
     */
    @Override
    public BorrowRepaymentInfoBean selectBorrowRepaymentInfoListForView(BorrowRepaymentInfoRequset request) {
        // 默认当天
        Date endDate = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        //modify by cwyang 搜索条件存在标的号时，不加时间限制  20180510
        /*if(StringUtils.isNotBlank(request.getBorrowNid())){
            request.setYesTimeStartSrch(null);
            request.setYesTimeEndSrch(null);
        }*/
        if(request.getYesTimeStartSrch() != null&&!"".equals(request.getYesTimeStartSrch())) {
            Date date;
            try {
                date = simpleDateFormat.parse(request.getYesTimeStartSrch());

                request.setYesTimeStartSrch(String.valueOf(date.getTime()/1000));
            } catch (ParseException e) {
                logger.error(e.getMessage());
            }

        }
        if(request.getYesTimeEndSrch() != null&&!"".equals(request.getYesTimeStartSrch())) {
            Date date;
            try {
                date = simpleDateFormat.parse(request.getYesTimeEndSrch());
                Calendar cal = Calendar.getInstance();
                cal.setTime(date);
                cal.add(Calendar.DATE, 1);

                request.setYesTimeEndSrch(String.valueOf(cal.getTime().getTime()/1000));
            } catch (ParseException e) {
                logger.error(e.getMessage());
            }

        }
        BorrowRepaymentInfoBean bean=new BorrowRepaymentInfoBean();
        Integer count = this.amTradeClient.countBorrowRepaymentInfo(request);
        Page page = Page.initPage(request.getCurrPage(), request.getPageSize());
        page.setTotal(count);
        request.setLimitStart(page.getOffset());
        request.setLimitEnd(page.getLimit());
        bean.setTotal(count);
        if (count != null && count > 0) {
            // 将列表查询与导出查询独立区分
            List<BorrowRepaymentInfoCustomizeVO> recordList = this.amTradeClient.selectBorrowRepaymentInfoListForView(request);
            bean.setRecordList(recordList);
            BorrowRepaymentInfoCustomizeVO sumObject = this.amTradeClient.sumBorrowRepaymentInfo(request);
            bean.setSumObject(sumObject);
        }else{
            bean.setRecordList(new ArrayList<BorrowRepaymentInfoCustomizeVO>());
            bean.setSumObject(new BorrowRepaymentInfoCustomizeVO());
        }

        return bean;
    }
    /**
     * @Description
     * @Author pangchengchao
     * @Version v0.1
     * @Date 查询导出列表数据
     */
    @Override
    public List<BorrowRepaymentInfoCustomizeVO> selectBorrowRepaymentList(BorrowRepaymentInfoRequset copyForm) {
        Page page = Page.initPage(copyForm.getCurrPage(), copyForm.getPageSize());
        copyForm.setLimitStart(page.getOffset());
        copyForm.setLimitEnd(page.getLimit());
        return this.amTradeClient.selectBorrowRepaymentInfoList(copyForm);
    }

    @Override
    public Integer countBorrowRepaymentInfo(BorrowRepaymentInfoRequset copyForm) {
        return amTradeClient.countBorrowRepaymentInfoExport(copyForm);
    }
}
