package restaurant.client.ui.form.order;

import java.time.LocalDate;
import java.util.List;
import restaurant.client.ui.form.order.controller.OrderSearchFormController;
import restaurant.common.domain.Employee;
import restaurant.common.domain.Order;
import restaurant.common.domain.OrderItem;
import restaurant.common.domain.Table;

/**
 *
 * @author Natasa Todorov Markovic
 */
public class OrderSearchForm extends javax.swing.JDialog {

    
    private List<Order> orders;
    private Table table;
    private Employee employee;
    private LocalDate date;
    private Boolean statusReady;
    private Boolean statusPaied;

    /**
     * Creates new form OrderSearchForm
     */
    public OrderSearchForm(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        setLocationRelativeTo(null);
        prepareView();
        setTableModel(orders);

        
        populateComboEmployee();
        populateComboTable();

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        groupPaied = new javax.swing.ButtonGroup();
        groupReady = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        listOrderItems = new javax.swing.JList<>();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblOrders = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        comboEmployee = new javax.swing.JComboBox<>();
        btnDateSelect = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        dateChooser = new com.toedter.calendar.JDateChooser();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        comboTable = new javax.swing.JComboBox<>();
        btnSelectEmployee = new javax.swing.JButton();
        btnSelectTable = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        rbtnPaied = new javax.swing.JRadioButton();
        rbtnNonPaied = new javax.swing.JRadioButton();
        rbtnReady = new javax.swing.JRadioButton();
        rbtnNonReady = new javax.swing.JRadioButton();
        jSeparator2 = new javax.swing.JSeparator();
        btnPaiedSelect = new javax.swing.JButton();
        btnPaiedClear = new javax.swing.JButton();
        btnReadySelect = new javax.swing.JButton();
        btnReadyClear = new javax.swing.JButton();
        jSeparator3 = new javax.swing.JSeparator();
        btnExit = new javax.swing.JButton();
        btnClearAll = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Stavke porudzbine"));

        listOrderItems.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jScrollPane2.setViewportView(listOrderItems);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 470, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 374, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Porudzbine"));

        tblOrders.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        tblOrders.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tblOrders.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblOrdersMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblOrders);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 648, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 374, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Filtriraj"));

        comboEmployee.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        btnDateSelect.setText("Izaberi");
        btnDateSelect.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDateSelectActionPerformed(evt);
            }
        });

        jLabel1.setText("Datum:");

        dateChooser.setBorder(javax.swing.BorderFactory.createTitledBorder("DD.MM.YYYY."));

        jLabel2.setText("Zaposleni:");

        jLabel3.setText("Sto:");

        comboTable.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        btnSelectEmployee.setText("Izaberi");
        btnSelectEmployee.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSelectEmployeeActionPerformed(evt);
            }
        });

        btnSelectTable.setText("Izaberi");
        btnSelectTable.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSelectTableActionPerformed(evt);
            }
        });

        jSeparator1.setOrientation(javax.swing.SwingConstants.VERTICAL);

        groupPaied.add(rbtnPaied);
        rbtnPaied.setText("Placena");

        groupPaied.add(rbtnNonPaied);
        rbtnNonPaied.setText("Neplacena");

        groupReady.add(rbtnReady);
        rbtnReady.setText("Spremna");

        groupReady.add(rbtnNonReady);
        rbtnNonReady.setText("Nije spremna");

        btnPaiedSelect.setText("Izaberi");
        btnPaiedSelect.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPaiedSelectActionPerformed(evt);
            }
        });

        btnPaiedClear.setText("Ponisti");
        btnPaiedClear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPaiedClearActionPerformed(evt);
            }
        });

        btnReadySelect.setText("Izaberi");
        btnReadySelect.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnReadySelectActionPerformed(evt);
            }
        });

        btnReadyClear.setText("Ponisti");
        btnReadyClear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnReadyClearActionPerformed(evt);
            }
        });

        jSeparator3.setOrientation(javax.swing.SwingConstants.VERTICAL);

        btnExit.setText("Izadji");
        btnExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExitActionPerformed(evt);
            }
        });

        btnClearAll.setText("Ponisti sve filtere");
        btnClearAll.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClearAllActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jLabel3))
                .addGap(48, 48, 48)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(dateChooser, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 25, Short.MAX_VALUE)
                        .addComponent(btnDateSelect))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(comboTable, javax.swing.GroupLayout.Alignment.LEADING, 0, 197, Short.MAX_VALUE)
                            .addComponent(comboEmployee, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnSelectEmployee, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(btnSelectTable, javax.swing.GroupLayout.Alignment.TRAILING))))
                .addGap(44, 44, 44)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(jSeparator2, javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel3Layout.createSequentialGroup()
                            .addComponent(rbtnPaied)
                            .addGap(18, 18, 18)
                            .addComponent(rbtnNonPaied)
                            .addGap(31, 31, 31)
                            .addComponent(btnPaiedSelect)
                            .addGap(18, 18, 18)
                            .addComponent(btnPaiedClear)))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(rbtnReady)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(rbtnNonReady)
                        .addGap(18, 18, 18)
                        .addComponent(btnReadySelect)
                        .addGap(18, 18, 18)
                        .addComponent(btnReadyClear)))
                .addGap(18, 18, 18)
                .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 12, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnClearAll)
                .addGap(18, 18, 18)
                .addComponent(btnExit)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnExit)
                            .addComponent(btnClearAll)))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel3Layout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel1)
                            .addComponent(dateChooser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnDateSelect))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(comboEmployee, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2)
                            .addComponent(btnSelectEmployee))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(comboTable, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnSelectTable)))
                    .addComponent(jSeparator1, javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel3Layout.createSequentialGroup()
                        .addGap(35, 35, 35)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(rbtnPaied)
                            .addComponent(rbtnNonPaied)
                            .addComponent(btnPaiedSelect)
                            .addComponent(btnPaiedClear))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(rbtnReady)
                            .addComponent(rbtnNonReady)
                            .addComponent(btnReadySelect)
                            .addComponent(btnReadyClear)))
                    .addComponent(jSeparator3, javax.swing.GroupLayout.Alignment.LEADING))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(9, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tblOrdersMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblOrdersMouseClicked
       OrderSearchFormController.tblOrdersMouseClicked(tblOrders, listOrderItems);
    }//GEN-LAST:event_tblOrdersMouseClicked

    private void btnSelectTableActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSelectTableActionPerformed
             OrderSearchFormController.btnSelectTableActionPerformed(listOrderItems, this, comboTable, tblOrders);

    }//GEN-LAST:event_btnSelectTableActionPerformed

    private void btnExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExitActionPerformed
        dispose();
    }//GEN-LAST:event_btnExitActionPerformed

    private void btnSelectEmployeeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSelectEmployeeActionPerformed
            OrderSearchFormController.btnSelectEmployeeActionPerformed(listOrderItems, this, comboEmployee, tblOrders);

    }//GEN-LAST:event_btnSelectEmployeeActionPerformed

    private void btnPaiedSelectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPaiedSelectActionPerformed
          OrderSearchFormController.btnPaiedSelectActionPerformed(listOrderItems, this, rbtnPaied, tblOrders, rbtnNonPaied);

    }//GEN-LAST:event_btnPaiedSelectActionPerformed

    private void btnPaiedClearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPaiedClearActionPerformed
        OrderSearchFormController.btnPaiedClearActionPerformed(listOrderItems, this, rbtnPaied, rbtnNonPaied, groupPaied, tblOrders);

    }//GEN-LAST:event_btnPaiedClearActionPerformed

    private void btnReadySelectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReadySelectActionPerformed
        OrderSearchFormController.btnReadySelectActionPerformed(listOrderItems, rbtnReady, this, tblOrders, rbtnNonReady);

    }//GEN-LAST:event_btnReadySelectActionPerformed

    private void btnReadyClearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReadyClearActionPerformed
        OrderSearchFormController.btnReadyClearActionPerformed(listOrderItems, this, groupReady, tblOrders);

    }//GEN-LAST:event_btnReadyClearActionPerformed

    private void btnClearAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClearAllActionPerformed
       OrderSearchFormController.btnClearAllActionPerformed(listOrderItems, tblOrders, this, comboEmployee, comboTable, groupPaied, groupReady, dateChooser);

    }//GEN-LAST:event_btnClearAllActionPerformed

    private void btnDateSelectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDateSelectActionPerformed
        OrderSearchFormController.btnDateSelectActionPerformed(listOrderItems, orders, tblOrders, this, dateChooser, rbtnNonPaied, rbtnPaied, comboEmployee);

    }//GEN-LAST:event_btnDateSelectActionPerformed

    /**
     * @param args the command line arguments
     */

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnClearAll;
    private javax.swing.JButton btnDateSelect;
    private javax.swing.JButton btnExit;
    private javax.swing.JButton btnPaiedClear;
    private javax.swing.JButton btnPaiedSelect;
    private javax.swing.JButton btnReadyClear;
    private javax.swing.JButton btnReadySelect;
    private javax.swing.JButton btnSelectEmployee;
    private javax.swing.JButton btnSelectTable;
    private javax.swing.JComboBox<Object> comboEmployee;
    private javax.swing.JComboBox<Object> comboTable;
    private com.toedter.calendar.JDateChooser dateChooser;
    private javax.swing.ButtonGroup groupPaied;
    private javax.swing.ButtonGroup groupReady;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JList<String> listOrderItems;
    private javax.swing.JRadioButton rbtnNonPaied;
    private javax.swing.JRadioButton rbtnNonReady;
    private javax.swing.JRadioButton rbtnPaied;
    private javax.swing.JRadioButton rbtnReady;
    private javax.swing.JTable tblOrders;
    // End of variables declaration//GEN-END:variables



    private void setListModel(List<OrderItem> orderItems) {
              OrderSearchFormController.setListModel(orderItems, listOrderItems);

    }

    private void populateComboEmployee() {
       OrderSearchFormController.populateComboEmployee(comboEmployee);

    }

    private void populateComboTable() {
        OrderSearchFormController.populateComboTable(comboTable);

    }

//    private void setEmployee() {
//        OrderSearchFormController.setEmployee(comboEmployee, this);
//  
//    }
//
//    private void setTable() {
//        OrderSearchFormController.setTable(comboTable, this);
//
//    }

//    private void setDate() {
//        OrderSearchFormController.setDate(dateChooser, this);
//
//    }
//
//    private void setReadyStatus() {
//       OrderSearchFormController.setReadyStatus(this, rbtnReady, rbtnNonReady);
//
//    }
//
//    private void setPaiedStatus() {
//        OrderSearchFormController.setPaiedStatus(rbtnPaied, this, rbtnNonPaied);
//
//    }

    private void prepareView() {
        OrderSearchFormController.prepareView(this);
    }

    

    private void setTableModel(List<Order> orders) {
         OrderSearchFormController.setTableModel(orders, tblOrders);
    }

    public Table getTable() {
        return table;
    }

    public void setTable(Table table) {
        this.table = table;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Boolean getStatusReady() {
        return statusReady;
    }

    public void setStatusReady(Boolean statusReady) {
        this.statusReady = statusReady;
    }

    public Boolean getStatusPaied() {
        return statusPaied;
    }

    public void setStatusPaied(Boolean statusPaied) {
        this.statusPaied = statusPaied;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

}
