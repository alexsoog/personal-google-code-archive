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

import java.util.Arrays;

import oe.assertions.Message;
import oe.assertions.Predicate;
import oe.assertions.predicates.numbers.NumberRelationComparator;
import oe.assertions.util.Relation;
import oe.assertions.util.RelationComparator;

/** Evaluates to {@code true} if the input value is equal to the target value. */
public final class IsEqualTo implements Predicate<Object> {

    private final Object target;

    private final boolean direct;


    /**
     * Creates a new predicate.
     *
     * @param target the target value
     */
    public IsEqualTo( Object target ) {
        this( target, true );
    }

    /**
     * Creates a new predicate.
     *
     * @param target the target value
     * @param direct if {@code false} then this predicate checks the inverted condition
     */
    public IsEqualTo( Object target, boolean direct ) {
        this.target = target;
        this.direct = direct;
    }


    @Override
    public boolean check( Object input ) {
        return check( input, target );
    }

    @Override
    public void describe( Object input, Message message ) {
        message.append( direct ? "is equal to" : "is not equal to" ).append( ' ' ).formatValue( target );
    }


    private boolean check( Object input, Object target ) {
        boolean result;
        if ( input == target ) {
            result = true;
        } else if ( input == null || target == null ) {
            result = false;
        } else if ( input instanceof Number && target instanceof Number ) {
            RelationComparator<Number> comparator = new NumberRelationComparator<Number>();
            result = comparator.compare( ( Number ) input, ( Number ) target ) == Relation.EQUAL;
        } else {
            result = checkObject( input, target );
        }
        return direct ? result : !result;
    }


    private boolean checkObject( Object input, Object target ) {
        if ( input == target ) {
            return true;
        } else if ( input == null || target == null ) {
            return false;
        } else if ( input instanceof byte[] && target instanceof byte[] ) {
            return Arrays.equals( ( byte[] ) input, ( byte[] ) target );
        } else if ( input instanceof short[] && target instanceof short[] ) {
            return Arrays.equals( ( short[] ) input, ( short[] ) target );
        } else if ( input instanceof int[] && target instanceof int[] ) {
            return Arrays.equals( ( int[] ) input, ( int[] ) target );
        } else if ( input instanceof long[] && target instanceof long[] ) {
            return Arrays.equals( ( long[] ) input, ( long[] ) target );
        } else if ( input instanceof float[] && target instanceof float[] ) {
            return Arrays.equals( ( float[] ) input, ( float[] ) target );
        } else if ( input instanceof double[] && target instanceof double[] ) {
            return Arrays.equals( ( double[] ) input, ( double[] ) target );
        } else if ( input instanceof char[] && target instanceof char[] ) {
            return Arrays.equals( ( char[] ) input, ( char[] ) target );
        } else if ( input instanceof boolean[] && target instanceof boolean[] ) {
            return Arrays.equals( ( boolean[] ) input, ( boolean[] ) target );
        } else if ( input instanceof Object[] && target instanceof Object[] ) {
            return checkObjectArray( ( Object[] ) input, ( Object[] ) target );
        } else {
            return input.equals( target );
        }
    }

    private boolean checkObjectArray( Object[] input, Object[] target ) {
        if ( input.length == target.length ) {
            for ( int i = 0; i < input.length; i++ ) {
                if ( !checkObject( input[ i ], target[ i ] ) ) {
                    return false;
                }
            }
            return true;
        } else {
            return false;
        }
    }

}
