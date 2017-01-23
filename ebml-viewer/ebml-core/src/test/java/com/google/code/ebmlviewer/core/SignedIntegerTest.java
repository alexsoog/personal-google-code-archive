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

import org.testng.annotations.Test;

import static oe.assertions.Assertions.assertThat;
import static oe.assertions.Predicates.isEqualTo;

public class SignedIntegerTest {

    @Test( expectedExceptions = IllegalArgumentException.class )
    public void decodeWithNullBufferFails() {
        EbmlDecoder decoder = new EbmlDecoder();
        decoder.decodeSignedInteger( null, 0 );
    }

    @Test( expectedExceptions = BufferUnderflowException.class )
    public void decodeWithBufferUnderflow() {
        EbmlDecoder decoder = new EbmlDecoder();
        ByteBuffer buffer = ByteBuffer.allocate( 32 );
        buffer.limit( 7 );
        int position = buffer.position();
        try {
            decoder.decodeSignedInteger( buffer, 8 );
        } finally {
            assertThat( buffer.position(), isEqualTo( position ) );
        }
    }


    @Test( expectedExceptions = IllegalArgumentException.class )
    public void encodeWithNullBufferFails() {
        EbmlEncoder encoder = new EbmlEncoder();
        encoder.encodeSignedInteger( null, 0L, 8 );
    }

    @Test( expectedExceptions = BufferOverflowException.class )
    public void encodeWithBufferOverflow() {
        EbmlEncoder encoder = new EbmlEncoder();
        ByteBuffer buffer = ByteBuffer.allocate( 32 );
        buffer.limit( 7 );
        int position = buffer.position();
        try {
            encoder.encodeSignedInteger( buffer, 0L, 8 );
        } finally {
            assertThat( buffer.position(), isEqualTo( position ) );
        }
    }


    @Test
    public void roundtripPositive() {
        EbmlEncoder encoder = new EbmlEncoder();
        EbmlDecoder decoder = new EbmlDecoder();
        ByteBuffer buffer = ByteBuffer.allocate( 32 );
        long write = 0x1122334455667788L;
        for ( int i = 8; i >= 0; i-- ) {
            buffer.clear();
            encoder.encodeSignedInteger( buffer, write, i );
            buffer.flip();
            long read = decoder.decodeSignedInteger( buffer, i );
            assertThat( read, isEqualTo( write ) );
            write >>= 8;
        }
    }

    @Test
    public void roundtripNegative() {
        EbmlEncoder encoder = new EbmlEncoder();
        EbmlDecoder decoder = new EbmlDecoder();
        ByteBuffer buffer = ByteBuffer.allocate( 32 );
        long write = 0x8877665544332211L;
        for ( int i = 8; i > 0; i-- ) {
            buffer.clear();
            encoder.encodeSignedInteger( buffer, write, i );
            buffer.flip();
            long read = decoder.decodeSignedInteger( buffer, i );
            assertThat( read, isEqualTo( write ) );
            write >>= 8;
        }
    }

}
