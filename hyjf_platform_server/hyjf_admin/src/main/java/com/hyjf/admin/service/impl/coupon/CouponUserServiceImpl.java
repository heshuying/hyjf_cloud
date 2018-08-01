/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.service.impl.coupon;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hyjf.admin.beans.BatchSubUserCouponBean;
import com.hyjf.admin.client.CouponUserClient;
import com.hyjf.admin.service.coupon.CouponUserService;
import com.hyjf.am.response.admin.AdminCouponUserCustomizeResponse;
import com.hyjf.am.response.admin.CouponTenderResponse;
import com.hyjf.am.response.admin.CouponUserCustomizeResponse;
import com.hyjf.am.response.admin.UtmResponse;
import com.hyjf.am.response.trade.CouponConfigResponse;
import com.hyjf.am.response.trade.CouponUserResponse;
import com.hyjf.am.response.user.UserInfoResponse;
import com.hyjf.am.response.user.UserResponse;
import com.hyjf.am.resquest.admin.AdminCouponUserRequestBean;
import com.hyjf.am.resquest.admin.CouponConfigRequest;
import com.hyjf.am.resquest.admin.CouponUserBeanRequest;
import com.hyjf.am.resquest.admin.CouponUserRequest;
import com.hyjf.am.resquest.market.ActivityListRequest;
import com.hyjf.am.vo.admin.ActivityListCustomizeVO;
import com.hyjf.am.vo.admin.CouponConfigCustomizeVO;
import com.hyjf.am.vo.admin.coupon.CouponRecoverVO;
import com.hyjf.am.vo.admin.coupon.CouponTenderDetailVo;
import com.hyjf.am.vo.trade.coupon.CouponUserVO;
import com.hyjf.soa.apiweb.CommonSoaUtils;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.*;

/**
 * @author yaoyong
 * @version CouponUserServiceImpl, v0.1 2018/7/23 16:10
 */
@Service
public class CouponUserServiceImpl implements CouponUserService {

    private final Logger logger = LoggerFactory.getLogger(CouponUserServiceImpl.class);
    @Autowired
    private CouponUserClient couponUserClient;


    /**
     * 获取优惠券用户列表
     *
     * @param couponUserBeanRequest
     * @return
     */
    @Override
    public CouponUserCustomizeResponse searchList(CouponUserBeanRequest couponUserBeanRequest) {
        return couponUserClient.searchList(couponUserBeanRequest);
    }

    /**
     * 根据id删除一条优惠券
     *
     * @param id
     * @return
     */
    @Override
    public CouponUserCustomizeResponse deleteById(int id, String remark, String userId) {
        return couponUserClient.deleteById(id, remark, userId);
    }

    /**
     * 获取优惠券配置
     * @param request
     * @return
     */
    @Override
    public AdminCouponUserCustomizeResponse getRecordList(CouponConfigRequest request) {
        AdminCouponUserCustomizeResponse response = new AdminCouponUserCustomizeResponse();
        List<CouponConfigCustomizeVO> adminCouponUserCustomizeVOS = couponUserClient.getCouponConfig(request);
        List<ActivityListCustomizeVO> activityListCustomizeVOS = couponUserClient.getActivityList(new ActivityListRequest());
        response.getResult().setCouponConfigCustomizeVOS(adminCouponUserCustomizeVOS);
        response.getResult().setActivityListCustomizeVOS(activityListCustomizeVOS);
        return response;
    }

    /**
     * 根据用户名获取用户
     * @param userName
     * @return
     */
    @Override
    public UserResponse getUser(String userName) {
        return couponUserClient.getUserByUserName(userName);
    }

    /**
     * 根据用户id获取用户详情信息
     * @param userId
     * @return
     */
    @Override
    public UserInfoResponse getUserInfo(Integer userId) {
        return couponUserClient.getUserInfoByUserId(userId);
    }

    /**
     * 获取注册时的用户渠道
     * @param userId
     * @return
     */
    @Override
    public UtmResponse getChannelName(Integer userId) {
        return couponUserClient.getChannelNameByUserId(userId);
    }

    /**
     * 根据优惠券编码查询优惠券
     * @param couponCode
     * @return
     */
    @Override
    public CouponConfigResponse getCouponConfig(String couponCode) {
        return couponUserClient.selectCouponConfig(couponCode);
    }

    /**
     * 发放一条优惠券
     * @param couponUserRequest
     * @return
     */
    @Override
    public CouponUserResponse insertCouponUser(CouponUserRequest couponUserRequest) {
        return couponUserClient.insertCouponUser(couponUserRequest);
    }

    /**
     * 根据优惠券编码查询优惠券用户
     * @param couponCode
     * @return
     */
    @Override
    public CouponUserResponse getCouponUserByCouponCode(String couponCode) {
        return couponUserClient.getCouponUserByCouponCode(couponCode);
    }

    /**
     *根据条件查询优惠券使用详情
     * @param paramMap
     * @return
     */
    @Override
    public CouponTenderDetailVo getCouponTenderDetailCustomize(Map<String,Object> paramMap) {
        CouponTenderResponse response = couponUserClient.getCouponTenderDetailCustomize(paramMap);
        return response.getDetail();
    }

    /**
     * 查询回款列表
     * @param paramMap
     * @return
     */
    @Override
    public List<CouponRecoverVO> getCouponRecoverCustomize(Map<String, Object> paramMap) {
        CouponTenderResponse couponTenderResponse = couponUserClient.getCouponRecoverCustomize(paramMap);
        if(null != couponTenderResponse){
            return couponTenderResponse.getCouponRecoverList();
        }
        return null;
    }

    /**
     * 根据id查询用户优惠券
     * @param couponUserId
     * @return
     */
    @Override
    public CouponUserVO selectCouponUserById(Integer couponUserId) {
        CouponUserCustomizeResponse response = couponUserClient.selectCouponUserById(couponUserId);
        if (null != response) {
            return response.getCouponUser();
        }
        return null;
    }

    /**
     *用户优惠券审批
     * @param adminCouponUserRequestBean
     * @return
     */
    @Override
    public CouponUserCustomizeResponse auditRecord(AdminCouponUserRequestBean adminCouponUserRequestBean) {
        return couponUserClient.auditRecord(adminCouponUserRequestBean);
    }

    /**
     * 手动批量发券上传
     * @param request
     * @param response
     * @return
     */
    @Override
    public String uploadFile(HttpServletRequest request, HttpServletResponse response, String loginUserId) throws IOException {
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        Iterator<String> itr = multipartRequest.getFileNames();
        MultipartFile multipartFile = null;
        JSONObject retResult = new JSONObject();

        List<BatchSubUserCouponBean> subBeans = new ArrayList<BatchSubUserCouponBean>();

        while (itr.hasNext()) {
            multipartFile = multipartRequest.getFile(itr.next());
            InputStream is = multipartFile.getInputStream();
            String encode = getEncoder(is);
            logger.info("优惠券批量导入编码格式：" + encode);
            Reader in = new InputStreamReader(is, encode);
            Iterable<CSVRecord> records = CSVFormat.RFC4180.parse(in);

            for (CSVRecord record : records) {

                // 格式小于3,跳过
                if(record.size() < 3){
                    continue;
                }
                BatchSubUserCouponBean subBean = new BatchSubUserCouponBean();
//				String userId = record.get(0);
                String userName = record.get(0);
                String activeId = record.get(1);
                String couponcode  = record.get(2);
                logger.info("优惠券批量导入username: " + userName);

                List<String> copuncodes = Arrays.asList(couponcode.split(","));

                if(copuncodes.size() <= 0 || StringUtils.isBlank(userName)){
                    continue;
                }

                subBean.setUserName(userName);
                subBean.setActivityId(activeId);
                subBean.setCouponCode(copuncodes);

                subBeans.add(subBean);
            }

            //关闭输入流
            in.close();
            is.close();

            // 只处理一个文件
            break;

        }

        if (subBeans.size() == 0) {
            retResult.put("status", 0);
            retResult.put("totalcouponCount", 0);
            retResult.put("couponCount", 0);
            return JSONObject.toJSONString(retResult, true);
        }else{
            // 访问API
            Map<String, String> params = new HashMap<String, String>();
            // 用户id
            params.put("usercoupons", JSON.toJSONString(subBeans));
            params.put("userId",loginUserId);

            // 请求路径
            JSONObject result = couponUserClient.getBatchCoupons(params);
            return JSONObject.toJSONString(result, true);
        }

    }

    private static String getEncoder(InputStream in) throws IOException {
        in.mark(100000);
        BufferedInputStream bin = new BufferedInputStream(in);
        int p = (bin.read() << 8) + bin.read();
        String code = null;
        in.reset();

        switch (p) {
            case 0xefbb:
                code = "UTF-8";
                in.skip(3);
                break;
            case 0xfffe:
                code = "Unicode";
                break;
            case 0xfeff:
                code = "UTF-16BE";
                in.skip(3);
                break;
            default:
                code = "GBK";
        }
        return code;
    }



}
