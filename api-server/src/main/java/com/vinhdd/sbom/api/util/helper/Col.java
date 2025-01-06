package com.vinhdd.sbom.api.util.helper;

import java.lang.annotation.*;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Col {
    String value();
    boolean jsonString() default false;
    boolean isTimestamp() default false;
}
