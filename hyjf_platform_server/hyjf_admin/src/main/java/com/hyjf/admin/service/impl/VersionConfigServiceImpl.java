package com.hyjf.admin.service.impl;

import com.hyjf.admin.client.AmTradeClient;
import com.hyjf.admin.client.VersionConfigClient;
import com.hyjf.admin.service.VersionConfigService;
import com.hyjf.am.response.admin.AdminVersionResponse;
import com.hyjf.am.resquest.admin.AdminVersionRequest;
import com.hyjf.am.vo.admin.VersionVO;
import com.hyjf.am.vo.admin.coupon.ParamName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author by xiehuili on 2018/7/16.
 */
@Service("adminVersionConfigService")
public class VersionConfigServiceImpl implements VersionConfigService {

    @Autowired
    private AmTradeClient amTradeClient;
    @Autowired
    private VersionConfigClient versionConfigClient;
    /**
     * 查询版本设置列表
     * @param adminRequest
     * @return
     */
    @Override
    public AdminVersionResponse versionConfigInit(AdminVersionRequest adminRequest){
        AdminVersionResponse response = new AdminVersionResponse();
        response =versionConfigClient.versionConfigInit(adminRequest);
        //数据字典
        List<ParamName> versionName = this.amTradeClient.getParamNameList("VERSION_NAME");
        List<ParamName> isUpdate = this.amTradeClient.getParamNameList("IS_UPDATE");
        response.getResult().setVersionNames(versionName);
        response.getResult().setIsUpdates(isUpdate);
        return response;
    }
    /**
     * 查询详情页面
     * @param adminRequest
     * @return
     */
    @Override
    public AdminVersionResponse searchVersionConfigInfo(AdminVersionRequest adminRequest){
        AdminVersionResponse response = new AdminVersionResponse();
        response= versionConfigClient.searchVersionConfigInfo(adminRequest);
        //数据字典
        List<ParamName> versionName = this.amTradeClient.getParamNameList("VERSION_NAME");
        List<ParamName> isUpdate = this.amTradeClient.getParamNameList("IS_UPDATE");
        response.getResult().setVersionNames(versionName);
        response.getResult().setIsUpdates(isUpdate);
        return response;
    }
    /**
     * 数据字典
     * @param code
     * @return
     */
    @Override
    public List<ParamName> getParamNameList(String code){
        return amTradeClient.getParamNameList(code);
    }

    /**
     * 编辑保存版本设置
     * @return
     */
    @Override
    public AdminVersionResponse saveVersionConfig(AdminVersionRequest req){
        return versionConfigClient.saveVersionConfig(req);
    }

    /**
     * 修改版本设置
     * @return
     */
    @Override
    public AdminVersionResponse updateVersionConfig(AdminVersionRequest req){
        return versionConfigClient.updateVersionConfig(req);
    }

    /**
     * 删除保证金配置
     * @return
     */
    @Override
    public AdminVersionResponse deleteVersionConfig(Integer id){
        return versionConfigClient.deleteVersionConfig(id);
    }
    /**
     * 校验版本号是否唯一
     * @return
     */
    @Override
    public VersionVO getVersionByCode(Integer vid, Integer type, String version){
        return versionConfigClient.getVersionByCode(vid,type,version);
    }

}
