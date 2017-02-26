package jpl.ch16.ex05;

import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@Inherited
@interface ClassInfo {
    String created();
    String createdBy();
    String lastModified();
    String lastModifiedBy();
    int revision();
}
