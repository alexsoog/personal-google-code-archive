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

import org.testng.annotations.Test;

import static oe.assertions.Assertions.assertThat;
import static oe.assertions.Predicates.isNull;
import static oe.assertions.Statement.value;


public class AssertionsTest {

    @Test
    public void assertThatWithOnePredicate() {
        Object value = null;
        assertThat( value, isNull() );
    }

    @Test
    public void assertThatWithTwoPredicates() {
        Object value = null;
        assertThat( value, isNull(), isNull() );
    }

    @Test
    public void assertThatWithThreePredicates() {
        Object value = null;
        assertThat( value, isNull(), isNull(), isNull() );
    }

    @Test
    @SuppressWarnings( "unchecked" )
    public void assertThatWithVarargPredicates() {
        Object value = null;
        assertThat( value, isNull(), isNull(), isNull(), isNull() );
    }


    @Test
    public void assertThatWithOneStatement() {
        Object value = null;
        assertThat( value( value ).describedAs( "test value" ).satisfies( isNull() ) );
    }

    @Test
    public void assertThatWithVarargStatements() {
        Object value = null;
        assertThat(
                value( value ).describedAs( "test value" ).satisfies( isNull() ),
                value( value ).describedAs( "test value" ).satisfies( isNull() ),
                value( value ).describedAs( "test value" ).satisfies( isNull() )
        );
    }

}
