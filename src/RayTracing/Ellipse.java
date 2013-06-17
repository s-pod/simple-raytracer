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

	public double intersect(Ray r) {
		//transform r
		
		//run super method
		return super.intersect(r);
	}

}
