package cn.jiiiiiin.security.browser.utils;

import cn.jiiiiiin.security.core.dict.CommonConstants;
import org.springframework.mobile.device.Device;
import org.springframework.mobile.device.DevicePlatform;
import org.springframework.mobile.device.DeviceType;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * @author jiiiiiin
 */
public class HttpUtils {

    //TODO 后期将下面几个成员变量改为可以配置形式

    private static final List<String> mobileUserAgentPrefixes = new ArrayList<String>();

    private static final List<String> mobileUserAgentKeywords = new ArrayList<String>();

    private static final List<String> tabletUserAgentKeywords = new ArrayList<String>();

    private static final List<String> normalUserAgentKeywords = new ArrayList<String>();

    public static Device resolveDevice(HttpServletRequest request) {
        return resolveDevice(request.getHeader("User-Agent"), request.getHeader("accept"));
    }

    public static Device resolveDevice(String userAgent, String accept) {
        // UserAgent keyword detection of Tablet devices
        if (userAgent != null) {
            userAgent = userAgent.toLowerCase();
            // Android special case
            if (userAgent.contains("android") && !userAgent.contains("mobile")) {
                return resolveWithPlatform(DeviceType.TABLET, DevicePlatform.ANDROID);
            }
            // Apple special case
            if (userAgent.contains("ipad")) {
                return resolveWithPlatform(DeviceType.TABLET, DevicePlatform.IOS);
            }
            // Kindle Fire special case
            if (userAgent.contains("silk") && !userAgent.contains("mobile")) {
                return resolveWithPlatform(DeviceType.TABLET, DevicePlatform.UNKNOWN);
            }
            for (String keyword : tabletUserAgentKeywords) {
                if (userAgent.contains(keyword)) {
                    return resolveWithPlatform(DeviceType.TABLET, DevicePlatform.UNKNOWN);
                }
            }
        }
        // UAProf detection
//        if (request.getHeader("x-wap-profile") != null || request.getHeader("Profile") != null) {
//            if (userAgent != null) {
//                // Android special case
//                if (userAgent.contains("android")) {
//                    return resolveWithPlatform(DeviceType.MOBILE, DevicePlatform.ANDROID);
//                }
//                // Apple special case
//                if (userAgent.contains("iphone") || userAgent.contains("ipod") || userAgent.contains("ipad")) {
//                    return resolveWithPlatform(DeviceType.MOBILE, DevicePlatform.IOS);
//                }
//            }
//            return resolveWithPlatform(DeviceType.MOBILE, DevicePlatform.UNKNOWN);
//        }
        // User-Agent prefix detection
        if (userAgent != null && userAgent.length() >= 4) {
            String prefix = userAgent.substring(0, 4).toLowerCase();
            if (mobileUserAgentPrefixes.contains(prefix)) {
                return resolveWithPlatform(DeviceType.MOBILE, DevicePlatform.UNKNOWN);
            }
        }
        // Accept-header based detection
        if (accept != null && (accept.contains(CommonConstants.ACCEPT_JSON_PREFIX) || accept.contains("wap"))) {
            return resolveWithPlatform(DeviceType.MOBILE, DevicePlatform.UNKNOWN);
        }
        // UserAgent keyword detection for Mobile devices
        if (userAgent != null) {
            // Android special case
            if (userAgent.contains("android")) {
                return resolveWithPlatform(DeviceType.MOBILE, DevicePlatform.ANDROID);
            }
            // Apple special case
            if (userAgent.contains("iphone") || userAgent.contains("ipod") || userAgent.contains("ipad")) {
                return resolveWithPlatform(DeviceType.MOBILE, DevicePlatform.IOS);
            }
            for (String keyword : mobileUserAgentKeywords) {
                if (userAgent.contains(keyword)) {
                    return resolveWithPlatform(DeviceType.MOBILE, DevicePlatform.UNKNOWN);
                }
            }
        }
        return MyLiteDevice.NORMAL_INSTANCE;
    }

    private static Device resolveWithPlatform(DeviceType tablet, DevicePlatform android) {
        return MyLiteDevice.from(tablet, android);
    }

    /**
     * A lightweight Device implementation suitable for use as support code.
     * Typically used to hold the output of a device resolution invocation.
     *
     * @author Keith Donald
     * @author Roy Clarkson
     * @author Scott Rossillo
     * @author Onur Kagan Ozcan
     */
    static class MyLiteDevice implements Device {

        public static final MyLiteDevice NORMAL_INSTANCE = new MyLiteDevice(DeviceType.NORMAL, DevicePlatform.UNKNOWN);

        public static final MyLiteDevice MOBILE_INSTANCE = new MyLiteDevice(DeviceType.MOBILE, DevicePlatform.UNKNOWN);

        public static final MyLiteDevice TABLET_INSTANCE = new MyLiteDevice(DeviceType.TABLET, DevicePlatform.UNKNOWN);

        @Override
        public boolean isNormal() {
            return this.deviceType == DeviceType.NORMAL;
        }

        @Override
        public boolean isMobile() {
            return this.deviceType == DeviceType.MOBILE;
        }

        @Override
        public boolean isTablet() {
            return this.deviceType == DeviceType.TABLET;
        }

        @Override
        public DevicePlatform getDevicePlatform() {
            return this.devicePlatform;
        }

        public DeviceType getDeviceType() {
            return this.deviceType;
        }

        public static Device from(DeviceType deviceType, DevicePlatform devicePlatform) {
            return new MyLiteDevice(deviceType, devicePlatform);
        }

        @Override
        public String toString() {
            StringBuilder builder = new StringBuilder();
            builder.append("[MyLiteDevice ");
            builder.append("type").append("=").append(this.deviceType);
            builder.append("]");
            return builder.toString();
        }

        private final DeviceType deviceType;

        private final DevicePlatform devicePlatform;

        /**
         * Creates a MyLiteDevice with DevicePlatform.
         */
        private MyLiteDevice(DeviceType deviceType, DevicePlatform devicePlatform) {
            this.deviceType = deviceType;
            this.devicePlatform = devicePlatform;
        }

    }
}
