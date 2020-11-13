package com.example.server.demo.protocol.controller;

import com.example.server.demo.protocol.entity.CurveMessage;
import com.example.server.demo.protocol.entity.CurveProtocol;
import com.example.server.demo.protocol.entity.ProtocolMessage;
import com.example.server.demo.protocol.entity.ProtocolPackInformation;
import com.example.server.demo.protocol.service.CurveMessageService;
import com.example.server.demo.protocol.service.impl.CurveMessageServiceImpl;
import com.example.server.demo.util.NettyUnit;
import com.example.server.demo.util.Utility;
import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class CurveMessageController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private final static String CHARGE_NW104 = "charge_nw104";

    //1、识别报文，确认是南网104计划值报文，报文第七个字节：89（类别）->137，计划值报文类别：137
    //protocolMessage进行赋值：ProtocolVersion，ProtocolType，
    //protocolMessage类似一个dto，接收传来的报文的数据内容，
    //protocolMessage解析报文并赋值后，赋给information类，


    @Autowired
    @Qualifier("curveMessageServiceImpl")
    private CurveMessageServiceImpl curveMessageService;




    public byte[] process(ChannelHandlerContext ctx, Object msg) throws InterruptedException {

        String SeverPort = NettyUnit.getPort(ctx);
        String ClientPort = NettyUnit.getClientPort(ctx);
        String ClientIp = NettyUnit.getClientIp(ctx);
        //String pro_type = dataSeverUtils.getPort().get(SeverPort);
        String pro_type = "charge_nw104";

        String message = (String)msg;
        String[] messageArr = message.split(" ");
        int length = messageArr.length;
        byte[] req = new byte[length];
        for (int i = 0;i<length;i++){
            req[i] = (byte)Integer.parseInt(messageArr[i], 16);
        }

        //DecoderMessage decoderMessage = (DecoderMessage)msg;
        //String proType = decoderMessage.getMessageType();

        //String contextS = Utility.ByteArrayToHexString(req, req.length);
        logger.info("客户端："+ClientIp+":"+ClientPort+"，接收报文内容："+ msg);

        ProtocolPackInformation information = new ProtocolPackInformation();
        information.init(Long.parseLong(NettyUnit.getPort(ctx)),Long.parseLong(ClientPort),
                NettyUnit.getHost(), ClientIp, req,
                new Date(), pro_type);
        information.setNewReceiveTime(new Date());


        ProtocolMessage protocolMessage = new ProtocolMessage();
        String ServerPort = String.valueOf(information.getServerPort());
        String port_protocol = "charge_nw104";
        if (information.getReceivedProtocolMessage() == null || information.getReceivedProtocolMessage().length == 0) {
            return new byte[0];
        }
        protocolMessage.setReceiveByteStream(information.getReceivedProtocolMessage());
        //判断类别，89->137计划值
        if (information.getReceivedProtocolMessage()[6] == (byte) 0x89) {
            if (port_protocol.compareTo(CHARGE_NW104) == 0) {

                protocolMessage.setProtocolVersion("PROTOCOL_NW_104");
                protocolMessage.setProtocolType("PROTOCOL_NW_104_PLAN");

                protocolMessage.setProtocolUnUsage(false);
                protocolMessage.setPortWithProtocol(port_protocol);
            }
        }


        information.setProtocolVersion(protocolMessage.getProtocolVersion());
        information.setProtocolType(protocolMessage.getProtocolType());

        //分析第13-15字节数据，确认地址值，->曲线号
        String ObjectAddressString = Utility.ByteArrayToHexString(protocolMessage.getReceiveByteStream(), 12, 14);
        Integer ObjectAddress = Integer.parseInt(ObjectAddressString, 16);
        int SequeceNumber = (ObjectAddress - 26080) / 289;
        int ObjectStartAddaress = 26080 + (SequeceNumber * 289);
        String ObjectStartAddaressHexString = Integer.toHexString(ObjectStartAddaress);


        logger.info("十六进制地址值：{}",ObjectAddressString);
        logger.info("十进制地址值：{}",ObjectStartAddaress);
        logger.info("十六地址值：{}",ObjectStartAddaressHexString);


        //分析1-288
        //解析信息对象
        //三个字节为一个地址，两个字节为数据值，存放在一个list中，(key,value)

        //信息对象 288个
        Double[] planData = new Double[288];


        List<CurveProtocol> curveProtocolList = new ArrayList<>();
        CurveProtocol cpl = new CurveProtocol();

        cpl.setProtocolContent(Utility.ByteArrayToHexString(protocolMessage.getReceiveByteStream(), protocolMessage.getReceiveByteStream().length));
        curveProtocolList.add(cpl);

        int dotNum = 288;
        String[] value = new String[dotNum];
        int ObjectEndAddress = ObjectStartAddaress+dotNum+1;

        int dot = 0;
        int data = 0;
        for (int i=0;i<curveProtocolList.size();i++){
            CurveProtocol cp = curveProtocolList.get(i);
            byte[] request = Utility.hexStrByte(cp.getProtocolContent());//把报文放到一个字节数组中
            for (int j=12;j<request.length;j=j+5){//从第13字节位置开始循环报文，五个字节的循环
                //计算地址值，作为下标使用
                //解析13-15字节的地址值
                dot = Integer.parseInt(Utility.ByteArrayToHexString(req,j,j+2),16);
                if(dot >= ObjectStartAddaress && dot < ObjectEndAddress){
                    dot = dot - ObjectStartAddaress -1;//下标从0开始
                    if(dot > -1){
                        //解析16-17两个字节的数据。解析每一个地址对象的数据值。
                        data = Integer.parseInt(Utility.ByteArrayToHexString(req,j+3,j+4),16);
                        value[dot] = data+"";//当前地址对应的数据值。
                    }
                }else {
                    break;
                }
            }
        }

        logger.info("对应节点的值：{}",value);


        //解析报文中的时间，7个八位位组的二进制时间，七个字节
        //(length-7,length-1)
        Integer timeYear = Integer.parseInt(Utility.ByteArrayToHexString(protocolMessage.getReceiveByteStream(), protocolMessage.getReceiveByteStream().length-1, protocolMessage.getReceiveByteStream().length-1), 16);
        Integer timeMonth = Integer.parseInt(Utility.ByteArrayToHexString(protocolMessage.getReceiveByteStream(), protocolMessage.getReceiveByteStream().length-2, protocolMessage.getReceiveByteStream().length-2), 16);
        Integer timeDay = Integer.parseInt(Utility.ByteArrayToHexString(protocolMessage.getReceiveByteStream(), protocolMessage.getReceiveByteStream().length-3, protocolMessage.getReceiveByteStream().length-3), 16);
        logger.info("曲线时间：{}",timeYear);
        logger.info("曲线时间：{}",timeMonth);
        logger.info("曲线时间：{}",timeDay);
        logger.info("曲线时间：{}",timeYear+timeMonth+timeDay);


        //生成回复数据
        if(req[8] == (byte)0x06) {
            req[8] = (byte)0x07;
        }
        logger.info("回复数据：{}",req);

        protocolMessage.setReplyByteStream(req);

        logger.info("回复数据是："+Utility.ByteArrayToHexString(protocolMessage.getReplyByteStream(),protocolMessage.getReplyByteStream().length));
        /*ByteBuf encoded = ctx.alloc().buffer(4 * protocolMessage.getReplyByteStream().length);
        encoded.writeBytes(protocolMessage.getReplyByteStream());
        ctx.writeAndFlush(encoded);
        Thread.sleep(50);
        encoded.clear();*/

        /*ByteBuf encoded = ctx.alloc().buffer(4 * req.length);
        encoded.writeBytes(req);*/

        //保存报文
        CurveMessage curveMessage = new CurveMessage();
        curveMessage.setReceiveByteStream(cpl.getProtocolContent());
        curveMessage.setReplyByteStream(Utility.ByteArrayToHexString(protocolMessage.getReplyByteStream(),protocolMessage.getReplyByteStream().length));
        curveMessage.setProtocolType(protocolMessage.getProtocolType());
        curveMessage.setCurveDate(String.valueOf(timeYear)+String.valueOf(timeMonth)+String.valueOf(timeDay));
        curveMessage.setProtocolVersion(protocolMessage.getProtocolVersion());
        curveMessage.setCreateTime(new Date());
        curveMessage.setUpdateTime(new Date());
        curveMessageService.insert(curveMessage);



        return protocolMessage.getReplyByteStream();
    }


}
