package controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import dBClasses.User;

/**
 * Controller for View manageUsers
 * @author ksaadDE
 */
@ManagedBean
@ViewScoped
public class manageUsersController {
	/** SavedStatus? <br> -1 = nothing happened, <br> 0 and 2 = saving failed, <br> 1 = successfully saved */
	private int savedStatus = -1;
	
	/** StopReloading when failed first and second reload */
	private boolean stopReloading = false;
	
	/** List for Users */
	private List<User> userList = new ArrayList<User>();
	
	public manageUsersController () {

	}

	/**
	 * Gets savedStatus
	 * @return boolean savedStatus
	 */
	public int getSavedStatus() {
		return savedStatus;
	}

	/**
	 * Sets savedStatus
	 * @param savedStatus
	 */
	public void setSavedStatus(int savedStatus) {
		this.savedStatus = savedStatus;
	}
	
	/**
	 * Saves a User
	 * @param user
	 */
	public void saveUser(User user) {
		// Tries to save a User
		try {
			// Checks if the PW is already hashed or not
			if  (!user.getPass().startsWith("$2a$") && user.getPass().length() > 0) {
				// if not generate a new Hash
				user.setSafePassword(user.getPass());
			}
			
			// Update the User in DB and set the savedStatus
			if(UserController.updateUser(user)) {
				// Success
				this.savedStatus = 1;
			} else {
				// Error
				this.savedStatus = 2;
			}
		} catch (Exception e) {
			// Error
			e.printStackTrace();
			this.savedStatus = 2;
		}
	}
	
	/**
	 * Getting all Users from DB and putting them into a List
	 * @return List of Users
	 */
	public List<User> getUserList() {
		
		// Do it only when the userList is empty
		if(userList.size() == 0) {
			try {
				// Do it only when stopReloading is not activated
				if (!stopReloading)
					userList = UserController.getAllUser();
				
				// Do it only when the List is empty and not stopReloading
				if(userList.size() == 0 && !stopReloading) {
					// DB Is empty
					userList = new ArrayList<User>();
					stopReloading = true;
				}
			} catch (SQLException e) {
				// Exception
				e.printStackTrace();
			}
		}
		
		// When done Return the List of Users
		return userList;
	}

	/**
	 * Sets the Users List
	 * @param userList
	 */
	public void setUserList(List<User> userList) {
		this.userList = userList;
	}
	
	/***
	 * Adds a new User to DB and "local" List 
	 */
	public void addNew() {
		try {
			
			// Creates the User by random Values, which can be changed afterwards
			User k = UserController.createUser("newuser", "e1243ß213213", false);
			if (k == null) {
				// Adding the User failed
				System.out.println("manageUsersController failed, addNew() == null");
				return;
			}
			
			// if the userList doesn't contain the entry then add it
			if(!this.userList.contains(k))
				this.userList.add(k);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Deletes a User out of List and DB
	 * @param k User
	 */
	public void delete(User k) {
		// If id is not valid
		if(k.getId() < 1)
			return;
		
		try {
			this.setSavedStatus(2);
			
			// Gets the Id from User k and deletes the User by using UserController
			int i = k.getId();
			// Deleting the User out of DB
			if(UserController.deleteUser(i)) {
				// When it works deleting the User out of "local" List
				userList.remove(k);
				// Setting the Status
				this.setSavedStatus(1);
			}
		} catch (Exception e) {
			// Exception
			e.printStackTrace();
			this.setSavedStatus(2);
		}
	}
}
