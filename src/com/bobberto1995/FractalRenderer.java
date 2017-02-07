package com.bobberto1995;
import processing.core.PConstants;
import processing.core.PImage;

public class FractalRenderer
{
	public static PImage renderMandelbrot(double minX, double maxX, double minY, double maxY, int xRes, int yRes, int maxIters)
	{
		PImage result = new PImage(xRes,yRes,PConstants.RGB);
		result.loadPixels();
		
		double real, imag, xx, yy, temp;
		int iter;
		
		for(int y = 0; y < yRes; y++)
		{
			for(int x = 0; x < xRes; x++)
			{
				iter = 0;
				real = map(x,0,xRes,minX, maxX);
				imag = map(y,0,yRes,minY, maxY);
				xx = 0;
				yy = 0;
				while(iter < maxIters && xx * xx + yy * yy < 4)
				{
					temp = xx * xx - yy * yy + real;
					yy = 2 * xx * yy + imag;
					xx = temp;
					iter++;
				}
				if(iter == maxIters)
				{
					result.pixels[x + y * xRes] = 0;
				}
				else
				{
					result.pixels[x + y * xRes] = ((iter % 255) << 16) + ((iter % 255) << 8) + (iter % 255);
				}
			}
		}
		result.updatePixels();
		return result;
	}
	
	public static double map(double input, double inMin, double inMax, double outMin, double outMax)
	{
		double inRange = (inMin - inMax);
		double outRange = (outMin - outMax);
		return ((input - inMin) / inRange) * outRange + outMin;
	}
}
