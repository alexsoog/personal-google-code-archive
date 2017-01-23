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

/**
 * A binary function.
 *
 * @param <T> the arguments type
 * @param <R> the return type
 */
public abstract class BinaryFunction<T extends Number, R> {

    /**
     * Applies this function to the specified arguments.
     * <p/>
     * This method delegates to the one of the specialized calculate methods based on the arguments type.
     *
     * @param a1 the first argument
     * @param a2 the second argument
     *
     * @return the result of this function when applied to the specified values
     */
    public R calculate( T a1, T a2 ) {
        if ( a1 instanceof BigDecimal && a2 instanceof BigDecimal ) {
            return calculateBigDecimal( ( BigDecimal ) a1, ( BigDecimal ) a2 );
        } else if ( a1 instanceof BigInteger && a2 instanceof BigInteger ) {
            return calculateBigInteger( ( BigInteger ) a1, ( BigInteger ) a2 );
        } else if ( a1 instanceof Double && a2 instanceof Double ) {
            return calculateDouble( a1.doubleValue(), a2.doubleValue() );
        } else if ( a1 instanceof Float && a2 instanceof Float ) {
            return calculateFloat( a1.floatValue(), a2.floatValue() );
        } else if ( a1 instanceof Long && a2 instanceof Long || a1 instanceof Integer && a2 instanceof Integer || a1 instanceof Short && a2 instanceof Short || a1 instanceof Byte && a2 instanceof Byte ) {
            return calculateLong( a1.longValue(), a2.longValue() );
        } else if ( a1 == null || a2 == null || !a1.getClass().equals( a2.getClass() ) ) {
            return calculateNumberUnsafe( a1, a2 );
        } else {
            return calculateNumberUnknown( a1, a2 );
        }
    }


    /**
     * Invoked if both arguments are {@code BigDecimal}.
     *
     * @param a1 the first argument
     * @param a2 the second argument
     *
     * @return the result of this function when applied to the specified values
     */
    protected abstract R calculateBigDecimal( BigDecimal a1, BigDecimal a2 );

    /**
     * Invoked if both arguments are {@code BigInteger}.
     *
     * @param a1 the first argument
     * @param a2 the second argument
     *
     * @return the result of this function when applied to the specified values
     */
    protected abstract R calculateBigInteger( BigInteger a1, BigInteger a2 );

    /**
     * Invoked if both arguments are {@code Double}.
     *
     * @param a1 the first argument
     * @param a2 the second argument
     *
     * @return the result of this function when applied to the specified values
     */
    protected abstract R calculateDouble( double a1, double a2 );

    /**
     * Invoked if both arguments are {@code Float}.
     *
     * @param a1 the first argument
     * @param a2 the second argument
     *
     * @return the result of this function when applied to the specified values
     */
    protected abstract R calculateFloat( float a1, float a2 );

    /**
     * Invoked if both arguments are {@code Long}, {@code Integer}, {@code Short} or {@code Byte}.
     *
     * @param a1 the first argument
     * @param a2 the second argument
     *
     * @return the result of this function when applied to the specified values
     */
    protected abstract R calculateLong( long a1, long a2 );

    /**
     * Invoked if both arguments have the same type which is not supported explicitly.
     *
     * @param a1 the first argument
     * @param a2 the second argument
     *
     * @return the result of this function when applied to the specified values
     */
    protected abstract R calculateNumberUnknown( Number a1, Number a2 );

    /**
     * Invoked if one of the arguments is {@code null}, or if argument's types are different.
     *
     * @param a1 the first argument
     * @param a2 the second argument
     *
     * @return the result of this function when applied to the specified values
     */
    protected abstract R calculateNumberUnsafe( Number a1, Number a2 );

}
