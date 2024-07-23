/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utilities;

import com.itextpdf.kernel.color.Color;
import com.itextpdf.kernel.color.DeviceRgb;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfName;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.canvas.draw.SolidLine;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.LineSeparator;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;

import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Administrator
 */
public class InvoiceGenerator  {

    public static void createInvoice(String tenKhachHang, String nhanVien, int maHoaDon) {
        String randomAssCode = "ABCDEFJHIKLMNOPQRSUOTZX@1234567890%$#";
        StringBuilder randomBuilder = new StringBuilder();
        while (randomBuilder.length() < 5) {
            Random rand = new Random();
            randomBuilder.append(randomAssCode.charAt((int) (rand.nextFloat() * randomAssCode.length())));
        }
        randomBuilder.append(".pdf");
        // Creating a PdfDocument object 
        PdfWriter writer;
        try {
            writer = new PdfWriter(randomBuilder.toString());

            // Creating a PdfDocument object      
            PdfDocument pdf = new PdfDocument(writer);

            // Creating a Document object       
            Document doc = new Document(pdf);
//            com.itextpdf.layout.element.LineSeparator
            Paragraph superTitle = new Paragraph("Sanrio World Shop")
                    .setFontColor(new DeviceRgb(8, 73, 117))
                    .setFontSize(30f).setMarginLeft(50);
            superTitle.setRole(PdfName.Title);
            doc.add(superTitle);

            Paragraph title = new Paragraph("Hoá đơn mua hàng")
                    .setFontColor(new DeviceRgb(8, 73, 117))
                    .setFontSize(20f).setMarginLeft(150);
            title.setRole(PdfName.H1);
            doc.add(title);
            
            //draw line
            SolidLine line = new SolidLine(1f);
            line.setColor(Color.BLUE);
            LineSeparator seperator = new LineSeparator(line);
            seperator.setMarginTop(15);
            seperator.setMarginBottom(15);
            doc.add(seperator);
            
            Paragraph invoiceIdP = new Paragraph("Mã hoá đơn: " + maHoaDon);
            Paragraph customerNameP = new Paragraph("Tên Khách Hàng: " + tenKhachHang);
            Paragraph employeeNameP = new Paragraph("Nhân Viên : " + nhanVien);
            Paragraph dateP = new Paragraph("Thời Gian Xuất Hoá đơn: " + DateHelper.getFullDateTime().toString());
            Paragraph para1 = new Paragraph("Danh sách sản phẩm:");

            doc.add(invoiceIdP);
            doc.add(customerNameP);
            doc.add(employeeNameP);
            doc.add(dateP);
            doc.add(para1);

            // Creating a table       
            float[] pointColumnWidths = {200F, 200F, 200F, 200F, 200F};
            Table table = new Table(pointColumnWidths);

            // Adding columnsName to the table       
            table.addCell(new Cell().add("ID").setBold());
            table.addCell(new Cell().add("TenSP").setBold());
            table.addCell(new Cell().add("").setBold());
            table.addCell(new Cell().add("DonGia").setBold());
            table.addCell(new Cell().add("TongTien").setBold());

            //Adding rows;
            PreparedStatement stmt = QueryHelper.getPreparedStatement("  SELECT ID_ChiTietSanPham,SoLuong FROM ChiTietHoaDon WHERE ID_HoaDon=? ", maHoaDon);
            ResultSet rs = stmt.executeQuery();
            BigDecimal finalPrice = BigDecimal.valueOf(0);
            int id = 1;
            while (rs.next()) {
                table.addCell(new Cell().add(String.valueOf(id)));
                PreparedStatement stmt2 = QueryHelper.getPreparedStatement("  SELECT TenSanPham,Gia FROM ChiTietSanPham WHERE ID_ChiTietSanPham=?", rs.getInt("ID_ChiTietSanPham"));
                ResultSet rs2 = stmt2.executeQuery();
                rs2.next();
                table.addCell(new Cell().add(rs2.getString("TenSP")));

                double quantity = Double.parseDouble(rs.getString("SoLuong"));
                BigDecimal unitPrice = rs2.getBigDecimal("DonGia");
                BigDecimal total = unitPrice.multiply(BigDecimal.valueOf(quantity));
                table.addCell(new Cell().add(String.valueOf(quantity)));
                table.addCell(new Cell().add(unitPrice.toString()));
                table.addCell(new Cell().add(total.toString()));
                finalPrice = finalPrice.add(total);
                ConnectionHelper.close(stmt2.getConnection());
                id++;
            }
            ConnectionHelper.close(stmt.getConnection());
            // Adding Table to document        
            doc.add(table);

            //draw line
            doc.add(seperator);

            //Add final price, format to money
            DecimalFormat df = new DecimalFormat("#,###.00");

            Paragraph total = new Paragraph("Tong so tien: " + df.format(finalPrice) + "VND");
            total.setBold();
            doc.add(total);
            // Closing the document       
            doc.close();
            DialogHelper.alert(null, "Đã tạo ra hóa đơn với tên " + randomBuilder.toString());
        } catch (FileNotFoundException | SQLException ex) {
            Logger.getLogger(InvoiceGenerator.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static String currencyFormat(BigDecimal n) {
        return NumberFormat.getCurrencyInstance().format(n);
    }
}
