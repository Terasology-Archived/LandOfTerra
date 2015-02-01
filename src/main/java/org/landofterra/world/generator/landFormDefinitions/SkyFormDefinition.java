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
import org.boundlessworlds.world.generator.landFormDefinitions.LandFormDefinition;
import org.terasology.math.geom.Vector3f;
import org.terasology.utilities.procedural.Noise3D;
import org.terasology.utilities.procedural.SimplexNoise;
import org.terasology.utilities.procedural.SubSampledNoise3D;

public class SkyFormDefinition extends LandFormDefinition implements Noise3D {
    
    /**
     * 
     * @param formValue
     */
	public SkyFormDefinition(Long seed){
    	super(0);
    	this.maxDensity=-150f;
    	this.minDensity=-800f;
    	this.maxAltitude=Float.MAX_VALUE;
    	this.minAltitude=-100f;
    	this.maxTemperature=Float.MAX_VALUE;
    	this.minTemperature=Float.MIN_VALUE;	
    	this.maxHumidity=Float.MAX_VALUE;
    	this.minHumidity=Float.MIN_VALUE;
    	
    	this.setScoreOffset(-600f);
    	
    	this.noiseList.add(new SubSampledNoise3D(new AdditionAdapter(new SimplexNoise(seed),-0.9f),
				new Vector3f(0.01f, 0.01f, 0.01f),4));
    }
    
}