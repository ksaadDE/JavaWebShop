package controller;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

import dBClasses.Article;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Shop Main
 * Contains everything Shop related, it's everywhere accessable by getInstance()
 * @author ksaadDE
 */
@ManagedBean (name="Shop")
@ApplicationScoped
public class Shop implements java.io.Serializable {

	/** Autogen serialVersionUID */
	private static final long serialVersionUID = 6147370763174432448L;
	
	/** Name of the Shop (is usually stored in DB, otherwise "cached" here) */
	private String name="Test Shop";
	/** Current List of Articles in Store (Stored in DB) */
	private List<Article> articleList = new ArrayList<Article>();
	/** Currency of this Shop */
	private String currency = " USD";
	
	/** Variable for Instance (getInstance) */
	private static Shop instance = new Shop();
	
	private static final Properties dbConfig  = loadDBConfig();
	
	/**
	 * Shop init
	 */
	public Shop () {
		try {
			// Getting the Name out of DB
			this.name = this.getNameFromDB();
			
			// if the articleList is empty, loading it from DB
			if (Shop.getInstance().articleList.size() == 0) {
				// Setting it to the current, "local" List
				Shop.getInstance().articleList = loadArticleList();
				System.out.println("Loaded " + this.articleList.size() + " Articles");
			}
		} catch (Exception e) {
			//Exception
			System.out.print(e.toString());
		}
	}
	
	/**
	 * Loads the articleList of this Shop, from DB
	 * @return List[Article] articles
	 * @throws SQLException
	 */
	public List<Article> loadArticleList() throws SQLException{
		// Init connection to DB
		Connection connect = Shop.getConnection();
		
		// Empty Article List
		List<Article> articles = new ArrayList<Article>();
		
		// Start Prepared Statement
		PreparedStatement pstmt = connect.prepareStatement("select id, name, description, price, image, disabled from Shop.articles");
		
		// Running it and getting the ResultSet
		ResultSet rs = pstmt.executeQuery();
		
		// Looping through the RS
		while(rs.next()) {
			// Init Article Obj
			Article article = new Article();
			
			// Filling the Object with data
			article.setId(rs.getInt("id"));
			article.setName(rs.getString("name"));
			article.setPrice(rs.getBigDecimal("price"));
			article.setDescription(rs.getString("description"));
			article.setImageid(rs.getInt("image"));
			// is the image id set? Then load the image from DB and assign it
			if(article.getImageid() > 0)
				article.setImage(ImageController.getImageFromDB(article.getImageid()));
			article.setDisabled(rs.getBoolean("disabled"));
			
			// Adding the article Obj to List
			articles.add(article);
		}
		
		// Closing the Connections
		rs.close();
		pstmt.close();
		connect.close();
		
		
		// returning the List
		return articles;
	}
	
	/**
	 * Save Article List to DB
	 * @throws SQLException
	 */
	public void saveArticleList() throws SQLException {
		System.out.println("Saving all Articles, amount:" + this.articleList.size());
		
		// Get DB Connection
		Connection connect = Shop.getConnection();
		
		// Lopping throuhg the current, "local" articleList, saving every Article by "saveArticle" function to DB
		for (Article k : this.articleList) 
			saveArticle(k);
		
		// Closing the DB connection
	    connect.close();
	}
	
	/**
	 * Gets the Shop Name from DB
	 * @return
	 * @throws SQLException
	 */
	public String getNameFromDB() throws SQLException {
		// Gets the DB connection
		Connection connect = Shop.getConnection();
		
		// Prepares Statement - Shop Options Table
		PreparedStatement pstmt = connect.prepareStatement("select id, settingname, settingcontent from Shop.shopsettings where settingname=?");
		
		// Sets prepared values (in this case for the option="name")
		pstmt.setString(1, "name");
		
		// Gets the ResultSet and runs the Query
		ResultSet rs = pstmt.executeQuery();
		
		// init Shopname
		String shopname = "empty";
		
		// When Next RS Element
		if (rs.next()) {
			//sets the Shopname to the value
			shopname = rs.getString("settingcontent");
		}
		
		// Closes everything (RS, PS & connection)
		rs.close();
		pstmt.close();
		connect.close();
		
		// returns the Shopname
		return shopname;
	}
	
	/**
	 * Updates the Shopname in DB
	 * @param newname String new Name of this Shop
	 * @throws SQLException
	 */
	public void updateNameInDB(String newName) throws SQLException {
		
		// Gets the DB Connection
		Connection connect = Shop.getConnection();
		
		// Prepare Statement for Update
		PreparedStatement pstmt = connect.prepareStatement("UPDATE Shop.shopsettings SET settingcontent=? WHERE settingname=?");
		
		// Option name gets the new Val of newName
		pstmt.setString(1, newName);
		pstmt.setString(2, "name");

		// Runs the Update
		pstmt.executeUpdate();

		// Closes everything
		pstmt.close();
		connect.close();
	}
	
	/**
	 * Setter for Shop name (updates in DB!!)
	 * @param name ShopName
	 */
	public void setName(String name) {
		try {
			// Updates name in DB
			this.updateNameInDB(name);
		} catch (SQLException e) {
			// Exception
			e.printStackTrace();
		}
		
		// Sets the Shop Name
		this.name = name;
	}
	
	/**
	 * Gets the Shop Name
	 * @return Shop Name
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * adds an Article to DB and Shop articleList
	 * @param name String Name of Article
	 * @param description String longDescription
	 * @param price (BigDecimal) price
	 */
	public void addArticle(String name, String description, BigDecimal price) {
		try {
			// Gets the connection
			Connection connect = Shop.getConnection();

			// prepares the query
		    String query = "insert into articles (name,price,description,image, disabled) VALUES (?,?,?,?,?)";
		    PreparedStatement preparedStmt = connect.prepareStatement(query);
		    preparedStmt.setString   (1, name);
		    preparedStmt.setBigDecimal   (2, price);
		    preparedStmt.setString   (3, description);
		    preparedStmt.setInt(4, 1);
		    preparedStmt.setBoolean   (5, false);
		    
		    // Executes the Query
		    preparedStmt.executeUpdate();
		     
		    // Closes the Connection
		    connect.close();
		    
		    // reloads the articleList
		    this.articleList = this.getArticleList();
		} catch (Exception e) {
			// Exception
			e.printStackTrace();
		}
	}
	
	/**
	 * Gets an Article by index from articleList
	 * @param k Integer Article to get (index, not DB Table id)
	 * @return Returns a <b>Article</b> from the articleList
	 */
	public Article getArticle(int k) {
		// When k exceeds the articleList size
		if(k > this.articleList.size()-1)
			return null;
		
		// returns the selected Article
		return this.articleList.get(k);
	}
	
	/**
	 * Loads a Article from DB
	 * @param id <b>int</b> DB id
	 * @return Returns a <b>Article</b> Object
	 * @throws SQLException
	 */
	public Article loadArticleFromDB(int id) throws SQLException {
		// Gets a connection
		Connection connect = Shop.getConnection();
		
		// Get the Query ready 
		PreparedStatement pstmt = connect.prepareStatement("select id, name, price,description, image, disabled from Shop.articles where id=?");
		// insert values to query, id in this case
		pstmt.setInt(1, id);
		
		// Getting a ResultSet by exec the Query
		ResultSet rs = pstmt.executeQuery();
		
		// Init Null Article Object
		Article article = null;
		
		// get the RS Result
		if(rs.next()) {
			// new Article (Obj)
			article = new Article();
			
			// Fill it with values
			article.setId(rs.getInt("id"));
			article.setName(rs.getString("name"));
			article.setPrice(rs.getBigDecimal("price"));
			article.setDescription(rs.getString("description"));
			article.setImageid(rs.getInt("image"));
			
			// When Image then get it from the DB
			if(article.getImageid() > 0)
				article.setImage(ImageController.getImageFromDB(article.getImageid()));
			
			article.setDisabled(rs.getBoolean("disabled"));
		}
		
		// Returns the Article
		return article;
	}

	/**
	 * Gets the articleList (from DB)
	 * @return
	 */
	public List<Article> getArticleList() {
		// Tries to Load articleList from DB
		try {
			// Loading from DB
			this.articleList = loadArticleList();
		} catch (Exception e) {
			// Exception
			e.printStackTrace();
		}
		
		// Returns the articleList
		return this.articleList;
	}
	
	/**
	 * Gets the currentAmount of Articles by .size()
	 * @return Amount <b>int </b>
	 */
	public int getArticleAmount() {
		return this.articleList.size();
	}
	
	/**
	 * Gets the ArticleList by Name (overloaded)
	 * <br>Search Functionality!
	 * @param name Search Keyword
	 * @return List[Article] articleList
	 */
	public List<Article> getArticleList(String name) {
		return searchArticleByName(name);
	}
	
	/**
	 * Gets the ShopInstance
	 * @return
	 */
	public static Shop getInstance() {
		return instance;
	}
	
	/**
	 * Gets the articleList by Name from DB
	 * @param name Keywords (String)
	 * @return List[Article] foundList
	 */
	public List<Article> searchArticleByName(String name) {
		// Empty List foundList
		List<Article> foundList = new ArrayList<Article>();
		
		// Going through the articleList
		for (Article k: this.articleList) {
			// has the looped Article matches with the Keyword of name?
			if(k.getName().equalsIgnoreCase(name) || k.getName().toLowerCase().contains(name)) {
				// When yes add it to the foundList
				foundList.add(k);
			}
		}
		
		// returns the foundList
		return foundList;
	}
	
	/**
	 * Shows an Article over link / Navi
	 * @param aC the current ArticleController
	 * @param article an Article to Show
	 * @return next location
	 */
	public String showArticle(ArticleController aC, Article article) {
		aC.setIndex(article.getId());
		return "showArticle";
	}
	
	/**
	 * Stackoverflow copied piece, reads the config: config/config.properties 
	 * 
	 * @author some hero on StackOverflow <3
	 * @return Properties Object
	 */
	public static Properties loadDBConfig() {
		try {
			// config file in /src/config
			String configFilePath = "config.properties";
	
			// Properties Object
			Properties props = new Properties();
	
			// Ressource to InputStream
			InputStream propInStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(configFilePath);
	
			// Stream to Props Object
			props.load(propInStream);
			
			// Return the Result
			return props;
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		// if Exception return null
		return null;
	}
	
	/**
	 * Shop Database get Connection using JDBC
	 * @return Returns the (new) connectio n
	 * @throws SQLException
	 */
	public static Connection getConnection() throws SQLException {

		// Init nulled connection
		Connection connect = null;
		
		
		// JDBC Url - setting the serverTZ temporary fixes a bug in JDBC
		String url = "jdbc:mysql://"+dbConfig.getProperty("dbHost")+":"+dbConfig.getProperty("dbPort")+"/Shop?serverTimezone=UTC&useTimezone=true";
		// DB Username
		String username = dbConfig.getProperty("dbUser");
		// DB Password
		String password = dbConfig.getProperty("dbPass");
		
		
		// Tries to register the JDBC Driver and getting the con
		try {
			DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
			connect = DriverManager.getConnection(url, username, password);
		} catch (SQLException ex) {
			// Exception
			ex.printStackTrace();
		}
		
		// Returns the new connection
		return connect;
	}

	/**
	 * Saves a Article
	 * @param article
	 * @return Did it work?
	 */
	public boolean saveArticle(Article article) {
		try {
			// Gets a Connection
			Connection connect = Shop.getConnection();
	
			// Preparing Query and inserting values
		    String query = "UPDATE Shop.articles SET name=? , price=? , description=?, image=?, disabled = ? WHERE id=?";
		    
		    PreparedStatement preparedStmt = connect.prepareStatement(query);
		    
		    preparedStmt.setString   (1, article.getName());
		    preparedStmt.setBigDecimal(2, article.getPrice());
		    preparedStmt.setString(3, article.getDescription());
		    preparedStmt.setInt(4, article.getImageid());
		    preparedStmt.setBoolean(5, article.getDisabled());
		    preparedStmt.setInt(6, article.getId());

		    // Was the update successful?
		    boolean o = (preparedStmt.executeUpdate() >  0);
		    
		    // Closing the connection
		    connect.close();
		    
		    // Returning the boolean
		    return o;
		} catch (Exception e) {
			// Exception
			e.printStackTrace();
		}
		
		// If not then not ;)
		return false;
	}

	/**
	 * Gets the current Currency for this Shop
	 * @return
	 */
	public String getCurrency() {
		return currency;
	}

	/**
	 * Sets the Currency for this Shop<br>e.g. USD
	 * @param currency String Currency in CAPS
	 */
	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public static Properties getDbconfig() {
		return dbConfig;
	}
}