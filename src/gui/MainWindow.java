/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

import db.DBWorker;

/**
 *
 * @author Qwaser
 */
public class MainWindow extends javax.swing.JFrame {
	
	
private static MainWindow instance;
	
	public static synchronized MainWindow getInstance(){
		if(instance == null){
			instance = new MainWindow();
		}
		return instance;
	}
	

    /**
     * Creates new form MainWindow
     */
    private List<Integer> docId = new ArrayList<Integer>(); //Создаём список int, который будет содержать docId

    private boolean validateData() {
        String numberTask = numberTaskField.getText();

        boolean valid = true;

        if (numberTask.isEmpty()) {
            JOptionPane.showMessageDialog(null,
                    "Поле задач. Заповнiть його.",
                    "Помилка",
                    JOptionPane.ERROR_MESSAGE);
            valid = false;
        }
        if (Integer.parseInt(numberTask) >= 7) {
            JOptionPane.showMessageDialog(null,
                    "Введiть меншу кiлькiсть полiв.",
                    "Помилка",
                    JOptionPane.ERROR_MESSAGE);
            valid = false;
        }

        return valid;
    }

    public void setComboBox() {

        try {
        	DBWorker con = DBWorker.getInstance();

            String query = "SELECT id_order,name_order FROM order_document where rubricationStatus = 1 and outStatus = 0;";
            Statement stmt = con.getConnection().createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                String s = rs.getString(2);
                selectDocComboBox.addItem(s);
                docId.add(rs.getInt(1));

            }
            stmt.close();
            

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public MainWindow() {
    	setBounds(100, 100, 600, 400);
        initComponents();

        setComboBox();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        addTasksButton = new javax.swing.JButton();
        cancel = new javax.swing.JButton();
        
        jLabel1 = new javax.swing.JLabel();
        selectDocComboBox = new javax.swing.JComboBox<String>();
        jLabel2 = new javax.swing.JLabel();
        numberTaskField = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Вікно вибору розпорядчого документа");

        addTasksButton.setText("Додати задачi");
       // addTasksButton.setBounds(numberTaskField.getWidth() + 15, MainWindow.this.getY() - 70, 170, 30);
        addTasksButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addTasksButtonActionPerformed(evt);
            }
        });
       // MainWindow.this.add(addTasksButton);
        
        
        cancel.setText("Відміна");
        cancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                instance.setVisible(false);
                MainFrame.getInstance().setVisible(true);
            }
        });

        jLabel1.setText("Виберiть документ iз списку:");
//        jLabel1.setBounds(10, 50, 500, 30);
//        MainWindow.this.add(jLabel1);

        //selectDocComboBox.setBounds(10, 100, 500, 30);
        selectDocComboBox.setName(""); // NOI18N
        //MainWindow.this.add(selectDocComboBox);

        jLabel2.setText("Введiть кiлькiсть задач:");
//        jLabel2.setBounds(10, MainWindow.this.getY() - 70, 150, 30);
//        MainWindow.this.add(jLabel2);

        //numberTaskField.setBounds(jLabel2.getWidth() + 15, MainWindow.this.getY() - 70, 25, 30);  
        numberTaskField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                onlyDigit(evt);
            }
        });
        MainWindow.this.add(numberTaskField);
        MainWindow.this.repaint();

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(selectDocComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addGap(34, 34, 34)
                        .addComponent(jLabel1)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(249, 249, 249)
                        .addComponent(jLabel2)
                        .addGap(29, 29, 29)
                        .addComponent(numberTaskField, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 22, Short.MAX_VALUE)
                        .addComponent(addTasksButton)
                        .addGap(29, 29, 29)
                        .addComponent(cancel)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(63, 63, 63)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(selectDocComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 138, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(addTasksButton)
                    .addComponent(cancel)
                    .addComponent(jLabel2)
                    .addComponent(numberTaskField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(36, 36, 36))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents


    private void addTasksButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addTasksButtonActionPerformed

        Integer documentId = docId.get(selectDocComboBox.getSelectedIndex());
        String orderName = (String) selectDocComboBox.getSelectedItem();

        Integer numberTask = Integer.parseInt(numberTaskField.getText());            

        if (validateData()) {
            SetTask st = new SetTask(documentId, numberTask, orderName);

            st.setVisible(true);
            setVisible(false);
        }
    }//GEN-LAST:event_addTasksButtonActionPerformed

    private void onlyDigit(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_onlyDigit
        char enter = evt.getKeyChar();
        if(!(Character.isDigit(enter))){
            evt.consume();
        }
    }//GEN-LAST:event_onlyDigit

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addTasksButton;
    private javax.swing.JButton cancel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JTextField numberTaskField;
    private javax.swing.JComboBox<String> selectDocComboBox;
    // End of variables declaration//GEN-END:variables
}
