package com.semanticbits.studentspringapp.controller;

import com.semanticbits.studentspringapp.controller.command.StudentCommand;
import com.semanticbits.studentspringapp.controller.validator.StudentValidator;
import com.semanticbits.studentspringapp.domain.School;
import com.semanticbits.studentspringapp.propertyeditors.SchoolPropertyEditor;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.support.SessionStatus;

import java.util.*;

/**
 * Created by shoaib on 2/11/14.
 */
@Controller
@RequestMapping("/studentRegistration")
public class StudentDetailsController {

    private static final Logger logger = Logger.getLogger(StudentDetailsController.class);

    public StudentDetailsController() {
        System.out.println("In Student Constructor my shoaib");
    }

    @InitBinder
    public void initBinder(WebDataBinder binder){
        logger.debug("In init binder");

        //binding custom validator
       binder.setValidator(new StudentValidator());
        binder.registerCustomEditor(School.class,new SchoolPropertyEditor());

    }

    @RequestMapping(method=RequestMethod.GET)
    public String showForm(ModelMap map){
        StudentCommand command =new StudentCommand();
        command.setSubjects(Arrays.asList(new String[]{"Spring"}));
        map.addAttribute("command", command);
        return "studentregistration";
    }

    @RequestMapping(method= RequestMethod.POST)
    public String processForm(@ModelAttribute("command") @Validated StudentCommand command,
                              BindingResult errors,SessionStatus status,ModelMap map){

        if(errors.hasErrors()){
            map.addAttribute("hasErrors",true);
            return "studentregistration";
        }

        status.setComplete();

        return "studentdetail";
    }

    @ModelAttribute("subjects")
    public List<String> referenceData(){
        List<String> subjects=new ArrayList<String>();
        subjects.add("Java");
        subjects.add("Spring");
        subjects.add("Hibernate");

        return subjects;
    }
}
