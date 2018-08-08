package com.hyjf.common.http;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CodingErrorAction;

public class CharsetDecoderUtil {

    public static CharBuffer decode(String charsetName, byte[] bytes) {
        return decode(charsetName, ByteBuffer.wrap(bytes));
    }

    public static CharBuffer decode(String charsetName, byte[] bytes, int offset, int length) {

        return decode(charsetName, ByteBuffer.wrap(bytes, offset, length));
    }

    public static CharBuffer decode(String charsetName, ByteBuffer byteBuffer) {
        try {
            CharsetDecoder charsetDecoder = getCharsetDecoder(charsetName);

            return charsetDecoder.decode(byteBuffer);
        } catch (CharacterCodingException cce) {
            throw new Error(cce);
        }
    }

    public static CharsetDecoder getCharsetDecoder(String charsetName) {
        Charset charset = Charset.forName(charsetName);

        CharsetDecoder charsetDecoder = charset.newDecoder();

        charsetDecoder.onMalformedInput(CodingErrorAction.REPLACE);
        charsetDecoder.onUnmappableCharacter(CodingErrorAction.REPLACE);

        return charsetDecoder;
    }

}