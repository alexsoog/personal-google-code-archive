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

import java.awt.Component;
import java.awt.KeyboardFocusManager;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Enumeration;

import javax.swing.JTree;
import javax.swing.event.TreeExpansionEvent;
import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;
import javax.swing.event.TreeWillExpandListener;
import javax.swing.tree.ExpandVetoException;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;

import static com.google.code.ebmlviewer.viewer.compontents.Components.getParentForComponent;

/** An extended {@code JTree} that works with {@code DynamicTreeModel} and {@code DynamicTreeNode}s. */
public class DynamicTree extends JTree {

    private TerminateEditOnFocusLostHandler terminateEditOnFocusLostHandler;


    public DynamicTree( DynamicTreeModel model ) {
        super( model );
        addTreeWillExpandListener( new TreeWillExpandListener() {
            @Override
            public void treeWillExpand( TreeExpansionEvent event ) throws ExpandVetoException {
                ( ( DynamicTreeNode ) event.getPath().getLastPathComponent() ).willExpand( event );
            }

            @Override
            public void treeWillCollapse( TreeExpansionEvent event ) throws ExpandVetoException {
                ( ( DynamicTreeNode ) event.getPath().getLastPathComponent() ).willCollapse( event );
            }
        } );
    }


    @Override
    public DynamicTreeModel getModel() {
        return ( DynamicTreeModel ) super.getModel();
    }

    @Override
    public void setModel( TreeModel newModel ) {
        super.setModel( ( DynamicTreeModel ) newModel ); // cast forces CCE
    }


    @Override
    public boolean isPathEditable( TreePath path ) {
        return ( ( DynamicTreeModel ) treeModel ).isPathEditable( path );
    }

    @Override
    public String convertValueToText( Object value, boolean selected, boolean expanded, boolean leaf, int row, boolean hasFocus ) {
        if ( value instanceof DynamicTreeNode ) {
            DynamicTreeNode node = ( DynamicTreeNode ) value;
            return node.getEditableValue();
        } else {
            return super.convertValueToText( value, selected, expanded, leaf, row, hasFocus );
        }
    }


    @Override
    protected TreeModelListener createTreeModelListener() {
        return new DynamicTreeModelHandler();
    }


    @Override
    public void startEditingAtPath( TreePath path ) {
        if ( terminateEditOnFocusLostHandler == null ) {
            terminateEditOnFocusLostHandler = new TerminateEditOnFocusLostHandler();
            KeyboardFocusManager.getCurrentKeyboardFocusManager().addPropertyChangeListener( "permanentFocusOwner", terminateEditOnFocusLostHandler );
        }
        super.startEditingAtPath( path );
    }

    @Override
    public void removeNotify() {
        if ( terminateEditOnFocusLostHandler != null ) {
            KeyboardFocusManager.getCurrentKeyboardFocusManager().removePropertyChangeListener( "permanentFocusOwner", terminateEditOnFocusLostHandler );
            terminateEditOnFocusLostHandler = null;
        }
        super.removeNotify();
    }


    protected class DynamicTreeModelHandler extends TreeModelHandler {

        private Object removedChild;

        private TreePath removedPath;

        private boolean removedPathSelected;

        private boolean removedPathExpanded;

        private Enumeration<TreePath> expandedDescendants;

        @Override
        public void treeNodesChanged( TreeModelEvent event ) {
            super.treeNodesChanged( event );
            removedChild = null;
        }

        @Override
        public void treeNodesInserted( TreeModelEvent event ) {
            super.treeNodesInserted( event );
            if ( removedChild == null ) {
                return;
            }
            Object[] children = event.getChildren();
            if ( children == null || children.length != 1 || children[ 0 ] != removedChild ) {
                removedChild = null;
                return;
            }
            // todo convert removedPathSelected and expandedDescendants into paths below the current position of the removedChild
            if ( removedPathSelected ) {
                setSelectionPath( removedPath );
            }
            if ( removedPathExpanded ) {
                expandPath( removedPath );
                while ( expandedDescendants.hasMoreElements() ) {
                    expandPath( expandedDescendants.nextElement() );
                }
            }
            removedChild = null;
        }

        @Override
        public void treeNodesRemoved( TreeModelEvent event ) {
            Object[] children = event.getChildren();
            if ( children == null || children.length != 1 ) {
                removedChild = null;
                return;
            }
            removedChild = children[ 0 ];
            removedPath = event.getTreePath().pathByAddingChild( removedChild );
            removedPathSelected = isPathSelected( removedPath );
            removedPathExpanded = isExpanded( removedPath );
            expandedDescendants = getExpandedDescendants( removedPath );
            super.treeNodesRemoved( event );
        }

        @Override
        public void treeStructureChanged( TreeModelEvent event ) {
            super.treeStructureChanged( event );
            removedChild = null;
        }

    }

    private class TerminateEditOnFocusLostHandler implements PropertyChangeListener {

        @Override
        public void propertyChange( PropertyChangeEvent event ) {
            if ( !isEditing() ) {
                return;
            }
            Component focusOwner = ( Component ) event.getNewValue();
            if ( focusOwner == null || DynamicTree.this == getParentForComponent( focusOwner, DynamicTree.class ) ) {
                return;
            }
            if ( getInvokesStopCellEditing() ) {
                stopEditing();
            }
            if ( isEditing() ) {
                cancelEditing();
            }
        }

    }

}
