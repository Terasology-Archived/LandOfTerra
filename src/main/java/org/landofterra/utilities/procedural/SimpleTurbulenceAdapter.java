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

    /***
     * takes in 3d noise and creates turbulence whit this noise. seed is static so result
     * isn't that strong as whit variable seeds. but it is much cheaper computationally.
     * @param noise 3d noise 
     */
    public SimpleTurbulenceAdapter(Noise3D noise) {
        this.noise3 = noise;
    }
    
    /***
     * takes in 2d noise and creates turbulence whit this noise. seed is static so result
     * isn't that strong as whit variable seeds. but it is much cheaper computationally.
     * @param noise 2d noise 
     */
    public SimpleTurbulenceAdapter(Noise2D noise) {
        this.noise2 = noise;
    }

    @Override
    public float noise(float x, float y,float z) {
        return noise3.noise(x+noise3.noise(x, y, z), y+noise3.noise(x, y, z), z+noise3.noise(x, y, z));
    }
    
    @Override
    public float noise(float x, float y) {
        return noise2.noise(x+noise2.noise(x, y), y+noise2.noise(x, y));
    }
}
