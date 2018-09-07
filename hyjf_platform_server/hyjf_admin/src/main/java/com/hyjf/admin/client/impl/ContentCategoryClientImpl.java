package com.hyjf.admin.client.impl;

import com.hyjf.admin.client.ContentCategoryClient;
import com.hyjf.am.response.admin.CategoryResponse;
import com.hyjf.am.resquest.admin.CategoryBeanRequest;
import com.hyjf.am.resquest.admin.ContentHelpBeanRequest;
import com.hyjf.am.vo.admin.CategoryVO;
import com.hyjf.am.vo.admin.ContentHelpVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * @Auther: walter.limeng
 * @Date: 2018/7/20 16:21
 * @Description: ContentCategoryClientImpl
 */
@Service
public class ContentCategoryClientImpl implements ContentCategoryClient {
    @Autowired
    private RestTemplate restTemplate;

    @Override
    public CategoryResponse getCategoryPage(CategoryBeanRequest categoryBeanRequest) {
        CategoryResponse response = restTemplate.postForEntity("http://AM-CONFIG/am-config/content/help/searchaction",
                categoryBeanRequest, CategoryResponse.class).getBody();
        if(null != response){
            return response;
        }
        return null;
    }

    @Override
    public CategoryResponse changeSubTypeAction(CategoryBeanRequest categoryBeanRequest) {
        CategoryResponse response = restTemplate.postForEntity("http://AM-CONFIG/am-config/content/help/changesubtype",
                categoryBeanRequest, CategoryResponse.class).getBody();
        if(null != response){
            return response;
        }
        return null;
    }

    @Override
    public CategoryResponse infoTypeAction(Integer id) {
        CategoryResponse response = restTemplate.getForEntity("http://AM-CONFIG/am-config/content/help/infotypeaction/"+id,
                CategoryResponse.class).getBody();
        if(null != response){
            return response;
        }
        return null;
    }

    @Override
    public CategoryResponse infoSubTypeAction(CategoryBeanRequest categoryBeanRequest) {
        CategoryResponse response = restTemplate.postForEntity("http://AM-CONFIG/am-config/content/help/infosubtypeaction",
                categoryBeanRequest, CategoryResponse.class).getBody();
        if(null != response){
            return response;
        }
        return null;
    }

    @Override
    public Integer insertCategory(CategoryVO categoryVO) {
        CategoryResponse response = restTemplate.postForEntity("http://AM-CONFIG/am-config/content/help/insertcategory",
                categoryVO, CategoryResponse.class).getBody();
        if(null != response){
            return response.getFlag();
        }
        return null;
    }

    @Override
    public Integer updateAction(CategoryVO categoryVO) {
        CategoryResponse response = restTemplate.postForEntity("http://AM-CONFIG/am-config/content/help/updateaction",
                categoryVO, CategoryResponse.class).getBody();
        if(null != response){
            return response.getFlag();
        }
        return null;
    }

    @Override
    public CategoryResponse getCategoryCount(CategoryVO categoryVO) {
        CategoryResponse response = restTemplate.postForEntity("http://AM-CONFIG/am-config/content/help/getcategorycount",
                categoryVO, CategoryResponse.class).getBody();
        if(null != response){
            return response;
        }
        return null;
    }

    @Override
    public Integer getCountByPcateIdAndcateId(Integer pid, Integer cid) {
        CategoryVO request = new CategoryVO();
        request.setPid(pid);
        request.setId(cid);
        CategoryResponse response = restTemplate.postForObject("http://AM-CONFIG/am-config/content/help/getbypcateidAandcateid", request,
                CategoryResponse.class);
        if(null != response){
            return response.getCount();
        }
        return null;
    }

    @Override
    public List<ContentHelpVO> getListByPcateIdAndcateId(Integer pid, Integer cid) {
        CategoryResponse response = restTemplate.getForEntity("http://AM-CONFIG/am-config/content/help/getlistbypcateidandcateid/"+pid+"/"+cid,
                CategoryResponse.class).getBody();
        if(null != response){
            return response.getRecordList();
        }
        return null;
    }

    @Override
    public Integer delContentHelp(Integer id) {
        CategoryResponse response = restTemplate.getForEntity("http://AM-CONFIG/am-config/content/help/delcontenthelp/"+id,
                CategoryResponse.class).getBody();
        if(null != response){
            return response.getFlag();
        }
        return null;
    }

    @Override
    public Integer delCategory(Integer id) {
        CategoryResponse response = restTemplate.getForEntity("http://AM-CONFIG/am-config/content/help/delcategory/"+id,
                CategoryResponse.class).getBody();
        if(null != response){
            return response.getFlag();
        }
        return null;
    }

    @Override
    public Integer updateContentHelp(ContentHelpVO contentHelpVO) {
        CategoryResponse response = restTemplate.postForEntity("http://AM-CONFIG/am-config/content/help/updatecontenthelp",contentHelpVO,
                CategoryResponse.class).getBody();
        if(null != response){
            return response.getFlag();
        }
        return null;
    }

    @Override
    public CategoryResponse getHelpPage(ContentHelpBeanRequest contentHelpBeanRequest) {
        CategoryResponse response = restTemplate.postForEntity("http://AM-CONFIG/am-config/content/help/gethelppage",contentHelpBeanRequest,
                CategoryResponse.class).getBody();
        if(null != response){
            return response;
        }
        return null;
    }

    @Override
    public CategoryResponse getHelpInfo(ContentHelpBeanRequest contentHelpBeanRequest) {
        CategoryResponse response = restTemplate.postForEntity("http://AM-CONFIG/am-config/content/help/gethelpinfo",contentHelpBeanRequest,
                CategoryResponse.class).getBody();
        if(null != response){
            return response;
        }
        return null;
    }

    @Override
    public CategoryResponse insertHelpInfo(ContentHelpBeanRequest contentHelpBeanRequest) {
        CategoryResponse response = restTemplate.postForEntity("http://AM-CONFIG/am-config/content/help/inserthelpinfo",contentHelpBeanRequest,
                CategoryResponse.class).getBody();
        if(null != response){
            return response;
        }
        return null;
    }

    @Override
    public CategoryResponse updateHelpAction(ContentHelpBeanRequest contentHelpBeanRequest) {
        CategoryResponse response = restTemplate.postForEntity("http://AM-CONFIG/am-config/content/help/updatehelpaction",contentHelpBeanRequest,
                CategoryResponse.class).getBody();
        if(null != response){
            return response;
        }
        return null;
    }

    @Override
    public Integer chanContentHelp(Integer contentId, Integer status, Integer zhiChiStatus) {
        CategoryResponse response = restTemplate.getForEntity("http://AM-CONFIG/am-config/content/help/chancontenthelp/"+contentId+"/"+status+"/"+zhiChiStatus,
                CategoryResponse.class).getBody();
        if(null != response){
            return response.getFlag();
        }
        return null;
    }

    @Override
    public CategoryResponse getOftenInitPage(ContentHelpBeanRequest contentHelpBeanRequest) {
        CategoryResponse response = restTemplate.postForEntity("http://AM-CONFIG/am-config/content/help/getofteninitpage",contentHelpBeanRequest,
                CategoryResponse.class).getBody();
        if(null != response){
            return response;
        }
        return null;
    }
}
