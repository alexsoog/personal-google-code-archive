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

import java.nio.BufferOverflowException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.CharBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.CodingErrorAction;
import java.text.Normalizer;
import java.util.concurrent.TimeUnit;

/** Encodes data into the EBML format. */
public final class EbmlEncoder {

    /** Creates a new {@code EbmlEncoder} object. */
    public EbmlEncoder() {
    }


    /**
     * Writes the variable-length integer to the specified buffer.
     *
     * @param buffer the output buffer
     * @param value the variable-length integer to write
     *
     * @throws IllegalArgumentException if {@code buffer} or {@code value} is {@code null}
     * @throws BufferOverflowException if there are fewer than required bytes remaining in the output buffer
     */
    public void encodeVariableLengthInteger( ByteBuffer buffer, VariableLengthInteger value ) {
        if ( buffer == null ) {
            throw new IllegalArgumentException( "buffer is null" );
        }
        if ( value == null ) {
            throw new IllegalArgumentException( "variableLengthInteger is null" );
        }
        int mark = buffer.position();
        try {
            ByteBuffer temp = ByteBuffer.allocate( 8 );
            temp.putLong( value.getEncodedValue() );
            temp.flip();
            temp.position( 8 - value.getEncodedLength() );
            buffer.put( temp );
            mark += value.getEncodedLength();
        } finally {
            buffer.position( mark );
        }
    }


    /**
     * Returns the minimum number of bytes required to encode the specified value as a signed integer.
     *
     * @param value the value
     *
     * @return the minimum size of the value in the encoded form
     */
    public int getMinimumEncodedSignedIntegerLength( long value ) {
        int encodedLength = 0;
        long mask = 0xffffffffffffff80L;
        long expected = value < 0L ? mask : 0L;
        while ( encodedLength < 8 && ( mask & value ) != expected ) {
            encodedLength++;
            mask <<= 8;
            expected <<= 8;
        }
        return encodedLength;
    }

    /**
     * Writes the signed integer to the specified buffer.
     *
     * @param buffer the output buffer
     * @param value the signed integer to write
     * @param encodedLength the required length of the {@code value} in the encoded form
     *
     * @throws IllegalArgumentException if {@code buffer} is {@code null}
     * @throws IllegalEncodedLengthException if {@code encodedLength} is negative or greater then {@code 8}, or if it is
     * less than the minimally possible length of the specified value in the encoded form
     * @throws BufferOverflowException if there are fewer than {@code encodedLength} bytes remaining in the output
     * buffer
     */
    public void encodeSignedInteger( ByteBuffer buffer, long value, int encodedLength ) {
        if ( buffer == null ) {
            throw new IllegalArgumentException( "buffer is null" );
        }
        if ( encodedLength < 0 || encodedLength > 8 ) {
            throw new IllegalEncodedLengthException( String.format( "the length of the encoded signed integer value is invalid: %d", encodedLength ) );
        }
        int minimumEncodedLength = getMinimumEncodedSignedIntegerLength( value );
        if ( encodedLength < minimumEncodedLength ) {
            throw new IllegalEncodedLengthException( String.format( "the minimum length of the encoded signed integer value %#018xL is %d, but %d was requested", value, minimumEncodedLength, encodedLength ) );
        }
        if ( encodedLength > 0 ) {
            int mark = buffer.position();
            try {
                ByteBuffer temp = ByteBuffer.allocate( 8 );
                temp.putLong( value );
                temp.flip();
                temp.position( 8 - encodedLength );
                buffer.put( temp );
                mark += encodedLength;
            } finally {
                buffer.position( mark );
            }
        }
    }


    /**
     * Returns the minimum number of bytes required to encode the specified value as an unsigned integer.
     *
     * @param value the value
     *
     * @return the minimum size of the value in the encoded form
     */
    public int getMinimumEncodedUnsignedIntegerLength( long value ) {
        int encodedLength = 0;
        long mask = 0xffffffffffffffffL;
        while ( encodedLength < 8 && ( mask & value ) != 0L ) {
            encodedLength++;
            mask <<= 8;
        }
        return encodedLength;
    }

    /**
     * Writes the unsigned integer to the specified buffer.
     *
     * @param buffer the output buffer
     * @param value the unsigned integer to write
     * @param encodedLength the required length of the {@code value} in the encoded form
     *
     * @throws IllegalArgumentException if {@code buffer} is {@code null}
     * @throws IllegalEncodedLengthException if {@code encodedLength} is negative or greater then {@code 8}, or if it is
     * less than the minimally possible length of the specified value in the encoded form
     * @throws BufferOverflowException if there are fewer than {@code encodedLength} bytes remaining in the output
     * buffer
     */
    public void encodeUnsignedInteger( ByteBuffer buffer, long value, int encodedLength ) {
        if ( buffer == null ) {
            throw new IllegalArgumentException( "buffer is null" );
        }
        if ( encodedLength < 0 || encodedLength > 8 ) {
            throw new IllegalEncodedLengthException( String.format( "the length of the encoded unsigned integer value is invalid: %d", encodedLength ) );
        }
        int minimumEncodedLength = getMinimumEncodedUnsignedIntegerLength( value );
        if ( encodedLength < minimumEncodedLength ) {
            throw new IllegalEncodedLengthException( String.format( "the minimum length of the encoded unsigned integer value %#018xL is %d, but %d was requested", value, minimumEncodedLength, encodedLength ) );
        }
        if ( encodedLength > 0 ) {
            int mark = buffer.position();
            try {
                ByteBuffer temp = ByteBuffer.allocate( 8 );
                temp.putLong( value );
                temp.flip();
                temp.position( 8 - encodedLength );
                buffer.put( temp );
                mark += encodedLength;
            } finally {
                buffer.position( mark );
            }
        }
    }


    /**
     * Writes the floating-point number to the specified buffer.
     *
     * @param buffer the output buffer
     * @param value the floating-point number to write
     * @param encodedLength the required length of the {@code value} in the encoded form
     *
     * @throws IllegalArgumentException if {@code buffer} is {@code null}
     * @throws IllegalEncodedLengthException if {@code encodedLength} is not equal to {@code 0}, {@code 4} or {@code 8},
     * or if it is less than the minimally possible length of the specified value in the encoded form
     * @throws BufferOverflowException if there are fewer than {@code encodedLength} bytes remaining in the output
     * buffer
     */
    public void encodeFloatingPoint( ByteBuffer buffer, double value, int encodedLength ) {
        if ( buffer == null ) {
            throw new IllegalArgumentException( "buffer is null" );
        }
        if ( encodedLength < 0 || encodedLength != 0 && encodedLength != 4 && encodedLength != 8 ) {
            throw new IllegalEncodedLengthException( String.format( "the length of the encoded floating-point value is invalid: %d", encodedLength ) );
        }
        if ( encodedLength > 0 ) {
            int mark = buffer.position();
            ByteOrder order = buffer.order();
            try {
                buffer.order( ByteOrder.BIG_ENDIAN );
                if ( encodedLength == 4 ) {
                    buffer.putFloat( ( float ) value );
                } else {
                    buffer.putDouble( value );
                }
                mark += encodedLength;
            } finally {
                buffer.position( mark );
                buffer.order( order );
            }
        } else {
            if ( Double.compare( 0.0, value ) != 0 ) {
                throw new IllegalEncodedLengthException( String.format( "the minimum length of the encoded floating-point number value %s is %d, but %d was requested", value, 4, encodedLength ) );
            }
        }
    }


    /**
     * Returns the minimum number of bytes required to encode the specified value as an ASCII string.
     *
     * @param value the value
     *
     * @return the minimum size of the value in the encoded form
     *
     * @throws CharacterCodingException if a character encoding error occurs
     */
    public int getMinimumEncodedAsciiStringLength( String value ) throws CharacterCodingException {
        return getMinimumEncodedStringLength( value, "ASCII" );
    }

    /**
     * Returns the minimum number of bytes required to encode the specified value as an UTF-8 string.
     *
     * @param value the value
     *
     * @return the minimum size of the value in the encoded form
     *
     * @throws CharacterCodingException if a character encoding error occurs
     */
    public int getMinimumEncodedUnicodeStringLength( String value ) throws CharacterCodingException {
        return getMinimumEncodedStringLength( value, "UTF-8" );
    }

    private int getMinimumEncodedStringLength( String value, String charsetName ) throws CharacterCodingException {
        if ( value == null ) {
            throw new NullPointerException( "value is null" );
        }
        if ( !Normalizer.isNormalized( value, Normalizer.Form.NFC ) ) {
            value = Normalizer.normalize( value, Normalizer.Form.NFC );
        }
        CharsetEncoder encoder = Charset.forName( charsetName ).newEncoder()
                .onMalformedInput( CodingErrorAction.REPORT )
                .onUnmappableCharacter( CodingErrorAction.REPORT );
        return encoder.encode( CharBuffer.wrap( value ) ).remaining();
    }


    /**
     * Writes the string to the specified buffer. The string is encoded using ASCII charset.
     *
     * @param buffer the output buffer
     * @param value the string to write
     * @param encodedLength the required length of the {@code value} in the encoded form
     *
     * @throws IllegalArgumentException if {@code buffer} or {@code value} is {@code null}
     * @throws IllegalEncodedLengthException if {@code encodedLength} is negative or less than the minimally possible
     * length of the specified value in the encoded form
     * @throws BufferOverflowException if there are fewer than {@code encodedLength} bytes remaining in the output
     * buffer
     * @throws CharacterCodingException if a character encoding error occurs
     */
    public void encodeAsciiString( ByteBuffer buffer, String value, int encodedLength ) throws CharacterCodingException {
        encodeString( buffer, value, encodedLength, "ASCII" );
    }

    /**
     * Writes the string to the specified buffer. The string is encoded using UTF-8 charset.
     *
     * @param buffer the output buffer
     * @param value the string to write
     * @param encodedLength the required length of the {@code value} in the encoded form
     *
     * @throws NullPointerException if {@code buffer} or {@code value} is {@code null}
     * @throws IllegalEncodedLengthException if {@code encodedLength} is negative or less than the minimally possible
     * length of the specified value in the encoded form
     * @throws BufferOverflowException if there are fewer than {@code encodedLength} bytes remaining in the output
     * buffer
     * @throws CharacterCodingException if a character encoding error occurs
     */
    public void encodeUnicodeString( ByteBuffer buffer, String value, int encodedLength ) throws CharacterCodingException {
        encodeString( buffer, value, encodedLength, "UTF-8" );
    }

    private void encodeString( ByteBuffer buffer, String value, int encodedLength, String charsetName ) throws CharacterCodingException {
        if ( buffer == null ) {
            throw new IllegalArgumentException( "buffer is null" );
        }
        if ( value == null ) {
            throw new IllegalArgumentException( "value is null" );
        }
        if ( encodedLength < 0 ) {
            throw new IllegalEncodedLengthException( String.format( "the length of the encoded %s string value is invalid: %d", charsetName, encodedLength ) );
        }
        if ( !Normalizer.isNormalized( value, Normalizer.Form.NFC ) ) {
            value = Normalizer.normalize( value, Normalizer.Form.NFC );
        }
        CharsetEncoder encoder = Charset.forName( charsetName ).newEncoder()
                .onMalformedInput( CodingErrorAction.REPORT )
                .onUnmappableCharacter( CodingErrorAction.REPORT );
        ByteBuffer bytes = encoder.encode( CharBuffer.wrap( value ) );
        int minimumEncodedLength = bytes.remaining();
        if ( encodedLength < minimumEncodedLength ) {
            throw new IllegalEncodedLengthException( String.format( "the minimum length of the encoded %s string \"%s\" is %d, but %d was requested", charsetName, value, minimumEncodedLength, encodedLength ) );
        }
        if ( encodedLength > 0 ) {
            int mark = buffer.position();
            try {
                buffer.put( bytes );
                for ( int i = minimumEncodedLength; i < encodedLength; i++ ) {
                    buffer.put( ( byte ) 0 );
                }
                mark += encodedLength;
            } finally {
                buffer.position( mark );
            }
        }
    }


    /**
     * Writes the date to the specified buffer.
     *
     * @param buffer the byte buffer
     * @param value the date as a number of milliseconds since January 1, 1970, 00:00:00 GMT
     * @param encodedLength the length of the encoded date value
     *
     * @throws IllegalArgumentException if {@code buffer} is {@code null}
     * @throws IllegalEncodedLengthException if {@code encodedLength} is not equal to {@code 8}
     * @throws BufferOverflowException if there are fewer than {@code encodedLength} bytes remaining in the output
     * buffer
     */
    public void encodeDate( ByteBuffer buffer, long value, int encodedLength ) {
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
            long result = TimeUnit.MILLISECONDS.toNanos( value - 978307200000L );
            buffer.putLong( result );
            mark += 8;
        } finally {
            buffer.position( mark );
            buffer.order( order );
        }
    }

}
