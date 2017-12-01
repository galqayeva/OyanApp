/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataAccessLayer.Repository;

import DataAccessLayer.Constant.Constants;
import SeriveLayer.Domain.MiddlewareModels.*;
import java.sql.*;

/**
 *
 * @author Orkhan
 */
public class MiddlewareRepository {

    private final static int INVALID_USER_ID = -1;
    private final int userID;

    public MiddlewareRepository(int id) {
        this.userID = id;
    }

    public MiddlewareRepository() {
        this.userID = INVALID_USER_ID;
    }

    public MiddlewareResponseModel UpdateDeviceInfo(final DeviceInfoMiddlewareModel model) {
        return callProcedure(Constants.UPDATE_DEVICE_INFO, 6, new StatementHelper() {
            @Override
            protected MiddlewareResponseModel onCallPrepared(PreparedStatement statement, int parameterIndex) throws Exception {
                statement.setString(parameterIndex++, model.name);
                statement.setInt(parameterIndex++, model.platformId);
                statement.setString(parameterIndex++, model.pushToken);
                statement.setString(parameterIndex++, model.osVersion);
                statement.setString(parameterIndex++, model.appVersion);
                statement.setBoolean(parameterIndex++, model.sandbox);
                statement.execute();
                return MiddlewareResponseModel.Success();
            }
        });
    } 
    
    public DeviceInfoMiddlewareModel GetDeviceInfoBySipId(final int sipId) {
        return (DeviceInfoMiddlewareModel)callProcedure(Constants.GET_DEVICE_INFO_BY_SIP_ID, 1, new StatementHelper() {
            @Override
            protected MiddlewareResponseModel onCallPrepared(PreparedStatement statement, int parameterIndex) throws Exception {
                statement.setInt(parameterIndex++, sipId);
                if (statement.execute()) {
                    ResultSet resultSet = statement.getResultSet();
                    MiddlewareResponseModel result = MiddlewareResponseModel.Success();
                    DeviceInfoMiddlewareModel data = new DeviceInfoMiddlewareModel();
                    if (resultSet.next()) {
                        data.name = resultSet.getString(Constants.NAME);
                        data.platformId = resultSet.getInt(Constants.PLATFORM_ID);
                        data.pushToken = resultSet.getString(Constants.PUSH_TOKEN);
                        data.osVersion = resultSet.getString(Constants.OS_VERSION);
                        data.appVersion = resultSet.getString(Constants.APP_VERSION);
                        data.sandbox = resultSet.getBoolean(Constants.SANDBOX);
                    }
                    result.data = data;
                    return result;
                }
                return MiddlewareResponseModel.DataAccessError();
            }
        }).data;
    }

    public int GetUserIdFromToken(final String token) {
        return (int)callProcedure(Constants.GET_USER_ID_BY_TOKEN, 1, new StatementHelper() {
            @Override
            protected MiddlewareResponseModel onCallPrepared(PreparedStatement statement, int parameterIndex) throws Exception {
                MiddlewareResponseModel result = MiddlewareResponseModel.Success();
                result.data = INVALID_USER_ID;
                statement.setString(parameterIndex++, token);
                if (statement.execute()) {
                    ResultSet resultSet = statement.getResultSet();
                    if (resultSet.next()) {
                        result.data = resultSet.getInt(Constants.USER_ID);
                    }
                }
                return result;
            }
        }).data;
    }

    private abstract class StatementHelper {

        protected abstract MiddlewareResponseModel onCallPrepared(PreparedStatement statement, int parameterIndex) throws Exception;
    }

    private MiddlewareResponseModel callProcedure(String procedure, int numberOfParams, StatementHelper callback) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            Class.forName(Constants.JDBC_DRIVER);
            connection = DriverManager.getConnection(Constants.DB_URL, Constants.DB_USERNAME, Constants.DB_PASSWORD);
            String questionMarks = "?"; // for ID
            for (int i = 0; i < numberOfParams; i++) {
                questionMarks += ",?";
            }
            int parameterIndex = 1;
            preparedStatement = connection.prepareCall(String.format("{CALL %S (%S)}", procedure, questionMarks));
            preparedStatement.setLong(parameterIndex++, userID);
            return callback.onCallPrepared(preparedStatement, parameterIndex);

        } catch (Exception exception) {
            return MiddlewareResponseModel.DatabaseError();
        } finally {
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException exception) {
                return MiddlewareResponseModel.DatabaseError();
            }
        }
    }
}
