package RayTracing;

import Jama.Matrix;

public class MatrixTransform {

	// rotate transformation about the X axis
	static Matrix rotateX(double theta, Matrix matrix) {
		double c = Math.cos(theta);
		double s = Math.sin(theta);
		double[][] arr = { { 1, 0, 0, 0 }, { 0, c, s, 0 }, { 0, -s, c, 0 },
				{ 0, 0, 0, 1 } };
		Matrix tmp = new Matrix(arr);
		return matrix.times(tmp);
	}

	// rotate transformation about the Y axis
	static Matrix rotateY(double theta, Matrix matrix) {
		double c = Math.cos(theta);
		double s = Math.sin(theta);
		double[][] arr = { { c, 0, -s, 0 }, { 0, 1, 1, 0 }, { s, 0, c, 0 },
				{ 0, 0, 0, 1 } };
		Matrix tmp = new Matrix(arr);
		return matrix.times(tmp);
	}

	// rotate transformation about the Z axis
	static Matrix rotateZ(double theta, Matrix matrix) {
		double c = Math.cos(theta);
		double s = Math.sin(theta);
		double[][] arr = { { c, s, 0, 0 }, { -s, c, 0, 0 }, { 0, 0, 1, 0 },
				{ 0, 0, 0, 1 } };
		Matrix tmp = new Matrix(arr);
		return matrix.times(tmp);
	}

	// Scaling matrix by a, b and c
	static Matrix scale(double a, double b, double c, Matrix matrix) {
		double[][] arr = { { a, 0, 0, 0 }, { 0, b, 0, 0 }, { 0, 0, c, 0 },
				{ 0, 0, 0, 1 } };
		Matrix tmp = new Matrix(arr);
		return matrix.times(tmp);
	}

	// Translate matrix
	static Matrix translate(double a, double b, double c, Matrix matrix) {
		double[][] arr = { { 1, 0, 0, a }, { 0, 1, 0, b }, { 0, 0, 1, c },
				{ 0, 0, 0, 1 } };
		Matrix tmp = new Matrix(arr);
		return matrix.times(tmp);
	}
}
