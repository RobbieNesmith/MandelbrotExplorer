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
				int[] iters = FractalRenderer.renderMandelbrotIters(minX, maxX, minY, maxY, width, height, iterations);
				p = FractalColorManager.itersMod(iters, width, height, iterations);
			}
			else
			{
				int[] iters = FractalRenderer.renderJuliaIters(real, imag, minX, maxX, minY, maxY, width, height, iterations);
				p = FractalColorManager.itersMod(iters, width, height, iterations);
			}
			
			p.save(sketchPath(filename + i + ".png"));
			
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
		exit();
	}
}
