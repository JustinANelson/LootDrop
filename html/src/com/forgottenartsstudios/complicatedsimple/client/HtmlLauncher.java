package com.forgottenartsstudios.complicatedsimple.client;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.gwt.GwtApplication;
import com.badlogic.gdx.backends.gwt.GwtApplicationConfiguration;
import com.forgottenartsstudios.complicatedsimple.SimpleGame;

public class HtmlLauncher extends GwtApplication {

        @Override
        public GwtApplicationConfiguration getConfig () {
                // Resizable application, uses available space in browser
                //return new GwtApplicationConfiguration(true);
                // Fixed size application:
                return new GwtApplicationConfiguration(480, 854);
        }

        @Override
        public ApplicationListener createApplicationListener () {
                return new SimpleGame();
        }
}