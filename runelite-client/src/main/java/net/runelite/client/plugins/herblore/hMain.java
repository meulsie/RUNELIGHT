/*
 * Copyright (c) 2019, Hermetism <https://github.com/Hermetism>
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this
 *   list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *   this list of conditions and the following disclaimer in the documentation
 *   and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package net.runelite.client.plugins.herblore;


import com.google.inject.Provides;
import net.runelite.api.Client;
import net.runelite.api.Player;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.plugins.helpers.HelperDraw;
import net.runelite.client.plugins.helpers.HelperThread;
import net.runelite.client.ui.overlay.Overlay;
import net.runelite.client.ui.overlay.OverlayPosition;
import net.runelite.client.ui.overlay.components.PanelComponent;

import javax.inject.Inject;
import java.awt.*;

import static java.awt.Color.CYAN;
import static net.runelite.client.plugins.herblore.hTasks.STOP;

class hMain extends Overlay {

    private final hConfig config;
    private final Client client;
    private final PanelComponent panelComponent = new PanelComponent();

    @Inject
    private ConfigManager configManager;

    @Provides
    hConfig provideConfig(ConfigManager configManager) {
        return configManager.getConfig(hConfig.class);
    }

    @Inject
    public hMain(Client client, hConfig config) {
        setPosition(OverlayPosition.DYNAMIC);
        this.client = client;
        this.config = config;
    }

    @Override
    public Dimension render(Graphics2D graphics) {

        if (STOP) {
            configManager.setConfiguration("h", "hAltarBuiltIn", false);
            if (!HelperThread.isBusy()) {
                STOP = false;
            }
        }


        if (hPlugin.startHerb) {

            /* Run Tasks */
            hTaskMan.runTasks(client, config);

        }

        // Render Player Wire Frame
        if (config.toggleWireFrame()) {
            Player local = client.getLocalPlayer();
            HelperDraw.renderPlayerWireframe(graphics, local, CYAN);
        }

        return panelComponent.render(graphics);
    }
}

