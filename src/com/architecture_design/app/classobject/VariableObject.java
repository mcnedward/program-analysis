package com.architecture_design.app.classobject;

/**
 * @author Edward McNealy <edwardmcn64@gmail.com> - Oct 19, 2015
 *
 */
public class VariableObject extends BaseObject {
	public VariableObject() {
		super("Variable");
	}
	
	public VariableObject(String variableName) {
		this();
		name = variableName;
	}
}
