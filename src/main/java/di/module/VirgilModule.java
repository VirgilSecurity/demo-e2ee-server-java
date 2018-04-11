package di.module;

import com.virgilsecurity.sdk.crypto.AccessTokenSigner;
import com.virgilsecurity.sdk.crypto.VirgilAccessTokenSigner;
import com.virgilsecurity.sdk.crypto.VirgilCrypto;
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
public final class VirgilModule {

    @Provides @Named("apiKey") String provideApiKey() {
        return SystemUtils.getSystemVariable("apiKey");
    }

    @Provides @Named("appId") String provideAppId() {
        return SystemUtils.getSystemVariable("appId");
    }

    @Provides @Named("apiPublicKeyId") String provideApiPublicKey() {
        return SystemUtils.getSystemVariable("apiPublicKeyId");
    }

    @Provides VirgilCrypto providesVirgilCrypro() {
        return new VirgilCrypto();
    }

    @Provides AccessTokenSigner providesAccessTokenSigner() {
        return new VirgilAccessTokenSigner();
    }
}
