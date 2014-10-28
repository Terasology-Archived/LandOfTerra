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
public class StripedAdapter implements Noise3D,Noise2D {

    private Noise3D noise3;
    private Noise2D noise2;
    
    private double freq2;

    /***
     * takes in 3d noise and creates turbulence whit in this noise.
     * @param noise
     * @param width
     * @param freq
     */
    public StripedAdapter(Noise3D noise,float width,double freq,double offSet,double freq2) {
        this.noise3 = new TurbulenceAdapter(noise,width,width,offSet);
        this.freq2=freq2;
    }

    
    /***
     * takes in 2d noise and creates turbulence whit in this noise.
     * @param noise
     * @param width
     * @param freq
     * @param b this does nothing. it is just place holder
     */
    public StripedAdapter(Noise2D noise,float width,double freq,double offSet,double freq2, byte b) {
        this.noise2 = new TurbulenceAdapter(noise,width,width,offSet,b);
        this.freq2=freq2;
    }


    @Override
    public float noise(float x, float y,float z) {
        return (float) (0.01 * stripes(x + 2 * noise3.noise(x, y, z), freq2)); 
    }
    
    @Override
    public float noise(float x, float y) {
        return (float) (0.01 * stripes(x + 2 * noise2.noise(x, y), freq2));  
    }
    
    double stripes(double x, double f) {
    	  double t = .5 + .5 * Math.sin(f * 2*TeraMath.PI * x);
    	  return t * t - .5;
    }
}
