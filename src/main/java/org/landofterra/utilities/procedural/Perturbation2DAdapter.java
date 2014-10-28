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

/**
 * @author Esereja
 */
public class Perturbation2DAdapter implements Noise2D {

    private Noise2D noise;
    private Noise2D noiseX;
    private Noise2D noiseY;

    public Perturbation2DAdapter(Noise2D noise,Noise2D noiseX,Noise2D noiseY) {
        this.noise = noise;
        this.noiseX=noiseX;
        this.noiseY=noiseY;
    }

    @Override
    public float noise(float x, float y) {
        return noise.noise(x+noiseX.noise(x, y), y+noiseY.noise(x, y));
    }
}
