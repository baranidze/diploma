/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import db.DBWorker;

public class SetTask extends javax.swing.JFrame {

    private final Integer documentId;
    private final Integer numberTask;
    private JTextArea[] taskField;
    private JLabel[] label;
    private JButton cancel = new JButton ("Відміна");
    private JScrollPane scroll = new JScrollPane();

    public SetTask(Integer documentId, Integer numberTask, String orderName) {
        initComponents();
        this.documentId = documentId;
        this.numberTask = numberTask;
        orderNameLabel.setText("Назва документа: " + orderName);
        SetTask.this.setBounds(100, 100, 700, 200 + 80 * numberTask);
        
        orderNameLabel.setBounds(10, 15, 650, 30);
        scroll.setBounds(20, 75, 650, 250);
        
        addButton.setBounds(SetTask.this.getWidth()/2 + 10, SetTask.this.getHeight() - 100, 130, 30);
        cancel.setBounds(addButton.getX() - addButton.getWidth() - 20, addButton.getY(), addButton.getWidth(), addButton.getHeight());
        
        add(addButton);
        add(cancel);
        add(orderNameLabel);
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        generateTextArea(numberTask);
    }

    public void generateTextField(Integer numberTask) {
        Container c = getContentPane();

        c.setLayout(new BoxLayout(c, BoxLayout.Y_AXIS));
        //c.setLayout(new FlowLayout());
        JTextField[] taskField = new JTextField[numberTask]; // FIELDS is an int, representing the max number of JTextFields

        int i = 0;
        for (i = 0; i < numberTask; i++) {
            taskField[i] = new JTextField();
            taskField[i].setColumns(10);
            c.add(taskField[i]);
        }
    }

    public void generateTextArea(Integer numberTask) {

        Container c = getContentPane();
        c.setLayout(null);

        //c.setLayout(new BoxLayout(c, BoxLayout.Y_AXIS));
        this.taskField = new JTextArea[numberTask];
        this.label = new JLabel[numberTask];

        int i = 0;
        for (i = 0; i < numberTask; i++) {
            int numberLabel = i + 1;
            label[i] = new JLabel("№ завдання" + numberLabel);
            taskField[i] = new JTextArea();
            //taskField[i] = new JTextArea(7,20);

            int z = (i + 1) * 25;

            taskField[i].setBounds(130, z * 3, 500, 50);
            
            label[i].setBounds(20, z * 3 + taskField[i].getHeight()/2 - 10, 100, 10);

            taskField[i].setLineWrap (true);
            taskField[i].setWrapStyleWord(true);
            c.add(label[i]);
            c.add(taskField[i]);
           // add(new JScrollPane(taskField[i]));
            SetTask.this.repaint();
        }
        

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        addButton = new javax.swing.JButton();
        orderNameLabel = new javax.swing.JLabel();
        orderNameLabel.setFont(new Font("Verdana", Font.PLAIN, 16));

        jLabel1.setText("jLabel1");

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setBackground(new java.awt.Color(255, 255, 255));
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        addButton.setText("Підтвердити");
        addButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addButtonActionPerformed(evt);
            }
        });
        
        cancel.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				SetTask.this.setVisible(false);
				MainWindow.getInstance().setVisible(true);
			}
		});

//        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
//        getContentPane().setLayout(layout);
//        layout.setHorizontalGroup(
//            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
//            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
//                .addContainerGap()
//                .addComponent(orderNameLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 491, javax.swing.GroupLayout.PREFERRED_SIZE)
//                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 28, Short.MAX_VALUE)
//                .addComponent(addButton, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
//                .addContainerGap())
//        );
//        layout.setVerticalGroup(
//            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
//            .addGroup(layout.createSequentialGroup()
//                .addContainerGap()
//                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
//                    .addComponent(addButton, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
//                    .addComponent(orderNameLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
//                .addContainerGap(291, Short.MAX_VALUE))
//        );
//
//        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void addButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addButtonActionPerformed
        for (int i = 0; i < numberTask; i++) {
            String value = this.taskField[i].getText();
            if (value == null || value.length() < 1) {
                JOptionPane.showMessageDialog(null, "Заповнiть усi поля!!!");
                break;
            } else {
                try {

                	DBWorker con = DBWorker.getInstance();
                    Statement s = con.getConnection().createStatement();
                    String sql = "insert into orderdocument.task" //название БД - diploma, task- таблицы
                            + " set  name_task=\"" + value + "\"," //Поле таблицы, в котором будет размещаться сама задача
                            + "id_order = \"" + documentId + "\"" + ",id_status = 1, id_appointment = 1;"; //Что бы одному доку не назначили тучу задач

                    s.execute(sql);
                    
                    sql = "update order_document set outStatus = 1 where id_order = " + documentId + ";";
                    
                    s.execute(sql);

                    s.close();
                   

                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, e);
                }
                closeForm();
            }


    }//GEN-LAST:event_addButtonActionPerformed

    }

    private void closeForm() {
        this.setVisible(false);
        MainWindow documentForm = new MainWindow(); // Открывает предыдущую форму
        documentForm.setVisible(true);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addButton;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel orderNameLabel;
    // End of variables declaration//GEN-END:variables
}
