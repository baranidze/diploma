package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import db.DBWorker;
import logic.Converter;
import logic.Rubric;
import logic.RubricComporator;

public class AddRubricFrame extends JFrame{
	
	private JLabel nameLabel = new JLabel("Введіть назву рубрики:");
	private JLabel wordsLabel = new JLabel("Введіть ключові слова рубрики(через кому):");
	private JTextField nameField = new JTextField();
	private JTextField wordsField = new JTextField();
	private JCheckBox logicalSequence = new JCheckBox("Чи є слова ключові слова логічно зв'язаними?");
	private JButton confirm = new JButton("Підтвердити");
	private JButton cancel = new JButton("Відміна");

	private static AddRubricFrame instance;

	public static synchronized AddRubricFrame getInstance(){
		if(instance == null){
			instance = new AddRubricFrame();
		}
		return instance;
	}
	
	private AddRubricFrame(){
		
		setBounds(100, 100, 500, 250);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setVisible(true);
		setResizable(false);
		setLayout(null);
		
		initialize();
		
		repaint();
	}
	
	private void initialize(){
		nameLabel.setBounds(10,10,150,30);
		add(nameLabel);
		
		nameField.setBounds(160, 10, 150, 30);
		nameField.setText("");
		add(nameField);
		
		wordsLabel.setBounds(10,60,270,30);
		add(wordsLabel);
		
		wordsField.setBounds(280, 60, 150, 30);
		wordsField.setText("");
		add(wordsField);
		
		logicalSequence.setBounds(10, 100, 350, 30);
		logicalSequence.setSelected(false);
		add(logicalSequence);
		
		confirm.setBounds(120, 140, 150, 30);
		add(confirm);
		
		cancel.setBounds(300, 140, 150, 30);
		add(cancel);
		
		confirm.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				Rubric temp;
				if(nameField.getText().equals("")){
					JOptionPane.showMessageDialog(null, "Потрібно ввести назву рубрики!");
				}
				else {
					if(logicalSequence.isSelected()) {
						temp = new Rubric(nameField.getText(), wordsField.getText(), 1);
					}
					else {
						temp = new Rubric(nameField.getText(), wordsField.getText(), 0);
					}
					
					Rubric[] rubrics = null;
					
					try {
						rubrics = Converter.convertDBData(DBWorker.getInstance().getRurics());
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
					int insert = 1;
					RubricComporator comporator = new RubricComporator();
					
					for(int i = 0; i < rubrics.length; i++){
						
						insert *= comporator.compare(rubrics[i], temp);
						
					}
					
					if(insert == 0) {
						JOptionPane.showMessageDialog(null, "Така рубрика вже існує!");
					}
					else {
						try {
							DBWorker.getInstance().insertRubric(temp);
						} catch (SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						
						RubricViewFrame.getInstance().setVisible(true);
						AddRubricFrame.this.setVisible(false);
						
					}
					
				}
			}
		});
		
		cancel.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
				MainFrame.getInstance().setVisible(true);
				AddRubricFrame.this.setVisible(false);
				
			}
			
		});
	}
	

}
