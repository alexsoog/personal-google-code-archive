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
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.KeyStroke;
import javax.swing.UIManager;
import javax.swing.plaf.UIResource;
import javax.swing.plaf.basic.BasicTabbedPaneUI;

final class TabPaneTabbedPaneUI extends BasicTabbedPaneUI {

    TabPaneTabbedPaneUI() {
    }


    @Override
    protected void installDefaults() {
        UIManager.put( "TabbedPane.tabsOpaque", Boolean.TRUE );
        super.installDefaults();
        tabInsets = new Insets( 3, 3, 2, 3 );
        tabAreaInsets = new Insets( 2, 1, 0, 1 );
    }

    @Override
    protected LayoutManager createLayoutManager() {
        return new TabPaneTabbedPaneLayout();
    }

    @Override
    protected void installKeyboardActions() {
        super.installKeyboardActions();

        ActionMap actionMap = tabPane.getActionMap();
        actionMap.put( "closeSelected", new CloseSelectedAction() );

        InputMap inputMap = tabPane.getInputMap( JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT );
        inputMap.put( KeyStroke.getKeyStroke( KeyEvent.VK_LEFT, InputEvent.ALT_DOWN_MASK ), "navigatePrevious" );
        inputMap.put( KeyStroke.getKeyStroke( KeyEvent.VK_RIGHT, InputEvent.ALT_DOWN_MASK ), "navigateNext" );
        inputMap.put( KeyStroke.getKeyStroke( KeyEvent.VK_W, InputEvent.CTRL_DOWN_MASK ), "closeSelected" );
        inputMap.put( KeyStroke.getKeyStroke( KeyEvent.VK_F4, InputEvent.CTRL_DOWN_MASK ), "closeSelected" );
    }

    @Override
    protected void uninstallKeyboardActions() {
        InputMap inputMap = tabPane.getInputMap( JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT );
        inputMap.remove( KeyStroke.getKeyStroke( KeyEvent.VK_LEFT, InputEvent.ALT_DOWN_MASK ) );
        inputMap.remove( KeyStroke.getKeyStroke( KeyEvent.VK_RIGHT, InputEvent.ALT_DOWN_MASK ) );
        inputMap.remove( KeyStroke.getKeyStroke( KeyEvent.VK_W, InputEvent.CTRL_DOWN_MASK ) );
        inputMap.remove( KeyStroke.getKeyStroke( KeyEvent.VK_F4, InputEvent.CTRL_DOWN_MASK ) );

        ActionMap actionMap = tabPane.getActionMap();
        actionMap.remove( "closeSelected" );

        super.uninstallKeyboardActions();
    }


    private class CloseSelectedAction extends AbstractAction implements UIResource {

        @Override
        public void actionPerformed( ActionEvent event ) {
            Component selectedComponent = tabPane.getSelectedComponent();
            if ( selectedComponent instanceof Tab ) {
                Tab tab = ( Tab ) selectedComponent;
                tab.notifyClosing();
            }
        }

    }

    private class TabPaneTabbedPaneLayout extends TabbedPaneLayout {

        @Override
        protected void calculateTabRects( int tabPlacement, int tabCount ) {
            // this is the fixed version of the super.calculateTabRects
            // the code for tabPlacement != TOP is removed
            // the tab runs code is fixed
            if ( tabPlacement != TOP ) {
                throw new AssertionError( tabPlacement );
            }
            maxTabHeight = calculateMaxTabHeight( tabPlacement );
            runCount = 0;
            selectedRun = -1;
            if ( tabCount == 0 ) {
                return;
            }
            FontMetrics metrics = getFontMetrics();
            Dimension size = tabPane.getSize();
            Insets insets = tabPane.getInsets();
            Insets tabAreaInsets = getTabAreaInsets( tabPlacement );
            int selectedIndex = tabPane.getSelectedIndex();
            int x = insets.left + tabAreaInsets.left;
            int y = insets.top + tabAreaInsets.top;
            int returnAt = size.width - ( insets.right + tabAreaInsets.right );
            int tabRunOverlay = getTabRunOverlay( tabPlacement );
            for ( int i = 0; i < tabCount; i++ ) {
                Rectangle rectangle = rects[ i ];
                if ( i == 0 ) {
                    tabRuns[ 0 ] = 0;
                    runCount = 1;
                    maxTabWidth = 0;
                    rectangle.x = x;
                } else {
                    rectangle.x = rects[ i - 1 ].x + rects[ i - 1 ].width;
                }
                rectangle.width = calculateTabWidth( tabPlacement, i, metrics );
                maxTabWidth = Math.max( maxTabWidth, rectangle.width );
                // the next condition is fixed with additional (i > 0) term
                if ( i > 0 && rectangle.x != 2 + insets.left && rectangle.x + rectangle.width > returnAt ) {
                    if ( runCount > tabRuns.length - 1 ) {
                        expandTabRunsArray();
                    }
                    tabRuns[ runCount ] = i;
                    runCount++;
                    rectangle.x = x;
                }
                rectangle.y = y;
                rectangle.height = maxTabHeight;
                if ( i == selectedIndex ) {
                    selectedRun = runCount - 1;
                }
            }
            if ( runCount > 1 ) {
                normalizeTabRuns( tabPlacement, tabCount, x, returnAt );
                selectedRun = getRunForTab( tabCount, selectedIndex );
                if ( shouldRotateTabRuns( tabPlacement ) ) {
                    rotateTabRuns( tabPlacement, selectedRun );
                }
            }
            for ( int i = runCount - 1; i >= 0; i-- ) {
                int start = tabRuns[ i ];
                int next = tabRuns[ i == ( runCount - 1 ) ? 0 : i + 1 ];
                int end = next == 0 ? tabCount - 1 : next - 1;
                for ( int j = start; j <= end; j++ ) {
                    Rectangle rectangle = rects[ j ];
                    rectangle.y = y;
                    rectangle.x += getTabRunIndent( tabPlacement, i );
                }
                if ( shouldPadTabRun( tabPlacement, i ) ) {
                    padTabRun( tabPlacement, start, end, returnAt );
                }
                y += maxTabHeight - tabRunOverlay;
            }
            padSelectedTab( tabPlacement, selectedIndex );
            if ( !tabPane.getComponentOrientation().isLeftToRight() ) {
                int rightMargin = size.width - ( insets.right + tabAreaInsets.right );
                for ( int i = 0; i < tabCount; i++ ) {
                    rects[ i ].x = rightMargin - rects[ i ].x - rects[ i ].width;
                }
            }
        }

        @Override
        public void layoutContainer( Container parent ) {
            super.layoutContainer( parent );
            fixedLayoutTabComponents();
        }

        private void fixedLayoutTabComponents() {
            // this is the fixed version of super.layoutTabComponents
            // the code for tabPlacement != TOP is removed
            // the tab component fills the tab bounds instead of being set to preferred size and centered
            int tabPlacement = tabPane.getTabPlacement();
            if ( tabPlacement != TOP ) {
                throw new AssertionError( tabPlacement );
            }
            Point delta = new Point( 0, 0 );
            for ( int i = 0; i < tabPane.getTabCount(); i++ ) {
                Component tabComponent = tabPane.getTabComponentAt( i );
                if ( tabComponent == null ) {
                    continue;
                }
                boolean isSelected = i == tabPane.getSelectedIndex();
                int sx = getTabLabelShiftX( tabPlacement, i, isSelected );
                int sy = getTabLabelShiftY( tabPlacement, i, isSelected );
                Rectangle tabBounds = getTabBounds( i, new Rectangle() );
                Insets insets = getTabInsets( tabPane.getTabPlacement(), i );
                int x = tabBounds.x + insets.left + delta.x + sx + 1;
                int y = tabBounds.y + insets.top + delta.y + sy + 1;
                int w = tabBounds.width - insets.left - insets.right - delta.x - sx - 2;
                int h = tabBounds.height - insets.top - insets.bottom - delta.y - sy - 2;
                tabComponent.setBounds( x, y, w, h );
            }
        }

    }

}
