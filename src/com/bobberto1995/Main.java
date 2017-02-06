package com.bobberto1995;

//this code is garbage
//TODO make all floats doubles

import processing.core.*; //add core.jar to the java build path

public class Main extends PApplet
{
	public final int MANDEL = 0;
	public final int JULIA = 1;
	public int mode = MANDEL;
	public int maxiter;
	public int iter;
	public double real, imag;
	public double pilr, piud;
	public PImage p;
	public boolean showOrbit = true, showHelp = true, showVerbose = true, showOverlay = true;
	public double minX = -2, maxX = 2, minY, maxY; 
	public int imageNum = 0;
	private int overlayMaxIter = 256;

	public static void main(String args[])
    {
      PApplet.main(new String[] { com.bobberto1995.Main.class.getName() });
    }
	
	public void setup() {
		colorMode(HSB);
		iter = 0;
		maxiter = 1024;
		size(1024, 768);
		maxY = maxX * (float) height / (float) width;
		minY = -maxY;
		real = .2f;
		imag = .4f;
		// drawJulia(0.2f,0.4f);
		drawMandel();
	}

	//==============================================================================================================
	//draw method! it's here!
	//==============================================================================================================
	
	public void draw() {
		if(mode == MANDEL && showOverlay)
		{
			drawJuliaOverlay(320, 240);
		}
		image(p, 0, 0);
		if (showOrbit) {
			drawIters(mode, real, imag);
		}
		if (showHelp) {
			drawHelp();
		}
		if (showVerbose) {
			drawVerbose();
		}
	}

	public void drawMandel() {
		p = createImage(width, height, HSB);
		p.loadPixels();
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				double ii = (double)i / (double)width * (maxX - minX) + minX; //map(i, 0, width, minX, maxX);
				double jj = (double)j / (double)height * (maxY - minY) + minY;
				double a = ii;
				double b = jj;

				iter = 0;

				while (iter < maxiter && a * a + b * b < 4) {
					double temp = a * a - b * b + ii;
					b = 2 * a * b + jj;
					a = temp;
					iter++;
				}
				if (iter == maxiter) {
					p.pixels[width * j + i] = color(0);
				} else
				{
					p.pixels[width * j + i] = color(iter % 255, 255, 255);
				}
			}
		}
	}

	public void drawJuliaOverlay(int xSize, int ySize) {
		double x = map(mouseX, 0, width, minX, maxX);
		double y = map(mouseY, 0, height, minY, maxY);
		for (int i = 0; i < xSize; i++) {
			for (int j = 0; j < ySize; j++) {
				double a = map(i, 0, xSize, -2d, 2d);
				double b = map(j, 0, ySize, -1.5d, 1.5d);

				int iter = 0;

				while (iter < overlayMaxIter && a * a + b * b < 4) {
					double temp = a * a - b * b + x;
					b = 2 * a * b + y;
					a = temp;
					iter++;
				}
				int xPix = width - xSize + i; // x pixel location
				int yPix = height - ySize + j; // y pixel location
				if (iter == overlayMaxIter) {
					p.pixels[width * yPix + xPix] = color(0);
				} else {
					p.pixels[width * yPix + xPix] = color(iter % 255, 255, 255);
				}
			}
		}
		p.updatePixels();
	}

	public void drawJulia(double real, double imag) {
		p = createImage(width, height, HSB);
		p.loadPixels();
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				double a = map(i, 0, width, minX, maxX);
				double b = map(j, 0, height, minY, maxY);

				iter = 0;

				while (iter < maxiter && a * a + b * b < 4) {
					double temp = a * a - b * b + real;
					b = 2 * a * b + imag;
					a = temp;
					iter++;
				}
				if (iter == maxiter) {
					p.pixels[width * j + i] = color(0);
				} else{
					p.pixels[width * j + i] = color(iter % 255, 255, 255);
				}
			}
		}
	}

	public void drawIters(int mode, double real, double imag) {
		double a, b, x, y;
		if (mode == MANDEL) {
			x = map(mouseX, 0, width, minX, maxX);
			y = map(mouseY, 0, height, minY, maxY);
			a = x;
			b = y;
		} else {
			x = real;
			y = imag;
			a = map(mouseX, 0, width, minX, maxX);
			b = map(mouseY, 0, height, minY, maxY);
		}

		double c = 0;
		double d = 0;

		iter = 0;
		
		while (iter < overlayMaxIter) {
			c = a;
			d = b;

			double temp = a * a - b * b + x;
			b = 2 * a * b + y;
			a = temp;

			//xx xxx yy yyy are coordinates for the line in pixels xx and yy are the further iterated ones
			
			double xx = map(a, minX, maxX, 0, width);
			double yy = map(b, minY, maxY, 0, height);
			double xxx = map(c, minX, maxX, 0, width);
			double yyy = map(d, minY, maxY, 0, height);
			
			stroke(255);
			line((float)xx, (float)yy, (float)xxx, (float)yyy);
			iter++;
		}
	}

	public void drawHelp() {
		int ts = 20;
		textSize(ts);
		textAlign(LEFT);
		text("Mandelbrot/Julia Visualizer Help", 0, ts);
		text("WASD = Move view", 0, 2 * ts);
		text("QE = Zoom in, zoom out", 0, 3 * ts);
		text("Z = Reset zoom", 0, 4 * ts);
		text("UJ = Double, halve overlay iteration count", 0, 5 * ts);
		text("IK = Double, halve iteration count", 0, 6 * ts);
		text("O = Toggle drawing of orbit", 0, 7 * ts);
		text("V = Toggle verbose output", 0, 8 * ts);
		text("? = Toggle help", 0, 9 * ts);
		text("X = Take Picture", 0, 10 * ts);
		text("L = Toggle overlay", 0, 11 * ts);
	}

	public void drawVerbose() {
		double a = map(mouseX, 0, width, minX, maxX);
		double b = map(mouseY, 0, height, minY, maxY);
		int ts = 20;
		textSize(ts);
		textAlign(RIGHT);
		if (mode == MANDEL) {
			text("Mode = Mandelbrot", width, ts);
		} else {
			text("Mode = Julia", width, ts);
		}
		text("Iteration Count = " + maxiter, width, ts * 2);
		text("Overlay Iteration Count = " + overlayMaxIter, width, ts * 3);
		text("minX = " + minX, width, ts * 4);
		text("maxX = " + maxX, width, ts * 5);
		text("minY = " + minY, width, ts * 6);
		text("maxY = " + maxY, width, ts * 7);
		if (b >= 0) {
			text("Pointer coordinates = " + a + " - " + b + "i", width, ts * 8);
		} else {
			text("Pointer coordinates = " + a + " + " + abs((float)b) + "i", width,
					ts * 8);
		}
		if (mode == JULIA) {
			if (imag >= 0) {
				text("Julia seed = " + real + " - " + imag + "i", width, ts * 9);
			} else {
				text("Julia seed = " + real + " + " + abs((float)imag) + "i", width,
						ts * 9);
			}
		}
	}

	public void mousePressed() {
		mode = (mode + 1) % 2;
		print(mode);
		if (mode == 0) {
			drawMandel();
		} else {
			real = map(mouseX, 0, width, minX, maxX);
			imag = map(mouseY, 0, height, minY, maxY);
			drawJulia(real, imag);
		}
	}

	public void keyPressed() {
		if (key == 'o' || key == 'O') {
			showOrbit = !showOrbit;
		}
		// iteration changes
		if (key == 'i' || key == 'I') {
			maxiter *= 2;
			refresh();
		}
		if ((key == 'k' || key == 'K') && maxiter > 1) {
			maxiter /= 2;
			refresh();
		}
		if (key == 'u' || key == 'U') {
			overlayMaxIter *= 2;
			refresh();
		}
		if ((key == 'j' || key == 'J') && overlayMaxIter > 1) {
			overlayMaxIter /= 2;
			refresh();
		}
		// movement
		if (key == 'a' || key == 'A') {
			double temp = minX - (maxX - minX) / 4;
			maxX -= (maxX - minX) / 4;
			minX = temp;
			refresh();
		}
		if (key == 'd' || key == 'D') {
			double temp = minX + (maxX - minX) / 4;
			maxX += (maxX - minX) / 4;
			minX = temp;
			refresh();
		}
		if (key == 's' || key == 'S') {
			double temp = minY + (maxY - minY) / 4;
			maxY += (maxY - minY) / 4;
			minY = temp;
			refresh();
		}
		if (key == 'w' || key == 'W') {
			double temp = minY - (maxY - minY) / 4;
			maxY -= (maxY - minY) / 4;
			minY = temp;
			refresh();
		}
		// zooming
		if (key == 'q' || key == 'Q') {
			double xRad = (maxX - minX) / 4;
			double yRad = (maxY - minY) / 4;
			double a = map(mouseX, 0, width, minX, maxX);
			double b = map(mouseY, 0, height, minY, maxY);
			minX = a - xRad;
			maxX = a + xRad;
			minY = b - yRad;
			maxY = b + yRad;
			refresh();
		}
		if (key == 'e' || key == 'E') {
			double xRad = (maxX - minX);
			double yRad = (maxY - minY);
			double a = map(mouseX, 0, width, minX, maxX);
			double b = map(mouseY, 0, height, minY, maxY);
			minX = a - xRad;
			maxX = a + xRad;
			minY = b - yRad;
			maxY = b + yRad;
			refresh();
		}
		if (key == 'z' || key == 'Z') {
			minX = -2;
			maxX = 2;
			minY = -1.5f;
			maxY = 1.5f;
			refresh();
		}
		if (key == 'v' || key == 'V') {
			showVerbose = !showVerbose;
		}
		if (key == '?') {
			showHelp = !showHelp;
		}
		if (key == 'x' || key == 'X') {
			String saveString = "Image" + imageNum + ".png";
			save(saveString);
			System.out.println(saveString);
			imageNum++;
		}
		if(key == 'l' || key == 'L')
		{
			showOverlay = !showOverlay;
			refresh();
		}
	}

	public double map(double input, double inMin, double inMax, double outMin, double outMax)
	{
		return (double)(input - inMin) / (double)(inMax - inMin) * (outMax - outMin) + outMin;
	}
	
	public void refresh() {
		if(mode == MANDEL)
		{
			drawMandel();
			if(showOverlay)
			{
				drawJuliaOverlay(320, 240);
			}
		}
		else
		{
			drawJulia(real, imag);
		}
	}
}