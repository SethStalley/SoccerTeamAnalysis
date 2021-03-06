/*
 * @author Seth Stalley
 * @version v1.1.1
 */
package teamidentifier;

public class KMeans {
	// Number of clusters, we default to 2 because there are two soccer teams.
	private int NUM_CLUSTERS = 2;

	/**
	 * Constructor for KMeans.
	 * 
	 * @param numClusters int
	 */
	public KMeans(int numClusters) {
		this.NUM_CLUSTERS = numClusters;
	}

	/**
	 * Calculate Bhattacharyya distance.
	 * 
	 * @param X double[]
	 * @param U double[]
	 * @return - double value. * @throws Exception
	 */
	public static double distance(double X[], double U[]) throws Exception {
		double sum = 0.0;

		if (X.length != U.length) {
			throw new Exception("Muestras no son del mismo largo!");
		}

		for (int i = 0; i < X.length; i++) {
			sum += Math.sqrt(Math.abs(X[i] - U[i]));
		}

		return sum;
	}

}
