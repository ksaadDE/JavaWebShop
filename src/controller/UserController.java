package controller;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import dBClasses.Order;
import dBClasses.User;

/**
 * UserController manages Users
 * @author ksaadDE
 */
@ManagedBean(name="UserController")
@ViewScoped
public class UserController implements Serializable { 
	/** autogen serialVersionUID */
	private static final long serialVersionUID = -3030550462603441333L;
	
	public UserController () {
	
	}
	
	
	/**
	 * creates a User
	 * @param name String
	 * @param plainPW String
	 * @param isAdmin Boolean
	 * @return The new <b>User</b> Object
	 * @throws SQLException
	 */
	public static User createUser (String name, String plainPW, boolean isAdmin) throws SQLException {
		// New User Obj
		User k = new User();
		
		// Assign Vars
		k.setName(name);
		k.setSafePassword(plainPW);
		k.setIsadmin(isAdmin);
		
		// Putting the User into the DB and get the id of the new User
		int id = createDBUser(k);
		
		k.setId(id);
		// when not created, return nill
		if(id <= 0)
			return null;
		
		// when finished return the Object
		return k;
	}
	
	/**
	 * Creates the DB User
	 * @param k Data of the User as <b>User Object</b>
	 * @return id or -1
	 */
	public static int createDBUser(User k) {
		// Tries to insert the User k, then returning the new id
		try {
			// Gets the connection
			Connection connect = Shop.getConnection();
	
			// Prepares the Insert Query
			PreparedStatement pstmt = connect.prepareStatement(
					"INSERT INTO users (username,password,isadmin) VALUES (?,?,?)",  // Query
					Statement.RETURN_GENERATED_KEYS // Return the new Keys Option
			);
			
			// Sets the Values
			pstmt.setString(1, k.getName());
			pstmt.setString(2, k.getPass());
			pstmt.setBoolean(3, k.isAdmin());
			
			// Executes the Insert / Query
			pstmt.executeUpdate();
			
			// init id
			int id = -1;
			
			// return the new Id
			ResultSet rs = pstmt.getGeneratedKeys();
			
			// next Element in RS
			if (rs.next()) {
				// the first element in rs.getInt is the new id
				id = rs.getInt(1);
			}
			
			// Closing the connection(s)
			pstmt.close();
			connect.close();
			
			// Returning the id
			return id;
		} catch (Exception e) {
			// Exception
			e.printStackTrace();
			return -1;
		}
	}
	
	/**
	 * Gets all Users 
	 * @return A list with all Users List[User]
	 * @throws SQLException
	 */
	public static List<User> getAllUser() throws SQLException {
		// Initialize empty userList
		List<User> userList = new ArrayList<User>();
		
		// Gets the connection
		Shop.getInstance();
		Connection connect = Shop.getConnection();

		// Prepares the Query
		PreparedStatement pstmt = connect.prepareStatement("select id, username, password, isadmin from Shop.users");
		
		// Executes the Query
		ResultSet rs = pstmt.executeQuery();
		
		// Looping through the RS elements
		while (rs.next()) {
			// new User Obj
			User user = new User();
			
			// Inserting the DB Vals from RS to the new User Obj
			user.setId(rs.getInt("id"));
			user.setName(rs.getString("username"));
			user.setPass(rs.getString("password"));
			user.setIsadmin(rs.getBoolean("isadmin"));
			
			// Adding the User to the userList
			userList.add(user);
		}
		
		// Closing the connections
		rs.close();
		pstmt.close();
		connect.close();
		
		// Returning the userList
		return userList;
	}
	
	/**
	 * Gets a User by id
	 * @param id DB userid
	 * @return
	 * @throws SQLException
	 */
	public static User getUser (int id) throws SQLException {
		// Gets the connection
		Connection connect = Shop.getConnection();
		
		// Prepares the Query
		PreparedStatement pstmt = connect.prepareStatement("select id, username, password, isadmin from Shop.users WHERE id=?");
		// Inserting the id to query
		pstmt.setInt(1, id);
		
		// Executes the Query and fetches the RS
		ResultSet rs = pstmt.executeQuery();
		
		// init new User Obj
		User user = new User();
		
		// Next result (first)
		if(rs.next()) {
			// Setting the user Obj data
			user.setId(rs.getInt("id"));
			user.setName(rs.getString("username"));
			user.setPass(rs.getString("password"));
			user.setIsadmin(rs.getBoolean("isadmin"));
		}
		
		// Closing the connections
		rs.close();
		pstmt.close();
		connect.close();
		
		// return the "fetched" User
		return user;
	}
	
	/**
	 * Gets a User by username
	 * @param username
	 * @return User
	 * @throws SQLException
	 */
	public static User getUser (String username) throws SQLException {
		// Gets the connection
		Connection connect = Shop.getConnection();

		// Prepares select Query
		PreparedStatement pstmt = connect.prepareStatement("select id, username, password, isadmin from Shop.users WHERE username=?");
		
		// Sets the username in Query
		pstmt.setString(1, username);
		
		// Executing the Query
		ResultSet rs = pstmt.executeQuery();
		
		// Initialize new User Obj
		User user = new User();
		
		// Next RS (first)
		if(rs.next()) {
			// Setting the User vals
			user.setId(rs.getInt("id"));
			user.setName(rs.getString("username"));
			user.setPass(rs.getString("password"));
			user.setIsadmin(rs.getBoolean("isadmin"));
		}
		
		// Closes the connections
		rs.close();
		pstmt.close();
		connect.close();
		
		// returns the User
		return user;
	}
	
	/**
	 * Updates a User by User
	 * @param user A user which needs a DB Update (needs id)
	 * @return Did it worked?
	 */
	public static boolean updateUser (User user) {
		try {
			// Gets a DB connection
			Connection connect = Shop.getConnection();
	
			// Prepares the query
			PreparedStatement pstmt = connect.prepareStatement("UPDATE users SET username=?, password=?, isadmin=? WHERE id=?");
			
			// Values to Query
			pstmt.setString(1, user.getName());
			pstmt.setString(2, user.getPass());
			pstmt.setBoolean(3, user.isAdmin());
			pstmt.setInt(4, user.getId());
			
			// Executes the Update
			pstmt.executeUpdate();
	
			// Closes the connections
			pstmt.close();
			connect.close();
			
			// returns if it worked
			return true;
		} catch (Exception e) {
			// Exception
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * Gets the Amount of Users (globally in the DB) 
	 * @return
	 * @throws SQLException 
	 */
	public static int getUsersAmount() throws SQLException {
		// Gets the connection to DB
		Connection connect = Shop.getConnection();
		
		// Prepares Query
		PreparedStatement pstmt = connect.prepareStatement("select count(id) from Shop.users");
		
		// Executes the Query, Stores the ResultSet
		ResultSet rs = pstmt.executeQuery();
		
		// next (first) RS
		if(rs.next()) {
			
			// Returns the Amount of Articles
			return(rs.getInt(1));
		}

		
		// Closing the Connections
		rs.close();
		pstmt.close();
		connect.close();
	
		// Returning Amount of Users if no ones
		return 0;
	}
	
	/**
	 * Gets the Orders By User<br>
	 * <b>Relation:</b> OneToMany
	 * @return
	 */
	public static List<Order> getOrdersByUser() { 
		// gets the currentSession User
		User sessionUser = LoginController.getSessionUser();
		
		// Empty List if sessionUser is not set
		if(sessionUser == null)
			return new ArrayList<Order>();
		
		// return Orders by User id
		return OrderController.getOrdersByUser(sessionUser.getId());
	}
	
	/**
	 * Gets the current User shortCut
	 * @return
	 */
	public static User getCurrentUser() {
		// Gets the sessionUser from LoginController
		User sessionUser = LoginController.getSessionUser();
		// Returns the User
		return sessionUser;
	}

	/**
	 * Deletes a User by id
	 * @param i int id
	 * @return worked?
	 * @throws SQLException
	 */
	public static boolean deleteUser(int i) throws SQLException {
		// Gets the connection
		Shop.getInstance();
		Connection connect = Shop.getConnection();
		
		// Prepares Query
		PreparedStatement pstmt = connect.prepareStatement("DELETE FROM users WHERE id=?");
		
		// Insert Values to Query
		pstmt.setInt(1, i);
		
		// Execute Delete Query, fetch the deletedRows
		int deletedRows = pstmt.executeUpdate();
		
		// If something was deleted, then true otherwise false!
		return (deletedRows > 0);
	}
}
