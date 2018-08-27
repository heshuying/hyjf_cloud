package com.hyjf.am.trade.dao.mapper.customize;

import java.util.List;
import java.util.Map;

import com.hyjf.am.trade.dao.model.customize.AdminHjhDebtCreditCustomize; /**
 * @Auther:yangchangwei
 * @Date:2018/7/5
 * @Description: 后台列表 汇计划-转让列表
 */
public interface AdminHjhDebtCreditCustomizeMapper {

    int countDebtCredit(Map paramMap);

    List<AdminHjhDebtCreditCustomize> selectDebtCreditList(Map map);
}
