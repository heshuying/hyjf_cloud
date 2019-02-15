/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.user.controller.batch.user;

import com.hyjf.cs.user.service.batch.UserEntryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

/**
 * @author yaoyong
 * @version UserEntryController, v0.1 2018/12/18 17:29
 */
@RestController
@ApiIgnore
@RequestMapping("/cs-user/batch")
public class UserEntryController {

    @Autowired
    private UserEntryService userEntryService;

    @RequestMapping("/entryUpdate")
    public void entryUpdate() {
        userEntryService.entryUpdate();
    }

    @RequestMapping("/leaveUpdate")
    public void leaveUpdate() {
        userEntryService.leaveUpdate();
    }
}
