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

package com.google.code.ebmlviewer.stream;

import java.io.Closeable;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.Deque;
import java.util.LinkedList;

import com.google.code.ebmlviewer.core.EbmlDecoder;
import com.google.code.ebmlviewer.core.EbmlFormatException;
import com.google.code.ebmlviewer.core.IllegalEncodedLengthException;
import com.google.code.ebmlviewer.core.VariableLengthInteger;
import com.google.code.ebmlviewer.io.EbmlIoException;

/** The {@code EbmlStreamReader} class allows sequential read-only access to the EBML data. */
public final class EbmlStreamReader implements Closeable {

    private static final int INPUT_BUFFER_SIZE = 8 * 1024;


    private final ReadableByteChannel source;

    private ByteBuffer buffer;

    private EbmlDecoder decoder;


    private final Deque<EbmlStreamEntry> containers;

    private EbmlStreamEntry container;

    private EbmlStreamEntry element;


    /**
     * Creates a new EBML stream reader.
     *
     * @param source the data source
     *
     * @throws NullPointerException if {@code source} is {@code null}
     * @see Channels#newChannel(InputStream)
     */
    public EbmlStreamReader( InputStream source ) {
        this( Channels.newChannel( source ) );
    }

    /**
     * Creates a new EBML stream reader.
     *
     * @param source the data source
     *
     * @throws IllegalArgumentException if {@code source} is {@code null}
     */
    public EbmlStreamReader( ReadableByteChannel source ) {
        this( source, VariableLengthInteger.MAXIMUM_PLAIN_VALUE );
    }

    /**
     * Creates a new EBML stream reader.
     *
     * @param source the data source
     * @param size the maximum number of bytes to read from the source
     *
     * @throws IllegalArgumentException if {@code source} is {@code null}
     * @throws IllegalArgumentException if {@code size} is negative or greater than {@value
     * VariableLengthInteger#MAXIMUM_PLAIN_VALUE}
     */
    public EbmlStreamReader( ReadableByteChannel source, long size ) {
        if ( source == null ) {
            throw new IllegalArgumentException( "source is null" );
        }
        if ( size < 0L || size > VariableLengthInteger.MAXIMUM_PLAIN_VALUE ) {
            throw new IllegalArgumentException( String.format( "size is out of valid range: %#018xL", size ) );
        }
        this.source = source;
        buffer = ByteBuffer.allocate( INPUT_BUFFER_SIZE );
        buffer.flip();
        decoder = new EbmlDecoder();
        containers = new LinkedList<EbmlStreamEntry>();
        container = new EbmlStreamEntry( VariableLengthInteger.fromEncoded( 0xffL ), VariableLengthInteger.fromPlain( size ) );
    }


    @Override
    public void close() throws IOException {
        source.close();
    }


    private void skip( long skip ) throws IOException {
        if ( skip < buffer.remaining() ) {
            buffer.position( buffer.position() + ( int ) skip );
        } else {
            long remaining = skip - buffer.remaining();
            while ( remaining > 0L ) {
                buffer.clear();
                int read = source.read( buffer );
                if ( read < 0 ) {
                    throw new EOFException();
                }
                remaining -= read;
            }
            buffer.flip();
            buffer.position( buffer.limit() + ( int ) remaining );
        }
    }

    private void fill( long required ) throws IOException {
        if ( required > buffer.capacity() ) {
            throw new AssertionError( required );
        }
        if ( buffer.remaining() < required ) {
            buffer.compact();
            while ( buffer.hasRemaining() ) {
                int read = source.read( buffer );
                if ( read < 0 ) {
                    break;
                }
            }
            buffer.flip();
        }
    }


    /**
     * Reads the next child element of the current container.
     *
     * @return {@code true} if the child element is available; {@code false} otherwise
     *
     * @throws EOFException if the input source reaches the end before reading all required data
     * @throws IOException if an I/O error has occurred
     */
    public boolean next() throws IOException {
        if ( element != null ) {
            skip( element.getRemaining() );
            container.decreaseRemaining( element.getSize().getPlainValue() );
            element = null;
        }
        if ( !container.hasRemaining() ) {
            return false;
        }
        fill( 8 );
        if ( !buffer.hasRemaining() ) {
            return false;
        }
        VariableLengthInteger identifier;
        try {
            identifier = decoder.decodeVariableLengthInteger( buffer );
        } catch ( EbmlFormatException e ) {
            throw new EbmlIoException( e );
        } catch ( BufferUnderflowException e ) {
            EOFException eof = new EOFException();
            eof.initCause( e );
            throw eof;
        }
        container.decreaseRemaining( identifier.getEncodedLength() );
        if ( container.getRemaining() < 0L ) {
            throw new EbmlIoException( "container size is invalid" );
        }
        if ( !identifier.isIdentifier() ) {
            throw new EbmlIoException( "element identifier has invalid value" );
        }
        fill( 8 );
        VariableLengthInteger size;
        try {
            size = decoder.decodeVariableLengthInteger( buffer );
        } catch ( EbmlFormatException e ) {
            throw new EbmlIoException( e );
        } catch ( BufferUnderflowException e ) {
            EOFException eof = new EOFException();
            eof.initCause( e );
            throw eof;
        }
        container.decreaseRemaining( size.getEncodedLength() );
        if ( container.getRemaining() < 0L ) {
            throw new EbmlIoException( "container size is invalid" );
        }
        if ( size.isReserved() ) {
            throw new EbmlIoException( "element size has reserved value" );
        }
        if ( container.getRemaining() < size.getPlainValue() ) {
            throw new EbmlIoException( "element size exceeds space remaining in the container" );
        }
        element = new EbmlStreamEntry( identifier, size );
        return true;
    }


    /**
     * Returns the identifier of the current element.
     *
     * @return the element identifier
     *
     * @throws IllegalStateException if the current element is not available
     */
    public VariableLengthInteger getIdentifier() {
        if ( element == null ) {
            throw new IllegalStateException( "the current element is not available" );
        }
        return element.getIdentifier();
    }

    /**
     * Returns the size of the current element.
     *
     * @return the element size
     *
     * @throws IllegalStateException if the current element is not available
     */
    public VariableLengthInteger getSize() {
        if ( element == null ) {
            throw new IllegalStateException( "the current element is not available" );
        }
        return element.getSize();
    }


    /**
     * Instructs the reader to parse the current element data as sub-elements. The current container will be saved on
     * the stack and the current element will become the new container.
     *
     * @throws IllegalStateException if the current element is not available or if the element data was already
     * processed
     */
    public void enterContainer() {
        if ( element == null ) {
            throw new IllegalStateException( "the current element is not available" );
        }
        if ( element.isTouched() ) {
            throw new IllegalStateException( "the current element data was already processed" );
        }
        element.touch();
        containers.addFirst( container );
        container = element;
        element = null;
    }

    /**
     * Instructs the reader to return to the previous container.
     *
     * @throws IllegalStateException if the current container represents the whole data source
     */
    public void leaveContainer() {
        if ( containers.isEmpty() ) {
            throw new IllegalStateException( "container stack underflow" );
        }
        if ( element != null ) {
            container.decreaseRemaining( element.getSize().getPlainValue() - element.getRemaining() );
        }
        element = container;
        container = containers.removeFirst();
    }


    /**
     * Reads the contents of the current element as a signed integer.
     *
     * @return the contents of the current element as a signed integer value
     *
     * @throws IllegalStateException if the current element is not available
     * @throws EOFException if the input source reaches the end before reading all required data
     * @throws IOException if an I/O error has occurred
     */
    public long readSignedInteger() throws IOException {
        if ( element == null ) {
            throw new IllegalStateException( "the current element is not available" );
        }
        if ( element.isTouched() ) {
            throw new IllegalStateException( "the current element data was already processed" );
        }
        element.touch();
        fill( element.getSize().getPlainValue() );
        try {
            int valueSize = ( int ) element.getSize().getPlainValue();
            long value = decoder.decodeSignedInteger( buffer, valueSize );
            element.decreaseRemaining( valueSize );
            return value;
        } catch ( IllegalEncodedLengthException e ) {
            throw new EbmlIoException( e );
        } catch ( BufferUnderflowException e ) {
            EOFException eof = new EOFException();
            eof.initCause( e );
            throw eof;
        }
    }

    /**
     * Reads the contents of the current element as an unsigned integer.
     *
     * @return the contents of the current element as an unsigned integer value
     *
     * @throws IllegalStateException if the current element is not available
     * @throws EOFException if the input source reaches the end before reading all required data
     * @throws IOException if an I/O error has occurred
     */
    public long readUnsignedInteger() throws IOException {
        if ( element == null ) {
            throw new IllegalStateException( "the current element is not available" );
        }
        if ( element.isTouched() ) {
            throw new IllegalStateException( "the current element data was already processed" );
        }
        element.touch();
        fill( element.getSize().getPlainValue() );
        try {
            int valueSize = ( int ) element.getSize().getPlainValue();
            long value = decoder.decodeUnsignedInteger( buffer, valueSize );
            element.decreaseRemaining( valueSize );
            return value;
        } catch ( IllegalEncodedLengthException e ) {
            throw new EbmlIoException( e );
        } catch ( BufferUnderflowException e ) {
            EOFException eof = new EOFException();
            eof.initCause( e );
            throw eof;
        }
    }

    /**
     * Reads the contents of the current element as a floating-point number.
     *
     * @return the contents of the current element as a floating-point number
     *
     * @throws IllegalStateException if the current element is not available
     * @throws EOFException if the input source reaches the end before reading all required data
     * @throws IOException if an I/O error has occurred
     */
    public double readFloatingPoint() throws IOException {
        if ( element == null ) {
            throw new IllegalStateException( "the current element is not available" );
        }
        if ( element.isTouched() ) {
            throw new IllegalStateException( "the current element data was already processed" );
        }
        element.touch();
        fill( element.getSize().getPlainValue() );
        try {
            int valueSize = ( int ) element.getSize().getPlainValue();
            double value = decoder.decodeFloatingPoint( buffer, valueSize );
            element.decreaseRemaining( valueSize );
            return value;
        } catch ( IllegalEncodedLengthException e ) {
            throw new EbmlIoException( e );
        } catch ( BufferUnderflowException e ) {
            EOFException eof = new EOFException();
            eof.initCause( e );
            throw eof;
        }
    }

    /**
     * Reads the contents of the current element as an ASCII string.
     *
     * @return the contents of the current element as an ASCII string
     *
     * @throws IllegalStateException if the current element is not available
     * @throws EOFException if the input source reaches the end before reading all required data
     * @throws IOException if an I/O error has occurred
     */
    public String readAsciiString() throws IOException {
        if ( element == null ) {
            throw new IllegalStateException( "the current element is not available" );
        }
        if ( element.isTouched() ) {
            throw new IllegalStateException( "the current element data was already processed" );
        }
        element.touch();
        fill( element.getSize().getPlainValue() );
        try {
            int valueSize = ( int ) element.getSize().getPlainValue();
            String value = decoder.decodeAsciiString( buffer, valueSize );
            element.decreaseRemaining( valueSize );
            return value;
        } catch ( IllegalEncodedLengthException e ) {
            throw new EbmlIoException( e );
        } catch ( BufferUnderflowException e ) {
            EOFException eof = new EOFException();
            eof.initCause( e );
            throw eof;
        }
    }

    /**
     * Reads the contents of the current element as an UTF-8 string.
     *
     * @return the contents of the current element as an UTF-8 string
     *
     * @throws IllegalStateException if the current element is not available
     * @throws EOFException if the input source reaches the end before reading all required data
     * @throws IOException if an I/O error has occurred
     */
    public String readUnicodeString() throws IOException {
        if ( element == null ) {
            throw new IllegalStateException( "the current element is not available" );
        }
        if ( element.isTouched() ) {
            throw new IllegalStateException( "the current element data was already processed" );
        }
        element.touch();
        fill( element.getSize().getPlainValue() );
        try {
            int valueSize = ( int ) element.getSize().getPlainValue();
            String value = decoder.decodeUnicodeString( buffer, valueSize );
            element.decreaseRemaining( valueSize );
            return value;
        } catch ( IllegalEncodedLengthException e ) {
            throw new EbmlIoException( e );
        } catch ( BufferUnderflowException e ) {
            EOFException eof = new EOFException();
            eof.initCause( e );
            throw eof;
        }
    }

    /**
     * Reads the contents of the current element as a date.
     *
     * @return the contents of the current element as a number of milliseconds since January 1, 1970, 00:00:00 GMT
     *
     * @throws IllegalStateException if the current element is not available
     * @throws EOFException if the input source reaches the end before reading all required data
     * @throws IOException if an I/O error has occurred
     */
    public long readDate() throws IOException {
        if ( element == null ) {
            throw new IllegalStateException( "the current element is not available" );
        }
        if ( element.isTouched() ) {
            throw new IllegalStateException( "the current element data was already processed" );
        }
        element.touch();
        fill( element.getSize().getPlainValue() );
        try {
            int valueSize = ( int ) element.getSize().getPlainValue();
            long value = decoder.decodeDate( buffer, valueSize );
            element.decreaseRemaining( valueSize );
            return value;
        } catch ( IllegalEncodedLengthException e ) {
            throw new EbmlIoException( e );
        } catch ( BufferUnderflowException e ) {
            EOFException eof = new EOFException();
            eof.initCause( e );
            throw eof;
        }
    }

}
