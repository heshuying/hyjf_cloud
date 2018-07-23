/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.beans.response;

import com.hyjf.admin.beans.vo.UserChangeLogCustomizeVO;
import com.hyjf.admin.beans.vo.UserRecommendCustomizeShowVO;

import java.util.List;

/**
 * @author nxl
 * @version UserDetailInfoResponseBean, v0.1 2018/7/16 13:51
 */
public class InitModifyreResponseBean {
    //用户信息
    private UserRecommendCustomizeShowVO modifyReForm;
    //更改记录
    private List<UserChangeLogCustomizeVO> usersChangeLogForm;

    public UserRecommendCustomizeShowVO getModifyReForm() {
        return modifyReForm;
    }

    public void setModifyReForm(UserRecommendCustomizeShowVO modifyReForm) {
        this.modifyReForm = modifyReForm;
    }

    public List<UserChangeLogCustomizeVO> getUsersChangeLogForm() {
        return usersChangeLogForm;
    }

    public void setUsersChangeLogForm(List<UserChangeLogCustomizeVO> usersChangeLogForm) {
        this.usersChangeLogForm = usersChangeLogForm;
    }
}
