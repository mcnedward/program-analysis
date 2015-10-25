package com.architecture_design.app.classobject.method;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.architecture_design.app.classobject.BaseObject;
import com.architecture_design.app.classobject.LineObject;
import com.architecture_design.app.classobject.statement.BaseStatement;

/**
 * @author Edward McNealy <edwardmcn64@gmail.com> - Oct 18, 2015
 *
 */
public class MethodObject extends BaseObject {

	private String returnType;
	private List<MethodParameter> methodParameters;
	
	// Lines and Statements
	private List<LineObject> lines;
	private List<BaseStatement> statements;
	
	private List<MethodCallObject> methodCallObjects;

	public MethodObject() {
		super("Method");
		methodParameters = new ArrayList<MethodParameter>();
		methodCallObjects = new ArrayList<MethodCallObject>();
		
		lines = new ArrayList<LineObject>();
		statements = new ArrayList<BaseStatement>();
	}

	public void addParameter(MethodParameter methodParameter) {
		methodParameters.add(methodParameter);
	}

	public void addMethodCallObject(MethodCallObject methodCallObject) {
		methodCallObjects.add(methodCallObject);
	}

	public void addLine(LineObject line) {
		lines.add(line);
	}
	
	public void addStatement(BaseStatement statement) {
		statements.add(statement);
	}

	public List<LineObject> getLineObjects() {
		List<LineObject> lineObjects = new ArrayList<LineObject>();
		if (!statements.isEmpty()) {
			for (BaseStatement statement : statements) {
				lineObjects.addAll(statement.getLines());
			}
		}
		for (LineObject line : lines) {
			lineObjects.add(line);
		}
		Collections.sort(lineObjects, new LineObjectComparator());
		int x = 1;
		for (LineObject line : lineObjects) {
			line.setNodeNumber(x++);
		}
		return lineObjects;
	}

	/**
	 * @return the returnType
	 */
	public String getReturnType() {
		return returnType;
	}

	/**
	 * @param returnType the returnType to set
	 */
	public void setReturnType(String returnType) {
		this.returnType = returnType;
	}

	/**
	 * @return the methodParameters
	 */
	public List<MethodParameter> getMethodParameters() {
		return methodParameters;
	}

	/**
	 * @param methodParameters the methodParameters to set
	 */
	public void setMethodParameters(List<MethodParameter> methodParameters) {
		this.methodParameters = methodParameters;
	}

	/**
	 * @return the lines
	 */
	public List<LineObject> getLines() {
		return lines;
	}

	/**
	 * @param lines the lines to set
	 */
	public void setLines(List<LineObject> lines) {
		this.lines = lines;
	}

	/**
	 * @return the statements
	 */
	public List<BaseStatement> getStatements() {
		return statements;
	}

	/**
	 * @param statements the statements to set
	 */
	public void setStatements(List<BaseStatement> statements) {
		this.statements = statements;
	}

	/**
	 * @return the methodCallObjects
	 */
	public List<MethodCallObject> getMethodCallObjects() {
		return methodCallObjects;
	}

	/**
	 * @param methodCallObjects the methodCallObjects to set
	 */
	public void setMethodCallObjects(List<MethodCallObject> methodCallObjects) {
		this.methodCallObjects = methodCallObjects;
	}

}

class LineObjectComparator implements Comparator<LineObject> {

	@Override
	public int compare(LineObject o1, LineObject o2) {
		return o1.getLineNumber() - o2.getLineNumber();
	}
	
}
