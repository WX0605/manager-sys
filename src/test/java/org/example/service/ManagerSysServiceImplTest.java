package org.example.service;


import org.assertj.core.util.Lists;
import org.example.domain.AddUserReq;
import org.example.domain.GetResourceQuery;
import org.example.domain.NoAccessException;
import org.example.domain.UserInfo;
import org.example.repository.ManagerSysRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ManagerSysServiceImplTest {
    private ManagerSysRepository mockmanagerSysRepository;

    private ManagerSysService managerSystemService;

    @BeforeEach
    public void setup(){
        mockmanagerSysRepository = mock(ManagerSysRepository.class);
        managerSystemService = new ManagerSysServiceImpl(mockmanagerSysRepository);
    }

    @Test
    void addUserSuccess() throws NoAccessException {
        AddUserReq command = new AddUserReq();
        command.setUserId(123456L);
        command.setEndpoint(Lists.list(
                "resource A",
                "resource B",
                "resource C"
        ));
        UserInfo userInfo = new UserInfo();
        userInfo.setUserId(123456L);
        userInfo.setAccountName("XXXXXXX");
        userInfo.setRole("admin");
        managerSystemService.addUser(command, userInfo);
    }

    @Test
    void addUserNoAccess() {
        AddUserReq command = new AddUserReq();
        command.setUserId(123456L);
        command.setEndpoint(Lists.list(
                "resource A",
                "resource B",
                "resource C"
        ));
        UserInfo userInfo = new UserInfo();
        userInfo.setUserId(123456L);
        userInfo.setAccountName("XXXXXXX");
        userInfo.setRole("user");
        try {
            managerSystemService.addUser(command, userInfo);
        } catch (Exception e){
            assertEquals(NoAccessException.class, e.getClass());
        }
    }

    @Test
    void getResourceSuccess() throws NoAccessException {
        GetResourceQuery query = new GetResourceQuery("resource A");
        UserInfo userInfo = new UserInfo();
        userInfo.setUserId(123456L);
        userInfo.setAccountName("XXXXXXX");
        userInfo.setRole("user");
        when(mockmanagerSysRepository.getResource(query, userInfo)).thenReturn("resource A");
        String actual = managerSystemService.getResource(query, userInfo);
        assertEquals("resource A", actual);
    }

    @Test
    void getResourceNoAccess() {
        GetResourceQuery query = new GetResourceQuery("resource A");
        UserInfo userInfo = new UserInfo();
        userInfo.setUserId(123456L);
        userInfo.setAccountName("XXXXXXX");
        userInfo.setRole("user");
        when(mockmanagerSysRepository.getResource(query, userInfo)).thenReturn(null);
        try {
            managerSystemService.getResource(query, userInfo);
        } catch (Exception e){
            assertEquals(NoAccessException.class, e.getClass());
        }
    }
}