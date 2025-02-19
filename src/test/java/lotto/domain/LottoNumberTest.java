package lotto.domain;

import lotto.exception.LottoNumberRangeException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class LottoNumberTest {

    @ParameterizedTest
    @ValueSource(ints = {
            1, 45
    })
    @DisplayName("로또 번호 한개 생성 - 성공")
    public void lottoNumberTest_ok(int number) {
        assertThat(new LottoNumber(number))
                .isEqualTo(new LottoNumber(number));
    }

    @ParameterizedTest
    @ValueSource(ints = {
            0, -1, 46
    })
    @DisplayName("로또 번호는 1부터 45까지의 숫자여야한다.")
    public void lottoNumberTest_fail(int number) {
        assertThatThrownBy(() -> new LottoNumber(number))
                .isInstanceOf(LottoNumberRangeException.class);
    }

}