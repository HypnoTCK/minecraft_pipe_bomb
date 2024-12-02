package com.hypno.bomb;

import com.hypno.bomb.item.ModItems;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.event.player.UseItemCallback;
import net.minecraft.entity.player.PlayerEntity;

import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.Position;
import net.minecraft.world.World;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

public class Bomb implements ModInitializer {
	public static final String MOD_ID = "bomb";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	private static int delayTicks = -1;
	private static PlayerEntity delayedPlayer;
	private static World delayedWorld;

	@Override
	public void onInitialize() {
		ModItems.registerModItems();

		UseItemCallback.EVENT.register((player, world, hand) -> {
			if (hand == Hand.MAIN_HAND || hand == Hand.OFF_HAND) {
				ItemStack itemStack = player.getStackInHand(hand);

				if (!itemStack.isEmpty()) {
					if (itemStack.getItem().toString().equals("mighty_mail:package")) {
						delayedPlayer = player;
						delayedWorld = world;
						delayTicks = 10;
					}
				}
			}

			return TypedActionResult.pass(ItemStack.EMPTY);
		});

		ServerTickEvents.START_SERVER_TICK.register(server -> {
			if (delayTicks > 0) {
				delayTicks--;
			} else if (delayTicks == 0) {
				listInventory(delayedPlayer, delayedPlayer.getPos(), delayedWorld);
				delayTicks = -1;
			}
		});
	}

	private void listInventory(PlayerEntity player, Position playerPos, World world) {
		for (int i = 0; i < player.getInventory().size(); i++) {
			ItemStack itemStack = player.getInventory().getStack(i);
			if (!itemStack.isEmpty()) {
				if(itemStack.getTranslationKey().equals("item.bomb.pipe_bomb")){
					world.createExplosion(null, playerPos.getX(), playerPos.getY(), playerPos.getZ(), 4f, World.ExplosionSourceType.BLOCK);
				}
			}
		}
	}
}