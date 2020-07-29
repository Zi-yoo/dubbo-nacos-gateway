package com.ziyoo.dnsg.provider.service;

import com.ziyoo.dnsg.api.DefaultServices;
import org.apache.dubbo.config.annotation.Service;

@Service(version = "0.0.2")
public class DefaultServicesImpl implements DefaultServices {


    public String sayHello() {
        return "";
    }

    public void asyncWorkFlow(String workTime) {
        System.out.println("Work NUM: " + workTime);
        //模拟等待
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Work NUM: " + workTime + "finish!");
    }



}
