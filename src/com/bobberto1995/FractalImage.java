package com.bobberto1995;

public class FractalImage
{
	public static boolean MODE_MANDELBROT = false;
	public static boolean MODE_JULIA = true;
	private boolean mode;
	private int[] iterMap;
	private double minX, maxX, minY, maxY;
	private int xRes, yRes, maxIters;
	private double seedReal, seedImag;
	
	public FractalImage(int xRes, int yRes)
	{
		this.iterMap = new int[xRes * yRes];
		this.xRes = xRes;
		this.yRes = yRes;
		this.reset();
	}
	
	public void render()
	{
		if(this.mode == FractalImage.MODE_MANDELBROT)
		{
			this.iterMap = FractalRenderer.renderMandelbrotIters(this.minX, this.maxX, this.minY, this.maxY, this.xRes, this.yRes, this.maxIters);
		}
		else
		{
			this.iterMap = FractalRenderer.renderJuliaIters(seedReal,seedImag,this.minX, this.maxX, this.minY, this.maxY, this.xRes, this.yRes, this.maxIters);
		}
	}
	
	public void reset()
	{
		this.minX = -2.5;
		this.maxX = 1.5;
		this.minY = -1.5;
		this.maxY = 1.5;
		this.maxIters = 1024;
		render();
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
						double real = FractalUtils.map(x, 0, xRes, minX, maxX);
						double imag = FractalUtils.map(y, 0, yRes, minY, maxY);
						if(this.mode == FractalImage.MODE_MANDELBROT)
						{
							newIterMap[x + y * this.xRes] = FractalRenderer.renderMandelbrotPixelIters(real, imag, this.maxIters);
						}
						else
						{
							newIterMap[x + y * this.xRes] = FractalRenderer.renderJuliaPixelIters(real, imag, seedReal, seedImag, this.maxIters);
						}
					}
				}
				else
				{
					double real = FractalUtils.map(x, 0, xRes, minX, maxX);
					double imag = FractalUtils.map(y, 0, yRes, minY, maxY);
					if(this.mode == FractalImage.MODE_MANDELBROT)
					{
						newIterMap[x + y * this.xRes] = FractalRenderer.renderMandelbrotPixelIters(real, imag, this.maxIters);
					}
					else
					{
						newIterMap[x + y * this.xRes] = FractalRenderer.renderJuliaPixelIters(real, imag, seedReal, seedImag, this.maxIters);
					}
				}
			}
		}
		this.iterMap = newIterMap;
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
					double real = FractalUtils.map(x, 0, xRes, minX, maxX);
					double imag = FractalUtils.map(y, 0, yRes, minY, maxY);
					if(this.mode == FractalImage.MODE_MANDELBROT)
					{
						newIterMap[x + y * this.xRes] = FractalRenderer.renderMandelbrotPixelIters(real, imag, this.maxIters);
					}
					else
					{
						newIterMap[x + y * this.xRes] = FractalRenderer.renderJuliaPixelIters(real, imag, seedReal, seedImag, this.maxIters);
					}
				}
			}
		}
		this.iterMap = newIterMap;
	}
	
	public void panLeft()
	{
		int[] newIterMap = new int[xRes * yRes];
		double xRange = maxX - minX;
		minX -= xRange / 8;
		maxX -= xRange / 8;
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
					double real = FractalUtils.map(x, 0, xRes, minX, maxX);
					double imag = FractalUtils.map(y, 0, yRes, minY, maxY);
					if(this.mode == FractalImage.MODE_MANDELBROT)
					{
						newIterMap[x + y * this.xRes] = FractalRenderer.renderMandelbrotPixelIters(real, imag, this.maxIters);
					}
					else
					{
						newIterMap[x + y * this.xRes] = FractalRenderer.renderJuliaPixelIters(real, imag, seedReal, seedImag, this.maxIters);
					}
				}
			}
		}
		this.iterMap = newIterMap;
	}
	
	public void panRight()
	{
		int[] newIterMap = new int[xRes * yRes];
		double xRange = maxX - minX;
		minX += xRange / 8;
		maxX += xRange / 8;
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
					double real = FractalUtils.map(x, 0, xRes, minX, maxX);
					double imag = FractalUtils.map(y, 0, yRes, minY, maxY);
					if(this.mode == FractalImage.MODE_MANDELBROT)
					{
						newIterMap[x + y * this.xRes] = FractalRenderer.renderMandelbrotPixelIters(real, imag, this.maxIters);
					}
					else
					{
						newIterMap[x + y * this.xRes] = FractalRenderer.renderJuliaPixelIters(real, imag, seedReal, seedImag, this.maxIters);
					}
				}
			}
		}
		this.iterMap = newIterMap;
	}
	
	public void panUp()
	{
		int[] newIterMap = new int[xRes * yRes];
		double yRange = maxY - minY;
		minY -= yRange / 8;
		maxY -= yRange / 8;
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
					double real = FractalUtils.map(x, 0, xRes, minX, maxX);
					double imag = FractalUtils.map(y, 0, yRes, minY, maxY);
					if(this.mode == FractalImage.MODE_MANDELBROT)
					{
						newIterMap[x + y * this.xRes] = FractalRenderer.renderMandelbrotPixelIters(real, imag, this.maxIters);
					}
					else
					{
						newIterMap[x + y * this.xRes] = FractalRenderer.renderJuliaPixelIters(real, imag, seedReal, seedImag, this.maxIters);
					}
				}
			}
		}
		this.iterMap = newIterMap;
	}
	
	public void panDown()
	{
		int[] newIterMap = new int[xRes * yRes];
		double yRange = maxY - minY;
		minY += yRange / 8;
		maxY += yRange / 8;
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
					double real = FractalUtils.map(x, 0, xRes, minX, maxX);
					double imag = FractalUtils.map(y, 0, yRes, minY, maxY);
					if(this.mode == FractalImage.MODE_MANDELBROT)
					{
						newIterMap[x + y * this.xRes] = FractalRenderer.renderMandelbrotPixelIters(real, imag, this.maxIters);
					}
					else
					{
						newIterMap[x + y * this.xRes] = FractalRenderer.renderJuliaPixelIters(real, imag, seedReal, seedImag, this.maxIters);
					}
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
						double real = FractalUtils.map(x, 0, xRes, minX, maxX);
						double imag = FractalUtils.map(y, 0, yRes, minY, maxY);
						if(this.mode == FractalImage.MODE_MANDELBROT)
						{
							this.iterMap[x + y * this.xRes] = FractalRenderer.renderMandelbrotPixelIters(real, imag, newMaxIters);
						}
						else
						{
							this.iterMap[x + y * this.xRes] = FractalRenderer.renderJuliaPixelIters(real, imag, seedReal, seedImag, newMaxIters);
						}
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
	public void setJuliaSeed(double seedReal, double seedImag)
	{
		this.seedReal = seedReal;
		this.seedImag = seedImag;
	}
	public void setJuliaSeedMouse(int x, int y)
	{
		double real = FractalUtils.map(x, 0, xRes, minX, maxX);
		double imag = FractalUtils.map(y, 0, yRes, minY, maxY);
		setJuliaSeed(real,imag);
	}
	public void setMode(boolean mode)
	{
		this.mode = mode;
		render();
	}
	public void toggleMode()
	{
		this.mode = !this.mode;
		render();
	}
	public int[] getIterMap()
	{
		return this.iterMap;
	}
}
