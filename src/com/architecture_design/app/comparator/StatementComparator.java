package com.architecture_design.app.comparator;

import java.util.Comparator;

import com.architecture_design.app.classobject.statement.BaseStatement;

/**
 * @author Edward McNealy <edwardmcn64@gmail.com> - Oct 28, 2015
 *
 */
public class StatementComparator implements Comparator<BaseStatement> {
	@Override
	public int compare(BaseStatement o1, BaseStatement o2) {
		return o1.getBeginLine() - o2.getBeginLine();
	}
}
