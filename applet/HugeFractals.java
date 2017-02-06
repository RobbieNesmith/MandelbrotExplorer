package com.bobberto1995;
//you're a masochist if you use this program
import processing.core.*;
import java.util.Scanner;

public class HugeFractals extends PApplet
{
	public void setup()
	{
		colorMode(HSB);
		noLoop();
		Scanner sc = new Scanner(System.in);
		System.out.print("Mode (0 for Mandelbrot, 1 for Julia): ");
		int mode = sc.nextInt();
		sc.nextLine();
		System.out.print("Filename: ");
		String filename = sc.nextLine() + ".png";
		System.out.print("Width (in pixels): ");
		int width = sc.nextInt();
		System.out.print("Height (in pixels):");
		int height = sc.nextInt();
		System.out.print("Iterations: ");
		int iterations = sc.nextInt();
		System.out.print("Minimum X: ");
		double minX = sc.nextDouble();
		System.out.print("Maximum X: ");
		double maxX = sc.nextDouble();
		System.out.print("Center Y: ");
		double centerY = -sc.nextDouble();
		double zoomY = (maxX - minX) * (double)height / (double)width / 2d;
		double minY = centerY - zoomY;
		double maxY = centerY + zoomY;
		PImage p;
		
		if(mode == 0)
		{
			p = drawMandelbrot(width, height, iterations, minX, minY, maxX, maxY);
		}
		else
		{
			System.out.print("Seed real component: ");
			double real = sc.nextDouble();
			System.out.print("Seed imaginary component: ");
			double imag = sc.nextDouble();
			p = drawJulia(width, height, real, imag, iterations, minX, minY, maxX, maxY);
		}
		p.save(filename);
		System.out.println("Done.");
		sc.close();
	}
	
	public void draw()
	{
		
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
			System.out.println((double) i / (double) w * 100);
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
			System.out.println((double) i / (double) w * 100);
		}
		
		return p;
	}
}
