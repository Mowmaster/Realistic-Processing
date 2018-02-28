package com.mowmaster.rprocessing.tiles;

import com.mowmaster.rprocessing.items.ItemRegistry;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ITickable;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;




public class TileClayBloomery extends TileEntity implements ITickable
{

    private ItemStack oreInBloomery = ItemStack.EMPTY;
    private String oreName = null;
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
    private final int progressMax = 6000;
    private int ticker = 0;
    private int ticker2 = 0;


    public ItemStack getSmeltingOutput(ItemStack input)
    {
        return FurnaceRecipes.instance().getSmeltingResult(input);
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
        /*
        int oreID = OreDictionary.getOreID(oreInBloomery.getUnlocalizedName());
        System.out.println(OreDictionary.getOreName(oreID));
        System.out.println(OreDictionary.getOreName(oreID).toLowerCase().contains("ore") && OreDictionary.getOreName(oreID).toLowerCase().contains("lead"));
        System.out.println(OreDictionary.getOreName(oreID));
         */
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
            //Is this item in hand an "ore"
            int[] oreIds = OreDictionary.getOreIDs(input);
            for(int i=0;i<=oreIds.length-1;i++)
            {
                if(OreDictionary.getOreName(oreIds[i]).contains("ore"))
                {
                    oreInputName = OreDictionary.getOreName(oreIds[i]);
                }
            }
            if(oreInputName.contains("ore")&& !getSmeltingOutput(input).isEmpty())
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
                    oreName = null;
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


    @Override
    public void update() {
        if (airCount >= 750) {
            //world.spawnParticle(EnumParticleTypes.LAVA, pos.getX() + 0.5, pos.getY() + 1.0, pos.getZ() + 0.5, 0.0, 0.0, 0.0, new int[0]);
            world.spawnParticle(EnumParticleTypes.WATER_SPLASH, pos.getX() + 0.5, pos.getY() + 0.9, pos.getZ() + 0.5, 0.2, 0.2, 0.2, new int[0]);
        }
        if (airCount < 750 && airCount >= 500) {
            world.spawnParticle(EnumParticleTypes.FLAME, pos.getX() + 0.4, pos.getY() + 1.1, pos.getZ() + 0.4, 0.001, 0.001, 0.001, new int[0]);
            world.spawnParticle(EnumParticleTypes.FLAME, pos.getX() + 0.4, pos.getY() + 1.0, pos.getZ() + 0.5, 0.001, 0.001, 0.001, new int[0]);
            world.spawnParticle(EnumParticleTypes.FLAME, pos.getX() + 0.5, pos.getY() + 1.0, pos.getZ() + 0.4, 0.001, 0.001, 0.001, new int[0]);
            world.spawnParticle(EnumParticleTypes.FLAME, pos.getX() + 0.5, pos.getY() + 1.1, pos.getZ() + 0.5, 0.001, 0.001, 0.001, new int[0]);
            world.spawnParticle(EnumParticleTypes.FLAME, pos.getX() + 0.6, pos.getY() + 1.0, pos.getZ() + 0.5, 0.001, 0.001, 0.001, new int[0]);
            world.spawnParticle(EnumParticleTypes.FLAME, pos.getX() + 0.5, pos.getY() + 1.0, pos.getZ() + 0.6, 0.001, 0.001, 0.001, new int[0]);
            world.spawnParticle(EnumParticleTypes.FLAME, pos.getX() + 0.6, pos.getY() + 1.1, pos.getZ() + 0.6, 0.001, 0.001, 0.001, new int[0]);

        }
        if (airCount < 500 && airCount >= 250) {
            world.spawnParticle(EnumParticleTypes.SMOKE_LARGE, pos.getX() + 0.5, pos.getY() + 1.0, pos.getZ() + 0.5, 0.001, 0.001, 0.001, new int[0]);
        }
        // && airCount >= 50
        if (airCount < 250) {
            world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, pos.getX() + 0.5, pos.getY() + 1.0, pos.getZ() + 0.5, 0.001, 0.001, 0.001, new int[0]);
        }




        if (running)
        {

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
                        if(heat>=1500)
                        {
                            charcoalCount=oldCharcoal-16;
                        }
                        else if(heat>=1000 && heat<1500)
                        {
                            charcoalCount=oldCharcoal-12;
                        }
                        else if(heat>=500 && heat<1000)
                        {
                            charcoalCount=oldCharcoal-8;
                        }
                        else if(heat>0 && heat<500)
                        {
                            charcoalCount=oldCharcoal-4;
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
                            else
                            {
                                heat--;
                            }
                        }



                    }



                    //second interval
                    if(ticker>=20)
                    {
                        ticker=0;

                        if(progress<progressMax)
                        {
                            if(oreName.contains("oreTin") && heat>250) {progress++;}
                            else if(oreName.contains("orePlatinum") && heat>1750) {progress++;}

                            else if(oreName.contains("oreUranium") && heat>1130) {progress++;}
                            else if(oreName.contains("oreNickle") && heat>1450) {progress++;}
                            else if(oreName.contains("oreSilver") && heat>850) {progress++;}
                            else if(oreName.contains("oreLead") && heat>320) {progress++;}
                            else if(oreName.contains("oreAluminum") && heat>660) {progress++;}
                            else if(oreName.contains("oreCopper") && heat>850) {progress++;}

                            else if(oreName.contains("oreGold") && heat>1050) {progress++;}
                            else if(oreName.contains("oreIron") && heat>1250) {progress++;}
                            else {if(heat>200){progress++;}}
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