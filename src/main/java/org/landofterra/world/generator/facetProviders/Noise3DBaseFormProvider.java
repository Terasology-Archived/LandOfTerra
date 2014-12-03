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

import org.landofterra.world.generation.facets.FormFacet;
import org.terasology.utilities.procedural.Noise3D;
import org.terasology.world.generation.Border3D;
import org.terasology.world.generation.FacetProvider;
import org.terasology.world.generation.GeneratingRegion;
import org.terasology.world.generation.Produces;

/**
 * 
 * @author esereja
 */
@Produces(FormFacet.class)
public class Noise3DBaseFormProvider implements FacetProvider {

    private Noise3D noise;

    private float modulus;
    private float multifier;
    private float increase;

    /**
     * these values are used to determine where land forms end up,
     * create scale enough big for land form definitions. 
     * distance between definitions should be more than 100
     * @param noise
     * @param frequency
     * @param multificator
     * @param increase
     */
    public Noise3DBaseFormProvider(Noise3D noise,float frequency, float multificator,float increase){
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
    	Border3D border = region.getBorderForFacet(FormFacet.class);
    	FormFacet formFacet = new FormFacet(region.getRegion(), border);

        for(int x=formFacet.getRelativeRegion().minX();x<formFacet.getRelativeRegion().maxX()+1;x++)
        	for(int y=formFacet.getRelativeRegion().minY();y<formFacet.getRelativeRegion().maxY()+1;y++){
        		for(int z=formFacet.getRelativeRegion().minZ();z<formFacet.getRelativeRegion().maxZ()+1;z++){
        			
        			float n = noise.noise(x, y, z);
        			
        			n*=multifier;
                	if(modulus!=0){
                		n=(float) (n %modulus);
                	}
                	n+=increase;
                	
                	formFacet.set(x, y, z, n);

                	if(formFacet.getMax()<n){
                		formFacet.setMax(n);
                	}else if(formFacet.getMin()>n){
                		formFacet.setMin(n);
                	}
        		}	
        	}
        region.setRegionFacet(FormFacet.class, formFacet);
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