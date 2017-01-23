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

package com.google.code.ebmlviewer.viewer.compontents;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Rectangle;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.prefs.Preferences;

import javax.swing.JFrame;
import javax.swing.JPopupMenu;

public final class Components {

    public static JFrame getFrameForComponent( Component component ) {
        return getParentForComponent( component, JFrame.class );
    }

    public static <T extends Component> T getParentForComponent( Component component, Class<T> parentClass ) {
        if ( component == null ) {
            throw new IllegalArgumentException( "component is null" );
        }
        if ( parentClass == null ) {
            throw new IllegalArgumentException( "parentClass is null" );
        }
        while ( component != null ) {
            if ( parentClass.isInstance( component ) ) {
                return parentClass.cast( component );
            }
            if ( component instanceof JPopupMenu ) {
                component = ( ( JPopupMenu ) component ).getInvoker();
            } else {
                component = component.getParent();
            }
        }
        return null;
    }


    public static void configurePersistentBounds( Frame frame, final Preferences preferences, final String prefix ) {
        Rectangle screenBounds = frame.getGraphicsConfiguration().getBounds();

        Dimension minimumSize = frame.getMinimumSize();
        int width = Math.max( Math.min( preferences.getInt( prefix + "@frame.width", -1 ), screenBounds.width ), minimumSize.width );
        int height = Math.max( Math.min( preferences.getInt( prefix + "@frame.height", -1 ), screenBounds.height ), minimumSize.height );
        if ( width < 0 || height < 0 ) {
            frame.pack();
            width = frame.getWidth();
            height = frame.getHeight();
        } else {
            frame.setSize( width, height );
        }

        int x = Math.min( preferences.getInt( prefix + "@frame.x", -1 ), screenBounds.width - width );
        int y = Math.min( preferences.getInt( prefix + "@frame.y", -1 ), screenBounds.height - height );
        if ( x < 0 || y < 0 ) {
            frame.setLocationByPlatform( true );
        } else {
            frame.setLocation( x, y );
        }

        int state = preferences.getInt( prefix + "@frame.state", 0 ) & Frame.MAXIMIZED_BOTH;
        frame.setExtendedState( state );

        frame.addWindowListener( new WindowAdapter() {
            @Override
            public void windowClosed( WindowEvent event ) {
                Frame frame = ( Frame ) event.getWindow();
                if ( ( frame.getExtendedState() & Frame.MAXIMIZED_BOTH ) == 0 ) {
                    preferences.putInt( prefix + "@frame.state", 0 );
                    Rectangle bounds = frame.getBounds();
                    preferences.putInt( prefix + "@frame.width", bounds.width );
                    preferences.putInt( prefix + "@frame.height", bounds.height );
                    preferences.putInt( prefix + "@frame.x", bounds.x );
                    preferences.putInt( prefix + "@frame.y", bounds.y );
                } else {
                    preferences.putInt( prefix + "@frame.state", Frame.MAXIMIZED_BOTH );
                }
            }
        } );
    }


    private Components() {
    }

}
