package org.example.repository;


import org.example.domain.AddUserReq;
import org.example.domain.GetResourceQuery;
import org.example.domain.UserInfo;

public interface ManagerSysRepository {
    void addUser(AddUserReq addUserReq);

    String getResource(GetResourceQuery query, UserInfo userInfo);
}
