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

import com.fernandocejas.arrow.functions.Predicate;
import com.fernandocejas.arrow.strings.Joiner;
import com.fernandocejas.arrow.strings.Strings;
import java.util.Collection;
import org.jetbrains.annotations.Nullable;

import static com.fernandocejas.arrow.checks.Preconditions.checkNotNull;
import static com.fernandocejas.arrow.functions.Predicates.in;

/**
 * Provides static methods for working with {@code Collection} instances.
 *
 * @author Chris Povirk
 * @author Mike Bostock
 * @author Jared Levy
 * @since 2.0 (imported from Google Collections Library)
 */
public final class MoreCollections {

  static final Joiner STANDARD_JOINER = Strings.joinOn(", ").useForNull("null");

  /**
   * Delegates to {@link Collection#contains}. Returns {@code false} if the
   * {@code contains} method throws a {@code ClassCastException} or
   * {@code NullPointerException}.
   */
  @SuppressWarnings("PMD.AvoidCatchingNPE")
  static boolean safeContains(Collection<?> collection, @Nullable Object object) {
    checkNotNull(collection);
    try {
      return collection.contains(object);
    } catch (ClassCastException e) {
      return false;
    } catch (NullPointerException e) {
      return false;
    }
  }

  /**
   * Returns the elements of {@code unfiltered} that satisfy a predicate. The
   * returned collection is a live view of {@code unfiltered}; changes to one
   * affect the other.
   *
   * <p>The resulting collection's iterator does not support {@code remove()},
   * but all other collection methods are supported. When given an element that
   * doesn't satisfy the predicate, the collection's {@code add()} and {@code
   * addAll()} methods throw an {@link IllegalArgumentException}. When methods
   * such as {@code removeAll()} and {@code clear()} are called on the filtered
   * collection, only elements that satisfy the filter will be removed from the
   * underlying collection.
   *
   * <p>The returned collection isn't threadsafe or serializable, even if
   * {@code unfiltered} is.
   *
   * <p>Many of the filtered collection's methods, such as {@code size()},
   * iterate across every element in the underlying collection and determine
   * which elements satisfy the filter. When a live view is <i>not</i> needed,
   * it may be faster to copy {@code Iterables.filter(unfiltered, predicate)}
   * and use the copy.
   *
   * <p><b>Warning:</b> {@code predicate} must be <i>consistent with equals</i>,
   * as documented at {@link Predicate#apply}. Do not provide a predicate such
   * as {@code Predicates.instanceOf(ArrayList.class)}, which is inconsistent
   * with equals.
   */
  // TODO(kevinb): how can we omit that Iterables link when building gwt
  // javadoc?
  public static <E> Collection<E> filter(
      Collection<E> unfiltered, Predicate<? super E> predicate) {
    if (unfiltered instanceof FilteredCollection) {
      // Support clear(), removeAll(), and retainAll() when filtering a filtered
      // collection.
      return ((FilteredCollection<E>) unfiltered).createCombined(predicate);
    }

    return new FilteredCollection<>(checkNotNull(unfiltered), checkNotNull(predicate));
  }

  /**
   * Returns {@code true} if the collection {@code self} contains all of the
   * elements in the collection {@code c}.
   *
   * <p>This method iterates over the specified collection {@code c}, checking
   * each element returned by the iterator in turn to see if it is contained in
   * the specified collection {@code self}. If all elements are so contained,
   * {@code true} is returned, otherwise {@code false}.
   *
   * @param self a collection which might contain all elements in {@code c}
   * @param c    a collection whose elements might be contained by {@code self}
   */
  static boolean containsAllImpl(Collection<?> self, Collection<?> c) {
    return Iterables.all(c, in(self));
  }

  /**
   * Used to avoid http://bugs.sun.com/view_bug.do?bug_id=6558557
   */
  static <T> Collection<T> cast(Iterable<T> iterable) {
    return (Collection<T>) iterable;
  }

  private MoreCollections() {
    // no instances
  }
}
