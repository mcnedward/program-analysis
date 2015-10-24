package com.architecture_design.app;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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

	private List<CompilationUnit> compilationUnits;
	private List<File> files;

	private ClassVisitor classVisitor;
	private List<ClassObject> classObjects;

	public Analyser() {
		compilationUnits = new ArrayList<CompilationUnit>();
		files = new ArrayList<File>();
		classVisitor = new ClassVisitor();
		classObjects = new ArrayList<ClassObject>();
	}

	public List<ClassObject> analyse(File file) {
		compilationUnits.clear();
		classObjects.clear();
		files.clear();
		
		try {
			loadFile(file);
		} catch (IOException e) {
			e.printStackTrace();
		}

		for (CompilationUnit cu : compilationUnits) {
			classVisitor.reset();
			classVisitor.visit(cu, null);
			ClassObject classObject = classVisitor.getClassObject();
			classObjects.add(classObject);
		}

		return classObjects;
	}

	private void loadFile(File selectedFile) throws IOException {
		System.out.println("Loading: " + selectedFile.getName());

		if (selectedFile.isDirectory())
			handleDirectory(selectedFile.listFiles());
		else
			files.add(selectedFile);

		try {
			for (File file : files) {
				CompilationUnit cu = JavaParser.parse(file);
				compilationUnits.add(cu);
			}
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	private void handleDirectory(File[] directory) {
		for (File file : directory) {
			if (file.isDirectory())
				handleDirectory(file.listFiles());
			if (file.isFile()) {
				if (file.getAbsolutePath().contains(".java")) {
					files.add(file);
				}
			}
		}
	}

	/**
	 * @return the files
	 */
	public List<File> getFiles() {
		return files;
	}
}
