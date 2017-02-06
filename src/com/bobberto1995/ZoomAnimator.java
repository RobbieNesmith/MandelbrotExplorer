package com.bobberto1995;
import java.util.Scanner;

import processing.core.PApplet;
import processing.core.PImage;

public class ZoomAnimator extends PApplet
{
	public void setup()
	{
		noLoop();
		Scanner sc = new Scanner(System.in);
		colorMode(HSB);
		
		System.out.print("Mode (0 for Mandelbrot, 1 for Julia): ");
		int mode = sc.nextInt();
		sc.nextLine();
		System.out.print("Filename: ");
		String filename = sc.nextLine();
		System.out.print("Width (in pixels): ");
		int width = sc.nextInt();
		System.out.print("Height (in pixels):");
		int height = sc.nextInt();
		System.out.print("Iterations: ");
		int iterations = sc.nextInt();
		System.out.print("Real coordinate of destination: ");
		double pointReal = sc.nextDouble();
		System.out.print("Imaginary coordinate of destination: ");
		double pointImag = -sc.nextDouble();
		System.out.print("Scale factor (per frame): ");
		double scaleFac = sc.nextDouble();
		System.out.print("Number of frames to render: ");
		int frames = sc.nextInt();
		
		double curPosReal = 0;
		double curPosImag = 0;
		double xRadius = 2;
		
		double real = 0;
		double imag = 0;
		if(mode != 0)
		{
			System.out.print("Seed real component: ");
			real = sc.nextDouble();
			System.out.print("Seed imaginary component: ");
			imag = sc.nextDouble();
		}
		
		
		for(int i = 0; i < frames; i++)
		{
			double minX = curPosReal - xRadius;
			double maxX = curPosReal + xRadius;
			double minY = curPosImag - xRadius * (double) height / (double) width;
			double maxY = curPosImag + xRadius * (double) height / (double) width;
			PImage p;
			if(mode == 0)
			{
				p = drawMandelbrot(width, height, iterations, minX, minY, maxX, maxY);
			}
			else
			{
				p = drawJulia(width, height, real, imag, iterations, minX, minY, maxX, maxY);
			}
			
			p.save(filename + i + ".png");
			
			curPosReal += (pointReal - curPosReal) / 16;
			curPosImag += (pointImag - curPosImag) / 16;
			xRadius *= scaleFac;
			System.out.println((double) i / (double) frames * 100);
		}
		
		sc.close();
	}
	
	public void draw()
	{
		System.out.println(100);
		System.out.println("Done.");
	}
	
	public PImage drawMandelbrot(int w, int h, int iterations, double minX, double minY, double maxX, double maxY)
	{
		PImage p = createImage(w, h, HSB);
		p.loadPixels();
		for (int i = 0; i < w; i++) {
			for (int j = 0; j < h; j++) {
				double ii = (double)i / (double)w * (maxX - minX) + minX; //map(i, 0, width, minX, maxX);
				double jj = (double)j / (double)h * (maxY - minY) + minY;
				double a = ii;
				double b = jj;

				int iter = 0;

				while (iter < iterations && a * a + b * b < 4) {
					double temp = a * a - b * b + ii;
					b = 2 * a * b + jj;
					a = temp;
					iter++;
				}
				if (iter == iterations) {
					p.pixels[w * j + i] = color(0);
				} else {
					p.pixels[w * j + i] = color(iter % 255, 255, 255);
				}
				
			}
		}
		p.updatePixels();
		return p;
	}
	public PImage drawJulia(int w, int h, double real, double imag, int iterations, double minX, double minY, double maxX, double maxY)
	{
		PImage p = createImage(w, h, HSB);
		
		p.loadPixels();
		for (int i = 0; i < w; i++) {
			for (int j = 0; j < h; j++) {
				double a = (double)i / (double)w * (maxX - minX) + minX;
				double b = (double)j / (double)h * (maxY - minY) + minY;

				int iter = 0;

				while (iter < iterations && a * a + b * b < 4) {
					double temp = a * a - b * b + real;
					b = 2 * a * b + imag;
					a = temp;
					iter++;
				}
				if (iter == iterations) {
					p.pixels[w * j + i] = color(0);
				} else {
					p.pixels[w * j + i] = color(iter % 255, 255, 255);
				}
			}
		}
		
		return p;
	}
}
