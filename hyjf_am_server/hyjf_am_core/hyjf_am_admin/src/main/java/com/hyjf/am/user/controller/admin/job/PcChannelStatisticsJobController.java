package com.hyjf.am.user.controller.admin.job;

import com.hyjf.am.response.admin.UtmResponse;
import com.hyjf.am.response.admin.promotion.ChannelReconciliationResponse;
import com.hyjf.am.resquest.admin.ChannelReconciliationRequest;
import com.hyjf.am.user.controller.BaseController;
import com.hyjf.am.user.dao.model.auto.Utm;
import com.hyjf.am.user.dao.model.auto.UtmPlat;
import com.hyjf.am.user.service.admin.promotion.PcChannelStatisticsJobService;
import com.hyjf.am.vo.admin.UtmVO;
import com.hyjf.am.vo.admin.promotion.channel.ChannelCustomizeVO;
import com.hyjf.am.vo.admin.promotion.channel.ChannelReconciliationVO;
import com.hyjf.am.vo.admin.promotion.channel.UtmChannelVO;
import com.hyjf.am.vo.user.UtmPlatVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * @author walter.limeng
 * @version UtmController, v0.1 2018/7/02 11:17
 */
@RestController
@RequestMapping("/am-admin/promotion/utm")
public class PcChannelStatisticsJobController extends BaseController {
    @Autowired
    private PcChannelStatisticsJobService pcChannelStatisticsJobService;

    /**
     * 分页获取数据
     * @param map 查询参数
     * @return
     */
    @RequestMapping("/getbypagelist")
    public UtmResponse getByPageList(@RequestBody @Valid Map<String, Object> map) {
        UtmResponse response = new UtmResponse();
        List<UtmVO> pageList = pcChannelStatisticsJobService.getByPageList(map);
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
        Integer size = pcChannelStatisticsJobService.getCountByParam(map);
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
        List<UtmPlatVO> utmPlatList = pcChannelStatisticsJobService.getUtmPlat(sourceId);
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
        UtmChannelVO utmChannelVO = pcChannelStatisticsJobService.getUtmByUtmId(utmId);
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
            Utm utm = pcChannelStatisticsJobService.insertOrUpdateUtm(channelCustomizeVO);
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
            Utm utm = pcChannelStatisticsJobService.deleteUtm(channelCustomizeVO);
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
        UtmPlatVO utmPlat = pcChannelStatisticsJobService.getUtmPlatById(id);
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
        Integer total = pcChannelStatisticsJobService.sourceNameIsExists(sourceName,sourceId);
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
            UtmPlat utmPlat = pcChannelStatisticsJobService.insertOrUpdateUtmPlat(utmPlatVO);
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
            UtmPlat utm = pcChannelStatisticsJobService.deleteUtmPlat(utmPlatVO);
            response.setRtn(UtmResponse.SUCCESS);
        }catch (Exception e){
            response.setRtn(UtmResponse.FAIL);
        }
        return response;
    }

    /**
     * 查询访问数
     * @param sourceId 账户推广平台
     * @return
     */
    @RequestMapping("/getaccessnumber/{sourceId}")
    public UtmResponse getAccessNumber(@PathVariable Integer sourceId) {
        UtmResponse response = new UtmResponse();
        try{
            Integer accessNumber = pcChannelStatisticsJobService.getAccessNumber(sourceId);
            response.setAccessNumber(accessNumber);
            response.setRtn(UtmResponse.SUCCESS);
        }catch (Exception e){
            logger.info("失败",e);
            response.setRtn(UtmResponse.FAIL);
        }
        return response;
    }

    /**
     * 查询注册数量
     * @param sourceId 账户推广平台
     * @return
     */
    @RequestMapping("/getregistnumber/{sourceId}")
    public UtmResponse getRegistNumber(@PathVariable Integer sourceId) {
        UtmResponse response = new UtmResponse();
        try{
            Integer registNumber = pcChannelStatisticsJobService.getRegistNumber(sourceId);
            response.setRegistNumber(registNumber);
            response.setRtn(UtmResponse.SUCCESS);
        }catch (Exception e){
            response.setRtn(UtmResponse.FAIL);
        }
        return response;
    }

    /**
     * 查询开户数量
     * @param sourceId 账户推广平台
     * @param type 0无主单 1有主单
     * @return
     */
    @RequestMapping("/getopenaccountnumber/{sourceId}/{type}")
    public UtmResponse getOpenAccountNumber(@PathVariable Integer sourceId,
                                            @PathVariable String type) {
        UtmResponse response = new UtmResponse();
        try{
            Integer openAccountNumber = pcChannelStatisticsJobService.getOpenAccountNumber(sourceId, type);
            response.setOpenAccountNumber(openAccountNumber);
            response.setRtn(UtmResponse.SUCCESS);
        }catch (Exception e){
            response.setRtn(UtmResponse.FAIL);
        }
        return response;
    }


    /**
     * 查询投资人数
     * @param sourceId 账户推广平台
     * @param type 0无主单 1有主单
     * @return
     */
    @RequestMapping("/gettendernumber/{sourceId}/{type}")
    public UtmResponse getTenderNumber(@PathVariable Integer sourceId,
                                            @PathVariable String type) {
        UtmResponse response = new UtmResponse();
        try{
            Integer tenderNumber = pcChannelStatisticsJobService.getTenderNumber(sourceId, type);
            response.setTenderNumber(tenderNumber);
            response.setRtn(UtmResponse.SUCCESS);
        }catch (Exception e){
            response.setRtn(UtmResponse.FAIL);
        }
        return response;
    }
    /**
     * 查询累计充值
     * @param sourceId 账户推广平台
     * @param type 0无主单 1有主单
     * @return
     */
    @RequestMapping("/getcumulativerecharge/{sourceId}/{type}")
    public UtmResponse getCumulativeRecharge(@PathVariable Integer sourceId,
                                       @PathVariable String type) {
        UtmResponse response = new UtmResponse();
        try{
            BigDecimal cumulativeRecharge = pcChannelStatisticsJobService.getCumulativeRecharge(sourceId, type);
            response.setCumulativeRecharge(cumulativeRecharge);
            response.setRtn(UtmResponse.SUCCESS);
        }catch (Exception e){
            response.setRtn(UtmResponse.FAIL);
        }
        return response;
    }
    /**
     * 查询汇直投投资金额
     * @param sourceId 账户推广平台
     * @param type 0无主单 1有主单
     * @return
     */
    @RequestMapping("/gethzttenderprice/{sourceId}/{type}")
    public UtmResponse getHztTenderPrice(@PathVariable Integer sourceId,
                                             @PathVariable String type) {
        UtmResponse response = new UtmResponse();
        try{
            BigDecimal hztTenderPrice = pcChannelStatisticsJobService.getHztTenderPrice(sourceId, type);
            response.setHztTenderPrice(hztTenderPrice);
            response.setRtn(UtmResponse.SUCCESS);
        }catch (Exception e){
            response.setRtn(UtmResponse.FAIL);
        }
        return response;
    }
    /**
     * 查询汇消费投资金额
     * @param sourceId 账户推广平台
     * @param type 0无主单 1有主单
     * @return
     */
    @RequestMapping("/gethxftenderprice/{sourceId}/{type}")
    public UtmResponse getHxfTenderPrice(@PathVariable Integer sourceId,
                                         @PathVariable String type) {
        UtmResponse response = new UtmResponse();
        try{
            BigDecimal hztTenderPrice = pcChannelStatisticsJobService.getHxfTenderPrice(sourceId, type);
            response.setHxfTenderPrice(hztTenderPrice);
            response.setRtn(UtmResponse.SUCCESS);
        }catch (Exception e){
            response.setRtn(UtmResponse.FAIL);
        }
        return response;
    }

    /**
     * 查询汇天利投资金额
     * @param sourceId 账户推广平台
     * @param type 0无主单 1有主单
     * @return
     */
    @RequestMapping("/gethtltenderprice/{sourceId}/{type}")
    public UtmResponse getHtlTenderPrice(@PathVariable Integer sourceId,
                                         @PathVariable String type) {
        UtmResponse response = new UtmResponse();
        try{
            BigDecimal hztTenderPrice = pcChannelStatisticsJobService.getHtlTenderPrice(sourceId, type);
            response.setHtlTenderPrice(hztTenderPrice);
            response.setRtn(UtmResponse.SUCCESS);
        }catch (Exception e){
            response.setRtn(UtmResponse.FAIL);
        }
        return response;
    }

    /**
     * 查询汇添金投资金额
     * @param sourceId 账户推广平台
     * @param type 0无主单 1有主单
     * @return
     */
    @RequestMapping("/gethtjtenderprice/{sourceId}/{type}")
    public UtmResponse getHtjTenderPrice(@PathVariable Integer sourceId,
                                         @PathVariable String type) {
        UtmResponse response = new UtmResponse();
        try{
            BigDecimal hztTenderPrice = pcChannelStatisticsJobService.getHtjTenderPrice(sourceId, type);
            response.setHtjTenderPrice(hztTenderPrice);
            response.setRtn(UtmResponse.SUCCESS);
        }catch (Exception e){
            response.setRtn(UtmResponse.FAIL);
        }
        return response;
    }

    /**
     * 融通宝投资金额
     * @param sourceId 账户推广平台
     * @param type 0无主单 1有主单
     * @return
     */
    @RequestMapping("/getrtbtenderprice/{sourceId}/{type}")
    public UtmResponse getRtbTenderPrice(@PathVariable Integer sourceId,
                                         @PathVariable String type) {
        UtmResponse response = new UtmResponse();
        try{
            BigDecimal hztTenderPrice = pcChannelStatisticsJobService.getRtbTenderPrice(sourceId, type);
            response.setRtbTenderPrice(hztTenderPrice);
            response.setRtn(UtmResponse.SUCCESS);
        }catch (Exception e){
            response.setRtn(UtmResponse.FAIL);
        }
        return response;
    }

    /**
     * 汇转让投资金额
     * @param sourceId 账户推广平台
     * @param type 0无主单 1有主单
     * @return
     */
    @RequestMapping("/gethzrtenderprice/{sourceId}/{type}")
    public UtmResponse getHzrTenderPrice(@PathVariable Integer sourceId,
                                         @PathVariable String type) {
        UtmResponse response = new UtmResponse();
        try{
            BigDecimal hztTenderPrice = pcChannelStatisticsJobService.getHzrTenderPrice(sourceId, type);
            response.setHzrTenderPrice(hztTenderPrice);
            response.setRtn(UtmResponse.SUCCESS);
        }catch (Exception e){
            response.setRtn(UtmResponse.FAIL);
        }
        return response;
    }
    /**
     * 查询pc统计明细散标
     * @return
     */
    @RequestMapping("/select_pc_channel_reconciliation_record")
    public ChannelReconciliationResponse selectPcChannelReconciliationRecord(@RequestBody ChannelReconciliationRequest request) {
        ChannelReconciliationResponse response = new ChannelReconciliationResponse();
        // 查询pc统计明细
        List<ChannelReconciliationVO> list = pcChannelStatisticsJobService.selectPcChannelReconciliationRecord(request);
        if (!CollectionUtils.isEmpty(list)) {
            response.setResultList(list);
        }
        // 查询符合条件的数量
        int count = pcChannelStatisticsJobService.selectPcChannelReconciliationCount(request);
        response.setCount(count);
        return response;
    }

    /**
     * 查询pc统计明细计划
     * @return
     */
    @RequestMapping("/select_pc_channel_reconciliation_record_hjh")
    public ChannelReconciliationResponse selectPcChannelReconciliationRecordHjh(@RequestBody ChannelReconciliationRequest request) {
        ChannelReconciliationResponse response = new ChannelReconciliationResponse();
        // 查询pc统计明细
        List<ChannelReconciliationVO> list = pcChannelStatisticsJobService.selectPcChannelReconciliationRecordHjh(request);
        if (!CollectionUtils.isEmpty(list)) {
            response.setResultList(list);
        }
        // 查询符合条件的数量
        int count = pcChannelStatisticsJobService.selectPcChannelReconciliationHjhCount(request);
        response.setCount(count);
        return response;
    }

    /**
     * 查询app统计明细散标
     * @return
     */
    @RequestMapping("/select_app_channel_reconciliation_record")
    public ChannelReconciliationResponse selectAppChannelReconciliationRecord(@RequestBody ChannelReconciliationRequest request) {
        ChannelReconciliationResponse response = new ChannelReconciliationResponse();
        // 查询app统计明细
        List<ChannelReconciliationVO> list = pcChannelStatisticsJobService.selectAppChannelReconciliationRecord(request);
        if (!CollectionUtils.isEmpty(list)) {
            response.setResultList(list);
        }
        // 查询符合条件的数量
        int count = pcChannelStatisticsJobService.selectAppChannelReconciliationCount(request);
        response.setCount(count);
        return response;
    }

    /**
     * 查询app统计明细计划
     * @return
     */
    @RequestMapping("/select_app_channel_reconciliation_record_hjh")
    public ChannelReconciliationResponse selectAppChannelReconciliationRecordHjh(@RequestBody ChannelReconciliationRequest request) {
        ChannelReconciliationResponse response = new ChannelReconciliationResponse();
        // 查询app统计明细
        List<ChannelReconciliationVO> list = pcChannelStatisticsJobService.selectAppChannelReconciliationRecordHjh(request);
        if (!CollectionUtils.isEmpty(list)) {
            response.setResultList(list);
        }
        // 查询符合条件的数量
        int count = pcChannelStatisticsJobService.selectAppChannelReconciliationHjhCount(request);
        response.setCount(count);
        return response;
    }
}
