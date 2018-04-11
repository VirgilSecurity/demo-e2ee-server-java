package data.remote;

import com.virgilsecurity.sdk.common.TimeSpan;
import com.virgilsecurity.sdk.crypto.AccessTokenSigner;
import com.virgilsecurity.sdk.crypto.PrivateKey;
import com.virgilsecurity.sdk.crypto.VirgilCrypto;
import com.virgilsecurity.sdk.crypto.exceptions.CryptoException;
import com.virgilsecurity.sdk.jwt.Jwt;
import com.virgilsecurity.sdk.jwt.JwtGenerator;
import com.virgilsecurity.sdk.jwt.contract.AccessToken;
import com.virgilsecurity.sdk.utils.ConvertionUtils;
import data.model.exception.VirgilSecurityException;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.concurrent.TimeUnit;

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
public final class VirgilHelper {

    private final String apiKey;
    private final String appId;
    private final String apiPublicKeyId;
    private final VirgilCrypto virgilCrypto;
    private final AccessTokenSigner accessTokenSigner;

    private JwtGenerator jwtGenerator;

    @Inject
    public VirgilHelper(@Named("apiKey") String apiKey,
                        @Named("appId") String appId,
                        @Named("apiPublicKeyId") String apiPublicKeyId,
                        VirgilCrypto virgilCrypto,
                        AccessTokenSigner accessTokenSigner) {

        this.apiKey = apiKey;
        this.appId = appId;
        this.apiPublicKeyId = apiPublicKeyId;
        this.virgilCrypto = virgilCrypto;
        this.accessTokenSigner = accessTokenSigner;

        PrivateKey privateKey;
        try {
            privateKey = virgilCrypto.importPrivateKey(ConvertionUtils.base64ToBytes(apiKey));
        } catch (CryptoException e) {
            e.printStackTrace();
            throw new VirgilSecurityException("VirgilHelper -> private key import error");
        }

        TimeSpan timeSpan = TimeSpan.fromTime(5, TimeUnit.MINUTES);

        jwtGenerator = new JwtGenerator(appId,
                                        privateKey,
                                        apiPublicKeyId,
                                        timeSpan,
                                        accessTokenSigner);
    }

    public AccessToken generateToken(String identity) {
        try {
            return jwtGenerator.generateToken(identity);
        } catch (CryptoException e) {
            e.printStackTrace();
            throw new VirgilSecurityException("VirgilHelper -> token generation error");
        }
    }

    public String getApiKey() {
        return apiKey;
    }

    public String getAppId() {
        return appId;
    }

    public String getApiPublicKeyId() {
        return apiPublicKeyId;
    }

    public VirgilCrypto getVirgilCrypto() {
        return virgilCrypto;
    }

    public AccessTokenSigner getAccessTokenSigner() {
        return accessTokenSigner;
    }

    public JwtGenerator getJwtGenerator() {
        return jwtGenerator;
    }
}
