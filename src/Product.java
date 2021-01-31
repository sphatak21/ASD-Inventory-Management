public class Product{
	private int id; 
	private String name; 
	private double unit_price;  
	private String brand; 
	private String description; 
	private int stock;
	public Product(int id, String name, double unit_price, String brand, String description, int stock) {
		this.id = id;
		this.name = name;
		this.unit_price = unit_price;
		this.brand = brand;
		this.description = description;
		this.stock = stock;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public double getUnit_price() {
		return unit_price;
	}
	public void setUnit_price(double unit_price) {
		this.unit_price = unit_price;
	}
	public String getBrand() {
		return brand;
	}
	public void setBrand(String brand) {
		this.brand = brand;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getStock() {
		return stock;
	}
	public void setStock(int stock) {
		this.stock = stock;
	} 
	
	
}