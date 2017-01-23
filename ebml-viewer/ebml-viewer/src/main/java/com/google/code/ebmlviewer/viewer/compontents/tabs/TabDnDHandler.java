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
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.DragGestureEvent;
import java.awt.dnd.DragGestureListener;
import java.awt.dnd.DragSource;
import java.awt.dnd.DragSourceDragEvent;
import java.awt.dnd.DragSourceDropEvent;
import java.awt.dnd.DragSourceEvent;
import java.awt.dnd.DragSourceListener;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DropTargetListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.swing.JTabbedPane;
import javax.swing.SwingUtilities;

final class TabDnDHandler implements DragGestureListener, DragSourceListener, DropTargetListener {

    static final String GUARD = TabDnDHandler.class.getName() + "#adjusting";


    private final JTabbedPane tabbedPane;

    private final TabDnDGlassPane glassPane;

    private boolean showDragImage;

    private boolean showDropLocation;


    TabDnDHandler( JTabbedPane tabbedPane ) {
        if ( tabbedPane == null ) {
            throw new IllegalArgumentException( "tabbedPane is null" );
        }
        this.tabbedPane = tabbedPane;
        glassPane = new TabDnDGlassPane();
    }


    private void moveTab( int sourceIndex, int targetIndex, boolean afterTarget ) {
        if ( sourceIndex == targetIndex || ( afterTarget ? sourceIndex == targetIndex + 1 : sourceIndex == targetIndex - 1 ) ) {
            return;
        }
        tabbedPane.putClientProperty( GUARD, true );
        TabComponent tabComponent = ( TabComponent ) tabbedPane.getTabComponentAt( sourceIndex );
        Tab tab = ( Tab ) tabbedPane.getComponentAt( sourceIndex );
        boolean selected = sourceIndex == tabbedPane.getSelectedIndex();
        tabbedPane.removeTabAt( sourceIndex );
        if ( sourceIndex < targetIndex ) {
            targetIndex--;
        }
        if ( afterTarget ) {
            targetIndex++;
        }
        tabbedPane.insertTab( tab.getTitle(), tab.getIcon(), tab, tab.getToolTipText(), targetIndex );
        tabbedPane.setTabComponentAt( targetIndex, tabComponent );
        if ( selected ) {
            tabbedPane.setSelectedIndex( targetIndex );
        }
        tabbedPane.putClientProperty( GUARD, null );
    }


    private void updateGlassPane() {
        boolean visible = showDragImage || showDropLocation;
        if ( visible && glassPane.getParent() == null ) {
            tabbedPane.getRootPane().setGlassPane( glassPane );
        }
        glassPane.setVisible( visible );
        if ( visible ) {
            glassPane.repaint();
        }
    }


    private void showDragImage( Point location ) {
        glassPane.showDragImage( location );
        showDragImage = true;
        updateGlassPane();
    }

    private void hideDragImage() {
        glassPane.hideDragImage();
        showDragImage = false;
        updateGlassPane();
    }

    private void showDropLocation( Point location ) {
        glassPane.showDropLocation( location );
        showDropLocation = true;
        updateGlassPane();
    }

    private void hideDropLocation() {
        glassPane.hideDropLocation();
        showDropLocation = false;
        updateGlassPane();
    }


    private Point convertPoint( Point point, Component source, Component destination ) {
        if ( source == null ) {
            Point p = new Point( point );
            SwingUtilities.convertPointFromScreen( p, destination );
            return p;
        } else {
            return SwingUtilities.convertPoint( source, point, destination );
        }
    }

    private TabDropLocation getDropLocation( Point location ) {
        int index = -1;
        int indexDistance = Integer.MAX_VALUE;
        for ( int i = 0; i < tabbedPane.getTabCount(); i++ ) {
            Rectangle bounds = tabbedPane.getBoundsAt( i );
            if ( bounds.contains( location ) ) {
                int distance = bounds.x + bounds.width / 2 - location.x;
                return new TabDropLocation( i, distance < 0 );
            } else if ( location.y >= bounds.y && location.y <= bounds.y + bounds.height ) {
                int distance = bounds.x + bounds.width / 2 - location.x;
                if ( Math.abs( distance ) < Math.abs( indexDistance ) ) {
                    index = i;
                    indexDistance = distance;
                }
            }
        }
        return index == -1 ? null : new TabDropLocation( index, indexDistance < 0 );
    }

    private Image createDragImage( int index ) {
        Rectangle bounds = tabbedPane.getBoundsAt( index );
        BufferedImage image = new BufferedImage( bounds.width, bounds.height, BufferedImage.TYPE_INT_ARGB );
        Graphics2D g = image.createGraphics();
        g.setClip( 1, 1, bounds.width - 2, bounds.height - 2 );
        g.translate( -bounds.x, -bounds.y );
        tabbedPane.paint( g );
        g.dispose();
        return image;
    }


    @Override
    public void dragGestureRecognized( DragGestureEvent event ) {
        if ( tabbedPane.getTabCount() > 1 ) {
            // Point dragOrigin = event.getDragOrigin();
            // int index = tabbedPane.indexAtLocation( dragOrigin.x, dragOrigin.y );
            int index = tabbedPane.getSelectedIndex();
            if ( index != -1 ) {
                Image dragImage = createDragImage( index );
                if ( DragSource.isDragImageSupported() ) {
                    glassPane.setDragImage( null );
                } else {
                    glassPane.setDragImage( dragImage );
                }
                event.startDrag( DragSource.DefaultMoveDrop, dragImage, new Point( 0, 0 ), new TabTransferable( index ), this );
            }
        }
    }


    @Override
    public void dragEnter( DragSourceDragEvent event ) {
        event.getDragSourceContext().setCursor( DragSource.DefaultMoveDrop );
        showDragImage( convertPoint( event.getLocation(), null, glassPane ) );
    }

    @Override
    public void dragExit( DragSourceEvent event ) {
        event.getDragSourceContext().setCursor( DragSource.DefaultMoveNoDrop );
        hideDragImage();
    }

    @Override
    public void dragOver( DragSourceDragEvent event ) {
        if ( getDropLocation( convertPoint( event.getLocation(), null, tabbedPane ) ) == null ) {
            event.getDragSourceContext().setCursor( DragSource.DefaultMoveNoDrop );
        } else {
            event.getDragSourceContext().setCursor( DragSource.DefaultMoveDrop );
        }
        showDragImage( convertPoint( event.getLocation(), null, glassPane ) );
    }

    @Override
    public void dragDropEnd( DragSourceDropEvent event ) {
        hideDragImage();
    }

    @Override
    public void dropActionChanged( DragSourceDragEvent event ) {
    }


    @Override
    public void dragEnter( DropTargetDragEvent event ) {
        if ( event.isDataFlavorSupported( TabTransferable.FLAVOR ) ) {
            event.acceptDrag( event.getDropAction() );
        } else {
            event.rejectDrag();
        }
    }

    @Override
    public void dragExit( DropTargetEvent event ) {
        hideDropLocation();
    }


    @Override
    public void dragOver( DropTargetDragEvent event ) {
        if ( event.isDataFlavorSupported( TabTransferable.FLAVOR ) ) {
            Transferable transferable = event.getTransferable();
            int dragIndex;
            try {
                dragIndex = ( Integer ) transferable.getTransferData( TabTransferable.FLAVOR );
            } catch ( UnsupportedFlavorException e ) {
                throw new AssertionError( e );
            } catch ( IOException e ) {
                throw new AssertionError( e );
            }
            Point location = event.getLocation();
            TabDropLocation dropLocation = getDropLocation( location );
            if ( dropLocation == null || dragIndex == dropLocation.index || ( dropLocation.after ? dragIndex == dropLocation.index + 1 : dragIndex == dropLocation.index - 1 ) ) {
                hideDropLocation();
            } else {
                Rectangle tabBounds = tabbedPane.getBoundsAt( dropLocation.index );
                Point p = new Point( tabBounds.getLocation() );
                if ( dropLocation.after ) {
                    p.x += tabBounds.width;
                }
                showDropLocation( convertPoint( p, tabbedPane, glassPane ) );
            }
        } else {
            event.rejectDrag();
        }
    }

    @Override
    public void drop( DropTargetDropEvent event ) {
        Transferable transferable = event.getTransferable();
        if ( transferable == null || !transferable.isDataFlavorSupported( TabTransferable.FLAVOR ) ) {
            event.dropComplete( false );
        } else {
            Point location = event.getLocation();
            TabDropLocation dropLocation = getDropLocation( location );
            if ( dropLocation == null ) {
                event.dropComplete( false );
            } else {
                try {
                    int dragIndex = ( Integer ) transferable.getTransferData( TabTransferable.FLAVOR );
                    moveTab( dragIndex, dropLocation.index, dropLocation.after );
                } catch ( UnsupportedFlavorException e ) {
                    throw new AssertionError( e );
                } catch ( IOException e ) {
                    throw new AssertionError( e );
                }
                event.dropComplete( true );
            }
        }
        hideDropLocation();
    }


    @Override
    public void dropActionChanged( DropTargetDragEvent e ) {
    }

}
