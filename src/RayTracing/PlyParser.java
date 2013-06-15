package RayTracing;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

/**
 * Created with IntelliJ IDEA.
 * User: artyom
 * Date: 6/15/13
 * Time: 4:36 PM
 * To change this template use File | Settings | File Templates.
 */
public class PlyParser {

    static public PointCloud parser(String filename) throws IOException{
        File file = new File(filename);
        Scanner scanner = new Scanner(file);
        PointCloud point_cloud = new PointCloud();
        boolean end_header = false;
        System.out.println("Start parsing of file " + filename);
        while(scanner.hasNextLine()){
            if (scanner.nextLine().equals("end_header"))
                end_header = true;
            if (end_header && scanner.hasNextFloat()){
                float x = scanner.nextFloat();
                float y = scanner.nextFloat();
                float z = scanner.nextFloat();
                float nx = scanner.nextFloat();
                float ny = scanner.nextFloat();
                float nz = scanner.nextFloat();
                point_cloud.addPoint(new Point(new Vector(x, y, z), new Vector(nx, ny, nz)));
            }
        }
        System.out.println("Finishing parsing");
        return point_cloud;
    }
}
