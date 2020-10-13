package dBClasses;

import java.sql.Date;

import controller.ShoppingCart;
/**
 * Order in DB
 * @author ksaadDE
 */
public class Order implements java.io.Serializable {
	/** serialVersionUID */
	private static final long serialVersionUID = 1126887808913336230L;
	
	/** Order id */
	private int id;
	/** Userid assigned to Order */
	private int userid;
	/** User obj from assigned userid */
	private User user;
	/** Creation Date of the Order */
	private Date createDate;
	/** Status of the Order / Delivery Status */
	private int status;
	/** JSON Shopping Cart to ShoppingCart Obj (contains Articles for this Order) */
	private ShoppingCart shoppingCart;
	
	
	/***
	 * Gets the ID
	 * @return
	 */
	public int getId() {
		return id;
	}
	
	/***
	 * Sets the ID
	 * @param id
	 */
	public void setId(int id) {
		this.id = id;
	}
	/**
	 * Gets the userid
	 * @return int userid
	 */
	public int getUserid() {
		return userid;
	}
	
	/**
	 * Setst the userid
	 * @param userid
	 */
	public void setUserid(int userid) {
		this.userid = userid;
	}
	
	/***
	 * Gets the User Obj 
	 * @return
	 */
	public User getUser() {
		return user;
	}
	
	/***
	 * Sets the User obj
	 * @param user User obj
	 */
	public void setUser(User user) {
		this.user = user;
	}
	
	/**
	 * Gets the creation Date
	 * @return
	 */
	public Date getCreateDate() {
		return createDate;
	}
	/**
	 * Sets the Creation Date
	 * @param createDate
	 */
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	
	/**
	 * Gets the Status
	 * @return
	 */
	public int getStatus() {
		return status;
	}
	/**
	 * Sets the Status
	 * @param status
	 */
	public void setStatus(int status) {
		this.status = status;
	}
	
	/**
	 * Gets the ShoppingCart
	 * @return
	 */
	public ShoppingCart getShoppingCart() {
		return shoppingCart;
	}
	
	/**
	 * Sets the ShoppingCart
	 * @param shoppingCart
	 */
	public void setShoppingCart(ShoppingCart shoppingCart) {
		this.shoppingCart = shoppingCart;
	}
}
