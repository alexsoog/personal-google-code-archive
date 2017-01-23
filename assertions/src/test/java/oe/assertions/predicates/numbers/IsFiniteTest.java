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
import static oe.assertions.Predicates.isFinite;

public class IsFiniteTest extends PredicateTestBase {

    private void compilesWithNull() {
        assertThat( null, isFinite() );
    }

    private void compilesWithPrimitive() {
        assertThat( ( byte ) 0, isFinite() );
        assertThat( ( short ) 0, isFinite() );
        assertThat( 0, isFinite() );
        assertThat( 0L, isFinite() );
        assertThat( 0.0f, isFinite() );
        assertThat( 0.0, isFinite() );
    }

    private void compilesWithWrapper() {
        assertThat( Byte.MAX_VALUE, isFinite() );
        assertThat( Short.MAX_VALUE, isFinite() );
        assertThat( Integer.MAX_VALUE, isFinite() );
        assertThat( Long.MAX_VALUE, isFinite() );
        assertThat( Float.MAX_VALUE, isFinite() );
        assertThat( Double.MAX_VALUE, isFinite() );
        assertThat( BigInteger.ZERO, isFinite() );
        assertThat( BigDecimal.ZERO, isFinite() );
    }


    @Test
    public void evaluatesToFalseIfInputIsNull() {
        assertEvaluatesToFalse( null, isFinite() );
    }


    @Test( dataProvider = "inputIsUnsignedZero", dataProviderClass = IsZeroTest.class )
    public void evaluatesToTrueIfInputIsUnsignedZero( Number input ) {
        assertEvaluatesToTrue( input, isFinite() );
    }

    @Test( dataProvider = "inputIsSignedZero", dataProviderClass = IsZeroTest.class )
    public void evaluatesToTrueIfInputIsSignedZero( Number input ) {
        assertEvaluatesToTrue( input, isFinite() );
    }

    @Test( dataProvider = "inputIsSubnormal", dataProviderClass = IsSubnormalTest.class )
    public void evaluatesToTrueIfInputIsSubnormal( Number input ) {
        assertEvaluatesToTrue( input, isFinite() );
    }

    @Test( dataProvider = "inputIsNormal", dataProviderClass = IsNormalTest.class )
    public void evaluatesToTrueIfInputIsNormal( Number input ) {
        assertEvaluatesToTrue( input, isFinite() );
    }

    @Test( dataProvider = "inputIsInfinite", dataProviderClass = IsInfiniteTest.class )
    public void evaluatesToFalseIfInputIsInfinite( Number number ) {
        assertEvaluatesToFalse( number, isFinite() );
    }

    @Test( dataProvider = "inputIsNaN", dataProviderClass = IsNaNTest.class )
    public void evaluatesToFalseIfInputIsNan( Number number ) {
        assertEvaluatesToFalse( number, isFinite() );
    }


    @Test
    public void producesExpectedMessage() {
        assertProducesExpectedMessage( Double.POSITIVE_INFINITY, isFinite(), "is finite" );
    }

}
