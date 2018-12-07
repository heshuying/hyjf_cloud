package com.hyjf.cs.message.controller.client;

import com.hyjf.am.response.admin.HjhInfoAccountBalanceResponse;
import com.hyjf.am.resquest.admin.HjhAccountBalanceRequest;
import com.hyjf.am.vo.admin.HjhAccountBalanceVO;
import com.hyjf.cs.message.service.message.AccountBalanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

/**
 * @author：yinhui
 * @Date: 2018/11/6  10:46
 */
@ApiIgnore
@RestController
@RequestMapping("/cs-message/manager/statis")
public class HjhInfoAccountBalanceController {

    @Autowired
    private AccountBalanceService accountBalanceService;

    @RequestMapping("/getHjhAccountBalanceMonthCount")
    public HjhInfoAccountBalanceResponse getHjhAccountBalanceMonthCount(@RequestBody HjhAccountBalanceRequest request){
        HjhInfoAccountBalanceResponse response = new HjhInfoAccountBalanceResponse();
        //总条数
        List<HjhAccountBalanceVO> count = accountBalanceService.getHjhAccountBalanceVOByMonth(request,false);
        int  totalCount = 0;
        HjhAccountBalanceVO vo = null;
        for(HjhAccountBalanceVO balanceVO : count){
            totalCount++;
            if(vo == null){
                vo = new HjhAccountBalanceVO();
                vo.setInvestAccount(balanceVO.getInvestAccount());
                vo.setCreditAccount(balanceVO.getCreditAccount());
                vo.setReinvestAccount(balanceVO.getReinvestAccount());
                vo.setAddAccount(balanceVO.getAddAccount());
            }else{

                vo.setInvestAccount(vo.getInvestAccount().add(balanceVO.getInvestAccount()));
                vo.setCreditAccount(vo.getCreditAccount().add(balanceVO.getCreditAccount()));
                vo.setReinvestAccount(vo.getReinvestAccount().add(balanceVO.getReinvestAccount()));
                vo.setAddAccount(vo.getAddAccount().add(balanceVO.getAddAccount()));
            }

        }
        response.setSum(vo);
        response.setCount(totalCount);
        return response;
    }

    @RequestMapping("/getHjhAccountBalanceMonth")
    public HjhInfoAccountBalanceResponse getHjhAccountBalanceMonth(@RequestBody HjhAccountBalanceRequest request){
        HjhInfoAccountBalanceResponse response = new HjhInfoAccountBalanceResponse();

        //分页数
        List<HjhAccountBalanceVO> list = accountBalanceService.getHjhAccountBalanceVOByMonth(request,true);
        response.setRecordList(list);
        return response;
    }

    @RequestMapping("/getHjhAccountBalanceDayCount")
    public HjhInfoAccountBalanceResponse getHjhAccountBalanceDayCount(@RequestBody HjhAccountBalanceRequest request){
        HjhInfoAccountBalanceResponse response = new HjhInfoAccountBalanceResponse();
        //总条数
        List<HjhAccountBalanceVO> count = accountBalanceService.getHjhAccountBalanceVOByDay(request,false);
        response.setCount(count.size());

        HjhAccountBalanceVO vo = null;
        for(HjhAccountBalanceVO balanceVO : count){
            if(vo == null){
                vo = new HjhAccountBalanceVO();
                vo.setInvestAccount(balanceVO.getInvestAccount());
                vo.setCreditAccount(balanceVO.getCreditAccount());
                vo.setReinvestAccount(balanceVO.getReinvestAccount());
                vo.setAddAccount(balanceVO.getAddAccount());
            }else{

                vo.setInvestAccount(vo.getInvestAccount().add(balanceVO.getInvestAccount()));
                vo.setCreditAccount(vo.getCreditAccount().add(balanceVO.getCreditAccount()));
                vo.setReinvestAccount(vo.getReinvestAccount().add(balanceVO.getReinvestAccount()));
                vo.setAddAccount(vo.getAddAccount().add(balanceVO.getAddAccount()));
            }
        }
        response.setSum(vo);
        return response;
    }

    @RequestMapping("/getHjhAccountBalanceDay")
    public HjhInfoAccountBalanceResponse getHjhAccountBalanceDay(@RequestBody HjhAccountBalanceRequest request){
        HjhInfoAccountBalanceResponse response = new HjhInfoAccountBalanceResponse();
        //分页数
        List<HjhAccountBalanceVO> list = accountBalanceService.getHjhAccountBalanceVOByDay(request,true);
        response.setRecordList(list);

        return response;
    }
}
