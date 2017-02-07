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
	}
}
