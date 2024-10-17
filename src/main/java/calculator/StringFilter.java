package calculator;

import java.util.Arrays;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

public class StringFilter {
    private final String DEFAULT_DELIMITER = "[,:]";

    public String[] StringController(String input) {
        String[] sanitizedInput = splitAndTrim(input);

        if (validateDelimiter(sanitizedInput)){
        validateNegativeNumber(sanitizedInput); //첫번째 인덱스가 음수,양수인지 확인
            return sanitizedInput;
        }
        else return customDelimiter(sanitizedInput);

    }

    //문자열 나누기
    private String[] splitAndTrim(String input) {
        String[] splitInput = input.split(DEFAULT_DELIMITER);

        return Arrays.stream(splitInput)
                .filter(s -> !s.isEmpty())
                .map(String::trim)
                .toArray(String[]::new);
    }

    //구분자 검증
    private boolean validateDelimiter(String[] splitedString) {
        try{
            Integer.parseInt(splitedString[0]);
            return true;
        }catch (NumberFormatException e){
            return false;
        }
    }
    
    //입력값에 대한 음수,양수 검증
    private static void validateNegativeNumber(String[] splitedString) {
        for (String numberStr : splitedString) {
            if (Integer.parseInt(numberStr.trim()) < 0) {
                throw new IllegalArgumentException("음수는 입력할 수 없습니다");
            }
        }
    }

    //커스텀 구분자 추출
    private String[] customDelimiter(String[] filteredString) {
        String s = String.join("", filteredString);
        String replaceInput = s.replace("//", "").replace("\\n", "").trim();
        String customDelimiter = replaceInput.substring(0, 1);
        customDelimiter = validateMetaCharacter(customDelimiter);

        return replaceInput.substring(1).split(customDelimiter);
    }

    //커스텀 구분자 추출시 입력된 메타문자 검증
    private static String validateMetaCharacter(String customDelimiter) {
        try {
            Pattern.compile(customDelimiter);
        } catch (PatternSyntaxException e) {
            customDelimiter = new StringBuilder().append("\\").append(customDelimiter).toString();
        }
        return customDelimiter;
    }
}