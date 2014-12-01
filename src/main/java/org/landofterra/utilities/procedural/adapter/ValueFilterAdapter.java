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
 * @author Esereja
 */
public class ValueFilterAdapter implements Noise3D,Noise2D {

    private Noise3D noise3;
    private Noise2D noise2;
    
    private float start;
    private float stop; 
    private float value;

    /**
     * Takes 3d noise and fills given range whit given value 
     * @param noise
     * @param start
     * @param stop
     * @param value
     */
    public ValueFilterAdapter(Noise3D noise,float start,float stop,float value) {
    	this.noise3=noise;
    	this.start=start;
    	this.stop=stop;
    	this.value=value;
    }

    
    /***
     * Takes 2d noise and fills given range whit given value 
     * @param noise
     * @param start
     * @param stop
     * @param value
     * @param b this does nothing. it is just place holder
     */
    public ValueFilterAdapter(Noise2D noise,float start,float stop,float value, byte b) {
    	this.noise2=noise;
    	this.start=start;
    	this.stop=stop;
    	this.value=value;
    }


    @Override
    public float noise(float x, float y,float z) {
    	float t=noise3.noise(x, y, z);
    	if(t>=this.start && t<this.stop){
    		t=this.value;
    	}
        return t; 
    }
    
    @Override
    public float noise(float x, float y) {
    	float t=noise2.noise(x, y);
    	if(t>=this.start && t<this.stop){
    		t=this.value;
    	}
        return t; 
    }
}
