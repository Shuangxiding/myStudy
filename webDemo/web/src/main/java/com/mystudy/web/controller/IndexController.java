package com.mystudy.web.controller;

import com.alibaba.fastjson.JSON;
import com.mystudy.web.common.BaseController;
import com.mystudy.web.common.log.LogUtil;
import com.mystudy.web.controller.util.MultithreadUtil;
import com.mystudy.web.service.GoodsService;
import com.mystudy.web.service.TestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * Created by 程祥 on 15/11/26.
 * Function：
 */

@Controller
public class IndexController extends BaseController {
    private final Logger logger = LogUtil.getCommonLogger();
    private  final Logger testLogger = LogUtil.getTestLogger();

    @Autowired
    private TestService testService;

    @Autowired
    private GoodsService goodsService;

    @RequestMapping(value = "/")
    public ModelAndView index(){
        logger.debug("index ********");
        return new ModelAndView("index.html");
    }

    @RequestMapping(value = "/index")
    public ModelAndView index2(){
        logger.debug("index2 ********");
        return new ModelAndView("index.html");
    }

    @RequestMapping(value = "/test",produces = "text/html; charset=utf-8")
    @ResponseBody
    public Object index3(){
        logger.debug("你好 ********");
        testLogger.debug("你好~~~~*****");
        String result = null;
        try {
            result = testService.testMethod("你好！");
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
        }
        return result;
    }

    @RequestMapping(value = "/database",produces = "application/json; charset=UTF-8")
    @ResponseBody
    public Object databaseTest() throws Exception{
        return JSON.toJSONString(goodsService.selectAll());
    }


}
