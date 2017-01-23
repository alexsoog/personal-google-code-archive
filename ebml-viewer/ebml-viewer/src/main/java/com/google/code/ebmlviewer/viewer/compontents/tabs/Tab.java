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

import java.awt.Component;

import javax.swing.Icon;
import javax.swing.JComponent;
import javax.swing.JTabbedPane;

import com.google.code.ebmlviewer.viewer.compontents.ContentPaneLayout;

/** A component that represents a single tab. */
public class Tab extends JComponent {

    private String title;

    private Icon icon;

    private Component contentPane;

    private DefaultCloseOperation defaultCloseOperation = DefaultCloseOperation.DO_NOTHING_ON_CLOSE;


    /** Creates a new empty {@code Tab}. */
    protected Tab() {
        setLayout( new ContentPaneLayout() );
    }

    /**
     * Creates a new {@code Tab} with the specified contents.
     *
     * @param title the title displayed in the tab header
     * @param icon the icon displayed in the tab header
     * @param contentPane the content displayed when the tab is selected
     */
    public Tab( String title, Icon icon, Component contentPane ) {
        this();
        setTitle( title );
        setIcon( icon );
        setContentPane( contentPane );
    }


    /**
     * Returns the title displayed in the tab header.
     *
     * @return the tab title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the title displayed in the tab header.
     *
     * @param title the new title, can be {@code null}
     */
    public void setTitle( String title ) {
        String oldTitle = this.title;
        this.title = title;
        firePropertyChange( "title", oldTitle, title );
    }


    /**
     * Returns the icon displayed in the tab header.
     *
     * @return the tab icon
     */
    public Icon getIcon() {
        return icon;
    }

    /**
     * Sets the icon displayed in the tab header.
     *
     * @param icon the new icon, can be {@code null}
     */
    public void setIcon( Icon icon ) {
        Icon oldIcon = this.icon;
        this.icon = icon;
        firePropertyChange( "icon", oldIcon, icon );
    }


    /**
     * Returns the content pane.
     *
     * @return the component displayed when the tab is selected
     */
    public Component getContentPane() {
        return contentPane;
    }

    /**
     * Sets the content pane.
     *
     * @param contentPane the component displayed when the tab is selected
     */
    public void setContentPane( Component contentPane ) {
        Component oldContentPane = this.contentPane;
        if ( this.contentPane != null ) {
            remove( this.contentPane );
        }
        this.contentPane = contentPane;
        if ( this.contentPane != null ) {
            add( this.contentPane );
        }
        firePropertyChange( "contentPane", oldContentPane, contentPane );
    }


    /**
     * Returns the operation that occurs when the user attempts to close this tab.
     *
     * @return the default close operation
     */
    public DefaultCloseOperation getDefaultCloseOperation() {
        return defaultCloseOperation;
    }

    /**
     * Sets the operation that will happen by default when the user attempts to close this tab.
     *
     * @param defaultCloseOperation the operation which should be performed when the user closes the tab
     */
    public void setDefaultCloseOperation( DefaultCloseOperation defaultCloseOperation ) {
        if ( defaultCloseOperation == null ) {
            throw new IllegalArgumentException( "defaultCloseOperation is null" );
        }
        DefaultCloseOperation oldDefaultCloseOperation = this.defaultCloseOperation;
        this.defaultCloseOperation = defaultCloseOperation;
        firePropertyChange( "defaultCloseOperation", oldDefaultCloseOperation, defaultCloseOperation );
    }


    /** Closes this tab and removes it from the tab pane. */
    public void dispose() {
        JTabbedPane parent = ( JTabbedPane ) getParent();
        if ( parent != null ) {
            if ( parent.getParent() instanceof TabPane ) {
                TabPane tabPane = ( TabPane ) parent.getParent();
                tabPane.removeTab( this );
            } else {
                parent.remove( this );
            }
        }
    }


    /**
     * Adds a {@code TabListener} to the listeners list.
     *
     * @param listener the {@code TabListener} to be added
     *
     * @throws IllegalArgumentException if {@code listener} is {@code null}
     */
    public void addTabListener( TabListener listener ) {
        listenerList.add( TabListener.class, listener );
    }

    /**
     * Removes a {@code TabListener} from the listeners list.
     *
     * @param listener the {@code TabListener} to be removed
     *
     * @throws IllegalArgumentException if {@code listener} is {@code null}
     * @throws IllegalArgumentException if {@code listener} was not previously added as a {@code TabListener}
     */
    public void removeTabListener( TabListener listener ) {
        listenerList.remove( TabListener.class, listener );
    }

    protected void fireTabClosing() {
        TabEvent event = null;
        Object[] listeners = listenerList.getListenerList();
        for ( int i = listeners.length - 2; i >= 0; i -= 2 ) {
            if ( listeners[ i ] == TabListener.class ) {
                if ( event == null ) {
                    event = new TabEvent( this );
                }
                ( ( TabListener ) listeners[ i + 1 ] ).tabClosing( event );
            }
        }
    }

    protected void fireTabClosed() {
        TabEvent event = null;
        Object[] listeners = listenerList.getListenerList();
        for ( int i = listeners.length - 2; i >= 0; i -= 2 ) {
            if ( listeners[ i ] == TabListener.class ) {
                if ( event == null ) {
                    event = new TabEvent( this );
                }
                ( ( TabListener ) listeners[ i + 1 ] ).tabClosed( event );
            }
        }
    }

    void notifyClosing() {
        fireTabClosing();
        if ( defaultCloseOperation == DefaultCloseOperation.DISPOSE_ON_CLOSE ) {
            dispose();
        }
    }

    void notifyClosed() {
        fireTabClosed();
    }


    /** Defines default tab close operations. */
    public enum DefaultCloseOperation {

        /**
         * Don't do anything; require the program to handle the operation in the {@code tabClosing} method of a
         * registered {@code TabListener} object.
         */
        DO_NOTHING_ON_CLOSE,
        /**
         * Automatically close and dispose the tab after invoking any registered {@code TabListener} objects.
         *
         * @see Tab#dispose()
         */
        DISPOSE_ON_CLOSE

    }

}
