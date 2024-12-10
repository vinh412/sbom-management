import React, { useMemo, useState } from 'react'
import { Controls, Handle, Position, ReactFlow } from '@xyflow/react';
import { v4 as uuidv4 } from 'uuid';
import { theme } from 'antd';
import '@xyflow/react/dist/style.css';

// const dependencies = [
//   {
//     "ref": "pkg:maven/com.vinhdd/sbom-api-server@0.0.1-SNAPSHOT?type=jar",
//     "dependsOn": [
//       "pkg:maven/io.jsonwebtoken/jjwt-api@0.11.5?type=jar",
//       "pkg:maven/io.jsonwebtoken/jjwt-impl@0.11.5?type=jar",
//       "pkg:maven/io.jsonwebtoken/jjwt-jackson@0.11.5?type=jar",
//       "pkg:maven/org.postgresql/postgresql@42.7.3?type=jar",
//       "pkg:maven/org.projectlombok/lombok@1.18.34?type=jar",
//       "pkg:maven/org.springframework.boot/spring-boot-starter-actuator@3.3.5?type=jar",
//       "pkg:maven/org.springframework.boot/spring-boot-starter-data-jpa@3.3.5?type=jar",
//       "pkg:maven/org.springframework.boot/spring-boot-starter-security@3.3.5?type=jar",
//       "pkg:maven/org.springframework.boot/spring-boot-starter-test@3.3.5?type=jar",
//       "pkg:maven/org.springframework.boot/spring-boot-starter-validation@3.3.5?type=jar",
//       "pkg:maven/org.springframework.boot/spring-boot-starter-web@3.3.5?type=jar",
//       "pkg:maven/org.springframework.security/spring-security-test@6.3.4?type=jar"
//     ]
//   },
//   {
//     "ref": "pkg:maven/org.springframework.boot/spring-boot-starter-actuator@3.3.5?type=jar",
//     "dependsOn": [
//       "pkg:maven/io.micrometer/micrometer-jakarta9@1.13.6?type=jar",
//       "pkg:maven/io.micrometer/micrometer-observation@1.13.6?type=jar",
//       "pkg:maven/org.springframework.boot/spring-boot-actuator-autoconfigure@3.3.5?type=jar",
//       "pkg:maven/org.springframework.boot/spring-boot-starter@3.3.5?type=jar"
//     ]
//   },
//   {
//     "ref": "pkg:maven/org.springframework.boot/spring-boot-starter@3.3.5?type=jar",
//     "dependsOn": [
//       "pkg:maven/jakarta.annotation/jakarta.annotation-api@2.1.1?type=jar",
//       "pkg:maven/org.springframework.boot/spring-boot-autoconfigure@3.3.5?type=jar",
//       "pkg:maven/org.springframework.boot/spring-boot-starter-logging@3.3.5?type=jar",
//       "pkg:maven/org.springframework.boot/spring-boot@3.3.5?type=jar",
//       "pkg:maven/org.springframework/spring-core@6.1.14?type=jar",
//       "pkg:maven/org.yaml/snakeyaml@2.2?type=jar"
//     ]
//   },
//   {
//     "ref": "pkg:maven/org.springframework.boot/spring-boot@3.3.5?type=jar",
//     "dependsOn": [
//       "pkg:maven/org.springframework/spring-context@6.1.14?type=jar",
//       "pkg:maven/org.springframework/spring-core@6.1.14?type=jar"
//     ]
//   },
//   {
//     "ref": "pkg:maven/org.springframework/spring-core@6.1.14?type=jar",
//     "dependsOn": [
//       "pkg:maven/org.springframework/spring-jcl@6.1.14?type=jar"
//     ]
//   },
//   {
//     "ref": "pkg:maven/org.springframework/spring-jcl@6.1.14?type=jar",
//     "dependsOn": []
//   },
//   {
//     "ref": "pkg:maven/org.springframework/spring-context@6.1.14?type=jar",
//     "dependsOn": [
//       "pkg:maven/io.micrometer/micrometer-observation@1.13.6?type=jar",
//       "pkg:maven/org.springframework/spring-aop@6.1.14?type=jar",
//       "pkg:maven/org.springframework/spring-beans@6.1.14?type=jar",
//       "pkg:maven/org.springframework/spring-core@6.1.14?type=jar",
//       "pkg:maven/org.springframework/spring-expression@6.1.14?type=jar"
//     ]
//   },
//   {
//     "ref": "pkg:maven/org.springframework/spring-aop@6.1.14?type=jar",
//     "dependsOn": [
//       "pkg:maven/org.springframework/spring-beans@6.1.14?type=jar",
//       "pkg:maven/org.springframework/spring-core@6.1.14?type=jar"
//     ]
//   },
//   {
//     "ref": "pkg:maven/org.springframework/spring-beans@6.1.14?type=jar",
//     "dependsOn": [
//       "pkg:maven/org.springframework/spring-core@6.1.14?type=jar"
//     ]
//   },
//   {
//     "ref": "pkg:maven/org.springframework/spring-expression@6.1.14?type=jar",
//     "dependsOn": [
//       "pkg:maven/org.springframework/spring-core@6.1.14?type=jar"
//     ]
//   },
//   {
//     "ref": "pkg:maven/io.micrometer/micrometer-observation@1.13.6?type=jar",
//     "dependsOn": [
//       "pkg:maven/io.micrometer/micrometer-commons@1.13.6?type=jar"
//     ]
//   },
//   {
//     "ref": "pkg:maven/io.micrometer/micrometer-commons@1.13.6?type=jar",
//     "dependsOn": []
//   },
//   {
//     "ref": "pkg:maven/org.springframework.boot/spring-boot-autoconfigure@3.3.5?type=jar",
//     "dependsOn": [
//       "pkg:maven/org.springframework.boot/spring-boot@3.3.5?type=jar"
//     ]
//   },
//   {
//     "ref": "pkg:maven/org.springframework.boot/spring-boot-starter-logging@3.3.5?type=jar",
//     "dependsOn": [
//       "pkg:maven/ch.qos.logback/logback-classic@1.5.11?type=jar",
//       "pkg:maven/org.apache.logging.log4j/log4j-to-slf4j@2.23.1?type=jar",
//       "pkg:maven/org.slf4j/jul-to-slf4j@2.0.16?type=jar"
//     ]
//   },
//   {
//     "ref": "pkg:maven/ch.qos.logback/logback-classic@1.5.11?type=jar",
//     "dependsOn": [
//       "pkg:maven/ch.qos.logback/logback-core@1.5.11?type=jar",
//       "pkg:maven/org.slf4j/slf4j-api@2.0.16?type=jar"
//     ]
//   },
//   {
//     "ref": "pkg:maven/ch.qos.logback/logback-core@1.5.11?type=jar",
//     "dependsOn": []
//   },
//   {
//     "ref": "pkg:maven/org.slf4j/slf4j-api@2.0.16?type=jar",
//     "dependsOn": []
//   },
//   {
//     "ref": "pkg:maven/org.apache.logging.log4j/log4j-to-slf4j@2.23.1?type=jar",
//     "dependsOn": [
//       "pkg:maven/org.apache.logging.log4j/log4j-api@2.23.1?type=jar",
//       "pkg:maven/org.slf4j/slf4j-api@2.0.16?type=jar"
//     ]
//   },
//   {
//     "ref": "pkg:maven/org.apache.logging.log4j/log4j-api@2.23.1?type=jar",
//     "dependsOn": []
//   },
//   {
//     "ref": "pkg:maven/org.slf4j/jul-to-slf4j@2.0.16?type=jar",
//     "dependsOn": [
//       "pkg:maven/org.slf4j/slf4j-api@2.0.16?type=jar"
//     ]
//   },
//   {
//     "ref": "pkg:maven/jakarta.annotation/jakarta.annotation-api@2.1.1?type=jar",
//     "dependsOn": []
//   },
//   {
//     "ref": "pkg:maven/org.yaml/snakeyaml@2.2?type=jar",
//     "dependsOn": []
//   },
//   {
//     "ref": "pkg:maven/org.springframework.boot/spring-boot-actuator-autoconfigure@3.3.5?type=jar",
//     "dependsOn": [
//       "pkg:maven/com.fasterxml.jackson.core/jackson-databind@2.17.2?type=jar",
//       "pkg:maven/com.fasterxml.jackson.datatype/jackson-datatype-jsr310@2.17.2?type=jar",
//       "pkg:maven/org.springframework.boot/spring-boot-actuator@3.3.5?type=jar",
//       "pkg:maven/org.springframework.boot/spring-boot-autoconfigure@3.3.5?type=jar",
//       "pkg:maven/org.springframework.boot/spring-boot@3.3.5?type=jar"
//     ]
//   },
//   {
//     "ref": "pkg:maven/org.springframework.boot/spring-boot-actuator@3.3.5?type=jar",
//     "dependsOn": [
//       "pkg:maven/org.springframework.boot/spring-boot@3.3.5?type=jar"
//     ]
//   },
//   {
//     "ref": "pkg:maven/com.fasterxml.jackson.core/jackson-databind@2.17.2?type=jar",
//     "dependsOn": [
//       "pkg:maven/com.fasterxml.jackson.core/jackson-annotations@2.17.2?type=jar",
//       "pkg:maven/com.fasterxml.jackson.core/jackson-core@2.17.2?type=jar"
//     ]
//   },
//   {
//     "ref": "pkg:maven/com.fasterxml.jackson.core/jackson-annotations@2.17.2?type=jar",
//     "dependsOn": []
//   },
//   {
//     "ref": "pkg:maven/com.fasterxml.jackson.core/jackson-core@2.17.2?type=jar",
//     "dependsOn": []
//   },
//   {
//     "ref": "pkg:maven/com.fasterxml.jackson.datatype/jackson-datatype-jsr310@2.17.2?type=jar",
//     "dependsOn": [
//       "pkg:maven/com.fasterxml.jackson.core/jackson-annotations@2.17.2?type=jar",
//       "pkg:maven/com.fasterxml.jackson.core/jackson-core@2.17.2?type=jar",
//       "pkg:maven/com.fasterxml.jackson.core/jackson-databind@2.17.2?type=jar"
//     ]
//   },
//   {
//     "ref": "pkg:maven/io.micrometer/micrometer-jakarta9@1.13.6?type=jar",
//     "dependsOn": [
//       "pkg:maven/io.micrometer/micrometer-commons@1.13.6?type=jar",
//       "pkg:maven/io.micrometer/micrometer-core@1.13.6?type=jar",
//       "pkg:maven/io.micrometer/micrometer-observation@1.13.6?type=jar"
//     ]
//   },
//   {
//     "ref": "pkg:maven/io.micrometer/micrometer-core@1.13.6?type=jar",
//     "dependsOn": [
//       "pkg:maven/io.micrometer/micrometer-commons@1.13.6?type=jar",
//       "pkg:maven/io.micrometer/micrometer-observation@1.13.6?type=jar",
//       "pkg:maven/org.hdrhistogram/HdrHistogram@2.2.2?type=jar",
//       "pkg:maven/org.latencyutils/LatencyUtils@2.0.3?type=jar"
//     ]
//   },
//   {
//     "ref": "pkg:maven/org.hdrhistogram/HdrHistogram@2.2.2?type=jar",
//     "dependsOn": []
//   },
//   {
//     "ref": "pkg:maven/org.latencyutils/LatencyUtils@2.0.3?type=jar",
//     "dependsOn": []
//   },
//   {
//     "ref": "pkg:maven/org.springframework.boot/spring-boot-starter-data-jpa@3.3.5?type=jar",
//     "dependsOn": [
//       "pkg:maven/org.hibernate.orm/hibernate-core@6.5.3.Final?type=jar",
//       "pkg:maven/org.springframework.boot/spring-boot-starter-aop@3.3.5?type=jar",
//       "pkg:maven/org.springframework.boot/spring-boot-starter-jdbc@3.3.5?type=jar",
//       "pkg:maven/org.springframework.data/spring-data-jpa@3.3.5?type=jar",
//       "pkg:maven/org.springframework/spring-aspects@6.1.14?type=jar"
//     ]
//   },
//   {
//     "ref": "pkg:maven/org.springframework.boot/spring-boot-starter-aop@3.3.5?type=jar",
//     "dependsOn": [
//       "pkg:maven/org.aspectj/aspectjweaver@1.9.22.1?type=jar",
//       "pkg:maven/org.springframework.boot/spring-boot-starter@3.3.5?type=jar",
//       "pkg:maven/org.springframework/spring-aop@6.1.14?type=jar"
//     ]
//   },
//   {
//     "ref": "pkg:maven/org.aspectj/aspectjweaver@1.9.22.1?type=jar",
//     "dependsOn": []
//   },
//   {
//     "ref": "pkg:maven/org.springframework.boot/spring-boot-starter-jdbc@3.3.5?type=jar",
//     "dependsOn": [
//       "pkg:maven/com.zaxxer/HikariCP@5.1.0?type=jar",
//       "pkg:maven/org.springframework.boot/spring-boot-starter@3.3.5?type=jar",
//       "pkg:maven/org.springframework/spring-jdbc@6.1.14?type=jar"
//     ]
//   },
//   {
//     "ref": "pkg:maven/com.zaxxer/HikariCP@5.1.0?type=jar",
//     "dependsOn": [
//       "pkg:maven/org.slf4j/slf4j-api@2.0.16?type=jar"
//     ]
//   },
//   {
//     "ref": "pkg:maven/org.springframework/spring-jdbc@6.1.14?type=jar",
//     "dependsOn": [
//       "pkg:maven/org.springframework/spring-beans@6.1.14?type=jar",
//       "pkg:maven/org.springframework/spring-core@6.1.14?type=jar",
//       "pkg:maven/org.springframework/spring-tx@6.1.14?type=jar"
//     ]
//   },
//   {
//     "ref": "pkg:maven/org.springframework/spring-tx@6.1.14?type=jar",
//     "dependsOn": [
//       "pkg:maven/org.springframework/spring-beans@6.1.14?type=jar",
//       "pkg:maven/org.springframework/spring-core@6.1.14?type=jar"
//     ]
//   },
//   {
//     "ref": "pkg:maven/org.hibernate.orm/hibernate-core@6.5.3.Final?type=jar",
//     "dependsOn": [
//       "pkg:maven/com.fasterxml/classmate@1.7.0?type=jar",
//       "pkg:maven/io.smallrye/jandex@3.1.2?type=jar",
//       "pkg:maven/jakarta.inject/jakarta.inject-api@2.0.1?type=jar",
//       "pkg:maven/jakarta.persistence/jakarta.persistence-api@3.1.0?type=jar",
//       "pkg:maven/jakarta.transaction/jakarta.transaction-api@2.0.1?type=jar",
//       "pkg:maven/jakarta.xml.bind/jakarta.xml.bind-api@4.0.2?type=jar",
//       "pkg:maven/net.bytebuddy/byte-buddy@1.14.19?type=jar",
//       "pkg:maven/org.antlr/antlr4-runtime@4.13.0?type=jar",
//       "pkg:maven/org.glassfish.jaxb/jaxb-runtime@4.0.5?type=jar",
//       "pkg:maven/org.hibernate.common/hibernate-commons-annotations@6.0.6.Final?type=jar",
//       "pkg:maven/org.jboss.logging/jboss-logging@3.5.3.Final?type=jar"
//     ]
//   },
//   {
//     "ref": "pkg:maven/jakarta.persistence/jakarta.persistence-api@3.1.0?type=jar",
//     "dependsOn": []
//   },
//   {
//     "ref": "pkg:maven/jakarta.transaction/jakarta.transaction-api@2.0.1?type=jar",
//     "dependsOn": []
//   },
//   {
//     "ref": "pkg:maven/org.jboss.logging/jboss-logging@3.5.3.Final?type=jar",
//     "dependsOn": []
//   },
//   {
//     "ref": "pkg:maven/org.hibernate.common/hibernate-commons-annotations@6.0.6.Final?type=jar",
//     "dependsOn": []
//   },
//   {
//     "ref": "pkg:maven/io.smallrye/jandex@3.1.2?type=jar",
//     "dependsOn": []
//   },
//   {
//     "ref": "pkg:maven/com.fasterxml/classmate@1.7.0?type=jar",
//     "dependsOn": []
//   },
//   {
//     "ref": "pkg:maven/net.bytebuddy/byte-buddy@1.14.19?type=jar",
//     "dependsOn": []
//   },
//   {
//     "ref": "pkg:maven/jakarta.xml.bind/jakarta.xml.bind-api@4.0.2?type=jar",
//     "dependsOn": [
//       "pkg:maven/jakarta.activation/jakarta.activation-api@2.1.3?type=jar"
//     ]
//   },
//   {
//     "ref": "pkg:maven/jakarta.activation/jakarta.activation-api@2.1.3?type=jar",
//     "dependsOn": []
//   },
//   {
//     "ref": "pkg:maven/org.glassfish.jaxb/jaxb-runtime@4.0.5?type=jar",
//     "dependsOn": [
//       "pkg:maven/org.glassfish.jaxb/jaxb-core@4.0.5?type=jar"
//     ]
//   },
//   {
//     "ref": "pkg:maven/org.glassfish.jaxb/jaxb-core@4.0.5?type=jar",
//     "dependsOn": [
//       "pkg:maven/com.sun.istack/istack-commons-runtime@4.1.2?type=jar",
//       "pkg:maven/jakarta.activation/jakarta.activation-api@2.1.3?type=jar",
//       "pkg:maven/jakarta.xml.bind/jakarta.xml.bind-api@4.0.2?type=jar",
//       "pkg:maven/org.eclipse.angus/angus-activation@2.0.2?type=jar",
//       "pkg:maven/org.glassfish.jaxb/txw2@4.0.5?type=jar"
//     ]
//   },
//   {
//     "ref": "pkg:maven/org.eclipse.angus/angus-activation@2.0.2?type=jar",
//     "dependsOn": [
//       "pkg:maven/jakarta.activation/jakarta.activation-api@2.1.3?type=jar"
//     ]
//   },
//   {
//     "ref": "pkg:maven/org.glassfish.jaxb/txw2@4.0.5?type=jar",
//     "dependsOn": []
//   },
//   {
//     "ref": "pkg:maven/com.sun.istack/istack-commons-runtime@4.1.2?type=jar",
//     "dependsOn": []
//   },
//   {
//     "ref": "pkg:maven/jakarta.inject/jakarta.inject-api@2.0.1?type=jar",
//     "dependsOn": []
//   },
//   {
//     "ref": "pkg:maven/org.antlr/antlr4-runtime@4.13.0?type=jar",
//     "dependsOn": []
//   },
//   {
//     "ref": "pkg:maven/org.springframework.data/spring-data-jpa@3.3.5?type=jar",
//     "dependsOn": [
//       "pkg:maven/jakarta.annotation/jakarta.annotation-api@2.1.1?type=jar",
//       "pkg:maven/org.antlr/antlr4-runtime@4.13.0?type=jar",
//       "pkg:maven/org.slf4j/slf4j-api@2.0.16?type=jar",
//       "pkg:maven/org.springframework.data/spring-data-commons@3.3.5?type=jar",
//       "pkg:maven/org.springframework/spring-aop@6.1.14?type=jar",
//       "pkg:maven/org.springframework/spring-beans@6.1.14?type=jar",
//       "pkg:maven/org.springframework/spring-context@6.1.14?type=jar",
//       "pkg:maven/org.springframework/spring-core@6.1.14?type=jar",
//       "pkg:maven/org.springframework/spring-orm@6.1.14?type=jar",
//       "pkg:maven/org.springframework/spring-tx@6.1.14?type=jar"
//     ]
//   },
//   {
//     "ref": "pkg:maven/org.springframework.data/spring-data-commons@3.3.5?type=jar",
//     "dependsOn": [
//       "pkg:maven/org.slf4j/slf4j-api@2.0.16?type=jar",
//       "pkg:maven/org.springframework/spring-beans@6.1.14?type=jar",
//       "pkg:maven/org.springframework/spring-core@6.1.14?type=jar"
//     ]
//   },
//   {
//     "ref": "pkg:maven/org.springframework/spring-orm@6.1.14?type=jar",
//     "dependsOn": [
//       "pkg:maven/org.springframework/spring-beans@6.1.14?type=jar",
//       "pkg:maven/org.springframework/spring-core@6.1.14?type=jar",
//       "pkg:maven/org.springframework/spring-jdbc@6.1.14?type=jar",
//       "pkg:maven/org.springframework/spring-tx@6.1.14?type=jar"
//     ]
//   },
//   {
//     "ref": "pkg:maven/org.springframework/spring-aspects@6.1.14?type=jar",
//     "dependsOn": [
//       "pkg:maven/org.aspectj/aspectjweaver@1.9.22.1?type=jar"
//     ]
//   },
//   {
//     "ref": "pkg:maven/org.springframework.boot/spring-boot-starter-security@3.3.5?type=jar",
//     "dependsOn": [
//       "pkg:maven/org.springframework.boot/spring-boot-starter@3.3.5?type=jar",
//       "pkg:maven/org.springframework.security/spring-security-config@6.3.4?type=jar",
//       "pkg:maven/org.springframework.security/spring-security-web@6.3.4?type=jar",
//       "pkg:maven/org.springframework/spring-aop@6.1.14?type=jar"
//     ]
//   },
//   {
//     "ref": "pkg:maven/org.springframework.security/spring-security-config@6.3.4?type=jar",
//     "dependsOn": [
//       "pkg:maven/org.springframework.security/spring-security-core@6.3.4?type=jar",
//       "pkg:maven/org.springframework/spring-aop@6.1.14?type=jar",
//       "pkg:maven/org.springframework/spring-beans@6.1.14?type=jar",
//       "pkg:maven/org.springframework/spring-context@6.1.14?type=jar",
//       "pkg:maven/org.springframework/spring-core@6.1.14?type=jar"
//     ]
//   },
//   {
//     "ref": "pkg:maven/org.springframework.security/spring-security-core@6.3.4?type=jar",
//     "dependsOn": [
//       "pkg:maven/io.micrometer/micrometer-observation@1.13.6?type=jar",
//       "pkg:maven/org.springframework.security/spring-security-crypto@6.3.4?type=jar",
//       "pkg:maven/org.springframework/spring-aop@6.1.14?type=jar",
//       "pkg:maven/org.springframework/spring-beans@6.1.14?type=jar",
//       "pkg:maven/org.springframework/spring-context@6.1.14?type=jar",
//       "pkg:maven/org.springframework/spring-core@6.1.14?type=jar",
//       "pkg:maven/org.springframework/spring-expression@6.1.14?type=jar"
//     ]
//   },
//   {
//     "ref": "pkg:maven/org.springframework.security/spring-security-crypto@6.3.4?type=jar",
//     "dependsOn": []
//   },
//   {
//     "ref": "pkg:maven/org.springframework.security/spring-security-web@6.3.4?type=jar",
//     "dependsOn": [
//       "pkg:maven/org.springframework.security/spring-security-core@6.3.4?type=jar",
//       "pkg:maven/org.springframework/spring-aop@6.1.14?type=jar",
//       "pkg:maven/org.springframework/spring-beans@6.1.14?type=jar",
//       "pkg:maven/org.springframework/spring-context@6.1.14?type=jar",
//       "pkg:maven/org.springframework/spring-core@6.1.14?type=jar",
//       "pkg:maven/org.springframework/spring-expression@6.1.14?type=jar",
//       "pkg:maven/org.springframework/spring-web@6.1.14?type=jar"
//     ]
//   },
//   {
//     "ref": "pkg:maven/org.springframework/spring-web@6.1.14?type=jar",
//     "dependsOn": [
//       "pkg:maven/io.micrometer/micrometer-observation@1.13.6?type=jar",
//       "pkg:maven/org.springframework/spring-beans@6.1.14?type=jar",
//       "pkg:maven/org.springframework/spring-core@6.1.14?type=jar"
//     ]
//   },
//   {
//     "ref": "pkg:maven/org.springframework.boot/spring-boot-starter-web@3.3.5?type=jar",
//     "dependsOn": [
//       "pkg:maven/org.springframework.boot/spring-boot-starter-json@3.3.5?type=jar",
//       "pkg:maven/org.springframework.boot/spring-boot-starter-tomcat@3.3.5?type=jar",
//       "pkg:maven/org.springframework.boot/spring-boot-starter@3.3.5?type=jar",
//       "pkg:maven/org.springframework/spring-web@6.1.14?type=jar",
//       "pkg:maven/org.springframework/spring-webmvc@6.1.14?type=jar"
//     ]
//   },
//   {
//     "ref": "pkg:maven/org.springframework.boot/spring-boot-starter-json@3.3.5?type=jar",
//     "dependsOn": [
//       "pkg:maven/com.fasterxml.jackson.core/jackson-databind@2.17.2?type=jar",
//       "pkg:maven/com.fasterxml.jackson.datatype/jackson-datatype-jdk8@2.17.2?type=jar",
//       "pkg:maven/com.fasterxml.jackson.datatype/jackson-datatype-jsr310@2.17.2?type=jar",
//       "pkg:maven/com.fasterxml.jackson.module/jackson-module-parameter-names@2.17.2?type=jar",
//       "pkg:maven/org.springframework.boot/spring-boot-starter@3.3.5?type=jar",
//       "pkg:maven/org.springframework/spring-web@6.1.14?type=jar"
//     ]
//   },
//   {
//     "ref": "pkg:maven/com.fasterxml.jackson.datatype/jackson-datatype-jdk8@2.17.2?type=jar",
//     "dependsOn": [
//       "pkg:maven/com.fasterxml.jackson.core/jackson-core@2.17.2?type=jar",
//       "pkg:maven/com.fasterxml.jackson.core/jackson-databind@2.17.2?type=jar"
//     ]
//   },
//   {
//     "ref": "pkg:maven/com.fasterxml.jackson.module/jackson-module-parameter-names@2.17.2?type=jar",
//     "dependsOn": [
//       "pkg:maven/com.fasterxml.jackson.core/jackson-core@2.17.2?type=jar",
//       "pkg:maven/com.fasterxml.jackson.core/jackson-databind@2.17.2?type=jar"
//     ]
//   },
//   {
//     "ref": "pkg:maven/org.springframework.boot/spring-boot-starter-tomcat@3.3.5?type=jar",
//     "dependsOn": [
//       "pkg:maven/jakarta.annotation/jakarta.annotation-api@2.1.1?type=jar",
//       "pkg:maven/org.apache.tomcat.embed/tomcat-embed-core@10.1.31?type=jar",
//       "pkg:maven/org.apache.tomcat.embed/tomcat-embed-el@10.1.31?type=jar",
//       "pkg:maven/org.apache.tomcat.embed/tomcat-embed-websocket@10.1.31?type=jar"
//     ]
//   },
//   {
//     "ref": "pkg:maven/org.apache.tomcat.embed/tomcat-embed-core@10.1.31?type=jar",
//     "dependsOn": []
//   },
//   {
//     "ref": "pkg:maven/org.apache.tomcat.embed/tomcat-embed-el@10.1.31?type=jar",
//     "dependsOn": []
//   },
//   {
//     "ref": "pkg:maven/org.apache.tomcat.embed/tomcat-embed-websocket@10.1.31?type=jar",
//     "dependsOn": [
//       "pkg:maven/org.apache.tomcat.embed/tomcat-embed-core@10.1.31?type=jar"
//     ]
//   },
//   {
//     "ref": "pkg:maven/org.springframework/spring-webmvc@6.1.14?type=jar",
//     "dependsOn": [
//       "pkg:maven/org.springframework/spring-aop@6.1.14?type=jar",
//       "pkg:maven/org.springframework/spring-beans@6.1.14?type=jar",
//       "pkg:maven/org.springframework/spring-context@6.1.14?type=jar",
//       "pkg:maven/org.springframework/spring-core@6.1.14?type=jar",
//       "pkg:maven/org.springframework/spring-expression@6.1.14?type=jar",
//       "pkg:maven/org.springframework/spring-web@6.1.14?type=jar"
//     ]
//   },
//   {
//     "ref": "pkg:maven/org.projectlombok/lombok@1.18.34?type=jar",
//     "dependsOn": []
//   },
//   {
//     "ref": "pkg:maven/io.jsonwebtoken/jjwt-api@0.11.5?type=jar",
//     "dependsOn": []
//   },
//   {
//     "ref": "pkg:maven/io.jsonwebtoken/jjwt-impl@0.11.5?type=jar",
//     "dependsOn": [
//       "pkg:maven/io.jsonwebtoken/jjwt-api@0.11.5?type=jar"
//     ]
//   },
//   {
//     "ref": "pkg:maven/io.jsonwebtoken/jjwt-jackson@0.11.5?type=jar",
//     "dependsOn": [
//       "pkg:maven/com.fasterxml.jackson.core/jackson-databind@2.17.2?type=jar",
//       "pkg:maven/io.jsonwebtoken/jjwt-api@0.11.5?type=jar"
//     ]
//   },
//   {
//     "ref": "pkg:maven/org.postgresql/postgresql@42.7.3?type=jar",
//     "dependsOn": [
//       "pkg:maven/org.checkerframework/checker-qual@3.42.0?type=jar"
//     ]
//   },
//   {
//     "ref": "pkg:maven/org.checkerframework/checker-qual@3.42.0?type=jar",
//     "dependsOn": []
//   },
//   {
//     "ref": "pkg:maven/org.springframework.boot/spring-boot-starter-validation@3.3.5?type=jar",
//     "dependsOn": [
//       "pkg:maven/org.apache.tomcat.embed/tomcat-embed-el@10.1.31?type=jar",
//       "pkg:maven/org.hibernate.validator/hibernate-validator@8.0.1.Final?type=jar",
//       "pkg:maven/org.springframework.boot/spring-boot-starter@3.3.5?type=jar"
//     ]
//   },
//   {
//     "ref": "pkg:maven/org.hibernate.validator/hibernate-validator@8.0.1.Final?type=jar",
//     "dependsOn": [
//       "pkg:maven/com.fasterxml/classmate@1.7.0?type=jar",
//       "pkg:maven/jakarta.validation/jakarta.validation-api@3.0.2?type=jar",
//       "pkg:maven/org.jboss.logging/jboss-logging@3.5.3.Final?type=jar"
//     ]
//   },
//   {
//     "ref": "pkg:maven/jakarta.validation/jakarta.validation-api@3.0.2?type=jar",
//     "dependsOn": []
//   },
//   {
//     "ref": "pkg:maven/org.springframework.boot/spring-boot-starter-test@3.3.5?type=jar",
//     "dependsOn": [
//       "pkg:maven/com.jayway.jsonpath/json-path@2.9.0?type=jar",
//       "pkg:maven/jakarta.xml.bind/jakarta.xml.bind-api@4.0.2?type=jar",
//       "pkg:maven/net.minidev/json-smart@2.5.1?type=jar",
//       "pkg:maven/org.assertj/assertj-core@3.25.3?type=jar",
//       "pkg:maven/org.awaitility/awaitility@4.2.2?type=jar",
//       "pkg:maven/org.hamcrest/hamcrest@2.2?type=jar",
//       "pkg:maven/org.junit.jupiter/junit-jupiter@5.10.5?type=jar",
//       "pkg:maven/org.mockito/mockito-core@5.11.0?type=jar",
//       "pkg:maven/org.mockito/mockito-junit-jupiter@5.11.0?type=jar",
//       "pkg:maven/org.skyscreamer/jsonassert@1.5.3?type=jar",
//       "pkg:maven/org.springframework.boot/spring-boot-starter@3.3.5?type=jar",
//       "pkg:maven/org.springframework.boot/spring-boot-test-autoconfigure@3.3.5?type=jar",
//       "pkg:maven/org.springframework.boot/spring-boot-test@3.3.5?type=jar",
//       "pkg:maven/org.springframework/spring-core@6.1.14?type=jar",
//       "pkg:maven/org.springframework/spring-test@6.1.14?type=jar",
//       "pkg:maven/org.xmlunit/xmlunit-core@2.9.1?type=jar"
//     ]
//   },
//   {
//     "ref": "pkg:maven/org.springframework.boot/spring-boot-test@3.3.5?type=jar",
//     "dependsOn": [
//       "pkg:maven/org.springframework.boot/spring-boot@3.3.5?type=jar",
//       "pkg:maven/org.springframework/spring-test@6.1.14?type=jar"
//     ]
//   },
//   {
//     "ref": "pkg:maven/org.springframework/spring-test@6.1.14?type=jar",
//     "dependsOn": [
//       "pkg:maven/org.springframework/spring-core@6.1.14?type=jar"
//     ]
//   },
//   {
//     "ref": "pkg:maven/org.springframework.boot/spring-boot-test-autoconfigure@3.3.5?type=jar",
//     "dependsOn": [
//       "pkg:maven/org.springframework.boot/spring-boot-autoconfigure@3.3.5?type=jar",
//       "pkg:maven/org.springframework.boot/spring-boot-test@3.3.5?type=jar",
//       "pkg:maven/org.springframework.boot/spring-boot@3.3.5?type=jar"
//     ]
//   },
//   {
//     "ref": "pkg:maven/com.jayway.jsonpath/json-path@2.9.0?type=jar",
//     "dependsOn": [
//       "pkg:maven/net.minidev/json-smart@2.5.1?type=jar",
//       "pkg:maven/org.slf4j/slf4j-api@2.0.16?type=jar"
//     ]
//   },
//   {
//     "ref": "pkg:maven/net.minidev/json-smart@2.5.1?type=jar",
//     "dependsOn": [
//       "pkg:maven/net.minidev/accessors-smart@2.5.1?type=jar"
//     ]
//   },
//   {
//     "ref": "pkg:maven/net.minidev/accessors-smart@2.5.1?type=jar",
//     "dependsOn": [
//       "pkg:maven/org.ow2.asm/asm@9.6?type=jar"
//     ]
//   },
//   {
//     "ref": "pkg:maven/org.ow2.asm/asm@9.6?type=jar",
//     "dependsOn": []
//   },
//   {
//     "ref": "pkg:maven/org.assertj/assertj-core@3.25.3?type=jar",
//     "dependsOn": [
//       "pkg:maven/net.bytebuddy/byte-buddy@1.14.19?type=jar"
//     ]
//   },
//   {
//     "ref": "pkg:maven/org.awaitility/awaitility@4.2.2?type=jar",
//     "dependsOn": [
//       "pkg:maven/org.hamcrest/hamcrest@2.2?type=jar"
//     ]
//   },
//   {
//     "ref": "pkg:maven/org.hamcrest/hamcrest@2.2?type=jar",
//     "dependsOn": []
//   },
//   {
//     "ref": "pkg:maven/org.junit.jupiter/junit-jupiter@5.10.5?type=jar",
//     "dependsOn": [
//       "pkg:maven/org.junit.jupiter/junit-jupiter-api@5.10.5?type=jar",
//       "pkg:maven/org.junit.jupiter/junit-jupiter-engine@5.10.5?type=jar",
//       "pkg:maven/org.junit.jupiter/junit-jupiter-params@5.10.5?type=jar"
//     ]
//   },
//   {
//     "ref": "pkg:maven/org.junit.jupiter/junit-jupiter-api@5.10.5?type=jar",
//     "dependsOn": [
//       "pkg:maven/org.apiguardian/apiguardian-api@1.1.2?type=jar",
//       "pkg:maven/org.junit.platform/junit-platform-commons@1.10.5?type=jar",
//       "pkg:maven/org.opentest4j/opentest4j@1.3.0?type=jar"
//     ]
//   },
//   {
//     "ref": "pkg:maven/org.opentest4j/opentest4j@1.3.0?type=jar",
//     "dependsOn": []
//   },
//   {
//     "ref": "pkg:maven/org.junit.platform/junit-platform-commons@1.10.5?type=jar",
//     "dependsOn": [
//       "pkg:maven/org.apiguardian/apiguardian-api@1.1.2?type=jar"
//     ]
//   },
//   {
//     "ref": "pkg:maven/org.apiguardian/apiguardian-api@1.1.2?type=jar",
//     "dependsOn": []
//   },
//   {
//     "ref": "pkg:maven/org.junit.jupiter/junit-jupiter-params@5.10.5?type=jar",
//     "dependsOn": [
//       "pkg:maven/org.apiguardian/apiguardian-api@1.1.2?type=jar",
//       "pkg:maven/org.junit.jupiter/junit-jupiter-api@5.10.5?type=jar"
//     ]
//   },
//   {
//     "ref": "pkg:maven/org.junit.jupiter/junit-jupiter-engine@5.10.5?type=jar",
//     "dependsOn": [
//       "pkg:maven/org.apiguardian/apiguardian-api@1.1.2?type=jar",
//       "pkg:maven/org.junit.jupiter/junit-jupiter-api@5.10.5?type=jar",
//       "pkg:maven/org.junit.platform/junit-platform-engine@1.10.5?type=jar"
//     ]
//   },
//   {
//     "ref": "pkg:maven/org.junit.platform/junit-platform-engine@1.10.5?type=jar",
//     "dependsOn": [
//       "pkg:maven/org.apiguardian/apiguardian-api@1.1.2?type=jar",
//       "pkg:maven/org.junit.platform/junit-platform-commons@1.10.5?type=jar",
//       "pkg:maven/org.opentest4j/opentest4j@1.3.0?type=jar"
//     ]
//   },
//   {
//     "ref": "pkg:maven/org.mockito/mockito-core@5.11.0?type=jar",
//     "dependsOn": [
//       "pkg:maven/net.bytebuddy/byte-buddy-agent@1.14.19?type=jar",
//       "pkg:maven/net.bytebuddy/byte-buddy@1.14.19?type=jar",
//       "pkg:maven/org.objenesis/objenesis@3.3?type=jar"
//     ]
//   },
//   {
//     "ref": "pkg:maven/net.bytebuddy/byte-buddy-agent@1.14.19?type=jar",
//     "dependsOn": []
//   },
//   {
//     "ref": "pkg:maven/org.objenesis/objenesis@3.3?type=jar",
//     "dependsOn": []
//   },
//   {
//     "ref": "pkg:maven/org.mockito/mockito-junit-jupiter@5.11.0?type=jar",
//     "dependsOn": [
//       "pkg:maven/org.junit.jupiter/junit-jupiter-api@5.10.5?type=jar",
//       "pkg:maven/org.mockito/mockito-core@5.11.0?type=jar"
//     ]
//   },
//   {
//     "ref": "pkg:maven/org.skyscreamer/jsonassert@1.5.3?type=jar",
//     "dependsOn": [
//       "pkg:maven/com.vaadin.external.google/android-json@0.0.20131108.vaadin1?type=jar"
//     ]
//   },
//   {
//     "ref": "pkg:maven/com.vaadin.external.google/android-json@0.0.20131108.vaadin1?type=jar",
//     "dependsOn": []
//   },
//   {
//     "ref": "pkg:maven/org.xmlunit/xmlunit-core@2.9.1?type=jar",
//     "dependsOn": [
//       "pkg:maven/jakarta.xml.bind/jakarta.xml.bind-api@4.0.2?type=jar"
//     ]
//   },
//   {
//     "ref": "pkg:maven/org.springframework.security/spring-security-test@6.3.4?type=jar",
//     "dependsOn": [
//       "pkg:maven/org.springframework.security/spring-security-core@6.3.4?type=jar",
//       "pkg:maven/org.springframework.security/spring-security-web@6.3.4?type=jar",
//       "pkg:maven/org.springframework/spring-core@6.1.14?type=jar",
//       "pkg:maven/org.springframework/spring-test@6.1.14?type=jar"
//     ]
//   }
// ]

const dependencies = [
  {
      "ref": "pkg:maven/com.vinhdd/sbom-api-server@0.0.1-SNAPSHOT?type=jar",
      "dependsOn": [
          "pkg:maven/org.springframework.boot/spring-boot-starter-test@3.3.5?type=jar",
          "pkg:maven/org.springframework.boot/spring-boot-starter-data-jpa@3.3.5?type=jar",
          "pkg:maven/org.springframework.boot/spring-boot-starter-web@3.3.5?type=jar",
          "pkg:maven/org.postgresql/postgresql@42.7.3?type=jar",
          "pkg:maven/org.projectlombok/lombok@1.18.34?type=jar",
          "pkg:maven/io.jsonwebtoken/jjwt-impl@0.11.5?type=jar",
          "pkg:maven/io.jsonwebtoken/jjwt-jackson@0.11.5?type=jar",
          "pkg:maven/org.springframework.boot/spring-boot-starter-validation@3.3.5?type=jar",
          "pkg:maven/org.springframework.boot/spring-boot-starter-security@3.3.5?type=jar",
          "pkg:maven/org.springframework.security/spring-security-test@6.3.4?type=jar",
          "pkg:maven/io.jsonwebtoken/jjwt-api@0.11.5?type=jar",
          "pkg:maven/org.springframework.boot/spring-boot-starter-actuator@3.3.5?type=jar"
      ]
  },
  {
      "ref": "pkg:maven/org.springframework.boot/spring-boot-starter-test@3.3.5?type=jar",
      "dependsOn": [
          "pkg:maven/net.minidev/json-smart@2.5.1?type=jar",
          "pkg:maven/org.springframework.boot/spring-boot-test-autoconfigure@3.3.5?type=jar",
          "pkg:maven/org.junit.jupiter/junit-jupiter@5.10.5?type=jar",
          "pkg:maven/org.springframework.boot/spring-boot-starter@3.3.5?type=jar",
          "pkg:maven/org.mockito/mockito-junit-jupiter@5.11.0?type=jar",
          "pkg:maven/org.skyscreamer/jsonassert@1.5.3?type=jar",
          "pkg:maven/org.hamcrest/hamcrest@2.2?type=jar",
          "pkg:maven/org.springframework.boot/spring-boot-test@3.3.5?type=jar",
          "pkg:maven/org.xmlunit/xmlunit-core@2.9.1?type=jar",
          "pkg:maven/org.springframework/spring-test@6.1.14?type=jar",
          "pkg:maven/org.awaitility/awaitility@4.2.2?type=jar",
          "pkg:maven/org.assertj/assertj-core@3.25.3?type=jar",
          "pkg:maven/jakarta.xml.bind/jakarta.xml.bind-api@4.0.2?type=jar",
          "pkg:maven/org.springframework/spring-core@6.1.14?type=jar",
          "pkg:maven/com.jayway.jsonpath/json-path@2.9.0?type=jar",
          "pkg:maven/org.mockito/mockito-core@5.11.0?type=jar"
      ]
  },
  {
      "ref": "pkg:maven/net.minidev/json-smart@2.5.1?type=jar",
      "dependsOn": [
          "pkg:maven/net.minidev/accessors-smart@2.5.1?type=jar"
      ]
  },
  {
      "ref": "pkg:maven/net.minidev/accessors-smart@2.5.1?type=jar",
      "dependsOn": [
          "pkg:maven/org.ow2.asm/asm@9.6?type=jar"
      ]
  },
  {
      "ref": "pkg:maven/org.ow2.asm/asm@9.6?type=jar",
      "dependsOn": []
  },
  {
      "ref": "pkg:maven/org.springframework.boot/spring-boot-test-autoconfigure@3.3.5?type=jar",
      "dependsOn": [
          "pkg:maven/org.springframework.boot/spring-boot-test@3.3.5?type=jar",
          "pkg:maven/org.springframework.boot/spring-boot-autoconfigure@3.3.5?type=jar",
          "pkg:maven/org.springframework.boot/spring-boot@3.3.5?type=jar"
      ]
  },
  {
      "ref": "pkg:maven/org.springframework.boot/spring-boot-test@3.3.5?type=jar",
      "dependsOn": [
          "pkg:maven/org.springframework/spring-test@6.1.14?type=jar",
          "pkg:maven/org.springframework.boot/spring-boot@3.3.5?type=jar"
      ]
  },
  {
      "ref": "pkg:maven/org.springframework/spring-test@6.1.14?type=jar",
      "dependsOn": [
          "pkg:maven/org.springframework/spring-core@6.1.14?type=jar"
      ]
  },
  {
      "ref": "pkg:maven/org.springframework/spring-core@6.1.14?type=jar",
      "dependsOn": [
          "pkg:maven/org.springframework/spring-jcl@6.1.14?type=jar"
      ]
  },
  {
      "ref": "pkg:maven/org.springframework/spring-jcl@6.1.14?type=jar",
      "dependsOn": []
  },
  {
      "ref": "pkg:maven/org.springframework.boot/spring-boot@3.3.5?type=jar",
      "dependsOn": [
          "pkg:maven/org.springframework/spring-core@6.1.14?type=jar",
          "pkg:maven/org.springframework/spring-context@6.1.14?type=jar"
      ]
  },
  {
      "ref": "pkg:maven/org.springframework/spring-context@6.1.14?type=jar",
      "dependsOn": [
          "pkg:maven/io.micrometer/micrometer-observation@1.13.6?type=jar",
          "pkg:maven/org.springframework/spring-aop@6.1.14?type=jar",
          "pkg:maven/org.springframework/spring-expression@6.1.14?type=jar",
          "pkg:maven/org.springframework/spring-core@6.1.14?type=jar",
          "pkg:maven/org.springframework/spring-beans@6.1.14?type=jar"
      ]
  },
  {
      "ref": "pkg:maven/io.micrometer/micrometer-observation@1.13.6?type=jar",
      "dependsOn": [
          "pkg:maven/io.micrometer/micrometer-commons@1.13.6?type=jar"
      ]
  },
  {
      "ref": "pkg:maven/io.micrometer/micrometer-commons@1.13.6?type=jar",
      "dependsOn": []
  },
  {
      "ref": "pkg:maven/org.springframework/spring-aop@6.1.14?type=jar",
      "dependsOn": [
          "pkg:maven/org.springframework/spring-core@6.1.14?type=jar",
          "pkg:maven/org.springframework/spring-beans@6.1.14?type=jar"
      ]
  },
  {
      "ref": "pkg:maven/org.springframework/spring-beans@6.1.14?type=jar",
      "dependsOn": [
          "pkg:maven/org.springframework/spring-core@6.1.14?type=jar"
      ]
  },
  {
      "ref": "pkg:maven/org.springframework/spring-expression@6.1.14?type=jar",
      "dependsOn": [
          "pkg:maven/org.springframework/spring-core@6.1.14?type=jar"
      ]
  },
  {
      "ref": "pkg:maven/org.springframework.boot/spring-boot-autoconfigure@3.3.5?type=jar",
      "dependsOn": [
          "pkg:maven/org.springframework.boot/spring-boot@3.3.5?type=jar"
      ]
  },
  {
      "ref": "pkg:maven/org.junit.jupiter/junit-jupiter@5.10.5?type=jar",
      "dependsOn": [
          "pkg:maven/org.junit.jupiter/junit-jupiter-engine@5.10.5?type=jar",
          "pkg:maven/org.junit.jupiter/junit-jupiter-api@5.10.5?type=jar",
          "pkg:maven/org.junit.jupiter/junit-jupiter-params@5.10.5?type=jar"
      ]
  },
  {
      "ref": "pkg:maven/org.junit.jupiter/junit-jupiter-engine@5.10.5?type=jar",
      "dependsOn": [
          "pkg:maven/org.apiguardian/apiguardian-api@1.1.2?type=jar",
          "pkg:maven/org.junit.platform/junit-platform-engine@1.10.5?type=jar",
          "pkg:maven/org.junit.jupiter/junit-jupiter-api@5.10.5?type=jar"
      ]
  },
  {
      "ref": "pkg:maven/org.apiguardian/apiguardian-api@1.1.2?type=jar",
      "dependsOn": []
  },
  {
      "ref": "pkg:maven/org.junit.platform/junit-platform-engine@1.10.5?type=jar",
      "dependsOn": [
          "pkg:maven/org.opentest4j/opentest4j@1.3.0?type=jar",
          "pkg:maven/org.apiguardian/apiguardian-api@1.1.2?type=jar",
          "pkg:maven/org.junit.platform/junit-platform-commons@1.10.5?type=jar"
      ]
  },
  {
      "ref": "pkg:maven/org.opentest4j/opentest4j@1.3.0?type=jar",
      "dependsOn": []
  },
  {
      "ref": "pkg:maven/org.junit.platform/junit-platform-commons@1.10.5?type=jar",
      "dependsOn": [
          "pkg:maven/org.apiguardian/apiguardian-api@1.1.2?type=jar"
      ]
  },
  {
      "ref": "pkg:maven/org.junit.jupiter/junit-jupiter-api@5.10.5?type=jar",
      "dependsOn": [
          "pkg:maven/org.opentest4j/opentest4j@1.3.0?type=jar",
          "pkg:maven/org.apiguardian/apiguardian-api@1.1.2?type=jar",
          "pkg:maven/org.junit.platform/junit-platform-commons@1.10.5?type=jar"
      ]
  },
  {
      "ref": "pkg:maven/org.junit.jupiter/junit-jupiter-params@5.10.5?type=jar",
      "dependsOn": [
          "pkg:maven/org.apiguardian/apiguardian-api@1.1.2?type=jar",
          "pkg:maven/org.junit.jupiter/junit-jupiter-api@5.10.5?type=jar"
      ]
  },
  {
      "ref": "pkg:maven/org.springframework.boot/spring-boot-starter@3.3.5?type=jar",
      "dependsOn": [
          "pkg:maven/org.yaml/snakeyaml@2.2?type=jar",
          "pkg:maven/jakarta.annotation/jakarta.annotation-api@2.1.1?type=jar",
          "pkg:maven/org.springframework/spring-core@6.1.14?type=jar",
          "pkg:maven/org.springframework.boot/spring-boot-starter-logging@3.3.5?type=jar",
          "pkg:maven/org.springframework.boot/spring-boot-autoconfigure@3.3.5?type=jar",
          "pkg:maven/org.springframework.boot/spring-boot@3.3.5?type=jar"
      ]
  },
  {
      "ref": "pkg:maven/org.yaml/snakeyaml@2.2?type=jar",
      "dependsOn": []
  },
  {
      "ref": "pkg:maven/jakarta.annotation/jakarta.annotation-api@2.1.1?type=jar",
      "dependsOn": []
  },
  {
      "ref": "pkg:maven/org.springframework.boot/spring-boot-starter-logging@3.3.5?type=jar",
      "dependsOn": [
          "pkg:maven/org.apache.logging.log4j/log4j-to-slf4j@2.23.1?type=jar",
          "pkg:maven/ch.qos.logback/logback-classic@1.5.11?type=jar",
          "pkg:maven/org.slf4j/jul-to-slf4j@2.0.16?type=jar"
      ]
  },
  {
      "ref": "pkg:maven/org.apache.logging.log4j/log4j-to-slf4j@2.23.1?type=jar",
      "dependsOn": [
          "pkg:maven/org.slf4j/slf4j-api@2.0.16?type=jar",
          "pkg:maven/org.apache.logging.log4j/log4j-api@2.23.1?type=jar"
      ]
  },
  {
      "ref": "pkg:maven/org.slf4j/slf4j-api@2.0.16?type=jar",
      "dependsOn": []
  },
  {
      "ref": "pkg:maven/org.apache.logging.log4j/log4j-api@2.23.1?type=jar",
      "dependsOn": []
  },
  {
      "ref": "pkg:maven/ch.qos.logback/logback-classic@1.5.11?type=jar",
      "dependsOn": [
          "pkg:maven/ch.qos.logback/logback-core@1.5.11?type=jar",
          "pkg:maven/org.slf4j/slf4j-api@2.0.16?type=jar"
      ]
  },
  {
      "ref": "pkg:maven/ch.qos.logback/logback-core@1.5.11?type=jar",
      "dependsOn": []
  },
  {
      "ref": "pkg:maven/org.slf4j/jul-to-slf4j@2.0.16?type=jar",
      "dependsOn": [
          "pkg:maven/org.slf4j/slf4j-api@2.0.16?type=jar"
      ]
  },
  {
      "ref": "pkg:maven/org.mockito/mockito-junit-jupiter@5.11.0?type=jar",
      "dependsOn": [
          "pkg:maven/org.junit.jupiter/junit-jupiter-api@5.10.5?type=jar",
          "pkg:maven/org.mockito/mockito-core@5.11.0?type=jar"
      ]
  },
  {
      "ref": "pkg:maven/org.mockito/mockito-core@5.11.0?type=jar",
      "dependsOn": [
          "pkg:maven/net.bytebuddy/byte-buddy@1.14.19?type=jar",
          "pkg:maven/net.bytebuddy/byte-buddy-agent@1.14.19?type=jar",
          "pkg:maven/org.objenesis/objenesis@3.3?type=jar"
      ]
  },
  {
      "ref": "pkg:maven/net.bytebuddy/byte-buddy@1.14.19?type=jar",
      "dependsOn": []
  },
  {
      "ref": "pkg:maven/net.bytebuddy/byte-buddy-agent@1.14.19?type=jar",
      "dependsOn": []
  },
  {
      "ref": "pkg:maven/org.objenesis/objenesis@3.3?type=jar",
      "dependsOn": []
  },
  {
      "ref": "pkg:maven/org.skyscreamer/jsonassert@1.5.3?type=jar",
      "dependsOn": [
          "pkg:maven/com.vaadin.external.google/android-json@0.0.20131108.vaadin1?type=jar"
      ]
  },
  {
      "ref": "pkg:maven/com.vaadin.external.google/android-json@0.0.20131108.vaadin1?type=jar",
      "dependsOn": []
  },
  {
      "ref": "pkg:maven/org.hamcrest/hamcrest@2.2?type=jar",
      "dependsOn": []
  },
  {
      "ref": "pkg:maven/org.xmlunit/xmlunit-core@2.9.1?type=jar",
      "dependsOn": [
          "pkg:maven/jakarta.xml.bind/jakarta.xml.bind-api@4.0.2?type=jar"
      ]
  },
  {
      "ref": "pkg:maven/jakarta.xml.bind/jakarta.xml.bind-api@4.0.2?type=jar",
      "dependsOn": [
          "pkg:maven/jakarta.activation/jakarta.activation-api@2.1.3?type=jar"
      ]
  },
  {
      "ref": "pkg:maven/jakarta.activation/jakarta.activation-api@2.1.3?type=jar",
      "dependsOn": []
  },
  {
      "ref": "pkg:maven/org.awaitility/awaitility@4.2.2?type=jar",
      "dependsOn": [
          "pkg:maven/org.hamcrest/hamcrest@2.2?type=jar"
      ]
  },
  {
      "ref": "pkg:maven/org.assertj/assertj-core@3.25.3?type=jar",
      "dependsOn": [
          "pkg:maven/net.bytebuddy/byte-buddy@1.14.19?type=jar"
      ]
  },
  {
      "ref": "pkg:maven/com.jayway.jsonpath/json-path@2.9.0?type=jar",
      "dependsOn": [
          "pkg:maven/net.minidev/json-smart@2.5.1?type=jar",
          "pkg:maven/org.slf4j/slf4j-api@2.0.16?type=jar"
      ]
  },
  {
      "ref": "pkg:maven/org.springframework.boot/spring-boot-starter-data-jpa@3.3.5?type=jar",
      "dependsOn": [
          "pkg:maven/org.springframework.data/spring-data-jpa@3.3.5?type=jar",
          "pkg:maven/org.springframework.boot/spring-boot-starter-jdbc@3.3.5?type=jar",
          "pkg:maven/org.hibernate.orm/hibernate-core@6.5.3.Final?type=jar",
          "pkg:maven/org.springframework.boot/spring-boot-starter-aop@3.3.5?type=jar",
          "pkg:maven/org.springframework/spring-aspects@6.1.14?type=jar"
      ]
  },
  {
      "ref": "pkg:maven/org.springframework.data/spring-data-jpa@3.3.5?type=jar",
      "dependsOn": [
          "pkg:maven/jakarta.annotation/jakarta.annotation-api@2.1.1?type=jar",
          "pkg:maven/org.slf4j/slf4j-api@2.0.16?type=jar",
          "pkg:maven/org.antlr/antlr4-runtime@4.13.0?type=jar",
          "pkg:maven/org.springframework.data/spring-data-commons@3.3.5?type=jar",
          "pkg:maven/org.springframework/spring-tx@6.1.14?type=jar",
          "pkg:maven/org.springframework/spring-orm@6.1.14?type=jar",
          "pkg:maven/org.springframework/spring-aop@6.1.14?type=jar",
          "pkg:maven/org.springframework/spring-core@6.1.14?type=jar",
          "pkg:maven/org.springframework/spring-context@6.1.14?type=jar",
          "pkg:maven/org.springframework/spring-beans@6.1.14?type=jar"
      ]
  },
  {
      "ref": "pkg:maven/org.antlr/antlr4-runtime@4.13.0?type=jar",
      "dependsOn": []
  },
  {
      "ref": "pkg:maven/org.springframework.data/spring-data-commons@3.3.5?type=jar",
      "dependsOn": [
          "pkg:maven/org.slf4j/slf4j-api@2.0.16?type=jar",
          "pkg:maven/org.springframework/spring-core@6.1.14?type=jar",
          "pkg:maven/org.springframework/spring-beans@6.1.14?type=jar"
      ]
  },
  {
      "ref": "pkg:maven/org.springframework/spring-tx@6.1.14?type=jar",
      "dependsOn": [
          "pkg:maven/org.springframework/spring-core@6.1.14?type=jar",
          "pkg:maven/org.springframework/spring-beans@6.1.14?type=jar"
      ]
  },
  {
      "ref": "pkg:maven/org.springframework/spring-orm@6.1.14?type=jar",
      "dependsOn": [
          "pkg:maven/org.springframework/spring-tx@6.1.14?type=jar",
          "pkg:maven/org.springframework/spring-core@6.1.14?type=jar",
          "pkg:maven/org.springframework/spring-jdbc@6.1.14?type=jar",
          "pkg:maven/org.springframework/spring-beans@6.1.14?type=jar"
      ]
  },
  {
      "ref": "pkg:maven/org.springframework/spring-jdbc@6.1.14?type=jar",
      "dependsOn": [
          "pkg:maven/org.springframework/spring-tx@6.1.14?type=jar",
          "pkg:maven/org.springframework/spring-core@6.1.14?type=jar",
          "pkg:maven/org.springframework/spring-beans@6.1.14?type=jar"
      ]
  },
  {
      "ref": "pkg:maven/org.springframework.boot/spring-boot-starter-jdbc@3.3.5?type=jar",
      "dependsOn": [
          "pkg:maven/org.springframework.boot/spring-boot-starter@3.3.5?type=jar",
          "pkg:maven/com.zaxxer/HikariCP@5.1.0?type=jar",
          "pkg:maven/org.springframework/spring-jdbc@6.1.14?type=jar"
      ]
  },
  {
      "ref": "pkg:maven/com.zaxxer/HikariCP@5.1.0?type=jar",
      "dependsOn": [
          "pkg:maven/org.slf4j/slf4j-api@2.0.16?type=jar"
      ]
  },
  {
      "ref": "pkg:maven/org.hibernate.orm/hibernate-core@6.5.3.Final?type=jar",
      "dependsOn": [
          "pkg:maven/org.hibernate.common/hibernate-commons-annotations@6.0.6.Final?type=jar",
          "pkg:maven/com.fasterxml/classmate@1.7.0?type=jar",
          "pkg:maven/org.antlr/antlr4-runtime@4.13.0?type=jar",
          "pkg:maven/jakarta.transaction/jakarta.transaction-api@2.0.1?type=jar",
          "pkg:maven/jakarta.inject/jakarta.inject-api@2.0.1?type=jar",
          "pkg:maven/jakarta.persistence/jakarta.persistence-api@3.1.0?type=jar",
          "pkg:maven/net.bytebuddy/byte-buddy@1.14.19?type=jar",
          "pkg:maven/jakarta.xml.bind/jakarta.xml.bind-api@4.0.2?type=jar",
          "pkg:maven/io.smallrye/jandex@3.1.2?type=jar",
          "pkg:maven/org.jboss.logging/jboss-logging@3.5.3.Final?type=jar",
          "pkg:maven/org.glassfish.jaxb/jaxb-runtime@4.0.5?type=jar"
      ]
  },
  {
      "ref": "pkg:maven/org.hibernate.common/hibernate-commons-annotations@6.0.6.Final?type=jar",
      "dependsOn": []
  },
  {
      "ref": "pkg:maven/com.fasterxml/classmate@1.7.0?type=jar",
      "dependsOn": []
  },
  {
      "ref": "pkg:maven/jakarta.transaction/jakarta.transaction-api@2.0.1?type=jar",
      "dependsOn": []
  },
  {
      "ref": "pkg:maven/jakarta.inject/jakarta.inject-api@2.0.1?type=jar",
      "dependsOn": []
  },
  {
      "ref": "pkg:maven/jakarta.persistence/jakarta.persistence-api@3.1.0?type=jar",
      "dependsOn": []
  },
  {
      "ref": "pkg:maven/io.smallrye/jandex@3.1.2?type=jar",
      "dependsOn": []
  },
  {
      "ref": "pkg:maven/org.jboss.logging/jboss-logging@3.5.3.Final?type=jar",
      "dependsOn": []
  },
  {
      "ref": "pkg:maven/org.glassfish.jaxb/jaxb-runtime@4.0.5?type=jar",
      "dependsOn": [
          "pkg:maven/org.glassfish.jaxb/jaxb-core@4.0.5?type=jar"
      ]
  },
  {
      "ref": "pkg:maven/org.glassfish.jaxb/jaxb-core@4.0.5?type=jar",
      "dependsOn": [
          "pkg:maven/jakarta.activation/jakarta.activation-api@2.1.3?type=jar",
          "pkg:maven/org.eclipse.angus/angus-activation@2.0.2?type=jar",
          "pkg:maven/org.glassfish.jaxb/txw2@4.0.5?type=jar",
          "pkg:maven/jakarta.xml.bind/jakarta.xml.bind-api@4.0.2?type=jar",
          "pkg:maven/com.sun.istack/istack-commons-runtime@4.1.2?type=jar"
      ]
  },
  {
      "ref": "pkg:maven/org.eclipse.angus/angus-activation@2.0.2?type=jar",
      "dependsOn": [
          "pkg:maven/jakarta.activation/jakarta.activation-api@2.1.3?type=jar"
      ]
  },
  {
      "ref": "pkg:maven/org.glassfish.jaxb/txw2@4.0.5?type=jar",
      "dependsOn": []
  },
  {
      "ref": "pkg:maven/com.sun.istack/istack-commons-runtime@4.1.2?type=jar",
      "dependsOn": []
  },
  {
      "ref": "pkg:maven/org.springframework.boot/spring-boot-starter-aop@3.3.5?type=jar",
      "dependsOn": [
          "pkg:maven/org.springframework.boot/spring-boot-starter@3.3.5?type=jar",
          "pkg:maven/org.aspectj/aspectjweaver@1.9.22.1?type=jar",
          "pkg:maven/org.springframework/spring-aop@6.1.14?type=jar"
      ]
  },
  {
      "ref": "pkg:maven/org.aspectj/aspectjweaver@1.9.22.1?type=jar",
      "dependsOn": []
  },
  {
      "ref": "pkg:maven/org.springframework/spring-aspects@6.1.14?type=jar",
      "dependsOn": [
          "pkg:maven/org.aspectj/aspectjweaver@1.9.22.1?type=jar"
      ]
  },
  {
      "ref": "pkg:maven/org.springframework.boot/spring-boot-starter-web@3.3.5?type=jar",
      "dependsOn": [
          "pkg:maven/org.springframework/spring-web@6.1.14?type=jar",
          "pkg:maven/org.springframework.boot/spring-boot-starter-json@3.3.5?type=jar",
          "pkg:maven/org.springframework.boot/spring-boot-starter@3.3.5?type=jar",
          "pkg:maven/org.springframework.boot/spring-boot-starter-tomcat@3.3.5?type=jar",
          "pkg:maven/org.springframework/spring-webmvc@6.1.14?type=jar"
      ]
  },
  {
      "ref": "pkg:maven/org.springframework/spring-web@6.1.14?type=jar",
      "dependsOn": [
          "pkg:maven/io.micrometer/micrometer-observation@1.13.6?type=jar",
          "pkg:maven/org.springframework/spring-core@6.1.14?type=jar",
          "pkg:maven/org.springframework/spring-beans@6.1.14?type=jar"
      ]
  },
  {
      "ref": "pkg:maven/org.springframework.boot/spring-boot-starter-json@3.3.5?type=jar",
      "dependsOn": [
          "pkg:maven/org.springframework/spring-web@6.1.14?type=jar",
          "pkg:maven/org.springframework.boot/spring-boot-starter@3.3.5?type=jar",
          "pkg:maven/com.fasterxml.jackson.datatype/jackson-datatype-jsr310@2.17.2?type=jar",
          "pkg:maven/com.fasterxml.jackson.core/jackson-databind@2.17.2?type=jar",
          "pkg:maven/com.fasterxml.jackson.datatype/jackson-datatype-jdk8@2.17.2?type=jar",
          "pkg:maven/com.fasterxml.jackson.module/jackson-module-parameter-names@2.17.2?type=jar"
      ]
  },
  {
      "ref": "pkg:maven/com.fasterxml.jackson.datatype/jackson-datatype-jsr310@2.17.2?type=jar",
      "dependsOn": [
          "pkg:maven/com.fasterxml.jackson.core/jackson-databind@2.17.2?type=jar",
          "pkg:maven/com.fasterxml.jackson.core/jackson-annotations@2.17.2?type=jar",
          "pkg:maven/com.fasterxml.jackson.core/jackson-core@2.17.2?type=jar"
      ]
  },
  {
      "ref": "pkg:maven/com.fasterxml.jackson.core/jackson-databind@2.17.2?type=jar",
      "dependsOn": [
          "pkg:maven/com.fasterxml.jackson.core/jackson-annotations@2.17.2?type=jar",
          "pkg:maven/com.fasterxml.jackson.core/jackson-core@2.17.2?type=jar"
      ]
  },
  {
      "ref": "pkg:maven/com.fasterxml.jackson.core/jackson-annotations@2.17.2?type=jar",
      "dependsOn": []
  },
  {
      "ref": "pkg:maven/com.fasterxml.jackson.core/jackson-core@2.17.2?type=jar",
      "dependsOn": []
  },
  {
      "ref": "pkg:maven/com.fasterxml.jackson.datatype/jackson-datatype-jdk8@2.17.2?type=jar",
      "dependsOn": [
          "pkg:maven/com.fasterxml.jackson.core/jackson-databind@2.17.2?type=jar",
          "pkg:maven/com.fasterxml.jackson.core/jackson-core@2.17.2?type=jar"
      ]
  },
  {
      "ref": "pkg:maven/com.fasterxml.jackson.module/jackson-module-parameter-names@2.17.2?type=jar",
      "dependsOn": [
          "pkg:maven/com.fasterxml.jackson.core/jackson-databind@2.17.2?type=jar",
          "pkg:maven/com.fasterxml.jackson.core/jackson-core@2.17.2?type=jar"
      ]
  },
  {
      "ref": "pkg:maven/org.springframework.boot/spring-boot-starter-tomcat@3.3.5?type=jar",
      "dependsOn": [
          "pkg:maven/jakarta.annotation/jakarta.annotation-api@2.1.1?type=jar",
          "pkg:maven/org.apache.tomcat.embed/tomcat-embed-websocket@10.1.31?type=jar",
          "pkg:maven/org.apache.tomcat.embed/tomcat-embed-core@10.1.31?type=jar",
          "pkg:maven/org.apache.tomcat.embed/tomcat-embed-el@10.1.31?type=jar"
      ]
  },
  {
      "ref": "pkg:maven/org.apache.tomcat.embed/tomcat-embed-websocket@10.1.31?type=jar",
      "dependsOn": [
          "pkg:maven/org.apache.tomcat.embed/tomcat-embed-core@10.1.31?type=jar"
      ]
  },
  {
      "ref": "pkg:maven/org.apache.tomcat.embed/tomcat-embed-core@10.1.31?type=jar",
      "dependsOn": []
  },
  {
      "ref": "pkg:maven/org.apache.tomcat.embed/tomcat-embed-el@10.1.31?type=jar",
      "dependsOn": []
  },
  {
      "ref": "pkg:maven/org.springframework/spring-webmvc@6.1.14?type=jar",
      "dependsOn": [
          "pkg:maven/org.springframework/spring-web@6.1.14?type=jar",
          "pkg:maven/org.springframework/spring-aop@6.1.14?type=jar",
          "pkg:maven/org.springframework/spring-expression@6.1.14?type=jar",
          "pkg:maven/org.springframework/spring-core@6.1.14?type=jar",
          "pkg:maven/org.springframework/spring-context@6.1.14?type=jar",
          "pkg:maven/org.springframework/spring-beans@6.1.14?type=jar"
      ]
  },
  {
      "ref": "pkg:maven/org.postgresql/postgresql@42.7.3?type=jar",
      "dependsOn": [
          "pkg:maven/org.checkerframework/checker-qual@3.42.0?type=jar"
      ]
  },
  {
      "ref": "pkg:maven/org.checkerframework/checker-qual@3.42.0?type=jar",
      "dependsOn": []
  },
  {
      "ref": "pkg:maven/org.projectlombok/lombok@1.18.34?type=jar",
      "dependsOn": []
  },
  {
      "ref": "pkg:maven/io.jsonwebtoken/jjwt-impl@0.11.5?type=jar",
      "dependsOn": [
          "pkg:maven/io.jsonwebtoken/jjwt-api@0.11.5?type=jar"
      ]
  },
  {
      "ref": "pkg:maven/io.jsonwebtoken/jjwt-api@0.11.5?type=jar",
      "dependsOn": []
  },
  {
      "ref": "pkg:maven/io.jsonwebtoken/jjwt-jackson@0.11.5?type=jar",
      "dependsOn": [
          "pkg:maven/com.fasterxml.jackson.core/jackson-databind@2.17.2?type=jar",
          "pkg:maven/io.jsonwebtoken/jjwt-api@0.11.5?type=jar"
      ]
  },
  {
      "ref": "pkg:maven/org.springframework.boot/spring-boot-starter-validation@3.3.5?type=jar",
      "dependsOn": [
          "pkg:maven/org.hibernate.validator/hibernate-validator@8.0.1.Final?type=jar",
          "pkg:maven/org.springframework.boot/spring-boot-starter@3.3.5?type=jar",
          "pkg:maven/org.apache.tomcat.embed/tomcat-embed-el@10.1.31?type=jar"
      ]
  },
  {
      "ref": "pkg:maven/org.hibernate.validator/hibernate-validator@8.0.1.Final?type=jar",
      "dependsOn": [
          "pkg:maven/com.fasterxml/classmate@1.7.0?type=jar",
          "pkg:maven/jakarta.validation/jakarta.validation-api@3.0.2?type=jar",
          "pkg:maven/org.jboss.logging/jboss-logging@3.5.3.Final?type=jar"
      ]
  },
  {
      "ref": "pkg:maven/jakarta.validation/jakarta.validation-api@3.0.2?type=jar",
      "dependsOn": []
  },
  {
      "ref": "pkg:maven/org.springframework.boot/spring-boot-starter-security@3.3.5?type=jar",
      "dependsOn": [
          "pkg:maven/org.springframework.boot/spring-boot-starter@3.3.5?type=jar",
          "pkg:maven/org.springframework.security/spring-security-config@6.3.4?type=jar",
          "pkg:maven/org.springframework/spring-aop@6.1.14?type=jar",
          "pkg:maven/org.springframework.security/spring-security-web@6.3.4?type=jar"
      ]
  },
  {
      "ref": "pkg:maven/org.springframework.security/spring-security-config@6.3.4?type=jar",
      "dependsOn": [
          "pkg:maven/org.springframework.security/spring-security-core@6.3.4?type=jar",
          "pkg:maven/org.springframework/spring-aop@6.1.14?type=jar",
          "pkg:maven/org.springframework/spring-core@6.1.14?type=jar",
          "pkg:maven/org.springframework/spring-context@6.1.14?type=jar",
          "pkg:maven/org.springframework/spring-beans@6.1.14?type=jar"
      ]
  },
  {
      "ref": "pkg:maven/org.springframework.security/spring-security-core@6.3.4?type=jar",
      "dependsOn": [
          "pkg:maven/io.micrometer/micrometer-observation@1.13.6?type=jar",
          "pkg:maven/org.springframework/spring-aop@6.1.14?type=jar",
          "pkg:maven/org.springframework/spring-expression@6.1.14?type=jar",
          "pkg:maven/org.springframework/spring-core@6.1.14?type=jar",
          "pkg:maven/org.springframework/spring-context@6.1.14?type=jar",
          "pkg:maven/org.springframework.security/spring-security-crypto@6.3.4?type=jar",
          "pkg:maven/org.springframework/spring-beans@6.1.14?type=jar"
      ]
  },
  {
      "ref": "pkg:maven/org.springframework.security/spring-security-crypto@6.3.4?type=jar",
      "dependsOn": []
  },
  {
      "ref": "pkg:maven/org.springframework.security/spring-security-web@6.3.4?type=jar",
      "dependsOn": [
          "pkg:maven/org.springframework/spring-web@6.1.14?type=jar",
          "pkg:maven/org.springframework.security/spring-security-core@6.3.4?type=jar",
          "pkg:maven/org.springframework/spring-aop@6.1.14?type=jar",
          "pkg:maven/org.springframework/spring-expression@6.1.14?type=jar",
          "pkg:maven/org.springframework/spring-core@6.1.14?type=jar",
          "pkg:maven/org.springframework/spring-context@6.1.14?type=jar",
          "pkg:maven/org.springframework/spring-beans@6.1.14?type=jar"
      ]
  },
  {
      "ref": "pkg:maven/org.springframework.security/spring-security-test@6.3.4?type=jar",
      "dependsOn": [
          "pkg:maven/org.springframework.security/spring-security-core@6.3.4?type=jar",
          "pkg:maven/org.springframework/spring-test@6.1.14?type=jar",
          "pkg:maven/org.springframework/spring-core@6.1.14?type=jar",
          "pkg:maven/org.springframework.security/spring-security-web@6.3.4?type=jar"
      ]
  },
  {
      "ref": "pkg:maven/org.springframework.boot/spring-boot-starter-actuator@3.3.5?type=jar",
      "dependsOn": [
          "pkg:maven/org.springframework.boot/spring-boot-starter@3.3.5?type=jar",
          "pkg:maven/io.micrometer/micrometer-observation@1.13.6?type=jar",
          "pkg:maven/org.springframework.boot/spring-boot-actuator-autoconfigure@3.3.5?type=jar",
          "pkg:maven/io.micrometer/micrometer-jakarta9@1.13.6?type=jar"
      ]
  },
  {
      "ref": "pkg:maven/org.springframework.boot/spring-boot-actuator-autoconfigure@3.3.5?type=jar",
      "dependsOn": [
          "pkg:maven/com.fasterxml.jackson.datatype/jackson-datatype-jsr310@2.17.2?type=jar",
          "pkg:maven/com.fasterxml.jackson.core/jackson-databind@2.17.2?type=jar",
          "pkg:maven/org.springframework.boot/spring-boot-actuator@3.3.5?type=jar",
          "pkg:maven/org.springframework.boot/spring-boot-autoconfigure@3.3.5?type=jar",
          "pkg:maven/org.springframework.boot/spring-boot@3.3.5?type=jar"
      ]
  },
  {
      "ref": "pkg:maven/org.springframework.boot/spring-boot-actuator@3.3.5?type=jar",
      "dependsOn": [
          "pkg:maven/org.springframework.boot/spring-boot@3.3.5?type=jar"
      ]
  },
  {
      "ref": "pkg:maven/io.micrometer/micrometer-jakarta9@1.13.6?type=jar",
      "dependsOn": [
          "pkg:maven/io.micrometer/micrometer-observation@1.13.6?type=jar",
          "pkg:maven/io.micrometer/micrometer-commons@1.13.6?type=jar",
          "pkg:maven/io.micrometer/micrometer-core@1.13.6?type=jar"
      ]
  },
  {
      "ref": "pkg:maven/io.micrometer/micrometer-core@1.13.6?type=jar",
      "dependsOn": [
          "pkg:maven/io.micrometer/micrometer-observation@1.13.6?type=jar",
          "pkg:maven/org.hdrhistogram/HdrHistogram@2.2.2?type=jar",
          "pkg:maven/io.micrometer/micrometer-commons@1.13.6?type=jar",
          "pkg:maven/org.latencyutils/LatencyUtils@2.0.3?type=jar"
      ]
  },
  {
      "ref": "pkg:maven/org.hdrhistogram/HdrHistogram@2.2.2?type=jar",
      "dependsOn": []
  },
  {
      "ref": "pkg:maven/org.latencyutils/LatencyUtils@2.0.3?type=jar",
      "dependsOn": []
  }
];

const countLeafNodes = (comp) => {
  if(comp.dependsOn.length === 0) {
    return 1;
  }
  return comp.dependsOn.reduce((acc, item) => acc + countLeafNodes(item), 0);
}

const findDependency = (ref) => {
  return dependencies.find(dep => dep.ref === ref)
};

const renderTree = (comp, x, y) => {
  const nodes = [];
  const edges = [];
  nodes.push({ id: comp.id, position: { x, y }, data: { label: comp.ref.split("?")[0] }, type: 'customNode' });
  const cd = countLeafNodes(comp)*h;
  let current = 0;
  comp.dependsOn.forEach((item, idx) => {
    const child = renderTree(item, x + w, y - cd/2 + countLeafNodes(item)*h/2 + current);
    current += countLeafNodes(item)*h;
    const childNodes = child.nodes;
    const childEdges = child.edges;
    nodes.push(...childNodes);
    edges.push({ id: `${comp.id}-${item.id}`, source: comp.id, target: item.id});
    edges.push(...childEdges);
  });
  return { nodes, edges };
}

const w = 500;
const h = 25;

function Board2() {
  const { token: {colorBgContainer, colorBorder}} = theme.useToken();
  const [tree, setTree] = useState({id: uuidv4(), ref: dependencies[0].ref, dependsOn: []});

  const findTreeById = (id, tree) => {
    if(tree.id === id) {
      return tree;
    }
    for(let i = 0; i < tree.dependsOn.length; i++) {
      const result = findTreeById(id, tree.dependsOn[i]);
      if(result) {
        return result;
      }
    }
    return null;
  }

  const handleClick = (id) => {
    const newTree = {...tree};
    const subTree = findTreeById(id, newTree);
    console.log(subTree);
    if(subTree.dependsOn.length === 0) {
      const newDep = findDependency(subTree.ref).dependsOn.map(dep => ({id: uuidv4(), ref: dep, dependsOn: []}));
      subTree.dependsOn.push(...newDep);
    } else {
      subTree.dependsOn.splice(0, subTree.dependsOn.length);
    }
    setTree(newTree);
  }

  function CustomNode({data, id}) {
    return (
        <div onClick={() => {console.log(id); handleClick(id);}}>
          <Handle type="target" position={Position.Left} /> 
          <div style={{
            border: '1px solid ' + colorBorder,
            borderRadius: '4px',
            padding: '0px 4px'
          }} >
            {data.label}
          </div>
          <Handle type="source" position={Position.Right} id="a" />
        </div>
    );
  }

  const nodeTypes = useMemo(() => ({ customNode: CustomNode }), []);
  return (
    <div style={{
      height: '100vh',
    }}>
      <ReactFlow style={{
        background: colorBgContainer,
        width: '100%',
        height: '100%',
      }} nodes={renderTree(tree, 0, countLeafNodes(tree)*h/2).nodes} edges={renderTree(tree, 0, countLeafNodes(tree)*h/2).edges} nodeTypes={nodeTypes}>
        <Controls />
      </ReactFlow>
    </div>
  )
}

export default Board2