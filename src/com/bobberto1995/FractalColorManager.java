package com.bobberto1995;

import java.util.Collections;

import processing.core.PImage;

public class FractalColorManager
{
	public static PImage itersMod(int[] iters, int xRes, int yRes, int maxIters)
	{
		PImage result = new PImage(xRes, yRes);
		result.loadPixels();
		
		for(int i = 0; i < result.pixels.length; i++)
		{
			if(iters[i] == maxIters)
			{
				result.pixels[i] = FractalUtils.color(0);
			}
			else
			{
				result.pixels[i] = FractalUtils.color(iters[i] % 255);
			}
		}
		return result;
	}
	
	public static PImage itersLinear(int[] iters, int xRes, int yRes, int maxIters)
	{
		int lowBound = maxIters;
		int highBound = 0;
		for(int i = 0; i < iters.length; i++)
		{
			if(iters[i] > highBound)
			{
				highBound = iters[i];
			}
			if(iters[i] < lowBound)
			{
				lowBound = iters[i];
			}
		}
		PImage result = new PImage(xRes, yRes);
		result.loadPixels();
		
		for(int i = 0; i < result.pixels.length; i++)
		{
			int greyVal = (int) FractalUtils.map(iters[i], lowBound, highBound, 0, 255);
			if(iters[i] == maxIters)
			{
				result.pixels[i] = FractalUtils.color(0);
			}
			else
			{
				result.pixels[i] = FractalUtils.color(greyVal);
			}
		}
		return result;
	}
}
