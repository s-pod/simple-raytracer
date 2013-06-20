package RayTracing;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class PlyParser {

	static public PointCloud parser(String filename, PointCloud point_cloud)
			throws IOException {
		File file = new File(filename);
		Scanner scanner = new Scanner(file);
		boolean end_header = false;
		System.out.println("Start parsing of file " + filename);
		while (scanner.hasNextLine()) {
			if (scanner.nextLine().equals("end_header"))
				end_header = true;
			if (end_header && scanner.hasNextFloat()) {
				float x = scanner.nextFloat();
				float y = scanner.nextFloat();
				float z = scanner.nextFloat();
				float nx = scanner.nextFloat();
				float ny = scanner.nextFloat();
				float nz = scanner.nextFloat();
				point_cloud.addPoint(new Point(new Vector(x, y, z), new Vector(
						nx, ny, nz),point_cloud));
			}
		}
		System.out.println("Finishing parsing");
		return point_cloud;
	}
}
