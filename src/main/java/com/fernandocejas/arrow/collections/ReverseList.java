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

import java.util.AbstractList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import org.jetbrains.annotations.Nullable;

import static com.fernandocejas.arrow.checks.Preconditions.checkElementIndex;
import static com.fernandocejas.arrow.checks.Preconditions.checkNotNull;
import static com.fernandocejas.arrow.checks.Preconditions.checkPositionIndex;
import static com.fernandocejas.arrow.checks.Preconditions.checkPositionIndexes;
import static com.fernandocejas.arrow.checks.Preconditions.checkState;

class ReverseList<T> extends AbstractList<T> {
  private final List<T> forwardList;

  ReverseList(List<T> forwardList) {
    this.forwardList = checkNotNull(forwardList);
  }

  List<T> getForwardList() {
    return forwardList;
  }

  private int reverseIndex(int index) {
    int size = size();
    checkElementIndex(index, size);
    return size - 1 - index;
  }

  private int reversePosition(int index) {
    int size = size();
    checkPositionIndex(index, size);
    return size - index;
  }

  @Override
  public void add(int index, @Nullable T element) {
    forwardList.add(reversePosition(index), element);
  }

  @Override
  public void clear() {
    forwardList.clear();
  }

  @Override
  public T remove(int index) {
    return forwardList.remove(reverseIndex(index));
  }

  @Override
  protected void removeRange(int fromIndex, int toIndex) {
    subList(fromIndex, toIndex).clear();
  }

  @Override
  public T set(int index, @Nullable T element) {
    return forwardList.set(reverseIndex(index), element);
  }

  @Override
  public T get(int index) {
    return forwardList.get(reverseIndex(index));
  }

  @Override
  public int size() {
    return forwardList.size();
  }

  @Override
  public List<T> subList(int fromIndex, int toIndex) {
    checkPositionIndexes(fromIndex, toIndex, size());
    return Lists.reverse(forwardList.subList(
        reversePosition(toIndex), reversePosition(fromIndex)));
  }

  @Override
  public Iterator<T> iterator() {
    return listIterator();
  }

  @Override
  public ListIterator<T> listIterator(int index) {
    int start = reversePosition(index);
    final ListIterator<T> forwardIterator = forwardList.listIterator(start);
    return new ReverseListIterator<>(forwardIterator);
  }

  private class ReverseListIterator<T> implements ListIterator<T> {

    private final ListIterator<T> forwardIterator;
    boolean canRemoveOrSet;

    public ReverseListIterator(ListIterator<T> forwardIterator) {
      this.forwardIterator = forwardIterator;
    }

    @Override
    public void add(T e) {
      forwardIterator.add(e);
      forwardIterator.previous();
      canRemoveOrSet = false;
    }

    @Override
    public boolean hasNext() {
      return forwardIterator.hasPrevious();
    }

    @Override
    public boolean hasPrevious() {
      return forwardIterator.hasNext();
    }

    @Override
    public T next() {
      if (!hasNext()) {
        throw new NoSuchElementException();
      }
      canRemoveOrSet = true;
      return forwardIterator.previous();
    }

    @Override
    public int nextIndex() {
      return reversePosition(forwardIterator.nextIndex());
    }

    @Override
    public T previous() {
      if (!hasPrevious()) {
        throw new NoSuchElementException();
      }
      canRemoveOrSet = true;
      return forwardIterator.next();
    }

    @Override
    public int previousIndex() {
      return nextIndex() - 1;
    }

    @Override
    public void remove() {
      forwardIterator.remove();
      canRemoveOrSet = false;
    }

    @Override
    public void set(T e) {
      checkState(canRemoveOrSet);
      forwardIterator.set(e);
    }
  }
}
