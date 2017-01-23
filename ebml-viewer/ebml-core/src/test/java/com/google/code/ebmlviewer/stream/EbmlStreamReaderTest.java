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

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

import org.testng.annotations.Test;
import com.google.code.ebmlviewer.core.EbmlEncoder;
import com.google.code.ebmlviewer.core.VariableLengthInteger;

import static oe.assertions.Assertions.assertThat;
import static oe.assertions.Predicates.isEqualTo;
import static oe.assertions.Predicates.isFalse;
import static oe.assertions.Predicates.isSameAs;
import static oe.assertions.Predicates.isTrue;

public class EbmlStreamReaderTest {

    @Test
    public void signedInteger() throws IOException {
        VariableLengthInteger identifier = VariableLengthInteger.fromEncoded( 0xec );
        VariableLengthInteger size = VariableLengthInteger.fromPlain( 8 );
        long value = 0x1122334455667788L;

        ByteBuffer buffer = ByteBuffer.allocate( 128 );

        EbmlEncoder encoder = new EbmlEncoder();
        encoder.encodeVariableLengthInteger( buffer, identifier );
        encoder.encodeVariableLengthInteger( buffer, size );
        encoder.encodeSignedInteger( buffer, value, 8 );
        buffer.flip();

        ByteArrayInputStream inputStream = new ByteArrayInputStream( buffer.array(), buffer.arrayOffset(), buffer.remaining() );
        EbmlStreamReader reader = new EbmlStreamReader( inputStream );
        try {
            assertThat( reader.next(), isTrue() );
            assertThat( reader.getIdentifier(), isEqualTo( identifier ) );
            assertThat( reader.getSize(), isEqualTo( size ) );
            assertThat( reader.readSignedInteger(), isEqualTo( value ) );
            assertThat( reader.next(), isFalse() );
        } finally {
            reader.close();
        }
    }

    @Test
    public void unsignedInteger() throws IOException {
        VariableLengthInteger identifier = VariableLengthInteger.fromEncoded( 0xec );
        VariableLengthInteger size = VariableLengthInteger.fromPlain( 8 );
        long value = 0x1122334455667788L;

        ByteBuffer buffer = ByteBuffer.allocate( 128 );

        EbmlEncoder encoder = new EbmlEncoder();
        encoder.encodeVariableLengthInteger( buffer, identifier );
        encoder.encodeVariableLengthInteger( buffer, size );
        encoder.encodeUnsignedInteger( buffer, value, 8 );
        buffer.flip();

        ByteArrayInputStream inputStream = new ByteArrayInputStream( buffer.array(), buffer.arrayOffset(), buffer.remaining() );
        EbmlStreamReader reader = new EbmlStreamReader( inputStream );
        try {
            assertThat( reader.next(), isTrue() );
            assertThat( reader.getIdentifier(), isEqualTo( identifier ) );
            assertThat( reader.getSize(), isEqualTo( size ) );
            assertThat( reader.readUnsignedInteger(), isEqualTo( value ) );
            assertThat( reader.next(), isFalse() );
        } finally {
            reader.close();
        }
    }

    @Test
    public void floatingPoint() throws IOException {
        VariableLengthInteger identifier = VariableLengthInteger.fromEncoded( 0xec );
        VariableLengthInteger size = VariableLengthInteger.fromPlain( 8 );
        double value = Math.PI;

        ByteBuffer buffer = ByteBuffer.allocate( 128 );

        EbmlEncoder encoder = new EbmlEncoder();
        encoder.encodeVariableLengthInteger( buffer, identifier );
        encoder.encodeVariableLengthInteger( buffer, size );
        encoder.encodeFloatingPoint( buffer, value, 8 );
        buffer.flip();

        ByteArrayInputStream inputStream = new ByteArrayInputStream( buffer.array(), buffer.arrayOffset(), buffer.remaining() );
        EbmlStreamReader reader = new EbmlStreamReader( inputStream );
        try {
            assertThat( reader.next(), isTrue() );
            assertThat( reader.getIdentifier(), isEqualTo( identifier ) );
            assertThat( reader.getSize(), isEqualTo( size ) );
            assertThat( reader.readFloatingPoint(), isSameAs( value ) );
            assertThat( reader.next(), isFalse() );
        } finally {
            reader.close();
        }
    }

    @Test
    public void asciiString() throws IOException {
        VariableLengthInteger identifier = VariableLengthInteger.fromEncoded( 0xec );
        VariableLengthInteger size = VariableLengthInteger.fromPlain( 26 );
        String value = "abcdefghijklmnopqrstuvwxyz";

        ByteBuffer buffer = ByteBuffer.allocate( 128 );

        EbmlEncoder encoder = new EbmlEncoder();
        encoder.encodeVariableLengthInteger( buffer, identifier );
        encoder.encodeVariableLengthInteger( buffer, size );
        encoder.encodeAsciiString( buffer, value, 26 );
        buffer.flip();

        ByteArrayInputStream inputStream = new ByteArrayInputStream( buffer.array(), buffer.arrayOffset(), buffer.remaining() );
        EbmlStreamReader reader = new EbmlStreamReader( inputStream );
        try {
            assertThat( reader.next(), isTrue() );
            assertThat( reader.getIdentifier(), isEqualTo( identifier ) );
            assertThat( reader.getSize(), isEqualTo( size ) );
            assertThat( reader.readAsciiString(), isEqualTo( value ) );
            assertThat( reader.next(), isFalse() );
        } finally {
            reader.close();
        }
    }

    @Test
    public void unicodeString() throws IOException {
        VariableLengthInteger identifier = VariableLengthInteger.fromEncoded( 0xec );
        VariableLengthInteger size = VariableLengthInteger.fromPlain( 26 );
        String value = "abcdefghijklmnopqrstuvwxyz";

        ByteBuffer buffer = ByteBuffer.allocate( 128 );

        EbmlEncoder encoder = new EbmlEncoder();
        encoder.encodeVariableLengthInteger( buffer, identifier );
        encoder.encodeVariableLengthInteger( buffer, size );
        encoder.encodeUnicodeString( buffer, value, 26 );
        buffer.flip();

        ByteArrayInputStream inputStream = new ByteArrayInputStream( buffer.array(), buffer.arrayOffset(), buffer.remaining() );
        EbmlStreamReader reader = new EbmlStreamReader( inputStream );
        try {
            assertThat( reader.next(), isTrue() );
            assertThat( reader.getIdentifier(), isEqualTo( identifier ) );
            assertThat( reader.getSize(), isEqualTo( size ) );
            assertThat( reader.readUnicodeString(), isEqualTo( value ) );
            assertThat( reader.next(), isFalse() );
        } finally {
            reader.close();
        }
    }

    @Test
    public void date() throws IOException {
        VariableLengthInteger identifier = VariableLengthInteger.fromEncoded( 0xec );
        VariableLengthInteger size = VariableLengthInteger.fromPlain( 8 );
        long value = 978307200000L; // 2001-01-01T00:00:00,000000000 UTC;

        ByteBuffer buffer = ByteBuffer.allocate( 128 );

        EbmlEncoder encoder = new EbmlEncoder();
        encoder.encodeVariableLengthInteger( buffer, identifier );
        encoder.encodeVariableLengthInteger( buffer, size );
        encoder.encodeDate( buffer, value, 8 );
        buffer.flip();

        ByteArrayInputStream inputStream = new ByteArrayInputStream( buffer.array(), buffer.arrayOffset(), buffer.remaining() );
        EbmlStreamReader reader = new EbmlStreamReader( inputStream );
        try {
            assertThat( reader.next(), isTrue() );
            assertThat( reader.getIdentifier(), isEqualTo( identifier ) );
            assertThat( reader.getSize(), isEqualTo( size ) );
            assertThat( reader.readDate(), isEqualTo( value ) );
            assertThat( reader.next(), isFalse() );
        } finally {
            reader.close();
        }
    }

}
