package com.springboot.FlomadAIplanner;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.web.client.RestTemplate;

// import com.springboot.FlomadAIplanner.DatabaseManagement.ConversationRepository;


// @ComponentScan(basePackages = { "com.springboot.FlomadAIplanner.DatabaseManagement" })
// @EntityScan("com.*")
// @EnableJpaRepositories("com.*")
@SpringBootApplication
public class FlomadAiPlannerApplication {

	public static void main(String[] args) {
		SpringApplication.run(FlomadAiPlannerApplication.class, args);
	}

	@Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
	
	// @Bean
	// public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
	// 	HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
	// 	vendorAdapter.setGenerateDdl(true);

	// 	LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
	// 	factory.setJpaVendorAdapter(vendorAdapter);
	// 	factory.setPackagesToScan(new String[]{"com.springboot.FlomadAIplanner.DatabaseManagement"});
	// 	return factory;
	// }

	// @Bean
	// public CommandLineRunner demo(ConversationRepository repository) {
	// 	return (args) -> {
	// 		// 测试JPA配置是否能够访问数据库和实体
	// 		System.out.println("测试实体管理状态:");
	// 		repository.findById("9999").ifPresent(message -> {
	// 			System.out.println("找到消息: " + message);
	// 		});
	// 	};
	// }
}
