package RayTracing;

public class Color {
	private double r, g, b;

	public Color(double r, double g, double b) {
		this.setR(r);
		this.setG(g);
		this.setB(b);
	}

	/**
	 * @return the r
	 */
	public double getR() {
		return r;
	}

	/**
	 * @param r
	 *            the r to set
	 */
	public void setR(double r) {
		this.r = r;
	}

	/**
	 * @return the g
	 */
	public double getG() {
		return g;
	}

	/**
	 * @param g
	 *            the g to set
	 */
	public void setG(double g) {
		this.g = g;
	}

	/**
	 * @return the b
	 */
	public double getB() {
		return b;
	}

	/**
	 * @param b
	 *            the b to set
	 */
	public void setB(double b) {
		this.b = b;
	}

	public static Color add(Color a, Color b) {
		return new Color(a.getR() + b.getR(),a.getG() + b.getG(),a.getB() + b.getB());
	}

	public static Color multiply(Color a, double x) {
		return new Color(a.getR() * x,a.getG() * x,a.getB() * x);
	}

	public static Color multiply(Color a, Color b) {
		return new Color(a.getR() * b.getR(),a.getG() * b.getG(),a.getB() * b.getB());
	}


}
