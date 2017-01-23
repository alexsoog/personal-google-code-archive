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

import java.awt.dnd.DnDConstants;
import java.awt.dnd.DragSource;
import java.awt.dnd.DropTarget;
import java.awt.event.ActionEvent;
import java.awt.event.ContainerEvent;
import java.awt.event.ContainerListener;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JComponent;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.google.code.ebmlviewer.viewer.compontents.ContentPaneLayout;

/** A component that lets the user to switch between a group of components by clicking on a tab with a given title. */
public class TabPane extends JComponent {

    private final Action closeSelectedTabAction = new AbstractAction() {
        @Override
        public void actionPerformed( ActionEvent event ) {
            closeSelectedTab( event );
        }
    };

    private final JTabbedPane tabbedPane;


    /** Creates a new {@code TabPane}. */
    public TabPane() {
        setLayout( new ContentPaneLayout() );

        tabbedPane = new JTabbedPane() {
            @Override
            public void updateUI() {
                setUI( new TabPaneTabbedPaneUI() );
            }
        };
        add( tabbedPane );

        TabDnDHandler dragHandler = new TabDnDHandler( tabbedPane );
        DragSource dragSource = new DragSource();
        dragSource.createDefaultDragGestureRecognizer( tabbedPane, DnDConstants.ACTION_MOVE, dragHandler );
        DropTarget dropTarget = new DropTarget( tabbedPane, DnDConstants.ACTION_MOVE, dragHandler );

        TabbedPaneHandler tabbedPaneHandler = new TabbedPaneHandler();
        tabbedPane.addContainerListener( tabbedPaneHandler );
        tabbedPane.addChangeListener( tabbedPaneHandler );

        closeSelectedTabAction.setEnabled( false );
    }


    public Action getCloseSelectedTabAction() {
        return closeSelectedTabAction;
    }

    private void closeSelectedTab( ActionEvent event ) {
        getSelectedTab().notifyClosing();
    }


    /**
     * Returns the number of tabs in this tab pane.
     *
     * @return the number of tabs
     */
    public int getTabCount() {
        return tabbedPane.getTabCount();
    }

    /**
     * Returns the tab with the specified index.
     *
     * @param index the index of the tab being queried
     *
     * @return the tab at {@code index}
     *
     * @throws IndexOutOfBoundsException if {@code index} is out of range (index < 0 || index >= tab count)
     */
    public Tab getTab( int index ) {
        if ( index < 0 || index >= tabbedPane.getTabCount() ) {
            throw new IndexOutOfBoundsException( String.format( "index %s is out of [%s, %s) range", index, 0, tabbedPane.getTabCount() ) );
        }
        return ( Tab ) tabbedPane.getComponentAt( index );
    }

    /**
     * Adds a new tab.
     *
     * @param tab the new tab
     *
     * @throws IllegalArgumentException if {@code tab} is {@code null}
     */
    public void addTab( Tab tab ) {
        if ( tab == null ) {
            throw new IllegalArgumentException( "tab is null" );
        }
        insertTab( tab, tabbedPane.getTabCount() );
    }

    /**
     * Inserts a new tab at the specified index.
     *
     * @param tab the new tab
     * @param index the insertion index
     *
     * @throws IllegalArgumentException if {@code tab} is {@code null}
     * @throws IndexOutOfBoundsException if {@code index} is out of range (index < 0 || index > tab count)
     */
    public void insertTab( Tab tab, int index ) {
        if ( tab == null ) {
            throw new IllegalArgumentException( "tab is null" );
        }
        if ( index < 0 || index > tabbedPane.getTabCount() ) {
            throw new IndexOutOfBoundsException( String.format( "index %s is out of [%s, %s] range", index, 0, tabbedPane.getTabCount() ) );
        }
        tabbedPane.insertTab( tab.getTitle(), tab.getIcon(), tab, tab.getToolTipText(), index );
        tabbedPane.setTabComponentAt( index, new TabComponent( tab ) );
    }

    /**
     * Removes the tab with the specified index.
     *
     * @param index the index of the tab to remove
     *
     * @throws IndexOutOfBoundsException if {@code index} is out of range (index < 0 || index >= tab count)
     */
    public void removeTab( int index ) {
        if ( index < 0 || index >= tabbedPane.getTabCount() ) {
            throw new IndexOutOfBoundsException( String.format( "index %s is out of [%s, %s) range", index, 0, tabbedPane.getTabCount() ) );
        }
        ( ( TabComponent ) tabbedPane.getTabComponentAt( index ) ).dispose();
        Tab tab = ( Tab ) tabbedPane.getComponentAt( index );
        tabbedPane.removeTabAt( index );
        tab.notifyClosed();
    }

    /**
     * Removes the specified tab.
     *
     * @param tab the tab to be removed
     *
     * @throws IllegalArgumentException if {@code tab} is {@code null} or if this pane does not contain {@code tab}
     */
    public void removeTab( Tab tab ) {
        if ( tab == null ) {
            throw new IllegalArgumentException( "tab is null" );
        }
        int index = tabbedPane.indexOfComponent( tab );
        if ( index < 0 ) {
            throw new IllegalArgumentException( "tab does not belong to this pane" );
        }
        removeTab( index );
    }


    /**
     * Returns the selected tab.
     *
     * @return the selected tab or {@code null} if this pane has no tabs
     */
    public Tab getSelectedTab() {
        return ( Tab ) tabbedPane.getSelectedComponent();
    }

    /**
     * Changes the selected tab.
     *
     * @param tab the tab to be selected
     *
     * @throws IllegalArgumentException if {@code tab} is {@code null} or if this pane does not contain {@code tab}
     */
    public void setSelectedTab( Tab tab ) {
        if ( tab == null ) {
            throw new IllegalArgumentException( "tab is null" );
        }
        int index = tabbedPane.indexOfComponent( tab );
        if ( index < 0 ) {
            throw new IllegalArgumentException( "tab does not belong to this pane" );
        }
        tabbedPane.setSelectedComponent( tab );
    }

    /**
     * Changes the selected tab.
     *
     * @param index the index of the tab to be selected
     *
     * @throws IndexOutOfBoundsException if {@code index} is out of range (index < 0 || index >= tab count)
     */
    public void setSelectedIndex( int index ) {
        if ( index < 0 || index >= tabbedPane.getTabCount() ) {
            throw new IndexOutOfBoundsException( String.format( "index %s is out of [%s, %s) range", index, 0, tabbedPane.getTabCount() ) );
        }
        tabbedPane.setSelectedIndex( index );
    }


    /**
     * Adds a {@code TabPaneListener} to the listeners list.
     *
     * @param listener the {@code TabPaneListener} to be added
     *
     * @throws IllegalArgumentException if {@code listener} is {@code null}
     */
    public void addTabPaneListener( TabPaneListener listener ) {
        if ( listener == null ) {
            throw new IllegalArgumentException( "listener is null" );
        }
        listenerList.add( TabPaneListener.class, listener );
    }

    /**
     * Removes a {@code TabPaneListener} from the listeners list.
     *
     * @param listener the {@code TabPaneListener} to be removed
     *
     * @throws IllegalArgumentException if {@code listener} is {@code null}
     */
    public void removeTabPaneListener( TabPaneListener listener ) {
        if ( listener == null ) {
            throw new IllegalArgumentException( "listener is null" );
        }
        listenerList.remove( TabPaneListener.class, listener );
    }

    private void fireTabAdded( Tab tab ) {
        TabPaneEvent event = null;
        Object[] listeners = listenerList.getListenerList();
        for ( int i = listeners.length - 2; i >= 0; i -= 2 ) {
            if ( listeners[ i ] == TabPaneListener.class ) {
                if ( event == null ) {
                    event = new TabPaneEvent( this, tab );
                }
                ( ( TabPaneListener ) listeners[ i + 1 ] ).tabAdded( event );
            }
        }
    }

    private void fireTabRemoved( Tab tab ) {
        TabPaneEvent event = null;
        Object[] listeners = listenerList.getListenerList();
        for ( int i = listeners.length - 2; i >= 0; i -= 2 ) {
            if ( listeners[ i ] == TabPaneListener.class ) {
                if ( event == null ) {
                    event = new TabPaneEvent( this, tab );
                }
                ( ( TabPaneListener ) listeners[ i + 1 ] ).tabRemoved( event );
            }
        }
    }

    private void fireTabSelected( Tab tab ) {
        TabPaneEvent event = null;
        Object[] listeners = listenerList.getListenerList();
        for ( int i = listeners.length - 2; i >= 0; i -= 2 ) {
            if ( listeners[ i ] == TabPaneListener.class ) {
                if ( event == null ) {
                    event = new TabPaneEvent( this, tab );
                }
                ( ( TabPaneListener ) listeners[ i + 1 ] ).tabSelected( event );
            }
        }
    }


    private final class TabbedPaneHandler implements ContainerListener, ChangeListener {

        @Override
        public void componentAdded( ContainerEvent event ) {
            if ( ( ( JComponent ) event.getSource() ).getClientProperty( TabDnDHandler.GUARD ) != null ) {
                return;
            }
            if ( event.getChild() instanceof Tab ) {
                fireTabAdded( ( Tab ) event.getChild() );
            }
        }

        @Override
        public void componentRemoved( ContainerEvent event ) {
            if ( ( ( JComponent ) event.getSource() ).getClientProperty( TabDnDHandler.GUARD ) != null ) {
                return;
            }
            if ( event.getChild() instanceof Tab ) {
                fireTabRemoved( ( Tab ) event.getChild() );
            }
        }

        @Override
        public void stateChanged( ChangeEvent event ) {
            if ( ( ( JComponent ) event.getSource() ).getClientProperty( TabDnDHandler.GUARD ) != null ) {
                return;
            }
            Tab selectedTab = getSelectedTab();
            fireTabSelected( selectedTab );
            if ( closeSelectedTabAction != null ) {
                closeSelectedTabAction.setEnabled( selectedTab != null );
            }
        }

    }

}
