package com.architecture_design.app.classobject;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Edward McNealy <edwardmcn64@gmail.com> - Oct 18, 2015
 *
 */
public class MethodObject extends BaseObject {

	private List<MethodParameter> methodParameters;
	private List<String> methodLines;

	public MethodObject() {
		super("Method");
		methodParameters = new ArrayList<MethodParameter>();
		methodLines = new ArrayList<String>();
	}

	/**
	 * @return the methodParameters
	 */
	public List<MethodParameter> getMethodParameters() {
		return methodParameters;
	}

	/**
	 * @param methodParameters
	 *            the methodParameters to set
	 */
	public void setMethodParameters(List<MethodParameter> methodParameters) {
		this.methodParameters = methodParameters;
	}

	/**
	 * @return the methodLines
	 */
	public List<String> getMethodLines() {
		return methodLines;
	}

	/**
	 * @param methodLines the methodLines to set
	 */
	public void setMethodLines(List<String> methodLines) {
		this.methodLines = methodLines;
	}

}
