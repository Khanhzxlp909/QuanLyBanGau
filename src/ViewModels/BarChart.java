package ViewModels;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.ApplicationFrame;
import Utilities.JdbcHelper;
import javax.swing.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class BarChart extends ApplicationFrame {

    private final String chartTitle;
    private final int year;

    public BarChart(String applicationTitle, String chartTitle, int year) {
        super(applicationTitle);
        this.chartTitle = chartTitle;
        this.year = year;
    }

    public JPanel getChartPanel() {
        JFreeChart barChart = ChartFactory.createBarChart(
                chartTitle,
                "Tháng",
                "Tổng tiền",
                createDataset(),
                PlotOrientation.VERTICAL,
                true, true, false);

        ChartPanel chartPanel = new ChartPanel(barChart);
        chartPanel.setPreferredSize(new java.awt.Dimension(560, 367));
        return chartPanel;
    }

   private CategoryDataset createDataset() {
    DefaultCategoryDataset dataset = new DefaultCategoryDataset();
    Map<Integer, Double> monthTotalMap = getMonthTotalMap();

    // Thêm dữ liệu cho các tháng từ 1 đến 12, nếu không có dữ liệu thì set giá trị là 0
    for (int month = 1; month <= 12; month++) {
        if (monthTotalMap.containsKey(month)) {
            dataset.addValue(monthTotalMap.get(month), "Tổng tiền", String.valueOf(month));
        } else {
            dataset.addValue(0.0, "Tổng tiền", String.valueOf(month));
        }
    }

    return dataset;
}

    private Map<Integer, Double> getMonthTotalMap() {
        Map<Integer, Double> monthTotalMap = new HashMap<>();
        try {
            StringBuilder queryBuilder = new StringBuilder();
            queryBuilder.append("SELECT MONTH(HoaDon.NgayLapHoaDon) AS Month, SUM(ChiTietSanPham.SoLuong * ChiTietSanPham.Gia) AS Total\n" +
                                "FROM ChiTietHoaDon\n" +
                                "JOIN ChiTietSanPham ON ChiTietHoaDon.ID_ChiTietSanPham = ChiTietSanPham.ID_ChiTietSanPham\n" +
                                "JOIN HoaDon ON ChiTietHoaDon.ID_HoaDon = HoaDon.ID_HoaDon\n" +
                                "WHERE YEAR(HoaDon.NgayLapHoaDon) = ? \n" +
                                "GROUP BY MONTH(HoaDon.NgayLapHoaDon) ");
            PreparedStatement stmt = JdbcHelper.prepareStatement(queryBuilder.toString());
            stmt.setInt(1, year);

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int month = rs.getInt("Month");
                double total = rs.getDouble("Total");
                monthTotalMap.put(month, total);
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return monthTotalMap;
    }
}
