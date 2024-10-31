package qlinx.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class Controller {

    @PostMapping("/test")
    public String index() { return "hi" ;}
}
