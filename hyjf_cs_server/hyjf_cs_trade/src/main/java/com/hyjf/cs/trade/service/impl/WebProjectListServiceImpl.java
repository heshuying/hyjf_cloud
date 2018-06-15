package com.hyjf.cs.trade.service.impl;

import com.hyjf.am.response.trade.ProjectListResponse;
import com.hyjf.am.resquest.trade.ProjectListRequest;
import com.hyjf.am.util.Result;
import com.hyjf.am.util.Page;
import com.hyjf.am.vo.trade.WebProjectListCsVO;
import com.hyjf.common.util.CommonUtils;
import com.hyjf.cs.trade.client.WebProjectListClient;
import com.hyjf.cs.trade.service.WebProjectListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class WebProjectListServiceImpl implements WebProjectListService {

    @Autowired
    private WebProjectListClient webProjectListClient;

    /**
     * service进行参数验证和结果集拼装
     * @param request
     * @return
     */
    @Override
    public Result getHomeProductList(ProjectListRequest request) {
        // 参数验证 略
        ProjectListResponse response = webProjectListClient.getHomeProductList(request);
        // 对调用返回的结果进行转换和拼装
        if (response != null && "00".equals((response.getRtn()))){  //判断可以抽出来单独封装成公用方法
            List<WebProjectListCsVO> result = new ArrayList<>();
            Page page = response.getPage();
            Result apiResult = new Result();
            if (page.getTotal() > 0){
                result = CommonUtils.convertBeanList(response.getResultList(),WebProjectListCsVO.class);
                apiResult.setResult(result);
            }
            apiResult.setPage(page);
            return  apiResult;
        }else{ // 如果需要还可以把原子层的错误信息继续向上抛
            throw new RuntimeException("查询原子层异常");
        }
    }
}
