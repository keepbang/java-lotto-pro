package lotto.controller;

import lotto.domain.BoughtLotto;
import lotto.domain.LottoNumbers;
import lotto.domain.LottoTicket;
import lotto.domain.Winning;
import lotto.domain.WinningMap;

import static lotto.view.ConsoleView.printBoughtLottoCount;
import static lotto.view.ConsoleView.printRevenue;
import static lotto.view.ConsoleView.printLottoTicket;
import static lotto.view.ConsoleView.printLine;
import static lotto.view.ConsoleView.printWinning;

public class LottoExecutor {

    private final LottoController lottoController;

    public LottoExecutor(LottoController lottoController) {
        this.lottoController = lottoController;
    }

    public void run() {
        BoughtLotto boughtLotto = enterPayment();
        LottoTicket lottoTicket = generateLotto(boughtLotto);
        LottoNumbers winningNumbers = enterWinningNumber();
        Winning winning = enterWinningBonusNumber(winningNumbers);
        WinningMap winningMap = winningResult(lottoTicket, winning);
        printRevenue(calculateRevenue(winningMap, boughtLotto));
    }

    private BoughtLotto enterPayment() {
        BoughtLotto boughtLotto = lottoController.buyLotto();
        printLine();
        return boughtLotto;
    }

    private LottoTicket generateLotto(BoughtLotto boughtLotto) {
        LottoTicket lottoTicket = lottoController.generateLottoTicket(boughtLotto);
        printBoughtLottoCount(boughtLotto);
        printLottoTicket(lottoTicket);
        printLine();
        return lottoTicket;
    }

    private LottoNumbers enterWinningNumber() {
        LottoNumbers winningNumbers = lottoController.enterWinningLottoNumbers();
        return winningNumbers;
    }

    private Winning enterWinningBonusNumber(LottoNumbers winningNumbers) {
        Winning bonusNumber = lottoController.enterWinningBonusNumber(winningNumbers);
        printLine();
        return bonusNumber;
    }

    private WinningMap winningResult(LottoTicket lottoTicket, Winning winningNumbers) {
        WinningMap winningMap = WinningMap.winningOf(lottoTicket, winningNumbers);
        printWinning(winningMap);
        return winningMap;
    }

    private double calculateRevenue(WinningMap winningMap, BoughtLotto boughtLotto) {
        return winningMap.revenue(boughtLotto);
    }


}
