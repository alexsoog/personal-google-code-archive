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
import static oe.assertions.Predicates.isLessThan;

public class IsLessThanTest extends PredicateTestBase {

    private void compilesWithNull() {
        assertThat( null, isLessThan( 0.0 ) );
    }

    private void compilesWithPrimitive() {
        assertThat( ( byte ) 0, isLessThan( ( byte ) 0 ) );
        assertThat( ( short ) 0, isLessThan( ( short ) 0 ) );
        assertThat( 0, isLessThan( 0 ) );
        assertThat( 0L, isLessThan( 0L ) );
        assertThat( 0.0f, isLessThan( 0.0f ) );
        assertThat( 0.0, isLessThan( 0.0 ) );
    }

    private void compilesWithWrapper() {
        assertThat( Byte.MAX_VALUE, isLessThan( ( byte ) 0 ) );
        assertThat( Short.MAX_VALUE, isLessThan( ( short ) 0 ) );
        assertThat( Integer.MAX_VALUE, isLessThan( 0 ) );
        assertThat( Long.MAX_VALUE, isLessThan( 0L ) );
        assertThat( Float.MAX_VALUE, isLessThan( 0.0f ) );
        assertThat( Double.MAX_VALUE, isLessThan( 0.0 ) );
        assertThat( BigInteger.ZERO, isLessThan( BigInteger.ZERO ) );
        assertThat( BigDecimal.ZERO, isLessThan( BigDecimal.ZERO ) );
    }


    @Test( dataProvider = "inputIsNull", dataProviderClass = IsNotEqualToTest.class )
    public void evaluatesToFalseIfInputIsNull( Number input, Number target ) {
        assertEvaluatesToFalse( input, isLessThan( target ) );
    }

    @Test( dataProvider = "targetIsNull", dataProviderClass = IsNotEqualToTest.class, expectedExceptions = IllegalArgumentException.class )
    public void throwsIAEIfTargetIsNull( Number input, Number target ) {
        assertEvaluatesToFalse( input, isLessThan( target ) );
    }

    @Test( dataProvider = "inputOrTargetIsNaN", dataProviderClass = IsNotEqualToTest.class )
    public void evaluatesToFalseIfInputOrTargetIsNaN( Number input, Number target ) {
        assertEvaluatesToFalse( input, isLessThan( target ) );
    }


    @Test( dataProvider = "inputIsLessThanTarget" )
    public void evaluatesToTrueIfInputIsLessThanTarget( Number input, Number target ) {
        assertEvaluatesToTrue( input, isLessThan( target ) );
    }

    @Test( dataProvider = "inputIsEqualToTarget", dataProviderClass = IsEqualToTest.class )
    public void evaluatesToFalseIfInputIsEqualToTarget( Number input, Number target ) {
        assertEvaluatesToFalse( input, isLessThan( target ) );
    }

    @Test( dataProvider = "inputIsGreaterThanTarget", dataProviderClass = IsGreaterThanTest.class )
    public void evaluatesToFalseIfInputIsGreaterThanTarget( Number input, Number target ) {
        assertEvaluatesToFalse( input, isLessThan( target ) );
    }


    @Test
    public void producesExpectedMessage() {
        assertProducesExpectedMessage( 3.0, isLessThan( 2.0 ), "is less than <2.0> (0x1.0p1)" );
    }


    @DataProvider
    public static Object[][] inputIsLessThanTarget() {
        return new Number[][] {
                { ( byte ) 0, ( byte ) 1 },
                { Byte.MIN_VALUE, Byte.MAX_VALUE },

                { ( short ) 1, ( short ) 2 },
                { Short.MIN_VALUE, Short.MAX_VALUE },

                { 2, 3 },
                { Integer.MIN_VALUE, Integer.MAX_VALUE },

                { 3L, 4L },
                { Long.MIN_VALUE, Long.MAX_VALUE },

                { 4.0f, 5.0f },
                { Float.MIN_VALUE, Float.MAX_VALUE },
                { Float.NEGATIVE_INFINITY, Float.POSITIVE_INFINITY },

                { 5.0, 6.0 },
                { Double.MIN_VALUE, Double.MAX_VALUE },
                { Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY },

                { BigInteger.valueOf( 6L ), BigInteger.valueOf( 7L ) },

                { BigDecimal.valueOf( 7L ), BigDecimal.valueOf( 8L ) },
        };
    }

}
