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

import java.util.Collections;
import java.util.List;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static oe.assertions.Assertions.assertThat;
import static oe.assertions.Predicates.isNotEqualTo;

public class IsNotEqualToTest extends PredicateTestBase {

    private void compilesWithNull() {
        assertThat( null, isNotEqualTo( null ) );
        assertThat( null, isNotEqualTo( "string" ) );
        assertThat( null, isNotEqualTo( new String[] { "array" } ) );
        assertThat( null, isNotEqualTo( Collections.singletonList( "list" ) ) );
    }

    private void compilesWithObject() {
        Object input = null;
        assertThat( input, isNotEqualTo( null ) );
        assertThat( input, isNotEqualTo( input ) );
        assertThat( input, isNotEqualTo( "string" ) );
        assertThat( input, isNotEqualTo( new String[] { "array" } ) );
        assertThat( input, isNotEqualTo( Collections.singletonList( "list" ) ) );
    }

    private void compilesWithArray() {
        Object[] input = null;
        assertThat( input, isNotEqualTo( null ) );
        assertThat( input, isNotEqualTo( input ) );
        assertThat( input, isNotEqualTo( "string" ) );
        assertThat( input, isNotEqualTo( new String[] { "array" } ) );
        assertThat( input, isNotEqualTo( Collections.singletonList( "list" ) ) );
    }

    private void compilesWithCollection() {
        List<Number> input = null;
        assertThat( input, isNotEqualTo( null ) );
        assertThat( input, isNotEqualTo( input ) );
        assertThat( input, isNotEqualTo( "string" ) );
        assertThat( input, isNotEqualTo( new String[] { "array" } ) );
        assertThat( input, isNotEqualTo( Collections.singletonList( "list" ) ) );
    }

    private void compilesWithWildcardCollection() {
        List<? extends Number> input = null;
        assertThat( input, isNotEqualTo( null ) );
        assertThat( input, isNotEqualTo( input ) );
        assertThat( input, isNotEqualTo( "string" ) );
        assertThat( input, isNotEqualTo( new String[] { "array" } ) );
        assertThat( input, isNotEqualTo( Collections.singletonList( "list" ) ) );
    }


    @Test( dataProvider = "inputIsEqualToTarget", dataProviderClass = IsEqualToTest.class )
    public void evaluatesToFalseIfInputIsEqualToTarget( Object input, Object target ) {
        assertEvaluatesToFalse( input, isNotEqualTo( target ) );
    }

    @Test( dataProvider = "arrayIsEqualToTarget", dataProviderClass = IsEqualToTest.class )
    public void evaluatesToFalseIfInputIsArrayEqualToTarget( Object input, Object target ) {
        assertEvaluatesToFalse( input, isNotEqualTo( target ) );
    }


    @Test( dataProvider = "inputIsNotEqualToTarget" )
    public void evaluatesToTrueIfInputIsNotEqualToTarget( Object input, Object target ) {
        assertEvaluatesToTrue( input, isNotEqualTo( target ) );
    }

    @Test( dataProvider = "arrayIsNotEqualToTarget" )
    public void evaluatesToTrueIfInputIsArrayNotEqualToTarget( Object input, Object target ) {
        assertEvaluatesToTrue( input, isNotEqualTo( target ) );
    }


    @Test
    public void producesExpectedMessage() {
        assertProducesExpectedMessage( 1.0, isNotEqualTo( 2.0 ), "is not equal to <2.0> (0x1.0p1)" );
    }


    @DataProvider
    public static Object[][] inputIsNotEqualToTarget() {
        return new Object[][] {
                { new Object(), new Object() },
                { new Object(), null },
                { null, new Object() },

                { 'a', 'b' },
                { 'a', null },
                { null, 'a' },

                { "a", "b" },
                { "a", null },
                { null, "a" },

                { true, false },
                { false, true },
                { true, null },
                { null, true },
        };
    }

    @DataProvider
    public static Object[][] arrayIsNotEqualToTarget() {
        return new Object[][] {
                { new byte[] { 0, 1, 2 }, new byte[] { 0, 3, 2 } },
                { new Byte[] { 0, 1, 2 }, new Byte[] { 0, 1, 2, 3 } },

                { new short[] { 0, 1, 2 }, new short[] { 0, 3, 2 } },
                { new Short[] { 0, 1, 2 }, new Short[] { 0, 1, 2, 3 } },

                { new int[] { 0, 1, 2 }, new int[] { 0, 3, 2 } },
                { new Integer[] { 0, 1, 2 }, new Integer[] { 0, 1, 2, 3 } },

                { new long[] { 0L, 1L, 2L }, new long[] { 0L, 3L, 2L } },
                { new Long[] { 0L, 1L, 2L }, new Long[] { 0L, 1L, 2L, 3L } },

                { new float[] { 0.0f, 1.0f, 2.0f }, new float[] { 0.0f, 3.0f, 2.0f } },
                { new Float[] { 0.0f, 1.0f, 2.0f }, new Float[] { 0.0f, 1.0f, 2.0f, 3.0f } },

                { new double[] { 0.0, 1.0, 2.0 }, new double[] { 0.0, 3.0, 2.0 } },
                { new Double[] { 0.0, 1.0, 2.0 }, new Double[] { 0.0, 1.0, 2.0, 3.0 } },

                { new char[] { 'a', 'b', 'c' }, new char[] { 'a', 'd', 'c' } },
                { new Character[] { 'a', 'b', 'c' }, new Character[] { 'a', 'b', 'c', 'd' } },

                { new String[] { "a", "b", "c" }, new String[] { "a", "d", "c" } },
                { new String[] { "a", "b", "c" }, new String[] { "a", "b", "c", "d" } },

                { new boolean[] { true, false, true }, new boolean[] { true, true, true } },
                { new Boolean[] { true, false, true }, new Boolean[] { true, false, true, false } },

                {
                        new Object[] {
                                ( byte ) 0, ( short ) 1, 2, 3L, 4.0f, 5.0d,
                                'a', "b",
                                true, false,
                                new byte[] { 0, 3, 2 },
                        },
                        new Object[] {
                                ( byte ) 0, ( short ) 1, 2, 3L, 4.0f, 5.0d,
                                'a', "b",
                                true, false,
                                new byte[] { 0, 1, 2 },
                        }
                },
        };
    }

}
