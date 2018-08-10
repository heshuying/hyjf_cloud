package com.hyjf.admin.service.impl;

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
import com.hyjf.common.paginator.Paginator;
import com.hyjf.common.pdf.PdfToHtml;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @author：yinhui
 * @Date: 2018/8/8  15:59
 */
@Service
public class ProtocolServiceImpl implements ProtocolService {

    @Autowired
    private ProtocolClient client;

    @Override
    public AdminProtocolResponse searchPage(AdminProtocolRequest request) {
        AdminProtocolResponse response = new AdminProtocolResponse();

        List<ProtocolTemplateCommonVO> recordList = null;
        Integer count = client.countRecord(request);
        response.setCount(count);
        if (count.intValue()>0) {
            Paginator paginator = new Paginator(request.getPaginatorPage(), count);
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
    public void insertProtocolTemplate(AdminProtocolRequest request){
        List<ProtocolTemplateCommonVO> listProtocolTemplateCommonVO = new ArrayList<>();
        ProtocolTemplateCommonVO protocolTemplateCommonVO = new ProtocolTemplateCommonVO();

        Integer createUserId = 0;
        //模板存放路径
        String fileDomainUrl = "";
        //年月日，随机数
        Calendar calendar1 = Calendar.getInstance();
        String yearNow = String.valueOf(calendar1.get(Calendar.YEAR)).substring(2);
        String monthNow = calendar1.get(Calendar.MONTH) + 1< 10? "0"+(calendar1.get(Calendar.MONTH) + 1):String.valueOf(calendar1.get(Calendar.MONTH) + 1);
        String dayNow =calendar1.get(Calendar.DATE)<10? "0"+calendar1.get(Calendar.DATE): String.valueOf(calendar1.get(Calendar.DATE));
        int random = (int)(Math.random()*900)+100;
        int randomUrl = (int)(Math.random()*9000)+1000;
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
            String protocolNam= protocolTemplate.getProtocolName();
            String versionNumber= protocolTemplate.getVersionNumber();
            String protocolUrl = protocolTemplate.getProtocolUrl();
            //将pdf转为图片---参数
            String pdfPath=protocolUrl;
            String savePath=pdfPath.substring(0,pdfPath.lastIndexOf("."));
            String imgUrl="";
            //1.保存协议模板
            protocolTemplate.setProtocolId(protocolId);
//            protocolTemplate.setCreateUserId(createUserId);
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

    //添加协议版本
    public void insertProtocolVersion(ProtocolTemplateVO protocolTemplate,Integer createUserId,ProtocolTemplateCommonVO protocolTemplateCommonVO){
        //新增协议版本
        List<ProtocolVersionVO> list = new ArrayList<>();
        ProtocolVersionVO protocolVersion=new ProtocolVersionVO();
        String protocolId = protocolTemplate.getProtocolId();
        int randomUrl = (int)(Math.random()*9000)+1000;
        protocolVersion.setProtocolId(protocolId);
        protocolVersion.setProtocolName(protocolId.substring(0,protocolId.length()-3)+randomUrl+".pdf");
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
    public void updateProtocolTemplate(AdminProtocolRequest request) {
        List<ProtocolTemplateCommonVO> listProtocolTemplateCommonVO = new ArrayList<>();
        ProtocolTemplateCommonVO protocolTemplateCommonVO = new ProtocolTemplateCommonVO();
        String fileDomainUrl = "";
        Integer updateUserId = 0;
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
    public void deleteProtocolTemplate(AdminProtocolRequest request){
        List<ProtocolTemplateCommonVO> listProtocolTemplateCommonVO = new ArrayList<>();
        Integer updateUserId = 0;
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
}
