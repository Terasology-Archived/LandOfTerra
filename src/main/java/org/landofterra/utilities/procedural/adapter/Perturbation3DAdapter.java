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

import org.terasology.utilities.procedural.Noise3D;

/**
 * @author Esereja
 */
public class Perturbation3DAdapter implements Noise3D {

    private Noise3D noise;
    private Noise3D noiseX;
    private Noise3D noiseY;
    private Noise3D noiseZ;

    public Perturbation3DAdapter(Noise3D noise, Noise3D noiseX, Noise3D noiseY, Noise3D noiseZ) {
        this.noise = noise;
        this.noiseX=noiseX;
        this.noiseY=noiseY;
        this.noiseZ=noiseZ;
    }

    @Override
    public float noise(float x, float y,float z) {
        return noise.noise(x+noiseX.noise(x, y, z), y+noiseY.noise(x, y, z), z+noiseZ.noise(x, y, z));
    }
}
