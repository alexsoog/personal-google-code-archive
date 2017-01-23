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

import java.util.Deque;

/** An expression component that represents a complex variable. */
final class VariableComponent extends ExpressionComponent {

    /**
     * Checks whether the specified name is valid.
     *
     * @param name the variable name
     *
     * @throws IllegalArgumentException if {@code name} is {@code null}, empty or contains illegal characters
     */
    public static void checkName( String name ) {
        if ( name == null ) {
            throw new IllegalArgumentException( "name is null" );
        }
        if ( name.isEmpty() ) {
            throw new IllegalArgumentException( "name is empty" );
        }
        if ( !Character.isUnicodeIdentifierStart( name.charAt( 0 ) ) ) {
            throw new IllegalArgumentException( "name starts with illegal character: " + name );
        }
        for ( int i = 1; i < name.length(); i++ ) {
            if ( !Character.isUnicodeIdentifierPart( name.charAt( i ) ) ) {
                throw new IllegalArgumentException( "name contains illegal character: " + name );
            }
        }
    }

    /**
     * Checks whether the specified name is valid.
     *
     * @param name the variable name
     *
     * @return {@code true} if the specified string contains a valid name; {@code false} otherwise
     */
    public static boolean isValidName( String name ) {
        if ( name == null ) {
            return false;
        }
        if ( name.isEmpty() ) {
            return false;
        }
        if ( !Character.isUnicodeIdentifierStart( name.charAt( 0 ) ) ) {
            return false;
        }
        for ( int i = 1; i < name.length(); i++ ) {
            if ( !Character.isUnicodeIdentifierPart( name.charAt( i ) ) ) {
                return false;
            }
        }
        return true;
    }


    private final String name;


    /**
     * Creates a new {@code VariableComponent} object.
     *
     * @param name the variable name
     *
     * @throws IllegalArgumentException if {@code name} is {@code null}, empty or contains illegal characters
     */
    VariableComponent( String name ) {
        super( Type.VARIABLE, Associativity.LEFT, Integer.MAX_VALUE, 0, 1 );
        checkName( name );
        this.name = name;
    }


    public String getName() {
        return name;
    }


    @Override
    public String getInfixSymbol() {
        return name;
    }

    @Override
    public String getPostfixSymbol() {
        return name;
    }


    @Override
    public void calculate( Deque<Complex> stack, CalculationContext context ) {
        Complex value = context.getVariable( name );
        if ( value == null ) {
            throw new IllegalArgumentException( "variable is not defined: " + name );
        }
        stack.push( value );
    }

    @Override
    public void format( Deque<FormatToken> stack, FormatContext context ) {
        stack.push( new FormatToken( name, associativity, precedence ) );
    }

    @Override
    public void parse( Deque<ExpressionComponent> stack, ExpressionBuilder output, ParseContext context ) {
        Type previousType = context.getPreviousType();
        if ( previousType == Type.LEFT_PARENTHESIS || previousType == Type.OPERATOR ) {
            output.append( this );
        } else {
            throw new IllegalStateException( type + " follows " + previousType );
        }
    }


    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public boolean equals( Object obj ) {
        if ( this == obj ) {
            return true;
        } else if ( obj instanceof VariableComponent ) {
            VariableComponent component = ( VariableComponent ) obj;
            return name.equals( component.name );
        } else {
            return false;
        }
    }

    @Override
    public String toString() {
        return name;
    }

}
