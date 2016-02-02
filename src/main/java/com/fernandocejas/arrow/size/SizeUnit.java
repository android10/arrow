/**
 * Copyright (C) 2016 Fernando Cejas Open Source Project
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
package com.fernandocejas.arrow.size;

import com.fernandocejas.arrow.annotations.WorkInProgress;

@WorkInProgress
enum SizeUnit {

  BYTES {
    public double toBytes(double d) { return 0; }
    public double toKilobytes(long d) { return 0; }
    public double toMegabytes(long d) { return 0; }
    public double toGigabytes(long d) { return 0; }
    public double toTerabytes(long d) { return 0; }
  },

  KILOBYTES {
    public double toBytes(double d) { return 0; }
    public double toKilobytes(long d) { return 0; }
    public double toMegabytes(long d) { return 0; }
    public double toGigabytes(long d) { return 0; }
    public double toTerabytes(long d) { return 0; }
  },

  MEGABYTES {
    public double toBytes(double d) { return 0; }
    public double toKilobytes(long d) { return 0; }
    public double toMegabytes(long d) { return 0; }
    public double toGigabytes(long d) { return 0; }
    public double toTerabytes(long d) { return 0; }
  },

  GIGABYTES {
    public double toBytes(double d) { return 0; }
    public double toKilobytes(long d) { return 0; }
    public double toMegabytes(long d) { return 0; }
    public double toGigabytes(long d) { return 0; }
    public double toTerabytes(long d) { return 0; }
  },

  TERABYTES {
    public double toBytes(double d) { return 0; }
    public double toKilobytes(long d) { return 0; }
    public double toMegabytes(long d) { return 0; }
    public double toGigabytes(long d) { return 0; }
    public double toTerabytes(long d) { return 0; }
  };

  public double toBytes(double size) {
    throw new AbstractMethodError();
  }

  public double toKilobytes(long size) {
    throw new AbstractMethodError();
  }

  public double toMegabytes(long size) {
    throw new AbstractMethodError();
  }

  public double toGigabytes(long size) {
    throw new AbstractMethodError();
  }

  public double toTerabytes(long size) {
    throw new AbstractMethodError();
  }
}
