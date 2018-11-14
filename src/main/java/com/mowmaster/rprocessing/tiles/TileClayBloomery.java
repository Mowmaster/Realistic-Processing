package com.mowmaster.rprocessing.tiles;

import com.mowmaster.rprocessing.items.ItemRegistry;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ITickable;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;

import javax.annotation.Nonnull;
import java.util.Random;


public class TileClayBloomery extends TileEntity implements ITickable
{

    private ItemStack oreInBloomery = ItemStack.EMPTY;
    private String oreName = "";
    private int oreCount = 0;
    private final int oreMax = 8;
    private int charcoalCount = 0;
    private final int charcoalMax = 25600;
    private int airCount = 0;
    private final int airMax = 1000;
    private int heat = 0;
    private final int heatMax = 2000;
    public boolean running = false;
    private int progress = 0;
    private int cold = 0;
    private final int progressMax = 300;
    private int ticker = 0;
    private int ticker2 = 0;
    private int itemsOutputCount = 0;
    private int oreOutputCount = 0;


    public void resetTile()
    {
        oreInBloomery = ItemStack.EMPTY;
        oreName = "";
        charcoalCount = 0;
        airCount=0;
        heat=0;
        running=false;
        progress=0;
        cold=0;
        itemsOutputCount=0;
        oreOutputCount=0;
    }

    public ItemStack getSmeltingOutput(ItemStack input)
    {
        return FurnaceRecipes.instance().getSmeltingResult(input);
    }


    public ItemStack getNuggetsIfAvailable(ItemStack input)
    {
        InventoryCrafting craft = new InventoryCrafting(new Container()
        {
            @Override
            public boolean canInteractWith(@Nonnull EntityPlayer player) {
                return false;
            }
        }, 1, 1);

        craft.setInventorySlotContents(0, input);

        for(IRecipe recipe : ForgeRegistries.RECIPES)
        {
            if(recipe.matches(craft, world)) {

                return recipe.getCraftingResult(craft);
            }
        }

        return ItemStack.EMPTY;
    }

    public int getSmeltedOut() {return itemsOutputCount;}

    public int getOreReturned() {return oreOutputCount;}

    public int getCharcoalReturned(){return Math.round(charcoalCount/1600);}

    public int getCharcoalBitsReturned()
    {
        int leftover=charcoalCount;
        int drop=0;

        while(leftover>=1600)
        {
            int oldleftover = leftover;
            leftover = oldleftover - 1600;
        }

        if (leftover>=200)
        {
            drop = Math.round(leftover/200);
        }
        return drop;
    }

    public String getOreName(){return oreName;}

    public int getOreCount()
    {
        return oreCount;
    }

    public int getAirCount()
    {
        return airCount;
    }

    public int getCharcoalCount()
    {
        return charcoalCount;
    }

    public int getHeat()
    {
        return heat;
    }

    public boolean getRunning(){return running;}

    public int getCold()
    {
        return cold;
    }

    public int getProgress()
    {
        return progress;
    }

    public ItemStack getOreItemInBlock()
    {
        return oreInBloomery;
    }

    public String getItemInBlock()
    {
        return oreInBloomery.getDisplayName();
    }

    public String getAnswer()
    {
        int[] oreIds = OreDictionary.getOreIDs(oreInBloomery);
        for(int i=0;i<=oreIds.length-1;i++)
        {
            if(OreDictionary.getOreName(oreIds[i]).contains("ore"))
            {
                oreName = OreDictionary.getOreName(oreIds[i]);
            }
        }
        return oreName;
    }

    public boolean isOre(ItemStack input)
    {
        int[] oreIds = OreDictionary.getOreIDs(input);
        for(int i=0;i<=oreIds.length-1;i++)
        {
            if(OreDictionary.getOreName(oreIds[i]).contains("ore"))
            {
                return true;
            }
        }
        return false;
    }

    public boolean isIngot(ItemStack input)
    {
        ItemStack getIngot = FurnaceRecipes.instance().getSmeltingResult(input);
        int[] oreIds = OreDictionary.getOreIDs(getIngot);
        for(int i=0;i<=oreIds.length-1;i++)
        {
            if(OreDictionary.getOreName(oreIds[i]).contains("ingot"))
            {
                return true;
            }
        }
        return false;
    }

    public void updateBlock()
    {
        markDirty();
        IBlockState state = world.getBlockState(pos);
        world.notifyBlockUpdate(pos, state, state, 3);
    }

    public void penaltySmeltingProgress()
    {
        if(progress<750)
        {
            progress=0;
        }
        else
        {
            int oldProgress = progress;
            progress = oldProgress - 750;
        }
    }

    public void penaltyHeat()
    {
        //10% penality
        int oldHeat = heat;
        if(heat<500)
        {
            heat = oldHeat-50;
        }
        else if(heat<=500 && heat<1000)
        {
            heat = oldHeat-100;
        }
        else if(heat<=1000 && heat<1500)
        {
            heat = oldHeat-150;
        }
        else if(heat<=1500)
        {
            heat = oldHeat-200;
        }
    }

    public Boolean addOre(ItemStack input)
    {
        String oreInputName = "";
        //Is Hand NOT Empty?
        if(!input.isEmpty())
        {
            if(isOre(input))
            {
                if(isIngot(input))
                {
                    //Currently no ores in bloomery?
                    if(oreInBloomery.isEmpty())
                    {
                        oreInBloomery = input;
                        oreName = getAnswer();
                        oreCount++;
                        input.shrink(1);
                        updateBlock();
                        return true;
                    }
                    //Ores In Bloomery and input item matches currently inside
                    else if(input.getItem().equals(oreInBloomery.getItem()) && oreCount<oreMax)
                    {
                        if(running){penaltySmeltingProgress();penaltyHeat();}
                        oreCount++;
                        input.shrink(1);
                        updateBlock();
                        return true;
                    }
                    else
                    {
                        return false;
                    }
                }
            }
            return false;
        }
        return false;
    }

    public ItemStack removeOre(EntityPlayer player)
    {
        ItemStack droppedItem = ItemStack.EMPTY;
        if(!running)
        {
            if (oreCount<=1)
            {
                if(oreInBloomery.getMetadata()!=0)
                {
                    droppedItem = new ItemStack(oreInBloomery.getItem(),1,oreInBloomery.getMetadata());
                    oreCount = 0;
                    oreInBloomery = ItemStack.EMPTY;
                    updateBlock();
                }
                else
                {
                    droppedItem = new ItemStack(oreInBloomery.getItem(),1);
                    oreCount = 0;
                    oreInBloomery = ItemStack.EMPTY;
                    oreName = "";
                    updateBlock();
                }
            }
            else if (oreCount >1)
            {
                if(oreInBloomery.getMetadata()!=0)
                {
                    droppedItem = new ItemStack(oreInBloomery.getItem(),1,oreInBloomery.getMetadata());
                    oreCount--;
                    updateBlock();
                }
                else
                {
                    droppedItem = new ItemStack(oreInBloomery.getItem(),1);
                    oreCount--;
                    updateBlock();
                }
            }
        }
        return droppedItem;
    }

    public Boolean addCharcoal(ItemStack input)
    {
        if(!input.isEmpty()) {
            //Is this item in hand an "ore"
            int oreID = OreDictionary.getOreID(input.getUnlocalizedName());
            if (OreDictionary.getOreName(oreID).contains("charcoal"))
            {

                //Items.Coal doesnt have a fuel value so had to set it in FuelTypes
                int fuel = GameRegistry.getFuelValue(input);
                if(charcoalCount + fuel <= charcoalMax)
                {
                    int oldCharcoalCount = charcoalCount;
                    charcoalCount = oldCharcoalCount + fuel;
                    input.shrink(1);
                    cold=0;
                    updateBlock();
                }

            }

        }
        return false;
    }

    //when player uses shovel on block
    public ItemStack removeCharcoal()
    {
        ItemStack droppedItem = ItemStack.EMPTY;
        if(!running)
        {
            if(charcoalCount >= 1600)
            {
                droppedItem = new ItemStack(Items.COAL,1,1);
                int oldCharcoalCount = charcoalCount;
                charcoalCount = oldCharcoalCount - 1600;
                updateBlock();
            }
            else if(charcoalCount < 1600 && charcoalCount >= 200)
            {
                droppedItem = new ItemStack(ItemRegistry.charcoalChunk,1);
                int oldCharcoalCount = charcoalCount;
                charcoalCount = oldCharcoalCount - 200;
                updateBlock();
            }
        }
        return droppedItem;

    }

    //when player uses reeds on block
    public Boolean addAir()
    {
        if(airCount<airMax)
        {
            int oldAir = airCount;
            airCount = oldAir + 50;
            cold=0;
            updateBlock();
            world.spawnParticle(EnumParticleTypes.SWEEP_ATTACK, pos.getX() + 0.5, pos.getY() + 0.9, pos.getZ() + 0.5, 0.2, 0.2, 0.2, new int[0]);
            return true;
        }
        return false;
    }

    //while running
    public void removeAir()
    {
        if(airCount>0)
        {
            airCount--;
            updateBlock();
        }
    }

    //when struck with flint and steel
    public boolean startProgress()
    {
        if(!running)
        {
            running=true;
            updateBlock();
            world.spawnParticle(EnumParticleTypes.FIREWORKS_SPARK, pos.getX() + 0.5, pos.getY() + 0.9, pos.getZ() + 0.5, 0.2, 0.2, 0.2, new int[0]);
            return true;
        }
        else
        {
            return false;
        }
    }

    public void stopProgress()
    {
        running=false;
        cold=0;
        heat=0;
        updateBlock();
    }

    public void getOutputAmount()
    {
        Random rn = new Random();
        int drop =0;
        int addedCount=0;
        int oreOut = 0;


        if(progress>0 && progress<=75)
        {
            oreOut = Math.round(oreCount* (float)0.25);
            for(int i=1;i<oreOut;i++)
            {
                drop = rn.nextInt(100);
                if(drop<=1)
                {
                    addedCount++;
                }
            }
        }
        else if(progress>75 && progress<=150)
        {
            oreOut = Math.round(oreCount* (float)0.5);
            for(int i=1;i<oreOut;i++)
            {
                drop = rn.nextInt(100);
                if(drop<=5)
                {
                    addedCount++;
                }
            }
        }
        else if(progress>150 && progress<=225)
        {
            oreOut = Math.round(oreCount* (float)0.75);
            for(int i=1;i<oreOut;i++)
            {
                drop = rn.nextInt(100);
                if(drop<=25)
                {
                    addedCount++;
                }
            }
        }
        else if(progress>225 && progress<=299)
        {
            oreOut = oreCount;
            for(int i=1;i<oreOut;i++)
            {
                drop = rn.nextInt(100);
                if(drop<=50)
                {
                    addedCount++;
                }
            }
        }
        else if(progress>=300)
        {
            oreOut = oreCount;
            for(int i=1;i<oreOut;i++)
            {
                drop = rn.nextInt(100);
                if(drop<=75)
                {
                    addedCount++;
                }
            }
        }

        itemsOutputCount = oreCount+addedCount;
        oreOutputCount = oreCount-oreOut;

    }



    @Override
    public void update() {





        if (running)
        {


            if (charcoalCount <=0 && oreCount>0) {
                world.spawnParticle(EnumParticleTypes.CRIT, pos.getX() + 0.125, pos.getY() + 1.25, pos.getZ() + 0.125, 0.0, 0.0, 0.0, new int[0]);
            }
            else if (charcoalCount <=1600 && charcoalCount>0 && oreCount>0) {
                world.spawnParticle(EnumParticleTypes.CRIT_MAGIC, pos.getX() + 0.125, pos.getY() + 1.25, pos.getZ() + 0.125, 0.0, 0.0, 0.0, new int[0]);
            }

            if (airCount >= 750) {
                world.spawnParticle(EnumParticleTypes.LAVA, pos.getX() + 0.5, pos.getY() + 1.0, pos.getZ() + 0.5, 0.0, 0.0, 0.0, new int[0]);

            }
            else if (airCount < 750 && airCount >= 500) {
                world.spawnParticle(EnumParticleTypes.FLAME, pos.getX() + 0.4, pos.getY() + 1.1, pos.getZ() + 0.4, 0.001, 0.001, 0.001, new int[0]);
                world.spawnParticle(EnumParticleTypes.FLAME, pos.getX() + 0.4, pos.getY() + 1.0, pos.getZ() + 0.5, 0.001, 0.001, 0.001, new int[0]);
                world.spawnParticle(EnumParticleTypes.FLAME, pos.getX() + 0.5, pos.getY() + 1.0, pos.getZ() + 0.4, 0.001, 0.001, 0.001, new int[0]);
                world.spawnParticle(EnumParticleTypes.FLAME, pos.getX() + 0.5, pos.getY() + 1.1, pos.getZ() + 0.5, 0.001, 0.001, 0.001, new int[0]);
                world.spawnParticle(EnumParticleTypes.FLAME, pos.getX() + 0.6, pos.getY() + 1.0, pos.getZ() + 0.5, 0.001, 0.001, 0.001, new int[0]);
                world.spawnParticle(EnumParticleTypes.FLAME, pos.getX() + 0.5, pos.getY() + 1.0, pos.getZ() + 0.6, 0.001, 0.001, 0.001, new int[0]);
                world.spawnParticle(EnumParticleTypes.FLAME, pos.getX() + 0.6, pos.getY() + 1.1, pos.getZ() + 0.6, 0.001, 0.001, 0.001, new int[0]);

            }
            else if (airCount < 500 && airCount >= 250) {
                world.spawnParticle(EnumParticleTypes.SMOKE_LARGE, pos.getX() + 0.5, pos.getY() + 1.0, pos.getZ() + 0.5, 0.001, 0.001, 0.001, new int[0]);
            }

            else if (airCount < 250 && airCount >= 25) {
                world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, pos.getX() + 0.5, pos.getY() + 1.0, pos.getZ() + 0.5, 0.001, 0.001, 0.001, new int[0]);
            }




            if(!world.isRemote)
            {
                if(cold>=200)
                {
                    stopProgress();
                }

                if(oreCount>0)
                {


                    ticker++;
                    ticker2++;
                    if(heat==0) {cold++;}
                    if(airCount>0) {removeAir();}//1000units lasts for 50 seconds roughly
                    if(charcoalCount>0)
                    {
                        int oldCharcoal = charcoalCount;
                        int charcoalBurned = Math.round(heat * (float) 0.003);
                        if(heat>0)
                        {
                            charcoalCount=oldCharcoal-charcoalBurned;
                        }
                    }

                    if(ticker2>=2)
                    {
                        ticker2=0;
                        if(heat<=2000)
                        {
                            if(getAirCount()>0 && getCharcoalCount()>0)
                            {
                                heat++;
                            }
                        }
                        if(getAirCount()<=0 || getCharcoalCount()<=0)
                        {
                            if(getHeat()>0)
                            {
                                heat--;
                                updateBlock();
                            }

                        }

                    }



                    //second interval
                    if(ticker>=20)
                    {
                        ticker=0;

                        if(progress<progressMax)
                        {
                            if(oreName.contains("oreTin") && heat>200) {progress++;}
                            else if(oreName.contains("orePlatinum") && heat>1800) {progress++;}

                            else if(oreName.contains("oreUranium") && heat>1400) {progress++;}
                            else if(oreName.contains("oreNickle") && heat>1400) {progress++;}
                            else if(oreName.contains("oreSilver") && heat>800) {progress++;}
                            else if(oreName.contains("oreLead") && heat>400) {progress++;}
                            else if(oreName.contains("oreAluminum") && heat>600) {progress++;}
                            else if(oreName.contains("oreCopper") && heat>800) {progress++;}

                            else if(oreName.contains("oreGold") && heat>1000) {progress++;}
                            else if(oreName.contains("oreIron") && heat>1200) {progress++;}

                            else if(oreName.contains("oreCoal") && heat>200) {progress++;}
                            else if(oreName.contains("oreQuartz") && heat>1200) {progress++;}
                            else if(oreName.contains("oreRedstone") && heat>200) {progress++;}
                            else if(oreName.contains("oreLapis") && heat>200) {progress++;}
                            else if(oreName.contains("oreDiamond") && heat>200) {progress++;}
                            else if(oreName.contains("oreEmerald") && heat>200) {progress++;}
                        }
                    }
                }
            }


        }
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound)
    {
        super.writeToNBT(compound);
        compound.setInteger("orecount",oreCount);
        compound.setInteger("charcoalcount",charcoalCount);
        compound.setInteger("aircount",airCount);
        compound.setInteger("heat",heat);
        compound.setInteger("progress",progress);
        compound.setInteger("cold",cold);
        compound.setInteger("itemsout",itemsOutputCount);
        compound.setInteger("oreitemsout",oreOutputCount);
        compound.setBoolean("running",running);
        compound.setString("name", oreName);
        compound.setTag("item",oreInBloomery.writeToNBT(new NBTTagCompound()));
        return compound;
    }



    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);

        this.oreCount=compound.getInteger("orecount");
        this.charcoalCount=compound.getInteger("charcoalcount");
        this.airCount=compound.getInteger("aircount");
        this.heat=compound.getInteger("heat");
        this.progress=compound.getInteger("progress");
        this.cold=compound.getInteger("cold");
        this.itemsOutputCount=compound.getInteger("itemsout");
        this.oreOutputCount=compound.getInteger("oreitemsout");
        this.running=compound.getBoolean("running");
        this.oreName=compound.getString("name");
        NBTTagCompound itemTag = compound.getCompoundTag("item");
        this.oreInBloomery = new ItemStack(itemTag);

    }


    @Override
    public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
        super.onDataPacket(net, pkt);
        readFromNBT(pkt.getNbtCompound());
    }

    @Override
    public NBTTagCompound getUpdateTag() {
        return writeToNBT(new NBTTagCompound());
    }

    @Override
    public SPacketUpdateTileEntity getUpdatePacket() {
        return new SPacketUpdateTileEntity(pos, 0, getUpdateTag());
    }




}