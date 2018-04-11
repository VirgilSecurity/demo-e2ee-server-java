package util;

import com.virgilsecurity.sdk.utils.StringUtils;

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
public final class SystemUtils {

    public static String getSystemVariable(String variableName) {
        if (org.apache.commons.lang3.SystemUtils.IS_OS_MAC_OSX || org.apache.commons.lang3.SystemUtils.IS_OS_LINUX) {
            if (StringUtils.isBlank(System.getenv(variableName))) {
                return null;
            }

            return System.getenv(variableName);
        } else {
            if (StringUtils.isBlank(System.getProperty(variableName))) {
                return null;
            }

            return System.getProperty(variableName);
        }
    }
}
