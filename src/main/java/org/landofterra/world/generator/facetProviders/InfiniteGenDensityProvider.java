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

import org.terasology.math.Region3i;
import org.terasology.math.TeraMath;
import org.terasology.math.Vector3i;
import org.terasology.world.generation.Facet;
import org.terasology.world.generation.FacetProvider;
import org.terasology.world.generation.GeneratingRegion;
import org.terasology.world.generation.Produces;
import org.terasology.world.generation.Requires;
import org.terasology.world.generation.facets.DensityFacet;
import org.terasology.world.generation.facets.InfiniteGenFacet;


/**
 * Sets density based on nose values. by default just copy them. but whit functions much more
 */
@Requires(@Facet(InfiniteGenFacet.class))
@Produces(DensityFacet.class)
public class InfiniteGenDensityProvider implements FacetProvider {

	private int origoOffSet;
	
	private double densityMultifier;
	private double upheightMultiplifier;
	private double downheightMultiplifier;
	
	private int DensityFunction;
	private int upDensityFunction;
	private int downDensityFunction;	
	
	/***
	 * just copy noise values to density as they are. 
	 * that is, if you don't set functions to other values. 
	 */
	public InfiniteGenDensityProvider(){
		upheightMultiplifier=0.1;
		downheightMultiplifier=0.1;
		origoOffSet=0;
		upDensityFunction=0;
		downDensityFunction=0;
		DensityFunction=0;
		densityMultifier=1;
	}
	
	/***
	 * 
	 * @param origoOffSet
	 * @param densityFunction
	 * @param densityMultifier
	 */
	public InfiniteGenDensityProvider(int origoOffSet,int densityFunction,double densityMultifier){
		this.origoOffSet=origoOffSet;
		this.DensityFunction = densityFunction;
		this.densityMultifier= densityMultifier;
		upheightMultiplifier=0.1;
		downheightMultiplifier=0.1;
		upDensityFunction=0;
		downDensityFunction=0;
	}
	
    @Override
    public void setSeed(long seed) {
    }
    
	@Override
    public void process(GeneratingRegion region) {
    	InfiniteGenFacet surface = region.getRegionFacet(InfiniteGenFacet.class);
        DensityFacet facet = new DensityFacet(region.getRegion(), region.getBorderForFacet(DensityFacet.class));

        Region3i area = region.getRegion();
        int Y=area.minY();//real universal Y coordinate
        
        for (int y = facet.getRelativeRegion().minY(); y <= facet.getRelativeRegion().maxY(); ++y) {
        	for (int x = facet.getRelativeRegion().minX(); x <= facet.getRelativeRegion().maxX(); ++x) {
        		for (int z = facet.getRelativeRegion().minZ(); z <= facet.getRelativeRegion().maxZ(); ++z) {
        			float denst = surface.get(new Vector3i(x,y,z));
        			switch(this.DensityFunction){
	        			case 1:
	        				denst *=densityMultifier;
	        				break;
	        			case 2:
	        				denst *=denst;
	        				denst *=densityMultifier;
	        				break;	
	        			case 0:
	        			default:
        			}

        			double a=0;
        			    if(Y+origoOffSet<0){
        			    	switch (downDensityFunction){
        			    	case 1://linear growth
        			    		a=TeraMath.fastAbs(((Y+origoOffSet)*downheightMultiplifier+1));
        			    		if(a!=0){
	        			    		facet.set(x, y, z, (float)(denst*a));        		
	    			    		} else{
	    			    			facet.set(x, y, z,denst);
	    			    		}
        			    		break;
        			    	case 2://linear decrease
        			    		a=TeraMath.fastAbs(((Y+origoOffSet)*downheightMultiplifier+1));
        			    		if(a!=0){
        			    			facet.set(x, y, z, (float)(denst/a));
        			    		} else{
        			    			facet.set(x, y, z,denst);
        			    		}
        			    		break;	
        			    	case 3://exponential growth
        			    		a=TeraMath.fastAbs(((Y+origoOffSet)*downheightMultiplifier+1));
        			    		if(a!=0){
	        			    		facet.set(x, y, z, (float)(denst*a*a));
	    			    		} else{
	    			    			facet.set(x, y, z,denst);
	    			    		}
        			    		break;
        			    	case 4://exponential decrease
        			    		a=TeraMath.fastAbs(((Y+origoOffSet)*downheightMultiplifier+1));
        			    		if(a!=0){
        			    			facet.set(x, y, z, (float)(denst/(a*a)));
        			    		} else{
        			    			facet.set(x, y, z,denst);
        			    		}
        			    		break;	
        			    	case 5://edited exponential growth
        			    		a=TeraMath.fastAbs(((Y+origoOffSet)*downheightMultiplifier+1));
        			    		if(a!=0){
	        			    		facet.set(x, y, z, (float)(denst*a*(a*downheightMultiplifier)));
	    			    		} else{
	    			    			facet.set(x, y, z,denst);
	    			    		}
        			    		break;
        			    	case 6://edited exponential decrease
        			    		a=TeraMath.fastAbs(((Y+origoOffSet)*downheightMultiplifier+1));
        			    		if(a!=0){
        			    			facet.set(x, y, z, (float)(denst/(a*a*downheightMultiplifier)));
        			    		} else{
        			    			facet.set(x, y, z,denst);
        			    		}
        			    		break;
        			    	case 7://linear increase
        			    		a=TeraMath.fastAbs(((Y+origoOffSet)*downheightMultiplifier+1));
        			    		if(a!=0){
	        			    		facet.set(x, y, z, (float)(denst+denst*a));        		
	    			    		} else{
	    			    			facet.set(x, y, z,denst);
	    			    		}
        			    		break;
        			    	case 8://linear decrease
        			    		a=TeraMath.fastAbs(((Y+origoOffSet)*downheightMultiplifier+1));
        			    		if(a!=0){
        			    			facet.set(x, y, z, (float)(denst-denst*a));
        			    		} else{
        			    			facet.set(x, y, z,denst);
        			    		}
        			    		break;
        			    	case 0:
        			    	default:
        			    			facet.set(x, y, z, denst);
        			    	}
        				}else{
        					switch (upDensityFunction){
        			    	case 1://linear growth
        			    		a=TeraMath.fastAbs(((Y+origoOffSet)*upheightMultiplifier+1));
        			    		if(a!=0){
        			    			facet.set(x, y, z, (float)(denst*a));
        			    		} else{
        			    			facet.set(x, y, z,denst);
        			    		}
        			    		break;
        			    	case 2://linear decrease
        			    		a=((Y+origoOffSet)*upheightMultiplifier+1);
        			    		if(a!=0){
        			    			facet.set(x, y, z, (float)(denst/a));
        			    		} else{
        			    			facet.set(x, y, z,denst);
        			    		}
        			    		break;	
        			    	case 3://exponential growth
        			    		a=TeraMath.fastAbs(((Y+origoOffSet)*upheightMultiplifier+1));
        			    		if(a!=0){
        			    			facet.set(x, y, z, (float)(denst*a*a));
        			    		} else{
        			    			facet.set(x, y, z,denst);
        			    		}
        			    		break;
        			    	case 4://exponential decrease
        			    		a=TeraMath.fastAbs(((Y+origoOffSet)*upheightMultiplifier+1));
        			    		if(a!=0){
        			    			facet.set(x, y, z, (float)(denst/(a*a)));
        			    		} else{
        			    			facet.set(x, y, z,denst);
        			    		}
        			    		break;
        			    	case 5://edited exponential growth
        			    		a=TeraMath.fastAbs(((Y+origoOffSet)*upheightMultiplifier+1));
        			    		if(a!=0){
        			    			facet.set(x, y, z, (float)(denst*a*(a*upheightMultiplifier)));
        			    		} else{
        			    			facet.set(x, y, z,denst);
        			    		}
        			    		break;
        			    	case 6://edited exponential decrease
        			    		a=TeraMath.fastAbs(((Y+origoOffSet)*upheightMultiplifier+1));
        			    		if(a!=0){
        			    			facet.set(x, y, z, (float)(denst/(a*a*upheightMultiplifier)));
        			    		} else{
        			    			facet.set(x, y, z,denst);
        			    		}
        			    		break;
        			    	case 7://linear increase
        			    		a=TeraMath.fastAbs(((Y+origoOffSet)*downheightMultiplifier+1));
        			    		if(a!=0){
	        			    		facet.set(x, y, z, (float)(denst+denst*a));        		
	    			    		} else{
	    			    			facet.set(x, y, z,denst);
	    			    		}
        			    		break;
        			    	case 8://linear decrease
        			    		a=TeraMath.fastAbs(((Y+origoOffSet)*downheightMultiplifier+1));
        			    		if(a!=0){
        			    			facet.set(x, y, z, (float)(denst-denst*a));
        			    		} else{
        			    			facet.set(x, y, z,denst);
        			    		}
        			    		break;
        			    	case 0:
        			    	default:
        			    			facet.set(x, y, z, denst);
        			    	}
        				}//end of density logic
 	
        		}
            }
        	Y++;
        }
        region.setRegionFacet(DensityFacet.class, facet);
    }
    
    /**
	 * @return the heightMultiplifier
	 */
	public double getUpHeightMultiplifier() {
		return upheightMultiplifier;
	}
	
	/**
	 * decides how much density changes depending distance to origo. fractions work best.
	 * formula used is = ((y+origoOffSet)*downheightMultiplifier+1)
	 * so value is in the end added to 1
	 * @param heightMultiplifier the heightMultiplifier value to set 
	 */
    public void setUpHeightMultiplifier(double heightMultiplifier) {
    	this.upheightMultiplifier =heightMultiplifier;
    }

    /**
	 * @return the downheightMultiplifier
	 */
	public double getDownheightMultiplifier() {
		return downheightMultiplifier;
	}

	/**
	 * decides how much density changes depending distance to origo. fractions work best.
	 * formula used is = ((y+origoOffSet)*downheightMultiplifier+1)
	 * so value is in the end added to 1
	 * @param downheightMultiplifier the downheightMultiplifier to set
	 */
	public void setDownHeightMultiplifier(double downheightMultiplifier) {
		this.downheightMultiplifier = downheightMultiplifier;
	}

	/**
	 * @return the upDensityFunction
	 */
	public int getUpDensityFunction() {
		return upDensityFunction;
	}

	/**
	 * values should be int in between 0-6
	 * 0 no function
	 * 1 linear growth
	 * 2 linear decrease
	 * 3 exponential growth
	 * 4 exponential decrease
	 * 5 edited exponential growth
	 * 6 edited exponential decrease
	 * 7 linear increase(means that real mass increases)
	 * 8 linear decrease(means that real mass decreases)
	 * @param upDensityFunction the upDensityFunction to set
	 */
	public void setUpDensityFunction(int upDensityFunction) {
		this.upDensityFunction = upDensityFunction;
	}

	/**
	 * @return the downDensityFunction
	 */
	public int getDownDensityFunction() {
		return downDensityFunction;
	}

	/**	 
	 * values should be int in between 0-6
	 * 0 no function
	 * 1 linear growth
	 * 2 linear decrease
	 * 3 exponential growth
	 * 4 exponential decrease
	 * 5 edited exponential growth
	 * 6 edited exponential decrease
	 * 7 linear increase(means that real mass increases)
	 * 8 linear decrease(means that real mass decreases)
	 * @param downDensityFunction the downDensityFunction to set
	 */
	public void setDownDensityFunction(int downDensityFunction) {
		this.downDensityFunction = downDensityFunction;
	}

	/**
     * Set off set off height multification origo
	 * @return the origoOffSet
	 */
	public int getOrigoOffSet() {
		return origoOffSet;
	}

	/**
	 * this offsets origo for density calculations. this value is added to Y axis value in calculations.
	 * so +100 would mean that -100 is seen as origo for calculations. 
	 * @param origoOffSet the origoOffSet to set
	 */
	public void setOrigoOffSet(int origoOffSet) {
		this.origoOffSet = origoOffSet;
	}

	/**
	 * @return the densityMultifier
	 */
	public double getDensityMultifier() {
		return densityMultifier;
	}

	/**
	 * multiplier for density function
	 * @param densityMultifier the densityMultifier to set
	 */
	public void setDensityMultifier(double densityMultifier) {
		this.densityMultifier = densityMultifier;
	}

	/**
	 * @return the densityFunction
	 */
	public int getDensityFunction() {
		return DensityFunction;
	}

	/**
	 * this function decides how densities are calculated from noise.
	 * default. they are just copied as they are.
	 * 1. they are multiplied by densityMultifier
	 * 2. they are taken to power of 2 and multiplied.
	 * @param densityFunction the densityFunction to set
	 */
	public void setDensityFunction(int densityFunction) {
		DensityFunction = densityFunction;
	}
	
}
