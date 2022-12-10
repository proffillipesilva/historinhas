package com.fiec.lpiiiback;

import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobContainerClientBuilder;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.PostConstruct;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;

@SpringBootApplication
public class TccApplication  {

	@Value("${spring.cloud.azure.storage.blob.account-key}")
	private String accountKey;

	public static void main(String[] args){

		SpringApplication.run(TccApplication.class, args);
		try {
			Files.createDirectory(Paths.get("uploads"));
		} catch (IOException ignored) {

		}
	}

	@PostConstruct
	public void init() throws IOException {
		String connStr = "DefaultEndpointsProtocol=https;" +
				"AccountName=historinhas;" +
				"AccountKey=" + accountKey + ";" +
				"EndpointSuffix=core.windows.net";

		BlobContainerClient blobContainerClient = new BlobContainerClientBuilder()
				.connectionString(connStr)
				.containerName("historinhas")
				.buildClient();
		BlobClient blob = blobContainerClient.getBlobClient("historinhas.json");

		URL resource = TccApplication.class.getClassLoader()
				.getResource("");

		assert resource != null;
		blob.downloadToFile(resource.getPath() + "fbase1.json", true);

		BlobClient blob2 = blobContainerClient.getBlobClient("google_credentials.json");
		blob2.downloadToFile(resource.getPath() + "google_credentials.json", true);

		FileInputStream serviceAccount =
				new FileInputStream(resource.getPath() + "fbase1.json");
		FirebaseOptions options = FirebaseOptions.builder()
				.setCredentials(GoogleCredentials.fromStream(serviceAccount))
				//.setDatabaseUrl("https://tcc-fiec-3mod-default-rtdb.firebaseio.com/")
				.build();
		FirebaseApp.initializeApp(options);
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
