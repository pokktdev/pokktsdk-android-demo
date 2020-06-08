package com.pokkt.demo.fragments.osvad;

public interface PokktNativeAdDelegates {
    void pokktOSVADInitDone();

    void setAgentVisible(boolean isVisible);

    void updateAgentBounds(int posX, int posY, int width, int height, float scaleFactor);
}
