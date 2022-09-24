/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package qtdl;

import java.util.Scanner;

public class LoaiSanPham {
    public String malsp, tenloaisp;
    
    //Ham xay dung
    public LoaiSanPham(){
        malsp = new String();
        tenloaisp = new String();
    }
    
     public LoaiSanPham(LoaiSanPham l){
        malsp = new String(l.malsp);
        tenloaisp = new String(l.tenloaisp);
    }
     
    
}