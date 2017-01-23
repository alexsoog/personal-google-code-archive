/*-
 * Copyright (c) 2008-2010, Oleg Estekhin
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

package oe.assertions;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/** An assertion is a set of a statements that should all be true for the assertion to pass. */
final class Assertion {

    private final List<Statement<?>> statements;


    /**
     * Creates a new assertion.
     *
     * @param statements the list of assertion statements
     *
     * @throws IllegalArgumentException if {@code statements} is {@code null}, or empty, or contains {@code null}
     * elements
     */
    Assertion( List<Statement<?>> statements ) {
        if ( statements == null ) {
            throw new IllegalArgumentException( "statements list is null" );
        }
        if ( statements.isEmpty() ) {
            throw new IllegalArgumentException( "statements list is empty" );
        }
        if ( statements.contains( null ) ) {
            throw new IllegalArgumentException( "statements list contains null" );
        }
        this.statements = Collections.unmodifiableList( new ArrayList<Statement<?>>( statements ) );
    }


    /**
     * Checks this assertion.
     *
     * @throws AssertionError if assertion fails
     */
    public void check() {
        Message message = new Message();
        boolean result = true;
        for ( Statement<?> statement : statements ) {
            if ( !statement.check() ) {
                message.eol();
                statement.describe( message );
                message.flush();
                result = false;
            }
        }
        if ( !result ) {
            message.flush();
            throw new AssertionError( message.toString() );
        }
    }

}
