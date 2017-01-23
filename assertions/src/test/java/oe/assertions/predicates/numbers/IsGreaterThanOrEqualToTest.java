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

import org.testng.annotations.Test;

import oe.assertions.predicates.PredicateTestBase;

import static oe.assertions.Assertions.assertThat;
import static oe.assertions.Predicates.isGreaterThanOrEqualTo;

public class IsGreaterThanOrEqualToTest extends PredicateTestBase {

    private void compilesWithNull() {
        assertThat( null, isGreaterThanOrEqualTo( 0.0 ) );
    }

    private void compilesWithPrimitive() {
        assertThat( ( byte ) 0, isGreaterThanOrEqualTo( ( byte ) 0 ) );
        assertThat( ( short ) 0, isGreaterThanOrEqualTo( ( short ) 0 ) );
        assertThat( 0, isGreaterThanOrEqualTo( 0 ) );
        assertThat( 0L, isGreaterThanOrEqualTo( 0L ) );
        assertThat( 0.0f, isGreaterThanOrEqualTo( 0.0f ) );
        assertThat( 0.0, isGreaterThanOrEqualTo( 0.0 ) );
    }

    private void compilesWithWrapper() {
        assertThat( Byte.MAX_VALUE, isGreaterThanOrEqualTo( ( byte ) 0 ) );
        assertThat( Short.MAX_VALUE, isGreaterThanOrEqualTo( ( short ) 0 ) );
        assertThat( Integer.MAX_VALUE, isGreaterThanOrEqualTo( 0 ) );
        assertThat( Long.MAX_VALUE, isGreaterThanOrEqualTo( 0L ) );
        assertThat( Float.MAX_VALUE, isGreaterThanOrEqualTo( 0.0f ) );
        assertThat( Double.MAX_VALUE, isGreaterThanOrEqualTo( 0.0 ) );
        assertThat( BigInteger.ZERO, isGreaterThanOrEqualTo( BigInteger.ZERO ) );
        assertThat( BigDecimal.ZERO, isGreaterThanOrEqualTo( BigDecimal.ZERO ) );
    }


    @Test( dataProvider = "inputIsNull", dataProviderClass = IsNotEqualToTest.class )
    public void evaluatesToFalseIfInputIsNull( Number input, Number target ) {
        assertEvaluatesToFalse( input, isGreaterThanOrEqualTo( target ) );
    }

    @Test( dataProvider = "targetIsNull", dataProviderClass = IsNotEqualToTest.class, expectedExceptions = IllegalArgumentException.class )
    public void throwsIAEIfTargetIsNull( Number input, Number target ) {
        assertEvaluatesToFalse( input, isGreaterThanOrEqualTo( target ) );
    }

    @Test( dataProvider = "inputOrTargetIsNaN", dataProviderClass = IsNotEqualToTest.class )
    public void evaluatesToFalseIfInputOrTargetIsNaN( Number input, Number target ) {
        assertEvaluatesToFalse( input, isGreaterThanOrEqualTo( target ) );
    }


    @Test( dataProvider = "inputIsLessThanTarget", dataProviderClass = IsLessThanTest.class )
    public void evaluatesToFalseIfInputIsLessThanTarget( Number input, Number target ) {
        assertEvaluatesToFalse( input, isGreaterThanOrEqualTo( target ) );
    }

    @Test( dataProvider = "inputIsEqualToTarget", dataProviderClass = IsEqualToTest.class )
    public void evaluatesToTrueIfInputIsEqualToTarget( Number input, Number target ) {
        assertEvaluatesToTrue( input, isGreaterThanOrEqualTo( target ) );
    }

    @Test( dataProvider = "inputIsGreaterThanTarget", dataProviderClass = IsGreaterThanTest.class )
    public void evaluatesToTrueIfInputIsGreaterThanTarget( Number input, Number target ) {
        assertEvaluatesToTrue( input, isGreaterThanOrEqualTo( target ) );
    }


    @Test
    public void producesExpectedMessage() {
        assertProducesExpectedMessage( 1.0, isGreaterThanOrEqualTo( 2.0 ), "is greater than or equal to <2.0> (0x1.0p1)" );
    }

}
