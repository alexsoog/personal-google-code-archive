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

package oe.assertions;

import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import oe.assertions.predicates.IsEqualTo;
import oe.assertions.predicates.IsInstanceOf;
import oe.assertions.predicates.IsNull;
import oe.assertions.predicates.IsSameAs;
import oe.assertions.predicates.SatisfiesAllOf;
import oe.assertions.predicates.SatisfiesAnyOf;
import oe.assertions.predicates.booleans.IsFalse;
import oe.assertions.predicates.booleans.IsTrue;
import oe.assertions.predicates.collections.ContainsElementThat;
import oe.assertions.predicates.numbers.IsCloseTo;
import oe.assertions.predicates.numbers.IsFinite;
import oe.assertions.predicates.numbers.IsGreaterThan;
import oe.assertions.predicates.numbers.IsGreaterThanOrEqualTo;
import oe.assertions.predicates.numbers.IsInfinite;
import oe.assertions.predicates.numbers.IsLessThan;
import oe.assertions.predicates.numbers.IsLessThanOrEqualTo;
import oe.assertions.predicates.numbers.IsNaN;
import oe.assertions.predicates.numbers.IsNegative;
import oe.assertions.predicates.numbers.IsNormal;
import oe.assertions.predicates.numbers.IsPositive;
import oe.assertions.predicates.numbers.IsSubnormal;
import oe.assertions.predicates.numbers.IsZero;
import oe.assertions.predicates.strings.Contains;
import oe.assertions.predicates.strings.EndsWith;
import oe.assertions.predicates.strings.Matches;
import oe.assertions.predicates.strings.StartsWith;

/** Contains static factory methods for creating {@code Predicate} instances. */
public final class Predicates {

    /**
     * Returns a predicate that evaluates to {@code true} if the input value is {@code null}.
     *
     * @return a new predicate
     */
    public static Predicate<Object> isNull() {
        return new IsNull();
    }

    /**
     * Returns a predicate that evaluates to {@code true} if the input value is not {@code null}.
     *
     * @return a new predicate
     */
    public static Predicate<Object> isNotNull() {
        return new IsNull( false );
    }


    /**
     * Returns a predicate that evaluates to {@code true} if the input value is an instance of the target class.
     *
     * @param targetClass the target class
     *
     * @return a new predicate
     *
     * @throws IllegalArgumentException if {@code targetClass} is {@code null}
     */
    public static Predicate<Object> isInstanceOf( Class<?> targetClass ) {
        return new IsInstanceOf( targetClass );
    }


    /**
     * Returns a predicate that evaluates to {@code true} if the input value is equal to the target value.
     *
     * @param target the target value
     *
     * @return a new predicate
     */
    public static Predicate<Object> isEqualTo( Object target ) {
        return new IsEqualTo( target );
    }

    /**
     * Returns a predicate that evaluates to {@code true} if the input value is not equal to the target value.
     *
     * @param target the target value
     *
     * @return a new predicate
     */
    public static Predicate<Object> isNotEqualTo( Object target ) {
        return new IsEqualTo( target, false );
    }

    /**
     * Returns a predicate that evaluates to {@code true} if the input value is the same as the target value.
     *
     * @param target the target value
     *
     * @return a new predicate
     */
    public static Predicate<Object> isSameAs( Object target ) {
        return new IsSameAs( target );
    }

    /**
     * Returns a predicate that evaluates to {@code true} if the input value is not the same as the target value.
     *
     * @param target the target value
     *
     * @return a new predicate
     */
    public static Predicate<Object> isNotSameAs( Object target ) {
        return new IsSameAs( target, false );
    }


    /**
     * Returns a predicate that evaluates to {@code true} if the input value is zero.
     *
     * @return a new predicate
     */
    public static <T extends Number> Predicate<T> isZero() {
        return new IsZero<T>();
    }

    /**
     * Returns a predicate that evaluates to {@code true} if the input value is a positive number.
     *
     * @return a new predicate
     */
    public static <T extends Number> Predicate<T> isPositive() {
        return new IsPositive<T>();
    }

    /**
     * Returns a predicate that evaluates to {@code true} if the input value is a negative number.
     *
     * @return a new predicate
     */
    public static <T extends Number> Predicate<T> isNegative() {
        return new IsNegative<T>();
    }

    /**
     * Returns a predicate that evaluates to {@code true} if the input value is a normal number (not zero, subnormal,
     * infinity or NaN).
     *
     * @return a new predicate
     */
    public static <T extends Number> Predicate<T> isNormal() {
        return new IsNormal<T>();
    }

    /**
     * Returns a predicate that evaluates to {@code true} if the input value is a subnormal number.
     *
     * @return a new predicate
     */
    public static <T extends Number> Predicate<T> isSubnormal() {
        return new IsSubnormal<T>();
    }

    /**
     * Returns a predicate that evaluates to {@code true} if the input value is a finite number.
     *
     * @return a new predicate
     */
    public static <T extends Number> Predicate<T> isFinite() {
        return new IsFinite<T>();
    }

    /**
     * Returns a predicate that evaluates to {@code true} if the input value is an infinite number.
     *
     * @return a new predicate
     */
    public static <T extends Number> Predicate<T> isInfinite() {
        return new IsInfinite<T>();
    }

    /**
     * Returns a predicate that evaluates to {@code true} if the input value is NaN.
     *
     * @return a new predicate
     */
    public static <T extends Number> Predicate<T> isNaN() {
        return new IsNaN<T>();
    }


    /**
     * Returns a predicate that evaluates to {@code true} if the input value is greater than the target value.
     *
     * @param target the target value
     *
     * @return a new predicate
     *
     * @throws IllegalArgumentException if {@code target} is {@code null}
     */
    public static <T extends Number> Predicate<T> isGreaterThan( T target ) {
        return new IsGreaterThan<T>( target );
    }

    /**
     * Returns a predicate that evaluates to {@code true} if the input value is greater than or equal to the target
     * value.
     *
     * @param target the target value
     *
     * @return a new predicate
     *
     * @throws IllegalArgumentException if {@code target} is {@code null}
     */
    public static <T extends Number> Predicate<T> isGreaterThanOrEqualTo( T target ) {
        return new IsGreaterThanOrEqualTo<T>( target );
    }

    /**
     * Returns a predicate that evaluates to {@code true} if the input value is less than the target value.
     *
     * @param target the target value
     *
     * @return a new predicate
     *
     * @throws IllegalArgumentException if {@code target} is {@code null}
     */
    public static <T extends Number> Predicate<T> isLessThan( T target ) {
        return new IsLessThan<T>( target );
    }

    /**
     * Returns a predicate that evaluates to {@code true} if the input value is less than or equal to the target value.
     *
     * @param target the target value
     *
     * @return a new predicate
     *
     * @throws IllegalArgumentException if {@code target} is {@code null}
     */
    public static <T extends Number> Predicate<T> isLessThanOrEqualTo( T target ) {
        return new IsLessThanOrEqualTo<T>( target );
    }


    /**
     * Returns a predicate that evaluates to {@code true} if the input value is close to the target value.
     *
     * @param target the target value
     *
     * @return a new predicate
     *
     * @throws IllegalArgumentException if {@code target} is {@code null}
     */
    public static <T extends Number> IsCloseTo<T> isCloseTo( T target ) {
        return new IsCloseTo<T>( target );
    }


    /**
     * Returns a predicate that evaluates to {@code true} if the input value is {@code true}.
     *
     * @return a new predicate
     */
    public static Predicate<Boolean> isTrue() {
        return new IsTrue();
    }

    /**
     * Returns a predicate that evaluates to {@code true} if the input value is {@code false}.
     *
     * @return a new predicate
     */
    public static Predicate<Boolean> isFalse() {
        return new IsFalse();
    }


    /**
     * Returns a predicate that evaluates to {@code true} if the input value contains the specified substring.
     *
     * @param substring the substring to search for
     *
     * @return a new predicate
     *
     * @throws IllegalArgumentException if {@code substring} is {@code null}
     */
    public static Predicate<String> contains( String substring ) {
        return new Contains( substring );
    }

    /**
     * Returns a predicate that evaluates to {@code true} if the input value starts with the specified prefix.
     *
     * @param prefix the prefix
     *
     * @return a new predicate
     *
     * @throws IllegalArgumentException if {@code prefix} is {@code null}
     */
    public static Predicate<String> startsWith( String prefix ) {
        return new StartsWith( prefix );
    }

    /**
     * Returns a predicate that evaluates to {@code true} if the input value ends with the specified suffix.
     *
     * @param suffix the suffix
     *
     * @return a new predicate
     *
     * @throws IllegalArgumentException if {@code suffix} is {@code null}
     */
    public static Predicate<String> endsWith( String suffix ) {
        return new EndsWith( suffix );
    }

    /**
     * Returns a predicate that evaluates to {@code true} if the input value matches the specified regular expression.
     *
     * @param regex the regular expression
     *
     * @return a new predicate
     *
     * @throws IllegalArgumentException if {@code regex} is {@code null}
     * @throws PatternSyntaxException if the regular expression's syntax is invalid
     * @see Pattern
     */
    public static Predicate<String> matches( String regex ) {
        return new Matches( regex );
    }


    /**
     * Returns a predicate that evaluates to {@code true} if the input collection contains an element that satisfies the
     * specified condition.
     *
     * @param condition the element condition
     *
     * @return a new predicate
     *
     * @throws IllegalArgumentException if {@code condition} is {@code null}
     */
    public static <E, T extends Iterable<? extends E>> Predicate<T> containsElementThat( Predicate<E> condition ) {
        return new ContainsElementThat<E, T>( condition );
    }


    /**
     * Returns a predicate that evaluates to {@code true} if all of the specified predicates evaluate to {@code true}.
     *
     * @param first a predicate to be checked
     * @param second another predicate to be checked
     *
     * @return a new predicate
     *
     * @throws IllegalArgumentException if {@code first} or {@code second} is {@code null}
     */
    public static <T> Predicate<T> satisfiesAllOf( Predicate<? super T> first, Predicate<? super T> second ) {
        return new SatisfiesAllOf<T>( first, second );
    }

    /**
     * Returns a predicate that evaluates to {@code true} if all of the specified predicates evaluate to {@code true}.
     *
     * @param first a predicate to be checked
     * @param second another predicate to be checked
     * @param third another predicate to be checked
     *
     * @return a new predicate
     *
     * @throws IllegalArgumentException if {@code first}, {@code second} or {@code third} is {@code null}
     */
    public static <T> Predicate<T> satisfiesAllOf( Predicate<? super T> first, Predicate<? super T> second, Predicate<? super T> third ) {
        return new SatisfiesAllOf<T>( first, second, third );
    }

    /**
     * Returns a predicate that evaluates to {@code true} if all of the specified predicates evaluate to {@code true}.
     *
     * @param first a predicate to be checked
     * @param second another predicate to be checked
     * @param rest the remaining predicates to be checked
     *
     * @return a new predicate
     *
     * @throws IllegalArgumentException if {@code first}, {@code second} or {@code rest} is {@code null}, or if {@code
     * rest} contains {@code null} elements
     */
    public static <T> Predicate<T> satisfiesAllOf( Predicate<? super T> first, Predicate<? super T> second, Predicate<? super T>... rest ) {
        return new SatisfiesAllOf<T>( first, second, rest );
    }


    /**
     * Returns a predicate that evaluates to {@code true} if any of the specified predicates evaluates to {@code true}.
     *
     * @param first a predicate to be checked
     * @param second another predicate to be checked
     *
     * @return a new predicate
     *
     * @throws IllegalArgumentException if {@code first} or {@code second} is {@code null}
     */
    public static <T> Predicate<T> satisfiesAnyOf( Predicate<? super T> first, Predicate<? super T> second ) {
        return new SatisfiesAnyOf<T>( first, second );
    }

    /**
     * Returns a predicate that evaluates to {@code true} if any of the specified predicates evaluates to {@code true}.
     *
     * @param first a predicate to be checked
     * @param second another predicate to be checked
     * @param third another predicate to be checked
     *
     * @return a new predicate
     *
     * @throws IllegalArgumentException if {@code first}, {@code second} or {@code third} is {@code null}
     */
    public static <T> Predicate<T> satisfiesAnyOf( Predicate<? super T> first, Predicate<? super T> second, Predicate<? super T> third ) {
        return new SatisfiesAnyOf<T>( first, second, third );
    }

    /**
     * Returns a predicate that evaluates to {@code true} if any of the specified predicates evaluates to {@code true}.
     *
     * @param first a predicate to be checked
     * @param second another predicate to be checked
     * @param rest the remaining predicates to be checked
     *
     * @return a new predicate
     *
     * @throws IllegalArgumentException if {@code first}, {@code second} or {@code rest} is {@code null}, or if {@code
     * rest} contains {@code null} elements
     */
    public static <T> Predicate<T> satisfiesAnyOf( Predicate<? super T> first, Predicate<? super T> second, Predicate<? super T>... rest ) {
        return new SatisfiesAnyOf<T>( first, second, rest );
    }


    private Predicates() {
    }

}
