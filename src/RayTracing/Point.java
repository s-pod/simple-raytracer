package RayTracing;

public class Point {
	private Vector p;
	private Vector n;
	/**
	 * @param p
	 * @param n
	 */
	public Point(Vector p, Vector n) {
		this.p = p;
		this.n = n;
	}
	/**
	 * @return the p
	 */
	public Vector getP() {
		return p;
	}
	/**
	 * @param p the p to set
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
	 * @param n the n to set
	 */
	public void setN(Vector n) {
		this.n = n;
	}
	
}
