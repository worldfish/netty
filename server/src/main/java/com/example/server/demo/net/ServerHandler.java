package com.example.server.demo.net;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.CharsetUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 1.我们自定义一个Handler需要继续netty规定好的某个HandlerAdapter（规范
 * 2.这时我们自定义一个Handler,才能成为一个handler
 * */
public class ServerHandler extends ChannelInboundHandlerAdapter {

    private Logger logger = LoggerFactory.getLogger(ServerHandler.class);

    /** 空闲次数 */
    private int idle_count =1;
    /** 发送次数 */
    private int count = 1;

    //心跳内容
    private static final ByteBuf HEARTBEAT_SEQUENCE = Unpooled
            .unreleasableBuffer(Unpooled.copiedBuffer("Heartbeat",
                    CharsetUtil.UTF_8));


    //读取数据实际（这里我们可以读取客户端发送的消息）
    /**
     * 1.ChannelHandlerContext ctx:上下文对象，含有管道pipeline，通道channel ,地址
     * 2.Object msg:就是客户端发送的数据，默认Object
     *
     * */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        String message = (String)msg;
        if("client_request".equals(message)){
            logger.info("第{}次接收到心跳,服务端接收的消息{}",count,msg);
            count++;
        }else {

            logger.info("收到客户端数据：{}",message.getBytes("UTF-8"));
            try {
                /*byte[] req = new byte[]{(byte) 0x68,(byte) 0x04,(byte) 0x43,(byte) 0x00,(byte) 0x00,(byte) 0x00};

                ByteBuf result;
                result = Unpooled.buffer(req.length);
                result.writeBytes(req);*/


                //ctx.channel().writeAndFlush(str);

                System.out.println("ok");
                //System.out.println("关闭通道！");
                //ctx.channel().close();
            } catch (Exception e) {
                System.out.println("报文解析错误");
            }
        }
    }


    //主动向客户端发送数据
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        try {
            byte[] req = new byte[]{(byte) 0x68,(byte) 0x04,(byte) 0x63,(byte) 0x00,(byte) 0x00,(byte) 0x00};
            ByteBuf result;
            logger.info("主动向客户端发送数据{}",req);
            result = Unpooled.buffer(req.length);
            result.writeBytes(req);

            ctx.channel().writeAndFlush(result);
        } catch (Exception e) {
            System.out.println("报文解析错误");
        }
    }

    //处理异常，一般是需要关闭通道
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if(evt instanceof IdleStateEvent){//判断是否是IdleStateEvent超时事件
            IdleStateEvent e = (IdleStateEvent) evt;
            if(IdleState.READER_IDLE.equals(e.state())){//如果读通道处于空闲状态，说明没有接收到心跳命令
                //System.out.println("已经5秒没有收到客户端的信息了");
                logger.info("已经5秒没有收到客户端的信息了");
                if(idle_count > 2){
                    //System.out.println("关闭这个不活跃的channel");
                    logger.info("关闭这个不活跃的channel");
                    ctx.channel().close();
                }
                idle_count++;
            }
        }else {
            super.userEventTriggered(ctx,evt);
        }
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("client exit...");
    }
}
