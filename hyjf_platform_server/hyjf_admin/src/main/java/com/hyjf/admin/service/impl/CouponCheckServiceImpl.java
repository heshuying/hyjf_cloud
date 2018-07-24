/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hyjf.admin.beans.BatchSubUserCouponBean;
import com.hyjf.admin.client.CouponCheckClient;
import com.hyjf.admin.service.CouponCheckService;
import com.hyjf.am.response.admin.CouponCheckResponse;
import com.hyjf.am.resquest.admin.AdminCouponCheckRequest;
import com.hyjf.am.vo.config.CouponCheckVO;
import com.hyjf.common.file.UploadFileUtils;
import com.hyjf.common.validator.Validator;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
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

    @Autowired
    CouponCheckClient couponCheckClient;
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
        return couponCheckClient.getCouponList(adminCouponCheckRequest);
    }

    /**
     * 删除优惠券信息
     *
     * @param acr
     * @return
     */
    @Override
    public CouponCheckResponse deleteCouponList(AdminCouponCheckRequest acr) {
        return couponCheckClient.deleteCouponList(acr);
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
        CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver();
        MultipartHttpServletRequest multipartRequest = commonsMultipartResolver.resolveMultipart(request);
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
                    checkResponse = couponCheckClient.insert(accr);
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
        CouponCheckVO couponCheck = couponCheckClient.selectCoupon(Integer.valueOf(id));
        String fileP = "";
        String fileN = "";
        if (couponCheck != null) {
            fileP = couponCheck.getFilePath();
            fileN = couponCheck.getFileName();
        }

        OutputStream out = null;
        try (FileInputStream in = new FileInputStream(fileP)){
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
        }finally {
            try {
                // 关闭输出流
                if (Validator.isNotNull(out)){
                    out.flush();
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean batchCheck(Integer path, HttpServletResponse response,String userId) throws Exception {
        String[] split = String.valueOf(path).split(",");
        String filePath = split[1];
        List<String> copuncodes;
        JSONObject retResult = new JSONObject();
        List<BatchSubUserCouponBean> subBeans = new ArrayList<>();
        FileInputStream is = new FileInputStream(filePath);
        String encode = "UTF-8";
        // is.skip(3);
        // String encode = getEncoder(is);
        Reader in = new InputStreamReader(is, encode);
        Iterable<CSVRecord> records = CSVFormat.RFC4180.parse(in);
        for (CSVRecord record : records) {
            // 格式小于3,跳过
            if (record.size() < 3) {
                continue;
            }
            BatchSubUserCouponBean subBean = new BatchSubUserCouponBean();
            String userName = record.get(0);
            String activeId = record.get(1);
            String couponcode = record.get(2);
            copuncodes = Arrays.asList(couponcode.split(","));
            if (copuncodes.size() <= 0 || org.apache.commons.lang.StringUtils.isBlank(userName)) {
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

        if (subBeans.size() == 0) {
            return false;
        } else {
            // 访问API
            Map<String, String> params = new HashMap<String, String>();
            // 用户id
            params.put("usercoupons", JSON.toJSONString(subBeans));
            params.put("userId", userId);
            // 请求路径
            // TODO: 2018/7/5 给MQ发送消息，批量发送优惠券
            JSONObject result = couponCheckClient.getBatchCoupons(params);

            return StringUtils.equals(result.getString("status"), "0");
        }
    }

    @Override
    public boolean updateCoupon(AdminCouponCheckRequest request) {
        return couponCheckClient.updateCoupon(request);
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

}
