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

import org.boundlessworlds.utilities.procedural.adapter.MultiplicationAdapter;
import org.boundlessworlds.world.generation.facets.InfiniteGenFacet;
import org.boundlessworlds.world.generator.landFormDefinitions.LandFormDefinition;
import org.terasology.math.geom.Vector3f;
import org.terasology.utilities.procedural.BrownianNoise3D;
import org.terasology.utilities.procedural.Noise3D;
import org.terasology.utilities.procedural.PerlinNoise;
import org.terasology.utilities.procedural.SimplexNoise;
import org.terasology.utilities.procedural.SubSampledNoise3D;
import org.terasology.world.generation.Produces;

@Produces(InfiniteGenFacet.class)
public class DevilsToothFormDefinition extends LandFormDefinition implements Noise3D {
    
    /**
     * 
     * @param formValue
     */
	public DevilsToothFormDefinition(Long seed){
    	super(-900);
    	this.maxDensity=150f;
    	this.minDensity=-500f;
    	this.maxAltitude=Float.MAX_VALUE;
    	this.minAltitude=-2000f;
    	this.maxTemperature=Float.MAX_VALUE;
    	this.minTemperature=Float.MIN_VALUE;	
    	this.maxHumidity=Float.MAX_VALUE;
    	this.minHumidity=Float.MIN_VALUE;
    	
    	this.noiseList.add(new SubSampledNoise3D(new BrownianNoise3D(new SimplexNoise(seed),6),
    					new Vector3f(0.00080f, 0.0007f, 0.00080f),4)
    	);
    	this.noiseList.add(new SubSampledNoise3D(new MultiplicationAdapter(new BrownianNoise3D(new SimplexNoise(seed+1),10),1.2f),
    			new Vector3f(0.0025f, 0.01f, 0.0025f),4)
    	);
    	this.noiseList.add(new SubSampledNoise3D(new MultiplicationAdapter(new BrownianNoise3D(new PerlinNoise(seed+2),9),0.8f),
    			new Vector3f(0.00085f, 0.0007f, 0.00085f),4)
    	);
    	this.noiseList.add(new SubSampledNoise3D(new MultiplicationAdapter(new PerlinNoise(seed+3),1.5f),
    			new Vector3f(0.0042f, 0.0042f, 0.0042f),4)
    	);
    	this.noiseList.add(new SubSampledNoise3D(new MultiplicationAdapter(new PerlinNoise(seed+4),0.8f),
    			new Vector3f(0.0015f, 0.0010f, 0.0015f),4)
    	);
    }
    
}