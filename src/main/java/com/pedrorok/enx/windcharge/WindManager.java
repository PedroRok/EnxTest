package com.pedrorok.enx.windcharge;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Rok, Pedro Lucas nmm. Created on 21/07/2024
 * @project EnxTest
 */
@Getter
public class WindManager {

    @Setter(value = AccessLevel.PROTECTED)
    private WindOptions windOptions;
    @Setter(value = AccessLevel.PROTECTED)
    private boolean useCustomWind;
    private WindConfig windConfig;

    public WindManager() {
        windConfig = new WindConfig(this);
        windConfig.init();
    }


}
