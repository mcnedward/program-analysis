package com.architecture_design.app.ui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.List;

import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EmptyBorder;

import com.architecture_design.app.Analyser;
import com.architecture_design.app.classobject.ClassObject;
import com.architecture_design.app.ui.panel.ContentPanel;

/**
 * @author Edward McNealy <edwardmcn64@gmail.com> - Oct 18, 2015
 *
 */
public class MainWindow extends JFrame {
	private static final long serialVersionUID = 1L;

	public static int WIDTH = 1300;
	public static int HEIGHT = 800;
	
	// Panels
	private JPanel topLevelPanel;
	private JPanel filePanel;
	private JPanel mainPanel;
	private ContentPanel contentPanel;
	// Buttons
	private JButton btnBrowse;
	private JButton btnLoad;
	private JComboBox<String> comboBox;
	// File list
	private JScrollPane scrollPane;
	private JList<File> listFiles;
	private DefaultListModel<File> listModel;

	private Analyser analyser;
	private String fileLocation;
	private JLabel lblFiles;
	private JPanel messagePanel;
	private JLabel lblMessage;

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
		findResources();

		analyser = new Analyser();
	}

	private void findResources() {
		// TODO Remove this hard-coded value
		// comboBox.addItem("C:\\Users\\Edward\\Dev\\Workspace");
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
		setBounds(100, 100, WIDTH, HEIGHT);
		topLevelPanel = new JPanel();
		topLevelPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(topLevelPanel);

		initializeComponents();
	}

	private void initializeComponents() {
		topLevelPanel.setLayout(null);

		filePanel = new JPanel();
		filePanel.setBounds(5, 5, 700, 30);
		topLevelPanel.add(filePanel);
		filePanel.setLayout(new GridLayout(2, 1, 0, 0));
		filePanel.setLayout(new BorderLayout(10, 0));

		JLabel lblFileLocation = new JLabel("Resource Location:");
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

		mainPanel = new JPanel();
		FlowLayout fl_mainPanel = (FlowLayout) mainPanel.getLayout();
		fl_mainPanel.setHgap(0);
		fl_mainPanel.setVgap(0);
		int width = getWidth() - (getWidth() / 5);
		int height = 700;
		int x = getWidth() - width - 40;
		int y = 50;

		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(200, 50, 1050, 750);
		scrollPane_1.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollPane_1.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		topLevelPanel.add(scrollPane_1);
		mainPanel.setBounds(x, y, width, height);
		scrollPane_1.setViewportView(mainPanel);

		contentPanel = new ContentPanel(mainPanel);
		mainPanel.add(contentPanel);

		listModel = new DefaultListModel<File>();

		JPanel filesPanel = new JPanel();
		filesPanel.setBounds(5, 51, 180, 425);
		topLevelPanel.add(filesPanel);
		filesPanel.setLayout(new BorderLayout(0, 0));

		lblFiles = new JLabel("Files");
		filesPanel.add(lblFiles, BorderLayout.NORTH);
		listFiles = new JList<File>(listModel);
		listFiles.setCellRenderer(new FileListCellRenderer());
		listFiles.setFont(new Font("Tahoma", Font.PLAIN, 10));
		listFiles.setVisibleRowCount(10);
		listFiles.setSelectedIndex(0);
		listFiles.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		listFiles.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					int index = listFiles.getSelectedIndex();
					File selectedFile = listModel.getElementAt(index);
					load(selectedFile, listModel.size() == 1);
				}
			}
		});
		scrollPane = new JScrollPane(listFiles);
		filesPanel.add(scrollPane);

		messagePanel = new JPanel();
		messagePanel.setBounds(720, 5, 440, 30);
		topLevelPanel.add(messagePanel);
		messagePanel.setLayout(new BorderLayout(0, 0));

		lblMessage = new JLabel("Load a java file or project.");
		lblMessage.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblMessage.setHorizontalAlignment(SwingConstants.LEFT);
		messagePanel.add(lblMessage);
	}

	private void browseAction() {
		lblMessage.setText("");
		final JFileChooser fileChooser = new JFileChooser();
		fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);

		File file;
		String currentFileLocation = comboBox.getSelectedItem().toString();
		if (!"".equals(currentFileLocation))
			file = new File(currentFileLocation);
		else
			file = new File(System.getProperty("user.home"));
		fileChooser.setCurrentDirectory(file);

		int result = fileChooser.showOpenDialog(MainWindow.this);
		if (result == JFileChooser.APPROVE_OPTION) {
			File selectedFile = fileChooser.getSelectedFile();
			comboBox.addItem(selectedFile.getAbsolutePath());
			comboBox.setSelectedItem(selectedFile.getAbsolutePath());
			fileLocation = selectedFile.getAbsolutePath();
			loadAction();
		}
	}

	private void loadAction() {
		lblMessage.setText("");
		fileLocation = (String) comboBox.getSelectedItem();
		if (fileLocation == null || fileLocation == "") {
			lblMessage.setText("You need to enter a file.");
			return;
		}
		File selectedFile = new File(fileLocation);
		if (selectedFile.isDirectory()) {
			boolean isProject = false;
			for (File file : selectedFile.listFiles()) {
				if (file.getName().contains(".project")) {
					isProject = true;
				}
			}
			if (isProject)
				load(selectedFile, true);
			else
				lblMessage.setText("You need to select a Java project directory or file.");
		}
		if (selectedFile.isFile()) {
			if (selectedFile.getPath().endsWith(".java"))
				load(selectedFile, true);
			else
				lblMessage.setText("You need to select a Java project directory or file.");
		}
	}

	private void load(File file, boolean clearList) {
		// Check if file is already loaded
		for (int x = 0; x < listModel.size(); x++) {
			if (listModel.getElementAt(x).equals(file)) {
				contentPanel.loadClassObjectForFile(file);
				return;
			}
		}
		List<ClassObject> classObjects = analyser.analyse(file);
		contentPanel.loadClassObjects(classObjects);

		if (clearList)
			listModel.clear();
		for (File f : analyser.getFiles())
			listModel.addElement(f);
	}
}

class FileListCellRenderer extends DefaultListCellRenderer {
	private static final long serialVersionUID = 1L;

	public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
		super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
		if (value instanceof File) {
			setText(((File) value).getName());
			setToolTipText(((File) value).getAbsolutePath());
		}
		return this;
	}

}
