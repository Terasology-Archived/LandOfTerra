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
import org.boundlessworlds.utilities.procedural.adapter.MultiplicationAdapter;
import org.boundlessworlds.world.generation.facets.InfiniteGenFacet;
import org.boundlessworlds.world.generator.landFormDefinitions.LandFormDefinition;
import org.terasology.math.geom.Vector3f;
import org.terasology.utilities.procedural.BrownianNoise3D;
import org.terasology.utilities.procedural.Noise3D;
import org.terasology.utilities.procedural.SimplexNoise;
import org.terasology.utilities.procedural.SubSampledNoise3D;
import org.terasology.world.generation.Produces;

@Produces(InfiniteGenFacet.class)
public class Underground3Definition extends LandFormDefinition implements Noise3D {
    
    /**
     * 
     * @param formValue
     */
	public Underground3Definition(Long seed){
    	super(0);
    	this.maxDensity=800f;
    	this.minDensity=60f;
    	this.maxAltitude=200f;
    	this.minAltitude=-500f;
    	this.maxTemperature=Float.MAX_VALUE;
    	this.minTemperature=Float.MIN_VALUE;	
    	this.maxHumidity=Float.MAX_VALUE;
    	this.minHumidity=Float.MIN_VALUE;
    	
    	this.setScoreOffset(-8f);
    	
    	this.noiseList.add(new SubSampledNoise3D(new MultiplicationAdapter(new AdditionAdapter(new BrownianNoise3D(new SimplexNoise(seed),3),0.6f),2f),
				new Vector3f(0.0001f, 0.002f, 0.0001f),4
		));
    	
    	this.noiseList.add(new SubSampledNoise3D(new BrownianNoise3D(new SimplexNoise(seed),5),
				new Vector3f(0.005f, 0.0005f, 0.005f),4
		));
    }
    
}