package com.bobberto1995;

import processing.core.PConstants;
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
	
	public void zoomIn(int xPos, int yPos)
	{
		double realPos = FractalUtils.map(xPos, 0, xRes, minX, maxX);
		double imagPos = FractalUtils.map(yPos, 0, yRes, minY, maxY);
		double xRange = maxX - minX;
		double yRange = maxY - minY;
		this.minX = realPos - xRange / 4;
		this.maxX = realPos + xRange / 4;
		this.minY = imagPos - yRange / 4;
		this.maxY = imagPos + yRange / 4;
		
		PImage newImg = new PImage(this.xRes,this.yRes,PConstants.ARGB);
		newImg.loadPixels();
		for(int y = 0; y < this.yRes; y++)
		{
			for(int x = 0; x < this.xRes; x++)
			{
				if(x % 2 == 0 && y % 2 == 0)
				{
					int oldX = x / 2 + xPos - xRes / 4;
					int oldY = y / 2 + yPos - yRes / 4;
					if(oldX >= 0 && oldX < xRes && oldY >= 0 && oldY < yRes)
					{
						newImg.pixels[y * this.xRes + x] = theImage.pixels[oldY * this.xRes + oldX];
					}
					else
					{
						double real = FractalUtils.map(x, 0, xRes, minX, maxX);
						double imag = FractalUtils.map(y, 0, yRes, minY, maxY);
						newImg.pixels[y * this.xRes + x] = FractalRenderer.renderMandelbrotPixel(real,imag,this.maxIters);
					}
				}
				else
				{
					double real = FractalUtils.map(x, 0, xRes, minX, maxX);
					double imag = FractalUtils.map(y, 0, yRes, minY, maxY);
					newImg.pixels[y * this.xRes + x] = FractalRenderer.renderMandelbrotPixel(real,imag,this.maxIters);
				}
			}
		}
		newImg.updatePixels();
		this.theImage = newImg;
	}
	
	public void zoomOut(int xPos,int yPos)
	{
		double realPos = FractalUtils.map(xPos, 0, xRes, minX, maxX);
		double imagPos = FractalUtils.map(yPos, 0, yRes, minY, maxY);
		double xRange = maxX - minX;
		double yRange = maxY - minY;
		this.minX = realPos - xRange;
		this.maxX = realPos + xRange;
		this.minY = imagPos - yRange;
		this.maxY = imagPos + yRange;
		
		PImage newImg = new PImage(this.xRes,this.yRes,PConstants.ARGB);
		newImg.loadPixels();
		for(int y = 0; y < this.yRes; y++)
		{
			for(int x = 0; x < this.xRes; x++)
			{
				int oldX = (x + xPos + xRes / 4) * 2;
				int oldY = (y + yPos + yRes / 4) * 2;
				if(oldX >= 0 && oldX < xRes && oldY >= 0 && oldY < yRes)
				{
					newImg.pixels[y * this.xRes + x] = theImage.pixels[oldY * this.xRes + oldX];
				}
				else
				{
					double real = FractalUtils.map(x, 0, xRes, minX, maxX);
					double imag = FractalUtils.map(y, 0, yRes, minY, maxY);
					newImg.pixels[y * this.xRes + x] = FractalRenderer.renderMandelbrotPixel(real,imag,this.maxIters);
					//newImg.pixels[y * this.xRes + x] = FractalUtils.color(255, 0, 0);
				}
			}
		}
		newImg.updatePixels();
		this.theImage = newImg;
	}
	
	public void panLeft()
	{
		PImage newImg = new PImage(xRes,yRes,PConstants.ARGB);
		newImg.loadPixels();
		double xRange = maxX - minX;
		minX -= xRange / 8;
		maxX -= xRange / 8;
		for(int y = 0; y < this.yRes; y++)
		{
			for(int x = 0; x < xRes; x++)
			{
				if(x > this.xRes / 8)
				{
					newImg.pixels[x + y * this.xRes] = theImage.pixels[(x - xRes / 8) + y * xRes];
				}
				else
				{
					double real = FractalUtils.map(x, 0, xRes, minX, maxX);
					double imag = FractalUtils.map(y, 0, yRes, minY, maxY);
					newImg.pixels[x + y * this.xRes] = FractalRenderer.renderMandelbrotPixel(real, imag, this.maxIters);
				}
			}
		}
		newImg.updatePixels();
		theImage = newImg;
	}
	
	public void panRight()
	{
		PImage newImg = new PImage(xRes,yRes,PConstants.ARGB);
		newImg.loadPixels();
		double xRange = maxX - minX;
		minX += xRange / 8;
		maxX += xRange / 8;
		for(int y = 0; y < this.yRes; y++)
		{
			for(int x = 0; x < xRes; x++)
			{
				if(x < xRes - this.xRes / 8)
				{
					newImg.pixels[x + y * this.xRes] = theImage.pixels[(x + xRes / 8) + y * xRes];
				}
				else
				{
					double real = FractalUtils.map(x, 0, xRes, minX, maxX);
					double imag = FractalUtils.map(y, 0, yRes, minY, maxY);
					newImg.pixels[x + y * this.xRes] = FractalRenderer.renderMandelbrotPixel(real, imag, this.maxIters);
				}
			}
		}
		newImg.updatePixels();
		theImage = newImg;
	}
	
	public void panUp()
	{
		PImage newImg = new PImage(xRes,yRes,PConstants.ARGB);
		newImg.loadPixels();
		double yRange = maxY - minY;
		minY -= yRange / 8;
		maxY -= yRange / 8;
		for(int y = 0; y < this.yRes; y++)
		{
			for(int x = 0; x < xRes; x++)
			{
				if(y > this.yRes / 8)
				{
					newImg.pixels[x + y * this.xRes] = theImage.pixels[x + (y - yRes / 8) * xRes];
				}
				else
				{
					double real = FractalUtils.map(x, 0, xRes, minX, maxX);
					double imag = FractalUtils.map(y, 0, yRes, minY, maxY);
					newImg.pixels[x + y * this.xRes] = FractalRenderer.renderMandelbrotPixel(real, imag, this.maxIters);
				}
			}
		}
		newImg.updatePixels();
		theImage = newImg;
	}
	
	public void panDown()
	{
		PImage newImg = new PImage(xRes,yRes,PConstants.ARGB);
		newImg.loadPixels();
		double yRange = maxY - minY;
		minY += yRange / 8;
		maxY += yRange / 8;
		for(int y = 0; y < this.yRes; y++)
		{
			for(int x = 0; x < xRes; x++)
			{
				if(y < this.yRes - this.yRes / 8)
				{
					newImg.pixels[x + y * this.xRes] = theImage.pixels[x + (y + yRes / 8) * xRes];
				}
				else
				{
					double real = FractalUtils.map(x, 0, xRes, minX, maxX);
					double imag = FractalUtils.map(y, 0, yRes, minY, maxY);
					newImg.pixels[x + y * this.xRes] = FractalRenderer.renderMandelbrotPixel(real, imag, this.maxIters);
				}
			}
		}
		newImg.updatePixels();
		theImage = newImg;
	}
	
	public PImage getImage()
	{
		return this.theImage;
	}
	
	private double calcPixelWidth() // assuming that there are square pixels
	{
		return (this.maxX - this.minX) / this.xRes;
	}
}
