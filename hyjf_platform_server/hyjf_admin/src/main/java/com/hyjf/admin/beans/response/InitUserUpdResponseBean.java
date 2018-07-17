/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.beans.response;

import com.hyjf.am.vo.trade.CorpOpenAccountRecordVO;
import com.hyjf.am.vo.user.*;

import java.util.List;

/**
 * @author nxl
 * @version UserDetailInfoResponseBean, v0.1 2018/7/16 13:51
 */
public class InitUserUpdResponseBean {
    //用户信息
    private UserManagerUpdateVO usersUpdateForm;
    //更改记录
    private List<UserChangeLogVO> usersChangeLogForm;

    public UserManagerUpdateVO getUsersUpdateForm() {
        return usersUpdateForm;
    }

    public void setUsersUpdateForm(UserManagerUpdateVO usersUpdateForm) {
        this.usersUpdateForm = usersUpdateForm;
    }

    public List<UserChangeLogVO> getUsersChangeLogForm() {
        return usersChangeLogForm;
    }

    public void setUsersChangeLogForm(List<UserChangeLogVO> usersChangeLogForm) {
        this.usersChangeLogForm = usersChangeLogForm;
    }
}
