package com.architecture_design.app.visitor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.architecture_design.app.classobject.MethodObject;
import com.architecture_design.app.classobject.MethodParameter;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.Parameter;
import com.github.javaparser.ast.stmt.BlockStmt;

/**
 * @author Edward McNealy <edwardmcn64@gmail.com> - Oct 23, 2015
 *
 */
public class MethodVisitor extends BaseVisitor<MethodObject> {

	private List<MethodObject> methodObjects;

	public MethodVisitor() {
		super();
		methodObjects = new ArrayList<MethodObject>();
	}

	@Override
	public void visit(MethodDeclaration n, MethodObject arg) {
		MethodObject methodObject = new MethodObject();
		methodObject.setName(n.getName());
		methodObject.setReturnType(n.getType().toString());
		methodObject.setModifiers(decodeModifiers(n.getModifiers()));
		for (Parameter p : n.getParameters()) {
			p.accept(this, methodObject);
		}
		n.getBody().accept(this, methodObject);
		methodObjects.add(methodObject);
	}

	@Override
	public void visit(Parameter p, MethodObject arg) {
		MethodObject methodObject = (MethodObject) arg;
		MethodParameter methodParameter = new MethodParameter();
		methodParameter.setParameterName(p.getId().getName());
		methodParameter.setParameterType(p.getType().toString());
		methodObject.addParameter(methodParameter);
	}

	@Override
	public void visit(BlockStmt b, MethodObject arg) {
		String[] lines = b.toString().split("[\\r\\n\\t]\\s?");
		arg.setMethodLines(Arrays.asList(lines));
	}

	/**
	 * @return the methodObjects
	 */
	public List<MethodObject> getMethodObjects() {
		return methodObjects;
	}

}
