
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

import org.landofterra.world.generation.facets.InfiniteGenFacet;
import org.terasology.math.Region3i;
import org.terasology.utilities.procedural.BrownianNoise3D;
import org.terasology.utilities.procedural.SimplexNoise;
import org.terasology.utilities.procedural.SubSampledNoise3D;
import org.terasology.world.generation.Facet;
import org.terasology.world.generation.FacetProvider;
import org.terasology.world.generation.GeneratingRegion;
import org.terasology.world.generation.Updates;

@Updates(@Facet(InfiniteGenFacet.class))
public class Simplex3DLayerProvider implements FacetProvider {

    private SubSampledNoise3D surfaceNoise;
    
    private Vector3f zoom;
    private int octaves;
    
	//TODO finish this
    private int origo;
    private int height;
    private int lampda;
    
    private double frequency;
    private double multificator;
    private int seedOffSet;

    public Simplex3DLayerProvider(){
    	/* some presets for zoom:
    	 * 0.0025f, 0.01f, 0.0025f floating planes zoom 10 or 8, and mult 2, freq 0.5 if wanted 
    	 */
    	zoom=new Vector3f(0.00085f, 0.0007f, 0.00085f);
    	octaves=8;
    	frequency=0;
    	multificator=1;
    	seedOffSet=0;
    }
    
    public Simplex3DLayerProvider(int seedOffSet,Vector3f zoom,int octaves,double frequency, double multificator){
    	this.zoom=zoom;
    	this.octaves=octaves;
    	this.frequency=frequency;
    	this.multificator=multificator;
    	this.seedOffSet=seedOffSet;
    }
    
    @Override
    public void setSeed(long seed) {
        surfaceNoise = new SubSampledNoise3D(new BrownianNoise3D(new SimplexNoise(seed+seedOffSet),octaves), zoom, 4);
    }
    
    @Override
    public void process(GeneratingRegion region) {
        InfiniteGenFacet facet =  region.getRegionFacet(InfiniteGenFacet.class);
        float[] noise = surfaceNoise.noise(facet.getWorldRegion());
        
        Region3i area = region.getRegion();
        int Y=area.minY();//real universal Y coordinate
       
        float[] orginalData = facet.getInternal();
        for(int i=0;orginalData.length>i;i++){
        	noise[i]*=multificator;
        	if(frequency!=0){
        		noise[i]=(float) (noise[i] %frequency);
        	}
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
		return frequency;
	}


	/**
	 * @param frequency the frequency to set
	 */
	public void setFrequency(double frequency) {
		this.frequency = frequency;
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