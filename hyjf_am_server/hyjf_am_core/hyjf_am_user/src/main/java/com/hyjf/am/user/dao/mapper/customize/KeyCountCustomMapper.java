package com.hyjf.am.user.dao.mapper.customize;

import com.hyjf.am.resquest.user.KeyCountRequest;
import com.hyjf.am.vo.admin.ChannelStatisticsDetailVO;
import com.hyjf.am.vo.trade.OperationReportJobVO;
import com.hyjf.am.vo.user.KeyCountVO;

import java.util.List;

/**
 * @author tanyy
 * @version KeyCountCustomMapper, v0.1 2018/7/16 17:25
 */

public interface KeyCountCustomMapper {
    /**
     * 取得数据总数量
     *@param request
     * @return
     */
    int countTotal(KeyCountRequest request);

    /**
     * 根据查询条件 取得数据
     * @param request
     * @return
     */
    List<KeyCountVO> searchAction(KeyCountRequest request);

    /**
     * 通过时间统计平台注册人数
     * @param
     * @return
     *
     */
    int countRegistUser();

    /**
     * 用户分析 - 性别分布拆分
     *
     * @param list 多个用户id
     * @return
     */
    List<OperationReportJobVO> getSexCount( List<OperationReportJobVO> list);

    /**
     * 用户分析 - 年龄分布拆分
     *
     * @param list 多个用户id
     * @return
     */
    List<OperationReportJobVO> getAgeCount( List<OperationReportJobVO> list);


    /**
     * 获取用户名
     *
     * @param list 多个用户id
     * @return
     */
    List<OperationReportJobVO> getUserNames( List<OperationReportJobVO> list);
}
