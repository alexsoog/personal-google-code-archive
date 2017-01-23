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
import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static oe.assertions.Assertions.assertThat;
import static oe.assertions.Predicates.isEqualTo;
import static oe.assertions.Predicates.isSameAs;

public class FloatingPointTest {

    @Test( expectedExceptions = IllegalArgumentException.class )
    public void decodeWithNullBufferFails() {
        EbmlDecoder decoder = new EbmlDecoder();
        decoder.decodeFloatingPoint( null, 0 );
    }

    @Test( expectedExceptions = BufferUnderflowException.class )
    public void decodeWithBufferUnderflow() {
        EbmlDecoder decoder = new EbmlDecoder();
        ByteBuffer buffer = ByteBuffer.allocate( 32 );
        buffer.limit( 7 );
        int position = buffer.position();
        try {
            decoder.decodeFloatingPoint( buffer, 8 );
        } finally {
            assertThat( buffer.position(), isEqualTo( position ) );
        }
    }


    @Test( expectedExceptions = IllegalArgumentException.class )
    public void encodeWithNullBufferFails() {
        EbmlEncoder encoder = new EbmlEncoder();
        encoder.encodeFloatingPoint( null, 0.0, 8 );
    }

    @Test( expectedExceptions = BufferOverflowException.class )
    public void encodeWithBufferOverflow() {
        EbmlEncoder encoder = new EbmlEncoder();
        ByteBuffer buffer = ByteBuffer.allocate( 32 );
        buffer.limit( 7 );
        int position = buffer.position();
        try {
            encoder.encodeFloatingPoint( buffer, 0.0, 8 );
        } finally {
            assertThat( buffer.position(), isEqualTo( position ) );
        }
    }


    @Test( dataProvider = "doublePrecisionData" )
    public void roundtripSinglePrecision( double value ) {
        EbmlEncoder encoder = new EbmlEncoder();
        EbmlDecoder decoder = new EbmlDecoder();
        ByteBuffer buffer = ByteBuffer.allocate( 32 );
        float write = ( float ) value;
        encoder.encodeFloatingPoint( buffer, write, 4 );
        buffer.flip();
        float read = ( float ) decoder.decodeFloatingPoint( buffer, 4 );
        assertThat( read, isSameAs( write ) );
    }

    @Test( dataProvider = "doublePrecisionData" )
    public void roundtripDoublePrecision( double value ) {
        EbmlEncoder encoder = new EbmlEncoder();
        EbmlDecoder decoder = new EbmlDecoder();
        ByteBuffer buffer = ByteBuffer.allocate( 32 );
        encoder.encodeFloatingPoint( buffer, value, 8 );
        buffer.flip();
        double read = decoder.decodeFloatingPoint( buffer, 8 );
        assertThat( read, isSameAs( value ) );
    }


    @DataProvider( name = "doublePrecisionData" )
    public Object[][] getDoublePrecisionData() {
        return new Object[][] {
                { 0.0 },
                { -0.0 },
                { 1.0 },
                { -1.0 },
                { Math.PI },
                { Math.E },
                { Double.MAX_VALUE },
                { Double.MIN_NORMAL },
                { Double.MIN_VALUE },
                { Double.NaN },
                { Double.NEGATIVE_INFINITY },
                { Double.POSITIVE_INFINITY },
        };
    }

}
