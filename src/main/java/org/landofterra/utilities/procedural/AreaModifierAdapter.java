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
package org.landofterra.utilities.procedural;

import org.landofterra.utilities.math.Statistics;
import org.terasology.math.TeraMath;
import org.terasology.utilities.procedural.Noise2D;
import org.terasology.utilities.procedural.Noise3D;

/**
 * @author Esereja
 */
public class AreaModifierAdapter implements Noise3D,Noise2D {

    private Noise3D noise3;
    private Noise2D noise2;
    
    private float xcenter;
    private float ycenter;
    private float zcenter;
    
    private int xignore;
    private int yignore;
    private int zignore;
    
    private int function;
    
    /**
     * 
     * @param noise
     * @param xcenter
     * @param xignore
     * @param ycenter
     * @param yignore
     * @param zcenter
     * @param zignore
     * @param function
     */
    public AreaModifierAdapter(Noise3D noise,float xcenter, int xignore, float ycenter, int yignore,float zcenter, int zignore,int function) {
    	this.noise3=noise;
    	this.xcenter= xcenter;
    	this.ycenter= ycenter;
    	this.zcenter= zcenter;
    	
    	this.xignore= xignore;
    	this.yignore= yignore;
    	this.zignore= zignore;
    	
    	this.function=function;
    }

    /**
     * 
     * @param noise
     * @param xcenter
     * @param xignore
     * @param ycenter
     * @param yignore
     * @param function
     */
    public AreaModifierAdapter(Noise2D noise,float xcenter, int xignore, float ycenter, int yignore,int function) {
    	this.noise2=noise;
    	this.xcenter= xcenter;
    	this.ycenter= ycenter;
    	
    	this.xignore= xignore;
    	this.yignore= yignore;
    	
    	this.function=function;
    }


    @Override
    public float noise(float x, float y,float z) {
    	float xp=calculate(x,this.xcenter,this.function,this.xignore);
    	float yp=calculate(y,this.ycenter,this.function,this.yignore);
    	float zp=calculate(z,this.zcenter,this.function,this.zignore);
	   
    	float[] a={xp,yp,zp};
    	
    	return this.noise3.noise(x, y, z)*Statistics.min(a); 
    }
    
    @Override
    public float noise(float x, float y) {
    	return 1;
    }
    
    private float calculate(float in,float center,int function,int ignore){
    	if(ignore==1){
    		if(in>center){
    			return 1;
    			}
    	}
    	if(ignore==2){
    		if(in<center){
    			return 1;
    			}
    	}
    	if(ignore>2){
    		return 1;
    	}
    	float p=TeraMath.fastAbs(in-center);
    	float result=0;
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
