package RayTracing;

import java.util.ArrayList;
import java.util.List;

import Jama.Matrix;
import Jama.SingularValueDecomposition;

public class PointCloud {
	private List<Point> cloud;
	private int p_size;
	private Color color;
	private List<Vector> bbox;
	private Vector centerOfMass;

	/**
	 * @param p_size
	 * @param color
	 */
	public PointCloud(int p_size, Color color) {
		this.p_size = p_size;
		this.color = color;
		this.cloud = new ArrayList<Point>();
		bbox = new ArrayList<Vector>();
	}

	/**
	 * @return the bbox
	 */
	public List<Vector> getBbox() {
		return bbox;
	}

	/**
	 * @param bbox
	 *            the bbox to set
	 */
	public void setBbox(List<Vector> bbox) {
		this.bbox = bbox;
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
			bbox.add(Vector.multiplyByConst(v, pos_len));
			bbox.add(Vector.multiplyByConst(v, neg_len));
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

}
