/*
 * Copyright 2013 MovingBlocks
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
package org.landofterra.utilities.procedural.noise;

import org.landofterra.utilities.math.Statistics;
import org.landofterra.utilities.random.BitScrampler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.terasology.math.TeraMath;
import org.terasology.utilities.procedural.Noise2D;
import org.terasology.utilities.procedural.Noise3D;
import org.terasology.utilities.random.FastRandom;

/**
 * 
 * @author Esereja
 */
public class NullNoise implements Noise2D, Noise3D {

	private float value;
	
    /**
     * Initialize permutations with a given seed
     *
     * @param seed a seed value used for permutation shuffling
     */
    public NullNoise(float value) {
       this.value=value;
    }


    /**
     * 2D semi white noise
     *
     * @param xin the x input coordinate
     * @param yin the y input coordinate
     * @return 
     */
    @Override
    public float noise(float xin, float yin) {
        return this.value;
    	} 
    /**
     * 
     *
     * @param xin the x input coordinate
     * @param yin the y input coordinate
     * @param zin the z input coordinate
     * @return 
     */
    @Override
    public float noise(float xin, float yin, float zin) {
    	return this.value;
	} 


    /**
     * 
     *
     * @param xin the x input coordinate
     * @param yin the y input coordinate
     * @param zin the z input coordinate
     * @return 
     */
    public float noise(float xin, float yin, float zin, float win) {
    	return this.value;
	} 
}
