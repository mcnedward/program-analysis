package com.architecture_design.app.visitor;

import com.github.javaparser.ast.body.ModifierSet;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

/**
 * @author Edward McNealy <edwardmcn64@gmail.com> - Oct 23, 2015
 *
 */
public abstract class BaseVisitor<T> extends VoidVisitorAdapter<T> {

	public BaseVisitor() {

	}
	
	// Adapted from DumpVisitor
    protected String decodeModifiers(final int modifiers) {
		if (ModifierSet.isPrivate(modifiers)) {
			return("private ");
		}
		if (ModifierSet.isProtected(modifiers)) {
			return("protected ");
		}
		if (ModifierSet.isPublic(modifiers)) {
			return("public ");
		}
		if (ModifierSet.isAbstract(modifiers)) {
			return("abstract ");
		}
		if (ModifierSet.isStatic(modifiers)) {
			return("static ");
		}
		if (ModifierSet.isFinal(modifiers)) {
			return("final ");
		}
		if (ModifierSet.isNative(modifiers)) {
			return("native ");
		}
		if (ModifierSet.isStrictfp(modifiers)) {
			return("strictfp ");
		}
		if (ModifierSet.isSynchronized(modifiers)) {
			return("synchronized ");
		}
		if (ModifierSet.isTransient(modifiers)) {
			return("transient ");
		}
		if (ModifierSet.isVolatile(modifiers)) {
			return("volatile ");
		}
		return "";
	}
}
