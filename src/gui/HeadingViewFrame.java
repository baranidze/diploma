package gui;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;

import javax.swing.ButtonGroup;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import db.DBWorker;
import logic.Converter;
import logic.Rubric;

public class HeadingViewFrame extends JFrame {
	
	private static HeadingViewFrame instance;
	
	private JLabel searchLabel = new JLabel("Пошук:");
	
	private JPanel searchPanel = new JPanel();
	
	private JRadioButton rubricSearch = new JRadioButton("за рубрикою");
	private JRadioButton dateSearch = new JRadioButton("за датою");
	private JRadioButton bothSearch = new JRadioButton("за датою та рубрикою");
	
	private Font menuFont = new Font("Verdana", Font.PLAIN, 11);
	private Font searchFont = new Font("Verdana", Font.PLAIN, 14);
	
	private JMenuBar menuBar = new JMenuBar();
	
	private JMenu mainMenu = new JMenu("Рубрикація");
	private JMenu taskMenu = new JMenu("Виділення задач");
	private JMenuItem viewItem = new JMenuItem("Перегляд документів");
	private JMenuItem rubricationItem = new JMenuItem("Рубрикація документів");
	private JMenuItem rubricationView = new JMenuItem("Перегляд рубрик");
	private JMenuItem rubricAdd = new JMenuItem("Добавити нову рубрику");
	
	private JTextField dateField = new JTextField("Введіть дату");
	private JComboBox<String> rubricsList = new JComboBox<String>();
	
	private JScrollPane tableScroll;
	
	JLabel rubricLabel = new JLabel("Оберіть рубрику: ");
	JLabel dateLabel = new JLabel("Введіть період пошуку: ");
	
	ButtonGroup bG = new ButtonGroup();
		
	Rubric[] rubric = null;
	
	public static HeadingViewFrame getInstance(){
		if(instance == null){
			instance = new HeadingViewFrame();
		}
		return instance;
	}
	
	
	public HeadingViewFrame() {	
		
		setBounds(100, 100, 800, 423);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		setResizable(false);
		getContentPane().setLayout(null);
		setTitle("Перегляд документів");
		
		initialize();
		initializeMenuListeners();
		
		
		repaint();
	}
	
	private void initialize() {
		
		try {
			rubric = Converter.convertDBData(DBWorker.getInstance().getRurics());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		for(int i = 0; i < rubric.length; i++){
			rubricsList.addItem(rubric[i].getName());
		}
		
		searchLabel.setBounds(10, 20, 100, 20);
		searchLabel.setFont(searchFont);
		add(searchLabel);
		
		rubricSearch.setBounds(70, 20, 120, 20);
		rubricSearch.setFont(searchFont);
		
		dateSearch.setBounds(200, 20, 100, 20);
		dateSearch.setFont(searchFont);
		
		bothSearch.setBounds(10, 50, 200, 20);
		bothSearch.setFont(searchFont);
		
		bG.add(rubricSearch);
		bG.add(dateSearch);
		bG.add(bothSearch);
		
		add(rubricSearch);
		add(dateSearch);
		add(bothSearch);
		
		viewItem.setFont(menuFont);
		rubricationItem.setFont(menuFont);
		rubricationView.setFont(menuFont);
		rubricAdd.setFont(menuFont);
		viewItem.setEnabled(false);
		menuBar.setBounds(10, 10, 100, 20);
		setJMenuBar(menuBar);
		
		menuBar.add(mainMenu);
		menuBar.add(taskMenu);
		
		taskMenu.setFont(menuFont);
		mainMenu.setFont(menuFont);
		mainMenu.add(viewItem);
		mainMenu.add(rubricationItem);
		mainMenu.add(rubricationView);
		mainMenu.add(rubricAdd);
		
		mainMenu.addSeparator();
		
	}
	
	private void initializeMenuListeners(){
		
		taskMenu.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent e) {
				HeadingViewFrame.this.setVisible(false);
				MainWindow.getInstance().setVisible(true);
			}
		});
		
		rubricationView.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				HeadingViewFrame.this.setVisible(false);
				RubricViewFrame.getInstance().setVisible(true);
			}
		});
		
		rubricationItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				HeadingViewFrame.this.setVisible(false);
				MainFrame.getInstance().setVisible(true);
			}
		});
		
		rubricAdd.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				HeadingViewFrame.this.setVisible(false);
				AddRubricFrame.getInstance().setVisible(true);
			}
		});
		
	}
	
	private void initializeComponentsListteners(){
		rubricSearch.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				dateLabel.setVisible(false);
				dateField.setVisible(false);
				rubricLabel.setBounds(500, 20, 170, 20);
				rubricLabel.setVisible(true);
				rubricsList.setVisible(true);
				rubricsList.setBounds(600, 20, 150, 20);
				
				HeadingViewFrame.this.add(rubricLabel);
				HeadingViewFrame.this.add(rubricsList);
				
				HeadingViewFrame.this.repaint();
			}
		});
		
		dateSearch.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				rubricLabel.setVisible(false);
				dateLabel.setVisible(true);
				dateField.setVisible(true);
				rubricsList.setVisible(false);
				dateLabel.setBounds(450, 20, 170, 20);
				dateField.setBounds(600, 20, 150, 20);
				HeadingViewFrame.this.add(dateLabel);
				HeadingViewFrame.this.add(dateField);
				HeadingViewFrame.this.repaint();
			}
		});
		
		bothSearch.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				rubricLabel.setVisible(true);
				dateLabel.setVisible(true);
				dateLabel.setBounds(450, 45, 170, 20);
				rubricLabel.setBounds(450, 20, 170, 20);
				dateField.setBounds(600, 45, 150, 20);
				rubricsList.setBounds(600, 20, 150, 20);
				rubricsList.setVisible(true);
				HeadingViewFrame.this.add(dateLabel);
				HeadingViewFrame.this.add(rubricsList);
				HeadingViewFrame.this.add(dateLabel);
				HeadingViewFrame.this.add(dateField);
				HeadingViewFrame.this.repaint();
			}
		});
		
		rubricsList.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){ 
				
			}
		});
		
	}
	
}
