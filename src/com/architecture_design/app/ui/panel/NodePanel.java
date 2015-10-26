package com.architecture_design.app.ui.panel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

/**
 * @author Edward McNealy <edwardmcn64@gmail.com> - Oct 26, 2015
 *
 */
public class NodePanel extends JPanel {
	private static final long serialVersionUID = 1L;

	private JLabel label;
	private Color color;

	public NodePanel(String text, Color color) {
		super();
		this.color = color;

		setPreferredSize(new Dimension(80, 60));
		setLayout(new BorderLayout(0, 0));
		label = new JLabel(text);
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setBorder(new EmptyBorder(10, 10, 0, 0));
		add(label, BorderLayout.CENTER);
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		int x = 20;
		int y = 10;
		int width = 50;
		int height = 50;
		g.setColor(color);
		g.drawOval(x, y, width, height);
	}

}
