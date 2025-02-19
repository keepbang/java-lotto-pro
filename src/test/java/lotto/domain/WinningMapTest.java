package lotto.domain;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class WinningMapTest {

    private static LottoTicket lottoTicket;

    @BeforeAll
    public static void beforeEach() {
        lottoTicket = new LottoTicket(Arrays.asList(
                LottoNumbers.fromString("1, 2, 30, 34, 37, 42")
        ));
    }

    private static Stream<Arguments> inputWinningTest() {
        return Stream.of(
                Arguments.of(LottoNumbers.fromString("1,2,37,41,43,44"), Rank.FIFTH),
                Arguments.of(LottoNumbers.fromString("2,30,34,37,40,41"), Rank.FOURTH),
                Arguments.of(LottoNumbers.fromString("1,2,8,30,37,42"), Rank.THIRD),
                Arguments.of(LottoNumbers.fromString("1,2,30,34,37,42"), Rank.FIRST)
        );
    }

    @ParameterizedTest
    @MethodSource
    @DisplayName("각 등수별로 맞췄을 경우")
    public void inputWinningTest(LottoNumbers winningNumbers, Rank rank) {
        Winning winning = Winning.of(winningNumbers, 10);
        WinningMap winningMap = WinningMap.winningOf(lottoTicket, winning);

        Map<Rank, Integer> resultMap = new HashMap<>();
        resultMap.put(rank, 1);

        assertThat(winningMap.getWinningMap().equals(resultMap)).isTrue();
    }

    @Test
    @DisplayName("2등 당첨 테스트")
    public void secondRankTest() {
        Winning winning = Winning.of(LottoNumbers.fromString("1,2,30,34,37,40"), 42);
        WinningMap winningMap = WinningMap.winningOf(lottoTicket, winning);
        HashMap<Rank, Integer> resultRankMap = new HashMap<>();
        resultRankMap.put(Rank.SECOND, 1);

        assertThat(winningMap.getWinningMap().equals(resultRankMap)).isTrue();
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "5,6,7,8,9,10",
            "1,7,23,31,33,44",
            "1,41,42,43,44,45"
    })
    @DisplayName("맞춘 번호가 2개 이하일 경우")
    public void lottoMatchNothing(String input) {
        LottoNumbers lottoNumbers = LottoNumbers.fromString(input);
        Winning winning = Winning.of(lottoNumbers, 11);

        WinningMap winningMap = WinningMap.winningOf(lottoTicket, winning);

        assertThat(winningMap.getWinningMap()
                .keySet()
                .size()).isEqualTo(0);
    }

    @ParameterizedTest
    @CsvSource(value = {
            "1,2,7,30,40,41|5",
            "1,2,30,34,40,41|50",
            "1,2,30,37,42,45|1500",
            "1,2,30,34,37,42|2000000"
    }, delimiter = '|')
    @DisplayName("수익 계산")
    public void revenueTest(String input, int revenuePercent) {
        LottoNumbers winningNumbers = LottoNumbers.fromString(input);
        Winning winning = Winning.of(winningNumbers, 10);
        BoughtLotto boughtLotto = BoughtLotto.of("1000", "0");

        WinningMap winningMap = WinningMap.winningOf(lottoTicket, winning);

        double revenue = winningMap.revenue(boughtLotto);

        assertThat(revenue).isEqualTo(revenuePercent);
    }

}
