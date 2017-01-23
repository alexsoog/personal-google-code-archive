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

package oe.assertions.predicates.strings;

import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import oe.assertions.Message;
import oe.assertions.Predicate;

/**
 * Evaluates to {@code true} if the input value matches the specified regular expression.
 *
 * @see Pattern
 */
public final class Matches implements Predicate<String> {

    private final Pattern pattern;


    /**
     * Creates a new predicate.
     *
     * @param regex the regular expression
     *
     * @throws IllegalArgumentException if {@code regex} is {@code null}
     * @throws PatternSyntaxException if the regular expression's syntax is invalid
     */
    public Matches( String regex ) {
        if ( regex == null ) {
            throw new IllegalArgumentException( "regex is null" );
        }
        pattern = Pattern.compile( regex );
    }


    @Override
    public boolean check( String input ) {
        return input != null && pattern.matcher( input ).matches();
    }

    @Override
    public void describe( String input, Message message ) {
        message.append( "matches" ).append( ' ' ).formatValue( pattern.pattern() );
    }

}
