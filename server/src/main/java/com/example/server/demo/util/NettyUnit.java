package com.example.server.demo.util;

import com.google.gson.Gson;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.net.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;


public class NettyUnit {
	
	private final static Gson gson = new Gson();
	
	//配置名称
	public final static String CONTEXT_ROOT_PORT = "application-netty-config.properties";
	public final static String CONTEXT_PORT = ".netty-config.properties";
	public final static String CONTEXT_PORT_PREFIX ="portContext.";
	
	public final static String CONTEXT_ROOT_SYS = "application-sys-config.properties";
	public final static String CONTEXT_SYS = ".sys-config.properties";
	public final static String CONTEXT_SYS_HOSTNAME = "sys.hostName";
	
	//常量
	//myMessage指定存储对象类型
	public final static String TYPE_BYTE="byte";
	public final static String TYPE_BYTEARR="btyeArr";
	public final static String TYPE_LIST="list";
	
	//报完标识
	public final static byte PRO_VOLTAGE_GD104_TLTLE = 0X68;
	public final static byte PRO_VOLTAGE_GD104_CODE = (byte)0XFE;
	public final static byte PRO_VOLTAGE_NW104_TITLE = 0X68;
	public final static String PRO_NW104 = "nw104";
	public final static String PRO_H9000="h9000";
	
	//报文类型
	public final static String PRO_TYPE_GENERAL = "general";
	public final static String PRO_TYPE_VOLTAGE_NW104 = "voltage_nw104";
	public final static String PRO_TYPE_ZH1100_HEARTBEAT = "zh1100_heartbeat";
	public final static String PRO_TYPE_FS_TIME_CALIBRATION = "fs_time_calibration";
	public final static String PRO_TYPE_H9000_NW104_DOWN = "pro_type_h9000_nw104_down";
	public final static String PRO_TYPE_H9000_NW104_UP = "pro_type_h9000_nw104_up";
	
	public final static String codingFormat = "UTF-8";
	
	/**
	 * 保存每个服务器最后回复时间戳
	 */
	public static Map<String,Long> replyMap = new ConcurrentHashMap<String, Long>();
	
	/**
	 * 端口协议
	 */
	public static Map<String,String> staticPort = new HashMap<String,String>();
	public static String HOSTNAME = "";
	
	public static Date timeCalibration = null; 
	
	/** 端口映射方法 */
	public static Map<String, String> portFun = new HashMap<String,String>();
	
	/**
	 * 获取端口映射
	 * @param v
	 * @return
	 */
	public static String getPortFun(String v){
		if(portFun.containsKey(v)){
			v = portFun.get(v);
		}
		return v;
	}
	
	/**
	 * socketip数组
	 */
//	private static Integer[] SOCKETIP = {8700,2404};
	/**
	 * websocketip数组
	 */
	private static Integer[] WEBSOCKETIP = {9090};
	/**
	 * socketipList
	 */
//	public static List<Integer> SOCKIPLIST = Arrays.asList(SOCKETIP);
	/**
	 * websocketipList
	 */
	public static List<Integer> WEBSOCKETIPLIST = Arrays.asList(WEBSOCKETIP);
	
	/**
	 * 存放fs主备端口
	 */
	public static List<Integer> fsPotrs = new ArrayList<Integer>(); 
	
	/**
	 * 将SysportsList String转为Integer
	 * @param portlist
	 * @return
	 */
	public  static Logger  logger = LoggerFactory.getLogger(NettyUnit.class); 
	
	/**
	 * 创建配置文件目录
	 * @return
	 */
	public static String getRootPath(){
		File directory = new File("");//参数为空
		String rootPath = "";
		try {
			rootPath = directory.getCanonicalPath();
		} catch (IOException e) {
			// TODO Auto-generated catch block
		}
		return rootPath;
	}
	
	/**
	 * 获取可用端口
	 * @param portlist
	 * @return
	 */
	/*public static List<Integer> getSysportsList(List<SysPort> portlist){
		List<Integer> result = new ArrayList<Integer>();
		 
		for (SysPort sysPort : portlist) {
			Integer port = new Integer(sysPort.getServerPort().intValue());
			if((port < 65535) && isHostConnectable("127.0.0.1",port)){
				logger.info("端口可用："+port);
				result.add(port);
			}else{
				logger.info("端口不可用"+port);
			}
		}
		return result;
	}*/
	
	/**
	 * 获取当前第一个网卡的ip
	 * @return
	 */
	public static String getHost(){
		String restult = "";
		Enumeration<NetworkInterface> nets;
		try {
			nets = NetworkInterface.getNetworkInterfaces();
			for (NetworkInterface netint : Collections.list(nets)){
				if (null != netint.getHardwareAddress()) {
					List<InterfaceAddress> list = netint.getInterfaceAddresses();
					for (InterfaceAddress interfaceAddress : list) {
						InetAddress localip=interfaceAddress.getAddress();
						int i = 0;
						if(localip instanceof Inet4Address)  {    //返回ipv4地址
							String ip =localip.toString().split("/")[1];
							if(i == 0){restult = ip;}
							i++;
		                }
					}
				}
			}
		} catch (SocketException e) {
			e.printStackTrace();
		}
		if(!StringUtils.isNotBlank(restult)){restult = "127.0.0.1";}
		logger.info("目前服务器IP:"+restult);
		return restult;
	}
	
	/**
	 * 获取所有ipv4 ip地址
	 * @return
	 */
	public static List<String> getHostIpList(){
		List<String> restult = new ArrayList<String>();
		Enumeration<NetworkInterface> nets;
		try {
			nets = NetworkInterface.getNetworkInterfaces();
			for (NetworkInterface netint : Collections.list(nets)){
				if (null != netint.getHardwareAddress()) {
					List<InterfaceAddress> list = netint.getInterfaceAddresses();
					for (InterfaceAddress interfaceAddress : list) {
						InetAddress localip=interfaceAddress.getAddress();
						if(localip instanceof Inet4Address)  {    //返回ipv4地址
							String ip =localip.toString().split("/")[1];
							restult.add(ip);
		                }
					}
				}
			}
		} catch (SocketException e) {
			e.printStackTrace();
		}
		return restult;
	}
	
	/**
	 * 获取服务端地址
	 * @param ctx
	 * @return
	 */
	public static String getlocalAddress(ChannelHandlerContext ctx){
		String result = "";
		result = ctx.channel().localAddress().toString();
		return result;
	}
	
	/**
	 * 获取连接服务器地址
	 * @param ctx
	 * @return
	 */
	public static String getServicetHome(ChannelHandlerContext ctx){
		String result = "";
		result = ctx.channel().remoteAddress().toString().replace("/", "");
		return result;
	}
	
	/**
	 * 获取客户端地址
	 * @param ctx
	 * @return
	 */
	public static String getremoteAddress(ChannelHandlerContext ctx){
		String result = "";
		result = ctx.channel().remoteAddress().toString();
		return result;
	}
	
	/**
	 * 获取服务端端口
	 * @param ctx
	 * @return
	 */
	public static String getPort(ChannelHandlerContext ctx){
		String reuslt = "";
		String clientPortStrin = getlocalAddress(ctx);
		if(clientPortStrin != null && !"".equals(clientPortStrin)){
			reuslt = clientPortStrin.split(":")[1];
    	}
		return reuslt;
	}
	
	/**
	 * 获取服务端ip地址
	 * @param ctx
	 * @return
	 */
	public static String getIp(ChannelHandlerContext ctx){
		String reuslt = "";
		String clientIpStrin = getlocalAddress(ctx);
		if(clientIpStrin != null && !"".equals(clientIpStrin)){
			reuslt = clientIpStrin.split(":")[0].replace("/", "");
    	}
		return reuslt;
	}
	
	/**
	 * 获取客户端端口
	 * @param ctx
	 * @return
	 */
	public static String getClientPort(ChannelHandlerContext ctx){
		String reuslt = "";
		String clientPortStrin = getremoteAddress(ctx);
		if(clientPortStrin != null && !"".equals(clientPortStrin)){
			reuslt = clientPortStrin.split(":")[1];
    	}
		return reuslt;
	}
	
	/**
	 * 获取客户端ip地址
	 * @param ctx
	 * @return
	 */
	public static String getClientIp(ChannelHandlerContext ctx){
		String reuslt = "";
		String clientIpStrin = getremoteAddress(ctx);
		if(clientIpStrin != null && !"".equals(clientIpStrin)){
			reuslt = clientIpStrin.split(":")[0].replace("/", "");
    	}
		return reuslt;
	}
	
	/**
	 * 获取当前中调连接信息
	 */
	/*public static String getSysConnectionStateString(){
		String result = "";
		List<ClientInfo> linkInfoList = new ArrayList<ClientInfo>();
		
		for(String key:SingleClient.getSingleClient().getKeys()){
			linkInfoList.add(SingleClient.getSingleClient().getInfo(key));
		}
		if(linkInfoList.size() > 0){
			result = gson.toJson(linkInfoList);
		}
		return result;
	}*/
	
	/**
	 * 返回目标端口当前连接数
	 * @param severPort
	 * @return
	 */
	/*public static int getSysConnectionPotrNum(String severPort){
		int result = 0;
		for(String key:SingleClient.getSingleClient().getKeys()){
			if(StringUtils.isNotBlank(key)){
				String port = key.split("_")[0];
				if(port.equals(severPort)){
					result ++;
				}
			}
		}
		return result;
	}*/
	
	/**
	 * 关闭指定端口的所有连接
	 * @param severPort
	 */
	/*public static void closePotrSysConnection(String severPort){
		for(String key:SingleClient.getSingleClient().getKeys()){
			if(StringUtils.isNotBlank(key)){
				String port = key.split("_")[0];
				Channel channel = SingleClient.getSingleClient().getClientCtx(key);
				if(port.equals(severPort) && channel != null){
					SingleClient.getSingleClient().getClientCtx(key).close();
				}
			}
		}
	}*/
	
	
	/**
	 * 判断端口是否可以使用
	 * @param host
	 * @param port
	 * @return
	 */
	public static boolean isHostConnectable(String host, int port) {
		Socket socket = new Socket();
        try {
        	socket.bind(new InetSocketAddress(host,port));
        	//socket.bind(new InetSocketAddress(port));
        } catch (IOException e) {
//	            e.printStackTrace();
            return false;
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return true;
    }
	
	public static byte[] intToByteArray(int i) {  
	    return new byte[] {  
	        (byte) ((i >> 24) & 0xFF),  
	        (byte) ((i >> 16) & 0xFF),     
	        (byte) ((i >> 8) & 0xFF),     
	        (byte) (i & 0xFF)  
	    };  
	} 
	
	public static int byteArrayToInt(byte[] b) {  
		return   b[3] & 0xFF |  
            (b[2] & 0xFF) << 8 |  
            (b[1] & 0xFF) << 16 |  
            (b[0] & 0xFF) << 24;  
	}   
	
	public static byte[] addBytes(byte[] data1, byte[] data2) {  
	    byte[] data3 = new byte[data1.length + data2.length];  
	    System.arraycopy(data1, 0, data3, 0, data1.length);  
	    System.arraycopy(data2, 0, data3, data1.length, data2.length);  
	    return data3;  
	}

	public static int merge(byte[] bytes,int start ,int end){
		int time = -1;
		if(start>end){ return time; }
		for(int i=end; i>start; i--){
			time = (time == -1) ?
					bytes[end - i] & 0xFF :
					(time | (bytes[end - i] & 0xFF) << (8 * (end - i)));
		}
		return time;
	}
}
