package com.fdm.testpoject.RTC_WebSocket;

import org.springframework.stereotype.Component;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author fdm
 * @date 2019/10/8 15:41
 * @Description: 当一个客户端连接时就会产生一个server对象
 */
@ServerEndpoint("/online")
@Component
public class Server {

    /**
     * 当前会话对象
     */
    private Session session;

    /**
     * 用户连接集合，String代表用户组
     */
    private  static Map<String,Server> servers = new HashMap();

    /**
     * 客户端建立连接的时候触发
     * @param session
     */
    @OnOpen
    public void onOpen(Session session){
        synchronized (servers){
            this.session = session;
            servers.put("普通用户",this);
        }

    }

    /**
     * 客户端关闭连接时触发
     */
    @OnClose
    public void onClose(){
        synchronized (this){
            servers.remove(this);
        }
    }

    /**
     * 服务端向客户端响应消息时触发
     * @param message
     * @param session
     */
    @OnMessage
    public void onMessage(String message,Session session){

        for (String key:servers.keySet()){
            try {

                Server server = servers.get(key);
                server.session.getBasicRemote().sendText("cdsc");

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}

