package cn.promptness.settle.controller;

import cn.promptness.settle.domain.CapitalExpectRepay;
import cn.promptness.settle.service.ExpectRepayService;
import cn.promptness.settle.vo.CapitalExpectRepayVo;
import cn.promptness.settle.vo.SettleVo;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@Slf4j
public class IndexController {

    @Autowired
    private ExpectRepayService expectRepayService;

    @GetMapping(value = {"", "/", "index", "index.html"})
    public String toIndex() {
        return "index";
    }

    @PostMapping(value = "/settle")
    @ResponseBody
    public List<CapitalExpectRepayVo> settle(@RequestBody SettleVo settleVo) {

//        StopWatch stopWatch = new StopWatch("计算还款计划");
//        stopWatch.start();
        List<CapitalExpectRepay> capitalExpectRepayList = expectRepayService.listCapitalExpectRepay(settleVo.getPayOrder(), settleVo.getRule());
        //stopWatch.stop();
        //log.debug(stopWatch.toString());

        List<CapitalExpectRepayVo> result = Lists.newArrayList();
        for (CapitalExpectRepay capitalExpectRepay : capitalExpectRepayList) {
            CapitalExpectRepayVo capitalExpectRepayVo = new CapitalExpectRepayVo();
            capitalExpectRepayVo.setRepayRate(nullToString(capitalExpectRepay.getRepayRate()));
            capitalExpectRepayVo.setMulctRate(nullToString(capitalExpectRepay.getMulctRate()));
            capitalExpectRepayVo.setRepayOccupyRate(nullToString(capitalExpectRepay.getRepayOccupyRate()));
            capitalExpectRepayVo.setCompensateRate(nullToString(capitalExpectRepay.getCompensateRate()));
            capitalExpectRepayVo.setRepayTerm(nullToString(capitalExpectRepay.getRepayTerm()));
            capitalExpectRepayVo.setRepayDate(capitalExpectRepay.getRepayDate());
            capitalExpectRepayVo.setRepayTotal(nullToString(capitalExpectRepay.getRepayTotal()));
            capitalExpectRepayVo.setRepayPrincipal(nullToString(capitalExpectRepay.getRepayPrincipal()));
            capitalExpectRepayVo.setRepayFee(nullToString(capitalExpectRepay.getRepayFee()));
            capitalExpectRepayVo.setNotRepayPrincipal(nullToString(capitalExpectRepay.getNotRepayPrincipal()));
            capitalExpectRepayVo.setBetweenDays(nullToString(capitalExpectRepay.getBetweenDays()));
            result.add(capitalExpectRepayVo);
        }

        return result;
    }

    private String nullToString(Object object) {
        return object != null ? object.toString() : null;
    }
}
