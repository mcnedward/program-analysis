package com.architecture_design.app.classobject.statement;

import java.util.ArrayList;
import java.util.List;

import com.architecture_design.app.classobject.LineObject;

/**
 * @author Edward McNealy <edwardmcn64@gmail.com> - Oct 24, 2015
 *
 */
public class BaseStatement implements IStatement {

	private List<LineObject> lines;
	
	public BaseStatement() {
		lines = new ArrayList<LineObject>();
	}
	
	public void addLine(String line, int lineNumber) {
		lines.add(new LineObject(line, lineNumber));
		
	}

	/**
	 * @return the lines
	 */
	@Override
	public List<LineObject> getLines() {
		return lines;
	}

	/**
	 * @param lines the lines to set
	 */
	public void setLines(List<LineObject> lines) {
		this.lines = lines;
	}
}
