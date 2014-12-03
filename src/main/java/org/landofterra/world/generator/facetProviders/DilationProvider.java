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

import org.landofterra.utilities.math.Statistics;
import org.landofterra.world.generation.facets.InfiniteGenFacet;
import org.terasology.math.Region3i;
import org.terasology.math.Vector3i;
import org.terasology.world.generation.Border3D;
import org.terasology.world.generation.Facet;
import org.terasology.world.generation.FacetProvider;
import org.terasology.world.generation.GeneratingRegion;
import org.terasology.world.generation.Updates;

/**
 * 
 * @author Isäntä
 *
 */
@Updates(@Facet(InfiniteGenFacet.class))
public class DilationProvider implements FacetProvider {
	//FIXME this goes out of boundaries
    private int kernelSize;
	
	/**
	 * 
	 * @param kernelSize
	 */
	public DilationProvider(int kernelSize){
		this.kernelSize=kernelSize;
	}

	
    @Override
    public void setSeed(long seed) {
    }
    
	@Override
    public void process(GeneratingRegion region) {    	
		Border3D border = region.getBorderForFacet(InfiniteGenFacet.class);
    	InfiniteGenFacet facet = region.getRegionFacet(InfiniteGenFacet.class);
    	

        Region3i area = region.getRegion();
        int Y=area.minY();//real universal Y coordinate
        
        for (int y = facet.getRelativeRegion().minY(); y <= facet.getRelativeRegion().maxY(); ++y) {
        	for (int x = facet.getRelativeRegion().minX(); x <= facet.getRelativeRegion().maxX(); ++x) {
        		for (int z = facet.getRelativeRegion().minZ(); z <= facet.getRelativeRegion().maxZ(); ++z) {
        			
        	    	int blocks=1;
        	    	for(int a=0;a<this.kernelSize;a++)
        	    		for(int b=0;b<this.kernelSize;b++)
        	    			for(int c=0;c<this.kernelSize;c++)
        	    				blocks++;
        	    	float[] array=new float[blocks];
        	    	int i=0;
        	    	for(int a=0;a<this.kernelSize;a++){
        	    		for(int b=0;b<this.kernelSize;b++){
        	    			for(int c=0;c<this.kernelSize;c++){
        	    				array[i]= facet.get(new Vector3i(x+a, y+b, z+c));
        	    				i++;
        	    				}
        	    			}
        	    		}
        	    	float t=Statistics.max(array);
        	        facet.set(new Vector3i(x, y, z),t-t/this.kernelSize);
        		}
        	}
        }
	}
	
}