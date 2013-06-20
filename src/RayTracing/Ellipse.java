package RayTracing;

import Jama.Matrix;

public class Ellipse extends Sphere {

	private Matrix trans;

	public Ellipse(Vector center, double radius, Material mat, int matIndex,
			Matrix transformation) {
		super(center, radius, mat, matIndex);
		trans = transformation;
		setType(sType.ELLIPSE);
	}

	/**
	 * @return the trans
	 */
	public Matrix getTrans() {
		return trans;
	}

	/**
	 * @param trans
	 *            the trans to set
	 */
	public void setTrans(Matrix trans) {
		this.trans = trans;
	}

	public Vector getNormal(Vector point) {
		Vector ret = super.getNormal(point);

		return ret;
	}

	public double intersect(Ray r) {
		// transform r
		Vector new_pos = new Vector(trans.copy().times(
				r.getPosition().toMatrix()));
		Vector new_direction = new Vector(trans.copy().times(
				r.getDirection().toMatrix()));
		// run super method
		return super.intersect(new Ray(new_pos, new_direction));
	}

}
