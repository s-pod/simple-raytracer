package RayTracing;

public class Intersection {
	private sType type;
	private Ray ray;
	private int index;
	private double t;

	/**
	 * @return the type
	 */
	public sType getType() {
		return type;
	}

	/**
	 * @return the index
	 */
	public int getIndex() {
		return index;
	}

	/**
	 * @return the t
	 */
	public double getT() {
		return t;
	}

	/**
	 * @param type
	 * @param index
	 * @param t
	 */
	public Intersection(sType type, int index, double t, Ray r) {
		this.type = type;
		this.index = index;
		this.t = t;
		this.ray = r;
	}

	/**
	 * @return the ray
	 */
	public Ray getRay() {
		return ray;
	}

	static Material getHitMaterial(Intersection hit, Scene scene) {
		Material mat;
		if (hit.getType() == sType.SPHERE) {
			mat = scene.spheres.get(hit.getIndex()).getMat();
		} else {
			mat = scene.planes.get(hit.getIndex()).getMaterial();
		}
		return mat;
	}

	

}
