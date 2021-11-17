package dev.ricecx.frostygamerzone.minigameapi.utils;

import dev.ricecx.frostygamerzone.minigameapi.MinigamesAPI;
import org.bukkit.Bukkit;

import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public class OffloadTask {

    public static void offloadAsync(Runnable runnable) {
        Bukkit.getScheduler().runTaskAsynchronously(MinigamesAPI.getMinigamesPlugin(), runnable);
    }

    public static <T> CompletableFuture<T> offload(Consumer<CompletableFuture<T>> consumer) {
        CompletableFuture<T> future = new CompletableFuture<>();

        consumer.accept(future);

        return future;
    }

    public static void offloadSync(Runnable runnable) {
        Bukkit.getScheduler().runTask(MinigamesAPI.getMinigamesPlugin(), runnable);
    }
}