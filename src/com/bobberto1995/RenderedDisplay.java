package com.bobberto1995;

import processing.core.PApplet;
import processing.core.PImage;

public class RenderedDisplay extends PApplet
{
	FractalImage fi;
	PImage gradient;
	boolean updated;
	public void setup()
	{
		size(1024,768);
		fi = new FractalImage(1024,768);
		fi.render();
		updated = true;
		gradient = loadImage(sketchPath("img/Gradient3.png"));
	}
	public void draw()
	{
		if(updated)
		{
			image(FractalColorManager.itersModGradient(fi.getIterMap(),1024, 768, fi.getMaxIters(), gradient),0,0);
			updated = false;
		}
	}
	public void mousePressed()
	{
		fi.zoomIn(mouseX, mouseY);
		updated = true;
	}
	public void keyPressed()
	{
		if(key == 'Q' || key == 'q')
		{
			fi.zoomIn(mouseX, mouseY);
			updated = true;
		}
		else if(key == 'E' || key == 'e')
		{
			fi.zoomOut(mouseX, mouseY);
			updated = true;
		}
		else if(key == 'A' || key == 'a')
		{
			fi.panLeft();
			updated = true;
		}
		else if(key == 'D' || key == 'd')
		{
			fi.panRight();
			updated = true;
		}
		else if(key =='W' || key == 'w')
		{
			fi.panUp();
			updated = true;
		}
		else if(key == 'S' || key == 's')
		{
			fi.panDown();
			updated = true;
		}
		else if(key == 'I' || key == 'i')
		{
			fi.setMaxiters(fi.getMaxIters() * 2);
			updated = true;
		}
		else if(key == 'K' || key == 'k')
		{
			if(fi.getMaxIters() > 1)
			{
				fi.setMaxiters(fi.getMaxIters() / 2);
				updated = true;
			}
		}
	}
}
