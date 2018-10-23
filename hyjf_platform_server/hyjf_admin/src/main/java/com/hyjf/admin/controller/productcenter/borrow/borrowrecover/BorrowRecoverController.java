package com.hyjf.admin.controller.productcenter.borrow.borrowrecover;

import com.google.common.collect.Maps;
import com.hyjf.admin.beans.BorrowRecoverBean;
import com.hyjf.admin.beans.request.BorrowRecoverRequestBean;
import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.common.util.ShiroConstants;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.interceptor.AuthorityAnnotation;
import com.hyjf.admin.service.BorrowRecoverService;
import com.hyjf.admin.utils.ConvertUtils;
import com.hyjf.admin.utils.exportutils.DataSet2ExcelSXSSFHelper;
import com.hyjf.admin.utils.exportutils.IValueFormatter;
import com.hyjf.am.resquest.admin.BorrowRecoverRequest;
import com.hyjf.am.vo.admin.BorrowRecoverCustomizeVO;
import com.hyjf.am.vo.user.HjhInstConfigVO;
import com.hyjf.common.cache.CacheUtil;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.util.GetDate;
import com.hyjf.common.util.StringPool;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author pangchengchao
 * @version BorrowRecoverController, v0.1 2018/7/2 10:13
 */
@Api(value = "产品中心-汇直投-放款明细",tags = "产品中心-汇直投-放款明细")
@RestController
@RequestMapping("/hyjf-admin/borrow/borrowrecover")
public class BorrowRecoverController extends BaseController {
    @Autowired
    private BorrowRecoverService borrowRecoverService;
    /** 查看权限 */
    public static final String PERMISSIONS = "borrowrecover";
    /**
     * 画面初始化
     *
     * @param request
     * @return 标签配置列表
     */
    @ApiOperation(value = "放款明细", notes = "放款明细页面查询初始化")
    @PostMapping(value = "/searchAction")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_VIEW)
    public AdminResult<BorrowRecoverBean> searchAction(HttpServletRequest request, @RequestBody @Valid BorrowRecoverRequestBean form) {


        BorrowRecoverRequest copyForm=new BorrowRecoverRequest();
        BeanUtils.copyProperties(form, copyForm);
        BorrowRecoverBean bean = borrowRecoverService.searchBorrowRecover(copyForm);
        bean.setLoanStarusList(ConvertUtils.convertParamMapToDropDown(CacheUtil.getParamNameMap("LOAN_STATUS")));

        // 资金来源
        List<HjhInstConfigVO> hjhInstConfigList = this.borrowRecoverService.selectHjhInstConfigByInstCode("-1");
        bean.setHjhInstConfigList(hjhInstConfigList);
        AdminResult<BorrowRecoverBean> result=new AdminResult<BorrowRecoverBean> ();
        result.setData(bean);
        return result;
    }

    /**
     * 带条件导出
     * 1.无法指定相应的列的顺序，
     * 2.无法配置，excel文件名，excel sheet名称
     * 3.目前只能导出一个sheet
     * 4.列的宽度的自适应，中文存在一定问题
     * 5.根据导出的业务需求最好可以在导出的时候输入起止页码，因为在大数据量的情况下容易造成卡顿
     * @param request
     * @return 带条件导出
     */
    @ApiOperation(value = "放款明细导出", notes = "带条件导出EXCEL")
    @PostMapping(value = "/exportAction")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_EXPORT)
    public void exportAction(HttpServletRequest request, HttpServletResponse response, @RequestBody @Valid BorrowRecoverRequestBean form) throws Exception {
        BorrowRecoverRequest copyForm=new BorrowRecoverRequest();
        BeanUtils.copyProperties(form, copyForm);
        //sheet默认最大行数
        int defaultRowMaxCount = Integer.valueOf(systemConfig.getDefaultRowMaxCount());
        // 表格sheet名称
        String sheetName = "放款明细";
        // 文件名称
        String fileName = URLEncoder.encode(sheetName, CustomConstants.UTF8) + StringPool.UNDERLINE + GetDate.getServerDateTime(8, new Date()) + ".xls";
        // 声明一个工作薄
        SXSSFWorkbook workbook = new SXSSFWorkbook(SXSSFWorkbook.DEFAULT_WINDOW_SIZE);
        DataSet2ExcelSXSSFHelper helper = new DataSet2ExcelSXSSFHelper();
        //请求第一页5000条
        copyForm.setPageSize(defaultRowMaxCount);
        copyForm.setCurrPage(1);
        // 查询
        List<BorrowRecoverCustomizeVO> resultList = this.borrowRecoverService.exportBorrowRecoverList(copyForm);
        Integer totalCount = resultList.size();
        int sheetCount = (totalCount % defaultRowMaxCount) == 0 ? totalCount / defaultRowMaxCount : totalCount / defaultRowMaxCount + 1;
        Map<String, String> beanPropertyColumnMap = buildMap();
        Map<String, IValueFormatter> mapValueAdapter = buildValueAdapter();
        String sheetNameTmp = sheetName + "_第1页";
        if (totalCount == 0) {

            helper.export(workbook, sheetNameTmp, beanPropertyColumnMap, mapValueAdapter, new ArrayList());
        }else {
            helper.export(workbook, sheetNameTmp, beanPropertyColumnMap, mapValueAdapter, resultList);
        }
        for (int i = 1; i < sheetCount; i++) {

            copyForm.setPageSize(defaultRowMaxCount);
            copyForm.setCurrPage(i+1);
            // 查询
            List<BorrowRecoverCustomizeVO> resultList2 = this.borrowRecoverService.exportBorrowRecoverList(copyForm);
            if (resultList2 != null && resultList2.size()> 0) {
                sheetNameTmp = sheetName + "_第" + (i + 1) + "页";
                helper.export(workbook, sheetNameTmp, beanPropertyColumnMap, mapValueAdapter,  resultList2);
            } else {
                break;
            }
        }
        DataSet2ExcelSXSSFHelper.write2Response(request, response, fileName, workbook);
    }

    private Map<String, String> buildMap() {
        Map<String, String> map = Maps.newLinkedHashMap();
        map.put("borrowNid","借款编号");
        map.put("instName","资产来源");
        map.put("planNid","计划编号");
        map.put("userId","借款人ID");
        map.put("username","借款人用户名");
        map.put("entrustedFlg","是否受托支付");
        map.put("entrustedUserName","受托支付用户名");
        map.put("borrowName","借款标题");
        map.put("borrowProjectTypeName","项目类型");
        map.put("borrowPeriod","借款期限");
        map.put("borrowApr","年化收益");
        map.put("borrowStyleName","还款方式");
        map.put("orderNum","投资订单号");
        map.put("loanOrdid","放款订单号");
        map.put("instCode","合作机构编号");
        map.put("tenderUsername","投资人用户名");
        map.put("tenderUserId","投资人ID");
        map.put("createTime","投资时间");
        map.put("account","投资金额");
        map.put("accountYes","应放款金额");
        map.put("loanFee","放款服务费");
        map.put("recoverPrice","实际放款金额");
        map.put("servicePrice","实收服务费");
        map.put("isRecover","放款状态");
        map.put("timeRecover","放款时间");
        map.put("tenderUserAttribute","投资人用户属性（投资时）");
        map.put("inviteUserAttribute","推荐人用户属性（投资时）");
        map.put("tenderReferrerUsername","推荐人（投资时）");
        map.put("tenderReferrerUserId","推荐人ID（投资时）");
        map.put("departmentLevel1Name","一级分部（投资时）");
        map.put("departmentLevel2Name","二级分部（投资时）");
        map.put("teamName","团队（投资时）");
        return map;
    }
    private Map<String, IValueFormatter> buildValueAdapter() {
        Map<String, IValueFormatter> mapAdapter = Maps.newHashMap();
        IValueFormatter entrustedFlgAdapter = new IValueFormatter() {
            @Override
            public String format(Object object) {
                String isStzf_str="否";
                if (object != null && Integer.parseInt(object.toString()) - 1 == 0) {
                    isStzf_str = "是";
                }
                return isStzf_str;
            }
        };
        mapAdapter.put("entrustedFlg", entrustedFlgAdapter);
        return mapAdapter;
    }
}
