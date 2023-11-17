package TriangleGeometry;

// 游戏中奖励机制的奖金计算
// 根据三人的位置所在，随机生成奖金值
// 计算三人坐标组成的三角形的外切圆和内切圆，作为奖金值的可选值
public class TriangleGeometry {
	public static void main(String[] args) {
		// 6个参数范围均为[0,100]
		double a = Double.parseDouble(args[0]);
		double b = Double.parseDouble(args[1]);
		double c = Double.parseDouble(args[2]);
		double d = Double.parseDouble(args[3]);
		double e = Double.parseDouble(args[4]);
		double f = Double.parseDouble(args[5]);

		// 示例输入：三个平面直角系坐标
		double[] pointA = { a, b, 0 };
		double[] pointB = { c, d, 0 };
		double[] pointC = { e, f, 0 };

		// 进行检查，如果无法构成三角形
		if (arePointsCollinear(pointA, pointB, pointC)) {
			System.out.println(0.0);
		} else {
			double probability = 0.35;
			double random = Math.random(); // 生成一个0到1之间的随机数
			if (random < probability) {
				// 计算内切圆的面积
				double inCircleArea = calculateInscribedCircleArea(pointA, pointB, pointC);
				inCircleArea = scale(inCircleArea); // 缩放
				inCircleArea = (double) (Math.round(inCircleArea * 100) / 100);
				System.out.println(inCircleArea);
			} else {
				// 计算外接圆的面积
				double circumscribedCircleArea = calculateCircumscribedCircleArea(pointA, pointB, pointC);
				circumscribedCircleArea = scale(circumscribedCircleArea); // 缩放
				circumscribedCircleArea = (double) (Math.round(circumscribedCircleArea * 100) / 100);
				System.out.println(circumscribedCircleArea);
			}
		}
	}

	// 判断三个点是否共线
	static boolean arePointsCollinear(double[] p1, double[] p2, double[] p3) {
		return (p2[1] - p1[1]) * (p3[2] - p1[2]) == (p3[1] - p1[1]) * (p2[2] - p1[2])
				&& (p2[2] - p1[2]) * (p3[0] - p1[0]) == (p3[2] - p1[2]) * (p2[0] - p1[0])
				&& (p2[0] - p1[0]) * (p3[1] - p1[1]) == (p3[0] - p1[0]) * (p2[1] - p1[1]);
	}

	// 计算三角形的面积（海伦公式）
	static double calculateTriangleArea(double[] a, double[] b, double[] c) {
		double sideAB = calculateDistance(a, b);
		double sideBC = calculateDistance(b, c);
		double sideCA = calculateDistance(c, a);

		double s = (sideAB + sideBC + sideCA) / 2;
		return Math.sqrt(s * (s - sideAB) * (s - sideBC) * (s - sideCA));
	}

	// 计算两点之间的距离
	static double calculateDistance(double[] p1, double[] p2) {
		return Math.sqrt(Math.pow(p2[0] - p1[0], 2) + Math.pow(p2[1] - p1[1], 2) + Math.pow(p2[2] - p1[2], 2));
	}

	// 计算内切圆的半径
	static double calculateInscribedCircleRadius(double a, double b, double c) {
		return calculateTriangleAreaFromSides(a, b, c) / (calculateTrianglePerimeter(a, b, c) / 2);
	}

	// 计算内切圆的面积
	static double calculateInscribedCircleArea(double[] a, double[] b, double[] c) {
		double sideA = calculateDistance(b, c);
		double sideB = calculateDistance(a, c);
		double sideC = calculateDistance(a, b);

		double radius = calculateInscribedCircleRadius(sideA, sideB, sideC);
		return Math.PI * Math.pow(radius, 2);
	}

	// 计算外接圆的半径
	static double calculateCircumscribedCircleRadius(double a, double b, double c) {
		return (a * b * c) / (4 * calculateTriangleAreaFromSides(a, b, c));
	}

	// 计算外接圆的面积
	static double calculateCircumscribedCircleArea(double[] a, double[] b, double[] c) {
		double sideA = calculateDistance(b, c);
		double sideB = calculateDistance(a, c);
		double sideC = calculateDistance(a, b);

		double radius = calculateCircumscribedCircleRadius(sideA, sideB, sideC);
		return Math.PI * Math.pow(radius, 2);
	}

	// 计算三角形的周长
	static double calculateTrianglePerimeter(double a, double b, double c) {
		return a + b + c;
	}

	// 根据三边长度计算三角形的面积（海伦公式）
	static double calculateTriangleAreaFromSides(double a, double b, double c) {
		double s = (a + b + c) / 2;
		return Math.sqrt(s * (s - a) * (s - b) * (s - c));
	}

	// 当数字绝对值太大时，不断除以2，直到绝对值<=10000
	public static double scale(double value) {
		while (value > 10000 || value < -10000) {
			value /= 2;
		}
		return value;
	}
}
