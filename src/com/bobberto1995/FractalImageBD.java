package com.bobberto1995;

import java.math.BigDecimal;

import processing.core.PImage;

public class FractalImageBD
{
	
	private int[] iterMap;
	private BigDecimal minX, maxX, minY, maxY;
	private int xRes, yRes, maxIters;
	
	public FractalImageBD(int xRes, int yRes)
	{
		this.iterMap = new int[xRes * yRes];
		this.xRes = xRes;
		this.yRes = yRes;
		this.reset();
	}
	
	public void render()
	{
		this.iterMap = FractalRenderer.renderMandelbrotItersBD(this.minX, this.maxX, this.minY, this.maxY, this.xRes, this.yRes, this.maxIters);
	}
	
	public void reset()
	{
		this.minX = new BigDecimal(-2.5);
		this.maxX = new BigDecimal(1.5);
		this.minY = new BigDecimal(-1.5);
		this.maxY = new BigDecimal(1.5);
		this.maxIters = 16;
	}
	
	public void zoomIn(int xPos, int yPos)
	{
		BigDecimal realPos = FractalUtils.mapBD(new BigDecimal(xPos), BigDecimal.ZERO, new BigDecimal(xRes), minX, maxX);
		BigDecimal imagPos = FractalUtils.mapBD(new BigDecimal(yPos), BigDecimal.ZERO, new BigDecimal(yRes), minY, maxY);
		BigDecimal xRange = maxX.subtract(minX);
		BigDecimal yRange = maxY.subtract(minY);
		BigDecimal four = new BigDecimal(4);
		this.minX = realPos.subtract(xRange.divide(four));
		this.maxX = realPos.add(xRange.divide(four));
		this.minY = imagPos.subtract(yRange.divide(four));
		this.maxY = imagPos.add(yRange.divide(four));
		
		int[] newIterMap = new int[xRes * yRes];
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
						newIterMap[y * this.xRes + x] = this.iterMap[oldY * this.xRes + oldX];
					}
					else
					{
						BigDecimal real = FractalUtils.mapBD(new BigDecimal(x), BigDecimal.ZERO, new BigDecimal(xRes), minX, maxX);
						BigDecimal imag = FractalUtils.mapBD(new BigDecimal(y), BigDecimal.ZERO, new BigDecimal(yRes), minY, maxY);
						newIterMap[y * this.xRes + x] = FractalRenderer.renderMandelbrotPixelItersBD(real, imag, this.maxIters);
					}
				}
				else
				{
					BigDecimal real = FractalUtils.mapBD(new BigDecimal(x), BigDecimal.ZERO, new BigDecimal(xRes), minX, maxX);
					BigDecimal imag = FractalUtils.mapBD(new BigDecimal(y), BigDecimal.ZERO, new BigDecimal(yRes), minY, maxY);
					newIterMap[y * this.xRes + x] = FractalRenderer.renderMandelbrotPixelItersBD(real, imag, this.maxIters);
				}
			}
		}
		this.iterMap = newIterMap;
	}
	
	public void zoomOut(int xPos,int yPos)
	{
		BigDecimal realPos = FractalUtils.mapBD(new BigDecimal(xPos), BigDecimal.ZERO, new BigDecimal(xRes), minX, maxX);
		BigDecimal imagPos = FractalUtils.mapBD(new BigDecimal(yPos), BigDecimal.ZERO, new BigDecimal(yRes), minY, maxY);
		BigDecimal xRange = maxX.subtract(minX);
		BigDecimal yRange = maxY.subtract(minY);
		this.minX = realPos.subtract(xRange);
		this.maxX = realPos.add(xRange);
		this.minY = imagPos.subtract(yRange);
		this.maxY = imagPos.add(yRange);
		
		int[] newIterMap = new int[xRes * yRes];
		for(int y = 0; y < this.yRes; y++)
		{
			for(int x = 0; x < this.xRes; x++)
			{
				int oldX = (x + xPos + xRes / 4) * 2;
				int oldY = (y + yPos + yRes / 4) * 2;
				if(oldX >= 0 && oldX < xRes && oldY >= 0 && oldY < yRes)
				{
					newIterMap[y * this.xRes + x] = this.iterMap[oldY * this.xRes + oldX];
				}
				else
				{
					BigDecimal real = FractalUtils.mapBD(new BigDecimal(x), BigDecimal.ZERO, new BigDecimal(xRes), minX, maxX);
					BigDecimal imag = FractalUtils.mapBD(new BigDecimal(y), BigDecimal.ZERO, new BigDecimal(yRes), minY, maxY);
					newIterMap[y * this.xRes + x] = FractalRenderer.renderMandelbrotPixelItersBD(real, imag, this.maxIters);
				}
			}
		}
		this.iterMap = newIterMap;
	}
	
	public void panLeft()
	{
		int[] newIterMap = new int[xRes * yRes];
		BigDecimal xRange = maxX.subtract(minX);
		minX = minX.subtract(xRange.divide(new BigDecimal(8)));
		maxX = maxX.subtract(xRange.divide(new BigDecimal(8)));
		for(int y = 0; y < this.yRes; y++)
		{
			for(int x = 0; x < xRes; x++)
			{
				if(x > this.xRes / 8)
				{
					newIterMap[x + y * this.xRes] = this.iterMap[(x - xRes / 8) + y * xRes];
				}
				else
				{
					BigDecimal real = FractalUtils.mapBD(new BigDecimal(x), BigDecimal.ZERO, new BigDecimal(xRes), minX, maxX);
					BigDecimal imag = FractalUtils.mapBD(new BigDecimal(y), BigDecimal.ZERO, new BigDecimal(yRes), minY, maxY);
					newIterMap[x + y * this.xRes] = FractalRenderer.renderMandelbrotPixelItersBD(real, imag, this.maxIters);
				}
			}
		}
		this.iterMap = newIterMap;
	}
	
	public void panRight()
	{
		int[] newIterMap = new int[xRes * yRes];
		BigDecimal xRange = maxX.subtract(minX);
		minX = minX.add(xRange.divide(new BigDecimal(8)));
		maxX = maxX.add(xRange.divide(new BigDecimal(8)));
		for(int y = 0; y < this.yRes; y++)
		{
			for(int x = 0; x < xRes; x++)
			{
				if(x < xRes - this.xRes / 8)
				{
					newIterMap[x + y * this.xRes] = iterMap[(x + xRes / 8) + y * xRes];
				}
				else
				{
					BigDecimal real = FractalUtils.mapBD(new BigDecimal(x), BigDecimal.ZERO, new BigDecimal(xRes), minX, maxX);
					BigDecimal imag = FractalUtils.mapBD(new BigDecimal(y), BigDecimal.ZERO, new BigDecimal(yRes), minY, maxY);
					newIterMap[x + y * this.xRes] = FractalRenderer.renderMandelbrotPixelItersBD(real, imag, this.maxIters);
				}
			}
		}
		this.iterMap = newIterMap;
	}
	
	public void panUp()
	{
		int[] newIterMap = new int[xRes * yRes];
		BigDecimal yRange = maxY.subtract(minY);
		minY = minY.subtract(yRange.divide(new BigDecimal(8)));
		maxY = maxY.subtract(yRange.divide(new BigDecimal(8)));
		for(int y = 0; y < this.yRes; y++)
		{
			for(int x = 0; x < xRes; x++)
			{
				if(y > this.yRes / 8)
				{
					newIterMap[x + y * this.xRes] = iterMap[x + (y - yRes / 8) * xRes];
				}
				else
				{
					BigDecimal real = FractalUtils.mapBD(new BigDecimal(x), BigDecimal.ZERO, new BigDecimal(xRes), minX, maxX);
					BigDecimal imag = FractalUtils.mapBD(new BigDecimal(y), BigDecimal.ZERO, new BigDecimal(yRes), minY, maxY);
					newIterMap[x + y * this.xRes] = FractalRenderer.renderMandelbrotPixelItersBD(real, imag, this.maxIters);
				}
			}
		}
		this.iterMap = newIterMap;
	}
	
	public void panDown()
	{
		int[] newIterMap = new int[xRes * yRes];
		BigDecimal yRange = maxY.subtract(minY);
		minY = minY.add(yRange.divide(new BigDecimal(8)));
		maxY = maxY.add(yRange.divide(new BigDecimal(8)));
		for(int y = 0; y < this.yRes; y++)
		{
			for(int x = 0; x < xRes; x++)
			{
				if(y < this.yRes - this.yRes / 8)
				{
					newIterMap[x + y * this.xRes] = this.iterMap[x + (y + yRes / 8) * xRes];
				}
				else
				{
					BigDecimal real = FractalUtils.mapBD(new BigDecimal(x), BigDecimal.ZERO, new BigDecimal(xRes), minX, maxX);
					BigDecimal imag = FractalUtils.mapBD(new BigDecimal(y), BigDecimal.ZERO, new BigDecimal(yRes), minY, maxY);
					newIterMap[x + y * this.xRes] = FractalRenderer.renderMandelbrotPixelItersBD(real, imag, this.maxIters);
				}
			}
		}
		this.iterMap = newIterMap;
	}
	
	public int getMaxIters()
	{
		return this.maxIters;
	}
	
	public void setMaxiters(int newMaxIters)
	{
		for(int y = 0; y < this.yRes; y++)
		{
			for(int x = 0; x < xRes; x++)
			{
				if(newMaxIters > this.maxIters)
				{
					if(this.iterMap[x + y * this.xRes] == this.maxIters)
					{
						BigDecimal real = FractalUtils.mapBD(new BigDecimal(x), BigDecimal.ZERO, new BigDecimal(xRes), minX, maxX);
						BigDecimal imag = FractalUtils.mapBD(new BigDecimal(y), BigDecimal.ZERO, new BigDecimal(yRes), minY, maxY);
						this.iterMap[x + y * this.xRes] = FractalRenderer.renderMandelbrotPixelItersBD(real, imag, this.maxIters);
					}
				}
				else
				{
					if(this.iterMap[x + y * this.xRes] > newMaxIters)
					{
						this.iterMap[x + y * this.xRes] = newMaxIters;
					}
				}
			}
		}
		this.maxIters = newMaxIters;
	}
	public int[] getIterMap()
	{
		return this.iterMap;
	}
}
