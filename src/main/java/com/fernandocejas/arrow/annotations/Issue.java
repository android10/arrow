package com.fernandocejas.arrow.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Denotes that the class, method or field is linked to an issue.
 * A ref value can be added in order to specify more information.
 */
@Retention(RetentionPolicy.SOURCE)
public @interface Issue {
  String ref();
}
