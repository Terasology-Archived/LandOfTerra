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

import org.terasology.utilities.procedural.SimplexNoise;
import org.terasology.utilities.procedural.SubSampledNoise3D;
import org.terasology.world.generation.Facet;
import org.terasology.world.generation.FacetProvider;
import org.terasology.world.generation.GeneratingRegion;
import org.terasology.world.generation.Updates;
import org.terasology.world.generation.facets.InfiniteGenFacet;

@Updates(@Facet(InfiniteGenFacet.class))
public class Simplex3DNoiseProvider implements FacetProvider {

    private SubSampledNoise3D surfaceNoise;
    
    private Vector3f zoom;
    private int seedOffSet;
    
    private double modulus;
    private double multificator;
    private double increase;
    
	public Simplex3DNoiseProvider(){
    	zoom=new Vector3f(0.1f, 0.1f, 0.1f);
    	seedOffSet=0;
    	
    	modulus=0;
    	multificator=1;
    	increase=0;
    }
    
    public Simplex3DNoiseProvider(int seedOffSet,Vector3f zoom,double modulus,double multificator,double increase){
    	this.zoom=zoom;
    	this.seedOffSet=seedOffSet;
    	
    	this.modulus=modulus;
    	this.multificator=multificator;
    	this.increase=increase;
    }
    
	@Override
    public void setSeed(long seed) {
        surfaceNoise = new SubSampledNoise3D(new SimplexNoise(seed+seedOffSet), zoom, 4);
    }
	

	@Override
    public void process(GeneratingRegion region) {
        InfiniteGenFacet facet =  region.getRegionFacet(InfiniteGenFacet.class);
        float[] noise = surfaceNoise.noise(facet.getWorldRegion());
       
        float[] orginalData = facet.getInternal();
        for(int i=0;orginalData.length>i;i++){
        	noise[i]*=multificator;
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
	 * @return the multificator
	 */
	public double getMultificator() {
		return multificator;
	}

	/**
	 * @param multificator the multificator to set
	 */
	public void setMultificator(double multificator) {
		this.multificator = multificator;
	}

    /**
	 * @return the modulus
	 */
	public double getModulus() {
		return modulus;
	}

	/**
	 * @param modulus the modulus to set
	 */
	public void setModulus(double modulus) {
		this.modulus = modulus;
	}

	/**
	 * @return the increase
	 */
	public double getIncrease() {
		return increase;
	}

	/**
	 * @param increase the increase to set
	 */
	public void setIncrease(double increase) {
		this.increase = increase;
	}

	/**
	 * @return the seedOffSet
	 */
	public int getSeedOffSet() {
		return seedOffSet;
	}

	/**
	 * @param seedOffSet the seedOffSet to set
	 */
	public void setSeedOffSet(int seedOffSet) {
		this.seedOffSet = seedOffSet;
	}

}