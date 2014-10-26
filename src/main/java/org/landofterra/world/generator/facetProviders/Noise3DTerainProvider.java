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

import org.terasology.utilities.procedural.Noise3D;
import org.terasology.utilities.procedural.SubSampledNoise3D;
import org.terasology.world.generation.Facet;
import org.terasology.world.generation.FacetProvider;
import org.terasology.world.generation.GeneratingRegion;
import org.terasology.world.generation.Updates;
import org.terasology.world.generation.facets.InfiniteGenFacet;

@Updates(@Facet(InfiniteGenFacet.class))
public class Noise3DTerainProvider implements FacetProvider {

    private SubSampledNoise3D surfaceNoise;
    private Noise3D noise;
    
    private Vector3f zoom;
    
    private double modulus;
    private double multifier;
    private double increase;
    
    public Noise3DTerainProvider(Noise3D noise,Vector3f zoom,double frequency, double multificator,double increase){
    	this.zoom=zoom;
    	this.modulus=frequency;
    	this.multifier=multificator;
    	this.noise=noise;
    	this.increase=increase;
    }
    
    @Override
    public void setSeed(long seed) {
        surfaceNoise = new SubSampledNoise3D(noise, zoom, 4);
    }
    
    @Override
    public void process(GeneratingRegion region) {
        InfiniteGenFacet facet =  region.getRegionFacet(InfiniteGenFacet.class);
        float[] noise = surfaceNoise.noise(facet.getWorldRegion());
       
        float[] orginalData = facet.getInternal();
        for(int i=0;orginalData.length>i;i++){
        	noise[i]*=multifier;
        	if(modulus!=0){
        		noise[i]=(float) (noise[i] %modulus);
        	}
        	noise[i]+=increase;
        	orginalData[i]+=noise[i];
        }
    }


	/**
	 * @return the zoom
	 */
	public Vector3f getZoom() {
		return zoom;
	}


	/**
	 * @param zoom the zoom to set
	 */
	public void setZoom(Vector3f zoom) {
		this.zoom = zoom;
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
	 * @return the noise
	 */
	public Noise3D getNoise() {
		return noise;
	}

	/**
	 * @param noise the noise to set
	 */
	public void setNoise(Noise3D noise) {
		this.noise = noise;
	}
}