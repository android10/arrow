package com.fernandocejas.arrow.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Denotes that the class, method or field is declared as Internal functionality.
 * This can be used in scenarios in which the functionality is replaced with a new one, but it hasn't been deprecated.
 */
@Retention(RetentionPolicy.SOURCE)
public @interface Internal {
}
