package com.architecture_design.app;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import com.architecture_design.app.classobject.ClassObject;
import com.architecture_design.app.visitor.ClassVisitor;
import com.github.javaparser.JavaParser;
import com.github.javaparser.ParseException;
import com.github.javaparser.ast.CompilationUnit;

/**
 * @author Edward McNealy <edwardmcn64@gmail.com> - Oct 23, 2015
 *
 */
public class Analyser {

	private CompilationUnit cu = null;
	private ClassVisitor classVisitor;

	public Analyser() {
		classVisitor = new ClassVisitor();
	}

	public ClassObject analyse(String fileLocation) {
		try {
			loadFile(fileLocation);
		} catch (IOException e) {
			e.printStackTrace();
		}

		classVisitor.reset();
		classVisitor.visit(cu, null);
		ClassObject classObject = classVisitor.getClassObject();

		System.out.println(classObject.getName());
		return classObject;
	}

	private void loadFile(String fileLocation) throws IOException {
		System.out.println("Loading file: " + fileLocation);
		FileInputStream input = null;
		try {
			input = new FileInputStream(fileLocation);
			cu = JavaParser.parse(input);
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			System.out.println("File not found: " + fileLocation + "...");
		} finally {
			if (input != null)
				input.close();
		}
	}
}
