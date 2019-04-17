package com.hyjf.wbs.user.service.constomerinfo;

import com.hyjf.wbs.user.dao.model.auto.UtmReg;
import com.hyjf.wbs.user.service.BaseService;

import java.util.List;

/**
 * @Auther: wxd
 * @Date: 2019-04-15 16:58
 * @Description:
 */
public interface UtmRegService extends BaseService {
    public UtmReg selectUtmInfo(Integer userId,List<Integer> utmId);
}
