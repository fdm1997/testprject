package com.fdm.testpoject.netty;

import io.netty.channel.*;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.stream.ChunkedWriteHandler;

/**
 * @author fdm
 * @date 2019/8/1 16:51
 * @Description:
 */
public class WsServerInitialzer extends ChannelInitializer<NioSocketChannel> {

    @Override
    protected void initChannel(NioSocketChannel nioSocketChannel) throws Exception {
        ChannelPipeline pipeline = nioSocketChannel.pipeline();

        //添加wbsocket 基于http协议的编解码器
        //pipeline.addLast(new HttpServerCodec());
        //添加对写大数据的支持
        // pipeline.addLast(new ChunkedWriteHandler());
        //对httpMessage进行聚合，聚合成FullHttpRequest和FullHttpResponse
        //  pipeline.addLast(new HttpObjectAggregator(1024*64));
        /**
         * =====================以上是支持HTTp
         */

        //webSocket服务器处理的协议，并且指定客户端连接路径（/ws）
        //WebSocketServerProtocolHandler会代替你处理一堆复杂的动作，例如连接握手,以frames进行传输，不同的数据类型对应的frame也不同
        // pipeline.addLast(new WebSocketServerProtocolHandler("/ws"));

        //添加自定义的消息handler处理器
        pipeline.addLast(new StringEncoder());
        pipeline.addLast(new SimpleChannelInboundHandler<String>() {
            @Override
            protected void channelRead0(ChannelHandlerContext channelHandlerContext, String s) throws Exception {
                System.out.println("已进入");
                System.err.println("消息"+s);
            }

            @Override
            public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
                System.out.println("客户端已连接..........");
                super.handlerAdded(ctx);
            }
        });
        //pipeline.addLast(new ChatHandler());
    }
}
