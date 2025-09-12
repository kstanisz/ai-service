package pl.kstanisz.ai.config.guardrail;

import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.guardrail.InputGuardrail;
import dev.langchain4j.guardrail.InputGuardrailResult;

import java.util.regex.Pattern;

public class EmployeeIdGuardrail implements InputGuardrail {

    private static final Pattern EMPLOYEE_ID_PATTERN = Pattern.compile("EMP_\\d{5}");

    @Override
    public InputGuardrailResult validate(UserMessage userMessage) {
        String content = userMessage.singleText();
        if (EMPLOYEE_ID_PATTERN.matcher(content).find()) {
            return failure("Found employee id in user prompt");
        }
        return success();
    }

}
