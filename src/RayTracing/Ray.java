package RayTracing;

public class Ray {
	private Vector position,direction;

	/**
	 * @param position
	 * @param direction
	 */
	public Ray(Vector position, Vector direction) {
		this.position = position;
		this.direction = direction;
	}

	/**
	 * @return the position
	 */
	public Vector getPosition() {
		return position;
	}

	/**
	 * @return the direction
	 */
	public Vector getDirection() {
		return direction;
	}

}
