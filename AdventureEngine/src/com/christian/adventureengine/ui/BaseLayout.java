package com.christian.adventureengine.ui;

import java.util.ArrayList;
import java.util.List;

import com.christian.adventureengine.data.Box;
import com.christian.adventureengine.input.IKeyListener;
import com.christian.adventureengine.input.IMouseClickListener;
import com.christian.adventureengine.ui.elements.Element;

/**
 * @author Christian
 * This is to be overridden to create custom layout types.
 */
public abstract class BaseLayout implements IMouseClickListener, IKeyListener {
	/**
	 * This stores all of the elements
	 * directly under the Layout.
	 */
	public List<Element> elements;
	
	/**
	 * This is the screen space region
	 * that this layout is responsible for.
	 */
	public Box Bounds;
	
	/**
	 * This constructor is to be called
	 * by static factory methods in implementations.
	 * @param bounds The screen space region that this layout
	 * is responsible for.
	 */
	protected BaseLayout(Box bounds) {
		Bounds = bounds;
		
		elements = new ArrayList<Element>();
	}
	
	/**
	 * This will add an element to this layout.
	 * Note: This will not automatically trigger
	 * a recalculation of the layout.
	 * @param element The element to add.
	 */
	public abstract void PushElement(Element element);
	
	
	/**
	 * Force the layout to recalculate itself.
	 * This should be used after adding, removing,
	 * or modifying elements within the layout. 
	 */
	public abstract void Recalculate();
	
	/**
	 * This will return an Element with the matching Id
	 * if it is contained within the Element tree of this
	 * layout.
	 * @param id The Id to search for.
	 * @return The Element with that Id or null
	 * if none were found.
	 */
	public abstract Element FindElementById(String id);
}
