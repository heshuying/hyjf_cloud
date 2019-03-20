package com.hyjf.admin.service.vip.content;

import com.hyjf.am.resquest.admin.CustomerTaskConfigRequest;
import com.hyjf.am.resquest.admin.ScreenConfigRequest;
import com.hyjf.am.vo.user.CustomerTaskConfigVO;
import com.hyjf.am.vo.user.ScreenConfigVO;

import java.util.List;
import java.util.Map;

/**
 * Created by future on 2019/3/18.
 */
public interface OperService {

    public List<ScreenConfigVO> operList(ScreenConfigRequest request);

    int operAdd(ScreenConfigVO screenConfigVO);

    ScreenConfigVO operInfo(ScreenConfigVO screenConfigVO);

    int operUpdate(ScreenConfigVO screenConfigVO);

    List<CustomerTaskConfigVO> taskList(CustomerTaskConfigRequest request);

    int taskAdd(CustomerTaskConfigVO customerTaskConfigVO);

    CustomerTaskConfigVO taskInfo(CustomerTaskConfigVO customerTaskConfigVO);

    int taskUpdate(CustomerTaskConfigVO customerTaskConfigVO);

    Map saveCheck(CustomerTaskConfigRequest request);

    boolean blankCheck(int flag, ScreenConfigVO screenConfigVO, CustomerTaskConfigVO customerTaskConfigVO, CustomerTaskConfigRequest request);
}
