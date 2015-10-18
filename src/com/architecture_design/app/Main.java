package com.architecture_design.app;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.architecture_design.app.classobject.ClassObject;
import com.architecture_design.app.parser.FileParser;

/**
 * @author Edward McNealy <edwardmcn64@gmail.com> - Oct 16, 2015
 *
 */
public class Main {

	private static String FILE_LOCATION = "resources/Account.java";

	public static void main(String[] args) {
		List<String> file = readFile();

		FileParser parser = new FileParser(file);
		ClassObject classObject = parser.createClassObject();
		System.out.println(classObject.toString());
	}

	private static List<String> readFile() {
		List<String> file = new ArrayList<String>();
		try {
			FileReader fr = new FileReader(FILE_LOCATION);
			BufferedReader reader = new BufferedReader(fr);
			String line = "";

			while ((line = reader.readLine()) != null) {
				file.add(line);
			}
			fr.close();
			reader.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.out.println("Could not find the file located at: " + FILE_LOCATION + ".");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return file;
	}

}
