package com.hyjf.am.user.service.admin.vip.content;

import com.hyjf.am.resquest.admin.CustomerTaskConfigRequest;
import com.hyjf.am.resquest.admin.ScreenConfigRequest;
import com.hyjf.am.user.dao.model.auto.CustomerTaskConfig;
import com.hyjf.am.user.dao.model.auto.ScreenConfig;
import com.hyjf.am.vo.user.CustomerTaskConfigVO;
import com.hyjf.am.vo.user.ScreenConfigVO;

import java.util.List;

/**
 * Created by future on 2019/3/18.
 */
public interface OperService {

    int countOperList(ScreenConfigRequest request);

    List<ScreenConfigVO> operList(ScreenConfigRequest request);

    int operAdd(ScreenConfigVO screenConfigVO);

    ScreenConfig operInfo(Integer id);

    int operUpdate(ScreenConfigVO screenConfigVO);

    List<CustomerTaskConfigVO> taskList(CustomerTaskConfigRequest request);

    int taskAdd(CustomerTaskConfigVO customerTaskConfigVO);

    CustomerTaskConfig taskInfo(Integer id);

    int taskUpdate(CustomerTaskConfigVO customerTaskConfigVO);

    int countTaskList(CustomerTaskConfigRequest request);
}
