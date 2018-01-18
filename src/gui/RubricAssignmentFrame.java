package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JRootPane;

import db.DBWorker;
import logic.Converter;
import logic.OrderDocument;
import logic.Rubric;

public class RubricAssignmentFrame extends JFrame {

		
	private JLabel selectLabel = new JLabel("Оберіть рубрику: ");
	private JComboBox<String> rubrics = new JComboBox<String>();
	private JButton confirmButton = new JButton("Підтвердити");
	Rubric[] rubric = null;
	
	public RubricAssignmentFrame(OrderDocument doc){
		
		setBounds(100, 100, 400, 200);
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setVisible(true);
		setResizable(false);
		getContentPane().setLayout(null);
		setTitle("Вибір рубрики вручну");
		
		initialize(doc);
		
		repaint();
		
	}
	
	private void initialize(OrderDocument doc){
		initializeComponents();
		initializeListeners(doc);
	}
	
	private void initializeComponents(){
		selectLabel.setBounds(10, 10, 100, 30);
		add(selectLabel);
		
		try {
			rubric = Converter.convertDBData(DBWorker.getInstance().getRurics());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		for(int i = 0; i < rubric.length; i++){
			rubrics.addItem(rubric[i].getName());
		}
		
		rubrics.setBounds(120, 10, 200, 30);
		add(rubrics);
		
		confirmButton.setBounds(80, 80, 150, 30);
		add(confirmButton);
	}
	
	private void initializeListeners(OrderDocument doc){
		confirmButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				doc.setRubricName((String) rubrics.getSelectedItem());
				String folder = doc.getRubricName();
				if(new File(MainFrame.getInstance().getPath() + folder).exists() == false) new File(MainFrame.getInstance().getPath() + folder).mkdirs();
				
				try {
					DBWorker.getInstance().updateDocument(doc);
					DBWorker.getInstance().insertRubricationFact(doc);
					DBWorker.getInstance().updateDocumentRubric(MainFrame.getDocuments().get(MainFrame.getTable().getSelectedRow()), (String) rubrics.getSelectedItem());
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
					JOptionPane.showMessageDialog(null, "Не вдалося занести дані до бази даних", "Помилка БД", JOptionPane.ERROR_MESSAGE);
				}
				
				MainFrame.getFilesList().get(MainFrame.getTable().getSelectedRow()).renameTo(new File(MainFrame.getInstance().getPath() + folder + "\\" + MainFrame.getFilesList().get(MainFrame.getTable().getSelectedRow()).getName()));
				MainFrame.getDocuments().remove(MainFrame.getTable().getSelectedRow());
				MainFrame.getModel().removeRow(MainFrame.getTable().getSelectedRow());
				MainFrame.getTable().setModel(MainFrame.getModel());
				RubricAssignmentFrame.this.setVisible(false);
			}
		});
	}
	
}
