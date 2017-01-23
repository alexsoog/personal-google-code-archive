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
import static oe.assertions.Predicates.isGreaterThan;

public class IsGreaterThanTest extends PredicateTestBase {

    private void compilesWithNull() {
        assertThat( null, isGreaterThan( 0.0 ) );
    }

    private void compilesWithPrimitive() {
        assertThat( ( byte ) 0, isGreaterThan( ( byte ) 0 ) );
        assertThat( ( short ) 0, isGreaterThan( ( short ) 0 ) );
        assertThat( 0, isGreaterThan( 0 ) );
        assertThat( 0L, isGreaterThan( 0L ) );
        assertThat( 0.0f, isGreaterThan( 0.0f ) );
        assertThat( 0.0, isGreaterThan( 0.0 ) );
    }

    private void compilesWithWrapper() {
        assertThat( Byte.MAX_VALUE, isGreaterThan( ( byte ) 0 ) );
        assertThat( Short.MAX_VALUE, isGreaterThan( ( short ) 0 ) );
        assertThat( Integer.MAX_VALUE, isGreaterThan( 0 ) );
        assertThat( Long.MAX_VALUE, isGreaterThan( 0L ) );
        assertThat( Float.MAX_VALUE, isGreaterThan( 0.0f ) );
        assertThat( Double.MAX_VALUE, isGreaterThan( 0.0 ) );
        assertThat( BigInteger.ZERO, isGreaterThan( BigInteger.ZERO ) );
        assertThat( BigDecimal.ZERO, isGreaterThan( BigDecimal.ZERO ) );
    }


    @Test( dataProvider = "inputIsNull", dataProviderClass = IsNotEqualToTest.class )
    public void evaluatesToFalseIfInputIsNull( Number input, Number target ) {
        assertEvaluatesToFalse( input, isGreaterThan( target ) );
    }

    @Test( dataProvider = "targetIsNull", dataProviderClass = IsNotEqualToTest.class, expectedExceptions = IllegalArgumentException.class )
    public void throwsIAEIfTargetIsNull( Number input, Number target ) {
        assertEvaluatesToFalse( input, isGreaterThan( target ) );
    }

    @Test( dataProvider = "inputOrTargetIsNaN", dataProviderClass = IsNotEqualToTest.class )
    public void evaluatesToFalseIfInputOrTargetIsNaN( Number input, Number target ) {
        assertEvaluatesToFalse( input, isGreaterThan( target ) );
    }


    @Test( dataProvider = "inputIsLessThanTarget", dataProviderClass = IsLessThanTest.class )
    public void evaluatesToFalseIfInputIsLessThanTarget( Number input, Number target ) {
        assertEvaluatesToFalse( input, isGreaterThan( target ) );
    }

    @Test( dataProvider = "inputIsEqualToTarget", dataProviderClass = IsEqualToTest.class )
    public void evaluatesToFalseIfInputIsEqualToTarget( Number input, Number target ) {
        assertEvaluatesToFalse( input, isGreaterThan( target ) );
    }

    @Test( dataProvider = "inputIsGreaterThanTarget" )
    public void evaluatesToTrueIfInputIsGreaterThanTarget( Number input, Number target ) {
        assertEvaluatesToTrue( input, isGreaterThan( target ) );
    }


    @Test
    public void producesExpectedMessage() {
        assertProducesExpectedMessage( 1.0, isGreaterThan( 2.0 ), "is greater than <2.0> (0x1.0p1)" );
    }


    @DataProvider
    public static Object[][] inputIsGreaterThanTarget() {
        return new Number[][] {
                { ( byte ) 1, ( byte ) 0 },
                { Byte.MAX_VALUE, Byte.MIN_VALUE },

                { ( short ) 2, ( short ) 1 },
                { Short.MAX_VALUE, Short.MIN_VALUE },

                { 3, 2 },
                { Integer.MAX_VALUE, Integer.MIN_VALUE },

                { 4L, 3L },
                { Long.MAX_VALUE, Long.MIN_VALUE },

                { 5.0f, 4.0f },
                { Float.MAX_VALUE, Float.MIN_VALUE },
                { Float.POSITIVE_INFINITY, Float.NEGATIVE_INFINITY },

                { 6.0, 5.0 },
                { Double.MAX_VALUE, Double.MIN_VALUE },
                { Double.POSITIVE_INFINITY, Double.NEGATIVE_INFINITY },

                { BigInteger.valueOf( 7L ), BigInteger.valueOf( 6L ) },

                { BigDecimal.valueOf( 8L ), BigDecimal.valueOf( 7L ) },
        };
    }

}
