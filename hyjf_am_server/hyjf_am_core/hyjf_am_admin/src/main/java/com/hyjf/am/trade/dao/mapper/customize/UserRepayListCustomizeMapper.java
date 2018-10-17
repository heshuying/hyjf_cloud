package com.hyjf.am.trade.dao.mapper.customize;
import java.util.Map;

public interface UserRepayListCustomizeMapper {

    /**
     * 根据用户id查找标的投资详情表和标的放款记录（投资人） 总表 的个数
     * @param params
     * @return
     */
    int countUserPayProjectRecordTotal(Map<String, Object> params);

    /**
     * 根据用户id查找标的还款记录的个数
     * @param userid
     * @return
     */
    int selectCanDismissRePay(int userid);
}