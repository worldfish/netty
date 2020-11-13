package com.example.server.demo.protocol.entity;

import java.util.Date;

public class ProtocolMessage {
    private byte[] receiveByteStream;
    private byte[] replyByteStream;
    private byte[][] replyByteStreamExtend;
    private Integer replyByteStreamExtendCount = 0;
    private String DataTypeName;

    private String protocolVersion;
    private String protocolType;

    private boolean ProtocolUnUsage;
    private String PortWithProtocol;
    private Date newReceiveTime;
    private boolean NeedReply = true;

    public Date getNewReceiveTime() {
        return newReceiveTime;
    }

    public void setNewReceiveTime(Date newReceiveTime) {
        this.newReceiveTime = newReceiveTime;
    }

    public String getPortWithProtocol() {
        return PortWithProtocol;
    }

    public void setPortWithProtocol(String portWithProtocol) {
        PortWithProtocol = portWithProtocol;
    }

    public boolean isProtocolUnUsage() {
        return ProtocolUnUsage;
    }

    public void setProtocolUnUsage(boolean protocolUnUsage) {
        ProtocolUnUsage = protocolUnUsage;
    }

    public byte[] getReceiveByteStream() {
        return receiveByteStream;
    }

    public void setReceiveByteStream(byte[] receiveByteStream) {
        if(receiveByteStream != null) {
            this.receiveByteStream = new byte[receiveByteStream.length];
            for (int i = 0; i < receiveByteStream.length; i++) {
                this.receiveByteStream[i] = receiveByteStream[i];
            }
        } else {
            this.receiveByteStream = receiveByteStream;
        }
    }

    public byte[] getReplyByteStream() {
        return replyByteStream;
    }

    public void setReplyByteStream(byte[] replyByteStream) {
        if (replyByteStream != null) {
            this.replyByteStream = new byte[replyByteStream.length];
            for (int i = 0; i < replyByteStream.length; i++) {
                this.replyByteStream[i] = replyByteStream[i];
            }
        }
    }

    public byte[][] getReplyByteStreamExtend() {
        return replyByteStreamExtend;
    }

    public void setReplyByteStreamExtend(byte[][] replyByteStreamExtend) {
        this.replyByteStreamExtend = replyByteStreamExtend;
    }

    public Integer getReplyByteStreamExtendCount() {
        return replyByteStreamExtendCount;
    }

    public void setReplyByteStreamExtendCount(Integer replyByteStreamExtendCount) {
        this.replyByteStreamExtendCount = replyByteStreamExtendCount;
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

    public String getDataTypeName() {
        return DataTypeName;
    }

    public void setDataTypeName(String dataTypeName) {
        DataTypeName = dataTypeName;
    }

    public boolean isNeedReply() {
        return NeedReply;
    }

    public void setNeedReply(boolean needReply) {
        NeedReply = needReply;
    }
}
