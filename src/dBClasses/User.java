package dBClasses;

import bcrypt.BCrypt;

/**
 * User class for DB
 * @author ksaadDE
 *
 */
public class User  implements java.io.Serializable {
	/** autogen serialVerisonUID */
	private static final long serialVersionUID = -2788851808750528437L;
	/** userid */
	private Integer id;
	/** Username */
	private String name;
	/** Password */
	private String pass;
	/** Is the user admin?*/
	private boolean isadmin = false;
	
	// Logged in Var - locally used, not in DB
	private boolean loggedin = false;
	
	public User () {
		
	}
	
	/**
	 * Constructor a User
	 * @param name
	 * @param pass
	 */
	public User(String name, String pass) {
		this.name = name;
		this.pass = pass;
	}
	
	/**
	 * Sets the Name of this User
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * Gets the Name of this User 
	 * @return
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Sets a unhashed pass<br>Function does <b>NOT</b> hash the entered Password, but it's needed in UI
	 * @param p
	 */
	public void setPass(String p) {
		this.pass = p;
	}
	
	/**
	 * Sets the Password and hashes it before!! <b>SAFE</b>
	 * @param p
	 */
	public void setSafePassword(String p) {
		this.pass = User.getHashedPassword(p);
	}
	
	/***
	 * Gets the pass as is (e.g. hash or plain)
	 * @return String usually a hash, but can be plain! (becareful)
	 */
	public String getPass() {
		return this.pass;
	}
	
	/**
	 * Sets the id
	 * @param int1
	 */
	public void setId(Integer int1) {
		this.id = int1;
	}
	
	/**
	 * Sets the id
	 * @param int1
	 */
	public void setId(int int1) {
		this.id = int1;
	}
	/**
	 * Gets the id
	 * @return int
	 */
	public Integer getId() {
		return this.id;
	}
	
	/**
	 * BCrypt Wrapper to verifyPassword
	 * @param password
	 * @return
	 */
	public boolean verifyPassword(String password) {
		return BCrypt.checkpw(password, this.getPass());
	}
	
	/**
	 * Hashes the String t by Crypto BCrypt Functions
	 * @param t input String
	 * @return String hashedString
	 */
	public static String getHashedPassword(String t) {
		return BCrypt.hashpw(t, BCrypt.gensalt(12));
	}

	/**
	 * Gets loggedin Status
	 * @return
	 */
	public boolean isLoggedin() {
		return loggedin;
	}

	/**
	 * Sets loggedin Status
	 * @param loggedin
	 */
	public void setLoggedin(boolean loggedin) {
		this.loggedin = loggedin;
	}

	/**
	 * Gets admin status
	 * @return
	 */
	public boolean getIsadmin() {
		return isadmin;
	}

	/**
	 * Sets admin status
	 * True or False
	 * @param isadmin
	 */
	public void setIsadmin(boolean isadmin) {
		this.isadmin = isadmin;
	}

	/**
	 * is Admin?
	 * @return
	 */
	public boolean isAdmin() {
		return isadmin;
	}
}
