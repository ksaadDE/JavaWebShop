package controller;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import dBClasses.Article;


/**
 * This is a ShoppingCart :)
 * @author ksaadDE
 *
 */
@ManagedBean(name="ShoppingCart")
@SessionScoped
public class ShoppingCart implements java.io.Serializable {
	/** autogen serialVersionUID */
	private static final long serialVersionUID = 4014390181966203657L;
	/** Items of this ShoppingCart */
	private List<ShoppingCartItem> shoppingCartItems = new ArrayList<ShoppingCartItem>();
	
	/** Was the OrderSend Message shown? */
	private boolean orderSendShown = false;
	/** User clicked on "Buy" ? */
	private int orderSend = -1;
	
	public ShoppingCart() {
		
	}

	/**
	 * Gets the ShoppingCart Price (summerized by Article in Cart)
	 * @return BigDecimal summarized Price
	 */
	public BigDecimal getPrice() {
		BigDecimal price = new BigDecimal(0.0f);
		
		// Goes through each item in cartItems
		for (ShoppingCartItem kI : this.shoppingCartItems) {
			// Following equals price += ki.getAmount() * kI.getArticle().getPrice(); 
			// It needs to be done like below, because it's Big Decimal
			BigDecimal articleQuantity = new BigDecimal(kI.getAmount());
			BigDecimal articleCost = kI.getArticle().getPrice();
			articleCost = articleCost.multiply(articleQuantity);
			price = price.add(articleCost);
		}
		
		// Returning the summarized price and rounding up (economic rounding)
		return price.setScale(2, RoundingMode.HALF_UP);
	}
	
	/**
	 * Gets the Item Count out of ShoppingCart (so summing up every Article)
	 * @return
	 */
	public int getItemCount() {
		// Zero int
		int i = 0;
		
		// Going through each cartItem and getting the Amount
		for (ShoppingCartItem k: this.shoppingCartItems) {
			i+= k.getAmount();
		}
		
		// Returns the sum
		return i;
	}
	
	/**
	 * finds a cartItem by Id
	 * @param i int id
	 * @return ShoppingCartItem cartItem (found cartItem)
	 */
	public ShoppingCartItem findOneById(int i) {
		// Loops through the shoppingCartItems
		for (ShoppingCartItem k: this.shoppingCartItems) {
			if(k.getArticle().getId() == i){
				// returns Item if ids are the same
				return k;
			}
		}
		// Otherweise return null
		return null;
	}
	
	/**
	 * Adds a Article to Cart
	 * @param a
	 */
	public void addArticle(Article a) {
		// gets a Article k from list/DB, to check if it exist
		ShoppingCartItem k = this.findOneById(a.getId());
		// if Article seems not to be in DB / List
		if (k == null)  {
			// init the Cart Item and set the Amount to 1 because it's the first in the Cart (for this Article Type)
			ShoppingCartItem cartItem = new ShoppingCartItem();
			cartItem.setArticle(a);
			cartItem.setAmount(1);
			
			// Add to "local" list (first Add)
			this.shoppingCartItems.add(cartItem);
		} else {
			// Add another Article
			k.add();
		}
	}
	
	/**
	 * Reduces the Article Amount of a cartItem (Article in Cart)
	 * @param i cartItem
	 */
	public void lessArticle (ShoppingCartItem i) {
		// Remove Article from ShoppingCart, if the Article amount-1 gets zero or less.
		if (i.getAmount()-1 <= 0) {
			this.removeArticle(i.getArticle().getId());
		} else {
			// only remove 1, if Article Amount-1 is > 0
			i.remove();
		}
	}
	
	/**
	 * Removes a Article when id of Article equals id
	 * @param id
	 */
	public void removeArticle(int id) {
		// remove Article with the id (not index!!!) 
		this.shoppingCartItems.removeIf(article -> (article.getArticle().getId() == id));
	}
	
	/**
	 * Setter for shoppingCartItems
	 * @param shoppingCartItems
	 */
	public void setShoppingCartItems(List<ShoppingCartItem> shoppingCartItems) {
		this.shoppingCartItems = shoppingCartItems;
	}

	/**
	 * Getter shoppingCartItems
	 * @return
	 */
	public List<ShoppingCartItem> getShoppingCartItems(){
		return this.shoppingCartItems;
	}
	
	/**
	 * Converts this Cart to JSON <3
	 * @return JSON String
	 */
	public String toString() {
		return OrderController.shoppingCartToJson(this);
	}
	
	/**
	 * Drops all Articles in shopppinCart (list clear)
	 */
	public void clearArticles() {
		this.shoppingCartItems.clear();
	}
	
	/**
	 * Sends a Order (saves it / creates it)
	 */
	public void sendOrder() {
		// Reset orderSendShown
		orderSendShown = false;
		
		// When currentUser Order to DB added
		if(OrderController.addCurrentUserOrder(this)) {
			// set Order send to "True"
			this.setOrderSend(1);
			// clear the Article amount in currentCart
			this.clearArticles();
		} else {
			// Error
			this.setOrderSend(2);
		}
	}
	
	/**
	 * Getter Order send
	 * @return
	 */
	public int getOrderSend() {
		// When orderSendShown
		if(orderSendShown) {
			// Reset OrderSend
			this.setOrderSend(-1);
			
			// Order not shown
			orderSendShown = false;
		}
		
		// When the Order is "send" / created --> orderSendShown
		if(orderSend == 1)
			orderSendShown = true;
		return orderSend;
	}

	/**
	 * Sets orderSend to int i
	 * @param i orderSend?
	 */
	public void setOrderSend(int i) {
		this.orderSend = i;
	}
}
