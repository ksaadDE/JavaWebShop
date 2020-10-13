package charts;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import org.primefaces.model.chart.*;

import controller.OrderController;
import controller.UserController;

/**
 * ChartView, based on PrimeFaces, uses only the linear Chart, but the principle stays the same!
 * 
 * @author ksaadDE
 */
@ManagedBean
@RequestScoped
public class ChartView implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = -4189929981221944971L;
	
	/**
	 * Model for the linear Chart (in the Admin View)
	 */
	private LineChartModel lineUsersModel;
 
    @PostConstruct
    public void init() {

    	// Tries to init the Chart (linear) Model
    	try {
			initLinearModel();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
 
	/**
	 * ManagedBean getter for the lineUsersChart
	 * @return LineChartModel lineUserModel
	 */
	public LineChartModel getLineUsersModel() {
		return lineUsersModel;
	}
	
	/**
	 * ManagedBean setter for the lineUsersChart
	 * @param lineUsersModel
	 */
	public void setLineUsersModel(LineChartModel lineUsersModel) {
		this.lineUsersModel = lineUsersModel;
	}
    
	/**
	 * Quick'n Dirty approach to find the Max in an List<Integer> could be improved?!
	 * @param List<Integer> inputs
	 * @return max Value
	 */
    private int findMax(List<Integer> inputs) {
    	int currentMax = 0;
    	
    	// Loops through if the currentMax is lower than the new Val, the new Val is the currentMax ;)
    	for (int i: inputs)
    		if(i > currentMax)currentMax=i;
		
    	return currentMax;
    } 

    /**
     * Initializes the LineChartModel
     * @return Model
     * @throws SQLException 
     */
    private void initLinearModel() throws SQLException, NullPointerException {
    	// Getting the Amounts of each represented value from DB
    	int usersAmount = UserController.getUsersAmount();
    	int ordersAmount = OrderController.getOrdersAmount(false);
    	int ordersAmountSuccessfully = OrderController.getOrdersAmount(true);
    	
    	List<Integer> seriesMax = new ArrayList<Integer>();
    	seriesMax.add(usersAmount);
    	seriesMax.add(ordersAmount);
    	
        LineChartModel model = new LineChartModel();
 
        // Setting the Title etc
        model.setTitle("Shop over Time");
        model.setLegendPosition("e");
        
        // Setting the Y-Axis min and max Values
        Axis yAxis = model.getAxis(AxisType.Y);
        yAxis.setMin(0);
        yAxis.setMax(Math.round(findMax(seriesMax)+1));
        
        // Amount of all Users
        LineChartSeries series1 = new LineChartSeries();
        series1.setLabel("current Users");
        series1.set(1, 1);
        series1.set(2, usersAmount);
 
        // Amount of all Orders, regardless if they're already completed or not!
        LineChartSeries series2 = new LineChartSeries();
        series2.setLabel("current Orders");
        series2.set(1, 0);
        series2.set(2, ordersAmount);
        
        // Amount of Orders which were successfully
        LineChartSeries series3 = new LineChartSeries();
        series3.setLabel("successful Orders");
        series3.set(1, 0);
        series3.set(2, ordersAmountSuccessfully);
        
        // add the predefined Series to the Model
        model.addSeries(series1);
        model.addSeries(series2);
        model.addSeries(series3);
        
        // Assign model to lineUsersModel
        this.lineUsersModel = model;
        
    }

}