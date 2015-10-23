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
 * @author Edward McNealy <edwardmcn64@gmail.com> - Oct 16, 2015
 *
 */
public class Main {

	private static String FILE_LOCATION = "resources/Account.java";

	private static CompilationUnit cu = null;

	private static ClassVisitor classVisitor;

	public static void main(String[] args) {
		try {
			initializeStuff();
		} catch (IOException e) {
			e.printStackTrace();
		}

		createVisitors();

		classVisitor.visit(cu, null);
		ClassObject classObject = classVisitor.getClassObject();

		System.out.println(classObject.getName());
	}

	private static void createVisitors() {
		classVisitor = new ClassVisitor();
	}

	private static void initializeStuff() throws IOException {
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
