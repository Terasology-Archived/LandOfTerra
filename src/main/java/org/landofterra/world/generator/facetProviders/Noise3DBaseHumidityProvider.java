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

import org.landofterra.world.generation.facets.HumidityFacet;
import org.terasology.utilities.procedural.Noise3D;
import org.terasology.world.generation.Border3D;
import org.terasology.world.generation.FacetProvider;
import org.terasology.world.generation.GeneratingRegion;
import org.terasology.world.generation.Produces;

/**
 * 
 * @author esereja
 */
@Produces(HumidityFacet.class)
public class Noise3DBaseHumidityProvider implements FacetProvider {

    private Noise3D noise;

    private float modulus;
    private float multifier;
    private float increase;

    /**
     * humidity values are in between 0-1
     * @param noise
     * @param frequency
     * @param multificator
     * @param increase
     */
    public Noise3DBaseHumidityProvider(Noise3D noise,float frequency, float multificator,float increase){
    	this.noise = noise;
    	
    	this.modulus=frequency;
    	this.multifier=multificator;
    	this.increase=increase;
    }
    
    @Override
    public void setSeed(long seed) {
    }
    
    @Override
    public void process(GeneratingRegion region) {
    	Border3D border = region.getBorderForFacet(HumidityFacet.class);
    	HumidityFacet humFacet = new HumidityFacet(region.getRegion(), border);
        //Region3i processRegion = facet.getWorldRegion();

        for(int x=humFacet.getRelativeRegion().minX();x<humFacet.getRelativeRegion().maxX()+1;x++)
        	for(int y=humFacet.getRelativeRegion().minY();y<humFacet.getRelativeRegion().maxY()+1;y++){
        		for(int z=humFacet.getRelativeRegion().minZ();z<humFacet.getRelativeRegion().maxZ()+1;z++){
        			
        			float n = (noise.noise(x, y, z)+1)/2;
        			
        			n*=multifier;
                	if(modulus!=0){
                		n=(float) (n %modulus);
                	}
                	n+=increase;
                	humFacet.set(x, y, z, n);
                	
                	if(humFacet.getMax()<n){
                		humFacet.setMax(n);
                	}else if(humFacet.getMin()>n){
                		humFacet.setMin(n);
                	}
        		}	
        	}
        
        region.setRegionFacet(HumidityFacet.class, humFacet);
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
	public void setIncrease(float increase) {
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
	public void setFrequency(float frequency) {
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
	public void setMultificator(float multificator) {
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