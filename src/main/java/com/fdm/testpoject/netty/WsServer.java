package com.fdm.testpoject.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.springframework.stereotype.Component;

/**
 * @author fdm
 * @date 2019/8/1 16:23
 * @Description:单例创建WsServer
 */
@Component
public class WsServer {

    /**
     *  chanle管理主线程池
     */
    private EventLoopGroup mainGroop;

    /**
     * 工作线程池
     */
    private EventLoopGroup subGroup;
    private ServerBootstrap serverBootstrap;  //服务启动器
    private ChannelFuture channelFuture;

    private WsServer(){
        //创建线程池并放进启动器中
        mainGroop = new NioEventLoopGroup();
        subGroup = new NioEventLoopGroup();
        serverBootstrap = new ServerBootstrap();
        serverBootstrap.group(mainGroop,subGroup)
                .channel(NioServerSocketChannel.class)
                .childHandler(new WsServerInitialzer());
    }

    private static class  singletionWsServer{
        static final  WsServer instance = new WsServer();
    }

    public static WsServer getInstance(){
        return singletionWsServer.instance;
    }

    /**
     * 启动服务
     */
    public void start(){
        this.channelFuture = serverBootstrap.bind(9090);
        System.err.println("server 启动成功。。。。");

    }

}
