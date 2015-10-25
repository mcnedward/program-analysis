package com.architecture_design.app.classobject.statement;

import com.architecture_design.app.classobject.LineObject;

/**
 * @author Edward McNealy <edwardmcn64@gmail.com> - Oct 24, 2015
 *
 */
public class IfStatement extends BaseStatement {

	private String condition;
	private BaseStatement thenStatement;
	private BaseStatement elseStatement;

	public IfStatement() {
		super();
	}

	public void updateNodeNumbers() {
		for (LineObject thenLine : thenStatement.getLines()) {
			for (LineObject line : lines) {
				if (thenLine.getLineNumber() == line.getLineNumber()) {
					thenLine.setNodeNumber(line.getNodeNumber());
					break;
				}
			}
		}
		for (LineObject elseLine : elseStatement.getLines()) {
			for (LineObject line : lines) {
				if (elseLine.getLineNumber() == line.getLineNumber()) {
					elseLine.setNodeNumber(line.getNodeNumber());
					break;
				}
			}
		}
	}

	/**
	 * @return the condition
	 */
	public String getCondition() {
		return condition;
	}

	/**
	 * @param condition
	 *            the condition to set
	 */
	public void setCondition(String condition) {
		this.condition = condition;
	}

	/**
	 * @return the thenStatement
	 */
	public BaseStatement getThenStatement() {
		return thenStatement;
	}

	/**
	 * @param thenStatement
	 *            the thenStatement to set
	 */
	public void setThenStatement(BaseStatement thenStatement) {
		this.thenStatement = thenStatement;
	}

	/**
	 * @return the elseStatement
	 */
	public BaseStatement getElseStatement() {
		return elseStatement;
	}

	/**
	 * @param elseStatement
	 *            the elseStatement to set
	 */
	public void setElseStatement(BaseStatement elseStatement) {
		this.elseStatement = elseStatement;
	}
}
