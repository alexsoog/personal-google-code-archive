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
import static oe.assertions.Predicates.isNotEqualTo;

public class IsNotEqualToTest extends PredicateTestBase {

    private void compilesWithNull() {
        assertThat( null, isNotEqualTo( 0.0 ) );
    }

    private void compilesWithPrimitive() {
        assertThat( ( byte ) 0, isNotEqualTo( ( byte ) 0 ) );
        assertThat( ( short ) 0, isNotEqualTo( ( short ) 0 ) );
        assertThat( 0, isNotEqualTo( 0 ) );
        assertThat( 0L, isNotEqualTo( 0L ) );
        assertThat( 0.0f, isNotEqualTo( 0.0f ) );
        assertThat( 0.0, isNotEqualTo( 0.0 ) );
    }

    private void compilesWithWrapper() {
        assertThat( Byte.MAX_VALUE, isNotEqualTo( ( byte ) 0 ) );
        assertThat( Short.MAX_VALUE, isNotEqualTo( ( short ) 0 ) );
        assertThat( Integer.MAX_VALUE, isNotEqualTo( 0 ) );
        assertThat( Long.MAX_VALUE, isNotEqualTo( 0L ) );
        assertThat( Float.MAX_VALUE, isNotEqualTo( 0.0f ) );
        assertThat( Double.MAX_VALUE, isNotEqualTo( 0.0 ) );
        assertThat( BigInteger.ZERO, isNotEqualTo( BigInteger.ZERO ) );
        assertThat( BigDecimal.ZERO, isNotEqualTo( BigDecimal.ZERO ) );
    }


    @Test( dataProvider = "inputIsNull" )
    public void evaluatesToTrueIfInputIsNull( Number input, Number target ) {
        assertEvaluatesToTrue( input, isNotEqualTo( target ) );
    }

    @Test( dataProvider = "targetIsNull" )
    public void evaluatesToTrueIfTargetIsNull( Number input, Number target ) {
        assertEvaluatesToTrue( input, isNotEqualTo( target ) );
    }

    @Test( dataProvider = "inputOrTargetIsNaN" )
    public void evaluatesToTrueIfInputOrTargetIsNaN( Number input, Number target ) {
        assertEvaluatesToTrue( input, isNotEqualTo( target ) );
    }


    @Test( dataProvider = "inputIsLessThanTarget", dataProviderClass = IsLessThanTest.class )
    public void evaluatesToTrueIfInputIsLessThanTarget( Number input, Number target ) {
        assertEvaluatesToTrue( input, isNotEqualTo( target ) );
    }

    @Test( dataProvider = "inputIsEqualToTarget", dataProviderClass = IsEqualToTest.class )
    public void evaluatesToFalseIfInputIsEqualToTarget( Number input, Number target ) {
        assertEvaluatesToFalse( input, isNotEqualTo( target ) );
    }

    @Test( dataProvider = "inputIsGreaterThanTarget", dataProviderClass = IsGreaterThanTest.class )
    public void evaluatesToTrueIfInputIsGreaterThanTarget( Number input, Number target ) {
        assertEvaluatesToTrue( input, isNotEqualTo( target ) );
    }


    @Test
    public void producesExpectedMessage() {
        assertProducesExpectedMessage( 1.0, isNotEqualTo( 2.0 ), "is not equal to <2.0> (0x1.0p1)" );
    }


    @DataProvider
    public static Object[][] inputIsNull() {
        return new Object[][] {
                { null, ( byte ) 0 },

                { null, ( short ) 0 },

                { null, 0 },

                { null, 0L },

                { null, 0.0f },

                { null, 0.0 },

                { null, BigInteger.ZERO },

                { null, BigDecimal.ZERO },
        };
    }

    @DataProvider
    public static Object[][] targetIsNull() {
        return new Object[][] {
                { ( byte ) 0, null },

                { ( short ) 0, null },

                { 0, null },

                { 0L, null },

                { 0.0f, null },

                { 0.0, null },

                { BigInteger.ZERO, null },

                { BigDecimal.ZERO, null },
        };
    }

    @DataProvider
    public static Object[][] inputOrTargetIsNaN() {
        return new Object[][] {
                { 0.0f, Float.NaN },
                { Float.NaN, 0.0f },
                { Float.NaN, Float.NaN },

                { 0.0, Double.NaN },
                { Double.NaN, 0.0 },
                { Double.NaN, Double.NaN },
        };
    }

}
