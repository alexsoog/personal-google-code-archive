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

package com.google.code.ebmlviewer.viewer.compontents.tree;

import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;

import com.google.code.ebmlviewer.viewer.util.EventListenerList;

/**
 * The {@code TreeModel} implementation that works with {@code DynamicTreeNode}s.
 *
 * @see DynamicTreeNode
 */
public class DynamicTreeModel implements TreeModel {

    private final EventListenerList listenerList = new EventListenerList();

    private final DynamicTreeNode root;


    /**
     * Creates a new {@code DynamicTreeModel}.
     *
     * @param root the root node
     *
     * @throws IllegalArgumentException if {@code root} is {@code null}
     */
    public DynamicTreeModel( DynamicTreeNode root ) {
        if ( root == null ) {
            throw new IllegalArgumentException( "root is null" );
        }
        this.root = root;
        root.connect();
        root.addDynamicTreeNodeListener( new DynamicTreeNodeListener() {
            @Override
            public void nodeInserted( DynamicTreeNodeEvent event ) {
                fireNodeInserted( event.getSource(), event.getIndex(), event.getNode() );
            }

            @Override
            public void nodeRemoved( DynamicTreeNodeEvent event ) {
                fireNodeRemoved( event.getSource(), event.getIndex(), event.getNode() );
            }

            @Override
            public void nodeChanged( DynamicTreeNodeEvent event ) {
                fireNodeChanged( event.getSource() );
            }
        } );
    }


    public void dispose() {
        root.disconnect();
    }


    @Override
    public DynamicTreeNode getRoot() {
        return root;
    }


    @Override
    public int getChildCount( Object parent ) {
        if ( parent == null ) {
            throw new IllegalArgumentException( "parent is null" );
        }
        return ( ( DynamicTreeNode ) parent ).getChildCount();
    }

    @Override
    public Object getChild( Object parent, int index ) {
        if ( parent == null ) {
            throw new IllegalArgumentException( "parent is null" );
        }
        return ( ( DynamicTreeNode ) parent ).getChild( index );
    }

    @Override
    public int getIndexOfChild( Object parent, Object child ) {
        if ( parent == null ) {
            throw new IllegalArgumentException( "parent is null" );
        }
        if ( child == null ) {
            throw new IllegalArgumentException( "child is null" );
        }
        return ( ( DynamicTreeNode ) parent ).getChildIndex( ( DynamicTreeNode ) child );
    }


    @Override
    public boolean isLeaf( Object node ) {
        return ( ( DynamicTreeNode ) node ).isLeaf();
    }


    /**
     * Returns whether the last node in the specified path is editable.
     *
     * @param path the path to the node
     *
     * @return {@code true} if the node indicated by the {@code path} is editable
     */
    public boolean isPathEditable( TreePath path ) {
        DynamicTreeNode node = ( DynamicTreeNode ) path.getLastPathComponent();
        return node.isEditable();
    }

    @Override
    public void valueForPathChanged( TreePath path, Object newValue ) {
        DynamicTreeNode node = ( DynamicTreeNode ) path.getLastPathComponent();
        node.setEditableValue( ( String ) newValue );
    }


    @Override
    public void addTreeModelListener( TreeModelListener listener ) {
        listenerList.addListener( TreeModelListener.class, listener );
    }

    @Override
    public void removeTreeModelListener( TreeModelListener listener ) {
        listenerList.removeListener( TreeModelListener.class, listener );
    }

    private void fireNodeInserted( DynamicTreeNode parent, int index, DynamicTreeNode node ) {
        TreeModelEvent event = null;
        Object[] listeners = listenerList.getListeners();
        for ( int i = listeners.length - 2; i >= 0; i -= 2 ) {
            if ( listeners[ i ] == TreeModelListener.class ) {
                if ( event == null ) {
                    event = new TreeModelEvent( this, parent.getPath(), new int[] { index }, new Object[] { node } );
                }
                ( ( TreeModelListener ) listeners[ i + 1 ] ).treeNodesInserted( event );
            }
        }
    }

    private void fireNodeRemoved( DynamicTreeNode parent, int index, DynamicTreeNode node ) {
        TreeModelEvent event = null;
        Object[] listeners = listenerList.getListeners();
        for ( int i = listeners.length - 2; i >= 0; i -= 2 ) {
            if ( listeners[ i ] == TreeModelListener.class ) {
                if ( event == null ) {
                    event = new TreeModelEvent( this, parent.getPath(), new int[] { index }, new Object[] { node } );
                }
                ( ( TreeModelListener ) listeners[ i + 1 ] ).treeNodesRemoved( event );
            }
        }
    }

    private void fireNodeChanged( DynamicTreeNode node ) {
        TreeModelEvent event = null;
        Object[] listeners = listenerList.getListeners();
        for ( int i = listeners.length - 2; i >= 0; i -= 2 ) {
            if ( listeners[ i ] == TreeModelListener.class ) {
                if ( event == null ) {
                    if ( node == root ) {
                        event = new TreeModelEvent( this, node.getPath(), null, null );
                    } else {
                        DynamicTreeNode parent = node.getParent();
                        event = new TreeModelEvent( this, parent.getPath(), new int[] { parent.getChildIndex( node ) }, new Object[] { node } );
                    }
                }
                ( ( TreeModelListener ) listeners[ i + 1 ] ).treeNodesChanged( event );
            }
        }
    }

}
