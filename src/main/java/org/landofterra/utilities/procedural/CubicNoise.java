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
package org.landofterra.utilities.procedural;

import org.landofterra.utilities.random.BitScrampler;
import org.terasology.math.TeraMath;
import org.terasology.utilities.procedural.Noise2D;
import org.terasology.utilities.procedural.Noise3D;

/**
 * 
 * @author Esereja
 */
public class CubicNoise implements Noise2D, Noise3D {
	
	long seed;
	private int type;
	
    /**
     * Initialize permutations with a given seed
     *
     * @param seed a seed value used for permutation shuffling
     */
    public CubicNoise(long seed) {
       this.seed=seed;
       this.type=1;
    }
    
    /**
     *
     * @param seed
     * @param type
     */
    public CubicNoise(long seed, int type) {
        this.seed=seed;
        this.type=type;
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
    	int s=Float.floatToRawIntBits(seed);
    	int x=s^TeraMath.floorToInt(xin);
    	int y=s^TeraMath.floorToInt(yin);    	
    	
        double xw = xin - TeraMath.fastFloor(xin);
        double yw = yin - TeraMath.fastFloor(yin);
        
        double w=0;
        
        if(this.type==1){
        	w=BitScrampler.sCurve(xw);
        }else{
        	w=BitScrampler.sCurve(yw);
        }
        
        return (float) TeraMath.lerp(
        		BitScrampler.integerNoise(
        				x^BitScrampler.scrampleBits(y)
        		),
        		BitScrampler.integerNoise(
        				(x+1)^BitScrampler.scrampleBits(y+1)			
        		)
        		, w);
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
    public float noise(float xin, float yin, float zin) {
    	int s=Float.floatToRawIntBits(seed);
    	int x=s^TeraMath.floorToInt(xin);
    	int y=s^TeraMath.floorToInt(yin);
    	int z=s^TeraMath.floorToInt(zin);
    	
    	
        double xw = xin - TeraMath.fastFloor(xin);
        double yw = yin - TeraMath.fastFloor(yin);
        double zw = zin - TeraMath.fastFloor(zin);
        
        double w=0;
        
        if(this.type==1){
        	w=BitScrampler.sCurve(xw);
        }else if(this.type==2){
        	w=BitScrampler.sCurve(yw);
        }else{
        	w=BitScrampler.sCurve(zw);
        }        
        
        return (float) TeraMath.lerp(
        		BitScrampler.integerNoise(
        				x^BitScrampler.scrampleBits(
        						y^BitScrampler.scrampleBits(z)
        						)
        		),
        		BitScrampler.integerNoise(
        				(x+1)^BitScrampler.scrampleBits(
        						(y+1)^BitScrampler.scrampleBits(z+1)
        						)    
        						
        		)
        		, w);
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
    	int s=Float.floatToRawIntBits(seed);
    	int x=s^TeraMath.floorToInt(xin);
    	int y=s^TeraMath.floorToInt(yin);
    	int z=s^TeraMath.floorToInt(zin);
    	int w=s^TeraMath.floorToInt(win);
    	
    	
        double xw = xin - TeraMath.fastFloor(xin);
        double yw = yin - TeraMath.fastFloor(yin);
        double zw = zin - TeraMath.fastFloor(zin);
        double ww = zin - TeraMath.fastFloor(win);
        
        double w1=0;
        
        if(this.type==1){
        	w1=BitScrampler.sCurve(xw);
        }else if(this.type==2){
        	w1=BitScrampler.sCurve(yw);
        }else if(this.type==3){
        	w1=BitScrampler.sCurve(zw);
        }else{
        	w1=BitScrampler.sCurve(ww);
        }        
        
        return (float) TeraMath.lerp(
        		BitScrampler.integerNoise(
        				x^BitScrampler.scrampleBits(
        						y^BitScrampler.scrampleBits(
        								z^BitScrampler.scrampleBits(w)
        								)
        						)
        		),
        		BitScrampler.integerNoise(
        				(x+1)^BitScrampler.scrampleBits(
        						(y+1)^BitScrampler.scrampleBits(
        								(z+1)^BitScrampler.scrampleBits(w+1)
        								)
        						)    
        						
        		)
        		, w1);
    }


	/**
	 * @return the type
	 */
	public int getType() {
		return type;
	}


	/**
	 * @param type the type to set
	 */
	public void setType(int type) {
		this.type = type;
	}

}
