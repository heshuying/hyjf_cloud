package com.hyjf.admin.client.impl;

import com.hyjf.admin.client.VersionConfigClient;
import com.hyjf.am.response.admin.AdminVersionResponse;
import com.hyjf.am.resquest.admin.AdminVersionRequest;
import com.hyjf.am.vo.admin.VersionVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

/**
 * @author by xiehuili on 2018/7/16.
 */
@Service
public class VersionConfigClientImpl implements VersionConfigClient {
    @Autowired
    private RestTemplate restTemplate;

    /**
     * 查询版本设置列表
     * @param adminRequest
     * @return
     */
    @Override
    public AdminVersionResponse versionConfigInit(AdminVersionRequest adminRequest){
        String url = "http://AM-CONFIG/am-config/config/versionconfig/list";
        AdminVersionResponse response = restTemplate.postForEntity(url,adminRequest,AdminVersionResponse.class).getBody();
        if (response != null) {
            return response;
        }
        return null;
    }
    /**
     * 查询详情页面
     * @param adminRequest
     * @return
     */
    @Override
    public AdminVersionResponse searchVersionConfigInfo(AdminVersionRequest adminRequest){
        String url = "http://AM-CONFIG/am-config/config/versionconfig/info";
        AdminVersionResponse response = restTemplate.postForEntity(url,adminRequest,AdminVersionResponse.class).getBody();
        if (response != null) {
            return response;
        }
        return null;
    }
//    /**
//     * 数据字典
//     * @param code
//     * @return
//     */
//    @Override
//    public List<ParamName> getParamNameList(String code){
//        String url = "http://AM-CONFIG/am-config/config/versionconfig/getParamNameList";
//        List<ParamName> response = restTemplate.postForEntity(url,code,List.class).getBody();
//        if (!CollectionUtils.isEmpty(response)) {
//            return response;
//        }
//        return null;
//    }

    /**
     * 编辑保存版本设置
     * @return
     */
    @Override
    public AdminVersionResponse saveVersionConfig(AdminVersionRequest req){
        String url = "http://AM-CONFIG/am-config/config/versionconfig/insert";
        AdminVersionResponse response = restTemplate.postForEntity(url,req,AdminVersionResponse.class).getBody();
        if (response != null) {
            return response;
        }
        return null;
    }

    /**
     * 修改版本设置
     * @return
     */
    @Override
    public AdminVersionResponse updateVersionConfig(AdminVersionRequest req){
        String url = "http://AM-CONFIG/am-config/config/versionconfig/update";
        AdminVersionResponse response = restTemplate.postForEntity(url,req,AdminVersionResponse.class).getBody();
        if (response != null) {
            return response;
        }
        return null;
    }

    /**
     * 删除保证金配置
     * @return
     */
    @Override
    public AdminVersionResponse deleteVersionConfig(Integer id){
        String url = "http://AM-CONFIG/am-config/config/versionconfig/delete";
        AdminVersionResponse response = restTemplate.postForEntity(url,id,AdminVersionResponse.class).getBody();
        if (response != null) {
            return response;
        }
        return null;
    }
    /**
     * 校验版本号是否唯一
     * @return
     */
    @Override
    public VersionVO getVersionByCode(Integer vid, Integer type, String version){
        String url = "http://AM-CONFIG/am-config/config/versionconfig/validationFeild";
        Map<String,Object> map =new HashMap<String,Object>();
        if(vid != null){
            map.put("vid",vid);
        }
        map.put("type",type);
        map.put("version",version);
        VersionVO response = restTemplate.postForEntity(url,map,VersionVO.class).getBody();
        if (response != null) {
            return response;
        }
        return null;
    }
}
