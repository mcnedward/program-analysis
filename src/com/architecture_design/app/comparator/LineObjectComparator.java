package com.architecture_design.app.comparator;

import java.util.Comparator;

import com.architecture_design.app.classobject.LineObject;

/**
 * @author Edward McNealy <edwardmcn64@gmail.com> - Oct 28, 2015
 *
 */
public class LineObjectComparator implements Comparator<LineObject> {
	@Override
	public int compare(LineObject o1, LineObject o2) {
		return o1.getLineNumber() - o2.getLineNumber();
	}
}
