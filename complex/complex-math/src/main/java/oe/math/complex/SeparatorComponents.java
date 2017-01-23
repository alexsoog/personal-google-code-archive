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

/** Contains predefined separator components. */
final class SeparatorComponents {

    public static final SeparatorComponent LEFT_PARENTHESIS = new SeparatorComponent( ExpressionComponent.Type.LEFT_PARENTHESIS, "(" ) {
        @Override
        public void parse( Deque<ExpressionComponent> stack, ExpressionBuilder output, ParseContext context ) {
            Type previousType = context.getPreviousType();
            if ( previousType == Type.LEFT_PARENTHESIS || previousType == Type.OPERATOR || previousType == Type.FUNCTION ) {
                stack.push( this );
            } else {
                throw new IllegalStateException( type + " follows " + previousType );
            }
        }
    };

    public static final SeparatorComponent RIGHT_PARENTHESIS = new SeparatorComponent( ExpressionComponent.Type.RIGHT_PARENTHESIS, ")" ) {
        @Override
        public void parse( Deque<ExpressionComponent> stack, ExpressionBuilder output, ParseContext context ) {
            Type previousType = context.getPreviousType();
            if ( previousType == Type.RIGHT_PARENTHESIS || previousType == Type.COMPLEX || previousType == Type.VARIABLE ) {
                ExpressionComponent component = stack.peek();
                while ( component != null && component.getType() != Type.LEFT_PARENTHESIS ) {
                    output.append( stack.pop() );
                    component = stack.peek();
                }
                if ( component == null ) {
                    throw new IllegalStateException( "mismatched right parenthesis" );
                }
                if ( !LEFT_PARENTHESIS.equals( component ) ) {
                    throw new AssertionError( component );
                }
                stack.pop();
                component = stack.peek();
                if ( component != null && component.getType() == Type.FUNCTION ) {
                    output.append( stack.pop() );
                }
            } else {
                throw new IllegalStateException( type + " follows " + previousType );
            }
        }
    };


    private static final SeparatorComponent[] VALUES = {
            LEFT_PARENTHESIS,
            RIGHT_PARENTHESIS,
    };

    public static SeparatorComponent[] values() {
        return VALUES.clone();
    }


    private SeparatorComponents() {
    }

}
