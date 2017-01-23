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
import static oe.assertions.Predicates.isPositive;

public class IsPositiveTest extends PredicateTestBase {

    private void compilesWithNull() {
        assertThat( null, isPositive() );
    }

    private void compilesWithPrimitive() {
        assertThat( ( byte ) 0, isPositive() );
        assertThat( ( short ) 0, isPositive() );
        assertThat( 0, isPositive() );
        assertThat( 0L, isPositive() );
        assertThat( 0.0f, isPositive() );
        assertThat( 0.0, isPositive() );
    }

    private void compilesWithWrapper() {
        assertThat( Byte.MAX_VALUE, isPositive() );
        assertThat( Short.MAX_VALUE, isPositive() );
        assertThat( Integer.MAX_VALUE, isPositive() );
        assertThat( Long.MAX_VALUE, isPositive() );
        assertThat( Float.MAX_VALUE, isPositive() );
        assertThat( Double.MAX_VALUE, isPositive() );
        assertThat( BigInteger.ZERO, isPositive() );
        assertThat( BigDecimal.ZERO, isPositive() );
    }


    @Test
    public void evaluatesToFalseIfInputIsNull() {
        assertEvaluatesToFalse( null, isPositive() );
    }


    @Test( dataProvider = "inputIsUnsignedZero", dataProviderClass = IsZeroTest.class )
    public void evaluatesToFalseIfInputIsUnsignedZero( Number input ) {
        assertEvaluatesToFalse( input, isPositive() );
    }

    @Test( dataProvider = "inputIsPositive" )
    public void evaluatesToTrueIfInputIsPositive( Number input ) {
        assertEvaluatesToTrue( input, isPositive() );
    }

    @Test( dataProvider = "inputIsNegative", dataProviderClass = IsNegativeTest.class )
    public void evaluatesToFalseIfInputIsNegative( Number input ) {
        assertEvaluatesToFalse( input, isPositive() );
    }

    @Test( dataProvider = "inputIsNaN", dataProviderClass = IsNaNTest.class )
    public void evaluatesToFalseIfInputIsNaN( Number input ) {
        assertEvaluatesToFalse( input, isPositive() );
    }


    @Test
    public void producesExpectedMessage() {
        assertProducesExpectedMessage( -1.0, isPositive(), "is positive" );
    }


    @DataProvider
    public static Object[][] inputIsPositive() {
        return new Number[][] {
                { ( byte ) 1 },
                { Byte.MAX_VALUE },

                { ( short ) 1 },
                { Short.MAX_VALUE },

                { 1 },
                { Integer.MAX_VALUE },

                { 1L },
                { Long.MAX_VALUE },

                { 0.0f },
                { 1.0f },
                { Float.MIN_VALUE },
                { Float.MIN_NORMAL },
                { Float.MAX_VALUE },
                { Float.POSITIVE_INFINITY },

                { 0.0 },
                { 1.0 },
                { Double.MIN_VALUE },
                { Double.MIN_NORMAL },
                { Double.MAX_VALUE },
                { Double.POSITIVE_INFINITY },

                { BigInteger.ONE },
                { BigInteger.TEN },

                { BigDecimal.ONE },
                { BigDecimal.TEN },
        };
    }

}
