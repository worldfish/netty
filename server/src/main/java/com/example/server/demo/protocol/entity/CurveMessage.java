package com.example.server.demo.protocol.entity;

import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class CurveMessage {
    private Integer id;
    private String receiveByteStream;
    private String replyByteStream;

    private String protocolVersion;
    private String protocolType;

    private String curveDate;

    private Date createTime;
    private Date updateTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getReceiveByteStream() {
        return receiveByteStream;
    }

    public void setReceiveByteStream(String receiveByteStream) {
        this.receiveByteStream = receiveByteStream;
    }

    public String getReplyByteStream() {
        return replyByteStream;
    }

    public void setReplyByteStream(String replyByteStream) {
        this.replyByteStream = replyByteStream;
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

    public String getCurveDate() {
        return curveDate;
    }

    public void setCurveDate(String curveDate) {
        this.curveDate = curveDate;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
