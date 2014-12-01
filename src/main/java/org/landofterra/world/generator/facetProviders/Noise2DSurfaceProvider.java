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

import org.landofterra.utilities.procedural.adapter.AdditionAdapter;
import org.landofterra.utilities.procedural.adapter.AreaLimitAdapter;
import org.landofterra.utilities.procedural.adapter.Noise2DTo3DAdapter;
import org.landofterra.world.generation.facets.InfiniteGenFacet;
import org.terasology.math.Region3i;
import org.terasology.utilities.procedural.BrownianNoise3D;
import org.terasology.utilities.procedural.Noise2D;
import org.terasology.utilities.procedural.Noise3D;
import org.terasology.utilities.procedural.SimplexNoise;
import org.terasology.utilities.procedural.SubSampledNoise3D;
import org.terasology.world.generation.Facet;
import org.terasology.world.generation.FacetProvider;
import org.terasology.world.generation.GeneratingRegion;
import org.terasology.world.generation.Updates;

import com.google.gson.internal.bind.ReflectiveTypeAdapterFactory.Adapter;

@Updates(@Facet(InfiniteGenFacet.class))
public class Noise2DSurfaceProvider extends Noise3DTerainProvider implements FacetProvider {

	private AreaLimitAdapter adapter;
	
	/**
	 * 
	 * @param noise
	 * @param zoom
	 * @param center
	 * @param width
	 * @param ignore
	 * @param frequency
	 * @param multificator
	 * @param increase
	 */
    public Noise2DSurfaceProvider(Noise2D noise,Vector3f zoom,float center,float width,int ignore,int function,float preIncrease,double frequency, double multificator,double increase){
    	super(zoom,frequency,multificator,increase);
    	byte b=0;
    	Noise3D noise3 =new Noise2DTo3DAdapter(new AdditionAdapter(noise,preIncrease,b));
    	this.adapter=new AreaLimitAdapter(noise3,
				0f,0f,0,
				center,width,ignore,
				0f,0f,0,
				function);
    	this.setSurfaceNoise(adapter);
    }
    
    /*@Override
    public void process(GeneratingRegion region) {
    InfiniteGenFacet facet =  region.getRegionFacet(InfiniteGenFacet.class);
    
    Region3i area = region.getRegion();
    int X=area.minX();
    int Y=area.minY();
    int Z=area.minZ();
        
        for(int x=facet.getRelativeRegion().minX();x<facet.getRelativeRegion().maxX()+1;x++){
        	for(int y=facet.getRelativeRegion().minY();y<facet.getRelativeRegion().maxY()+1;y++){
        		for(int z=facet.getRelativeRegion().minZ();z<facet.getRelativeRegion().maxZ()+1;z++){
        			
        			float orginal=facet.get(x, y, z);
        			float n = this.surfaceNoise.noise(X, Y, Z);
        			n*=multifier;
                	if(modulus!=0){
                		n=(float) (n %modulus);
                	}
                	n+=increase;
                	
                	n=this.adapter.noise(n, X, Y, Z);
                	facet.set(x, y, z, orginal+n);
                	Z++;
        		}	
        	Y++;
        	}
        X++;
       }
    }*/

    
}