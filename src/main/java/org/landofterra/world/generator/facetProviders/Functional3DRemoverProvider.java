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
import org.terasology.math.TeraMath;
import org.terasology.math.Vector3i;
import org.terasology.world.generation.Facet;
import org.terasology.world.generation.FacetProvider;
import org.terasology.world.generation.GeneratingRegion;
import org.terasology.world.generation.Updates;

/***
 * 
 * @author Esereja
 *
 */
@Updates(@Facet(InfiniteGenFacet.class))
public class Functional3DRemoverProvider implements FacetProvider {
    
	private boolean anti;
	private int function;
	
    private Vector3f origo;
    private Vector3f deviation;

    /***
     * Functional remover removes data which is inside given range.
     * @param origo of calculation.(means center)
     * @param deviation has deviation values of x,y,z. set radius on given coordinate 
     * direction that is affected by function. negative values inverse noise map of given area. 
     * zero causes calculation to ignore axis
     * @param mode if true removes selected area, false removes all but selected area
     */
    public Functional3DRemoverProvider(Vector3f origo,Vector3f deviation, boolean mode,int function){
    	this.origo=origo;
    	this.deviation=deviation;
    	
    	this.anti=mode;
    	this.function=function;
    }
    
    @Override
    public void setSeed(long seed) {
    }
    
    @Override
    public void process(GeneratingRegion region) {
    	if(this.deviation.x==0&&this.deviation.y==0&&this.deviation.z==0){
    		return;
    	}
        InfiniteGenFacet facet =  region.getRegionFacet(InfiniteGenFacet.class);
        
        Region3i area = region.getRegion();
        int X=area.minX();//real universal X coordinate
        int Y=area.minY();//real universal Y coordinate
        int Z=area.minZ();//real universal Z coordinate
        
        for (int y = facet.getRelativeRegion().minY(); y <= facet.getRelativeRegion().maxY(); ++y) {
        	for (int x = facet.getRelativeRegion().minX(); x <= facet.getRelativeRegion().maxX(); ++x) {
        		for (int z = facet.getRelativeRegion().minZ(); z <= facet.getRelativeRegion().maxZ(); ++z) {
        			float data = facet.get(new Vector3i(x,y,z));
        			if(this.anti){// removes selected area
        				if(this.deviation.z!=0){
							double d =TeraMath.fastAbs(Z-this.origo.z);
							if(d>this.deviation.z){
								if(d!=0){
									if(this.function ==2){
										double a=(d/this.deviation.z);
										a*=a;
										data=(float)(data*a);
									}else{
										data=(float)(data*(d/this.deviation.z));
									}
								}
							}
						}
						
						if(this.deviation.y!=0){
							double d =TeraMath.fastAbs(Y-this.origo.y);
							if(d>this.deviation.y){
								if(d!=0){
									if(this.function ==2){
										double a=(d/this.deviation.y);
										a*=a;
										data=(float)(data*a);
									}else{
										data=(float)(data*(d/this.deviation.y));
									}
								}
							}
						}	
						
						if(this.deviation.x!=0){
							double d =TeraMath.fastAbs(X-this.origo.x);
							if(d>this.deviation.x){
								if(d!=0){
									if(this.function ==2){
										double a=(d/this.deviation.x);
										a*=a;
										data=(float)(data*a);
									}else{
										data=(float)(data*(d/this.deviation.x));
									}
								}
							}
						}

        			}else{//normal version of function removes all but selected area 
						if(this.deviation.z!=0){
							double d =TeraMath.fastAbs(Z-this.origo.z);
							if(d>this.deviation.z){
								if(d!=0){
									if(this.function ==2){
										double a=(this.deviation.z-d/this.deviation.z);
										a*=a;
										data=(float)(data*a);
									}else{
										data=(float)(data*(this.deviation.z-d/this.deviation.z));
									}
								}
							}else{
								data=0;
							}
						}
						
						if(this.deviation.y!=0){
							double d =TeraMath.fastAbs(Y-this.origo.y);
							if(d>this.deviation.y){
								if(d!=0){
									if(this.function ==2){
										double a=(this.deviation.y-d/this.deviation.y);
										a*=a;
										data=(float)(data*a);
									}else{
										data=(float)(data*(this.deviation.y-d/this.deviation.y));
									}
								}
							}else{
								data=0;
							}
						}	
						
						if(this.deviation.x!=0){
							double d =TeraMath.fastAbs(X-this.origo.x);
							if(d>this.deviation.x){
								if(d!=0){
									if(this.function ==2){
										double a=(this.deviation.x-d/this.deviation.x);
										a*=a;
										data=(float)(data*a);
									}else{
										data=(float)(data*(this.deviation.x-d/this.deviation.x));
									}
								}
							}else{
								data=0;
							}
						}

    				}
        			facet.set(x, y, z,data);
        			Z++;
        		}
        		X++;
        	}
        	Y++;
        }
    }
    
}