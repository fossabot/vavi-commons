/*
 * Copyright (c) 2002 by Naohide Sano, All rights reserved.
 *
 * Programmed by Naohide Sano
 */

package vavi.io;

import java.io.EOFException;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;


/**
 * Little Endian バイトオーダで読み込むストリームです．
 *
 * @author <a href="mailto:vavivavi@yahoo.co.jp">Naohide Sano</a> (nsano)
 * @version 0.00 020507 nsano initial version <br>
 *          0.01 020623 nsano refine method name <br>
 *          0.10 030726 nsano implement DataInput <br>
 */
public class LittleEndianDataInputStream extends FilterInputStream implements LittleEndianDataInput {

    /**
     * Little Endian バイトオーダで読み込むストリームを作成します．
     */
    public LittleEndianDataInputStream(InputStream in) {
        super(in);
    }

    // ----

    /**
     * 16bit 読み込みます．
     */
    public short readShort() throws IOException {

        int b8L, b8H;

        b8L = in.read() & 0xff;
        b8H = in.read();
        if ((b8L | b8H) < 0) {
            throw new EOFException();
        }
        return (short) ((b8H << 8) | b8L);
    }

    /**
     * 32bit 読み込みます．
     */
    public int readInt() throws IOException {

        int b16L, b16H;

        b16L = readShort() & 0xffff;
        b16H = readShort();
        return (b16H << 16) | b16L;
    }

    /**
     * 64bit 読み込みます．
     */
    public long readLong() throws IOException {

        long b32L, b32H;

        b32L = readInt() & 0xffffffffL;
        b32H = readInt();
        return (b32H << 32) | b32L;
    }

    /**
     * TODO
     */
    public String readUTF() throws IOException {
        throw new UnsupportedOperationException("not implemented");
    }

    /**
     * TODO
     */
    public String readLine() throws IOException {
        throw new UnsupportedOperationException("not implemented");
    }

    /**
     *
     */
    public void readFully(byte[] b, int offset, int len) throws IOException {
        int l = in.read(b, offset, len);
        if (l == -1) {
            throw new EOFException();
        }
    }

    /**
     *
     */
    public void readFully(byte[] b) throws IOException {
        readFully(b, 0, b.length);
    }

    /**
     *
     */
    public int skipBytes(int len) throws IOException {
        return (int) in.skip(len);
    }

    /**
     * boolean 読み込みます．
     */
    public boolean readBoolean() throws IOException {
        throw new UnsupportedOperationException("not implemented"); // TODO not implemented
    }

    /**
     * unsigned 16bit 読み込みます．
     */
    public int readUnsignedShort() throws IOException {

        int b8L, b8H;

        b8L = in.read();
        b8H = in.read();
        if ((b8L | b8H) < 0) {
            throw new EOFException();
        }
        return ((b8H << 8) + b8L) & 0xffff;
    }

    /**
     * unsigned byte 読み込みます．
     */
    public int readUnsignedByte() throws IOException {
        int r = in.read();
        if (r == -1) {
            throw new EOFException();
        } else {
            return r & 0xff;
        }
    }

    /**
     * float 読み込みます．
     */
    public float readFloat() throws IOException {
        return Float.intBitsToFloat(readInt());
    }

    /**
     * double 読み込みます．
     */
    public double readDouble() throws IOException {
        return Double.longBitsToDouble(readLong());
    }

    /**
     * char 読み込みます．
     */
    public char readChar() throws IOException {
        throw new UnsupportedOperationException("not implemented"); // TODO not implemented
    }

    /**
     * byte 読み込みます．
     */
    public byte readByte() throws IOException {
        int r = in.read();
        if (r == -1) {
            throw new EOFException();
        } else {
            return (byte) (r & 0xff);
        }
    }
}

/* */
