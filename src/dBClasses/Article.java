package dBClasses;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.SQLException;

import controller.ImageController;

/**
 * Article for DB
 * @author ksaadDE
 */
public class Article  implements java.io.Serializable {
	/** autogen serialVerisonUID */
	private static final long serialVersionUID = -6497348055131305360L;

	/** DB id in Table of this Article */
	private int id=0;
	
	/** Name of this Article */
	private String name = "";
	/** Price of this Article */
	private BigDecimal price = new BigDecimal(1.0);
	/** Description of this Article*/
	private String description = "";
	
	/** Image id (for DB) of this Article */
	private int imageid = 0;
	
	/** Image Object for this Article*/
	private Image image = null;
	
	/**
	 * Gets the Description of the Article
	 * @return
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Sets the Description of the Article<br>
	 * Removes a few HTML Tags like p and div.
	 * @param description Text
	 */
	public void setDescription(String description) {
		description = description.replace("<div>", "");
		description = description.replace("</div>", "");
		description = description.replace("<p>", "");
		description = description.replace("</p>", "");
		this.description = description;
	}

	/** Is disabled in DB? Like soft deleted! */
	private boolean disabled = false;
	
	public Article () {
		
	}
	
	/**
	 * Article by following Params
	 * @param name
	 * @param price
	 * @param description
	 */
	public Article (String name, BigDecimal price, String description) {
		this.id = -1;
		this.name = name;
		this.price = price;
		this.description = description;
	}
	
	/**
	 * Sets the Name of this Article
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * Gets the name of this Article 
	 * @return String name
	 */
	public String getName () {
		return this.name;
	}
	
	/**
	 * Sets the price of this Article
	 * @param p BigDecimal is needed!!!
	 */
	public void setPrice(BigDecimal p) {
		this.price = p;
	}
	/**
	 * Gets the Price in Bigdecimal
	 * @return Bigdecimal
	 */
	public BigDecimal getPrice() {
		return this.price;
	}
	
	/**
	 * Multiply Function impl
	 * @param d
	 * @return
	 */
	public BigDecimal getPriceMulitplyBy(int d) {
		return this.price.multiply(new BigDecimal(d));
	}
	
	/**
	 * Rounds BigDecimal price, half up
	 * @return
	 */
	public BigDecimal roundedPrice() {
		return this.price.setScale(2, RoundingMode.HALF_UP);
	}

	/**
	 * Gets the Article Id
	 * @return
	 */
	public int getId() {
		return this.id;
	}
	
	/**
	 *  Sets the article id 
	 * @param id
	 */
	public void setId(int id) {
		this.id = id;
	}

	/** Get disabled Status
	 *  @return Boolean disabled?
	 *  */
	public boolean getDisabled() {
		return this.disabled;
	}

	/**
	 * Sets disabled bool
1	 * @param boolean Disabled? True, False
	 */
	public void setDisabled(boolean boolean1) {
		this.disabled = boolean1;
	}
	
	/***
	 * To String Function, for baremetal View
	 */
	public String toString() {
		return this.id + "|" + this.name + "|" + this.description + "|" + this.price;
	}

	/**
	 * Get the Image DB id of the Article Image (alternatively getable by calling article.image.id)
	 * @return imageid int
	 */
	public int getImageid() {
		return imageid;
	}

	/**
	 * Sets the image id
	 * @param imageid
	 */
	public void setImageid(int imageid) {
		this.imageid = imageid;
	}

	/**
	 * Gets the image out of DB by imageid<br><br>
	 * Image.data contains the base64 OR link
	 * @return DB Image
	 */
	public Image getImage() {
		if(this.imageid > 0 && this.imageid != this.image.getId()) {
			try {
				this.image = ImageController.getImageFromDB(this.imageid);
			} catch (SQLException e) {
				// Exception
				e.printStackTrace();
			}
		}
		// retuns the Image out of DB
		return image;
	}

	/**
	 * Sets the DB image
	 * @param image DB image 
	 */
	public void setImage(Image image) {
		this.image = image;
	}
}