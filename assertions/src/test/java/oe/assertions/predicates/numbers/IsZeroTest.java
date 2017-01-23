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
import static oe.assertions.Predicates.isZero;

public class IsZeroTest extends PredicateTestBase {

    private void compilesWithNull() {
        assertThat( null, isZero() );
    }

    private void compilesWithPrimitive() {
        assertThat( ( byte ) 0, isZero() );
        assertThat( ( short ) 0, isZero() );
        assertThat( 0, isZero() );
        assertThat( 0L, isZero() );
        assertThat( 0.0f, isZero() );
        assertThat( 0.0, isZero() );
    }

    private void compilesWithWrapper() {
        assertThat( Byte.MAX_VALUE, isZero() );
        assertThat( Short.MAX_VALUE, isZero() );
        assertThat( Integer.MAX_VALUE, isZero() );
        assertThat( Long.MAX_VALUE, isZero() );
        assertThat( Float.MAX_VALUE, isZero() );
        assertThat( Double.MAX_VALUE, isZero() );
        assertThat( BigInteger.ZERO, isZero() );
        assertThat( BigDecimal.ZERO, isZero() );
    }


    @Test
    public void evaluatesToFalseIfInputIsNull() {
        assertEvaluatesToFalse( null, isZero() );
    }


    @Test( dataProvider = "inputIsUnsignedZero" )
    public void evaluatesToTrueIfInputIsUnsignedZero( Number input ) {
        assertEvaluatesToTrue( input, isZero() );
    }

    @Test( dataProvider = "inputIsSignedZero" )
    public void evaluatesToTrueIfInputIsSignedZero( Number input ) {
        assertEvaluatesToTrue( input, isZero() );
    }

    @Test( dataProvider = "inputIsSubnormal", dataProviderClass = IsSubnormalTest.class )
    public void evaluatesToFalseIfInputIsSubnormal( Number input ) {
        assertEvaluatesToFalse( input, isZero() );
    }

    @Test( dataProvider = "inputIsNormal", dataProviderClass = IsNormalTest.class )
    public void evaluatesToFalseIfInputIsNormal( Number input ) {
        assertEvaluatesToFalse( input, isZero() );
    }

    @Test( dataProvider = "inputIsInfinite", dataProviderClass = IsInfiniteTest.class )
    public void evaluatesToFalseIfInputIsInfinite( Number input ) {
        assertEvaluatesToFalse( input, isZero() );
    }

    @Test( dataProvider = "inputIsNaN", dataProviderClass = IsNaNTest.class )
    public void evaluatesToFalseIfInputIsNaN( Number input ) {
        assertEvaluatesToFalse( input, isZero() );
    }


    @Test
    public void producesExpectedMessage() {
        assertProducesExpectedMessage( 1.0, isZero(), "is zero" );
    }


    @DataProvider
    public static Object[][] inputIsUnsignedZero() {
        return new Object[][] {
                { ( byte ) 0 },
                { ( short ) 0 },
                { 0 },
                { 0L },
                { BigInteger.ZERO },
                { BigDecimal.ZERO },
        };
    }

    @DataProvider
    public static Object[][] inputIsSignedZero() {
        return new Object[][] {
                { 0.0f },
                { -0.0f },

                { 0.0 },
                { -0.0 },
        };
    }

}
