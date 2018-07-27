/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.trade.controller.wechat.agreement;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.vo.trade.ProtocolTemplateVO;
import com.hyjf.common.cache.RedisConstants;
import com.hyjf.common.cache.RedisUtils;
import com.hyjf.common.enums.utils.ProtocolEnum;
import com.hyjf.cs.trade.controller.BaseTradeController;
import com.hyjf.cs.trade.service.newagreement.NewAgreementService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author libin
 * @version AgreementController.java, v0.1 2018年7月27日 上午10:26:08
 */
@Api(description = "微信端协议接口")
@RestController
@RequestMapping("/hyjf-wechat/wx/agreement")
public class AgreementController extends BaseTradeController{
	
    @Autowired
    private NewAgreementService agreementService;
    
    /**
     * 查询协议图片(这个方法跟前端协商暂时废弃)
     * @author libin
     * @param request
     * @param response
     * @return
     */
    @ApiOperation(value = "微信端协议接口", notes = "跳转协议")
    @ResponseBody
    @PostMapping("/goDetail")
    public ModelAndView goDetail(String pageType) {
        ModelAndView modeAndView = null;
        if (pageType.equals("wx_hjh_contract")) {
            // 微信 投资风险确认书H5
            modeAndView = new ModelAndView("invest/app_contract");
        } else if (pageType.equals("wx_hjh_serve")) {
            // 投资服务协议
            modeAndView = new ModelAndView("invest/type-new-hjhxieyi");
        } else if (pageType.equals("wx_hjh_invest")) {
            // 居间服务协议
            modeAndView = new ModelAndView("invest/type-invest");
        }
        return modeAndView;
    }
    
    /**
     * 通过 模板名字或者协议模板HTML
     *
     * @param aliasName 协议模版的别名
     * @return
     */
    @ApiOperation(value = "微信端协议接口", notes = "通过模板名字或者协议模板HTML")
    @ResponseBody
    @PostMapping("/goAgreementImg")
    public JSONObject getUrl(@RequestParam String aliasName) {
        JSONObject jsonObject = new JSONObject();
        if (StringUtils.isEmpty(aliasName)) {
            jsonObject.put("status","99");
            jsonObject.put("statusDesc","请求参数非法");
            return jsonObject;
        }
        //是否在枚举中有定义
        String displayName = ProtocolEnum.getDisplayName(aliasName);
        if (StringUtils.isEmpty(displayName)) {
            jsonObject.put("status","99");
            jsonObject.put("statusDesc","请求参数非法");
            return jsonObject;
        }
        String protocolId = RedisUtils.get(RedisConstants.PROTOCOL_TEMPLATE_ALIAS + aliasName);
        if (StringUtils.isEmpty(protocolId)) {
            boolean flag = this.setRedisProtocolTemplate(displayName);
            if (!flag) {
                jsonObject.put("status","99");
                jsonObject.put("statusDesc","数据非法");
                return jsonObject;
            }
        }
        try {
            List<String> imgJSON = this.getImgUrlList(protocolId);
            jsonObject.put("request", imgJSON);
            jsonObject.put("status","000");
            jsonObject.put("statusDesc","成功");
        } catch (Exception e) {
            jsonObject.put("status","99");
            jsonObject.put("statusDesc","失败");
        }
        return jsonObject;
    }
    
    /**
     * 往Redis中放入协议模板内容
     *
     * @param displayName
     * @return
     */
    public boolean setRedisProtocolTemplate(String displayName) {
/*        ProtocolTemplateExample examplev = new ProtocolTemplateExample();
        ProtocolTemplateExample.Criteria criteria = examplev.createCriteria();
        criteria.andDisplayNameEqualTo(displayName);
        criteria.andStatusEqualTo(1);
        List<ProtocolTemplate> list = protocolTemplateMapper.selectByExample(examplev);
        if (CollectionUtils.isEmpty(list)) {
            return false;
        }
        ProtocolTemplate protocolTemplate = list.get(0);*/
    	List<ProtocolTemplateVO> list = this.agreementService.getProtocolTemplateVOByDisplayName(displayName);
        if (CollectionUtils.isEmpty(list)) {
            return false;
        }
        ProtocolTemplateVO protocolTemplate = list.get(0);
        //将协议模板放入redis中
        RedisUtils.set(RedisConstants.PROTOCOL_TEMPLATE_URL + protocolTemplate.getProtocolId(), protocolTemplate.getProtocolUrl() + "&" + protocolTemplate.getImgUrl());
        //获取协议模板前端显示名称对应的别名
        String alias = ProtocolEnum.getAlias(protocolTemplate.getDisplayName());
        if (StringUtils.isNotBlank(alias)) {
            RedisUtils.set(RedisConstants.PROTOCOL_TEMPLATE_ALIAS + alias, protocolTemplate.getProtocolId());//协议 ID放入redis
        }
        return true;
    }
    
    /**
     * 查询协议图片
     *
     * @param protocolId 协议模版的ID
     * @return
     */
    public List<String> getImgUrlList(String protocolId) throws Exception {

        // 拿出来的信息 /hyjfdata/data/pdf/template/1528268728879.pdf&/hyjfdata/data/pdf/template/1528268728879-0, 1, 2, 3, 4
        String templateUrl = RedisUtils.get(RedisConstants.PROTOCOL_TEMPLATE_URL + protocolId);

        if (StringUtils.isEmpty(templateUrl)) {
            throw new Exception("templateUrl is null");
        }

        if (!templateUrl.contains("&")) {
            throw new Exception("templateUrl is null");
        }

        String[] strUrl = templateUrl.split("&");// &之前的是 pdf路径，&之后的是 图片路径

        //图片地址存储的路径是： /hyjfdata/data/pdf/template/1528087341328-0,1,2
        String imgUrl = strUrl[1];
        if (!imgUrl.contains("-")) {
            throw new Exception("templateUrl is null");
        }

        return getJpgJson(imgUrl);
    }
    
    /**
     * 将图片拆分，配上路径
     *
     * @param imgUrl
     * @return
     */
    public List<String> getJpgJson(String imgUrl) {
        List<String> listImg = new ArrayList<>();
        String[] url = imgUrl.split("-");
        String imgPath = url[0];// /hyjfdata/data/pdf/template/1528087341328
        String[] imgSize = url[1].split(",");// 0,1,2
        for (String str : imgSize) {
            listImg.add(new StringBuilder().append(imgPath).append("/").append(str).append(".jpg").toString());
        }
        return listImg;
    }

}
