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
import static oe.assertions.Predicates.isNotSameAs;

public class IsNotSameAsTest extends PredicateTestBase {

    private void compilesWithNull() {
        assertThat( null, isNotSameAs( null ) );
        assertThat( null, isNotSameAs( "string" ) );
        assertThat( null, isNotSameAs( new String[] { "array" } ) );
        assertThat( null, isNotSameAs( Collections.singletonList( "list" ) ) );
    }

    private void compilesWithObject() {
        Object input = null;
        assertThat( input, isNotSameAs( null ) );
        assertThat( input, isNotSameAs( input ) );
        assertThat( input, isNotSameAs( "string" ) );
        assertThat( input, isNotSameAs( new String[] { "array" } ) );
        assertThat( input, isNotSameAs( Collections.singletonList( "list" ) ) );
    }

    private void compilesWithArray() {
        Object[] input = null;
        assertThat( input, isNotSameAs( null ) );
        assertThat( input, isNotSameAs( input ) );
        assertThat( input, isNotSameAs( "string" ) );
        assertThat( input, isNotSameAs( new String[] { "array" } ) );
        assertThat( input, isNotSameAs( Collections.singletonList( "list" ) ) );
    }

    private void compilesWithCollection() {
        List<Number> input = null;
        assertThat( input, isNotSameAs( null ) );
        assertThat( input, isNotSameAs( input ) );
        assertThat( input, isNotSameAs( "string" ) );
        assertThat( input, isNotSameAs( new String[] { "array" } ) );
        assertThat( input, isNotSameAs( Collections.singletonList( "list" ) ) );
    }

    private void compilesWithWildcardCollection() {
        List<? extends Number> input = null;
        assertThat( input, isNotSameAs( null ) );
        assertThat( input, isNotSameAs( input ) );
        assertThat( input, isNotSameAs( "string" ) );
        assertThat( input, isNotSameAs( new String[] { "array" } ) );
        assertThat( input, isNotSameAs( Collections.singletonList( "list" ) ) );
    }


    @Test( dataProvider = "inputIsSameAsTarget", dataProviderClass = IsSameAsTest.class )
    public void evaluatesToFalseIfInputIsSameAsTarget( Object input, Object target ) {
        assertEvaluatesToFalse( input, isNotSameAs( target ) );
    }

    @Test( dataProvider = "inputIsNotSameAsTarget" )
    public void evaluatesToTrueIfInputIsNotSameAsTarget( Object input, Object target ) {
        assertEvaluatesToTrue( input, isNotSameAs( target ) );
    }


    @Test
    public void producesExpectedMessage() {
        assertProducesExpectedMessage( 1.0, isNotSameAs( 2.0 ), "is not same as <2.0> (0x1.0p1)" );
    }


    @DataProvider
    public static Object[][] inputIsNotSameAsTarget() {
        String str1 = "string";
        String str2 = new String( str1 );

        return new Object[][] {
                { new Object(), new Object() },
                { new Object(), null },
                { null, new Object() },

                { ( byte ) 0, ( byte ) 1 },
                { ( byte ) 0, null },
                { null, ( byte ) 0 },
                { ( byte ) 0, ( short ) 0 },

                { ( short ) 0, ( short ) 1 },
                { ( short ) 0, null },
                { null, ( short ) 0 },
                { ( short ) 0, 0 },

                { 0, 1 },
                { 0, null },
                { null, 0 },
                { 0, 0L },

                { 0L, 1L },
                { 0L, null },
                { null, 0L },
                { 0L, ( byte ) 0 },

                { 0.0f, -0.0f },
                { -0.0f, 0.0f },
                { 0.0f, 1.0f },
                { 0.0f, Float.NaN },
                { Float.NaN, 0.0f },
                { 0.0f, null },
                { null, 0.0f },
                { 0.0f, 0.0 },

                { 0.0, -0.0 },
                { -0.0, 0.0 },
                { 0.0, 1.0 },
                { 0.0, Double.NaN },
                { Double.NaN, 0.0 },
                { 0.0, null },
                { null, 0.0 },
                { 0.0, 0.0f },

                { 'a', 'b' },
                { 'a', null },
                { null, 'a' },
                { 'a', "b" },

                { str1, str2 },
                { "a", "b" },
                { "a", null },
                { null, "a" },
                { "a", 'a' },

                { true, false },
                { false, true },
                { true, null },
                { null, true },
                { true, "true" },


                { BigInteger.ZERO, BigInteger.ONE },
                { new BigInteger( "10" ), new BigInteger( "11" ) },
                { BigInteger.ZERO, BigDecimal.ZERO },

                { BigDecimal.ZERO, BigDecimal.ONE },
                { new BigDecimal( "10.0" ), new BigDecimal( "10.00" ) },
                { BigDecimal.ZERO, BigInteger.ZERO },
        };
    }

}
