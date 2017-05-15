package com.bobberto1995;

public class FractalRenderer
{
	public static int[] renderMandelbrotIters(double minX, double maxX, double minY, double maxY, int xRes, int yRes, int maxIters)
	{
		int[] result = new int[xRes * yRes];
		
		double real, imag, xx, yy, temp;
		int iter;
		
		for(int y = 0; y < yRes; y++)
		{
			for(int x = 0; x < xRes; x++)
			{
				iter = 0;
				real = FractalUtils.map(x,0,xRes,minX, maxX);
				imag = FractalUtils.map(y,0,yRes,minY, maxY);
				xx = 0;
				yy = 0;
				while(iter < maxIters && xx * xx + yy * yy < 4)
				{
					temp = xx * xx - yy * yy + real;
					yy = 2 * xx * yy + imag;
					xx = temp;
					iter++;
				}
				result[x + y * xRes] = iter;
			}
		}
		return result;
	}
	public static int renderMandelbrotPixelIters(double real, double imag, int maxIters)
	{
		int iter = 0;
		double xx = 0;
		double yy = 0;
		double temp;
		while(iter < maxIters && xx * xx + yy * yy < 4)
		{
			temp = xx * xx - yy * yy + real;
			yy = 2 * xx * yy + imag;
			xx = temp;
			iter++;
		}
		return iter;
	}
	public static int[] renderJuliaIters(double seedReal, double seedImag, double minX, double maxX, double minY, double maxY, int xRes, int yRes, int maxIters)
	{
		int[] result = new int[xRes * yRes];
		double xx, yy, temp;
		int iter;
		
		for(int y = 0; y < yRes; y++)
		{
			for(int x = 0; x < xRes; x++)
			{
				iter = 0;
				xx = 0;
				yy = 0;
				while(iter < maxIters && xx * xx + yy * yy < 4)
				{
					temp = xx * xx - yy * yy + seedReal;
					yy = 2 * xx * yy + seedImag;
					xx = temp;
					iter++;
				}
				result[x + y * xRes] = iter;
			}
		}
		return result;
	}
}
