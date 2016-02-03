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
import java.util.List;

import static com.fernandocejas.arrow.checks.Preconditions.checkElementIndex;

class Partition<T> extends AbstractList<List<T>> {
  final List<T> list;
  final int size;

  Partition(List<T> list, int size) {
    this.list = list;
    this.size = size;
  }

  @Override
  public List<T> get(int index) {
    checkElementIndex(index, size());
    int start = index * size;
    int end = Math.min(start + size, list.size());
    return list.subList(start, end);
  }

  @Override
  public int size() {
    int div = list.size() / size;
    int rem = list.size() % size;
    if (rem == 0) {
      return div;
    } else {
      return div + 1;
    }
  }

  @Override
  public boolean isEmpty() {
    return list.isEmpty();
  }
}
