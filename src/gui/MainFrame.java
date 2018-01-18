package gui;


import javax.swing.JFrame;

import javax.swing.JButton;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import db.DBWorker;
import logic.Check;
import logic.OrderDocument;
import logic.Rubrication;

import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;

public class MainFrame extends JFrame{
	
	private static ArrayList<OrderDocument> documents;
	private Rubrication rubrication = new Rubrication();
	
	private final String PATH = "K:\\РОЗПОРЯДЧІ ДОКУМЕНТИ\\";
	
	
	private static ArrayList<File>fList;        
	private File F = new File(PATH + "НЕРУБРИКОВАНІ ДОКУМЕНТИ");

	private static MainFrame instance;
	
	private JButton rubricationButton = new JButton("Рубрикувати");
	private JButton updateButton = new JButton("Оновити");
	
	private static DefaultTableModel model; 
	private static JTable rubricsTable;
	
	private Font font = new Font("Verdana", Font.PLAIN, 11);
	
	private JMenuBar menuBar = new JMenuBar();
	
	private JMenu mainMenu = new JMenu("Рубрикація");
	private JMenu taskMenu = new JMenu("Виділення задач");
	private JMenuItem rubricationItem = new JMenuItem("Рубрикація документів");
	private JMenuItem rubricationView = new JMenuItem("Перегляд рубрик");
	private JMenuItem rubricAdd = new JMenuItem("Добавити нову рубрику");
	
	private Vector<String> headers = new Vector<String>();
	private Vector<Vector<String>> filesList = new Vector<Vector<String>>();
	
	private JScrollPane tableScroll;

	/**
	 * Create the application.
	 */
	
	public static synchronized MainFrame getInstance(){
		if(instance == null){
			instance = new MainFrame();
		}
		return instance;
	}
	public MainFrame() {
		
		setBounds(100, 100, 800, 423);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		setResizable(false);
		getContentPane().setLayout(null);
		setTitle("Рубрикація докуменів");
		
		initialize();
		repaint();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		
		fillList();
		initializeComponents();
		initializeListeners();
		
	}
	
	
	private void initializeListeners(){
		
		initializeMenuListeners();
		initializeButtonsListeners();		
				
	}
	
	private void initializeComponents(){
		
		headers.add("Шифр");
		headers.add("Дата");
		headers.add("Назва");
		
		
		filesList = fillDataArray(documents);
		model = new DefaultTableModel(filesList, headers);
		rubricsTable = new JTable(model);
		
		TableColumn column = null;
		for (int i = 0; i < 3; i++) {
		    column = rubricsTable.getColumnModel().getColumn(i);
		    if (i == 2) {
		        column.setPreferredWidth(200); //third column is bigger
		    } else {
		        column.setPreferredWidth(50);
		    }
		}
		
		tableScroll = new JScrollPane(rubricsTable);
		tableScroll.setBounds(10, 24, 622, 319);
		add(tableScroll);
		
		
		rubricationButton.setBounds(642, 21, 142, 23);
		add(rubricationButton);
				
		
		updateButton.setBounds(642, 55, 142, 23);
		add(updateButton);
		
		rubricationItem.setFont(font);
		rubricationView.setFont(font);
		rubricAdd.setFont(font);
		rubricationItem.setEnabled(false);
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
	
	private void initializeMenuListeners(){
		
		taskMenu.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent e) {
				MainFrame.this.setVisible(false);
				MainWindow.getInstance().setVisible(true);
			}
		});
				
		rubricationView.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				MainFrame.this.setVisible(false);
				RubricViewFrame.getInstance().setVisible(true);
			}
		});
		
		rubricAdd.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				MainFrame.this.setVisible(false);
				AddRubricFrame.getInstance().setVisible(true);
			}
		});
		
	}
	
	private void initializeButtonsListeners(){
		
		rubricationButton.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent event){
				if(rubricsTable.getSelectedRow() == -1){
					JOptionPane.showMessageDialog(null, "Оберіть документ!");
				}
				else if(rubricsTable.getSelectedRowCount() > 1){
					JOptionPane.showMessageDialog(null, "Оберіть тільки один документ!");
				}
				else{
					String temp = rubrication.headingCode(documents.get(rubricsTable.getSelectedRow()).getCode());
					if(temp.equals("Нема рубрики")){
						try {
							temp = rubrication.headingName(documents.get(rubricsTable.getSelectedRow()).getName());
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					
					if(temp.equals("Нема рубрики")){
						JOptionPane.showMessageDialog(null, "Не вдалося присвоїти рубрику");
						if(new File(PATH + "без рубрики").exists() == false) new File(PATH + "БЕЗ РУБРИКИ").mkdirs();
						fList.get(rubricsTable.getSelectedRow()).renameTo(new File(PATH + "БЕЗ РУБРИКИ\\" + fList.get(rubricsTable.getSelectedRow()).getName()));
						fList.remove(rubricsTable.getSelectedRow());
						documents.remove(rubricsTable.getSelectedRow());
						model.removeRow(rubricsTable.getSelectedRow());
						rubricsTable.setModel(model);
					}
					
					else{
					
						int confirmResult = JOptionPane.showConfirmDialog(null, "Присвоена рубрика: " + temp + ". Ви згодні?");
						
						String folder = rubrication.folderName(temp);
						
						if(confirmResult == JOptionPane.YES_OPTION){
							
							if(new File(PATH + folder).exists() == false) new File(PATH + folder).mkdirs();
							
							try {
								DBWorker.getInstance().updateDocument(documents.get(rubricsTable.getSelectedRow()));
								DBWorker.getInstance().insertRubricationFact(documents.get(rubricsTable.getSelectedRow()));
								DBWorker.getInstance().updateDocumentRubric(documents.get(rubricsTable.getSelectedRow()), temp);
							} catch (SQLException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
								JOptionPane.showMessageDialog(null, "Не вдалося занести дані до бази даних", "Помилка БД", JOptionPane.ERROR_MESSAGE);
							}
							fList.get(rubricsTable.getSelectedRow()).renameTo(new File(PATH + folder + "\\" + fList.get(rubricsTable.getSelectedRow()).getName()));
							fList.remove(rubricsTable.getSelectedRow());
							documents.remove(rubricsTable.getSelectedRow());
							model.removeRow(rubricsTable.getSelectedRow());
							rubricsTable.setModel(model);
							
						}
						
						if(confirmResult == JOptionPane.NO_OPTION){
							
							new RubricAssignmentFrame(documents.get(rubricsTable.getSelectedRow()));
							
						}
					}
					
				}
			}
		});
		
		updateButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				updateList();
			}
		});
		
	}
		
	private Vector<Vector<String>> fillDataArray(ArrayList<OrderDocument> documents){
		
		Vector<Vector<String>> result = new Vector<Vector<String>>();
		
		for(int i = 0; i < documents.size(); i++){
			Vector<String> temp = new Vector<String>();
			temp.add(documents.get(i).getCode());
			temp.add(documents.get(i).getDate());
			temp.add(documents.get(i).getName());
			result.add(temp);
		}
		
		return result;
	}
	
	public void updateList(){
		
		fillList();
		
		filesList = fillDataArray(documents);
		model = new DefaultTableModel(filesList, headers);
		
				
		model.fireTableDataChanged();
		rubricsTable.setModel(model);
		
		TableColumn column = null;
		for (int i = 0; i < 3; i++) {
		    column = rubricsTable.getColumnModel().getColumn(i);
		    if (i == 2) {
		        column.setPreferredWidth(200); //third column is bigger
		    } else {
		        column.setPreferredWidth(50);
		    }
		}
		
		MainFrame.this.repaint();
		
	}
	
	
	public void fillList(){
		fList = null;
		documents = null;
		model = null;
		File []temp = F.listFiles();
		fList = new ArrayList<File>();
		documents = new ArrayList<OrderDocument>();
		
		if (temp == null) { return;}
		
		for(int i = 0; i < temp.length; i++){
		     if(temp[i].isFile()){
		    	 fList.add(temp[i]);
		     }
		}
		
		for(int i = 0; i < fList.size(); i++){
		     if(fList.get(i).isFile()){
		    	 documents.add(new OrderDocument(fList.get(i).getName()));
		     }
		}
	
		try {
			Check.checkAndInsert(documents);
		} catch (SQLException | ParseException e) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(null, "Не вдалося занести інформацію у БД!");
			System.out.println(e.getMessage());
			
		}
	
	}
	
	public static ArrayList<File> getFilesList(){ return fList;}
	public static DefaultTableModel getModel() { return model;} 
	public static JTable getTable(){ return rubricsTable;}
	public static ArrayList<OrderDocument> getDocuments() {return documents;}
	public Rubrication getRubrication() {return rubrication;}
	public String getPath() {return PATH;}
}
