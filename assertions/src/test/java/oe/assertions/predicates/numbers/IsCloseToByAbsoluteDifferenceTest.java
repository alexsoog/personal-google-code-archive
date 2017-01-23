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
import static oe.assertions.Predicates.isCloseTo;

public class IsCloseToByAbsoluteDifferenceTest extends PredicateTestBase {

    private void compilesWithNull() {
        assertThat( null, isCloseTo( 0.0 ).byAbsoluteDifference( 0.0 ) );
    }

    private void compilesWithPrimitive() {
        assertThat( ( byte ) 0, isCloseTo( ( byte ) 0 ).byAbsoluteDifference( ( byte ) 0 ) );
        assertThat( ( short ) 0, isCloseTo( ( short ) 0 ).byAbsoluteDifference( ( short ) 0 ) );
        assertThat( 0, isCloseTo( 0 ).byAbsoluteDifference( 0 ) );
        assertThat( 0L, isCloseTo( 0L ).byAbsoluteDifference( 0L ) );
        assertThat( 0.0f, isCloseTo( 0.0f ).byAbsoluteDifference( 0.0f ) );
        assertThat( 0.0, isCloseTo( 0.0 ).byAbsoluteDifference( 0.0 ) );
    }

    private void compilesWithWrapper() {
        assertThat( Byte.MAX_VALUE, isCloseTo( ( byte ) 0 ).byAbsoluteDifference( ( byte ) 0 ) );
        assertThat( Short.MAX_VALUE, isCloseTo( ( short ) 0 ).byAbsoluteDifference( ( short ) 0 ) );
        assertThat( Integer.MAX_VALUE, isCloseTo( 0 ).byAbsoluteDifference( 0 ) );
        assertThat( Long.MAX_VALUE, isCloseTo( 0L ).byAbsoluteDifference( 0L ) );
        assertThat( Float.MAX_VALUE, isCloseTo( 0.0f ).byAbsoluteDifference( 0.0f ) );
        assertThat( Double.MAX_VALUE, isCloseTo( 0.0 ).byAbsoluteDifference( 0.0 ) );
        assertThat( BigInteger.ZERO, isCloseTo( BigInteger.ZERO ).byAbsoluteDifference( BigInteger.ZERO ) );
        assertThat( BigDecimal.ZERO, isCloseTo( BigDecimal.ZERO ).byAbsoluteDifference( BigDecimal.ZERO ) );
    }


    @Test
    public void evaluatesToFalseIfInputIsNull() {
        assertEvaluatesToFalse( null, isCloseTo( 0.0 ).byAbsoluteDifference( 0.0 ) );
    }

    @Test( expectedExceptions = IllegalArgumentException.class )
    public void throwsIAEIfTargetIsNull() {
        assertEvaluatesToFalse( 0.0, isCloseTo( null ).byAbsoluteDifference( 0.0 ) );
    }

    @Test( expectedExceptions = IllegalArgumentException.class )
    public void throwsIAEIfToleranceIsNegative() {
        assertEvaluatesToFalse( 0.0, isCloseTo( 0.0 ).byAbsoluteDifference( -0.0 ) );
    }


    @Test( dataProvider = "numberIsCloseToTargetByAbsoluteDifference" )
    public void evaluatesToTrueIfNumberIsCloseToTargetByAbsoluteDifference( Number input, Number target, Number tolerance ) {
        assertEvaluatesToTrue( input, isCloseTo( target ).byAbsoluteDifference( tolerance ) );
    }


    @Test( dataProvider = "numberIsNotCloseToTargetByAbsoluteDifference" )
    public void evaluatesToFalseIfNumberIsNotCloseToTargetByAbsoluteDifference( Number input, Number target, Number tolerance ) {
        assertEvaluatesToFalse( input, isCloseTo( target ).byAbsoluteDifference( tolerance ) );
    }


    @Test
    public void producesExpectedMessage() {
        assertProducesExpectedMessage( 1.0, isCloseTo( 2.0 ).byAbsoluteDifference( 0.1 ), "is close to <2.0> (0x1.0p1) by the absolute difference of 0.1" );
    }


    @DataProvider
    public static Object[][] numberIsCloseToTargetByAbsoluteDifference() {
        return new Object[][] {
                { ( byte ) 0, ( byte ) 0, ( byte ) 0 },
                { Byte.MAX_VALUE, Byte.MAX_VALUE, ( byte ) 0 },
                { Byte.MIN_VALUE, Byte.MIN_VALUE, ( byte ) 0 },

                { ( short ) 0, ( short ) 0, ( short ) 0 },
                { Short.MAX_VALUE, Short.MAX_VALUE, ( short ) 0 },
                { Short.MIN_VALUE, Short.MIN_VALUE, ( short ) 0 },

                { 0, 0, 0 },
                { Integer.MAX_VALUE, Integer.MAX_VALUE, 0 },
                { Integer.MIN_VALUE, Integer.MIN_VALUE, 0 },

                { 0L, 0L, 0L },
                { Long.MAX_VALUE, Long.MAX_VALUE, 0L },
                { Long.MIN_VALUE, Long.MIN_VALUE, 0L },

                { 0.0f, 0.0f, 0.0f },
                { 0.0f, -0.0f, 0.0f },
                { -0.0f, 0.0f, 0.0f },
                { -0.0f, -0.0f, 0.0f },
                { 1.0f, 1.0f, 0.0f },
                { Float.MIN_VALUE, Float.MIN_VALUE, 0.0f },
                { Float.MIN_NORMAL, Float.MIN_NORMAL, 0.0f },
                { Float.MAX_VALUE, Float.MAX_VALUE, 0.0f },

                { 0.0, 0.0, 0.0 },
                { 0.0, -0.0, 0.0 },
                { -0.0, 0.0, 0.0 },
                { -0.0, -0.0, 0.0 },
                { 1.0, 1.0, 0.0 },
                { Double.MIN_VALUE, Double.MIN_VALUE, 0.0 },
                { Double.MIN_NORMAL, Double.MIN_NORMAL, 0.0 },
                { Double.MAX_VALUE, Double.MAX_VALUE, 0.0 },

                { BigInteger.ZERO, BigInteger.ZERO, BigInteger.ZERO },
                { BigInteger.ZERO, BigInteger.ONE, BigInteger.ONE },

                { BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO },
                { BigDecimal.ZERO, BigDecimal.ONE, BigDecimal.ONE },
        };
    }

    @DataProvider
    public static Object[][] numberIsNotCloseToTargetByAbsoluteDifference() {
        return new Object[][] {
                { ( byte ) 0, ( byte ) 2, ( byte ) 1 },
                { Byte.MAX_VALUE, Byte.MIN_VALUE, Byte.MAX_VALUE },

                { ( short ) 0, ( short ) 2, ( short ) 1 },
                { Short.MAX_VALUE, Short.MIN_VALUE, Short.MAX_VALUE },

                { 0, 2, 1 },
                { Integer.MAX_VALUE, Integer.MIN_VALUE, Integer.MAX_VALUE },

                { 0L, 2L, 1L },
                { Long.MAX_VALUE, Long.MIN_VALUE, Long.MAX_VALUE },

                { 0.0f, 2.0f, 1.0f },
                { Float.MAX_VALUE, -Float.MAX_VALUE, Float.MAX_VALUE },

                { 0.0, 2.0, 1.0 },
                { Double.MAX_VALUE, -Double.MAX_VALUE, Double.MAX_VALUE },

                { BigInteger.ZERO, BigInteger.TEN, BigInteger.ONE },

                { BigDecimal.ZERO, BigDecimal.TEN, BigDecimal.ONE },
        };
    }

}
