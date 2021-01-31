import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.ArrayList; 

import java.sql.ResultSet;

public class ProductDAO {
	private final String url; 
	private final String username; 
	private final String password;
	public ProductDAO(String url, String username, String password) {
		this.url = url;
		this.username = username;
		this.password = password;
	} 
	
	private Connection getConnection() throws SQLException {
		final String driver = "com.mysql.cj.jdbc.Driver";
		
		try {
			Class.forName(driver);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return DriverManager.getConnection(url, username, password);
	}
	public Product getProduct(int id) throws SQLException{
		final String sql = "SELECT * FROM products WHERE product_id = ?";
		
		Product product = null;
		Connection conn = getConnection(); 
		PreparedStatement pstmt = conn.prepareStatement(sql);
		
		pstmt.setInt(1, id);
		ResultSet rs = pstmt.executeQuery(); 
		
		if(rs.next()) {
			String name = rs.getString("name");
			double unit_price = rs.getDouble("unit_price");
			String brand = rs.getString("brand");
			String description = rs.getString("description");
			int stock = rs.getInt("stock");
			
			product = new Product(id, name, unit_price, brand, description, stock);
		}
		rs.close(); 
		pstmt.close(); 
		conn.close();
		return product;
	}

	public List<Product> getProducts() throws SQLException{
		final String sql = "SELECT * FROM products ORDER BY product_id ASC";
		
		List<Product> products = new ArrayList<>();
		Connection conn = getConnection();
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery(sql);
		
		while(rs.next()) {
			int id = rs.getInt("product_id");
			String name = rs.getString("name");
			double unit_price = rs.getDouble("unit_price");
			String brand = rs.getString("brand");
			String description = rs.getString("description");
			int stock = rs.getInt("stock");
			
			products.add(new Product(id, name, unit_price, brand, description, stock));
		}
		rs.close();
		stmt.close();
		conn.close();
		
		return products;
	}
	
	public List<Product> getProducts(String s, boolean isName) throws SQLException{
		final String sql; 
		if(isName) {
			sql = "SELECT * FROM products WHERE name = ? ORDER BY product_id ASC";
		}else {
			sql = "SELECT * FROM products WHERE brand = ? ORDER BY product_id ASC";
		}
		
		List<Product> products = new ArrayList<>();
		Connection conn = getConnection();
		PreparedStatement pstmt = conn.prepareStatement(sql);

		pstmt.setString(1, s);
		ResultSet rs = pstmt.executeQuery(); 
		
		while(rs.next()) {
			int id = rs.getInt("product_id");
			String name = rs.getString("name");
			double unit_price = rs.getDouble("unit_price");
			String brand = rs.getString("brand");
			String description = rs.getString("description");
			int stock = rs.getInt("stock");
			
			products.add(new Product(id, name, unit_price, brand, description, stock));
		}
		rs.close(); 
		pstmt.close(); 
		conn.close(); 
		
		return products;
	}
	public List<Product> getProducts(double price) throws SQLException{
		final String sql = "SELECT * FROM products WHERE unit_price = ? ORDER BY product_id ASC";
		
		List<Product> products = new ArrayList<>();
		Connection conn = getConnection();
		PreparedStatement pstmt = conn.prepareStatement(sql);
		
		pstmt.setDouble(1, price);
		ResultSet rs = pstmt.executeQuery(); 
		
		while(rs.next()) {
			int id = rs.getInt("product_id");
			String name = rs.getString("name");
			double unit_price = rs.getDouble("unit_price");
			String brand = rs.getString("brand");
			String description = rs.getString("description");
			int stock = rs.getInt("stock");
			
			products.add(new Product(id, name, unit_price, brand, description, stock));
		}
		rs.close(); 
		pstmt.close(); 
		conn.close(); 
		
		return products;
		
		
	}
	public boolean insertProduct(String name, double unit_price, String brand, String description, int stock) throws SQLException{
		final String sql = "INSERT INTO products (name, unit_price, brand, description, stock) VALUES (?, ?, ?, ?, ?) ";
		
		Connection conn = getConnection(); 
		PreparedStatement pstmt = conn.prepareStatement(sql);
		
		pstmt.setString(1, name);
		pstmt.setDouble(2, unit_price);
		pstmt.setString(3, brand);
		pstmt.setString(4, description);
		pstmt.setInt(5, stock);
		int affected = pstmt.executeUpdate(); 
		
		pstmt.close(); 
		conn.close();
		
		return affected == 1; 
	}

	public boolean updateProduct(Product product) throws SQLException {
		final String sql = "UPDATE products SET name = ?, unit_price = ?, brand = ?, description = ?, stock = ? " + "WHERE product_id = ?";
		
		Connection conn = getConnection(); 
		PreparedStatement pstmt = conn.prepareStatement(sql);
		
		pstmt.setString(1, product.getName());
		pstmt.setDouble(2,  product.getUnit_price());
		pstmt.setString(3, product.getBrand());
		pstmt.setString(4, product.getDescription());
		pstmt.setInt(5, product.getStock());
		pstmt.setInt(6, product.getId());
		
		int affected = pstmt.executeUpdate();
		
		pstmt.close(); 
		conn.close();
		
		return affected == 1; 
		
	}
	
	public boolean deleteProduct(Product product) throws SQLException {
		final String sql = "DELETE FROM products WHERE product_id = ?";
		
		Connection conn = getConnection();
		PreparedStatement pstmt = conn.prepareStatement(sql);
		
		pstmt.setInt(1, product.getId());
		int affected = pstmt.executeUpdate();
		
		return affected == 1; 
		
		
	}
}