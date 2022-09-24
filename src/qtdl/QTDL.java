package qtdl;

import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import qtdl.SanPham;
import qtdl.NguoiDung;

public class QTDL {

    public static int ma_user;

    public static Connection connect() throws ClassNotFoundException, InstantiationException, SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/eyeswear?" + "user=root");
            return conn;
        } catch (IllegalAccessException ex) {
            Logger.getLogger(QTDL.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public static void Option() {
        Scanner sc = new Scanner(System.in);
        System.out.println("BẠN CÓ MUỐN? ");
        System.out.println("1. Quay lại danh sách các chức năng");
        System.out.println("2. Thoát khỏi hệ thống");
        System.out.println("\tVUI LÒNG CHỌN CHỨC NĂNG BẠN MUỐN THỰC HIỆN");
        int option = sc.nextInt();
        switch (option) {
            case 1:
                FunctionList();
                break;
            case 2:
                System.out.println("BẠN ĐÃ THOÁT KHỎI HỆ THỐNG. CẢM ƠN BẠN ĐÃ CHỌN SẢN PHẨM CỦA CHÚNG TÔI !!!");
                break;
            default:
                System.out.println("Vui lòng chọn lại: ");
                Option();
                break;
        }
    }

    public static void showAll() {
        ResultSet rs = null;
        Statement stmt = null;
        try {
            System.out.format("%110s\n", "KẾT QUẢ HIỂN THỊ TẤT CẢ SẢN PHẨM");
            Connection conn = connect();
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT * FROM sanpham s JOIN loaisanpham l ON s.ma_loai_san_pham = l.ma_loai_san_pham");
            for (int i = 0; i <= 198; i++) {
                System.out.printf("-");
            }
            System.out.printf("\n");
            System.out.printf("%-25s%-30s%-30s%-70s%-25s%-25s\n", "MÃ SẢN PHẨM", "TÊN SẢN PHẨM", "GIÁ SẢN PHẨM", "MÔ TẢ", "MÀU SẮC", "TÊN LOẠI SẢN PHẨM");
            for (int i = 0; i <= 198; i++) {
                System.out.printf("-");
            }
            System.out.printf("\n");
            while (rs.next()) {
                String masp = rs.getString("ma_san_pham");
                String tensp = rs.getString("ten_san_pham");
                String gia = rs.getString("gia_san_pham");
                String mota = rs.getString("mo_ta_san_pham");
                String mausac = rs.getString("mau_sac");
                String tenlsp = rs.getString("ten_loai_san_pham");
                System.out.printf("%-20s%-36s%-20s%-70s%-40s%-25s\n", masp, tensp, gia, mota, mausac, tenlsp);
            }
            for (int i = 0; i <= 198; i++) {
                System.out.printf("-");
            }
            System.out.printf("\n");
        } catch (ClassNotFoundException | InstantiationException | SQLException ex) {
            ex.printStackTrace();
        }
    }

    public static void showType() {
        Statement stmt = null;
        ResultSet rs = null;
        String mlsp = new String();
        Scanner sc = new Scanner(System.in);
        System.out.println("BẠN MUỐN CHỌN LOẠI SẢN PHẨM DÀNH CHO?");
        System.out.println(" 1.  Nam");
        System.out.println(" 2.  Nữ");
        try {
            int op = sc.nextInt();
            switch (op) {
                case 1:
                    mlsp = "L01";
                    break;
                case 2:
                    mlsp = "L02";
                    break;
                default:
                    System.out.println("BẠN ĐÃ NHẬP SAI CÚ PHÁP. VUI LÒNG NHẬP LẠI!!!\n");
                    break;
            }
            if (mlsp.equals("")) {
                showAll();
            } else {
                System.out.format("%110s\n", "KẾT QUẢ HIỂN THỊ THEO LOẠI SẢN PHẨM");
                Connection conn = connect();
                stmt = conn.createStatement();
                //dùng phương thức executeQuery để yêu cầu thực hiện lệnh SQL
                rs = stmt.executeQuery("SELECT * FROM sanPham WHERE ma_loai_san_pham='" + mlsp + "'");
                for (int i = 0; i <= 189; i++) {
                    System.out.printf("-");
                }
                System.out.printf("\n");
                System.out.printf("|\t%-25s%-30s%-35s%-60s%-31s|\n", "MÃ SẢN PHẨM", "TÊN SẢN PHẨM", "GIÁ SẢN PHẨM", "MÔ TẢ", "MÀU SẮC");
                for (int i = 0; i <= 189; i++) {
                    System.out.printf("-");
                }
                System.out.printf("\n");
                while (rs.next()) {
                    String masp = rs.getString("ma_san_pham");
                    String tensp = rs.getString("ten_san_pham");
                    String gia = rs.getString("gia_san_pham");
                    String mota = rs.getString("mo_ta_san_pham");
                    String mausac = rs.getString("mau_sac");
                    System.out.printf("|\t%-20s%-36s%-20s%-65s%-40s|\n", masp, tensp, gia, mota, mausac);
                }
                for (int i = 0; i <= 189; i++) {
                    System.out.printf("-");
                }
                System.out.printf("\n");
            }
            Option();
        } catch (ClassNotFoundException | InstantiationException | SQLException ex) {
            ex.printStackTrace();
            System.out.println("SQLException: " + ex.getMessage());
        }
    }

    public static void detailProduct() {
        Statement stmt = null;
        ResultSet rs = null;
        Scanner sc = new Scanner(System.in);
        System.out.println("Nhập mã sản phẩm mà bạn muốn xem: ");
        String msp = new String();
        msp = sc.nextLine();
        int magiohang = 0, t = 0;
        try {
            Connection conn = connect();
            stmt = conn.createStatement();
            //dùng phương thức executeQuery để yêu cầu thực hiện lệnh SQL
            rs = stmt.executeQuery("SELECT * FROM sanPham s JOIN loaisanpham l ON s.ma_loai_san_pham = l.ma_loai_san_pham WHERE s.ma_san_pham='" + msp + "'");
            for (int i = 0; i <= 100; i++) {
                System.out.printf("-");
            }
            System.out.format("\n|%60s%40s\n", "THÔNG TIN CHI TIẾT SẢN PHẨM", "|");
            for (int i = 0; i <= 100; i++) {
                System.out.printf("-");
            }
            System.out.printf("\n");

            while (rs.next()) {
                t++;
                String masp = rs.getString("ma_san_pham");
                String tensp = rs.getString("ten_san_pham");
                String gia = rs.getString("gia_san_pham");
                String mota = rs.getString("mo_ta_san_pham");
                String mausac = rs.getString("mau_sac");
                String tenlsp = rs.getString("ten_loai_san_pham");
                System.out.printf("|%-7s* Mã Sản Phẩm: ", " ");
                System.out.printf("%-24s", masp);
                System.out.printf("* Tên Sản Phẩm: ");
                System.out.printf("%-37s|\n", tensp);

                System.out.printf("|%-7s* Tên Loại Sản Phẩm: ", " ");
                System.out.printf("%-18s", tenlsp);
                System.out.printf("* Giá Sản Phẩm: ");
                System.out.printf("%-37s|\n", gia);

                System.out.printf("|%-7s* Mô Tả Sản Phẩm: ", " ");
                System.out.printf("%-74s|\n", mota);
            }

            if (t > 0) {
                for (int i = 0; i <= 100; i++) {
                    System.out.printf("-");
                }
                System.out.printf("\n");
                System.out.println("BẠN CÓ MUỐN THÊM SẢN PHẨM NÀY VÀO GIỎ HÀNG?");
                System.out.println("1. Thêm vào giỏ hàng");
                System.out.println("2. Quay lại");
                int luachon = sc.nextInt();
                switch (luachon) {
                    case 1:
                        addCart(msp);
                        System.out.println("=> THÊM VÀO GIỎ HÀNG THÀNH CÔNG!!!\n\n");
                        System.out.println("- XIN VUI LÒNG CHỌN:");
                        System.out.println("1. Tiếp tục mua sắm");
                        System.out.println("2. Quay lại");
                        int c = sc.nextInt();
                        switch (c) {
                            case 1:
                                detailProduct();
                                break;
                            case 2:
                                FunctionList();
                                break;
                        }
                        
                        break;
                    case 2:
                        showAll();
                        break;
                }
            } else {
                System.out.format("|%53s%47s\n", "KHÔNG TỒN TẠI SẢN PHẨM", "|");
                for (int i = 0; i <= 100; i++) {
                    System.out.printf("-");
                }
                System.out.printf("\n");
            }

            Option();
        } catch (ClassNotFoundException | InstantiationException | SQLException ex) {
            ex.printStackTrace();
        }
    }

    public static void sortPrice() {
        Statement stmt = null;
        ResultSet rs = null, min = null;
        List<SanPham> result = new ArrayList();
        try {
            System.out.format("%120s\n", "KẾT QUẢ HIỂN THỊ TẤT CẢ SẢN PHẨM THEO GIÁ TIỀN (TỪ BÉ ĐẾN LỚN) ");
            Connection conn = connect();
            stmt = conn.createStatement();
            //dùng phương thức executeQuery để yêu cầu thực hiện lệnh SQL
            rs = stmt.executeQuery("SELECT * FROM sanPham");
            while (rs.next()) {
                String masp = rs.getString("ma_san_pham");
                String tensp = rs.getString("ten_san_pham");
                int gia = rs.getInt("gia_san_pham");
                String mota = rs.getString("mo_ta_san_pham");
                String mausac = rs.getString("mau_sac");
                String malsp = rs.getString("ma_loai_san_pham");
                SanPham sp = new SanPham(masp, tensp, mota, mausac, malsp, gia);
                result.add(sp);
            }
            int i, j;
            for (i = 0; i < result.size() - 1; i++) {
                for (j = i + 1; j < result.size(); j++) {
                    if (result.get(i).gia > result.get(j).gia) {
                        int temp = result.get(i).gia;
                        result.get(i).gia = result.get(j).gia;
                        result.get(j).gia = temp;
                    }
                }
            }
            for (int k = 0; k <= 198; k++) {
                System.out.printf("-");
            }
            System.out.printf("\n");
            System.out.printf("%-25s%-30s%-30s%-70s%-25s%-25s\n", "MÃ SẢN PHẨM", "TÊN SẢN PHẨM", "GIÁ SẢN PHẨM", "MÔ TẢ", "MÀU SẮC", "MÃ SẢN PHẨM");
            for (int h = 0; h <= 198; h++) {
                System.out.printf("-");
            }
            System.out.printf("\n");
            result.forEach(sp -> {
                System.out.printf("%-20s%-36s%-20s%-70s%-40s%-25s\n", sp.masp, sp.tensp, sp.gia, sp.mota, sp.mausac, sp.malsp);
            });
            for (int k = 0; k <= 198; k++) {
                System.out.printf("-");
            }
            System.out.printf("\n");
            Option();
        } catch (ClassNotFoundException | InstantiationException | SQLException ex) {
            ex.printStackTrace();
            System.out.println("SQLException: " + ex.getMessage());
        }
    }

    public static void sortPriceByType() {
        Statement stmt = null;
        ResultSet rs = null;
        List<SanPham> result = new ArrayList();
        String mlsp = new String();
        CallableStatement csmt = null;
        Scanner sc = new Scanner(System.in);
        System.out.println("\nBẠN MUỐN CHỌN LOẠI SẢN PHẨM DÀNH CHO?");
        System.out.println(" 1.  Nam");
        System.out.println(" 2.  Nữ");
        int op = sc.nextInt();
        switch (op) {
            case 1:
                mlsp = "Nam";
                break;
            case 2:
                mlsp = "Nữ";
                break;
            default:
                System.out.println("NHẬP SAI CÚ PHÁP @@");
                break;
        }
        try {
            System.out.format("%120s\n", "KẾT QUẢ HIỂN THỊ LOẠI SẢN PHẨM THEO GIÁ TIỀN (TỪ BÉ ĐẾN LỚN) ");
            Connection conn = connect();
            stmt = conn.createStatement();
            //dùng phương thức executeQuery để yêu cầu thực hiện lệnh SQL
            if (op != 1 && op != 2) {
                System.out.println("KẾT QUẢ LỖI. VUI LÒNG CHỌN LOẠI SẢN PHẨM KHÁC: ");
                sortPriceByType();
            } else {
                csmt = conn.prepareCall("{call sp_SortPriceByType(?)}");
                csmt.setString(1, mlsp);
                rs = csmt.executeQuery();
                while (rs.next()) {
                    String masp = rs.getString("ma_san_pham");
                    String tensp = rs.getString("ten_san_pham");
                    int gia = rs.getInt("gia_san_pham");
                    String mota = rs.getString("mo_ta_san_pham");
                    String mausac = rs.getString("mau_sac");
                    String malsp = rs.getString("ten_loai_san_pham");
                    SanPham sp = new SanPham(masp, tensp, mota, mausac, malsp, gia);
                    result.add(sp);
                }
                for (int i = 0; i <= 198; i++) {
                    System.out.printf("-");
                }
                System.out.printf("\n");
                System.out.printf("%-25s%-30s%-30s%-70s%-25s%-25s\n", "MÃ SẢN PHẨM", "TÊN SẢN PHẨM", "GIÁ SẢN PHẨM", "MÔ TẢ", "MÀU SẮC", "MÃ LOẠI SẢN PHẨM");
                for (int i = 0; i <= 198; i++) {
                    System.out.printf("-");
                }
                System.out.printf("\n");
                result.forEach(sp -> {
                    System.out.printf("%-20s%-36s%-20s%-70s%-40s%-25s\n", sp.masp, sp.tensp, sp.gia, sp.mota, sp.mausac, sp.malsp);
                });
                for (int k = 0; k <= 198; k++) {
                    System.out.printf("-");
                }
                System.out.printf("\n");
                Option();
            }
        } catch (ClassNotFoundException | InstantiationException | SQLException ex) {
            ex.printStackTrace();
            System.out.println("SQLException: " + ex.getMessage());
        }
    }

    public static void searchByPrice() {
        int min, max;
        Scanner sc = new Scanner(System.in);
        System.out.println("Vui lòng nhập khoảng giá thấp nhất");
        min = sc.nextInt();
        System.out.println("Vui lòng nhập khoảng giá cao nhất");
        max = sc.nextInt();
        ResultSet rs = null;
        Statement stmt = null;
        try {
            System.out.format("%120s\n", "KẾT QUẢ HIỂN THỊ TẤT CẢ SẢN PHẨM TÌM THEO KHOẢNG GIÁ");
            Connection conn = connect();
            stmt = conn.createStatement();
            //dùng phương thức executeQuery để yêu cầu thực hiện lệnh SQL
            rs = stmt.executeQuery("SELECT * FROM  SANPHAM s JOIN loaiSanPham l ON s.ma_loai_san_pham = l.ma_loai_san_pham "
                    + "WHERE s.gia_san_pham BETWEEN " + min + " and " + max);
            if ((min >= 0 && max < 1380000) || (min > 19700000)) {
                System.out.format("%100s\n", "=> KHÔNG TỒN TẠI SẢN PHẨM TRONG KHOẢNG GIÁ NÀY");
            } else {
                for (int i = 0; i <= 198; i++) {
                    System.out.printf("-");
                }
                System.out.printf("\n");
                System.out.printf("%-25s%-30s%-30s%-70s%-25s%-25s\n", "MÃ SẢN PHẨM", "TÊN SẢN PHẨM", "GIÁ SẢN PHẨM", "MÔ TẢ", "MÀU SẮC", "TÊN LOẠI SẢN PHẨM");
                for (int i = 0; i <= 198; i++) {
                    System.out.printf("-");
                }
                System.out.printf("\n");
                while (rs.next()) {
                    String masp = rs.getString("ma_san_pham");
                    String tensp = rs.getString("ten_san_pham");
                    String gia = rs.getString("gia_san_pham");
                    String mota = rs.getString("mo_ta_san_pham");
                    String mausac = rs.getString("mau_sac");
                    String tenlsp = rs.getString("ten_loai_san_pham");
                    System.out.printf("%-20s%-36s%-20s%-70s%-40s%-20s\n", masp, tensp, gia, mota, mausac, tenlsp);
                }
                for (int i = 0; i <= 198; i++) {
                    System.out.printf("-");
                }
                System.out.printf("\n");
            }
            Option();
        } catch (ClassNotFoundException | InstantiationException | SQLException ex) {
            ex.printStackTrace();
            System.out.println("SQLException: " + ex.getMessage());
        }
    }

    public static void searchByName(String tsp) {
        Statement stmt = null;
        ResultSet rs = null;
        List<SanPham> result = new ArrayList();
        CallableStatement csmt = null;
        try {
            System.out.format("%120s\n", "KẾT QUẢ HIỂN THỊ TẤT CẢ SẢN PHẨM TÌM THEO TÊN SẢN PHẨM");
            Connection conn = connect();
            stmt = conn.createStatement();
            //dùng phương thức executeQuery để yêu cầu thực hiện lệnh SQL
            csmt = conn.prepareCall("{call sp_SearchByName(?)}");
            csmt.setString(1, tsp);
            rs = csmt.executeQuery();
            while (rs.next()) {
                String masp = rs.getString("ma_san_pham");
                String tensp = rs.getString("ten_san_pham");
                int gia = rs.getInt("gia_san_pham");
                String mota = rs.getString("mo_ta_san_pham");
                String mausac = rs.getString("mau_sac");
                String malsp = rs.getString("ma_loai_san_pham");
                SanPham sp = new SanPham(masp, tensp, mota, mausac, malsp, gia);
                result.add(sp);
            }
            if (result.isEmpty()) {
                System.out.format("%100s\n", "=> KHÔNG TỒN TẠI SẢN PHẨM\n");

            } else {
                for (int i = 0; i <= 198; i++) {
                    System.out.printf("-");
                }
                System.out.printf("\n");
                System.out.printf("%-25s%-30s%-30s%-70s%-25s%-25s\n", "MÃ SẢN PHẨM", "TÊN SẢN PHẨM", "GIÁ SẢN PHẨM", "MÔ TẢ", "MÀU SẮC", "MÃ LOẠI SẢN PHẨM");
                for (int i = 0; i <= 198; i++) {
                    System.out.printf("-");
                }
                System.out.printf("\n");
                result.forEach(sp -> {
                    System.out.printf("%-20s%-36s%-20s%-70s%-40s%-25s\n", sp.masp, sp.tensp, sp.gia, sp.mota, sp.mausac, sp.malsp);
                });
                for (int k = 0; k <= 198; k++) {
                    System.out.printf("-");
                }
                System.out.printf("\n");
            }
            Option();
        } catch (ClassNotFoundException | InstantiationException | SQLException ex) {
            ex.printStackTrace();
            System.out.println("SQLException: " + ex.getMessage());
        }
    }

    public static void searchByColor(String color) {
        Statement stmt = null;
        ResultSet rs = null;
        List<SanPham> result = new ArrayList();
        CallableStatement csmt = null;
        try {
            System.out.format("%120s\n", "KẾT QUẢ HIỂN THỊ TẤT CẢ SẢN PHẨM TÌM THEO MÀU SẮC CỦA SẢN PHẨM");
            Connection conn = connect();
            stmt = conn.createStatement();
            //dùng phương thức executeQuery để yêu cầu thực hiện lệnh SQL
            csmt = conn.prepareCall("{call sp_SearchByColor(?)}");
            csmt.setString(1, color);
            rs = csmt.executeQuery();
            while (rs.next()) {
                String masp = rs.getString("ma_san_pham");
                String tensp = rs.getString("ten_san_pham");
                int gia = rs.getInt("gia_san_pham");
                String mota = rs.getString("mo_ta_san_pham");
                String mausac = rs.getString("mau_sac");
                String malsp = rs.getString("ma_loai_san_pham");
                SanPham sp = new SanPham(masp, tensp, mota, mausac, malsp, gia);
                result.add(sp);
            }
            if (result.isEmpty()) {
                System.out.format("%100s\n", "=> KHÔNG TỒN TẠI SẢN PHẨM\n");

            } else {
                for (int i = 0; i <= 198; i++) {
                    System.out.printf("-");
                }
                System.out.printf("\n");
                System.out.printf("%-25s%-30s%-30s%-70s%-25s%-25s\n", "MÃ SẢN PHẨM", "TÊN SẢN PHẨM", "GIÁ SẢN PHẨM", "MÔ TẢ", "MÀU SẮC", "MÃ LOẠI SẢN PHẨM");
                for (int i = 0; i <= 198; i++) {
                    System.out.printf("-");
                }
                System.out.printf("\n");
                result.forEach(sp -> {
                    System.out.printf("%-20s%-36s%-20s%-70s%-40s%-25s\n", sp.masp, sp.tensp, sp.gia, sp.mota, sp.mausac, sp.malsp);
                });
                for (int k = 0; k <= 198; k++) {
                    System.out.printf("-");
                }
                System.out.printf("\n");
            }
            Option();
        } catch (ClassNotFoundException | InstantiationException | SQLException ex) {
            ex.printStackTrace();
            System.out.println("SQLException: " + ex.getMessage());

        }
    }

    public static void addCart(String masp) {
        Statement stmt = null;
        ResultSet rs = null;
        try {
            Connection conn = connect();
            stmt = conn.createStatement();
            String sql = "SELECT ma_gio_hang FROM GIOHANG WHERE ma_nguoi_dung = ' " + ma_user + "'";
            rs = stmt.executeQuery(sql);
            int ma_gio_hang = 0;
            while (rs.next()) {
                ma_gio_hang = rs.getInt("ma_gio_hang");
            }
            int i = 0;
            String mgh = "insert into chitietgiohang values (" + ma_gio_hang + ",'" + masp + "','" + (i + 1) + "')";
            stmt.executeUpdate(mgh);
        } catch (ClassNotFoundException | InstantiationException | SQLException ex) {
            ex.printStackTrace();
            System.out.println("SQLException: " + ex.getMessage());
        }
    }

    public static void detailCart() {
        Statement stmt = null;
        ResultSet rs = null;
        List<SanPham> result = new ArrayList();
        CallableStatement csmt = null;
        try {
            int magiohang = 0;
            Connection conn = connect();
            stmt = conn.createStatement();
            //dùng phương thức executeQuery để yêu cầu thực hiện lệnh SQL
            csmt = conn.prepareCall("{call sp_DetailCart(?)}");
            csmt.setInt(1, ma_user);
            rs = csmt.executeQuery();
            System.out.format("\n%105s\n", "THÔNG TIN CHI TIẾT GIỎ HÀNG");
            int t = 0, tongtien = 0, tongsl = 0;
            for (int i = 0; i <= 180; i++) {
                System.out.printf("-");
            }
            System.out.printf("\n");
            System.out.printf("|\t%-25s%-35s%-35s%-30s%-31s%-16s|\n", "MÃ SẢN PHẨM", "TÊN SẢN PHẨM", "MÀU SẮC", "TÊN LOẠI SẢN PHẨM", "GIÁ SẢN PHẨM", "SỐ LƯỢNG");
            for (int i = 0; i <= 180; i++) {
                System.out.printf("-");
            }
            System.out.printf("\n");
            while (rs.next()) {
                t++;
                magiohang = rs.getInt("ma_gio_hang");
                String masp = rs.getString("ma_san_pham");
                String tensp = rs.getString("ten_san_pham");
                int gia = rs.getInt("gia_san_pham");
                String mausac = rs.getString("mau_sac");
                String tenlsp = rs.getString("ten_loai_san_pham");
                int soluong = rs.getInt("soluong");
                tongsl += soluong;
                tongtien += soluong * gia;
                System.out.printf("|\t%-20s%-40s%-42s%-27s%-32s%-11s|\n", masp, tensp, mausac, tenlsp, gia, soluong);
            }
            for (int i = 0; i <= 180; i++) {
                System.out.printf("-");
            }

            System.out.format("\n\n%113s\n", "ĐƠN HÀNG");
            System.out.format("\n%105s%20s\n", "TỔNG SỐ LƯỢNG: ", tongsl);
            System.out.format("\n%105s%20s\n", "TỔNG TIỀN: ", tongtien);
            System.out.printf("\n");

            if (t > 0) {
                System.out.println("BẠN MUỐN CẬP NHẬT GIỎ HÀNG?");
                System.out.println("1. Sửa sản phẩm");
                System.out.println("2. Xóa sản phẩm");
                System.out.println("3. Đặt hàng");
                System.out.println("4. Thoát");
                int op;
                Scanner sc = new Scanner(System.in);
                op = sc.nextInt();
                switch (op) {
                    case 1:
                        updateQuantity(magiohang);
                        System.out.println("=> CẬP NHẬT THÀNH CÔNG!!!\n");
                        detailCart();
                        break;
                    case 2:
                        deleteProduct(magiohang);
                        System.out.println("=> XÓA SẢN PHẨM THÀNH CÔNG!!!\n");
                        detailCart();
                        break;
                    case 3:
                        orderConfirm();
                        break;
                    case 4:
                        FunctionList();
                        break;
                }
            }
            Option();
        } catch (ClassNotFoundException | InstantiationException | SQLException ex) {
            ex.printStackTrace();
            System.out.println("SQLException: " + ex.getMessage());

        }
    }

    public static void updateQuantity(int mgh) {
        try {
            Statement stmt = null;
            ResultSet rs = null;
            List<SanPham> result = new ArrayList();
            CallableStatement csmt = null;
            int soluong = 0;
            int option;
            String masp = new String();
            Scanner sc = new Scanner(System.in);
            System.out.println("Nhập mã sản phẩm bạn muốn thay đổi: ");
            masp = sc.nextLine();
            System.out.println("Số lượng mà bạn muốn thay đổi: ");
            soluong = sc.nextInt();
            System.out.println("\n");
            Connection conn = connect();
            stmt = conn.createStatement();
            //dùng phương thức executeQuery để yêu cầu thực hiện lệnh SQL
            csmt = conn.prepareCall("{call sp_UpdateQuantity(?,?,?)}");
            csmt.setInt(1, soluong);
            csmt.setInt(2, mgh);
            csmt.setString(3, masp);
            rs = csmt.executeQuery();
        } catch (ClassNotFoundException | InstantiationException | SQLException ex) {
            ex.printStackTrace();
            System.out.println("SQLException: " + ex.getMessage());
        }
    }

    public static void deleteProduct(int magiohang) {
        try {
            Statement stmt = null;
            ResultSet rs = null;
            CallableStatement csmt = null;
            String masp = new String();
            Scanner sc = new Scanner(System.in);
            System.out.println("Nhập mã sản phẩm bạn muốn xóa: ");
            masp = sc.nextLine();
            System.out.println("\n");
            Connection conn = connect();
            stmt = conn.createStatement();
            //dùng phương thức executeQuery để yêu cầu thực hiện lệnh SQL
            csmt = conn.prepareCall("{call sp_DeleteProduct(?,?)}");
            csmt.setString(1, masp);
            csmt.setInt(2, magiohang);
            rs = csmt.executeQuery();
        } catch (ClassNotFoundException | InstantiationException | SQLException ex) {
            ex.printStackTrace();
            System.out.println("SQLException: " + ex.getMessage());
        }
    }

    public static void orderConfirm() {
        try {
            Statement stmt = null;
            Connection conn = connect();
            stmt = conn.createStatement();
            Scanner sc = new Scanner(System.in);
            List<String> result = new ArrayList<>();
            //dùng phương thức executeQuery để yêu cầu thực hiện lệnh SQL
            int tongtien = 0, mgh = 0;
            ResultSet rs = stmt.executeQuery("select c.ma_san_pham, c.soluong, s.gia_san_pham, c.ma_gio_hang\n"
                    + "	from chitietgiohang c  \n"
                    + "	join sanpham s \n"
                    + "    on c.ma_san_pham = s.ma_san_pham \n"
                    + "    join giohang g on g.ma_gio_hang = c.ma_gio_hang \n"
                    + "    join loaisanpham l on s.ma_loai_san_pham = l.ma_loai_san_pham\n"
                    + "    where g.ma_nguoi_dung = " + ma_user);

            while (rs.next()) {
                String msp = rs.getString("ma_san_pham");
                result.add(msp);
                int gia = rs.getInt("gia_san_pham");
                int soluong = rs.getInt("soluong");
                mgh = rs.getInt("ma_gio_hang");
                tongtien += gia * soluong;
            }

            String sql = "insert into hoadon(ngay_lap_hoa_don,tong_tien,ma_nguoi_dung) values (now()," + tongtien + "," + ma_user + ")";
            stmt.executeUpdate(sql);

            String m = "select ma_hoa_don from hoadon";
            ResultSet mhd = stmt.executeQuery(m);

            int ma_hoa_don = 0;
            while (mhd.next()) {
                ma_hoa_don = mhd.getInt("ma_hoa_don");
            }

            for (int i = 0; i < result.size(); i++) {
                String ma_san_pham = result.get(i);
                String cthd = "insert into chitiethoadon values (" + ma_hoa_don + ",'" + ma_san_pham + "')";
                stmt.executeUpdate(cthd);
            }

            String del = "delete from chitietgiohang where ma_gio_hang =" + mgh;
            stmt.executeUpdate(del);
            System.out.println("=> ĐẶT HÀNG THÀNH CÔNG!!!\n");
            System.out.println("\nBẠN CÓ MUỐN XEM ĐƠN HÀNG?\n");
            System.out.println("1. Có");
            System.out.println("2. Không");
            int t = sc.nextInt();
            switch (t) {
                case 1:
                    detailBill();
                    break;
                case 2:
                    FunctionList();
                    break;
            }
        } catch (ClassNotFoundException | InstantiationException | SQLException ex) {
            ex.printStackTrace();
            System.out.println("SQLException: " + ex.getMessage());

        }
    }

    public static void detailBill() {
        Statement stmt = null;
        List<String> result = new ArrayList<String>();
        try {
            Connection conn = connect();
            stmt = conn.createStatement();

            ResultSet mhd = stmt.executeQuery("select * from hoadon");
            while (mhd.next()) {
                String ma_hoa_don = mhd.getString("ma_hoa_don");
                result.add(ma_hoa_don);
            }

            if (result.isEmpty()) {
                System.out.println("\n=> CHƯA CÓ ĐƠN HÀNG !!!\n");
            }
            for (int i = 0; i < result.size(); i++) {
                System.out.format("\n%55s\n", "THÔNG TIN ĐƠN HÀNG");
                String ma_hd = result.get(i);
                ResultSet rs = stmt.executeQuery("select * from "
                        + "chitiethoadon c join sanpham s on c.ma_san_pham = s.ma_san_pham "
                        + "join hoadon h on h.ma_hoa_don = c.ma_hoa_don "
                        + "where h.ma_nguoi_dung = ' " + ma_user + "' and h.ma_hoa_don = ' " + ma_hd + "'");

                System.out.printf("%15s%5s\n", "Mã hóa đơn: ", ma_hd);

                for (int j = 0; j <= 89; j++) {
                    System.out.printf("-");
                }
                System.out.printf("\n");
                System.out.printf("|\t%-25s%-35s%-21s|\n", "MÃ SẢN PHẨM", "TÊN SẢN PHẨM", "GIÁ SẢN PHẨM");
                for (int j = 0; j <= 89; j++) {
                    System.out.printf("-");
                }
                System.out.printf("\n");
                int tongtien = 0;
                Date nlhd = null;
                while (rs.next()) {
                    String masp = rs.getString("ma_san_pham");
                    String tensp = rs.getString("ten_san_pham");
                    int gia = rs.getInt("gia_san_pham");
                    tongtien = rs.getInt("tong_tien");
                    nlhd = rs.getDate("ngay_lap_hoa_don");

                    System.out.printf("|\t%-25s%-35s%-21s|\n", masp, tensp, gia);

                }
                for (int j = 0; j <= 89; j++) {
                    System.out.printf("-");
                }
                System.out.printf("\n|%20s%15s\t\t", "Ngày lập hóa đơn: ", nlhd);
                System.out.printf("%-20s%-22s|\n", "TỔNG TIỀN: ", tongtien);
                for (int j = 0; j <= 89; j++) {
                    System.out.printf("-");
                }
                System.out.printf("\n\n");
            }

            Option();
        } catch (ClassNotFoundException | InstantiationException | SQLException ex) {
            ex.printStackTrace();
            System.out.println("SQLException: " + ex.getMessage());

        }
    }

    public static boolean Login() {
        try {
            Scanner sc = new Scanner(System.in);
            Connection conn = connect();
            String email, password;
            Statement stmt = conn.createStatement();
            System.out.println("\t\tMỜI BẠN ĐĂNG NHẬP");
            System.out.printf("- Nhập vào email của bạn: ");
            email = sc.nextLine();
            System.out.printf("- Nhập vào mật khẩu của bạn: ");
            password = sc.nextLine();
            String sql = "SELECT * FROM NGUOIDUNG WHERE EMAIL = '" + email + "' AND MAT_KHAU  ='" + password + "'  ";
            ResultSet rs = stmt.executeQuery(sql);
            if (rs.next()) {
                ma_user = rs.getInt("ma_nguoi_dung");
                System.out.println("=> ĐĂNG NHẬP THÀNH CÔNG!!!\n");
                return true;
            }
        } catch (ClassNotFoundException | InstantiationException | SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public static boolean Register() {
        try {
            Connection conn = connect();
            Statement stmt = conn.createStatement();
            NguoiDung user = new NguoiDung();
            user.nhap();
            String sql = "INSERT  INTO  NGUOIDUNG(ten_nguoi_dung,so_dien_thoai,email,diachi,mat_khau) VALUES (?,?,?,?,?)";
            PreparedStatement pstm = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pstm.setString(1, user.getTennguoidung());
            pstm.setString(2, user.getSodienthoai());
            pstm.setString(3, user.getEmail());
            pstm.setString(4, user.getDiachi());
            pstm.setString(5, user.getPassword());
            pstm.execute();
            ResultSet rs = pstm.getGeneratedKeys();
            int idValue = 0;
            if (rs.next()) {
                idValue = rs.getInt(1);
            }
            String giohang = "insert into giohang(ma_nguoi_dung) values (" + idValue + ")";
            stmt.executeUpdate(giohang);
        } catch (ClassNotFoundException | InstantiationException | SQLException ex) {
            ex.printStackTrace();
        }
        return true;
    }

    public static void checkLogin() {
        if (Login()) {
            FunctionList();
        } else {
            System.out.println("Email hoặc Password đã sai @@ Vui lòng đăng nhập lại\n");
            checkLogin();
        }
    }

    public static void FunctionList() {
        Scanner sc = new Scanner(System.in);
        System.out.println("\t\tDANH SÁCH CÁC CHỨC NĂNG");
        System.out.println("1. Hiển thị tất cả sản phẩm");
        System.out.println("2. Hiển thị sản phẩm theo loại sản phẩm");
        System.out.println("3. Sắp xếp sản phẩm theo giá tiền (Tăng dần)");
        System.out.println("4. Sắp xếp loại sản phẩm theo giá tiền (Tăng dần)");
        System.out.println("5. Tìm sản phẩm theo khoảng giá");
        System.out.println("6. Tìm sản phẩm theo tên sản phẩm");
        System.out.println("7. Tìm sản phẩm theo màu sắc của sản phẩm");
        System.out.println("8. Xem chi chi tiết sản phẩm");
        System.out.println("9. Xem chi tiết giỏ hàng");
        System.out.println("10. Xem chi tiết đơn hàng\n");
        System.out.println("\tVUI LÒNG CHỌN CHỨC NĂNG BẠN MUỐN THỰC HIỆN");
        int option;
        option = sc.nextInt();
        switch (option) {
            case 1:
                showAll();
                Option();
                break;
            case 2:
                showType();
                break;
            case 3:
                sortPrice();
                break;
            case 4:
                sortPriceByType();
            case 5:
                searchByPrice();
                break;
            case 6:
                String tensp;
                System.out.println("Vui lòng nhập tên sản phẩm bạn cần tìm");
                sc.nextLine();
                tensp = sc.nextLine();
                searchByName(tensp);
                break;
            case 7:
                String mausac;
                System.out.println("Vui lòng chọn màu sắc bạn cần tìm");
                sc.nextLine();
                mausac = sc.nextLine();
                searchByColor(mausac);
                break;
            case 8:
                showAll();
                detailProduct();
                break;
            case 9:
                detailCart();
                break;
            case 10:
                detailBill();
                break;
            default:
                System.out.println("Vui lòng chọn chức năng khác");
                Option();
                break;
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Welcome to LT's Glasses\n");
        System.out.println("MỜI BẠN ĐĂNG NHẬP VÀO TÀI KHOẢN");
        System.out.println("1. Bạn đã có tài khoản ? ");
        System.out.println("2. Chưa có tài khoản - Đăng ký tại đây ? ");
        int luachon;
        luachon = sc.nextInt();
        switch (luachon) {
            case 1:
                checkLogin();
                break;
            case 2:
                System.out.println("\t\tĐĂNG KÝ TÀI KHOẢN");
                if (Register()) {
                    System.out.println("=> ĐĂNG KÝ TÀI KHOẢN THÀNH CÔNG!!!\n");
                    checkLogin();
                }
                break;
        }
    }
}
