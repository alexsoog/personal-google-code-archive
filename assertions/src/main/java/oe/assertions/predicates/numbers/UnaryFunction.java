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
 * An unary function.
 *
 * @param <T> the argument type
 * @param <R> the return type
 */
public abstract class UnaryFunction<T extends Number, R> {

    /**
     * Applies this function to the specified argument.
     * <p/>
     * This method delegates to the one of the specialized calculate methods based on the argument type.
     *
     * @param arg the argument
     *
     * @return the result of this function when applied to the specified value
     */
    public R calculate( T arg ) {
        if ( arg instanceof BigDecimal ) {
            return calculateBigDecimal( ( BigDecimal ) arg );
        } else if ( arg instanceof BigInteger ) {
            return calculateBigInteger( ( BigInteger ) arg );
        } else if ( arg instanceof Double ) {
            return calculateDouble( arg.doubleValue() );
        } else if ( arg instanceof Float ) {
            return calculateFloat( arg.floatValue() );
        } else if ( arg instanceof Long || arg instanceof Integer || arg instanceof Short || arg instanceof Byte ) {
            return calculateLong( arg.longValue() );
        } else if ( arg == null ) {
            return calculateNumberUnsafe( arg );
        } else {
            return calculateNumberUnknown( arg );
        }
    }


    /**
     * Invoked if the argument is {@code BigDecimal}.
     *
     * @param arg the argument
     *
     * @return the result of this function when applied to the specified value
     */
    protected abstract R calculateBigDecimal( BigDecimal arg );

    /**
     * Invoked if the argument is {@code BigInteger}.
     *
     * @param arg the argument
     *
     * @return the result of this function when applied to the specified value
     */
    protected abstract R calculateBigInteger( BigInteger arg );

    /**
     * Invoked if the argument is {@code Double}.
     *
     * @param arg the argument
     *
     * @return the result of this function when applied to the specified value
     */
    protected abstract R calculateDouble( double arg );

    /**
     * Invoked if the argument is {@code Float}.
     *
     * @param arg the argument
     *
     * @return the result of this function when applied to the specified value
     */
    protected abstract R calculateFloat( float arg );

    /**
     * Invoked if the argument is {@code Long}, {@code Integer}, {@code Short} or {@code Byte}.
     *
     * @param arg the argument
     *
     * @return the result of this function when applied to the specified value
     */
    protected abstract R calculateLong( long arg );

    /**
     * Invoked if the argument's type is not supported explicitly.
     *
     * @param arg the argument
     *
     * @return the result of this function when applied to the specified value
     */
    protected abstract R calculateNumberUnknown( Number arg );

    /**
     * Invoked if the argument is {@code null}.
     *
     * @param arg the argument
     *
     * @return the result of this function when applied to the specified value
     */
    protected abstract R calculateNumberUnsafe( Number arg );

}
