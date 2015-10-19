package com.architecture_design.app.parser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.architecture_design.app.classobject.ClassObject;
import com.architecture_design.app.classobject.MethodObject;
import com.architecture_design.app.classobject.MethodParameter;
import com.architecture_design.app.classobject.VariableObject;

/**
 * @author Edward McNealy <edwardmcn64@gmail.com> - Oct 16, 2015
 *
 */
public class FileParser {

	private static String[] ACCESS_MODIFIERS = { "public", "private", "protected" };
	private static String[] NON_ACCESS_MODIFIERS = { "static", "final", "abstract", "synchronized" };

	private List<String> file;

	// Patterns
	private Pattern classPattern;
	private Pattern methodWithModifierPattern;
	private Pattern methodWithNonModifierPattern;
	private Pattern variablePattern;
	// Matchers
	private Matcher classMatcher;
	private Matcher variableMatcher;
	private Matcher modifierMatcher;
	private Matcher nonModifierMatcher;
	// Reference to all variable names already found, so duplicates will not be added to class object
	private List<String> variableNames = new ArrayList<String>();

	public FileParser() {
		createPatterns();
	}

	public FileParser(List<String> file) {
		this();
		this.file = file;
	}

	public ClassObject createClassObject() {
		ClassObject classObject = null;
		variableNames.clear();

		for (String line : file) {
			classMatcher = classPattern.matcher(line);
			if (classMatcher.find()) {
				classObject = getClassObject(line);
			} else {
				variableMatcher = variablePattern.matcher(line.trim());
				modifierMatcher = methodWithModifierPattern.matcher(line.trim());
				nonModifierMatcher = methodWithNonModifierPattern.matcher(line.trim());

				if (modifierMatcher.find() || nonModifierMatcher.find()) {
					MethodObject method = getMethodObject(line);
					classObject.addMethod(method);
				} else if (variableMatcher.find()) {
					VariableObject variableObject = handleVariable(line);
					if (variableObject != null) {
						classObject.addVariable(variableObject);
					}
				}
			}
		}

		return classObject;
	}

	private ClassObject getClassObject(String line) {
		ClassObject classObject = new ClassObject();
		String[] words = getWords(line);
		for (int x = 0; x < words.length; x++) {
			String word = words[x];
			if (Arrays.asList(ACCESS_MODIFIERS).contains(word))
				classObject.setAccessModifier(word);
			if (Arrays.asList(NON_ACCESS_MODIFIERS).contains(word))
				classObject.setNonAccessModifier(word);
			else if (word.equals("class"))
				classObject.setClassName(words[x + 1]);
			else if (word.equals("extends"))
				classObject.setSuperClassName(words[x + 1]);
			else if (words[x].equals("implements")) {
				for (int y = x + 1; y < words.length; y++)
					classObject.addInterface(words[y]);
			}
		}
		return classObject;
	}

	private MethodObject getMethodObject(String line) {
		MethodObject methodObject = new MethodObject();

		List<MethodParameter> methodParameters = getMethodParameters(line);
		methodObject.setMethodParameters(methodParameters);

		String methodRegex = "\\([^\\(]*\\)";
		String methodDefinition = line.replaceAll(methodRegex, "");
		String[] words = getWords(methodDefinition);
		for (int x = 0; x < words.length; x++) {
			String word = words[x];
			if (Arrays.asList(ACCESS_MODIFIERS).contains(word)) {
				methodObject.setAccessModifier(word);
			} else if (Arrays.asList(NON_ACCESS_MODIFIERS).contains(word)) {
				methodObject.setNonAccessModifier(word);
			} else if (x == words.length - 2) {
				methodObject.setReturnType(word);
			} else {
				methodObject.setName(word);
			}
		}
		return methodObject;
	}

	private List<MethodParameter> getMethodParameters(String line) {
		List<MethodParameter> methodParameters = new ArrayList<MethodParameter>();

		int beginIndex = line.indexOf("(") + 1;
		int endIndex = line.indexOf(")");
		if (endIndex - beginIndex > 1) {
			String[] parameters = getWords(line.substring(beginIndex, endIndex));
			for (int x = 0; x < parameters.length; x += 2) {
				String paramType = parameters[x];
				String paramName = parameters[x + 1];
				MethodParameter methodParameter = new MethodParameter(paramType, paramName);
				methodParameters.add(methodParameter);
			}
		}
		return methodParameters;
	}
	
	private VariableObject handleVariable(String line) {
		VariableObject variableObject = null;
		String newLine = line;
		if (line.contains("=")) {
			newLine = line.replace(line.substring(line.indexOf("=")), "").trim();
		}
		String[] words = getWords(newLine);

		String variableName = words[words.length - 1];
		if (!variableNames.contains(variableName)) {
			variableNames.add(variableName);

			variableObject = new VariableObject(variableName);
			for (int x = 0; x < words.length; x++) {
				String word = words[x];
				if (Arrays.asList(ACCESS_MODIFIERS).contains(word)) {
					variableObject.setAccessModifier(word);
				} else if (Arrays.asList(NON_ACCESS_MODIFIERS).contains(word)) {
					variableObject.setNonAccessModifier(word);
				} else if (x == words.length - 2) {
					variableObject.setReturnType(word);
				} else {
					variableObject.setName(word);
				}
			}
		}
		return variableObject;
	}

	private void createPatterns() {
		String classRegex = "^(?!\\/\\*).*class.*\\{$";
		classPattern = Pattern.compile(classRegex);

		String modifiers = "";
		for (int x = 0; x < ACCESS_MODIFIERS.length; x++) {
			modifiers += ACCESS_MODIFIERS[x];
			if (x != ACCESS_MODIFIERS.length - 1)
				modifiers += "|";
		}

		String variableRegex = "^(?!package|import|this.|return)\\b.*\\b\\s{1}\\b.*\\b=*.*\\;$";
		variablePattern = Pattern.compile(variableRegex);

		String modifierRegex = "\\b*\\b\\s{1}\\b.*\\b\\s{1}\\b.*\\b\\s*[(].*[)]\\s*\\{$";
		methodWithModifierPattern = Pattern.compile(modifierRegex);
		String nonModifierRegex = "^(?!" + modifiers + ")\\b.*\\b\\s{1}\\b.*\\b\\s*[(].*[)]\\s*\\{$";
		methodWithNonModifierPattern = Pattern.compile(nonModifierRegex);
	}

	private String[] getWords(String line) {
		return line.replaceAll(",", " ").replaceAll("[^a-zA-Z0-9\\<\\> ]+", "").split("\\s+");
	}

	/**
	 * @return the file
	 */
	public List<String> getFile() {
		return file;
	}

	/**
	 * @param file
	 *            the file to set
	 */
	public void setFile(List<String> file) {
		this.file = file;
	}

}
