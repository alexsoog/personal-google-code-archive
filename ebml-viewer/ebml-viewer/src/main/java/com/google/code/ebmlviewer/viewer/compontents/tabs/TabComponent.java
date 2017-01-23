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

package com.google.code.ebmlviewer.viewer.compontents.tabs;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.Icon;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import static com.google.code.ebmlviewer.viewer.compontents.Events.dispatchToParent;

final class TabComponent extends JComponent {

    private final TitleHandler titleHandler;

    private final JLabel label;

    private final Tab tab;


    TabComponent( Tab tab ) {
        if ( tab == null ) {
            throw new IllegalArgumentException( "tab is null" );
        }
        this.tab = tab;

        setOpaque( false );
        setLayout( new BorderLayout() );

        label = new JLabel( tab.getTitle(), tab.getIcon(), SwingConstants.CENTER );
        label.setToolTipText( tab.getToolTipText() );
        TabButton button = new TabButton();

        add( label, BorderLayout.CENTER );
        add( button, BorderLayout.LINE_END );

        titleHandler = new TitleHandler();
        tab.addPropertyChangeListener( titleHandler );
        button.addActionListener( new ButtonHandler() );

        MouseHandler mouseHandler = new MouseHandler();
        label.addMouseListener( mouseHandler );
        label.addMouseMotionListener( mouseHandler );
    }


    public void dispose() {
        tab.removePropertyChangeListener( titleHandler );
    }


    private class TitleHandler implements PropertyChangeListener {

        @Override
        public void propertyChange( PropertyChangeEvent event ) {
            String propertyName = event.getPropertyName();
            if ( "title".equals( propertyName ) ) {
                label.setText( ( String ) event.getNewValue() );
            } else if ( "icon".equals( propertyName ) ) {
                label.setIcon( ( Icon ) event.getNewValue() );
            } else if ( JComponent.TOOL_TIP_TEXT_KEY.equals( propertyName ) ) {
                label.setToolTipText( ( String ) event.getNewValue() );
            }
        }

    }

    private class ButtonHandler implements ActionListener {

        @Override
        public void actionPerformed( ActionEvent event ) {
            tab.notifyClosing();
        }

    }

    private class MouseHandler implements MouseListener, MouseMotionListener {

        @Override
        public void mouseClicked( MouseEvent event ) {
            if ( SwingUtilities.isMiddleMouseButton( event ) ) {
                tab.notifyClosing();
            } else {
                dispatchToParent( event );
            }
        }

        @Override
        public void mousePressed( MouseEvent event ) {
            if ( SwingUtilities.isMiddleMouseButton( event ) ) {
                // consume to prevent accidental tab selection
            } else {
                dispatchToParent( event );
            }
        }

        @Override
        public void mouseReleased( MouseEvent event ) {
            if ( SwingUtilities.isMiddleMouseButton( event ) ) {
                // consume to prevent accidental tab selection
            } else {
                dispatchToParent( event );
            }
        }

        @Override
        public void mouseEntered( MouseEvent event ) {
            dispatchToParent( event );
        }

        @Override
        public void mouseExited( MouseEvent event ) {
            dispatchToParent( event );
        }

        @Override
        public void mouseDragged( MouseEvent event ) {
            dispatchToParent( event );
        }

        @Override
        public void mouseMoved( MouseEvent event ) {
            dispatchToParent( event );
        }

    }

}
