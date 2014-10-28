/*
 * Copyright 2013 MovingBlocks
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.landofterra.utilities.random;

import org.terasology.math.TeraMath;
import org.terasology.module.sandbox.API;

/**
 * Random number generator based on the Xorshift generator by George Marsaglia.
 *
 * @author Benjamin Glatzel <benjamin.glatzel@me.com>
 */
@API
public class BitScrampler {
	//TODO change all operator to be final
	
    private BitScrampler() {
    }

    /***
     * Returns a random int value.
     *
     * @return Random value
     */
    public static int ScrampleInt(final int intIn) {
    	int in=intIn;
    	in++;
    	in ^= (in << 21);
    	in ^= (in >>> 35);
    	in ^= (in << 4);
    	return in;
    }
    
    /**
     * 
     * @param fIn
     * @return
     */
    public static float ScrampleFloat(final float fIn) {
    	return (float) (ScrampleInt(Float.floatToRawIntBits(fIn)));
    }
    
    /**
     * 
     * @param intIn
     * @param rounds
     * @return
     */
    public static int ScrampleInt(final int intIn, final int rounds) {
    	int in=intIn;
    	for(int r = 0;r<rounds;r++){
	    	in++;
	    	in ^= (in << 21);
	    	in ^= (in >>> 35);
	    	in ^= (in << 4);
	    }
    	return in;
    }
    
    /**
     * 
     * @param fIn
     * @param rounds
     * @return
     */
    public static float ScrampleFloat(final float fIn, final int rounds) {
    	return (float) (ScrampleInt(Float.floatToRawIntBits(fIn),rounds));
    }
    
    
    
    /***
     * This function returns always values between [-1,1].
     * this function is copied from libnoise project.
     * @param n
     * @return
     */
    public static double integerNoise (final int in){
    	int n=in;
    	n = (n >> 13) ^ n;
    	int nn = (n * (n * n * 60493 + 19990303) + 1376312589) & 0x7fffffff;
    	return 1.0 - ((double)nn / 1073741824.0);
    }
    
    /***
     *
     * this function is copied from libnoise project.
     * @param x
     * @return
     */
    public static double fastCoherentNoise (final double x){
      int intX = (int)(TeraMath.fastFloor(x));
      double n0 = integerNoise (intX);
      double n1 = integerNoise (intX + 1);
      double weight = x - TeraMath.fastFloor(x);
      double noise = TeraMath.lerp(n0, n1, weight);
      return noise;
    }
    
    /**
     * 
     * @param x
     * @return
     */
    public static double coherentNoise (final double x){
      int intX = (int)(TeraMath.fastFloor(x));
      double n0 = integerNoise (intX);
      double n1 = integerNoise (intX + 1);
      double weight = x - TeraMath.fastFloor(x);
      double noise = TeraMath.lerp(n0, n1, sCurve(weight));
      return noise;
    }
    
    /**
     * SCurve function smoothes derivative of input
     * @param in
     * @return
     */
    public static double sCurve(final double in){
    	double i=in;
    	//return -2*TeraMath.pow(in, 3)+3*TeraMath.pow(in, 2);
    	return -2*i*i*i+3*i*i;
    }
    
    
    /**
     * 
     * @param xin
     * @param yin
     * @return
     */
    public static int zCurve(final int xin,final int yin){
	  int i = 0;
	  int x= xin;
	  int y= yin;
	  for(int j=0; j < 16; j++){
		i |= (x & 1) << (2 * j);
		x >>= 1;
		i |= (y & 1) << (2 * j + 1);
		y >>= 1;
	  }
	  return i;
   }
    
}
