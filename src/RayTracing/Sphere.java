package RayTracing;

public class Sphere {
	private Vector center;
	private double radius;
	private Material mat;
	public int matIndex;

	/**
	 * @param center
	 * @param radius
	 * @param mat
	 */
	public Sphere(Vector center, double radius, Material mat, int matIndex) {
		this.center = center;
		this.radius = radius;
		this.mat = mat;
		this.matIndex = matIndex;
	}

	/**
	 * @return the center
	 */
	public Vector getCenter() {
		return center;
	}

	/**
	 * @param center
	 *            the center to set
	 */
	public void setCenter(Vector center) {
		this.center = center;
	}

	/**
	 * @return the radius
	 */
	public double getRadius() {
		return radius;
	}

	/**
	 * @param radius
	 *            the radius to set
	 */
	public void setRadius(double radius) {
		this.radius = radius;
	}

	/**
	 * @return the mat
	 */
	public Material getMat() {
		return mat;
	}

	/**
	 * @param mat
	 *            the mat to set
	 */
	public void setMat(Material mat) {
		this.mat = mat;
	}

	// Function to calculate t to intersection point with sphere(if exist)
	// input: sphere and ray
	// output: t to intersection point if exist, else -1
	static public double sphereIntersec(Sphere s, Ray r) {
		Vector tmpVector = Vector.sum(s.center,
				Vector.multiplyByConst(r.getPosition(), -1));
		double dotDistTo = Vector.dotProd(tmpVector, r.getDirection());
		// System.out.println("Dot:" + dotDistTo);
		double t = -1, lenProj = 0, dotDistFrom = 0;
		if (dotDistTo < 0)
			return t;
		else {
			lenProj = Vector.dotProd(tmpVector, tmpVector) - dotDistTo
					* dotDistTo;
			// System.out.println("prog:" + lenProj);
			if (lenProj > s.radius * s.radius)
				return t;
			dotDistFrom = Math.sqrt(s.radius * s.radius - lenProj);
			t = dotDistTo - dotDistFrom;
		}
		return t;
	}

	public Vector getNormal(Vector hit_point) {
		// TODO Auto-generated method stub
		Vector normal = Vector.sum(hit_point,
				Vector.multiplyByConst(center, -1));
		Vector.normalize(normal);
		return normal;
	}

}
