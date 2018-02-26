MKLROOT=/opt/intel/mkl/
INC_PATH=-I/opt/intel/mkl/include/
LIB_PATH=-L/opt/intel/mkl/lib/intel64 -L/opt/intel/lib/intel64

#libMKLFFTW: MKLFFTW.cpp
#	gcc -fPIC $(INC_PATH) $(LIB_PATH) -lmkl_intel_lp64 -lmkl_core -lmkl_intel_thread -lmkl_tbb_thread -lmkl_rt -liomp5 -lpthread -ldl MKLFFTW.cpp

#libMKLFFTW.o: MKLFFTW.cpp
#	gcc -fPIC $(INC_PATH) $(LIB_PATH) -lmkl_rt -lpthread -lm -ldl -o $@ $<  

#MKLFFTWtest: MKLFFTW.cpp
#	gcc $(INC_PATH) $(LIB_PATH) -Wl,--start-group ${MKLROOT}/lib/intel64/libmkl_intel_lp64.a ${MKLROOT}/lib/intel64/libmkl_intel_thread.a ${MKLROOT}/lib/intel64/libmkl_core.a -Wl,--end-group -liomp5 -#lpthread -lm -ldl MKLFFTW.cpp

#libMKLFFTW.so: MKLFFTW.cpp
#	gcc -shared -fPIC $(INC_PATH) $(LIB_PATH) -Wl,--no-as-needed -lmkl_intel_lp64 -lmkl_intel_thread -lmkl_core -liomp5 -lpthread -lm -ldl -o $@ $<

libMKLFFTW.so: MKLFFTW.cpp
	gcc -shared -fPIC $(INC_PATH) $(LIB_PATH) -Wl,--no-as-needed -lmkl_rt -lpthread -lm -ldl -o $@ $<


#MKLFFTWtest: MKLFFTW.cpp
#	gcc $(INC_PATH) $(LIB_PATH) -Wl,--no-as-needed -lmkl_intel_lp64 -lmkl_intel_thread -lmkl_core -liomp5 -lpthread -lm -ldl -o $@ $<




clean:
	-rm *.o *.so *.a
