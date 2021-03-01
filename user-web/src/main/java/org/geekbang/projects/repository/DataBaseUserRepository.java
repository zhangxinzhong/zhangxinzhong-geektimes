package org.geekbang.projects.repository;

import org.geekbang.projects.db.DBConnectionManager;
import org.geekbang.projects.domain.User;
import org.geekbang.projects.function.ThrowableFunction;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.*;
import java.util.*;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.apache.commons.lang.ClassUtils.wrapperToPrimitive;

/**
 * 用户的数据库仓储
 *
 * @author: zhangxinzhong
 * @since: 1.0.0
 * @version: JDK8
 * @create: 2021-03-01 10:47
 **/
public class DataBaseUserRepository implements UserRepository {
    Logger logger = Logger.getLogger(DataBaseUserRepository.class.getName());

    private final static String DATA_BASE_URL = "jdbc:derby:db/user-platform;create=true";


    private final Consumer<Throwable> consumer = (e) -> logger.log(Level.INFO, "发生异常：" + e.getMessage());


    private static Map<Class, String> preparedStatementParamMapping = new HashMap<>();

    static {
        preparedStatementParamMapping.put(String.class, "setString");
        preparedStatementParamMapping.put(Long.class, "setLong");
        preparedStatementParamMapping.put(Integer.class, "setInteger");

    }

    @Override
    public boolean create(User user) {

        return execute("INSERT INTO users(name,password,email,phoneNumber) VALUES (?,?,?,?)", consumer, user.getName(), user.getPassword(), user.getEmail(), user.getPhoneNumber());
    }

    @Override
    public List<User> queryAll() {
        return executeQuery("SELECT id,name,password,email,phoneNumber FROM users",(resultSet)->{
            List<User> users = new ArrayList<>();
            while (resultSet.next()){
                User user = new User();
                user.setId(resultSet.getLong("id"));
                user.setName(resultSet.getString("name"));
                user.setPassword(resultSet.getString("password"));
                user.setEmail(resultSet.getString("email"));
                user.setPhoneNumber(resultSet.getString("phoneNumber"));
                users.add(user);
            }
            return users;
        },consumer);
    }


    public Boolean execute(String sql, Consumer<Throwable> consumer, Object... args) {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            invokePreparedStatementParam(preparedStatement, args, consumer);

            return preparedStatement.executeUpdate() > 0;

        } catch (SQLException e) {
            consumer.accept(e);
        }
        return Boolean.FALSE;
    }

    /**
     * 获取 {@link Connection}
     *  1. jndi
     *  2. ServiceLoader
     *
     * @return
     */
    private Connection getConnection() {
        Connection connection = null;
        try {
            Context initCtx = new InitialContext();
            Context envCtx = (Context) initCtx.lookup("java:comp/env");

            DataSource ds = (DataSource) envCtx.lookup("jdbc/UserPlatformDB");
            connection = ds.getConnection();
            logger.log(Level.INFO,"------>获取了 jndi 加载数据源");
        } catch (NamingException | SQLException e) {
            try {
                connection = DriverManager.getConnection(DATA_BASE_URL);
                logger.log(Level.INFO,"------>获取了 serviceLoader 加载的数据源");
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }

        return connection;
    }

    public <T> T executeQuery(String sql, ThrowableFunction<ResultSet, T> function, Consumer<Throwable> exceptionHandler, Object... args) {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            invokePreparedStatementParam(preparedStatement, args, exceptionHandler);
            ResultSet resultSet = preparedStatement.executeQuery();
            return function.apply(resultSet);
        } catch (Throwable e) {
            exceptionHandler.accept(e);
        }

        return null;
    }


    private void invokePreparedStatementParam(PreparedStatement preparedStatement, Object[] args, Consumer<Throwable> exceptionHandler) {
        try {
            for (int i = 0; i < args.length; i++) {
                Object arg = args[i];
                Class argType = arg.getClass();

                // 包装到原始类型
                Class wrapperType = wrapperToPrimitive(argType);

                if (wrapperType == null) {
                    wrapperType = argType;
                }

                String methodName = preparedStatementParamMapping.get(argType);
                Method method = PreparedStatement.class.getMethod(methodName, int.class, wrapperType);
                method.invoke(preparedStatement, i + 1, arg);
            }
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            consumer.accept(e);
        }
    }

}
