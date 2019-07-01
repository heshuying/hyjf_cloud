/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.common.validator;

import com.hyjf.common.util.CharPool;
import com.hyjf.common.util.StringPool;
import com.hyjf.common.util.StringUtil;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author fuqiang
 * @version Validator, v0.1 2018/4/11 23:00
 */
public class Validator {

    /**
     * Returns <code>true</code> if the booleans are equal.
     *
     * @param boolean1 the first boolean
     * @param boolean2 the second boolean
     * @return <code>true</code> if the booleans are equal; <code>false</code>
     * otherwise
     */
    public static boolean equals(boolean boolean1, boolean boolean2) {
        if (boolean1 == boolean2) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Returns <code>true</code> if the bytes are equal.
     *
     * @param byte1 the first byte
     * @param byte2 the second byte
     * @return <code>true</code> if the bytes are equal; <code>false</code>
     * otherwise
     */
    public static boolean equals(byte byte1, byte byte2) {
        if (byte1 == byte2) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Returns <code>true</code> if the characters are equal.
     *
     * @param char1 the first character
     * @param char2 the second character
     * @return <code>true</code> if the characters are equal; <code>false</code>
     * otherwise
     */
    public static boolean equals(char char1, char char2) {
        if (char1 == char2) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Returns <code>true</code> if the doubles are equal.
     *
     * @param double1 the first double
     * @param double2 the second double
     * @return <code>true</code> if the doubles are equal; <code>false</code>
     * otherwise
     */
    public static boolean equals(double double1, double double2) {
        if (Double.compare(double1, double2) == 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Returns <code>true</code> if the floats are equal.
     *
     * @param float1 the first float
     * @param float2 the second float
     * @return <code>true</code> if the floats are equal; <code>false</code>
     * otherwise
     */
    public static boolean equals(float float1, float float2) {
        if (Float.compare(float1, float2) == 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Returns <code>true</code> if the integers are equal.
     *
     * @param int1 the first integer
     * @param int2 the second integer
     * @return <code>true</code> if the integers are equal; <code>false</code>
     * otherwise
     */
    public static boolean equals(int int1, int int2) {
        if (int1 == int2) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Returns <code>true</code> if the long integers are equal.
     *
     * @param long1 the first long integer
     * @param long2 the second long integer
     * @return <code>true</code> if the long integers are equal;
     * <code>false</code> otherwise
     */
    public static boolean equals(long long1, long long2) {
        if (long1 == long2) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Returns <code>true</code> if the objects are either equal, the same
     * instance, or both <code>null</code>.
     *
     * @param obj1 the first object
     * @param obj2 the second object
     * @return <code>true</code> if the objects are either equal, the same
     * instance, or both <code>null</code>; <code>false</code> otherwise
     */
    public static boolean equals(Object obj1, Object obj2) {
        if ((obj1 == null) && (obj2 == null)) {
            return true;
        } else if ((obj1 == null) || (obj2 == null)) {
            return false;
        } else {
            return obj1.equals(obj2);
        }
    }

    /**
     * Returns <code>true</code> if the short integers are equal.
     *
     * @param short1 the first short integer
     * @param short2 the second short integer
     * @return <code>true</code> if the short integers are equal;
     * <code>false</code> otherwise
     */
    public static boolean equals(short short1, short short2) {
        if (short1 == short2) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Returns <code>true</code> if the string is an email address. The only
     * requirements are that the string consist of two parts separated by an @
     * symbol, and that it contain no whitespace.
     *
     * @param address the string to check
     * @return <code>true</code> if the string is an email address;
     * <code>false</code> otherwise
     */
    public static boolean isAddress(String address) {
        if (isNull(address)) {
            return false;
        }

        String[] tokens = address.split(StringPool.AT);

        if (tokens.length != 2) {
            return false;
        }

        for (String token : tokens) {
            for (char c : token.toCharArray()) {
                if (Character.isWhitespace(c)) {
                    return false;
                }
            }
        }

        return true;
    }

    public static boolean isUrl2(String url) {
        if (isNull(url)) {
            return false;
        }
        Matcher matcher = _urlPattern.matcher(url);

        return matcher.matches();
    }

    /**
     * Returns <code>true</code> if the string is an alphanumeric name, meaning
     * it contains nothing but English letters, numbers, and spaces.
     *
     * @param name the string to check
     * @return <code>true</code> if the string is an Alphanumeric name;
     * <code>false</code> otherwise
     */
    public static boolean isAlphanumericName(String name) {
        if (isNull(name)) {
            return false;
        }

        for (char c : name.trim().toCharArray()) {
            if (!isChar(c) && !isDigit(c) && !Character.isWhitespace(c)) {
                return false;
            }
        }

        return true;
    }

    /**
     * Returns <code>true</code> if the character is in the ASCII character set.
     * This includes characters with integer values between 32 and 126
     * (inclusive).
     *
     * @param c the character to check
     * @return <code>true</code> if the character is in the ASCII character set;
     * <code>false</code> otherwise
     */
    public static boolean isAscii(char c) {
        int i = c;

        if ((i >= 32) && (i <= 126)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Returns <code>true</code> if the character is an upper or lower case
     * English letter.
     *
     * @param c the character to check
     * @return <code>true</code> if the character is an upper or lower case
     * English letter; <code>false</code> otherwise
     */
    public static boolean isChar(char c) {
        int x = c;

        if (((x >= _CHAR_LOWER_CASE_BEGIN) && (x <= _CHAR_LOWER_CASE_END)) || ((x >= _CHAR_UPPER_CASE_BEGIN) && (x <= _CHAR_UPPER_CASE_END))) {

            return true;
        }

        return false;
    }

    /**
     * Returns <code>true</code> if string consists only of upper and lower case
     * English letters.
     *
     * @param s the string to check
     * @return <code>true</code> if the string consists only of upper and lower
     * case English letters
     */
    public static boolean isChar(String s) {
        if (isNull(s)) {
            return false;
        }

        for (char c : s.toCharArray()) {
            if (!isChar(c)) {
                return false;
            }
        }

        return true;
    }

    /**
     * Returns <code>true</code> if the date is valid in the Gregorian calendar.
     *
     * @param month the month to check
     * @param day   the day to check
     * @return <code>true</code> if the date is valid in the Gregorian calendar;
     * <code>false</code> otherwise
     */
    public static boolean isDate(int month, int day, int year) {
        return isGregorianDate(month, day, year);
    }

    /**
     * Returns <code>true</code> if the character is a digit between 0 and 9
     * (inclusive).
     *
     * @param c the character to check
     * @return <code>true</code> if the character is a digit between 0 and 9
     * (inclusive); <code>false</code> otherwise
     */
    public static boolean isDigit(char c) {
        int x = c;

        if ((x >= _DIGIT_BEGIN) && (x <= _DIGIT_END)) {
            return true;
        }

        return false;
    }

    /**
     * Returns <code>true</code> if the string consists of only digits between 0
     * and 9 (inclusive).
     *
     * @param s the string to check
     * @return <code>true</code> if the string consists of only digits between 0
     * and 9 (inclusive); <code>false</code> otherwise
     */
    public static boolean isDigit(String s) {
        if (isNull(s)) {
            return false;
        }

        for (char c : s.toCharArray()) {
            if (!isDigit(c)) {
                return false;
            }
        }

        return true;
    }

    /**
     * Returns <code>true</code> if the string is a valid domain name. See
     * RFC-1034 (section 3), RFC-1123 (section 2.1), and RFC-952 (section B.
     * Lexical grammar).
     *
     * @param domainName the string to check
     * @return <code>true</code> if the string is a valid domain name;
     * <code>false</code> otherwise
     */
    public static boolean isDomain(String domainName) {

        // See RFC-1034 (section 3), RFC-1123 (section 2.1), and RFC-952
        // (section B. Lexical grammar)

        if (isNull(domainName)) {
            return false;
        }

        if (domainName.length() > 255) {
            return false;
        }

        if (domainName.startsWith(StringPool.PERIOD) || domainName.endsWith(StringPool.PERIOD)) {

            return false;
        }

        if (!domainName.contains(StringPool.PERIOD) && !domainName.equals(_LOCALHOST)) {

            return false;
        }

        String[] domainNameArray = StringUtils.split(domainName, CharPool.PERIOD);

        for (String domainNamePart : domainNameArray) {
            char[] domainNamePartCharArray = domainNamePart.toCharArray();

            for (int i = 0; i < domainNamePartCharArray.length; i++) {
                char c = domainNamePartCharArray[i];

                if ((i == 0) && (c == CharPool.DASH)) {
                    return false;
                }

                if ((i == (domainNamePartCharArray.length - 1)) && (c == CharPool.DASH)) {

                    return false;
                }

                if ((!isChar(c)) && (!isDigit(c)) && (c != CharPool.DASH)) {
                    return false;
                }
            }
        }

        return true;
    }

    /**
     * Returns <code>true</code> if the string is a valid email address.
     *
     * @param emailAddress the string to check
     * @return <code>true</code> if the string is a valid email address;
     * <code>false</code> otherwise
     */
    public static boolean isEmailAddress(String emailAddress) {
        Matcher matcher = _emailAddressPattern.matcher(emailAddress);

        return matcher.matches();
    }

    /**
     * Returns <code>true</code> if the character is a special character in an
     * email address.
     *
     * @param c the character to check
     * @return <code>true</code> if the character is a special character in an
     * email address; <code>false</code> otherwise
     */
    public static boolean isEmailAddressSpecialChar(char c) {

        // LEP-1445

        for (int i = 0; i < _EMAIL_ADDRESS_SPECIAL_CHAR.length; i++) {
            if (c == _EMAIL_ADDRESS_SPECIAL_CHAR[i]) {
                return true;
            }
        }

        return false;
    }

    /**
     * Returns <code>true</code> if the date is valid in the Gregorian calendar.
     *
     * @param month the month (0-based, meaning 0 for January)
     * @param day   the day of the month
     * @param year  the year
     * @return <code>true</code> if the date is valid; <code>false</code>
     * otherwise
     */
    public static boolean isGregorianDate(int month, int day, int year) {
        if ((month < 0) || (month > 11)) {
            return false;
        }

        int[] months = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};

        if (month == 1) {
            int febMax = 28;

            if (((year % 4) == 0) && ((year % 100) != 0) || ((year % 400) == 0)) {

                febMax = 29;
            }

            if ((day < 1) || (day > febMax)) {
                return false;
            }
        } else if ((day < 1) || (day > months[month])) {
            return false;
        }

        return true;
    }

    /**
     * Returns <code>true</code> if the string is a hexidecimal number. At
     * present the only requirement is that the string is not <code>null</code>;
     * it does not actually check the format of the string.
     *
     * @param s the string to check
     * @return <code>true</code> if the string is a hexidecimal number;
     * <code>false</code> otherwise
     * @see #isNull(String)
     */
    public static boolean isHex(String s) {
        if (isNull(s)) {
            return false;
        }

        return true;
    }

    /**
     * Returns <code>true</code> if the string is a valid host name.
     *
     * @param name the string to check
     * @return <code>true</code> if the string is a valid host name;
     * <code>false</code> otherwise
     */
    public static boolean isHostName(String name) {
        if (isNull(name)) {
            return false;
        }

        char[] nameCharArray = name.toCharArray();

        if ((nameCharArray[0] == CharPool.DASH) || (nameCharArray[0] == CharPool.PERIOD) || (nameCharArray[nameCharArray.length - 1] == CharPool.DASH)) {

            return false;
        }

        for (char c : nameCharArray) {
            if (!isChar(c) && !isDigit(c) && (c != CharPool.DASH) && (c != CharPool.PERIOD)) {

                return false;
            }
        }

        return true;
    }

    /**
     * Returns <code>true</code> if the string is an HTML document. The only
     * requirement is that it contain the opening and closing html tags.
     *
     * @param s the string to check
     * @return <code>true</code> if the string is an HTML document;
     * <code>false</code> otherwise
     */
    public static boolean isHTML(String s) {
        if (isNull(s)) {
            return false;
        }

        if (((s.indexOf("<html>") != -1) || (s.indexOf("<HTML>") != -1)) && ((s.indexOf("</html>") != -1) || (s.indexOf("</HTML>") != -1))) {

            return true;
        }

        return false;
    }

    /**
     * Returns <code>true</code> if the string is a valid IPv4 IP address.
     *
     * @param ipAddress the string to check
     * @return <code>true</code> if the string is an IPv4 IP address;
     * <code>false</code> otherwise
     */
    public static boolean isIPAddress(String ipAddress) {
        Matcher matcher = _ipAddressPattern.matcher(ipAddress);

        return matcher.matches();
    }

    /**
     * Returns <code>true</code> if the date is valid in the Julian calendar.
     *
     * @param month the month (0-based, meaning 0 for January)
     * @param day   the day of the month
     * @param year  the year
     * @return <code>true</code> if the date is valid; <code>false</code>
     * otherwise
     */
    public static boolean isJulianDate(int month, int day, int year) {
        if ((month < 0) || (month > 11)) {
            return false;
        }

        int[] months = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};

        if (month == 1) {
            int febMax = 28;

            if ((year % 4) == 0) {
                febMax = 29;
            }

            if ((day < 1) || (day > febMax)) {
                return false;
            }
        } else if ((day < 1) || (day > months[month])) {
            return false;
        }

        return true;
    }

    /**
     * Returns <code>true</code> if the string contains a valid number according
     * to the Luhn algorithm, commonly used to validate credit card numbers.
     *
     * @param number the string to check
     * @return <code>true</code> if the string contains a valid number according
     * to the Luhn algorithm; <code>false</code> otherwise
     */
    public static boolean isLUHN(String number) {
        if (number == null) {
            return false;
        }

        number = StringUtils.reverse(number);

        int total = 0;

        for (int i = 0; i < number.length(); i++) {
            int x = 0;

            if (((i + 1) % 2) == 0) {
                x = Integer.parseInt(number.substring(i, i + 1)) * 2;

                if (x >= 10) {
                    String s = String.valueOf(x);

                    x = Integer.parseInt(s.substring(0, 1)) + Integer.parseInt(s.substring(1, 2));
                }
            } else {
                x = Integer.parseInt(number.substring(i, i + 1));
            }

            total = total + x;
        }

        if ((total % 10) == 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Returns <code>true</code> if the string is a name, meaning it contains
     * nothing but English letters and spaces.
     *
     * @param name the string to check
     * @return <code>true</code> if the string is a name; <code>false</code>
     * otherwise
     */
    public static boolean isName(String name) {
        if (isNull(name)) {
            return false;
        }

        for (char c : name.trim().toCharArray()) {
            if (!isChar(c) && !Character.isWhitespace(c)) {
                return false;
            }
        }

        return true;
    }

    /**
     * Returns <code>true</code> if the long number object is not
     * <code>null</code>, meaning it is neither a <code>null</code> reference or
     * zero.
     *
     * @param l the long number object to check
     * @return <code>true</code> if the long number object is not
     * <code>null</code>; <code>false</code> otherwise
     */
    public static boolean isNotNull(Long l) {
        return !isNull(l);
    }

    /**
     * Returns <code>true</code> if the object is not <code>null</code>, using
     * the rules from {@link #isNotNull(Long)} or {@link #isNotNull(String)} if
     * the object is one of these types.
     *
     * @param obj the object to check
     * @return <code>true</code> if the object is not <code>null</code>;
     * <code>false</code> otherwise
     */
    public static boolean isNotNull(Object obj) {
        return !isNull(obj);
    }

    /**
     * Returns <code>true</code> if the array is not <code>null</code>, meaning
     * it is neither a <code>null</code> reference or empty.
     *
     * @param array the array to check
     * @return <code>true</code> if the array is not <code>null</code>;
     * <code>false</code> otherwise
     */
    public static boolean isNotNull(Object[] array) {
        return !isNull(array);
    }

    /**
     * Returns <code>true</code> if the string is not <code>null</code>, meaning
     * it is not a <code>null</code> reference, nothing but spaces, or the
     * string "<code>null</code>".
     *
     * @param s the string to check
     * @return <code>true</code> if the string is not <code>null</code>;
     * <code>false</code> otherwise
     */
    public static boolean isNotNull(String s) {
        return !isNull(s);
    }

    /**
     * Returns <code>true</code> if the long number object is <code>null</code>,
     * meaning it is either a <code>null</code> reference or zero.
     *
     * @param l the long number object to check
     * @return <code>true</code> if the long number object is <code>null</code>;
     * <code>false</code> otherwise
     */
    public static boolean isNull(Long l) {
        if ((l == null) || (l.longValue() == 0)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Returns <code>true</code> if the object is <code>null</code>, using the
     * rules from {@link #isNull(Long)} or {@link #isNull(String)} if the object
     * is one of these types.
     *
     * @param obj the object to check
     * @return <code>true</code> if the object is <code>null</code>;
     * <code>false</code> otherwise
     */
    public static boolean isNull(Object obj) {
        if (obj instanceof Long) {
            return isNull((Long) obj);
        } else if (obj instanceof String) {
            return isNull((String) obj);
        } else if (obj == null) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Returns <code>true</code> if the array is <code>null</code>, meaning it
     * is either a <code>null</code> reference or empty.
     *
     * @param array the array to check
     * @return <code>true</code> if the array is <code>null</code>;
     * <code>false</code> otherwise
     */
    public static boolean isNull(Object[] array) {
        if ((array == null) || (array.length == 0)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Returns <code>true</code> if the string is <code>null</code>, meaning it
     * is a <code>null</code> reference, nothing but spaces, or the string
     * "<code>null</code>".
     *
     * @param s the string to check
     * @return <code>true</code> if the string is <code>null</code>;
     * <code>false</code> otherwise
     */
    public static boolean isNull(String s) {
        if (s == null) {
            return true;
        }

        int counter = 0;

        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);

            if (c == CharPool.SPACE) {
                continue;
            } else if (counter > 3) {
                return false;
            }

            if (counter == 0) {
                if (c != CharPool.LOWER_CASE_N) {
                    return false;
                }
            } else if (counter == 1) {
                if (c != CharPool.LOWER_CASE_U) {
                    return false;
                }
            } else if ((counter == 2) || (counter == 3)) {
                if (c != CharPool.LOWER_CASE_L) {
                    return false;
                }
            }

            counter++;
        }

        if ((counter == 0) || (counter == 4)) {
            return true;
        }

        return false;
    }

    /**
     * Returns <code>true</code> if the string is a decimal integer number,
     * meaning it contains nothing but decimal digits.
     *
     * @param number the string to check
     * @return <code>true</code> if the string is a decimal integer number;
     * <code>false</code> otherwise
     */
    public static boolean isNumber(String number) {
        if (isNull(number)) {
            return false;
        }

        for (char c : number.toCharArray()) {
            if (!isDigit(c)) {
                return false;
            }
        }

        return true;
    }

    /**
     * Returns <code>true</code> if the string is a valid password, meaning it
     * is at least four characters long and contains only letters and decimal
     * digits.
     *
     * @return <code>true</code> if the string is a valid password;
     * <code>false</code> otherwise
     */
    public static boolean isPassword(String password) {
        if (isNull(password)) {
            return false;
        }

        if (password.length() < 4) {
            return false;
        }

        for (char c : password.toCharArray()) {
            if (!isChar(c) && !isDigit(c)) {
                return false;
            }
        }

        return true;
    }

    /**
     * Returns <code>true</code> if the string is a valid phone number. The only
     * requirement is that there are decimal digits in the string; length and
     * format are not checked.
     *
     * @param phoneNumber the string to check
     * @return <code>true</code> if the string is a valid phone number;
     * <code>false</code> otherwise
     */
    public static boolean isPhoneNumber(String phoneNumber) {
        return isNumber(StringUtil.extractDigits(phoneNumber));
    }

    /**
     * Returns <code>true</code> if the string is a valid URL based on the rules
     * in {@link URL}.
     *
     * @param url the string to check
     * @return <code>true</code> if the string is a valid URL;
     * <code>false</code> otherwise
     */
    public static boolean isUrl(String url) {
        if (Validator.isNotNull(url)) {
            if (url.indexOf(CharPool.COLON) == -1) {
                return false;
            }

            try {
                new URL(url);

                return true;
            } catch (MalformedURLException murle) {
            }
        }

        return false;
    }

    /**
     * Returns <code>true</code> if the string is a valid variable name in Java.
     *
     * @param variableName the string to check
     * @return <code>true</code> if the string is a valid variable name in Java;
     * <code>false</code> otherwise
     */
    public static boolean isVariableName(String variableName) {
        if (isNull(variableName)) {
            return false;
        }

        Matcher matcher = _variableNamePattern.matcher(variableName);

        if (matcher.matches()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Returns <code>true</code> if the string is a valid variable term, meaning
     * it begins with "[$" and ends with "$]".
     *
     * @param s the string to check
     * @return <code>true</code> if the string is a valid variable term;
     * <code>false</code> otherwise
     */
    public static boolean isVariableTerm(String s) {
        if (s.startsWith(_VARIABLE_TERM_BEGIN) && s.endsWith(_VARIABLE_TERM_END)) {

            return true;
        } else {
            return false;
        }
    }

    /**
     * Returns <code>true</code> if the character is whitespace, meaning it is
     * either the <code>null</code> character '0' or whitespace according to
     * {@link Character#isWhitespace(char)}.
     *
     * @param c the character to check
     * @return <code>true</code> if the character is whitespace;
     * <code>false</code> otherwise
     */
    public static boolean isWhitespace(char c) {
        int i = c;

        if ((i == 0) || Character.isWhitespace(c)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Returns <code>true</code> if the string is an XML document. The only
     * requirement is that it contain either the xml start tag "<?xml" or the
     * empty document tag "<root />".
     *
     * @param s the string to check
     * @return <code>true</code> if the string is an XML document;
     * <code>false</code> otherwise
     */
    public static boolean isXml(String s) {
        if (s.startsWith(_XML_BEGIN) || s.startsWith(_XML_EMPTY)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 手机号验证
     *
     * @param str
     * @return 验证通过返回true
     */
    public static boolean isMobile(String str) {
        Pattern p = null;
        Matcher m = null;
        boolean b = false;
        p = Pattern.compile("^[1][3,4,5,6,7,8,9][0-9]{9}$");
        m = p.matcher(str);
        b = m.matches();
        return b;
    }

    /**
     * 我国公民的身份证号码特点如下
     * 1.长度18位
     * 2.第1-17号只能为数字
     * 3.第18位只能是数字或者x
     * 4.第7-14位表示特有人的年月日信息
     * 请实现身份证号码合法性判断的函数，函数返回值：
     * 1.如果身份证合法返回0
     * 2.如果身份证长度不合法返回1
     * 3.如果第1-17位含有非数字的字符返回2
     * 4.如果第18位不是数字也不是x返回3
     * 5.如果身份证号的出生日期非法返回4
     *
     * @param id
     * @return boolean
     * @author Zha Daojian
     * @date 2018/9/20 11:53
     **/
    public static boolean isIdcard(String id) {
        String isIDCard2 = "^[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])((\\d{4})|\\d{3}[A-Z])$";
        Pattern pattern = Pattern.compile(isIDCard2);
        return pattern.matcher(id).matches();
    }

    /**
     * 是否为产品加息
     *
     * @param increaseFlag
     * @param borrowExtraYield
     * @return
     * @author sunss
     */
    public static boolean isIncrease(Integer increaseFlag, BigDecimal borrowExtraYield) {
        if (increaseFlag == null || increaseFlag.intValue() == 0) {
            return false;
        }
        if (borrowExtraYield == null || borrowExtraYield.compareTo(BigDecimal.ZERO) <= 0) {
            return false;
        }
        return true;
    }

    private static final int _CHAR_LOWER_CASE_BEGIN = 97;

    private static final int _CHAR_LOWER_CASE_END = 122;

    private static final int _CHAR_UPPER_CASE_BEGIN = 65;

    private static final int _CHAR_UPPER_CASE_END = 90;

    private static final int _DIGIT_BEGIN = 48;

    private static final int _DIGIT_END = 57;

    private static final char[] _EMAIL_ADDRESS_SPECIAL_CHAR = new char[]{'.', '!', '#', '$', '%', '&', '\'', '*', '+', '-', '/', '=', '?', '^', '_', '`', '{', '|', '}', '~'};

    private static final String _LOCALHOST = "localhost";

    private static final String _VARIABLE_TERM_BEGIN = "[$";

    private static final String _VARIABLE_TERM_END = "$]";

    private static final String _XML_BEGIN = "<?xml";

    private static final String _XML_EMPTY = "<root />";

    private static Pattern _emailAddressPattern = Pattern.compile("[\\w!#$%&'*+/=?^_`{|}~-]+(?:\\.[\\w!#$%&'*+/=?^_`{|}~-]+)*@" + "(?:[\\w](?:[\\w-]*[\\w])?\\.)+[\\w](?:[\\w-]*[\\w])?");
    private static Pattern _ipAddressPattern = Pattern.compile("\\b" + "((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\." + "((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\."
            + "((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\." + "((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])" + "\\b");
    private static Pattern _urlPattern = Pattern.compile("(http://|ftp://|https://|www){0,1}[^\u4e00-\u9fa5\\s]*?\\.(com|net|cn|me|tw|fr)[^\u4e00-\u9fa5\\s]*");
    private static Pattern _variableNamePattern = Pattern.compile("[_a-zA-Z]+[_a-zA-Z0-9]*");

    public static void main(String[] args) {
        System.out.println(isTimeFormat("23:59:59"));
    }


    /**
     * 时间格式校验（hh:mm:ss）
     */
    public static boolean isTimeFormat(String timeStr) {
        String regex_hhmmss = "([0-1][0-9]|2[0-3]):([0-5][0-9]):([0-5][0-9])";//HH:mm:ss
        boolean result = Pattern.compile(regex_hhmmss).matcher(timeStr).matches();
        return result;
    }

}

