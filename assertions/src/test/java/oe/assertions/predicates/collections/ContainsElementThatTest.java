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

package oe.assertions.predicates.collections;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import oe.assertions.Predicate;
import oe.assertions.predicates.PredicateTestBase;

import static oe.assertions.Assertions.assertThat;
import static oe.assertions.Predicates.containsElementThat;
import static oe.assertions.Predicates.isEqualTo;
import static oe.assertions.Predicates.isNull;

public class ContainsElementThatTest extends PredicateTestBase {

    private void compilesWithNull() {
        Predicate<Object> condition = null;
        assertThat( null, containsElementThat( condition ) );
    }

    private void compilesWithSet() {
        Set<Double> input = null;
        Predicate<Object> condition = null;
        assertThat( input, containsElementThat( condition ) );
    }

    private void compilesWithList() {
        List<Double> input = null;
        Predicate<Object> condition = null;
        assertThat( input, containsElementThat( condition ) );
    }


    @Test( expectedExceptions = IllegalArgumentException.class )
    public void throwsIAEIfConditionIsNull() {
        containsElementThat( null );
    }


    @Test( dataProvider = "collectionContainsSuitableElement" )
    public <E> void evaluatesToTrueIfCollectionContainsSuitableElement( Iterable<E> collection, Predicate<E> condition ) {
        assertEvaluatesToTrue( collection, containsElementThat( condition ) );
    }

    @Test( dataProvider = "collectionDoesNotContainSuitableElement" )
    public <E> void evaluatesToFalseOfCollectionDoesNotContainSuitableElement( Iterable<E> collection, Predicate<E> condition ) {
        assertEvaluatesToFalse( collection, containsElementThat( condition ) );
    }


    @Test
    public void producesExpectedMessage() {
        assertProducesExpectedMessage( Collections.emptyList(), containsElementThat( isNull() ), "contains element that: is null" );
    }


    @DataProvider
    public static Object[][] collectionContainsSuitableElement() {
        return new Object[][] {
                { Arrays.asList( "first", "second", "third" ), isEqualTo( "second" ) },
                { new HashSet<String>( Arrays.asList( "first", "second", "third" ) ), isEqualTo( "second" ) },
        };
    }

    @DataProvider
    public static Object[][] collectionDoesNotContainSuitableElement() {
        return new Object[][] {
                { Collections.emptyList(), isEqualTo( "element" ) },
                { Arrays.asList( "first", "second", "third" ), isEqualTo( "fourth" ) },
                { new HashSet<String>( Arrays.asList( "first", "second", "third" ) ), isEqualTo( "fourth" ) },
        };
    }

}
