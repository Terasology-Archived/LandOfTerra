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

/**
 * tools for number randomization, hassing and similar, mostly for noise generation or manipulation.
 * from assorted sources.
 * @author Esereja 
 */
public class BitScrampler {
	
    private BitScrampler() {
    }

    /***
     * Returns a random int value.
     * @param intIn
     * @return
     */
    public static int scrampleBits(final int intIn) {
    	int in=intIn;
    	in++;
    	in ^= (in << 21);
    	in ^= (in >>> 35);
    	in ^= (in << 4);
    	return in;
    }
    
    /**
     * Returns a random Float value.
     * @param fIn
     * @return
     */
    public static float scrampleBits(final float fIn) {
    	return (float) (scrampleBits(Float.floatToRawIntBits(fIn)));
    }
    
    /**
     * Returns a random int value.
     * @param intIn
     * @param rounds
     * @return
     */
    public static int scrampleBits(final int intIn, final int rounds) {
    	int in=intIn;
    	for(int r = 0;r<rounds;r++){
	    	in++;
	    	in ^= (in << 21);
	    	in ^= (in >>> 35);
	    	in ^= (in << 4);
	    }
    	return in;
    }
    
    /***
     * 
     * @param longIn
     * @param rounds
     * @return
     */
    public static long scrampleBits(final long longIn, final int rounds) {
    	long in=longIn;//TODO change this method to be suitable for Long
    	in++;
    	for(int r = 0;r<rounds;r++){
    		in ^= (in << 43);
    		in ^= (in >>> 69);
    		in ^= (in << 8);
    		}
    	return in;
    }
    
    /**
     * 
     * @param longIn
     * @return
     */
    public static long scrampleBits(final long longIn) {
    	return scrampleBits(longIn,1);
    }

    /**
     * 
     * @param dIn
     * @return
     */
    public static double scrampleBits(final double dIn) {
    	long in= Double.doubleToLongBits(dIn);
    	return Double.longBitsToDouble(scrampleBits(in,1));
    }
    
    /**
     * 
     * @param dIn
     * @param rounds
     * @return
     */
    public static double scrampleBits(final double dIn, final int rounds) {
    	long in= Double.doubleToLongBits(dIn);
    	return Double.longBitsToDouble(scrampleBits(in,rounds));
    }
    
    /**
     * Returns a random Float value.
     * @param fIn
     * @param rounds
     * @return
     */
    public static float scrampleBits(final float fIn, final int rounds) {
    	return (float) (scrampleBits(Float.floatToRawIntBits(fIn),rounds));
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
    
    /**
     * 
     * @param xin
     * @param yin
     * @return
     */
    public static float zCurve(final float xin,final float yin){
    	int i = 0;
    	int x= Float.floatToIntBits(xin);
    	int y= Float.floatToIntBits(yin);
    	for(int j=0; j < 16; j++){
    		i |= (x & 1) << (2 * j);
    		x >>= 1;
    		i |= (y & 1) << (2 * j + 1);
    		y >>= 1;
    	}
    	return Float.intBitsToFloat(i);
    }
    
    /**
     * 
     * @param xin
     * @param yin
     * @return
     */
    public static long zCurve(final long xin,final long yin){
    	long i = 0;
    	long x= xin;
    	long y= yin;
    	for(long j=0; j < 16; j++){
    		i |= (x & 1) << (2 * j);
    		x >>= 1;
    		i |= (y & 1) << (2 * j + 1);
    		y >>= 1;
    	}
    	return i;
   }
    
    /**
     * 
     * @param xin
     * @param yin
     * @return
     */
    public static double zCurve(final double xin,final double yin){
    	long i = 0;
    	long x= Double.doubleToLongBits(xin);
    	long y= Double.doubleToLongBits(yin);
    	for(long j=0; j < 16; j++){
    		i |= (x & 1) << (2 * j);
    		x >>= 1;
    		i |= (y & 1) << (2 * j + 1);
    		y >>= 1;
    	}
    	return Double.longBitsToDouble(i);
   }
    
    /**
     * Bob Jenkins' One-At-A-Time hashing algorithm
     * @param xin
     * @param rounds
     * @return
     */
    public static int oaatHash(final int xin,final int rounds) {
    	int x = xin;
    	for(int i=0; i<rounds; i++){
	        x += ( x << 10 );
	        x ^= ( x >>  6 );
	        x += ( x <<  3 );
	        x ^= ( x >> 11 );
	       	x += ( x << 15);
        }
        return x;
    }
    
    /**
     * Bob Jenkins' One-At-A-Time hashing algorithm
     * @param xin
     * @param rounds
     * @return
     */
    public static long oaatHash(final long xin,final int rounds) {
    	long x = xin;
    	for(int i=0; i<rounds; i++){
	        x += ( x << 10 );
	        x ^= ( x >>  6 );
	        x += ( x <<  3 );
	        x ^= ( x >> 11 );
	       	x += ( x << 15);
        }
        return x;
    }
    
    /**
     * returns float value in between 0-1, which is constructed from inputs mantissa.
     * @param in
     * @return
     */
    public static float subZero(final float in){
    	int bits = Float.floatToIntBits(in);
    	bits = bits & ((1 << 23) - 1);
    	int one=Float.floatToIntBits(1);
    	bits |= one;
    	return Float.intBitsToFloat(bits)-1f;
    }
    
    public static float subZero(final int in){
    	int bits = in;
    	bits = bits & ((1 << 23) - 1);
    	int one=Float.floatToIntBits(1);
    	bits |= one;
    	return Float.intBitsToFloat(bits)-1f;
    }

    //TODO fix this method
    public static double subZero(final double in){
    	long bits = Double.doubleToLongBits(in);
    	bits = bits & ((1 << 53) - 1);
    	int one=Float.floatToIntBits(1);
    	bits |= one;
    	return Double.longBitsToDouble(bits)-1;
    }
    
    /** 
     * random based on One-At-A-Time hashing
     * @param in
     * @return
     */
    public static float hashRandom(final int in ) {
    	int i=in;
    	return subZero(oaatHash(i,1))*2-1; 
    }
    
    /** 
     * random based on One-At-A-Time hashing
     * @param in
     * @return
     */
    public static float hashRandom(final float in ) {
    	int i=Float.floatToIntBits(in);
    	return subZero(oaatHash(i,1))*2-1; 
    }
    
    /**
     * random based on One-At-A-Time hashing
     * @param in
     * @return
     */
    public static double hashRandom(final long in ) {
    	long i=in;
    	return subZero(oaatHash(i,2))*2-1; 
    }
    
    /**
     * random based on One-At-A-Time hashing
     * @param in
     * @return
     */
    public static double hashRandom(final double in ) {
    	long i=Double.doubleToLongBits(in);
    	return subZero(oaatHash(i,2))*2-1; 
    }
    
    /**
     * xor implementation, because writing conversions all time is pain.
     * @param x
     * @param y
     * @return
     */
    public static float xor(final float x,final float y){
    	int xin= Float.floatToIntBits(x);
    	int yin= Float.floatToIntBits(y);
    	return Float.intBitsToFloat(xin^yin);
    }
    
    /**
     * xor implementation, because writing conversions all time is pain.
     * @param x
     * @param y
     * @return
     */
    public static float xor(final float x,final int y){
    	int xin= Float.floatToIntBits(x);
    	int yin= y;
    	return Float.intBitsToFloat(xin^yin);
    }
    
    /**
     * xor implementation, because writing conversions all time is pain.
     * @param x
     * @param y
     * @return
     */
    public static float xor(final int x,final float y){
    	int xin= x;
    	int yin= Float.floatToIntBits(y);
    	return Float.intBitsToFloat(xin^yin);
    }
    
    /**
     * xor implementation, because writing conversions all time is pain.
     * @param x
     * @param y
     * @return
     */
    public static double xor(final double x,final double y){
    	long xin= Double.doubleToLongBits(x);
    	long yin= Double.doubleToLongBits(y);
    	return Double.longBitsToDouble(xin^yin);
    }
    
    /**
     * xor implementation, because writing conversions all time is pain.
     * @param x
     * @param y
     * @return
     */
    public static double xor(final double x,final long y){
    	long xin= Double.doubleToLongBits(x);
    	long yin= y;
    	return Double.longBitsToDouble(xin^yin);
    }
    
    /**
     * xor implementation, because writing conversions all time is pain.
     * @param x
     * @param y
     * @return
     */
    public static double xor(final long x,final double y){
    	long xin= x;
    	long yin= Double.doubleToLongBits(y);
    	return Double.longBitsToDouble(xin^yin);
    }
    
}
