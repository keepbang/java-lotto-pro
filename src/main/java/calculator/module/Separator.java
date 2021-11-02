package calculator.module;

import calculator.domain.Numbers;
import calculator.utils.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Separator {
    private static final String CUSTOM_REGEX = "//(.)\n(.*)";
    private static final String DEFAULT_REGEX = ",|:";
    private static final Pattern PATTERN_COMPILE = Pattern.compile(CUSTOM_REGEX);

    private String delimiter;
    private String separated;

    private Separator(String delimiter, String separated) {
        this.delimiter = delimiter;
        this.separated = separated;
    }

    public static Separator separate(String input) {
        if (StringUtils.isEmpty(input)) {
            input = "0";
        }

        Matcher m = PATTERN_COMPILE.matcher(input);

        String delimiter = DEFAULT_REGEX;
        String separated = input;

        if (m.find()) {
            delimiter = "\\" + m.group(1);
            separated = m.group(2);
        }

        return new Separator(delimiter, separated);
    }

    public Numbers splitSeparated() {
        List<String> stringList = Arrays.asList(separated.split(delimiter));
        return new Numbers(stringList);
    }
}
