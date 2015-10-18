package com.architecture_design.app.classobject;

/**
 * @author Edward McNealy <edwardmcn64@gmail.com> - Oct 18, 2015
 *
 */
public class MethodObject {

	private String accessModifier;
	private String nonAccessModifier;
	private String returnType;
	private String methodName;

	public MethodObject() {

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
	 * @return the returnType
	 */
	public String getReturnType() {
		return returnType;
	}

	/**
	 * @param returnType
	 *            the returnType to set
	 */
	public void setReturnType(String returnType) {
		this.returnType = returnType;
	}

	/**
	 * @return the methodName
	 */
	public String getMethodName() {
		return methodName;
	}

	/**
	 * @param methodName
	 *            the methodName to set
	 */
	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	@Override
	public String toString() {
		String value = "Method: ";
		if (accessModifier != null)
			value += accessModifier + " ";
		if (nonAccessModifier != null)
			value += nonAccessModifier + " ";
		if (returnType != null)
			value += returnType + " ";
		if (methodName != null)
			value += methodName;
		return value;
	}

}
