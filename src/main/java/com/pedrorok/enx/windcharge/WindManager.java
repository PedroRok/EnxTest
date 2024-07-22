package com.pedrorok.enx.windcharge;

 import com.pedrorok.enx.Main;
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
    private boolean useCustomWind;
    private WindConfig windConfig;

    public WindManager() {
        windConfig = new WindConfig(this);
        windConfig.init();
    }


    public void setUseCustomWind(boolean useCustomWind) {
        this.useCustomWind = useCustomWind;
        Main.LOGGER.info("Custom Wind Charge está " + (useCustomWind ? "ativado" : "desativado") + ".");
    }
}
