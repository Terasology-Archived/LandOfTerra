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
package org.landofterra.utilities.procedural.noise;

import org.landofterra.utilities.random.BitScrampler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.terasology.math.TeraMath;
import org.terasology.utilities.procedural.Noise2D;
import org.terasology.utilities.procedural.Noise3D;
import org.terasology.utilities.random.FastRandom;

/**
 * Deterministic white noise generator
 * @author Esereja
 */
public class SinNoise implements Noise2D, Noise3D {
	private float[] offset;
	private int octaves;
	private float decreaseRate;

    /**
     * Initialize permutations with a given seed
     *
     * @param seed a seed value used for permutation shuffling
     */
    public SinNoise(long seed,int octaves) {
       FastRandom rand=new FastRandom(seed);
       this.octaves=octaves;
       offset= new float[4*(octaves)];
       for(int i=0;i<offset.length;i++){
    	   offset[i]=rand.nextFloat(); 
       }
       this.decreaseRate=0f;
    }
    
    /**
     * Initialize permutations with a given seed
     *
     * @param seed a seed value used for permutation shuffling
     */
    public SinNoise(long seed,int octaves,float decreaseRate) {
       FastRandom rand=new FastRandom(seed);
       this.octaves=octaves;
       offset= new float[8*(octaves)];
       for(int i=0;i<offset.length;i++){
    	   offset[i]=rand.nextFloat(); 
       }
       this.decreaseRate=decreaseRate;
    }


    /**
     * 2D scalable noise
     *
     * @param xin the x input coordinate
     * @param yin the y input coordinate
     * @return a noise value in the interval [-1,1]
     */
    @Override
    public float noise(float xin, float yin) {
    	final int DIMENSION=2;
    	float x=xin;
    	float y=yin;

    	float n=0;
    	
    	for(int multifier=0;multifier<this.octaves;multifier++){
    		float t=(float) (    				
    				 Math.sin( (x+ (offset[0+multifier*DIMENSION]*3.141) )*(multifier+1) )
    				+Math.sin( (y+ (offset[1+multifier*DIMENSION]*3.141) )*(multifier+1) )
    				);
    			float div=DIMENSION;
    			if(this.decreaseRate!=0){
    				div+=multifier*this.decreaseRate;
    			}
    			t=t/div;
    			n+=t;
    		}
        
        return n;
    	}

    /**
     * 3D scalable noise
     *
     * @param xin the x input coordinate
     * @param yin the y input coordinate
     * @param zin the z input coordinate
     * @return a noise value in the interval [-1,1]
     */
    @Override
    public float noise(final float xin,final float yin,final float zin) {
    	final int DIMENSION=3;
    	float x=xin;
    	float y=yin;
    	float z=zin;

    	float n=0;
    	
    	for(int multifier=0;multifier<this.octaves;multifier++){
    		float t=(float) (    				
    				 Math.sin( (x+ (offset[0+multifier*DIMENSION]*3.141) )*(multifier+1) )
    				+Math.sin( (y+ (offset[1+multifier*DIMENSION]*3.141) )*(multifier+1) )
    				+Math.sin( (z+ (offset[2+multifier*DIMENSION]*3.141) )*(multifier+1) )
    				);
    			float div=DIMENSION;
    			if(this.decreaseRate!=0){
    				div+=multifier*this.decreaseRate;
    			}
    			t=t/div;
    			n+=t;
    		}
        
        return n;
    	}


    /**
     * 4D scalable noise
     *
     * @param xin the x input coordinate
     * @param yin the y input coordinate
     * @param zin the z input coordinate
     * @return a noise value in the interval [-1,1]
     */
    public float noise(float xin, float yin, float zin, float win) {
    	final int DIMENSION=4;
    	float x=xin;
    	float y=yin;
    	float z=zin;
    	float w=zin;

    	float n=0;
    	
    	for(int multifier=0;multifier<this.octaves;multifier++){
    		float t=(float) (    				
    				 Math.sin( (x+ (offset[0+multifier*DIMENSION]*3.141) )*(multifier+1) )
    				+Math.sin( (y+ (offset[1+multifier*DIMENSION]*3.141) )*(multifier+1) )
    				+Math.sin( (z+ (offset[2+multifier*DIMENSION]*3.141) )*(multifier+1) )
    				+Math.sin( (z+ (offset[3+multifier*DIMENSION]*3.141) )*(multifier+1) )
    				);
    			float div=DIMENSION;
    			if(this.decreaseRate!=0){
    				div+=multifier*this.decreaseRate;
    			}
    			t=t/div;
    			n+=t;
    		}
        
        return n;
    	}
}