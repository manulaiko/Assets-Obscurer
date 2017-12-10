package com.manulaiko.tabitha.net;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/**
 * Command class.
 * ==============
 *
 * Represents a communication command.
 *
 * @author Manulaiko <manulaiko@gmail.com>
 */
public abstract class Command {
    /**
     * Command ID.
     */
    private short _id;

    /**
     * Writes the command to the output stream.
     *
     * @param output Output stream to write the command to.
     *
     * @throws IOException In case the write failed.
     */
    public void write(DataOutput output) throws IOException {
        output.writeShort(this.id());
        this._write(output);
    }

    /**
     * Performs the specific write for the command.
     *
     * @param output Output stream to write the command to.
     *
     * @throws IOException In case the write failed.
     */
    protected abstract void _write(DataOutput output) throws IOException;

    /**
     * Reads the command from the input stream.
     *
     * @param input Input stream to read the command from.
     *
     * @throws IOException In case the read failed.
     */
    public abstract void read(DataInput input) throws IOException;

    /**
     * Sets `id`.
     *
     * @param id New value for `id`.
     */
    public void id(short id) {
        this._id = id;
    }

    /**
     * Returns `id`.
     *
     * @return Current value of `id`.
     */
    public short id() {
        return this._id;
    }
}
