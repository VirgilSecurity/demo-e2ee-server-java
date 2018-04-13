package di;

import data.model.exception.InitializationException;
import di.component.AppComponent;
import di.component.DaggerAppComponent;
import di.module.AppModule;
import di.module.VirgilModule;

/**
 * .._  _
 * .| || | _
 * -| || || |   Created by:
 * .| || || |-  Danylo Oliinyk
 * ..\_  || |   on
 * ....|  _/    4/13/18
 * ...-| | \    at Virgil Security
 * ....|_|-
 */
public class DaggerManager {

    private static DaggerManager instance;
    private static AppComponent appComponent;

    private DaggerManager() {
    }

    public static DaggerManager getInstance() {
        if (instance == null)
            instance = new DaggerManager();

        return instance;
    }

    public void init() {
        if (appComponent != null)
            return;

        appComponent = DaggerAppComponent.builder()
                                         .appModule(new AppModule())
                                         .virgilModule(new VirgilModule())
                                         .build();
    }

    public AppComponent getAppComponent() {
        if (appComponent == null)
            throw new InitializationException("DaggerManager -> init() method must be called first");

        return appComponent;
    }
}
