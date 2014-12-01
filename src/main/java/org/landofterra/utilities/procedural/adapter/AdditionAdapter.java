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
public class AdditionAdapter implements Noise3D,Noise2D {

    private Noise3D noise3;
    private Noise2D noise2;
    
    private float Addition;
    

    /**
     * 
     * @param noise
     * @param Addition
     */
    public AdditionAdapter(Noise3D noise,float Addition) {
        this.noise3 = noise;
        this.Addition=Addition;
    }
    
    /**
     * 
     * @param noise
     * @param Addition
     * @param b
     */
    public AdditionAdapter(Noise2D noise,float Addition,byte b) {
        this.noise2 = noise;
        this.Addition=Addition;
    }


    @Override
    public float noise(float x, float y,float z) {
    	return this.noise3.noise(x, y, z)+this.Addition;
    }
    
    @Override
    public float noise(float x, float y) {
		return this.noise2.noise(x, y)+this.Addition;
    }
  
}
