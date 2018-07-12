package com.atguigu.gmall.list.controller;


import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.atguigu.gmall.bean.SkuLsParams;
import com.atguigu.gmall.bean.SkuLsResult;
import com.atguigu.gmall.service.ListService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ListController {

    @Reference
    private ListService listService;


    @RequestMapping(value = "list",method = RequestMethod.GET)
    @ResponseBody
    public String list(SkuLsParams skuLsParams){
        SkuLsResult search = listService.search(skuLsParams);
        String jsonStringList = JSON.toJSONString(search);
        return jsonStringList;
    }

}
