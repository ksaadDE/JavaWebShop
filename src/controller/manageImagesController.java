package controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;


import dBClasses.Image;

/**
 * Controller for the manageImages View
 * @author ksaadDE
 */
@ManagedBean
@ViewScoped
public class manageImagesController {
	/** List of (DB) Images */
	private List<Image> imageList = new ArrayList<Image>();
	
	/** prevents a continuous reload when the list remains empty after the first and second load */
	private boolean stopReloading = false;

	/**
	 * Loads the Images out of the DB and prepares a nice List, uses ImageController
	 * @return
	 */
	public List<Image> getImageList() {
		if(imageList.size() == 0) {
			
			try {
				imageList = ImageController.getAllImageFromDB();
				if(imageList.size() == 0) {
					setStopReloading(true);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
		}
		return imageList;
	}

	/**
	 * Sets the Image List
	 * @param imageList
	 */
	public void setImageList(List<Image> imageList) {
		this.imageList = imageList;
	}

	/**
	 * Getter for stopReloading
	 * @return
	 */
	public boolean isStopReloading() {
		return stopReloading;
	}

	/**
	 * setter for stopReloading
	 * @param stopReloading
	 */
	public void setStopReloading(boolean stopReloading) {
		this.stopReloading = stopReloading;
	}
	
	/**
	 * Saves the Image k to (update in DB)
	 * @param k - (DB) Image which needs to be updated
	 * @return boolean worked?
	 */
	public boolean save(Image k) {
		try {
			if(ImageController.updateImageInDB(k)) {
				return true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * adds a new Image with placeholders, the placeholders should be replaced by the User
	 */
	public void addNew() {
		try {
			// Creates the placeHolder, and putting them into the DB as a new Image
			String name = "new";
			String data = "content";
			Image k = new Image();
			int id = ImageController.createImageInDB(name, data);
			if(id <= 0) {
				System.out.println("ERROR: adding New failed | manageImagesController");
				return;
			}
			// After doing it the createImageInDB returns us the id of the new Image
			// We are filling the Image k with all useful data, we got / defined before
			k.setId(id); 
			k.setName(name);
			k.setData(data);
			/// At the end we just need to add our Image k into the imageList
			imageList.add(k);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Deletes the image by Id from DB and ImageList
	 * @param id - int Image.id (out of DB)
	 * @return boolean worked?
	 */
	public boolean delete(int id) {
		try {
			if(ImageController.deleteImageInDB(id)) {
				this.imageList.removeIf(x -> (x.getId() == id));
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return false;
	}
}
