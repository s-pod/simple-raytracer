package RayTracing;

public class Material {
	
	private Color diffuse,specular,reflect;
	private double phong,trans,incidence;
	/**
	 * @param diffuse
	 * @param sepecular
	 * @param reflect
	 * @param phong
	 * @param trans
	 * @param incidence
	 */
	public Material(Color diffuse, Color sepecular, Color reflect, double phong,
			double trans, double incidence) {
		this.diffuse = diffuse;
		this.specular = sepecular;
		this.reflect = reflect;
		this.phong = phong;
		this.trans = trans;
		this.incidence = incidence;
	}

	public Color getDiffuse() {
		return diffuse;
	}

	public void setDiffuse(Color diffuse) {
		this.diffuse = diffuse;
	}

	public Color getSpecular() {
		return specular;
	}

	public void setSepecular(Color sepecular) {
		this.specular = sepecular;
	}

	public Color getReflect() {
		return reflect;
	}

	public void setReflect(Color reflect) {
		this.reflect = reflect;
	}

	/**
	 * @return the phong
	 */
	public double getPhong() {
		return phong;
	}

	/**
	 * @param phong the phong to set
	 */
	public void setPhong(double phong) {
		this.phong = phong;
	}

	/**
	 * @return the trans
	 */
	public double getTrans() {
		return trans;
	}

	/**
	 * @param trans the trans to set
	 */
	public void setTrans(double trans) {
		this.trans = trans;
	}

	/**
	 * @return the incidence
	 */
	public double getIncidence() {
		return incidence;
	}

	/**
	 * @param incidence the incidence to set
	 */
	public void setIncidence(double incidence) {
		this.incidence = incidence;
	}
}
