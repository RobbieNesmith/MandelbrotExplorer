package com.bobberto1995;

import java.math.BigDecimal;

public class FractalUtils
{
	public static double map(double input, double inMin, double inMax, double outMin, double outMax)
	{
		double inRange = (inMin - inMax);
		double outRange = (outMin - outMax);
		return ((input - inMin) / inRange) * outRange + outMin;
	}
	
	public static BigDecimal mapBD(BigDecimal input, BigDecimal inMin, BigDecimal inMax, BigDecimal outMin, BigDecimal outMax)
	{
		BigDecimal inRange = (inMin.subtract(inMax));
		BigDecimal outRange = (outMin.subtract(outMax));
		return (input.subtract(inMin).divide(inRange)).multiply(outRange).add(outMin);
	}
	
	public static int color(int a, int r, int g, int b)
	{
		return (a << 24) | (r << 16) | (g << 8) | b;
	}
	
	public static int color(int r, int g, int b)
	{
		return FractalUtils.color(255, r, g, b);
	}
	
	public static int color(int grey)
	{
		return FractalUtils.color(255, grey, grey, grey);
	}
}
