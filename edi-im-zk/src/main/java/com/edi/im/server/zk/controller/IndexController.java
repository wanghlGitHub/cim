package com.edi.im.server.zk.controller;

import com.edi.im.common.enums.StatusEnum;
import com.edi.im.common.res.BaseResponse;
import com.edi.im.server.zk.cache.ServerCache;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Function:
 *
 * @author crossoverJie
 *         Date: 22/05/2018 14:46
 * @since JDK 1.8
 */
@Controller
@RequestMapping("/")
public class IndexController {


    @Autowired
    private ServerCache serverCache ;

    /**
     * 获取所有路由节点
     * @return
     */
    @ApiOperation("获取所有路由节点")
    @RequestMapping(value = "getAllRoute",method = RequestMethod.POST)
    @ResponseBody()
    public BaseResponse<List<String>> getAllRoute(){
        BaseResponse<List<String>> res = new BaseResponse();
        List<String> all = serverCache.getAll();
        res.setDataBody(all);
        res.setCode(StatusEnum.SUCCESS.getCode()) ;
        res.setMessage(StatusEnum.SUCCESS.getMessage()) ;
        return res ;
    }

    /**
     * 获取所有路由节点
     * @return
     */
    @ApiOperation("获取所有路由节点")
    @RequestMapping(value = "getOneOfRoute",method = RequestMethod.POST)
    @ResponseBody()
    public BaseResponse<String> getOneOfRoute(){
        BaseResponse<String> res = new BaseResponse();
        String server = serverCache.selectServer();
        res.setDataBody(server);
        res.setCode(StatusEnum.SUCCESS.getCode()) ;
        res.setMessage(StatusEnum.SUCCESS.getMessage()) ;
        return res ;
    }




}
