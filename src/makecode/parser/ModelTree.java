package makecode.parser;

import java.util.LinkedList;
import java.util.List;

import makecode.uml.ModelElement;

public class ModelTree {
	
	private static ModelTree instance;
	
	private List<ModelElement> elements;
	
	private ModelTree() {
		elements = new LinkedList<ModelElement>();
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
	public void addModelElement(ModelElement me) {
		elements.add(me);
	}
	
	/**
	 * 
	 * @return
	 */
	public List<ModelElement> getModelElements() {
		return elements;
	}
	
	/**
	 * 
	 */
	public String toString() {
		String str = "";
		
		for (ModelElement e : elements)
			str += e.toString() + System.lineSeparator();
		
		return str;
	}
}
