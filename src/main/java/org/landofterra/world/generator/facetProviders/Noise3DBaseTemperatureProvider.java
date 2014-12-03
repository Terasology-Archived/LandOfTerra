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

import org.landofterra.world.generation.facets.TemperatureFacet;
import org.terasology.utilities.procedural.Noise3D;
import org.terasology.world.generation.Border3D;
import org.terasology.world.generation.FacetProvider;
import org.terasology.world.generation.GeneratingRegion;
import org.terasology.world.generation.Produces;

/**
 * 
 * @author esereja
 */
@Produces(TemperatureFacet.class)
public class Noise3DBaseTemperatureProvider implements FacetProvider {

    private Noise3D noise;

    private double modulus;
    private double multifier;
    private double increase;

    /**
     * temperature noise values are meant to be in celcius
     * @param noise
     * @param frequency
     * @param multificator
     * @param increase
     */
    public Noise3DBaseTemperatureProvider(Noise3D noise,double frequency, double multificator,double increase){
    	this.modulus=frequency;
    	this.multifier=multificator;
    	this.increase=increase;
    	this.noise = noise;
    }
    
    @Override
    public void setSeed(long seed) {
    }
    
    @Override
    public void process(GeneratingRegion region) {
    	Border3D border = region.getBorderForFacet(TemperatureFacet.class);
    	TemperatureFacet tempFacet = new TemperatureFacet(region.getRegion(), border);
        //Region3i processRegion = facet.getWorldRegion();

        for(int x=tempFacet.getRelativeRegion().minX();x<tempFacet.getRelativeRegion().maxX()+1;x++)
        	for(int y=tempFacet.getRelativeRegion().minY();y<tempFacet.getRelativeRegion().maxY()+1;y++){
        		for(int z=tempFacet.getRelativeRegion().minZ();z<tempFacet.getRelativeRegion().maxZ()+1;z++){
        			
        			float n = noise.noise(x, y, z);
        			
        			n*=multifier;
                	if(modulus!=0){
                		n=(float) (n %modulus);
                	}
                	n+=increase;
                	tempFacet.set(x, y, z, n);
                	
                	if(tempFacet.getMax()<n){
                		tempFacet.setMax(n);
                	}else if(tempFacet.getMin()>n){
                		tempFacet.setMin(n);
                	}
        		}	
        	}
        
        region.setRegionFacet(TemperatureFacet.class, tempFacet);
    }

	/**
	 * 
	 * @return
	 */
	public double getIncrease() {
		return increase;
	}

	/**
	 * 
	 * @param increase
	 */
	public void setIncrease(double increase) {
		this.increase = increase;
	}

	/**
	 * @return the frequency
	 */
	public double getFrequency() {
		return modulus;
	}


	/**
	 * @param frequency the frequency to set
	 */
	public void setFrequency(double frequency) {
		this.modulus = frequency;
	}


	/**
	 * @return the multificator
	 */
	public double getMultificator() {
		return multifier;
	}


	/**
	 * @param multificator the multificator to set
	 */
	public void setMultificator(double multificator) {
		this.multifier = multificator;
	}

	/**
	 * 
	 * @return
	 */
	public Noise3D getNoise() {
		return noise;
	}

	/**
	 * 
	 * @param noise
	 */
	public void setNoise(Noise3D noise) {
		this.noise = noise;
	}
}
