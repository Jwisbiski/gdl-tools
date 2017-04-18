package se.cambio.cm.configuration;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import se.cambio.cm.model.util.CMElementDAOFactory;
import se.cambio.openehr.util.configuration.UserConfiguration;

@Configuration
@Import({FileClinicalModelsPersistenceConfiguration.class})
public class CmPersistenceConfig {

    @Bean
    CMElementDAOFactory cmElementDAOFactory(ApplicationContext applicationContext) {
        return new CMElementDAOFactory(applicationContext);
    }

}