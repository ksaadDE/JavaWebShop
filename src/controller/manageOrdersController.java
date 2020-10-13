package controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import dBClasses.Order;
import dBClasses.User;

/**
 * Controller for the manageOrders View
 * @author ksaadDE
 */
@ManagedBean
@ViewScoped
public class manageOrdersController {
	/** Saved?  
	 * <br>
	 * -1 = nothing happened<br> 0 = false<br>1 = true<br>2 = false
	 * */
	private int savedStatus = -1;
	
	/** Currently not allowed to do Reloads of Orders? */
	private boolean stopReloadingOrders = false;
	/** Currently not allowed to do Reloads of Users? */
	private boolean stopReloadingUsers = false;
	
	/** List with Orders */
	private List<Order> ordersList = new ArrayList<Order>();
	/** List with Users */
	private List<User> userList = new ArrayList<User>();
	
	public manageOrdersController () {

	}

	/**
	 * Gets savedStatus
	 * @return
	 */
	public int getSavedStatus() {
		return savedStatus;
	}

	/**
	 * Sets savedStatus
	 * @param savedstatus
	 */
	public void setSavedStatus(int savedStatus) {
		this.savedStatus = savedStatus;
	}
	
	/**
	 * Saves a Order o
	 * @param o Order which needs to be saved in DB by OrderController <3
	 */
	public void saveOrder(Order o) {
		// Tries to save it
		try {
			// Trying to save Order <3
			OrderController.saveOrder(o);
			this.savedStatus = 1;
		} catch (Exception e) {
			// Error
			e.printStackTrace();
			this.savedStatus = 2;
		}
	}
	
	/**
	 * Gets the current List of Users
	 * @return
	 */
	public List<User> getUserList() {
		if(userList.size() == 0 && !stopReloadingUsers) {
			try {
				userList = UserController.getAllUser();
				if(userList.size() == 0) {
					// Prevent continuous reloading of usersList, if it was empty  :D
					stopReloadingUsers = true;
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return userList;
	}

	/**
	 * Sets the current List of Users
	 * @param userList
	 */
	public void setUserList(List<User> userList) {
		this.userList = userList;
	}

	/**
	 * Gets the List of Orders
	 * @return
	 */
	public List<Order> getOrdersList() {
		// When Orders == 0 and not StopReloadingOrders
		if (ordersList.size() == 0 && !stopReloadingOrders) {
			// Trying to load the Lists out of the DB
			try {
				ordersList = OrderController.getOrdersFromDB();
				
				// When List is again empty
				if(ordersList.size() == 0) 
				{
					// Activate Stop Reloading or you will see...
					stopReloadingOrders = true;
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return ordersList;
	}

	/**
	 * Sets the List of Orders
	 * @param ordersList
	 */
	public void setOrdersList(List<Order> ordersList) {
		this.ordersList = ordersList;
	}
}
