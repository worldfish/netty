package com.example.server.demo.protocol.entity;

import java.util.Date;

public class CurveProtocol{
	
	private int curveNumber;
	private Date curveDate;
	private Date receiveTime;
	private String protocolContent;
	private int frameNumber;
	private int frameNumberCurrent;
	
	public int getCurveNumber() {
		return curveNumber;
	}
	public void setCurveNumber(int curveNumber) {
		this.curveNumber = curveNumber;
	}
	public Date getCurveDate() {
		return curveDate;
	}
	public void setCurveDate(Date curveDate) {
		this.curveDate = curveDate;
	}
	public Date getReceiveTime() {
		return receiveTime;
	}
	public void setReceiveTime(Date receiveTime) {
		this.receiveTime = receiveTime;
	}
	public String getProtocolContent() {
		return protocolContent;
	}
	public void setProtocolContent(String protocolContent) {
		this.protocolContent = protocolContent;
	}
	public int getFrameNumber() {
		return frameNumber;
	}
	public void setFrameNumber(int frameNumber) {
		this.frameNumber = frameNumber;
	}
	public int getFrameNumberCurrent() {
		return frameNumberCurrent;
	}
	public void setFrameNumberCurrent(int frameNumberCurrent) {
		this.frameNumberCurrent = frameNumberCurrent;
	}
	
}
