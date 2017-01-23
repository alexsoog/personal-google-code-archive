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

package oe.assertions.predicates;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Collections;
import java.util.List;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static oe.assertions.Assertions.assertThat;
import static oe.assertions.Predicates.isSameAs;

public class IsSameAsTest extends PredicateTestBase {

    private void compilesWithNull() {
        assertThat( null, isSameAs( null ) );
        assertThat( null, isSameAs( "string" ) );
        assertThat( null, isSameAs( new String[] { "array" } ) );
        assertThat( null, isSameAs( Collections.singletonList( "list" ) ) );
    }

    private void compilesWithObject() {
        Object input = null;
        assertThat( input, isSameAs( null ) );
        assertThat( input, isSameAs( input ) );
        assertThat( input, isSameAs( "string" ) );
        assertThat( input, isSameAs( new String[] { "array" } ) );
        assertThat( input, isSameAs( Collections.singletonList( "list" ) ) );
    }

    private void compilesWithArray() {
        Object[] input = null;
        assertThat( input, isSameAs( null ) );
        assertThat( input, isSameAs( input ) );
        assertThat( input, isSameAs( "string" ) );
        assertThat( input, isSameAs( new String[] { "array" } ) );
        assertThat( input, isSameAs( Collections.singletonList( "list" ) ) );
    }

    private void compilesWithCollection() {
        List<Number> input = null;
        assertThat( input, isSameAs( null ) );
        assertThat( input, isSameAs( input ) );
        assertThat( input, isSameAs( "string" ) );
        assertThat( input, isSameAs( new String[] { "array" } ) );
        assertThat( input, isSameAs( Collections.singletonList( "list" ) ) );
    }

    private void compilesWithWildcardCollection() {
        List<? extends Number> input = null;
        assertThat( input, isSameAs( null ) );
        assertThat( input, isSameAs( input ) );
        assertThat( input, isSameAs( "string" ) );
        assertThat( input, isSameAs( new String[] { "array" } ) );
        assertThat( input, isSameAs( Collections.singletonList( "list" ) ) );
    }


    @Test( dataProvider = "inputIsSameAsTarget" )
    public void evaluatesToTrueIfInputIsSameAsTarget( Object input, Object target ) {
        assertEvaluatesToTrue( input, isSameAs( target ) );
    }

    @Test( dataProvider = "inputIsNotSameAsTarget", dataProviderClass = IsNotSameAsTest.class )
    public void evaluatesToFalseIfInputIsNotSameAsTarget( Object input, Object target ) {
        assertEvaluatesToFalse( input, isSameAs( target ) );
    }


    @Test
    public void producesExpectedMessage() {
        assertProducesExpectedMessage( 1.0, isSameAs( 2.0 ), "is same as <2.0> (0x1.0p1)" );
    }


    @DataProvider
    public static Object[][] inputIsSameAsTarget() {
        Object object = new Object();

        return new Object[][] {
                { null, null },
                { object, object },

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
                { -0.0f, -0.0f },
                { 1.0f, 1.0f },
                { Float.MIN_VALUE, Float.MIN_VALUE },
                { Float.MIN_NORMAL, Float.MIN_NORMAL },
                { Float.MAX_VALUE, Float.MAX_VALUE },
                { Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY },
                { Float.NEGATIVE_INFINITY, Float.NEGATIVE_INFINITY },
                { Float.NaN, Float.NaN },

                { 0.0, 0.0 },
                { -0.0, -0.0 },
                { 1.0, 1.0 },
                { Double.MIN_VALUE, Double.MIN_VALUE },
                { Double.MIN_NORMAL, Double.MIN_NORMAL },
                { Double.MAX_VALUE, Double.MAX_VALUE },
                { Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY },
                { Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY },
                { Double.NaN, Double.NaN },

                { 'a', 'a' },

                { "string", "string" },

                { true, true },
                { false, false },

                { BigInteger.ZERO, BigInteger.ZERO },
                { new BigInteger( "10" ), new BigInteger( "10" ) },

                { BigDecimal.ZERO, BigDecimal.ZERO },
                { new BigDecimal( "10.0" ), new BigDecimal( "10.0" ) },
        };
    }

}
