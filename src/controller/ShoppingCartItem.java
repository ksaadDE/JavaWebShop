package controller;

import dBClasses.Article;

/**
 * Defines the ShoppingCartItem used in ShoppingCart
 * Needs to be Serializable (otherweise GJSon can't convert them)
 * @author ksaadDE
 */
public class ShoppingCartItem implements java.io.Serializable {
	/** autogen serialVerisonUID*/
	private static final long serialVersionUID = 4752541747305556607L;
	
	/**  CartItem Article */
	private Article article = null;
	/** Amount of Articles of the Type Article */
	private int amount = 0;
	
	public ShoppingCartItem() {
		
	}
	
	/**
	 * Sets the Article
	 * @param a
	 */
	public void setArticle(Article a) {
		this.article = a;
	}
	
	/**
	 * Gets the Article
	 * @return
	 */
	public Article getArticle() {
		return this.article;
	}
	
	/**
	 * Sets the Amount of current Article
	 * @param i
	 */
	public void setAmount(int i) {
		this.amount = i;
	}
	
	/**
	 * Gets the Amount of current Article
	 * @return
	 */
	public int getAmount() {
		return this.amount;
	}
	
	/**
	 * +1 Article Amount
	 */
	public void add() {
		this.amount += 1;
	}
	
	/**
	 * -1 Article Amount
	 */
	public void remove() {
		this.amount -= 1;
	}
	
	/** Used for View, e.g. 2xArticle.ToString() */
	public String toString() {
		return amount +"x"+ this.article.toString();
	}
}
