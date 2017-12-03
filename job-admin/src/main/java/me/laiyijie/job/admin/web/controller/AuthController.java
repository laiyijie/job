package me.laiyijie.job.admin.web.controller;

import io.swagger.annotations.ApiParam;
import me.laiyijie.job.admin.dao.TbAdminRepository;
import me.laiyijie.job.admin.dao.entity.TbAdmin;
import me.laiyijie.job.admin.web.handler.AuthInterceptor;
import me.laiyijie.job.swagger.api.AuthApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by laiyijie on 12/2/17.
 */
@RestController
public class AuthController implements AuthApi {
    @Autowired
    private TbAdminRepository tbAdminRepository;

    @Override
    public ResponseEntity<Void> authLoginPost(@ApiParam(value = "", required = true) @RequestParam(value = "username", required = true) String username, @ApiParam(value = "", required = true) @RequestParam(value = "password", required = true) String password, HttpServletRequest request, HttpServletResponse response) throws Exception {
        TbAdmin tbAdmin = tbAdminRepository.findByUsername(username);
        if (tbAdmin == null || !tbAdmin.getUsername().equals(password))
            return ResponseEntity.status(401).build();
        request.getSession().setAttribute(AuthInterceptor.SESSION_KEY, username);
        return ResponseEntity.ok().build();
    }
}
