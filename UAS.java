package com.program;

import java.sql.Connection;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class DBGudang {
    private static final String JBDC_DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String DB_URL = "jdbc:mysql://localhost/gudang"; // "gudang" => nama database
    private static final String USER = "root";
    private static final String PASS = "";
    private Connection conn;
    private Statement stmt;
    private ResultSet rs;
    private ArrayList<Category> products;

    static InputStreamReader inputStreamReader = new InputStreamReader(System.in);
    static BufferedReader input = new BufferedReader(inputStreamReader);

    // Membuat Koneksi dengan database
    public DBGudang() throws Exception {
        Class.forName(JBDC_DRIVER);
        this.conn = DriverManager.getConnection(DB_URL, USER, PASS);
        this.stmt = conn.createStatement();
        this.products = new ArrayList<Category>();
    }

    public ArrayList<Category> getProducts() {
        return products;
    }

    // Insert data ke table Category
    public void insertCategory(String id, String name) throws Exception {
        String sql = "INSERT into Category (id, name) VALUES (%s, '%s')";
        sql = String.format(sql, id, name);
        this.stmt.execute(sql);
    }

    public void input(Boolean function) throws Exception {
        int id = 0;
        if (function) {
            System.out.println("Menu Input Barang");
        } else {
            System.out.println("Menu Update Barang");
            System.out.print("Masukkan ID Barang yang akan diubah : ");
            id = Integer.parseInt(input.readLine());
        }
        int category_id, price, status;
        String name, description;
        System.out.println("Pilih Category: ");
        System.out.println("1. Minuman");
        System.out.println("2. Makanan");
        System.out.println("3. Pakaian");
        System.out.println("4. Buah-buahan");
        System.out.print("Pilih: ");
        category_id = Integer.parseInt(input.readLine());
        System.out.print("Masukkan Nama Barang: ");
        name = input.readLine();
        System.out.print("Masukkan Deskripsi Barang: ");
        description = input.readLine();
        System.out.print("Masukkan Harga Barang: ");
        price = Integer.parseInt(input.readLine());
        System.out.println("Pilih Status Barang: ");
        System.out.println("1. Tersedia");
        System.out.println("2. Tidak Tersedia");
        System.out.print("Pilih: ");
        status = Integer.parseInt(input.readLine());
        if (function) {
            insertProducts(category_id, name, description, price, status);
        } else {
            updateProducts(id, category_id, name, description, price, status);
        }
    }

    // Insert data ke table Products
    public void insertProducts(int category_id, String name, String description, int price, int status)
            throws Exception {
        String sql = "INSERT INTO Products (category_id, name, description, price, status) values (%d, '%s', '%s', %d, %b)";
        sql = String.format(sql, category_id, name, description, price, status);
        this.stmt.execute(sql);
    }

    // Update data record pada table Product
    public void updateProducts(int id, int category_id, String name, String description, int price, int status)
            throws Exception {
        String sql = "UPDATE Products SET category_id = %d, name = '%s', description = '%s', price = %d, status = %b WHERE id = %s";
        sql = String.format(sql, category_id, name, description, price, status, id);
        this.stmt.execute(sql);
    }

    // Menghapus data record pada table Product
    public void deleteProducts() throws Exception {
        System.out.println("Menu Hapus Barang");
        System.out.print("Masukkan ID Barang yang akan dihapus : ");
        int id = Integer.parseInt(input.readLine());
        String sql = "DELETE FROM Products WHERE id = %s";
        sql = String.format(sql, id);
        this.stmt.execute(sql);
    }

    // Menampilkan data record pada table Product
    public void selectProducts() throws Exception {
        String sql = "SELECT * FROM Products";
        this.rs = this.stmt.executeQuery(sql);
        while (rs.next()) {
            System.out.println("ID: " + rs.getInt("id"));
            System.out.println("Category ID: " + rs.getInt("category_id"));
            System.out.println("Name: " + rs.getString("name"));
            System.out.println("Description: " + rs.getString("description"));
            System.out.println("Price: " + rs.getInt("price"));
            String status = rs.getBoolean("status") ? "Tersedia" : "Tidak Tersedia";
            System.out.println("Status: " + status);
            System.out.println("===================================");
        }
    }
}