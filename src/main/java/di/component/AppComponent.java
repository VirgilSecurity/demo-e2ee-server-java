package di.component;

import dagger.Component;
import data.remote.WebSocketHelper;
import di.module.AppModule;
import di.module.VirgilModule;

/**
 * .._  _
 * .| || | _
 * -| || || |   Created by:
 * .| || || |-  Danylo Oliinyk
 * ..\_  || |   on
 * ....|  _/    4/11/18
 * ...-| | \    at Virgil Security
 * ....|_|-
 */
@Component(modules = { VirgilModule.class, AppModule.class })
public interface AppComponent {

    void inject(WebSocketHelper helper);
}
