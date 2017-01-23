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

import java.util.List;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static oe.assertions.Assertions.assertThat;
import static oe.assertions.Predicates.isInstanceOf;

public class IsInstanceOfTest extends PredicateTestBase {

    private void compilesWithNull() {
        assertThat( null, isInstanceOf( String.class ) );
    }

    private void compilesWithObject() {
        Object input = null;
        assertThat( input, isInstanceOf( String.class ) );
    }

    private void compilesWithArray() {
        Object[] input = null;
        assertThat( input, isInstanceOf( String.class ) );
    }

    private void compilesWithCollection() {
        List<Number> input = null;
        assertThat( input, isInstanceOf( String.class ) );
    }

    private void compilesWithWildcardCollection() {
        List<? extends Number> input = null;
        assertThat( input, isInstanceOf( String.class ) );
    }


    @Test( dataProvider = "inputIsInstanceOfTargetClass" )
    public void evaluatesToTrueIfInputIsInstanceOfTargetClass( Object input, Class<?> targetClass ) {
        assertEvaluatesToTrue( input, isInstanceOf( targetClass ) );
    }


    @Test( dataProvider = "inputIsNotInstanceOfTargetClass" )
    public void evaluatesToFalseIfInputIsNotInstanceOfTargetClass( Object input, Class<?> targetClass ) {
        assertEvaluatesToFalse( input, isInstanceOf( targetClass ) );
    }


    @Test
    public void producesExpectedMessage() {
        assertProducesExpectedMessage( 1.0, isInstanceOf( String.class ), "is instance of class java.lang.String" );
    }


    @DataProvider
    public static Object[][] inputIsInstanceOfTargetClass() {
        return new Object[][] {
                { 0, Object.class },
                { 0, Number.class },
                { 0, Integer.class },

                { "string", Object.class },
                { "string", String.class },

                { new int[ 0 ], int[].class },
        };
    }

    @DataProvider
    public static Object[][] inputIsNotInstanceOfTargetClass() {
        return new Object[][] {
                { null, Object.class },

                { "string", Number.class },

                { new int[ 0 ], Object[].class },
        };
    }

}
