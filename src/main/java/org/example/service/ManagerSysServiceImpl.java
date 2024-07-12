package org.example.service;


import org.example.domain.AddUserReq;
import org.example.domain.GetResourceQuery;
import org.example.domain.NoAccessException;
import org.example.domain.UserInfo;
import org.example.repository.ManagerSysRepository;
import org.springframework.stereotype.Service;

@Service
public class ManagerSysServiceImpl implements ManagerSysService {
    private final ManagerSysRepository managerSysRepository;
    public ManagerSysServiceImpl(ManagerSysRepository managerSysRepository) {
        this.managerSysRepository = managerSysRepository;
    }

    @Override
    public void addUser(AddUserReq command, UserInfo userInfo) throws NoAccessException {
        if(!"admin".equals(userInfo.getRole())){
            throw new NoAccessException();
        }
        managerSysRepository.addUser(command);
    }

    @Override
    public String getResource(GetResourceQuery query, UserInfo userInfo) throws NoAccessException {
        String resource = managerSysRepository.getResource(query, userInfo);
        if(resource == null){
            throw new NoAccessException();
        }
        return resource;
    }
}
