package dBClasses;

/**
 * DB Image class Image in DB (contains id,name,data)
 * @author ksaadDE
 */
public class Image {
	/** imageid in DB */
	private int id = 0;
	/** Name of this Image */
	private String name = "";
	/** Data of this Image<br> Base64 <b>OR</b> Link!*/
	private String data = "";
	
	/**
	 * Gets the imageid of this image
	 * @return int imageid
	 */
	public int getId() {
		return id;
	}
	
	/**
	 * Sets the imageid of this image
	 * @param id int
	 */
	public void setId(int id) {
		this.id = id;
	}
	
	/**
	 * Gets the name of this Image<br>
	 * for example used in comboBoxes etc.
	 * @return
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Sets the name of this image
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/***
	 * Gets the data of this Image
	 * @return
	 */
	public String getData() {
		return data;
	}
	
	/**
	 * Sets the Data of this Image
	 * @param data
	 */
	public void setData(String data) {
		this.data = data;
	}
	
}
