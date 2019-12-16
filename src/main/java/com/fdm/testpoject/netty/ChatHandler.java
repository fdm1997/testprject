package com.fdm.testpoject.netty;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.concurrent.GlobalEventExecutor;

/**
 * @author fdm
 * @date 2019/8/1 17:29
 * @Description:自定义消息处理器
 */
public class ChatHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

    //用于记录和管理所有客户端的Channel,所有客户端都会在这里面
    private static ChannelGroup channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, TextWebSocketFrame textWebSocketFrame) throws Exception {
        System.err.println("已进入");
        //获取客户端传输进来的消息
        String content = textWebSocketFrame.text();
        System.out.println("消息："+content);

        //获取所有客户端进行发送
        for (Channel channel:channels) {
            channel.writeAndFlush(new TextWebSocketFrame(content));
        }

    }

    /**
     * 客户端创建完触发该方法
     * @param ctx
     * @throws Exception
     */
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {

        System.out.println("客户端已连接..........");
        //当客户端连接服务端时，获取客户端的channel并放进channels里进行统一的自动管理
       channels.add(ctx.channel());
    }

    /**
     * 客户端离开触发该方法,当handlerRemoved被触发时，ChannelGroup会自动移除对应客户端的channel
     * @param ctx
     * @throws Exception
     */
    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        System.out.println("客户端已断开连接........");
    }
}
