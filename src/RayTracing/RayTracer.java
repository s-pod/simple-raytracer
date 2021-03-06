package RayTracing;

import java.awt.Transparency;
import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.ComponentColorModel;
import java.awt.image.DataBuffer;
import java.awt.image.DataBufferByte;
import java.awt.image.Raster;
import java.awt.image.SampleModel;
import java.awt.image.WritableRaster;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * Main class for ray tracing exercise.
 */
public class RayTracer {

	public int imageWidth;
	public int imageHeight;
	private Scene scene;
	private final double min_dist_between_hits = 0.0000000001d;
	private final double min_dist_to_hit = 0.0000000001d;

	/**
	 * Runs the ray tracer. Takes scene file, output image file and image size
	 * as input.
	 */
	public static void main(String[] args) {

		try {

			RayTracer tracer = new RayTracer();

			// Default values:
			tracer.imageWidth = 500;
			tracer.imageHeight = 500;

			if (args.length < 2)
				throw new RayTracerException(
						"Not enough arguments provided. Please specify an input scene file and an output image file for rendering.");

			String sceneFileName = args[0];
			String outputFileName = args[1];

			if (args.length > 3) {
				tracer.imageWidth = Integer.parseInt(args[2]);
				tracer.imageHeight = Integer.parseInt(args[3]);
			}

			// Parse scene file:
			tracer.parseScene(sceneFileName);

			// Render scene:
			tracer.renderScene(outputFileName);

		} catch (RayTracerException e) {
			System.out.println(e.getMessage());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}

	/**
	 * Parses the scene file and creates the scene.
	 */
	public void parseScene(String sceneFileName) throws IOException,
			RayTracerException {
		FileReader fr = new FileReader(sceneFileName);

		BufferedReader r = new BufferedReader(fr);
		String line = null;
		int lineNum = 0;
		System.out.println("Started parsing scene file " + sceneFileName);
		scene = new Scene();
		while ((line = r.readLine()) != null) {
			line = line.trim();
			++lineNum;

			if (line.isEmpty() || (line.charAt(0) == '#')) { // This line in the
																// scene file is
																// a comment
				continue;
			} else {
				String code = line.substring(0, 3).toLowerCase();
				// Split according to white space characters:
				String[] params = line.substring(3).trim().split("\\s+");

				if (code.equals("cam")) {
					// Add code here to parse camera parameters
					Vector pos = new Vector(Double.parseDouble(params[0]),
							Double.parseDouble(params[1]),
							Double.parseDouble(params[2]));
					Vector at = new Vector(Double.parseDouble(params[3]),
							Double.parseDouble(params[4]),
							Double.parseDouble(params[5]));
					Vector up = new Vector(Double.parseDouble(params[6]),
							Double.parseDouble(params[7]),
							Double.parseDouble(params[8]));
					Camera cam = new Camera(pos, at, up,
							Double.parseDouble(params[9]),
							Double.parseDouble(params[10]),
							(double) imageHeight / (double) imageWidth);
					scene.setCamera(cam);
					System.out.println(String.format(
							"Parsed camera parameters (line %d)", lineNum));
				} else if (code.equals("set")) {
					// Add code here to parse general settings parameters
					Color col = new Color(Double.parseDouble(params[0]),
							Double.parseDouble(params[1]),
							Double.parseDouble(params[2]));
					scene.setBg_color(col);
					scene.setShad_rays(Integer.parseInt(params[3]));
					scene.setRec_iter(Integer.parseInt(params[4]));
					System.out.println(String.format(
							"Parsed general settings (line %d)", lineNum));
				} else if (code.equals("mtl")) {
					// Add code here to parse material parameters
					Color dc = new Color(Double.parseDouble(params[0]),
							Double.parseDouble(params[1]),
							Double.parseDouble(params[2]));
					Color sc = new Color(Double.parseDouble(params[3]),
							Double.parseDouble(params[4]),
							Double.parseDouble(params[5]));
					Color rc = new Color(Double.parseDouble(params[6]),
							Double.parseDouble(params[7]),
							Double.parseDouble(params[8]));
					double incidence = 0;
					if (params.length > 11) {
						incidence = Double.parseDouble(params[11]);
					}
					Material mat = new Material(dc, sc, rc,
							Double.parseDouble(params[9]),
							Double.parseDouble(params[10]), incidence);
					scene.materials.add(mat);
					System.out.println(String.format(
							"Parsed material (line %d)", lineNum));
				} else if (code.equals("sph")) {
					// Add code here to parse sphere parameters
					Vector pos = new Vector(Double.parseDouble(params[0]),
							Double.parseDouble(params[1]),
							Double.parseDouble(params[2]));
					Shape sph = new Sphere(pos, Double.parseDouble(params[3]),
							null, Integer.parseInt(params[4]) - 1);
					scene.shapes.add(sph);
					System.out.println(String.format("Parsed sphere (line %d)",
							lineNum));
				} else if (code.equals("pln")) {
					// Add code here to parse plane parameters
					Vector pos = new Vector(Double.parseDouble(params[0]),
							Double.parseDouble(params[1]),
							Double.parseDouble(params[2]));
					Shape pln = new Plane(pos, Double.parseDouble(params[3]),
							null, Integer.parseInt(params[4]) - 1);
					scene.shapes.add(pln);
					System.out.println(String.format("Parsed plane (line %d)",
							lineNum));
				} else if (code.equals("lgt")) {
					// Add code here to parse light parameters
					Vector pos = new Vector(Double.parseDouble(params[0]),
							Double.parseDouble(params[1]),
							Double.parseDouble(params[2]));
					Color c = new Color(Double.parseDouble(params[3]),
							Double.parseDouble(params[4]),
							Double.parseDouble(params[5]));
					Light lgt = new Light(pos, c,
							Double.parseDouble(params[6]),
							Double.parseDouble(params[7]),
							Double.parseDouble(params[8]));
					scene.lights.add(lgt);
					System.out.println(String.format("Parsed light (line %d)",
							lineNum));
				} else if (code.equals("pnt")) {
					// Add code here to parse light parameters
					String fname = params[0];
					int size = Integer.parseInt(params[1]);
					Color c = new Color(Double.parseDouble(params[2]),
							Double.parseDouble(params[3]),
							Double.parseDouble(params[4]));
					PointCloud pcloud = new PointCloud(size, c);
					scene.pcloud.add(pcloud);
					PlyParser.parser(fname, pcloud);
					System.out.println(String.format(
							"Parsed point cloud (line %d)", lineNum));
				} else {
					System.out.println(String.format(
							"ERROR: Did not recognize object: %s (line %d)",
							code, lineNum));
				}
			}
		}

		// It is recommended that you check here that the scene is valid,
		// for example camera settings and all necessary materials were defined.
		if (scene.getCamera() == null) {
			System.out.println("Camera not defined");
			System.exit(0);
		}

		for (int i = 0; i < scene.shapes.size(); i++) {
			if (scene.shapes.get(i).getMatIndex() > scene.materials.size()) {
				System.out.println("Undefined material index");
				System.exit(0);
			}
			scene.shapes.get(i).setMaterial(
					scene.materials.get(scene.shapes.get(i).getMatIndex()));
		}
		System.out.println("Finished parsing scene file " + sceneFileName);
		r.close();
	}

	/**
	 * returns intersection with the nearest object
	 */
	public Intersection intersectFirst(Ray r) {
		double min_t = Double.MAX_VALUE;
		int min_ind = -1;

		for (int i = 0; i < scene.shapes.size(); i++) {
			double t = scene.shapes.get(i).intersect(r);
			if (t > min_dist_to_hit && t < min_t) {
				min_t = t;
				min_ind = i;
			}
		}
		if (min_ind > -1) {
			return new Intersection(scene.shapes.get(min_ind).getType(),
					min_ind, min_t, r);
		} else {
			// no intersection at all
			return null;
		}
	}

	/**
	 * returns the energy of light ray, if it passed through transparent objects
	 * its energy gets decreased.
	 */
	public double rayEnergy(Ray light_ray, Vector end_point, double max_value) {
		Intersection light_hit = intersectFirst(light_ray);
		if (light_hit != null) {
			Vector light_hit_point = Vector.sum(
					light_ray.getPosition(),
					Vector.multiplyByConst(light_ray.getDirection(),
							light_hit.getT()));
			double dist_between_hits = Vector.square_dist(end_point,
					light_hit_point);
			if (dist_between_hits < min_dist_between_hits) {
				return max_value;
			} else {
				Material mat = Intersection.getHitMaterial(light_hit, scene);
				if (mat.getTrans() > 0) {
					Ray new_ray = new Ray(light_hit_point,
							light_ray.getDirection());
					return mat.getTrans()
							* rayEnergy(new_ray, end_point, max_value);
				} else {
					return 0;
				}
			}
		} else {
			return 0;
		}
	}

	/**
	 * Constructs Ray through pixel (x,y)
	 */
	public Ray ConstructRayThroughPixel(int x, int y) {
		double screen_w = scene.getCamera().getScreen_width();
		double screen_h = scene.getCamera().getScreen_height();
		double dist = scene.getCamera().getScreen_dist();
		Vector camera_pos = scene.getCamera().getPosition();
		Vector towards = scene.getCamera().getTowards();
		Vector up = scene.getCamera().getUp();
		Vector right = Vector.crossProd(towards, up);
		Vector.normalize(right);
		Vector left_screen_edge = Vector.sum(
				camera_pos,
				Vector.sum(Vector.multiplyByConst(towards, dist),
						Vector.multiplyByConst(right, -1 * screen_w / 2.0d)));
		double x_offset = screen_w
				* (((double) x + 0.5d) / (double) imageWidth);
		Vector v1 = Vector.sum(left_screen_edge,
				Vector.multiplyByConst(right, x_offset));

		double y_offset = screen_h / 2.0d - screen_h
				* (((double) y + 0.5d) / (double) imageHeight);
		Vector v2 = Vector.multiplyByConst(up, y_offset);
		Vector pixel_pos = Vector.sum(v1, v2);
		Vector ray_direction = Vector.sum(pixel_pos,
				Vector.multiplyByConst(camera_pos, -1));
		Vector.normalize(ray_direction);
		Vector.normalize(right);

		return new Ray(camera_pos, ray_direction);
	}

	/**
	 * 
	 * @param point
	 * @return returns 2D vector which is the position of projected point on
	 *         screen
	 */
	public Vector projectPointOnScreen(Point point) {

		double screenDist = scene.getCamera().getScreen_dist();
		Vector camera_pos = scene.getCamera().getPosition();
		Vector towards = scene.getCamera().getTowards();
		Vector up = scene.getCamera().getUp();
		Vector right = Vector.crossProd(towards, up);
		Vector.normalize(right);
		Vector pos = Vector.sum(point.getP(),
				Vector.multiplyByConst(camera_pos, -1));
		double ratio = Vector.dotProd(pos, towards) / screenDist;
		Vector screenProj = Vector.multiplyByConst(pos, 1.0 / ratio);
		Vector centerOfScreen = Vector.multiplyByConst(towards, screenDist);
		// vector from the center of screen to the projected point
		Vector centerToProj = Vector.sum(screenProj,
				Vector.multiplyByConst(centerOfScreen, -1));
		double x_offset = Vector.dotProd(centerToProj, right);
		double half_screen_width = scene.getCamera().getScreen_width() / 2;
		if (x_offset < -1 * half_screen_width || x_offset > half_screen_width) {
			return null;
		}
		x_offset = x_offset + half_screen_width;
		double y_offset = Vector.dotProd(centerToProj, up);
		double half_screen_height = scene.getCamera().getScreen_height() / 2;
		if (y_offset < -half_screen_height || y_offset > half_screen_height) {
			return null;
		}
		y_offset = (-1 * y_offset + half_screen_height);
		point.setDist(Math.sqrt(Vector.square_dist(point.getP(), camera_pos)));
		return new Vector(x_offset, y_offset, 0);
	}

	/**
	 * returns the color of hit point which caused by lights
	 */
	public Color calcLightColor(Vector hit_point, Intersection hit) {
		Color result = new Color(0, 0, 0);
		Vector N;
		Material mat;
		N = scene.shapes.get(hit.getIndex()).getNormal(hit_point);
		mat = scene.shapes.get(hit.getIndex()).getMaterial();

		double max_ray_energy = (1.0d / (scene.getShad_rays() * scene
				.getShad_rays()));
		for (int i = 0; i < scene.lights.size(); i++) {
			double point_light_intensity = 0;
			for (int n = 0; n < scene.getShad_rays(); n++) {
				for (int m = 0; m < scene.getShad_rays(); m++) {
					Ray light_ray = scene.lights.get(i)
							.ConstructRayThroughPoint(n, m, hit_point,
									scene.getCamera().getUp(),
									scene.getShad_rays());
					double energy = rayEnergy(light_ray, hit_point,
							max_ray_energy);
					point_light_intensity += energy;

				}
			}
			point_light_intensity *= scene.lights.get(i).getShadow_int();
			point_light_intensity += (1 - scene.lights.get(i).getShadow_int());

			if (point_light_intensity == 0) {
				continue;
			}
			// calculate diffuse light
			Vector light_direction = Vector.sum(Vector.multiplyByConst(
					scene.lights.get(i).getPosition(), -1), hit_point);
			Vector.normalize(light_direction);
			Color new_col = new Color(0, 0, 0);
			Vector L = Vector.multiplyByConst(light_direction, -1);
			double diffuse_intensity = Vector.dotProd(N, L);
			if (diffuse_intensity > 0) {
				new_col = Color.multiply(scene.lights.get(i).getColor(),
						diffuse_intensity);
				new_col = Color.multiply(new_col, mat.getDiffuse());
			}

			// calculate specular light
			Vector R = Vector.reflect(light_direction, N);
			Vector.normalize(R);
			double spec_intensity = Vector.dotProd(R,
					Vector.multiplyByConst(hit.getRay().getDirection(), -1));
			if (spec_intensity > 0) {
				spec_intensity = Math.pow(spec_intensity, mat.getPhong());
				new_col = Color.add(new_col, Color.multiply(scene.lights.get(i)
						.getColor(), Color.multiply(mat.getSpecular(),
						spec_intensity * scene.lights.get(i).getSpec_int())));
			}
			// multiply by point light intensity
			new_col = Color.multiply(new_col, point_light_intensity);

			result = Color.add(result, new_col);
		}
		return result;
	}

	/**
	 * returns the color of hit point which consists of light transparency and
	 * reflections
	 */
	public Color calcColor(Ray r, int iterations) {
		if (iterations == scene.getRec_iter()) {
			return scene.getBg_color();
		}
		Intersection hit = intersectFirst(r);
		if (hit == null) {
			return scene.getBg_color();
		}
		Vector normal;
		Vector hit_point = Vector.sum(r.getPosition(),
				Vector.multiplyByConst(r.getDirection(), hit.getT()));
		Material mat;
		mat = scene.shapes.get(hit.getIndex()).getMaterial();
		normal = scene.shapes.get(hit.getIndex()).getNormal(hit_point);

		// light
		Color result = calcLightColor(hit_point, hit);
		double trans = mat.getTrans();
		double iv = 0;
		double mi = mat.getIncidence();

		// incidence
		if (mat.getIncidence() > 0) {
			iv = -1 * Vector.dotProd(r.getDirection(), normal);
			mi = mat.getIncidence();
			trans = incidentTrans(mat.getTrans(), mi, iv);
		}

		// transparency
		result = Color.multiply(result, 1 - trans);
		if (mat.getTrans() > 0) {
			Ray new_ray = new Ray(hit_point, r.getDirection());
			if (mat.getIncidence() == 0) {
				result = Color.add(result, Color.multiply(
						calcColor(new_ray, iterations + 1), trans));
			} else {
				result = Color.add(result, Color.multiply(
						calcColor(new_ray, iterations + 1), trans));
			}
		}
		// reflections
		if (mat.getReflect().getR() > 0 || mat.getReflect().getG() > 0
				|| mat.getReflect().getB() > 0) {
			Ray new_ray = new Ray(hit_point, Vector.reflect(r.getDirection(),
					normal));
			if (mat.getIncidence() == 0) {
				result = Color.add(
						result,
						Color.multiply(calcColor(new_ray, iterations + 1),
								mat.getReflect()));
			} else {
				result = Color.add(result, Color.multiply(
						calcColor(new_ray, iterations + 1),
						incidentReflect(mat.getReflect(), mi, iv)));

			}
		}
		return result;
	}

	/**
	 * returns the new reflect color after applying incidence
	 */
	private Color incidentReflect(Color c, double mi, double iv) {
		return Color.add(Color.multiply(c, (1 - mi)),
				Color.multiply(c, mi * (1 - iv)));
	}

	/**
	 * returns the new transparency value after applying incidence
	 */
	private Double incidentTrans(double trans, double mi, double iv) {
		return trans * (1 - mi) + trans * mi * iv;
	}

	/**
	 * Fills a circle of received point into screen[][] array
	 * 
	 */
	private void fillScreenSircle(Point[][] screen, int x, int y, Point p) {
		double size = p.getCloud().getP_size() / p.getDist();
		int w_begin = (int) Math.max(0, Math.floor(x - size));
		int w_end = (int) Math.min(screen.length, Math.ceil(x + size));
		int h_begin = (int) Math.max(0, Math.floor(y - size));
		int h_end = (int) Math.min(screen[0].length, Math.ceil(y + size));
		for (int i = w_begin; i < w_end; i++) {
			for (int j = h_begin; j < h_end; j++) {
				if ((i - x) * (i - x) + (j - y) * (j - y) <= size * size / 4) {
					if (screen[i][j] == null) {
						screen[i][j] = p;
					} else if (screen[i][j].getDist() > p.getDist()) {
						screen[i][j] = p;
					}
				}
			}
		}
	}

	/**
	 * Renders the loaded scene and saves it to the specified file location.
	 */
	public void renderScene(String outputFileName) {
		long startTime = System.currentTimeMillis();
		int x, y, i, j;
		// Create a byte array to hold the pixel data:
		byte[] rgbData = new byte[this.imageWidth * this.imageHeight * 3];
		Point[][] screen = new Point[imageWidth][imageHeight];
		for (i = 0; i < scene.pcloud.size(); i++) {
			scene.pcloud.get(i).calcBBox();
			for (j = 0; j < scene.pcloud.get(i).getCloud().size(); j++) {
				Point p = scene.pcloud.get(i).getCloud().get(j);
				Vector proj = projectPointOnScreen(p);
				if (proj == null) {
					continue;
				}
				fillScreenSircle(screen,
						(int) Math.round(proj.getX() * imageWidth),
						(int) Math.round(proj.getY() * imageHeight), p);
			}
			System.out.println(scene.pcloud.get(i));
			for (int k = 0; k < scene.pcloud.get(i).getBbox().getCloud().size(); k++) {
				Point p = scene.pcloud.get(i).getBbox().getCloud().get(k);
				Vector proj = projectPointOnScreen(p);
				if (proj == null) {
					continue;
				}
				fillScreenSircle(screen,
						(int) Math.round(proj.getX() * imageWidth),
						(int) Math.round(proj.getY() * imageHeight), p);
			}
		}

		for (y = 0; y < imageHeight; y++) {
			// render scene
			for (x = 0; x < imageWidth; x++) {
				if (screen[x][y] != null) {
					Color c = screen[x][y].getCloud().getColor();
					rgbData[(y * this.imageWidth + (imageWidth - x - 1)) * 3 + 0] = (byte) (c
							.getR() * 255);
					rgbData[(y * this.imageWidth + (imageWidth - x - 1)) * 3 + 1] = (byte) (c
							.getG() * 255);
					rgbData[(y * this.imageWidth + (imageWidth - x - 1)) * 3 + 2] = (byte) (c
							.getB() * 255);
					continue;
				}
				Ray r = ConstructRayThroughPixel(x, y);
				Color c = calcColor(r, 0);
				rgbData[(y * this.imageWidth + (imageWidth - x - 1)) * 3 + 0] = (byte) (Math
						.min(c.getR() * 255, 255));
				rgbData[(y * this.imageWidth + (imageWidth - x - 1)) * 3 + 1] = (byte) (Math
						.min(c.getG() * 255, 255));
				rgbData[(y * this.imageWidth + (imageWidth - x - 1)) * 3 + 2] = (byte) (Math
						.min(c.getB() * 255, 255));
			}
		}

		long endTime = System.currentTimeMillis();
		Long renderTime = endTime - startTime;

		// The time is measured for your own conveniece, rendering speed will
		// not affect your score
		// unless it is exceptionally slow (more than a couple of minutes)
		System.out.println("Finished rendering scene in "
				+ renderTime.toString() + " milliseconds.");

		// This is already implemented, and should work without adding any code.
		saveImage(this.imageWidth, rgbData, outputFileName);

		System.out.println("Saved file " + outputFileName);

	}

	// ////////////////////// FUNCTIONS TO SAVE IMAGES IN PNG FORMAT
	// //////////////////////////////////////////

	/*
	 * Saves RGB data as an image in png format to the specified location.
	 */
	public static void saveImage(int width, byte[] rgbData, String fileName) {
		try {

			BufferedImage image = bytes2RGB(width, rgbData);
			ImageIO.write(image, "png", new File(fileName));

		} catch (IOException e) {
			System.out.println("ERROR SAVING FILE: " + e.getMessage());
		}

	}

	/*
	 * Producing a BufferedImage that can be saved as png from a byte array of
	 * RGB values.
	 */
	public static BufferedImage bytes2RGB(int width, byte[] buffer) {
		int height = buffer.length / width / 3;
		ColorSpace cs = ColorSpace.getInstance(ColorSpace.CS_sRGB);
		ColorModel cm = new ComponentColorModel(cs, false, false,
				Transparency.OPAQUE, DataBuffer.TYPE_BYTE);
		SampleModel sm = cm.createCompatibleSampleModel(width, height);
		DataBufferByte db = new DataBufferByte(buffer, width * height);
		WritableRaster raster = Raster.createWritableRaster(sm, db, null);
		BufferedImage result = new BufferedImage(cm, raster, false, null);
		return result;
	}

	@SuppressWarnings("serial")
	public static class RayTracerException extends Exception {
		public RayTracerException(String msg) {
			super(msg);
		}
	}

}
