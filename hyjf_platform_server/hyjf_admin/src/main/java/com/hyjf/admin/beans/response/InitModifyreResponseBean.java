/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.beans.response;

import com.hyjf.am.vo.user.UserChangeLogVO;
import com.hyjf.am.vo.user.UserManagerUpdateVO;
import com.hyjf.am.vo.user.UserRecommendCustomizeVO;

import java.util.List;

/**
 * @author nxl
 * @version UserDetailInfoResponseBean, v0.1 2018/7/16 13:51
 */
public class InitModifyreResponseBean {
    //用户信息
    private UserRecommendCustomizeVO modifyReForm;
    //更改记录
    private List<UserChangeLogVO> usersChangeLogForm;

    public UserRecommendCustomizeVO getModifyReForm() {
        return modifyReForm;
    }

    public void setModifyReForm(UserRecommendCustomizeVO modifyReForm) {
        this.modifyReForm = modifyReForm;
    }

    public List<UserChangeLogVO> getUsersChangeLogForm() {
        return usersChangeLogForm;
    }

    public void setUsersChangeLogForm(List<UserChangeLogVO> usersChangeLogForm) {
        this.usersChangeLogForm = usersChangeLogForm;
    }
}
