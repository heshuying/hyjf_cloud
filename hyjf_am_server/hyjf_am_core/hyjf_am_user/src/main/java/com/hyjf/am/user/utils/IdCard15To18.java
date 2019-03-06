package com.hyjf.am.user.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class IdCard15To18 {

    private final static Logger logger = LoggerFactory.getLogger(IdCard15To18.class);
    /**
     * 省、直辖市代码表：
     *     11 : 北京  12 : 天津  13 : 河北   14 : 山西  15 : 内蒙古
     *     21 : 辽宁  22 : 吉林  23 : 黑龙江 31 : 上海  32 : 江苏
     *     33 : 浙江  34 : 安徽  35 : 福建   36 : 江西  37 : 山东
     *     41 : 河南  42 : 湖北  43 : 湖南   44 : 广东  45 : 广西  46 : 海南
     *     50 : 重庆  51 : 四川  52 : 贵州   53 : 云南  54 : 西藏
     *     61 : 陕西  62 : 甘肃  63 : 青海   64 : 宁夏  65 : 新疆
     *     71 : 台湾
     *     81 : 香港  82 : 澳门
     *     91 : 国外
     */
    private final static String CITY_CODE[] = {"11", "12", "13", "14", "15", "21", "22", "23", "31", "32", "33", "34", "35", "36", "37", "41", "42", "43", "44", "45", "46", "50", "51", "52", "53", "54", "61", "62", "63", "64", "65", "71", "81", "82", "91"};

    /**
     * 效验码
     */
    private final static char[] VERIFYCODE = {'1', '0', 'X', '9', '8', '7', '6', '5', '4', '3', '2'};

    /**
     * 加权因子
     * Math.pow(2,  i - 1) % 11
     */
    private final static int[] WI = {7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2};
    /**
     * 身份证前4位对应的城市名称
     * */
    private static Map<String,String> city = new HashMap<>();

    /**
     * 根据15位的身份证号码获得18位身份证号码
     * @param fifteenIDCard 15位的身份证号码
     * @return 升级后的18位身份证号码
     * @throws Exception 如果不是15位的身份证号码，则抛出异常
     */
    public static String getEighteenIDCard(String fifteenIDCard) throws Exception{
        if(fifteenIDCard != null && fifteenIDCard.length() == 15){
            StringBuilder sb = new StringBuilder();
            sb.append(fifteenIDCard.substring(0, 6))
                    .append("19")
                    .append(fifteenIDCard.substring(6));
            sb.append(getVerifyCode(sb.toString()));
            return sb.toString();
        } else {
            throw new Exception("不是15位的身份证");
        }
    }

    /**
     * 获取校验码
     * @param idCardNumber 不带校验位的身份证号码（17位）
     * @return 校验码
     * @throws Exception 如果身份证没有加上19，则抛出异常
     */
    private static char getVerifyCode(String idCardNumber) throws Exception{
        if(idCardNumber == null || idCardNumber.length() < 17) {
            throw new Exception("不合法的身份证号码");
        }
        char[] Ai = idCardNumber.toCharArray();
        int S = 0;
        int Y;
        for(int i = 0; i < WI.length; i++){
            S += (Ai[i] - '0') * WI[i];
        }
        Y = S % 11;
        return VERIFYCODE[Y];
    }

    /**
     * 校验18位的身份证号码的校验位是否正确
     * @param idCardNumber 18位的身份证号码
     * @return
     * @throws Exception
     */
    public static boolean verify(String idCardNumber) throws Exception{
        if(idCardNumber == null || idCardNumber.length() != 18) {
            throw new Exception("不是18位的身份证号码");
        }
        return getVerifyCode(idCardNumber) == idCardNumber.charAt(idCardNumber.length() - 1);
    }

    /**
     * 方法描述： 根据身份证获取年龄
     * @param idNum
     * @return
     */
    public static int getAgeById(String idNum) {
        int age = 0;
        GregorianCalendar calendar = new GregorianCalendar(TimeZone.getDefault());//获取系统当前时间
        int currentYear = calendar.get(Calendar.YEAR);
        if (idNum.matches("^\\d{15}$|^\\d{17}[\\dxX]$")) {
            if (idNum.length() == 18) {
                Pattern pattern = Pattern.compile("\\d{6}(\\d{4})\\d{6}(\\d{1})[\\dxX]{1}");
                Matcher matcher = pattern.matcher(idNum);
                if (matcher.matches()) {
                    age = currentYear - Integer.parseInt(matcher.group(1));
                }
            } else if (idNum.length() == 15) {
                Pattern p = Pattern.compile("\\d{6}(\\d{2})\\d{5}(\\d{1})\\d{1}");
                Matcher m = p.matcher(idNum);
                if (m.matches()) {
                    int year = Integer.parseInt(m.group(1));
                    year = 2000 + year;
                    if (year > 2020) {
                        year = year - 100;
                    }
                    age = currentYear - year;
                }
            }
        }
        return age;
    }
    /**
     * 方法描述: 校验身份证号码是否有效
     * @param id 身份证号码
     * @return
     * */
    public final static boolean isValid(String id){
        if (id == null){
            return false;
        }

        int len = id.length();
        if (len != 15 && len != 18){
            return false;
        }

        //校验区位码
        if (!validCityCode(id.substring(0, 2))){
            return false;
        }
        //校验生日
        if (!validDate(id)){
            return false;
        }
        if (len == 15){
            return true;
        }
        //校验位数
        return validParityBit(id);
    }
    /**
     * 校验身份证位数
     * @param id 身份证号码
     * */
    private static boolean validParityBit(String id){
        char[] cs = id.toUpperCase().toCharArray();
        int power = 0;
        for (int i = 0; i < cs.length; i++){
            //最后一位可以是X
            if (i == cs.length - 1 && cs[i] == 'X') {
                break;
            }
            // 非数字
            if (cs[i] < '0' || cs[i] > '9') {
                return false;
            }
            // 加权求和
            if (i < cs.length - 1){
                power += (cs[i] - '0') * WI[i];
            }
        }
        return VERIFYCODE[power % 11] == cs[cs.length - 1];
    }
    /**
     * 校验身份证生日
     * @param id 身份证号码
     * */
    private static boolean validDate(String id){
        try{
            String birth = id.length() == 15 ? "19" + id.substring(6, 12) : id.substring(6, 14);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            Date birthDate = sdf.parse(birth);
            if (!birth.equals(sdf.format(birthDate))) {
                return false;
            }
        }catch (ParseException e){
            return false;
        }
        return true;
    }
    /**
     * 校验身份证城市码
     * @param cityCode 身份证前4位
     * */
    private static boolean validCityCode(String cityCode){
        for (String code : CITY_CODE){
            if (code.equals(cityCode)) {
                return true;
            }
        }
        return false;
    }
    /**
     * 根据身份证前4位获取对应的城市名称
     * @param code 身份证前4位
     * @return area 身份证前4位对应的城市名称
     * */
    public static String getCityFromCode(String code){
        // 如果map中没有数据，就从city.properties文件中载入一次
        String area = "";
        if(city != null){
            cityInit();
            try {
                area = unicodeToString(city.get(code));
            }catch (Exception e){
                logger.info("根据身份证前6位获取对应的城市名称失败,6位code:[{}]",code);
            }
        }
        return area;
    }

    //字符串转换unicode
    public static String stringToUnicode(String string) {
        StringBuilder unicode = new StringBuilder();
        for (int i = 0; i < string.length(); i++) {
            char c = string.charAt(i);  // 取出每一个字符
            unicode.append("\\u").append(Integer.toHexString(c));// 转换为unicode
        }
        return unicode.toString();
    }

    //unicode 转字符串
    private static String unicodeToString(String unicode) {
        StringBuilder stringBuilder = new StringBuilder();
        String[] hex = unicode.split("\\\\u");
        for (int i = 1; i < hex.length; i++) {
            int data = Integer.parseInt(hex[i], 16);// 转换出每一个代码点
            stringBuilder.append((char) data);// 追加成string
        }
        return stringBuilder.toString();
    }
    private static void cityInit(){
        String area = "";
        if(city.size() == 0){
            try {
                Properties properties = new Properties();
                InputStream inputStream = IdCard15To18.class.getClassLoader().getResourceAsStream("city.properties");
                properties.load(inputStream);
                String json = properties.toString();
                area = json.substring(1, json.length() -1);
                if(!"".equals(area)){
                    String[] text = area.split(";");
                    for(String str:text){
                        String[] keyValue = str.split("=");
                        if (keyValue.length < 1) {
                            continue;
                        }
                        String key = keyValue[0]; // key
                        String value = keyValue[1]; // value
                        city.put(key,value);
                    }
                }
            } catch (IOException e) {
                logger.error(e.getMessage());
            }
        }
    }
}