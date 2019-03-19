package com.hyjf.admin.controller.vip.content;

import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.service.vip.content.OperService;
import com.hyjf.am.bean.result.BaseResult;
import com.hyjf.am.resquest.admin.ScreenConfigRequest;
import com.hyjf.am.vo.user.ScreenConfigVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Controller("/hyjf-admin/content/oper")
@Api(tags = "会员中心-内容中心-大屏运营部数据配置 admin")
public class OperController {

    @Autowired
    private OperService operService;

    @PostMapping("list")
    @ApiOperation(value = "列表查询",notes = "列表查询")
    private AdminResult list(@RequestBody ScreenConfigRequest request){
        AdminResult result = new AdminResult();
        result.setTotalCount(0);
        List<ScreenConfigVO> list = operService.list(request);
        if (list != null){
            result.setTotalCount(list.size());
        }
        result.setData(list);
        return result;
    }

    @PostMapping("add")
    @ApiOperation(value = "数据新增",notes = "数据新增")
    private AdminResult add(@RequestBody ScreenConfigVO screenConfigVO){
        int insertFlag = operService.add(screenConfigVO);
        if (insertFlag < 1){
            return new AdminResult(BaseResult.FAIL, BaseResult.FAIL_DESC);
        }
        return new AdminResult();
    }

    @PostMapping("update")
    @ApiOperation(value = "数据编辑",notes = "数据编辑")
    private AdminResult update(@RequestBody ScreenConfigVO screenConfigVO){
        int updateFlag = operService.update(screenConfigVO);
        if (updateFlag < 1){
            return new AdminResult(BaseResult.FAIL, BaseResult.FAIL_DESC);
        }
        return new AdminResult();
    }
}
