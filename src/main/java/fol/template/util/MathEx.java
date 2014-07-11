package fol.template.util;

import java.util.Random;

public class MathEx {

	private static Random random = new Random();

	public static Integer toInt(final Number number) {
		return number.intValue();
	}

	public static Long toLong(final Number number) {
		return number.longValue();
	}

	public static Double toDouble(final Number number) {
		return number.doubleValue();
	}

	public static Float toFloat(final Number number) {
		return number.floatValue();
	}

	public static Byte toByte(final Number number) {
		return number.byteValue();
	}

	public static int toPow(int a, int b) {
		return (int) Math.pow((double) a, (double) b);
	}

	public static int toSqrt(int a) {
		return (int) Math.sqrt(a);
	}

	public static int rand(final int number) {
		return MathEx.random.nextInt(number);
	}

	public static int rand(final int from, final int to) {
		if (to < from)
			throw new IllegalArgumentException("to : " + to + " < " + "from : " + from);
		return MathEx.rand((to - from) + 1) + from;
	}

}
