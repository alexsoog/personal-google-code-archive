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

import oe.assertions.util.Relation;
import oe.assertions.util.RelationComparator;

/** Compares two numbers using floating-point relation semantics. */
public class NumberRelationComparator<T extends Number> extends BinaryFunction<T, Relation> implements RelationComparator<T> {

    @Override
    public Relation compare( T o1, T o2 ) {
        return calculate( o1, o2 );
    }


    @Override
    protected Relation calculateBigDecimal( BigDecimal a1, BigDecimal a2 ) {
        int i = a1.compareTo( a2 );
        return i < 0 ? Relation.LESS_THEN : i == 0 ? Relation.EQUAL : Relation.GREATER_THEN;
    }

    @Override
    protected Relation calculateBigInteger( BigInteger a1, BigInteger a2 ) {
        int i = a1.compareTo( a2 );
        return i < 0 ? Relation.LESS_THEN : i == 0 ? Relation.EQUAL : Relation.GREATER_THEN;
    }

    @Override
    protected Relation calculateDouble( double a1, double a2 ) {
        if ( a1 < a2 ) {
            return Relation.LESS_THEN;
        } else if ( a1 == a2 ) {
            return Relation.EQUAL;
        } else if ( a1 > a2 ) {
            return Relation.GREATER_THEN;
        } else {
            return Relation.UNORDERED;
        }
    }

    @Override
    protected Relation calculateFloat( float a1, float a2 ) {
        if ( a1 < a2 ) {
            return Relation.LESS_THEN;
        } else if ( a1 == a2 ) {
            return Relation.EQUAL;
        } else if ( a1 > a2 ) {
            return Relation.GREATER_THEN;
        } else {
            return Relation.UNORDERED;
        }
    }

    @Override
    protected Relation calculateLong( long a1, long a2 ) {
        return a1 < a2 ? Relation.LESS_THEN : a1 == a2 ? Relation.EQUAL : Relation.GREATER_THEN;
    }

    @Override
    protected Relation calculateNumberUnknown( Number a1, Number a2 ) {
        return Relation.UNORDERED;
    }

    @Override
    protected Relation calculateNumberUnsafe( Number a1, Number a2 ) {
        return Relation.UNORDERED;
    }

}
