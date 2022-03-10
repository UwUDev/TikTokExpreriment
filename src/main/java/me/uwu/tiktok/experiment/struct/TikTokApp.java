package me.uwu.tiktok.experiment.struct;

import lombok.Data;

public @Data class TikTokApp {
    private final int version, aid;
    private final String language;
}
