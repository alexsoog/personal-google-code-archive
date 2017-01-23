/*-
 * Copyright (c) 2008-2012, Oleg Estekhin
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

package com.google.code.ebmlviewer.elements;

/** Defines the EBML element types. */
public enum ElementType {

    /** An element that contains a signed integer. */
    SIGNED_INTEGER( "integer" ),

    /** An element that contains an unsigned integer element. */
    UNSIGNED_INTEGER( "uinteger" ),

    /** An element that contains a floating-point number. */
    FLOATING_POINT( "float" ),

    /** An element that contains a character string in the ASCII encoding. */
    ASCII_STRING( "string" ),

    /** An element that contains a character string in the UTF-8 encoding. */
    UNICODE_STRING( "utf-8" ),

    /** An element that contains a date. */
    DATE( "date" ),

    /** An element that contains binary data. */
    BINARY( "binary" ),

    /** A master element that contains other elements. */
    MASTER( "master" );


    /**
     * Decodes a string into an {@code ElementType}.
     *
     * @param s the string to decode
     *
     * @return an {@code ElementType} object corresponding to the input string
     *
     * @throws IllegalArgumentException if the input string does not contain a parsable {@code ElementType}
     */
    public static ElementType fromString( String s ) {
        for ( ElementType type : values() ) {
            if ( type.name.equals( s ) ) {
                return type;
            }
        }
        throw new IllegalArgumentException( String.format( "unable to decode \"%s\" as an element type", s ) );
    }


    private final String name;


    ElementType( String name ) {
        if ( name == null ) {
            throw new IllegalArgumentException( "name is null" );
        }
        this.name = name;
    }


    @Override
    public String toString() {
        return name;
    }

}
