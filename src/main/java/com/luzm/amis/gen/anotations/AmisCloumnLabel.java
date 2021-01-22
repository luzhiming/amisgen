package com.luzm.amis.gen.anotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface AmisCloumnLabel {
    String label();
}
