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
package org.landofterra.utilities.procedural.adapter;

import org.terasology.math.TeraMath;
import org.terasology.utilities.procedural.Noise2D;
import org.terasology.utilities.procedural.Noise3D;

/**
 * takes in noise and creates fancy trigonometric effects whit it.
 * @author Esereja
 */
public class TrigonometricAdapter implements Noise3D,Noise2D {

    private Noise3D noise3;
    private Noise2D noise2;
    
    private int function;
    

    /***
     * takes in 3d noise and creates fancy trigonometric effects whit it.
     * Computationally some what heavy. but result are worth of many layers of noise
     * @param noise
     * @param function
     */
    public TrigonometricAdapter(Noise3D noise,int function) {
        this.noise3 = noise;
        this.function=function;
    }

    
    /***
     * takes in 2d noise and creates fancy trigonometric effects whit it.
     * Computationally some what heavy. but result are worth of many layers of noise
     * @param noise
     * @param function
     * @param b
     */
    public TrigonometricAdapter(Noise2D noise,int function,byte b) {
        this.noise2 = noise;
        this.function=function;
    }

    @Override
    public float noise(float x, float y,float z) {
    	switch (function){
    		//TODO fix artifact at near origo, propaply by if(function !=0){ }else noise()
			case 15://1d layers
				return (float) (Math.sin(TeraMath.sqrt(z*z)) * noise3.noise( x, y, z) );
			case 14://1d layers
				return (float) (Math.sin(TeraMath.sqrt(y*y)) * noise3.noise( x, y, z) );
			case 13://1d layers
				return (float) (Math.sin(TeraMath.sqrt(x*x)) * noise3.noise( x, y, z) );
    		case 12://2d vertical pipes
    			return (float) (Math.sin(TeraMath.sqrt(y*y + z*z)) * noise3.noise( x, y, z) );
    		case 11://2d horizontal pipes
    			return (float) (Math.sin(TeraMath.sqrt(x*x + z*z)) * noise3.noise( x, y, z) );
    		case 10://2d diagonal pipes
    			return (float) (Math.sin(TeraMath.sqrt(x*x + y*y)) * noise3.noise( x, y, z) );
			case 9://3d rings of circle, starting from center and getting bigger looks quite fancy
				return (float) (Math.sin(TeraMath.sqrt(x*x + y*y + z*z)) * noise3.noise( x, y, z) );
			case 8://3d
				return (float) (Math.sin(TeraMath.fastAbs( (float) (Float.floatToRawIntBits(x)^Float.floatToRawIntBits(y)^Float.floatToRawIntBits(z)) ) ) * noise3.noise( x, y, z) );
			case 7://3d doted texture like polka dots but in 3d
				return (float) (Math.sin(TeraMath.fastAbs(x))*Math.sin(TeraMath.fastAbs(y))*Math.sin(TeraMath.fastAbs(z)) * noise3.noise( x, y, z) );
			case 6://2d rods
				return (float) (Math.sin(TeraMath.fastAbs(y))*Math.sin(TeraMath.fastAbs(z)) * noise3.noise( x, y, z) );
			case 5://2d rods
				return (float) (Math.sin(TeraMath.fastAbs(x))*Math.sin(TeraMath.fastAbs(z)) * noise3.noise( x, y, z) );
			case 4://2d rods
				return (float) (Math.sin(TeraMath.fastAbs(x))*Math.sin(TeraMath.fastAbs(y)) * noise3.noise( x, y, z) );
			case 3://1d layers
				return (float) (Math.sin(TeraMath.fastAbs(z)) * noise3.noise( x, y, z) );
			case 2://1d layers
				return (float) (Math.sin(TeraMath.fastAbs(y)) * noise3.noise( x, y, z) );
    		case 1://1d layers
    			return (float) (Math.sin(TeraMath.fastAbs(x)) * noise3.noise( x, y, z) );
    	 	case 0:
    		default:
    			return noise3.noise( x,y,z);	
    	}
    }
    
    @Override
    public float noise(float x, float y) {
        return noise2.noise( x,y); 
    }
}
