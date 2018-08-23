package com.hyjf.am.response.user;

import com.hyjf.am.response.Response;
import com.hyjf.am.vo.admin.BankOpenAccountLogVO;

/**
 * @version BankOpenAccountLogResponse, v0.1 2018/8/21 13:56
 * @Author: Zha Daojian
 */
public class BankOpenAccountLogResponse  extends Response<BankOpenAccountLogVO> {

    /**
     * 新增flag 0失败 1成功
     */
    private boolean insertFlag;
    /**
     * 更新flag 0失败 1成功
     */
    private boolean updateFlag;
    /**
     * 删除flag 0失败 1成功
     */
    private boolean deleteFlag;

    /**
     * 校验返回的电子账号是否已被使用flag 0未开户 1已开户
     */
    private boolean bankOpenFlag;

    public boolean isInsertFlag() {
        return insertFlag;
    }

    public void setInsertFlag(boolean insertFlag) {
        this.insertFlag = insertFlag;
    }

    public boolean isUpdateFlag() {
        return updateFlag;
    }

    public void setUpdateFlag(boolean updateFlag) {
        this.updateFlag = updateFlag;
    }

    public boolean isDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(boolean deleteFlag) {
        this.deleteFlag = deleteFlag;
    }

    public boolean isBankOpenFlag() {
        return bankOpenFlag;
    }

    public void setBankOpenFlag(boolean bankOpenFlag) {
        this.bankOpenFlag = bankOpenFlag;
    }
}
