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

	private List<CompilationHolder> compilationHolders;
	private List<File> files;

	private ClassVisitor classVisitor;
	private List<ClassObject> classObjects;

	public Analyser() {
		compilationHolders = new ArrayList<CompilationHolder>();
		files = new ArrayList<File>();
		classVisitor = new ClassVisitor();
		classObjects = new ArrayList<ClassObject>();
	}

	/**
	 * Analyse the file or directory. This will create a List of ClassObjects for every Java item in the File.
	 * 
	 * @param file
	 *            The file or directory.
	 * @return A List of all the ClassObjects in the file or directory.
	 */
	public List<ClassObject> analyse(File file) {
		compilationHolders.clear();
		classObjects.clear();
		files.clear();

		try {
			loadFile(file);
		} catch (IOException e) {
			e.printStackTrace();
		}

		for (CompilationHolder holder : compilationHolders) {
			classVisitor.reset();
			classVisitor.visit(holder.compilationUnit, null);
			ClassObject classObject = classVisitor.getClassObject();
			classObject.setSourceFile(holder.file);
			classObjects.add(classObject);
		}

		return classObjects;
	}

	/**
	 * Loads the file or directory and creates the CompilationUnits.
	 * 
	 * @param selectedFile
	 *            The selected file or directory.
	 * @throws IOException
	 */
	private void loadFile(File selectedFile) throws IOException {
		System.out.println("Loading: " + selectedFile.getAbsolutePath());

		if (selectedFile.isDirectory())
			handleDirectory(selectedFile.listFiles());
		else
			files.add(selectedFile);

		try {
			for (File file : files) {
				CompilationUnit cu = JavaParser.parse(file);
				compilationHolders.add(new CompilationHolder(cu, file));
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

class CompilationHolder {
	protected CompilationUnit compilationUnit;
	protected File file;

	protected CompilationHolder(CompilationUnit cu, File file) {
		compilationUnit = cu;
		this.file = file;
	}
}
