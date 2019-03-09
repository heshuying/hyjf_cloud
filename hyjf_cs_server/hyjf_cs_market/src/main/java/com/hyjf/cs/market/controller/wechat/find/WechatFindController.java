/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.market.controller.wechat.find;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.vo.config.ContentArticleCustomizeVO;
import com.hyjf.am.vo.config.ContentArticleVO;
import com.hyjf.cs.market.controller.BaseMarketController;
import com.hyjf.cs.market.service.AppFindService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author fq
 * @version AppFindController, v0.1 2018/7/20 9:29
 */
@Api(tags = "wechat端-微信发现页")
@RestController
@RequestMapping("/hyjf-wechat/find")
public class WechatFindController extends BaseMarketController {
    @Autowired
    private AppFindService appFindService;

    @Value("${hyjf.app.host}")
    public String appHost;

    @ApiOperation(value = "根据类型获取文章", notes = "根据类型获取文章")
    @GetMapping("/contentArticle/getContentArticleListByType.do")
    public JSONObject getContentArticleListByType(HttpServletRequest request, @RequestParam(value = "offset", required = false) String offset,
                                                  @RequestParam(value = "type", required = false) String type,
                                                  @RequestParam(value = "messageId", required = false) Integer messageId,
                                                  @RequestParam(value = "currentPage", defaultValue = "1") Integer currentPage,
                                                  @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize) {
        JSONObject ret = new JSONObject();
        ret.put("currentPage", currentPage);
        ret.put("status", "000");
        ret.put("statusDesc", "请求成功");
        ret.put("endPage", 1);
        try {

            // 检查参数正确性
            if (pageSize<0||currentPage<0){
                ret.put("status", "1");
                ret.put("statusDesc", "分页参数非法");
                return ret;
            }
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("type", type);
            params.put("limitStart", -1);
            params.put("limitEnd", -1);
            // 查询总数
            Integer count = appFindService.countContentArticleByType(type);

            if (count != null && count > 0) {
                // 构造分页
                params.put("limitStart", pageSize * (currentPage - 1));
                params.put("limitEnd", pageSize+1);
                List<ContentArticleCustomizeVO> list=appFindService.getContentArticleListByType(params);

                if (!CollectionUtils.isEmpty(list)) {

                    // todo 上面的方法用错了，原子层不具有通用新，拼接的host是app独有的，应该拿出来在调用者添加  临时代码，需要优化
                    List<ContentArticleCustomizeVO> targetList = new ArrayList<>(list.size());
                    ContentArticleCustomizeVO entity = null;
                    for(ContentArticleCustomizeVO vo: list){
                        entity = new ContentArticleCustomizeVO();
                        BeanUtils.copyProperties(vo,entity);
                        entity.setMessageUrl(vo.getMessageUrl().replace(appHost,""));
                        entity.setShareUrl(vo.getShareUrl().replace(appHost,""));
                        targetList.add(entity);
                    }


                    ret.put("messageCount", count);
                    ret.put("messageList", targetList);
                    if (targetList.size() == (pageSize+1)) {
                        // 0:不是组后一页
                        ret.put("endPage", 0);
                    }
                } else {
                    ret.put("messageCount", "0");
                    ret.put("messageList", new ArrayList<ContentArticleCustomizeVO>());
                }
            } else {
                ret.put("messageCount", "0");
                ret.put("messageList", new ArrayList<ContentArticleCustomizeVO>());
            }
        } catch (Exception e) {
            ret.put("status", "1");
            ret.put("statusDesc", "系统异常请稍后再试");
            ret.put("messageCount", "0");
            ret.put("messageList", new ArrayList<ContentArticleCustomizeVO>());
            return ret;
        }
        return ret;

    }

    @ApiOperation(value = "根据类型和文章编号查找文章", notes = "根据类型和文章编号查找文章")
    @GetMapping("/contentArticle/{type}/{contentArticleId}")
    public JSONObject contentArticle (@PathVariable Integer type,
                                      @PathVariable Integer contentArticleId) {
        JSONObject ret = new JSONObject();
        ret.put("status", "000");
        ret.put("statusDesc", "成功");
        ret.put("topTitle", getTopTitle(type));
        try {
            ContentArticleVO contentArticle = appFindService.getContentArticleById(contentArticleId);
            if(contentArticle!=null){
                JSONObject details = new JSONObject();
                details.put("title",contentArticle.getTitle());
                details.put("content",contentArticle.getContent());
                details.put("date",new SimpleDateFormat("yyyy-MM-dd").format(contentArticle.getCreateTime()));
                ret.put("details", details);
            }else{
                ret.put("status", 99);
                ret.put("statusDesc", "失败");
            }
        } catch (Exception e) {
            ret.put("status", 99);
            ret.put("statusDesc", "失败");
        }
        return ret;
    }

    private String getTopTitle(Integer type) {
        switch (type) {
            case 2:
                return "网站公告";
            case 3:
                return "网贷知识";
            case 5:
                return "关于我们";
            case 101:
                return "风险教育";
            case 8:
                return "联系我们";
            case 20:
                return "公司动态";
            default:
                return "";
        }
    }

}
