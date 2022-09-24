/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package qtdl;

import java.util.Scanner;

public class SanPham {

    public String masp, tensp, mota, mausac, malsp;
    public int gia;

    //Ham xay dung
    public SanPham() {
        masp = new String();
        tensp = new String();
        mota = new String();
        mausac = new String();
        malsp = new String();
        gia = 0;
    }

    public SanPham(SanPham s) {
        masp = new String(s.masp);
        tensp = new String(s.tensp);
        mota = new String(s.mota);
        mausac = new String(s.mausac);
        malsp = new String(s.malsp);
        gia = s.gia;
    }

    public SanPham(String masp, String tensp, String mota, String mausac, String malsp, int gia) {
        this.masp = masp;
        this.tensp = tensp;
        this.mota = mota;
        this.mausac = mausac;
        this.malsp = malsp;
        this.gia = gia;
    }

    SanPham(String masp, String tensp, String mausac, int gia, int soluong, String tenlsp) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    

   

}