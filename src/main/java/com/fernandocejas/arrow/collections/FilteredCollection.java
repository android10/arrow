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
import com.fernandocejas.arrow.functions.Predicates;
import java.util.AbstractCollection;
import java.util.Collection;
import java.util.Iterator;
import org.jetbrains.annotations.Nullable;

import static com.fernandocejas.arrow.checks.Preconditions.checkArgument;

class FilteredCollection<E> extends AbstractCollection<E> {
  final Collection<E> unfiltered;
  final Predicate<? super E> predicate;

  FilteredCollection(Collection<E> unfiltered,
      Predicate<? super E> predicate) {
    this.unfiltered = unfiltered;
    this.predicate = predicate;
  }

  FilteredCollection<E> createCombined(Predicate<? super E> newPredicate) {
    return new FilteredCollection<>(unfiltered,
        Predicates.and(predicate, newPredicate));
    // .<E> above needed to compile in JDK 5
  }

  @Override
  public boolean add(E element) {
    checkArgument(predicate.apply(element));
    return unfiltered.add(element);
  }

  @Override
  public boolean addAll(Collection<? extends E> collection) {
    for (E element : collection) {
      checkArgument(predicate.apply(element));
    }
    return unfiltered.addAll(collection);
  }

  @Override
  public void clear() {
    Iterables.removeIf(unfiltered, predicate);
  }

  @Override
  public boolean contains(@Nullable Object element) {
    if (MoreCollections.safeContains(unfiltered, element)) {
      @SuppressWarnings("unchecked") // element is in unfiltered, so it must be an E
          E e = (E) element;
      return predicate.apply(e);
    }
    return false;
  }

  @Override
  public boolean containsAll(Collection<?> collection) {
    return MoreCollections.containsAllImpl(this, collection);
  }

  @Override
  public boolean isEmpty() {
    return !Iterables.any(unfiltered, predicate);
  }

  @Override
  public Iterator<E> iterator() {
    return Iterators.filter(unfiltered.iterator(), predicate);
  }

  @Override
  public boolean remove(Object element) {
    return contains(element) && unfiltered.remove(element);
  }

  @Override
  public boolean removeAll(final Collection<?> collection) {
    return Iterables.removeIf(unfiltered, Predicates.and(predicate, Predicates.in(collection)));
  }

  @Override
  public boolean retainAll(final Collection<?> collection) {
    return Iterables.removeIf(unfiltered,
        Predicates.and(predicate, Predicates.not(Predicates.in(collection))));
  }

  @Override
  public int size() {
    return Iterators.size(iterator());
  }

  @Override
  public Object[] toArray() {
    // creating an ArrayList so filtering happens once
    return Lists.newArrayList(iterator()).toArray();
  }

  @Override
  public <T> T[] toArray(T[] array) {
    return Lists.newArrayList(iterator()).toArray(array);
  }
}
