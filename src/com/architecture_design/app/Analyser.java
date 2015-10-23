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
	private static String FILE_LOCATION = "resources/Account.java";

	private static CompilationUnit cu = null;
	private static ClassVisitor classVisitor;

	public Analyser() {
		classVisitor = new ClassVisitor();
	}

	public ClassObject analyse(String fileLocation) {
		try {
			loadFile(fileLocation);
		} catch (IOException e) {
			e.printStackTrace();
		}

		classVisitor.visit(cu, null);
		ClassObject classObject = classVisitor.getClassObject();

		System.out.println(classObject.getName());
		return classObject;
	}

	private void loadFile(String fileLocation) throws IOException {
		FileInputStream input = null;
		try {
			input = new FileInputStream(FILE_LOCATION);
			cu = JavaParser.parse(input);
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			System.out.println("File not found: " + FILE_LOCATION + "...");
		} finally {
			if (input != null)
				input.close();
		}
	}
}
