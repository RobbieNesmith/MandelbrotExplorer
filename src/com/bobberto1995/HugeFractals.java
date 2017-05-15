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
			int[] iters = FractalRenderer.renderMandelbrotIters(minX, maxX, minY, maxY, width, height, iterations);
			p = FractalColorManager.itersMod(iters, width, height, iterations);
		}
		else
		{
			System.out.print("Seed real component: ");
			double real = sc.nextDouble();
			System.out.print("Seed imaginary component: ");
			double imag = sc.nextDouble();
			int[] iters = FractalRenderer.renderJuliaIters(real, imag, minX, maxX, minY, maxY, width, height, iterations);
			p = FractalColorManager.itersMod(iters, width, height, iterations);
		}
		p.save(sketchPath(filename));
		System.out.println("Done.");
		sc.close();
	}
	
	public void draw()
	{
		exit();
	}
}
