package gui;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class AuthorizationFrame extends JFrame{
	
	private static AuthorizationFrame instance;
	
	public static AuthorizationFrame getInstance(){
		if(instance == null){
			instance = new AuthorizationFrame();
		}
		return instance;
	}
	
	private JLabel loginLabel;
	private JLabel passLabel;
	
	private JTextField loginField;
	private JPasswordField passField;
	
	private JButton okButton;
	
	public AuthorizationFrame() {	
		
		setBounds(100, 100, 300, 300);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		setResizable(false);
		setLayout(null);
		
		initialize();
		initializeListeners();
		repaint();
	}
	
	private void initialize() {
		
		loginLabel = new JLabel("Логин");
		loginLabel.setBounds(10, 20, 50, 50);
		add(loginLabel);
		
		passLabel = new JLabel("Пароль");
		passLabel.setBounds(10, 70, 50, 50);
		add(passLabel);
		
		loginField = new JTextField();
		loginField.setBounds(60, 30, 200, 30);
		add(loginField);
		
		passField = new JPasswordField();
		passField.setBounds(60, 80, 200, 30);
		add(passField);
		
		okButton = new JButton("Вход");
		okButton.setBounds(100, 200, 100, 30);
		add(okButton);
		
	}
	
	private void initializeListeners(){
		okButton.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent event){
				
				if(!passField.getText().equals("123ius")
						&& !loginField.getText().equals("manager")) JOptionPane.showMessageDialog(null, "Неверный логин и пароль");
				 
				else if(!loginField.getText().equals("manager")) JOptionPane.showMessageDialog(null, "Неверный логин");
				
				else if(!passField.getText().equals("123ius")) JOptionPane.showMessageDialog(null, "Неверный пароль");
				
				
				
				else if(loginField.getText().equals("manager") 
							&& passField.getText().equals("123ius")){
					AuthorizationFrame.this.setVisible(false);
						MainFrame.getInstance().setVisible(true);;
					}
				}
			});
		}

}
