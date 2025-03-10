/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package View;

import BUS.ChucVuService;
import BUS.IChucVuService;
import BUS.INhanVienService;
import BUS.NhanVienService;
import Dao.IChucVuRepo;
import Models.ChucVu;
import Models.NhanVien;
import Utilities.SessionHelper;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author qivub
 */
public class FMGNhanVien extends javax.swing.JPanel {
    DefaultTableModel modelChucVu;
    DefaultTableModel modelNV;
    INhanVienService invService;
    IChucVuService iCvService;
    /**
     * Creates new form NhanVienFrame
     */
    public FMGNhanVien() {
        initComponents();
        invService = new NhanVienService();
        iCvService = new ChucVuService();
        modelNV = (DefaultTableModel) tblNhanVien.getModel();
        modelChucVu = (DefaultTableModel) tblChucvu.getModel();
        filldatatable();
        getCbbChucVu();
        fillDataChucVu();
    }

    public boolean validateNhanVien() {
        if (txtTenDangNhap.getText().isEmpty()) {
            JOptionPane.showMessageDialog(jButton1, "Thiếu tên đăng nhập");
            return false;
        }
        if (txtEmail.getText().isEmpty()) {
            JOptionPane.showMessageDialog(jButton1, "Thiếu tên đăng nhập");
            return false;
        }
        if (txtTenNhanVien.getText().isEmpty()) {
            JOptionPane.showMessageDialog(jButton1, "Thiếu tên đăng nhập");
            return false;
        }
        if (txtPassword.getText().isEmpty()) {
            JOptionPane.showMessageDialog(jButton1, "Thiếu tên đăng nhập");
            return false;
        }
        return true;
    }
     public void fillDataChucVu(){
        modelChucVu.setRowCount(0);
        for (ChucVu object : iCvService.getdata()) {
            Object[] rowData = {
                object.getID(),
                object.getTenChucVu()
            };
            modelChucVu.addRow(rowData);
        }
    }
    public NhanVien setForm() {
        NhanVien cv = new NhanVien();
        cv.setID_NhanVien(Integer.valueOf(txtID.getText()));
        cv.setTenNhanVien(txtTenNhanVien.getText());
        cv.setEmail(txtEmail.getText());
        cv.setTenDangNhap(txtTenDangNhap.getText());
        cv.setMatKhau(txtPassword.getText());

        int chuc = SessionHelper.getRole();
        cv.setID_ChucVu(chuc);
//        cv.setTrangThai(txtTenNhanVien.getText());
        return cv;
    }

    public NhanVien getForm() {
        NhanVien cv = new NhanVien();
//        cv.setID_NhanVien(Integer.valueOf(txtID.getText()));
        cv.setTenNhanVien(txtTenNhanVien.getText());
        cv.setEmail(txtEmail.getText()+"@gmail.com");
        cv.setTenDangNhap(txtTenDangNhap.getText());
        cv.setMatKhau(txtPassword.getText());
//        cv.setTrangThai(txtTenNhanVien.getText());
        int chuc = SessionHelper.getRole();
        cv.setID_ChucVu(chuc);
        return cv;
    }

    private void getCbbChucVu() {
        List<ChucVu> cv = iCvService.getdata();
        for (ChucVu chucvu : cv) {
            cbbChucVu.addItem(chucvu.getTenChucVu());
        }
    }

    private int getIDChucVu() {
        List<ChucVu> chucvulist = iCvService.getdata();
        int id = -1;
        int select = cbbChucVu.getSelectedIndex();
        ChucVu chucvu = chucvulist.get(select);
        id = chucvu.getID();
        SessionHelper.setRole(id);
        System.out.println(chucvu.getTenChucVu());
        return id;
    }

    public void filldatatable() {
        modelNV.setRowCount(0);
        for (NhanVien object : invService.getData()) {
            Object[] rowData = {
                object.getID_NhanVien(),
                object.getTenNhanVien(),
                object.getEmail(),
                object.getTenDangNhap(),
                object.getMatKhau(),
                object.getID_ChucVu(),
                object.getTrangThai()};
            modelNV.addRow(rowData);
        }
    }
    private void findDataToSQL(ChucVu view) {
        Object[] rowData = new Object[11];
        rowData[0] = view.getID();
        rowData[1] = view.getTenChucVu();
        modelChucVu.addRow(rowData);
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblNhanVien = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txtID = new javax.swing.JTextField();
        txtTenNhanVien = new javax.swing.JTextField();
        txtEmail = new javax.swing.JTextField();
        txtTenDangNhap = new javax.swing.JTextField();
        txtPassword = new javax.swing.JPasswordField();
        cbbChucVu = new javax.swing.JComboBox<>();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        cbkShowPass = new javax.swing.JCheckBox();
        jLabel7 = new javax.swing.JLabel();
        jButton3 = new javax.swing.JButton();
        txtID1 = new javax.swing.JPanel();
        lblMa_DanhMucSP2 = new javax.swing.JLabel();
        txtIDChucVu = new javax.swing.JTextField();
        lblTen_DanhMucSP2 = new javax.swing.JLabel();
        txtTenChucVu = new javax.swing.JTextField();
        btnCreate_DanhMucSP2 = new javax.swing.JButton();
        btnUpdate_DanhMucSP2 = new javax.swing.JButton();
        btnDelete_DanhMucSP2 = new javax.swing.JButton();
        jScrollPane7 = new javax.swing.JScrollPane();
        tblChucvu = new javax.swing.JTable();
        txtTimKiemChucVu = new javax.swing.JTextField();
        lblMa_DanhMucSP3 = new javax.swing.JLabel();
        btnCreate_DanhMucSP3 = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel();

        setMaximumSize(new java.awt.Dimension(2147483647, 2147483647));
        setPreferredSize(new java.awt.Dimension(1308, 690));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setMaximumSize(new java.awt.Dimension(2147483647, 2147483647));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Danh Sách Nhân Viên", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N

        tblNhanVien.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "ID", "Tên NV", "Email", "Tên Đăng Nhập", "Mật khẩu", "Chức vụ", "Trạng thái"
            }
        ));
        tblNhanVien.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                tblNhanVienMouseReleased(evt);
            }
        });
        jScrollPane1.setViewportView(tblNhanVien);

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Thêm, Sửa, Xoá Nhân Viên"));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel1.setText("ID");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel2.setText("Tên Nhân viên");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel3.setText("Email");

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel4.setText("Tên Đăng Nhập");

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel5.setText("Mật Khẩu");

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel6.setText("Chức Vụ");

        txtID.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        txtTenNhanVien.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        txtEmail.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        txtTenDangNhap.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        txtPassword.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPasswordActionPerformed(evt);
            }
        });
        txtPassword.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtPasswordKeyReleased(evt);
            }
        });

        cbbChucVu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbbChucVuActionPerformed(evt);
            }
        });

        jButton1.setText("Thêm Nhân Viên");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Sửa Nhân Viên");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        cbkShowPass.setText("Hiện mật khẩu");

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(204, 204, 204));
        jLabel7.setText("@gmail.com");

        jButton3.setText("jButton3");

        lblMa_DanhMucSP2.setText("ID");

        txtIDChucVu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtIDChucVuActionPerformed(evt);
            }
        });

        lblTen_DanhMucSP2.setText("Tên chức vụ");

        btnCreate_DanhMucSP2.setText("Thêm");
        btnCreate_DanhMucSP2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCreate_DanhMucSP2ActionPerformed(evt);
            }
        });

        btnUpdate_DanhMucSP2.setText("Sửa");
        btnUpdate_DanhMucSP2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdate_DanhMucSP2ActionPerformed(evt);
            }
        });

        btnDelete_DanhMucSP2.setText("Xoá");
        btnDelete_DanhMucSP2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDelete_DanhMucSP2ActionPerformed(evt);
            }
        });

        tblChucvu.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "ID", "Chức Vụ"
            }
        ));
        tblChucvu.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                tblChucvuMouseReleased(evt);
            }
        });
        jScrollPane7.setViewportView(tblChucvu);

        lblMa_DanhMucSP3.setText("Tìm kiếm");

        btnCreate_DanhMucSP3.setText("TÌm kiếm");
        btnCreate_DanhMucSP3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCreate_DanhMucSP3ActionPerformed(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel9.setText("Chức vụ");

        javax.swing.GroupLayout txtID1Layout = new javax.swing.GroupLayout(txtID1);
        txtID1.setLayout(txtID1Layout);
        txtID1Layout.setHorizontalGroup(
            txtID1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(txtID1Layout.createSequentialGroup()
                .addGroup(txtID1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(txtID1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(txtID1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 467, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, txtID1Layout.createSequentialGroup()
                                .addGap(88, 88, 88)
                                .addComponent(btnCreate_DanhMucSP2)
                                .addGap(63, 63, 63)
                                .addComponent(btnUpdate_DanhMucSP2)
                                .addGap(63, 63, 63)
                                .addComponent(btnDelete_DanhMucSP2))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, txtID1Layout.createSequentialGroup()
                                .addComponent(lblMa_DanhMucSP3, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(34, 34, 34)
                                .addComponent(txtTimKiemChucVu, javax.swing.GroupLayout.PREFERRED_SIZE, 266, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnCreate_DanhMucSP3))))
                    .addGroup(txtID1Layout.createSequentialGroup()
                        .addGap(190, 190, 190)
                        .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(txtID1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(txtID1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, txtID1Layout.createSequentialGroup()
                                .addComponent(lblTen_DanhMucSP2)
                                .addGap(26, 26, 26)
                                .addGroup(txtID1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(txtIDChucVu, javax.swing.GroupLayout.PREFERRED_SIZE, 266, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtTenChucVu, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 266, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(lblMa_DanhMucSP2, javax.swing.GroupLayout.Alignment.LEADING))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        txtID1Layout.setVerticalGroup(
            txtID1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(txtID1Layout.createSequentialGroup()
                .addGap(9, 9, 9)
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(txtID1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtTimKiemChucVu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblMa_DanhMucSP3)
                    .addComponent(btnCreate_DanhMucSP3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 43, Short.MAX_VALUE)
                .addGroup(txtID1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtIDChucVu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblMa_DanhMucSP2))
                .addGap(18, 18, 18)
                .addGroup(txtID1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtTenChucVu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblTen_DanhMucSP2))
                .addGap(44, 44, 44)
                .addGroup(txtID1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, txtID1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnUpdate_DanhMucSP2)
                        .addComponent(btnCreate_DanhMucSP2))
                    .addComponent(btnDelete_DanhMucSP2, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(48, 48, 48)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1))
                .addGap(28, 28, 28)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtTenDangNhap, javax.swing.GroupLayout.DEFAULT_SIZE, 237, Short.MAX_VALUE)
                            .addComponent(txtEmail)
                            .addComponent(txtTenNhanVien)
                            .addComponent(txtPassword))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel7))
                    .addComponent(txtID, javax.swing.GroupLayout.PREFERRED_SIZE, 263, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(cbbChucVu, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel3Layout.createSequentialGroup()
                            .addComponent(jButton2)
                            .addGap(30, 30, 30)
                            .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(cbkShowPass))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(txtID1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(35, 35, 35))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(19, 19, 19)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtTenNhanVien, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtTenDangNhap, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txtPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(cbkShowPass)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbbChucVu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addGap(27, 27, 27)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton2)
                    .addComponent(jButton1)
                    .addComponent(jButton3))
                .addGap(46, 46, 46))
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(txtID1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1203, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 31, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(57, 57, 57)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(33, 33, 33))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void tblNhanVienMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblNhanVienMouseReleased
        // TODO add your handling code here:
        int row = tblNhanVien.getSelectedRow();
        String valueToSelect = tblNhanVien.getValueAt(row, 5).toString();
        for (int i = 0; i < cbbChucVu.getItemCount(); i++) {
            if (cbbChucVu.getItemAt(i).toString().equals(valueToSelect)) { // So sánh giá trị
                cbbChucVu.setSelectedIndex(i);
                break;
            }
        }
    }//GEN-LAST:event_tblNhanVienMouseReleased

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        if (validateNhanVien()) {
            invService.addNhanVien(getForm());
            JOptionPane.showMessageDialog(jButton1, "Thêm thành công");
            filldatatable();
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void txtPasswordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPasswordActionPerformed
        // TODO add your handling code here:

    }//GEN-LAST:event_txtPasswordActionPerformed

    private void txtPasswordKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPasswordKeyReleased
        // TODO add your handling code here:
        if (cbkShowPass.isSelected()) {
            txtPassword.setEchoChar((char) 0);
        } else {
            txtPassword.setEchoChar('*');
        }
    }//GEN-LAST:event_txtPasswordKeyReleased

    private void cbbChucVuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbbChucVuActionPerformed
        // TODO add your handling code here:
        System.out.println(getIDChucVu());
    }//GEN-LAST:event_cbbChucVuActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        if (validateNhanVien()) {
            invService.updateNhanVien(setForm());
            JOptionPane.showMessageDialog(jButton1, "Sửa thành công");
            filldatatable();
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void txtIDChucVuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtIDChucVuActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtIDChucVuActionPerformed
    public boolean validateChucVu(){
        if (txtTenChucVu.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Fail");
            return false;
        }
        return true;
    }
    public ChucVu setFormChucVu(){
        ChucVu cv = new ChucVu();
        cv.setID(Integer.valueOf(txtIDChucVu.getText()));
        cv.setTenChucVu(txtTenChucVu.getText());
        return cv;
    }
    public ChucVu getFormChucVu(){
        ChucVu cv = new ChucVu();
//        cv.setID(Integer.valueOf(txtIDChucVu.getText()));
        cv.setTenChucVu(txtTenChucVu.getText());
        return cv;
    }
    private void btnCreate_DanhMucSP2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCreate_DanhMucSP2ActionPerformed
        // TODO add your handling code here:
        if (validateChucVu()) {
            iCvService.add(getFormChucVu());
            JOptionPane.showMessageDialog(null, "Thêm thành công");
            fillDataChucVu();
        }
    }//GEN-LAST:event_btnCreate_DanhMucSP2ActionPerformed

    private void btnUpdate_DanhMucSP2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdate_DanhMucSP2ActionPerformed
        // TODO add your handling code here:
        if (validateChucVu()) {
            iCvService.update(setFormChucVu());
            JOptionPane.showMessageDialog(null, "sửa thành công");
            fillDataChucVu();
        }
    }//GEN-LAST:event_btnUpdate_DanhMucSP2ActionPerformed

    private void btnDelete_DanhMucSP2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDelete_DanhMucSP2ActionPerformed
        // TODO add your handling code here:
        if (validateChucVu()) {
            iCvService.delete(setFormChucVu());
            JOptionPane.showMessageDialog(null, "xoá thành công");
            fillDataChucVu();
        }
    }//GEN-LAST:event_btnDelete_DanhMucSP2ActionPerformed

    private void tblChucvuMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblChucvuMouseReleased
        // TODO add your handling code here:
        int row = tblChucvu.getSelectedRow();
        txtIDChucVu.setText(tblChucvu.getValueAt(row, 0).toString());
        txtTenChucVu.setText(tblChucvu.getValueAt(row, 1).toString());
    }//GEN-LAST:event_tblChucvuMouseReleased

    private void btnCreate_DanhMucSP3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCreate_DanhMucSP3ActionPerformed
        // TODO add your handling code here:
        iCvService.getdataByName(txtTimKiemChucVu.getText());
        List<ChucVu> chucvu = iCvService.getdataByName(txtTimKiemChucVu.getText());
        modelChucVu.setRowCount(0);
        for (ChucVu product : chucvu) {
            findDataToSQL(product);
        }

    }//GEN-LAST:event_btnCreate_DanhMucSP3ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCreate_DanhMucSP2;
    private javax.swing.JButton btnCreate_DanhMucSP3;
    private javax.swing.JButton btnDelete_DanhMucSP2;
    private javax.swing.JButton btnUpdate_DanhMucSP2;
    private javax.swing.JComboBox<String> cbbChucVu;
    private javax.swing.JCheckBox cbkShowPass;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JLabel lblMa_DanhMucSP2;
    private javax.swing.JLabel lblMa_DanhMucSP3;
    private javax.swing.JLabel lblTen_DanhMucSP2;
    private javax.swing.JTable tblChucvu;
    private javax.swing.JTable tblNhanVien;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtID;
    private javax.swing.JPanel txtID1;
    private javax.swing.JTextField txtIDChucVu;
    private javax.swing.JPasswordField txtPassword;
    private javax.swing.JTextField txtTenChucVu;
    private javax.swing.JTextField txtTenDangNhap;
    private javax.swing.JTextField txtTenNhanVien;
    private javax.swing.JTextField txtTimKiemChucVu;
    // End of variables declaration//GEN-END:variables
}
