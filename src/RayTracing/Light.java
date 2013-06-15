package RayTracing;

import java.util.Random;

public class Light {
	private Vector position;
	private Color color;
	private double spec_int, shadow_int, radius;
	private Random random;

	/**
	 * @param position
	 * @param color
	 * @param spec_int
	 * @param shadow_int
	 * @param radius
	 */
	public Light(Vector position, Color color, double spec_int,
			double shadow_int, double radius) {
		this.position = position;
		this.color = color;
		this.spec_int = spec_int;
		this.shadow_int = shadow_int;
		this.radius = radius;
		random = new Random();
	}

	/**
	 * @return the position
	 */
	public Vector getPosition() {
		return position;
	}

	/**
	 * @return the color
	 */
	public Color getColor() {
		return color;
	}

	/**
	 * @return the spec_int
	 */
	public double getSpec_int() {
		return spec_int;
	}

	/**
	 * @return the shadow_int
	 */
	public double getShadow_int() {
		return shadow_int;
	}

	/**
	 * @return the radius
	 */
	public double getRadius() {
		return radius;
	}

	public Ray ConstructRayThroughPoint(int x, int y, Vector hit_point,
			Vector up_vector, int shadow_rays) {

		double randomD = 0;
		double screen_w = radius;
		double screen_h = radius;
		Vector direction = Vector.sum(position,
				Vector.multiplyByConst(hit_point, -1));
		double dist = Math.sqrt(Vector.dotProd(direction, direction));
		Vector camera_pos = hit_point;
		Vector towards = direction;
		Vector.normalize(towards);
		Vector proj = Vector.multiplyByConst(
				towards,
				Vector.innerProd(towards, up_vector)
						/ Vector.innerProd(towards, towards));
		Vector up = Vector.sum(up_vector, Vector.multiplyByConst(proj, -1));
		Vector.normalize(up);
		Vector right = Vector.crossProd(towards, up);
		Vector.normalize(right);
		Vector left_screen_edge = Vector.sum(
				camera_pos,
				Vector.sum(Vector.multiplyByConst(towards, dist),
						Vector.multiplyByConst(right, -1 * screen_w / 2.0d)));
		randomD =  random.nextDouble();
		double x_offset = screen_w
				* (((double) x + randomD) / (double) shadow_rays);
		Vector v1 = Vector.sum(left_screen_edge,
				Vector.multiplyByConst(right, x_offset));

		randomD =  random.nextDouble();
		double y_offset = screen_h / 2.0d - screen_h
				* (((double) y + randomD) / (double) shadow_rays);
		Vector v2 = Vector.multiplyByConst(up, y_offset);
		Vector pixel_pos = Vector.sum(v1, v2);
		Vector ray_direction = Vector.sum(pixel_pos,
				Vector.multiplyByConst(camera_pos, -1));
		ray_direction = Vector.multiplyByConst(ray_direction, -1);
		Vector.normalize(ray_direction);
		return new Ray(pixel_pos, ray_direction);
	}
}
