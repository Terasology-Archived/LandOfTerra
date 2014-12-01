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

import java.util.List;

import org.landofterra.utilities.math.Statistics;
import org.terasology.utilities.procedural.Noise2D;
import org.terasology.utilities.procedural.Noise3D;

/**
 * @author Esereja
 */
public class Noise2DTo3DAdapter implements Noise3D {

    private Noise2D noise2;
    private int mode;
    
    /***
     * 
     * @param noise
     */
    public Noise2DTo3DAdapter(Noise2D noise) {
        this.noise2 = noise;
        this.mode=1;
    }
    
    /***
     * 
     * @param noise
     */
    public Noise2DTo3DAdapter(Noise2D noise,int mode) {
        this.noise2 = noise;
        this.mode=mode;
    }


    @Override
    public float noise(float x, float y,float z) {
    	switch(this.mode){	
    		case 2:
				return this.noise2.noise(y, z);
			case 1:
    			return this.noise2.noise(x, z);
    		default:
    			return this.noise2.noise(x, y);		
    	}
    }
  
}
