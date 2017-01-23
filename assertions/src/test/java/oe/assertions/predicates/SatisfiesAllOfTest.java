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

import oe.assertions.Predicate;

import static oe.assertions.Assertions.assertThat;
import static oe.assertions.Predicates.endsWith;
import static oe.assertions.Predicates.isEqualTo;
import static oe.assertions.Predicates.isInstanceOf;
import static oe.assertions.Predicates.isNotNull;
import static oe.assertions.Predicates.isNull;
import static oe.assertions.Predicates.satisfiesAllOf;
import static oe.assertions.Predicates.startsWith;

@SuppressWarnings( "unchecked" )
public class SatisfiesAllOfTest extends PredicateTestBase {

    private void compilesWithNull() {
        Predicate<Object> predicate = null;
        assertThat( null, satisfiesAllOf( predicate, predicate ) );
        assertThat( null, satisfiesAllOf( predicate, predicate, predicate ) );
        assertThat( null, satisfiesAllOf( predicate, predicate, predicate, predicate ) );
        assertThat( null, satisfiesAllOf( isNull(), isEqualTo( null ), isInstanceOf( String.class ) ) );
    }

    private void compilesWithObject() {
        Object input = null;
        Predicate<Object> predicate = null;
        assertThat( input, satisfiesAllOf( predicate, predicate ) );
        assertThat( input, satisfiesAllOf( predicate, predicate, predicate ) );
        assertThat( input, satisfiesAllOf( predicate, predicate, predicate, predicate ) );
        assertThat( input, satisfiesAllOf( isNull(), isEqualTo( null ), isInstanceOf( String.class ) ) );
    }

    private void compilesWithArray() {
        Object[] input = null;
        Predicate<Object[]> predicate = null;
        assertThat( input, satisfiesAllOf( predicate, predicate ) );
        assertThat( input, satisfiesAllOf( predicate, predicate, predicate ) );
        assertThat( input, satisfiesAllOf( predicate, predicate, predicate, predicate ) );
        assertThat( input, satisfiesAllOf( isNull(), isEqualTo( null ), isInstanceOf( String.class ) ) );
    }

    private void compilesWithCollection() {
        List<Number> input = null;
        Predicate<List<Number>> predicate = null;
        assertThat( input, satisfiesAllOf( predicate, predicate ) );
        assertThat( input, satisfiesAllOf( predicate, predicate, predicate ) );
        assertThat( input, satisfiesAllOf( predicate, predicate, predicate, predicate ) );
        assertThat( input, satisfiesAllOf( isNull(), isEqualTo( null ), isInstanceOf( String.class ) ) );
    }

    private void compilesWithWildcardCollection() {
        List<? extends Number> input = null;
        Predicate<List<? extends Number>> predicate = null;
        assertThat( input, satisfiesAllOf( predicate, predicate ) );
        assertThat( input, satisfiesAllOf( predicate, predicate, predicate ) );
        assertThat( input, satisfiesAllOf( predicate, predicate, predicate, predicate ) );
        assertThat( input, satisfiesAllOf( isNull(), isEqualTo( null ), isInstanceOf( String.class ) ) );
    }


    @Test( dataProvider = "inputSatisfiesAllPredicates2" )
    public void evaluatesToTrueIfInputSatisfiesAllPredicates2( Object input, Predicate<Object> first, Predicate<Object> second ) {
        assertEvaluatesToTrue( input, satisfiesAllOf( first, second ) );
    }

    @Test( dataProvider = "inputSatisfiesAllPredicates3" )
    public void evaluatesToTrueIfInputSatisfiesAllPredicates3( Object input, Predicate<Object> first, Predicate<Object> second, Predicate<Object> third ) {
        assertEvaluatesToTrue( input, satisfiesAllOf( first, second, third ) );
    }

    @Test( dataProvider = "inputSatisfiesAllPredicatesVararg" )
    public void evaluatesToTrueIfInputSatisfiesAllPredicatesVararg( Object input, Predicate<Object> first, Predicate<Object> second, Predicate<Object> third, Predicate<Object> fourth ) {
        assertEvaluatesToTrue( input, satisfiesAllOf( first, second, third, fourth ) );
    }


    @Test( dataProvider = "inputSatisfiesSomePredicates2" )
    public void evaluatesToFalseIfInputSatisfiesSomePredicates2( Object input, Predicate<Object> first, Predicate<Object> second ) {
        assertEvaluatesToFalse( input, satisfiesAllOf( first, second ) );
    }

    @Test( dataProvider = "inputSatisfiesSomePredicates3" )
    public void evaluatesToFalseIfInputSatisfiesSomePredicates3( Object input, Predicate<Object> first, Predicate<Object> second, Predicate<Object> third ) {
        assertEvaluatesToFalse( input, satisfiesAllOf( first, second, third ) );
    }

    @Test( dataProvider = "inputSatisfiesSomePredicatesVararg" )
    public void evaluatesToFalseIfInputSatisfiesSomePredicatesVararg( Object input, Predicate<Object> first, Predicate<Object> second, Predicate<Object> third, Predicate<Object> fourth ) {
        assertEvaluatesToFalse( input, satisfiesAllOf( first, second, third, fourth ) );
    }


    @Test( dataProvider = "inputDoesNotSatisfyAnyPredicate2" )
    public void evaluatesToFalseIfInputDoesNotSatisfyAnyPredicate2( Object input, Predicate<Object> first, Predicate<Object> second ) {
        assertEvaluatesToFalse( input, satisfiesAllOf( first, second ) );
    }

    @Test( dataProvider = "inputDoesNotSatisfyAnyPredicate3" )
    public void evaluatesToFalseIfInputDoesNotSatisfyAnyPredicate3( Object input, Predicate<Object> first, Predicate<Object> second, Predicate<Object> third ) {
        assertEvaluatesToFalse( input, satisfiesAllOf( first, second, third ) );
    }

    @Test( dataProvider = "inputDoesNotSatisfyAnyPredicateVararg" )
    public void evaluatesToFalseIfInputDoesNotSatisfyAnyPredicateVararg( Object input, Predicate<Object> first, Predicate<Object> second, Predicate<Object> third, Predicate<Object> fourth ) {
        assertEvaluatesToFalse( input, satisfiesAllOf( first, second, third, fourth ) );
    }


    @Test
    public void producesExpectedMessage() {
        assertProducesExpectedMessage( "string",
                satisfiesAllOf(
                        isNull(),
                        satisfiesAllOf(
                                isNull(),
                                isEqualTo( null )
                        ),
                        isEqualTo( null )
                ),
                String.format( "satisfies all of:%n    is null%n    satisfies all of:%n        is null%n        is equal to <null>%n    is equal to <null>" ) );
    }


    @DataProvider
    public static Object[][] inputSatisfiesAllPredicates2() {
        return new Object[][] {
                { null, isNull(), isNull() },
                { "string", isNotNull(), isInstanceOf( String.class ) },
        };
    }

    @DataProvider
    public static Object[][] inputSatisfiesAllPredicates3() {
        return new Object[][] {
                { null, isNull(), isNull(), isNull() },
                { "string", isNotNull(), isInstanceOf( String.class ), startsWith( "str" ) },
        };
    }

    @DataProvider
    public static Object[][] inputSatisfiesAllPredicatesVararg() {
        return new Object[][] {
                { null, isNull(), isNull(), isNull(), isNull() },
                { "string", isNotNull(), isInstanceOf( String.class ), startsWith( "str" ), endsWith( "ing" ) },
        };
    }


    @DataProvider
    public static Object[][] inputSatisfiesSomePredicates2() {
        return new Object[][] {
                { null, isNull(), isNotNull() },
                { "string", isNotNull(), isInstanceOf( Number.class ) },
                { "string", isNull(), isInstanceOf( String.class ) },
        };
    }

    @DataProvider
    public static Object[][] inputSatisfiesSomePredicates3() {
        return new Object[][] {
                { null, isNull(), isNotNull(), isNull() },
                { "string", isNotNull(), isInstanceOf( Number.class ), startsWith( "prefix" ) },
                { "string", isNull(), isInstanceOf( String.class ), startsWith( "prefix" ) },
                { "string", isNull(), isInstanceOf( Number.class ), startsWith( "str" ) },
        };
    }

    @DataProvider
    public static Object[][] inputSatisfiesSomePredicatesVararg() {
        return new Object[][] {
                { null, isNull(), isNotNull(), isNull(), isNull() },
                { "string", isNotNull(), isInstanceOf( Number.class ), startsWith( "prefix" ), endsWith( "suffix" ) },
                { "string", isNull(), isInstanceOf( String.class ), startsWith( "prefix" ), endsWith( "suffix" ) },
                { "string", isNull(), isInstanceOf( Number.class ), startsWith( "str" ), endsWith( "suffix" ) },
                { "string", isNull(), isInstanceOf( Number.class ), startsWith( "prefix" ), endsWith( "ing" ) },
        };
    }


    @DataProvider
    public static Object[][] inputDoesNotSatisfyAnyPredicate2() {
        return new Object[][] {
                { null, isNotNull(), isNotNull() },
                { "string", isNull(), isInstanceOf( Number.class ) },
        };
    }

    @DataProvider
    public static Object[][] inputDoesNotSatisfyAnyPredicate3() {
        return new Object[][] {
                { null, isNotNull(), isNotNull(), isNotNull() },
                { "string", isNull(), isInstanceOf( Number.class ), startsWith( "prefix" ) },
        };
    }

    @DataProvider
    public static Object[][] inputDoesNotSatisfyAnyPredicateVararg() {
        return new Object[][] {
                { null, isNotNull(), isNotNull(), isNotNull(), isNotNull() },
                { "string", isNull(), isInstanceOf( Number.class ), startsWith( "prefix" ), endsWith( "suffix" ) },
        };
    }

}
