/*
 * Copyright 2015-2016 Alessio Vinerbi. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,
 * MA 02110-1301  USA
 */

package com.anchorage.docks.containers.common;

import com.anchorage.docks.containers.interfaces.DockContainableComponent;
import com.anchorage.docks.containers.subcontainers.DockSplitterContainer;
import com.anchorage.docks.containers.subcontainers.DockTabberContainer;
import com.anchorage.docks.node.DockNode;
import com.anchorage.docks.node.DockNode.DockPosition;
import com.anchorage.docks.node.ui.TabDockUIPanel;

import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.control.Tab;

/**
 *
 * @author Alessio
 */
public class DockCommons {

	public static boolean isABorderPosition(DockNode.DockPosition position) {
		return position != DockNode.DockPosition.CENTER;
	}

	public static DockSplitterContainer createSplitter(Node existNode, Node newNode, DockNode.DockPosition position,
			double percentage) {
		DockSplitterContainer splitter = createEmptySplitter();

		if (position == DockNode.DockPosition.BOTTOM || position == DockNode.DockPosition.TOP) {
			splitter.setOrientation(Orientation.VERTICAL);
		}

		DockContainableComponent existContainableComponent = toDockContainableComponent(existNode, position);
		DockContainableComponent newContainableComponent = toDockContainableComponent(newNode, position);

		existContainableComponent.setParentContainer(splitter);
		newContainableComponent.setParentContainer(splitter);

		if (position == DockNode.DockPosition.BOTTOM || position == DockNode.DockPosition.RIGHT) {
			splitter.getItems().addAll((Node)existContainableComponent, (Node)newContainableComponent);
		} else {
			splitter.getItems().addAll((Node)newContainableComponent, (Node)existContainableComponent);
		}

		splitter.getStyleClass().add("docknode-split-pane");
		splitter.setDividerPositions(percentage);
		return splitter;
	}

	public static DockSplitterContainer createEmptySplitter() {
		return new DockSplitterContainer();
	}

	private static DockContainableComponent toDockContainableComponent(Node existNode, DockPosition position) {
		if (existNode instanceof DockNode) {
			if (((DockNode) existNode).isComeWithATab()) {
				return createTabber((DockNode) existNode, position);
			} else {
				return (DockContainableComponent) existNode;
			}
		} else {
			return (DockContainableComponent) existNode;
		}
	}

	public static DockTabberContainer createTabber(DockNode newNode, DockNode.DockPosition position) {

		DockNode newDockNode = (DockNode) newNode;

		DockTabberContainer tabber = new DockTabberContainer();
		Tab newTabPanel = ((TabDockUIPanel) newDockNode.getContent()).getOwnTab();

		if(newTabPanel.getOnCloseRequest() == null){
			newTabPanel.setOnCloseRequest(event -> {
	
				if (newDockNode.getCloseRequestHandler() == null || newDockNode.getCloseRequestHandler().canClose()) {
					newDockNode.undock();
					event.consume();
				}
	
			});
		}

		newTabPanel.closableProperty().bind(newDockNode.closeableProperty());

		newTabPanel.setContent(newDockNode);

		DockContainableComponent newContainableComponent = (DockContainableComponent) newNode;

		newContainableComponent.setParentContainer(tabber);

		tabber.getTabs().addAll(newTabPanel);

		tabber.getStyleClass().add("docknode-tab-pane");

		newDockNode.ensureVisibility();

		return tabber;
	}

	public static DockTabberContainer createTabber(Node existNode, Node newNode, DockNode.DockPosition position) {

		if (existNode instanceof DockNode && newNode instanceof DockNode) {
			DockNode existDockNode = (DockNode) existNode;
			DockNode newDockNode = (DockNode) newNode;

			DockTabberContainer tabber = new DockTabberContainer();
			Tab existTabPanel = existDockNode.isComeWithATab() ? existDockNode.getContent().getOwnTab() : new Tab(existDockNode.getContent().titleProperty().get());
			Tab newTabPanel = newDockNode.isComeWithATab() ? newDockNode.getContent().getOwnTab() : new Tab(newDockNode.getContent().titleProperty().get());

			if(existTabPanel.getOnCloseRequest() == null){
				existTabPanel.setOnCloseRequest(event -> {
	
					if (existDockNode.getCloseRequestHandler() == null
							|| existDockNode.getCloseRequestHandler().canClose()) {
						existDockNode.undock();
						event.consume();
					}
	
				});
			}

			if(newTabPanel.getOnCloseRequest() == null){
				newTabPanel.setOnCloseRequest(event -> {
	
					if (newDockNode.getCloseRequestHandler() == null || newDockNode.getCloseRequestHandler().canClose()) {
						newDockNode.undock();
						event.consume();
					}
	
				});
			}

			existTabPanel.closableProperty().bind(existDockNode.closeableProperty());
			newTabPanel.closableProperty().bind(newDockNode.closeableProperty());

			existTabPanel.setContent(existDockNode);
			newTabPanel.setContent(newDockNode);

			DockContainableComponent existContainableComponent = (DockContainableComponent) existNode;
			DockContainableComponent newContainableComponent = (DockContainableComponent) newNode;

			existContainableComponent.setParentContainer(tabber);
			newContainableComponent.setParentContainer(tabber);

			tabber.getTabs().addAll(existTabPanel, newTabPanel);

			tabber.getStyleClass().add("docknode-tab-pane");

			newDockNode.ensureVisibility();

			return tabber;
		}
		return null;
	}
}
