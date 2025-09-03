package br.com.cotiinformatica.factories;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectionFactory {

	// Atributos
	private static final String HOST = "jdbc:postgresql://localhost:5432/bd_apiagenda";
	private static final String USER = "postgres";
	private static final String PASS = "coti";
	private static final String DRIVER = "org.postgresql.Driver";

	// Método
	public static Connection getConnection() throws Exception {
		Class.forName(DRIVER);
		return DriverManager.getConnection(HOST, USER, PASS);
	}
}
