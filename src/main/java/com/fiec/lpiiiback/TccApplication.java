package com.fiec.lpiiiback;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;

@SpringBootApplication
public class TccApplication  {

	public static void main(String[] args) throws IOException {
		SpringApplication.run(TccApplication.class, args);
		URL resource = new TccApplication().getClass().getClassLoader()
				.getResource("fbase.json");
		FileInputStream serviceAccount =
				new FileInputStream(resource.getPath());
		FirebaseOptions options = FirebaseOptions.builder()
				.setCredentials(GoogleCredentials.fromStream(serviceAccount))
				//.setDatabaseUrl("https://tcc-fiec-3mod-default-rtdb.firebaseio.com/")
				.build();
		FirebaseApp.initializeApp(options);
		try {
			Files.createDirectory(Paths.get("uploads"));
		} catch (IOException e) {

		}
	}

	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**").allowedOrigins("*").allowedMethods("*");
			}
		};
	}

}
