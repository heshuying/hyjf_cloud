package com.hyjf.cs.user.controller.web.invite;

import com.hyjf.am.vo.user.MyInviteListCustomizeVO;
import com.hyjf.am.vo.user.UserUtmInfoCustomizeVO;
import com.hyjf.common.file.UploadFileUtils;
import com.hyjf.common.util.QRCodeUtil;
import com.hyjf.cs.common.bean.result.WebResult;
import com.hyjf.cs.common.util.Page;
import com.hyjf.cs.user.config.SystemConfig;
import com.hyjf.cs.user.controller.BaseUserController;
import com.hyjf.cs.user.service.invite.InviteService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
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
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 邀请及奖励记录
 * @author hesy
 * @version InviteController, v0.1 2018/6/23 17:14
 */
@Api(value = "web端-邀请记录",tags = "web端-邀请记录")
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/hyjf-web/user/invite")
public class InviteController extends BaseUserController {
    private static final Logger logger = LoggerFactory.getLogger(InviteController.class);

    @Autowired
    InviteService inviteService;

    @Autowired
    private SystemConfig systemConfig;

    /**
     *
     * @param userId
     * @param request
     * @return
     * @Author : huanghui
     */
    @ApiOperation(value = "我的邀请信息", notes = "我的邀请奖励和邀请链接")
    @PostMapping(value = "/myInviteInfo", produces = "application/json; charset=utf-8")
    public WebResult<Map<String,Object>> selectMyInviteInfo(@RequestHeader(value = "userId") Integer userId, HttpServletRequest request){

        WebResult<Map<String,Object>> result = new WebResult<>();

        // 初始化返回Map
        Map<String,Object> resultMap = new HashMap<>();

        Map<String,String> pageData = inviteService.selectInvitePageData(String.valueOf(userId));

        String inviteLink = null;
        String inviteLinkWechat = null;

        // 合规自查添加
        // 20181205 产品需求, 屏蔽渠道,只保留用户ID
        UserUtmInfoCustomizeVO userUtmInfo = inviteService.getUserUtmInfo(userId);
//        if (userUtmInfo != null){
//            inviteLink = systemConfig.getWebQrcodeUrl() + "refferUserId=" + userId + "&utmId=" + userUtmInfo.getSourceId().toString() + "&utmSource=" + userUtmInfo.getSourceName();
//            inviteLinkWechat = systemConfig.getWechatQrcodeUrl() + "refferUserId=" + userId + "&utmId=" + userUtmInfo.getSourceId().toString() + "&utmSource=" + userUtmInfo.getSourceName();
//        }else {
            inviteLink = systemConfig.getWebQrcodeUrl() + "refferUserId=" + userId;
            inviteLinkWechat = systemConfig.getWechatQrcodeUrl() + "refferUserId=" + userId;
//        }
        // 二维码的下载地址
        String downloadUrl = systemConfig.webHost + "/hyjf-web/user/invite/download/" + userId;
        logger.info("二维码下载地址：" + downloadUrl);

        resultMap.putAll(pageData);
        resultMap.put("downloadUrl", downloadUrl);
        resultMap.put("inviteLink", inviteLink);
        resultMap.put("inviteLink2", inviteLink+"&action=scan");
        resultMap.put("inviteLinkWechat", inviteLinkWechat);
        result.setData(resultMap);
        return result;
    }
    /**
     * 我的邀请列表
     * @auther: hesy
     * @date: 2018/6/23
     */
    @ApiOperation(value = "我的邀请列表", notes = "我的邀请列表")
    @ApiImplicitParam(name = "param",value = "{currPage:string,pageSize:string}", dataType = "Map")
    @PostMapping(value = "/myInviteList", produces = "application/json; charset=utf-8")
    public WebResult<List<MyInviteListCustomizeVO>> selectMyInviteList(@RequestHeader(value = "userId") int userId, @RequestBody  Map<String,String> param, HttpServletRequest request){
        WebResult<List<MyInviteListCustomizeVO>> result = new WebResult<List<MyInviteListCustomizeVO>>();
        List<MyInviteListCustomizeVO> resultList = Collections.emptyList();

        logger.info("获取我的邀请列表开始，userId：{}", userId);

        // 请求参数校验
        inviteService.checkForInviteList(param);

        Integer inviteCount = inviteService.selectMyInviteCount(String.valueOf(userId));
        Page page = Page.initPage(Integer.parseInt(param.get("currPage")), Integer.parseInt(param.get("pageSize")));
        page.setTotal(inviteCount);

        try {
            resultList = inviteService.selectMyInviteList(String.valueOf(userId), page.getOffset(), page.getLimit());
            result.setData(resultList);
        } catch (Exception e) {
            logger.error("获取我的邀请列表异常", e);
            result.setStatus(WebResult.ERROR);
            result.setStatusDesc(WebResult.ERROR_DESC);
        }

        result.setPage(page);
        return result;
    }

    /**
     * 下载二维码
     */
    @GetMapping("/download/{userId}")
    public void download(@PathVariable  Integer userId, HttpServletRequest request, HttpServletResponse response){
        logger.info("开始生成二维码图片，userId：" + userId);
        try {
            // 合规自查添加
            // 20181205 产品需求, 屏蔽渠道,只保留用户ID
            String downloadUrl = null;
            UserUtmInfoCustomizeVO userUtmInfo = inviteService.getUserUtmInfo(userId);
//            if (userUtmInfo != null){
//                downloadUrl = systemConfig.getWechatQrcodeUrl() + "refferUserId=" + userId + "&utmId=" + userUtmInfo.getSourceId().toString() + "&utmSource=" + userUtmInfo.getSourceName();
//            }else {
                downloadUrl = systemConfig.getWechatQrcodeUrl() + "refferUserId=" + userId;
//            }
            QRCodeUtil.encode(downloadUrl, String.valueOf(userId),systemConfig.getPhysicalPath() + systemConfig.getFileUpload(), false);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }

        String webhost = UploadFileUtils.getDoPath(systemConfig.getFileDomainUrl());
        String fileUploadRealPath = UploadFileUtils.getDoPath(systemConfig.getFileUpload());
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
            logger.error(e.getMessage());
        } finally {
            output.flush();
            if (input != null){
                input.close();
            }if (output != null){
                output.close();
            }
        }
        return true;
    }

}
