package me.tinggu.sample.common.rest;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URLDecoder;
import java.nio.charset.Charset;

import okio.Buffer;
import okio.BufferedSink;
import okio.ByteString;
import okio.Source;
import okio.Timeout;

/**
 * Created by dongyanjing on 2015/12/23.
 */
public class CacheBufferdSkin implements BufferedSink {


    public String bodyStr;

    @Override
    public Buffer buffer() {
        return null;
    }

    @Override
    public BufferedSink write(ByteString byteString) throws IOException {
        bodyStr = new String(byteString.toByteArray(), Charset.forName("UTF-8"));
        bodyStr = URLDecoder.decode(bodyStr, "UTF-8");
        return null;
    }

    @Override
    public BufferedSink write(byte[] source) throws IOException {
        bodyStr = new String(source);
        bodyStr = URLDecoder.decode(bodyStr, "UTF-8");
        return null;
    }

    @Override
    public BufferedSink write(byte[] source, int offset, int byteCount) throws IOException {
        bodyStr = URLDecoder.decode(new String(source), "UTF-8");
        return null;
    }

    @Override
    public long writeAll(Source source) throws IOException {
        return 0;
    }

    @Override
    public BufferedSink write(Source source, long byteCount) throws IOException {
        return null;
    }

    @Override
    public BufferedSink writeUtf8(String string) throws IOException {
        bodyStr = URLDecoder.decode(string, "UTF-8");
        return null;
    }

    @Override
    public BufferedSink writeUtf8(String string, int beginIndex, int endIndex) throws IOException {
        return null;
    }

    @Override
    public BufferedSink writeUtf8CodePoint(int codePoint) throws IOException {
        return null;
    }

    @Override
    public BufferedSink writeString(String string, Charset charset) throws IOException {
        bodyStr = URLDecoder.decode(string, "UTF-8");
        return null;
    }

    @Override
    public BufferedSink writeString(String string, int beginIndex, int endIndex, Charset charset) throws IOException {
        bodyStr = URLDecoder.decode(string, "UTF-8");
        return null;
    }

    @Override
    public BufferedSink writeByte(int b) throws IOException {
        return null;
    }

    @Override
    public BufferedSink writeShort(int s) throws IOException {
        return null;
    }

    @Override
    public BufferedSink writeShortLe(int s) throws IOException {
        return null;
    }

    @Override
    public BufferedSink writeInt(int i) throws IOException {
        return null;
    }

    @Override
    public BufferedSink writeIntLe(int i) throws IOException {
        return null;
    }

    @Override
    public BufferedSink writeLong(long v) throws IOException {
        return null;
    }

    @Override
    public BufferedSink writeLongLe(long v) throws IOException {
        return null;
    }

    @Override
    public BufferedSink writeDecimalLong(long v) throws IOException {
        return null;
    }

    @Override
    public BufferedSink writeHexadecimalUnsignedLong(long v) throws IOException {
        return null;
    }

    @Override
    public BufferedSink emitCompleteSegments() throws IOException {
        return null;
    }

    @Override
    public BufferedSink emit() throws IOException {
        return null;
    }

    @Override
    public OutputStream outputStream() {
        return null;
    }

    @Override
    public void write(Buffer source, long byteCount) throws IOException {

    }

    @Override
    public void flush() throws IOException {

    }

    @Override
    public Timeout timeout() {
        return null;
    }

    @Override
    public void close() throws IOException {

    }
}
