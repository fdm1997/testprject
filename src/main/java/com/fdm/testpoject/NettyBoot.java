package com.fdm.testpoject;

import com.fdm.testpoject.netty.WsServer;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

/**
 * @author fdm
 * @date 2019/8/1 16:39
 * @Description: 监听springboot,当springboot启动完成，启动netty
 */
@Component
public class NettyBoot implements ApplicationListener<ContextRefreshedEvent> {
    @Override
    public void onApplicationEvent(ContextRefreshedEvent context) {

       /* if (context.getApplicationContext().getParent() == null){
            try {
                WsServer.getInstance().start();//启动netty
            }catch (Exception e){
                System.err.println("netty启动失败...........");
                e.printStackTrace();
            }

        }*/
    }
}
