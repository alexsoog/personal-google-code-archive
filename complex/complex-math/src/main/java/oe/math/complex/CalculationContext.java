/*-
 * Copyright (c) 2009-2010, Oleg Estekhin
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

package oe.math.complex;

import java.util.HashMap;
import java.util.Map;

/** Provides a context for calculation of complex arithmetic expressions. */
public final class CalculationContext {

    private final Map<String, Complex> variables;


    /**
     * Creates a new {@code CalculationContext} object.
     * <p/>
     * The context will contain the following predefined variables: {@link Complex#I i}, {@link Math#E e} and {@link
     * Math#PI pi}.
     */
    public CalculationContext() {
        variables = new HashMap<String, Complex>();
        variables.put( "i", Complex.I );
        variables.put( "e", Complex.real( Math.E ) );
        variables.put( "pi", Complex.real( Math.PI ) );
    }


    /**
     * Sets the variable value.
     *
     * @param name the variable name
     * @param value the variable value
     *
     * @throws IllegalArgumentException if {@code name} or {@code value} is {@code null}
     * @throws IllegalArgumentException if {@code name} is empty or contains illegal characters
     */
    public void setVariable( String name, Complex value ) {
        VariableComponent.checkName( name );
        if ( value == null ) {
            throw new IllegalArgumentException( "value is null" );
        }
        variables.put( name, value );
    }

    /**
     * Returns the variable value.
     *
     * @param name the variable name
     *
     * @return the variable value or {@code null} if the variable with the specified name is not defined
     *
     * @throws IllegalArgumentException if {@code name} is {@code null}
     * @throws IllegalArgumentException if {@code name} is empty or contains illegal characters
     */
    public Complex getVariable( String name ) {
        VariableComponent.checkName( name );
        return variables.get( name );
    }

}
