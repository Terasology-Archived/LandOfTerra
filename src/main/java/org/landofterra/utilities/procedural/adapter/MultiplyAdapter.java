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
public class MultiplyAdapter implements Noise3D,Noise2D {

    private Noise3D noise31;
    private Noise2D noise21;
    
    private Noise3D noise32;
    private Noise2D noise22;
    

    /** 
     * 
     * @param noise1
     * @param noise2
     */
    public MultiplyAdapter(Noise3D noise1,Noise3D noise2) {
        this.noise31 = noise1;
        this.noise32 = noise2;
    }
    
    /**
     * 
     * @param noise1
     * @param noise2
     * @param b
     */
    public MultiplyAdapter(Noise2D noise1,Noise2D noise2,byte b) {
        this.noise21 = noise1;
        this.noise22 = noise2;
    }


    @Override
    public float noise(float x, float y,float z) {
    	return this.noise31.noise(x, y, z)*this.noise32.noise(x, y, z);
    }
    
    @Override
    public float noise(float x, float y) {
		return this.noise21.noise(x, y)*this.noise22.noise(x, y);
    }
  
}
