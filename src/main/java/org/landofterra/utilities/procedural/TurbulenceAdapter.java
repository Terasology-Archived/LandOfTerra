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

import org.terasology.math.TeraMath;
import org.terasology.utilities.procedural.Noise2D;
import org.terasology.utilities.procedural.Noise3D;

/**
 * @author Esereja
 */
public class TurbulenceAdapter implements Noise3D,Noise2D {

    private Noise3D noise3;
    private Noise2D noise2;
    
    private double freq;
    private double offSet=-0.5;
    private float width;
    

    /***
     * takes in 3d noise and creates turbulence whit in this noise.
     * @param noise
     * @param width
     * @param freq
     */
    public TurbulenceAdapter(Noise3D noise,float width,double freq,double offSet) {
        this.noise3 = noise;
        this.freq=freq;
        this.width=width;
        this.offSet = offSet;
    }
    
    /***
     * takes in 2d noise and creates turbulence whit in this noise.
     * @param noise
     * @param width
     * @param freq
     * @param b this does nothing. it is just place holder
     */
    public TurbulenceAdapter(Noise2D noise,float width,double freq,double offSet, byte b) {
        this.noise2 = noise;
        this.freq=freq;
        this.width=width;
        this.offSet = offSet;
    }


    @Override
    public float noise(float x, float y,float z) {
    	double t= this.offSet;
        for (double f=freq; f <= width/12 ; f *= 2)
            t += TeraMath.fastAbs(noise3.noise( (float)(x*(f/4)),(float)(y*(f/4)),(float)(z*(f/4)) ) / f);
        return (float) t;
    }
    
    @Override
    public float noise(float x, float y) {
    	double t= this.offSet;
        for (double f=freq; f <= width/12 ; f *= 2)
            t += TeraMath.fastAbs(noise2.noise((float)(x*(f/3)),(float)(y*(f/3))) / f);
        return (float) t; 
    }
  
}
