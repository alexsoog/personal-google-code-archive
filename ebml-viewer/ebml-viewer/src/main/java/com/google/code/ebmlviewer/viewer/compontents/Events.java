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
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.util.EventObject;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import static com.google.code.ebmlviewer.viewer.compontents.Components.getParentForComponent;

public final class Events {

    public static JFrame getFrameForEvent( EventObject event ) {
        return getParentForEvent( event, JFrame.class );
    }

    public static <T extends Component> T getParentForEvent( EventObject event, Class<T> parentClass ) {
        if ( event == null ) {
            throw new IllegalArgumentException( "event is null" );
        }
        if ( event.getSource() instanceof Component ) {
            return getParentForComponent( ( Component ) event.getSource(), parentClass );
        } else {
            return null;
        }
    }


    public static void dispatchToParent( MouseEvent event ) {
        Component parent = event.getComponent().getParent();
        while ( parent != null && parent.getMouseListeners().length == 0 ) {
            parent = parent.getParent();
        }
        if ( parent != null ) {
            parent.dispatchEvent( cloneEvent( event, parent ) );
        }
    }

    private static MouseEvent cloneEvent( MouseEvent event, Component target ) {
        Point clonePoint = SwingUtilities.convertPoint( event.getComponent(), event.getPoint(), target );
        return new MouseEvent( target,
                event.getID(), event.getWhen(), event.getModifiers(),
                clonePoint.x, clonePoint.y, event.getXOnScreen(), event.getYOnScreen(),
                event.getClickCount(), event.isPopupTrigger(), event.getButton() );
    }


    private Events() {
    }

}
