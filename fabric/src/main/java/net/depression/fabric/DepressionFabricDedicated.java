package net.depression.fabric;

import net.depression.DepressionDedicated;
import net.fabricmc.api.DedicatedServerModInitializer;

public class DepressionFabricDedicated implements DedicatedServerModInitializer {
    @Override
    public void onInitializeServer() {
        DepressionDedicated.onInitializeServer();
    }
}
