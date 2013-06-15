package RayTracing;

public class Vector {
	private double x, y, z;

	/**
	 * @param x
	 * @param y
	 * @param z
	 */
	public Vector(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	/**
	 * @return the x
	 */
	public double getX() {
		return x;
	}

	/**
	 * @param x
	 *            the x to set
	 */
	public void setX(double x) {
		this.x = x;
	}

	/**
	 * @return the y
	 */
	public double getY() {
		return y;
	}

	/**
	 * @param y
	 *            the y to set
	 */
	public void setY(double y) {
		this.y = y;
	}

	/**
	 * @return the z
	 */
	public double getZ() {
		return z;
	}

	/**
	 * @param z
	 *            the z to set
	 */
	public void setZ(double z) {
		this.z = z;
	}

	/*
	 * Vector Math
	 */
	static Vector sum(Vector a, Vector b) {
		return new Vector(a.getX() + b.getX(), a.getY() + b.getY(), a.getZ()
				+ b.getZ());
	}

	static Vector multiplyByConst(Vector a, double sf) {
		return new Vector(sf * a.getX(), sf * a.getY(), sf * a.getZ());
	}

	// returns the dot product of 2 vectors
	static double dotProd(Vector a, Vector b) {
		return a.getX() * b.getX() + a.getY() * b.getY() + a.getZ() * b.getZ();
	}

	// returns the cross product of 2 vectors
	static Vector crossProd(Vector a, Vector b) {
		double x = a.getY() * b.getZ() - a.getZ() * b.getY();
		double y = a.getZ() * b.getX() - a.getX() * b.getZ();
		double z = a.getX() * b.getY() - a.getY() * b.getX();
		return new Vector(x, y, z);
	}

	// returns the inner product of 2 vectors
	static double innerProd(Vector a, Vector b) {
		return a.getX() * b.getX() + a.getY() * b.getY() + a.getZ() * b.getZ();
	}

	// normalize the vector
	static void normalize(Vector a) {
		double norm = Math.sqrt(innerProd(a, a));
		a.setX(a.getX() / norm);
		a.setY(a.getY() / norm);
		a.setZ(a.getZ() / norm);
	}

	static double square_dist(Vector a, Vector b) {
		Vector tmp = Vector.sum(a, Vector.multiplyByConst(b, -1));
		return Vector.dotProd(tmp, tmp);
	}

	static public Vector reflect(Vector direction, Vector normal) {
		Vector r = Vector.sum(
				Vector.multiplyByConst(normal, Vector.dotProd(
						Vector.multiplyByConst(direction, -2), normal)),
				direction);
		return r;
	}

	@Override
	public String toString() {
		// TODO: unnecessary. Made for testing purposes.
		return "(" + x + "," + y + "," + z + ")";
	}
}
