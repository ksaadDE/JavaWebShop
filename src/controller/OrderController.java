package controller;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import com.google.gson.Gson;

import dBClasses.Order;
import dBClasses.User;

/**
 * 
 * @author ksaadDE
 */
@ManagedBean(name="OrderController")
@ViewScoped
public class OrderController implements Serializable { 
	/*** Autogen SerialVersionUID */
	private static final long serialVersionUID = -3030550462603441333L;
	
	/*** List of Orders */
	private List<Order> orders = new ArrayList<Order>();
	
	public OrderController () {
	
	}
	
	/**
	 * Gets the Orders from DB
	 * @return List[Orders]
	 * @throws SQLException
	 */
	public static List<Order> getOrdersFromDB() throws SQLException {
		// Init empty Orders List
		List<Order> orders = new ArrayList<Order>();
		
		// Get Shop connection
		Shop.getInstance();
		Connection con = Shop.getConnection();
		
		// Prepare Statement, for Escaping etc (in this case no escaping due to the fact we are getting ALL entries of Orders Table)
		PreparedStatement stmt = con.prepareStatement("SELECT id, userid, shoppingCart, createdate, status FROM Shop.orders;");
		
		// Getting the ResultSet -> List of Results (Orders)
		ResultSet rs = stmt.executeQuery();
		
		// Looping through the RS and getting each Order
		while(rs.next()) {
			// Creating a Order Object
			Order order = new Order();
			
			// Inserting the Data of this Order
			order.setId(rs.getInt("id"));
			order.setUserid(rs.getInt("userid"));
			order.setUser(UserController.getUser(order.getUserid()));
			order.setShoppingCart(shoppingCartFromJson(rs.getString("shoppingCart")));
			order.setCreateDate(rs.getDate("createdate"));
			order.setStatus(rs.getInt("status"));
			
			// Adding the Object to List
			orders.add(order);
		}
		
		// Afterwards Returning the Orders
		return orders;
	}
	
	/***
	 * Gets Order by User<br>
	 * <b>Relation: </b> ManyToOne
	 * @param userid
	 * @return List[Order] orderList (found Orders)
	 * @throws SQLException
	 */
	public static List<Order> getOrdersFromDB(int userid) throws SQLException {
		// Initialize a empty orders List
		List<Order> orders = new ArrayList<Order>();
		
		// "Getting" the connection to DB
		Shop.getInstance();
		Connection con = Shop.getConnection();
		
		// Prepare Selection of specific Orders in Orders Table
		PreparedStatement stmt = con.prepareStatement("SELECT id, userid, shoppingCart, createdate, status FROM Shop.orders WHERE userid=?;");
		
		// Replace placeholder with userid
		stmt.setInt(1, userid);
		
		// Execute Query and get the ResultSet
		ResultSet rs = stmt.executeQuery();
	
		// Looping through the ResultSets
		while(rs.next()) {
			// Creating a Order Object
			Order order = new Order();
			
			// Filling it with the DB Data
			order.setId(rs.getInt("id"));
			order.setUserid(rs.getInt("userid"));
			order.setUser(UserController.getUser(order.getUserid()));
			order.setShoppingCart(shoppingCartFromJson(rs.getString("shoppingCart")));
			order.setCreateDate(rs.getDate("createdate"));
			order.setStatus(rs.getInt("status"));
			
			// Adding the Order to the List
			orders.add(order);
		}
		
		// Close the Connection
		con.close();
		
		// Return the List of Orders
		return orders;
	}
	
	/**
	 * Saving all Orders to DB of a List
	 * @param orders - a List of Orders which needs to be saved!
	 * @throws SQLException
	 */
	public static void saveOrdersToDB(List<Order> orders) throws SQLException {
		// Getting the DB Connection
		Shop.getInstance();
		Connection con = Shop.getConnection();
		
		// Looping through the Orders
		for(Order order : orders) {
			// Saving each by a UPDATE query
			
			// Prep. Query
			PreparedStatement stmt = con.prepareStatement("UPDATE Shop.orders SET userid=?, shoppingCart=?, createdate=?, status=? WHERE id=?");
			
			// Inserting the Data
			stmt.setInt(1, order.getId());
			stmt.setString(2, shoppingCartToJson(order.getShoppingCart()));
			stmt.setDate(3, order.getCreateDate());
			stmt.setInt(4, order.getStatus());
			
			// executing the Query / the Update
			stmt.executeUpdate();
		}
		
		// Close the connection
		con.close();
	}
	
	/**
	 * Loads a Order
	 * @param id
	 * @return a Order Object out of DB by id
	 * @throws SQLException
	 */
	public static Order loadOrder(int id) throws SQLException {
		// Get the Connection
		Shop.getInstance();
		Connection con = Shop.getConnection();
		
		// Preparing the Statement / Selecting Order by id
		PreparedStatement stmt = con.prepareStatement("SELECT id, userid, shoppingCart, createdate, status FROM Shop.orders WHERE id=?");
		
		// Executing it and getting the RS
		ResultSet rs = stmt.executeQuery();
		
		
		// Initialize a Order
		Order order = new Order();
		
		// Checking ResultSet
		if(rs.next()) {
			// Filling the Order
			order.setId(rs.getInt("id"));
			order.setUserid(rs.getInt("userid"));
			order.setUser(UserController.getUser(order.getUserid()));
			order.setShoppingCart(shoppingCartFromJson(rs.getString("shoppingCart")));
			order.setCreateDate(rs.getDate("createdate"));
			order.setStatus(rs.getInt("status"));
		}
		
		// Closing the Connection
		con.close();
		
		// Returning the (loaded) Order
		return order;
	}
	
	/**
	 * Saves a Order given as Order in order
	 * @param order Order to save
	 * @throws SQLException
	 */
	public static void saveOrder(Order order) throws SQLException {
		// Getting the DB Connection
		Shop.getInstance();
		Connection con = Shop.getConnection();
		
		// Preparing the Statement
		PreparedStatement stmt = con.prepareStatement("UPDATE Shop.orders SET shoppingCart=?, createdate=?, status=?, shoppingCart=?, userid=? WHERE id=?");
		
		// Inserting the values
		stmt.setString(1, shoppingCartToJson(order.getShoppingCart()));
		stmt.setDate(2, order.getCreateDate());
		stmt.setInt(3, order.getStatus());
		stmt.setString(4, OrderController.shoppingCartToJson(order.getShoppingCart()));
		stmt.setInt(6, order.getId());

		// Tries to save a Order
		try {
			// Get User from Order
			User k = order.getUser();
			
			// When User is null / not set
			if (k == null) {
				// When the Order hasn't a User assigned
				System.out.println("User, for order saving, doesn't exist!");
				return;
			}
			
			// Sets the userid which is usually needed for the Statement above
			stmt.setInt(5, k.getId());
			
			// Doing the Update
			stmt.executeUpdate();
			
		} catch (Exception ex) {
			// Exception
			ex.printStackTrace();
		}
		
		// Closing the Connection
		con.close();
	}
	
	/**
	 * Adds a Order
	 * @param userid  [for which user?]
	 * @param shoppingCart ShoppingCart (Object)
	 * @param status Order Status / Delivery Status
	 * @return
	 */
	public static boolean addOrder(int userid, ShoppingCart shoppingCart, int status) {
		// Tries to add a Order
		try {
			System.out.println("Adding Order with cartItemsCount: " + shoppingCart.getItemCount());
			
			// Gets the Connection
			Shop.getInstance();
			Connection connect = Shop.getConnection();

			// prepares the Statement
		    String query = "insert into Shop.orders (userid, shoppingCart, createdate,status) VALUES (?,?,?,?)";
		    PreparedStatement preparedStmt = connect.prepareStatement(query);
		   
		    // Values to Query
		    preparedStmt.setInt   (1, userid);
		    preparedStmt.setString   (2, shoppingCartToJson(shoppingCart));
		    preparedStmt.setDate   (3,  new java.sql.Date(new java.util.Date().getTime()));
		    preparedStmt.setInt   (4, status);
		    
		    // Getting the Result after Update
		    int result = preparedStmt.executeUpdate();
		     
		    // Closing the Connection
		    connect.close();
		    
		    // Return True when Order got added :-)
		    return (result > 0);
		} catch (Exception e) {
			//Exception
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * Adds a Order to the current loggedin User
	 * @param shoppingCart ShoppingCart with the Items to add
	 * @return
	 */
	public static boolean addCurrentUserOrder(ShoppingCart shoppingCart) {
		// Checking if the User is not loggedin
		if(LoginController.getSessionUser() == null) {
			// If not it's not advised to add a Order assigned to null?
			System.out.println("ERROR: addCurrentUserOrder, user not loggedin!");
			return false;
		}
		
		// Tries to add the Order
		try {
			// get the current User id from LoginController
			int currentUserId = LoginController.getSessionUser().getId();
			
			// adding the Order
			if(addOrder(currentUserId, shoppingCart, 1))
				return true;
		} catch (Exception e) {
			// Exception
			e.printStackTrace();
		}
		// when not true, it must be false ;)
		return false;
	}
	
	/***
	 * Loads the Orders from the current User and putting it to this.orders..
	 */
	public void loadCurrentUserOrders() {
		// When the Session with the User is not existing
		if(LoginController.getSessionUser() == null)
			return;
		
		// Tries to load the Orders by User
		try {
			// getting the current User id from LoginController
			int currentUserId = LoginController.getSessionUser().getId();
			
			// Setting the Orders to the Orders of the current User
			this.orders = getOrdersFromDB(currentUserId);
		} catch (Exception e) {
			// Exception
			e.printStackTrace();
		}
	}
	
	/**
	 * Getting the Orders of a User
	 * @param userid
	 * @return
	 */
	public static List<Order> getOrdersByUser(int userid) {
		// Init empty List
		List<Order> orders = new ArrayList<Order>();
		
		// Tries to load the Orders from DB
		try {
			orders = getOrdersFromDB(userid);
		} catch (SQLException e) {
			// Exception
			e.printStackTrace();
		}
		
		// Returning the List of Orders
		return orders;
	}

	/**
	 * Gets the current "local" Orders
	 * @return
	 */
	public List<Order> getOrders() {
		
		// If null or empty
		if (this.orders == null || this.orders.size() == 0) {
			// Try to get the Orders from DB
			try {
				// set the Orders
				this.orders = getOrdersFromDB();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		// Returning the Orders
		return this.orders;
	}

	/**
	 * Get Orders by user (lodas them then returning the current (Order) List)
	 * @return
	 */
	public List<Order> getOrdersByUser() {
		this.loadCurrentUserOrders();
		//this.orders.forEach((x) -> { System.out.println(x.getUser().getName()); });
		return orders;
	}
	
	/**
	 * Sets the current, "local" Orders List
	 * @param orders
	 */
	public void setOrders(List<Order> orders) {
		this.orders = orders;
	}
	
	/**
	 * Converts the shoppingCart to JSON <3
	 * @param cart ShoppingCart <--
	 * @return String jsonShoppingCart
	 */
	public static String shoppingCartToJson(ShoppingCart cart) {
		// Init empty String
		String json = "";
		
		// Google JSON tries to convert the ShoppingCart Object to JSON
		// A alternative way would be generating the needed String manually... but that's not needed
		try {
			// Init Google JSON
			Gson gson = new Gson();
			
			// Convertion and JsonText to json String
			json = gson.toJson(cart);
		} catch (Exception e) {
			// Exception
			e.printStackTrace();
		}
		
		// Returning the JSON String
		return json;
	}
	
	/**
	 * Creates a ShoppingCart out of JSON STring
	 * @param json JSONString
	 * @return ShoppingCart
	 */
	public static ShoppingCart shoppingCartFromJson(String json) {
		// ShoppingCart Init
		ShoppingCart res = null;
		
		// Tries to init GJSON and convert the String(JSON) to Object
		try {
			// Init GJSON
			Gson gson = new Gson();
			
			// Try to convert
			res = gson.fromJson(json, ShoppingCart.class);
		} catch (Exception e) {
			// Exception
			e.printStackTrace();
		}
		
		// Returning the ShoppingCart Object (even if it's null!)
		return res;
	}
	
	
	/**
	 * Gets Orders Amount by onlySuccess (Order Status -> successful or not)
	 * @param onlysuccess Returning only the successful Orders?
	 * @return
	 * @throws SQLException 
	 */
	public static int getOrdersAmount(boolean onlySuccess) throws SQLException {
		// Gets static ShopInstance
		Shop.getInstance();
		// Gets the connection & init PreparedStatement && init PreparedStatement
		Connection connect = Shop.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		// Tries to select by onlySuccess Orders from DB (preparing statement and setting values)
		try {
			if (!onlySuccess) {
				// Not successful Orders amount
				pstmt = connect.prepareStatement("select count(id) from Shop.orders");
			} else {
				// ONLY Successful Orders amount
				pstmt = connect.prepareStatement("select count(id) from Shop.orders WHERE status=?");
				pstmt.setInt(1, 4);
			}
			
			// Run the Query
			rs = pstmt.executeQuery();
			
		} catch (SQLException e) {
			// Exception
			e.printStackTrace();
		}

		
		// Tries to get the Amount (Result) and returning it
		try {
			// Goes through RS if existing
			if(rs.next()) {
				// Returns the Amount
				return(rs.getInt(1));
			}
			
			// Closing the connections
			pstmt.close();
			rs.close();
			connect.close();
			
		} catch (SQLException e) {
			// Exception
			e.printStackTrace();
		}
		
		// Returns 0 if nothing changed or it's really empty
		return 0;
	}
}
