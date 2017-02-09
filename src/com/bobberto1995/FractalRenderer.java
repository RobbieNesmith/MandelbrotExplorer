package com.bobberto1995;
import java.math.BigDecimal;

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
				if(iter == maxIters)
				{
					result.pixels[x + y * xRes] = 255 << 24;
				}
				else
				{
					result.pixels[x + y * xRes] = FractalUtils.color(iter % 255, iter % 255, iter % 255);
				}
			}
		}
		result.updatePixels();
		return result;
	}
	
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
	
	public static int[] renderMandelbrotItersBD(BigDecimal minX, BigDecimal maxX, BigDecimal minY, BigDecimal maxY, int xRes, int yRes, int maxIters)
	{
		int[] result = new int[xRes * yRes];
		
		BigDecimal real, imag, xx, yy, temp;
		int iter;
		
		for(int y = 0; y < yRes; y++)
		{
			for(int x = 0; x < xRes; x++)
			{
				iter = 0;
				real = FractalUtils.mapBD(new BigDecimal(x),BigDecimal.ZERO,new BigDecimal(xRes),minX, maxX);
				imag = FractalUtils.mapBD(new BigDecimal(y),BigDecimal.ZERO,new BigDecimal(yRes),minY, maxY);
				xx = BigDecimal.ZERO;
				yy = BigDecimal.ZERO;
				while(iter < maxIters && (xx.pow(2).add(yy.pow(2)).compareTo(new BigDecimal(4)) < 0))
				{
					temp = xx.pow(2).subtract(yy.pow(2)).add(real);
					yy = new BigDecimal(2).multiply(xx).multiply(yy).add(imag);
					xx = temp;
					iter++;
				}
				result[x + y * xRes] = iter;
			}
		}
		return result;
	}
	
	public static int renderMandelbrotPixel(double real, double imag, int maxIters)
	{
		int result;
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
		if(iter == maxIters)
		{
			result = 255 << 24;
		}
		else
		{
			result = FractalUtils.color(iter % 255, iter % 255, iter % 255);
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
	
	public static int renderMandelbrotPixelItersBD(BigDecimal real, BigDecimal imag, int maxIters)
	{
		int iter = 0;
		BigDecimal xx = BigDecimal.ZERO;
		BigDecimal yy = BigDecimal.ZERO;
		BigDecimal temp;
		while(iter < maxIters && xx.pow(2).add(yy.pow(2)).compareTo(new BigDecimal(4)) < 0)
		{
			temp = xx.pow(2).subtract(yy.pow(2)).add(real);
			yy = new BigDecimal(2).multiply(xx).multiply(yy).add(imag);
			xx = temp;
			iter++;
		}
		return iter;
	}
}
