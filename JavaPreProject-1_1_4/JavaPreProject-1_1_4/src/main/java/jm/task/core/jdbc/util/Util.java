package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Util {
    public static final String URL = "jdbc:mysql://localhost:3306/USER" + "?verifyServerCertificate=false"+"&useSSL=false"+"&requireSSL=false"+"&useLegacyDatetimeCode=false"+"&amp"+"&serverTimezone=UTC";
    public static final String USERNAME = "root";
    public static final String PASSWORD = "root";

    private SessionFactory sessionFactory;

    public Connection getConnection(){
        Connection connection = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(URL,USERNAME,PASSWORD);
            System.out.println("Connection successful");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            System.out.println("Connection Error");
        }
        return connection;
    }

    // Hibernate
    public SessionFactory getSessionFactory() {

        if (sessionFactory == null) {

            try {
                Configuration configuration = new Configuration();
                Properties properties = new Properties();

                properties.put(Environment.DRIVER,"com.mysql.jdbc.Driver");
                properties.put(Environment.URL,URL);
                properties.put(Environment.USER,USERNAME);
                properties.put(Environment.PASS,PASSWORD);


                properties.put(Environment.DIALECT,"org.hibernate.dialect.MySQL8Dialect");
                properties.put(Environment.CURRENT_SESSION_CONTEXT_CLASS,"thread");
                properties.put(Environment.SHOW_SQL,false);
                properties.put(Environment.HBM2DDL_AUTO,"create-drop");

                configuration.setProperty("hibernate.connection.autocommit", "false" );
                configuration.setProperties(properties);
                configuration.addAnnotatedClass(User.class);

                ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                        .applySettings(configuration.getProperties()).build();

                sessionFactory = configuration.buildSessionFactory(serviceRegistry);

            } catch (Exception e) {
                System.out.println("Connection error !" + e);
            }
        }
        return sessionFactory;
    }
}
