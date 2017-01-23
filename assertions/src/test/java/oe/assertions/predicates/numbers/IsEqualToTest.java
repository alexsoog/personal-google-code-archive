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
import static oe.assertions.Predicates.isEqualTo;

public class IsEqualToTest extends PredicateTestBase {

    private void compilesWithNull() {
        assertThat( null, isEqualTo( 0.0 ) );
    }

    private void compilesWithPrimitive() {
        assertThat( ( byte ) 0, isEqualTo( ( byte ) 0 ) );
        assertThat( ( short ) 0, isEqualTo( ( short ) 0 ) );
        assertThat( 0, isEqualTo( 0 ) );
        assertThat( 0L, isEqualTo( 0L ) );
        assertThat( 0.0f, isEqualTo( 0.0f ) );
        assertThat( 0.0, isEqualTo( 0.0 ) );
    }

    private void compilesWithWrapper() {
        assertThat( Byte.MAX_VALUE, isEqualTo( ( byte ) 0 ) );
        assertThat( Short.MAX_VALUE, isEqualTo( ( short ) 0 ) );
        assertThat( Integer.MAX_VALUE, isEqualTo( 0 ) );
        assertThat( Long.MAX_VALUE, isEqualTo( 0L ) );
        assertThat( Float.MAX_VALUE, isEqualTo( 0.0f ) );
        assertThat( Double.MAX_VALUE, isEqualTo( 0.0 ) );
        assertThat( BigInteger.ZERO, isEqualTo( BigInteger.ZERO ) );
        assertThat( BigDecimal.ZERO, isEqualTo( BigDecimal.ZERO ) );
    }


    @Test( dataProvider = "inputIsNull", dataProviderClass = IsNotEqualToTest.class )
    public void evaluatesToFalseIfInputIsNull( Number input, Number target ) {
        assertEvaluatesToFalse( input, isEqualTo( target ) );
    }

    @Test( dataProvider = "targetIsNull", dataProviderClass = IsNotEqualToTest.class )
    public void evaluatesToFalseIfTargetIsNull( Number input, Number target ) {
        assertEvaluatesToFalse( input, isEqualTo( target ) );
    }

    @Test( dataProvider = "inputOrTargetIsNaN", dataProviderClass = IsNotEqualToTest.class )
    public void evaluatesToFalseIfInputOrTargetIsNaN( Number input, Number target ) {
        assertEvaluatesToFalse( input, isEqualTo( target ) );
    }


    @Test( dataProvider = "inputIsLessThanTarget", dataProviderClass = IsLessThanTest.class )
    public void evaluatesToFalseIfInputIsLessThanTarget( Number input, Number target ) {
        assertEvaluatesToFalse( input, isEqualTo( target ) );
    }

    @Test( dataProvider = "inputIsEqualToTarget" )
    public void evaluatesToTrueIfInputIsEqualToTarget( Number input, Number target ) {
        assertEvaluatesToTrue( input, isEqualTo( target ) );
    }

    @Test( dataProvider = "inputIsGreaterThanTarget", dataProviderClass = IsGreaterThanTest.class )
    public void evaluatesToFalseIfInputIsGreaterThanTarget( Number input, Number target ) {
        assertEvaluatesToFalse( input, isEqualTo( target ) );
    }


    @Test
    public void producesExpectedMessage() {
        assertProducesExpectedMessage( 1.0, isEqualTo( 2.0 ), "is equal to <2.0> (0x1.0p1)" );
    }


    @DataProvider
    public static Object[][] inputIsEqualToTarget() {
        return new Number[][] {
                { ( byte ) 0, ( byte ) 0 },
                { Byte.MAX_VALUE, Byte.MAX_VALUE },
                { Byte.MIN_VALUE, Byte.MIN_VALUE },

                { ( short ) 0, ( short ) 0 },
                { Short.MAX_VALUE, Short.MAX_VALUE },
                { Short.MIN_VALUE, Short.MIN_VALUE },

                { 0, 0 },
                { Integer.MAX_VALUE, Integer.MAX_VALUE },
                { Integer.MIN_VALUE, Integer.MIN_VALUE },

                { 0L, 0L },
                { Long.MAX_VALUE, Long.MAX_VALUE },
                { Long.MIN_VALUE, Long.MIN_VALUE },

                { 0.0f, 0.0f },
                { 0.0f, -0.0f },
                { -0.0f, 0.0f },
                { -0.0f, -0.0f },
                { 1.0f, 1.0f },
                { -1.0f, -1.0f },
                { Float.MIN_VALUE, Float.MIN_VALUE },
                { Float.MIN_NORMAL, Float.MIN_NORMAL },
                { Float.MAX_VALUE, Float.MAX_VALUE },
                { Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY },
                { Float.NEGATIVE_INFINITY, Float.NEGATIVE_INFINITY },

                { 0.0, 0.0 },
                { 0.0, -0.0 },
                { -0.0, 0.0 },
                { -0.0, -0.0 },
                { 1.0, 1.0 },
                { -1.0, -1.0 },
                { Double.MIN_VALUE, Double.MIN_VALUE },
                { Double.MIN_NORMAL, Double.MIN_NORMAL },
                { Double.MAX_VALUE, Double.MAX_VALUE },
                { Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY },
                { Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY },

                { BigInteger.ZERO, BigInteger.ZERO },
                { BigInteger.valueOf( 1L ), BigInteger.valueOf( 1L ) },

                { BigDecimal.ZERO, BigDecimal.ZERO },
                { new BigDecimal( "1.23" ), new BigDecimal( "1.23" ) },
        };
    }

}
