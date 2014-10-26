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
package org.landofterra.utilities.random;

import org.terasology.module.sandbox.API;

/**
 * Random number generator based on the Xorshift generator by George Marsaglia.
 *
 * @author Benjamin Glatzel <benjamin.glatzel@me.com>
 */
@API
public class BitScrampler {

    public BitScrampler() {
    }

    /**
     * Returns a random int value.
     *
     * @return Random value
     */
    public static int ScrampleInt(final int intIn) {
    	int in=intIn;
    	in++;
    	in ^= (in << 21);
    	in ^= (in >>> 35);
    	in ^= (in << 4);
    	return in;
    }
}
