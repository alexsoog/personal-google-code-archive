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

package oe.assertions;

import java.io.Closeable;
import java.io.Flushable;
import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Formatter;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/** A buffer that contains assertion details. */
public class Message implements Appendable, Flushable, Closeable {

    private final Message parent;

    private final int indentationLevel;

    private final StringBuilder buffer;

    private Formatter formatter;


    /** Creates a new message buffer. */
    public Message() {
        parent = null;
        indentationLevel = 0;
        buffer = new StringBuilder();
    }

    /**
     * Creates a new message buffer with the specified parent buffer and indentation level.
     *
     * @param parent the parent buffer
     * @param indentationLevel the indentation level
     *
     * @throws IllegalArgumentException if {@code parent} is {@code null}
     * @throws IllegalArgumentException if {@code indentationLevel} is negative
     */
    public Message( Message parent, int indentationLevel ) {
        if ( parent == null ) {
            throw new IllegalArgumentException( "parent is null" );
        }
        if ( indentationLevel < 0 ) {
            throw new IllegalArgumentException( "indentationLevel is negative" );
        }
        this.parent = parent;
        this.indentationLevel = indentationLevel;
        buffer = new StringBuilder();
    }


    /**
     * Creates a new child buffer with the same indentation level.
     *
     * @return a new child buffer
     */
    public Message open() {
        return new Message( this, indentationLevel );
    }

    /**
     * Creates a new child buffer with the modified indentation level.
     *
     * @param indentationChange the indentation change relative to this buffer
     *
     * @return a new child buffer
     *
     * @throws IllegalArgumentException if the resulting indentation level is negative
     */
    public Message open( int indentationChange ) {
        if ( indentationLevel + indentationChange < 0 ) {
            throw new IllegalArgumentException( "indentation level is negative" );
        }
        return new Message( this, indentationLevel + indentationChange );
    }


    @Override
    public void flush() {
        if ( formatter != null ) {
            formatter.flush();
        }
        if ( parent != null ) {
            parent.append( buffer );
            buffer.setLength( 0 );
        }
    }

    @Override
    public void close() {
        if ( formatter != null ) {
            formatter.close();
        }
    }


    @Override
    public Message append( CharSequence charSequence ) {
        buffer.append( charSequence );
        return this;
    }

    @Override
    public Message append( CharSequence charSequence, int start, int end ) {
        buffer.append( charSequence, start, end );
        return this;
    }

    @Override
    public Message append( char c ) {
        buffer.append( c );
        return this;
    }


    /**
     * Indents the current line using the current indentation level.
     *
     * @return this buffer
     */
    public Message indent() {
        return indent( 0 );
    }

    /**
     * Indents the current line using the modified indentation level.
     *
     * @param indentationChange the indentation change relative to the current indentation level
     *
     * @return this buffer
     *
     * @throws IllegalArgumentException if the resulting indentation level is negative
     */
    public Message indent( int indentationChange ) {
        if ( indentationLevel + indentationChange < 0 ) {
            throw new IllegalArgumentException( "indentation level is negative" );
        }
        for ( int i = indentationLevel + indentationChange; i > 0; i-- ) {
            append( "    " );
        }
        return this;
    }

    /**
     * Appends a system-dependent line separator to this buffer.
     *
     * @return this buffer
     */
    public Message eol() {
        return format( "%n" );
    }


    /**
     * Appends a formatted string to this buffer using the specified format string and arguments.
     *
     * @param format a format string
     * @param arguments a format arguments
     *
     * @return this buffer
     *
     * @see Formatter#format(String, Object...)
     */
    public Message format( String format, Object... arguments ) {
        if ( formatter == null ) {
            formatter = new Formatter( buffer, null );
        }
        formatter.format( null, format, arguments );
        return this;
    }


    /**
     * Appends a formatted value to this buffer.
     *
     * @param value a value to append
     *
     * @return this buffer
     */
    public Message formatValue( Object value ) {
        return formatValue( value, true );
    }

    private Message formatValue( Object value, boolean verbose ) {
        if ( value == null ) {
            return append( verbose ? "<null>" : "null" );

        } else if ( value instanceof Byte ) {
            return format( verbose ? "<%s> (0x%<02x)" : "%s", ( Byte ) value );
        } else if ( value instanceof Short ) {
            return format( verbose ? "<%s> (0x%<04x)" : "%s", ( Short ) value );
        } else if ( value instanceof Integer ) {
            return format( verbose ? "<%s> (0x%<08x)" : "%s", ( Integer ) value );
        } else if ( value instanceof Long ) {
            return format( verbose ? "<%sL> (0x%<016xL)" : "%sL", ( Long ) value );

        } else if ( value instanceof Float ) {
            return format( verbose ? "<%sf> (%<af)" : "%sf", ( Float ) value );
        } else if ( value instanceof Double ) {
            return format( verbose ? "<%s> (%<a)" : "%s", ( Double ) value );

        } else if ( value instanceof Character ) {
            Character c = ( Character ) value;
            return format( verbose ? "'%s' (u%04x)" : "'%s'", c, ( short ) c.charValue() );
        } else if ( value instanceof String ) {
            String s = ( String ) value;
            append( '"' );
            for ( int index = 0; index < s.length(); index++ ) {
                char c = s.charAt( index );
                if ( c == '"' ) {
                    append( "\\\"" );
                } else if ( c == '\t' ) {
                    append( "\\t" );
                } else if ( c == '\r' ) {
                    append( "\\r" );
                } else if ( c == '\n' ) {
                    append( "\\n" );
                } else {
                    append( c );
                }
            }
            append( '"' );
            return this;

        } else if ( value.getClass().isArray() ) {
            return formatArray( value );
        } else if ( value instanceof Collection || value instanceof Map ) {
            return format( "%s", value );

        } else {
            return format( verbose ? "<%s>" : "%s", value );
        }
    }

    private Message formatArray( Object array ) {
        return formatArray( array, new LinkedList<Object>() );
    }

    private Message formatArray( Object array, List<Object> path ) {
        path.add( 0, array );
        append( '[' );
        int length = Array.getLength( array );
        for ( int index = 0; index < length; index++ ) {
            if ( index > 0 ) {
                append( ',' ).append( ' ' );
            }
            Object element = Array.get( array, index );
            if ( element != null && element.getClass().isArray() ) {
                int nested = path.indexOf( element );
                if ( nested < 0 ) {
                    formatArray( element, path );
                } else {
                    append( '[' );
                    while ( nested > 0 ) {
                        append( "../" );
                        nested--;
                    }
                    append( ".." );
                    append( ']' );
                }
            } else {
                formatValue( element, false );
            }
        }
        append( ']' );
        path.remove( 0 );
        return this;
    }


    @Override
    public String toString() {
        return buffer.toString();
    }

}
