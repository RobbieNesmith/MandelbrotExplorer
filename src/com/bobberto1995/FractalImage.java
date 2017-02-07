package com.bobberto1995;

import processing.core.PImage;

public class FractalImage
{
	
	private PImage theImage;
	private double minX, maxX, minY, maxY;
	private int xRes, yRes, maxIters;
	
	public FractalImage(int xRes, int yRes)
	{
		this.theImage = new PImage(xRes, yRes);
		this.xRes = xRes;
		this.yRes = yRes;
		this.reset();
	}
	
	public void render()
	{
		theImage = FractalRenderer.renderMandelbrot(this.minX, this.maxX, this.minY, this.maxY, this.xRes, this.yRes, this.maxIters);
	}
	
	public void reset()
	{
		this.minX = -2;
		this.maxX = 2;
		this.minY = -1.5;
		this.maxY = 1.5;
		this.maxIters = 1024;
	}
	
	public void zoomIn()
	{
		double pixelWidth = this.calcPixelWidth();
		PImage newImg = new PImage(this.xRes,this.yRes);
		newImg.loadPixels();
		for(int y = 0; y < this.yRes; y++)
		{
			for(int x = 0; x < this.xRes; x++)
			{
				if(x % 2 == 0 && y % 2 == 0)
				{
					int oldX = x / 2 + this.xRes / 4;
					int oldY = y / 2 + this.yRes / 4;
					newImg.pixels[y * this.xRes + x] = theImage.pixels[oldY * this.xRes + oldX];
				}
			}
		}
		this.theImage = newImg;
	}
	
	public void zoomOut()
	{
		
	}
	
	public void panLeft()
	{
		
	}
	
	public void panRight()
	{
		
	}
	
	public void panUp()
	{
		
	}
	
	public void panDown()
	{
		
	}
	
	public PImage getImage()
	{
		return this.theImage;
	}
	
	private double calcPixelWidth() // assuming that there are square pixels
	{
		return (this.maxX - this.minX) / xRes;
	}
}
