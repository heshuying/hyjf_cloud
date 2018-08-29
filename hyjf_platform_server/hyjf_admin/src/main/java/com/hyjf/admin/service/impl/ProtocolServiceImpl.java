package com.hyjf.admin.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.admin.beans.BorrowCommonImage;
import com.hyjf.admin.client.ProtocolClient;
import com.hyjf.admin.service.ProtocolService;
import com.hyjf.am.response.admin.AdminProtocolResponse;
import com.hyjf.am.resquest.admin.AdminProtocolRequest;
import com.hyjf.am.vo.admin.ProtocolLogVO;
import com.hyjf.am.vo.admin.ProtocolTemplateCommonVO;
import com.hyjf.am.vo.admin.ProtocolVersionVO;
import com.hyjf.am.vo.trade.ProtocolTemplateVO;
import com.hyjf.common.cache.RedisConstants;
import com.hyjf.common.cache.RedisUtils;
import com.hyjf.common.enums.ProtocolEnum;
import com.hyjf.common.file.UploadFileUtils;
import com.hyjf.common.paginator.Paginator;
import com.hyjf.common.pdf.PdfToHtml;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.*;

/**
 * @author：yinhui
 * @Date: 2018/8/8  15:59
 */
@Service
public class ProtocolServiceImpl implements ProtocolService {

    @Autowired
    private ProtocolClient client;

    @Value("${file.domain.url}")
    private String FILEDOMAINURL;
    @Value("${file.physical.path}")
    private String FILEPHYSICALPATH;
    @Value("${file.upload.temp.path}")
    private String FILEUPLOADTEMPPATH;

    @Override
    public AdminProtocolResponse searchPage(AdminProtocolRequest request) {
        AdminProtocolResponse response = new AdminProtocolResponse();

        List<ProtocolTemplateCommonVO> recordList = null;
        Integer count = client.countRecord(request);
        response.setCount(count);
        if (count.intValue()>0) {
            Paginator paginator = new Paginator(request.getCurrPage(), count,request.getPageSize()==0?10:request.getPageSize());
            request.setLimitStart(paginator.getOffset());
            request.setLimitEnd( paginator.getLimit());
            recordList = client.getRecordList(request);
            response.setResultList(recordList);
        }
        return response;
    }

    /**
     * 根据协议id查询协议和版本
     *
     * @return
     */
    @Override
    public AdminProtocolResponse getProtocolTemplateById(AdminProtocolRequest request) {
        AdminProtocolResponse response = new AdminProtocolResponse();
        ProtocolTemplateCommonVO vo = client.getProtocolTemplateById(request);
        response.setResult(vo);
        return response;
    }

    @Override
    public Integer getProtocolTemplateNum(AdminProtocolRequest request) {
        return client.getProtocolTemplateNum(request);
    }

    /**
     * 添加协议模板
     *
     * @return
     */
    @Override
    public void insertProtocolTemplate(AdminProtocolRequest request,String userId){
        List<ProtocolTemplateCommonVO> listProtocolTemplateCommonVO = new ArrayList<>();
        ProtocolTemplateCommonVO protocolTemplateCommonVO = new ProtocolTemplateCommonVO();

        Integer createUserId = Integer.valueOf(userId);
        //模板存放路径
        String fileDomainUrl = "";
        //年月日，随机数
        Calendar calendar1 = Calendar.getInstance();
        String yearNow = String.valueOf(calendar1.get(Calendar.YEAR)).substring(2);
        String monthNow = calendar1.get(Calendar.MONTH) + 1< 10? "0"+(calendar1.get(Calendar.MONTH) + 1):String.valueOf(calendar1.get(Calendar.MONTH) + 1);
        String dayNow =calendar1.get(Calendar.DATE)<10? "0"+calendar1.get(Calendar.DATE): String.valueOf(calendar1.get(Calendar.DATE));
        int random = (int)(Math.random()*900)+100;
        //查询协议模板名称
        ProtocolTemplateVO protocolTemplate=request.getProtocolTemplateVO();
        int num=getProtocolTemplateNum(request);
        //协议id规则：Agreement001180526001
        String protocolId="";
        if(protocolTemplate != null){
            //判断删除的协议中是否存在当前协议模板名称Agreement006
            ProtocolTemplateVO vo=client.getProtocolTemplateByProtocolName(request);
            if(vo != null){
                protocolId = vo.getProtocolId().substring(0,12)+ yearNow+monthNow+dayNow+random;
            }else{
                if(num<10){
                    protocolId="Agreement"+"00"+num+ yearNow+monthNow+dayNow+random;
                }else if(num>=10&&num<100){
                    protocolId="Agreement"+"0"+num+ yearNow+monthNow+dayNow+random;
                }else{
                    protocolId="Agreement"+"0"+num+ yearNow+monthNow+dayNow+random;
                }
            }
            String protocolUrl = protocolTemplate.getProtocolUrl();
            //将pdf转为图片---参数
            String pdfPath=protocolUrl;
            String savePath=pdfPath.substring(0,pdfPath.lastIndexOf("."));
            String imgUrl="";
            //1.保存协议模板
            protocolTemplate.setProtocolId(protocolId);
            //将pdf转为图片
            List<String> imgs = PdfToHtml.pdftoImg(fileDomainUrl+pdfPath,fileDomainUrl+savePath,PdfToHtml.IMG_TYPE_JPG);
            if(!CollectionUtils.isEmpty(imgs)){
                String img =  StringUtils.join(imgs.toArray(),",");
                imgUrl=savePath+"-"+img;
            }
            protocolTemplate.setImgUrl(imgUrl);

            //新增协议模板
            protocolTemplateCommonVO.setProtocolTemplateVO(protocolTemplate);

            //3.新增协议版本
            this.insertProtocolVersion(protocolTemplate,createUserId,protocolTemplateCommonVO);
            //4.添加新增协议日志
            this.insertProtocolLog(protocolTemplate,0,protocolTemplateCommonVO);

            //发往am里面进行保存
            listProtocolTemplateCommonVO.add(protocolTemplateCommonVO);
            request.setRecordList(listProtocolTemplateCommonVO);
            client.insert(request);

            //将协议模板放入redis中
            RedisUtils.set(RedisConstants.PROTOCOL_TEMPLATE_URL+protocolTemplate.getProtocolId(),protocolTemplate.getProtocolUrl()+"&"+protocolTemplate.getImgUrl());
            //获取协议模板前端显示名称对应的别名
            String alias = ProtocolEnum.getAlias(protocolTemplate.getDisplayName());
            if(StringUtils.isNotBlank(alias)){
                RedisUtils.set(RedisConstants.PROTOCOL_TEMPLATE_ALIAS+alias,protocolTemplate.getProtocolId());//协议 ID放入redis
            }
        }
    }

    /**
     * 获得最新协议模版 前台展示信息
     *
     * @return
     */
    @Override
    public List<ProtocolTemplateVO> getNewInfo() {
        return client.getNewInfo();
    }

    //添加协议版本
    public void insertProtocolVersion(ProtocolTemplateVO protocolTemplate,Integer createUserId,ProtocolTemplateCommonVO protocolTemplateCommonVO){
        //新增协议版本
        List<ProtocolVersionVO> list = new ArrayList<>();
        ProtocolVersionVO protocolVersion=new ProtocolVersionVO();
        String protocolId = protocolTemplate.getProtocolId();
        int randomUrl = (int)(Math.random()*9000)+1000;
        protocolVersion.setProtocolId(protocolId);
        protocolVersion.setProtocolName(protocolId.substring(0,protocolId.length()-3)+randomUrl+".pdf");
        protocolVersion.setDisplayName(protocolTemplate.getDisplayName());
        protocolVersion.setVersionNumber(protocolTemplate.getVersionNumber());
        protocolVersion.setProtocolUrl(protocolTemplate.getProtocolUrl());
        protocolVersion.setRemarks(protocolTemplate.getRemarks());
        protocolVersion.setCreateUser(createUserId);
        protocolVersion.setUpdateUser(createUserId);
        list.add(protocolVersion);
        protocolTemplateCommonVO.setProtocolVersion(list);
    }

    //添加协议日志
    public void insertProtocolLog(ProtocolTemplateVO protocolTemplate,Integer operation,ProtocolTemplateCommonVO protocolTemplateCommonVO){
        //3.添加协议日志
        List<ProtocolLogVO> list = new ArrayList<>();
        ProtocolLogVO protocolLog = new ProtocolLogVO();
        protocolLog.setProtocolId(protocolTemplate.getProtocolId());
        protocolLog.setProtocolName(protocolTemplate.getProtocolName());
        protocolLog.setVersionNumber(protocolTemplate.getVersionNumber());
        protocolLog.setOperation(operation);
        if(operation.intValue() == 0){
            //添加协议时候，设置创建时间
            protocolLog.setCreateUser(protocolTemplate.getCreateUserId());
            protocolLog.setCreateTime(new Date());
        } else if(operation.intValue() == 1){
            //修改协议时候，设置修改时间
            protocolLog.setUpdateUser(protocolTemplate.getUpdateUserId());
            protocolLog.setUpdateTime(new Date());
        }
        if(operation.intValue() == 2){
            //删除协议时候，设置删除时间
            protocolLog.setDeleteUser(protocolTemplate.getUpdateUserId());
            protocolLog.setDeleteTime(new Date());
        }
        list.add(protocolLog);
        protocolTemplateCommonVO.setProtocolLog(list);

    }

    /**
     * 修改协议模板
     *
     * @return
     */
    @Override
    public void updateProtocolTemplate(AdminProtocolRequest request,String userId) {
        List<ProtocolTemplateCommonVO> listProtocolTemplateCommonVO = new ArrayList<>();
        ProtocolTemplateCommonVO protocolTemplateCommonVO = new ProtocolTemplateCommonVO();
        String fileDomainUrl = "";
        Integer updateUserId = Integer.valueOf(userId);
        //1.1修改协议模板
        ProtocolTemplateVO protocolTemplate = request.getProtocolTemplateVO();
        if (protocolTemplate != null) {
            protocolTemplate.setUpdateUserId(updateUserId);
            //将pdf转为图片---参数
            String pdfPath = protocolTemplate.getProtocolUrl();
            String savePath = pdfPath.substring(0, pdfPath.lastIndexOf("."));
            String imgUrl = "";
            //将pdf转为图片
            List<String> imgs = PdfToHtml.pdftoImg(fileDomainUrl + pdfPath, fileDomainUrl + savePath, PdfToHtml.IMG_TYPE_JPG);
            if (!CollectionUtils.isEmpty(imgs)) {
                String img = StringUtils.join(imgs.toArray(), ",");
                imgUrl = savePath + "-" + img;
            }
            protocolTemplate.setImgUrl(imgUrl);
            //修改协议模板
            protocolTemplateCommonVO.setProtocolTemplateVO(protocolTemplate);
            request.setRecordList(listProtocolTemplateCommonVO);
            client.updateProtocolTemplate(request);

            //获取将要启用的版本号和协议模板名称
            int count = client.updateDisplayFlag(request);
            if (count == 0) {
                //2.31新增协议版本
                this.insertProtocolVersion(protocolTemplate, updateUserId,protocolTemplateCommonVO);
            }
            //3.添加修改协议的日志
            this.insertProtocolLog(protocolTemplate, 1,protocolTemplateCommonVO);

            //发往am里面进行保存
            listProtocolTemplateCommonVO.add(protocolTemplateCommonVO);
            request.setRecordList(listProtocolTemplateCommonVO);
            client.insert(request);

            //将协议模板放入redis中
            RedisUtils.set(RedisConstants.PROTOCOL_TEMPLATE_URL + protocolTemplate.getProtocolId(), protocolTemplate.getProtocolUrl() + "&" + protocolTemplate.getImgUrl());
            //获取协议模板前端显示名称对应的别名
            String alias = ProtocolEnum.getAlias(protocolTemplate.getDisplayName());
            if (StringUtils.isNotBlank(alias)) {
                RedisUtils.set(RedisConstants.PROTOCOL_TEMPLATE_ALIAS + alias, protocolTemplate.getProtocolId());//协议 ID放入redis
            }

        }
    }

    /**
     * 删除协议模板
     *
     */
    @Override
    public void deleteProtocolTemplate(AdminProtocolRequest request,String userId){
        List<ProtocolTemplateCommonVO> listProtocolTemplateCommonVO = new ArrayList<>();
        Integer updateUserId =  Integer.valueOf(userId);
        ProtocolTemplateCommonVO protocolTemplateCommon=new ProtocolTemplateCommonVO();
        request.setIds(String.valueOf(updateUserId));
        //根据协议的id查询协议
        AdminProtocolResponse response = client.deleteProtocolTemplate(request);
        ProtocolTemplateVO protocolTemplate = response.getResult().getProtocolTemplateVO();

        if(protocolTemplate != null){
            String protocolId= protocolTemplate.getProtocolId();
            if(StringUtils.isNotBlank(protocolId)){
                //3.添加删除协议日志
                this.insertProtocolLog(protocolTemplate,2,protocolTemplateCommon);

                //发往am里面进行保存
                listProtocolTemplateCommonVO.add(protocolTemplateCommon);
                request.setRecordList(listProtocolTemplateCommonVO);
                client.insert(request);

                //将协议模板移除redis中
                RedisUtils.del(RedisConstants.PROTOCOL_TEMPLATE_URL+protocolTemplate.getProtocolId());
                //获取协议模板前端显示名称对应的别名
                String alias = ProtocolEnum.getAlias(protocolTemplate.getDisplayName());
                if(StringUtils.isNotBlank(alias)){
                    RedisUtils.del(RedisConstants.PROTOCOL_TEMPLATE_ALIAS+alias);//删除对应协议ID
                }
            }
        }

    }

    /**
     * 图片上传
     *
     * @param request
     * @return
     */
    @Override
    public String uploadFile(HttpServletRequest request, HttpServletResponse response) {
        CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver();
        MultipartHttpServletRequest multipartRequest = commonsMultipartResolver.resolveMultipart(request);
        String fileDomainUrl = FILEDOMAINURL;
        String filePhysicalPath = FILEPHYSICALPATH;
        String fileUploadTempPath = FILEUPLOADTEMPPATH;
        String logoRealPathDir = filePhysicalPath + fileUploadTempPath;
        File logoSaveFile = new File(logoRealPathDir);
        if (!logoSaveFile.exists()) {
            logoSaveFile.mkdirs();
        }
        BorrowCommonImage fileMeta = null;
        LinkedList<BorrowCommonImage> files = new LinkedList<BorrowCommonImage>();

        Iterator<String> itr = multipartRequest.getFileNames();
        MultipartFile multipartFile = null;

        boolean flag =true;
        while (itr.hasNext()) {
            // 文件错误信息
            String errorMessage = null;
            multipartFile = multipartRequest.getFile(itr.next());
            String fileRealName = String.valueOf(new Date().getTime());
            String originalFilename = multipartFile.getOriginalFilename();
            String  suf = UploadFileUtils.getSuffix(multipartFile.getOriginalFilename());
            fileRealName = fileRealName + suf;
            if(StringUtils.isEmpty(suf) ) {
                errorMessage="上传的文件不能是空";
            }
            try {
                //判断上传文件是否是Pdf格式的
                if(!suf.equalsIgnoreCase(".pdf")){
                    errorMessage="上传的文件必须是pdf格式";
                    flag = false;
                }else{
                    Long size=multipartFile.getSize();
                    if(multipartFile.getSize() > 5000000L){
                        errorMessage="上传的文件过大";
                        flag = false;
                    }else if(multipartFile.getSize() < 0L){
                        errorMessage="上传的文件为空";
                        flag = false;
                    }else{
                        errorMessage = UploadFileUtils.upload4Stream(fileRealName, logoRealPathDir, multipartFile.getInputStream(), 50000000L);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            fileMeta = new BorrowCommonImage();
            int index = originalFilename.lastIndexOf(".");
            if (index != -1) {
                fileMeta.setImageName(originalFilename.substring(0, index));
            } else {
                fileMeta.setImageName(originalFilename);
            }

            fileMeta.setImageRealName(fileRealName);
            fileMeta.setImageSize(multipartFile.getSize() / 1024 + "");// KB
            fileMeta.setImageType(multipartFile.getContentType());
            fileMeta.setErrorMessage(errorMessage);
            // 获取文件路径
            fileMeta.setImagePath(fileUploadTempPath + fileRealName);
            fileMeta.setImageSrc(fileDomainUrl + fileUploadTempPath + fileRealName);
            files.add(fileMeta);

        }
        return JSONObject.toJSONString(files, flag);
    }
}
