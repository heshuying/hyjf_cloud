package com.hyjf.cs.message.controller.wechat;

import com.hyjf.am.response.trade.WeeklyResponse;
import com.hyjf.am.vo.config.EventVO;
import com.hyjf.am.vo.trade.CreditRepayVO;
import com.hyjf.am.vo.trade.CreditTenderVO;
import com.hyjf.am.vo.trade.account.AccountVO;
import com.hyjf.am.vo.trade.borrow.BorrowRecoverVO;
import com.hyjf.am.vo.trade.borrow.BorrowTenderCpnVO;
import com.hyjf.am.vo.trade.borrow.BorrowTenderVO;
import com.hyjf.am.vo.trade.hjh.HjhAccedeVO;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.util.GetDate;
import com.hyjf.cs.message.bean.ic.WeeklyreportEntity;
import com.hyjf.cs.message.service.wechatWeekly.WeeklyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author lisheng
 * @version WeeklyController, v0.1 2018/7/27 14:25
 */
@Api(tags = "weChat端-上周周报")
@RestController
@RequestMapping("/hyjf-wechat/wx/weekly")
public class WeeklyController {
    @Autowired
    private WeeklyService weeklyService;

    SimpleDateFormat sdyy= new SimpleDateFormat("yyyy");
    SimpleDateFormat sddd= new SimpleDateFormat("dd");

    @ApiOperation(value = "上周周报", notes = "上周周报")
    @RequestMapping("/data")
    public WeeklyResponse getWeeklyData(@RequestHeader("userId") Integer userId) {
        //Integer userId=1;
        WeeklyResponse resultBean = new WeeklyResponse();
        String methodName = "getWeeklyData";
        DecimalFormat df = CustomConstants.DF_FOR_VIEW;
        UserVO user= weeklyService.getUserById(userId);
        resultBean.setTouxiang(user.getIconUrl());
        resultBean.setYonghuming(user.getUsername());
        resultBean.setKaihu(user.getBankOpenAccount());
        if(user.getBankOpenAccount()==1) {
            resultBean.setXingming(weeklyService.findUsersInfoById(userId).getTruename());
        }
        Map<String, String> date= this.getLastTimeInterval(GetDate.getTime10(user.getRegTime()));
        int date1= Integer.valueOf(date.get("date1"));
        int date2=Integer.valueOf(date.get("date2"));
        String dat1=date.get("date1");
        String dat2=date.get("date2");

        List<WeeklyreportEntity> wse1 = weeklyService.getWeeklyReport(0, dat1);

        if(wse1.isEmpty()) {
            //放入公共周数据
            EventVO eal = weeklyService.getEventsAll(date1, date2);
            String eventTime = eal.getEventTime();
            BigDecimal benzhoutouzie = new BigDecimal(eventTime);
            String content = eal.getContent();
            BigDecimal benzhoushouyi = new BigDecimal(eventTime);
            resultBean.setBenzhoutouzie(benzhoutouzie.divide(new BigDecimal(10000)).setScale(2, BigDecimal.ROUND_DOWN).toString() + "万");
            resultBean.setBenzhoushouyi(benzhoushouyi.divide(new BigDecimal(10000)).setScale(2, BigDecimal.ROUND_DOWN).toString() + "万");
            resultBean.setChengjiaoshu(eal.getEventYear());
            WeeklyreportEntity wrt=new WeeklyreportEntity();
            wrt.setUserId(0);
            wrt.setBeginDate(dat1);
            wrt.setTouzie(new BigDecimal(eventTime));
            //eal.getContent() 值判空
            wrt.setShouyi(new BigDecimal(StringUtils.isBlank(content)?"0.00": content));
            wrt.setBishu(eal.getEventYear());
            weeklyService.inWeeklyReport(wrt);

        }else{
            BigDecimal touzie = wse1.get(0).getTouzie();
            BigDecimal shouyi = wse1.get(0).getShouyi();
            resultBean.setBenzhoutouzie(touzie.divide(new BigDecimal(10000)).setScale(2, BigDecimal.ROUND_DOWN).toString() + "万");
            resultBean.setBenzhoushouyi(shouyi.divide(new BigDecimal(10000)).setScale(2, BigDecimal.ROUND_DOWN).toString() + "万");
            resultBean.setChengjiaoshu(wse1.get(0).getBishu());
        }
        resultBean.setBeginDate(dat1);
        resultBean.setEndDate(dat2);
        resultBean.setZongtianshu(Integer.valueOf(date.get("zongtianshu")));
        resultBean.setDateString(date.get("dateString"));
        AccountVO act = weeklyService.getAccount(Integer.valueOf(userId));
        resultBean.setZongshouyi(df.format(act.getBankInterestSum()));
        resultBean.setZongjine(df.format(act.getBankTotal()));
        List<WeeklyreportEntity> wse2 = weeklyService.getWeeklyReport(Integer.valueOf(userId), dat1);
        WeeklyreportEntity wrt=new WeeklyreportEntity();
        wrt.setUserId(Integer.valueOf(userId));
        wrt.setBeginDate(dat1);
        wrt.setEndDate(dat2);
        wrt.setZongtianshu(Integer.valueOf(date.get("zongtianshu")));
        wrt.setDatestring(date.get("dateString"));

        EventVO eal2 = weeklyService.selectPercentage(act.getBankInterestSum().intValue(),date1,date2,Integer.valueOf(userId));
        resultBean.setBaifenbi(eal2.getEventYear());
        wrt.setBaifenbi(eal2.getEventYear());

        List<BorrowTenderVO> bt=weeklyService.getBorrowTender(Integer.valueOf(userId),date1,date2);
        List<CreditTenderVO> ct = weeklyService.getCreditTender(Integer.valueOf(userId),dat1,dat2);
        List<HjhAccedeVO> ae = weeklyService.getAccede(Integer.valueOf(userId), date1,date2);
        List<BorrowTenderCpnVO> btc=weeklyService.getBorrowTenderCPN(Integer.valueOf(userId),date1,date2);

        resultBean.setBishu(bt.size() + ct.size() + ae.size());
        wrt.setBishu(bt.size() + ct.size() + ae.size());
        BigDecimal leijie = new BigDecimal(0);
        BigDecimal yuqi = new BigDecimal(0);
        List<String> list1=new ArrayList<String>();
        for (BorrowTenderVO borrowTender : bt) {
            leijie=leijie.add(borrowTender.getAccount());
            yuqi=yuqi.add(borrowTender.getRecoverAccountInterest());
            Date mm = new Date(Long.valueOf(borrowTender.getAddTime())* 1000);
            list1.add(sddd.format(mm));
        }
        for (CreditTenderVO creditTender : ct) {
            leijie=leijie.add(creditTender.getAssignCapital());
            yuqi=yuqi.add(creditTender.getAssignInterest());
            Date mm = creditTender.getAddTime();
            list1.add(sddd.format(mm));
        }
        for (HjhAccedeVO hjhAccede : ae) {
            leijie=leijie.add(hjhAccede.getAccedeAccount());
            yuqi=yuqi.add(hjhAccede.getShouldPayInterest());

        }
        for (BorrowTenderCpnVO borrowTenderCpn : btc) {
            yuqi=yuqi.add(borrowTenderCpn.getRecoverAccountWait());
        }
        resultBean.setTouzigaikuang(this.quchong(list1));
        resultBean.setTouzie(df.format(leijie));
        resultBean.setShouyi(df.format(yuqi));
        wrt.setTouzie(leijie);
        wrt.setShouyi(yuqi);
        StringBuffer s = new StringBuffer();
        for(int i = 0;i < resultBean.getTouzigaikuang().size(); i ++){
            s.append(resultBean.getTouzigaikuang().get(i));
            if(i!=(resultBean.getTouzigaikuang().size()-1)) {
                s.append("|");
            }
        }
        wrt.setTouzigaikuang(s.toString());

        List<BorrowRecoverVO> br = weeklyService.getBorrowRecover(Integer.valueOf(userId),dat1,dat2);
        List<CreditRepayVO> cr = weeklyService.getCreditRepay(Integer.valueOf(userId),date1,date2);
        List<CreditRepayVO> cr2 = weeklyService.getCreditRepayToCredit(Integer.valueOf(userId),date1,date2);

        BigDecimal huankuan = new BigDecimal(0);
        List<String> list2=new ArrayList<String>();
        BigDecimal zhaizhuan = new BigDecimal(0);
        for (BorrowRecoverVO borrowRecover : br) {
            huankuan=huankuan.add(borrowRecover.getRecoverAccountYes());
            Date mm = new Date(Long.valueOf(Integer.valueOf(borrowRecover.getRecoverYestime())) * 1000);
            list2.add(sddd.format(mm));

        }
        for (CreditRepayVO creditRepay : cr) {
            huankuan=huankuan.add(creditRepay.getAssignRepayAccount());
            Date mm = creditRepay.getCreateTime();
            list2.add(sddd.format(mm));

        }
        for (CreditRepayVO creditRepay : cr2) {
            zhaizhuan=zhaizhuan.add(creditRepay.getAssignRepayAccount());
        }
        huankuan=huankuan.subtract(zhaizhuan);
        String rr=eal2.getContent();
        if(StringUtils.isNotBlank(rr)) {
            huankuan=huankuan.add(new BigDecimal(rr));
        }
        resultBean.setHuankuanzonge(df.format(huankuan));
        resultBean.setHuankuangaikuang(this.quchong(list2));
        wrt.setHuankuanzonge(huankuan);
        StringBuffer s2 = new StringBuffer();
        for(int i = 0;i < resultBean.getHuankuangaikuang().size(); i ++){
            s2.append(resultBean.getHuankuangaikuang().get(i));
            if(i!=(resultBean.getHuankuangaikuang().size()-1)) {
                s2.append("|");
            }
        }
        wrt.setHuankuangaikuang(s2.toString());
        weeklyService.inWeeklyReport(wrt);
        if(weeklyService.getCoupon(Integer.valueOf(userId))) {
            resultBean.setYouhuiquan(1);
        }else {
            resultBean.setYouhuiquan(0);
        }
        resultBean.setHuodong(weeklyService.getActivity((int) (System.currentTimeMillis()/1000)));
        resultBean.setJishi(weeklyService.getEvents(date1,date2,Integer.valueOf(date.get("year"))));

        return resultBean;
    }


    /**
     * 根据当前日期获得上周的日期区间（上周周一和周日日期）
     *
     * @return
     */
    public Map<String, String>  getLastTimeInterval(int regTime) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar calendar1 = Calendar.getInstance();
        Calendar calendar2 = Calendar.getInstance();
        calendar1.set(Calendar.HOUR_OF_DAY, 0);
        calendar1.set(Calendar.MINUTE, 0);
        calendar1.set(Calendar.SECOND, 0);
        calendar2.set(Calendar.HOUR_OF_DAY, 23);
        calendar2.set(Calendar.MINUTE, 59);
        calendar2.set(Calendar.SECOND, 59);
        int dayWeek = calendar1.get(Calendar.DAY_OF_WEEK);// 获得当前日期是一个星期的第几天
        if (1 == dayWeek) {
            calendar1.add(Calendar.DAY_OF_MONTH, -1);
        }
        int dayOfWeek = calendar1.get(Calendar.DAY_OF_WEEK) - 1;
        int offset1 = 1 - dayOfWeek;
        int offset2 = 7 - dayOfWeek;
        calendar1.add(Calendar.DATE, offset1 - 7);
        calendar2.add(Calendar.DATE, offset2 - 7);

        Date date1 = calendar1.getTime();
        Date date2 = calendar2.getTime();
        Date newDate = new Date();
        long time = Long.valueOf(regTime) * 1000;
        Date zongtianshu = new Date(time);
        Calendar calendar3 = calendar1;
        String dateString=sddd.format(calendar3.getTime());
        for (int i = 1; i <= 6; i++) {
            calendar3.add(Calendar.DATE, 1);
            dateString=dateString+"|"+ sddd.format(calendar3.getTime());
        }

        Map<String, String> date=new HashMap<>();
        date.put("zongtianshu", String.valueOf(this.differentDaysByMillisecond(zongtianshu, newDate)));
        date.put("date1", String.valueOf((int)(date1.getTime()/1000)));
        date.put("date2", String.valueOf((int)(date2.getTime()/1000)));
        date.put("dateString", dateString);
        date.put("year", sdyy.format(date2));
        String lastBeginDate = sdf.format(calendar1.getTime());
        String lastEndDate = sdf.format(calendar2.getTime());
//         System.out.println(lastBeginDate+"kkkk"+lastEndDate);
        return date;
    }
    /**
     * 通过时间秒毫秒数判断两个时间的间隔
     * @param date1
     * @param date2
     * @return
     */
    public  int differentDaysByMillisecond(Date date1,Date date2)
    {
        int days = (int) ((date2.getTime() - date1.getTime()) / (1000*3600*24)) + 1;
        return days;
    }

    public List<String> quchong(List<String> list){

        HashSet<String> h = new HashSet<String>(list);
        list.clear();
        list.addAll(h);
        return list;
    }

}
