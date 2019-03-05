package com.hyjf.cs.trade.controller.api.wdzj.borrowdata;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.vo.wdzj.BorrowListCustomizeVO;
import com.hyjf.am.vo.wdzj.PreapysListCustomizeVO;
import com.hyjf.common.cache.RedisConstants;
import com.hyjf.common.cache.RedisUtils;
import com.hyjf.common.paginator.Paginator;
import com.hyjf.common.util.GetDate;
import com.hyjf.cs.common.controller.BaseController;
import com.hyjf.cs.trade.config.SystemConfig;
import com.hyjf.cs.trade.service.wdzj.BorrowDataService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 网贷之家标的列表
 * @author hesy
 * @version BorrowDataController, v0.1 2018/7/16 15:04
 */
@Api(tags = "api端-网贷之家标的列表接口")
@RestController
@RequestMapping("/hyjf-api/wdzj/borrowdata")
public class BorrowDataController extends BaseController {
    @Autowired
    BorrowDataService borrowDataService;
    @Autowired
    SystemConfig systemConfig;

    /**
     * 标的放款数据接口
     * @param request
     * @param response
     * @return
     */
    @ApiOperation(value = "标的放款数据接口", notes = "标的放款数据接口")
    @ResponseBody
    @RequestMapping("/list")
    public JSONObject getBorrowDataList(HttpServletRequest request, HttpServletResponse response) {
        JSONObject result = new JSONObject();
        String username = systemConfig.getUserNameWDZJ();

        String token = request.getParameter("token");
        String date = request.getParameter("date");
        String page = request.getParameter("page");
        String pageSize = request.getParameter("pageSize");
        logger.info("网贷之家 getBorrowDataList接口请求参数：token=" + token + " date=" + date + " page=" + page + " pageSize" + pageSize);

        // 请求参数校验
        if(StringUtils.isEmpty(date) || StringUtils.isEmpty(token)){
            result = getFailMsg("1", "请求参数非法");
            logger.info("网贷之家 getBorrowDataList接口返回：" + result.toJSONString());
            return result;
        }
        if(StringUtils.isBlank(page)){
            page = "1";
        }
        if(StringUtils.isBlank(pageSize)){
            pageSize = "10";
        }

        //token校验
        if(!tokenCheck(token, username)){
            result = getFailMsg("1", "token校验失败");
            logger.info("网贷之家 getBorrowDataList接口返回：" + result.toJSONString());
            return result;
        }

        // 得出开始结束时间
        Integer timeStart;
        Integer timeEnd;
        try {
            timeStart = GetDate.getDayStart10(date);
            timeEnd = GetDate.getDayEnd10(date);
        } catch (Exception e) {
            e.printStackTrace();
            result = getFailMsg("1", "data格式不正确");
            logger.info("网贷之家 getBorrowDataList接口返回：" + result.toJSONString());
            return result;
        }

        Map<String, Object> paraMap = new HashMap<String, Object>();
        paraMap.put("timeStart", timeStart);
        paraMap.put("timeEnd", timeEnd);

        String totalAmount = borrowDataService.sumBorrowAmount(paraMap);
        Integer count = borrowDataService.countBorrowList(paraMap);
        if (count != null && count > 0) {
            Paginator paginator = new Paginator(Integer.parseInt(page), count, Integer.parseInt(pageSize));
            paraMap.put("limitStart", paginator.getOffset());
            paraMap.put("limitEnd", paginator.getLimit());
            List<BorrowListCustomizeVO> recordList  = borrowDataService.selectBorrowList(paraMap);

            result.put("status", "0");
            result.put("statusDesc", "成功");
            result.put("totalPage", String.valueOf(paginator.getTotalPages()));
            result.put("currentPage", page);
            result.put("totalCount", String.valueOf(count));
            result.put("totalAmount", totalAmount);
            result.put("borrowList", JSON.toJSON(recordList));
        }else{
            result.put("status", "0");
            result.put("statusDesc", "成功");
            result.put("totalPage", "0");
            result.put("currentPage", page);
            result.put("totalCount", "0");
            result.put("totalAmount", "0");
            result.put("borrowList", JSON.toJSON(new ArrayList<BorrowListCustomizeVO>()));
        }

        return result;
    }

    /**
     * 标的提前放款数据接口
     * @param request
     * @param response
     * @return
     */
    @ApiOperation(value = "标的提前放款数据接口", notes = "标的提前放款数据接口")
    @ResponseBody
    @RequestMapping("/preapyslist")
    public JSONObject getPreapysList(HttpServletRequest request, HttpServletResponse response) {
        JSONObject result = new JSONObject();
        String username = systemConfig.getUserNameWDZJ();

        String token = request.getParameter("token");
        String date = request.getParameter("date");
        String page = request.getParameter("page");
        String pageSize = request.getParameter("pageSize");

        // 请求参数校验
        if(StringUtils.isEmpty(date) || StringUtils.isEmpty(token)){
            result = getFailMsg("1", "请求参数非法");
            logger.info("网贷之家 getPreapysList接口返回：" + result.toJSONString());
            return result;
        }
        if(StringUtils.isBlank(page)){
            page = "1";
        }
        if(StringUtils.isBlank(pageSize)){
            pageSize = "10";
        }

        //token校验
        if(!tokenCheck(token, username)){
            result = getFailMsg("1", "token校验失败");
            logger.info("网贷之家 getPreapysList接口返回：" + result.toJSONString());
            return result;
        }

        // 得出开始结束时间
        Integer timeStart;
        Integer timeEnd;
        try {
            timeStart = GetDate.getDayStart10(date);
            timeEnd = GetDate.getDayEnd10(date);
        } catch (Exception e) {
            e.printStackTrace();
            result = getFailMsg("1", "data格式不正确");
            logger.info("网贷之家 getPreapysList接口返回：" + result.toJSONString());
            return result;
        }

        Map<String, Object> paraMap = new HashMap<String, Object>();
        paraMap.put("timeStart", timeStart);
        paraMap.put("timeEnd", timeEnd);

        Integer count = borrowDataService.countPreapysList(paraMap);
        if (count != null && count > 0) {
            Paginator paginator = new Paginator(Integer.parseInt(page), count, Integer.parseInt(pageSize));
            paraMap.put("limitStart", paginator.getOffset());
            paraMap.put("limitEnd", paginator.getLimit());
            List<PreapysListCustomizeVO> recordList  = borrowDataService.selectPreapysList(paraMap);
            result.put("status", 0);
            result.put("statusDesc", "成功");
            result.put("totalPage", paginator.getTotalPages());
            result.put("currentPage", page);
            result.put("preapys", JSON.toJSON(recordList));
        }else{
            result.put("status", 0);
            result.put("statusDesc", "成功");
            result.put("totalPage", "0");
            result.put("currentPage", page);
            result.put("preapys", JSON.toJSON(new ArrayList<PreapysListCustomizeVO>()));
        }

        return result;
    }

    /**
     * token校验
     * @param token
     * @param userName
     * @return
     */
    private boolean tokenCheck(String token, String userName){
//        String key = "token_wdzj_" + userName;
        String key = RedisConstants.KEY_WDZJ_KEY + userName;
        if(!RedisUtils.exists(key)){
            return false;
        }else{
            String tokenInRedis = RedisUtils.get(key);
            return token.equals(tokenInRedis) ? true : false;
        }
    }

    private JSONObject getFailMsg(String status, String desc) {
        JSONObject result = new JSONObject();

        result.put("status", status);
        result.put("statusDesc", desc);
        result.put("borrowList", new JSONArray());
        return result;
    }
}
