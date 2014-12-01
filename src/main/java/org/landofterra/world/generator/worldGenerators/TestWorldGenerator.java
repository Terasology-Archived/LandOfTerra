/*
 * Copyright 2013 MovingBlocks
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
package org.landofterra.world.generator.worldGenerators;

import javax.vecmath.Vector2f;
import javax.vecmath.Vector3f;

import org.landofterra.utilities.procedural.TestNoise;
import org.landofterra.utilities.procedural.adapter.AreaLimitAdapter;
import org.landofterra.utilities.procedural.adapter.FastPerturbationAdapter;
import org.landofterra.utilities.procedural.adapter.MedianAdapter;
import org.landofterra.utilities.procedural.adapter.Noise2DTo3DAdapter;
import org.landofterra.utilities.procedural.adapter.Perturbation3DAdapter;
import org.landofterra.utilities.procedural.adapter.TrigonometricAdapter;
import org.landofterra.utilities.procedural.noise.CoherentNoise;
import org.landofterra.utilities.procedural.noise.MeanNoise;
import org.landofterra.utilities.procedural.noise.NullNoise;
import org.landofterra.world.generator.facetProviders.DilationProvider;
import org.landofterra.world.generator.facetProviders.Noise2DSurfaceProvider;
import org.landofterra.world.generator.facetProviders.Noise3DBaseProvider;
import org.landofterra.world.generator.facetProviders.Noise3DBaseTerainProvider;
import org.landofterra.world.generator.facetProviders.Noise3DProvider;
import org.landofterra.world.generator.facetProviders.Noise3DTerainProvider;
import org.landofterra.world.generator.facetProviders.SimplePlanetSimulatorProvider;
import org.landofterra.world.generator.rasterizers.DebugSolidRasterizer;
import org.terasology.core.world.generator.facetProviders.BiomeProvider;
import org.terasology.core.world.generator.facetProviders.PerlinHumidityProvider;
import org.terasology.core.world.generator.facetProviders.PerlinSurfaceTemperatureProvider;
import org.terasology.core.world.generator.facetProviders.SeaLevelProvider;
import org.terasology.engine.SimpleUri;
import org.terasology.utilities.procedural.BrownianNoise2D;
import org.terasology.utilities.procedural.BrownianNoise3D;
import org.terasology.utilities.procedural.Noise3DTo2DAdapter;
import org.terasology.utilities.procedural.SimplexNoise;
import org.terasology.world.generation.BaseFacetedWorldGenerator;
import org.terasology.world.generation.WorldBuilder;
import org.terasology.world.generator.RegisterWorldGenerator;

@RegisterWorldGenerator(id = "test", displayName = "Test")
public class TestWorldGenerator extends BaseFacetedWorldGenerator {

    public TestWorldGenerator(SimpleUri uri) {
        super(uri);
    }

    @Override
    protected WorldBuilder createWorld(long seed) {
    	SimplePlanetSimulatorProvider densityProv =new SimplePlanetSimulatorProvider();
    	densityProv.setOrigoOffSet(+500);
    	densityProv.setUpHeightMultiplifier(0.001);
    	densityProv.setUpDensityFunction(2);
    	densityProv.setDownHeightMultiplifier(0.008);
    	densityProv.setDownDensityFunction(7);
    	densityProv.setDensityMultifier(30);
    	densityProv.setDensityFunction(1);
        return new WorldBuilder(seed)
                .addProvider(new SeaLevelProvider(32))
                //.addProvider(new Simplex3DBaseTerainProvider(1,new Vector3f(0.013f, 0.013f, 0.013f),5,0,1,+0.2))
                /*.addProvider( 
                		new Noise3DBaseTerainProvider(
                				new BrownianNoise3D(new Turbulence3DAdapter(new SimplexNoise(seed),new SimplexNoise(seed+1),new SimplexNoise(seed+2),new SimplexNoise(seed+3)),2)
                				,new Vector3f(0.005f, 0.005f, 0.005f),0,1,0
                				)
                		)*/
                /*.addProvider( 
                		new Noise3DBaseTerainProvider(
                				new BrownianNoise3D(new SimplePerturbationAdapter(new SimplexNoise(seed),4),2)
                				,new Vector3f(0.005f, 0.005f, 0.005f),0,1,0
                				)
                		)*/
                /*.addProvider( 
                		new Noise3DBaseTerainProvider(
                				new BrownianNoise3D(new TrigonometricAdapter(new SimplexNoise(seed),15),2)
                				,new Vector3f(0.005f, 0.005f, 0.005f),0,0.2f,-0.2f
                				)
                		)*/
                		
                
                  /*.addProvider( 
                		new Noise3DBaseTerainProvider(
                				new AreaLimitAdapter(new BrownianNoise3D(new SimplexNoise(seed),2),
                						0f,0f,0,
                						-6f,10f,0,
                						0f,0f,0,
                						1
                				)
                				//,new Vector3f(0.05f, 0.05f, 0.05f),0,2,0
                				,new Vector3f(0.001f, 0.001f, 0.001f),0,0.6,0
                				)
                		)*/
                
                 .addProvider( 
                		new Noise3DBaseProvider(
                				new NullNoise(0)
                				, 0, 1, 0)
                		)
                		
                 .addProvider( 
                		new Noise2DSurfaceProvider(
                				new BrownianNoise2D(new SimplexNoise(seed),8),
                				new Vector3f(0.0005f, 0.0005f, 0.0005f),
                				-0.3f,0.4f,0,1,0.7f
                				, 0, 2, 0)
                		)
                		
                 /*.addProvider( 
                		new Noise2DSurfaceProvider(
                				new BrownianNoise2D(new SimplexNoise(seed),8),
                				new Vector3f(0.0005f, 0.0005f, 0.0005f),
                				0f,0.2f,0,1,0f
                				, 0, 0.01f, 0)
                		)*/

                  /*.addProvider( 
                		new Noise3DBaseTerainProvider(
                				//new BrownianNoise3D(new SimplexNoise(seed),2)
                				new SimplexNoise(seed)
                				,new Vector3f(0.005f, 0.005f, 0.005f),0,0.4,0
                				)
                		)*/
                		
                  /*.addProvider( 
                		new Noise3DBaseTerainProvider(
                				new BrownianNoise3D(new SimplexNoise(seed),2)
                				//new CoherentNoise(seed)
                				,new Vector3f(0.0005f, 0.0005f, 0.0005f),0,0.4,0
                				)
                		)*/
                		
                	 /*.addProvider( 
                		new Noise3DTerainProvider(
                				new BrownianNoise3D(new SimplexNoise(seed),1)
                				,new Vector3f(0.01f, 0.01f, 0.01f),0,0.5f,-0.2f
                				)
                		)*/
                		
                	/*	
                   .addProvider( 
                		new Noise3DTerainProvider(
                				new BrownianNoise3D(new MeanNoise(seed),4)
                				//new CoherentNoise(seed)
                				,new Vector3f(0.01f, 0.01f, 0.01f),0,0.5,-1
                				)
                		)*/
                		
                
                
                .addProvider(new PerlinHumidityProvider())
                .addProvider(new PerlinSurfaceTemperatureProvider())
                .addProvider(new BiomeProvider())
                .addProvider(densityProv)
                .addRasterizer(new DebugSolidRasterizer())
                //.addRasterizer(new InfiniteGenSolidRasterizer())
                .addPlugins();
    }
    
}

/* some presets for simplex noise:
 * 0.0025f, 0.01f, 0.0025f floating planes zoom 10 or 8, and mult 2, freq 0.5 if wanted 
 * 0.00085f, 0.0007f, 0.00085f big and mighty mountains, add freg 0.5 and mult 2 to get some character
 */

/* some presets for perlin:
 * 0.00085f, 0.0007f, 0.00085f big and mighty mountains, add freg 0.5 and mult 2 to get some character
 * 0.0025f, 0.01f, 0.0025f plate mountains
 */
