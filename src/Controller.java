import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher; 
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
 
@SuppressWarnings("serial")
public class Controller extends HttpServlet{
	private ProductDAO dao; 
	
	public void init() {
		final String url = getServletContext().getInitParameter("JDBC-URL");
		final String username = getServletContext().getInitParameter("JDBC-USERNAME");
		final String password = getServletContext().getInitParameter("JDBC-PASSWORD");
		
		dao = new ProductDAO(url, username, password);
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		final String action = request.getServletPath();
		
		try {
			switch(action) {
				case "/add":
				case "/edit": showEditForm(request, response); break;
				case "/insert": insertProduct(request, response); break; 
				case "/update": updateProduct(request, response); break;
				case "/search": searchProducts(request, response); break; 
				default: viewProducts(dao.getProducts(), request, response); break;
			}
		} catch (SQLException e) {
			throw new ServletException(e);
		}
	}
	private void viewProducts(List<Product> listProducts, HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
		List<Product> products = listProducts; 
		request.setAttribute("products", products);
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("inventory.jsp");
		dispatcher.forward(request, response);
	}
	
	
	private void insertProduct(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
		String name = request.getParameter("name");
		double unit_price = Double.parseDouble(request.getParameter("unit_price"));
		String brand = request.getParameter("brand");
		String description = request.getParameter("description");
		int stock = Integer.parseInt(request.getParameter("stock"));
		
		dao.insertProduct(name, unit_price, brand, description, stock);
		response.sendRedirect(request.getContextPath() + "/");
	}
	
	private void updateProduct(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
		final String action = request.getParameter("action") != null
			? request.getParameter("action")
			: request.getParameter("submit").toLowerCase(); 
		final int id = Integer.parseInt(request.getParameter("id"));
		
		Product product = dao.getProduct(id);
		switch(action) {
			case "save": 
				String name = request.getParameter("name");
				double unit_price = Double.parseDouble(request.getParameter("unit_price"));
				String brand = request.getParameter("brand");
				String description = request.getParameter("description");
				int stock = Integer.parseInt(request.getParameter("stock"));
				
				product.setName(name);
				product.setUnit_price(unit_price);
				product.setBrand(brand);
				product.setDescription(description);
				product.setStock(stock);
				break;
			case "delete": deleteProduct(id, request, response); return;
		}
		dao.updateProduct(product);
		response.sendRedirect(request.getContextPath() + "/");
	}
	private void showEditForm(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
		try {
			final int id = Integer.parseInt(request.getParameter("id"));
			
			Product product = dao.getProduct(id);
			request.setAttribute("product", product);
		} catch (NumberFormatException e) {
			
		} finally {
			RequestDispatcher dispatcher = request.getRequestDispatcher("productform.jsp");
			dispatcher.forward(request, response);
		}
	}
	private void deleteProduct(final int id, HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
		dao.deleteProduct(dao.getProduct(id));
		response.sendRedirect(request.getContextPath() + "/");
	}
	private void searchProducts(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException{
		final String searchParameter = request.getParameter("searchParameter");
		List <Product> searchResults = new ArrayList<>(); 
		String keyword = null;
		double keynum = -1; 
		try {
			keynum = Integer.parseInt(request.getParameter("keyword"));
			if(searchParameter.equals("ID")) {
				searchResults.add(dao.getProduct((int) keynum));
			}else {
				searchResults = dao.getProducts(keynum);
			}
		}catch (NumberFormatException e) {
			keyword = request.getParameter("keyword");
			if(searchParameter.equals("Name")) {
				searchResults = dao.getProducts(keyword, true);
			}else {
				searchResults = dao.getProducts(keyword, false);
			}
		}finally {
			viewProducts(searchResults, request, response);
		}
		
	}
	
}
