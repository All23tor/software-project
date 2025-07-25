package org.unsa.softwareproject;

import org.springframework.boot.SpringApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("")
class SoftwareProjectApplicationController {
    public static void main(String[] args) {
        SpringApplication.run(SoftwareProjectApplication.class, args);
    }
}
