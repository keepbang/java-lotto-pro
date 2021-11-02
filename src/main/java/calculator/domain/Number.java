package calculator.domain;

import calculator.utils.StringUtils;

public class Number {
    private int number;

    public Number(String input) {
        this.number = parsing(input);
    }

    private void validation(String input) {
        if (input.startsWith("-")) {
            throw new RuntimeException("음수는 입력할 수 없습니다.");
        }
    }

    private int parsing(String input) {
        if (StringUtils.isEmpty(input)) return 0;
        validation(input);
        return Integer.parseInt(input);
    }

    public int getNumber() {
        return number;
    }
}
