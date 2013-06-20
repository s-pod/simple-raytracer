package RayTracing;

public class Sphere extends Shape {
	private Vector center;
	private double radius;

	/**
	 * @param center
	 * @param radius
	 * @param mat
	 */
	public Sphere(Vector center, double radius, Material mat, int matIndex) {
		super(sType.SPHERE, mat, matIndex);
		this.center = center;
		this.radius = radius;
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
	// Function to calculate t to intersection point with sphere(if exist)
	// input: sphere and ray
	// output: t to intersection point if exist, else -1
	@Override
	public double intersect(Ray r) {
		Vector tmpVector = Vector.sum(center,
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
			if (lenProj > radius * radius)
				return t;
			dotDistFrom = Math.sqrt(radius * radius - lenProj);
			t = dotDistTo - dotDistFrom;
		}
		return t;
	}

	public Vector getNormal(Vector point) {
		Vector normal = Vector.sum(point,
				Vector.multiplyByConst(center, -1));
		Vector.normalize(normal);
		return normal;
	}

}
