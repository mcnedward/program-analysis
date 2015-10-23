package com.architecture_design.app.classobject;

/**
 * @author Edward McNealy <edwardmcn64@gmail.com> - Oct 19, 2015
 *
 */
public abstract class BaseObject {
	
	protected String accessModifier;
	protected String nonAccessModifier;
	protected String modifiers;
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
	 * @param accessModifier the accessModifier to set
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
	 * @param nonAccessModifier the nonAccessModifier to set
	 */
	public void setNonAccessModifier(String nonAccessModifier) {
		this.nonAccessModifier = nonAccessModifier;
	}

	/**
	 * @return the modifiers
	 */
	public String getModifiers() {
		return modifiers;
	}

	/**
	 * @param modifiers the modifiers to set
	 */
	public void setModifiers(String modifiers) {
		this.modifiers = modifiers;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		String value = objectType + ": ";
		if (modifiers != null)
			value += modifiers + " ";
		if (name != null)
			value += name;
		return value;
	}

}
