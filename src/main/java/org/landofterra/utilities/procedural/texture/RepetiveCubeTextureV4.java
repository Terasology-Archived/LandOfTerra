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
package org.landofterra.utilities.procedural.texture;

import org.landofterra.utilities.random.BitScrampler;
import org.terasology.math.TeraMath;
import org.terasology.utilities.procedural.Noise2D;
import org.terasology.utilities.procedural.Noise3D;

/**
 * 
 * @author Esereja
 */
public class RepetiveCubeTextureV4 implements Noise2D, Noise3D {
	
	long seed;
    /**
     * Initialize permutations with a given seed
     *
     * @param seed a seed value used for permutation shuffling
     */
    public RepetiveCubeTextureV4(long seed) {
       this.seed=seed;
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

        double xn = TeraMath.lerp(
        		BitScrampler.integerNoise(x)  , BitScrampler.integerNoise(x+1), BitScrampler.sCurve(xw)
        		);
        
        double yn = TeraMath.lerp(
        		BitScrampler.integerNoise(y)  , BitScrampler.integerNoise(y+1), BitScrampler.sCurve(yw)
        		);

    	return (float) (xn+yn)/2;    
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
        
        double xn = TeraMath.lerp(
        		BitScrampler.integerNoise(x)  , BitScrampler.integerNoise(x+1), BitScrampler.sCurve(xw)
        		);
        
        double yn = TeraMath.lerp(
        		BitScrampler.integerNoise(y)  , BitScrampler.integerNoise(y+1), BitScrampler.sCurve(yw)
        		);
        
        double zn = TeraMath.lerp(
        		BitScrampler.integerNoise(z)  , BitScrampler.integerNoise(z+1), BitScrampler.sCurve(zw)
        		);
    	
    	return (float) (xn+yn+zn)/3;  
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
        double ww = win - TeraMath.fastFloor(win);
        
        double xn = TeraMath.lerp(
        		BitScrampler.integerNoise(x)  , BitScrampler.integerNoise(x+1), BitScrampler.sCurve(xw)
        		);
        
        double yn = TeraMath.lerp(
        		BitScrampler.integerNoise(y)  , BitScrampler.integerNoise(y+1), BitScrampler.sCurve(yw)
        		);
        
        double zn = TeraMath.lerp(
        		BitScrampler.integerNoise(z)  , BitScrampler.integerNoise(z+1), BitScrampler.sCurve(zw)
        		);
        
        double wn = TeraMath.lerp(
        		BitScrampler.integerNoise(w)  , BitScrampler.integerNoise(w+1), BitScrampler.sCurve(ww)
        		);
    	
    	return (float) (xn+yn+zn+wn)/4;       
    	}

}
