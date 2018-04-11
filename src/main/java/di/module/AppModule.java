package di.module;

import dagger.Module;
import dagger.Provides;
import util.SystemUtils;

import javax.inject.Named;

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

@Module
public class AppModule {

    @Provides @Named("clientId") String provideClientId() {
        return SystemUtils.getSystemVariable("clientId");
    }
}
