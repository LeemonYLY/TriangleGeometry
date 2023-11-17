package TriangleGeometry;

/*
 * As the core implementation of the reward mechanism module in the mini game, 
 * this program randomly generates the number of reward coins 
 * according to the coordinate position of the character and calculated by random different algorithms.
 */
// Depending on the position of the three people, the bonus value is randomly generated
// Calculate the inscribed and inscribed circles of the triangle composed of three coordinates as optional values for the bonus value
public class TriangleGeometry {
	public static void main(String[] args) {
		// All 6 parameters range [0,100]
		double a = Double.parseDouble(args[0]);
		double b = Double.parseDouble(args[1]);
		double c = Double.parseDouble(args[2]);
		double d = Double.parseDouble(args[3]);
		double e = Double.parseDouble(args[4]);
		double f = Double.parseDouble(args[5]);

		// Three planar Cartesian coordinates
		double[] pointA = { a, b, 0 };
		double[] pointB = { c, d, 0 };
		double[] pointC = { e, f, 0 };

		// Check if you can't form a triangle
		if (arePointsCollinear(pointA, pointB, pointC)) {
			System.out.println(0.0);
		} else {
			double probability = 0.35;
			double random = Math.random(); // Generate a random number between 0 and 1
			if (random < probability) {
				// Calculate the area of the inscribed circle
				double inCircleArea = calculateInscribedCircleArea(pointA, pointB, pointC);
				inCircleArea = scale(inCircleArea); // scale
				inCircleArea = (double) (Math.round(inCircleArea * 100) / 100);
				System.out.println(inCircleArea);
			} else {
				// Calculate the area of the circumscribed circle
				double circumscribedCircleArea = calculateCircumscribedCircleArea(pointA, pointB, pointC);
				circumscribedCircleArea = scale(circumscribedCircleArea); // scale
				circumscribedCircleArea = (double) (Math.round(circumscribedCircleArea * 100) / 100);
				System.out.println(circumscribedCircleArea);
			}
		}
	}

	// Determine whether three points are collinear
	static boolean arePointsCollinear(double[] p1, double[] p2, double[] p3) {
		return (p2[1] - p1[1]) * (p3[2] - p1[2]) == (p3[1] - p1[1]) * (p2[2] - p1[2])
				&& (p2[2] - p1[2]) * (p3[0] - p1[0]) == (p3[2] - p1[2]) * (p2[0] - p1[0])
				&& (p2[0] - p1[0]) * (p3[1] - p1[1]) == (p3[0] - p1[0]) * (p2[1] - p1[1]);
	}

	// Calculate the area of a triangle (Helen's formula)
	static double calculateTriangleArea(double[] a, double[] b, double[] c) {
		double sideAB = calculateDistance(a, b);
		double sideBC = calculateDistance(b, c);
		double sideCA = calculateDistance(c, a);

		double s = (sideAB + sideBC + sideCA) / 2;
		return Math.sqrt(s * (s - sideAB) * (s - sideBC) * (s - sideCA));
	}

	// Calculate the distance between two points
	static double calculateDistance(double[] p1, double[] p2) {
		return Math.sqrt(Math.pow(p2[0] - p1[0], 2) + Math.pow(p2[1] - p1[1], 2) + Math.pow(p2[2] - p1[2], 2));
	}

	// Calculate the radius of the inscribed circle
	static double calculateInscribedCircleRadius(double a, double b, double c) {
		return calculateTriangleAreaFromSides(a, b, c) / (calculateTrianglePerimeter(a, b, c) / 2);
	}

	// Calculate the area of the inscribed circle
	static double calculateInscribedCircleArea(double[] a, double[] b, double[] c) {
		double sideA = calculateDistance(b, c);
		double sideB = calculateDistance(a, c);
		double sideC = calculateDistance(a, b);

		double radius = calculateInscribedCircleRadius(sideA, sideB, sideC);
		return Math.PI * Math.pow(radius, 2);
	}

	// Calculate the radius of the circumscribed circle
	static double calculateCircumscribedCircleRadius(double a, double b, double c) {
		return (a * b * c) / (4 * calculateTriangleAreaFromSides(a, b, c));
	}

	// Calculate the area of the circumscribed circle
	static double calculateCircumscribedCircleArea(double[] a, double[] b, double[] c) {
		double sideA = calculateDistance(b, c);
		double sideB = calculateDistance(a, c);
		double sideC = calculateDistance(a, b);

		double radius = calculateCircumscribedCircleRadius(sideA, sideB, sideC);
		return Math.PI * Math.pow(radius, 2);
	}

	// Calculate the perimeter of the triangle
	static double calculateTrianglePerimeter(double a, double b, double c) {
		return a + b + c;
	}

	// Calculate the area of a triangle from the length of the three sides (Helen's
	// formula)
	static double calculateTriangleAreaFromSides(double a, double b, double c) {
		double s = (a + b + c) / 2;
		return Math.sqrt(s * (s - a) * (s - b) * (s - c));
	}

	// When the absolute value of the number is too large, keep dividing by 2 until
	// the absolute value <=10000
	public static double scale(double value) {
		while (value > 10000 || value < -10000) {
			value /= 2;
		}
		return value;
	}
}
