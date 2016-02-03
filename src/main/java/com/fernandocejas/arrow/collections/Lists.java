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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.RandomAccess;

import static com.fernandocejas.arrow.checks.Preconditions.checkArgument;
import static com.fernandocejas.arrow.checks.Preconditions.checkNotNull;

/**
 * Static utility methods pertaining to {@link List} instances.
 *
 * <p>See the Guava User Guide article on <a href=
 * "http://code.google.com/p/guava-libraries/wiki/CollectionUtilitiesExplained#Lists">
 * {@code Lists}</a>.
 *
 * @author Kevin Bourrillion
 * @author Mike Bostock
 * @author Louis Wasserman
 * @since 2.0 (imported from Google Collections Library)
 *
 * <p><b>This class contains code derived from <a href="https://github.com/google/guava">Google Guava</a></b>
 */
public final class Lists {
  /**
   * Creates a <i>mutable</i> {@code ArrayList} instance containing the given
   * elements.
   *
   * <p><b>Note:</b> essentially the only reason to use this method is when you
   * will need to add or remove elements later. If any elements
   * might be null, or you need support for {@link List#set(int, Object)}, use
   * {@link java.util.Arrays#asList}.
   *
   * <p>Note that even when you do need the ability to add or remove, this method
   * provides only a tiny bit of syntactic sugar for {@code newArrayList(}{@link
   * java.util.Arrays#asList asList}{@code (...))}, or for creating an empty list then
   * calling {@link Collections#addAll}. This method is not actually very useful
   * and will likely be deprecated in the future.
   */
  @SuppressWarnings({"unchecked", "PMD.LooseCoupling"})
  public static <E> ArrayList<E> newArrayList(E... elements) {
    checkNotNull(elements);
    ArrayList<E> list = new ArrayList<>(elements.length);
    Collections.addAll(list, elements);
    return list;
  }

  /**
   * Creates a <i>mutable</i> {@code ArrayList} instance containing the given
   * elements; a very thin shortcut for creating an empty list then calling
   * {@link Iterables#addAll}.
   *
   * <p><b>Note for Java 7 and later:</b> if {@code elements} is a {@link
   * Collection}, you don't need this method. Use the {@code ArrayList}
   * {@linkplain ArrayList#ArrayList(Collection) constructor} directly, taking
   * advantage of the new <a href="http://goo.gl/iz2Wi">"diamond" syntax</a>.
   */
  @SuppressWarnings("PMD.LooseCoupling") // we want the concrete type
  public static <E> ArrayList<E> newArrayList(Iterable<? extends E> elements) {
    checkNotNull(elements); // for GWT
    // Let ArrayList's sizing logic work, if possible
    return elements instanceof Collection
        ? new ArrayList<>(MoreCollections.cast(elements))
        : newArrayList(elements.iterator());
  }

  /**
   * Creates a <i>mutable</i> {@code ArrayList} instance containing the given
   * elements; a very thin shortcut for creating an empty list and then calling
   * {@link Iterators#addAll}.
   */
  @SuppressWarnings("PMD.LooseCoupling") // we want the concrete type
  public static <E> ArrayList<E> newArrayList(Iterator<? extends E> elements) {
    final ArrayList<E> list = new ArrayList<>();
    Iterators.addAll(list, elements);
    return list;
  }

  /**
   * Creates a <i>mutable</i> {@code ArrayList} instance containing the given
   * primitive elements.
   */
  @SuppressWarnings("PMD.LooseCoupling") // we want the concrete type
  public static ArrayList<Integer> newArrayList(int[] elements) {
    final ArrayList<Integer> list = new ArrayList<>(elements.length);
    for (int el : elements) {
      list.add(el);
    }
    return list;
  }

  /**
   * Creates a <i>mutable</i> {@code LinkedList} instance containing the given
   * elements; a very thin shortcut for creating an empty list then calling
   * {@link Iterables#addAll}.
   *
   * <p><b>Performance note:</b> {@link ArrayList} and {@link
   * java.util.ArrayDeque} consistently outperform {@code LinkedList} except in
   * certain rare and specific situations. Unless you have spent a lot of time
   * benchmarking your specific needs, use one of those instead.
   *
   * <p><b>Note for Java 7 and later:</b> if {@code elements} is a {@link
   * Collection}, you don't need this method. Use the {@code LinkedList}
   * {@linkplain LinkedList#LinkedList(Collection) constructor} directly, taking
   * advantage of the new <a href="http://goo.gl/iz2Wi">"diamond" syntax</a>.
   */
  @SuppressWarnings("PMD.LooseCoupling") // we want the concrete type
  public static <E> LinkedList<E> newLinkedList(Iterable<? extends E> elements) {
    LinkedList<E> list = new LinkedList<>();
    Iterables.addAll(list, elements);
    return list;
  }

  /**
   * Used to avoid http://bugs.sun.com/view_bug.do?bug_id=6558557
   */
  static <T> List<T> cast(Iterable<T> iterable) {
    return (List<T>) iterable;
  }

  /**
   * Returns consecutive {@linkplain List#subList(int, int) sublists} of a list,
   * each of the same size (the final list may be smaller). For example,
   * partitioning a list containing {@code [a, b, c, d, e]} with a partition
   * size of 3 yields {@code [[a, b, c], [d, e]]} -- an outer list containing
   * two inner lists of three and two elements, all in the original order.
   *
   * <p>The outer list is unmodifiable, but reflects the latest state of the
   * source list. The inner lists are sublist views of the original list,
   * produced on demand using {@link List#subList(int, int)}, and are subject
   * to all the usual caveats about modification as explained in that API.
   *
   * @param list the list to return consecutive sublists of
   * @param size the desired size of each sublist (the last may be
   *             smaller)
   * @return a list of consecutive sublists
   * @throws IllegalArgumentException if {@code partitionSize} is nonpositive
   */
  public static <T> List<List<T>> partition(List<T> list, int size) {
    checkNotNull(list);
    checkArgument(size > 0);
    return list instanceof RandomAccess
        ? new RandomAccessPartition<>(list, size)
        : new Partition<>(list, size);
  }

  /**
   * Returns a reversed view of the specified list. For example, {@code
   * Lists.reverse(Arrays.asList(1, 2, 3))} returns a list containing {@code 3,
   * 2, 1}. The returned list is backed by this list, so changes in the returned
   * list are reflected in this list, and vice-versa. The returned list supports
   * all of the optional list operations supported by this list.
   * <p/>
   * <p>The returned list is random-access if the specified list is random
   * access.
   *
   * @since 7.0
   */
  public static <T> List<T> reverse(List<T> list) {
    if (list instanceof ReverseList) {
      return ((ReverseList<T>) list).getForwardList();
    } else if (list instanceof RandomAccess) {
      return new RandomAccessReverseList<>(list);
    } else {
      return new ReverseList<>(list);
    }
  }

  private Lists() {
    // no instances
  }
}
