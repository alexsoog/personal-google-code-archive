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
import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.LinkedList;
import java.util.List;

import com.google.code.ebmlviewer.core.EbmlDecoder;
import com.google.code.ebmlviewer.core.EbmlFormatException;
import com.google.code.ebmlviewer.core.VariableLengthInteger;

final class EbmlFileReader {

    private static final int INPUT_BUFFER_SIZE = 8 * 1024;


    private final FileChannel channel;


    /**
     * Creates a new {@code EbmlFileReader}.
     *
     * @param channel the data source
     *
     * @throws IllegalArgumentException if {@code channel} is {@code null}
     */
    EbmlFileReader( FileChannel channel ) {
        if ( channel == null ) {
            throw new IllegalArgumentException( "channel is null" );
        }
        this.channel = channel;
    }


    /**
     * Reads a sequence of bytes from this reader into the given buffer, starting at the given file position.
     *
     * @param buffer the buffer into which bytes are to be transferred
     * @param position the file position at which the transfer is to begin
     * @param size the maximum number of bytes to read from the file
     *
     * @return the number of bytes read, possibly zero, or {@code -1} if the given position is greater than or equal to
     *         the file's size
     *
     * @throws IllegalArgumentException if {@code buffer} is {@code null}
     * @throws IllegalArgumentException if {@code position} or {@code size} is negative
     * @throws IOException if an I/O error has occurred
     */
    public int read( ByteBuffer buffer, long position, long size ) throws IOException {
        if ( buffer == null ) {
            throw new IllegalArgumentException( "buffer is null" );
        }
        if ( position < 0L ) {
            throw new IllegalArgumentException( "position is negative" );
        }
        if ( size < 0L ) {
            throw new IllegalArgumentException( "size is negative" );
        }
        int limit = buffer.limit();
        if ( buffer.remaining() > size ) {
            buffer.limit( buffer.position() + ( int ) size );
        }
        try {
            int read = 0;
            while ( buffer.hasRemaining() ) {
                int r = channel.read( buffer, position + read );
                if ( r < 0 ) {
                    if ( read == 0 ) {
                        read = -1;
                    }
                    break;
                }
                read += r;
            }
            return read;
        } finally {
            buffer.limit( limit );
        }
    }


    private int fill( ByteBuffer buffer, long position, long size ) throws IOException {
        buffer.compact();
        int read = read( buffer, position, size );
        buffer.flip();
        return read >= 0 ? read : 0;
    }


    /**
     * Reads a sequence of entries from this reader, starting at the given file position.
     *
     * @param position the file position at which the transfer is to begin
     * @param size the maximum number of bytes to read from the file
     *
     * @return a list of entries
     *
     * @throws IOException if an I/O error has occurred
     */
    public List<EbmlFileEntry> readEntries( long position, long size ) throws IOException {
        return readEntries( position, size, null );
    }

    List<EbmlFileEntry> readEntries( long position, long size, ByteBuffer buffer ) throws IOException {
        if ( buffer == null ) {
            buffer = ByteBuffer.allocate( INPUT_BUFFER_SIZE );
            buffer.flip();
        }

        EbmlDecoder decoder = new EbmlDecoder();
        List<EbmlFileEntry> entries = new LinkedList<EbmlFileEntry>();

        long filePosition = position;
        long remainingPosition = position + buffer.remaining();
        long remainingSize = size - buffer.remaining();

        while ( buffer.hasRemaining() || remainingSize > 0L ) {
            long entryPosition = filePosition;
            // read identifier of the next entry
            if ( buffer.remaining() < 8 && remainingSize > 0L ) {
                int fill = fill( buffer, remainingPosition, remainingSize );
                remainingPosition += fill;
                remainingSize -= fill;
            }
            VariableLengthInteger entryIdentifier;
            try {
                entryIdentifier = decoder.decodeVariableLengthInteger( buffer ); // EbmlFormatException, BufferUnderflowException
            } catch ( EbmlFormatException e ) {
                throw new EbmlIoException( filePosition, e );
            } catch ( BufferUnderflowException e ) {
                throw new EbmlIoException( filePosition, "unexpected end of data while reading identifier vli", e );
                //throw new EbmlIoException( String.format( "the entry identifier is expected at %s, but the current frame does not have enough remaining data", filePosition ), e );
            }
            if ( !entryIdentifier.isIdentifier() ) {
                throw new EbmlIoException( filePosition, String.format( "the vli %s does not represent valid entry identifier", entryIdentifier ) );
            }
            filePosition += entryIdentifier.getEncodedLength();
            // read size of the next entry
            if ( buffer.remaining() < 8 && remainingSize > 0L ) {
                int fill = fill( buffer, remainingPosition, remainingSize );
                remainingPosition += fill;
                remainingSize -= fill;
            }
            VariableLengthInteger entrySize;
            try {
                entrySize = decoder.decodeVariableLengthInteger( buffer ); // EbmlFormatException, BufferUnderflowException
            } catch ( EbmlFormatException e ) {
                throw new EbmlIoException( filePosition, e );
            } catch ( BufferUnderflowException e ) {
                throw new EbmlIoException( filePosition, "unexpected end of data while reading identifier vli", e );
            }
            if ( entrySize.isReserved() ) {
                throw new EbmlIoException( filePosition, String.format( "the variable-length integer %s does not represent valid entry size", entrySize ) );
            }
            long dataSize = entrySize.getPlainValue();
            if ( dataSize > buffer.remaining() + remainingSize ) {
                throw new EbmlIoException( filePosition, "the data size of the entry exceeds the number of bytes remaining in the parent entry" );
            }
            filePosition += entrySize.getEncodedLength();
            // create next entry or read data of the next entry
            EbmlFileEntry entry = new EbmlFileEntry( this, entryPosition, entryIdentifier, entrySize );
            if ( dataSize <= buffer.remaining() ) {
                if ( buffer.isReadOnly() ) {
                    int limit = buffer.limit();
                    buffer.limit( buffer.position() + ( int ) dataSize );
                    entry.setData( buffer.slice() );
                    buffer.position( buffer.limit() );
                    buffer.limit( limit );
                } else {
                    byte[] cache = new byte[ ( int ) dataSize ];
                    buffer.get( cache, 0, cache.length );
                    entry.setData( ByteBuffer.wrap( cache ).asReadOnlyBuffer() );
                }
            } else {
                long skip = entrySize.getPlainValue() - buffer.remaining();
                buffer.position( buffer.limit() );
                remainingPosition += skip; // should be equal to the current filePosition + dataSize
                remainingSize -= skip; // should be non-negative
            }
            entries.add( entry );
            filePosition += dataSize;
        }
        return entries;
    }

}
