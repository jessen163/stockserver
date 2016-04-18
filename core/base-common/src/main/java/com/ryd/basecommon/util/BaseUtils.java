package com.ryd.basecommon.util;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.*;
import java.util.Map.Entry;

/**
 * 基础操作类
 */
public class BaseUtils {
    /**
     * Construct a map with key-value pairs.
     *
     * @param objects key-value pair array like {"key1", value1, "key2", value2...}
     *
     * @return map
     */
    public static Map<String, Object> toMap(Object... objects) {
        Map<String, Object> map = new HashMap<String, Object>();

        for (int i = 0; i < objects.length; i++) {
            map.put((String) objects[i], objects[i + 1]);
            i++;
        }
        return map;
    }

    /**
     * Create a Object-Object map
     *
     * @param objects key-value pair array like {key1, value1, key2, value2...}
     *
     * @return map
     */
    public static Map<Object, Object> toObjectMap(Object... objects) {
        Map<Object, Object> map = new HashMap<Object, Object>();

        for (int i = 0; i < objects.length; i++) {
            map.put(objects[i], objects[i + 1]);
            i++;
        }
        return map;
    }

    /**
     * 获取配置文件的信息
     *
     * @param propName
     *
     * @return
     */
    public final static Properties getProperties(String propName) {
        return getProperties(propName, null);
    }

    public final static String getProValByKey(String propName, String key) {
        Properties prop = getProperties(propName);
        return prop.getProperty(key);
    }

    public static String getProValByKey(String propName, String key, String _default) {
        Properties prop = getProperties(propName);
        if (StringUtils.isNotBlank(prop.getProperty(key))) {
            return prop.getProperty(key);
        }
        return _default;
    }

    /**
     * 获取配置文件的信息
     *
     * @param propName
     *
     * @return
     */
    public final static Properties getProperties(String propName, String charset) {

        //获取配置文件的默认路径
        String configPath = System.getProperty("configuration.path");
        String propPath = configPath + File.separator + propName;
        File propFile = new File(propPath);

        //如果不存在则取当前目录
        if (!propFile.isFile()) {
            String path = BaseUtils.class.getResource("/").getPath().replaceAll("%20", " ");
            propFile = new File(path, propName);
        }
        //读取配置文件
        InputStream is = null;
        Reader r = null;
        try {

                //如果没有
                if (propFile.isFile()) {
                    is = new FileInputStream(propFile);
                } else {
                    is = BaseUtils.class.getClassLoader().getResourceAsStream(propName);
                }

            Properties prop = new Properties();
            if (charset == null) {
                prop.load(is);
            } else {
                r = new InputStreamReader(is, charset);
                prop.load(r);
            }
            return prop;
        } catch (Exception e) {
        } finally {
            FileUtils.closeReader(r);
            FileUtils.closeInputStream(is);
        }
        return new Properties();
    }

    public static <T> List<T> toList(T... items) {
        return Arrays.asList(items);
    }

    /**
     * 将字符串编码成 Unicode 。
     *
     * @param theString   待转换成Unicode编码的字符串。
     * @param escapeSpace 是否忽略空格。
     *
     * @return 返回转换后Unicode编码的字符串。
     */
    public final static String toUnicode(String theString, boolean escapeSpace) {
        int len = theString.length();
        int bufLen = len * 2;
        if (bufLen < 0) {
            bufLen = Integer.MAX_VALUE;
        }
        StringBuffer outBuffer = new StringBuffer(bufLen);

        for (int x = 0; x < len; x++) {
            char aChar = theString.charAt(x);
            // Handle common case first, selecting largest block that
            // avoids the specials below
            if ((aChar > 61) && (aChar < 127)) {
                if (aChar == '\\') {
                    outBuffer.append('\\');
                    outBuffer.append('\\');
                    continue;
                }
                outBuffer.append(aChar);
                continue;
            }
            switch (aChar) {
                case ' ':
                    if (x == 0 || escapeSpace)
                        outBuffer.append('\\');
                    outBuffer.append(' ');
                    break;
                case '\t':
                    outBuffer.append('\\');
                    outBuffer.append('t');
                    break;
                case '\n':
                    outBuffer.append('\\');
                    outBuffer.append('n');
                    break;
                case '\r':
                    outBuffer.append('\\');
                    outBuffer.append('r');
                    break;
                case '\f':
                    outBuffer.append('\\');
                    outBuffer.append('f');
                    break;
                case '=': // Fall through
                case ':': // Fall through
                case '#': // Fall through
                case '!':
                    outBuffer.append('\\');
                    outBuffer.append(aChar);
                    break;
                default:
                    if ((aChar < 0x0020) || (aChar > 0x007e)) {
                        outBuffer.append('\\');
                        outBuffer.append('u');
                        outBuffer.append(toHex((aChar >> 12) & 0xF));
                        outBuffer.append(toHex((aChar >> 8) & 0xF));
                        outBuffer.append(toHex((aChar >> 4) & 0xF));
                        outBuffer.append(toHex(aChar & 0xF));
                    } else {
                        outBuffer.append(aChar);
                    }
            }
        }
        return outBuffer.toString();
    }

    private static char toHex(int nibble) {
        return hexDigit[(nibble & 0xF)];
    }

    public final static String fromUnicode(String str, boolean flog) {
        if (!flog) {
            StringBuilder sbl = new StringBuilder("");
            for (int i = 0; i < str.length() - 3; i += 4) {
                sbl.append("\\u").append(str.substring(i, i + 4));
            }
            str = sbl.toString();
        }
        return fromUnicode(str.toCharArray(), 0, str.length(), new char[1024]);
    }

    /**
     * @param in       Unicode编码的字符数组。
     * @param off      转换的起始偏移量。
     * @param len      转换的字符长度。
     * @param convtBuf 转换的缓存字符数组。
     *
     * @return 完成转换，返回编码前的特殊字符串。
     */
    private static String fromUnicode(char[] in, int off, int len, char[] convtBuf) {
        if (convtBuf.length < len) {
            int newLen = len * 2;
            if (newLen < 0) {
                newLen = Integer.MAX_VALUE;
            }
            convtBuf = new char[newLen];
        }
        char aChar;
        char[] out = convtBuf;
        int outLen = 0;
        int end = off + len;

        while (off < end) {
            aChar = in[off++];
            if (aChar == '\\') {
                aChar = in[off++];
                if (aChar == 'u') {
                    // Read the xxxx
                    int value = 0;
                    for (int i = 0; i < 4; i++) {
                        aChar = in[off++];
                        switch (aChar) {
                            case '0':
                            case '1':
                            case '2':
                            case '3':
                            case '4':
                            case '5':
                            case '6':
                            case '7':
                            case '8':
                            case '9':
                                value = (value << 4) + aChar - '0';
                                break;
                            case 'a':
                            case 'b':
                            case 'c':
                            case 'd':
                            case 'e':
                            case 'f':
                                value = (value << 4) + 10 + aChar - 'a';
                                break;
                            case 'A':
                            case 'B':
                            case 'C':
                            case 'D':
                            case 'E':
                            case 'F':
                                value = (value << 4) + 10 + aChar - 'A';
                                break;
                            default:
                                throw new IllegalArgumentException(
                                        "Malformed \\uxxxx encoding.");
                        }
                    }
                    out[outLen++] = (char) value;
                } else {
                    if (aChar == 't') {
                        aChar = '\t';
                    } else if (aChar == 'r') {
                        aChar = '\r';
                    } else if (aChar == 'n') {
                        aChar = '\n';
                    } else if (aChar == 'f') {
                        aChar = '\f';
                    }
                    out[outLen++] = aChar;
                }
            } else {
                out[outLen++] = (char) aChar;
            }
        }
        return new String(out, 0, outLen);
    }

    private static final char[] hexDigit = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

    static Map<String, Integer> osMap = new HashMap<String, Integer>();
    static Map<Integer, String> sdkMap = new HashMap<Integer, String>();
    static Properties props = null;
    static{
        osMap.put("1.0", 1);
        osMap.put("1.1", 2);
        osMap.put("1.5", 3);
        osMap.put("1.6", 4);
        osMap.put("2.0", 5);
        osMap.put("2.0.1", 6);
        osMap.put("2.1", 7);
        osMap.put("2.2", 8);
        osMap.put("2.3.1", 9);
        osMap.put("2.3.3", 10);
        osMap.put("3.0", 11);
        osMap.put("3.1", 12);

        for(Entry<String, Integer> entry : osMap.entrySet()){
            Integer minSdk = entry.getValue();
            String osInfo = entry.getKey();
            sdkMap.put(minSdk, osInfo);
        }

        props = getProperties("mario.properties");
    }

    /**
     * 根据minsdk值获取osinfo
     *
     * @param minSdk
     * @return
     */
    public final static String getOsInfo(Integer minSdk){
        return sdkMap.get(minSdk);
    }

    /**
     * 根据osinfo值获取minsdk
     *
     * @param osInfo
     * @return
     */
    public final static Integer getMinSdk(String osInfo){
        return osMap.get(osInfo);
    }

    /**
     * 获取Page Icon 宽度配置
     * @return
     */
    public final static int getPageIconWidth(){
        return Integer.valueOf(props.getProperty("page.icon.width", "123"));
    }

    /**
     * 获取Page Icon 高度配置
     * @return
     */
    public final static int getPageIconHeight(){
        return Integer.valueOf(props.getProperty("page.icon.height", "91"));
    }

    /**
     * 获取Icon的宽度配置
     *
     * @return
     */
    public final static int getIconWidth(){
        return Integer.valueOf(props.getProperty("icon.width", "510"));
    }

    /**
     * 获取中图的宽度配置
     *
     * @return
     */
    public final static int getMidPicWidth(){
        return Integer.valueOf(props.getProperty("midPic.width", "510"));
    }

    /**
     * 获取用户Icon的宽度配置
     *
     * @return
     */
    public final static int getUserIconWidth(){
        return Integer.valueOf(props.getProperty("userIcon.width", "100"));
    }

    /**
     * 获取用户中图的高度配置
     *
     * @return
     */
    public final static int getUserMidPicHeight(){
        return Integer.valueOf(props.getProperty("userMidPic.height", "400"));
    }

    /**
     * 获取用户中图的宽度配置
     *
     * @return
     */
    public final static int getUserMidPicWidth(){
        return Integer.valueOf(props.getProperty("userMidPic.width", "400"));
    }

    /**
     * 获取中图的高度配置
     *
     * @return
     */
    public final static int getMidPicHeight(){
        return Integer.valueOf(props.getProperty("midPic.height", "510"));
    }

    /**
     * 获取图片压缩的质量
     * @return
     */
    public static float getImageQuality() {
        return Float.valueOf(props.getProperty("image.quality", "0.5"));
    }

    /**
     * 获取图片压缩的质量(用户icon)
     * @return
     */
    public static float getUserImageQuality() {
        return Float.valueOf(props.getProperty("userImage.quality", "0.25"));
    }

    public static List<String> str2List(String strDate) {
        List<String> strings = new ArrayList<String>();
        if(StringUtils.isNotBlank(strDate)) {
            String[] strs = strDate.split(",");
            for (String s : strs) {
                strings.add(s);
            }
        }
        return strings;
    }

    public static String list2str(List<String> list) {
        if(CollectionUtils.isNotEmpty(list)) {
            boolean flag=false;
            StringBuilder result=new StringBuilder();
            for (String cul : list) {
                if (flag) {
                    result.append(";");
                }else {
                    flag=true;
                }
                result.append(cul);
            }
            return result.toString();
        }
        return "";
    }

    public static List<String> judgmentList(List<String> before){
        List<String> returnDTOs = new ArrayList<String>();
        if (CollectionUtils.isNotEmpty(before)) {
            for (String str : before) {
                returnDTOs.add(str);
            }
        }
        return returnDTOs;
    }

    public static void main(String[] args) {
    }
}
