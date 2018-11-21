package com.hyjf.am.config.controller.web;

import com.hyjf.am.config.controller.BaseConfigController;
import com.hyjf.am.config.dao.model.auto.ContentHelp;
import com.hyjf.am.config.dao.model.customize.HelpCategoryCustomize;
import com.hyjf.am.config.dao.model.customize.HelpContentCustomize;
import com.hyjf.am.config.service.CategoryService;
import com.hyjf.am.response.admin.CategoryResponse;
import com.hyjf.am.vo.admin.ContentHelpCustomizeVO;
import com.hyjf.am.vo.admin.ContentHelpVO;
import com.hyjf.common.util.CommonUtils;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * @Auther: walter.limeng
 * @Date: 2018/7/20 16:28
 * @Description: CategoryController
 */
@RestController
@RequestMapping("/am-config/content/help")
public class WisdomToothController extends BaseConfigController {
    private static final Logger logger = LoggerFactory.getLogger(WisdomToothController.class);

    @Autowired
    private CategoryService categoryService;

    /**
     * @Author walter.tanyy
     * @user walter.tanyy
     * @Description  通过ID查询问题
     * @Date 14:36 2018/7/24
     * @Param contentId  问题ID
     * @return
     */
    @GetMapping("/help/{id}")
    public ContentHelpVO contentHelp(@PathVariable Integer id) {
        ContentHelp con = this.categoryService.queryContentById(id);
        ContentHelpVO vo = CommonUtils.convertBean(con,ContentHelpVO.class);
        return vo;
    }
    /**
     * @Author walter.tanyy
     * @user walter.tanyy
     * @Description  智齿客服常见问题列表
     * @Date 14:36 2018/7/24
     * @Param contentId  问题ID
     * @return
     */
    @GetMapping("/contenthelps")
    public CategoryResponse contentHelps()  throws Exception {
        CategoryResponse response = new CategoryResponse();
        ContentHelpCustomizeVO contentHelpCustomize = new ContentHelpCustomizeVO();
        contentHelpCustomize.setStatus(1);
        contentHelpCustomize.setZhiChiStatus(1);
        List<ContentHelpCustomizeVO> pageList= categoryService.queryContentCustomize(contentHelpCustomize);
        // 按照帮助中心逻辑生成itemId
        List<HelpCategoryCustomize> list = categoryService.selectCategory("help");
        List<Map<String, Object>> AllList = new ArrayList<Map<String, Object>>();
        for (int i = 0; i < list.size(); i++) {
            String typeId = "hp"+i;
            String itemId = "is"+i;
            Map<String, Object> tmpmap = new HashMap<String, Object>();
            HelpCategoryCustomize HelpCategoryCustomize = list.get(i);
            HelpCategoryCustomize.setItemId(itemId);
            tmpmap.put("HelpCategoryCustomize", list.get(i));
            // 查出帮助中心子分类
            List<HelpCategoryCustomize> listsun = categoryService.selectSunCategory(list.get(i).getId() + "");
            if (listsun != null) {
                for (int j = 0; j < listsun.size(); j++) {
                    HelpCategoryCustomize HelpCategoryCustomize1 = listsun.get(j);
                    HelpCategoryCustomize1.setItemId(typeId+(j+1));
                    List<HelpContentCustomize> listsunContent = categoryService.selectSunContentCategory(
                            String.valueOf(HelpCategoryCustomize1.getId()), String.valueOf(HelpCategoryCustomize1.getPid()));
                    for(int k=0;k<listsunContent.size();k++){
                        HelpContentCustomize HelpContentCustomize2 =  listsunContent.get(k);
                        HelpContentCustomize2.setTypeId(typeId+(j+1));
                        HelpContentCustomize2.setItemId(itemId+j+(k+1));
                    }
                    HelpCategoryCustomize1.setListsunContent(listsunContent);
                }
                tmpmap.put("listsun", listsun);
            }
            AllList.add(tmpmap);
        }
        for(int i =0;i< pageList.size();i++){
            ContentHelpCustomizeVO help = pageList.get(i);
            for(int j = 0;j<AllList.size();j++){
                Map<String, Object> mapList = AllList.get(j);
                if(mapList!=null){
                    List<HelpCategoryCustomize> listsun = (List<HelpCategoryCustomize>)mapList.get("listsun");
                    if(!CollectionUtils.isEmpty(listsun)){
                        for(int k=0;k<listsun.size();k++){
                            List<HelpContentCustomize> listsunContent =  listsun.get(k).getListsunContent();
                            if(!CollectionUtils.isEmpty(listsunContent)){
                                for(int ii=0;ii<listsunContent.size();ii++){
                                    if(listsunContent.get(ii).getId().equals(help.getId().toString())){
                                        help.setTypeId(listsunContent.get(ii).getTypeId());
                                        help.setItemId(listsunContent.get(ii).getItemId());
                                    }
                                }
                            }
                        }
                    }
                }

            }
        }
        response.setRecordList(pageList);
        return response;
    }

}
