package dev.fullstackcode.qrcode.bean;

public record WifiNetwork(String ssid, String password, String networkType) {

    public String getWifiNetworkString(){
        return "WIFI:S:%s;T:%s;P:%s;;".formatted(ssid(),networkType(),password());
    }
}
