package com.hyjf.admin.controller.message;

import com.hyjf.admin.beans.request.SmsLogRequestBean;
import com.hyjf.admin.service.SmsCodeService;
import com.hyjf.admin.service.SmsCountService;
import com.hyjf.admin.service.SmsLogService;
import com.hyjf.am.resquest.message.SmsLogRequest;
import com.hyjf.am.vo.admin.SmsCountCustomizeVO;
import com.hyjf.am.vo.admin.SmsLogVO;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.common.util.GetDate;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

/**
 * 短信统计  临时类
 * @Author: yinhui
 * @Date: 2019/5/8 10:01
 * @Version 1.0
 */
@Controller
@RequestMapping(value = "/hyjf-admin/smsauto")
public class SmsCountTemp {

    private Logger logger = LoggerFactory.getLogger(SmsCountTemp.class);

    @Autowired
    private SmsCountService smsCountService;
    @Autowired
    private SmsCodeService smsCodeService;
    @Autowired
    private SmsLogService smsLogService;

    @Value("${hyjf.env.test}")
    private boolean hyjfEnvTest;

    @RequestMapping(value = "/auto", method = RequestMethod.GET)
    @ResponseBody
    public void smsAuto() {
        logger.info("SmsCountTemp smsAuto 开始运行");
        long startTime = System.currentTimeMillis();   //获取开始时间

        //先将CRM中的企业用户对应的部门都查询出来
        List<SmsCountCustomizeVO> listIdAndDepartmentName = smsCountService.getuserIdAnddepartmentName();
        HashSet<Integer> setUserId = new HashSet<>();
        List<Integer> listInt = new ArrayList<>();
        for (SmsCountCustomizeVO sms : listIdAndDepartmentName) {
            setUserId.add(sms.getId());//存放的是用户ID ---  这是用来查询是否包含用户ID的
            listInt.add(sms.getId());//存放的是用户ID   ---  这是用来查询用户ID在数组中对的位置
        }

        SmsLogRequestBean smlogCustomize = new SmsLogRequestBean();
        //测试环境生成所有的
        if(!hyjfEnvTest){
            smlogCustomize.setStatus(0);
        }

        smlogCustomize.setPostTimeBegin("2019-3-8");//开始时间 2019-3-8
        smlogCustomize.setPostTimeEnd(String.valueOf(GetDate.dateToDateFormatStr(new Date(),"yyyy-MM-dd")));//结束时间 当前时间
        //查询当前短信日志的总数
        Integer count = this.smsCodeService.queryLogCount(smlogCustomize);

        Integer loopCount = (count + 5000 - 1) / 5000;

        for (int i = 0; i < loopCount; i++) {
            try {
                //每次查询以前条记录
                SmsLogRequest request = new SmsLogRequest();
                //测试环境生成所有的
                if(!hyjfEnvTest){
                    smlogCustomize.setStatus(0);
                }
                //从那条开始
                request.setCurrPage(i+1);
                //一页显示几条
                request.setPageSize(5000);
                request.setPostTimeBegin("2019-3-8");//开始时间 2019-3-8
                request.setPostTimeEnd(String.valueOf(GetDate.dateToDateFormatStr(new Date(),"yyyy-MM-dd")));//结束时间 当前时间

                //根据ID查询，  1000<id<2000
                List<SmsLogVO> listSmsLog = smsLogService.findSmsLogList(request);

                if (CollectionUtils.isEmpty(listSmsLog)) {
                    continue;
                }

                //批次通过手机号码查询用户ID和推荐人ID，将 key:手机号码,value:UserVO
                Map<String, UserVO> mapUserVo = getUserByMobile(listSmsLog);

                Map<String, Map<String, String>> departmentMap = new LinkedHashMap<>();
                for (SmsLogVO dto : listSmsLog) {

                    try {

                        if (StringUtils.isEmpty(dto.getType()) || StringUtils.isEmpty(dto.getMobile())) {
                            continue;
                        }

                        String[] strSms = null;
                        if (dto.getMobile().contains(",")) {
                            strSms = dto.getMobile().split(",");
                        } else {
                            strSms = new String[]{dto.getMobile()};
                        }

                        String postString = GetDate.timestamptoStrYYYYMMDD(dto.getPosttime());

                        //拆分每个手机号码
                        for (String mobile : strSms) {
                            String departmentId = null;
                            String departmentName = null;
                            Map<String, String> mobileMap = new HashMap<String, String>();

                            UserVO users = mapUserVo.get(mobile);
                            if (users == null) {
                                continue;
                            }

                            boolean flag = setUserId.contains(users.getUserId());
                            if (!flag) {
                                //再查询推荐人是否是分公司的
                                if (users.getReferrer() != null) {
                                    flag = setUserId.contains(users.getReferrer());
                                }

                            }

                            if (flag) {
                                Integer idIndex = listInt.indexOf(users.getUserId());
                                if(idIndex > 0){
                                    departmentId = String.valueOf(listIdAndDepartmentName.get(idIndex).getDepartmentId());
                                    departmentName = listIdAndDepartmentName.get(idIndex).getDepartmentName();
                                }
                            }

//                            Map<String,String> mobileMap = smsCountServiceImpl.getDeptByMobile(mobile);

                            //短信数量
                            int smscounts = this.computeSms(dto.getContent());

                            //如果当前手机号码不归属某个分公司，就放入到其他里面
                            if (StringUtils.isEmpty(departmentId) && StringUtils.isEmpty(departmentName)) {

                                departmentId = "0";//0 -代表 其他
                                departmentName = "其他";//
                            }

                            mobileMap.put("smsCount", String.valueOf(smscounts));
                            mobileMap.put("deptId", departmentId);
                            mobileMap.put("deptName", departmentName);
                            mobileMap.put("posttime", postString);

                            if (departmentMap.containsKey(postString + departmentId)) {
                                Map<String, String> summobileMap = departmentMap.get(postString + departmentId);
                                int counts = Integer.valueOf(summobileMap.get("smsCount")) + smscounts;
                                summobileMap.put("smsCount", String.valueOf(counts));
                                departmentMap.put(postString + departmentId, summobileMap);
                            } else {
                                departmentMap.put(postString + departmentId, mobileMap);
                            }
                        }

                    } catch (Exception e) {
                        logger.error(SmsCountController.class.toString(), "初始化数据失败", e);
                    }

                }

                //保存短信统计记录
                List<SmsCountCustomizeVO> insertListsms = new ArrayList<>();

                Iterator iter = departmentMap.entrySet().iterator();
                while (iter.hasNext()) {
                    SmsCountCustomizeVO smsLogCustomize1 = new SmsCountCustomizeVO();
                    Map.Entry entry = (Map.Entry) iter.next();
                    Map<String, String> mapEn = (Map<String, String>) entry.getValue();
                    smsLogCustomize1.setDepartmentId(Integer.valueOf(mapEn.get("deptId")));
                    smsLogCustomize1.setDepartmentName(mapEn.get("deptName"));
                    smsLogCustomize1.setSmsNumber(Integer.valueOf(mapEn.get("smsCount")));
                    smsLogCustomize1.setPosttime(GetDate.dataformat(mapEn.get("posttime"), "yyyy-MM-dd"));
                    insertListsms.add(smsLogCustomize1);
                }

                if (CollectionUtils.isEmpty(insertListsms)) {
                    continue;
                }

                smsCountService.insertBatchSmsCount(insertListsms);

            } catch (Exception e) {
                logger.error(SmsCountController.class.toString(), "初始化大循环数据失败", e);
            }
        }

        //修改and删除短信统计重复数据
        smsCountService.updateOrDelectRepeatData();
        long endTime = System.currentTimeMillis(); //获取结束时间
        logger.info(this.toString(), "smsAuto" + "程序运行时间： " + (endTime - startTime) + "ms");
    }

    /**
     * 拆分短信计数，（70个字符以内计一条，之后每增加67字符增加一条）
     * @param content
     * @return
     */
    public int computeSms(String content) {
        int  contentSize  = 0;//按照字数计算短信条数
        int size = content.length();
        if(size>70){
            //算法计算字数规则  小于等于70算一条 ，大于70后每67字加一条
            if((size-70)%67!=0){
                contentSize=contentSize+1;
            }
            contentSize =contentSize+ 1+(size-70)/67;
        }else {
            contentSize = 1;
        }

        return contentSize;
    }

    /**
     * 批次通过手机号码查询用户ID和推荐人ID，将 key:手机号码,value:UserVO
     * @param listSmsLog
     * @return
     */
    public Map<String,UserVO> getUserByMobile(List<SmsLogVO> listSmsLog){
        List<String> listMobile = new ArrayList<>();
        Map<String, UserVO> mapUserVo = new HashMap<>();
        //批量通过手机号码查询用户
        for (SmsLogVO dtomobile : listSmsLog) {
            if (StringUtils.isEmpty(dtomobile.getType()) || StringUtils.isEmpty(dtomobile.getMobile())) {
                continue;
            }

            String[] strSmsMobile = null;
            if (dtomobile.getMobile().contains(",")) {
                strSmsMobile = dtomobile.getMobile().split(",");
            } else {
                strSmsMobile = new String[]{dtomobile.getMobile()};
            }
            for (String mobile : strSmsMobile) {
                listMobile.add(mobile);
            }

        }
        List<UserVO> listUserVo = smsCountService.getUsersVo(listMobile);
        for (UserVO vo : listUserVo) {
            mapUserVo.put(vo.getMobile(), vo);
        }

        return mapUserVo;
    }
}
