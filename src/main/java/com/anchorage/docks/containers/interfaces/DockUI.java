package com.anchorage.docks.containers.interfaces;

import com.anchorage.docks.node.DockNode;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.StringProperty;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Tab;
import javafx.scene.image.Image;

public interface DockUI {

	default Tab getOwnTab(){
		return null;
	}

	void setDockNode(DockNode dockNode);

	Node getNodeContent();

	void setIcon(Image icon);

	void setOpacity(double d);

	StringProperty titleProperty();

	boolean isMenuButtonEnable();
	
	ObjectProperty<EventHandler<Event>> onCloseRequestProperty();
	
	EventHandler<Event> getOnCloseRequest();
	
	void setOnCloseRequest(EventHandler<Event> value);
}
