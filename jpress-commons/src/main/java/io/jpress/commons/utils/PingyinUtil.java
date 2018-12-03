/*
 * huirong Inc.
 * Copyright (c) 2016 All Rights Reserved.
 * Author     :liyb
 * Create Date:2016年6月21日
 */
package io.jpress.commons.utils;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

import java.util.ArrayList;
import java.util.List;

/**
 * 字符串拼音
 * 
 * @author liyb
 * @version PingyinUtil.java,2016年6月21日 下午2:24:31
 */
public class PingyinUtil {
	
    private static final HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
	
	static{
		defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
		defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
	}
	
	/**
	 * 提取每个汉字的拼音
	 */
	public static String convert(String word) {
		try {
			return PinyinHelper.toHanYuPinyinString(word, defaultFormat, "", true);
		} catch (BadHanyuPinyinOutputFormatCombination e) {
			throw new RuntimeException("汉语转拼音异常", e);
		}
	}

	/**
	 * 提取每个汉字的首字母
	 */
	public static String getPinYinHeadChar(String str) {
		StringBuilder convert = new StringBuilder();
		for (int j = 0; j < str.length(); j++) {
			char word = str.charAt(j);
			// 提取汉字的首字母
			String[] pinyinArray = PinyinHelper.toHanyuPinyinStringArray(word);
			if (pinyinArray != null) {
				convert.append(pinyinArray[0].charAt(0));
			} else {
				convert.append(word);
			}
		}
		return convert.toString();
	}

    /**
     * 提取每个汉字的拼音
     */
    public static String hanziToPinyin(String word) {
        String convert = "";
        //创建汉语拼音处理类  
        HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
        try {
            //输出设置，大小写，音标方式  
            defaultFormat.setCaseType(HanyuPinyinCaseType.UPPERCASE);
            defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
            defaultFormat.setVCharType(HanyuPinyinVCharType.WITH_V);
            convert = PinyinHelper.toHanYuPinyinString(word, defaultFormat, "", true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return convert;
    }
    
    /**
     * 提取每个汉子的大写拼音字母
     * @param str
     * @param separator 分隔符
     * @return 如：WANG,XING,LIN
     */
    public static String hanziToPinyinChar(String str, String separator){
        String convert = "";
        if(StringUtil.isNotBlank(str)){
        	char[] pinyinArray = str.toCharArray(); 
            for (char hanzi:pinyinArray) { 
                if(StringUtil.isNotEmpty(convert)){
                    convert = convert+separator+PingyinUtil.hanziToPinyin(hanzi+"");
                }else{
                    convert = hanziToPinyin(hanzi+"");
                }
            } 
        }
        return convert;
    }
    
    /**
     * 去除多余空格，返回大写拼音，以逗号隔开
     * @param str
     * @return
     */
    public static String skipEmptyPinyin(String str){
        String convert = "";
        if(StringUtil.isNotBlank(str)){
        	convert = str.replaceAll(" ", ",").toUpperCase();
        	String array[] = convert.split(",");
        	List<String> list = new ArrayList<String>();
        	for (int i = 0; i < array.length; i++) {
        		if(StringUtil.isNotEmpty(array[i])){
        			list.add(array[i]);
        		}
        	}
        	convert = "";
        	for (String py:list) {
        		if(StringUtil.isNotEmpty(convert)){
        			convert = convert+","+py;
        		}else{
        			convert = py;
        		}
        	}
        }
        return convert;
    }
    
    public static void main(String[] args) {
		System.out.println(hanziToPinyin("	周波	").toLowerCase()+"\t:周波	");
		System.out.println(hanziToPinyin("	林敏	").toLowerCase()+"\t:林敏	");
		System.out.println(hanziToPinyin("	余勇辉	").toLowerCase()+"\t:余勇辉	");
		System.out.println(hanziToPinyin("	王亮	").toLowerCase()+"\t:王亮	");
		System.out.println(hanziToPinyin("	李艳波	").toLowerCase()+"\t:李艳波	");
		System.out.println(hanziToPinyin("	李俊	").toLowerCase()+"\t:李俊	");
		System.out.println(hanziToPinyin("	余文侨	").toLowerCase()+"\t:余文侨	");
		System.out.println(hanziToPinyin("	柳亚星	").toLowerCase()+"\t:柳亚星	");
		System.out.println(hanziToPinyin("	杜红燕	").toLowerCase()+"\t:杜红燕	");
		System.out.println(hanziToPinyin("	何倩芸	").toLowerCase()+"\t:何倩芸	");
		System.out.println(hanziToPinyin("	曹鹏程	").toLowerCase()+"\t:曹鹏程	");
		System.out.println(hanziToPinyin("	赵瑾萱	").toLowerCase()+"\t:赵瑾萱	");
		System.out.println(hanziToPinyin("	沈燕飞	").toLowerCase()+"\t:沈燕飞	");
		System.out.println(hanziToPinyin("	任莎莎	").toLowerCase()+"\t:任莎莎	");
		System.out.println(hanziToPinyin("	宋健东	").toLowerCase()+"\t:宋健东	");
		System.out.println(hanziToPinyin("	刘涛	").toLowerCase()+"\t:刘涛	");
		System.out.println(hanziToPinyin("	黄艳萍	").toLowerCase()+"\t:黄艳萍	");
		System.out.println(hanziToPinyin("	刘东风	").toLowerCase()+"\t:刘东风	");
		System.out.println(hanziToPinyin("	冯程恩	").toLowerCase()+"\t:冯程恩	");
		System.out.println(hanziToPinyin("	余铁萍	").toLowerCase()+"\t:余铁萍	");
		System.out.println(hanziToPinyin("	郑杨康	").toLowerCase()+"\t:郑杨康	");
		System.out.println(hanziToPinyin("	陈友敬	").toLowerCase()+"\t:陈友敬	");
		System.out.println(hanziToPinyin("	丁鹏	").toLowerCase()+"\t:丁鹏	");
		System.out.println(hanziToPinyin("	吕照利	").toLowerCase()+"\t:吕照利	");
		System.out.println(hanziToPinyin("	徐鹤燕	").toLowerCase()+"\t:徐鹤燕	");
		System.out.println(hanziToPinyin("	杨敬鑫	").toLowerCase()+"\t:杨敬鑫	");
		System.out.println(hanziToPinyin("	高梦颖	").toLowerCase()+"\t:高梦颖	");
		System.out.println(hanziToPinyin("	曹琴芳	").toLowerCase()+"\t:曹琴芳	");
		System.out.println(hanziToPinyin("	高乐	").toLowerCase()+"\t:高乐	");
		System.out.println(hanziToPinyin("	陈泽齐	").toLowerCase()+"\t:陈泽齐	");
		System.out.println(hanziToPinyin("	况涛	").toLowerCase()+"\t:况涛	");
		System.out.println(hanziToPinyin("	郭飞飞	").toLowerCase()+"\t:郭飞飞	");
		System.out.println(hanziToPinyin("	黄灿霞	").toLowerCase()+"\t:黄灿霞	");
		System.out.println(hanziToPinyin("	施青楠	").toLowerCase()+"\t:施青楠	");
		System.out.println(hanziToPinyin("	王庆歌	").toLowerCase()+"\t:王庆歌	");
		System.out.println(hanziToPinyin("	常庆东	").toLowerCase()+"\t:常庆东	");
		System.out.println(hanziToPinyin("	顾鸿飞	").toLowerCase()+"\t:顾鸿飞	");
		System.out.println(hanziToPinyin("	黄锋	").toLowerCase()+"\t:黄锋	");
		System.out.println(hanziToPinyin("	黄道胜	").toLowerCase()+"\t:黄道胜	");
		System.out.println(hanziToPinyin("	王小亮	").toLowerCase()+"\t:王小亮	");
		System.out.println(hanziToPinyin("	叶茹	").toLowerCase()+"\t:叶茹	");
		System.out.println(hanziToPinyin("	张玉兰	").toLowerCase()+"\t:张玉兰	");
		System.out.println(hanziToPinyin("	胡文莲	").toLowerCase()+"\t:胡文莲	");
		System.out.println(hanziToPinyin("	杨标	").toLowerCase()+"\t:杨标	");
		System.out.println(hanziToPinyin("	陈亚民	").toLowerCase()+"\t:陈亚民	");
		System.out.println(hanziToPinyin("	吴晓阳	").toLowerCase()+"\t:吴晓阳	");
		System.out.println(hanziToPinyin("	俞世奇	").toLowerCase()+"\t:俞世奇	");
		System.out.println(hanziToPinyin("	陈皮伟	").toLowerCase()+"\t:陈皮伟	");
		System.out.println(hanziToPinyin("	吴君裕	").toLowerCase()+"\t:吴君裕	");
		System.out.println(hanziToPinyin("	张翔	").toLowerCase()+"\t:张翔	");
		System.out.println(hanziToPinyin("	江军杰	").toLowerCase()+"\t:江军杰	");
		System.out.println(hanziToPinyin("	吕清毅	").toLowerCase()+"\t:吕清毅	");
		System.out.println(hanziToPinyin("	王峰	").toLowerCase()+"\t:王峰	");
		System.out.println(hanziToPinyin("	李文艺	").toLowerCase()+"\t:李文艺	");
		System.out.println(hanziToPinyin("	刘玲花	").toLowerCase()+"\t:刘玲花	");
		System.out.println(hanziToPinyin("	余晓宇	").toLowerCase()+"\t:余晓宇	");

	}
}
