/**
 * Copyright (C) 2007 The Guava Authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.fernandocejas.arrow.collections;

import java.lang.reflect.Array;
import java.util.Arrays;
import org.jetbrains.annotations.Nullable;

/**
 * Static utility methods pertaining to object arrays.
 *
 * @author Kevin Bourrillion
 * @since 2.0 (imported from Google Collections Library)
 */
public final class MoreArrays {

  /**
   * Returns a new array of the given length with the specified component type.
   *
   * @param type the component type
   * @param length the length of the new array
   */
  @SuppressWarnings("unchecked")
  public static <T> T[] newArray(Class<T> type, int length) {
    return (T[]) Array.newInstance(type, length);
  }

  /**
   * Returns a new array of the given length with the same type as a reference
   * array.
   *
   * @param reference any array of the desired type
   * @param length the length of the new array
   */
  public static <T> T[] newArray(T[] reference, int length) {
    Class<?> type = reference.getClass().getComponentType();
    // the cast is safe because
    // result.getClass() == reference.getClass().getComponentType()
    return (T[]) Array.newInstance(type, length);
  }

  /**
   * Returns a new array that contains the concatenated contents of two arrays.
   *
   * @param first the first array of elements to concatenate
   * @param second the second array of elements to concatenate
   * @param type the component type of the returned array
   */
  public static <T> T[] concat(T[] first, T[] second, Class<T> type) {
    T[] result = newArray(type, first.length + second.length);
    System.arraycopy(first, 0, result, 0, first.length);
    System.arraycopy(second, 0, result, first.length, second.length);
    return result;
  }

  /**
   * Returns a new array that prepends {@code element} to {@code array}.
   *
   * @param element the element to prepend to the front of {@code array}
   * @param array the array of elements to append
   * @return an array whose size is one larger than {@code array}, with
   * {@code element} occupying the first position, and the
   * elements of {@code array} occupying the remaining elements.
   */
  public static <T> T[] concat(@Nullable T element, T[] array) {
    T[] result = newArray(array, array.length + 1);
    result[0] = element;
    System.arraycopy(array, 0, result, 1, array.length);
    return result;
  }

  /**
   * Returns a new array that appends {@code element} to {@code array}.
   *
   * @param array the array of elements to prepend
   * @param element the element to append to the end
   * @return an array whose size is one larger than {@code array}, with
   * the same contents as {@code array}, plus {@code element} occupying the
   * last position.
   */
  public static <T> T[] concat(T[] array, @Nullable T element) {
    T[] result = Arrays.copyOf(array, array.length + 1);
    result[array.length] = element;
    return result;
  }

  @Nullable
  public static <T> T firstNonNull(T... nullableItems) {
    for (T item : nullableItems) {
      if (item != null) {
        return item;
      }
    }
    return null;
  }

  private MoreArrays() {
    // no instances
  }
}
