package RayTracing;

public class Plane {
	private Vector normal;
	private double offset;
	private Material material;
	public int matIndex;

	/**
	 * @param normal
	 * @param offset
	 * @param material
	 */
	public Plane(Vector normal, double offset, Material material, int matIndex) {
		super();
		Vector.normalize(normal);
		this.normal = normal;
		this.offset = offset;
		this.material = material;
		this.matIndex = matIndex;
	}

	/**
	 * @return the normal
	 */
	public Vector getNormal() {
		return normal;
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

	/**
	 * @return the material
	 */
	public Material getMaterial() {
		return material;
	}

	/**
	 * @param material
	 *            the material to set
	 */
	public void setMaterial(Material material) {
		this.material = material;
	}

	public double intersect(Ray r) {
		return (offset - Vector.dotProd(r.getPosition(), normal))
				/ (Vector.dotProd(r.getDirection(), normal));
	}
	
	public void setMat(Material mat){
		this.material = mat;
	}

}
