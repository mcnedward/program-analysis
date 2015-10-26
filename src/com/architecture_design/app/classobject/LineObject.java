package com.architecture_design.app.classobject;

/**
 * @author Edward McNealy <edwardmcn64@gmail.com> - Oct 24, 2015
 *
 */
public class LineObject {

	protected LineType lineType;
	protected int lineNumber;
	protected String line;
	protected int nodeNumber;

	public LineObject() {

	}

	public LineObject(String line, int lineNumber) {
		this.line = line;
		this.lineNumber = lineNumber;
	}

	/**
	 * @return the lineType
	 */
	public LineType getLineType() {
		return lineType;
	}

	/**
	 * @param lineType
	 *            the lineType to set
	 */
	public void setLineType(LineType lineType) {
		this.lineType = lineType;
	}

	/**
	 * @return the lineNumber
	 */
	public int getLineNumber() {
		return lineNumber;
	}

	/**
	 * @param lineNumber
	 *            the lineNumber to set
	 */
	public void setLineNumber(int lineNumber) {
		this.lineNumber = lineNumber;
	}

	/**
	 * @return the line
	 */
	public String getLine() {
		return line;
	}

	/**
	 * @param line
	 *            the line to set
	 */
	public void setLine(String line) {
		this.line = line;
	}

	/**
	 * @return the nodeNumber
	 */
	public int getNodeNumber() {
		return nodeNumber;
	}

	/**
	 * @param nodeNumber
	 *            the nodeNumber to set
	 */
	public void setNodeNumber(int nodeNumber) {
		this.nodeNumber = nodeNumber;
	}

	@Override
	public String toString() {
		return "Line: " + line + " [" + lineNumber + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((line == null) ? 0 : line.trim().hashCode());
		result = prime * result + lineNumber;
		result = prime * result + ((lineType == null) ? 0 : lineType.hashCode());
		result = prime * result + nodeNumber;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		LineObject other = (LineObject) obj;
		if (line == null) {
			if (other.line != null)
				return false;
		} else if (!line.trim().equals(other.line.trim()))
			return false;
		if (lineNumber != other.lineNumber)
			return false;
		if (lineType != other.lineType)
			return false;
		if (nodeNumber != other.nodeNumber)
			return false;
		return true;
	}
}