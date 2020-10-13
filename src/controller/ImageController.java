package controller;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import dBClasses.Image;

/**
 * ImageController manages the DB Stuff for Images
 * @author ksaadDE
 */
@ManagedBean
@ViewScoped
public class ImageController implements Serializable {

	/**
	 * serialVersionID - autogen
	 */
	private static final long serialVersionUID = -4008982456257358278L; 
	
	/**
	 * Creates a image in DB
	 * @param name - name in DB
	 * @param data	- data (aka. base64 or link)
	 * @return returns the new added id out of DB
	 * @throws SQLException
	 */
	public static int createImageInDB(String name, String data) throws SQLException {
		// Getting the Connection
		Connection con;
		con = Shop.getConnection();
		
		// Preparing the statement, ready for escaping
		PreparedStatement ps = con.prepareStatement("INSERT INTO images (name, data) VALUES (?,?)", Statement.RETURN_GENERATED_KEYS);
		
		// Putting the data at the ?
		ps.setString(1, name);
		ps.setString(2, data);
		
		int changed = ps.executeUpdate();
		int id = -1;
		
		// when the update has worked, get the generatedKeys (so the new id after insert)
		if (changed > 0) {
			ResultSet rs = ps.getGeneratedKeys();
			
			if(rs.next()) {
				id = rs.getInt(1);
			}
		}
		// returning the id when done
		return id;
	}
	
	/**
	 * deletes a Image from DB
	 * @param id - id in table of DB
	 * @return changedRows boolean if changedRows > 0 (is the amount of deleted rows > 0)
	 * @throws SQLException
	 */
	public static boolean deleteImageInDB(int id) throws SQLException {
		// Getting the Connection
		Connection con;
		con = Shop.getConnection();
		
		// Making the query ready for escapement ;)
		PreparedStatement ps = con.prepareStatement("DElETE FROM images WHERE id=?");
		
		// Putting the id at the first ? 
		ps.setInt(1, id);
		
		// Do the delete and return the amount of changedRows <3
		int changedRows = ps.executeUpdate();
		return (changedRows > 0);
	}

	/**
	 * Updates an image in the DB by Image object
	 * @param k - Image Object to update (in DB)
	 * @return boolean is the amount of changedRows > 0 ?
	 * @throws SQLException
	 */
	public static boolean updateImageInDB(Image k) throws SQLException {
		if(k==null)return false;
		
		// Getting the Connection
		Connection con;
		con = Shop.getConnection();
		
		// Preparing (=ready for escapement and holding, getting values) the Statement
		PreparedStatement ps = con.prepareStatement("UPDATE images SET name=?,data=? WHERE id=?");
		
		// adding the Values, which getting replaced instead of the ?
		ps.setString(1, k.getName());
		ps.setString(2, k.getData());
		ps.setInt(3, k.getId());
		
		// Do the update and return the amount of changedRows <3
		int changedRows = ps.executeUpdate();
		return (changedRows > 0);
	}
	
	/**
	 * Gets an Image by Id from DB
	 * @param id - integer, id in DB Table
	 * @return 
	 * @throws SQLException
	 */
	public static Image getImageFromDB(int id) throws SQLException {
		// Initialize a new Image (DBImage)
		Image k = new Image();
		
		// Getting the Connection (to DB)
		Connection con;
		Shop.getInstance();
		con = Shop.getConnection();
		
		// Preparing the Statement
		PreparedStatement ps = con.prepareStatement("SELECT id, name, data FROM images WHERE id=?");
		
		// Inserting the Id at the ?
		ps.setInt(1, id);
		
		// Execute the PreparedStatement
		ResultSet rs = ps.executeQuery();
		
		// Getting the Results of the resultSet
		if (rs.next()) {
			// Inserting the values of the image in the a priori created Image Object
			k.setId(rs.getInt("id"));
			k.setName(rs.getString("name"));
			k.setData(rs.getString("data"));
		}
		
		// Returning the ImageObject (not the DB Object!!)
		return k;
	}
	
	/**
	 * Getting all images from DB
	 * @return List[Image] images
	 * @throws SQLException
	 */
	public static List<Image> getAllImageFromDB() throws SQLException {
		// Init a new, empty List
		List<Image> imageList = new ArrayList<Image>();
		
		// Getting the Connection to DB
		Connection con;
		Shop.getInstance();
		con = Shop.getConnection();
		
		// Preparing the Statement
		PreparedStatement ps = con.prepareStatement("SELECT id, name, data FROM images");
		
		// Getting the ResultSet, after Execution of the Query (= escaped PreparedStatement content)
		ResultSet rs = ps.executeQuery();
		
		// looping until nothing new, through the ResultSet Data (Items from DB)
		while (rs.next()) {
			
			// Creates a new ImageObject && fills it with the data of the Image in DB
			Image k = new Image();
			
			// Filling k with the Data of the current ResultSet
			k.setId(rs.getInt("id"));
			k.setName(rs.getString("name"));
			k.setData(rs.getString("data"));
			
			// Adding the Image k to the imageList, initialized above!
			imageList.add(k);
		
		}
		
		// Returns a filled ImageList (if possible) otherwise a empty List
		return imageList;
	}
}
