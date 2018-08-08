package com.hyjf.common.http;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.CodingErrorAction;

public class CharsetEncoderUtil {

    public static ByteBuffer encode(String charsetName, char[] chars, int offset, int length) {

        return encode(charsetName, CharBuffer.wrap(chars, offset, length));
    }

    public static ByteBuffer encode(String charsetName, CharBuffer charBuffer) {
        try {
            CharsetEncoder charsetEncoder = getCharsetEncoder(charsetName);

            return charsetEncoder.encode(charBuffer);
        } catch (CharacterCodingException cce) {
            throw new Error(cce);
        }
    }

    public static ByteBuffer encode(String charsetName, String string) {
        return encode(charsetName, CharBuffer.wrap(string));
    }

    public static CharsetEncoder getCharsetEncoder(String charsetName) {
        Charset charset = Charset.forName(charsetName);

        CharsetEncoder charsetEncoder = charset.newEncoder();

        charsetEncoder.onMalformedInput(CodingErrorAction.REPLACE);
        charsetEncoder.onUnmappableCharacter(CodingErrorAction.REPLACE);

        return charsetEncoder;
    }

}