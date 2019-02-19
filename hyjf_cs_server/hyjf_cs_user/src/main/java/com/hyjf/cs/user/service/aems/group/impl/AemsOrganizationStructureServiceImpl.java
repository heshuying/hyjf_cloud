/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.user.service.aems.group.impl;

import com.hyjf.am.vo.user.*;
import com.hyjf.common.cache.RedisConstants;
import com.hyjf.common.cache.RedisUtils;
import com.hyjf.common.enums.MsgEnum;
import com.hyjf.common.util.GetDate;
import com.hyjf.common.validator.CheckUtil;
import com.hyjf.common.validator.Validator;
import com.hyjf.cs.user.bean.AemsOrganizationStructureBean;
import com.hyjf.cs.user.bean.BaseDefine;
import com.hyjf.cs.user.service.aems.group.AemsOrganizationStructureService;
import com.hyjf.cs.user.service.impl.BaseUserServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 *
 * @author Zha Daojian
 * @date 2018/12/7 14:03
 * @param 
 * @return 
 **/
@Service
public class AemsOrganizationStructureServiceImpl extends BaseUserServiceImpl implements AemsOrganizationStructureService {


    /**
     * 获取集团组织架构信息
     * @param bean
     * @return
     */
    @Override
    public List<OrganizationStructureVO> searchOrganizationList(AemsOrganizationStructureBean bean) {
        //校验参数
        CheckUtil.check(Validator.isNotNull(bean.getInstCode()),MsgEnum.STATUS_CE000001);
        //校验签名
        CheckUtil.check(this.verifyRequestSign(bean,BaseDefine.ORGANIZATION_LIST),MsgEnum.ERR_SIGN);

        List<OrganizationStructureVO> resultBean = new ArrayList<>();
        // 获取信息
        try{
            resultBean = amUserClient.searchGroupInfo();
        }catch (Exception e){
            logger.info("查询异常",e);
        }
        return resultBean;
    }

}
