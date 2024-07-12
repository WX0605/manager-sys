package org.example.repository;


import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.TypeReference;
import org.example.domain.AddUserReq;
import org.example.domain.GetResourceQuery;
import org.example.domain.UserInfo;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Repository
public class ManagerSysRepositoryImpl implements ManagerSysRepository {
    private Set<ResourcePo> resources;
    private static final String fileName = "resources.json";

    @PostConstruct
    public void init() {
        File file = Paths.get(fileName).toFile();
        if (!file.exists()){
            resources = new HashSet<>();
        } else {
            byte[] bytes = new byte[(int) file.length()];
            try (FileInputStream fis = new FileInputStream(file)) {
                fis.read(bytes);
                resources = JSON.parseObject(new String(bytes), new TypeReference<Set<ResourcePo>>(){});
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void addUser(AddUserReq addUserReq) {
        addUserReq.getEndpoint().stream().map(
                resource ->new ResourcePo(addUserReq.getUserId(), resource)
        ).forEach(resources::add);
        try(OutputStream outputStream = Files.newOutputStream(Paths.get(fileName))) {
            outputStream.write(JSON.toJSONBytes(resources));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getResource(GetResourceQuery query, UserInfo userInfo) {
        List<ResourcePo> resourcePos = resources.stream().filter(
                resourcePo -> resourcePo.getResource().equals(query.getResource()) &&
                        resourcePo.getuserId().equals(userInfo.getUserId())
        ).collect(Collectors.toList());
        if(resourcePos.isEmpty()){
            return null;
        }
        return resourcePos.get(0).getResource();
    }
}
