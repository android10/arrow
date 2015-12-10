package com.fernandocejas.arrow.size;

public enum SizeUnit {

  BYTES {
    public long toBytes(long d) { return 0; }
    public long toKilobytes(long d) { return 0; }
    public long toMegabytes(long d) { return 0; }
    public long toGigabytes(long d) { return 0; }
    public long toTerabytes(long d) { return 0; }
  },

  KILOBYTES {
    public long toBytes(long d) { return d * Math.round(Math.pow(2, 10)); }
    public long toKilobytes(long d) { return d; }
    public long toMegabytes(long d) { return d / 1024; }
    public long toGigabytes(long d) { return 0; }
    public long toTerabytes(long d) { return 0; }
  },

  MEGABYTES {
    public long toBytes(long d) { return 0; }
    public long toKilobytes(long d) { return 0; }
    public long toMegabytes(long d) { return 0; }
    public long toGigabytes(long d) { return 0; }
    public long toTerabytes(long d) { return 0; }
  },

  GIGABYTES {
    public long toBytes(long d) { return 0; }
    public long toKilobytes(long d) { return 0; }
    public long toMegabytes(long d) { return 0; }
    public long toGigabytes(long d) { return 0; }
    public long toTerabytes(long d) { return 0; }
  },

  TERABYTES {
    public long toBytes(long d) { return 0; }
    public long toKilobytes(long d) { return 0; }
    public long toMegabytes(long d) { return 0; }
    public long toGigabytes(long d) { return 0; }
    public long toTerabytes(long d) { return 0; }
  };

  public long toBytes(long size) {
    throw new AbstractMethodError();
  }

  public long toKilobytes(long size) {
    throw new AbstractMethodError();
  }

  public long toMegabytes(long size) {
    throw new AbstractMethodError();
  }

  public long toGigabytes(long size) {
    throw new AbstractMethodError();
  }

  public long toTerabytes(long size) {
    throw new AbstractMethodError();
  }
}
