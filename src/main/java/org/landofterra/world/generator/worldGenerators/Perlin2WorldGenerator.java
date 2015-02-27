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
package org.landofterra.world.generator.worldGenerators;

import org.boundlessworlds.utilities.procedural.adapter.FastPerturbationAdapter;
import org.boundlessworlds.utilities.procedural.noise.NullNoise;
import org.boundlessworlds.world.generator.facetProviders.Noise3DBaseProvider;
import org.boundlessworlds.world.generator.facetProviders.Noise3DTerainProvider;
import org.boundlessworlds.world.generator.rasterizers.BW2DefaultRasterizer;
import org.terasology.core.world.generator.facetProviders.BiomeProvider;
import org.terasology.core.world.generator.facetProviders.DefaultFloraProvider;
import org.terasology.core.world.generator.facetProviders.EnsureSpawnableChunkZeroProvider;
import org.terasology.core.world.generator.facetProviders.PerlinBaseSurfaceProvider;
import org.terasology.core.world.generator.facetProviders.PerlinHillsAndMountainsProvider;
import org.terasology.core.world.generator.facetProviders.PerlinHumidityProvider;
import org.terasology.core.world.generator.facetProviders.PerlinOceanProvider;
import org.terasology.core.world.generator.facetProviders.PerlinRiverProvider;
import org.terasology.core.world.generator.facetProviders.PerlinSurfaceTemperatureProvider;
import org.terasology.core.world.generator.facetProviders.SeaLevelProvider;
import org.terasology.core.world.generator.facetProviders.SurfaceToDensityProvider;
import org.terasology.core.world.generator.facetProviders.DefaultTreeProvider;
import org.terasology.core.world.generator.facetProviders.World2dPreviewProvider;
import org.terasology.core.world.generator.rasterizers.FloraRasterizer;
import org.terasology.core.world.generator.rasterizers.SolidRasterizer;
import org.terasology.core.world.generator.rasterizers.TreeRasterizer;
import org.terasology.engine.SimpleUri;
import org.terasology.math.geom.Vector3f;
import org.terasology.utilities.procedural.BrownianNoise3D;
import org.terasology.utilities.procedural.SimplexNoise;
import org.terasology.world.generation.BaseFacetedWorldGenerator;
import org.terasology.world.generation.WorldBuilder;
import org.terasology.world.generator.RegisterWorldGenerator;

/**
 * @author Immortius
 */
@RegisterWorldGenerator(id = "facetedperlin", displayName = "PerlinReloaded", description = "Faceted world generator")
public class Perlin2WorldGenerator extends BaseFacetedWorldGenerator {

    public Perlin2WorldGenerator(SimpleUri uri) {
        super(uri);
    }

    @Override
    protected WorldBuilder createWorld(long seed) {
        return new WorldBuilder(seed)
                .addProvider(new World2dPreviewProvider())
                .addProvider(new SeaLevelProvider())
                .addProvider(new PerlinHumidityProvider())
                .addProvider(new PerlinSurfaceTemperatureProvider())
                .addProvider(new PerlinBaseSurfaceProvider())
                .addProvider(new PerlinRiverProvider())
                .addProvider(new PerlinOceanProvider())
                .addProvider(new PerlinHillsAndMountainsProvider())
                .addProvider(new BiomeProvider())
                .addProvider(new SurfaceToDensityProvider())
                .addProvider(new DefaultFloraProvider())
                .addProvider(new DefaultTreeProvider())
                        //.addRasterizer(new GroundRasterizer(blockManager))
                /*.addProvider( 
                		new Noise3DBaseProvider(
                				new NullNoise(0f)
                				, 0, 1, 0)
                		)
                		
                .addProvider( 
                		new Noise3DTerainProvider(
                				new BrownianNoise3D(new SimplexNoise(seed+3),2)
                				,new Vector3f(0.08f, 0.08f, 0.08f),0,1,-0.995)
                		)
                		
                .addRasterizer(new BW2DefaultRasterizer(0, 0.00001f,20f,false,true,false))*/
                
                .addProvider( 
                		new Noise3DBaseProvider(
                				new NullNoise(0f)
                				, 0, 1, 0)
                		)
                		
                .addProvider( 
                		new Noise3DTerainProvider(
                				new BrownianNoise3D(new FastPerturbationAdapter(new SimplexNoise(seed+1),4),2)
                				,new Vector3f(0.0004f, 0.0002f, 0.0004f),0,35,25)
                		)
                
                .addProvider( 
                		new Noise3DTerainProvider(
                				new BrownianNoise3D(new SimplexNoise(seed+3),1)
                				,new Vector3f(0.00005f, 0.00005f, 0.00005f),0,1,0)
                		)
                		
                .addRasterizer(new BW2DefaultRasterizer(0, -280f,-80f,false,false,true))//this converts BW noise to default noise in given value range
                //lets wipe layer clean and create new
                .addProvider( 
                		new Noise3DBaseProvider(
                				new NullNoise(0f)
                				, 0, 1, 0)
                		)
                		
                .addProvider( 
                		new Noise3DTerainProvider(
                				new BrownianNoise3D(new FastPerturbationAdapter(new SimplexNoise(seed+2),4),2)
                				,new Vector3f(0.004f, 0.004f, 0.004f),0,1,0)
                		)
                		
                .addRasterizer(new BW2DefaultRasterizer(0, 15f,115f,false,true,false))//and apply it again
                //lets wipe  again and create new
                .addProvider( 
                		new Noise3DBaseProvider(
                				new NullNoise(0f)
                				, 0, 1, 0)
                		)
                		
                .addProvider( 
                		new Noise3DTerainProvider(
                				new BrownianNoise3D(new SimplexNoise(seed+3),4)
                				,new Vector3f(0.005f, 0.005f, 0.005f),0,1,0.2)
                		)
                		
                .addRasterizer(new BW2DefaultRasterizer(0, 125f,255f,false,true,false))
                
                .addProvider(new EnsureSpawnableChunkZeroProvider())
                .addRasterizer(new SolidRasterizer())
                .addPlugins()

                .addRasterizer(new FloraRasterizer())
                .addRasterizer(new TreeRasterizer());
    }
}
