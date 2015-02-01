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

import org.boundlessworlds.utilities.procedural.adapter.FastPerturbationAdapter;
import org.boundlessworlds.utilities.procedural.texture.RepetiveCubeTextureV2;
import org.boundlessworlds.world.generator.facetProviders.Noise3DBaseTerainProvider;
import org.boundlessworlds.world.generator.facetProviders.Noise3DTerainProvider;
import org.boundlessworlds.world.generator.facetProviders.Perlin3DNoiseProvider;
import org.boundlessworlds.world.generator.facetProviders.SimplePlanetSimulatorProvider;
import org.boundlessworlds.world.generator.rasterizers.InfiniteGenSolidRasterizer;
import org.terasology.core.world.generator.facetProviders.BiomeProvider;
import org.terasology.core.world.generator.facetProviders.PerlinHumidityProvider;
import org.terasology.core.world.generator.facetProviders.PerlinSurfaceTemperatureProvider;
import org.terasology.core.world.generator.facetProviders.SeaLevelProvider;
import org.terasology.engine.SimpleUri;
import org.terasology.math.geom.Vector3f;
import org.terasology.utilities.procedural.BrownianNoise3D;
import org.terasology.utilities.procedural.PerlinNoise;
import org.terasology.utilities.procedural.SimplexNoise;
import org.terasology.world.generation.BaseFacetedWorldGenerator;
import org.terasology.world.generation.WorldBuilder;
import org.terasology.world.generator.RegisterWorldGenerator;

/***
 * This is example generation to show howto use different type facets to create world.
 * @author esereja
 *
 */
@RegisterWorldGenerator(id = "example", displayName = "Example")
public class ExampleWorldGenerator extends BaseFacetedWorldGenerator {

    public ExampleWorldGenerator(SimpleUri uri) {
        super(uri);
    }

    @Override
    protected WorldBuilder createWorld(long seed) {
    	/*
    	 * First of all 3d noise generators(providers) create noise in between -1 - 1. median being 0. 
    	 * so if you have only one layer of terrain provider and you don't change its values by operators, you're 
    	 * going to end up world filled whit nothing(because density values are directly noise values, in this case)
    	 * if you use raterizer that i have written. Because my raterizer era set up in way that values <1 are air.
    	 * 
    	 * Process of generation is next:
    	 * first you create base terrain whit some *BaseTerainProvider (like Noise3DBaseTerainProvider or Siplex3DBaseTerainProvider)
    	 * Normally people prefer first layer to be low detail. like basic shape  but that up to you artistic preference.
    	 * after that you start adding more details or characteristic by adding layers.
    	 * 
    	 * layers must not be BaseTerainProvider cause they over write already exiting data. 
    	 * layers should be 3DTerainProvider type or function layers like NoiseMapFunctions
    	 * you can have as many you those type layers as you want. remember tough that each layer takes computing power.
    	 * 
    	 * noise values are then used in raterizer to decide blocks. I have two rasterizer. Debug version and normal.
    	 * Debug shows density values by choosing blocks depending densities.0-1=ice,1-10 sand and so on look at file for more information.
    	 * 
    	 * after that decorator are used to add extra details like more caves, fauna or buildings. They have their own rasterizers. 
    	 */
    	
    	SimplePlanetSimulatorProvider densityProv =new SimplePlanetSimulatorProvider();
    	densityProv.setOrigoOffSet(+500);
    	densityProv.setUpHeightMultiplifier(0.001f);
    	densityProv.setUpDensityFunction(2);
    	densityProv.setDownHeightMultiplifier(0.008f);
    	densityProv.setDownDensityFunction(7);
    	densityProv.setDensityMultifier(30);
    	densityProv.setDensityFunction(1);
        return new WorldBuilder(seed)
                .addProvider(new SeaLevelProvider(32))
                /*add base by using Noise3DBaseTerainProvider. it takes in 
                 * Noise3DBaseTerainProvider(Noise3D noise,Vector3f zoom,double frequency, double multifier,double increase)
                 * first value is some sort of noise like PerlinNoise
                 * second vector named zoom. remember smaller the zoom values bigger is scaling. 
                 * Means 0.00008f creates gigantic mountains and stuff, and whit 0.1f you wont fit in holes between land mass.
                 * frequency operates modulus, 0 means it is of. it is applied after after multiplication by multifier.
                 * so putting multifier=2 and frequency=1 means you have cut your noise values to three parts.  
                 * cause values are in between -2 - 2 before modulus. this creates some nice cuts 
                 * in created terrain like steep mountain cliffs.
                 * multifier is normally used to control strength of effects of layer. It simply multiplies every noise value.
                 * 
                 * increase is added to noise values last. be careful whit this because it offsets values. 
                 * if your values were originally -1 - 1 and increase is 1 then result is values in between 0 - 2. 
                 * and in normal generation this can mean that your world has no free air, depending how you use it. 
                 * it is useful just know what you are doing. 
                 * 
                 * most terrainProfiders work exactly same, expect they don't take noise as parameter but have it in build.
                 */
                //in next one i have lowered multiplicator to have this effect less to generation. 
                //Because i used fractalic noise which is very textured. Normal you use perlin or simplex 
                //noise as base noise.
                .addProvider( 
                		new Noise3DBaseTerainProvider(new RepetiveCubeTextureV2(seed)
                		,new Vector3f(0.0005f, 0.0005f, 0.0005f),0,0.6,0)
                		)
                //next one uses Perturbation adapter which uses perturbation technique to spice up to noise. 
                //it is bit computationally heavy(4x) so don't use too many octaves whit brownian noise whit it.
                //octaves decide how detailed brownian noise is, and computationally it multiplies cpu time needed.
                .addProvider( 
                		new Noise3DTerainProvider(
                				new BrownianNoise3D(new FastPerturbationAdapter(new SimplexNoise(seed+1),4),2)
                				,new Vector3f(0.004f, 0.004f, 0.004f),0,0.8,0)
                		)
                
                .addProvider( 
                		new Noise3DTerainProvider(
                				new BrownianNoise3D(new PerlinNoise(seed+2),1)
                				,new Vector3f(0.00085f, 0.00085f, 0.00085f),0,1,0)
                		)
                //next two are basic layer, they are easy to use, they create noise whit brownian movement.  	
                //.addProvider(new Simplex3DTerainProvider(2,new Vector3f(0.0025f, 0.01f, 0.0025f),10,0,0.7,0))//this would create odd looking floating planes
                //.addProvider(new Perlin3DTerainProvider(4,new Vector3f(0.00085f, 0.0085f, 0.00085f),9,0,1,0))// this creates big mountains
                
                //these two are simple noise whit out brownian movement, i use them to create low low frequency details.
                //meaning big scale differences.
                .addProvider(new Perlin3DNoiseProvider(5,new Vector3f(0.0012f, 0.0012f, 0.0012f),0,-0.5,0))
                .addProvider(new Perlin3DNoiseProvider(6,new Vector3f(0.0001f, 0.0001f, 0.0001f),0,-0.3,0))
                .addProvider(new PerlinHumidityProvider())
                .addProvider(new PerlinSurfaceTemperatureProvider())
                .addProvider(new BiomeProvider())
                .addProvider(densityProv)
                .addRasterizer(new InfiniteGenSolidRasterizer())
                //.addRasterizer(new InfiniteGenSolidRasterizer())
                .addPlugins();
    }
}
