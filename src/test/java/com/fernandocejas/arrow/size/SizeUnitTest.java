package com.fernandocejas.arrow.size;

import com.fernandocejas.arrow.annotations.See;
import com.fernandocejas.arrow.annotations.WorkInProgress;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@WorkInProgress
@See(ref = "https://www.google.de/search?q=convert+kilobytes+to+mb")
@Ignore
@RunWith(MockitoJUnitRunner.class)
public class SizeUnitTest {

  @Test
  public void shouldConvertKilobytesToBytes() {
    double resultOne = SizeUnit.KILOBYTES.toBytes(123543);
    assertThat(resultOne).isEqualTo(123543000);

    double resultTwo = SizeUnit.KILOBYTES.toBytes(888);
    assertThat(resultTwo).isEqualTo(888000);

    double resultThree = SizeUnit.KILOBYTES.toBytes(987611);
    assertThat(resultThree).isEqualTo(987611000);

    double resultFour = SizeUnit.KILOBYTES.toBytes(1432);
    assertThat(resultFour).isEqualTo(1432000);
  }

  @Test
  public void shouldConvertKilobytesToKilobytes() {
    double resultOne = SizeUnit.KILOBYTES.toKilobytes(9);
    assertThat(resultOne).isEqualTo(9);

    double resultTwo = SizeUnit.KILOBYTES.toKilobytes(495855);
    assertThat(resultTwo).isEqualTo(495855);
  }

  @Test
  public void shouldConvertKilobytesToMegabytes() {
    double resultOne = SizeUnit.KILOBYTES.toMegabytes(111);
    assertThat(resultOne).isEqualTo(0.111);

    double resultTwo = SizeUnit.KILOBYTES.toMegabytes(2324234);
    assertThat(resultTwo).isEqualTo(2324.234);

    double resultThree = SizeUnit.KILOBYTES.toMegabytes(320492384);
    assertThat(resultThree).isEqualTo(320492.384);

    double resultFour = SizeUnit.KILOBYTES.toMegabytes(999999999);
    assertThat(resultFour).isEqualTo(999999.999);
  }

  @Test
  public void shouldConvertKilobytesToGigabytes() {
    double resultOne = SizeUnit.KILOBYTES.toGigabytes(2342342);
    assertThat(resultOne).isEqualTo(2.342342);

    double resultTwo = SizeUnit.KILOBYTES.toGigabytes(34995);
    assertThat(resultTwo).isEqualTo(0.034995);

    double resultThree = SizeUnit.KILOBYTES.toGigabytes(4534);
    assertThat(resultThree).isEqualTo(0.004534);

    double resultFour = SizeUnit.KILOBYTES.toGigabytes(1000);
    assertThat(resultFour).isEqualTo(0.001);
  }

  @Test
  public void shouldConvertKilobytesToTerabytes() {
    double resultOne = SizeUnit.KILOBYTES.toTerabytes(233234);
    assertThat(resultOne).isEqualTo(0.000233234);

    double resultTwo = SizeUnit.KILOBYTES.toTerabytes(888888);
    assertThat(resultTwo).isEqualTo(0.000888888);

    double resultThree = SizeUnit.KILOBYTES.toTerabytes(89795867);
    assertThat(resultThree).isEqualTo(0.089795867);

    double resultFour = SizeUnit.KILOBYTES.toTerabytes(32402234);
    assertThat(resultFour).isEqualTo(0.032402234);
  }
}