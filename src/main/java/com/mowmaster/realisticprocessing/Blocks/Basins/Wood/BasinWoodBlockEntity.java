package com.mowmaster.realisticprocessing.Blocks.Basins.Wood;

import com.mowmaster.realisticprocessing.Blocks.Basins.BaseBasinBlockEntity;
import com.mowmaster.realisticprocessing.Registry.DeferredBlockEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

public class BasinWoodBlockEntity extends BaseBasinBlockEntity
{
    private LazyOptional<IItemHandler> handler = LazyOptional.of(this::createHandlerBasin);
    private LazyOptional<IFluidHandler> fluidHandlerInput = LazyOptional.of(this::createHandlerBasinFluidInput);
    private LazyOptional<IFluidHandler> fluidHandlerOutput = LazyOptional.of(this::createHandlerBasinFluidOutput);
    private FluidStack storedFluidInput = FluidStack.EMPTY;
    private FluidStack storedFluidOutput = FluidStack.EMPTY;
    private BasinWoodBlockEntity getBasin() { return this; }

    public BlockPos getPos() { return this.worldPosition; }

    public BasinWoodBlockEntity(BlockPos p_155229_, BlockState p_155230_) {
        super(DeferredBlockEntityTypes.BASIN_WOOD.get(), p_155229_, p_155230_);
    }



    public IItemHandler createHandlerBasin() {
        return new ItemStackHandler(18) {
            @Override
            protected void onLoad() {
                super.onLoad();
            }

            @Override
            protected void onContentsChanged(int slot) {
                update();
            }

            @Override
            public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
                return true;
            }
        };
    }

    public IFluidHandler createHandlerBasinFluidInput() {
        return new IFluidHandler() {

            @Override
            public int getTanks() {
                return 1;
            }

            @NotNull
            @Override
            public FluidStack getFluidInTank(int tank) {
                return storedFluidInput;
            }

            @Override
            public int getTankCapacity(int tank) {
                return 1000;
            }

            @Override
            public boolean isFluidValid(int tank, @NotNull FluidStack stack) {
                return (getFluidInTank(tank).isEmpty())?(true):((getFluidInTank(tank).isFluidEqual(stack))?(true):(false));
            }

            @Override
            public int fill(FluidStack resource, FluidAction action) {

                if(isFluidValid(0,resource))
                {
                    FluidStack stored = storedFluidInput.copy();
                    FluidStack receivingFluid = resource.copy();
                    int storedAmount = stored.getAmount();
                    int receivingAmount = receivingFluid.getAmount();
                    int canReceive = getTankCapacity(0) - getFluidInTank(0).getAmount();

                    if(canReceive>0)
                    {
                        if(action.simulate())
                        {
                            if(receivingAmount>canReceive)return canReceive;
                            else return receivingAmount;
                        }
                        else if(action.execute())
                        {
                            if(receivingFluid.isEmpty())
                            {
                                storedFluidInput = FluidStack.EMPTY;
                            }
                            else if(receivingAmount>canReceive)
                            {
                                receivingFluid.setAmount(canReceive);
                                storedFluidInput = receivingFluid;
                                update();
                                return canReceive;
                            }
                            else
                            {
                                receivingFluid.setAmount(storedAmount+receivingAmount);
                                storedFluidInput = receivingFluid;
                                update();
                                return receivingAmount;
                            }
                        }
                    }

                }

                return 0;
            }

            @NotNull
            @Override
            public FluidStack drain(FluidStack resource, FluidAction action) {
                FluidStack stored = storedFluidInput.copy();
                if(stored.isFluidEqual(resource))
                {
                    int storedAmount = stored.getAmount();
                    int receivingAmount = resource.getAmount();
                    if(action.simulate())
                    {
                        if(receivingAmount>storedAmount)
                        {
                            return new FluidStack(resource.getFluid(),storedAmount,resource.getTag());
                        }
                        return resource;
                    }
                    else if(action.execute())
                    {
                        if(receivingAmount>storedAmount)
                        {
                            storedFluidInput = FluidStack.EMPTY;
                            update();
                            return new FluidStack(resource.getFluid(),storedAmount,resource.getTag());
                        }

                        FluidStack newStack = storedFluidInput.copy();
                        newStack.setAmount(storedAmount-receivingAmount);
                        storedFluidInput = newStack;
                        update();
                        return resource;
                    }
                }

                return FluidStack.EMPTY;
            }

            @NotNull
            @Override
            public FluidStack drain(int maxDrain, FluidAction action) {
                FluidStack stored = storedFluidInput.copy();
                int storedAmount = stored.getAmount();
                int receivingAmount = maxDrain;
                if(action.simulate())
                {
                    if(receivingAmount>storedAmount)
                    {
                        return stored;
                    }
                    return new FluidStack(stored.getFluid(),receivingAmount,stored.getTag());
                }
                else if(action.execute())
                {
                    if(receivingAmount>storedAmount)
                    {
                        storedFluidInput = FluidStack.EMPTY;
                        update();
                        return stored;
                    }

                    FluidStack newStack = stored.copy();
                    newStack.setAmount(storedAmount-receivingAmount);
                    storedFluidInput = newStack;
                    update();
                    return new FluidStack(stored.getFluid(),receivingAmount,stored.getTag());
                }

                return FluidStack.EMPTY;
            }
        };
    }

    public IFluidHandler createHandlerBasinFluidOutput() {
        return new IFluidHandler() {

            @Override
            public int getTanks() {
                return 1;
            }

            @NotNull
            @Override
            public FluidStack getFluidInTank(int tank) {
                return storedFluidOutput;
            }

            @Override
            public int getTankCapacity(int tank) {
                return 1000;
            }

            @Override
            public boolean isFluidValid(int tank, @NotNull FluidStack stack) {
                return (getFluidInTank(tank).isEmpty())?(true):((getFluidInTank(tank).isFluidEqual(stack))?(true):(false));
            }

            @Override
            public int fill(FluidStack resource, FluidAction action) {

                if(isFluidValid(0,resource))
                {
                    FluidStack stored = storedFluidOutput.copy();
                    FluidStack receivingFluid = resource.copy();
                    int storedAmount = stored.getAmount();
                    int receivingAmount = receivingFluid.getAmount();
                    int canReceive = getTankCapacity(0) - getFluidInTank(0).getAmount();

                    if(canReceive>0)
                    {
                        if(action.simulate())
                        {
                            if(receivingAmount>canReceive)return canReceive;
                            else return receivingAmount;
                        }
                        else if(action.execute())
                        {
                            if(receivingFluid.isEmpty())
                            {
                                storedFluidOutput = FluidStack.EMPTY;
                            }
                            else if(receivingAmount>canReceive)
                            {
                                receivingFluid.setAmount(canReceive);
                                storedFluidOutput = receivingFluid;
                                update();
                                return canReceive;
                            }
                            else
                            {
                                receivingFluid.setAmount(storedAmount+receivingAmount);
                                storedFluidOutput = receivingFluid;
                                update();
                                return receivingAmount;
                            }
                        }
                    }

                }

                return 0;
            }

            @NotNull
            @Override
            public FluidStack drain(FluidStack resource, FluidAction action) {
                FluidStack stored = storedFluidOutput.copy();
                if(stored.isFluidEqual(resource))
                {
                    int storedAmount = stored.getAmount();
                    int receivingAmount = resource.getAmount();
                    if(action.simulate())
                    {
                        if(receivingAmount>storedAmount)
                        {
                            return new FluidStack(resource.getFluid(),storedAmount,resource.getTag());
                        }
                        return resource;
                    }
                    else if(action.execute())
                    {
                        if(receivingAmount>storedAmount)
                        {
                            storedFluidOutput = FluidStack.EMPTY;
                            update();
                            return new FluidStack(resource.getFluid(),storedAmount,resource.getTag());
                        }

                        FluidStack newStack = storedFluidOutput.copy();
                        newStack.setAmount(storedAmount-receivingAmount);
                        storedFluidOutput = newStack;
                        update();
                        return resource;
                    }
                }

                return FluidStack.EMPTY;
            }

            @NotNull
            @Override
            public FluidStack drain(int maxDrain, FluidAction action) {
                FluidStack stored = storedFluidOutput.copy();
                int storedAmount = stored.getAmount();
                int receivingAmount = maxDrain;
                if(action.simulate())
                {
                    if(receivingAmount>storedAmount)
                    {
                        return stored;
                    }
                    return new FluidStack(stored.getFluid(),receivingAmount,stored.getTag());
                }
                else if(action.execute())
                {
                    if(receivingAmount>storedAmount)
                    {
                        storedFluidOutput = FluidStack.EMPTY;
                        update();
                        return stored;
                    }

                    FluidStack newStack = stored.copy();
                    newStack.setAmount(storedAmount-receivingAmount);
                    storedFluidOutput = newStack;
                    update();
                    return new FluidStack(stored.getFluid(),receivingAmount,stored.getTag());
                }

                return FluidStack.EMPTY;
            }
        };
    }

    /*============================================================================
    ==============================================================================
    ===========================    FLUID  START      =============================
    ==============================================================================
    ============================================================================*/

    //Input Fluid Stuff
    public boolean hasFluidInput()
    {
        IFluidHandler h = fluidHandlerInput.orElse(null);
        if(!h.getFluidInTank(0).isEmpty())return true;
        return false;
    }

    public FluidStack getStoredFluidInput()
    {
        IFluidHandler h = fluidHandlerInput.orElse(null);
        if(hasFluidInput())return h.getFluidInTank(0);
        return FluidStack.EMPTY;
    }

    public int getFluidCapacityInput()
    {
        IFluidHandler h = fluidHandlerInput.orElse(null);
        return h.getTankCapacity(0);
    }

    public int spaceForFluidInput()
    {
        return getFluidCapacityInput() - getStoredFluidInput().getAmount();
    }

    public boolean canAcceptFluidInput(FluidStack fluidStackIn)
    {
        IFluidHandler h = fluidHandlerInput.orElse(null);
        return h.isFluidValid(0,fluidStackIn);
    }

    public FluidStack removeFluidInput(FluidStack fluidToRemove, IFluidHandler.FluidAction action)
    {
        IFluidHandler h = fluidHandlerInput.orElse(null);
        return h.drain(fluidToRemove,action);
    }

    public FluidStack removeFluidInput(int fluidAmountToRemove, IFluidHandler.FluidAction action)
    {
        IFluidHandler h = fluidHandlerInput.orElse(null);
        return h.drain(new FluidStack(getStoredFluidInput().getFluid(),fluidAmountToRemove,getStoredFluidInput().getTag()),action);
    }

    public int addFluidInput(FluidStack fluidStackIn, IFluidHandler.FluidAction fluidAction)
    {
        IFluidHandler h = fluidHandlerInput.orElse(null);
        return h.fill(fluidStackIn,fluidAction);
    }




    //Output Fluid Stuff
    public boolean hasFluidOutput()
    {
        IFluidHandler h = fluidHandlerInput.orElse(null);
        if(!h.getFluidInTank(0).isEmpty())return true;
        return false;
    }

    public FluidStack getStoredFluidOutput()
    {
        IFluidHandler h = fluidHandlerInput.orElse(null);
        if(hasFluidInput())return h.getFluidInTank(0);
        return FluidStack.EMPTY;
    }

    public int getFluidCapacityOutput()
    {
        IFluidHandler h = fluidHandlerInput.orElse(null);
        return h.getTankCapacity(0);
    }

    public int spaceForFluidOutput()
    {
        return getFluidCapacityOutput() - getStoredFluidOutput().getAmount();
    }

    public boolean canAcceptFluidOutput(FluidStack fluidStackIn)
    {
        IFluidHandler h = fluidHandlerOutput.orElse(null);
        return h.isFluidValid(0,fluidStackIn);
    }

    public FluidStack removeFluidOutput(FluidStack fluidToRemove, IFluidHandler.FluidAction action)
    {
        IFluidHandler h = fluidHandlerOutput.orElse(null);
        return h.drain(fluidToRemove,action);
    }

    public FluidStack removeFluidOutput(int fluidAmountToRemove, IFluidHandler.FluidAction action)
    {
        IFluidHandler h = fluidHandlerOutput.orElse(null);
        return h.drain(new FluidStack(getStoredFluidOutput().getFluid(),fluidAmountToRemove,getStoredFluidOutput().getTag()),action);
    }

    public int addFluidOutput(FluidStack fluidStackIn, IFluidHandler.FluidAction fluidAction)
    {
        IFluidHandler h = fluidHandlerOutput.orElse(null);
        return h.fill(fluidStackIn,fluidAction);
    }

    /*============================================================================
    ==============================================================================
    ===========================     FLUID  END       =============================
    ==============================================================================
    ============================================================================*/


    /*============================================================================
    ==============================================================================
    ===========================    ITEM  START       =============================
    ==============================================================================
    ============================================================================*/

    public ItemStack addItemToBasin(ItemStack incomingStack, boolean simulate)
    {
        IItemHandler h = handler.orElse(null);

        if (h == null || incomingStack.isEmpty())
            return incomingStack;

        //Half the slots are inputs half are outputs, only allow inserting to inputs
        for (int i = 0; i < 9; i++)
        {
            incomingStack = h.insertItem(i, incomingStack, simulate);
            if(!simulate)System.out.println("Added Stack" + incomingStack);
            if (incomingStack.isEmpty())
            {
                return ItemStack.EMPTY;
            }
        }

        return incomingStack;
    }

    public ItemStack removeItemFromBasin(int amount, boolean simulate)
    {
        IItemHandler h = handler.orElse(null);
        ItemStack stackToExtract = ItemStack.EMPTY;

        if (h == null)
            return ItemStack.EMPTY;

        //Pull from output slots first, then inputs, in reverse order.
        for (int i = h.getSlots()-1; i >= 0; i--)
        {
            stackToExtract = h.extractItem(i,amount,simulate);
            if (stackToExtract.isEmpty())
            {
                return ItemStack.EMPTY;
            }
        }

        return stackToExtract;
    }

    public ItemStack removeItemFromBasin( boolean simulate)
    {
        IItemHandler h = handler.orElse(null);
        ItemStack stackToExtract = ItemStack.EMPTY;

        if (h == null)
            return ItemStack.EMPTY;

        //Pull from output slots first, then inputs, in reverse order.
        for (int i = h.getSlots()-1; i >= 0; i--)
        {
            stackToExtract = h.extractItem(i,h.getStackInSlot(i).getCount(),simulate);
            if (stackToExtract.isEmpty())
            {
                return ItemStack.EMPTY;
            }
        }

        return stackToExtract;
    }

    public List<ItemStack> getStoredItems()
    {
        IItemHandler h = handler.orElse(null);
        List<ItemStack> stacksList = new ArrayList<>();
        for(int i=0; i<h.getSlots();i++)
        {
            if(!h.getStackInSlot(i).isEmpty()) stacksList.add(h.getStackInSlot(i));
        }

        return stacksList;
    }


    /*============================================================================
    ==============================================================================
    ===========================     ITEM  END        =============================
    ==============================================================================
    ============================================================================*/



    public void tick()
    {



    }





    @Override
    public void load(CompoundTag p_155245_)
    {
        super.load(p_155245_);
        CompoundTag invTag = p_155245_.getCompound("inv");
        handler.ifPresent(h -> ((INBTSerializable<CompoundTag>) h).deserializeNBT(invTag));

        this.storedFluidInput = FluidStack.loadFluidStackFromNBT(p_155245_.getCompound("storedFluidInput"));
        this.storedFluidOutput = FluidStack.loadFluidStackFromNBT(p_155245_.getCompound("storedFluidOutput"));
    }

    /*
    https://discord.com/channels/313125603924639766/915304642668290119/933514186267459658
        When you want to save some BE to something else:
        - saveWithFullMetadata()if you want the full data (includes the position of the block, this may be problematic for certain applications)
        - saveWithId() if you want to be able to reconstruct a BE from this data without knowing beforehand which BE type you need (for example picking up a BE with a special "carrier" item could use this)
        - saveWithoutMetadata() if you only need the actual data but not the BE type or position
     */

    @Override
    public CompoundTag save(CompoundTag p_58888_)
    {
        handler.ifPresent(h -> {
            CompoundTag compound = ((INBTSerializable<CompoundTag>) h).serializeNBT();
            p_58888_.put("inv", compound);
        });
        p_58888_.put("storedFluidInput",storedFluidInput.writeToNBT(new CompoundTag()));
        p_58888_.put("storedFluidOutput",storedFluidOutput.writeToNBT(new CompoundTag()));

        return p_58888_;
    }

    @Override
    public void setRemoved() {
        super.setRemoved();
        if(this.handler != null) {
            this.handler.invalidate();
        }
        if(this.fluidHandlerInput != null) {
            this.fluidHandlerInput.invalidate();
        }
        if(this.fluidHandlerOutput != null) {
            this.fluidHandlerOutput.invalidate();
        }
    }
}
