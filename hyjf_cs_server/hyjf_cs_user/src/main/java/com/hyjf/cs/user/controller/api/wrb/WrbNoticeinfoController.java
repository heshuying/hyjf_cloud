package com.hyjf.cs.user.controller.api.wrb;

import com.hyjf.am.response.WrbResponse;
import com.hyjf.am.resquest.config.MsgPushTemplateRequest;
import com.hyjf.am.vo.config.MessagePushTemplateVO;
import com.hyjf.common.util.GetDate;
import com.hyjf.common.util.WrbParseParamUtil;
import com.hyjf.cs.user.bean.WrbNoticeinfoRequest;
import com.hyjf.cs.user.bean.WrbNoticeinfoResponse;
import com.hyjf.cs.user.bean.WrbNoticeinfoResponse.NoticeinfoDetail;
import com.hyjf.cs.user.service.wrb.WrbNoticeinfoSerice;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lisheng
 * @version WrbNoticeinfoController, v0.1 2018/9/20 14:32
 */
@RestController
@RequestMapping("")
public class WrbNoticeinfoController {
    private Logger logger = LoggerFactory.getLogger(WrbNoticeinfoController.class);
    public static final String datetimeFormat_key = "yyyy-MM-dd HH:mm:ss";
    @Autowired
    private WrbNoticeinfoSerice wrbNoticeinfoSerice;

    @RequestMapping("")
    public WrbNoticeinfoResponse getNoticeinfoDetail(@RequestParam String param,
                                                     @RequestParam(value = "sign", required = false) String sign){
        logger.info("获取平台的公告信息, param is :{}, sign is :{}", param, sign);
        WrbNoticeinfoResponse response=new WrbNoticeinfoResponse();

        WrbNoticeinfoRequest request= null;
        try{
            request = WrbParseParamUtil.mapToBean(WrbParseParamUtil.parseParam(param), WrbNoticeinfoRequest.class);
        }catch(Exception e){
            logger.error("参数解析失败....", e);
            response.setRetcode(WrbResponse.FAIL_RETCODE);
            response.setRetmsg(WrbResponse.FAIL_RETMESSAGE);
            return response;
        }finally {
            if (request == null) {
                response.setRetcode(WrbResponse.FAIL_RETCODE);
                response.setRetmsg(WrbResponse.FAIL_RETMESSAGE);
                return response;
            }
        }
        Integer limit = request.getLimit();
        Integer page = request.getPage();
        if(null == limit || null  == page ){
            response.setRetcode(WrbResponse.FAIL_RETCODE);
            response.setRetmsg(WrbResponse.FAIL_RETMESSAGE);
            return response;
        }
        MsgPushTemplateRequest msgPushTemplateRequest = new MsgPushTemplateRequest();
        msgPushTemplateRequest.setCurrPage(limit);
        msgPushTemplateRequest.setPageSize(page);
        //只查打开微信的
        msgPushTemplateRequest.setTemplateAction(3);
        //启用的
        msgPushTemplateRequest.setStatus(1);
        //查询平台的公告信息-新
        List<MessagePushTemplateVO> noticeinfoDetailList = wrbNoticeinfoSerice.getNoticeinfoDetailNew(msgPushTemplateRequest);
        WrbNoticeinfoResponse wrbNoticeinfoResponse=new WrbNoticeinfoResponse();
        List<NoticeinfoDetail> detailList = new ArrayList<NoticeinfoDetail>();
        for (MessagePushTemplateVO messagePushTemplate:noticeinfoDetailList){
            WrbNoticeinfoResponse.NoticeinfoDetail noticeinfoDetail = new NoticeinfoDetail();
            noticeinfoDetail.setId(String.valueOf(messagePushTemplate.getId()));
            noticeinfoDetail.setContent(messagePushTemplate.getTemplateContent());
            noticeinfoDetail.setTitle(messagePushTemplate.getTemplateTitle());
            noticeinfoDetail.setUrl(messagePushTemplate.getTemplateActionUrl());
            if(messagePushTemplate.getUpdateTime()!=null){
                noticeinfoDetail.setRelease_time(GetDate.dateToString2(messagePushTemplate.getUpdateTime(),datetimeFormat_key));
            }else{
                noticeinfoDetail.setRelease_time("");
            }
            detailList.add(noticeinfoDetail);
        }
        wrbNoticeinfoResponse.setAll_notices(detailList);
        return wrbNoticeinfoResponse;

    }
}
