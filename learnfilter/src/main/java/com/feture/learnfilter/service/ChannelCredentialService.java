package com.feture.learnfilter.service;

import org.springframework.stereotype.Service;

import java.util.concurrent.ConcurrentHashMap;

@Service
public class ChannelCredentialService {

    private static final ConcurrentHashMap<String, String> ChannelCredentialHashMap = new ConcurrentHashMap<String, String>() {
        {
            put("qinghotel", "46344343");
            put("web","6AF0062B-9C6D-4796-8760-803779CF7E48");
            put("wechat","wechat");
        }
    };

    public String getAppKeyByChannelKey(String channelKey) {
        return ChannelCredentialHashMap.getOrDefault(channelKey, "");
    }
}
