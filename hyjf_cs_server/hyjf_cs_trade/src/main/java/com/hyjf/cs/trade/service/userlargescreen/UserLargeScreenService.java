/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.trade.service.userlargescreen;

import com.hyjf.am.vo.screen.ScreenTransferVO;
import com.hyjf.cs.trade.bean.UserLargeScreenResultBean;
import com.hyjf.cs.trade.bean.UserLargeScreenTwoResultBean;

import java.util.List;

/**
 * @author tanyy
 * @version UserLargeScreenService, v0.1 2019/3/18 14:28
 */
public interface UserLargeScreenService {

    UserLargeScreenResultBean getOnePage();

    /**
     * 运营大屏接口-屏幕二数据获取
     * @return
     */
    UserLargeScreenTwoResultBean getTwoPage();

    /**
     * @Author walter.limeng
     * @Description //获取投屏采集的所有用户
     * @Date 14:34 2019-05-29
     * @Param []
     * @return java.util.List<java.lang.Integer>
     **/

    List<ScreenTransferVO> getAllUser(int start, int sizes);

    /**
     * @Author walter.limeng
     * @Description //对ht_user_operate_list表执行更新操作
     * @Date 17:11 2019-05-29
     * @Param [updateList]
     * @return void
     **/
    void updateOperatieList(List<ScreenTransferVO> updateList);

    /**
     * @Author walter.limeng
     * @Description //对ht_user_operate_list表执行删除操作
     * @Date 17:57 2019-05-29
     * @Param [deleteList]
     * @return void
     **/
    void deleteOperatieList(List<ScreenTransferVO> deleteList);

    /**
     * @Author walter.limeng
     * @Description //对ht_repayment_plan表执行更新操作
     * @Date 17:11 2019-05-29
     * @Param [updateList]
     * @return void
     **/
    void updateRepaymentPlan(List<ScreenTransferVO> updateList);

    /**
     * @Author walter.limeng
     * @Description //对ht_repayment_plan表执行删除操作
     * @Date 17:57 2019-05-29
     * @Param [deleteList]
     * @return void
     **/
    void deleteRepaymentPlan(List<ScreenTransferVO> deleteList);
}
