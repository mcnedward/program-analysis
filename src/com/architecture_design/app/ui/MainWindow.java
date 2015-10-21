package com.architecture_design.app.ui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EmptyBorder;

import com.architecture_design.app.classobject.ClassObject;
import com.architecture_design.app.parser.FileParser;

/**
 * @author Edward McNealy <edwardmcn64@gmail.com> - Oct 18, 2015
 *
 */
public class MainWindow extends JFrame {
	private static final long serialVersionUID = 1L;

	// Panels
	private DrawingPanel drawingPanel;
	private DrawingPanel filePanel;
	private DrawingPanel mainPanel;
	private ContentPanel contentPanel;
	// Buttons
	private JButton btnBrowse;
	private JButton btnLoad;
	private JComboBox<String> comboBox;
	
	private FileParser fileParser;
	
	private String fileLocation;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainWindow frame = new MainWindow();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public MainWindow() {
		initialize();
		
		fileParser = new FileParser();
		findResources();
	}
	
	private void findResources() {
		File resourceDir = new File("resources");
		for (File file : resourceDir.listFiles()) {
			comboBox.addItem(file.getAbsolutePath());
		}
	}
	
	private void initialize() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
			e.printStackTrace();
			System.out.println("Something went wrong when trying to use the System Look and Feel...");
		}
		setBounds(100, 100, 1200, 500);
		drawingPanel = new DrawingPanel();
		drawingPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(drawingPanel);
		
		initializeComponents();
	}
	
	private void initializeComponents() {
		drawingPanel.setLayout(null);
		
		filePanel = new DrawingPanel();
		filePanel.setBounds(5, 5, 573, 29);
		drawingPanel.add(filePanel);
		filePanel.setLayout(new GridLayout(2, 1, 0, 0));
		filePanel.setLayout(new BorderLayout(0, 0));
		
		JLabel lblFileLocation = new JLabel("File Location:");
		filePanel.add(lblFileLocation, BorderLayout.WEST);
		lblFileLocation.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		
		comboBox = new JComboBox<String>();
		comboBox.setFont(new Font("Tahoma", Font.PLAIN, 10));
		comboBox.setEditable(true);
		filePanel.add(comboBox, BorderLayout.CENTER);
		
		JPanel panel = new JPanel();
		filePanel.add(panel, BorderLayout.EAST);
		panel.setLayout(new GridLayout(0, 2, 0, 0));
		
		btnLoad = new JButton("Load");
		panel.add(btnLoad);
		btnLoad.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		
		btnBrowse = new JButton("Browse");
		panel.add(btnBrowse);
		btnBrowse.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		btnBrowse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				browseAction();
			}
		});
		btnLoad.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				loadAction();
			}
		});
		
		mainPanel = new DrawingPanel();
		FlowLayout fl_mainPanel = (FlowLayout) mainPanel.getLayout();
		fl_mainPanel.setHgap(0);
		fl_mainPanel.setVgap(0);
		mainPanel.setBounds(15, 50, 1154, 395);
		drawingPanel.add(mainPanel);
		
		contentPanel = new ContentPanel(mainPanel);
		mainPanel.add(contentPanel);
	}
	
	private void browseAction() {
		final JFileChooser fileChooser = new JFileChooser();
		fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
		int result = fileChooser.showOpenDialog(MainWindow.this);
		if (result == JFileChooser.APPROVE_OPTION) {
			File selectedFile = fileChooser.getSelectedFile();
			comboBox.addItem(selectedFile.getAbsolutePath());
			comboBox.setSelectedItem(selectedFile.getAbsolutePath());
			fileLocation = selectedFile.getAbsolutePath();
		}
	}
	
	private void loadAction() {
		fileLocation = (String) comboBox.getSelectedItem();
		if (fileLocation == null || fileLocation == "") {
			return;
		}
		List<String> file = readFile(fileLocation);
		fileParser.setFile(file);
		ClassObject classObject = fileParser.createClassObject();
		
		contentPanel.addClassObject(classObject);
	}
	
	private static List<String> readFile(String fileLocation) {
		List<String> file = new ArrayList<String>();
		try {
			FileReader fr = new FileReader(fileLocation);
			BufferedReader reader = new BufferedReader(fr);
			String line = "";

			while ((line = reader.readLine()) != null) {
				file.add(line);
			}
			fr.close();
			reader.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.out.println("Could not find the file located at: " + fileLocation + ".");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return file;
	}
}
