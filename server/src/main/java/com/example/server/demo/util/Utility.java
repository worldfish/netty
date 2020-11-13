package com.example.server.demo.util;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.nio.ByteBuffer;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
public class Utility {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	/*@Autowired
	private MyselfLog log;*/
	

	public Utility() {
//		PropertyConfigurator.configure("G:\\ps3000_2.0\\PS3000FS\\src\\main\\resources\\log4j.properties");
//		ProtocolDataMessageLogger  = Logger.getLogger("ProtocolDataMessageLogger");
		try {
//			File  protocolDirectory= new File("./logs/protocol");
//			if(!protocolDirectory.exists() ) {
//				protocolDirectory.mkdirs();
//			}
//			
//			ProtocolDataMessageLogFullName = "./logs/protocol/" + ProtocolDataMessageLogName;
//			File logFileHandler = new File(ProtocolDataMessageLogFullName);
//						
//			ProtocolDataMessageLogWriter = new FileWriter("./logs/protocol/" + ProtocolDataMessageLogName, true);
//			ProtocolDataMessageLogBufferedWriter=new BufferedWriter(ProtocolDataMessageLogWriter);
//			
//			Path path = Paths.get(logFileHandler.getAbsolutePath());
//			BasicFileAttributes basicFileAttributes = Files.readAttributes(path, BasicFileAttributes.class);
//			
//			Calendar logFileCreateTime = Calendar.getInstance();
//			String logFileCreateTimeString = basicFileAttributes.creationTime().toString();
//			logFileCreateTime.setTimeInMillis(basicFileAttributes.creationTime().toMillis());
//			LogFileCreateDateTime = logFileCreateTime.getTime();

		} catch (Exception e) {
			e.printStackTrace();
		}

		CurrentDateTime = new Date();
	}
//	private Logger ProtocolDataMessageLogger = null;

	private String ProtocolDataMessageLogName = "ProtocolDataMessage.log";
	private String ProtocolDataMessageLogFullName = null;
	private FileWriter ProtocolDataMessageLogWriter = null;
	private BufferedWriter ProtocolDataMessageLogBufferedWriter = null;
	private Date CurrentDateTime = null;
	private Date LogFileCreateDateTime = null;

	// 时间戳
	private static Date EventCurrentServiceDateTime = new Date();
	private static Date CurveCurrentServiceDateTime = new Date();

	public static Date getEventCurrentServiceDateTime() {
		return EventCurrentServiceDateTime;
	}

	public static void setEventCurrentServiceDateTime(Date eventCurrentServiceDateTime) {
		EventCurrentServiceDateTime = eventCurrentServiceDateTime;
	}

	public static Date getCurveCurrentServiceDateTime() {
		return CurveCurrentServiceDateTime;
	}

	public static void setCurveCurrentServiceDateTime(Date curveCurrentServiceDateTime) {
		CurveCurrentServiceDateTime = curveCurrentServiceDateTime;
	}

	public static byte[] HexStringToByteArray(String HexString) {
		byte[] ByteArray = new byte[HexString.length() / 2];
		try {
			for (int index = 0; index < (HexString.length()); index += 2) {
				ByteArray[index / 2] = (byte) ((Character.digit(HexString.charAt(index), 16) << 4)
						+ Character.digit(HexString.charAt(index + 1), 16));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ByteArray;
	}

	public static String ByteArrayToHexString(byte[] ByteArray, int Size) {
		StringBuilder HexStringBuilder = new StringBuilder(Size * 4);
		for (int i = 0; i < Size; ++i) {
			HexStringBuilder.append(Character.forDigit((ByteArray[i] & 0xF0) >> 4, 16));
			HexStringBuilder.append(Character.forDigit((ByteArray[i] & 0x0F) >> 0, 16));
			HexStringBuilder.append(" ");
		}
		return HexStringBuilder.toString().toUpperCase();
	}

	public static String ByteArrayToHexString(byte[] ByteArray, int Start, int End) {
		StringBuilder HexStringBuilder = new StringBuilder(End - Start + 1);
		for (int i = End; i >= Start; --i) {
			HexStringBuilder.append(Character.forDigit((ByteArray[i] & 0xF0) >> 4, 16));
			HexStringBuilder.append(Character.forDigit((ByteArray[i] & 0x0F) >> 0, 16));
		}
		return HexStringBuilder.toString().toUpperCase();
	}

	public static String DigitArrayToString(Integer[] Array, String gap) {
		StringBuilder stringBuilder = new StringBuilder("");
		for (int i = 0; i < Array.length; i++) {
			if(Array[i] != null) {
				stringBuilder.append(Array[i]);
			}
			if (i < (Array.length - 1)) {
				stringBuilder.append(gap);
			}
		}
		return stringBuilder.toString();
	}

	public static String DigitArrayToString(Integer[] Array, Integer length, String gap) {
		StringBuilder stringBuilder = new StringBuilder("");
		for (int i = 0; i < (length == 0 ? Array.length : length); i++) {
			if(Array[i] != null) {
				stringBuilder.append(Array[i]);
			}
			if (i < (Array.length - 1)) {
				stringBuilder.append(gap);
			}
		}
		return stringBuilder.toString();
	}

	public static String DigitArrayToString(Short[] Array, String gap) {
		StringBuilder stringBuilder = new StringBuilder("");
		for (int i = 0; i < Array.length; i++) {
			if(Array[i] != null) {
				stringBuilder.append(Array[i]);
			}
			if (i < (Array.length - 1)) {
				stringBuilder.append(gap);
			}
		}
		return stringBuilder.toString();
	}

	public static String DigitArrayToString(Short[] Array, Integer length, String gap) {
		StringBuilder stringBuilder = new StringBuilder("");
		for (int i = 0; i < (length == 0 ? Array.length : length); i++) {
			if(Array[i] != null) {
				stringBuilder.append(Array[i]);
			}
			if (i < (Array.length - 1)) {
					stringBuilder.append(gap);
			}
		}
		return stringBuilder.toString();
	}

	public static String DigitArrayToString(Short[] Array, Integer length, Integer currentMaximum, String gap) {
		StringBuilder stringBuilder = new StringBuilder("");
		for (int i = 0; i < (length == 0 ? Array.length : length); i++) {
			if (i < currentMaximum) {
				if(Array[i] != null) {
					stringBuilder.append(Array[i]);
				}
			}
			if (i < (Array.length - 1)) {
				stringBuilder.append(gap);
			}
		}
		return stringBuilder.toString();
	}

	public static String DigitArrayToString(Double[] Array, String gap) {
		StringBuilder stringBuilder = new StringBuilder("");
		for (int i = 0; i < Array.length; i++) {
			if(Array[i] != null) {
				stringBuilder.append(Array[i]);
			}
			if (i < (Array.length - 1)) {
				stringBuilder.append(gap);
			}
		}
		return stringBuilder.toString();
	}

	public static String DigitArrayToString(Double[] Array, Integer length, String gap) {
		try {
			StringBuilder stringBuilder = new StringBuilder("");
			for (int i = 0; i < (length == 0 ? Array.length : length); i++) {
				if(Array[i] != null) {
					stringBuilder.append(Array[i]);
				}
				if (i < (Array.length - 1)) {
					stringBuilder.append(gap);
				}
			}
			return stringBuilder.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static Integer[] StringToIntegerArray(String src, String gap) {
		if (src != null) {
			List<String> curveDataArray = Arrays.asList(src.split(gap));
			Integer[] Reply = new Integer[curveDataArray.size() == 0 ? 512 : curveDataArray.size()];
			for (int __index = 0; __index < Reply.length; __index++) {
				if (curveDataArray.get(__index) != null && (curveDataArray.get(__index)).compareTo("") != 0
						&& curveDataArray.get(__index).compareTo("null") != 0) {
					Reply[__index] = Integer.valueOf(curveDataArray.get(__index));
				}
			}
			return Reply;
		}
		return null;
	}

	public static Short[] StringToShortArray(String src, String gap) {
		if (src != null) {
			List<String> curveDataArray = Arrays.asList(src.split(gap));
			Short[] Reply = new Short[curveDataArray.size() == 0 ? 512 : curveDataArray.size()];
			for (int __index = 0; __index < Reply.length; __index++) {
				if (curveDataArray.get(__index) != null && (curveDataArray.get(__index)).compareTo("") != 0
						&& curveDataArray.get(__index).compareTo("null") != 0) {
					Reply[__index] = Short.valueOf(curveDataArray.get(__index));
				}
			}
			return Reply;
		}
		return null;
	}

	public static Double[] StringToDoubleArray(String src, String gap) {
		if (src != null) {
			List<String> curveDataArray = Arrays.asList(src.split(gap, -1));
			Double[] Reply = new Double[curveDataArray.size() == 0 ? 512 : curveDataArray.size()];
			for (int __index = 0; __index < Reply.length; __index++) {
				if (curveDataArray.get(__index) != null && (curveDataArray.get(__index)).compareTo("") != 0
						&& curveDataArray.get(__index).compareTo("null") != 0) {
					Reply[__index] = Double.valueOf(curveDataArray.get(__index));
				}
			}
			return Reply;
		}
		return null;
	}

	public static Integer HexStringToInteger(String Src) {
		return Integer.parseInt(Src, 16);
	}

	public static Integer ByteArrayToInteger(byte[] source) {
		int targets = (source[0] & 0xff) | ((source[1] << 8) & 0xff00) // |
																		// 表示安位或
				| ((source[2] << 24) >>> 8) | (source[3] << 24);
		return targets;
	}

	public static String GetDataFormatName(byte[] ByteStream) {
		String DataFormatName = null;

		byte LENGH = ByteStream[1];

		byte ITF_T = (byte) (ByteStream[2] & (byte) 0x01);
		byte ITF_R = (byte) (ByteStream[4] & (byte) 0x01);
		if (LENGH > 0x04 && ITF_T == (byte) 0x00 && ITF_R == (byte) 0x00) {
			DataFormatName = "I";
		}

		byte STF_T_1 = (byte) (ByteStream[2] & (byte) 0x01);
		byte STF_T_2 = (byte) (ByteStream[2] & (byte) 0xFE);
		byte STF_T_3 = (byte) (ByteStream[3] & (byte) 0xFF);

		if (LENGH == 0x04 && STF_T_1 == (byte) 0x01 && STF_T_2 == (byte) 0x00 && STF_T_3 == (byte) 0x00) {
			DataFormatName = "S";
		}

		byte UTF_T_1 = (byte) (ByteStream[2] & (byte) 0x03);
		byte UTF_T_2 = (byte) (ByteStream[2] & (byte) 0xFC);
		byte UTF_T_3 = (byte) (ByteStream[3] & (byte) 0xFF);
		byte UTF_T_4 = (byte) (ByteStream[4] & (byte) 0xFF);
		byte UTF_T_5 = (byte) (ByteStream[5] & (byte) 0xFF);
		if (LENGH == 0x04 && UTF_T_1 == (byte) 0x03 && UTF_T_2 != (byte) 0x00 && UTF_T_3 == (byte) 0x00
				&& UTF_T_4 == (byte) 0x00 && UTF_T_5 == (byte) 0x00) {
			DataFormatName = "U";
		}

		return DataFormatName;
	}

	/*public void saveProtocolByDay(ProtocolPackInfomation potocolPackInfomation) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
		SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat fileNameFormat = new SimpleDateFormat("yyyyMMdd");
		try {
			String messageDesc = potocolPackInfomation.isReply() ? potocolPackInfomation.getReplyDetailMessage()
					: potocolPackInfomation.getReceivedDetailMessage();

			String hostDestDesc = potocolPackInfomation.getClientHostAddress() + ":"
					+ potocolPackInfomation.getClientPort();
			String hostSrcDesc = potocolPackInfomation.getServcerInternetProtocolAddress() + ":"
					+ potocolPackInfomation.getServerPort();

			String hostDest = potocolPackInfomation.isReply() ? hostDestDesc : hostSrcDesc;
			String hostSrc = potocolPackInfomation.isReply() ? hostSrcDesc : hostDestDesc;

			if (potocolPackInfomation != null) {
				String CurrentDateTime = "[" + format.format(new Date()) + "]:";
				
				String message = potocolPackInfomation.getReceivedProtocolMessage()!=null?ByteArrayToHexString(potocolPackInfomation.getReceivedProtocolMessage(),
						potocolPackInfomation.getReceivedProtocolMessage().length):"";
				
				String ReceivedMessage = CurrentDateTime + messageDesc + "[" + hostSrc + " >> " + hostDest + "]-" + message;
				String[] ReceivedMessageArr = ReceivedMessage.split(" ");
				String date = "";
				boolean flag = false;
				
				if("PROTOCOL_NW_104_PLAN".equals(potocolPackInfomation.getProtocolType().name())){
					flag = true;
				}
				
				if (flag && potocolPackInfomation.getReceivedProtocolMessage() != null) {
					
					// ProtocolDataMessageLogBufferedWriter.write(ReceivedMessage);
					
					int y = ((byte)Integer.parseInt(ReceivedMessageArr[ReceivedMessageArr.length-1], 16) & 0xff);
					String yS = y>9?y+"":"0"+y;
					int m = ((byte)Integer.parseInt(ReceivedMessageArr[ReceivedMessageArr.length-2], 16) & 0x0f);
					String mS = m>9?m+"":"0"+m;
					int d = ((byte)Integer.parseInt(ReceivedMessageArr[ReceivedMessageArr.length-3], 16) & 0x1f);
					String dS = d>9?d+"":"0"+d;
					
					date = "20"+ yS + "-"+ mS + "-"+ dS ;
					
				}else{
					date = format2.format(new Date());
				}
				
				logger.warn(ReceivedMessage);
//				log.setLogMessage(potocolPackInfomation.getReceiveTime(), ReceivedMessage);
				//log.setLogMessage(date, ReceivedMessage);
				// ProtocolDataMessageLogBufferedWriter.newLine();
				// ProtocolDataMessageLogBufferedWriter.flush();

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}*/
	
	public static byte[] hexStr2Byte(String hex) {
        ByteBuffer bf = ByteBuffer.allocate(hex.length() / 2);
        for (int i = 0; i < hex.length(); i++) {
            String hexStr = hex.charAt(i) + "";
            i++;
            hexStr += hex.charAt(i);
            byte b = (byte) Integer.parseInt(hexStr, 16);
            bf.put(b);
        }
        return bf.array();
    }
	
	/**
	 * 调换字节位置
	 * @param i
	 * @return
	 */
	public static String transposition(int i){
		if(i > 65535){
			return Integer.toHexString((int)(((i & 0xFF0000) >> 16 )| (i & 0xFF00) | ((i & 0xFF) << 16) ));
		}else{
			return Integer.toHexString((int)(((i & 0xFF00) >> 8 ) | ((i & 0xFF) << 8) ));
		}
	}
	
	/**
	 * 补齐位置
	 * @param s
	 * @param len
	 * @return
	 */
	public static String fillPosition(String s , int len){
		if(s.length() < len){
			int max = len-s.length();
			for(int i=0;i<max;i++){
				s = "0"+s;
			} 
		}
		return s.toUpperCase();
	}
	
	/**
	 * 补齐位置
	 * @param s
	 * @return
	 */
	public static String fillPosition(String s){
//		System.out.println(s);
		List<String> lists = Arrays.asList(s.split(",",-1));
		
		int start = 0;
		String startValue = lists.get(0);
		String value = "";
		for(int i =1;i<lists.size();i++ ){
			value = lists.get(i);
			if(value.trim().equals("") && !lists.get(i-1).trim().equals("")){
				start = i-1;
				startValue = lists.get(start);
			}
			else if(!value.trim().equals("") && lists.get(i-1).trim().equals("")){
				if(!"".equals(startValue)){
					int count = i - start;
					Float startF = Float.valueOf(startValue);
					Float endF = Float.valueOf(lists.get(i));
					Float balance = (startF - endF) / count;
					
//					System.out.println("start:"+start+" value:"+startValue);
					for(int j = 1;j<count;j++){
//						System.out.println("no:"+(start+j)+" value:"+ (startF - (balance*j)) );
						lists.set(start+j, String.valueOf(startF - (balance*j)));
					}
//					System.out.println("end:"+i+" value:"+lists.get(i));
//					System.out.println("===============================");
				}
			}
			
		}
		s = StringUtils.join(lists.toArray(),",");
//		System.out.println(s);
		return s;
	}
	
	/**
	 * 补齐位置
	 * @param s
	 * @param start
	 * @param end
	 * @return
	 */
	public static String fillPosition(String s, int start , int end){
		if(end < start){
			System.out.println("end 小于 start");
			return s;
		}
		List<String> lists = Arrays.asList(s.split(",",-1));
		int sum = end - start;
		Float startF = Float.valueOf(lists.get(start));
		Float endF = Float.valueOf(lists.get(end));
		//Float balance = DataUtils.calculateFloat(DataUtils.calculateFloat(startF, "-", endF),"/",sum == 0?1:sum);
		for(int i=0;i<sum;i++){
			//lists.set(start+i , String.valueOf(startF - (balance*i)));
		}
		s = StringUtils.join(lists.toArray(),",");
		return s;
	}

	public static byte[] hexStrByte(String hexStr){
		String[] hexStrArr = hexStr.trim().split(" ");
		byte[] result = new byte[hexStrArr.length];
		for (int i=0;i<hexStrArr.length;i++) {
			result[i] = (byte)Integer.parseInt(hexStrArr[i], 16);
		}
		return result;
	}
	
	/**
	 * 
	 * @param //浮点字符串转整数字符串
	 * @return
	 */
	public static String IntString(String s){
		List<String> lists = Arrays.asList(s.split(",",-1));
		List<String> returnList = new ArrayList<String>();
		int valueI = 0;
		for (String value : lists) {
			if(StringUtils.isNotBlank(value)){
				valueI = Math.round(Float.valueOf(value));
				returnList.add(String.valueOf(valueI));
			}else{
				returnList.add(value);
			}
		}
		return StringUtils.join(returnList,",").toString();
	}


}
