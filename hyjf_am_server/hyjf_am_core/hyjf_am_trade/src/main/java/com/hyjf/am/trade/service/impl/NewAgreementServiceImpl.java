/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service.impl;

import com.hyjf.am.bean.app.NewAgreementResultBean;
import com.hyjf.am.trade.dao.mapper.auto.ProtocolTemplateMapper;
import com.hyjf.am.trade.dao.model.auto.ProtocolTemplate;
import com.hyjf.am.trade.dao.model.auto.ProtocolTemplateExample;
import com.hyjf.am.trade.service.NewAgreementService;
import com.hyjf.am.trade.utils.constant.ProtocolEnum;
import com.hyjf.common.cache.RedisConstants;
import com.hyjf.common.cache.RedisUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.ArrayList;
import java.util.List;

/**
 * @author dangzw
 * @version NewAgreementServiceImpl, v0.1 2018/7/31 17:44
 */
public class NewAgreementServiceImpl implements NewAgreementService {

    @Autowired
    ProtocolTemplateMapper protocolTemplateMapper;

    /**
     * 获得协议模板图片
     * @param aliasName 别名
     * @return
     */
    @Override
    public NewAgreementResultBean setProtocolImg(String aliasName){
        NewAgreementResultBean newAgreementResultBean = new NewAgreementResultBean();
        if (StringUtils.isEmpty(aliasName)) {
            newAgreementResultBean.setStatus("99");
            newAgreementResultBean.setStatusDesc("请求参数非法");
            return newAgreementResultBean;
        }

        //是否在枚举中有定义
        String displayName = ProtocolEnum.getDisplayName(aliasName);
        if (StringUtils.isEmpty(displayName)) {
            newAgreementResultBean.setStatus("99");
            newAgreementResultBean.setStatusDesc("请求参数非法");
            return newAgreementResultBean;
        }

        List<String> url = null;
        String protocolId = null;

        protocolId = RedisUtils.get(RedisConstants.PROTOCOL_TEMPLATE_ALIAS + aliasName);
        if (StringUtils.isEmpty(protocolId)) {

            boolean flag = this.setRedisProtocolTemplate(displayName);
            if (!flag) {
                newAgreementResultBean.setStatus("000");
                newAgreementResultBean.setStatusDesc("成功");
                return newAgreementResultBean;
            }
        }

        try {

            url = this.getImgUrlList(protocolId);
            newAgreementResultBean.setStatus("000");
            newAgreementResultBean.setStatusDesc("成功");
            newAgreementResultBean.setRequest(url);
        } catch (Exception e) {
            newAgreementResultBean.setStatus("99");
            newAgreementResultBean.setStatusDesc("数据非法");
        }

        return newAgreementResultBean;
    }

    /**
     * 往Redis中放入协议模板内容
     *
     * @param displayName
     * @return
     */
    public boolean setRedisProtocolTemplate(String displayName) {
        ProtocolTemplateExample examplev = new ProtocolTemplateExample();
        ProtocolTemplateExample.Criteria criteria = examplev.createCriteria();
        criteria.andDisplayNameEqualTo(displayName);
        criteria.andStatusEqualTo(1);
        List<ProtocolTemplate> list = protocolTemplateMapper.selectByExample(examplev);

        if (CollectionUtils.isEmpty(list)) {
            return false;
        }

        ProtocolTemplate protocolTemplate = list.get(0);

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
