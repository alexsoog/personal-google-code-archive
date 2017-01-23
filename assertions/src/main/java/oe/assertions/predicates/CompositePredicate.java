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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import oe.assertions.Message;
import oe.assertions.Predicate;

/**
 * A composite predicate contains other predicates. The result of the composite predicate is determined by results of
 * contained predicates and an operator used to combine them.
 */
public abstract class CompositePredicate<T> implements Predicate<T> {

    private final CompositeOperator operator;

    private final List<Predicate<? super T>> predicates;


    /**
     * Creates a new predicate.
     *
     * @param operator the operator
     * @param first a predicate to be checked
     * @param second another predicate to be checked
     *
     * @throws IllegalArgumentException if {@code operator}, {@code first} or {@code second} is {@code null}
     */
    protected CompositePredicate( CompositeOperator operator, Predicate<? super T> first, Predicate<? super T> second ) {
        if ( operator == null ) {
            throw new IllegalArgumentException( "operator is null" );
        }
        if ( first == null ) {
            throw new IllegalArgumentException( "first is null" );
        }
        if ( second == null ) {
            throw new IllegalArgumentException( "second is null" );
        }
        this.operator = operator;
        @SuppressWarnings( "unchecked" )
        List<Predicate<? super T>> list = Arrays.<Predicate<? super T>>asList( first, second );
        predicates = list;
    }

    /**
     * Creates a new predicate.
     *
     * @param operator the operator
     * @param first a predicate to be checked
     * @param second another predicate to be checked
     * @param third another predicate to be checked
     *
     * @throws IllegalArgumentException if {@code operator}, {@code first}, {@code second} or {@code third} is {@code
     * null}
     */
    protected CompositePredicate( CompositeOperator operator, Predicate<? super T> first, Predicate<? super T> second, Predicate<? super T> third ) {
        if ( operator == null ) {
            throw new IllegalArgumentException( "operator is null" );
        }
        if ( first == null ) {
            throw new IllegalArgumentException( "first is null" );
        }
        if ( second == null ) {
            throw new IllegalArgumentException( "second is null" );
        }
        if ( third == null ) {
            throw new IllegalArgumentException( "third is null" );
        }
        this.operator = operator;
        @SuppressWarnings( "unchecked" )
        List<Predicate<? super T>> list = Arrays.<Predicate<? super T>>asList( first, second, third );
        predicates = list;
    }

    /**
     * Creates a new predicate.
     *
     * @param operator the operator
     * @param first a predicate to be checked
     * @param second another predicate to be checked
     * @param rest the remaining predicates to be checked
     *
     * @throws IllegalArgumentException if {@code operator}, {@code first}, {@code second} or {@code rest} is {@code
     * null}, or if {@code rest} contains {@code null} elements
     */
    protected CompositePredicate( CompositeOperator operator, Predicate<? super T> first, Predicate<? super T> second, Predicate<? super T>... rest ) {
        if ( operator == null ) {
            throw new IllegalArgumentException( "operator is null" );
        }
        if ( first == null ) {
            throw new IllegalArgumentException( "first is null" );
        }
        if ( second == null ) {
            throw new IllegalArgumentException( "second is null" );
        }
        if ( rest == null ) {
            throw new IllegalArgumentException( "rest is null" );
        }
        List<Predicate<? super T>> restAsList = Arrays.asList( rest );
        if ( restAsList.contains( null ) ) {
            throw new IllegalArgumentException( "rest contains null" );
        }
        this.operator = operator;
        predicates = new ArrayList<Predicate<? super T>>( 2 + rest.length );
        predicates.add( first );
        predicates.add( second );
        predicates.addAll( restAsList );
    }


    @Override
    public boolean check( T input ) {
        List<Boolean> predicateResults = new ArrayList<Boolean>( predicates.size() );
        for ( Predicate<? super T> predicate : predicates ) {
            predicateResults.add( predicate.check( input ) );
        }
        return operator.combine( predicateResults );
    }

    @Override
    public void describe( T input, Message message ) {
        message.append( operator.toString() ).append( ':' );
        Message compositeMessage = message.open( 1 );
        for ( Predicate<? super T> predicate : predicates ) {
            if ( !predicate.check( input ) ) {
                compositeMessage.eol().indent();
                predicate.describe( input, compositeMessage );
            }
        }
        compositeMessage.flush();
        compositeMessage.close();
    }

}
