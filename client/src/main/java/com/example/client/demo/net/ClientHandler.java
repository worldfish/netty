package com.example.client.demo.net;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.EventLoop;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.CharsetUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

public class ClientHandler extends ChannelInboundHandlerAdapter {

    private Logger logger = LoggerFactory.getLogger(ClientHandler.class);

    // 通过nio方式来接收连接和处理连接
    private EventLoopGroup group = new NioEventLoopGroup();

    /** 客户端请求的心跳命令  */
    private static final ByteBuf HEARTBEAT_SEQUENCE = Unpooled.unreleasableBuffer(Unpooled.copiedBuffer("client_request",
            CharsetUtil.UTF_8));

    /** 空闲次数 */
    private int idle_count = 1;

    /** 发送次数 */
    private int count = 1;

    /**循环次数 */
    private int fcount = 1;

    /**唯一标记 */
    private boolean initFalg=true;

    //private byte[] req;

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        try {
            byte[] req = new byte[]{(byte) 0x68,(byte) 0x04,(byte) 0x83,(byte) 0x00,(byte) 0x00,(byte) 0x00};
            ByteBuf result;
            logger.info("向服务器发送数据{}",req);
            result = Unpooled.buffer(req.length);
            result.writeBytes(req);

            ctx.channel().writeAndFlush(result);
        } catch (Exception e) {
            System.out.println("报文解析错误");
        }
    }

    //当通道有读取事件时，会触发
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        String message = (String)msg;
        if("client_request".equals(message)){
            logger.info("第{}次，收到服务器消息：{}",count,message.getBytes("UTF-8"));
            count++;
        }
        logger.info("收到服务器主动发过来的消息：{}",message.getBytes("UTF-8"));
        //System.out.println("第"+count+"次"+",客户端接受的消息:"+msg);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

    //channelInactive()方法。当服务器关闭客户端channel时，会触发这个回调方法


    /**
     * 心跳请求处理
     * 每4秒发送一次心跳请求
     * */
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        System.out.println("循环请求的时间"+new Date()+".次数"+fcount);
        if(evt instanceof IdleStateEvent){
            IdleStateEvent event = (IdleStateEvent) evt;
            if(IdleState.WRITER_IDLE.equals(event.state())){//如果写通道处于空闲状态,就发送心跳命令
                if(idle_count <= 3){//设置发送次数
                    idle_count++;
                    ctx.channel().writeAndFlush(HEARTBEAT_SEQUENCE.duplicate());
                }else {
                    System.out.println("不再发送心跳请求了");
                }
                fcount++;
            }
        }
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("client exit,will reconnect.."+new Date());
        //client.doConnect(String.valueOf(ctx.channel().remoteAddress()),6668);
        //final EventLoop group = ctx.channel().eventLoop();
        Client client = new Client();
        client.doConnect(new Bootstrap(),group);
        super.channelInactive(ctx);
    }
}
