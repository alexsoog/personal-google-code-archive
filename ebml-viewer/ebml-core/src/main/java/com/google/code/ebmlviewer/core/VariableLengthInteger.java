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

/** The variable-length integer in the EBML format. */
public final class VariableLengthInteger {

    private static final long[] DESCRIPTOR_MASK = {
            0L,
            0x80L,
            0x4000L,
            0x200000L,
            0x10000000L,
            0x0800000000L,
            0x040000000000L,
            0x02000000000000L,
            0x0100000000000000L,
    };

    private static final long[] VALUE_MASK = {
            0L,
            0x7fL,
            0x3fffL,
            0x1fffffL,
            0x0fffffffL,
            0x07ffffffffL,
            0x03ffffffffffL,
            0x01ffffffffffffL,
            0x00ffffffffffffffL,
    };


    /** The maximum plain value a variable-length integer can have, {@value}. */
    public static final long MAXIMUM_PLAIN_VALUE = 0x00fffffffffffffeL;


    /**
     * Creates a variable-length integer with the specified plain value.
     *
     * @param plainValue the plain value
     *
     * @return a new {@code VariableLengthInteger} object
     *
     * @throws IllegalArgumentException if {@code plainValue} is negative or greater than {@value #MAXIMUM_PLAIN_VALUE}
     */
    public static VariableLengthInteger fromPlain( long plainValue ) {
        if ( plainValue < 0L || plainValue > MAXIMUM_PLAIN_VALUE ) {
            throw new IllegalArgumentException( String.format( "plain value is out of valid range: %#018xL", plainValue ) );
        }
        int encodedLength = 1;
        while ( ( plainValue & VALUE_MASK[ encodedLength ] ) != plainValue ) {
            encodedLength++;
        }
        if ( ( plainValue & VALUE_MASK[ encodedLength ] ) == VALUE_MASK[ encodedLength ] ) {
            encodedLength++;
        }
        return new VariableLengthInteger( plainValue, plainValue | DESCRIPTOR_MASK[ encodedLength ], encodedLength );
    }

    /**
     * Creates a variable-length integer with the specified plain value.
     *
     * @param plainValue the plain value
     * @param encodedLength the required length of the encoded value
     *
     * @return a new {@code VariableLengthInteger} object
     *
     * @throws IllegalArgumentException if {@code plainValue} is negative or greater than {@value #MAXIMUM_PLAIN_VALUE}
     * @throws IllegalEncodedLengthException if {@code encodedLength} is less than {@code 0} of greater than {@code 8}
     * @throws EbmlFormatException if it is not possible to encode the {@code plainValue} as a variable-length integer
     * of the specified length
     */
    public static VariableLengthInteger fromPlain( long plainValue, int encodedLength ) {
        if ( plainValue < 0L || plainValue > MAXIMUM_PLAIN_VALUE ) {
            throw new IllegalArgumentException( String.format( "plain value is out of valid range: %#018xL", plainValue ) );
        }
        if ( encodedLength < 0 || encodedLength > 8 ) {
            throw new IllegalEncodedLengthException( String.format( "invalid length for an encoded variable-length integer value: %d", encodedLength ) );
        }
        if ( ( plainValue & VALUE_MASK[ encodedLength ] ) != plainValue
                || ( plainValue & VALUE_MASK[ encodedLength ] ) == VALUE_MASK[ encodedLength ] ) {
            throw new EbmlFormatException( String.format( "plain value %#018xL can not be encoded as a variable-length integer of length %d", plainValue, encodedLength ) );
        }
        return new VariableLengthInteger( plainValue, plainValue | DESCRIPTOR_MASK[ encodedLength ], encodedLength );
    }


    /**
     * Creates a variable-length integer with the specified encoded value.
     *
     * @param encodedValue the encoded value
     *
     * @return a new {@code VariableLengthInteger} object
     *
     * @throws EbmlFormatException if {@code encodedValue} is not a valid encoded representation of a variable-length
     * integer
     */
    public static VariableLengthInteger fromEncoded( long encodedValue ) {
        int encodedLength = 1;
        while ( encodedLength <= 8
                && ( encodedValue & VALUE_MASK[ encodedLength ] ) != ( encodedValue ^ DESCRIPTOR_MASK[ encodedLength ] ) ) {
            encodedLength++;
        }
        if ( encodedLength > 8 ) {
            throw new EbmlFormatException( String.format( "%#xL is not a valid encoded representation of a variable-length integer", encodedValue ) );
        }
        return new VariableLengthInteger( encodedValue & VALUE_MASK[ encodedLength ], encodedValue, encodedLength );
    }

    /**
     * Creates a variable-length integer with the specified encoded value.
     *
     * @param encodedValue the encoded value
     * @param encodedLength the length of the encoded value
     *
     * @return a new {@code VariableLengthInteger} object
     *
     * @throws IllegalEncodedLengthException if {@code encodedLength} is less than {@code 0} of greater than {@code 8}
     * @throws EbmlFormatException if {@code encodedValue} is not a valid encoded representation of a variable-length
     * integer of the specified length
     */
    public static VariableLengthInteger fromEncoded( long encodedValue, int encodedLength ) {
        if ( encodedLength < 0 || encodedLength > 8 ) {
            throw new IllegalEncodedLengthException( String.format( "invalid length for an encoded variable-length integer value: %d", encodedLength ) );
        }
        if ( ( encodedValue & VALUE_MASK[ encodedLength ] ) != ( encodedValue ^ DESCRIPTOR_MASK[ encodedLength ] ) ) {
            throw new EbmlFormatException( String.format( "%#xL is not a valid encoded representation of a variable-length integer of length %d", encodedValue, encodedLength ) );
        }
        return new VariableLengthInteger( encodedValue & VALUE_MASK[ encodedLength ], encodedValue, encodedLength );
    }

    /**
     * Decodes a string into an {@code VariableLengthInteger}.
     *
     * @param s the string to decode
     *
     * @return an {@code VariableLengthInteger} object corresponding to the input string
     *
     * @throws IllegalArgumentException if the input string does not contain a parsable {@code VariableLengthInteger}
     */
    public static VariableLengthInteger fromString( String s ) {
        if ( s.startsWith( "0x" ) || s.startsWith( "0X" ) ) {
            s = s.substring( 2 );
        } else {
            throw new IllegalArgumentException( String.format( "unable to decode \"%s\" as a variable-length integer", s ) );
        }
        return fromEncoded( Long.parseLong( s, 16 ) );
    }


    private final long plainValue;

    private final long encodedValue;

    private final int encodedLength;


    /**
     * Creates a new {@code VariableLengthInteger} object.
     *
     * @param plainValue the plain value
     * @param encodedValue the encoded value
     * @param encodedLength the length of the encoded value
     */
    private VariableLengthInteger( long plainValue, long encodedValue, int encodedLength ) {
        this.plainValue = plainValue;
        this.encodedValue = encodedValue;
        this.encodedLength = encodedLength;
    }


    /**
     * Returns the plain value of this variable-length integer.
     *
     * @return the plain value
     */
    public long getPlainValue() {
        return plainValue;
    }

    /**
     * Returns the encoded value of this variable-length integer.
     *
     * @return the encoded value
     */
    public long getEncodedValue() {
        return encodedValue;
    }

    /**
     * Returns the length of the encoded value.
     *
     * @return the length of the encoded value
     */
    public int getEncodedLength() {
        return encodedLength;
    }


    /**
     * Returns whether this variable-length integer represents valid identifier.
     *
     * @return {@code true} if the encoded value of this variable-length integer is valid identifier; {@code false}
     *         otherwise
     */
    public boolean isIdentifier() {
        return plainValue != VALUE_MASK[ encodedLength ] &&
                ( encodedLength == 1
                        || ( plainValue & VALUE_MASK[ encodedLength - 1 ] ) != plainValue
                        || ( plainValue & VALUE_MASK[ encodedLength - 1 ] ) == VALUE_MASK[ encodedLength - 1 ] );
    }

    /**
     * Returns whether this variable-length integer has reserved encoded value.
     *
     * @return {@code true} if the encoded value of this variable-length integer is reserved; {@code false} otherwise
     */
    public boolean isReserved() {
        return plainValue == VALUE_MASK[ encodedLength ];
    }


    @Override
    public int hashCode() {
        int result = 17;
        result = 37 * result + ( int ) ( plainValue ^ plainValue >>> 32 );
        result = 37 * result + ( int ) ( encodedValue ^ encodedValue >>> 32 );
        result = 37 * result + encodedLength;
        return result;
    }

    @Override
    public boolean equals( Object object ) {
        if ( this == object ) {
            return true;
        } else if ( object instanceof VariableLengthInteger ) {
            VariableLengthInteger other = ( VariableLengthInteger ) object;
            return plainValue == other.plainValue
                    && encodedValue == other.encodedValue
                    && encodedLength == other.encodedLength;
        } else {
            return false;
        }
    }

    @Override
    public String toString() {
        StringBuilder buffer = new StringBuilder();
        buffer.append( "0x" );
        String s = Long.toHexString( encodedValue ).toUpperCase();
        if ( s.length() != encodedLength * 2 ) {
            buffer.append( '0' );
        }
        buffer.append( s );
        return buffer.toString();
    }

}
