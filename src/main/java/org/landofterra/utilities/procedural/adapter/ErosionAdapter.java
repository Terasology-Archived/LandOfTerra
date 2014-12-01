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

import java.util.List;

import org.landofterra.utilities.math.Statistics;
import org.terasology.utilities.procedural.Noise2D;
import org.terasology.utilities.procedural.Noise3D;

/**
 * @author Esereja
 */
public class ErosionAdapter implements Noise3D,Noise2D {

    private Noise3D noise3;
    private Noise2D noise2;
    
    private int kernelSize;
    

    /***
     * takes in 3d noise and creates turbulence whit in this noise.
     * @param noise
     * @param width
     * @param freq
     */
    public ErosionAdapter(Noise3D noise,int kernelSize) {
        this.noise3 = noise;
        this.kernelSize=kernelSize;
    }
    
    /***
     * takes in 2d noise and creates turbulence whit in this noise.
     * @param noise
     * @param width
     * @param freq
     * @param b this does nothing. it is just place holder
     */
    public ErosionAdapter(Noise2D noise,int kernelSize,byte b) {
        this.noise2 = noise;
        this.kernelSize=kernelSize;
    }


    @Override
    public float noise(float x, float y,float z) {
    	int blocks=1;
    	for(int a=-this.kernelSize;a<this.kernelSize;a++)
    		for(int b=-this.kernelSize;b<this.kernelSize;b++)
    			for(int c=-this.kernelSize;c<this.kernelSize;c++)
    				blocks++;
    	float[] array=new float[blocks];
    	int i=0;
    	for(int a=-this.kernelSize;a<this.kernelSize;a++){
    		for(int b=-this.kernelSize;b<this.kernelSize;b++){
    			for(int c=-this.kernelSize;c<this.kernelSize;c++){
    				array[i]=this.noise3.noise(x+a, y+b, z+c);
    				i++;
    				}
    			}
    		}
    	float t=Statistics.min(array);
        return t+t/blocks;
    }
    
    @Override
    public float noise(float x, float y) {
    	int blocks=1;
    	for(int a=-this.kernelSize;a<this.kernelSize;a++)
    		for(int b=-this.kernelSize;b<this.kernelSize;b++)
    				blocks++;
    	float[] array=new float[blocks];
    	int i=0;
    	for(int a=-this.kernelSize;a<this.kernelSize;a++){
    		for(int b=-this.kernelSize;b<this.kernelSize;b++){
    				array[i]=this.noise2.noise(x+a, y+b);
    				i++;
    			}
    		}
    	float t=Statistics.min(array);
        return t+t/blocks; 
    }
  
}
