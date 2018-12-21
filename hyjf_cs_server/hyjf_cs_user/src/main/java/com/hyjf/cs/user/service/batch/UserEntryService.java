/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.user.service.batch;

/**
 * @author yaoyong
 * @version UserEntryService, v0.1 2018/12/18 17:31
 */
public interface UserEntryService {
    /**
     * 员工入职修改客户属性
     */
    void entryUpdate();

    /**
     * 员工离职，修改客户属性
     */
    void leaveUpdate();
}
