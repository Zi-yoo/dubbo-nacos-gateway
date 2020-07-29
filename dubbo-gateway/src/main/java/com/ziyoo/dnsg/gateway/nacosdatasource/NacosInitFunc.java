package com.ziyoo.dnsg.gateway.nacosdatasource;

import com.alibaba.csp.sentinel.datasource.ReadableDataSource;
import com.alibaba.csp.sentinel.datasource.nacos.NacosDataSource;
import com.alibaba.csp.sentinel.init.InitFunc;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRule;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRuleManager;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import com.alibaba.csp.sentinel.slots.system.SystemRule;
import com.alibaba.csp.sentinel.slots.system.SystemRuleManager;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class NacosInitFunc implements InitFunc {

    private static String remoteAddresss;
    private static String dataIdd;
    private static String groupIdd;

    @Value("${sentinel.config.nacos.serveraddr}")
    public void setRemoteAddresss(String address){
        remoteAddresss = address;
    }
    @Value("${sentinel.config.nacos.dataid}")
    public void setDataIdd(String data){
        dataIdd = data;
    }
    @Value("${sentinel.config.nacos.groupid}")
    public void setGroupIdd(String groupIdd1){
        groupIdd = groupIdd1;
    }


    private final String remoteAddress;
    private final String dataId;
    private final String groupId;


    public NacosInitFunc() {
        this.remoteAddress = remoteAddresss;
        this.dataId = dataIdd;
        this.groupId = groupIdd;
    }


    @Override
    public void init(){


        ReadableDataSource<String, List<FlowRule>> flowRuleDataSource = new NacosDataSource<>(remoteAddress, groupId, dataId,
                source -> JSON.parseObject(source, new TypeReference<List<FlowRule>>() {}));
        FlowRuleManager.register2Property(flowRuleDataSource.getProperty());

        ReadableDataSource<String,List<DegradeRule>> degradeRuleDataSource = new NacosDataSource<>(remoteAddress,groupId,
                dataId, source -> JSON.parseObject(source, new TypeReference<List<DegradeRule>>() {}));
        DegradeRuleManager.register2Property(degradeRuleDataSource.getProperty());

        ReadableDataSource<String,List<SystemRule>> systemRuleDataSource = new NacosDataSource<>(remoteAddress,groupId,
                dataId,source -> JSON.parseObject(source, new TypeReference<List<SystemRule>>() {}));
        SystemRuleManager.register2Property(systemRuleDataSource.getProperty());



        /*ReadableDataSource<String,List<AuthorityRule>> authorityRuleDataSource = new NacosDataSource<>(remoteAddress,
                groupId,dataId,source -> JSON.parseObject(source, new TypeReference<>() {}));
        AuthorityRuleManager.register2Property(authorityRuleDataSource.getProperty());*/



    }
}
