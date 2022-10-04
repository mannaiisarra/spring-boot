package com.spring.pfe.security.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class VideoStreamingService {

    @Autowired
    private ResourceLoader resourceLoader;

    public Mono<Resource> getVideoStreaming(String titleName) {
        return Mono.fromSupplier(() -> resourceLoader.getResource("file:C:\\Users\\Sarra\\Desktop\\devloppement\\spring-boot\\uploads\\" + titleName));
    }
}
