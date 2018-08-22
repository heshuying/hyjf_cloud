/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.admin.excel.ReadExcel;
import com.hyjf.admin.client.AmConfigClient;
import com.hyjf.admin.client.AmTradeClient;
import com.hyjf.admin.client.AmUserClient;
import com.hyjf.admin.client.CouponCheckClient;
import com.hyjf.admin.service.CouponCheckService;
import com.hyjf.am.response.admin.CouponCheckResponse;
import com.hyjf.am.response.admin.UtmResponse;
import com.hyjf.am.response.trade.CouponConfigResponse;
import com.hyjf.am.response.trade.CouponRecoverCustomizeResponse;
import com.hyjf.am.response.trade.CouponUserResponse;
import com.hyjf.am.resquest.admin.AdminCouponCheckRequest;
import com.hyjf.am.resquest.admin.CouponUserRequest;
import com.hyjf.am.vo.config.CouponCheckVO;
import com.hyjf.am.vo.config.ParamNameVO;
import com.hyjf.am.vo.trade.coupon.CouponConfigVO;
import com.hyjf.am.vo.trade.coupon.CouponUserVO;
import com.hyjf.am.vo.user.UserInfoVO;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.common.file.UploadFileUtils;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.util.GetCode;
import com.hyjf.common.util.GetDate;
import com.hyjf.common.validator.Validator;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author yaoyong
 * @version CouponCheckServiceImpl, v0.1 2018/7/3 16:34
 */
@Service
public class CouponCheckServiceImpl implements CouponCheckService {
    private static final Logger logger = LoggerFactory.getLogger(CouponCheckServiceImpl.class);

    @Autowired
    CouponCheckClient couponCheckClient;
    @Autowired
    AmConfigClient amConfigClient;
    @Autowired
    AmUserClient amUserClient;
    @Autowired
    AmTradeClient amTradeClient;
    @Value("${file.upload.activity.img.path}")
    private String FILEUPLOADTEMPPATH;

    /**
     * 查询优惠券列表
     *
     * @param adminCouponCheckRequest
     * @return
     */
    @Override
    public CouponCheckResponse serchCouponList(AdminCouponCheckRequest adminCouponCheckRequest) {
        return amConfigClient.getCouponList(adminCouponCheckRequest);
    }

    /**
     * 删除优惠券信息
     *
     * @param acr
     * @return
     */
    @Override
    public CouponCheckResponse deleteCouponList(AdminCouponCheckRequest acr) {
        return amConfigClient.deleteCouponList(acr);
    }

    /**
     * 上传文件
     *
     * @param request
     * @param response
     * @return
     */
    @Override
    public CouponCheckResponse uploadFile(HttpServletRequest request, HttpServletResponse response) {
        CouponCheckResponse checkResponse = new CouponCheckResponse();
        String errorMessage = "";
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest)request;
        String filePhysicalPath = UploadFileUtils.getDoPath(FILEUPLOADTEMPPATH);
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        String today = format.format(date);

        String logoRealPathDir = filePhysicalPath + today;
        File logoSaveFile = new File(logoRealPathDir);
        if (!logoSaveFile.exists()) {
            logoSaveFile.mkdirs();
        }
        Iterator<String> itr = multipartRequest.getFileNames();
        MultipartFile multipartFile = null;
        while (itr.hasNext()) {
            multipartFile = multipartRequest.getFile(itr.next());
            String fileRealName = String.valueOf(System.currentTimeMillis() / 1000);
            Integer createTime = Integer.valueOf(fileRealName);
            String originalFilename = multipartFile.getOriginalFilename();
            String suffix = UploadFileUtils.getSuffix(multipartFile.getOriginalFilename());
            if (StringUtils.equals(suffix, ".csv")) {
                fileRealName = fileRealName + suffix;
                try {
                    errorMessage = UploadFileUtils.upload4Stream(fileRealName, logoRealPathDir, multipartFile.getInputStream(), 5000000L);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (StringUtils.equals("上传文件成功！", errorMessage)) {
                    AdminCouponCheckRequest accr = new AdminCouponCheckRequest();
                    accr.setFileName(originalFilename);
                    accr.setCreateTime(String.valueOf(createTime));
                    accr.setFilePath(logoRealPathDir + "/" + fileRealName);
                    accr.setDeFlag(0);
                    accr.setStatus(1);
                    checkResponse = amConfigClient.insert(accr);
                    checkResponse.getMessage();
                }
            } else {
                checkResponse.setMessage("上传失败，文件后缀必须为CSV");
            }
        }
        return checkResponse;
    }

    @Override
    public void downloadFile(String id, HttpServletResponse response) {
        CouponCheckVO couponCheck = amConfigClient.selectCoupon(Integer.valueOf(id));
        String fileP = "";
        String fileN = "";
        if (couponCheck != null) {
            fileP = couponCheck.getFilePath();
            fileN = couponCheck.getFileName();
        }

        OutputStream out = null;
        try (FileInputStream in = new FileInputStream(fileP)) {
            response.setHeader("content-disposition",
                    "attachment;filename=" + URLEncoder.encode(fileN, "utf-8"));

            // 创建输出流
            out = response.getOutputStream();
            // 创建缓冲区
            byte buffer[] = new byte[1024];
            int len = 0;
            // 循环将输入流中的内容读取到缓冲区中
            while ((len = in.read(buffer)) > 0) {
                // 输出缓冲区内容到浏览器，实现文件下载
                out.write(buffer, 0, len);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                // 关闭输出流
                if (Validator.isNotNull(out)) {
                    out.flush();
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean batchCheck(Integer path, HttpServletResponse response, String userId) throws Exception {
        try {
            String[] split = String.valueOf(path).split(",");
            String filePath = split[1];
            Map<String, String> nameMaps = new HashMap<>();
            nameMaps.put("couponCode", "couponCode");
            nameMaps.put("activityId", "activityId");
            nameMaps.put("userName", "userName");
            ReadExcel readExcel = new ReadExcel();
            List<JSONObject> list = new ArrayList<>();
            try {
                list = readExcel.readExcel(filePath, nameMaps);
            } catch (IOException e) {
                logger.error("批量发送优惠券，解析Excel：" + filePath + "失败！", e);
                return false;
            }
            final List<JSONObject> lists = list;
            Thread t = new Thread(new Runnable() {
                public void run() {
                    // run方法具体重写
                    int totalcouponCount = 0;
                    int succouponCount = 0;

                    // 优惠券来源1：手动发放，2：活动发放，3：vip礼包
                    int couponSource = 2;
                    logger.info("批量发放优惠券开始： 预计" + lists.size() + " 张");
                    for (JSONObject jsonObject : lists) {
                        List<String> copuncodes;
                        String userName = jsonObject.getString("userName");
                        String activeId = jsonObject.getString("activityId");
                        String couponcode = jsonObject.getString("couponCode");
                        copuncodes = Arrays.asList(couponcode.split(","));
                        if (copuncodes.size() <= 0 || org.apache.commons.lang.StringUtils.isBlank(userName)) {
                            continue;
                        }
                        Integer activityId = null;
                        if (StringUtils.isNotBlank(activeId)) {
                            activityId = Integer.parseInt(activeId.trim());
                        }
//                        String userId = userCouponBean.getUserId();
                        logger.info("批量发放优惠券当前用户名：" + userName);
                        if (StringUtils.isBlank(userName)) {
                            continue;
                        }
                        batchInsertUserCoupon(userName, copuncodes, totalcouponCount, succouponCount, activityId, couponSource);

                    }
                }
            });
            t.start();

        } catch (Exception e) {
            logger.error("优惠券发放异常", e);
            return false;
        }
        return true;

    }

    private boolean batchInsertUserCoupon(String userName, List<String> copuncodes, int totalcouponCount, int succouponCount, Integer activityId, int couponSource) {
        UserVO user = this.getUserByUserName(userName);
        logger.info("批量发放优惠券User：" + user);
        if(user == null){
            return false;
        }
        if(copuncodes ==null || copuncodes.isEmpty()){
            return false;
        }
        totalcouponCount = totalcouponCount+copuncodes.size();
        // 发放优惠券
        int couponCount = 0;
        try {
            couponCount = this.sendConponAction(copuncodes, String.valueOf(user.getUserId()), activityId, couponSource);
        } catch (Exception e) {
            logger.error("用户："+userName + "发送优惠券失败！",e);
            e.printStackTrace();
        }
        succouponCount = succouponCount + couponCount;
        logger.info(user.getUserId()+ " 发放优惠券：" + couponCount + " 张");
        return true;
    }

    private synchronized int sendConponAction(List<String> couponCodeList, String userId, Integer activityId, int couponSource) throws Exception {
        // sendflg设置1跳过活动id不设置的逻辑
        return sendUserConponAction(couponCodeList, userId, 1, activityId, couponSource,"上传csv文件，批量发券");
    }

    public int sendUserConponAction(List<String> couponCodeList, String userId, Integer sendFlg, Integer activityId,
                                    Integer couponSource, String content) throws Exception {
        logger.info("用户："+userId+",执行发券逻辑开始  " + GetDate.dateToString(new Date()));
        String methodName = "sendConponAction";
        int nowTime = GetDate.getNowTime10();
        // String couponGroupCode = CreateUUID.createUUID();

        UserInfoVO userInfo = this.getUsersInfoByUserId(Integer.parseInt(userId));
        if(userInfo == null){
            return 0;
        }

        String channelName = this.getChannelNameByUserId(Integer.parseInt(userId));

        int couponCount = 0;
        if (couponCodeList != null && couponCodeList.size() > 0) {
            for (String couponCode : couponCodeList) {
                // 如果优惠券的发行数量已大于等于配置的发行数量，则不在发放该类别优惠券
                if (!this.checkSendNum(couponCode)) {
                    logger.info("优惠券发行数量超出上限，不再发放！");
                    continue;
                }
                CouponUserVO couponUser = new CouponUserVO();
                couponUser.setCouponCode(couponCode);
                if (StringUtils.contains(couponCode, "PT")) {
                    // 体验金编号
                    couponUser.setCouponUserCode(GetCode.getCouponUserCode(1));
                } else if (StringUtils.contains(couponCode, "PJ")) {
                    // 加息券编号
                    couponUser.setCouponUserCode(GetCode.getCouponUserCode(2));
                } else if (StringUtils.contains(couponCode, "PD")) {
                    // 代金券编号
                    couponUser.setCouponUserCode(GetCode.getCouponUserCode(3));
                }
                // 优惠券组编号
                // couponUser.setCouponGroupCode(couponGroupCode);
                couponUser.setUserId(Integer.parseInt(userId));
                if (Validator.isNotNull(sendFlg) && sendFlg != 2 && Validator.isNotNull(activityId)) {
                    // 购买vip与活动无关
                    couponUser.setActivityId(activityId);
                }
                couponUser.setUsedFlag(CustomConstants.USER_COUPON_STATUS_UNUSED);

                // 根据优惠券编码查询优惠券
                CouponConfigResponse configResponse = amTradeClient.selectCouponConfig(couponCode);
                List<CouponConfigVO> configList = configResponse.getResultList();
                if (configList == null || configList.isEmpty()) {
                    continue;
                }
                CouponConfigVO config = configList.get(0);

                Integer status = config.getStatus();
                if(status==null||status==1||status==3){
                    logger.info("优惠券审核未通过，无法发放！（coupon）"+couponCode);
                    continue;
                }
                // 加息券编号
                couponUser.setCouponUserCode(GetCode.getCouponUserCode(config.getCouponType()));

                if (config.getExpirationType() == 1) { // 截止日
                    couponUser.setEndTime(config.getExpirationDate());
                } else if(config.getExpirationType() == 2) { // 时长
                    couponUser.setEndTime((int) (GetDate.countDate(2, config.getExpirationLength()).getTime() / 1000));
                } else if(config.getExpirationType() == 3){
                    couponUser.setEndTime((int) (GetDate.countDate(5, config.getExpirationLengthDay()).getTime() / 1000));
                }
                couponUser.setCouponSource(couponSource);
                couponUser.setAddTime(nowTime);
                couponUser.setAddUser(CustomConstants.OPERATOR_AUTO_REPAY);
                couponUser.setUpdateTime(nowTime);
                couponUser.setUpdateUser(CustomConstants.OPERATOR_AUTO_REPAY);
                couponUser.setDelFlag(CustomConstants.FALG_NOR);
                couponUser.setChannel(channelName);
                couponUser.setAttribute(userInfo.getAttribute());
                couponUser.setContent(StringUtils.isEmpty(content)?"":content);
                CouponUserRequest couponUserRequest = new CouponUserRequest();
                BeanUtils.copyProperties(couponUser,couponUserRequest);
                CouponUserResponse response = amTradeClient.insertCouponUser(couponUserRequest);
                couponCount++;
            }
            logger.info("发放优惠券成功，发放张数：" + couponCount);
        }
        logger.info("用户："+userId+",执行发券逻辑结束  " + GetDate.dateToString(new Date()));
        return couponCount;
    }

    @Override
    public boolean updateCoupon(AdminCouponCheckRequest request) {
        CouponCheckResponse response = amConfigClient.updateCoupon(request);
        boolean result = response.isBool();
        return result;
    }

    @Override
    public List<ParamNameVO> getParamNameList(String nameClass) {
        return amConfigClient.getParamNameList(nameClass);
    }


    /**
     * 根据用户名获取用户
     * @param userName
     * @return
     */
    public UserVO getUserByUserName(String userName){
        if(StringUtils.isEmpty(userName)){
            return null;
        }
        UserVO user = amUserClient.getUserByUserName(userName);
        return user;
    }

    /**
     * 根据用户ID取得用户信息
     *
     * @param userId
     * @return
     */
    public UserInfoVO getUsersInfoByUserId(Integer userId) {
        if (userId != null) {
            UserInfoVO userInfoVO = amUserClient.selectUsersInfoByUserId(userId);
            if (userInfoVO != null) {
                return userInfoVO;
            }
        }
        return null;
    }

    /**
     *
     * 获取用户注册时的渠道名称
     * @author hsy
     * @param userId
     * @return
     */
    public String getChannelNameByUserId(Integer userId){
        UtmResponse response = amUserClient.getChannelNameByUserId(userId);
        return response.getChannelName();
    }

    /**
     * 校验优惠券的已发行数量
     *
     * @return
     */
    private boolean checkSendNum(String couponCode) {
        CouponRecoverCustomizeResponse response = amTradeClient.checkCouponSendExcess(couponCode);
        int remain = response.getCount();
        return remain > 0 ? true : false;
    }


    /**
     * 批量上传发券接口
     */
//    public static JSONObject getBatchCoupons(String userId,Map<String,String> paramsMap) {
//        String timestamp = String.valueOf(GetDate.getNowTime10());
//        String chkValue = StringUtils.lowerCase(MD5.toMD5Code(SOA_COUPON_KEY + userId + timestamp + SOA_COUPON_KEY));
//        Map<String, String> params = new HashMap<String, String>();
//
//        // 用户id
//        params.put("userId", userId+"");
//        // 时间戳
//        params.put("timestamp", timestamp);
//        // 签名
//        params.put("sign", chkValue);
//        // 数据
//        params.put("usercoupons", paramsMap.get("usercoupons"));
//        // 请求路径
//        String requestUrl = PropUtils.getSystem(PropertiesConstants.HYJF_API_WEB_URL) + CommonSoaUtils.COUPON_BATCH_SEND_USER;
//        String result = HttpClientUtils.post(requestUrl, params);
//        JSONObject status = JSONObject.parseObject(result);
//        return status;
//    }

    @Bean(name = "multipartResolver")

    public MultipartResolver multipartResolver(){
        CommonsMultipartResolver resolver = new CommonsMultipartResolver();
        resolver.setDefaultEncoding("UTF-8");
        resolver.setResolveLazily(true);//resolveLazily属性启用是为了推迟文件解析，以在在UploadAction中捕获文件大小异常
        resolver.setMaxInMemorySize(40960);
        resolver.setMaxUploadSize(50*1024*1024);//上传文件大小 50M 50*1024*1024
        return resolver;
    }

}
