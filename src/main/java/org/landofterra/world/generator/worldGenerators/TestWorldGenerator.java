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

import org.boundlessworlds.utilities.procedural.noise.NullNoise;
import org.boundlessworlds.world.InfGenBiome;
import org.boundlessworlds.world.generator.facetProviders.LandFormProvider;
import org.boundlessworlds.world.generator.facetProviders.Noise2DSurfaceProvider;
import org.boundlessworlds.world.generator.facetProviders.Noise3DBaseFormProvider;
import org.boundlessworlds.world.generator.facetProviders.Noise3DBaseHumidityProvider;
import org.boundlessworlds.world.generator.facetProviders.Noise3DBaseProvider;
import org.boundlessworlds.world.generator.facetProviders.Noise3DBaseTemperatureProvider;
import org.boundlessworlds.world.generator.facetProviders.SimpleBiomeProvider;
import org.boundlessworlds.world.generator.facetProviders.SimplePlanetSimulatorProvider;
import org.boundlessworlds.world.generator.landFormDefinitions.FillFormDefinition;
import org.boundlessworlds.world.generator.landFormDefinitions.VoidFormDefinition;
import org.boundlessworlds.world.generator.rasterizers.DebugSolidRasterizer;
import org.landofterra.world.generator.landFormDefinitions.DevilsToothFormDefinition;
import org.landofterra.world.generator.landFormDefinitions.LowSkyFormDefinition;
import org.landofterra.world.generator.landFormDefinitions.NearSurface2Definition;
import org.landofterra.world.generator.landFormDefinitions.NearSurface3Definition;
import org.landofterra.world.generator.landFormDefinitions.NearSurfaceDefinition;
import org.landofterra.world.generator.landFormDefinitions.SkyFormDefinition;
import org.landofterra.world.generator.landFormDefinitions.UndergroundDefinition;
import org.terasology.engine.SimpleUri;
import org.terasology.math.geom.Vector3f;
import org.terasology.utilities.procedural.BrownianNoise2D;
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
    	SimplePlanetSimulatorProvider planetsim =new SimplePlanetSimulatorProvider();
    	planetsim.setOrigo(-200);
    	planetsim.setUpHeightMultiplifier(0.0007f);
    	planetsim.setUpDensityFunction(8);
    	planetsim.setDownHeightMultiplifier(0.008f);
    	planetsim.setDownDensityFunction(7);
    	planetsim.setDensityMultifier(10);
    	planetsim.setDensityFunction(1);
    	//planetsim.setDebug(true);
    	
    	
    	LandFormProvider lfp= new LandFormProvider(0, 1, 0);
    	// universal definitions
    	//lfp.addNoise(new PlainFormDefinition(seed+4));
    	//lfp.addNoise(new Plain2FormDefinition(seed+5));
    	//lfp.addNoise(new HillsFormDefinition(seed+5));
    	//lfp.addNoise(new ChaosFormDefinition(seed+6));
    	
    	lfp.addNoise(new FillFormDefinition(seed+7));
    	lfp.addNoise(new UndergroundDefinition(seed+8));
    	//lfp.addNoise(new Underground2Definition(seed+9));
    	//lfp.addNoise(new Underground3Definition(seed+10));
    	lfp.addNoise(new VoidFormDefinition(seed+13));
    	lfp.addNoise(new SkyFormDefinition(seed+14));
    	lfp.addNoise(new LowSkyFormDefinition(seed+15));
    	lfp.addNoise(new NearSurfaceDefinition(seed+11));
    	lfp.addNoise(new NearSurface2Definition(seed+12));
    	lfp.addNoise(new NearSurface3Definition(seed+17));
    	
    	//area specific definitions
    	//lfp.addNoise(new DevilsToothFormDefinition(seed+16));
    	lfp.setDebug(true);
    	
		return new WorldBuilder(seed)
		//base noise bit at negative side for no reason
                .addProvider( 
                		new Noise3DBaseProvider(
                				new NullNoise(-0.1f)
                				, 0, 1, 0)
                		)
                //planet like surface which ends about 3 Kblock height	
                .addProvider( 
                		new Noise2DSurfaceProvider(
                				new BrownianNoise2D(new SimplexNoise(seed),4),
                				new Vector3f(0.00005f, 0.0004f, 0.00005f),
                				-1.f,3f,//offset and width
                				0,1,//ignore and function
                				0.7f,//pre increase
                				0, 1000, 0)
                		)
               //some simplex noise at grand scale to give some more character
                /*.addProvider( 
                		new Noise3DTerainProvider(
                				new BrownianNoise3D(new SimplexNoise(seed+1),2),
                				new Vector3f(0.00015f, 0.00045f, 0.00015f),0,40,0
                				)
                		)*/
               //planet simulation to get planet like results
               .addProvider(planetsim)
		
               .addProvider(new Noise3DBaseTemperatureProvider(
            		   new SubSampledNoise3D(new SimplexNoise(seed+2),
            		   new Vector3f(0.00008f, 0.00008f, 0.00008f), 4)
                	,0f,50f,20f))
                		
                /*.addProvider(new Noise3DBaseTemperatureSimProvider(
                		new SubSampledNoise3D(new SimplexNoise(seed+1),new Vector3f(0.001f, 0.001f, 0.001f), 4)
                	,0f,1f,0f,
                	10f,40f,0f))*/
                
                .addProvider(new Noise3DBaseHumidityProvider(
                		new SubSampledNoise3D(new SimplexNoise(seed+3)
                		,new Vector3f(0.0005f, 0.0005f, 0.0005f), 4)
                	,0f,1f,0f))
               
                .addProvider(new Noise3DBaseFormProvider(
                		new SubSampledNoise3D(new SimplexNoise(seed+4)
                		,new Vector3f(0.00008f, 0.00008f, 0.00008f), 4)
                	,0f,1000f,0f))
               
                //land form generation uses all noises created before and creates world based on them
                .addProvider(lfp)
                .addProvider(planetsim)
                
                //.addProvider(new Noise3DProvider(new HillsFormDefinition(seed),0,1,0))
                	
                .addProvider(new SimpleBiomeProvider(InfGenBiome.DEFAULT))
                .addRasterizer(new DebugSolidRasterizer(-10,false))
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
