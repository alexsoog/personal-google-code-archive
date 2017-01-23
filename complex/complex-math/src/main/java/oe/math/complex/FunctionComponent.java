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

/** An expression component that represents a function with complex arguments. */
abstract class FunctionComponent extends ExpressionComponent {

    protected final String infixSymbol;

    protected final String postfixSymbol;


    /**
     * Creates a new {@code FunctionComponent} object.
     *
     * @param type the type of this component
     * @param associativity the associativity of this component
     * @param precedence the precedence of this component
     * @param operandsCount the number of operands required by this component
     * @param resultsCount the number of return values produced by this component
     * @param infixSymbol the symbol that represents this component in the infix notation
     * @param postfixSymbol the symbol that represents this component in the postfix notation
     *
     * @throws IllegalArgumentException if {@code type} or {@code associativity} is {@code null}
     * @throws IllegalArgumentException if {@code operandsCount} or {@code resultsCount} is negative
     * @throws IllegalArgumentException if {@code infixSymbol} or {@code postfixSymbol} is {@code null}
     */
    protected FunctionComponent( Type type, Associativity associativity, int precedence, int operandsCount, int resultsCount, String infixSymbol, String postfixSymbol ) {
        super( type, associativity, precedence, operandsCount, resultsCount );
        if ( infixSymbol == null ) {
            throw new IllegalArgumentException( "infixSymbol is null" );
        }
        if ( postfixSymbol == null ) {
            throw new IllegalArgumentException( "postfixSymbol is null" );
        }
        this.infixSymbol = infixSymbol;
        this.postfixSymbol = postfixSymbol;
    }


    @Override
    public String getInfixSymbol() {
        return infixSymbol;
    }

    @Override
    public String getPostfixSymbol() {
        return postfixSymbol;
    }


    protected void formatUnaryOperator( Deque<FormatToken> stack, FormatContext context ) {
        if ( operandsCount != 1 ) {
            throw new AssertionError( operandsCount );
        }
        StringBuilder buffer = new StringBuilder();
        FormatToken right = stack.pop();

        // todo review space around unary operators or between unary operator and argument
        buffer.append( infixSymbol );

        boolean rightParentheses = requiresParenthesesForRightOperand( right );
        if ( rightParentheses ) {
            buffer.append( '(' );
            if ( context.isSpaceWithinParentheses() ) {
                buffer.append( ' ' );
            }
        }
        buffer.append( right.getValue() );
        if ( rightParentheses ) {
            if ( context.isSpaceWithinParentheses() ) {
                buffer.append( ' ' );
            }
            buffer.append( ')' );
        }

        stack.push( new FormatToken( buffer.toString(), associativity, precedence ) );
    }

    protected void formatBinaryOperator( Deque<FormatToken> stack, FormatContext context ) {
        if ( operandsCount != 2 ) {
            throw new AssertionError( operandsCount );
        }
        StringBuilder buffer = new StringBuilder();
        FormatToken right = stack.pop();
        FormatToken left = stack.pop();

        boolean leftParentheses = requiresParenthesesForLeftOperand( left );
        if ( leftParentheses ) {
            buffer.append( '(' );
            if ( context.isSpaceWithinParentheses() ) {
                buffer.append( ' ' );
            }
        }
        buffer.append( left.getValue() );
        if ( leftParentheses ) {
            if ( context.isSpaceWithinParentheses() ) {
                buffer.append( ' ' );
            }
            buffer.append( ')' );
        }

        if ( context.isSpaceAroundBinaryOperators() ) {
            buffer.append( ' ' );
        }
        buffer.append( infixSymbol );
        if ( context.isSpaceAroundBinaryOperators() ) {
            buffer.append( ' ' );
        }

        boolean rightParentheses = requiresParenthesesForRightOperand( right );
        if ( rightParentheses ) {
            buffer.append( '(' );
            if ( context.isSpaceWithinParentheses() ) {
                buffer.append( ' ' );
            }
        }
        buffer.append( right.getValue() );
        if ( rightParentheses ) {
            if ( context.isSpaceWithinParentheses() ) {
                buffer.append( ' ' );
            }
            buffer.append( ')' );
        }

        stack.push( new FormatToken( buffer.toString(), associativity, precedence ) );
    }

    protected boolean requiresParenthesesForLeftOperand( FormatToken leftOperand ) {
        return precedence > leftOperand.getPrecedence()
                || precedence == leftOperand.getPrecedence() && associativity == Associativity.RIGHT && leftOperand.getAssociativity() == Associativity.RIGHT;
    }

    protected boolean requiresParenthesesForRightOperand( FormatToken rightOperand ) {
        return precedence > rightOperand.getPrecedence()
                || precedence == rightOperand.getPrecedence() && associativity == Associativity.LEFT && rightOperand.getAssociativity() == Associativity.LEFT;
    }


    protected void formatFunction( Deque<FormatToken> stack, FormatContext context ) {
        if ( operandsCount != 1 ) {
            throw new AssertionError( operandsCount );
        }
        FormatToken top = stack.pop();
        StringBuilder buffer = new StringBuilder();

        buffer.append( infixSymbol ).append( '(' );
        if ( context.isSpaceWithinParentheses() ) {
            buffer.append( ' ' );
        }
        buffer.append( top.getValue() );
        if ( context.isSpaceWithinParentheses() ) {
            buffer.append( ' ' );
        }
        buffer.append( ')' );

        stack.push( new FormatToken( buffer.toString(), associativity, precedence ) );
    }


    protected void parseUnaryOperator( Deque<ExpressionComponent> stack, ExpressionBuilder output, ParseContext context ) {
        Type previousType = context.getPreviousType();
        if ( previousType == Type.LEFT_PARENTHESIS || previousType == Type.OPERATOR ) {
            shift( stack, output );
            stack.push( this );
        } else {
            throw new IllegalStateException( type + " follows " + previousType );
        }
    }

    protected void parseBinaryOperator( Deque<ExpressionComponent> stack, ExpressionBuilder output, ParseContext context ) {
        Type previousType = context.getPreviousType();
        if ( previousType == Type.RIGHT_PARENTHESIS || previousType == Type.COMPLEX || previousType == Type.VARIABLE ) {
            shift( stack, output );
            stack.push( this );
        } else {
            throw new IllegalStateException( type + " follows " + previousType );
        }
    }

    protected void shift( Deque<ExpressionComponent> stack, ExpressionBuilder output ) {
        ExpressionComponent component = stack.peek();
        while ( component != null && component.getType() == Type.OPERATOR ) {
            boolean shift = associativity == Associativity.LEFT
                    ? precedence <= component.getPrecedence()
                    : precedence < component.getPrecedence();
            if ( shift ) {
                output.append( stack.pop() );
                component = stack.peek();
            } else {
                break;
            }
        }
    }


    protected void parseFunction( Deque<ExpressionComponent> stack, ExpressionBuilder output, ParseContext context ) {
        Type previousType = context.getPreviousType();
        if ( previousType == Type.LEFT_PARENTHESIS || previousType == Type.OPERATOR ) {
            stack.push( this );
        } else {
            throw new IllegalStateException( type + " follows " + previousType );
        }
    }


    @Override
    public String toString() {
        return postfixSymbol;
    }

}
