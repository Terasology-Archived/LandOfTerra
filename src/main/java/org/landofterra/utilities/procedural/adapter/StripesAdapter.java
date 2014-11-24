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
public class StripesAdapter implements Noise3D,Noise2D {

    private Noise3D noise3;
    private Noise2D noise2;
    
    private double freq;

    /***
     * takes in 3d noise and creates turbulence whit in this noise.
     * @param noise
     * @param width
     * @param freq
     */
    public StripesAdapter(Noise3D noise,double freq) {
        this.noise3 = noise;
        this.freq=freq;
    }

    
    /***
     * takes in 2d noise and creates turbulence whit in this noise.
     * @param noise
     * @param width
     * @param freq
     * @param b this does nothing. it is just place holder
     */
    public StripesAdapter(Noise2D noise,double freq, byte b) {
        this.noise2 = noise;
        this.freq=freq;
    }


    @Override
    public float noise(float x, float y,float z) {
        return noise3.noise( x+ stripes(x,this.freq), y+ stripes(y,this.freq), z+ stripes(z,this.freq)); 
    }
    
    @Override
    public float noise(float x, float y) {
        return noise2.noise( x+stripes(x,this.freq), y+stripes(y,this.freq));
    }
    
    float stripes(double x, double f) {
    	  double t = .5 + .5 * Math.sin(f * 2*TeraMath.PI * x);
    	  return (float)(t * t - .5);
    }
}
