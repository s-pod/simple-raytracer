package RayTracing;

import java.util.ArrayList;
import java.util.List;

public class PointCloud {
	private List<Point> cloud;

	/**
	 * @param cloud
	 */
	public PointCloud() {
		this.cloud = new ArrayList<Point>();
	}

	public void addPoint(Point p) {
		cloud.add(p);
	}

	/**
	 * @return the cloud
	 */
	public List<Point> getCloud() {
		return cloud;
	}

	/**
	 * @param cloud
	 *            the cloud to set
	 */
	public void setCloud(List<Point> cloud) {
		this.cloud = cloud;
	}

}
