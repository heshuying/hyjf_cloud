package com.hyjf.admin.controller.vip.content;

import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.service.vip.content.OperService;
import com.hyjf.am.bean.result.BaseResult;
import com.hyjf.am.resquest.admin.ScreenConfigRequest;
import com.hyjf.am.vo.user.ScreenConfigVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(value = "会员中心-内容中心 admin", tags = "会员中心-内容中心 admin")
@RequestMapping("/hyjf-admin/login")
@RestController
public class OperController {

    @Autowired
    private OperService operService;

    @ApiOperation(value = "大屏运营部数据配置-列表查询",notes = "大屏运营部数据配置-列表查询")
    @PostMapping("/oper/list")
    @ResponseBody
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

    @ApiOperation(value = "大屏运营部数据配置-数据新增",notes = "大屏运营部数据配置-数据新增")
    @PostMapping("/oper/add")
    @ResponseBody
    private AdminResult add(@RequestBody ScreenConfigVO screenConfigVO){
        screenConfigVO.setStatus(1);
        int insertFlag = operService.add(screenConfigVO);
        if (insertFlag < 1){
            return new AdminResult(BaseResult.FAIL, BaseResult.FAIL_DESC);
        }
        return new AdminResult();
    }


    @ApiOperation(value = "大屏运营部数据配置-数据编辑/启用/禁用",notes = "大屏运营部数据配置-数据编辑/启用/禁用")
    @PostMapping("/oper/update")
    @ResponseBody
    private AdminResult update(@RequestBody ScreenConfigVO screenConfigVO){
        int updateFlag = operService.update(screenConfigVO);
        if (updateFlag < 1){
            return new AdminResult(BaseResult.FAIL, BaseResult.FAIL_DESC);
        }
        return new AdminResult();
    }
}
