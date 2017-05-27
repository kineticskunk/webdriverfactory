package com.kineticskunk.driverutilities;

import com.google.common.base.Strings;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.ProxySelector;
import java.net.URI;
import java.util.List;

public class DetectProxyConfig {

    private static String host;
    private static int port;
    private static boolean proxyAvailable = false;

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }

    public boolean isProxyAvailable() {
        return proxyAvailable;
    }

    public DetectProxyConfig(String proxyAddress) {

        String proxyHost = System.getProperty("http.proxyHost");
        Integer proxyPort = Integer.getInteger(System.getProperty("http.proxyPort"));

        if (Strings.isNullOrEmpty(proxyHost) || null == proxyPort) {

            String useSystemProxy = System.getProperty("java.net.useSystemProxies");
            System.setProperty("java.net.useSystemProxies", "true");
            Proxy proxy = getProxy(proxyAddress);

            if (null != proxy) {
                if (null != proxy.address()) {
                    InetSocketAddress socketAddress = (InetSocketAddress) proxy.address();
                    host = socketAddress.getHostName();
                    port = socketAddress.getPort();
                    proxyAvailable = true;

                    System.setProperty("http.proxyHost", host);
                    System.setProperty("http.proxyPort", "" + port);
                }
            }

            if (Strings.isNullOrEmpty(useSystemProxy)) {
                System.clearProperty("java.net.useSystemProxies");
            } else {
                System.setProperty("java.net.useSystemProxies", useSystemProxy);
            }

        } else {
            host = proxyHost;
            port = proxyPort;
            proxyAvailable = true;
        }
    }

    private Proxy getProxy(String proxyAddress) {
        List<Proxy> proxyList = null;
        try {
            proxyList = ProxySelector.getDefault().select(new URI(proxyAddress));
        } catch (Exception ignored) {
        }
        if (null != proxyList) {
            for (Proxy proxy : proxyList) {
                if (null != proxy) {
                    return proxy;
                }
            }
        }
        return null;
    }
}