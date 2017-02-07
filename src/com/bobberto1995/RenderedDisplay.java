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
		fi.zoomIn();
		image(fi.getImage(),0,0);
	}
	public void draw()
	{
		
	}
}
