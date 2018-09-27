package com.hyjf.am.trade.service.front.config;

import com.hyjf.am.resquest.admin.AdminBorrowStyleRequest;
import com.hyjf.am.trade.dao.model.auto.BorrowStyle;
import com.hyjf.am.trade.dao.model.auto.BorrowStyleWithBLOBs;

import java.util.List;

/**
 * @author by xiehuili on 2018/7/12.
 */
public interface BorrowStyleService {

    /**
     * 查询还款方式总数
     * @param
     */
    public Integer getBorrowStyleCount();
    /**
     * 查询还款方式分页记录
     * @param limitStart,limitEnd
     */
    public List<BorrowStyleWithBLOBs> selectBorrowStyleListByPage(AdminBorrowStyleRequest reques, int limitStart, int limitEnd);

    /**
     * 根据id查询还款方式
     * @param id
     */
    public BorrowStyleWithBLOBs searchBorrowStyleInfoById(Integer id);

    /**
     * 根据主键判断权限维护中权限是否存在
     *
     * @return
     */
    public boolean isExistsPermission(BorrowStyle record);
    /**
     *  添加还款方式
     * @param adminRequest
     */
    public void insertBorrowStyle(AdminBorrowStyleRequest adminRequest);
    /**
     *  修改还款方式
     * @param adminRequest
     */
    public void updateBorrowStyleById(AdminBorrowStyleRequest adminRequest);
    /**
     * 根据id删除还款方式
     * @param id
     */
    public void deleteBorrowStyleById(Integer id);

    /**
     * 获取还款方式
     * @param borrowStyle
     * @return
     */
    BorrowStyle getBorrowStyle(String borrowStyle);
}