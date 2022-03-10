package me.uwu.tiktok.experiment.struct;

import lombok.Data;

public @Data class Device {
    private final DeviceOS os;
    private final String osVersion, name;
}
