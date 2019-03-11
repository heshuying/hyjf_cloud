package com.hyjf.admin.controller.promotion.channel;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.admin.beans.BorrowCommonImage;
import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.common.util.ExportExcel;
import com.hyjf.admin.common.util.ShiroConstants;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.excel.ReadExcel;
import com.hyjf.admin.interceptor.AuthorityAnnotation;
import com.hyjf.admin.service.promotion.channel.ChannelService;
import com.hyjf.admin.utils.FileUpLoadUtil;
import com.hyjf.am.response.admin.promotion.UtmResultResponse;
import com.hyjf.am.vo.admin.promotion.channel.ChannelCustomizeVO;
import com.hyjf.am.vo.admin.promotion.channel.UtmChannelVO;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.am.vo.user.UtmPlatVO;
import com.hyjf.common.enums.MsgEnum;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.util.GetDate;
import com.hyjf.common.util.StringPool;
import com.hyjf.common.validator.CheckUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.*;

/**
 * @Auther: walter.limeng
 * @Date: 2018/7/14 10:38
 * @Description: ChannelController
 */
@Api(tags ="推广中心-推广管理列表")
@RestController
@RequestMapping("/hyjf-admin/promotion/channel")
public class ChannelController extends BaseController {
    private Logger logger = LoggerFactory.getLogger(ChannelController.class);
    /** 查看权限 */
    public static final String PERMISSIONS = "channel";

    @Resource
    private ChannelService channelService;

    @ApiOperation(value = "页面初始化", notes = "推广列表")
    @PostMapping("/init")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_VIEW)
    public UtmResultResponse channelListInit(HttpServletRequest request, HttpServletResponse response, @RequestBody ChannelCustomizeVO channelCustomizeVO) {
        UtmResultResponse adminResult = new UtmResultResponse();
        Integer count = this.channelService.countList(channelCustomizeVO);
        if(null != count){
            List<ChannelCustomizeVO> channelList = channelService.getByPageList(channelCustomizeVO);
            adminResult.setData(channelList);
        }
        List<UtmPlatVO> utmPlatVOList = channelService.getUtmPlat();
        adminResult.setUtmPlatList(utmPlatVOList);
        adminResult.setTotal(count);
        return adminResult;
    }

    @ApiOperation(value = "画面迁移(含有id更新，不含有id添加)", notes = "画面迁移(含有id更新，不含有id添加)")
    @PostMapping("/infoaction")
    @AuthorityAnnotation(key = PERMISSIONS, value = {ShiroConstants.PERMISSION_ADD, ShiroConstants.PERMISSION_MODIFY})
    public UtmResultResponse info(HttpServletRequest request, HttpServletResponse response, @RequestBody ChannelCustomizeVO channelCustomizeVO){
        UtmResultResponse adminResult = new UtmResultResponse();
        if (StringUtils.isNotEmpty(channelCustomizeVO.getUtmId())) {
            UtmChannelVO record = channelService.getRecord(channelCustomizeVO.getUtmId());
            if (null == record || record.getUtmReferrer() == null || record.getUtmReferrer() == 0) {
                adminResult.setUtmReferrer("");
            } else {
                UserVO user = this.channelService.getUserByUserId(record.getUtmReferrer());
                adminResult.setUtmReferrer(user.getUsername());
            }
            String url = "";
            if (record != null) {
                url = getUrl(record);
                record.setUrl(url);
            }

            adminResult.setUrl(url);
            adminResult.setData(record);
        }else{
            adminResult.setStatus(UtmResultResponse.FAIL);
            adminResult.setStatusDesc("查询条件异常！");
        }
        return adminResult;
    }

    @ApiOperation(value = "添加或修改信息", notes = "添加或修改信息")
    @PostMapping("/insertorupdateaction")
    @AuthorityAnnotation(key = PERMISSIONS, value = {ShiroConstants.PERMISSION_ADD, ShiroConstants.PERMISSION_MODIFY})
    public UtmResultResponse insertAction(HttpServletRequest request, HttpServletResponse response, @RequestBody ChannelCustomizeVO channelCustomizeVO){
        UtmResultResponse adminResult = new UtmResultResponse();
        //根据utmId判断，如存在，则为修改，如不存在，则为新增
        logger.info("新增或修改推广渠道，sourceId："+channelCustomizeVO.getSourceId()+",utmTerm:"+channelCustomizeVO.getUtmTerm());
        validatorFieldCheck(adminResult,channelCustomizeVO);
        if(AdminResult.SUCCESS.equals(adminResult.getStatus())){
            boolean flag = channelService.insertOrUpdateUtm(channelCustomizeVO);
            logger.info("新增或修改推广渠道结果，flag："+flag);
            if(!flag){
                adminResult.setStatus(AdminResult.FAIL);
                adminResult.setStatusDesc("系统异常，请联系管理员!");
            }
        }
        return adminResult;
    }

    @ApiOperation(value = "通过用户名校验用户是否存在", notes = "通过用户名校验用户是否存在")
    @PostMapping("/getUserByUserName")
    @ApiImplicitParam(name = "userName",value = "用户名")
    @AuthorityAnnotation(key = PERMISSIONS, value = {ShiroConstants.PERMISSION_ADD, ShiroConstants.PERMISSION_MODIFY})
    public UtmResultResponse getUserByUserName(HttpServletRequest request, HttpServletResponse response, @RequestBody Map map){
        UtmResultResponse adminResult = new UtmResultResponse();
        String userName = (String) map.get("userName");
        //根据utmId判断，如存在，则为修改，如不存在，则为新增
        logger.info("校验推荐人合法性，username：" + userName);
        if(StringUtils.isNotBlank(userName)){
            UserVO user = channelService.getUserByUserName(userName);
            if(user == null){
                adminResult.setStatus(UtmResultResponse.NOUSER);
                adminResult.setStatusDesc("用户不存在");
            }else {
                adminResult.setData(user);
                adminResult.setStatusDesc("用户存在");
                adminResult.setStatus(AdminResult.SUCCESS);
            }
        }else{
            adminResult.setStatus(AdminResult.FAIL);
            adminResult.setStatusDesc("用户名为空!");
        }
        return adminResult;
    }




    @ApiOperation(value = "删除信息", notes = "删除信息")
    @PostMapping("/deleteaction")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_DELETE)
    public UtmResultResponse deleteAction(HttpServletRequest request, HttpServletResponse response, @RequestBody ChannelCustomizeVO channelCustomizeVO){
        UtmResultResponse adminResult = new UtmResultResponse();
        //根据utmId判断，如存在，则为修改，如不存在，则为新增
        if(StringUtils.isNotEmpty(channelCustomizeVO.getUtmId())){
            boolean flag = channelService.deleteAction(channelCustomizeVO);
            if(!flag){
                adminResult.setStatus(AdminResult.FAIL);
                adminResult.setStatusDesc("系统异常，请联系管理员!");
            }
        }else{
            adminResult.setStatus(AdminResult.FAIL);
            adminResult.setStatusDesc("删除异常！");
        }
        return adminResult;
    }

    /**
     * @Author walter.limeng
     * @Description  验证对象
     * @Date 10:24 2018/7/16
     * @Param adminResult
     * @Param channelCustomizeVO
     * @return
     */
    public void validatorFieldCheck(UtmResultResponse adminResult,ChannelCustomizeVO channelCustomizeVO){
        ModelAndView modelAndView = new ModelAndView();
        // 渠道CheckUtil
        if(StringUtils.isNotEmpty(channelCustomizeVO.getSourceId())){
            // 推广方式
            CheckUtil.check(channelCustomizeVO.getUtmMedium()==null||channelCustomizeVO.getUtmMedium().length()<=50, MsgEnum.ERR_OBJECT_EXCEED_LIMIT,"推广方式");
           // ValidatorFieldCheckUtil.validateMaxLength(modelAndView, "utmMedium", channelCustomizeVO.getUtmMedium(), 50, false);
            // 推广单元
            CheckUtil.check(channelCustomizeVO.getUtmContent()==null||channelCustomizeVO.getUtmContent().length()<=50, MsgEnum.ERR_OBJECT_EXCEED_LIMIT,"推广单元");
           // ValidatorFieldCheckUtil.validateMaxLength(modelAndView, "utmContent", channelCustomizeVO.getUtmContent(), 50, false);
            // 推广计划
            CheckUtil.check(channelCustomizeVO.getUtmCampaign()==null||channelCustomizeVO.getUtmCampaign().length()<=50, MsgEnum.ERR_OBJECT_EXCEED_LIMIT,"推广计划");
            // ValidatorFieldCheckUtil.validateMaxLength(modelAndView, "utmCampaign", channelCustomizeVO.getUtmCampaign(), 50, false);
            // 关键字
            CheckUtil.check(channelCustomizeVO.getUtmTerm()==null||channelCustomizeVO.getUtmTerm().length()<=50, MsgEnum.ERR_OBJECT_EXCEED_LIMIT,"关键字");
            //boolean utmTermFlag = ValidatorFieldCheckUtil.validateMaxLength(modelAndView, "utmTerm", channelCustomizeVO.getUtmTerm(), 50, false);

            // 链接地址
            CheckUtil.check(StringUtils.isNotBlank(channelCustomizeVO.getLinkAddress()), MsgEnum.ERR_OBJECT_REQUIRED,"链接地址");
            // ValidatorFieldCheckUtil.validateRequired(modelAndView, "linkAddress", channelCustomizeVO.getLinkAddress());
            CheckUtil.check(channelCustomizeVO.getLinkAddress()==null||channelCustomizeVO.getLinkAddress().length()<=250, MsgEnum.ERR_OBJECT_EXCEED_LIMIT,"链接地址");
            //ValidatorFieldCheckUtil.validateMaxLength(modelAndView, "linkAddress", channelCustomizeVO.getLinkAddress(), 250, false);
            // 备注说明
            CheckUtil.check(channelCustomizeVO.getRemark()==null||channelCustomizeVO.getRemark().length()<=100, MsgEnum.ERR_OBJECT_EXCEED_LIMIT,"备注说明");
           // ValidatorFieldCheckUtil.validateMaxLength(modelAndView, "remark", channelCustomizeVO.getRemark(), 100, false);
            boolean check = true;
            if (null != channelCustomizeVO.getUtmId()){
                check = channelService.getBySourceIdAndTerm(channelCustomizeVO.getUtmId(),channelCustomizeVO.getSourceId(),channelCustomizeVO.getUtmTerm());
            }else{
                //新增数据
                check = channelService.getBySourceIdAndTerm(null,channelCustomizeVO.getSourceId(),channelCustomizeVO.getUtmTerm());
            }
            //modify by cwyang 20190221 增加推荐人合法校验
            String utmReferrer = channelCustomizeVO.getUtmReferrer();
            if(StringUtils.isNotBlank(utmReferrer)){
                UserVO user = channelService.getUserByUserName(utmReferrer);
                if(user == null){
                    adminResult.setStatus(UtmResultResponse.FAIL);
                    adminResult.setStatusDesc("推荐人用户名不存在！");
                }
            }

            logger.info("校验sourceId和utmTerm结果 check："+check);
            if(check){
                adminResult.setStatus(UtmResultResponse.FAIL);
                adminResult.setStatusDesc("渠道下的关键字已经存在！");
            }else{
                adminResult.setStatus(AdminResult.SUCCESS);
            }
        }else{
            adminResult.setStatus(UtmResultResponse.FAIL);
            adminResult.setStatusDesc("SourceId为空！");
        }
    }

    /**
     * 获取URL
     *
     * @return
     * @throws UnsupportedEncodingException
     */
    private String getUrl(UtmChannelVO record){
        StringBuffer strBuf = new StringBuffer();
        strBuf.append(record.getLinkAddress());
        strBuf.append("?utm_id=");
        strBuf.append(record.getUtmId());
        if (StringUtils.isNotEmpty(record.getUtmSource())) {
            strBuf.append("&utm_source=");
            strBuf.append(toUnicode(record.getUtmSource()));
        }
        if (StringUtils.isNotEmpty(record.getUtmMedium())) {
            strBuf.append("&utm_medium=");
            strBuf.append(toUnicode(record.getUtmMedium()));

        }
        if (StringUtils.isNotEmpty(record.getUtmTerm())) {
            strBuf.append("&utm_term=");
            strBuf.append(toUnicode(record.getUtmTerm()));

        }
        if (StringUtils.isNotEmpty(record.getUtmContent())) {
            strBuf.append("&utm_content=");
            strBuf.append(toUnicode(record.getUtmContent()));

        }
        if (StringUtils.isNotEmpty(record.getUtmCampaign())) {
            strBuf.append("&utm_campaign=");
            strBuf.append(toUnicode(record.getUtmCampaign()));

        }
        if (record.getUtmReferrer() != null && record.getUtmReferrer() != 0) {
            strBuf.append("&refferUserId=");
            strBuf.append(record.getUtmReferrer());

        }
        return strBuf.toString();
    }

    /**
     * Unicode
     *
     * @param str
     * @return
     * @throws UnsupportedEncodingException
     */
    private String toUnicode(String str) {
        try{
            return URLEncoder.encode(str, "UTF-8");
        }catch (Exception e){
            logger.info("UnsupportedEncodingException异常",e);
        }
        return null;
    }

    @Autowired
    private FileUpLoadUtil fileUpLoadUtil ;
    /**
     * 资料上传
     *
     * @param request
     * @return
     * @throws Exception
     */
    @ApiOperation(value = "资料上传", notes = "资料上传")
    @PostMapping("/upload")
    public AdminResult uploadFile(HttpServletRequest request) throws Exception {
        logger.info(ChannelController.class.toString(), "startLog -- /hyjf-admin/promotion/channel/upload");
        AdminResult adminResult = new AdminResult();

        LinkedList<BorrowCommonImage> list = fileUpLoadUtil.upLoad((MultipartHttpServletRequest) request);

        for (BorrowCommonImage borrowCommonImage : list) {
            String fileRealName = borrowCommonImage.getImagePath();
            Map<String, String> nameMaps = new HashMap<>();
            nameMaps.put("utmSource", "utmSource");
            nameMaps.put("utmMedium", "utmMedium");
            nameMaps.put("utmContent", "utmContent");
            nameMaps.put("utmCampaign", "utmCampaign");
            nameMaps.put("utmTerm", "utmTerm");
            nameMaps.put("utmReferrer", "utmReferrer");
            nameMaps.put("remark", "remark");
            ReadExcel readExcel = new ReadExcel();
            List<JSONObject> lists = new ArrayList<>();
            try {
                lists = readExcel.readExcel(fileRealName, nameMaps,null);

                List<ChannelCustomizeVO> voList = new ArrayList<>();

                for (JSONObject jsonObject : lists) {
                    ChannelCustomizeVO vo = new ChannelCustomizeVO();
                    vo.setSourceName(jsonObject.getString("utmSource"));
                    vo.setUtmMedium(jsonObject.getString("utmMedium"));
                    vo.setUtmContent(jsonObject.getString("utmContent"));
                    vo.setUtmCampaign(jsonObject.getString("utmCampaign"));
                    vo.setUtmTerm(jsonObject.getString("utmTerm"));
                    vo.setUtmReferrer(jsonObject.getString("utmReferrer"));
                    vo.setRemark(jsonObject.getString("remark"));
                    voList.add(vo);
                }
                channelService.insertUtmList(voList);

            } catch (Exception e) {
                logger.error("推广管理导入数据失败！", e);
                adminResult.setStatus(FAIL);
                adminResult.setStatusDesc("推广管理导入数据失败！");
                return adminResult;
            }
        }

        adminResult.setData(list);
        adminResult.setStatus(SUCCESS);
        adminResult.setStatusDesc(SUCCESS_DESC);
        logger.info(ChannelController.class.toString(), "endLog -- /hyjf-admin/promotion/channel/upload");
        return adminResult;
    }

    /**
     * 导出功能
     * @param request
     * @param response
     * @throws Exception
     */
    @ApiOperation(value = "导出功能", notes = "导出功能")
    @PostMapping("/export")
    public void exportAction(HttpServletRequest request, HttpServletResponse response) throws Exception {
        // 表格sheet名称
        String sheetName = "推广管理模板";

        String fileName = URLEncoder.encode(sheetName, "UTF-8") + StringPool.UNDERLINE + GetDate.getServerDateTime(8, new Date()) + CustomConstants.EXCEL_EXT;

        String[] titles = new String[] { "utmSource", "utmMedium", "utmContent", "utmCampaign", "utmTerm", "utmReferrer", "remark" };
        // 声明一个工作薄
        HSSFWorkbook workbook = new HSSFWorkbook();

        // 生成一个表格
        ExportExcel.createHSSFWorkbookTitle(workbook, titles, sheetName);

        // 导出
        ExportExcel.writeExcelFile(response, workbook, titles, fileName);
    }

}
