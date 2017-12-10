package com.manulaiko.tabitha.net;

import com.manulaiko.tabitha.utils.Str;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * String command.
 * ===============
 *
 * Represents a string-based command and provides method for easily parsing them.
 *
 * The constructor accepts as parameter the received packet. If no packet
 * is specified and you try to read from it, a `RuntimeException` will be
 * thrown.
 *
 * This class implements the `DataInput` interface so you can easily use it
 * like the `DataInputStream` class, but instead of reading bytes, it will
 * read indexes.
 * This means that `Packet.skipBytes(1)` will not skip 1 byte of the original
 * packet, instead, it will skip to the next index (from the original packet
 * divided by the delimiter).
 *
 * It also implements the `DataOutput` interface so you can do the same in the same
 * object.
 *
 * The method `packet` returns the packet sent to the constructor.
 * The method `toString` returns the packet so it can be sent.
 *
 * The method `input` returns the packet sent to the constructor divided by the delimiter.
 * The method `output` returns the array of strings that builds the packet.
 *
 * @author Manulaiko <manulaiko@gmail.com>
 */
public class StringCommand implements DataInput, DataOutput {
    /**
     * Packet to parse.
     */
    private String _packet = "";

    /**
     * String delimiter.
     */
    private String _delimiter = "|";

    /**
     * Input pointer
     */
    private int _i = 0;

    /**
     * Parsed packet.
     */
    private String[] _input;

    /**
     * Output packet.
     */
    private List<String> _output = new ArrayList<>();

    /**
     * Constructor.
     *
     * @param packet Packet to parse.
     */
    public StringCommand(String packet) {
        this._packet = packet;

        this._input = this.packet().split("\\" + this.delimiter());
    }

    /**
     * Constructor.
     */
    public StringCommand() {
        this("");
    }

    /**
     * Returns the packet sent to the constructor.
     *
     * @return Packet sent to the constructor.
     */
    public String packet() {
        return this._packet;
    }

    /**
     * Returns the ready to send build packet.
     *
     * @return Ready to send build packet.
     */
    public String toString() {
        if (this.output().isEmpty()) {
            return "";
        }

        return Str.implode(this.output(), this.delimiter());
    }

    /**
     * Returns the packet divided by the delimiter.
     *
     * @return Packet divided by the delimiter.
     */
    public String[] input() {
        return this._input;
    }

    /**
     * Returns the array of strings of the building packet.
     *
     * @return Array of strings of the building packet.
     */
    public List<String> output() {
        return this._output;
    }

    /**
     * Sets `delimiter`.
     *
     * @param delimiter New value for `delimiter`.
     */
    public void delimiter(String delimiter) {
        this._delimiter = delimiter;
    }

    /**
     * Returns `delimiter`.
     *
     * @return Current value of `delimiter`.
     */
    public String delimiter() {
        return this._delimiter;
    }

    ////////////////////////////////////////////
    // No documentation for overridden method //
    //    Because we all know what they do.   //
    ////////////////////////////////////////////

    @Override
    public void readFully(byte[] bytes) throws IOException {
        // Not used
    }

    @Override
    public void readFully(byte[] bytes, int i, int i1) throws IOException {
        // Not used
    }

    @Override
    public int skipBytes(int i) throws IOException {
        int nBytes = Math.min(this.input().length, i);

        this._i += nBytes;

        return nBytes;
    }

    @Override
    public boolean readBoolean() throws IOException {
        int i = Integer.parseInt(this.readUTF());

        return (i != 0);
    }

    @Override
    public byte readByte() throws IOException {
        return Byte.parseByte(this.readUTF());
    }

    @Override
    public int readUnsignedByte() throws IOException {
        return this.readByte() & 0xff;
    }

    @Override
    public short readShort() throws IOException {
        return Short.parseShort(this.readUTF());
    }

    @Override
    public int readUnsignedShort() throws IOException {
        return this.readShort() & 0xffff;
    }

    @Override
    public char readChar() throws IOException {
        return (char) this.readShort();
    }

    @Override
    public int readInt() throws IOException {
        return Integer.parseInt(this.readUTF());
    }

    @Override
    public long readLong() throws IOException {
        return Long.parseLong(this.readUTF());
    }

    @Override
    public float readFloat() throws IOException {
        return Float.parseFloat(this.readUTF());
    }

    @Override
    public double readDouble() throws IOException {
        return Double.parseDouble(this.readUTF());
    }

    @Override
    public String readLine() throws IOException {
        return this.readUTF();
    }

    @Override
    public String readUTF() throws IOException {
        return this.input()[this._i++];
    }

    @Override
    public void write(int i) throws IOException {
        this.writeUTF(i + "");
    }

    @Override
    public void write(byte[] bytes) throws IOException {
        // Not used
    }

    @Override
    public void write(byte[] bytes, int i, int i1) throws IOException {
        // Not used
    }

    @Override
    public void writeBoolean(boolean b) throws IOException {
        this.writeUTF(b ? "1" : "0");
    }

    @Override
    public void writeByte(int i) throws IOException {
        this.writeUTF(i + "");
    }

    @Override
    public void writeShort(int i) throws IOException {
        this.writeUTF(i + "");
    }

    @Override
    public void writeChar(int i) throws IOException {
        this.writeUTF(i + "");
    }

    @Override
    public void writeInt(int i) throws IOException {
        this.writeUTF(i + "");
    }

    @Override
    public void writeLong(long l) throws IOException {
        this.writeUTF(l + "");
    }

    @Override
    public void writeFloat(float v) throws IOException {
        this.writeUTF(v + "");
    }

    @Override
    public void writeDouble(double v) throws IOException {
        this.writeUTF(v + "");
    }

    @Override
    public void writeBytes(String s) throws IOException {
        this.writeUTF(s);
    }

    @Override
    public void writeChars(String s) throws IOException {
        this.writeUTF(s);
    }

    @Override
    public void writeUTF(String s) throws IOException {
        this.output().add(s);
    }
}
