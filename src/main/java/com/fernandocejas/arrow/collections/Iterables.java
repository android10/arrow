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

import com.fernandocejas.arrow.checks.Preconditions;
import com.fernandocejas.arrow.functions.Predicate;
import com.fernandocejas.arrow.optional.Optional;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.RandomAccess;
import org.jetbrains.annotations.Nullable;

import static com.fernandocejas.arrow.checks.Preconditions.checkNotNull;
import static com.fernandocejas.arrow.collections.CollectPreconditions.checkIndexNonnegative;

/**
 * This class contains static utility methods that operate on or return objects
 * of type {@code Iterable}. Except as noted, each method has a corresponding
 * {@link Iterator}-based method in the {@link Iterators} class.
 *
 * <p><i>Performance notes:</i> Unless otherwise noted, all of the iterables
 * produced in this class are <i>lazy</i>, which means that their iterators
 * only advance the backing iteration when absolutely necessary.
 *
 * <p>See the Guava User Guide article on <a href=
 * "http://code.google.com/p/guava-libraries/wiki/CollectionUtilitiesExplained#Iterables">
 * {@code Iterables}</a>.
 *
 * @author Kevin Bourrillion
 * @author Jared Levy
 * @since 2.0 (imported from Google Collections Library)
 */
public final class Iterables {
  /**
   * Returns the number of elements in {@code iterable}.
   */
  public static int size(Iterable<?> iterable) {
    return iterable instanceof Collection
        ? ((Collection<?>) iterable).size()
        : Iterators.size(iterable.iterator());
  }

  /**
   * Returns {@code true} if {@code iterable} contains any object for which {@code equals(element)}
   * is true.
   */
  public static boolean contains(Iterable<?> iterable, @Nullable Object element) {
    if (iterable instanceof Collection) {
      Collection<?> collection = (Collection<?>) iterable;
      return MoreCollections.safeContains(collection, element);
    }
    return Iterators.contains(iterable.iterator(), element);
  }

  /**
   * Divides an iterable into unmodifiable sublists of the given size (the final
   * iterable may be smaller). For example, partitioning an iterable containing
   * {@code [a, b, c, d, e]} with a partition size of 3 yields {@code
   * [[a, b, c], [d, e]]} -- an outer iterable containing two inner lists of
   * three and two elements, all in the original order.
   *
   * <p>Iterators returned by the returned iterable do not support the {@link
   * Iterator#remove()} method. The returned lists implement {@link
   * RandomAccess}, whether or not the input list does.
   *
   * <p><b>Note:</b> if {@code iterable} is a {@link List}, use {@link
   * Lists#partition(List, int)} instead.
   *
   * @param iterable the iterable to return a partitioned view of
   * @param size the desired size of each partition (the last may be smaller)
   * @return an iterable of unmodifiable lists containing the elements of {@code
   * iterable} divided into partitions
   * @throws IllegalArgumentException if {@code size} is nonpositive
   */
  public static <T> Iterable<List<T>> partition(
      final Iterable<T> iterable, final int size) {
    checkNotNull(iterable);
    Preconditions.checkArgument(size > 0);
    return new Iterable<List<T>>() {
      @Override
      public Iterator<List<T>> iterator() {
        return Iterators.partition(iterable.iterator(), size);
      }
    };
  }

  /**
   * Divides an iterable into unmodifiable sublists of the given size, padding
   * the final iterable with null values if necessary. For example, partitioning
   * an iterable containing {@code [a, b, c, d, e]} with a partition size of 3
   * yields {@code [[a, b, c], [d, e, null]]} -- an outer iterable containing
   * two inner lists of three elements each, all in the original order.
   *
   * <p>Iterators returned by the returned iterable do not support the {@link
   * Iterator#remove()} method.
   *
   * @param iterable the iterable to return a partitioned view of
   * @param size the desired size of each partition
   * @return an iterable of unmodifiable lists containing the elements of {@code
   * iterable} divided into partitions (the final iterable may have
   * trailing null elements)
   * @throws IllegalArgumentException if {@code size} is nonpositive
   */
  public static <T> Iterable<List<T>> paddedPartition(
      final Iterable<T> iterable, final int size) {
    checkNotNull(iterable);
    Preconditions.checkArgument(size > 0);
    return new Iterable<List<T>>() {
      @Override
      public Iterator<List<T>> iterator() {
        return Iterators.paddedPartition(iterable.iterator(), size);
      }
    };
  }

  /**
   * Returns the elements of {@code unfiltered} that satisfy a predicate. The
   * resulting iterable's iterator does not support {@code remove()}.
   */
  public static <T> Iterable<T> filter(
      final Iterable<T> unfiltered, final Predicate<? super T> predicate) {
    checkNotNull(unfiltered);
    checkNotNull(predicate);
    return new Iterable<T>() {
      @Override
      public Iterator<T> iterator() {
        return Iterators.filter(unfiltered.iterator(), predicate);
      }
    };
  }

  /**
   * Returns all instances of class {@code type} in {@code unfiltered}. The
   * returned iterable has elements whose class is {@code type} or a subclass of
   * {@code type}. The returned iterable's iterator does not support
   * {@code remove()}.
   *
   * @param unfiltered an iterable containing objects of any type
   * @param type the type of elements desired
   * @return an unmodifiable iterable containing all elements of the original
   * iterable that were of the requested type
   */
  public static <T> Iterable<T> filter(
      final Iterable<?> unfiltered, final Class<T> type) {
    checkNotNull(unfiltered);
    checkNotNull(type);
    return new Iterable<T>() {
      @Override
      public Iterator<T> iterator() {
        return Iterators.filter(unfiltered.iterator(), type);
      }
    };
  }

  /**
   * Removes, from an iterable, every element that belongs to the provided
   * collection.
   *
   * <p>This method calls {@link Collection#removeAll} if {@code iterable} is a
   * collection, and {@link Iterators#removeAll} otherwise.
   *
   * @param removeFrom the iterable to (potentially) remove elements from
   * @param elementsToRemove the elements to remove
   * @return {@code true} if any element was removed from {@code iterable}
   */
  public static boolean removeAll(
      Iterable<?> removeFrom, Collection<?> elementsToRemove) {
    return removeFrom instanceof Collection
        ? ((Collection<?>) removeFrom).removeAll(checkNotNull(elementsToRemove))
        : Iterators.removeAll(removeFrom.iterator(), elementsToRemove);
  }

  /**
   * Removes, from an iterable, every element that does not belong to the
   * provided collection.
   *
   * <p>This method calls {@link Collection#retainAll} if {@code iterable} is a
   * collection, and {@link Iterators#retainAll} otherwise.
   *
   * @param removeFrom the iterable to (potentially) remove elements from
   * @param elementsToRetain the elements to retain
   * @return {@code true} if any element was removed from {@code iterable}
   */
  public static boolean retainAll(
      Iterable<?> removeFrom, Collection<?> elementsToRetain) {
    return removeFrom instanceof Collection
        ? ((Collection<?>) removeFrom).retainAll(checkNotNull(elementsToRetain))
        : Iterators.retainAll(removeFrom.iterator(), elementsToRetain);
  }

  /**
   * Removes, from an iterable, every element that satisfies the provided
   * predicate.
   *
   * @param removeFrom the iterable to (potentially) remove elements from
   * @param predicate a predicate that determines whether an element should
   * be removed
   * @return {@code true} if any elements were removed from the iterable
   * @throws UnsupportedOperationException if the iterable does not support
   * {@code remove()}.
   * @since 2.0
   */
  public static <T> boolean removeIf(
      Iterable<T> removeFrom, Predicate<? super T> predicate) {
    if (removeFrom instanceof RandomAccess && removeFrom instanceof List) {
      return removeIfFromRandomAccessList(
          (List<T>) removeFrom, checkNotNull(predicate));
    }
    return Iterators.removeIf(removeFrom.iterator(), predicate);
  }

  private static <T> boolean removeIfFromRandomAccessList(
      List<T> list, Predicate<? super T> predicate) {
    // Note: Not all random access lists support set() so we need to deal with
    // those that don't and attempt the slower remove() based solution.
    int from = 0;
    int to = 0;

    for (; from < list.size(); from++) {
      T element = list.get(from);
      if (!predicate.apply(element)) {
        if (from > to) {
          try {
            list.set(to, element);
          } catch (UnsupportedOperationException e) {
            slowRemoveIfForRemainingElements(list, predicate, to, from);
            return true;
          }
        }
        to++;
      }
    }

    // Clear the tail of any remaining items
    list.subList(to, list.size()).clear();
    return from != to;
  }

  private static <T> void slowRemoveIfForRemainingElements(List<T> list,
      Predicate<? super T> predicate, int to, int from) {
    // Here we know that:
    // * (to < from) and that both are valid indices.
    // * Everything with (index < to) should be kept.
    // * Everything with (to <= index < from) should be removed.
    // * The element with (index == from) should be kept.
    // * Everything with (index > from) has not been checked yet.

    // Check from the end of the list backwards (minimize expected cost of
    // moving elements when remove() is called). Stop before 'from' because
    // we already know that should be kept.
    for (int n = list.size() - 1; n > from; n--) {
      if (predicate.apply(list.get(n))) {
        list.remove(n);
      }
    }
    // And now remove everything in the range [to, from) (going backwards).
    for (int n = from - 1; n >= to; n--) {
      list.remove(n);
    }
  }

  /**
   * Removes and returns the first matching element, or returns {@code null} if there is none.
   */
  @Nullable
  static <T> T removeFirstMatching(Iterable<T> removeFrom, Predicate<? super T> predicate) {
    checkNotNull(predicate);
    Iterator<T> iterator = removeFrom.iterator();
    while (iterator.hasNext()) {
      T next = iterator.next();
      if (predicate.apply(next)) {
        iterator.remove();
        return next;
      }
    }
    return null;
  }

  /**
   * Determines whether two iterables contain equal elements in the same order.
   * More specifically, this method returns {@code true} if {@code iterable1}
   * and {@code iterable2} contain the same number of elements and every element
   * of {@code iterable1} is equal to the corresponding element of
   * {@code iterable2}.
   */
  public static boolean elementsEqual(
      Iterable<?> iterable1, Iterable<?> iterable2) {
    if (iterable1 instanceof Collection && iterable2 instanceof Collection) {
      Collection<?> collection1 = (Collection<?>) iterable1;
      Collection<?> collection2 = (Collection<?>) iterable2;
      if (collection1.size() != collection2.size()) {
        return false;
      }
    }
    return Iterators.elementsEqual(iterable1.iterator(), iterable2.iterator());
  }

  /**
   * Returns a string representation of {@code iterable}, with the format {@code
   * [e1, e2, ..., en]} (that is, identical to {@link java.util.Arrays
   * Arrays}{@code .toString(Iterables.toArray(iterable))}). Note that for
   * <i>most</i> implementations of {@link Collection}, {@code
   * collection.toString()} also gives the same result, but that behavior is not
   * generally guaranteed.
   */
  public static String toString(Iterable<?> iterable) {
    return Iterators.toString(iterable.iterator());
  }

  /**
   * Returns the single element contained in {@code iterable}.
   *
   * @throws NoSuchElementException if the iterable is empty
   * @throws IllegalArgumentException if the iterable contains multiple
   * elements
   */
  public static <T> T getOnlyElement(Iterable<T> iterable) {
    return Iterators.getOnlyElement(iterable.iterator());
  }

  /**
   * Returns the single element contained in {@code iterable}, or {@code
   * defaultValue} if the iterable is empty.
   *
   * @throws IllegalArgumentException if the iterator contains multiple
   * elements
   */
  @Nullable
  public static <T> T getOnlyElement(
      Iterable<? extends T> iterable, @Nullable T defaultValue) {
    return Iterators.getOnlyElement(iterable.iterator(), defaultValue);
  }

  /**
   * Converts an iterable into a collection. If the iterable is already a
   * collection, it is returned. Otherwise, an {@link java.util.ArrayList} is
   * created with the contents of the iterable in the same iteration order.
   */
  private static <E> Collection<E> toCollection(Iterable<E> iterable) {
    return iterable instanceof Collection
        ? (Collection<E>) iterable
        : Lists.newArrayList(iterable.iterator());
  }

  /**
   * Adds all elements in {@code iterable} to {@code collection}.
   *
   * @return {@code true} if {@code collection} was modified as a result of this
   * operation.
   */
  public static <T> boolean addAll(
      Collection<T> addTo, Iterable<? extends T> elementsToAdd) {
    if (elementsToAdd instanceof Collection) {
      Collection<? extends T> c = MoreCollections.cast(elementsToAdd);
      return addTo.addAll(c);
    }
    return Iterators.addAll(addTo, checkNotNull(elementsToAdd).iterator());
  }

  /**
   * Returns {@code true} if any element in {@code iterable} satisfies the predicate.
   */
  public static <T> boolean any(
      Iterable<T> iterable, Predicate<? super T> predicate) {
    return Iterators.any(iterable.iterator(), predicate);
  }

  /**
   * Returns {@code true} if every element in {@code iterable} satisfies the
   * predicate. If {@code iterable} is empty, {@code true} is returned.
   */
  public static <T> boolean all(
      Iterable<T> iterable, Predicate<? super T> predicate) {
    return Iterators.all(iterable.iterator(), predicate);
  }

  /**
   * Returns the first element in {@code iterable} that satisfies the given
   * predicate; use this method only when such an element is known to exist. If
   * it is possible that <i>no</i> element will match, use {@link #tryFind} or
   * {@link #find(Iterable, Predicate, Object)} instead.
   *
   * @throws NoSuchElementException if no element in {@code iterable} matches
   * the given predicate
   */
  public static <T> T find(Iterable<T> iterable,
      Predicate<? super T> predicate) {
    return Iterators.find(iterable.iterator(), predicate);
  }

  /**
   * Returns the first element in {@code iterable} that satisfies the given
   * predicate, or {@code defaultValue} if none found. Note that this can
   * usually be handled more naturally using {@code
   * tryFind(iterable, predicate).or(defaultValue)}.
   *
   * @since 7.0
   */
  @Nullable
  public static <T> T find(Iterable<? extends T> iterable,
      Predicate<? super T> predicate, @Nullable T defaultValue) {
    return Iterators.find(iterable.iterator(), predicate, defaultValue);
  }

  /**
   * Returns an {@link Optional} containing the first element in {@code
   * iterable} that satisfies the given predicate, if such an element exists.
   *
   * <p><b>Warning:</b> avoid using a {@code predicate} that matches {@code
   * null}. If {@code null} is matched in {@code iterable}, a
   * NullPointerException will be thrown.
   *
   * @since 11.0
   */
  public static <T> Optional<T> tryFind(Iterable<T> iterable,
      Predicate<? super T> predicate) {
    return Iterators.tryFind(iterable.iterator(), predicate);
  }

  /**
   * Returns the index in {@code iterable} of the first element that satisfies
   * the provided {@code predicate}, or {@code -1} if the Iterable has no such
   * elements.
   *
   * <p>More formally, returns the lowest index {@code i} such that
   * {@code predicate.apply(Iterables.get(iterable, i))} returns {@code true},
   * or {@code -1} if there is no such index.
   *
   * @since 2.0
   */
  public static <T> int indexOf(
      Iterable<T> iterable, Predicate<? super T> predicate) {
    return Iterators.indexOf(iterable.iterator(), predicate);
  }

  /**
   * Returns the element at the specified position in an iterable.
   *
   * @param position position of the element to return
   * @return the element at the specified position in {@code iterable}
   * @throws IndexOutOfBoundsException if {@code position} is negative or
   * greater than or equal to the size of {@code iterable}
   */
  public static <T> T get(Iterable<T> iterable, int position) {
    checkNotNull(iterable);
    return iterable instanceof List
        ? ((List<T>) iterable).get(position)
        : Iterators.get(iterable.iterator(), position);
  }

  /**
   * Returns the element at the specified position in an iterable or a default
   * value otherwise.
   *
   * @param position position of the element to return
   * @param defaultValue the default value to return if {@code position} is
   * greater than or equal to the size of the iterable
   * @return the element at the specified position in {@code iterable} or
   * {@code defaultValue} if {@code iterable} contains fewer than
   * {@code position + 1} elements.
   * @throws IndexOutOfBoundsException if {@code position} is negative
   * @since 4.0
   */
  @Nullable
  public static <T> T get(Iterable<? extends T> iterable, int position, @Nullable T defaultValue) {
    checkNotNull(iterable);
    checkIndexNonnegative(position);
    if (iterable instanceof List) {
      List<? extends T> list = Lists.cast(iterable);
      return position < list.size() ? list.get(position) : defaultValue;
    } else {
      Iterator<? extends T> iterator = iterable.iterator();
      Iterators.advance(iterator, position);
      return Iterators.getNext(iterator, defaultValue);
    }
  }

  /**
   * Returns the first element in {@code iterable} or {@code defaultValue} if
   * the iterable is empty.  The {@link Iterators} analog to this method is
   * {@link Iterators#getNext}.
   *
   * <p>If no default value is desired (and the caller instead wants a
   * {@link NoSuchElementException} to be thrown), it is recommended that
   * {@code iterable.iterator().next()} is used instead.
   *
   * @param defaultValue the default value to return if the iterable is empty
   * @return the first element of {@code iterable} or the default value
   * @since 7.0
   */
  @Nullable
  public static <T> T getFirst(Iterable<? extends T> iterable, @Nullable T defaultValue) {
    return Iterators.getNext(iterable.iterator(), defaultValue);
  }

  /**
   * Returns the last element of {@code iterable}.
   *
   * @return the last element of {@code iterable}
   * @throws NoSuchElementException if the iterable is empty
   */
  public static <T> T getLast(Iterable<T> iterable) {
    // TODO(kevinb): Support a concurrently modified collection?
    if (iterable instanceof List) {
      List<T> list = (List<T>) iterable;
      if (list.isEmpty()) {
        throw new NoSuchElementException();
      }
      return getLastInNonemptyList(list);
    }

    return Iterators.getLast(iterable.iterator());
  }

  /**
   * Returns the last element of {@code iterable} or {@code defaultValue} if
   * the iterable is empty.
   *
   * @param defaultValue the value to return if {@code iterable} is empty
   * @return the last element of {@code iterable} or the default value
   * @since 3.0
   */
  @Nullable
  public static <T> T getLast(Iterable<? extends T> iterable, @Nullable T defaultValue) {
    if (iterable instanceof Collection) {
      Collection<? extends T> c = MoreCollections.cast(iterable);
      if (c.isEmpty()) {
        return defaultValue;
      } else if (iterable instanceof List) {
        return getLastInNonemptyList(Lists.cast(iterable));
      }
    }

    return Iterators.getLast(iterable.iterator(), defaultValue);
  }

  private static <T> T getLastInNonemptyList(List<T> list) {
    return list.get(list.size() - 1);
  }

  // Methods only in Iterables, not in Iterators

  /**
   * Determines if the given iterable contains no elements.
   *
   * <p>There is no precise {@link Iterator} equivalent to this method, since
   * one can only ask an iterator whether it has any elements <i>remaining</i>
   * (which one does using {@link Iterator#hasNext}).
   *
   * @return {@code true} if the iterable contains no elements
   */
  public static boolean isEmpty(Iterable<?> iterable) {
    if (iterable instanceof Collection) {
      return ((Collection<?>) iterable).isEmpty();
    }
    return !iterable.iterator().hasNext();
  }

  private Iterables() {
    // no instances
  }
}

