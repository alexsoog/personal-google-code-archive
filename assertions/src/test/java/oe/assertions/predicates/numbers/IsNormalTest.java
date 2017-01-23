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
import static oe.assertions.Predicates.isNormal;

public class IsNormalTest extends PredicateTestBase {

    private void compilesWithNull() {
        assertThat( null, isNormal() );
    }

    private void compilesWithPrimitive() {
        assertThat( ( byte ) 0, isNormal() );
        assertThat( ( short ) 0, isNormal() );
        assertThat( 0, isNormal() );
        assertThat( 0L, isNormal() );
        assertThat( 0.0f, isNormal() );
        assertThat( 0.0, isNormal() );
    }

    private void compilesWithWrapper() {
        assertThat( Byte.MAX_VALUE, isNormal() );
        assertThat( Short.MAX_VALUE, isNormal() );
        assertThat( Integer.MAX_VALUE, isNormal() );
        assertThat( Long.MAX_VALUE, isNormal() );
        assertThat( Float.MAX_VALUE, isNormal() );
        assertThat( Double.MAX_VALUE, isNormal() );
        assertThat( BigInteger.ZERO, isNormal() );
        assertThat( BigDecimal.ZERO, isNormal() );
    }


    @Test
    public void evaluatesToFalseIfInputIsNull() {
        assertEvaluatesToFalse( null, isNormal() );
    }


    @Test( dataProvider = "inputIsUnsignedZero", dataProviderClass = IsZeroTest.class )
    public void evaluatesToFalseIfInputIsUnsignedZero( Number input ) {
        assertEvaluatesToFalse( input, isNormal() );
    }

    @Test( dataProvider = "inputIsSignedZero", dataProviderClass = IsZeroTest.class )
    public void evaluatesToFalseIfInputIsSignedZero( Number input ) {
        assertEvaluatesToFalse( input, isNormal() );
    }

    @Test( dataProvider = "inputIsSubnormal", dataProviderClass = IsSubnormalTest.class )
    public void evaluatesToFalseIfInputIsSubnormal( Number input ) {
        assertEvaluatesToFalse( input, isNormal() );
    }

    @Test( dataProvider = "inputIsNormal" )
    public void evaluatesToTrueIfInputIsNormal( Number input ) {
        assertEvaluatesToTrue( input, isNormal() );
    }

    @Test( dataProvider = "inputIsInfinite", dataProviderClass = IsInfiniteTest.class )
    public void evaluatesToFalseIfInputIsInfinite( Number input ) {
        assertEvaluatesToFalse( input, isNormal() );
    }

    @Test( dataProvider = "inputIsNaN", dataProviderClass = IsNaNTest.class )
    public void evaluatesToFalseIfInputIsNaN( Number input ) {
        assertEvaluatesToFalse( input, isNormal() );
    }


    @Test
    public void producesExpectedMessage() {
        assertProducesExpectedMessage( 1.0, isNormal(), "is normal" );
    }


    @DataProvider
    public static Object[][] inputIsNormal() {
        return new Number[][] {
                { ( byte ) 1 },
                { ( byte ) -1 },
                { Byte.MIN_VALUE },
                { Byte.MAX_VALUE },

                { ( short ) 1 },
                { ( short ) -1 },
                { Short.MIN_VALUE },
                { Short.MAX_VALUE },

                { 1 },
                { -1 },
                { Integer.MIN_VALUE },
                { Integer.MAX_VALUE },

                { 1L },
                { -1L },
                { Long.MIN_VALUE },
                { Long.MAX_VALUE },

                { 1.0f },
                { -1.0f },
                { Float.MIN_NORMAL },
                { -Float.MIN_NORMAL },
                { Float.MAX_VALUE },
                { -Float.MAX_VALUE },

                { 1.0 },
                { -1.0 },
                { Double.MIN_NORMAL },
                { -Double.MIN_NORMAL },
                { Double.MAX_VALUE },
                { -Double.MAX_VALUE },

                { BigInteger.ONE },
                { BigInteger.ONE.negate() },
                { BigInteger.TEN },
                { BigInteger.TEN.negate() },

                { BigDecimal.ONE },
                { BigDecimal.ONE.negate() },
                { BigDecimal.TEN },
                { BigDecimal.TEN.negate() },
        };
    }

}
