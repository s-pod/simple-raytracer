package RayTracing;

import Jama.Matrix;

public class Ellipse extends Sphere {

	private Matrix trans;

	public Ellipse(Vector center, double radius, Material mat, int matIndex,
			Matrix transformation) {
		super(center, radius, mat, matIndex);
		// TODO Auto-generated constructor stub
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
//		Matrix tmp1 = Matrix.identity(4, 4);
//		tmp1.set(0, 3, getCenter().getX());
//		tmp1.set(1, 3, getCenter().getY());
//		tmp1.set(2, 3, getCenter().getZ());
//		Matrix tmp2 = Matrix.identity(4, 4);
//		tmp2.set(0, 3, -getCenter().getX());
//		tmp2.set(1, 3, -getCenter().getY());
//		tmp2.set(2, 3, -getCenter().getZ());
//		Matrix scale = Matrix.identity(4, 4);
//		scale.set(1, 1, 1.55);
//		scale.set(0, 0, 0.55);
//		trans = scale;
		Vector new_pos = new Vector(trans.copy().times(
				r.getPosition().toMatrix()));
		Vector new_direction = new Vector(trans.copy().times(
				r.getDirection().toMatrix()));
		// run super method
		return super.intersect(new Ray(r.getPosition(), new_direction));
	}

}
