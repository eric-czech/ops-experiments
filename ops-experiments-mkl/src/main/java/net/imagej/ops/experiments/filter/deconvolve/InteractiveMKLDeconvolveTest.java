
package net.imagej.ops.experiments.filter.deconvolve;

import java.io.IOException;

import net.imagej.ImageJ;
import net.imglib2.RandomAccessibleInterval;
import net.imglib2.img.Img;
import net.imglib2.type.NativeType;
import net.imglib2.type.numeric.RealType;
import net.imglib2.type.numeric.real.FloatType;

public class InteractiveMKLDeconvolveTest<T extends RealType<T> & NativeType<T>> {

	final static ImageJ ij = new ImageJ();

	public static <T extends RealType<T> & NativeType<T>> void main(
		final String[] args) throws IOException
	{

		System.out.println("CWD: " + System.getProperty("user.dir"));
		final String libPathProperty = System.getProperty("java.library.path");
		System.out.println("Lib path:" + libPathProperty);

		ij.launch(args);

		final String inputName = "../images/Bars-G10-P15-stack-cropped.tif";
		final String psfName = "../images/PSF-Bars-stack-cropped.tif";
		//final String inputName = "/home/bnorthan/images/m2lasers/Spheroid/GFP RAW Stack-192.tif";
		//final String psfName = "/home/bnorthan/images/m2lasers/Spheroid/psfnorm-64-100.tif";
		
		final int iterations = 50;
		@SuppressWarnings("unchecked")
		final Img<T> img = (Img<T>) ij.dataset().open(inputName).getImgPlus()
			.getImg();
		final Img<FloatType> imgF = ij.op().convert().float32(img);

		@SuppressWarnings("unchecked")
		final Img<T> psf = (Img<T>) ij.dataset().open(psfName).getImgPlus()
			.getImg();

		// convert PSF to float
		Img<FloatType> psfF = ij.op().convert().float32(psf);

		// normalize PSF
		final FloatType sum = new FloatType(ij.op().stats().sum(psfF)
			.getRealFloat());
		psfF = (Img<FloatType>) ij.op().math().divide(psfF, sum);

		ij.ui().show("img ", img);
		ij.ui().show("psf ", psf);

		long startTime, endTime;

		// run MKL Richardson Lucy op

		startTime = System.currentTimeMillis();

		final RandomAccessibleInterval<FloatType> outputMKL =
			(RandomAccessibleInterval<FloatType>) ij.op().run(
				MKLRichardsonLucyOp.class, imgF, psfF, new long[] { 32, 32, 50 },null,null,null,
				false, iterations,true);
		

		endTime = System.currentTimeMillis();

		ij.log().info("Total execution time MKL (decon+overhead) is: " + (endTime -
			startTime));

		ij.ui().show("MKL op deconvolved", outputMKL);
	}

}
