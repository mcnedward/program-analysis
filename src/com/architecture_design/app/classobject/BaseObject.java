package com.architecture_design.app.classobject;

/**
 * @author Edward McNealy <edwardmcn64@gmail.com> - Oct 19, 2015
 *
 */
public abstract class BaseObject {
	protected String accessModifier;
	protected String nonAccessModifier;
	protected String returnType;
	protected String name;
	
	private String objectType;
	
	public BaseObject(String objectType) {
		this.objectType = objectType;
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
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	

	@Override
	public String toString() {
		String value = objectType + ": ";
		if (accessModifier != null)
			value += accessModifier + " ";
		if (nonAccessModifier != null)
			value += nonAccessModifier + " ";
		if (returnType != null)
			value += returnType + " ";
		if (name != null)
			value += name;
		return value;
	}

}
