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

import java.util.EventListener;

/**
 * A class that supports a list of {@code EventListener}s.
 * <p/>
 * This class provides a strict version of the {@link javax.swing.event.EventListenerList} class. Passing {@code null}
 * values to any method of this class or removing a listener that was not previously added will cause an exception.
 * <p/>
 * Usage example:
 * <pre>
 * private final EventListenerList listenerList = new EventListenerList();
 *
 * public void addFooListener( FooListener listener ) {
 *     listenerList.addListener( FooListener.class, listener );
 * }
 *
 * public void removeFooListener( FooListener listener ) {
 *     listenerList.removeListener( FooListener.class, listener );
 * }
 *
 * protected void fireFooHappened( ... ) {
 *     FooEvent event = null;
 *     Object[] listeners = listenerList.getListeners();
 *     for ( int i = listeners.length - 2; i >= 0; i -= 2 ) {
 *         if ( listeners[ i ] == FooListener.class ) {
 *             if ( event == null ) {
 *                 event = new FooEvent( ... );
 *             }
 *         ( ( FooListener ) listeners[ i + 1 ] ).fooHappened( event );
 *     }
 * }
 * </pre>
 *
 * @see javax.swing.event.EventListenerList
 */
public final class EventListenerList {

    private static final Object[] EMPTY_LISTENERS = new Object[ 0 ];


    private volatile Object[] listeners = EMPTY_LISTENERS;


    /**
     * Returns the event listeners as an array of {type, listener} pairs.
     * <p/>
     * WARNING: The returned array is the actual data structure in which the listeners data is stored internally and it
     * must not be modified in any way.
     *
     * @return the event listeners list
     */
    public Object[] getListeners() {
        return listeners;
    }

    private void setListeners( Object[] listeners ) {
        this.listeners = listeners;
    }


    /**
     * Registers an object as a listener of the specified type.
     *
     * @param type the type of the listener to be added
     * @param listener the listener to be added
     *
     * @throws IllegalArgumentException if {@code type} or {@code listener} is {@code null}
     * @throws IllegalArgumentException if {@code listener} is not an instance of the {@code type} class
     */
    public <T extends EventListener> void addListener( Class<T> type, T listener ) {
        if ( type == null ) {
            throw new IllegalArgumentException( "type is null" );
        }
        if ( listener == null ) {
            throw new IllegalArgumentException( "listener is null" );
        }
        if ( !type.isInstance( listener ) ) {
            throw new IllegalArgumentException( "listener is not an instance of the type class" );
        }
        synchronized ( this ) {
            Object[] listeners = getListeners();
            if ( listeners == EMPTY_LISTENERS ) {
                listeners = new Object[] { type, listener };
            } else {
                int length = listeners.length;
                Object[] temp = new Object[ length + 2 ];
                System.arraycopy( listeners, 0, temp, 0, length );
                temp[ length ] = type;
                temp[ length + 1 ] = listener;
                listeners = temp;
            }
            setListeners( listeners );
        }
    }

    /**
     * Unregisters an object as a listener of the specified type.
     *
     * @param type the type of the listener to be removed
     * @param listener the listener to be removed
     *
     * @throws IllegalArgumentException if {@code type} or {@code listener} is {@code null}
     * @throws IllegalArgumentException if {@code listener} is not an instance of the {@code type} class
     * @throws IllegalArgumentException if {@code listener} was not previously registered as a listener of {@code type}
     * class
     */
    public <T extends EventListener> void removeListener( Class<T> type, T listener ) {
        if ( type == null ) {
            throw new IllegalArgumentException( "type is null" );
        }
        if ( listener == null ) {
            throw new IllegalArgumentException( "listener is null" );
        }
        if ( !type.isInstance( listener ) ) {
            throw new IllegalArgumentException( "listener is not an instance of the type class" );
        }
        synchronized ( this ) {
            Object[] listeners = getListeners();
            int index = listeners.length - 2;
            while ( index >= 0 && ( listeners[ index ] != type || listeners[ index + 1 ] != listener ) ) {
                index -= 2;
            }
            if ( index < 0 ) {
                throw new IllegalArgumentException( "type/listener pair is not registered with this list" );
            } else if ( listeners.length == 2 ) {
                listeners = EMPTY_LISTENERS;
            } else {
                int length = listeners.length;
                Object[] temp = new Object[ length - 2 ];
                if ( index > 0 ) {
                    System.arraycopy( listeners, 0, temp, 0, index );
                }
                if ( index < length - 2 ) {
                    System.arraycopy( listeners, index + 2, temp, index, length - index - 2 );
                }
                listeners = temp;
            }
            setListeners( listeners );
        }
    }


    @Override
    public String toString() {
        Object[] listeners = getListeners();
        StringBuilder buffer = new StringBuilder();
        buffer.append( '[' );
        for ( int index = 0; index < listeners.length; index += 2 ) {
            if ( index > 0 ) {
                buffer.append( ',' );
            }
            buffer.append( '{' ).append( listeners[ index ] ).append( ',' ).append( listeners[ index + 1 ] ).append( '}' );
        }
        buffer.append( ']' );
        return buffer.toString();
    }

}
