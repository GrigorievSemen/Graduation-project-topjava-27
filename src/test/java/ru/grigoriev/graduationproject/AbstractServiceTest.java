package ru.grigoriev.graduationproject;

import org.springframework.core.NestedExceptionUtils;
import org.springframework.lang.NonNull;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;


@SqlGroup({
        @Sql(value = "classpath:db/initDB.sql", executionPhase = BEFORE_TEST_METHOD),
        @Sql(value = "classpath:db/populateDB.sql", executionPhase = BEFORE_TEST_METHOD)
})

public abstract class AbstractServiceTest {

    protected <T extends Throwable> void validateRootCause(Class<T> rootExceptionClass, Runnable runnable) {
        assertThrows(rootExceptionClass, () -> {
            try {
                runnable.run();
            } catch (Exception e) {
                throw getRootCause(e);
            }
        });
    }

    @NonNull
    public static Throwable getRootCause(@NonNull Throwable t) {
        Throwable rootCause = NestedExceptionUtils.getRootCause(t);
        return rootCause != null ? rootCause : t;
    }
}