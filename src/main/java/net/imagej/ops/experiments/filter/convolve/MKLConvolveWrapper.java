package net.imagej.ops.experiments.filter.convolve;

import org.bytedeco.javacpp.FloatPointer;
import org.bytedeco.javacpp.Loader;
import org.bytedeco.javacpp.annotation.Platform;
import org.bytedeco.javacpp.annotation.Properties;

@Properties( value = {
		@Platform(include="MKLFFTW.h", link={"MKLFFTW", "libiomp5md", "mkl_core", "mkl_intel_lp64", "mkl_intel_thread"}),
	    @Platform(value = "windows-x86_64", 
	    linkpath = {"C:/Program Files (x86)/IntelSWTools/compilers_and_libraries/windows/mkl/lib/intel64/", 
	    		"C:/Program Files (x86)/IntelSWTools/compilers_and_libraries_2017/windows/compiler/lib/intel64_win"},
	    includepath = "C:/Program Files (x86)/IntelSWTools/compilers_and_libraries/windows/mkl/include"),
	    
		})
public class MKLConvolveWrapper {
	static {
		Loader.load();
	}
	
	public static native void mklConvolve
	(FloatPointer x, FloatPointer h, FloatPointer y, FloatPointer X_, FloatPointer H_, int width, int height, boolean conj);
	
	public static void load() {
		Loader.load();
	};
}
