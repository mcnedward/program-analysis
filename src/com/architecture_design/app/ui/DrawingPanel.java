package com.architecture_design.app.ui;

import java.awt.Dimension;
import java.awt.Graphics;

import javax.sound.midi.ControllerEventListener;
import javax.sound.midi.ShortMessage;
import javax.swing.JPanel;

import com.architecture_design.app.classobject.ClassObject;
import com.architecture_design.app.shapes.ClassDiagram;

/**
 * @author Edward McNealy <edwardmcn64@gmail.com> - Oct 18, 2015
 *
 */
public class DrawingPanel extends JPanel implements ControllerEventListener {
	private static final long serialVersionUID = 8628678497368848302L;


	public DrawingPanel() {
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
	}

	public void addClassObject(ClassObject classObject) {
		ClassDiagram classDiagram = new ClassDiagram(this, classObject);
		add(classDiagram);
		repaint();
	}

	@Override
	public void controlChange(ShortMessage event) {
//		repaint();
	}

	@Override
	public Dimension getPreferredSize() {
		return new Dimension(200, 200);
	}
}
