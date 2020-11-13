package com.example.client.demo.net;


import com.example.client.demo.protocol.MyDecoder;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.timeout.IdleStateHandler;

import java.nio.charset.Charset;
import java.util.concurrent.TimeUnit;

public class Client {

    private String ip = "172.20.1.208";
    private int port = 6668;


    /**唯一标记 */
    private boolean initFalg=true;



    public  void connectServer() throws InterruptedException {
        //客户端需要一个事件循环组
        EventLoopGroup eventExecutors = new NioEventLoopGroup();

        try {
            //创建客户端启动对象
            //注意客户端使用的不是ServerBoostrap，而是Bootstrap
            Bootstrap bootstrap = new Bootstrap();

            //设置相关参数
            bootstrap.group(eventExecutors)//设置线程组
                    .channel(NioSocketChannel.class)//设置客户端通道的实现类（反射）
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(new IdleStateHandler(0,4,0, TimeUnit.SECONDS));//心跳机制,每隔30秒进行读操作
                            ch.pipeline().addLast(new StringDecoder());
                            ch.pipeline().addLast(new StringEncoder());
                            ch.pipeline().addLast(new ClientHandler());//加入自己的处理器
                        }
                    });

            System.out.println("客户端ok...");

            //启动客户端去连接服务器
            //关于ChannelFuture要分析，涉及到netty的异步模型
            ChannelFuture channelFuture = bootstrap.connect("172.20.1.208",6668).sync();
            //给关闭通道进行监听
            channelFuture.channel().closeFuture().sync();

        }finally {
            eventExecutors.shutdownGracefully();
        }

    }

    /**
     * 重连
     * */
    public void doConnect(Bootstrap bootstrap,EventLoopGroup eventLoopGroup){
        ChannelFuture f = null;
        try {
            if(bootstrap != null){
                bootstrap.group(eventLoopGroup)
                        .channel(NioSocketChannel.class)
                        .option(ChannelOption.SO_KEEPALIVE,true)
                        .handler(new ChannelInitializer<SocketChannel>() {
                            @Override
                            protected void initChannel(SocketChannel ch) throws Exception {

                                ch.pipeline().addLast(new IdleStateHandler(0,4,0, TimeUnit.SECONDS));//心跳机制,每隔30秒进行读操作
                                ch.pipeline().addLast(new StringDecoder(Charset.forName("UTF-8")));
                                ch.pipeline().addLast(new StringEncoder(Charset.forName("GBK")));
                                //ch.pipeline().addLast("encoder",new MyDecoder());
                                ch.pipeline().addLast(new ClientHandler());//加入自己的处理器
                            }
                        })
                        .remoteAddress(ip,port);
                f = bootstrap.connect().addListener((ChannelFuture futureListener)->{
                    final EventLoop eventLoop = futureListener.channel().eventLoop();
                    if(!futureListener.isSuccess()){
                        System.out.println("与服务器断开连接！在10s之后准备尝试重连！");
                        eventLoop.schedule(()-> doConnect(new Bootstrap(),eventLoop),10,TimeUnit.SECONDS);
                    }
                });
                if(initFalg){
                    System.out.println("Netty客户端启动成功！");
                    initFalg = false;
                }
                //阻塞
                //f.channel().closeFuture().sync();
            }
        } catch (Exception e) {
            System.out.println("客户端连接失败"+e.getMessage());
        }
    }

}

