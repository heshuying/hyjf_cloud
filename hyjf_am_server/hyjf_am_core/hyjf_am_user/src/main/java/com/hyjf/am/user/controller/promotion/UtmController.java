package com.hyjf.am.user.controller.promotion;

import com.hyjf.am.response.admin.UtmResponse;
import com.hyjf.am.user.controller.BaseController;
import com.hyjf.am.user.dao.model.auto.Utm;
import com.hyjf.am.user.dao.model.auto.UtmPlat;
import com.hyjf.am.user.service.promotion.UtmService;
import com.hyjf.am.vo.admin.UtmVO;
import com.hyjf.am.vo.admin.promotion.channel.ChannelCustomizeVO;
import com.hyjf.am.vo.admin.promotion.channel.UtmChannelVO;
import com.hyjf.am.vo.user.UtmPlatVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
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
        List<UtmVO> pageList = utmService.getByPageList(map);
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
            response.setRecordTotal(size);
        }
        return response;
    }

    /**
     *  取pc渠道
     * @param sourceId
     * @return
     */
    @RequestMapping("/getutmplat/{sourceId}")
    public UtmResponse getUtmPlat(@PathVariable String sourceId) {
        UtmResponse response = new UtmResponse();
        List<UtmPlatVO> utmPlatList = utmService.getUtmPlat(sourceId);
        response.setResultList(utmPlatList);
        return response;
    }

    /**
     * 获取Utm对象
     * @param utmId
     * @return
     */
    @RequestMapping("/getutmbyutmid/{utmId}")
    public UtmResponse getUtmByUtmId(@PathVariable String utmId) {
        UtmResponse response = new UtmResponse();
        UtmChannelVO utmChannelVO = utmService.getUtmByUtmId(utmId);
        response.setResult(utmChannelVO);
        return response;
    }

    /**
     * 更新或新增Utm对象
     * @param channelCustomizeVO
     * @return
     */
    @RequestMapping("/insertorupdateutm")
    public UtmResponse insertOrUpdateUtm(@RequestBody @Valid ChannelCustomizeVO channelCustomizeVO) {
        UtmResponse response = new UtmResponse();
        try{
            Utm utm = utmService.insertOrUpdateUtm(channelCustomizeVO);
            response.setRtn(UtmResponse.SUCCESS);
        }catch (Exception e){
            response.setRtn(UtmResponse.FAIL);
        }
        return response;
    }
    /**
     * 删除Utm对象
     * @param channelCustomizeVO
     * @return
     */
    @RequestMapping("/deleteutm")
    public UtmResponse deleteUtm(@RequestBody @Valid ChannelCustomizeVO channelCustomizeVO) {
        UtmResponse response = new UtmResponse();
        try{
            Utm utm = utmService.deleteUtm(channelCustomizeVO);
            response.setRtn(UtmResponse.SUCCESS);
        }catch (Exception e){
            response.setRtn(UtmResponse.FAIL);
        }
        return response;
    }

    /**
     * @Author walter.limeng
     * @Description  根据ID查询Utm对象
     * @Date 10:55 2018/7/16
     * @Param id
     * @return
     */
    @RequestMapping("/getutmbyid/{id}")
    public UtmResponse getUtmPlatById(@PathVariable Integer id) {
        UtmResponse response = new UtmResponse();
        UtmPlatVO utmPlat = utmService.getUtmPlatById(id);
        response.setResult(utmPlat);
        return response;
    }

    /**
     * @Author walter.limeng
     * @Description  根据ID查询Utm对象
     * @Date 10:55 2018/7/16
     * @Param id
     * @return
     */
    @RequestMapping("/sourcenameisexists/{sourceName}/{sourceId}")
    public UtmResponse sourceNameIsExists(@PathVariable String sourceName, @PathVariable Integer sourceId) {
        UtmResponse response = new UtmResponse();
        Integer total = utmService.sourceNameIsExists(sourceName,sourceId);
        response.setRecordTotal(total);
        return response;
    }

    /**
     * 更新或新增UtmPlat对象
     * @param utmPlatVO
     * @return
     */
    @RequestMapping("/insertorupdateutmplat")
    public UtmResponse insertOrUpdateUtmPlat(@RequestBody @Valid UtmPlatVO utmPlatVO) {
        UtmResponse response = new UtmResponse();
        try{
            UtmPlat utmPlat = utmService.insertOrUpdateUtmPlat(utmPlatVO);
            response.setRtn(UtmResponse.SUCCESS);
        }catch (Exception e){
            response.setRtn(UtmResponse.FAIL);
        }
        return response;
    }
    /**
     * @Author walter.limeng
     * @Description  删除Utm对象
     * @Date 11:47 2018/7/16
     * @param utmPlatVO
     * @return
     */
    @RequestMapping("/deleteutmplat")
    public UtmResponse deleteUtmPlat(@RequestBody @Valid UtmPlatVO utmPlatVO) {
        UtmResponse response = new UtmResponse();
        try{
            UtmPlat utm = utmService.deleteUtmPlat(utmPlatVO);
            response.setRtn(UtmResponse.SUCCESS);
        }catch (Exception e){
            response.setRtn(UtmResponse.FAIL);
        }
        return response;
    }
}
