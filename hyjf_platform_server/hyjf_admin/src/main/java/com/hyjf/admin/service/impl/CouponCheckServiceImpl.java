/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.admin.client.AmConfigClient;
import com.hyjf.admin.client.AmTradeClient;
import com.hyjf.admin.client.AmUserClient;
import com.hyjf.admin.excel.ReadExcel;
import com.hyjf.admin.service.CouponCheckService;
import com.hyjf.am.response.Response;
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
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URL;
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
    AmConfigClient amConfigClient;
    @Autowired
    AmUserClient amUserClient;
    @Autowired
    AmTradeClient amTradeClient;

    @Value("${file.upload.path}")
    private String FILEUPLOADPATH;

    @Value("${file.physical.path}")
    private String PHYSICAL_PATH;

    @Value("${admin.front.host}")
    private String ADMIN_HOST;

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
    public synchronized CouponCheckResponse uploadFile(HttpServletRequest request, HttpServletResponse response) {
        CouponCheckResponse checkResponse = new CouponCheckResponse();
        String errorMessage = "";
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        String filePhysicalPath = PHYSICAL_PATH + FILEUPLOADPATH;
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        String today = format.format(date);

        String logoRealPathDir = filePhysicalPath + "/" + today;
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
            if (StringUtils.equals(suffix, ".xls") || StringUtils.equals(suffix, ".xlsx")) {
                fileRealName = fileRealName + suffix;
                try {
                    errorMessage = UploadFileUtils.upload4Stream(fileRealName, logoRealPathDir, multipartFile.getInputStream(), 5000000L);
                } catch (Exception e) {
                    logger.error("上传文件失败,失败原因：{}", e);
                }
                if (StringUtils.equals("上传文件成功！", errorMessage)) {
                    AdminCouponCheckRequest accr = new AdminCouponCheckRequest();
                    accr.setFileName(originalFilename);
                    accr.setCreateTime(String.valueOf(createTime));
                    accr.setFilePath(FILEUPLOADPATH + "/" + today + "/" + fileRealName);
                    accr.setDeFlag(0);
                    accr.setStatus(1);
                    checkResponse = amConfigClient.insert(accr);
                    checkResponse.getMessage();
                    if (checkResponse.getRecordTotal() > 0) {
                        checkResponse.setMessage(errorMessage);
                    } else {
                        checkResponse.setMessage("插入数据异常失败");
                    }
                }
            } else {
                checkResponse.setRtn(Response.FAIL);
                checkResponse.setMessage("上传失败，请上传Excel文件");
            }
        }
        return checkResponse;
    }

    @Override
    public void downloadFile(String id, HttpServletResponse response) {
        BufferedInputStream in;
        OutputStream out;
        CouponCheckVO couponCheck = amConfigClient.selectCoupon(Integer.valueOf(id));
        String filePath = "";
        String fileName = "";
        if (couponCheck != null) {
            filePath = couponCheck.getFilePath();
            fileName = couponCheck.getFileName();
        }
        try {
            response.setHeader("content-disposition",
                    "attachment;filename=" + URLEncoder.encode(fileName, "utf-8"));
            logger.info("ADMIN_HOST is : {}", ADMIN_HOST);
            String path = ADMIN_HOST + filePath;
            logger.info("path is : {}", path);
            URL url = new URL(path);
            in = new BufferedInputStream(url.openStream());
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
            // 关闭文件流
            in.close();
            // 关闭输出流
            out.close();
        } catch (Exception e) {
            logger.error("下载失败, 失败原因 ：", e);
        }
    }

    @Override
    public boolean batchCheck(String path, HttpServletResponse response, String loginUserId) throws Exception {
        try {
            String[] split = path.split(",");
            String filePath = ADMIN_HOST + split[1];
            URL url = new URL(filePath);
            Map<String, String> nameMaps = new HashMap<>();
            nameMaps.put("userName", "userName");
            nameMaps.put("activityId", "activityId");
            nameMaps.put("couponCode", "couponCode");
            ReadExcel readExcel = new ReadExcel();
            List<JSONObject> list = new ArrayList<>();
            try {
                list = readExcel.readExcel(url.openStream(), nameMaps);
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
                        batchInsertUserCoupon(userName, copuncodes, totalcouponCount, succouponCount, activityId, couponSource, loginUserId);

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

    private boolean batchInsertUserCoupon(String userName, List<String> copuncodes, int totalcouponCount, int succouponCount, Integer activityId, int couponSource, String loginUserId) {
        UserVO user = this.getUserByUserName(userName);
        logger.info("批量发放优惠券User：" + user);
        if (user == null) {
            return false;
        }
        if (copuncodes == null || copuncodes.isEmpty()) {
            return false;
        }
        totalcouponCount = totalcouponCount + copuncodes.size();
        // 发放优惠券
        int couponCount = 0;
        try {
            couponCount = this.sendConponAction(copuncodes, String.valueOf(user.getUserId()), activityId, couponSource, loginUserId);
        } catch (Exception e) {
            logger.error("用户：" + userName + "发送优惠券失败！", e);
        }
        succouponCount = succouponCount + couponCount;
        logger.info(user.getUserId() + " 发放优惠券：" + couponCount + " 张");
        return true;
    }

    private synchronized int sendConponAction(List<String> couponCodeList, String userId, Integer activityId, int couponSource, String loginUserId) throws Exception {
        // sendflg设置1跳过活动id不设置的逻辑
        return sendUserConponAction(couponCodeList, userId, 1, activityId, couponSource, loginUserId, "上传csv文件，批量发券");
    }

    public int sendUserConponAction(List<String> couponCodeList, String userId, Integer sendFlg, Integer activityId,
                                    Integer couponSource, String loginUserId, String content) throws Exception {
        logger.info("用户：" + userId + ",执行发券逻辑开始  " + GetDate.dateToString(new Date()));
        String methodName = "sendConponAction";
        int nowTime = GetDate.getNowTime10();
        // String couponGroupCode = CreateUUID.createUUID();

        UserInfoVO userInfo = this.getUsersInfoByUserId(Integer.parseInt(userId));
        if (userInfo == null) {
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
                CouponConfigVO config = configResponse.getResult();
                if (config == null) {
                    continue;
                }
//                CouponConfigVO config = configList.get(0);

                Integer status = config.getStatus();
                if (status == null || status == 1 || status == 3) {
                    logger.info("优惠券审核未通过，无法发放！（coupon）" + couponCode);
                    continue;
                }
                // 加息券编号
                couponUser.setCouponUserCode(GetCode.getCouponUserCode(config.getCouponType()));

                if (config.getExpirationType() == 1) { // 截止日
                    couponUser.setEndTime(config.getExpirationDate());
                } else if (config.getExpirationType() == 2) { // 时长
                    couponUser.setEndTime((int) (GetDate.countDate(2, config.getExpirationLength()).getTime() / 1000));
                } else if (config.getExpirationType() == 3) {
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
                couponUser.setContent(StringUtils.isEmpty(content) ? "" : content);
                CouponUserRequest couponUserRequest = new CouponUserRequest();
                BeanUtils.copyProperties(couponUser, couponUserRequest);
                couponUserRequest.setCreateUserId(Integer.parseInt(loginUserId));
                couponUserRequest.setUpdateUserId(Integer.parseInt(loginUserId));
                CouponUserResponse response = amTradeClient.insertCouponUser(couponUserRequest);
                couponCount++;
            }
            logger.info("发放优惠券成功，发放张数：" + couponCount);
        }
        logger.info("用户：" + userId + ",执行发券逻辑结束  " + GetDate.dateToString(new Date()));
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

    @Override
    public CouponCheckVO getCouponCheckById(int id) {
        return amConfigClient.selectCoupon(id);
    }


    /**
     * 根据用户名获取用户
     *
     * @param userName
     * @return
     */
    public UserVO getUserByUserName(String userName) {
        if (StringUtils.isEmpty(userName)) {
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
     * 获取用户注册时的渠道名称
     *
     * @param userId
     * @return
     * @author hsy
     */
    public String getChannelNameByUserId(Integer userId) {
        UtmResponse response = amUserClient.getChannelNameByUserId(userId);
        return response.getChannelName();
    }

    /**
     * 校验优惠券的已发行数量
     *
     * @return
     */
    @Override
    public boolean checkSendNum(String couponCode) {
        CouponRecoverCustomizeResponse response = amTradeClient.checkCouponSendExcess(couponCode);
        int remain = response.getCount();
        return remain > 0 ? true : false;
    }

}
