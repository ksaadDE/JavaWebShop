package controller;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import dBClasses.Article;
import dBClasses.Image;

/**
 * ArticleController class, as in the Script / Uni Book
 * It's surely a bit modifed, because it uses the DB
 * 
 * @author ksaadDE
 */
@ManagedBean(name="ArticleController")
@ViewScoped
public class ArticleController  implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/** articleIndex <b>NOT Id</b> */
	private int index = 0;
	
	/**
	 * When the user activates the save action / save event and it's completed, the val is stored here
	 * -1 means nothing happend, 0 error, 1 yeah it got saved! <3
	 */
	private int savedArticleStatus = -1;
	
	/**
	 * Gets the Article with the currentIndex (just ArticleController.article)
	 * @return
	 */
	public Article getArticle() {
		return Shop.getInstance().getArticle(index);
	}
	
	/**
	 * The Actions when the user presses the next Button
	 * Due to some problems with the savings on view switching, <br>when one of the Buttons got pressed the save action "event" gets send
	 */
	public void next() {
		if(this.index+1 > Shop.getInstance().getArticleList().size()-1 ) // if i+1 > size(list)-1
			return;
		save();
		this.index++;
		this.savedArticleStatus = -1;
	}
	
	/**
	 * The Actions when the user presses the before Button
	 * Also here save Action gets fired ;)
	 */
	public void before() {
		if((this.index-1) < 0) // if i-1 < 0
			return;
		save();
		this.index--;
		this.savedArticleStatus = -1;
	}
	
	/**
	 * Sets the current Article Index (Var: index)
	 * @param Int i
	 * @implNote i must be > 0
	 */
	public void setIndex(int i) {
		if(i < Shop.getInstance().getArticleList().size())
		this.index = i;
		this.savedArticleStatus = -1;
	}
	
	/**
	 * Gets the current article Index
	 * @return
	 */
	public int getIndex() {
		return this.index;
	}
	
	/**
	 * Deletes the current Article from DB and also from temp List
	 */
	public void delete() {
		Shop.getInstance().getArticleList().remove(index);
		this.index = Shop.getInstance().getArticleList().size()-1; 
		try {
			Shop.getInstance().saveArticleList();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.savedArticleStatus = -1;
	}
	
	/**
	 * Adds a article to the DB & list, and switches the index to it
	 */
	public void add() {
		Shop.getInstance().addArticle("new Art", "new Art", new BigDecimal(0.0));
		this.savedArticleStatus = -1;
		this.index = Shop.getInstance().getArticleAmount()-1;
	}
	
	/**
	 * Saves the current Article
	 */
	public void save() {
			try {
				Shop.getInstance().saveArticle(this.getArticle());
				setSavedArticleStatus(1);
			} catch (Exception e) {
				e.printStackTrace();
				setSavedArticleStatus(0);
			}
	}

	/**
	 * Gets the Article Saved Status
	 * -1 = nothing happened here, 0 = nuclear fallout, 1  = Gibbs is happy
	 * @return
	 */
	public int getSavedArticleStatus() {
		return savedArticleStatus;
	}

	/**
	 * Sets the Article saved Status 
	 *  -1 = nothing happened here, 0 = nuclear fallout, 1  = Gibbs is happy
	 * @param savedArticleStatus
	 */
	public void setSavedArticleStatus(int savedArticleStatus) {
		this.savedArticleStatus = savedArticleStatus;
	}
	
	/**
	 * Gets the Images from DB
	 * @return List[image] image
	 */
	public static List<Image> getImageList() {
		try {
			return ImageController.getAllImageFromDB();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new ArrayList<Image>();
	}

}
