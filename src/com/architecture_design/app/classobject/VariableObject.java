package com.architecture_design.app.classobject;

/**
 * @author Edward McNealy <edwardmcn64@gmail.com> - Oct 19, 2015
 *
 */
public class VariableObject extends BaseObject {
	
	private String type;
	
	public VariableObject() {
		super("Variable");
	}
	
	public VariableObject(String variableName) {
		this();
		name = variableName;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}
}
