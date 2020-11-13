package com.example.server.demo.net;

import com.example.server.demo.protocol.entity.ProtocolMessage;
import com.example.server.demo.protocol.controller.CurveMessageController;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.CharsetUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * 1.我们自定义一个Handler需要继续netty规定好的某个HandlerAdapter（规范
 * 2.这时我们自定义一个Handler,才能成为一个handler
 * */
@Component
public class ServerHandler extends ChannelInboundHandlerAdapter {

    private static ServerHandler serverHandler;

    private Logger logger = LoggerFactory.getLogger(ServerHandler.class);

    /*@Autowired
    private ProtocolProcess protocolProcess;*/

    /** 空闲次数 */
    private int idle_count =1;
    /** 发送次数 */
    private int count = 1;

    //心跳内容
    private static final ByteBuf HEARTBEAT_SEQUENCE = Unpooled
            .unreleasableBuffer(Unpooled.copiedBuffer("Heartbeat",
                    CharsetUtil.UTF_8));

    @PostConstruct
    //因为是new出来的handler,没有托给spring容器,所以一定要先初始化,否则autowired失效
    public void init() {
        serverHandler = this;
    }

    @Autowired
    private CurveMessageController curveMessageController;

    //读取数据实际（这里我们可以读取客户端发送的消息）
    /**
     * 1.ChannelHandlerContext ctx:上下文对象，含有管道pipeline，通道channel ,地址
     * 2.Object msg:就是客户端发送的数据，默认Object
     *
     * */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {


        //protocolProcess.process();


        logger.info("接收报文内容：{}",msg);
        logger.info("接收的地址："+ctx.channel().remoteAddress());
        logger.info("执行方法");


        CurveMessageController curveMessageController = new CurveMessageController();
        ProtocolMessage protocolMessage = new ProtocolMessage();

        protocolMessage.setReplyByteStream(serverHandler.curveMessageController.process(ctx,msg));
        //logger.info("回复数据：{}",protocolProcess.process(ctx,msg));

        ByteBuf encoded = ctx.alloc().buffer(4 * protocolMessage.getReplyByteStream().length);
        encoded.writeBytes(protocolMessage.getReplyByteStream());
        ctx.writeAndFlush(encoded);
        Thread.sleep(50);
        encoded.clear();

        /*String message = (String)msg;
        String[] messageArr = message.split(" ");
        logger.info("第九个元素：{}",messageArr[8]);
        int length = messageArr.length;
        byte[] req = new byte[length];

        for (int i = 0;i<length;i++){
            req[i] = (byte)Integer.parseInt(messageArr[i], 16);
        }
        if(req[8] == (byte)0x06){
            req[8] = (byte)0x07;
        }
        logger.info("回复数据：{}",req);

        ByteBuf encoded = ctx.alloc().buffer(4 * req.length);
        encoded.writeBytes(req);
*/
        /*ByteBuf result;
        result = Unpooled.buffer(req.length);
        result.writeBytes(req);*/
        //ctx.channel().writeAndFlush(encoded);

        logger.info("执行结束");

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
        /*ctx.close();*/
        logger.error(cause.getStackTrace().toString());
        cause.printStackTrace();

        super.exceptionCaught(ctx, cause);
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
