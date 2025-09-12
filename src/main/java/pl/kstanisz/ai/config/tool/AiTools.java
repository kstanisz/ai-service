package pl.kstanisz.ai.config.tool;

import dev.langchain4j.agent.tool.P;
import dev.langchain4j.agent.tool.Tool;
import dev.langchain4j.agent.tool.ToolMemoryId;
import org.springframework.stereotype.Component;
import pl.kstanisz.ai.config.chat_memory.ChatMemoryId;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;

@Component
public class AiTools {

    private static final Map<String, Integer> EMPLOYEE_AVAILABLE_LEAVE_DAYS = new HashMap<>(
            Map.of(
                    "EMP_12345", 10,
                    "EMP_23456", 3,
                    "EMP_34567", 1
            )
    );

    @Tool("Zwraca liczbę dostępnych dni urlopowych pracownika.")
    public int getRemainingLeaveDays(@ToolMemoryId ChatMemoryId chatMemoryId) {
        return EMPLOYEE_AVAILABLE_LEAVE_DAYS.get(chatMemoryId.userId());
    }

    @Tool("""
            Rejestruje wniosek urlopowy pracownika.
            Zwraca informację o liczbie pozostałych dni urlopu po uwzględnieniu wniosku.
            Jeżeli liczba pozostałych dni jest ujemna, oznacza to brak wystarczającej liczby dni urlopowych.
            Jeżeli użytkownik nie podał roku, należy domyślnie przyjąć rok bieżący.
            Jeżeli użytkownik nie podał miesiąca, należy domyślnie przyjąć miesiąc bieżący.
            Po zarejestrowaniu urlopu nie trzeba już informować przełożonego. Dostanie informację mailem.
            """)
    public int requestLeave(@ToolMemoryId ChatMemoryId chatMemoryId,
                            @P("Data rozpoczęcia urlopu") LocalDate leaveStartDate,
                            @P("Data zakończenia urlopu") LocalDate leaveEndDate) {
        String employeeId = chatMemoryId.userId();
        int availableLeaveDays = EMPLOYEE_AVAILABLE_LEAVE_DAYS.get(employeeId);
        int requestedLeaveDays = (int) ChronoUnit.DAYS.between(leaveStartDate, leaveEndDate) + 1;
        int updatedLeaveDays = availableLeaveDays - requestedLeaveDays;

        if (updatedLeaveDays >= 0) {
            EMPLOYEE_AVAILABLE_LEAVE_DAYS.put(employeeId, updatedLeaveDays);
        }

        return updatedLeaveDays;
    }

}
