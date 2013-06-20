package RayTracing;

public class Plane extends Shape {
	private Vector normal;
	private double offset;

	/**
	 * @param normal
	 * @param offset
	 * @param material
	 */
	public Plane(Vector normal, double offset, Material material, int matIndex) {
		super(sType.PLANE, material, matIndex);
		Vector.normalize(normal);
		this.normal = normal;
		this.offset = offset;
	}

	/**
	 * @param normal
	 *            the normal to set
	 */
	public void setNormal(Vector normal) {
		Vector.normalize(normal);
		this.normal = normal;
	}

	/**
	 * @return the offset
	 */
	public double getOffset() {
		return offset;
	}

	/**
	 * @param offset
	 *            the offset to set
	 */
	public void setOffset(double offset) {
		this.offset = offset;
	}

	public double intersect(Ray r) {
		return (offset - Vector.dotProd(r.getPosition(), normal))
				/ (Vector.dotProd(r.getDirection(), normal));
	}

	@Override
	public Vector getNormal(Vector point) {
		return normal;
	}

}
