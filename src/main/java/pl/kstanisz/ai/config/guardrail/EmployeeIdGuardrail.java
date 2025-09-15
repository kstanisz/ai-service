package pl.kstanisz.ai.config.guardrail;

import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.guardrail.InputGuardrail;
import dev.langchain4j.guardrail.InputGuardrailRequest;
import dev.langchain4j.guardrail.InputGuardrailResult;
import dev.langchain4j.internal.ValidationUtils;
import dev.langchain4j.memory.ChatMemory;
import pl.kstanisz.ai.config.chat_memory.ChatMemoryId;

import java.util.regex.Pattern;

public class EmployeeIdGuardrail implements InputGuardrail {

    private static final Pattern EMPLOYEE_ID_PATTERN = Pattern.compile("EMP_\\d{5}");

    @Override
    public InputGuardrailResult validate(InputGuardrailRequest params) {
        ChatMemory chatMemory = params.requestParams().chatMemory();
        ChatMemoryId chatMemoryId = (ChatMemoryId) chatMemory.id();

        ValidationUtils.ensureNotNull(chatMemoryId, "chatMemoryId");
        ValidationUtils.ensureNotNull(chatMemoryId.conversationId(), "conversationId");
        ValidationUtils.ensureNotNull(chatMemoryId.userId(), "userId");

        return this.validate(params.userMessage());
    }

    @Override
    public InputGuardrailResult validate(UserMessage userMessage) {
        String content = userMessage.singleText();
        if (EMPLOYEE_ID_PATTERN.matcher(content).find()) {
            return failure("Found employee id in user prompt");
        }
        return success();
    }

}
