package com.example.server.demo.protocol.entity;

import com.example.server.demo.util.Utility;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ProtocolPackInformation {
    private long ServerPort;
    private long ClientPort;
    private String DataTypeName;
    private String ReceivedDetailMessage;
    private String ReplyDetailMessage;
    private Integer ReplyDetailExtendCount = 0;
    private String[] ReplyDetailMessageExtend;
    private String ServcerInternetProtocolAddress;
    private String ClientHostAddress;
    private boolean isErrorProtocol;
    private boolean isReply = false;
    private String ErrorMessage;
    private byte[] OriginalProtocolMessage;
    private byte[] ReplyProtocolMessage;
    private byte[][] ReplyProtocolMessageExtend;
    private Date ReceiveTime;
    private String BindProtocolType;
    private boolean ProtocolUnUsage;

    private String  protocolVersionTypes;
    private String protocolVersion;
    private String protocolType;

    private Date newReceiveTime;

    public boolean isReply() {
        return isReply;
    }

    public void setReply(boolean isReply) {
        this.isReply = isReply;
    }

    public Date getNewReceiveTime() {
        return newReceiveTime;
    }

    public void setNewReceiveTime(Date newReceiveTime) {
        this.newReceiveTime = newReceiveTime;
    }

    public boolean isProtocolUnUsage() {
        return ProtocolUnUsage;
    }

    public void setProtocolUnUsage(boolean protocolUnUsage) {
        ProtocolUnUsage = protocolUnUsage;
    }

    public ProtocolPackInformation() {
        init(0, 0, "", "", null, new Date(), null);
    }

    public ProtocolPackInformation(long ServerPort, long ClientPort, String ServcerInternetProtocolAddress,
                                  String ClientHostAddress, byte[] ProtocolMessage, Date DateTime, String BindProtocolType) {
        init(ServerPort, ClientPort, ServcerInternetProtocolAddress,
                ClientHostAddress, ProtocolMessage, DateTime, BindProtocolType);
    }

    public String getDataTypeName() {
        return DataTypeName;
    }

    public void setDataTypeName(String dataTypeName) {
        DataTypeName = dataTypeName;
    }

    public void init(long ServerPort, long ClientPort, String ServcerInternetProtocolAddress,
                     String ClientHostAddress, byte[] ProtocolMessage, Date DateTime, String BindProtocolType) {
        this.ServerPort = ServerPort;
        this.ClientPort = ClientPort;
        this.ServcerInternetProtocolAddress = ServcerInternetProtocolAddress;
        this.ClientHostAddress = ClientHostAddress;
        if (ProtocolMessage != null && ProtocolMessage.length != 0) {
            this.OriginalProtocolMessage = new byte[ProtocolMessage.length];
            this.ReplyProtocolMessage = new byte[ProtocolMessage.length];
            for (int i = 0; i < ProtocolMessage.length; i++) {
                this.OriginalProtocolMessage[i] = ProtocolMessage[i];
                this.ReplyProtocolMessage[i] = ProtocolMessage[i];
            }
        }
        this.BindProtocolType = BindProtocolType;
        this.ReceiveTime = DateTime;
    }

    public long getServerPort() {
        return ServerPort;
    }

    public void setServerHostPort(long serverPort) {
        ServerPort = serverPort;
    }

    public long getClientPort() {
        return ClientPort;
    }

    public void setClientHostPort(long clientPort) {
        ClientPort = clientPort;
    }

    public String getReceivedDetailMessage() {
        return ReceivedDetailMessage;
    }

    public void setReceivedDetailMessage(String receivedDetailMessage) {
        ReceivedDetailMessage = receivedDetailMessage;
    }

    public String getReplyDetailMessage() {
        return ReplyDetailMessage;
    }

    public void setReplyDetailMessage(String replyDetailMessage) {
        ReplyDetailMessage = replyDetailMessage;
    }

    public Integer getReplyDetailExtendCount() {
        return ReplyDetailExtendCount;
    }

    public void setReplyDetailExtendCount(Integer replyDetailExtendCount) {
        ReplyDetailExtendCount = replyDetailExtendCount;
    }

    public String[] getReplyDetailMessageExtend() {
        return ReplyDetailMessageExtend;
    }

    public void setReplyDetailMessageExtend(String[] replyDetailMessageExtend) {
        ReplyDetailMessageExtend = replyDetailMessageExtend;
    }

    public byte[][] getReplyProtocolMessageExtend() {
        return ReplyProtocolMessageExtend;
    }

    public void setReplyProtocolMessageExtend(byte[][] replyProtocolMessageExtend) {
        ReplyProtocolMessageExtend = replyProtocolMessageExtend;
    }

    public String getServcerInternetProtocolAddress() {
        return ServcerInternetProtocolAddress;
    }

    public void setServcerInternetProtocolAddress(String servcerInternetProtocolAddress) {
        ServcerInternetProtocolAddress = servcerInternetProtocolAddress;
    }

    public String getClientHostAddress() {
        return ClientHostAddress;
    }

    public void setClientHostAddress(String clientHostAddress) {
        ClientHostAddress = clientHostAddress;
    }

    public String getBindProtocolType() {
        return BindProtocolType;
    }

    public void setBindProtocolType(String bindProtocolType) {
        BindProtocolType = bindProtocolType;
    }

    public boolean isErrorProtocol() {
        this.isErrorProtocol = checked();
        return !this.isErrorProtocol;
    }

    private boolean checked() {

            if (this.protocolType == "PROTOCOL_NW_104_PLAN") {
                this.ReceivedDetailMessage = "收到计划值报文";
                this.ReplyDetailMessage = "回复计划值报文";
                this.protocolVersionTypes = "PROT0COL_NW_CHARGE";
                return true;
            }

            else if (this.protocolType == "PROTOCOL_NW_104_VOLTAGE") {
                this.ReceivedDetailMessage = "收到电压计划值报文";
                this.ReplyDetailMessage = "回复电压计划值报文";
                this.protocolVersionTypes = "PROTOCOL_NW_VOLTAGE";
                return true;
            }



        this.ErrorMessage = "未知报文类型";
        protocolVersionTypes = "PROTOCOL_NONE";
        return false;
    }

    public void setErrorProtocol(boolean isErrorProtocol) {
        this.isErrorProtocol = isErrorProtocol;
    }

    public String getErrorMessage() {
        return ErrorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        ErrorMessage = errorMessage;
    }

    public byte[] getReceivedProtocolMessage() {
        return this.OriginalProtocolMessage;
    }

    public void setReceivedProtocolMessage(byte[] protocolMessage) {
        this.OriginalProtocolMessage = protocolMessage;
    }

    public String getOriginalProtocolMessage() {
        if(OriginalProtocolMessage != null) {
            return Utility.ByteArrayToHexString(OriginalProtocolMessage, OriginalProtocolMessage.length);
        }
        return "";
    }

    public void setOriginalProtocolMessage(byte[] originalProtocolMessage) {
        OriginalProtocolMessage = originalProtocolMessage;
    }

    public byte[] getReplyProtocolMessage() {
        return this.ReplyProtocolMessage;
    }

    public String getReplyProtocolMessageString() {
        // if (this.protocolVersion == ProtocolVersion.PROTOCOL_GD_216) {
        // this.ReplyProtocolMessage[4] = (byte) 0xFD;
        // }
        // if (this.protocolVersion == ProtocolVersion.PROTOCOL_GD_104) {
        // if (this.protocolType == ProtocolType.PROTOCOL_GD_104_VOLTAGE) {
        // this.ReplyProtocolMessage[8] = (byte) 0x07;
        // }
        // if (this.protocolType ==
        // ProtocolType.PROTOCOL_GD_104_CONTROL_INFORMATION) {
        // this.ReplyProtocolMessage[2] = (byte) 0x83;
        // }
        // }
        // if (this.protocolVersion == ProtocolVersion.PROTOCOL_NW_104) {
        //
        // }
        return Utility.ByteArrayToHexString(ReplyProtocolMessage, ReplyProtocolMessage.length);
    }

    public void setReplyProtocolMessage(byte[] replyProtocolMessage) {
        if (replyProtocolMessage != null) {
            int replyProtocolMessageLength = replyProtocolMessage.length;
            this.ReplyProtocolMessage = new byte[replyProtocolMessageLength];
            for (int i = 0; i < replyProtocolMessageLength; i++) {
                ReplyProtocolMessage[i] = replyProtocolMessage[i];
            }
        }
    }

    public Date getReceiveTime() {
        return ReceiveTime;
    }

    public String getReceiveTimeString() {
        SimpleDateFormat receivedDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        return receivedDateFormat.format(ReceiveTime);
    }

    public void setReceiveTime(Date receiveTime) {
        ReceiveTime = receiveTime;
    }

    public String getProtocolVersionTypes() {
        return protocolVersionTypes;
    }

    public void setProtocolVersionTypes(String protocolVersionTypes) {
        this.protocolVersionTypes = protocolVersionTypes;
    }

    public String getProtocolVersion() {
        return protocolVersion;
    }

    public void setProtocolVersion(String protocolVersion) {
        this.protocolVersion = protocolVersion;
    }

    public String getProtocolType() {
        return protocolType;
    }

    public void setProtocolType(String protocolType) {
        this.protocolType = protocolType;
    }
}
