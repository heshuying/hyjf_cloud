package com.hyjf.cs.trade.service.wjt.impl;

import com.hyjf.am.resquest.trade.ProjectListRequest;
import com.hyjf.am.vo.trade.WebProjectListCsVO;
import com.hyjf.am.vo.trade.WebProjectListCustomizeVO;
import com.hyjf.am.vo.trade.borrow.BorrowProjectTypeVO;
import com.hyjf.common.util.CommonUtils;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.util.GetDate;
import com.hyjf.cs.common.bean.result.WebResult;
import com.hyjf.cs.common.util.Page;
import com.hyjf.cs.trade.bean.BorrowProjectListBean;
import com.hyjf.cs.trade.config.SystemConfig;
import com.hyjf.cs.trade.service.impl.BaseTradeServiceImpl;
import com.hyjf.cs.trade.service.wjt.WjtWebProjectListService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author pangchengchao
 * @version WjtWebProjectListServiceImpl,  v0.1  2019/5/14  13:46
 * @description 类说明
 */
@Service
public class WjtWebProjectListServiceImpl extends BaseTradeServiceImpl implements WjtWebProjectListService {


    @Autowired
    private SystemConfig systemConfig;
    /**
     * 获取温金投Web端项目列表
     * @author pcc
     * @date 2018/10/9 15:48
     */
    @Override
    public WebResult searchWjtWebProjectListNew(ProjectListRequest request) {
        WebResult result = new WebResult();
        BorrowProjectListBean resultBean = new BorrowProjectListBean();
        // 初始化分页参数，并组合到请求参数
        Page page = Page.initPage(request.getCurrPage(), request.getPageSize());

        List<BorrowProjectTypeVO> borrowTypes = amTradeClient.getProjectTypeList();
        String projectType = request.getProjectType();// 项目类型
        String borrowClass = request.getBorrowClass();// 项目子类型
        // 校验相应的项目类型
        if (borrowTypes != null && borrowTypes.size() > 0) {
            boolean typeFlag = false;
            boolean classFlag = false;
            if (StringUtils.isNotBlank(projectType)) {
                for (BorrowProjectTypeVO borrowType : borrowTypes) {
                    String type = borrowType.getBorrowProjectType();
                    if (type.equals(projectType)) {
                        typeFlag = true;
                    }
                    if (StringUtils.isNotBlank(borrowClass)) {
                        String classType = borrowType.getBorrowClass();
                        if (classType.equals(borrowClass)) {
                            classFlag = true;
                        }
                    } else {
                        classFlag = true;
                    }
                }
            } else {
                resultBean.setList(new ArrayList<WebProjectListCsVO>());
                page.setTotal(0);
            }
            if (typeFlag && classFlag) {

                request.setProjectType(projectType);
                request.setBorrowClass(borrowClass);
                request.setPublishInstCode(systemConfig.getWjtInstCode());

                // 统计相应的汇直投的数据记录数
                int projectToal = amTradeClient.countWjtWebProjectList(request);

                Map<String, Object> params = new HashMap<String, Object>();
                if (projectToal > 0) {

                    //add by cwyang 项目列表显示2页
                    int pageNum = 2;
                    if(projectToal > request.getPageSize() * pageNum){
                        projectToal = request.getPageSize() * pageNum;
                    }

                    page.setTotal(projectToal);
                    // 查询相应的汇直投列表数据
                    int limit = page.getLimit();
                    int offSet = page.getOffset();

                    if (offSet == 0 || offSet > 0) {
                        request.setLimitStart(offSet);
                    }
                    if (limit > 0) {
                        request.setLimitEnd(limit);
                    }
                    List<WebProjectListCustomizeVO> projectList = amTradeClient.searchWjtWebProjectList(request);
                    resultBean.setList( CommonUtils.convertBeanList(projectList, WebProjectListCsVO.class));
                    //int nowTime = GetDate.getNowTime10();
                    // result.setNowTime(nowTime);
                } else {
                    resultBean.setList(new ArrayList<WebProjectListCsVO>());
                    page.setTotal(0);
                }
            } else {
                resultBean.setList(new ArrayList<WebProjectListCsVO>());
                page.setTotal(0);
            }
        } else {
            resultBean.setList(new ArrayList<WebProjectListCsVO>());
            page.setTotal(0);
        }
        resultBean.setNowTime(GetDate.getNowTime10());
        result.setData(resultBean);
        result.setPage(page);
        return result;
    }
}
