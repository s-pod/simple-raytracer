package RayTracing;

public class Camera {
	private Vector position, lookat, up, towards;
	private double screen_dist, screen_width, screen_height;

	/**
	 * @return the screen_height
	 */
	public double getScreen_height() {
		return screen_height;
	}

	/**
	 * @return the position
	 */
	public Vector getPosition() {
		return position;
	}

	/**
	 * @return the lookat
	 */
	public Vector getLookat() {
		return lookat;
	}

	/**
	 * @return the up
	 */
	public Vector getUp() {
		return up;
	}

	/**
	 * @return the screen_dist
	 */
	public double getScreen_dist() {
		return screen_dist;
	}

	/**
	 * @return the screen_width
	 */
	public double getScreen_width() {
		return screen_width;
	}

	/**
	 * @param position
	 * @param lookat
	 * @param up
	 * @param screen_dist
	 * @param screen_width
	 */
	public Camera(Vector position, Vector lookat, Vector up,
			double screen_dist, double screen_width, double aspect) {
		this.position = position;
		this.lookat = lookat;
		this.screen_dist = screen_dist;
		this.screen_width = screen_width;
		this.screen_height = screen_width * aspect;
		towards = Vector.sum(lookat, Vector.multiplyByConst(position, -1));
		Vector proj = Vector.multiplyByConst(
				towards,
				Vector.innerProd(towards, up)
						/ Vector.innerProd(towards, towards));
		this.up = Vector.sum(up, Vector.multiplyByConst(proj, -1));
		Vector.normalize(towards);
		Vector.normalize(this.up);

	}

	/**
	 * @return the towards
	 */
	public Vector getTowards() {
		return towards;
	}
}
