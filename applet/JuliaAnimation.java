package com.bobberto1995;

import java.util.Scanner;

import processing.core.PApplet;
import processing.core.PImage;

public class JuliaAnimation extends PApplet
{
	public void setup()
	{
		colorMode(HSB);
		noLoop();
		Scanner sc = new Scanner(System.in);
		System.out.print("Filename: ");
		String filename = sc.nextLine();
		System.out.print("Width (in pixels): ");
		int width = sc.nextInt();
		System.out.print("Height (in pixels):");
		int height = sc.nextInt();
		System.out.print("Iterations: ");
		int iterations = sc.nextInt();
		
		double minX = -2;
		double maxX = 2;
		
		double zoomY = (maxX - minX) * (double)height / (double)width / 2d;
		double minY = -zoomY;
		double maxY = zoomY;
		PImage p;
		
		
		System.out.print("Seed real component: ");
		double real = sc.nextDouble();
		System.out.print("Seed imaginary component: ");
		double imag = -sc.nextDouble();
		System.out.print("Seed real destination: ");
		double realD = sc.nextDouble();
		System.out.print("Seed imaginary destination: ");
		double imagD = -sc.nextDouble();
		System.out.print("Frames of animation: ");
		int frames = sc.nextInt();
		
		double stepR = (realD - real) / (double) frames;
		double stepI = (imagD - imag) / (double) frames;
		
		for(int i = 0; i < frames; i++)
		{
			p = drawJulia(width, height, real, imag, iterations, minX, minY, maxX, maxY);
			p.save(filename + i + ".png");
			real += stepR;
			imag += stepI;
			System.out.println((double) i / (double) frames * 100);
		}	
		
		sc.close();
	}
	
	public void draw()
	{
		System.out.println("Done.");
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
