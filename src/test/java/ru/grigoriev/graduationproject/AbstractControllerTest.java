package ru.grigoriev.graduationproject;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.NestedExceptionUtils;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.filter.CharacterEncodingFilter;
import ru.grigoriev.graduationproject.mapper.DishMapper;
import ru.grigoriev.graduationproject.mapper.UserMapper;
import ru.grigoriev.graduationproject.repository.DishRepository;
import ru.grigoriev.graduationproject.security.jwt.JwtTokenProvider;
import ru.grigoriev.graduationproject.service.AuthUserService;
import ru.grigoriev.graduationproject.service.UserService;
import ru.grigoriev.graduationproject.util.DB.DB;
import ru.grigoriev.graduationproject.web.AuthRestController;
import ru.grigoriev.graduationproject.web.DishRestController;
import ru.grigoriev.graduationproject.web.UserRestController;

import javax.annotation.PostConstruct;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;


@SqlGroup({
        @Sql(value = "classpath:db/initDB.sql", executionPhase = BEFORE_TEST_METHOD),
        @Sql(value = "classpath:db/populateDB.sql", executionPhase = BEFORE_TEST_METHOD)
})

public abstract class AbstractControllerTest {
    private static final CharacterEncodingFilter CHARACTER_ENCODING_FILTER = new CharacterEncodingFilter();

    static {
        CHARACTER_ENCODING_FILTER.setEncoding("UTF-8");
        CHARACTER_ENCODING_FILTER.setForceEncoding(true);
    }

    @Autowired
    private Jackson2ObjectMapperBuilder mapperBuilder;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    @Autowired
    private AuthUserService authUserService;
    @Autowired
    private UserService userService;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private DishMapper dishMapper;
    private MockMvc mockMvc;
    private ObjectMapper mapper;
    @Autowired
    private DishRepository repository;
    @Autowired
    private DB db;

    @PostConstruct
    private void postConstruct() {
        mockMvc = MockMvcBuilders
                .standaloneSetup(new AuthRestController(authenticationManager, jwtTokenProvider, authUserService)
                        , new UserRestController(userService, userMapper), new DishRestController(repository, dishMapper, db)
                )
                .addFilter(CHARACTER_ENCODING_FILTER)
                .build();
        mapper = mapperBuilder.build();
    }

    protected ResultActions perform(MockHttpServletRequestBuilder builder) throws Exception {
        return mockMvc.perform(builder);
    }

    protected ObjectMapper mapper() {
        return mapper;
    }

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