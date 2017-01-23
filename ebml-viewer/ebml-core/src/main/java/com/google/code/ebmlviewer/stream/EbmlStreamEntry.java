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

import com.google.code.ebmlviewer.core.VariableLengthInteger;

/** Contains the entry data. */
final class EbmlStreamEntry {

    private final VariableLengthInteger identifier;

    private final VariableLengthInteger size;

    private long remaining;

    private boolean touched;


    EbmlStreamEntry( VariableLengthInteger identifier, VariableLengthInteger size ) {
        if ( identifier == null ) {
            throw new IllegalArgumentException( "identifier is null" );
        }
        if ( size == null ) {
            throw new IllegalArgumentException( "size is null" );
        }
        this.identifier = identifier;
        this.size = size;
        remaining = size.getPlainValue();
    }


    public VariableLengthInteger getIdentifier() {
        return identifier;
    }

    public VariableLengthInteger getSize() {
        return size;
    }


    public long getRemaining() {
        return remaining;
    }

    public void decreaseRemaining( long decrement ) {
        remaining -= decrement;
    }

    public boolean hasRemaining() {
        return remaining > 0L;
    }


    public boolean isTouched() {
        return touched;
    }

    public void touch() {
        touched = true;
    }

}
