package appPack;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.Timer;
import javax.swing.table.DefaultTableModel;

public class DashBoard extends javax.swing.JFrame {

    DB db = new DB();
    ArrayList<Integer> arrayListCategory = new ArrayList<>();
    ArrayList<Integer> arrayListCustomer = new ArrayList<>();
    
    static String customerID = "";
    static String customerName = "";
    static String customerTelephone = "";
    static String customerAddress = "";

    static String categoryID = "";
    static String categoryName = "";
    static String categoryComment = "";
    
    static String productID = "";
    static String productName = "";
    static int productCategoryID = -1; 
    static String productPurchase = "" ;
    static String productSale = "";
    static String productStock = "";
    static String productComment = "";
    static String productNewStock = "";
    
    static String orderID = "";
    static String orderProductName = "";
    static int orderCategoryID = -1;
    static int orderCustomerID = -1; 
    static String orderPurchase = "" ;
    static String orderSale = "";
    static String orderAmount = "";
    static String orderStock = "";
    static String orderDate = "";
    
    static String searchName = "";
    
    Date d = new Date();
    SimpleDateFormat s = new SimpleDateFormat("dd-MM-yyyy");
    public DashBoard() {
        initComponents();
        String adminName = "Hoş Geldin " + Login.name;
        lblUserName.setText(adminName);
        lblDate.setText(s.format(d));
        showTime();
        tableCustomerResult();
        tableCategoryResult();
        tableProductResult();
        tableReportResult();
        comboCategoryResult();
        comboCustomerResult();
    }
        void showTime(){
       new Timer(0, new ActionListener(){
       @Override
       public void actionPerformed(ActionEvent e){
           Date d = new Date();
        SimpleDateFormat s = new SimpleDateFormat("hh:mm");
        lblTime.setText(s.format(d));
       }
       }).start();
    }
    public void tableCustomerResult(){
        DefaultTableModel dtmCustomer = new DefaultTableModel();
        dtmCustomer.addColumn("ID");
        dtmCustomer.addColumn("Müşteri");
        dtmCustomer.addColumn("TELEFON");
        dtmCustomer.addColumn("ADRES");
        try {
            String query = "SELECT * FROM `customers`";
            ResultSet rs = db.baglan().executeQuery(query);
            while(rs.next()){
                dtmCustomer.addRow(new String[]{rs.getString("customerID"),rs.getString("customerName"),
                rs.getString("customerTelephone"),rs.getString("customerAddress")});
            }
            tblCustomer.setModel(dtmCustomer);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(rootPane, "Müşteri Listesi Veritabanından Alınamadı!");
        }    
    }
    public void tableCategoryResult(){
        DefaultTableModel dtmCategory = new DefaultTableModel();
        dtmCategory.addColumn("ID");
        dtmCategory.addColumn("KATEGORİ");
        dtmCategory.addColumn("AÇIKLAMA");
        try {
            String query = "SELECT * FROM `category`";
            ResultSet rs = db.baglan().executeQuery(query);
            while(rs.next()){
                dtmCategory.addRow(new String[]{rs.getString("categoryID"), rs.getString("categoryName"),rs.getString("categoryComment"),});
            }
            tblCategory.setModel(dtmCategory);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(rootPane, "Kategori Listesi Veritabanından Alınamadı!");
        }    
    }
    public void tableProductResult(){
        DefaultTableModel dtmProduct = new DefaultTableModel();
        dtmProduct.addColumn("ID");
        dtmProduct.addColumn("ÜRÜN ADI");
        dtmProduct.addColumn("KATEGORİ");
        dtmProduct.addColumn("ALIŞ FİYATI");
        dtmProduct.addColumn("SATIŞ FİYATI");
        dtmProduct.addColumn("STCOK");
        dtmProduct.addColumn("AÇIKLAMA");
        try {
            String query = "SELECT * FROM product INNER JOIN category ON product.productCategoryID = category.categoryID";
            ResultSet rs = db.baglan().executeQuery(query);
            while(rs.next()){
                dtmProduct.addRow(new String[]{rs.getString("productID"),rs.getString("productName"),
                rs.getString("CategoryName"),rs.getString("productPurchase"),rs.getString("productSale"),rs.getString("productStock"),rs.getString("productComment")});
            }
            tblProduct.setModel(dtmProduct);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(rootPane, "Ürün Listesi Veritabanından Alınamadı!");
        }    
    }
    public void tableSoldProductResult(){
        DefaultTableModel dtmSoldProduct = new DefaultTableModel();
        dtmSoldProduct.addColumn("ID");
        dtmSoldProduct.addColumn("ÜRÜN ADI");
        dtmSoldProduct.addColumn("ALIŞ FİYATI");
        dtmSoldProduct.addColumn("SATIŞ FİYATI");
        dtmSoldProduct.addColumn("STCOK");
        dtmSoldProduct.addColumn("AÇIKLAMA");
        try {
            String query = "SELECT * FROM `product`";
            ResultSet rs = db.baglan().executeQuery(query);
            while(rs.next()){
                if(orderCategoryID == Integer.parseInt(rs.getString("productCategoryID")))
                dtmSoldProduct.addRow(new String[]{rs.getString("productID"), rs.getString("productName"),
                rs.getString("productPurchase"),rs.getString("productSale"),rs.getString("productStock"),rs.getString("productComment")});
                
            }
            tblSoldProduct.setModel(dtmSoldProduct);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(rootPane, "Ürün Listesi Veritabanından Alınamadı!");
        }    
    }
    public void tableReportResult(){
        DefaultTableModel dtmOrder = new DefaultTableModel();
        dtmOrder.addColumn("ÜRÜN ADI");
        dtmOrder.addColumn("KATEGORİ");
        dtmOrder.addColumn("MÜŞTERİ");
        dtmOrder.addColumn("ALIŞ FİYATI");
        dtmOrder.addColumn("SATIŞ FİYATI");
        dtmOrder.addColumn("SİPARİŞ ADEDİ");
        dtmOrder.addColumn("TARİH");
        try {
            String query = "SELECT * FROM orders INNER JOIN category ON orders.orderCategoryID = category.categoryID "
                    + "INNER JOIN customers ON orders.orderCustomerID = customers.customerID";
            ResultSet rs = db.baglan().executeQuery(query);
            while(rs.next()){
                dtmOrder.addRow(new String[]{rs.getString("orderProductName"),
                rs.getString("categoryName"),rs.getString("customerName"),rs.getString("orderPurchase"),rs.getString("orderSale"),
                rs.getString("orderAmount"),rs.getString("orderDate")});
            }
            tblReport.setModel(dtmOrder);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(rootPane, "Sipariş Listesi Veritabanından Alınamadı!");
        }    
    }
    public void editCustomerResult(){
        customerName = txtCEditName.getText().trim();
        customerTelephone = txtCEditTelephone.getText().trim();
        customerAddress = txtCEditAddress.getText().trim();
        if(customerID.equals("")){
            JOptionPane.showMessageDialog(rootPane, "Lütfen Müşteri Tablosundan Seçim Yapınız!");
        }else{
            try {
            DB db = new DB();
            String query = "UPDATE `customers` SET `customerName` = '"+customerName+"', "
                    + "`customerTelephone` = '"+customerTelephone+"', `customerAddress` = '"+customerAddress+"' "
                    + "WHERE `customers`.`customerID` = '"+customerID+"'";
            int add = db.baglan().executeUpdate(query);
            if(add > 0){
                JOptionPane.showMessageDialog(rootPane, "Müşteri Düzenlendi.");
                txtCEditName.setText("");
                txtCEditTelephone.setText("");
                txtCEditAddress.setText("");
                tableCustomerResult();
                customerID = "";
            }else{
                JOptionPane.showMessageDialog(rootPane, "Müşteri Düzenlenemedi!");
            }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(rootPane, "Düzenleme Başarısız!");
            }
        }
    
    }
    public void editCategoryResult(){
        categoryName = txtEditCategoryName.getText().trim();
        categoryComment = txtEditCategoryComment.getText().trim();
        if(categoryID.equals("")){
            JOptionPane.showMessageDialog(rootPane, "Lütfen Kategori Seçimi Yapınız.");
        }else{
            try {
            DB db = new DB();
            String query = "UPDATE `category` SET `categoryName` = '"+categoryName+"', `categoryComment` = '"+categoryComment+"'"
                    + "WHERE `category`.`categoryID` = '"+categoryID+"'";
            
            int add = db.baglan().executeUpdate(query);
            
            if(add > 0){
                JOptionPane.showMessageDialog(rootPane, "Kategori Düzenlendi.");
                txtEditCategoryName.setText("");
                txtEditCategoryComment.setText("");
                tableCategoryResult();
                comboCategoryResult();
                categoryID = "";
            }else{
                JOptionPane.showMessageDialog(rootPane, "Kategori Düzenlenemedi!");
            }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(rootPane, "Düzenleme Başarısız!");
            }
        }
  
    }
    public void editProductResult(){
        productName = txtEditPName.getText().trim();
        productPurchase = txtEditCustPrice.getText().trim();
        productSale = txtEditSalePrice.getText().trim();
        productStock = txtEditStock.getText().trim();
        productComment = txtEditPComment.getText().trim();
        if(productID.equals("")){
            JOptionPane.showMessageDialog(rootPane, "Lütfen Ürün Seçimi Yapınız!");
        }else{
            try {
            DB db = new DB();
            String query = "UPDATE `product` SET `productName` = '"+productName+"', `productCategoryID` = '"+productCategoryID+"', `productPurchase` = '"+productPurchase+"',"
                    + "`productSale` = '"+productSale+"', `productStock` = '"+productStock+"', `productComment` = '"+productComment+"'"
                    + "WHERE `product`.`productID` = '"+productID+"'";   
            int add = db.baglan().executeUpdate(query);
            
            if(add > 0){
                JOptionPane.showMessageDialog(rootPane, "Ürün Düzenlendi.");
                txtEditPName.setText("");
                txtEditCustPrice.setText("");
                txtEditSalePrice.setText("");
                txtEditStock.setText("");
                txtEditPComment.setText("");
                tableProductResult();
                productID = "";
                productCategoryID = -1;
            }else{
                JOptionPane.showMessageDialog(rootPane, "Ürün Düzenlenemedi!");
            }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(rootPane, "Düzenleme Başarısız!");
            }
        } 
    
    }
    public void editNewProductStock(){
        try {
            DB db = new DB();
            String query = "UPDATE `product` SET `productStock` = '"+productNewStock+"' WHERE `product`.`productID` = '"+productID+"'";   
            int add = db.baglan().executeUpdate(query);
            
            if(add > 0){
                tableProductResult();
                tableReportResult();
                productID = "";
            }else{
                JOptionPane.showMessageDialog(rootPane, "Stok Düzenlenemedi!");
            }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(rootPane, "Stok Düzenlemesi Başarısız!");
            }
    }
    public void searchOrderFunc(){
        if(txtSearchName.getText().equals("")){
            JOptionPane.showMessageDialog(rootPane, "Lütfen Ürün Adı Giriniz.");
        }else{
            DefaultTableModel dtmOrder = new DefaultTableModel();
            dtmOrder.addColumn("ÜRÜN ADI");
            dtmOrder.addColumn("KATEGORİ");
            dtmOrder.addColumn("MÜŞTERİ");
            dtmOrder.addColumn("ALIŞ FİYATI");
            dtmOrder.addColumn("SATIŞ FİYATI");
            dtmOrder.addColumn("SİPARİŞ ADEDİ");
            dtmOrder.addColumn("TARİH");
            searchName = txtSearchName.getText();
            try {
                String query = "SELECT * FROM orders INNER JOIN category ON orders.orderCategoryID = category.categoryID "
                    + "INNER JOIN customers ON orders.orderCustomerID = customers.customerID WHERE `orderProductName` LIKE '"+searchName+"'";
                
                ResultSet rs = db.baglan().executeQuery(query);
            while(rs.next()){
                dtmOrder.addRow(new String[]{rs.getString("orderProductName"),
                rs.getString("categoryName"),rs.getString("customerName"),rs.getString("orderPurchase"),rs.getString("orderSale"),
                rs.getString("orderAmount"),rs.getString("orderDate")});
            }
            tblReport.setModel(dtmOrder);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(rootPane, "Sipariş Listesi Veritabanından Alınamadı!");
            }
        }
    }
    public void deleteCategoryProduct(){
        try {
            String query = "DELETE FROM `product` WHERE `product`.`productCategoryID` = "+categoryID+"";
            int sonuc = db.baglan().executeUpdate(query);
            if(sonuc > 0){
                JOptionPane.showMessageDialog(rootPane, "Kategori Silindi.");
                categoryID = "";
        }
        }catch (Exception e) {
            JOptionPane.showMessageDialog(rootPane, "Kategori Ürün Silme Hatası!");
        }
    }
    public void deleteCategoryOrder(){
        try {
            String query = "DELETE FROM `orders` WHERE `orders`.`orderCategoryID` = "+categoryID+"";
            int sonuc = db.baglan().executeUpdate(query);
            if(sonuc > 0){
                deleteCategoryProduct();
        }
        }catch (Exception e) {
            JOptionPane.showMessageDialog(rootPane, "Kategori Sipariş Silme Hatası!");
        }
    }
    public void deleteCustomerOrder(){
        try {
            String query = "DELETE FROM `orders` WHERE `orders`.`orderCustomerID` = "+customerID+"";
            int sonuc = db.baglan().executeUpdate(query);
            if(sonuc > 0){
                JOptionPane.showMessageDialog(rootPane, "Müşteri Silindi.");
                customerID = "";
            }
        }catch (Exception e) {
            JOptionPane.showMessageDialog(rootPane, "Müşteri Sipariş Silme Hatası!");
        }
    }
    void comboCategoryResult(){
        DefaultComboBoxModel<String> dcm = new DefaultComboBoxModel<>();
        arrayListCategory.clear();
        try {
        String query = "select * from category";
        ResultSet rs = db.baglan().executeQuery(query);
        while(rs.next()){
            dcm.addElement(rs.getString("categoryName"));
            arrayListCategory.add(rs.getInt("categoryID"));
        }
        cmbNewCategory.setModel(dcm);
        cmbCategory.setModel(dcm);
        cmbEditCategory.setModel(dcm);
        } catch (Exception e) {
        System.out.println("category result error : " + e);
        }
    }
    void comboCustomerResult(){
        DefaultComboBoxModel<String> dcm = new DefaultComboBoxModel<>();
        arrayListCustomer.clear();
        try {
        String query = "select * from customers";
        ResultSet rs = db.baglan().executeQuery(query);
        while(rs.next()){
            dcm.addElement(rs.getString("customerName"));
            arrayListCustomer.add(rs.getInt("customerID"));
        }
        cmbCustomer.setModel(dcm);
        } catch (Exception e) {
        System.out.println("customer result error : " + e);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnExit = new javax.swing.JButton();
        btnNewUser = new javax.swing.JButton();
        lblUserName = new javax.swing.JLabel();
        lblDate = new javax.swing.JLabel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblCustomer = new javax.swing.JTable();
        btnDeleteCustomer = new javax.swing.JButton();
        jPanel7 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txtNewCName = new javax.swing.JTextField();
        txtNewCAddress = new javax.swing.JTextField();
        txtNewCTelephone = new javax.swing.JTextField();
        btnAddCustomer = new javax.swing.JButton();
        jPanel8 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        txtCEditTelephone = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        txtCEditName = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        txtCEditAddress = new javax.swing.JTextField();
        btnEditCustomer = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();
        cmbCategory = new javax.swing.JComboBox<>();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblSoldProduct = new javax.swing.JTable();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        txtTotalOrder = new javax.swing.JTextField();
        cmbCustomer = new javax.swing.JComboBox<>();
        btnComplateSale = new javax.swing.JButton();
        lblSelectProduct = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jPanel10 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblProduct = new javax.swing.JTable();
        btnDeleteProduct = new javax.swing.JButton();
        jPanel11 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        txtNewPName = new javax.swing.JTextField();
        txtNewCustPrice = new javax.swing.JTextField();
        txtNewSalePrice = new javax.swing.JTextField();
        txtNewStock = new javax.swing.JTextField();
        cmbNewCategory = new javax.swing.JComboBox<>();
        btnAddProduct = new javax.swing.JButton();
        jScrollPane7 = new javax.swing.JScrollPane();
        txtNewPComment = new javax.swing.JTextArea();
        jPanel12 = new javax.swing.JPanel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        txtEditPName = new javax.swing.JTextField();
        txtEditCustPrice = new javax.swing.JTextField();
        txtEditSalePrice = new javax.swing.JTextField();
        txtEditStock = new javax.swing.JTextField();
        cmbEditCategory = new javax.swing.JComboBox<>();
        btnEditProduct = new javax.swing.JButton();
        jScrollPane8 = new javax.swing.JScrollPane();
        txtEditPComment = new javax.swing.JTextArea();
        jPanel4 = new javax.swing.JPanel();
        jPanel13 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tblCategory = new javax.swing.JTable();
        btnDeleteCategory = new javax.swing.JButton();
        jPanel14 = new javax.swing.JPanel();
        jLabel25 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        txtEditCategoryName = new javax.swing.JTextField();
        jScrollPane5 = new javax.swing.JScrollPane();
        txtEditCategoryComment = new javax.swing.JTextArea();
        btnEditCategory = new javax.swing.JButton();
        jPanel15 = new javax.swing.JPanel();
        jLabel27 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        txtNewCategoryName = new javax.swing.JTextField();
        jScrollPane6 = new javax.swing.JScrollPane();
        txtNewCategoryComment = new javax.swing.JTextArea();
        btnAddCategory = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jPanel16 = new javax.swing.JPanel();
        txtSearchName = new javax.swing.JTextField();
        btnSearch = new javax.swing.JButton();
        btnToday = new javax.swing.JButton();
        jPanel18 = new javax.swing.JPanel();
        jScrollPane9 = new javax.swing.JScrollPane();
        tblReport = new javax.swing.JTable();
        btnDeleteAllOrder = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        lblTime = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(1120, 870));
        setPreferredSize(new java.awt.Dimension(1120, 870));
        setResizable(false);
        setSize(new java.awt.Dimension(1120, 870));
        getContentPane().setLayout(null);

        btnExit.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        btnExit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/exit.png"))); // NOI18N
        btnExit.setToolTipText("");
        btnExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExitActionPerformed(evt);
            }
        });
        getContentPane().add(btnExit);
        btnExit.setBounds(1040, 20, 70, 60);

        btnNewUser.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        btnNewUser.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/new admin.png"))); // NOI18N
        btnNewUser.setText("Yeni Kullanıcı");
        btnNewUser.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnNewUser.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnNewUser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNewUserActionPerformed(evt);
            }
        });
        getContentPane().add(btnNewUser);
        btnNewUser.setBounds(920, 10, 110, 80);

        lblUserName.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        lblUserName.setText("Admin");
        getContentPane().add(lblUserName);
        lblUserName.setBounds(530, 20, 350, 37);

        lblDate.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        getContentPane().add(lblDate);
        lblDate.setBounds(20, 10, 70, 37);

        jTabbedPane1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N

        jPanel1.setOpaque(false);

        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Müşteri Listesi", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 1, 16))); // NOI18N
        jPanel6.setToolTipText("");
        jPanel6.setOpaque(false);

        tblCustomer.setModel(new javax.swing.table.DefaultTableModel(
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
        tblCustomer.setFillsViewportHeight(true);
        tblCustomer.setGridColor(new java.awt.Color(0, 0, 0));
        tblCustomer.setOpaque(false);
        tblCustomer.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblCustomerMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblCustomer);

        btnDeleteCustomer.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        btnDeleteCustomer.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/delete customer.png"))); // NOI18N
        btnDeleteCustomer.setText("Müşteriyi Sil");
        btnDeleteCustomer.setToolTipText("");
        btnDeleteCustomer.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnDeleteCustomer.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnDeleteCustomer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteCustomerActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 918, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addComponent(btnDeleteCustomer)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 376, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnDeleteCustomer, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)))
                .addContainerGap())
        );

        jPanel7.setBackground(new java.awt.Color(197, 226, 197));
        jPanel7.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Yeni Müşteri", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 1, 16))); // NOI18N

        jLabel2.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel2.setText("Ad");

        jLabel3.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel3.setText("Telefon");

        jLabel5.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel5.setText("Adres");

        btnAddCustomer.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/add customer.png"))); // NOI18N
        btnAddCustomer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddCustomerActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnAddCustomer, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtNewCTelephone, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txtNewCName, javax.swing.GroupLayout.DEFAULT_SIZE, 335, Short.MAX_VALUE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtNewCAddress)))
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(1, 1, 1)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtNewCName, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtNewCTelephone, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(24, 24, 24)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtNewCAddress, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(btnAddCustomer, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel8.setBackground(new java.awt.Color(236, 218, 201));
        jPanel8.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Müşteri Düzenle", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 1, 16))); // NOI18N

        jLabel6.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel6.setText("Telefon");

        jLabel8.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel8.setText("Ad");

        jLabel9.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel9.setText("Adres");

        btnEditCustomer.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/edit customer.png"))); // NOI18N
        btnEditCustomer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditCustomerActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtCEditName, javax.swing.GroupLayout.DEFAULT_SIZE, 386, Short.MAX_VALUE))
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtCEditAddress)
                            .addComponent(txtCEditTelephone, javax.swing.GroupLayout.Alignment.TRAILING)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnEditCustomer, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtCEditName))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtCEditTelephone, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtCEditAddress, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(btnEditCustomer)
                .addGap(17, 17, 17))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(114, 114, 114))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        jTabbedPane1.addTab("Müşteriler", jPanel1);

        jPanel2.setOpaque(false);

        jPanel9.setOpaque(false);

        cmbCategory.setBackground(new java.awt.Color(197, 226, 197));
        cmbCategory.setFont(new java.awt.Font("Arial", 1, 16)); // NOI18N
        cmbCategory.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cmbCategory.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmbCategoryİtemStateChanged(evt);
            }
        });

        tblSoldProduct.setModel(new javax.swing.table.DefaultTableModel(
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
        tblSoldProduct.setFillsViewportHeight(true);
        tblSoldProduct.setOpaque(false);
        tblSoldProduct.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblSoldProductMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tblSoldProduct);

        jLabel11.setFont(new java.awt.Font("Arial", 3, 14)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 51, 51));
        jLabel11.setText("Satış yapmak için listeden seçim yapınız...");

        jLabel12.setBackground(new java.awt.Color(197, 226, 197));
        jLabel12.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel12.setText("Seçilen Ürün :");
        jLabel12.setOpaque(true);

        jLabel13.setBackground(new java.awt.Color(236, 218, 201));
        jLabel13.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel13.setText("Adet Giriniz :");
        jLabel13.setOpaque(true);

        cmbCustomer.setBackground(new java.awt.Color(197, 226, 197));
        cmbCustomer.setFont(new java.awt.Font("Arial", 1, 16)); // NOI18N
        cmbCustomer.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cmbCustomer.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmbCustomerİtemStateChanged(evt);
            }
        });

        btnComplateSale.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        btnComplateSale.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/add order.png"))); // NOI18N
        btnComplateSale.setText("Satışı Tamamla");
        btnComplateSale.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnComplateSale.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnComplateSale.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnComplateSaleActionPerformed(evt);
            }
        });

        lblSelectProduct.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 330, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cmbCategory, javax.swing.GroupLayout.PREFERRED_SIZE, 316, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 1059, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                .addGap(146, 146, 146)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lblSelectProduct, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cmbCustomer, 0, 277, Short.MAX_VALUE)
                    .addComponent(txtTotalOrder))
                .addGap(54, 54, 54)
                .addComponent(btnComplateSale)
                .addGap(362, 362, 362))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(cmbCategory, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(35, 35, 35)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 331, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel11)
                .addGap(49, 49, 49)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblSelectProduct, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtTotalOrder, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cmbCustomer, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(65, 65, 65))
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addComponent(btnComplateSale)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, 1086, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("Satış Yönetimi", jPanel2);

        jPanel3.setOpaque(false);

        jPanel10.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Ürün Listesi", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 1, 16))); // NOI18N
        jPanel10.setOpaque(false);

        tblProduct.setModel(new javax.swing.table.DefaultTableModel(
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
        tblProduct.setFillsViewportHeight(true);
        tblProduct.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblProductMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(tblProduct);

        btnDeleteProduct.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        btnDeleteProduct.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/delete product.png"))); // NOI18N
        btnDeleteProduct.setText("Ürünü Sil");
        btnDeleteProduct.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnDeleteProduct.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnDeleteProduct.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteProductActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 889, Short.MAX_VALUE)
                .addGap(39, 39, 39)
                .addComponent(btnDeleteProduct)
                .addContainerGap())
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 311, Short.MAX_VALUE)
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnDeleteProduct)))
                .addContainerGap())
        );

        jPanel11.setBackground(new java.awt.Color(197, 226, 197));
        jPanel11.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Yeni Ürün", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 1, 16))); // NOI18N

        jLabel10.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel10.setText("Ürün Adı");

        jLabel14.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel14.setText("Geliş Ücreti");

        jLabel15.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel15.setText("Kategori");

        jLabel16.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel16.setText("Satış Fiyatı");

        jLabel17.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel17.setText("Açıklama");

        jLabel18.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel18.setText("Stok");

        cmbNewCategory.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        cmbNewCategory.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        btnAddProduct.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/add product.png"))); // NOI18N
        btnAddProduct.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddProductActionPerformed(evt);
            }
        });

        txtNewPComment.setColumns(20);
        txtNewPComment.setRows(5);
        jScrollPane7.setViewportView(txtNewPComment);

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(txtNewCustPrice, javax.swing.GroupLayout.DEFAULT_SIZE, 347, Short.MAX_VALUE))
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(txtNewSalePrice, javax.swing.GroupLayout.DEFAULT_SIZE, 347, Short.MAX_VALUE))
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(txtNewStock, javax.swing.GroupLayout.DEFAULT_SIZE, 347, Short.MAX_VALUE))
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cmbNewCategory, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtNewPName)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel11Layout.createSequentialGroup()
                        .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane7))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel11Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnAddProduct, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addComponent(txtNewPName)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cmbNewCategory, javax.swing.GroupLayout.DEFAULT_SIZE, 28, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtNewCustPrice))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtNewSalePrice))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtNewStock))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel17, javax.swing.GroupLayout.DEFAULT_SIZE, 28, Short.MAX_VALUE)
                    .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnAddProduct, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jPanel12.setBackground(new java.awt.Color(236, 218, 201));
        jPanel12.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Ürün Düzenle", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 1, 16))); // NOI18N

        jLabel19.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel19.setText("Ürün Adı");

        jLabel20.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel20.setText("Geliş Ücreti");

        jLabel21.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel21.setText("Kategori");

        jLabel22.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel22.setText("Satış Fiyatı");

        jLabel23.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel23.setText("Açıklama");

        jLabel24.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel24.setText("Stok");

        cmbEditCategory.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        cmbEditCategory.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cmbEditCategory.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmbEditCategoryİtemStateChanged(evt);
            }
        });

        btnEditProduct.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/edit product.png"))); // NOI18N
        btnEditProduct.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditProductActionPerformed(evt);
            }
        });

        txtEditPComment.setColumns(20);
        txtEditPComment.setRows(5);
        jScrollPane8.setViewportView(txtEditPComment);

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel12Layout.createSequentialGroup()
                        .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(txtEditCustPrice, javax.swing.GroupLayout.DEFAULT_SIZE, 311, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel12Layout.createSequentialGroup()
                        .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(txtEditSalePrice, javax.swing.GroupLayout.DEFAULT_SIZE, 311, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel12Layout.createSequentialGroup()
                        .addComponent(jLabel24, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(txtEditStock, javax.swing.GroupLayout.DEFAULT_SIZE, 311, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel12Layout.createSequentialGroup()
                        .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cmbEditCategory, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtEditPName)))
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addComponent(jLabel23, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel12Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(btnEditProduct, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jScrollPane8))))
                .addContainerGap())
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addComponent(txtEditPName)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel21, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cmbEditCategory, javax.swing.GroupLayout.DEFAULT_SIZE, 28, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtEditCustPrice))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtEditSalePrice))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel24, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtEditStock))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel23, javax.swing.GroupLayout.DEFAULT_SIZE, 28, Short.MAX_VALUE)
                    .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnEditProduct, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        jTabbedPane1.addTab("Ürün Yönetimi", jPanel3);

        jPanel4.setOpaque(false);

        jPanel13.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Kategori Listesi", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 1, 16))); // NOI18N
        jPanel13.setOpaque(false);

        tblCategory.setModel(new javax.swing.table.DefaultTableModel(
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
        tblCategory.setFillsViewportHeight(true);
        tblCategory.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblCategoryMouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(tblCategory);

        btnDeleteCategory.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        btnDeleteCategory.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/delete product.png"))); // NOI18N
        btnDeleteCategory.setText("Kategori Sil");
        btnDeleteCategory.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnDeleteCategory.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnDeleteCategory.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteCategoryActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 486, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel13Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnDeleteCategory, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addComponent(jScrollPane4)
                .addGap(18, 18, 18)
                .addComponent(btnDeleteCategory)
                .addContainerGap())
        );

        jPanel14.setBackground(new java.awt.Color(236, 218, 201));
        jPanel14.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Kategori Düzenle", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 1, 16))); // NOI18N

        jLabel25.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel25.setText("Kategori Adı :");

        jLabel26.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel26.setText("Açıklama :");

        txtEditCategoryComment.setColumns(20);
        txtEditCategoryComment.setRows(5);
        jScrollPane5.setViewportView(txtEditCategoryComment);

        btnEditCategory.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/edit product.png"))); // NOI18N
        btnEditCategory.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditCategoryActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel14Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel14Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnEditCategory, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel14Layout.createSequentialGroup()
                        .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLabel26, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel25, javax.swing.GroupLayout.DEFAULT_SIZE, 130, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtEditCategoryName)
                            .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 348, Short.MAX_VALUE))))
                .addContainerGap())
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtEditCategoryName, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
                    .addComponent(jLabel25, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel26, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 30, Short.MAX_VALUE)
                .addComponent(btnEditCategory)
                .addContainerGap())
        );

        jPanel15.setBackground(new java.awt.Color(197, 226, 197));
        jPanel15.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Yeni Kategori", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 1, 16))); // NOI18N

        jLabel27.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel27.setText("Kategori Adı :");

        jLabel28.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel28.setText("Açıklama :");

        txtNewCategoryComment.setColumns(20);
        txtNewCategoryComment.setRows(5);
        jScrollPane6.setViewportView(txtNewCategoryComment);

        btnAddCategory.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/add product.png"))); // NOI18N
        btnAddCategory.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddCategoryActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel15Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel15Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnAddCategory))
                    .addGroup(jPanel15Layout.createSequentialGroup()
                        .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLabel28, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel27, javax.swing.GroupLayout.DEFAULT_SIZE, 130, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtNewCategoryName)
                            .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 348, Short.MAX_VALUE))))
                .addContainerGap())
        );
        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtNewCategoryName, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel27, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel28, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(39, 39, 39)
                .addComponent(btnAddCategory)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel14, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jPanel15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 116, Short.MAX_VALUE)
                        .addComponent(jPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );

        jTabbedPane1.addTab("Kategori Yönetimi", jPanel4);

        jPanel5.setOpaque(false);

        jPanel16.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Filtrele", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 1, 16))); // NOI18N
        jPanel16.setOpaque(false);

        btnSearch.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        btnSearch.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/search.png"))); // NOI18N
        btnSearch.setText("Ara");
        btnSearch.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnSearch.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSearchActionPerformed(evt);
            }
        });

        btnToday.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        btnToday.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/today.png"))); // NOI18N
        btnToday.setText("Bugünün Satışları İçin Tıklayın");
        btnToday.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnToday.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnToday.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTodayActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel16Layout = new javax.swing.GroupLayout(jPanel16);
        jPanel16.setLayout(jPanel16Layout);
        jPanel16Layout.setHorizontalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txtSearchName, javax.swing.GroupLayout.PREFERRED_SIZE, 266, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(37, 37, 37)
                .addComponent(btnSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnToday)
                .addContainerGap())
        );
        jPanel16Layout.setVerticalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel16Layout.createSequentialGroup()
                        .addComponent(btnToday, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(44, 44, 44))
                    .addGroup(jPanel16Layout.createSequentialGroup()
                        .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtSearchName, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnSearch))
                        .addGap(0, 0, Short.MAX_VALUE))))
        );

        jPanel18.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Satışlar", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 1, 14))); // NOI18N
        jPanel18.setOpaque(false);

        tblReport.setModel(new javax.swing.table.DefaultTableModel(
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
        tblReport.setFillsViewportHeight(true);
        tblReport.setOpaque(false);
        jScrollPane9.setViewportView(tblReport);

        btnDeleteAllOrder.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        btnDeleteAllOrder.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/all delete.png"))); // NOI18N
        btnDeleteAllOrder.setText("Tüm Siparişleri Sil");
        btnDeleteAllOrder.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnDeleteAllOrder.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnDeleteAllOrder.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteAllOrderActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel18Layout = new javax.swing.GroupLayout(jPanel18);
        jPanel18.setLayout(jPanel18Layout);
        jPanel18Layout.setHorizontalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane9, javax.swing.GroupLayout.DEFAULT_SIZE, 1025, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel18Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnDeleteAllOrder)))
                .addContainerGap())
        );
        jPanel18Layout.setVerticalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane9, javax.swing.GroupLayout.PREFERRED_SIZE, 399, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 44, Short.MAX_VALUE)
                .addComponent(btnDeleteAllOrder)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel18, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel16, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel18, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );

        jTabbedPane1.addTab("Raporlar", jPanel5);

        getContentPane().add(jTabbedPane1);
        jTabbedPane1.setBounds(12, 75, 1090, 760);

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel4.setText("|");
        getContentPane().add(jLabel4);
        jLabel4.setBounds(90, 10, 18, 37);

        lblTime.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        getContentPane().add(lblTime);
        lblTime.setBounds(110, 10, 50, 37);

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/media/thumb-1920-570906.jpg"))); // NOI18N
        jLabel1.setText("jLabel1");
        getContentPane().add(jLabel1);
        jLabel1.setBounds(-10, -10, 1140, 890);

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExitActionPerformed
        int answer = JOptionPane.showConfirmDialog(null, "Emin misiniz?", "WARNING",JOptionPane.YES_NO_OPTION);
        if (answer == 0){
            System.exit(0);
        }
    }//GEN-LAST:event_btnExitActionPerformed

    private void btnNewUserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNewUserActionPerformed
        NewUser tls = new NewUser();
        tls.setVisible(true);
    }//GEN-LAST:event_btnNewUserActionPerformed

    private void tblCustomerMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblCustomerMouseClicked
        int row = tblCustomer.getSelectedRow();
        customerID = "" + tblCustomer.getValueAt(row,0);
        customerName = "" + tblCustomer.getValueAt(row,1);
        customerTelephone = "" + tblCustomer.getValueAt(row,2);
        customerAddress = "" + tblCustomer.getValueAt(row,3);
        txtCEditName.setText(customerName);
        txtCEditTelephone.setText(customerTelephone);
        txtCEditAddress.setText(customerAddress);
    }//GEN-LAST:event_tblCustomerMouseClicked

    private void btnDeleteCustomerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteCustomerActionPerformed
        if(customerID.equals("")){
            JOptionPane.showMessageDialog(rootPane, "Lütfen Müşteriler Tablosundan Seçim Yapınız!");
        }else{
            int answer = JOptionPane.showConfirmDialog(null, "Emin misiniz?", "WARNING",JOptionPane.YES_NO_OPTION);
            if (answer == 0){
                try {
                    String query = "DELETE FROM `customers` WHERE `customers`.`customerID` = "+customerID+"";
                    int sonuc = db.baglan().executeUpdate(query);
                    if(sonuc > 0){
                        txtCEditName.setText("");
                        txtCEditTelephone.setText("");
                        txtCEditAddress.setText("");
                        deleteCustomerOrder();
                        tableCustomerResult();
                        comboCustomerResult();
                        tableReportResult();
                    }
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(rootPane, "Müşteri Silme Hatası!");
                }
            }
        }
    }//GEN-LAST:event_btnDeleteCustomerActionPerformed

    private void btnAddCustomerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddCustomerActionPerformed
        customerName = txtNewCName.getText().trim();
        customerTelephone = txtNewCTelephone.getText().trim();
        customerAddress = txtNewCAddress.getText().trim();

        if(customerName.equals("")){
            JOptionPane.showMessageDialog(rootPane, "Lütfen Müşterinin Adını ve Soyadını Giriniz!");
            txtNewCName.requestFocus();
        }else if(customerTelephone.equals("")){
            JOptionPane.showMessageDialog(rootPane, "Lütfen Telefon Giriniz!");
            txtNewCTelephone.requestFocus();
        }else if(customerAddress.equals("")){
            JOptionPane.showMessageDialog(rootPane, "Lütfen Adres Giriniz!");
            txtNewCAddress.requestFocus();
        }else{
            try {
                DB db = new DB();
                int add = db.baglan().executeUpdate("INSERT INTO `customers` (`customerID`, `customerName`, `customerTelephone`, `customerAddress`) "
                    + "VALUES (NULL, '"+customerName+"', '"+customerTelephone+"', '"+customerAddress+"');");
                if(add > 0){
                    JOptionPane.showMessageDialog(rootPane, "Yeni Müşteri Eklendi.");
                    txtNewCName.setText("");
                    txtNewCTelephone.setText("");
                    txtNewCAddress.setText("");
                    txtNewCName.requestFocus();
                    tableCustomerResult();
                    comboCustomerResult();

                }else{
                    JOptionPane.showMessageDialog(rootPane, "Yeni Müşteri Eklenemedi!");
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(rootPane, "Ekleme Hatası!");
            }
        }
    }//GEN-LAST:event_btnAddCustomerActionPerformed

    private void btnEditCustomerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditCustomerActionPerformed
        editCustomerResult();
    }//GEN-LAST:event_btnEditCustomerActionPerformed

    private void cmbCategoryİtemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmbCategoryİtemStateChanged
        orderCategoryID = arrayListCategory.get(cmbCategory.getSelectedIndex());
        tableSoldProductResult();
    }//GEN-LAST:event_cmbCategoryİtemStateChanged

    private void tblSoldProductMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblSoldProductMouseClicked
        int row = tblSoldProduct.getSelectedRow();
        productID = "" + tblSoldProduct.getValueAt(row,0);
        orderProductName = "" + tblSoldProduct.getValueAt(row,1);
        orderPurchase = "" + tblSoldProduct.getValueAt(row,2);
        orderSale = "" + tblSoldProduct.getValueAt(row,3);
        orderStock = "" + tblSoldProduct.getValueAt(row,4);
        lblSelectProduct.setText(orderProductName);
    }//GEN-LAST:event_tblSoldProductMouseClicked

    private void cmbCustomerİtemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmbCustomerİtemStateChanged
        orderCustomerID = arrayListCustomer.get(cmbCustomer.getSelectedIndex());
    }//GEN-LAST:event_cmbCustomerİtemStateChanged

    private void btnComplateSaleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnComplateSaleActionPerformed
        orderAmount = txtTotalOrder.getText().trim();
        orderDate = s.format(d);
        if(orderCategoryID == -1){
            JOptionPane.showMessageDialog(rootPane, "Lütfen Kategori Seçimi Yapınız!");
            txtTotalOrder.setText("");
            txtTotalOrder.requestFocus();
        }else if(productID.equals("")){
            JOptionPane.showMessageDialog(rootPane, "Lütfen Ürün Seçimi Yapınız!");
            txtTotalOrder.setText("");
            txtTotalOrder.requestFocus();
        }else if(orderAmount.equals("")){
            JOptionPane.showMessageDialog(rootPane, "Lütfen Şipariş Adedini Giriniz!");
            txtTotalOrder.setText("");
            txtTotalOrder.requestFocus();
        }else if(Integer.parseInt(orderAmount) > Integer.parseInt(orderStock)){
            JOptionPane.showMessageDialog(rootPane, "Lütfen Stok Miktarına Uygun Sipariş Giriniz!");
            txtTotalOrder.setText("");
            txtTotalOrder.requestFocus();
        }else{
            try {
                DB db = new DB();
                int add = db.baglan().executeUpdate("INSERT INTO `orders` (`orderID`, `orderProductName`, `orderCategoryID`, `orderCustomerID`,"
                    + " `orderPurchase`, `orderSale`, `orderAmount`, `orderDate`) "
                    + "VALUES (NULL, '"+orderProductName+"', '"+orderCategoryID+"', '"+orderCustomerID+"',"
                    + " '"+Integer.parseInt(orderPurchase)+"', '"+Integer.parseInt(orderSale)+"', '"+orderAmount+"', '"+orderDate+"');");
                if(add > 0){
                    JOptionPane.showMessageDialog(rootPane, "Sipariş Eklendi.");
                    productNewStock = String.valueOf(Integer.parseInt(orderStock) - Integer.parseInt(orderAmount));
                    txtTotalOrder.setText("");
                    txtTotalOrder.requestFocus();
                    editNewProductStock();
                }else{
                    JOptionPane.showMessageDialog(rootPane, "Sipariş Eklenemedi!");
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(rootPane, "Ekleme Hatası!");
            }
        }
    }//GEN-LAST:event_btnComplateSaleActionPerformed

    private void tblProductMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblProductMouseClicked
        int row = tblProduct.getSelectedRow();
        productID = "" + tblProduct.getValueAt(row,0);
        productName = "" + tblProduct.getValueAt(row,1);
        productPurchase = "" + tblProduct.getValueAt(row,3);
        productSale = "" + tblProduct.getValueAt(row,4);
        productStock = "" + tblProduct.getValueAt(row,5);
        productComment = "" + tblProduct.getValueAt(row,6);
        txtEditPName.setText(productName);
        txtEditCustPrice.setText(productPurchase);
        txtEditSalePrice.setText(productSale);
        txtEditStock.setText(productStock);
        txtEditPComment.setText(productComment);
    }//GEN-LAST:event_tblProductMouseClicked

    private void btnDeleteProductActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteProductActionPerformed
        if(productID.equals("")){
            JOptionPane.showMessageDialog(rootPane, "Lütfen Ürün Tablosundan Seçim Yapınız!");
        }else{
            int answer = JOptionPane.showConfirmDialog(null, "Emin misiniz?", "WARNING",JOptionPane.YES_NO_OPTION);
            if (answer == 0){
                try {
                    String query = "DELETE FROM `product` WHERE `product`.`productID` = "+productID+"";
                    int sonuc = db.baglan().executeUpdate(query);
                    if(sonuc > 0){
                        JOptionPane.showMessageDialog(rootPane, "Ürün Silindi.");
                        txtEditPName.setText("");
                        txtEditCustPrice.setText("");
                        txtEditSalePrice.setText("");
                        txtEditStock.setText("");
                        txtEditPComment.setText("");
                        tableProductResult();
                        productID = "";
                    }
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(rootPane, "Ürün Silme Hatası!");
                }
            }
        }
    }//GEN-LAST:event_btnDeleteProductActionPerformed

    private void btnAddProductActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddProductActionPerformed
        productName = txtNewPName.getText().trim();
        productCategoryID = arrayListCategory.get(cmbNewCategory.getSelectedIndex());
        productPurchase = txtNewCustPrice.getText().trim();
        productSale = txtNewSalePrice.getText().trim();
        productStock = txtNewStock.getText().trim();
        productComment = txtNewPComment.getText().trim();

        if(productName.equals("")){
            JOptionPane.showMessageDialog(rootPane, "Lütfen Ürün Adı Giriniz!");
            txtNewPName.requestFocus();
        }else if(productPurchase.equals("")){
            JOptionPane.showMessageDialog(rootPane, "Lütfen Geliş Fiyatı Giriniz!");
            txtNewCustPrice.requestFocus();
        }else if(productSale.equals("")){
            JOptionPane.showMessageDialog(rootPane, "Lütfen Satış Fiyatı Giriniz!");
            txtNewSalePrice.requestFocus();
        }else if(productStock.equals("")){
            JOptionPane.showMessageDialog(rootPane, "Lütfen Stock Giriniz!");
            txtNewStock.requestFocus();
        }else if(productComment.equals("")){
            JOptionPane.showMessageDialog(rootPane, "Lütfen Açıklama Giriniz!");
            txtNewPComment.requestFocus();
        }else{
            try {
                DB db = new DB();
                int add = db.baglan().executeUpdate("INSERT INTO `product` (`productID`, `productName`,"
                    + " `productCategoryID`, `productPurchase`, `productSale`, `productStock`, `productComment`) "
                    + "VALUES (NULL, '"+productName+"', '"+productCategoryID+"', '"+Integer.parseInt(productPurchase)+"',"
                    + "'"+Integer.parseInt(productSale)+"', '"+Integer.parseInt(productStock)+"', '"+productComment+"');");
                if(add > 0){
                    JOptionPane.showMessageDialog(rootPane, "Yeni Ürün Eklendi.");
                    txtNewPName.setText("");
                    txtNewCustPrice.setText("");
                    txtNewSalePrice.setText("");
                    txtNewStock.setText("");
                    txtNewPComment.setText("");
                    txtNewPName.requestFocus();
                }else{
                    JOptionPane.showMessageDialog(rootPane, "Yeni Ürün Eklenemedi!");
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(rootPane, "Ekleme Hatası!");
            }
        }
        tableProductResult();

    }//GEN-LAST:event_btnAddProductActionPerformed

    private void cmbEditCategoryİtemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmbEditCategoryİtemStateChanged
        productCategoryID = arrayListCategory.get(cmbEditCategory.getSelectedIndex());
    }//GEN-LAST:event_cmbEditCategoryİtemStateChanged

    private void btnEditProductActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditProductActionPerformed
        editProductResult();
    }//GEN-LAST:event_btnEditProductActionPerformed

    private void tblCategoryMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblCategoryMouseClicked
        int row = tblCategory.getSelectedRow();
        categoryID = "" + tblCategory.getValueAt(row,0);
        categoryName = "" + tblCategory.getValueAt(row,1);
        categoryComment = "" + tblCategory.getValueAt(row,2);
        txtEditCategoryName.setText(categoryName);
        txtEditCategoryComment.setText(categoryComment);
    }//GEN-LAST:event_tblCategoryMouseClicked

    private void btnDeleteCategoryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteCategoryActionPerformed
        if(categoryID.equals("")){
            JOptionPane.showMessageDialog(rootPane, "Lütfen Kategori Tablosundan Seçim Yapınız!");
        }else{
            int answer = JOptionPane.showConfirmDialog(null, "Emin misiniz?", "WARNING",JOptionPane.YES_NO_OPTION);
            if (answer == 0){
                try {
                    String query = "DELETE FROM `category` WHERE `category`.`categoryID` = "+categoryID+"";
                    int sonuc = db.baglan().executeUpdate(query);
                    if(sonuc > 0){
                        txtEditCategoryName.setText("");
                        txtEditCategoryComment.setText("");
                        deleteCategoryOrder();
                        tableCategoryResult();
                        tableProductResult();
                        tableReportResult();
                        comboCategoryResult();
                    }
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(rootPane, "Kategori Silme Hatası!");
                }
            }
        }
        comboCategoryResult();
    }//GEN-LAST:event_btnDeleteCategoryActionPerformed

    private void btnEditCategoryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditCategoryActionPerformed
        editCategoryResult();
    }//GEN-LAST:event_btnEditCategoryActionPerformed

    private void btnAddCategoryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddCategoryActionPerformed
        categoryName = txtNewCategoryName.getText().trim();
        categoryComment = txtNewCategoryComment.getText().trim();

        if(categoryName.equals("")){
            JOptionPane.showMessageDialog(rootPane, "Lütfen Kategori Giriniz!");
            txtNewCategoryName.requestFocus();
        }else if(categoryComment.equals("")){
            JOptionPane.showMessageDialog(rootPane, "Lütfen Açıklama Giriniz!");
            txtNewCategoryComment.requestFocus();
        }else{
            try {
                DB db = new DB();
                int add = db.baglan().executeUpdate("INSERT INTO `category` (`categoryID`,`categoryName`, `categoryComment`) "
                    + "VALUES (NULL, '"+categoryName+"', '"+categoryComment+"');");
                if(add > 0){
                    JOptionPane.showMessageDialog(rootPane, "Yeni Kategori Eklendi.");
                    txtNewCategoryName.setText("");
                    txtNewCategoryComment.setText("");
                    txtNewCategoryName.requestFocus();
                    tableCategoryResult();
                    comboCategoryResult();
                }else{
                    JOptionPane.showMessageDialog(rootPane, "Yeni Kategori Eklenemedi!");
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(rootPane, "Ekleme Hatası!");
            }
        }
    }//GEN-LAST:event_btnAddCategoryActionPerformed

    private void btnSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchActionPerformed
        searchOrderFunc();
    }//GEN-LAST:event_btnSearchActionPerformed

    private void btnTodayActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTodayActionPerformed
        String nowDate = s.format(d);
        DefaultTableModel dtmOrder = new DefaultTableModel();
        dtmOrder.addColumn("ÜRÜN ADI");
        dtmOrder.addColumn("KATEGORİ");
        dtmOrder.addColumn("MÜŞTERİ");
        dtmOrder.addColumn("ALIŞ FİYATI");
        dtmOrder.addColumn("SATIŞ FİYATI");
        dtmOrder.addColumn("SİPARİŞ ADEDİ");
        dtmOrder.addColumn("TARİH");
        try {

            String query = "SELECT * FROM orders INNER JOIN category ON orders.orderCategoryID = category.categoryID "
            + "INNER JOIN customers ON orders.orderCustomerID = customers.customerID WHERE `orderDate` LIKE '"+nowDate+"'";
            ResultSet rs = db.baglan().executeQuery(query);
            while(rs.next()){
                dtmOrder.addRow(new String[]{rs.getString("orderProductName"),
                    rs.getString("categoryName"),rs.getString("customerName"),rs.getString("orderPurchase"),rs.getString("orderSale"),
                    rs.getString("orderAmount"),rs.getString("orderDate")});
        }
        tblReport.setModel(dtmOrder);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(rootPane, "Bugünün Sipariş Listesi Veritabanından Alınamadı!");
        }
    }//GEN-LAST:event_btnTodayActionPerformed

    private void btnDeleteAllOrderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteAllOrderActionPerformed
        int answer = JOptionPane.showConfirmDialog(null, "Tüm Siparişler Sistemden Silinecek, Emin Misiniz?", "WARNING",JOptionPane.YES_NO_OPTION);
        if (answer == 0){
            try {
                String query = "DELETE FROM `orders`";
                int sonuc = db.baglan().executeUpdate(query);
                if(sonuc > 0){
                    JOptionPane.showMessageDialog(rootPane, "Tüm Siparişler Sistemden Silindi.");
                    tableReportResult();
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(rootPane, "Silme Hatası!");
            }
        }
    }//GEN-LAST:event_btnDeleteAllOrderActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(DashBoard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(DashBoard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(DashBoard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(DashBoard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new DashBoard().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAddCategory;
    private javax.swing.JButton btnAddCustomer;
    private javax.swing.JButton btnAddProduct;
    private javax.swing.JButton btnComplateSale;
    private javax.swing.JButton btnDeleteAllOrder;
    private javax.swing.JButton btnDeleteCategory;
    private javax.swing.JButton btnDeleteCustomer;
    private javax.swing.JButton btnDeleteProduct;
    private javax.swing.JButton btnEditCategory;
    private javax.swing.JButton btnEditCustomer;
    private javax.swing.JButton btnEditProduct;
    private javax.swing.JButton btnExit;
    private javax.swing.JButton btnNewUser;
    private javax.swing.JButton btnSearch;
    private javax.swing.JButton btnToday;
    private javax.swing.JComboBox<String> cmbCategory;
    private javax.swing.JComboBox<String> cmbCustomer;
    private javax.swing.JComboBox<String> cmbEditCategory;
    private javax.swing.JComboBox<String> cmbNewCategory;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JLabel lblDate;
    private javax.swing.JLabel lblSelectProduct;
    private javax.swing.JLabel lblTime;
    private javax.swing.JLabel lblUserName;
    private javax.swing.JTable tblCategory;
    public javax.swing.JTable tblCustomer;
    private javax.swing.JTable tblProduct;
    private javax.swing.JTable tblReport;
    private javax.swing.JTable tblSoldProduct;
    private javax.swing.JTextField txtCEditAddress;
    private javax.swing.JTextField txtCEditName;
    private javax.swing.JTextField txtCEditTelephone;
    private javax.swing.JTextArea txtEditCategoryComment;
    private javax.swing.JTextField txtEditCategoryName;
    private javax.swing.JTextField txtEditCustPrice;
    private javax.swing.JTextArea txtEditPComment;
    private javax.swing.JTextField txtEditPName;
    private javax.swing.JTextField txtEditSalePrice;
    private javax.swing.JTextField txtEditStock;
    private javax.swing.JTextField txtNewCAddress;
    private javax.swing.JTextField txtNewCName;
    private javax.swing.JTextField txtNewCTelephone;
    private javax.swing.JTextArea txtNewCategoryComment;
    private javax.swing.JTextField txtNewCategoryName;
    private javax.swing.JTextField txtNewCustPrice;
    private javax.swing.JTextArea txtNewPComment;
    private javax.swing.JTextField txtNewPName;
    private javax.swing.JTextField txtNewSalePrice;
    private javax.swing.JTextField txtNewStock;
    private javax.swing.JTextField txtSearchName;
    private javax.swing.JTextField txtTotalOrder;
    // End of variables declaration//GEN-END:variables
}
