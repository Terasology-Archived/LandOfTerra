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

import org.landofterra.utilities.procedural.WhiteNoise;
import org.landofterra.world.generation.facets.InfiniteGenFacet;
import org.terasology.math.Region3i;
import org.terasology.utilities.procedural.BrownianNoise3D;
import org.terasology.utilities.procedural.SubSampledNoise3D;
import org.terasology.world.generation.Border3D;
import org.terasology.world.generation.Facet;
import org.terasology.world.generation.FacetProvider;
import org.terasology.world.generation.GeneratingRegion;
import org.terasology.world.generation.Updates;

@Updates(@Facet(InfiniteGenFacet.class))
public class White3DTerainProvider implements FacetProvider {

    private SubSampledNoise3D surfaceNoise;
    
    private Vector3f zoom;
    private int octaves;
    private int seedOffSet;
    
    private double modulus;
    private double multificator;
    private double increase;
    
    public White3DTerainProvider(){
    	/* some presets for zoom:
    	 * 0.00085f, 0.0007f, 0.00085f big and mighty mountains, add freg 0.5 and mult 2 to get some character
    	 * 0.0025f, 0.01f, 0.0025f plate mountains
    	 */
    	zoom=new Vector3f(0.0025f, 0.01f, 0.0025f);
    	octaves=10;
    	seedOffSet=0;
    	
    	modulus=0;
    	multificator=1;
    	increase=0;
    }
    
    public White3DTerainProvider(int seedOffSet,Vector3f zoom,int octaves,double modulus, double multificator,double increase){
    	this.zoom=zoom;
    	this.octaves=octaves;
    	this.seedOffSet=seedOffSet;
    	
    	this.modulus=modulus;
    	this.multificator=multificator;
    	this.increase=increase;
    }
    
    @Override
    public void setSeed(long seed) {
        surfaceNoise = new SubSampledNoise3D(new BrownianNoise3D(new WhiteNoise(seed+seedOffSet),octaves), zoom, 4);
    }
    
    @Override
    public void process(GeneratingRegion region) {
        Border3D border = region.getBorderForFacet(InfiniteGenFacet.class);
        InfiniteGenFacet facet = new InfiniteGenFacet(region.getRegion(), border);
        Region3i processRegion = facet.getWorldRegion();
        float[] noise = surfaceNoise.noise(processRegion);
        for(int i=0;noise.length>i;i++){
        	noise[i]*=multificator;
        	if(modulus!=0){
        		noise[i]=(float) (noise[i] %modulus);
        	}
        	noise[i]+=increase;
        }
        facet.set(noise);
        region.setRegionFacet(InfiniteGenFacet.class, facet);
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
	 * @return the octaves
	 */
	public int getOctaves() {
		return octaves;
	}

	/**
	 * @param octaves the octaves to set
	 */
	public void setOctaves(int octaves) {
		this.octaves = octaves;
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
		return multificator;
	}

	/**
	 * @param multificator the multificator to set
	 */
	public void setMultificator(double multificator) {
		this.multificator = multificator;
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