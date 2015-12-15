package com.fernandocejas.arrow.size;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class SizeUnitTest {

  @Test
  public void shouldConvertKilobytesToBytes() {
    long resultOne = SizeUnit.KILOBYTES.toBytes(2);
    assertThat(resultOne).isEqualTo(2048);

    long resultTwo = SizeUnit.KILOBYTES.toBytes(4);
    assertThat(resultTwo).isEqualTo(4096);

    long resultThree = SizeUnit.KILOBYTES.toBytes(8);
    assertThat(resultThree).isEqualTo(8192);
  }
}