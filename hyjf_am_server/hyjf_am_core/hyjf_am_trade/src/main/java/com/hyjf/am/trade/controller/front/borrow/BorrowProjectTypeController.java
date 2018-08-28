package com.hyjf.am.trade.controller.front.borrow;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.response.BooleanResponse;
import com.hyjf.am.response.IntegerResponse;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.trade.BorrowProjectRepayReponse;
import com.hyjf.am.response.trade.BorrowProjectTypeResponse;
import com.hyjf.am.response.trade.BorrowStyleResponse;
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
            Paginator paginator = new Paginator(adminRequest.getCurrPage(),list.size(),adminRequest.getPageSize() == 0?10:adminRequest.getPageSize());
            BorrowProjectTypeVO borrowProjectTypeVO =new BorrowProjectTypeVO();
            borrowProjectTypeVO.setLimitStart(paginator.getOffset());
            borrowProjectTypeVO.setLimitEnd(paginator.getLimit());
            list = borrowProjectTypeService.selectProjectTypeList(borrowProjectTypeVO);
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
    public BooleanResponse isExistsRecord(@RequestBody BorrowProjectTypeVO record){
        BooleanResponse response = new BooleanResponse();
        Boolean b= borrowProjectTypeService.isExistsRecord(record);
        response.setResultBoolean(b);
        return response;
    }

    /**
     * 获取单个汇直投项目类型维护
     * @return
     */
    @RequestMapping("/getRecord")
    public BorrowProjectTypeResponse getRecord(@RequestBody BorrowProjectTypeVO record){
        BorrowProjectTypeResponse response = new BorrowProjectTypeResponse();
        BorrowProjectTypeVO borrowProjectTypeVO= borrowProjectTypeService.getRecord(record);
        if(null != borrowProjectTypeVO){
            response.setResult(borrowProjectTypeVO);
            return response;
        }
        return null;
    }

    /**
     * 根据项目编号查询还款方式
     * @param str
     */
    @RequestMapping("/selectRepay")
    public BorrowProjectRepayReponse selectRepay(@RequestBody String str){
        BorrowProjectRepayReponse repayReponse = new BorrowProjectRepayReponse();
        List<BorrowProjectRepayVO>  repayVOS =borrowProjectTypeService.selectRepay(str);
        if(!CollectionUtils.isEmpty(repayVOS)){
            repayReponse.setResultList(repayVOS);
            return repayReponse;
        }
        return null;
    }
    /**
     * 查询类型表
     */
    @RequestMapping("/selectStyles")
    public BorrowStyleResponse selectStyles(){
        BorrowStyleResponse response= new BorrowStyleResponse();
        List<BorrowStyleVO> borrowList =borrowProjectTypeService.selectStyles();
        if(!CollectionUtils.isEmpty(borrowList)){
            response.setResultList(borrowList);
            return response;
        }
        return null;
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
    public IntegerResponse borrowCdIsExists(@RequestBody BorrowProjectTypeRequest request){
        int count = borrowProjectTypeService.borrowCdIsExists(request.getBorrowCd());
        IntegerResponse response=new IntegerResponse();
        response.setResultInt(count);
       return response;
    }
}
