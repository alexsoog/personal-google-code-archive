/*-
 * Copyright (c) 2011-2012, Oleg Estekhin
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

package com.google.code.ebmlviewer.viewer.util;

import java.text.AttributedCharacterIterator;
import java.text.AttributedString;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/** Provides the fluent interface for building an {@code AttributedString}. */
public class AttributedStringBuilder {

    private StringBuilder buffer;

    private List<AttributedSpan> attributedSpans;


    public AttributedStringBuilder() {
        buffer = new StringBuilder();
        attributedSpans = new LinkedList<AttributedSpan>();
    }


    public AttributedStringBuilder append( char c ) {
        buffer.append( c );
        return this;
    }

    public AttributedStringBuilder append( char c, AttributedCharacterIterator.Attribute attribute, Object value ) {
        if ( attribute == null ) {
            throw new IllegalArgumentException( "attribute is null" );
        }
        if ( value == null ) {
            throw new IllegalArgumentException( "value is null" );
        }
        int beginIndex = buffer.length();
        buffer.append( c );
        int endIndex = buffer.length();
        attributedSpans.add( new AttributedSpan( Collections.singletonMap( attribute, value ), beginIndex, endIndex ) );
        return this;
    }

    public AttributedStringBuilder append( char c, Map<? extends AttributedCharacterIterator.Attribute, ?> attributes ) {
        if ( attributes == null ) {
            throw new IllegalArgumentException( "attributes is null" );
        }
        int beginIndex = buffer.length();
        buffer.append( c );
        int endIndex = buffer.length();
        attributedSpans.add( new AttributedSpan( attributes, beginIndex, endIndex ) );
        return this;
    }


    public AttributedStringBuilder append( String str ) {
        if ( str == null ) {
            throw new IllegalArgumentException( "str is null" );
        }
        buffer.append( str );
        return this;
    }

    public AttributedStringBuilder append( String str, AttributedCharacterIterator.Attribute attribute, Object value ) {
        if ( str == null ) {
            throw new IllegalArgumentException( "str is null" );
        }
        if ( attribute == null ) {
            throw new IllegalArgumentException( "attribute is null" );
        }
        if ( value == null ) {
            throw new IllegalArgumentException( "value is null" );
        }
        int beginIndex = buffer.length();
        buffer.append( str );
        int endIndex = buffer.length();
        attributedSpans.add( new AttributedSpan( Collections.singletonMap( attribute, value ), beginIndex, endIndex ) );
        return this;
    }

    public AttributedStringBuilder append( String str, Map<? extends AttributedCharacterIterator.Attribute, ?> attributes ) {
        if ( str == null ) {
            throw new IllegalArgumentException( "str is null" );
        }
        if ( attributes == null ) {
            throw new IllegalArgumentException( "attributes is null" );
        }
        int beginIndex = buffer.length();
        buffer.append( str );
        int endIndex = buffer.length();
        attributedSpans.add( new AttributedSpan( attributes, beginIndex, endIndex ) );
        return this;
    }


    public AttributedString build() {
        AttributedString attributedString = new AttributedString( buffer.toString() );
        for ( AttributedSpan attributedSpan : attributedSpans ) {
            if ( attributedSpan.beginIndex != attributedSpan.endIndex ) {
                attributedString.addAttributes( attributedSpan.attributes, attributedSpan.beginIndex, attributedSpan.endIndex );
            }
        }
        return attributedString;
    }


    private static final class AttributedSpan {

        private final Map<? extends AttributedCharacterIterator.Attribute, ?> attributes;

        private final int beginIndex;

        private final int endIndex;

        private AttributedSpan( Map<? extends AttributedCharacterIterator.Attribute, ?> attributes, int beginIndex, int endIndex ) {
            this.attributes = attributes;
            this.beginIndex = beginIndex;
            this.endIndex = endIndex;
        }

    }

}
