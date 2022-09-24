/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package qtdl;

import java.util.Scanner;

/**
 *
 * @author Tran
 */
public class NguoiDung {

    public String tennguoidung, sodienthoai, email, password, diachi;

    public NguoiDung() {
        tennguoidung = new String();
        sodienthoai = new String();
        email = new String();
        password = new String();
        diachi = new String();
    }

    public NguoiDung(String tennguoidung, String sodienthoai, String email, String password, String diachi) {
        this.tennguoidung = tennguoidung;
        this.sodienthoai = sodienthoai;
        this.email = email;
        this.password = password;
        this.diachi = diachi;
    }

    public NguoiDung(NguoiDung tk) {
        tennguoidung = new String(tk.tennguoidung);
        sodienthoai = new String(tk.sodienthoai);
        email = new String(tk.email);
        password = new String(tk.password);
        diachi = new String(tk.diachi);
    }

    public void nhap() {
        Scanner sc = new Scanner(System.in);
        System.out.println("- Nhap ten cua ban: ");
        tennguoidung = sc.nextLine();
        System.out.println("- Nhap so dien thoai cua ban: ");
        sodienthoai = sc.nextLine();
        System.out.println("- Nhap dia chi cua ban: ");
        diachi = sc.nextLine();
        System.out.println("- Nhap email cua ban:");
        email = sc.nextLine();
        System.out.println("- Nhap mat khau cua ban: ");
        password = sc.nextLine();
    }

    public String getTennguoidung() {
        return tennguoidung;
    }

    public String getSodienthoai() {
        return sodienthoai;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getDiachi() {
        return diachi;
    }

    public void setTennguoidung(String tennguoidung) {
        this.tennguoidung = tennguoidung;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setSodienthoai(String sodienthoai) {
        this.sodienthoai = sodienthoai;

    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setDiachi(String diachi) {
        this.diachi = diachi;
    }
}
