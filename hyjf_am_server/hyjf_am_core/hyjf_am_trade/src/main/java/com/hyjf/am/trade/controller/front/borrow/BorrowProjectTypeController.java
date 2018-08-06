package com.hyjf.am.trade.controller.front.borrow;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.trade.BorrowProjectTypeResponse;
import com.hyjf.am.resquest.trade.BorrowProjectTypeRequest;
import com.hyjf.am.trade.controller.BaseController;
import com.hyjf.am.trade.dao.model.auto.BorrowProjectType;
import com.hyjf.am.trade.service.front.borrow.BorrowProjectTypeService;
import com.hyjf.am.vo.config.ParamNameVO;
import com.hyjf.am.vo.trade.borrow.BorrowProjectRepayVO;
import com.hyjf.am.vo.trade.borrow.BorrowProjectTypeVO;
import com.hyjf.am.vo.trade.borrow.BorrowStyleVO;
import com.hyjf.common.paginator.Paginator;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author by xiehuili on 2018/7/27.
 */
@RestController
@RequestMapping("/am-trade/config/projecttype")
public class BorrowProjectTypeController extends BaseController {

    @Autowired
    private BorrowProjectTypeService borrowProjectTypeService;
    /**
     * 分页查询配置中心项目类型
     * @return
     */
    @RequestMapping("/selectProjectTypeList")
    public BorrowProjectTypeResponse selectProjectTypeList(@RequestBody BorrowProjectTypeRequest adminRequest) {
        logger.info("项目类型列表..." + JSONObject.toJSON(adminRequest));
        List<ParamNameVO> paramNameVOS = adminRequest.getParamNameVO();
        BorrowProjectTypeResponse  result=new BorrowProjectTypeResponse();
        List<BorrowProjectTypeVO> list = borrowProjectTypeService.selectProjectTypeList(new BorrowProjectTypeVO());
        if(!CollectionUtils.isEmpty(list)){
            Paginator paginator = new Paginator(adminRequest.getCurrPage(), list.size());
            BorrowProjectTypeVO borrowProjectTypeVO =new BorrowProjectTypeVO();
            borrowProjectTypeVO.setLimitStart(paginator.getOffset());
            borrowProjectTypeVO.setLimitEnd(paginator.getLimit());
            list = borrowProjectTypeService.selectProjectTypeList(borrowProjectTypeVO);
            for(int i=0;i<list.size();i++){
                for(int j=0;j<paramNameVOS.size();j++){
                    if(paramNameVOS.get(j).getNameCd().equals(list.get(i).getBorrowProjectType())){
                        list.get(i).setBorrowProjectType(paramNameVOS.get(j).getName());
                    }
                    if(paramNameVOS.get(j).getNameCd().equals(list.get(i).getInvestUserType())){
                        list.get(i).setInvestUserType(paramNameVOS.get(j).getName());
                    }
                    if(paramNameVOS.get(j).getNameCd().equals(list.get(i).getStatus())){
                        list.get(i).setStatus(paramNameVOS.get(j).getName());
                    }
                }
            }
            result.setResultList(list);
            result.setRtn(Response.SUCCESS);
            return result;
        }
        return null;
    }

    /**
     * 查询项目类型详情页面
     * @return
     */
    @RequestMapping("/selectProjectTypeRecord")
    public BorrowProjectTypeResponse selectProjectTypeRecord(@RequestBody BorrowProjectTypeRequest adminRequest) {
        logger.info("项目类型详情页面..." + JSONObject.toJSON(adminRequest));
        BorrowProjectTypeResponse  result=new BorrowProjectTypeResponse();
        if (adminRequest.getId() != null) {
            BorrowProjectType record = this.borrowProjectTypeService.selectProjectTypeRecord(adminRequest.getId());
            BorrowProjectTypeVO recordVo = new BorrowProjectTypeVO();
            if(null != record){
                BeanUtils.copyProperties(record, recordVo);
                result.setResult(recordVo);
                result.setRtn(Response.SUCCESS);
                return result;
            }
            result.setRtn(Response.FAIL);
            return result;
        }
        return null;
    }

    /**
     * 根据主键判断汇直投项目类型维护中数据是否存在
     * @return
     */
    @RequestMapping("/isExistsRecord")
    public boolean isExistsRecord(@RequestBody BorrowProjectTypeVO record){
        return borrowProjectTypeService.isExistsRecord(record);
    }

    /**
     * 获取单个汇直投项目类型维护
     * @return
     */
    @RequestMapping("/getRecord")
    public BorrowProjectTypeVO getRecord(@RequestBody BorrowProjectTypeVO record){
        return borrowProjectTypeService.getRecord(record);
    }

    /**
     * 根据项目编号查询还款方式
     * @param str
     */
    @RequestMapping("/selectRepay")
    public List<BorrowProjectRepayVO> selectRepay(@RequestBody String str){
        return borrowProjectTypeService.selectRepay(str);
    }
    /**
     * 查询类型表
     */
    @RequestMapping("/selectStyles")
    public List<BorrowStyleVO> selectStyles(){
        return borrowProjectTypeService.selectStyles();
    }

    /**
     * 汇直投项目类型维护插入
     *
     * @param record
     */
    @RequestMapping("/insertRecord")
    public  BorrowProjectTypeResponse insertRecord(@RequestBody BorrowProjectTypeRequest record){
       return borrowProjectTypeService.insertRecord(record);
    }

    /**
     * 汇直投项目类型维护插入
     *
     * @param record
     */
    @RequestMapping("/updateRecord")
    public  BorrowProjectTypeResponse updateRecord(@RequestBody BorrowProjectTypeRequest record){
       return borrowProjectTypeService.updateRecord(record);
    }
    /**
     *  汇直投项目类型维护删除
     * @param adminRequest
     */
    @RequestMapping("/deleteProjectType")
    public BorrowProjectTypeResponse deleteProjectType(@RequestBody BorrowProjectTypeRequest adminRequest){
        BorrowProjectTypeResponse resp = new BorrowProjectTypeResponse();
        if(adminRequest.getBorrowCd() != null){
            this.borrowProjectTypeService.deleteProjectType(adminRequest.getBorrowCd());
            /*--------------------------add by LSY START-----------------------------------*/
            //删除asset表相应数据
            this.borrowProjectTypeService.deleteAsset(Integer.parseInt(adminRequest.getBorrowCd()));
            /*--------------------------add by LSY END-----------------------------------*/
            resp.setRtn(Response.SUCCESS);
            return resp;
        }
        resp.setRtn(Response.FAIL);
        return resp;
    }
    /**
     * 检查项目名称唯一性
     * @param request
     * @return
     */
    @RequestMapping("/borrowCdIsExists")
    public Integer borrowCdIsExists(@RequestBody BorrowProjectTypeRequest request){
       return borrowProjectTypeService.borrowCdIsExists(request.getBorrowCd());
    }
}
