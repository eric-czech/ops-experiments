
package net.imagej.ops.experiments.filter.deconvolve;

import java.io.IOException;

import net.imagej.ImageJ;
import net.imglib2.img.Img;
import net.imglib2.type.NativeType;
import net.imglib2.type.numeric.RealType;
import net.imglib2.type.numeric.real.FloatType;

public class InteractiveDeconvolveTest<T extends RealType<T> & NativeType<T>> {

	final static ImageJ ij = new ImageJ();

	public static <T extends RealType<T> & NativeType<T>> void main(
		final String[] args) throws IOException
	{

		final String libPathProperty = System.getProperty("java.library.path");
		System.out.println("Lib path:" + libPathProperty);

		ij.launch(args);

		final String inputName = "../images/Bars-G10-P15-stack-cropped.tif";
		final String psfName = "../images/PSF-Bars-stack-cropped.tif";

		@SuppressWarnings("unchecked")
		final Img<T> img = (Img<T>) ij.dataset().open(inputName).getImgPlus()
			.getImg();
		
		final Img<FloatType> imgF=ij.op().convert().float32(img);

		@SuppressWarnings("unchecked")
		final Img<T> psf = (Img<T>) ij.dataset().open(psfName).getImgPlus()
			.getImg();

		// convert PSF to float
		Img<FloatType> psfF = ij.op().convert().float32(psf);

		// normalize PSF
		final FloatType sum = new FloatType(ij.op().stats().sum(psfF)
			.getRealFloat());
		psfF = (Img<FloatType>) ij.op().math().divide(psfF, sum);

		ij.ui().show("bars ", img);
		ij.ui().show("psf ", psf);
		
		final int iterations=100;
		final int pad=20;

		// run Ops Richardson Lucy

		final long startTime = System.currentTimeMillis();

		final Img<FloatType> deconvolved = (Img<FloatType>) ij.op().deconvolve()
			.richardsonLucy(imgF, psfF, new long[] { pad, pad, pad }, null, null,
				null, null, iterations, false, false);

		final long endTime = System.currentTimeMillis();

		ij.log().info("Total execution time (Ops) is: " + (endTime - startTime));

		ij.ui().show("Richardson Lucy deconvolved", deconvolved);

		/*
		
		// run Cuda Richardson Lucy op
		
		startTime = System.currentTimeMillis();
		
		final RandomAccessibleInterval<FloatType> outputCuda = (RandomAccessibleInterval<FloatType>) ij.op()
				.run(YacuDecuRichardsonLucyOp.class, imgF, psfF, new long[] { pad, pad, pad }, iterations);
		
		endTime = System.currentTimeMillis();
		
		ij.log().info("Total execution time (Cuda) is: " + (endTime - startTime));
		
		
		ij.ui().show("cuda op deconvolved", outputCuda);
		
		// run MKL Richardson Lucy
		
		startTime = System.currentTimeMillis();
		
		// run MKL Richardson Lucy op
		final RandomAccessibleInterval<FloatType> outputMKL = (RandomAccessibleInterval<FloatType>) ij.op()
				.run(MKLRichardsonLucyOp.class, imgF, psfF, new long[]{pad,pad,pad}, null, null, null, iterations, true);
		
		endTime = System.currentTimeMillis();
		
		
		ij.log().info("Total execution time (MKL) is: " + (endTime - startTime));
		
		
		ij.ui().show("mkl op deconvolved", outputMKL);
		*/

	}

}
