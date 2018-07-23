/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.beans.response;

import com.hyjf.admin.beans.vo.UserChangeLogCustomizeVO;
import com.hyjf.admin.beans.vo.UserManagerUpdateCustomizeVO;

import java.util.List;

/**
 * @author nxl
 * @version UserDetailInfoResponseBean, v0.1 2018/7/16 13:51
 */
public class InitUserUpdResponseBean {
    //用户信息
    private UserManagerUpdateCustomizeVO usersUpdateForm;
    //更改记录
    private List<UserChangeLogCustomizeVO> usersChangeLogForm;

    public UserManagerUpdateCustomizeVO getUsersUpdateForm() {
        return usersUpdateForm;
    }

    public void setUsersUpdateForm(UserManagerUpdateCustomizeVO usersUpdateForm) {
        this.usersUpdateForm = usersUpdateForm;
    }

    public List<UserChangeLogCustomizeVO> getUsersChangeLogForm() {
        return usersChangeLogForm;
    }

    public void setUsersChangeLogForm(List<UserChangeLogCustomizeVO> usersChangeLogForm) {
        this.usersChangeLogForm = usersChangeLogForm;
    }
}
