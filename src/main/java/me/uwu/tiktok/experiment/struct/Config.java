package me.uwu.tiktok.experiment.struct;

import lombok.Data;

public @Data class Config {
    private final long videoID;
    private final int workers, iterations;
    private final Device device;
    private final TikTokApp tikTokApp;
}
