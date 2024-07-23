/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package ViewModels;

import Utilities.JdbcHelper;
import ViewModels.BarChart;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.Year;
import java.util.Arrays;
import java.util.regex.Pattern;

/**
 *
 * @author khazi
 */
public class FMGStatisticView extends javax.swing.JPanel {

    private final JPanel panel = new JPanel();
    private final JComboBox yearComboBox = new JComboBox();
    private final JTextField yearTextField = new JTextField();

    /**
     * Creates new form FMGStatisticView
     */
    public FMGStatisticView() {
        initComponents();
        panel.setLayout(new GridLayout(1, 3));
        initStatistic3();
        TongDoanhThu();
        TongDoanhThuThang();
        tongDonHangThanhCong();
        doanhthuTheoNgay();
    }
    private void cbbNam(){
        int tenYearsAgo = Year.now().minusYears(10).getValue();
        cbbNam.addItem(String.valueOf(tenYearsAgo));
    }
    private void TongDoanhThu() {
        try {
            
            
            StringBuilder builder = new StringBuilder();
            builder.append("SELECT SUM(ChiTietSanPham.SoLuong * ChiTietSanPham.Gia) AS 'TOTAL' \n" +
                            "FROM ChiTietHoaDon\n" +
                            "JOIN ChiTietSanPham ON ChiTietHoaDon.ID_ChiTietSanPham = ChiTietSanPham.ID_ChiTietSanPham\n" +
                            "JOIN HoaDon ON ChiTietHoaDon.ID_HoaDon = HoaDon.ID_HoaDon\n" +
                            "WHERE HoaDon.TrangThai =?");
            PreparedStatement stmt = JdbcHelper.prepareStatement(builder.toString(), 0);
            ResultSet rs = stmt.executeQuery();
            rs.next();
            DecimalFormat df = new DecimalFormat("#,###.00");
            lblTongDoanhThu.setText(df.format(rs.getBigDecimal("TOTAL")) + " VND");
            stmt.getConnection().close();
        } catch (SQLException e) {
            System.out.println("Lỗi tổng doanh thu");
        }
    }

    private void doanhthuTheoNgay() {
        try {
            LocalDate currentDate = LocalDate.now();
            System.out.println(currentDate);
            StringBuilder queryBuilder = new StringBuilder();
            queryBuilder.append("SELECT SUM(ChiTietSanPham.SoLuong * ChiTietSanPham.Gia) AS TongTien\n" +
                                "FROM ChiTietHoaDon\n" +
                                "JOIN ChiTietSanPham ON ChiTietHoaDon.ID_ChiTietSanPham = ChiTietSanPham.ID_ChiTietSanPham\n" +
                                "JOIN HoaDon ON ChiTietHoaDon.ID_HoaDon = HoaDon.ID_HoaDon\n" +
                                "WHERE HoaDon.TrangThai = ? \n" +
                                "AND CAST(HoaDon.NgayLapHoaDon AS DATE) = '"+currentDate+"';");
            PreparedStatement stmt = JdbcHelper.prepareStatement(queryBuilder.toString(), 0);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                double totalRevenue = rs.getDouble("TongTien");
                if(totalRevenue == 0){
                    lblDoanhThuNgay.setText("0 VND");
                }else{
                    DecimalFormat df = new DecimalFormat("#,###.00");
                    lblDoanhThuNgay.setText(df.format(totalRevenue) + " VND");
                }
            } else{
                // Nếu không có kết quả, hiển thị 0 VND
                lblDoanhThuNgay.setText("0 VND");
            }

            // Đóng kết nối
            stmt.getConnection().close();
        } catch (SQLException e) {
            System.out.println("Lỗi khi tính tổng doanh thu hàng ngày: " + e.getMessage());
        }
    }

    private void tongDonHangThanhCong() {
        try {
            StringBuilder builder = new StringBuilder();
            builder.append("SELECT COUNT(*) FROM HoaDon WHERE TrangThai = ?  ");

            // Chuẩn bị câu lệnh PreparedStatement bằng Helper
            PreparedStatement stmt = JdbcHelper.prepareStatement(builder.toString(), 0);

            // Thực thi truy vấn
            ResultSet rs = stmt.executeQuery();
            rs.next();
            lblDonHangThanhCong.setText(String.valueOf(rs.getInt(1)) + " Đơn hàng");

            // Đóng kết nối
            stmt.getConnection().close();
        } catch (SQLException e) {
            System.out.println("Lỗi tổng đơn hàng");
        }
    }

    private void TongDoanhThuThang() {
        try {
            StringBuilder builder = new StringBuilder();
            builder.append("SELECT SUM(ChiTietSanPham.SoLuong * ChiTietSanPham.Gia) AS TongTien\n" +
                            "FROM ChiTietHoaDon\n" +
                            "JOIN ChiTietSanPham ON ChiTietHoaDon.ID_ChiTietSanPham = ChiTietSanPham.ID_ChiTietSanPham\n" +
                            "JOIN HoaDon ON ChiTietHoaDon.ID_HoaDon = HoaDon.ID_HoaDon\n" +
                            "WHERE HoaDon.TrangThai = ? \n" +
                            "AND MONTH(HoaDon.NgayLapHoaDon) = MONTH(GETDATE())");

            // Chuẩn bị câu lệnh PreparedStatement bằng Helper
            PreparedStatement stmt = JdbcHelper.prepareStatement(builder.toString(), 0);

            // Thực thi truy vấn
            ResultSet rs = stmt.executeQuery();
            rs.next();

            // Định dạng số tiền theo định dạng "#,###.00"
            if(rs.getBigDecimal(1) == null){
                lblDoanhThuThang.setText(0+"VND");
            }else{
                DecimalFormat df = new DecimalFormat("#,###.00");
                lblDoanhThuThang.setText(df.format(rs.getBigDecimal(1)) + " VND");
            }

            // Đóng kết nối
            stmt.getConnection().close();
        } catch (SQLException e) {
            System.out.println("Lỗi doanh thu tháng");
        }
    }

    private void initStatistic3() {
        try {
            statistic3.setLayout(new BorderLayout());

            // Tạo biểu đồ với tổng tiền theo tháng
            BarChart barChart = new BarChart("Bar Chart", "Tổng tiền theo tháng", 2024);
            JPanel chartPanel = barChart.getChartPanel();

            if (chartPanel != null) {
                statistic3.add(chartPanel, BorderLayout.CENTER);
                statistic3.revalidate();
                statistic3.repaint();
            } else {
                System.out.println("Panel của biểu đồ là null.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Lỗi trong quá trình khởi tạo BarChart: " + e.getMessage());
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

        jTabbedPane1 = new javax.swing.JTabbedPane();
        ThongKeThang = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        statistic3 = new javax.swing.JPanel();
        topPanel = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        totalRevenuePanel = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        lblTongDoanhThu = new javax.swing.JLabel();
        monthlyRevenuePanel = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        lblDoanhThuThang = new javax.swing.JLabel();
        totalOrderPanel = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        lblDonHangThanhCong = new javax.swing.JLabel();
        monthlyOrderPanel = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        lblDoanhThuNgay = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        cbbNam = new javax.swing.JComboBox<>();
        txtNgay = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        txtThang = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        txtNam = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();

        ThongKeThang.setBackground(new java.awt.Color(255, 255, 255));

        jPanel1.setBackground(new java.awt.Color(0, 153, 153));
        jPanel1.setLayout(new java.awt.GridLayout(1, 3, 0, 15));

        statistic3.setBackground(new java.awt.Color(255, 255, 255));
        statistic3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 153, 153)));

        topPanel.setBackground(new java.awt.Color(153, 255, 255));

        jLabel1.setBackground(new java.awt.Color(0, 153, 153));
        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Biểu Đồ Thống Kê Doanh Thu");
        jLabel1.setVerticalAlignment(javax.swing.SwingConstants.TOP);

        javax.swing.GroupLayout topPanelLayout = new javax.swing.GroupLayout(topPanel);
        topPanel.setLayout(topPanelLayout);
        topPanelLayout.setHorizontalGroup(
            topPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(topPanelLayout.createSequentialGroup()
                .addGap(430, 430, 430)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 241, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(449, Short.MAX_VALUE))
        );
        topPanelLayout.setVerticalGroup(
            topPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(topPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout statistic3Layout = new javax.swing.GroupLayout(statistic3);
        statistic3.setLayout(statistic3Layout);
        statistic3Layout.setHorizontalGroup(
            statistic3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, statistic3Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(topPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        statistic3Layout.setVerticalGroup(
            statistic3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(statistic3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(topPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(275, Short.MAX_VALUE))
        );

        jPanel1.add(statistic3);

        jPanel3.setLayout(new java.awt.GridLayout(1, 4, 10, 15));

        totalRevenuePanel.setBackground(new java.awt.Color(255, 255, 255));
        totalRevenuePanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 153, 153)));

        jLabel2.setBackground(new java.awt.Color(0, 153, 153));
        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Tổng doanh thu năm");

        lblTongDoanhThu.setBackground(new java.awt.Color(0, 255, 153));
        lblTongDoanhThu.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        lblTongDoanhThu.setForeground(new java.awt.Color(0, 204, 102));
        lblTongDoanhThu.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblTongDoanhThu.setText("jLabel6");

        javax.swing.GroupLayout totalRevenuePanelLayout = new javax.swing.GroupLayout(totalRevenuePanel);
        totalRevenuePanel.setLayout(totalRevenuePanelLayout);
        totalRevenuePanelLayout.setHorizontalGroup(
            totalRevenuePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(totalRevenuePanelLayout.createSequentialGroup()
                .addComponent(lblTongDoanhThu)
                .addGap(0, 190, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, totalRevenuePanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel2)
                .addGap(68, 68, 68))
        );
        totalRevenuePanelLayout.setVerticalGroup(
            totalRevenuePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(totalRevenuePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addGap(34, 34, 34)
                .addComponent(lblTongDoanhThu)
                .addContainerGap(37, Short.MAX_VALUE))
        );

        jPanel3.add(totalRevenuePanel);

        monthlyRevenuePanel.setBackground(new java.awt.Color(255, 255, 255));
        monthlyRevenuePanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 153, 153)));

        jLabel3.setBackground(new java.awt.Color(0, 153, 153));
        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("Doanh thu tháng");

        lblDoanhThuThang.setBackground(new java.awt.Color(0, 255, 153));
        lblDoanhThuThang.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        lblDoanhThuThang.setForeground(new java.awt.Color(0, 204, 102));
        lblDoanhThuThang.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblDoanhThuThang.setText("jLabel6");

        javax.swing.GroupLayout monthlyRevenuePanelLayout = new javax.swing.GroupLayout(monthlyRevenuePanel);
        monthlyRevenuePanel.setLayout(monthlyRevenuePanelLayout);
        monthlyRevenuePanelLayout.setHorizontalGroup(
            monthlyRevenuePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(monthlyRevenuePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblDoanhThuThang)
                .addContainerGap(178, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, monthlyRevenuePanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel3)
                .addGap(73, 73, 73))
        );
        monthlyRevenuePanelLayout.setVerticalGroup(
            monthlyRevenuePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(monthlyRevenuePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3)
                .addGap(34, 34, 34)
                .addComponent(lblDoanhThuThang)
                .addContainerGap(37, Short.MAX_VALUE))
        );

        jPanel3.add(monthlyRevenuePanel);

        totalOrderPanel.setBackground(new java.awt.Color(255, 255, 255));
        totalOrderPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 153, 153)));

        jLabel5.setBackground(new java.awt.Color(0, 153, 153));
        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("Tổng đơn hàng thành công");

        lblDonHangThanhCong.setBackground(new java.awt.Color(0, 255, 153));
        lblDonHangThanhCong.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        lblDonHangThanhCong.setForeground(new java.awt.Color(0, 204, 102));
        lblDonHangThanhCong.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblDonHangThanhCong.setText("jLabel6");

        javax.swing.GroupLayout totalOrderPanelLayout = new javax.swing.GroupLayout(totalOrderPanel);
        totalOrderPanel.setLayout(totalOrderPanelLayout);
        totalOrderPanelLayout.setHorizontalGroup(
            totalOrderPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(totalOrderPanelLayout.createSequentialGroup()
                .addGroup(totalOrderPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(totalOrderPanelLayout.createSequentialGroup()
                        .addGap(50, 50, 50)
                        .addComponent(jLabel5))
                    .addGroup(totalOrderPanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(lblDonHangThanhCong)))
                .addContainerGap(35, Short.MAX_VALUE))
        );
        totalOrderPanelLayout.setVerticalGroup(
            totalOrderPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(totalOrderPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel5)
                .addGap(33, 33, 33)
                .addComponent(lblDonHangThanhCong)
                .addContainerGap(38, Short.MAX_VALUE))
        );

        jPanel3.add(totalOrderPanel);

        monthlyOrderPanel.setBackground(new java.awt.Color(255, 255, 255));
        monthlyOrderPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 153, 153)));

        jLabel4.setBackground(new java.awt.Color(0, 153, 153));
        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("Doanh Thu Theo Ngày");

        lblDoanhThuNgay.setBackground(new java.awt.Color(0, 255, 153));
        lblDoanhThuNgay.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        lblDoanhThuNgay.setForeground(new java.awt.Color(0, 204, 102));
        lblDoanhThuNgay.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblDoanhThuNgay.setText("jLabel6");

        javax.swing.GroupLayout monthlyOrderPanelLayout = new javax.swing.GroupLayout(monthlyOrderPanel);
        monthlyOrderPanel.setLayout(monthlyOrderPanelLayout);
        monthlyOrderPanelLayout.setHorizontalGroup(
            monthlyOrderPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(monthlyOrderPanelLayout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(lblDoanhThuNgay)
                .addContainerGap(168, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, monthlyOrderPanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel4)
                .addGap(56, 56, 56))
        );
        monthlyOrderPanelLayout.setVerticalGroup(
            monthlyOrderPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(monthlyOrderPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4)
                .addGap(33, 33, 33)
                .addComponent(lblDoanhThuNgay)
                .addContainerGap(38, Short.MAX_VALUE))
        );

        jPanel3.add(monthlyOrderPanel);

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        cbbNam.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "2023", "2024" }));

        jLabel6.setText("Năm ");

        jLabel7.setText("Ngày");

        jLabel8.setText("Tháng");

        jLabel9.setText("Năm");

        jLabel10.setText("-");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(128, 128, 128)
                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(cbbNam, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(146, 146, 146)
                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtNgay, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(25, 25, 25)
                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtThang, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(33, 33, 33)
                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtNam, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(177, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(26, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbbNam, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtNgay, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6)
                    .addComponent(jLabel7)
                    .addComponent(txtThang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8)
                    .addComponent(txtNam, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9)
                    .addComponent(jLabel10))
                .addGap(22, 22, 22))
        );

        javax.swing.GroupLayout ThongKeThangLayout = new javax.swing.GroupLayout(ThongKeThang);
        ThongKeThang.setLayout(ThongKeThangLayout);
        ThongKeThangLayout.setHorizontalGroup(
            ThongKeThangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(ThongKeThangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(ThongKeThangLayout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addGroup(ThongKeThangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 1122, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 1122, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
        ThongKeThangLayout.setVerticalGroup(
            ThongKeThangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ThongKeThangLayout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 548, Short.MAX_VALUE))
            .addGroup(ThongKeThangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(ThongKeThangLayout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 336, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, 0)
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );

        jTabbedPane1.addTab("Thống kê theo tháng", ThongKeThang);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel ThongKeThang;
    private javax.swing.JComboBox<String> cbbNam;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JLabel lblDoanhThuNgay;
    private javax.swing.JLabel lblDoanhThuThang;
    private javax.swing.JLabel lblDonHangThanhCong;
    private javax.swing.JLabel lblTongDoanhThu;
    private javax.swing.JPanel monthlyOrderPanel;
    private javax.swing.JPanel monthlyRevenuePanel;
    private javax.swing.JPanel statistic3;
    private javax.swing.JPanel topPanel;
    private javax.swing.JPanel totalOrderPanel;
    private javax.swing.JPanel totalRevenuePanel;
    private javax.swing.JTextField txtNam;
    private javax.swing.JTextField txtNgay;
    private javax.swing.JTextField txtThang;
    // End of variables declaration//GEN-END:variables
}
