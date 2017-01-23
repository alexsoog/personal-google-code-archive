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

package oe.assertions.predicates.numbers;

import java.math.BigDecimal;
import java.math.BigInteger;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import oe.assertions.predicates.PredicateTestBase;

import static oe.assertions.Assertions.assertThat;
import static oe.assertions.Predicates.isInfinite;

public class IsInfiniteTest extends PredicateTestBase {

    private void compilesWithNull() {
        assertThat( null, isInfinite() );
    }

    private void compilesWithPrimitive() {
        assertThat( ( byte ) 0, isInfinite() );
        assertThat( ( short ) 0, isInfinite() );
        assertThat( 0, isInfinite() );
        assertThat( 0L, isInfinite() );
        assertThat( 0.0f, isInfinite() );
        assertThat( 0.0, isInfinite() );
    }

    private void compilesWithWrapper() {
        assertThat( Byte.MAX_VALUE, isInfinite() );
        assertThat( Short.MAX_VALUE, isInfinite() );
        assertThat( Integer.MAX_VALUE, isInfinite() );
        assertThat( Long.MAX_VALUE, isInfinite() );
        assertThat( Float.MAX_VALUE, isInfinite() );
        assertThat( Double.MAX_VALUE, isInfinite() );
        assertThat( BigInteger.ZERO, isInfinite() );
        assertThat( BigDecimal.ZERO, isInfinite() );
    }


    @Test
    public void evaluatesToFalseIfInputIsNull() {
        assertEvaluatesToFalse( null, isInfinite() );
    }


    @Test( dataProvider = "inputIsUnsignedZero", dataProviderClass = IsZeroTest.class )
    public void evaluatesToFalseIfInputIsUnsignedZero( Number input ) {
        assertEvaluatesToFalse( input, isInfinite() );
    }

    @Test( dataProvider = "inputIsSignedZero", dataProviderClass = IsZeroTest.class )
    public void evaluatesToFalseIfInputIsSignedZero( Number input ) {
        assertEvaluatesToFalse( input, isInfinite() );
    }

    @Test( dataProvider = "inputIsSubnormal", dataProviderClass = IsSubnormalTest.class )
    public void evaluatesToFalseIfInputIsSubnormal( Number input ) {
        assertEvaluatesToFalse( input, isInfinite() );
    }

    @Test( dataProvider = "inputIsNormal", dataProviderClass = IsNormalTest.class )
    public void evaluatesToFalseIfInputIsNormal( Number input ) {
        assertEvaluatesToFalse( input, isInfinite() );
    }

    @Test( dataProvider = "inputIsInfinite" )
    public void evaluatesToTrueIfInputIsInfinite( Number number ) {
        assertEvaluatesToTrue( number, isInfinite() );
    }

    @Test( dataProvider = "inputIsNaN", dataProviderClass = IsNaNTest.class )
    public void evaluatesToFalseIfInputIsNan( Number number ) {
        assertEvaluatesToFalse( number, isInfinite() );
    }


    @Test
    public void producesExpectedMessage() {
        assertProducesExpectedMessage( 1.0, isInfinite(), "is infinite" );
    }


    @DataProvider
    public static Object[][] inputIsInfinite() {
        return new Number[][] {
                { Float.POSITIVE_INFINITY },
                { Float.NEGATIVE_INFINITY },

                { Double.POSITIVE_INFINITY },
                { Double.NEGATIVE_INFINITY },
        };
    }

}
