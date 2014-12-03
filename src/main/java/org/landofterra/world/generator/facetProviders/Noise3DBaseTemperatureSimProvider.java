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

import org.landofterra.world.generation.facets.InfiniteGenFacet;
import org.landofterra.world.generation.facets.TemperatureFacet;
import org.terasology.math.TeraMath;
import org.terasology.utilities.procedural.Noise3D;
import org.terasology.world.generation.Border3D;
import org.terasology.world.generation.Facet;
import org.terasology.world.generation.FacetProvider;
import org.terasology.world.generation.GeneratingRegion;
import org.terasology.world.generation.Produces;
import org.terasology.world.generation.Requires;

/**
 * 
 * @author esereja
 */
@Requires(@Facet(InfiniteGenFacet.class))
@Produces(TemperatureFacet.class)
public class Noise3DBaseTemperatureSimProvider implements FacetProvider {

    private Noise3D noise;

    private float modulus;
    private float multifier;
    private float increase;
    
    private float gravity=9.80665f;
    private float AtmosphericPresure=0.001f;
    private float dALR;//Dry adiabatic lapse rate
    private float gTG=25/1000;//ground temperature gradient
    
    private float temperatureMean;
    private float temperatureAD;// absolute difference
    private float tZL;//temperature zone lenght
    
    private float xOffset;
    private float zOffset;
    
    private float xMultifier;
    private float zMultifier;
    
    private int tZF;//temperature zone function
    
    /**
     * Temperature values are in celcius
     * @param noise 3d noise to randomize values
     * @param frequency modulus
     * @param multificator values are multiplied whit this at end
     * @param increase this is added to value at very end
     * @param temperatureMean average temperature
     * @param temperatureAD max difference from mean value
     * @param tZL temperature zone length
     */
    public Noise3DBaseTemperatureSimProvider(Noise3D noise,float frequency, float multificator,float increase,
    		float temperatureMean,float temperatureAD,float tZL){
    	this.noise = noise;
    	    	
    	this.modulus=frequency;
    	this.multifier=multificator;
    	this.increase=increase;

    	this.dALR=this.gravity/this.AtmosphericPresure;
    	
    	this.temperatureMean=temperatureMean;
    	this.temperatureAD=temperatureAD;
    	this.tZL=tZL;
    	
    	this.tZF=0;
    }
    
    @Override
    public void setSeed(long seed) {
    }
    
    @Override
    public void process(GeneratingRegion region) {
        InfiniteGenFacet denFacet =  region.getRegionFacet(InfiniteGenFacet.class);
        
    	Border3D border = region.getBorderForFacet(TemperatureFacet.class);
    	TemperatureFacet tempFacet = new TemperatureFacet(region.getRegion(), border);
        //Region3i processRegion = facet.getWorldRegion();

        for(int x=tempFacet.getRelativeRegion().minX();x<tempFacet.getRelativeRegion().maxX()+1;x++)
        	for(int y=tempFacet.getRelativeRegion().minY();y<tempFacet.getRelativeRegion().maxY()+1;y++){
        		for(int z=tempFacet.getRelativeRegion().minZ();z<tempFacet.getRelativeRegion().maxZ()+1;z++){
        			
        			float n = this.noise.noise(x, y, z);
        			
        			if(this.modulus!=0){
                		n=(float) (n %this.modulus);
                	}
        			n*=this.multifier;
                	n+=this.increase;
        			
        			switch(this.tZF){
        			case 4:
        				n =(temperatureZoneSim(n,(x+this.xOffset)*this.xMultifier )
        						+temperatureZoneSim(n,(z+this.zOffset)*this.zMultifier ))/2;
        				break;
        			case 3:
        				n =temperatureZoneSim(n,(float)Math.sin( ((z+this.zOffset)*this.zMultifier +(x+this.xOffset)*this.xMultifier )*TeraMath.PI ) );
        				break;
        			case 2:
        				n =temperatureZoneSim(n,( (z+this.zOffset)*this.zMultifier +(x+this.xOffset)*this.xMultifier )/2);
        				break;
        			case 1:
        				n =temperatureZoneSim(n,(z+this.zOffset)*this.zMultifier );
        				break;
        			default:
        				n =temperatureZoneSim(n,(x+this.xOffset)*this.xMultifier );
        			}
        			
        			n =altitudeSim(n,denFacet.get(x, y, z),y);
        			
                	tempFacet.set(x, y, z, n);
                	
                	if(tempFacet.getMax()<n){
                		tempFacet.setMax(n);
                	}else if(tempFacet.getMin()>n){
                		tempFacet.setMin(n);
                	}
        		}	
        	}
        
        region.setRegionFacet(TemperatureFacet.class, tempFacet);
    }
    
    private float temperatureZoneSim(float noise,float d){
    	return (float) ((
    			Math.sin((d/this.tZL)*TeraMath.PI)
    			*this.temperatureAD)+this.temperatureMean)*noise;
    	
    }
    
    private float altitudeSim(float noise,float density,float height){
    	if(density<=0){
    		return noise+( (-height)*this.dALR);
    	}
		return noise+height*this.gTG;
    }

	public float getGravity() {
		return gravity;
	}

	public void setGravity(float gravity) {
		this.gravity = gravity;
    	this.dALR=this.gravity/this.AtmosphericPresure;
	}

	public float getAtmosphericPresure() {
		return AtmosphericPresure;
	}

	public void setAtmosphericPresure(float atmosphericPresure) {
		AtmosphericPresure = atmosphericPresure;
    	this.dALR=this.gravity/this.AtmosphericPresure;
	}

	public float getgTG() {
		return gTG;
	}

	public void setgTG(float gTG) {
		this.gTG = gTG;
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

	/**
	 * @return the temperatureMean
	 */
	public float getTemperatureMean() {
		return temperatureMean;
	}

	/**
	 * @param temperatureMean the temperatureMean to set
	 */
	public void setTemperatureMean(float temperatureMean) {
		this.temperatureMean = temperatureMean;
	}

	/**
	 * @return the temperatureAD
	 */
	public float getTemperatureAD() {
		return temperatureAD;
	}

	/**
	 * @param temperatureAD the temperatureAD to set
	 */
	public void setTemperatureAD(float temperatureAD) {
		this.temperatureAD = temperatureAD;
	}

	/**
	 * @return the tZL
	 */
	public float gettZL() {
		return tZL;
	}

	/**
	 * @param tZL the tZL to set
	 */
	public void settZL(float tZL) {
		this.tZL = tZL;
	}

	/**
	 * @return the xOffset
	 */
	public float getxOffset() {
		return xOffset;
	}

	/**
	 * @param xOffset the xOffset to set
	 */
	public void setxOffset(float xOffset) {
		this.xOffset = xOffset;
	}

	/**
	 * @return the zOffset
	 */
	public float getzOffset() {
		return zOffset;
	}

	/**
	 * @param zOffset the zOffset to set
	 */
	public void setzOffset(float zOffset) {
		this.zOffset = zOffset;
	}

	/**
	 * @return the xMultifier
	 */
	public float getxMultifier() {
		return xMultifier;
	}

	/**
	 * @param xMultifier the xMultifier to set
	 */
	public void setxMultifier(float xMultifier) {
		this.xMultifier = xMultifier;
	}

	/**
	 * @return the zMultifier
	 */
	public float getzMultifier() {
		return zMultifier;
	}

	/**
	 * @param zMultifier the zMultifier to set
	 */
	public void setzMultifier(float zMultifier) {
		this.zMultifier = zMultifier;
	}

	/**
	 * @return the tZF
	 */
	public int gettZF() {
		return tZF;
	}

	/**
	 * @param tZF the tZF to set
	 */
	public void settZF(int tZF) {
		this.tZF = tZF;
	}
}