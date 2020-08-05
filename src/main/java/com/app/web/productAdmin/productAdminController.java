package com.app.web.productAdmin;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@RequestMapping(value = "/productadmin", method = RequestMethod.GET)
@Controller
public class productAdminController {

    @RequestMapping(value = "/productoperation")
    public String productOperation(){
        return "/productoperation";
    }

    @RequestMapping(value = "/productlist")
    public String productList(){
        return "/productlist";
    }

    @RequestMapping(value = "/productdetail")
    public String productDetail(){
        return "/productdetail";
    }
}
