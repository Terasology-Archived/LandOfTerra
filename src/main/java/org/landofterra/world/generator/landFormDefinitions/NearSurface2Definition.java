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
package org.landofterra.world.generator.landFormDefinitions;

import org.boundlessworlds.utilities.procedural.adapter.AdditionAdapter;
import org.boundlessworlds.world.generation.facets.InfiniteGenFacet;
import org.boundlessworlds.world.generator.landFormDefinitions.LandFormDefinition;
import org.terasology.math.geom.Vector3f;
import org.terasology.utilities.procedural.BrownianNoise3D;
import org.terasology.utilities.procedural.Noise3D;
import org.terasology.utilities.procedural.SimplexNoise;
import org.terasology.utilities.procedural.SubSampledNoise3D;
import org.terasology.world.generation.Produces;

@Produces(InfiniteGenFacet.class)
public class NearSurface2Definition extends LandFormDefinition implements Noise3D {
    
    /**
     * 
     * @param formValue
     */
	public NearSurface2Definition(Long seed){
    	super(0);
    	this.maxDensity=80f;
    	this.minDensity=-10f;
    	this.maxAltitude=Float.MAX_VALUE;
    	this.minAltitude=Float.MIN_VALUE;
    	this.maxTemperature=Float.MAX_VALUE;
    	this.minTemperature=Float.MIN_VALUE;	
    	this.maxHumidity=Float.MAX_VALUE;
    	this.minHumidity=Float.MIN_VALUE;
    	
    	this.setScoreOffset(-260f);
    	
    	this.noiseList.add(new SubSampledNoise3D(new AdditionAdapter(new BrownianNoise3D(new SimplexNoise(seed),3),0.85f),
				new Vector3f(0.0002f, 0.0002f, 0.0002f),4
		));
    }
    
}