package com.neshan.shortLink.service.scheduler;

import com.neshan.shortLink.service.higLevel.RemoveShortLinkManagerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class RemoveAddressScheduler {

    private final RemoveShortLinkManagerService removeShortLinkManagerService;

    @Value("${neshan.scheduler.remove-link.enable}")
    private boolean enableScheduler;

    @Scheduled(cron = "${neshan.scheduler.remove-link.cron}")
    public void runRemover() {
        if (!enableScheduler) {
            log.warn("Remove expire Address scheduler is disable!");
            return;
        }
        log.info("---------------------------");
        log.info("Start scheduling to remove expired address");
        removeShortLinkManagerService.remove();
        log.info("Exit scheduling for removing expired address");
        log.info("---------------------------");
    }
}
