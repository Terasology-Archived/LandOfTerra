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
package org.landofterra.world.generator.facetProviders;

import javax.vecmath.Vector3f;

import org.landofterra.world.generation.facets.InfiniteGenFacet;
import org.terasology.utilities.procedural.PerlinNoise;
import org.terasology.world.generation.Facet;
import org.terasology.world.generation.FacetProvider;
import org.terasology.world.generation.Updates;

/**
 * 
 * @author esereja
 */
@Updates(@Facet(InfiniteGenFacet.class))
public class Perlin3DNoiseProvider extends Noise3DTerainProvider implements
		FacetProvider {

	private int seedOffset;

	/**
	 * 
	 * @param seedOffset
	 * @param zoom
	 * @param frequency
	 * @param multificator
	 * @param increase
	 */
	public Perlin3DNoiseProvider(int seedOffset, Vector3f zoom,
			double frequency, double multificator, double increase) {
		super(zoom, frequency, multificator, increase);
		this.seedOffset = seedOffset;
	}

	@Override
	public void setSeed(long seed) {
		this.setSurfaceNoise(new PerlinNoise(seed + seedOffset));
	}
}
