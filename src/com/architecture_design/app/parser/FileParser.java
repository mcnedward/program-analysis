package com.architecture_design.app.parser;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.architecture_design.app.classobject.ClassObject;
import com.architecture_design.app.classobject.MethodObject;

/**
 * @author Edward McNealy <edwardmcn64@gmail.com> - Oct 16, 2015
 *
 */
public class FileParser {

	private static String[] ACCESS_MODIFIERS = { "public", "private", "protected" };
	private static String[] NON_ACCESS_MODIFIERS = { "static", "final", "abstract", "synchronized" };

	private List<String> file;

	private Pattern classPattern;
	private Pattern methodWithModifierPattern;
	private Pattern methodWithNonModifierPattern;
	private Matcher classMatcher;
	private Matcher modifierMatcher;
	private Matcher nonModifierMatcher;

	public FileParser(List<String> file) {
		this.file = file;

		createPatterns();
	}

	public ClassObject createClassObject() {
		ClassObject classObject = null;

		for (String line : file) {
			classMatcher = classPattern.matcher(line);
			if (classMatcher.find()) {
				classObject = getClassObject(line);
			} else {
				modifierMatcher = methodWithModifierPattern.matcher(line.trim());
				nonModifierMatcher = methodWithNonModifierPattern.matcher(line.trim());
				if (modifierMatcher.find() || nonModifierMatcher.find()) {
					MethodObject method = getMethodObject(line);
					classObject.addMethod(method);
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
				methodObject.setMethodName(word);
			}
		}
		return methodObject;
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
		String modifierRegex = "\\b*\\b\\s{1}\\b.*\\b\\s{1}\\b.*\\b\\s*[(].*[)]\\s*\\{$";
		methodWithModifierPattern = Pattern.compile(modifierRegex);
		String nonModifierRegex = "^(?!" + modifiers + ")\\b.*\\b\\s{1}\\b.*\\b\\s*[(].*[)]\\s*\\{$";
		methodWithNonModifierPattern = Pattern.compile(nonModifierRegex);
	}

	private String[] getWords(String line) {
		return line.replaceAll(",", " ").replaceAll("[^a-zA-Z0-9 ]+", "").split("\\s+");
	}
}
