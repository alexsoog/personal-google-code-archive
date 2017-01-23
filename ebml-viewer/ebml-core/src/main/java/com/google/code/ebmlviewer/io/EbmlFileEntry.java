/*-
 * Copyright (c) 2008-2012, Oleg Estekhin
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *  * Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 *  * Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in
 *    the documentation and/or other materials provided with the distribution.
 *  * Neither the names of the copyright holders nor the names of their
 *    contributors may be used to endorse or promote products derived
 *    from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE REGENTS OR CONTRIBUTORS BE LIABLE FOR
 * ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT
 * LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY
 * OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH
 * DAMAGE.
 */

package com.google.code.ebmlviewer.io;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.List;

import com.google.code.ebmlviewer.core.VariableLengthInteger;

/** Contains the entry data. */
public final class EbmlFileEntry {

    private final EbmlFileReader reader;

    private final long entryPosition;

    private final VariableLengthInteger identifier;

    private final VariableLengthInteger size;


    /** The cached list of entries contained in this entry. */
    private List<EbmlFileEntry> entries;

    /** The cached data contained in this entry. */
    private ByteBuffer data;


    EbmlFileEntry( EbmlFileReader reader, long entryPosition, VariableLengthInteger identifier, VariableLengthInteger size ) {
        if ( reader == null ) {
            throw new IllegalArgumentException( "reader is null" );
        }
        if ( entryPosition < 0L ) {
            throw new IllegalArgumentException( "entryPosition is negative" );
        }
        if ( identifier == null ) {
            throw new IllegalArgumentException( "identifier is null" );
        }
        if ( size == null ) {
            throw new IllegalArgumentException( "size is null" );
        }
        this.reader = reader;
        this.entryPosition = entryPosition;
        this.identifier = identifier;
        this.size = size;
    }


    /**
     * Returns the file position at which this entry starts.
     *
     * @return the entry position in the file
     */
    public long getEntryPosition() {
        return entryPosition;
    }

    /**
     * Returns the file position at which this entry data starts.
     *
     * @return the entry data position in the file
     */
    public long getDataPosition() {
        return entryPosition + identifier.getEncodedLength() + size.getEncodedLength();
    }

    /**
     * Returns the element identifier of this entry.
     *
     * @return the entry element identifier
     */
    public VariableLengthInteger getIdentifier() {
        return identifier;
    }

    /**
     * Returns the data size of this entry.
     *
     * @return the entry data size
     */
    public VariableLengthInteger getSize() {
        return size;
    }


    // todo get data as input stream
    // todo get data as channel

    /**
     * Reads a sequence of bytes from this entry into the given buffer.
     *
     * @param destination the buffer into which bytes are to be transferred
     *
     * @return the number of bytes read, possibly zero, or {@code -1} if the entry's data size is zero
     *
     * @throws IllegalArgumentException if {@code destination} is {@code null}
     * @throws IOException if an I/O error has occurred
     */
    public int read( ByteBuffer destination ) throws IOException {
        return read( destination, 0L );
    }

    /**
     * Reads a sequence of bytes from this entry into the given buffer, starting at the given data position.
     *
     * @param destination the buffer into which bytes are to be transferred
     * @param dataOffset the offset from the position of entry data at which the transfer is to begin
     *
     * @return the number of bytes read, possibly zero, or {@code -1} if the given offset is greater than or equal to
     *         the entry's data size
     *
     * @throws IllegalArgumentException if {@code destination} is {@code null}
     * @throws IllegalArgumentException if {@code dataOffset} is negative
     * @throws IOException if an I/O error has occurred
     */
    public int read( ByteBuffer destination, long dataOffset ) throws IOException {
        if ( destination == null ) {
            throw new IllegalArgumentException( "destination is null" );
        }
        if ( dataOffset < 0L ) {
            throw new IllegalArgumentException( "dataOffset is negative" );
        }
        if ( dataOffset >= size.getPlainValue() ) {
            return -1;
        }
        if ( destination.remaining() == 0 ) {
            return 0;
        }
        return reader.read( destination, entryPosition + identifier.getEncodedLength() + size.getEncodedLength() + dataOffset, size.getPlainValue() - dataOffset );
    }


    /**
     * Returns whether the {@link #getEntries()} method will require blocking I/O operations.
     *
     * @return {@code true} if the {@link #getEntries()} method will require blocking I/O operations; {@code false}
     *         otherwise
     */
    public boolean getEntriesWillBlock() {
        return entries == null && data == null;
    }

    /**
     * Returns a list of entries contained in this entry.
     *
     * @return a list of entries contained in this entry
     *
     * @throws IOException if an I/O error has occurred
     */
    public List<EbmlFileEntry> getEntries() throws IOException {
        if ( entries == null ) {
            entries = reader.readEntries( entryPosition + identifier.getEncodedLength() + size.getEncodedLength(), size.getPlainValue(), getData() );
        }
        return entries;
    }


    void setData( ByteBuffer data ) {
        this.data = data;
    }

    ByteBuffer getData() {
        return data == null ? null : data.duplicate();
    }


    @Override
    public String toString() {
        return String.format( "EbmlFileEntry(%s, %s (%s), %s (%s))", entryPosition, identifier, identifier.getEncodedLength(), size.getPlainValue(), size.getEncodedLength() );
    }

}
