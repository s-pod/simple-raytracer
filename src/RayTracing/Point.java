package RayTracing;

public class Point {
	private Vector p;
	private Vector n;
	private PointCloud cloud;
	private double dist;

	/**
	 * @return the dist
	 */
	public double getDist() {
		return dist;
	}


	/**
	 * @param dist the dist to set
	 */
	public void setDist(double dist) {
		this.dist = dist;
	}


	/**
	 * @param p
	 * @param n
	 */
	public Point(Vector p, Vector n, PointCloud cloud) {
		this.p = p;
		this.n = n;
		this.cloud = cloud;
	}


	/**
	 * @return the cloud
	 */
	public PointCloud getCloud() {
		return cloud;
	}


	/**
	 * @param cloud the cloud to set
	 */
	public void setCloud(PointCloud cloud) {
		this.cloud = cloud;
	}


	/**
	 * @return the p
	 */
	public Vector getP() {
		return p;
	}

	/**
	 * @param p
	 *            the p to set
	 */
	public void setP(Vector p) {
		this.p = p;
	}

	/**
	 * @return the n
	 */
	public Vector getN() {
		return n;
	}

	/**
	 * @param n
	 *            the n to set
	 */
	public void setN(Vector n) {
		this.n = n;
	}

}
