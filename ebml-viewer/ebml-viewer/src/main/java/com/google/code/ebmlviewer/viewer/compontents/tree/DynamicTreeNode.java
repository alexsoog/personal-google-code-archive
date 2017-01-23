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

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.AttributedString;
import java.text.CharacterIterator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.swing.Icon;
import javax.swing.event.TreeExpansionEvent;
import javax.swing.tree.ExpandVetoException;

import com.google.code.ebmlviewer.viewer.util.EventListenerList;

/** Represents a tree node. */
public class DynamicTreeNode implements Iterable<DynamicTreeNode> {

    protected final EventListenerList listenerList = new EventListenerList();

    private String text;

    private AttributedString attributedText;

    private Icon icon;

    private DynamicTreeNode parent;

    private List<DynamicTreeNode> children;

    private boolean connected;

    private boolean editable;


    /** Creates a new {@code DynamicTreeNode}. */
    public DynamicTreeNode() {
    }

    /**
     * Creates a new {@code DynamicTreeNode}.
     *
     * @param text the text displayed by the node
     * @param icon the icon displayed by the node
     */
    public DynamicTreeNode( String text, Icon icon ) {
        this.text = text;
        this.icon = icon;
    }


    /**
     * Returns the text that this node displays.
     *
     * @return the node text
     */
    public String getText() {
        return text;
    }

    /**
     * Changes the text that this node displays.
     *
     * @param text the new node text
     */
    public void setText( String text ) {
        setText( text, null );
    }


    /**
     * Returns the text that this node displays.
     *
     * @return the node text with attributes
     */
    public AttributedString getAttributedText() {
        return attributedText;
    }

    /**
     * Changes the text that this node displays.
     *
     * @param attributedText the new node text with attributes
     */
    public void setAttributedText( AttributedString attributedText ) {
        CharacterIterator iterator = attributedText.getIterator();
        StringBuilder plaintText = new StringBuilder( iterator.getEndIndex() - iterator.getBeginIndex() );
        for ( char c = iterator.first(); c != CharacterIterator.DONE; c = iterator.next() ) {
            plaintText.append( c );
        }
        setText( plaintText.toString(), attributedText );
    }


    private void setText( String text, AttributedString attributedText ) {
        String oldText = this.text;
        this.text = text;
        firePropertyChange( "text", oldText, text );
        AttributedString oldAttributedText = this.attributedText;
        this.attributedText = attributedText;
        firePropertyChange( "attributedText", oldAttributedText, attributedText );
        fireNodeChanged();
    }


    /**
     * Returns the icon that this node displays.
     *
     * @return the node icon
     */
    public Icon getIcon() {
        return icon;
    }

    /**
     * Changes the icon that this node displays.
     *
     * @param icon the new node icon
     */
    public void setIcon( Icon icon ) {
        Icon oldIcon = this.icon;
        this.icon = icon;
        firePropertyChange( "icon", oldIcon, icon );
        fireNodeChanged();
    }


    /**
     * Returns the parent node of this node.
     *
     * @return the parent node or {@code null} if this node has no parent
     */
    public DynamicTreeNode getParent() {
        return parent;
    }

    /**
     * Sets the parent node of this node.
     *
     * @param parent the new parent node
     */
    public void setParent( DynamicTreeNode parent ) {
        Object oldParent = this.parent;
        this.parent = parent;
        firePropertyChange( "parent", oldParent, parent );
    }


    /**
     * Returns the root node of this node.
     *
     * @return the root node
     */
    public DynamicTreeNode getRoot() {
        return parent == null ? this : parent.getRoot();
    }

    /**
     * Returns the path from the root node to this node.
     *
     * @return the path to this node
     */
    public DynamicTreeNode[] getPath() {
        List<DynamicTreeNode> path = new LinkedList<DynamicTreeNode>();
        for ( DynamicTreeNode temp = this; temp != null; temp = temp.getParent() ) {
            path.add( 0, temp );
        }
        return path.toArray( new DynamicTreeNode[ path.size() ] );
    }


    /**
     * Checks whether this node is an ancestor of the specified node.
     *
     * @param node the node to test as a descendant of this node
     *
     * @return {@code true} if this node is an ancestor of the specified node
     *
     * @throws IllegalArgumentException if {@code node} is {@code null}
     */
    public final boolean isAncestorOf( DynamicTreeNode node ) {
        if ( node == null ) {
            throw new IllegalArgumentException( "node is null" );
        }
        for ( DynamicTreeNode temp = node; temp != null; temp = temp.parent ) {
            if ( this == temp ) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks whether this node is a descendant of the specified node.
     *
     * @param node the node to test as an ancestor of this node
     *
     * @return {@code true} if this node is a descendant of the specified node
     *
     * @throws IllegalArgumentException if {@code node} is {@code null}
     */
    public final boolean isDescendantOf( DynamicTreeNode node ) {
        if ( node == null ) {
            throw new IllegalArgumentException( "node is null" );
        }
        return node.isAncestorOf( this );
    }


    private List<DynamicTreeNode> children( boolean modificationPossible ) {
        if ( children == null ) {
            if ( modificationPossible ) {
                children = new ArrayList<DynamicTreeNode>();
            } else {
                return Collections.emptyList();
            }
        }
        return children;
    }


    /**
     * Returns the number of child nodes.
     *
     * @return the number of child nodes
     */
    public int getChildCount() {
        return children( false ).size();
    }

    /**
     * Returns the child node with the specified index.
     *
     * @param index the index of the required child node
     *
     * @return the child node
     *
     * @throws IndexOutOfBoundsException if {@code index} is out of range (<code>index < 0 || index >=
     * getChildCount()</code>)
     */
    public DynamicTreeNode getChild( int index ) {
        return children( false ).get( index );
    }

    /**
     * Returns the index of the specified child node.
     *
     * @param node the child node
     *
     * @return the index of the child node or {@code -1} if the {@code node} is not a child of this node
     *
     * @throws IllegalArgumentException if {@code node} is {@code null}
     */
    public int getChildIndex( DynamicTreeNode node ) {
        if ( node == null ) {
            throw new IllegalArgumentException( "node is null" );
        }
        List<DynamicTreeNode> children = children( false );
        int index = children.size() - 1;
        while ( index >= 0 && children.get( index ) != node ) {
            index--;
        }
        return index;
    }

    /**
     * Appends the specified node to the end of the children list of this node.
     * <p/>
     * If the node already has a parent node, it is removed from its current parent node.
     *
     * @param node the child node to be appended
     *
     * @throws IllegalArgumentException if {@code node} is {@code null} or is an ancestor of this node
     */
    public void appendChild( DynamicTreeNode node ) {
        insertChild( node, children( false ).size() );
    }

    /**
     * Inserts the specified node at the specified position in the children list of this node.
     * <p/>
     * If the node already has a parent node, it is removed from its current parent node.
     *
     * @param node the child node to be inserted
     * @param index the index at which the specified node is to be inserted
     *
     * @throws IllegalArgumentException if {@code node} is {@code null} or is an ancestor of this node
     * @throws IndexOutOfBoundsException if {@code index} is out of range (<code>index < 0 || index >
     * getChildCount()</code>)
     */
    public void insertChild( DynamicTreeNode node, int index ) {
        if ( node == null ) {
            throw new IllegalArgumentException( "node is null" );
        }
        if ( isDescendantOf( node ) ) {
            throw new IllegalArgumentException( "node is ancestor of this node" );
        }
        if ( index < 0 || index > getChildCount() ) {
            throw new IndexOutOfBoundsException( String.format( "index %s is out of [%s, %s] range", index, 0, getChildCount() ) );
        }
        if ( node.getParent() == this ) {
            int currentIndex = getChildIndex( node );
            if ( currentIndex < 0 ) {
                throw new AssertionError();
            }
            if ( currentIndex == index ) {
                return;
            }
            if ( currentIndex < index ) {
                index--;
            }
            removeChild( currentIndex );
        } else {
            node.removeFromParent();
        }
        children( true ).add( index, node );
        node.setParent( this );
        fireNodeInserted( index, node );
        if ( connected ) {
            node.connect();
        }
    }

    /**
     * Appends the specified node to the end of the children list of this node, but does not fire any events.
     *
     * @param node the child node to be appended
     *
     * @throws IllegalArgumentException if {@code node} is {@code null}, or already has a parent, or is an ancestor of
     * this node
     */
    protected void appendChildSilently( DynamicTreeNode node ) {
        if ( node == null ) {
            throw new IllegalArgumentException( "node is null" );
        }
        if ( node.getParent() != null ) {
            throw new IllegalArgumentException( "node already has parent" );
        }
        if ( isDescendantOf( node ) ) {
            throw new IllegalArgumentException( "node is ancestor of this node" );
        }
        if ( connected ) {
            node.connect();
        }
        children( true ).add( node );
        node.setParent( this );
    }

    /**
     * Inserts the specified node at the specified position in the children list of this node, but does not fire any
     * events.
     *
     * @param node the child node to be inserted
     * @param index the index at which the specified node is to be inserted
     *
     * @throws IllegalArgumentException if {@code node} is {@code null}, or already has a parent, or is an ancestor of
     * this node
     * @throws IndexOutOfBoundsException if {@code index} is out of range (<code>index < 0 || index >
     * getChildCount()</code>)
     */
    protected void insertChildSilently( DynamicTreeNode node, int index ) {
        if ( node == null ) {
            throw new IllegalArgumentException( "node is null" );
        }
        if ( node.getParent() != null ) {
            throw new IllegalArgumentException( "node already has parent" );
        }
        if ( isDescendantOf( node ) ) {
            throw new IllegalArgumentException( "node is ancestor of this node" );
        }
        if ( index < 0 || index > getChildCount() ) {
            throw new IndexOutOfBoundsException( String.format( "index %s is out of [%s, %s] range", index, 0, getChildCount() ) );
        }
        children( true ).add( index, node );
        node.setParent( this );
    }


    /**
     * Removes the child node with the specified index.
     *
     * @param index the index of the child node to be removed
     *
     * @return the removed child node
     *
     * @throws IndexOutOfBoundsException if {@code index} is out of range (<code>index < 0 || index >=
     * getChildCount()</code>)
     */
    public DynamicTreeNode removeChild( int index ) {
        DynamicTreeNode node = children( false ).remove( index );
        node.setParent( null );
        fireNodeRemoved( index, node );
        if ( connected ) {
            node.disconnect();
        }
        return node;
    }

    /**
     * Removes the specified child node.
     *
     * @param node the child node to be removed
     *
     * @throws IllegalArgumentException if {@code node} is {@code null} or not a child of this node
     */
    public void removeChild( DynamicTreeNode node ) {
        if ( node == null ) {
            throw new IllegalArgumentException( "node is null" );
        }
        int index = getChildIndex( node );
        if ( index < 0 ) {
            throw new IllegalArgumentException( "node is not a child" );
        }
        removeChild( index );
    }


    /** Removes this node from its parent. */
    public void removeFromParent() {
        if ( parent != null ) {
            parent.removeChild( this );
        }
    }


    @Override
    public Iterator<DynamicTreeNode> iterator() {
        return children( false ).iterator();
    }


    /**
     * Returns whether this node is a leaf.
     *
     * @return {@code true} if this node is a leaf node; {@code false} otherwise
     */
    public boolean isLeaf() {
        return children( false ).isEmpty();
    }


    /**
     * Checks whether this node is connected to the model.
     *
     * @return {@code true} if this node is connected to the model, {@code false} otherwise
     */
    protected boolean isConnected() {
        return connected;
    }

    /** Connects the node and its children to the model. */
    void connect() {
        if ( connected ) {
            throw new IllegalStateException( "node is already connected to the model" );
        }
        connected = true;
        notifyConnected();
        for ( DynamicTreeNode child : children( false ) ) {
            child.connect();
        }
    }

    /** Called to notify the node that it is now connected to the model. */
    protected void notifyConnected() {}

    /** Disconnects the node and its children from the model. */
    void disconnect() {
        if ( !connected ) {
            throw new IllegalStateException( "node is already disconnected from the model" );
        }
        connected = false;
        notifyDisconnected();
        for ( DynamicTreeNode child : children( false ) ) {
            child.disconnect();
        }
    }

    /** Called to notify the node that it is now disconnected from the model. */
    protected void notifyDisconnected() {}


    /**
     * Returns whether this node is editable.
     *
     * @return {@code true} if this node is editable
     */
    public boolean isEditable() {
        return editable;
    }

    /**
     * Sets whether this node is editable.
     *
     * @param editable {@code true} if this node is editable
     */
    public void setEditable( boolean editable ) {
        boolean oldEditable = this.editable;
        this.editable = editable;
        firePropertyChange( "editable", oldEditable, editable );
    }


    public String getEditableValue() {
        return getText();
    }

    public void setEditableValue( String value ) {
        setText( value );
    }


    public void willExpand( TreeExpansionEvent event ) throws ExpandVetoException {
    }

    public void willCollapse( TreeExpansionEvent event ) throws ExpandVetoException {
    }


    /**
     * Adds a {@code PropertyChangeListener} to the listeners list.
     *
     * @param listener the {@code PropertyChangeListener} to be added
     *
     * @throws IllegalArgumentException if {@code listener} is {@code null}
     */
    public void addPropertyChangeListener( PropertyChangeListener listener ) {
        listenerList.addListener( PropertyChangeListener.class, listener );
    }

    /**
     * Removes a {@code PropertyChangeListener} from the listeners list.
     *
     * @param listener the {@code PropertyChangeListener} to be removed
     *
     * @throws IllegalArgumentException if {@code listener} is {@code null} or was not previously added as a {@code
     * PropertyChangeListener}
     */
    public void removePropertyChangeListener( PropertyChangeListener listener ) {
        listenerList.removeListener( PropertyChangeListener.class, listener );
    }

    /**
     * Reports a property update to all registered {@code PropertyChangeListener} listeners. No event is fired if old
     * and new values are equal.
     *
     * @param propertyName the name of the property that was changed
     * @param oldValue the old value of the property
     * @param newValue the new value of the property
     */
    protected void firePropertyChange( String propertyName, Object oldValue, Object newValue ) {
        if ( oldValue == null ? newValue == null : oldValue.equals( newValue ) ) {
            return;
        }
        PropertyChangeEvent event = null;
        Object[] listeners = listenerList.getListeners();
        for ( int i = listeners.length - 2; i >= 0; i -= 2 ) {
            if ( listeners[ i ] == PropertyChangeListener.class ) {
                if ( event == null ) {
                    event = new PropertyChangeEvent( this, propertyName, oldValue, newValue );
                }
                ( ( PropertyChangeListener ) listeners[ i + 1 ] ).propertyChange( event );
            }
        }
    }


    /**
     * Adds a {@code DynamicTreeNodeListener} to the listeners list.
     *
     * @param listener the {@code DynamicTreeNodeListener} to be added
     *
     * @throws IllegalArgumentException if {@code listener} is {@code null}
     */
    public final void addDynamicTreeNodeListener( DynamicTreeNodeListener listener ) {
        listenerList.addListener( DynamicTreeNodeListener.class, listener );
    }

    /**
     * Removes a {@code DynamicTreeNodeListener} from the listeners list.
     *
     * @param listener the {@code DynamicTreeNodeListener} to be removed
     *
     * @throws IllegalArgumentException if {@code listener} is {@code null} or was not previously added as a {@code
     * DynamicTreeNodeListener}
     */
    public final void removeDynamicTreeNodeListener( DynamicTreeNodeListener listener ) {
        listenerList.removeListener( DynamicTreeNodeListener.class, listener );
    }

    protected void fireNodeInserted( int index, DynamicTreeNode node ) {
        fireNodeInserted( this, index, node, null );
    }

    protected void fireNodeInserted( DynamicTreeNode source, int index, DynamicTreeNode node, DynamicTreeNodeEvent event ) {
        Object[] listeners = listenerList.getListeners();
        for ( int i = listeners.length - 2; i >= 0; i -= 2 ) {
            if ( listeners[ i ] == DynamicTreeNodeListener.class ) {
                if ( event == null ) {
                    event = new DynamicTreeNodeEvent( source, index, node );
                }
                ( ( DynamicTreeNodeListener ) listeners[ i + 1 ] ).nodeInserted( event );
            }
        }
        if ( parent != null ) {
            parent.fireNodeInserted( source, index, node, event );
        }
    }

    protected void fireNodeRemoved( int index, DynamicTreeNode node ) {
        fireNodeRemoved( this, index, node, null );
    }

    protected void fireNodeRemoved( DynamicTreeNode source, int index, DynamicTreeNode node, DynamicTreeNodeEvent event ) {
        Object[] listeners = listenerList.getListeners();
        for ( int i = listeners.length - 2; i >= 0; i -= 2 ) {
            if ( listeners[ i ] == DynamicTreeNodeListener.class ) {
                if ( event == null ) {
                    event = new DynamicTreeNodeEvent( source, index, node );
                }
                ( ( DynamicTreeNodeListener ) listeners[ i + 1 ] ).nodeRemoved( event );
            }
        }
        if ( parent != null ) {
            parent.fireNodeRemoved( source, index, node, event );
        }
    }

    protected final void fireNodeChanged() {
        fireNodeChanged( this, null );
    }

    protected final void fireNodeChanged( DynamicTreeNode source, DynamicTreeNodeEvent event ) {
        Object[] listeners = listenerList.getListeners();
        for ( int i = listeners.length - 2; i >= 0; i -= 2 ) {
            if ( listeners[ i ] == DynamicTreeNodeListener.class ) {
                if ( event == null ) {
                    event = new DynamicTreeNodeEvent( source );
                }
                ( ( DynamicTreeNodeListener ) listeners[ i + 1 ] ).nodeChanged( event );
            }
        }
        if ( parent != null ) {
            parent.fireNodeChanged( source, event );
        }
    }

}
