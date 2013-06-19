package RayTracing;

import java.util.ArrayList;
import java.util.List;

import Jama.Matrix;
import Jama.SingularValueDecomposition;

public class PointCloud {
	private List<Point> cloud;
	private int p_size;
	private Color color;
	private List<Vector> bbox_vectors;
	private List<Vector> bbox_points;
	private Vector centerOfMass;
	private Vector centerOfBox;

	/**
	 * @return the bbox_vectors
	 */
	public List<Vector> getBbox_vectors() {
		return bbox_vectors;
	}

	/**
	 * @param bbox_vectors the bbox_vectors to set
	 */
	public void setBbox_vectors(List<Vector> bbox_vectors) {
		this.bbox_vectors = bbox_vectors;
	}

	/**
	 * @return the bbox_points
	 */
	public List<Vector> getBbox_points() {
		return bbox_points;
	}

	/**
	 * @param bbox_points the bbox_points to set
	 */
	public void setBbox_points(List<Vector> bbox_points) {
		this.bbox_points = bbox_points;
	}

	/**
	 * @return the centerOfBox
	 */
	public Vector getCenterOfBox() {
		return centerOfBox;
	}

	/**
	 * @param centerOfBox the centerOfBox to set
	 */
	public void setCenterOfBox(Vector centerOfBox) {
		this.centerOfBox = centerOfBox;
	}

	/**
	 * @param p_size
	 * @param color
	 */
	public PointCloud(int p_size, Color color) {
		this.p_size = p_size;
		this.color = color;
		this.cloud = new ArrayList<Point>();
		bbox_vectors = new ArrayList<Vector>();
		bbox_points = new ArrayList<Vector>();
	}

	/**
	 * @return the p_size
	 */
	public int getP_size() {
		return p_size;
	}

	/**
	 * @param p_size
	 *            the p_size to set
	 */
	public void setP_size(int p_size) {
		this.p_size = p_size;
	}

	/**
	 * @return the color
	 */
	public Color getColor() {
		return color;
	}

	/**
	 * @param color
	 *            the color to set
	 */
	public void setColor(Color color) {
		this.color = color;
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

	public void calcBBox() {
		centerOfMass = centerOfMass();
		int offset = (int) Math.floor(cloud.size() / 1000.0d);
		double[][] m = new double[3][1000];
		for (int i = 0; i < 1000; i++) {
			m[0][i] = cloud.get(i * offset).getP().getX() - centerOfMass.getX();
			m[1][i] = cloud.get(i * offset).getP().getY() - centerOfMass.getY();
			m[2][i] = cloud.get(i * offset).getP().getZ() - centerOfMass.getZ();
		}
		Matrix mat = new Matrix(m);
		SingularValueDecomposition svd = new SingularValueDecomposition(mat);
		Matrix u = svd.getU();
		for (int i = 0; i < 3; i++) {
			Vector v = new Vector(u.get(0, i), u.get(1, i), u.get(2, i));
			Vector.normalize(v);
			double neg_len = 0, pos_len = 0;
			for (int n = 0; n < cloud.size(); n++) {
				double dotp = Vector.dotProd(
						v,
						Vector.sum(cloud.get(n).getP(),
								Vector.multiplyByConst(centerOfMass, -1)));
				if (neg_len > dotp) {
					neg_len = dotp;
				}
				if (pos_len < dotp) {
					pos_len = dotp;
				}
			}
			bbox_vectors.add(Vector.multiplyByConst(v, pos_len));
			bbox_vectors.add(Vector.multiplyByConst(v, neg_len));
		}
		for (int i = 0; i < 2; i++) {
			Vector p1 = Vector.sum(
					Vector.sum(Vector.sum(bbox_vectors.get(i),
							bbox_vectors.get(2)), bbox_vectors.get(4)),
					centerOfMass);
			Vector p2 = Vector.sum(
					Vector.sum(Vector.sum(bbox_vectors.get(i),
							bbox_vectors.get(2)), bbox_vectors.get(5)),
					centerOfMass);
			Vector p3 = Vector.sum(
					Vector.sum(Vector.sum(bbox_vectors.get(i),
							bbox_vectors.get(3)), bbox_vectors.get(5)),
					centerOfMass);
			Vector p4 = Vector.sum(
					Vector.sum(Vector.sum(bbox_vectors.get(i),
							bbox_vectors.get(3)), bbox_vectors.get(4)),
					centerOfMass);
			bbox_points.add(p1);
			bbox_points.add(p2);
			bbox_points.add(p3);
			bbox_points.add(p4);
		}
	}

	/**
	 * @return the centerOfMass
	 */
	public Vector getCenterOfMass() {
		return centerOfMass;
	}

	/**
	 * @param centerOfMass
	 *            the centerOfMass to set
	 */
	public void setCenterOfMass(Vector centerOfMass) {
		this.centerOfMass = centerOfMass;
	}

	public Vector centerOfMass() {
		double sumX = 0, sumY = 0, sumZ = 0;
		double size = cloud.size();
		for (int i = 0; i < size; i++) {
			sumX += cloud.get(i).getP().getX();
			sumY += cloud.get(i).getP().getY();
			sumZ += cloud.get(i).getP().getZ();
		}
		return new Vector(sumX / size, sumY / size, sumZ / size);
	}

	public String toString() {
		String s = "";
		for (int i = 0; i < bbox_points.size(); i++) {
			s += bbox_points.get(i) + "\n";

		}
		return s;

	}

}
