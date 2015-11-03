package playground.wdoering.debugvisualization.model;
import java.util.HashMap;


public class Agent {
	
	public HashMap<Double,DataPoint>dataPoints = new HashMap<Double,DataPoint>();
	

	public HashMap<Double, DataPoint> getDataPoints()
	{
		return dataPoints;
	}

	public void setDataPoints(HashMap<Double, DataPoint> dataPoints)
	{
		this.dataPoints = dataPoints;
	}

	public void Agent ()	
	{
		System.out.println("agent initialized");
	}
	
	public void addDataPoint(DataPoint dataPoint)
	{
		dataPoints.put(dataPoint.getTime(), dataPoint);
	}	

	public void addDataPoint(Double time, Double posX, Double posY)
	{
		DataPoint dataPoint = new DataPoint(time, posX, posY);
		dataPoints.put(time,dataPoint);
	}

	public void addDataPoint(Double time, Double posX, Double posY, Double posZ)
	{
		DataPoint dataPoint = new DataPoint(time, posX, posY, posZ);
		dataPoints.put(time,dataPoint);
	}
	
	public DataPoint removeDataPoint(Double time)
	{
		//System.out.println("rem timestep:" + dataPoints.remove(time));
		DataPoint deletedDataPoint = dataPoints.get(time);
		deletedDataPoint = dataPoints.remove(time);
		
		//System.out.println("dp:" + deletedDataPoint.toString());
		
		return deletedDataPoint;
	}

	public DataPoint getDataPoint(double index)
	{
		return this.dataPoints.get(index);
	}
	
}