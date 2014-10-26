/*
 * Copyright 2014 MovingBlocks
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

import org.terasology.utilities.procedural.Noise2D;
import org.terasology.utilities.procedural.Noise3D;

/**
 * @author Esereja
 */
public class SimpleTurbulenceAdapter implements Noise3D,Noise2D {

    private Noise3D noise3;
    private Noise2D noise2;
    
    private int function;

    /***
     * takes in 3d noise and creates turbulence whit this noise. seed is static so result
     * isn't that strong as whit variable seeds. but it is much cheaper computationally.
     * @param noise 3d noise 
     */
    public SimpleTurbulenceAdapter(Noise3D noise) {
        this.noise3 = noise;
        this.function=0;
    }
    
    /***
     * takes in 3d noise and creates turbulence whit this noise. seed is static so result
     * isn't that strong as whit variable seeds. but it is much cheaper computationally.
     * @param noise 3d noise 
     */
    public SimpleTurbulenceAdapter(Noise3D noise,int function) {
        this.noise3 = noise;
        this.function=function;
    }
    
    /***
     * takes in 2d noise and creates turbulence whit this noise. seed is static so result
     * isn't that strong as whit variable seeds. but it is much cheaper computationally.
     * @param noise 2d noise
     * @param b does nothing, give it some small value. like zero. 
     */
    public SimpleTurbulenceAdapter(Noise2D noise, byte b) {
        this.noise2 = noise;
        this.function=0;
    }
    
    /***
     * takes in 2d noise and creates turbulence whit this noise. seed is static so result
     * isn't that strong as whit variable seeds. but it is much cheaper computationally.
     * @param noise
     * @param function
     * @param b
     */
    public SimpleTurbulenceAdapter(Noise2D noise,int function, byte b) {
        this.noise2 = noise;
        this.function=function;
    } 

    @Override
    public float noise(float x, float y,float z) {
    	switch (this.function){
			case 8:
				return noise3.noise(x+x*noise3.noise(x, y, z), y+y*noise3.noise(x, y, z), z+z*noise3.noise(x, y, z));
			case 7:
				return noise3.noise(x*noise3.noise(x, y, z), y*noise3.noise(x, y, z), z*noise3.noise(x, y, z));
    		case 6:
    			return noise3.noise(z+noise3.noise(x, y, z), x+noise3.noise(x, y, z), y+noise3.noise(x, y, z));
    		case 5:
    			return noise3.noise(y+noise3.noise(x, y, z), z+noise3.noise(x, y, z), x+noise3.noise(x, y, z));
    		case 4:
    			return noise3.noise(x+noise3.noise(x, x, x), y+noise3.noise(y, y, y), z+noise3.noise(z, z, z));
			case 3:
				return noise3.noise(x+noise3.noise(x, y, z), y+noise3.noise(x, z, y), z+noise3.noise(y, x, z));
    		case 2:
        		return noise3.noise(x+noise3.noise(x, y, z), y+noise3.noise(y, z, x), z+noise3.noise(z, x, y));
        	default:
        		return noise3.noise(x+noise3.noise(x, y, z), y+noise3.noise(x, y, z), z+noise3.noise(x, y, z));
        }
    }
    
    @Override
    public float noise(float x, float y) {
    	switch (this.function){
    	case 4:
    		return noise2.noise(y+noise2.noise(x, y), x+noise2.noise(y, x));
    	case 3:
    		return noise2.noise(y+noise2.noise(x, y), x+noise2.noise(x, y));
    	case 2:
    		return noise2.noise(x+noise2.noise(x, y), y+noise2.noise(y, x));
    	default:
    		return noise2.noise(x+noise2.noise(x, y), y+noise2.noise(x, y));
    	}
    }
}
