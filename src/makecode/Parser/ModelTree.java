package makecode.Parser;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import makecode.UML.ModelElement;

public class ModelTree {
	
	private static ModelTree instance;
	
	private Map<String, ModelElement> elements;
	
	private ModelTree() {
		elements = new HashMap<String, ModelElement>();
	}
	
	/**
	 * 
	 * @return
	 */
	public static ModelTree getInstance() {
		if (instance == null)
			instance = new ModelTree();
		
		return instance;
	}
	
	/**
	 * 
	 * @param name
	 * @param me
	 */
	public void addModelElement(String name, ModelElement me) {
		elements.put(name, me);
	}
	
	/**
	 * 
	 * @return
	 */
	public Collection<ModelElement> getModelElements() {
		return elements.values();
	}
	
	/**
	 * 
	 * @param name
	 * @return
	 */
	public ModelElement getModelElement(String name) {
		return elements.get(name);
	}
	
	/**
	 * 
	 */
	public String toString() {
		String str = "";
		
		for (ModelElement e : elements.values())
			str += e.toString() + System.lineSeparator();
		
		return str;
	}
}
