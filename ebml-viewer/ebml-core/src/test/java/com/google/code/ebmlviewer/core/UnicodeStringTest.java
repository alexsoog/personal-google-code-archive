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

import java.io.UnsupportedEncodingException;
import java.nio.BufferOverflowException;
import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;
import java.nio.charset.CharacterCodingException;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static oe.assertions.Assertions.assertThat;
import static oe.assertions.Predicates.isEqualTo;

public class UnicodeStringTest {

    @Test( expectedExceptions = IllegalArgumentException.class )
    public void decodeWithNullBufferFails() throws CharacterCodingException {
        EbmlDecoder decoder = new EbmlDecoder();
        decoder.decodeUnicodeString( null, 0 );
    }

    @Test( expectedExceptions = BufferUnderflowException.class )
    public void decodeWithBufferUnderflow() throws CharacterCodingException, UnsupportedEncodingException {
        EbmlDecoder decoder = new EbmlDecoder();
        ByteBuffer buffer = ByteBuffer.allocate( 128 );
        buffer.put( "test".getBytes( "UTF-8" ) );
        buffer.flip();
        buffer.limit( 3 );
        int position = buffer.position();
        int limit = buffer.limit();
        try {
            decoder.decodeUnicodeString( buffer, 4 );
        } finally {
            assertThat( buffer.position(), isEqualTo( position ) );
            assertThat( buffer.limit(), isEqualTo( limit ) );
        }
    }

    @Test( expectedExceptions = IllegalArgumentException.class )
    public void encodeWithNullBufferFails() throws CharacterCodingException {
        EbmlEncoder encoder = new EbmlEncoder();
        encoder.encodeUnicodeString( null, "", 0 );
    }

    @Test( expectedExceptions = BufferOverflowException.class )
    public void encodeWithBufferOverflow() throws CharacterCodingException, UnsupportedEncodingException {
        EbmlEncoder encoder = new EbmlEncoder();
        ByteBuffer buffer = ByteBuffer.allocate( 128 );
        buffer.limit( 3 );
        int position = buffer.position();
        int limit = buffer.limit();
        try {
            encoder.encodeUnicodeString( buffer, "test", 4 );
        } finally {
            assertThat( buffer.position(), isEqualTo( position ) );
            assertThat( buffer.limit(), isEqualTo( limit ) );
        }
    }


    @Test( dataProvider = "data" )
    public void roundtrip( String value, int encodedValueLength ) throws CharacterCodingException, UnsupportedEncodingException {
        EbmlEncoder encoder = new EbmlEncoder();
        EbmlDecoder decoder = new EbmlDecoder();
        ByteBuffer buffer = ByteBuffer.allocate( 128 );
        encoder.encodeUnicodeString( buffer, value, encodedValueLength );
        buffer.flip();
        String read = decoder.decodeUnicodeString( buffer, encodedValueLength );
        assertThat( read, isEqualTo( value ) );
    }


    @DataProvider( name = "data" )
    public Object[][] getData() {
        return new Object[][] {
                { "", 0 },
                { "test", 4 },
                { "abcdefghijklmnopqrstuvwxyz", 26 },
                { "0123456789", 10 },
                { "абвгдеёжзийклмнопрстуфхцчшщъыьэюя", 66 },
        };
    }

}
