package com.hyjf.am.user.controller.promotion;

import com.hyjf.am.response.admin.UtmResponse;
import com.hyjf.am.user.controller.BaseController;
import com.hyjf.am.user.service.promotion.UtmService;
import com.hyjf.am.vo.admin.UtmVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * @author walter.limeng
 * @version UtmController, v0.1 2018/7/02 11:17
 */
@RestController
@RequestMapping("/am-user/promotion/utm")
public class UtmController extends BaseController {
    @Autowired
    private UtmService utmService;

    /**
     * 分页获取数据
     * @param map 查询参数
     * @return
     */
    @RequestMapping("/getbypagelist")
    public UtmResponse getByPageList(@RequestBody @Valid Map<String, Object> map) {
        UtmResponse response = new UtmResponse();
        List<UtmVo> pageList = utmService.getByPageList(map);
        if (pageList != null) {
            response.setResultList(pageList);
        }
        return response;
    }

    /**
     * 获取总条数
     * @param map 查询参数
     * @return UtmResponse
     */
    @RequestMapping("/getcount")
    public UtmResponse getCountByParam(@RequestBody @Valid Map<String, Object> map) {
        UtmResponse response = new UtmResponse();
        Integer size = utmService.getCountByParam(map);
        if (size != null) {
            response.setRecordTotal(size+"");
        }
        return response;
    }
}
