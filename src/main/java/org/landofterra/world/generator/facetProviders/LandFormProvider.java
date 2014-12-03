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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.landofterra.utilities.math.Statistics;
import org.landofterra.world.generation.facets.FormFacet;
import org.landofterra.world.generation.facets.HumidityFacet;
import org.landofterra.world.generation.facets.InfiniteGenFacet;
import org.landofterra.world.generation.facets.TemperatureFacet;
import org.landofterra.world.generator.landFormDefinitions.LandFormDefinition;
import org.terasology.world.generation.Facet;
import org.terasology.world.generation.FacetProvider;
import org.terasology.world.generation.GeneratingRegion;
import org.terasology.world.generation.Requires;
import org.terasology.world.generation.Updates;

@Requires({@Facet(FormFacet.class), @Facet(TemperatureFacet.class), @Facet(HumidityFacet.class)} )
@Updates(@Facet(InfiniteGenFacet.class))
public class LandFormProvider implements FacetProvider {
	
	protected List<LandFormDefinition> noiseList; 
	
    protected float modulus;
    protected float multifier;
    protected float increase;

    
    /**
     * 
     * @param frequency
     * @param multificator
     * @param increase
     */
    public LandFormProvider(float frequency, float multificator,float increase){
    	this.noiseList = new ArrayList<LandFormDefinition>();
    	
    	this.modulus=frequency;
    	this.multifier=multificator;
    	this.increase=increase;
    }
    
    /**
     * 
     * @param noiseList
     * @param frequency
     * @param multificator
     * @param increase
     */
    public LandFormProvider( ArrayList<LandFormDefinition> noiseList,float frequency, float multificator,float increase){
    	this.noiseList = noiseList;
    	
    	this.modulus=frequency;
    	this.multifier=multificator;
    	this.increase=increase;
    }
    
    
    @Override
    public void setSeed(long seed) {
    }
    
    @Override
    public void process(GeneratingRegion region) {
        InfiniteGenFacet denFacet =  region.getRegionFacet(InfiniteGenFacet.class);
        TemperatureFacet tempFacet =  region.getRegionFacet(TemperatureFacet.class);
        HumidityFacet humFacet =  region.getRegionFacet(HumidityFacet.class);
        FormFacet formFacet =  region.getRegionFacet(FormFacet.class);

        if(this.noiseList.size()<1)	
        	return;
        
        for(int x=denFacet.getRelativeRegion().minX();x<denFacet.getRelativeRegion().maxX()+1;x++)
        	for(int y=denFacet.getRelativeRegion().minY();y<denFacet.getRelativeRegion().maxY()+1;y++){
        		for(int z=denFacet.getRelativeRegion().minZ();z<denFacet.getRelativeRegion().maxZ()+1;z++){
        			
        			float orginal=denFacet.get(x, y, z);
        			float form=formFacet.get(x, y, z);
        			float humidity=humFacet.get(x, y, z);
        			float temperature=tempFacet.get(x, y, z);
        			float n = 0;
        			int i=0;
        			
        			float[] scores= new float[this.noiseList.size()];
        			while(i<this.noiseList.size()){
        				scores[i]=this.noiseList.get(i).getScore(y, form, orginal, humidity, temperature);
        				i++;
        			}
        			
        			//arrange scores 
        			float[] t=scores;
        			Arrays.sort(t);
        			
        			//find best
        			i=Statistics.find(scores,t[this.noiseList.size()-1]);
        			n+=(this.noiseList.get(i).noise(x, y, z))/2;
        			
        			//find second best
        			if(this.noiseList.size()>1){
        				i=Statistics.find(scores,t[this.noiseList.size()-2]);
        				n+=(this.noiseList.get(i).noise(x, y, z))/2;
        			}
        			
        			
        			n*=multifier;
                	if(modulus!=0){
                		n=(float) (n %modulus);
                	}
                	n+=increase;
        			denFacet.set(x, y, z, orginal+n);
        			
                	if(denFacet.getMax()<n){
                		denFacet.setMax(n);
                	}else if(denFacet.getMin()>n){
                		denFacet.setMin(n);
                	}
        		}	
        	}
    }
    
    public void addNoise(LandFormDefinition noise){
    	this.noiseList.add(noise);
    } 

	/**
	 * @return the modulus
	 */
	public float getModulus() {
		return modulus;
	}

	/**
	 * @param modulus the modulus to set
	 */
	public void setModulus(float modulus) {
		this.modulus = modulus;
	}

	/**
	 * @return the multifier
	 */
	public float getMultifier() {
		return multifier;
	}

	/**
	 * @param multifier the multifier to set
	 */
	public void setMultifier(float multifier) {
		this.multifier = multifier;
	}

	/**
	 * @return the increase
	 */
	public float getIncrease() {
		return increase;
	}

	/**
	 * @param increase the increase to set
	 */
	public void setIncrease(float increase) {
		this.increase = increase;
	}
}