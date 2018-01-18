package gui;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import db.DBWorker;
import logic.Converter;
import logic.OrderDocument;
import logic.Rubric;

public class RubricViewFrame extends JFrame{
	
	private static RubricViewFrame instance;
	
	private JScrollPane tableScroll;
	private DefaultTableModel model; 
	private JTable rubricsTable;
	
	
	private Font font = new Font("Verdana", Font.PLAIN, 11);
	
	private JMenuBar menuBar = new JMenuBar();
	
	private JMenu mainMenu = new JMenu("Рубрикація");
	private JMenu taskMenu = new JMenu("Виділення задач");
	private JMenuItem rubricationItem = new JMenuItem("Рубрикація документів");
	private JMenuItem rubricationView = new JMenuItem("Перегляд рубрик");
	private JMenuItem rubricAdd = new JMenuItem("Добавити нову рубрику");
	
	private Vector<String> headers = new Vector<String>();
	private Vector<Vector<String>> rubrics = new Vector<Vector<String>>();
	
	public static synchronized RubricViewFrame getInstance(){
		if(instance == null){
			instance = new RubricViewFrame();
		}
		return instance;
	}
	
	private RubricViewFrame(){
		
		setBounds(100, 100, 800, 423);
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setVisible(true);
		setResizable(false);
		getContentPane().setLayout(null);
		setTitle("Перегляд рубрик");
		
		initializeComponents();
		
		initialize();
				
		tableScroll = new JScrollPane(rubricsTable);
		tableScroll.setBounds(10, 20, 700, 340);
		add(tableScroll);
		
		rubricationItem.setFont(font);
		rubricationView.setFont(font);
		rubricAdd.setFont(font);
		rubricationView.setEnabled(false);
		menuBar.setBounds(10, 10, 100, 20);
		
		rubricationItem.setFont(font);
		rubricationView.setFont(font);
		rubricAdd.setFont(font);
		rubricationView.setEnabled(false);
		menuBar.setBounds(10, 10, 100, 20);
		setJMenuBar(menuBar);
		
		menuBar.add(mainMenu);
		menuBar.add(taskMenu);
		
		taskMenu.setFont(font);
		mainMenu.setFont(font);
		mainMenu.add(rubricationItem);
		mainMenu.add(rubricationView);
		mainMenu.add(rubricAdd);
		
		mainMenu.addSeparator();
		
	}
	
	private void initializeComponents(){
		
		headers.add("Назва");
		headers.add("Ключові слова");
		headers.add("Чи є слова логічно пов'язаними?");
		
		try {
			rubrics = fillDataArray(Converter.convertDBData(DBWorker.getInstance().getRurics()));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		model = new DefaultTableModel(rubrics, headers);
		rubricsTable = new JTable(model);
		
		
	}

	private void initialize() {
		// TODO Auto-generated method stub
		
		taskMenu.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent e) {
				RubricViewFrame.this.setVisible(false);
				MainWindow.getInstance().setVisible(true);
			}
		});		
		
		rubricationItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				RubricViewFrame.this.setVisible(false);
				MainFrame.getInstance().setVisible(true);
			}
		});
		
		rubricAdd.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				RubricViewFrame.this.setVisible(false);
				AddRubricFrame.getInstance().setVisible(true);
			}
		});
		
	}
	
private Vector<Vector<String>> fillDataArray(Rubric[] rubric){
		
		Vector<Vector<String>> result = new Vector<Vector<String>>();
		
		for(int i = 0; i < rubric.length; i++){
			Vector<String> temp = new Vector<String>();
			temp.add(rubric[i].getName());
			temp.add(rubric[i].getKeyWords());
			if(rubric[i].getLogicalSequence() == 0) temp.add("Ні");
			else temp.add("Так");
			result.add(temp);
		}
		
		return result;
	}

}
