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

package oe.assertions.predicates.strings;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import oe.assertions.predicates.PredicateTestBase;

import static oe.assertions.Assertions.assertThat;
import static oe.assertions.Predicates.contains;

public class ContainsTest extends PredicateTestBase {

    private void compilesWithNull() {
        assertThat( null, contains( "string" ) );
    }

    private void compilesWithString() {
        assertThat( "string", contains( "string" ) );
    }


    @Test( expectedExceptions = IllegalArgumentException.class )
    public void throwsIAEIfSubstringIsNull() {
        contains( null );
    }


    @Test
    public void evaluatesToFalseIfInputIsNull() {
        assertEvaluatesToFalse( null, contains( "substring" ) );
    }


    @Test( dataProvider = "inputContainsSubstring" )
    public void evaluatesToTrueIfInputContainsSubstring( String input, String substring ) {
        assertEvaluatesToTrue( input, contains( substring ) );
    }

    @Test( dataProvider = "inputDoesNotContainSubstring" )
    public void evaluatesToTrueIfInputDoesNotContainSubstring( String input, String substring ) {
        assertEvaluatesToFalse( input, contains( substring ) );
    }


    @Test
    public void producesExpectedMessage() {
        assertProducesExpectedMessage( "string", contains( "substring" ), "contains \"substring\"" );
    }


    @DataProvider
    public static Object[][] inputContainsSubstring() {
        return new Object[][] {
                { "prefix string suffix", "string" },
                { "prefix string suffix", "prefix" },
                { "prefix string suffix", "suffix" },
        };
    }

    @DataProvider
    public static Object[][] inputDoesNotContainSubstring() {
        return new Object[][] {
                { "prefix string suffix", "test" },
        };
    }

}
