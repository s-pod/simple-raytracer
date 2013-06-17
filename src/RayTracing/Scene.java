package RayTracing;

import java.util.ArrayList;
import java.util.List;

public class Scene {

	private Camera camera = null;
	public List<Light> lights = null;
	public List<Sphere> spheres = null;
	public List<Plane> planes = null;
	public List<Material> materials = null;
	private Color bg_color = null;
	private int shad_rays, rec_iter;
	public List<Vector> screen = null;
	public List<PointCloud> pcloud = null;
	public List<Ellipse> ellipses = null;

	public Scene() {
		lights = new ArrayList<Light>();
		spheres = new ArrayList<Sphere>();
		planes = new ArrayList<Plane>();
		materials = new ArrayList<Material>();
		screen = new ArrayList<Vector>();
		pcloud = new ArrayList<PointCloud>();
		ellipses = new ArrayList<Ellipse>();
	}

	/**
	 * @return the bg_color
	 */
	public Color getBg_color() {
		return bg_color;
	}

	/**
	 * @param bg_color
	 *            the bg_color to set
	 */
	public void setBg_color(Color bg_color) {
		this.bg_color = bg_color;
	}

	/**
	 * @return the shad_rays
	 */
	public int getShad_rays() {
		return shad_rays;
	}

	/**
	 * @param shad_rays
	 *            the shad_rays to set
	 */
	public void setShad_rays(int shad_rays) {
		this.shad_rays = shad_rays;
	}

	/**
	 * @return the rec_iter
	 */
	public int getRec_iter() {
		return rec_iter;
	}

	/**
	 * @param rec_iter
	 *            the rec_iter to set
	 */
	public void setRec_iter(int rec_iter) {
		this.rec_iter = rec_iter;
	}

	/**
	 * @return the camera
	 */
	public Camera getCamera() {
		return camera;
	}

	/**
	 * @param camera
	 *            the camera to set
	 */
	public void setCamera(Camera camera) {
		this.camera = camera;
	}

}
