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
import static oe.assertions.Predicates.isNegative;

public class IsNegativeTest extends PredicateTestBase {

    private void compilesWithNull() {
        assertThat( null, isNegative() );
    }

    private void compilesWithPrimitive() {
        assertThat( ( byte ) 0, isNegative() );
        assertThat( ( short ) 0, isNegative() );
        assertThat( 0, isNegative() );
        assertThat( 0L, isNegative() );
        assertThat( 0.0f, isNegative() );
        assertThat( 0.0, isNegative() );
    }

    private void compilesWithWrapper() {
        assertThat( Byte.MAX_VALUE, isNegative() );
        assertThat( Short.MAX_VALUE, isNegative() );
        assertThat( Integer.MAX_VALUE, isNegative() );
        assertThat( Long.MAX_VALUE, isNegative() );
        assertThat( Float.MAX_VALUE, isNegative() );
        assertThat( Double.MAX_VALUE, isNegative() );
        assertThat( BigInteger.ZERO, isNegative() );
        assertThat( BigDecimal.ZERO, isNegative() );
    }


    @Test
    public void evaluatesToFalseIfInputIsNull() {
        assertEvaluatesToFalse( null, isNegative() );
    }


    @Test( dataProvider = "inputIsUnsignedZero", dataProviderClass = IsZeroTest.class )
    public void evaluatesToFalseIfInputIsUnsignedZero( Number input ) {
        assertEvaluatesToFalse( input, isNegative() );
    }

    @Test( dataProvider = "inputIsPositive", dataProviderClass = IsPositiveTest.class )
    public void evaluatesToFalseIfInputIsPositive( Number input ) {
        assertEvaluatesToFalse( input, isNegative() );
    }

    @Test( dataProvider = "inputIsNegative" )
    public void evaluatesToTrueIfInputIsNegative( Number input ) {
        assertEvaluatesToTrue( input, isNegative() );
    }

    @Test( dataProvider = "inputIsNaN", dataProviderClass = IsNaNTest.class )
    public void evaluatesToFalseIfInputIsNaN( Number input ) {
        assertEvaluatesToFalse( input, isNegative() );
    }


    @Test
    public void producesExpectedMessage() {
        assertProducesExpectedMessage( 1.0, isNegative(), "is negative" );
    }


    @DataProvider
    public static Object[][] inputIsNegative() {
        return new Number[][] {
                { ( byte ) -1 },
                { Byte.MIN_VALUE },

                { ( short ) -1 },
                { Short.MIN_VALUE },

                { -1 },
                { Integer.MIN_VALUE },

                { -1L },
                { Long.MIN_VALUE },

                { -0.0f },
                { -1.0f },
                { -Float.MIN_VALUE },
                { -Float.MIN_NORMAL },
                { -Float.MAX_VALUE },
                { Float.NEGATIVE_INFINITY },

                { -0.0 },
                { -1.0 },
                { -Double.MIN_VALUE },
                { -Double.MIN_NORMAL },
                { -Double.MAX_VALUE },
                { Double.NEGATIVE_INFINITY },

                { BigInteger.ONE.negate() },
                { BigInteger.TEN.negate() },

                { BigDecimal.ONE.negate() },
                { BigDecimal.TEN.negate() },
        };
    }

}
