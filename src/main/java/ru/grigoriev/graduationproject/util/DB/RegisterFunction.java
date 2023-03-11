package ru.grigoriev.graduationproject.util.DB;

import org.hibernate.boot.MetadataBuilder;
import org.hibernate.boot.spi.MetadataBuilderContributor;
import org.hibernate.dialect.function.StandardSQLFunction;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.stereotype.Component;

//https://question-it.com/questions/11967366/kak-zaregistrirovat-nestandartnye-funktsii-sql-vruchnuju-v-prilozhenii-spring-boot
@Component
public class RegisterFunction implements MetadataBuilderContributor {

    @Override
    public void contribute(MetadataBuilder metadataBuilder) {
        metadataBuilder.applySqlFunction("group_concat", new StandardSQLFunction("group_concat", StandardBasicTypes.STRING));
    }
}
