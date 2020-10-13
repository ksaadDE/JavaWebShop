package controller;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import dBClasses.User;

/**
 * LoginController manages everything with the currentUser and Loggedin stuff <br>
 * You could place here also a registration System (but better put it in a own File)
 * @author ksaadDE
 */
@ManagedBean(name="LoginController")
@ViewScoped
public class LoginController  implements java.io.Serializable {
	
	/**
	 * Autogen, serialVersionID
	 */
	private static final long serialVersionUID = 1967744264091240466L;
	
	/** When a User is stupid and can't login, but tries it anyways ;) */
	private Boolean loginFailed = false;
	
	/** When a User successfully logins, maybe he isn't that stupid?	 */
	private Boolean loginSuccess = false;
	
	/** When the logout is correctly done <3 */
	private Boolean logoutSuccess = false;
	
	/** Entered by the User in the LoginView (Username) */
	private String username = ""; 
	/** Entered by the User in the LoginView (Password) */
	private String password = "";
	
	
	public LoginController() {
		
	}
	
	/**
	 * Gets the current LoginView (entered) <b>Username</b>
	 * @return String Username
	 */
	public String getUsername() {
		return this.username;
	}
	
	/**
	 * Gets the current LoginView (entered) <b>Password</b>
	 * @return String Password
	 */
	public String getPassword() {
		return this.password;
	}
	
	/**
	 * Sets the current Username, used in the LoginView
	 * @param username
	 */
	public void setUsername(String username) {
		this.username = username;
	}
	
	/**
	 * Sets the current Password, used in the LoginView
	 * @param password
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	
	/**
	 * Checks the Input, when the user pressed "login"
	 * if the input was correct, the User gets loggedin, if not failed Message + no Session!
	 * @param username
	 */
	public boolean checkLogin() throws NoSuchAlgorithmException, SQLException {
		try {
			// gets the User out of the DB by Username
			User user = UserController.getUser(this.username);
			
			// Uses BCrypt to verfiy the currentUserPassword hash with the BCrypt hash in DB
			boolean val = user.verifyPassword(this.password);
			
			// When Bcrypt says "Sir, that's allowed!", then we can go further
			if (val) {
				// First of all resetting all the User Inputs!
				this.username = "";
				this.password = "";
				
				// Setting the User loggedin
				user.setLoggedin(val);
				
				// Putting the User into the sessionCache
				FacesContext context = FacesContext.getCurrentInstance();
				context.getExternalContext().getSessionMap().put("user", user);
				
				// Setting the correct "Answers" for the View
				this.setLoginFailed(false);
				setLoginSuccess(true);
			} else {
				// This dumb can't give us wrong data :(
				this.setLoginFailed(true);
				this.setLoginSuccess(false);
			}
			// resetting the User Variable
			user = null;
			
			// Return if it has worked or not
			return val;
		} catch (Exception e) {
			// Some Exception handling here ;p
			e.printStackTrace();
			this.setLoginFailed(true);
			this.setLoginSuccess(false);
			return false;
		}
		
		// Done
	}
	
	
	/**
	 * Logout for the User ;)
	 */
	public void logOut() {
		// Tries to log the user out
		try {
			// Destroying the session -> the user can't be loggedin
			FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
			this.setLogoutSuccess(true);
		} catch (Exception ex) {
			// Exception handling
			ex.printStackTrace();
			this.setLogoutSuccess(false);
		}
	}
	
	/**
	 * Get the current User from currentSession <3
	 * @return
	 */
	public static User getSessionUser() {
		FacesContext context = FacesContext.getCurrentInstance();
		return (User) context.getExternalContext().getSessionMap().get("user");
	}

	/**
	 * Getter for loginFailed
	 * @return
	 */
	public Boolean getLoginFailed() {
		return loginFailed;
	}

	/**
	 * Setter for loginFailed
	 * @param loginFailed
	 */
	public void setLoginFailed(Boolean loginFailed) {
		this.loginFailed = loginFailed;
	}

	/**
	 * getter for LoginSuccess
	 * @return
	 */
	public Boolean getLoginSuccess() {
		return loginSuccess;
	}

	/**
	 * Setter Login Success
	 * @param loginSuccess
	 */
	public void setLoginSuccess(Boolean loginSuccess) {
		this.loginSuccess = loginSuccess;
	}

	/**
	 * Getter for logoutSuccess
	 * @return
	 */
	public Boolean getLogoutSuccess() {
		return logoutSuccess;
	}

	/**
	 * Setter for logoutSuccess
	 */
	public void setLogoutSuccess(Boolean logoutSuccess) {
		this.logoutSuccess = logoutSuccess;
	}
}
