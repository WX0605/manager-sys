package org.example.service;

import org.example.domain.AddUserReq;
import org.example.domain.GetResourceQuery;
import org.example.domain.NoAccessException;
import org.example.domain.UserInfo;

public interface ManagerSysService {
    void addUser(AddUserReq addUserReq, UserInfo userInfo) throws NoAccessException;

    String getResource(GetResourceQuery query, UserInfo userInfo) throws NoAccessException;
}
