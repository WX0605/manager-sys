package org.example.controller;

import com.alibaba.fastjson2.JSON;
import org.example.domain.AddUserReq;
import org.example.domain.GetResourceQuery;
import org.example.service.ManagerSysService;
import org.example.domain.NoAccessException;
import org.example.domain.UserInfo;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Base64;

@RestController
public class ManagerSysController {
    private final ManagerSysService managerSystemService;

    public ManagerSysController(ManagerSysService managerSystemService) {
        this.managerSystemService = managerSystemService;
    }

    @RequestMapping("/admin/addUser")
    public ResponseEntity<String> addUser(@RequestBody AddUserReq addUserReq, @RequestHeader(name = "Authorization") String authorization) {
        UserInfo userInfo = getUserInfo(authorization);
        try {
            this.managerSystemService.addUser(addUserReq, userInfo);
            return ResponseEntity.ok("Success");
        } catch (NoAccessException e) {
            return ResponseEntity.badRequest().body("No Access");
        }
    }

    @RequestMapping("/user/{resource}")
    public ResponseEntity<String> getResource(@PathVariable String resource, @RequestHeader(name = "Authorization") String authorization) {
        UserInfo userInfo = getUserInfo(authorization);
        try {
            return ResponseEntity.ok(this.managerSystemService.getResource(new GetResourceQuery(resource), userInfo));
        } catch (NoAccessException e) {
            return ResponseEntity.badRequest().body("No Access");
        }
    }


    private UserInfo getUserInfo(String authorization) {
        String decodedHeader = new String(Base64.getDecoder().decode(authorization));
        return JSON.parseObject(decodedHeader, UserInfo.class);
    }
}
