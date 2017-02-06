package com.bobberto1995;

import processing.core.*;

public class ProgressiveRefine extends PApplet
{
	int step;
	PImage p;
	public void setup()
	{
		size(1024,768);
		colorMode(HSB);
		p = createImage(1024,768,HSB);
		p.loadPixels();
		step = 8;
		
	}
	public void draw()
	{
		if(step > 0)
		{
			image(drawMandel(-2,-1.5,2,1.5,1024,p,step),0,0);
			
			println(step);
		}
		step /= 2;
	}
	
	public PImage drawMandel(double minX, double minY, double maxX, double maxY, int maxiter, PImage p, int step)
	{
		for (int i = 0; i < p.width; i += step) {
			for (int j = 0; j < p.height; j += step) {
				double ii = (double)i / (double)p.width * (maxX - minX) + minX; //map(i, 0, width, minX, maxX);
				double jj = (double)j / (double)p.height * (maxY - minY) + minY;
				double a = ii;
				double b = jj;

				int iter = 0;

				while (iter < maxiter && a * a + b * b < 4) {
					double temp = a * a - b * b + ii;
					b = 2 * a * b + jj;
					a = temp;
					iter++;
				}
				int theColor;
				if(iter == maxiter)
				{
					 theColor = color(0);
				}
				else
				{
					theColor = color(iter % 255, 255, 255);
				}
				for(int k = 0; k < step; k++)
				{
					for(int l = 0; l < step; l++)
					{
						p.pixels[p.width * (j + l) + (i + k)] = theColor;
					}
				}
			}
		}
		p.updatePixels();
		return p;
	}
}
