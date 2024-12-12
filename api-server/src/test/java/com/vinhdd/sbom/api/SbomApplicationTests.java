package com.vinhdd.sbom.api;

import com.vinhdd.sbom.api.dto.restTemplate.ComponentReportDto;
import com.vinhdd.sbom.api.service.VulnerabilityService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@Slf4j
@SpringBootTest
class SbomApplicationTests {
    @Autowired
    private VulnerabilityService vulnerabilityService;

    @Test
    void contextLoads() {
    }

    @Test
    void test() {
        List<ComponentReportDto> reports = vulnerabilityService.getVulnerabilityByComponentPurl(
                List.of("pkg:maven/org.springframework/spring-context@6.1.11",
                        "pkg:maven/org.springframework/spring-web@6.1.11"));
        log.info("Reports: {}", reports);
        vulnerabilityService.saveVulnerabilityReport(reports);
    }

    @Test
    void test2() {
//        List<Vulnerability> vulnerabilities = vulnerabilityService.getAllVulnerabilities();
//        log.info("Vulnerabilities: {}", vulnerabilities);
        String u = "pkg:maven/org.springframework/spring-context@6.1.11?type=jar";
        log.info("{}", u.substring(0, u.indexOf("?")));
    }

}
