package io.jpress.commons.utils;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符处理公共类
 * @author yh.yu
 *
 */
public class StringUtil {
	/***空字符串*/
	public static final String EMPTY_STRING = "";
	private static final String PIC_FORMAT = ".jpg";
	
	
	public static String format(String str, Object... args)
	{
		return format(str,java.util.regex.Pattern.compile("\\{(\\d+)\\}"),args);
	}
	/**
	 * 字符串转换为ASCII码
	 * @param s
	 * @return
	 */
	public static String string2ASCII(String str, String regex) {
        if (isEmpty(str)) {
            return null;
        }

        char[] chars = str.toCharArray();
        int[] asciiArray = new int[chars.length];

        for (int i = 0; i < chars.length; i++) {
            asciiArray[i] = char2ASCII(chars[i]);
        }

        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < asciiArray.length; i++) {
            sb.append(asciiArray[i]).append(regex);
        }
        return sb.toString();
	}


	public static String ascii2String(String ASCIIs, String regex) {
		String[] ASCIIss = ASCIIs.split(regex);
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < ASCIIss.length; i++) {
            sb.append((char) ascii2Char(Integer.parseInt(ASCIIss[i])));
        }
        return sb.toString();
    }


	public static char ascii2Char(int ASCII) {
        return (char) ASCII;
    }
	public static int char2ASCII(char c) {
	    return (int) c;
	}

	/**
	 * 字符串参数格式化
	 * @param str
	 * @param args
	 * @return
	 */
	public static String format(final String str, Pattern pattern, Object... args){
		// 这里用于验证数据有效性
		if (str == null || "".equals(str))
			return "";
		if (args.length == 0) {
			return str;
		}

		String result = str;

		// 这里的作用是只匹配{}里面是数字的子字符串
		java.util.regex.Pattern p = pattern;
		java.util.regex.Matcher m = p.matcher(str);

		while (m.find()) {
			// 获取{}里面的数字作为匹配组的下标取值
			int index = Integer.parseInt(m.group(1));
			// 这里得考虑数组越界问题，{1000}也能取到值么？？
			if (index < args.length && args[index]!=null) {
				// 替换，以{}数字为下标，在参数数组中取值
				result = result.replace(m.group(), args[index].toString());
			} else {
				result = result.replace(m.group(), "");
			}
		}
		return result;
	}
	/**
	 * 检测是否是空字符串
	 * @param str
	 * @return
	 */
	public static final boolean isEmpty(String str) {
		if (str == null || str.length() == 0 || "null".equals(str)) {
			return true;
		}
		return false;
	}
	/**
	  * 汉语转拼音
	  * @param src
	  * @return
	  */
	 public static String getPingYin(String src) {
		  if(StringUtil.isNotEmpty(src)){

		  }
	        char[] t1 = null;
	        t1 = src.toCharArray();
	        String[] t2 = new String[t1.length];
	        HanyuPinyinOutputFormat t3 = new HanyuPinyinOutputFormat();

	        t3.setCaseType(HanyuPinyinCaseType.LOWERCASE);
	        t3.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
	        t3.setVCharType(HanyuPinyinVCharType.WITH_V);
	        String t4 = "";
	        int t0 = t1.length;
	        try {
	            for (int i = 0; i < t0; i++) {
	                // 判断是否为汉字字符
	                if (java.lang.Character.toString(t1[i]).matches(
	                        "[\\u4E00-\\u9FA5]+")) {
	                    t2 = PinyinHelper.toHanyuPinyinStringArray(t1[i], t3);
	                    t4 += t2[0];
	                } else
	                    t4 += java.lang.Character.toString(t1[i]);
	            }  
	            // System.out.println(t4);  
	            return t4;  
	        } catch (BadHanyuPinyinOutputFormatCombination e1) {  
	            e1.printStackTrace();  
	        }  
	        return t4;  
	    }
	/**
	 * 检测指定字符串是否不是空串
	 * @param str
	 * @return
	 */
	public static final boolean isNotEmpty(String str) {
		return !isEmpty(str);
	}
	/**
	 * 检测指定字符串是否为空串（去除前后导全半角空格）
	 * @param str
	 * @return
	 */
	public static final boolean isBlank(String str) {
		if (isNotEmpty(str)) {
			for (int i = 0; i < str.length(); i++) {
				if ((Character.isWhitespace(str.charAt(i)) == false)) {
					return false;
				}
			}
		}
		return true;
	}
	/**
	 * 检测指定字符串是否不为空串
	 * @param str
	 * @return
	 */
	public static final boolean isNotBlank(String str) {
		return !isBlank(str);
	}
	/**
	 * 将为NULl的字符串转换为空字符串
	 * @param str
	 * @return
	 */
	public static final String trimNull(String str) {
		return null==str?EMPTY_STRING:str;
	}
	/**
	 * 如果字符串为Null则将其转换为对应的默认串
	 * @param str
	 * @param defaultStr
	 * @return
	 */
	public static final String setDefaultIfNull(String str, String defaultStr) {
		return null==str?trimNull(defaultStr):str;
	}
	/**
	 * 如果字符串为Null则将其转换为对应的默认串""
	 * @param str
	 * @return
	 */
	public static final String setDefaultIfNull(String str) {
		return setDefaultIfNull(str, EMPTY_STRING);
	}
	/**
	 * 如果字符串为空字符串则将其转换为对应的默认串
	 * @param str
	 * @param defaultStr
	 * @return
	 */
	public static final String setDefaultIfEmpty(String str, String defaultStr) {
		return isEmpty(str)?trimNull(defaultStr):str;
	}
	/**
	 * 如果字符串为空字符串则将其转换为对应的默认串""
	 * @param str
	 * @return
	 */
	public static final String setDefaultIfEmpty(String str) {
		return setDefaultIfEmpty(str, EMPTY_STRING);
	}
	/**
	 * 如果字符串为空串则将其转换为对应的默认串
	 * @param str
	 * @param defaultStr
	 * @return
	 */
	public static final String setDefaultIfBlank(String str, String defaultStr) {
		return isBlank(str)?trimNull(defaultStr):str;
	}
	/**
	 * 如果字符串为空串则将其转换为对应的默认串""
	 * @param str
	 * @return
	 */
	public static final String setDefaultIfBlank(String str) {
		return setDefaultIfBlank(str, EMPTY_STRING);
	}
	/**
	 * 检测是不是数字化的字符串
	 * @param str
	 * @return
	 */
	public static final boolean isNumer(String str) {
		if (isEmpty(str)) {
			return false;
		}
		int length = str.length();
		for (int i = 0; i < length; i++) {
			if (!Character.isDigit(str.charAt(i))) {
				return false;
			}
		}
		return true;
	}
	/**
	 * 转换为半角字符串
	 * 任意字符串转换为<p>半角</p>字符串
	 * 全角空格为12288，半角空格为32
	 * 其他字符半角(33-126)与全角(65281-65374)的对应关系是：均相差65248
	 * @param input
	 * @return
	 */
	public static final String ToDBC(String input) {
		char[] c = input.toCharArray();
		for (int i = 0; i< c.length; i++) {
			if (c[i] == 12288) {
				c[i] = (char) 32;
				continue;
			}
			if (c[i]> 65280&& c[i]< 65375)
				c[i] = (char) (c[i] - 65248);
		}
		return new String(c);
	}
	/**
	 * 转全角的函数(SBC case)
	 * 任意字符串转	全角字符串
	 * 全角空格为12288，半角空格为32
	 * 其他字符半角(33-126)与全角(65281-65374)的对应关系是：均相差65248
	 * @param input
	 * @return
	 */
	public static final String ToSBC(String input) {
		char[] c = input.toCharArray();
		for (int i = 0; i< c.length; i++) {
			if (c[i] == 32) {
				c[i] = (char) 12288;
				continue;
			}
			if (c[i]< 127)
				c[i] = (char) (c[i] + 65248);
		}
		return new String(c);
	}
	/**
	 * 去除前后导空格（包括半角和全角的）
	 * @param input
	 * @return
	 */
	public static final String trim(String input) {
		return input.replaceAll("(^[ |　]*|[ |　]*$)","");
	}
	/**
	 * 去除所有的空格（包括半角和全角的）
	 * @param input
	 * @return
	 */
	public static final String trimAll(String input) {
		return input.replaceAll(" ","").replace("　", "");
	}


	/**
	 * 功能：
	 *
	 * @author yh1.yu
	 * @return
	 */
	public    static final String getLinkMobileShow(String mobile) {
		String linkMobileShow = "";
		if (StringUtil.isNotBlank(mobile) && mobile.length() > 3) {
			linkMobileShow = mobile.substring(0, 3)	+ "****" 
				+ mobile.substring(mobile.length() - 4,mobile.length());
		}
		return linkMobileShow;
	}
	/**
	 * 功能：
	 *
	 * @author yh1.yu
	 * @return
	 */
	public static final String getUserNameShow(String memberName){
		String userNameShow="";
		if (StringUtil.isNotBlank(memberName) && memberName.length() >= 2) {
			userNameShow = memberName.substring(0, 1)	+ "**";
		}
		return userNameShow;
	}
	/**
	 */
	public static final String get6Show(String showString){
		String userNameShow="";
		if (StringUtil.isNotBlank(showString) && showString.length() >= 6) {
			userNameShow = showString.substring(0, showString.length()-6)	+ "******";
		}else {
			return "******";
		}
		return userNameShow;
	}
	
	public static final String get4Show(String showString){
		String userNameShow="";
		if (StringUtil.isNotBlank(showString) && showString.length() >= 4) {
			userNameShow = showString.substring(0, showString.length()-4)	+ "****";
		}else {
			return "****";
		}
		return userNameShow;
	}
	
	public static final String get3MiddleShow(String pcode){
		String userNameShow="";
		if (StringUtil.isNotBlank(pcode) && pcode.length() >= 6) {
			userNameShow = pcode.replaceAll(pcode.substring(3,6),"***");
		}else {
			return "***";
		}
		return userNameShow;
	}
	
	/**
     * 生成交易流水号，规则:Yyyymmddhh24miss+6位自生成序号
     * @return
     */
	public static final String generatorSerialNumber() {
		String serialNumber = "";
		int num = generateSixCode();
		serialNumber = DateUtil.ymdhmsTimeStampFormat(new Date());
		return serialNumber + num;
	}
	
	/**
     * 生成六位随机数字
     * @return
     */
	private static int generateSixCode() {
		Random random = new Random();
		int x = random.nextInt(899999);
		int y = x + 100000;
		return y;
	}
    
    /**
     * 重新生成文件名
     * @param fileName 源文件名
     * @return
     */
	public static String restFileName(String fileName) {
		Random random = new Random();
		String suffix = StringUtils.EMPTY;
		if (StringUtils.isNotEmpty(fileName)) {
			suffix = fileName.substring(fileName.lastIndexOf("."));
		}
		if (StringUtils.isEmpty(suffix)) {
			suffix = PIC_FORMAT;
		}
		fileName = String.valueOf(System.currentTimeMillis()) + random.nextInt() + suffix;
		return fileName;
	}
	
    /**
     * 根据身份证号码取出生日期
     * @param idCard
     * @return
     */
    public static final String getBirthdate(String idCard){
		Integer idLength = idCard.length();
		if (idLength == 18) {
			return idCard.substring(6, 14);
		} else if (idLength == 15) {
			return "19" + idCard.substring(6, 12);
		}
		return "00000000";
    }
    
    /**
     * 字符串如果是空值，则返回空字符串
     * @param str
     * @return
     */
    public static final String nullToStr(Object obj){
    	return null == obj || "".equals(obj) ? "" : obj.toString();
    }
    
    /**
     * 转换文件大小
     * @param size
     * @return
     */
    public static String FormetFileSize(String size) {
        DecimalFormat df = new DecimalFormat("#.00");
        String fileSizeString = "0B";
        String format = "00";
        if(StringUtil.isNotEmpty(size)){
            int fileS = Integer.parseInt(size);
            if (fileS < 1024) {
                fileSizeString = size + "B";
            } else if (fileS < 1048576) {
                fileSizeString = df.format((double) fileS / 1024);
                fileSizeString = FormetFileSize(fileSizeString,format) + "KB";
            } else if (fileS < 1073741824) {
                fileSizeString = df.format((double) fileS / 1048576);
                fileSizeString = FormetFileSize(fileSizeString,format) + "MB";
            } else {
                fileSizeString = df.format((double) fileS / 1073741824);
                fileSizeString = FormetFileSize(fileSizeString,format) + "G";
            }
        }
        return fileSizeString;
    }
    
    /**
     * 文件大小小数位数格式化
     * @param fileSizeString
     * @param format
     * @return
     */
    private static String FormetFileSize(String fileSizeString, String format){
        try {
            String[] split = fileSizeString.split("\\.");
            if(split.length==2){
                if(split[1].equals(format)){
                    fileSizeString = split[0];
                }
            }
        } catch (Exception e) {}
        return fileSizeString;
    }
    
    /**
     * 生成字母+数字的随机数
     * @param length 长度
     * @return
     */
    public static String getStringRandom(int length) {
        String val = "";
        Random random = new Random();
        //参数length，表示生成几位随机数
        for(int i = 0; i < length; i++) {
            String charOrNum = random.nextInt(2) % 2 == 0 ? "char" : "num";
            //输出字母还是数字
            if( "char".equalsIgnoreCase(charOrNum) ) {
                //输出是大写字母还是小写字母 
                int temp = random.nextInt(2) % 2 == 0 ? 65 : 97;
                val += (char)(random.nextInt(26) + temp);
            } else if( "num".equalsIgnoreCase(charOrNum) ) {
                val += String.valueOf(random.nextInt(10));
            }
        }
        return val;
    }
    
    /**
     * 将数字转化为大写  
     * @param num
     * @return
     */
    public static String numToUpper(int num) {
        //String u[] = {"零","壹","贰","叁","肆","伍","陆","柒","捌","玖"};  
        String u[] = { "〇", "一", "二", "三", "四", "五", "六", "七", "八", "九" };
        char[] str = String.valueOf(num).toCharArray();
        String rstr = "";
        for (int i = 0; i < str.length; i++) {  
            rstr = rstr + u[Integer.parseInt(str[i] + "")];
        }  
        return rstr;  
    }
    
    /**
     * 将Map转为xml
     * @param map
     * @param sb
     */
    public static void mapToXML(Map map, StringBuffer sb) {
        Set set = map.keySet();
        for (Iterator it = set.iterator(); it.hasNext();) {
            String key = (String) it.next();
            Object value = map.get(key);
            if (null == value)  
                value = "";  
            if (value.getClass().getName().equals("java.util.ArrayList")) {  
                ArrayList list = (ArrayList) map.get(key);
                sb.append("<" + key + ">");  
                for (int i = 0; i < list.size(); i++) {  
                    HashMap hm = (HashMap) list.get(i);
                    mapToXML(hm, sb);  
                }  
                sb.append("</" + key + ">");  
  
            } else {  
                if (value instanceof HashMap) {
                    sb.append("<" + key + ">");  
                    mapToXML((HashMap) value, sb);
                    sb.append("</" + key + ">");  
                } else {  
                    sb.append("<" + key + ">" + value + "</" + key + ">");  
                }  
  
            }  
  
        }  
    }  
    
    /**
     * 字符填充(数据补位)
     * @param data 待补位数据
     * @param fillData 补位数据
     * @param dataLength 数据总长度
     * @param fillWay 补位方式(1：左补  2：右补)
     * @return 补位后数据
     */
    public static String zerofilling(String data, String fillData, int dataLength, int fillWay) {
        int strLen = data.length();
        if (strLen < dataLength) {
            while (strLen < dataLength) {
                StringBuffer sb = new StringBuffer();
                if(fillWay==1){
                    sb.append(fillData).append(data);//左补
                }else if(fillWay==2){
                    sb.append(data).append(fillData);//右补
                }
                data = sb.toString();
                strLen = data.length();
            }
        }
        return data;
    }
    
    /**
     * 数据比对是否存在
     * @return
     */
    public static boolean dataComparison(List<String> list, String _val){
        if (list.size() == 0) {
            return false;
        }
        boolean confg = false;
        for (String str : list) {
            if (str.equals(_val)) {
                confg = true;
                break;
            }
        }
        return confg;
    }
    
    /**
     * 名称头字母大写
     * @param name
     * @return
     */
    public static String captureName(String name) {
        char[] cs = name.toCharArray();
        cs[0] -= 32;
        return String.valueOf(cs);
    }
    
    /**
     * 比较结果
     * @param first
     * @param second
     * @return
     */
    public static boolean compareRes(Object first, Object second) {
        if(null == first || null == second){
            return false;
        }
        if (first instanceof BigDecimal && second instanceof BigDecimal) {
            BigDecimal bigFirst = (BigDecimal) first;
            BigDecimal bigSec = (BigDecimal) second;
            if (0 == bigFirst.compareTo(bigSec)) {
                return true;
            } else {
                return false;
            }
        } else {
            String strFirst = first.toString();
            String strSec = second.toString();
            if (strFirst.equals(strSec)) {
                return true;
            } else {
                return false;
            }
        }
    }
    
    /**
     * 获取SQL文
     * @param list
     * @param str
     * @return
     */
    public static String fillStringByArgs(List<String> list, String str) {
        Matcher m = Pattern.compile("\\{(\\d)\\}").matcher(str);
        while (m.find()) {
            str = str.replace(m.group(), list.get(Integer.parseInt(m.group(1))));
        }
        return str;
    }
    
	public static void main(String[] args) {
//		System.out.println(get3MiddleShow("浙江惠瀜网络科技有限公司"));
//		System.out.println(getUserNameShow("余勇辉"));
//		System.out.println(get4Show("测试赛帅帅2228大幅度反对法"));
//		System.err.println("压缩前:"+FormetFileSize("8869034")+"\r\n压缩后："+FormetFileSize("1790619"));
//		System.err.println(numToUpper(1));
//		String dkLoan="";
//		for (int i = 0; i < 2; i++) {
//		    dkLoan+="未结清贷款"+StringUtil.numToUpper(i+1)+" ";
//	        dkLoan+="1"+i;
//	        dkLoan+="+"+"23"+i;
//	        dkLoan+="+"+"45"+i;
//	        dkLoan+="+"+"78"+i+";";
//        }
//		String noClearLoan="";
//		noClearLoan+=dkLoan;
//		System.err.println(noClearLoan);
		
//		System.err.println(getBirthdate("330226197903200031"));
		
//		String str = "1.0";
//		
//		System.err.println(str.indexOf(".")>0);
//		
//		
//		String b = str.substring(0,str.length()-2);
//
//		
//		b = StringUtils.leftPad(b, 6, "0");
//		
//		System.err.println(b);
//		String orderNo = "vxurcb20170707151105095";
//		String aa = orderNo.substring(orderNo.length()-8, orderNo.length()-2);
//		System.err.println(aa);
		
//		String spellStr = "";
//		String name = "王者荣耀";
//		String spellCode = "WANG/ZHE/RONG/YAO";
//		int len = name.trim().length();
//		
//		if(len<=2){
//			spellStr = spellCode+"/";
//		}else if(len>3){
//			int idx = spellCode.indexOf("/");
//			String frSpell = spellCode.substring(0, idx);
//			String laSpell = spellCode.substring(idx+1, spellCode.length());
//			spellStr = frSpell.concat(laSpell);
//		}
//		
//		System.err.println("spellStr:"+spellStr);
		
//		String a = "WANG LI JIU";
//		int idx = a.trim().indexOf(" ");
//		String b1 = a.substring(0,idx+1);
//		String b2 = a.substring(idx+1);
//		
//		b2 = b2.replace(" ", "");
//		
//		a = b1.concat(b2);
//		
//		System.err.println(a);
		
		
//		String spellCode = "DAN JIA JUAN JUAN".trim().replace(" ", "/");
//		int len = "但佳娟娟".trim().length();
//		if(len<=2){
//			spellCode = spellCode+"/";
//		}else if(len>3){
//			int idx = spellCode.indexOf("/");
//			String frSpell = spellCode.substring(0, idx);
//			String laSpell = spellCode.substring(idx+1, spellCode.length());
//			spellCode = frSpell.concat(laSpell);
//		}
//		System.err.println(spellCode);
//		
//		String engname = "DAN JIA JUAN CHEN";
//		int idx = engname.trim().indexOf(" ");
//		if(idx>0){
//			String preN =engname.substring(0,idx+1);
//			String fixN = engname.substring(idx+1);
//			fixN = fixN.replace(" ", "");
//			engname = preN.concat(fixN);
//		}
//		engname = engname.toLowerCase();
//		System.err.println(engname);
		System.out.println(restFileName(null));
		
	}


}
