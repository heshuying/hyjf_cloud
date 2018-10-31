package com.hyjf.cs.trade.controller.web.coupon;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.resquest.trade.AppCouponRequest;
import com.hyjf.am.vo.trade.coupon.MyCouponListCustomizeVO;
import com.hyjf.am.vo.user.WebViewUserVO;
import com.hyjf.common.file.UploadFileUtils;
import com.hyjf.common.util.QRCodeUtil;
import com.hyjf.common.validator.Validator;
import com.hyjf.cs.common.bean.result.WebResult;
import com.hyjf.cs.trade.config.SystemConfig;
import com.hyjf.cs.trade.service.coupon.AppCouponService;
import com.hyjf.cs.trade.service.coupon.MyCouponListService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 我的优惠券列表
 * @author hesy
 * @version MyCouponListController, v0.1 2018/6/23 14:09
 */
@Api(value = "web端-我的优惠券列表", tags = "web端-我的优惠券列表")
@RestController
@RequestMapping("/hyjf-web/coupon")
public class MyCouponListController {
    private static final Logger logger = LoggerFactory.getLogger(MyCouponListController.class);

    @Autowired
    MyCouponListService myCouponListService;
    @Autowired
    private AppCouponService appCouponService;
    @Autowired
    private SystemConfig systemConfig;

    /**
     * 我的优惠券列表
     * @auther: hesy
     * @date: 2018/6/23
     */
    @ApiOperation(value = "获取我的优惠券列表", notes = "我的优惠券列表")
    @PostMapping(value = "/myCouponList", produces = "application/json; charset=utf-8")
    public WebResult<Map<String,Object>> selectMyCouponList(@RequestHeader(value = "userId") Integer userId, HttpServletRequest request){
        WebResult<Map<String,Object>> result = new WebResult<>();
        WebViewUserVO userVO = myCouponListService.getUserFromCache(userId);
        logger.info("获取我的优惠券列表数据开始，userId:{}", userVO.getUserId());

//        try {
//            QRCodeUtil.encode(systemConfig.getWechatInviteUrl() +userId+".html",String.valueOf(userId),systemConfig.getFilePhysicalPath() + systemConfig.getFileUploadRealPath(), false);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        String downloadUrl = systemConfig.webHost + "/hyjf-web/coupon/download/" + userId;
        logger.info("二维码下载地址：" + downloadUrl);

        try {
            List<MyCouponListCustomizeVO> listUnUsed = myCouponListService.selectMyCouponListUnUsed(String.valueOf(userVO.getUserId()));
            List<MyCouponListCustomizeVO> listUsed = myCouponListService.selectMyCouponListUsed(String.valueOf(userVO.getUserId()));
            List<MyCouponListCustomizeVO> listInValid = myCouponListService.selectMyCouponListInValid(String.valueOf(userVO.getUserId()));
            Map<String,String> pageData = myCouponListService.selectInvitePageData(String.valueOf(userVO.getUserId()));
            Map<String,Object> resultMap = new HashMap<>();
            resultMap.put("wsyList", listUnUsed);
            resultMap.put("ysyList", listUsed);
            resultMap.put("ysxList", listInValid);
            resultMap.put("downloadUrl", downloadUrl);
            resultMap.putAll(pageData);
            result.setData(resultMap);
        } catch (Exception e) {
            result.setStatus(WebResult.ERROR);
            result.setStatusDesc(WebResult.ERROR_DESC);
            logger.error("查询优惠券列表异常", e);
        }

        return result;
    }

    /**
     * 下载二维码
     */
    @GetMapping("/download/{userId}")
    public void download(@PathVariable  Integer userId, HttpServletRequest request, HttpServletResponse response){
        logger.info("开始生成二维码图片，userId：" + userId);
        try {
            QRCodeUtil.encode(systemConfig.getWechatInviteUrl() +userId+".html",String.valueOf(userId),systemConfig.getFilePhysicalPath() + systemConfig.getFileUploadRealPath(), false);
        } catch (Exception e) {
            e.printStackTrace();
        }

        String webhost = UploadFileUtils.getDoPath(systemConfig.getFileDomainUrl());
        String fileUploadRealPath = UploadFileUtils.getDoPath(systemConfig.getFileUploadRealPath());
        logger.info("开始下载二维码：userid：" + userId + " webhost: " + webhost +" fileUploadRealPath: " + fileUploadRealPath);
        try {
            logger.info("download url: " + webhost + fileUploadRealPath+String.valueOf(userId)+".jpg");
            this.getServletFile(request, response, webhost + fileUploadRealPath+String.valueOf(userId)+".jpg", String.valueOf(userId)+".jpg");
        } catch (Exception e) {
            logger.error("下载二维码异常", e);
        }
    }

    /**
     * 通过HTTP方式获取文件,文件类型.jpg
     *
     * @param strUrl
     * @param fileName
     * @return
     * @throws IOException
     */
    public boolean getServletFile(HttpServletRequest request, HttpServletResponse response, String strUrl, String fileName) throws IOException {

        URL url = new URL(strUrl);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        // 设置是否向httpUrlConnection输出，因为这个是post请求，参数要放在
        // http正文内，因此需要设为true, 默认情况下是false;
        conn.setDoOutput(true);
        // 设置是否从httpUrlConnection读入，默认情况下是true;
        conn.setDoInput(true);
        // Post 请求不能使用缓存
        conn.setUseCaches(false);
        conn.setRequestProperty("Content-type", "application/x-java-serialized-object");
        conn.setRequestProperty("Accept-Charset", "UTF-8");
        // 设定请求的方法为"POST"，默认是GET
        conn.setRequestMethod("GET");
        DataInputStream input =null;
        ServletOutputStream output = null;
        byte[] buffer = new byte[1024];
        int count = 0;
        try {
            output = response.getOutputStream();
            response.reset();
            response.setContentType("image/jpeg;charset=utf-8");
            response.setHeader("content-disposition", "attachment;filename=" + new String((fileName).getBytes("UTF-8"), "ISO8859-1"));
            input=new DataInputStream(conn.getInputStream());
            while ((count = input.read(buffer)) != -1) {
                output.write(buffer, 0, count);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            output.flush();
            output.close();
            input.close();
        }
        return true;
    }

    @ApiOperation(value = "web根据borrowNid和用户id获取用户可用优惠券和不可用优惠券列表", notes = "根据borrowNid和用户id获取用户可用优惠券和不可用优惠券列表")
    @PostMapping("/getborrowcoupon")
    public JSONObject getProjectAvailableUserCoupon(@RequestHeader(value = "userId") Integer userId,@RequestBody AppCouponRequest appCouponRequest) throws Exception {
        JSONObject ret = new JSONObject();
        // 检查参数正确性
        String borrowNid = appCouponRequest.getBorrowNid();
        String investType = appCouponRequest.getBorrowType();
        String money = appCouponRequest.getMoney();
        String platform = appCouponRequest.getPlatform();
        if ( Validator.isNull(borrowNid)||  Validator.isNull(platform)) {
            ret.put("status", "1");
            ret.put("statusDesc", "请求参数非法");
            return ret;
        }
        if(money==null||"".equals(money)||money.length()==0){
            money="0";
        }
        logger.info("investType is :{}", investType);
        JSONObject json = new JSONObject();
        if(investType != null){

            if(investType==null||!"HJH".equals(investType)){
                // 如果为空  就执行以前的逻辑
                json = appCouponService.getBorrowCoupon(userId,borrowNid,money,platform);
            }else{
                // HJH的接口
                json = appCouponService.getPlanCoupon(userId,borrowNid,money,platform);
            }
        }else {
            if(borrowNid.contains("HJH")){
                json = appCouponService.getPlanCoupon(userId,borrowNid,money,platform);
            }else{
                // 如果为空  就执行以前的逻辑
                json = appCouponService.getBorrowCoupon(userId,borrowNid,money,platform);
            }
        }
        ret.put("status","000");
        ret.put("statusDesc","成功");
        ret.put("data",json);
        return ret;
    }
}
