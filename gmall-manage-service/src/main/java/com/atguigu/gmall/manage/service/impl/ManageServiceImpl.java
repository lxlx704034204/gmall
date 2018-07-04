package com.atguigu.gmall.manage.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.atguigu.gmall.bean.*;
import com.atguigu.gmall.manage.mapper.*;
import com.atguigu.gmall.service.ManageService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Service
public class ManageServiceImpl implements ManageService {

    @Autowired
    private BaseCatalog1Mapper baseCatalog1Mapper;
    @Autowired
    private BaseCatalog2Mapper baseCatalog2Mapper;
    @Autowired
    private BaseCatalog3Mapper baseCatalog3Mapper;
    @Autowired
    private BaseAttrInfoMapper baseAttrInfoMapper;
    @Autowired
    private BaseAttrValueMapper baseAttrValueMapper;

    @Override
    public List<BaseCatalog1> getCatalog1() {
        return baseCatalog1Mapper.selectAll();
    }

    @Override
    public List<BaseCatalog2> getCatalog2(String catalog1) {
        BaseCatalog2 catalog2 = new BaseCatalog2();
        catalog2.setCatalog1Id(catalog1);
        return baseCatalog2Mapper.select(catalog2);
    }

    @Override
    public List<BaseCatalog3> getCatalog3(String catalog2) {
        BaseCatalog3 catalog3 = new BaseCatalog3();
        catalog3.setCatalog2Id(catalog2);
        return baseCatalog3Mapper.select(catalog3);
    }

    @Override
    public List<BaseAttrInfo> getAttrInfo(String catalog3) {
        BaseAttrInfo attrInfo = new BaseAttrInfo();
        attrInfo.setCatalog3Id(catalog3);
        return baseAttrInfoMapper.select(attrInfo);
    }

    @Override
    public void saveAttrInfo(BaseAttrInfo baseAttrInfo) {
        if (baseAttrInfo.getId() != null && baseAttrInfo.getId().length() > 0){
            baseAttrInfoMapper.updateByPrimaryKey(baseAttrInfo);
        }else {
            if (baseAttrInfo.getId().length() == 0){
                baseAttrInfo.setId(null);
            }
            baseAttrInfoMapper.insertSelective(baseAttrInfo);
        }

        // 操作属性值，先将属性值情况
        BaseAttrValue baseAttrValue = new BaseAttrValue();
        baseAttrValue.setAttrId(baseAttrInfo.getId());
        baseAttrValueMapper.delete(baseAttrValue);

        if (baseAttrInfo.getAttrValueList() != null && baseAttrInfo.getAttrValueList().size() > 0){
            for (BaseAttrValue attrValue : baseAttrInfo.getAttrValueList()) {
                if (attrValue.getId().length() == 0){
                    attrValue.setId(null);
                }
                attrValue.setAttrId(baseAttrInfo.getId());
                baseAttrValueMapper.insertSelective(attrValue);

            }
        }
    }
}
