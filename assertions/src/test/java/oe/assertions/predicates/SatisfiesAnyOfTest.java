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

import org.testng.annotations.Test;

import oe.assertions.Predicate;

import static oe.assertions.Assertions.assertThat;
import static oe.assertions.Predicates.isEqualTo;
import static oe.assertions.Predicates.isInstanceOf;
import static oe.assertions.Predicates.isNull;
import static oe.assertions.Predicates.satisfiesAnyOf;

@SuppressWarnings( "unchecked" )
public class SatisfiesAnyOfTest extends PredicateTestBase {

    private void compilesWithNull() {
        Predicate<Object> predicate = null;
        assertThat( null, satisfiesAnyOf( predicate, predicate ) );
        assertThat( null, satisfiesAnyOf( predicate, predicate, predicate ) );
        assertThat( null, satisfiesAnyOf( predicate, predicate, predicate, predicate ) );
        assertThat( null, satisfiesAnyOf( isNull(), isEqualTo( null ), isInstanceOf( String.class ) ) );
    }

    private void compilesWithObject() {
        Object input = null;
        Predicate<Object> predicate = null;
        assertThat( input, satisfiesAnyOf( predicate, predicate ) );
        assertThat( input, satisfiesAnyOf( predicate, predicate, predicate ) );
        assertThat( input, satisfiesAnyOf( predicate, predicate, predicate, predicate ) );
        assertThat( input, satisfiesAnyOf( isNull(), isEqualTo( null ), isInstanceOf( String.class ) ) );
    }

    private void compilesWithArray() {
        Object[] input = null;
        Predicate<Object[]> predicate = null;
        assertThat( input, satisfiesAnyOf( predicate, predicate ) );
        assertThat( input, satisfiesAnyOf( predicate, predicate, predicate ) );
        assertThat( input, satisfiesAnyOf( predicate, predicate, predicate, predicate ) );
        assertThat( input, satisfiesAnyOf( isNull(), isEqualTo( null ), isInstanceOf( String.class ) ) );
    }

    private void compilesWithCollection() {
        List<Number> input = null;
        Predicate<List<Number>> predicate = null;
        assertThat( input, satisfiesAnyOf( predicate, predicate ) );
        assertThat( input, satisfiesAnyOf( predicate, predicate, predicate ) );
        assertThat( input, satisfiesAnyOf( predicate, predicate, predicate, predicate ) );
        assertThat( input, satisfiesAnyOf( isNull(), isEqualTo( null ), isInstanceOf( String.class ) ) );
    }

    private void compilesWithWildcardCollection() {
        List<? extends Number> input = null;
        Predicate<List<? extends Number>> predicate = null;
        assertThat( input, satisfiesAnyOf( predicate, predicate ) );
        assertThat( input, satisfiesAnyOf( predicate, predicate, predicate ) );
        assertThat( input, satisfiesAnyOf( predicate, predicate, predicate, predicate ) );
        assertThat( input, satisfiesAnyOf( isNull(), isEqualTo( null ), isInstanceOf( String.class ) ) );
    }


    @Test( dataProvider = "inputSatisfiesAllPredicates2", dataProviderClass = SatisfiesAllOfTest.class )
    public void evaluatesToTrueIfInputSatisfiesAllPredicates2( Object input, Predicate<Object> first, Predicate<Object> second ) {
        assertEvaluatesToTrue( input, satisfiesAnyOf( first, second ) );
    }

    @Test( dataProvider = "inputSatisfiesAllPredicates3", dataProviderClass = SatisfiesAllOfTest.class )
    public void evaluatesToTrueIfInputSatisfiesAllPredicates3( Object input, Predicate<Object> first, Predicate<Object> second, Predicate<Object> third ) {
        assertEvaluatesToTrue( input, satisfiesAnyOf( first, second, third ) );
    }

    @Test( dataProvider = "inputSatisfiesAllPredicatesVararg", dataProviderClass = SatisfiesAllOfTest.class )
    public void evaluatesToTrueIfInputSatisfiesAllPredicatesVararg( Object input, Predicate<Object> first, Predicate<Object> second, Predicate<Object> third, Predicate<Object> fourth ) {
        assertEvaluatesToTrue( input, satisfiesAnyOf( first, second, third, fourth ) );
    }


    @Test( dataProvider = "inputSatisfiesSomePredicates2", dataProviderClass = SatisfiesAllOfTest.class )
    public void evaluatesToTrueIfInputSatisfiesSomePredicates2( Object input, Predicate<Object> first, Predicate<Object> second ) {
        assertEvaluatesToTrue( input, satisfiesAnyOf( first, second ) );
    }

    @Test( dataProvider = "inputSatisfiesSomePredicates3", dataProviderClass = SatisfiesAllOfTest.class )
    public void evaluatesToTrueIfInputSatisfiesSomePredicates3( Object input, Predicate<Object> first, Predicate<Object> second, Predicate<Object> third ) {
        assertEvaluatesToTrue( input, satisfiesAnyOf( first, second, third ) );
    }

    @Test( dataProvider = "inputSatisfiesSomePredicatesVararg", dataProviderClass = SatisfiesAllOfTest.class )
    public void evaluatesToTrueIfInputSatisfiesSomePredicatesVararg( Object input, Predicate<Object> first, Predicate<Object> second, Predicate<Object> third, Predicate<Object> fourth ) {
        assertEvaluatesToTrue( input, satisfiesAnyOf( first, second, third, fourth ) );
    }


    @Test( dataProvider = "inputDoesNotSatisfyAnyPredicate2", dataProviderClass = SatisfiesAllOfTest.class )
    public void evaluatesToFalseIfInputDoesNotSatisfyAnyPredicate2( Object input, Predicate<Object> first, Predicate<Object> second ) {
        assertEvaluatesToFalse( input, satisfiesAnyOf( first, second ) );
    }

    @Test( dataProvider = "inputDoesNotSatisfyAnyPredicate3", dataProviderClass = SatisfiesAllOfTest.class )
    public void evaluatesToFalseIfInputDoesNotSatisfyAnyPredicate3( Object input, Predicate<Object> first, Predicate<Object> second, Predicate<Object> third ) {
        assertEvaluatesToFalse( input, satisfiesAnyOf( first, second, third ) );
    }

    @Test( dataProvider = "inputDoesNotSatisfyAnyPredicateVararg", dataProviderClass = SatisfiesAllOfTest.class )
    public void evaluatesToFalseIfInputDoesNotSatisfyAnyPredicateVararg( Object input, Predicate<Object> first, Predicate<Object> second, Predicate<Object> third, Predicate<Object> fourth ) {
        assertEvaluatesToFalse( input, satisfiesAnyOf( first, second, third, fourth ) );
    }


    @Test
    public void producesExpectedMessage() {
        assertProducesExpectedMessage( "string",
                satisfiesAnyOf(
                        isNull(),
                        satisfiesAnyOf(
                                isNull(),
                                isEqualTo( null )
                        ),
                        isEqualTo( null )
                ),
                String.format( "satisfies any of:%n    is null%n    satisfies any of:%n        is null%n        is equal to <null>%n    is equal to <null>" ) );
    }

}
