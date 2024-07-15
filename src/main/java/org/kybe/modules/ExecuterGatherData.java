package org.kybe.modules;

import org.rusherhack.client.api.feature.module.Module;
import org.rusherhack.client.api.feature.module.ModuleCategory;
import org.rusherhack.core.event.subscribe.Subscribe;

import static io.netty.util.ResourceLeakDetector.isEnabled;

public class ExecuterGatherData extends Module {
    private static long lastTime = System.currentTimeMillis();
    private static int tickCount = 0;
    public static double tps = 20.0;

    public ExecuterGatherData() {
        super("ExecuterGatherData", "Gathers data for the executer module(for example tps)", ModuleCategory.MISC);
    }

    @Subscribe
    public void onTick() {
        if (!isEnabled()) return;
        long currentTime = System.currentTimeMillis();
        tickCount++;
        if (tickCount >= 20) {
            long timeDiff = currentTime - lastTime;
            tps = (1000.0 / (timeDiff / 20.0));
            lastTime = currentTime;
            tickCount = 0;
        }
    }

    public double getTps() {
        return tps;
    }
}
