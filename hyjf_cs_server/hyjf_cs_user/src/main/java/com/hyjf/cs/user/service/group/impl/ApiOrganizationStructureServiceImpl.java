/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.user.service.group.impl;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.vo.user.OrganizationStructureVO;
import com.hyjf.common.enums.MsgEnum;
import com.hyjf.common.validator.CheckUtil;
import com.hyjf.common.validator.Validator;
import com.hyjf.cs.user.bean.BaseDefine;
import com.hyjf.cs.user.bean.OrganizationStructureRequestBean;
import com.hyjf.cs.user.client.AmUserClient;
import com.hyjf.cs.user.service.group.ApiOrganizationStructureService;
import com.hyjf.cs.user.service.impl.BaseUserServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author: sunpeikai
 * @version: ApiGroupQueryServiceImpl, v0.1 2018/6/27 9:39
 */
@Service
public class ApiOrganizationStructureServiceImpl extends BaseUserServiceImpl implements ApiOrganizationStructureService {
    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    AmUserClient amUserClient;

    /**
     * 查询集团组织结构
     * @param bean 主要是instCode 机构编号
     * */
    @Override
    public List<OrganizationStructureVO> queryInfo(OrganizationStructureRequestBean bean) {
        //校验参数
        CheckUtil.check(Validator.isNotNull(bean.getInstCode()),MsgEnum.STATUS_CE000001);
        //校验签名
        CheckUtil.check(this.verifyRequestSign(bean,BaseDefine.ORGANIZATION_LIST),MsgEnum.ERR_SIGN);
        // 获取信息
        List<OrganizationStructureVO> resultBean = amUserClient.searchGroupInfo();
        // 拼接参数
        log.info("集团组织机构查询返回参数："+ JSONObject.toJSONString(resultBean));

        return resultBean;
    }
}
