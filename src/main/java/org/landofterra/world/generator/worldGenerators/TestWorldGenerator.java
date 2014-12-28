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

import javax.vecmath.Vector3f;

import org.boundlessworlds.utilities.procedural.noise.NullNoise;
import org.boundlessworlds.world.generator.facetProviders.BiomeProvider;
import org.boundlessworlds.world.generator.facetProviders.LandFormProvider;
import org.boundlessworlds.world.generator.facetProviders.Noise3DBaseFormProvider;
import org.boundlessworlds.world.generator.facetProviders.Noise3DBaseHumidityProvider;
import org.boundlessworlds.world.generator.facetProviders.Noise3DBaseProvider;
import org.boundlessworlds.world.generator.facetProviders.Noise3DBaseTemperatureProvider;
import org.boundlessworlds.world.generator.landFormDefinitions.ChaosFormDefinition;
import org.boundlessworlds.world.generator.landFormDefinitions.HillsFormDefinition;
import org.boundlessworlds.world.generator.landFormDefinitions.PlainFormDefinition;
import org.boundlessworlds.world.generator.landFormDefinitions.VoidFormDefinition;
import org.boundlessworlds.world.generator.rasterizers.DebugSolidRasterizer;
import org.terasology.core.world.generator.facetProviders.SeaLevelProvider;
import org.terasology.engine.SimpleUri;
import org.terasology.utilities.procedural.SimplexNoise;
import org.terasology.utilities.procedural.SubSampledNoise3D;
import org.terasology.world.generation.BaseFacetedWorldGenerator;
import org.terasology.world.generation.WorldBuilder;
import org.terasology.world.generator.RegisterWorldGenerator;

/**
 * test bed for world generation code
 * @author esereja
 */
@RegisterWorldGenerator(id = "test", displayName = "Test")
public class TestWorldGenerator extends BaseFacetedWorldGenerator {

    public TestWorldGenerator(SimpleUri uri) {
        super(uri);
    }

    @Override
    protected WorldBuilder createWorld(long seed) {
    	/*SimplePlanetSimulatorProvider densityProv =new SimplePlanetSimulatorProvider();
    	densityProv.setOrigoOffSet(+500);
    	densityProv.setUpHeightMultiplifier(0.001f);
    	densityProv.setUpDensityFunction(2);
    	densityProv.setDownHeightMultiplifier(0.008f);
    	densityProv.setDownDensityFunction(7);
    	densityProv.setDensityMultifier(30);
    	densityProv.setDensityFunction(1);
    	*/
    	
    	LandFormProvider lfp= new LandFormProvider(0, 3, 0);
    	lfp.addNoise(new PlainFormDefinition(seed+4));
    	lfp.addNoise(new HillsFormDefinition(seed+5));
    	lfp.addNoise(new ChaosFormDefinition(seed+6));
    	lfp.addNoise(new VoidFormDefinition(seed+6));

		return new WorldBuilder(seed)
                .addProvider(new SeaLevelProvider(32))
         
                .addProvider( 
                		new Noise3DBaseProvider(
                				new NullNoise(-0.1f)
                				, 0, 1, 0)
                		)

                		
               .addProvider(new Noise3DBaseTemperatureProvider(new SubSampledNoise3D(new SimplexNoise(seed+1)
                		,new Vector3f(0.08f, 0.08f, 0.08f), 4)
                	,0f,1f,0f))
                		
                /*.addProvider(new Noise3DBaseTemperatureSimProvider(
                		new SubSampledNoise3D(new SimplexNoise(seed+1),new Vector3f(0.001f, 0.001f, 0.001f), 4)
                	,0f,1f,0f,
                	10f,40f,0f))*/
                
                .addProvider(new Noise3DBaseHumidityProvider(
                		new SubSampledNoise3D(new SimplexNoise(seed+2)
                		,new Vector3f(0.001f, 0.001f, 0.001f), 4)
                	,0f,1f,0f))
               
                .addProvider(new Noise3DBaseFormProvider(
                		new SubSampledNoise3D(new SimplexNoise(seed+3)
                		,new Vector3f(0.0008f, 0.0008f, 0.0008f), 4)
                	,0f,1000f,0f))
               
                .addProvider(lfp)
                
                //.addProvider(new Noise3DProvider(new HillsFormDefinition(seed),0,1,0))
                	
                .addProvider(new BiomeProvider())
                //.addProvider(densityProv)
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
                	

                		/*
                .addProvider( 
                		new Noise3DProvider(
                				new Scaling3DAdapter(new AreaLimitAdapter(new Noise2DTo3DAdapter(new SimplexNoise(seed)),
                						0f,0f,0,
                						0,0.5f,0,
                						0f,0f,0,
                						1),
                						new Vector3f(0.01f, 0.01f, 0.01f))
                				,0,3,0
                		))*/
                		
                		
                		/*
                .addProvider( 
                		new Noise2DSurfaceProvider(
                				new BrownianNoise2D(new SimplexNoise(seed),8),
                				new Vector3f(0.0005f, 0.0005f, 0.0005f),
                				-0.3f,0.4f,0,1,0.7f
                				, 0, 4, 0)
                		)*/

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
