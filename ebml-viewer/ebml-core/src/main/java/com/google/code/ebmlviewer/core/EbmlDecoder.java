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

package com.google.code.ebmlviewer.core;

import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CodingErrorAction;
import java.util.concurrent.TimeUnit;

/** Decodes data from the EBML format. */
public final class EbmlDecoder {

    /** Creates a new {@code EbmlDecoder} object. */
    public EbmlDecoder() {
    }


    /**
     * Reads the variable-length integer from the contents of the specified buffer.
     *
     * @param buffer the input buffer
     *
     * @return the decoded variable-length integer value
     *
     * @throws IllegalArgumentException if {@code buffer} is {@code null}
     * @throws EbmlFormatException if the length descriptor of the variable-length integer is zero or if the buffer does
     * not contain a valid encoded representation of a variable-length integer
     * @throws BufferUnderflowException if there are fewer than required bytes remaining in the input buffer
     */
    public VariableLengthInteger decodeVariableLengthInteger( ByteBuffer buffer ) {
        if ( buffer == null ) {
            throw new IllegalArgumentException( "buffer is null" );
        }
        int mark = buffer.position();
        try {
            int lengthDescriptor = buffer.get() & 0xff;
            if ( lengthDescriptor == 0 ) {
                throw new EbmlFormatException( "length descriptor is 0b00000000" );
            }
            int mask = 0x80;
            long encodedValue = lengthDescriptor;
            int encodedLength = 1;
            while ( ( lengthDescriptor & mask ) != mask ) {
                mask >>= 1;
                encodedValue = encodedValue << 8 | buffer.get() & 0xff;
                encodedLength++;
            }
            mark += encodedLength;
            return VariableLengthInteger.fromEncoded( encodedValue, encodedLength );
        } finally {
            buffer.position( mark );
        }
    }


    /**
     * Reads the next {@code encodedLength} bytes from the specified buffer and decodes them as a signed integer.
     *
     * @param buffer the input buffer
     * @param encodedLength the length of the encoded signed integer value
     *
     * @return the decoded signed integer value
     *
     * @throws IllegalArgumentException if {@code buffer} is {@code null}
     * @throws IllegalEncodedLengthException if {@code encodedLength} is negative or greater then {@code 8}
     * @throws BufferUnderflowException if there are fewer than {@code encodedLength} bytes remaining in the input
     * buffer
     */
    public long decodeSignedInteger( ByteBuffer buffer, int encodedLength ) {
        if ( buffer == null ) {
            throw new IllegalArgumentException( "buffer is null" );
        }
        if ( encodedLength < 0 || encodedLength > 8 ) {
            throw new IllegalEncodedLengthException( String.format( "the length of the encoded signed integer value is invalid: %d", encodedLength ) );
        }
        if ( encodedLength == 0 ) {
            return 0L;
        }
        int mark = buffer.position();
        try {
            long result = buffer.get();  // with sign extension
            for ( int i = 1; i < encodedLength; i++ ) {
                result = result << 8 | buffer.get() & 0xff;
            }
            mark += encodedLength;
            return result;
        } finally {
            buffer.position( mark );
        }
    }

    /**
     * Reads the next {@code encodedLength} bytes from the specified buffer and decodes them as an unsigned integer.
     *
     * @param buffer the input buffer
     * @param encodedLength the length of the encoded unsigned integer value
     *
     * @return the decoded unsigned integer value
     *
     * @throws IllegalArgumentException if {@code buffer} is {@code null}
     * @throws IllegalEncodedLengthException if {@code encodedLength} is negative or greater then {@code 8}
     * @throws BufferUnderflowException if there are fewer than {@code encodedLength} bytes remaining in the input
     * buffer
     */
    public long decodeUnsignedInteger( ByteBuffer buffer, int encodedLength ) {
        if ( buffer == null ) {
            throw new IllegalArgumentException( "buffer is null" );
        }
        if ( encodedLength < 0 || encodedLength > 8 ) {
            throw new IllegalEncodedLengthException( String.format( "the length of the encoded unsigned integer value is invalid: %d", encodedLength ) );
        }
        int mark = buffer.position();
        try {
            long result = 0L;
            for ( int i = 0; i < encodedLength; i++ ) {
                result = result << 8 | buffer.get() & 0xff;
            }
            mark += encodedLength;
            return result;
        } finally {
            buffer.position( mark );
        }
    }


    /**
     * Reads the next {@code encodedLength} bytes from the specified buffer and decodes them as a floating-point value.
     *
     * @param buffer the input buffer
     * @param encodedLength the length of the encoded floating-point value
     *
     * @return the decoded floating-point value
     *
     * @throws IllegalArgumentException if {@code buffer} is {@code null}
     * @throws IllegalEncodedLengthException if {@code encodedLength} is not equal to {@code 0}, {@code 4} or {@code 8}
     * @throws BufferUnderflowException if there are fewer than {@code encodedLength} bytes remaining in the input
     * buffer
     */
    public double decodeFloatingPoint( ByteBuffer buffer, int encodedLength ) {
        if ( buffer == null ) {
            throw new IllegalArgumentException( "buffer is null" );
        }
        if ( encodedLength < 0 || encodedLength != 0 && encodedLength != 4 && encodedLength != 8 ) {
            throw new IllegalEncodedLengthException( String.format( "the length of the encoded floating-point value is invalid: %d", encodedLength ) );
        }
        int mark = buffer.position();
        ByteOrder order = buffer.order();
        try {
            buffer.order( ByteOrder.BIG_ENDIAN );
            double value;
            switch ( encodedLength ) {
                case 0:
                    value = 0.0;
                    break;
                case 4:
                    value = buffer.getFloat();
                    mark += 4;
                    break;
                case 8:
                    value = buffer.getDouble();
                    mark += 8;
                    break;
                default:
                    throw new AssertionError( encodedLength );
            }
            return value;
        } finally {
            buffer.position( mark );
            buffer.order( order );
        }
    }


    /**
     * Reads the next {@code encodedLength} bytes from the specified buffer and decodes them as an ASCII string.
     *
     * @param buffer the input buffer
     * @param encodedLength the length of the encoded string value
     *
     * @return the decoded string
     *
     * @throws IllegalArgumentException if {@code buffer} is {@code null}
     * @throws IllegalEncodedLengthException if {@code encodedLength} is negative
     * @throws BufferUnderflowException if there are fewer than {@code encodedLength} bytes remaining in the input
     * buffer
     * @throws CharacterCodingException if a character decoding error occurs
     */
    public String decodeAsciiString( ByteBuffer buffer, int encodedLength ) throws CharacterCodingException {
        return decodeString( buffer, encodedLength, "ASCII" );
    }

    /**
     * Reads the next {@code encodedLength} bytes from the specified buffer and decodes them as an UTF-8 string.
     *
     * @param buffer the input buffer
     * @param encodedLength the length of the encoded string value
     *
     * @return the decoded string
     *
     * @throws IllegalArgumentException if {@code buffer} is {@code null}
     * @throws IllegalEncodedLengthException if {@code encodedLength} is negative
     * @throws BufferUnderflowException if there are fewer than {@code encodedLength} bytes remaining in the input
     * buffer
     * @throws CharacterCodingException if a character decoding error occurs
     */
    public String decodeUnicodeString( ByteBuffer buffer, int encodedLength ) throws CharacterCodingException {
        return decodeString( buffer, encodedLength, "UTF-8" );
    }

    private String decodeString( ByteBuffer buffer, int encodedLength, String charsetName ) throws CharacterCodingException {
        if ( buffer == null ) {
            throw new IllegalArgumentException( "buffer is null" );
        }
        if ( encodedLength < 0 ) {
            throw new IllegalEncodedLengthException( String.format( "the length of the encoded string value is invalid: %d", encodedLength ) );
        }
        int mark = buffer.position();
        int limit = buffer.limit();
        if ( mark + encodedLength > limit ) {
            throw new BufferUnderflowException();
        }
        try {
            buffer.limit( mark + encodedLength );
            ByteBuffer in = ByteBuffer.allocate( encodedLength );
            CharsetDecoder decoder = Charset.forName( charsetName ).newDecoder()
                    .onMalformedInput( CodingErrorAction.REPORT )
                    .onUnmappableCharacter( CodingErrorAction.REPORT );
            in.put( buffer );
            in.flip();
            while ( in.limit() > 0 && in.get( in.limit() - 1 ) == 0 ) {
                in.limit( in.limit() - 1 );
            }
            String result = decoder.decode( in ).toString();
            mark += encodedLength;
            return result;
        } finally {
            buffer.limit( limit );
            buffer.position( mark );
        }
    }


    /**
     * Reads the next {@code encodedLength} bytes from the specified buffer and decodes them as a date value.
     *
     * @param buffer the input buffer
     * @param encodedLength the length of the encoded date value
     *
     * @return the decoded date value as a number of milliseconds since January 1, 1970, 00:00:00 GMT.
     *
     * @throws IllegalArgumentException if {@code buffer} is {@code null}
     * @throws IllegalEncodedLengthException if {@code encodedLength} is not equal to {@code 8}
     * @throws BufferUnderflowException if there are fewer than {@code encodedLength} bytes remaining in the input
     * buffer
     */
    public long decodeDate( ByteBuffer buffer, int encodedLength ) {
        if ( buffer == null ) {
            throw new IllegalArgumentException( "buffer is null" );
        }
        if ( encodedLength != 8 ) {
            throw new IllegalEncodedLengthException( String.format( "the length of the encoded date value is invalid: %d", encodedLength ) );
        }
        int mark = buffer.position();
        ByteOrder order = buffer.order();
        try {
            buffer.order( ByteOrder.BIG_ENDIAN );
            long result = buffer.getLong();
            mark += 8;
            return TimeUnit.NANOSECONDS.toMillis( result ) + 978307200000L;
        } finally {
            buffer.position( mark );
            buffer.order( order );
        }
    }

}
