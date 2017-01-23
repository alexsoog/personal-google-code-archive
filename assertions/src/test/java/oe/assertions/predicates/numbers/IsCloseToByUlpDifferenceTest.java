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

public class IsCloseToByUlpDifferenceTest extends PredicateTestBase {

    private void compilesWithNull() {
        assertThat( null, isCloseTo( 0.0 ).byUlpDifference( 0L ) );
    }

    private void compilesWithPrimitive() {
        assertThat( ( byte ) 0, isCloseTo( ( byte ) 0 ).byUlpDifference( 0L ) );
        assertThat( ( short ) 0, isCloseTo( ( short ) 0 ).byUlpDifference( 0L ) );
        assertThat( 0, isCloseTo( 0 ).byUlpDifference( 0L ) );
        assertThat( 0L, isCloseTo( 0L ).byUlpDifference( 0L ) );
        assertThat( 0.0f, isCloseTo( 0.0f ).byUlpDifference( 0L ) );
        assertThat( 0.0, isCloseTo( 0.0 ).byUlpDifference( 0L ) );
    }

    private void compilesWithWrapper() {
        assertThat( Byte.MAX_VALUE, isCloseTo( ( byte ) 0 ).byUlpDifference( 0L ) );
        assertThat( Short.MAX_VALUE, isCloseTo( ( short ) 0 ).byUlpDifference( 0L ) );
        assertThat( Integer.MAX_VALUE, isCloseTo( 0 ).byUlpDifference( 0L ) );
        assertThat( Long.MAX_VALUE, isCloseTo( 0L ).byUlpDifference( 0L ) );
        assertThat( Float.MAX_VALUE, isCloseTo( 0.0f ).byUlpDifference( 0L ) );
        assertThat( Double.MAX_VALUE, isCloseTo( 0.0 ).byUlpDifference( 0L ) );
        assertThat( BigInteger.ZERO, isCloseTo( BigInteger.ZERO ).byUlpDifference( 0L ) );
        assertThat( BigDecimal.ZERO, isCloseTo( BigDecimal.ZERO ).byUlpDifference( 0L ) );
    }


    @Test
    public void evaluatesToFalseIfInputIsNull() {
        assertEvaluatesToFalse( null, isCloseTo( 0.0 ).byUlpDifference( 0L ) );
    }

    @Test( expectedExceptions = IllegalArgumentException.class )
    public void throwsIAEIfTargetIsNull() {
        assertEvaluatesToFalse( 0.0, isCloseTo( null ).byUlpDifference( 0L ) );
    }

    @Test( expectedExceptions = IllegalArgumentException.class )
    public void throwsIAEIfToleranceIsNegative() {
        assertEvaluatesToFalse( 0.0, isCloseTo( 0.0 ).byUlpDifference( -1L ) );
    }


    @Test( dataProvider = "numberIsCloseToTargetByUlpDifference" )
    public void evaluatesToTrueIfNumberIsCloseToTargetByUlpDifference( Number input, Number target, long tolerance ) {
        assertEvaluatesToTrue( input, isCloseTo( target ).byUlpDifference( tolerance ) );
    }


    @Test( dataProvider = "numberIsNotCloseToTargetByUlpDifference" )
    public void evaluatesToFalseIfNumberIsNotCloseToTargetByUlpDifference( Number input, Number target, long tolerance ) {
        assertEvaluatesToFalse( input, isCloseTo( target ).byUlpDifference( tolerance ) );
    }


    @Test
    public void producesExpectedMessage() {
        assertProducesExpectedMessage( 1.0, isCloseTo( 2.0 ).byUlpDifference( 1L ), "is close to <2.0> (0x1.0p1) by the ulp difference of 1" );
    }


    @DataProvider
    public static Object[][] numberIsCloseToTargetByUlpDifference() {
        return new Object[][] {
                { ( byte ) 0, ( byte ) 0, 0L },
                { Byte.MAX_VALUE, Byte.MAX_VALUE, 0L },
                { Byte.MIN_VALUE, Byte.MIN_VALUE, 0L },
                { ( byte ) 10, ( byte ) 20, 10L },

                { ( short ) 0, ( short ) 0, 0L },
                { Short.MAX_VALUE, Short.MAX_VALUE, 0L },
                { Short.MIN_VALUE, Short.MIN_VALUE, 0L },
                { ( short ) 10, ( short ) 20, 10L },

                { 0, 0, 0L },
                { Integer.MAX_VALUE, Integer.MAX_VALUE, 0L },
                { Integer.MIN_VALUE, Integer.MIN_VALUE, 0L },
                { 10, 20, 10L },

                { 0L, 0L, 0L },
                { Long.MAX_VALUE, Long.MAX_VALUE, 0L },
                { Long.MIN_VALUE, Long.MIN_VALUE, 0L },
                { 10L, 20L, 10L },

                { 0.0f, 0.0f, 0L },
                { -0.0f, -0.0f, 0L },
                { 1.0f, 1.0f, 0L },
                { Float.MIN_VALUE, Float.MIN_VALUE, 0L },
                { Float.MIN_NORMAL, Float.MIN_NORMAL, 0L },
                { Float.MAX_VALUE, Float.MAX_VALUE, 0L },
                { 10.0f, Math.nextAfter( 10.0f, Float.POSITIVE_INFINITY ), 1L },

                { 0.0, 0.0, 0L },
                { -0.0, -0.0, 0L },
                { 1.0, 1.0, 0L },
                { Double.MIN_VALUE, Double.MIN_VALUE, 0L },
                { Double.MIN_NORMAL, Double.MIN_NORMAL, 0L },
                { Double.MAX_VALUE, Double.MAX_VALUE, 0L },
                { 10.0, Math.nextAfter( 10.0, Double.POSITIVE_INFINITY ), 1L },

                { BigInteger.ZERO, BigInteger.ZERO, 0L },
                { BigInteger.ZERO, BigInteger.ONE, 1L },

                { BigDecimal.ZERO, BigDecimal.ZERO, 0L },
                { BigDecimal.ZERO, BigDecimal.ONE, 1L },
        };
    }

    @DataProvider
    public static Object[][] numberIsNotCloseToTargetByUlpDifference() {
        return new Object[][] {
                { ( byte ) 1, ( byte ) 2, 0L },
                { Byte.MAX_VALUE, Byte.MIN_VALUE, 0L },

                { ( short ) 1, ( short ) 2, 0L },
                { Short.MAX_VALUE, Short.MIN_VALUE, 0L },

                { 1, 2, 0L },
                { Integer.MAX_VALUE, Integer.MIN_VALUE, 0L },

                { 1L, 2L, 0L },
                { Long.MAX_VALUE, Long.MIN_VALUE, 0L },

                { 0.0f, -0.0f, 0L },
                { -0.0f, 0.0f, 0L },
                { 0.0f, 2.0f, 0L },
                { Float.MAX_VALUE, -Float.MAX_VALUE, 0L },
                { 10.0f, Math.nextAfter( Math.nextAfter( 10.0f, Float.POSITIVE_INFINITY ), Float.POSITIVE_INFINITY ), 1L },

                { 0.0, -0.0, 0L },
                { -0.0, 0.0, 0L },
                { 1.0, 2.0, 0L },
                { Double.MAX_VALUE, -Double.MAX_VALUE, 0L },
                { 10.0, Math.nextAfter( Math.nextAfter( 10.0, Double.POSITIVE_INFINITY ), Double.POSITIVE_INFINITY ), 1L },

                { BigInteger.ZERO, BigInteger.TEN, 9L },

                { BigDecimal.ZERO, BigDecimal.TEN, 9L },
        };
    }

}
