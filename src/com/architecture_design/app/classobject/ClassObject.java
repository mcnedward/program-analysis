package com.architecture_design.app.classobject;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Edward McNealy <edwardmcn64@gmail.com> - Oct 18, 2015
 *
 */
public class ClassObject {

	private String accessModifier;
	private String nonAccessModifier;
	private String className;
	private String superClassName;
	private List<String> interfaces;
	private List<MethodObject> methods;
	private List<VariableObject> variables;

	public ClassObject() {
		interfaces = new ArrayList<String>();
		methods = new ArrayList<MethodObject>();
		variables = new ArrayList<VariableObject>();
	}

	public void addInterface(String interfaceName) {
		interfaces.add(interfaceName);
	}

	public void addMethod(MethodObject method) {
		methods.add(method);
	}

	public void addVariable(VariableObject variable) {
		variables.add(variable);
	}

	/**
	 * @return the accessModifier
	 */
	public String getAccessModifier() {
		return accessModifier;
	}

	/**
	 * @param accessModifier
	 *            the accessModifier to set
	 */
	public void setAccessModifier(String accessModifier) {
		this.accessModifier = accessModifier;
	}

	/**
	 * @return the nonAccessModifier
	 */
	public String getNonAccessModifier() {
		return nonAccessModifier;
	}

	/**
	 * @param nonAccessModifier
	 *            the nonAccessModifier to set
	 */
	public void setNonAccessModifier(String nonAccessModifier) {
		this.nonAccessModifier = nonAccessModifier;
	}

	/**
	 * @return the className
	 */
	public String getClassName() {
		return className;
	}

	/**
	 * @param className
	 *            the className to set
	 */
	public void setClassName(String className) {
		this.className = className;
	}

	/**
	 * @return the superClassName
	 */
	public String getSuperClassName() {
		return superClassName;
	}

	/**
	 * @param superClassName
	 *            the superClassName to set
	 */
	public void setSuperClassName(String superClassName) {
		this.superClassName = superClassName;
	}

	/**
	 * @return the interfaces
	 */
	public List<String> getInterfaces() {
		return interfaces;
	}

	/**
	 * @param interfaces
	 *            the interfaces to set
	 */
	public void setInterfaces(List<String> interfaces) {
		this.interfaces = interfaces;
	}

	/**
	 * @return the methods
	 */
	public List<MethodObject> getMethods() {
		return methods;
	}

	/**
	 * @param methods
	 *            the methods to set
	 */
	public void setMethods(List<MethodObject> methods) {
		this.methods = methods;
	}

	/**
	 * @return the variables
	 */
	public List<VariableObject> getVariables() {
		return variables;
	}

	/**
	 * @param variables
	 *            the variables to set
	 */
	public void setVariables(List<VariableObject> variables) {
		this.variables = variables;
	}

	@Override
	public String toString() {
		String value = "Class: ";
		if (accessModifier != null)
			value += accessModifier + " ";
		if (nonAccessModifier != null)
			value += nonAccessModifier + " ";
		if (className != null)
			value += className + " ";
		if (superClassName != null)
			value += "extends " + superClassName;
		if (!interfaces.isEmpty()) {
			value += " implements ";
			for (int x = 0; x < interfaces.size(); x++) {
				value += interfaces.get(x);
				if (x != interfaces.size() - 1)
					value += ", ";
			}
		}
		return value;
	}

}
