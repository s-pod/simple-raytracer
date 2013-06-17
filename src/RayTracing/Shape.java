package RayTracing;

public abstract class Shape {
	private sType type;
	private Material material;
	private int matIndex;

	Shape(sType type, Material material, int matIndex) {
		this.type = type;
		this.material = material;
		this.matIndex = matIndex;
	}

	public abstract double intersect(Ray r);

	public abstract Vector getNormal(Vector point);

	/**
	 * @return the type
	 */
	public sType getType() {
		return type;
	}

	/**
	 * @param type
	 *            the type to set
	 */
	public void setType(sType type) {
		this.type = type;
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

	/**
	 * @return the matIndex
	 */
	public int getMatIndex() {
		return matIndex;
	}

	/**
	 * @param matIndex
	 *            the matIndex to set
	 */
	public void setMatIndex(int matIndex) {
		this.matIndex = matIndex;
	}
}
