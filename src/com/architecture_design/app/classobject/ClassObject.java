package com.architecture_design.app.classobject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.architecture_design.app.classobject.method.MethodObject;

/**
 * @author Edward McNealy <edwardmcn64@gmail.com> - Oct 18, 2015
 *
 */
public class ClassObject extends BaseObject {

	private String accessModifier;
	private String nonAccessModifier;
	private List<String> extendsList;
	private List<String> interfaces;
	private List<MethodObject> methods;
	private List<VariableObject> variables;
	private List<ExpressionObject> expressions;

	private File sourceFile;

	public ClassObject() {
		super("class");
		extendsList = new ArrayList<String>();
		interfaces = new ArrayList<String>();
		methods = new ArrayList<MethodObject>();
		variables = new ArrayList<VariableObject>();
	}

	public void addExtends(String extendsName) {
		extendsList.add(extendsName);
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

	public MethodObject getMethodByName(String methodName) {
		for (MethodObject method : methods) {
			if (method.getName().equals(methodName))
				return method;
		}
		return null;
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
	 * @return the extendsList
	 */
	public List<String> getExtendsList() {
		return extendsList;
	}

	/**
	 * @param extendsList
	 *            the extendsList to set
	 */
	public void setExtendsList(List<String> extendsList) {
		this.extendsList = extendsList;
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

	/**
	 * @return the expressions
	 */
	public List<ExpressionObject> getExpressions() {
		return expressions;
	}

	/**
	 * @param expressions
	 *            the expressions to set
	 */
	public void setExpressions(List<ExpressionObject> expressions) {
		this.expressions = expressions;
	}

	/**
	 * @return the sourceFile
	 */
	public File getSourceFile() {
		return sourceFile;
	}

	/**
	 * @param sourceFile
	 *            the sourceFile to set
	 */
	public void setSourceFile(File sourceFile) {
		this.sourceFile = sourceFile;
	}

	@Override
	public String toString() {
		String value = "Class: ";
		if (accessModifier != null)
			value += accessModifier + " ";
		if (nonAccessModifier != null)
			value += nonAccessModifier + " ";
		if (name != null)
			value += name + " ";
		if (!extendsList.isEmpty()) {
			value += " extends ";
			for (int x = 0; x < extendsList.size(); x++) {
				value += extendsList.get(x);
				if (x != extendsList.size() - 1)
					value += ", ";
			}
		}
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
