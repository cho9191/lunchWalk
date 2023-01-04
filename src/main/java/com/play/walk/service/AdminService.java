package com.play.walk.service;

import com.play.walk.mapper.AdminMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService {
    @Autowired
    private AdminMapper adminMapper;

    public void test(){
        System.out.println("adminService test!!");
        String abc = adminMapper.abc("TEST1");

        List<String> abcList = adminMapper.abcList();
        int iabc = adminMapper.save();

        System.out.println("adminService test!! abc : "+abc);
        System.out.println("adminService test!! abcList : "+abcList);
        System.out.println("adminService test!! iabc : "+iabc);

    }
}
