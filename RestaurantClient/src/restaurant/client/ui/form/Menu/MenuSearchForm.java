package restaurant.client.ui.form.Menu;

import restaurant.client.ui.form.Menu.controller.MenuSearchFormController;
import restaurant.common.domain.Menu;

/**
 *
 * @author Natasa Todorov Markovic
 */
public class MenuSearchForm extends javax.swing.JDialog {

    private Menu menu;

    /**
     * Creates new form MenuSearchForm
     */
    public MenuSearchForm(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        setLocationRelativeTo(null);

        populateComboMenu();
        populateComboCategory();
        setTableModel();
        prepareView();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnGroupMenuIsActive = new javax.swing.ButtonGroup();
        pnlMenuSearch = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblMenuItems = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        comboCategory = new javax.swing.JComboBox<>();
        btnSelectCategory = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        comboMenu = new javax.swing.JComboBox<>();
        btnSelectMenu = new javax.swing.JButton();
        btnDetails = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        rbtnActive = new javax.swing.JRadioButton();
        rbtnNonActive = new javax.swing.JRadioButton();
        txtName = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowActivated(java.awt.event.WindowEvent evt) {
                formWindowActivated(evt);
            }
        });

        pnlMenuSearch.setBorder(javax.swing.BorderFactory.createTitledBorder("Jelovnik"));

        tblMenuItems.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(tblMenuItems);

        jLabel1.setText("Odaberi kategoriju:");

        comboCategory.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        btnSelectCategory.setText("Izaberi");
        btnSelectCategory.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSelectCategoryActionPerformed(evt);
            }
        });

        jLabel2.setText("Odaberi jelovnik:");

        comboMenu.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        comboMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboMenuActionPerformed(evt);
            }
        });

        btnSelectMenu.setText("Izaberi");
        btnSelectMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSelectMenuActionPerformed(evt);
            }
        });

        btnDetails.setText("Izmeni izabrani jelovnik");
        btnDetails.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDetailsActionPerformed(evt);
            }
        });

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Izabrani jelovnik"));

        btnGroupMenuIsActive.add(rbtnActive);
        rbtnActive.setText("Aktivan");

        btnGroupMenuIsActive.add(rbtnNonActive);
        rbtnNonActive.setText("Nije aktivan");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(rbtnActive)
                .addGap(18, 18, 18)
                .addComponent(rbtnNonActive)
                .addContainerGap(175, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rbtnActive)
                    .addComponent(rbtnNonActive))
                .addContainerGap(22, Short.MAX_VALUE))
        );

        txtName.setEditable(false);
        txtName.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N

        javax.swing.GroupLayout pnlMenuSearchLayout = new javax.swing.GroupLayout(pnlMenuSearch);
        pnlMenuSearch.setLayout(pnlMenuSearchLayout);
        pnlMenuSearchLayout.setHorizontalGroup(
            pnlMenuSearchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlMenuSearchLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlMenuSearchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 941, Short.MAX_VALUE)
                    .addGroup(pnlMenuSearchLayout.createSequentialGroup()
                        .addGroup(pnlMenuSearchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnlMenuSearchLayout.createSequentialGroup()
                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(comboCategory, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnSelectCategory))
                            .addGroup(pnlMenuSearchLayout.createSequentialGroup()
                                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(comboMenu, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnSelectMenu)))
                        .addGap(18, 18, 18)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlMenuSearchLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnDetails))
                    .addGroup(pnlMenuSearchLayout.createSequentialGroup()
                        .addComponent(txtName, javax.swing.GroupLayout.PREFERRED_SIZE, 270, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        pnlMenuSearchLayout.setVerticalGroup(
            pnlMenuSearchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlMenuSearchLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(txtName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 265, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnDetails)
                .addGroup(pnlMenuSearchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlMenuSearchLayout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(pnlMenuSearchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addGroup(pnlMenuSearchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(comboMenu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(btnSelectMenu)))
                        .addGap(18, 18, 18)
                        .addGroup(pnlMenuSearchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnlMenuSearchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel1)
                                .addComponent(comboCategory, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(btnSelectCategory)))
                    .addGroup(pnlMenuSearchLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(40, 40, 40))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pnlMenuSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(9, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(pnlMenuSearch, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnSelectMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSelectMenuActionPerformed
        MenuSearchFormController.btnSelectMenuActionPerformed(this, btnGroupMenuIsActive, comboMenu, txtName, tblMenuItems, rbtnActive, rbtnNonActive);

    }//GEN-LAST:event_btnSelectMenuActionPerformed

    private void btnSelectCategoryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSelectCategoryActionPerformed

        MenuSearchFormController.btnSelectCategoryActionPerformed(comboMenu, comboCategory, tblMenuItems);

    }//GEN-LAST:event_btnSelectCategoryActionPerformed

    private void btnDetailsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDetailsActionPerformed

        MenuSearchFormController.btnDetailsActionPerformed(comboMenu);

    }//GEN-LAST:event_btnDetailsActionPerformed

    private void formWindowActivated(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowActivated
        populateComboMenu();
        setTableModel();

        prepareView();
    }//GEN-LAST:event_formWindowActivated

    private void comboMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboMenuActionPerformed
        comboCategory.setSelectedIndex(0);
    }//GEN-LAST:event_comboMenuActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnDetails;
    private javax.swing.ButtonGroup btnGroupMenuIsActive;
    private javax.swing.JButton btnSelectCategory;
    private javax.swing.JButton btnSelectMenu;
    private javax.swing.JComboBox<Object> comboCategory;
    private javax.swing.JComboBox<Object> comboMenu;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPanel pnlMenuSearch;
    private javax.swing.JRadioButton rbtnActive;
    private javax.swing.JRadioButton rbtnNonActive;
    private javax.swing.JTable tblMenuItems;
    private javax.swing.JTextField txtName;
    // End of variables declaration//GEN-END:variables

    private void populateComboMenu() {
        MenuSearchFormController.populateComboMenu(comboMenu);

    }

    private void populateComboCategory() {
        MenuSearchFormController.populateComboCategory(comboCategory);

    }

    private void setTableModel() {
        MenuSearchFormController.setTableModel(comboMenu, tblMenuItems);

    }

    private void prepareView() {
        MenuSearchFormController.prepareView(this, btnDetails, comboMenu, txtName, rbtnActive, rbtnNonActive);

    }

    private void setIsSelected() {
        MenuSearchFormController.setIsSelected(this, rbtnActive, rbtnNonActive);

    }

    public Menu getMenu() {
        return menu;
    }

    public void setMenu(Menu menu) {
        this.menu = menu;
    }

}