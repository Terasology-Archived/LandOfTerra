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
package org.landofterra.utilities.procedural.adapter;

import org.landofterra.utilities.math.Statistics;
import org.terasology.math.TeraMath;
import org.terasology.utilities.procedural.Noise2D;
import org.terasology.utilities.procedural.Noise3D;

/**
 * @author Esereja
 */
public class AreaLimitAdapter implements Noise3D,Noise2D {

    private Noise3D noise3;
    private Noise2D noise2;
    
    private float xcenter;
    private float ycenter;
    private float zcenter;

    private float xwidth;
    private float ywidth;
    private float zwidth;
    
    private int xignore;
    private int yignore;
    private int zignore;
    
    private int function;
    
    /**
     * 
     * @param noise
     * @param xcenter
     * @param xwidth
     * @param xignore
     * @param ycenter
     * @param ywidth
     * @param yignore
     * @param zcenter
     * @param zwidth
     * @param zignore
     * @param function
     */
    public AreaLimitAdapter(Noise3D noise,float xcenter, float xwidth, int xignore, float ycenter, float ywidth, int yignore,float zcenter, float zwidth, int zignore,int function) {
    	this.noise3=noise;
    	this.xcenter= xcenter;
    	this.ycenter= ycenter;
    	this.zcenter= zcenter;

    	this.xwidth= xwidth;
    	this.ywidth= ywidth;
    	this.zwidth= zwidth;
        
    	this.xignore= xignore;
    	this.yignore= yignore;
    	this.zignore= zignore;
    	
    	this.function=function;
    }

    /**
     * 
     * @param noise
     * @param xcenter
     * @param xwidth
     * @param xignore
     * @param ycenter
     * @param ywidth
     * @param yignore
     * @param function
     */
    public AreaLimitAdapter(Noise2D noise,float xcenter, float xwidth, int xignore, float ycenter, float ywidth, int yignore,int function) {
    	this.noise2=noise;
    	this.xcenter= xcenter;
    	this.ycenter= ycenter;
    	
    	this.xwidth= xwidth;
    	this.ywidth= ywidth;
    	
    	this.xignore= xignore;
    	this.yignore= yignore;
    	
    	this.function=function;
    }
    
    /**
     * 
     * @param xcenter
     * @param xwidth
     * @param xignore
     * @param ycenter
     * @param ywidth
     * @param yignore
     * @param zcenter
     * @param zwidth
     * @param zignore
     * @param function
     */
    public AreaLimitAdapter(float xcenter, float xwidth, int xignore, float ycenter, float ywidth, int yignore,float zcenter, float zwidth, int zignore,int function) {
    	this.xcenter= xcenter;
    	this.ycenter= ycenter;
    	this.zcenter= zcenter;

    	this.xwidth= xwidth;
    	this.ywidth= ywidth;
    	this.zwidth= zwidth;
        
    	this.xignore= xignore;
    	this.yignore= yignore;
    	this.zignore= zignore;
    	
    	this.function=function;
    }

    @Override
    public float noise(float x, float y,float z) {
    	float xp;
    	if(this.xwidth<=0){
    		xp=1;
    	}else{
	    	xp=calculate(x,this.xcenter,this.xwidth,this.function,this.xignore);
	    	if(xp<=0){
	    		return 0;
	    	}
	    }
    	float yp;
    	if(this.ywidth<=0){
    		yp=1;
    	}else{
	    	yp=calculate(y,this.ycenter,this.ywidth,this.function,this.yignore);
	    	if(yp<=0){
	    		return 0;
	    	}
	    }
    	float zp;
    	if(this.zwidth<=0){
    		zp=1;
    	}else{
	    	zp=calculate(z,this.zcenter,this.zwidth,this.function,this.zignore);
	    	if(zp<=0){
	    		return 0;
	    	}
	    }
    	float[] a={xp,yp,zp};
    	
    	return this.noise3.noise(x, y, z)*Statistics.min(a); 
    }
    
    @Override
    public float noise(float x, float y) {
    	float xp;
    	if(this.xwidth>0){
    		xp=1;
    	}else{
	    	xp=calculate(x,this.xcenter,this.xwidth,this.function,this.xignore);
	    	if(xp<0){
	    		return 0;
	    	}
	    }
    	float yp;
    	if(this.ywidth>0){
    		yp=1;
    	}else{
	    	yp=calculate(y,this.ycenter,this.ywidth,this.function,this.yignore);
	    	if(yp<0){
	    		return 0;
	    	}
	    }
    	float[] a={xp,yp};
    	
    	return this.noise2.noise(x, y)*Statistics.min(a); 
    }
    
    public float noise(final float noiseIn,float x,float y,float z) {
    	float noise=noiseIn;
    	float xp;
    	if(this.xwidth<=0){
    		xp=1;
    	}else{
	    	xp=calculate(x,this.xcenter,this.xwidth,this.function,this.xignore);
	    	if(xp<=0){
	    		return 0;
	    	}
	    }
    	float yp;
    	if(this.ywidth<=0){
    		yp=1;
    	}else{
	    	yp=calculate(y,this.ycenter,this.ywidth,this.function,this.yignore);
	    	if(yp<=0){
	    		return 0;
	    	}
	    }
    	float zp;
    	if(this.zwidth<=0){
    		zp=1;
    	}else{
	    	zp=calculate(z,this.zcenter,this.zwidth,this.function,this.zignore);
	    	if(zp<=0){
	    		return 0;
	    	}
	    }
    	float[] a={xp,yp,zp};
    	
    	return noise*Statistics.min(a); 
    }

    private float calculate(float in,float center,float width,int function,int ignore){
    	if(ignore>0){
    		if(in>center){
    			return 1;
    			}
    	}
    	if(ignore<0){
    		if(in<center){
    			return 1;
    			}
    	}
    	float p=TeraMath.fastAbs(in-center);
    	if(p>width ){
    		return 0;
    	}
    	float result=0;
    	p=(width-p)/width;
    	switch(function){
			case 2:
				result=p*p*p;
				break;
    		case 1:
    			result=p*p;
    			break;
    		default:
    			result=p;	
    	}		
    	
    	return result;
    }
    
}
