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
package org.landofterra.world.generator.rasterizers;

import org.terasology.core.world.CoreBiome;
import org.terasology.core.world.generator.facets.BiomeFacet;
import org.terasology.math.Vector2i;
import org.terasology.math.Vector3i;
import org.terasology.registry.CoreRegistry;
import org.terasology.world.block.Block;
import org.terasology.world.block.BlockManager;
import org.terasology.world.chunks.ChunkConstants;
import org.terasology.world.chunks.CoreChunk;
import org.terasology.world.generation.Region;
import org.terasology.world.generation.WorldRasterizer;
import org.terasology.world.generation.facets.DensityFacet;


/**
 * Debug Rasterizer to vizualize generation.
 * Blocks have diferent material depending densities.
 * 0-1 ice
 * 1-10 sand
 * 10-100 dirt
 * 100- stone
 * @author Esereja
 */
public class DebugSolidRasterizer implements WorldRasterizer {

    private Block water;
    private Block ice;
    private Block sand;
    private Block dirt;
    private Block stone;
    
    private boolean sea=true;

    @Override
    public void initialize() {
        BlockManager blockManager = CoreRegistry.get(BlockManager.class);
        water = blockManager.getBlock("core:water");
        ice = blockManager.getBlock("core:Ice");
        sand = blockManager.getBlock("core:Sand");
        dirt = blockManager.getBlock("core:Dirt");
        stone = blockManager.getBlock("core:stone");
    }

    /**
	 * @return the sea
	 */
	public boolean isSea() {
		return sea;
	}

	/**
	 * @param sea the sea to set
	 */
	public void setSea(boolean sea) {
		this.sea = sea;
	}

	@Override
    public void generateChunk(CoreChunk chunk, Region chunkRegion) {
        BiomeFacet biomeFacet = chunkRegion.getFacet(BiomeFacet.class);
        DensityFacet solidityFacet = chunkRegion.getFacet(DensityFacet.class);
        Vector2i pos2d = new Vector2i();
        for (Vector3i pos : ChunkConstants.CHUNK_REGION) {
            pos2d.set(pos.x, pos.z);
            CoreBiome biome = biomeFacet.get(pos2d);
            chunk.setBiome(pos.x, pos.y, pos.z, biome);
            
            float density = solidityFacet.get(pos);
            if (density < 0) {
            	if(sea){
	            	int posY = pos.y + chunk.getChunkWorldOffsetY();
	
	                if (posY == 0 && CoreBiome.SNOW == biomeFacet.get(pos2d)) {
	                    chunk.setBlock(pos, ice);
	                } else if (posY <= 0) {
	                    chunk.setBlock(pos, water);
	                }
                }
            } else if (density < 1) {
                chunk.setBlock(pos, ice);
            }else if (density < 10) {
                chunk.setBlock(pos, sand);
            }else if (density < 100) {
                chunk.setBlock(pos, dirt);
            } else {
            	chunk.setBlock(pos, stone);
            }
        }
    }
    
}