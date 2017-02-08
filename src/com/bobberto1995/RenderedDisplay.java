package com.bobberto1995;

import processing.core.PApplet;
import processing.core.PImage;

public class RenderedDisplay extends PApplet
{
	FractalImage fi;
	public void setup()
	{
		size(1024,768);
		fi = new FractalImage(1024,768);
		fi.render();
		image(fi.getImage(),0,0);
	}
	public void draw()
	{
		
	}
	public void mousePressed()
	{
		fi.zoomIn(mouseX, mouseY);
		background(255);
		image(fi.getImage(),0,0);
	}
	public void keyPressed()
	{
		if(key == 'Q' || key == 'q')
		{
			fi.zoomIn(mouseX, mouseY);
			image(fi.getImage(),0,0);
		}
		else if(key == 'E' || key == 'e')
		{
			fi.zoomOut(mouseX, mouseY);
			image(fi.getImage(),0,0);
		}
		else if(key == 'A' || key == 'a')
		{
			fi.panLeft();
			image(fi.getImage(),0,0);
		}
		else if(key == 'D' || key == 'd')
		{
			fi.panRight();
			image(fi.getImage(),0,0);
		}
		else if(key =='W' || key == 'w')
		{
			fi.panUp();
			image(fi.getImage(),0,0);
		}
		else if(key == 'S' || key == 's')
		{
			fi.panDown();
			image(fi.getImage(),0,0);
		}
		else if(key == 'I' || key == 'i')
		{
			fi.setMaxiters(fi.getMaxIters() * 2);
			image(fi.getImage(),0,0);
		}
		else if(key == 'K' || key == 'k')
		{
			if(fi.getMaxIters() > 1)
			{
				fi.setMaxiters(fi.getMaxIters() / 2);
				image(fi.getImage(),0,0);
			}
		}
	}
}
