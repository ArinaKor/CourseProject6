package com.example.servercurs.controller;

import com.example.servercurs.Rating.RatingTeacher;
import com.example.servercurs.Singleton.SingletonEnum;
import com.example.servercurs.entities.*;
import com.example.servercurs.repository.CourseRepository;
import com.example.servercurs.repository.GroupRepository;
import com.example.servercurs.repository.StudentRepository;
import com.example.servercurs.service.*;
//import com.lowagie.text.DocumentException;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

//import javax.mail.MessagingException;
import jakarta.servlet.http.HttpServletResponse;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import javax.mail.MessagingException;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.Date;
import java.sql.Time;
import java.util.*;

@Controller
public class StudentMainController {
    @Autowired
    private StudentService studentService;
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private GroupRepository groupRepository;
    @Autowired
    private CourseService courseService;
    @Autowired
    private GroupService groupService;
    @Autowired
    private EmailSenderService emailService;
    @Autowired
    private TeacherService teacherService;
   @Autowired
    private SkillsService skillsService;
   @Autowired
   private LanguageService languageService;
   @Autowired
   private CourseRepository courseRepository;
    @Autowired
    private DocumentGenerator documentGenerator;
    @Autowired
    private EmailSenderService emailSenderService;
    @Autowired
    private SpringTemplateEngine springTemplateEngine;

    @Autowired
    private DataMapper dataMapper;
    PdfController pdfController = new PdfController();


    SingletonEnum se = SingletonEnum.INSTANCE;
    //List<Group> listGroups;
    RatingTeacher ratingTeacher = new RatingTeacher();
    static int id = AuthorizationController.stud;
    List<String> listLastGroups = new ArrayList<>();
    @GetMapping("/student")
    private String student(RedirectAttributes attributes,Model model){
        //model.addAttribute("student", studentService.findById(id));
       // model.addAttribute("student", student);

        return "StudentPersonal";
    }

    @GetMapping("/students/groups/{id_student}")
    private String findAllGroups(@PathVariable(name="id_student") int id_student,RedirectAttributes attributes, Model model){
/*
        /*List<Student> list1 = studentService.findAllStudents();
            model.addAttribute("list", list1);*/

        Student student = studentService.findById(id_student);
        model.addAttribute("student", student);
        List<Group> listCourse = groupService.findAllGroups();
       // int tch = 0;
        List<Group> list = new ArrayList<>();
        Teacher teacher = new Teacher();
        User user = new User();
        for (Group gr:listCourse) {
            if(gr.getTeacher()==null){
                user.setSurname("not found");
                user.setName("yet");
                teacher.setId_user(user);
                gr.setTeacher(teacher);
            }


        }
        //model.addAttribute("tch", tch);
        model.addAttribute("list", listCourse);
        List<Skills> skillsList = skillsService.findAllSkillss();
        model.addAttribute("skills", skillsList);
        List<Language> langList = languageService.findAllLanguages();
        model.addAttribute("lang", langList);

        int k=0;
        if(student.getId_group()==null){
            k=0;
        }
        else if(!(student.getId_group()==null)){
            k++;
        }
        System.out.println(k);
        model.addAttribute("k", k);
        attributes.addFlashAttribute("student", student);
       /* List<Skills> list = skillsService.findAllSkillss();
        model.addAttribute("list", list);
        List<Language> lang = languageService.findAllLanguages();
        model.addAttribute("lang", lang);
*/
        return "FindGroupsStudent";
    }

    @PostMapping("/student/groups/{id_student}/{id_group}/enroll")
    public String enrollCourse(@PathVariable(name="id_student") int id_student, @PathVariable(name="id_group") int id_group, Model model, RedirectAttributes attributes){
        Student student = studentService.findById(id_student);
        Group group = groupService.findById(id_group);

        if(student.getBalance()<group.getCourse().getPrice()){

            String message="На вашем балансе недостаточно средст, пополните баланс и попробуйте ещё раз)";
            attributes.addFlashAttribute("message", message);
            model.addAttribute("message", message);
            return "redirect:/students/groups/"+student.getId_student();
        }
        else{
            student.setId_group(group);
            student.setBalance(student.getBalance()-group.getCourse().getPrice());
            studentService.save(student);
            group.setRecorded_count(group.getRecorded_count()+1);
            groupService.save(group);

        }
       // emailSender.sendSimpleMessage(student.getId_user().getMail(), "subject", "body");
        model.addAttribute("student", student);
        model.addAttribute("group", group);
        String mail = student.getId_user().getMail();
        String body = "Поздавряем, "+student.getId_user().getSurname()+" "+student.getId_user().getName()+"!\nВы были зачислены на курс "+group.getCourse().getCourse_name()+"\nС направлением "+group.getCourse().getId_skills().getName_skills()+" "
                +group.getCourse().getId_language().getName_language()+"\n Спасибо, что выбрали нашу компанию для вашего будущего";
        String subject = "IT Company Education Courses";
        emailService.sendSimpleEmail(mail, subject, body);
        attributes.addFlashAttribute("student", student);


        return "redirect:/student/"+student.getId_student();
    }
    @GetMapping("/students/mygroup/{id}")
    public String checkMyGroup(@PathVariable(name="id") int id,RedirectAttributes attributes, Model model){
        Student student = studentService.findById(id);
        if(student.getId_group()==null){
            String msg = "Никакой курс не проходится";
            attributes.addFlashAttribute("msg", msg);
            model.addAttribute("student", student);
            attributes.addFlashAttribute("student", student);
            return "redirect:/student/"+student.getId_student();
        }
        model.addAttribute("student", student);
        attributes.addFlashAttribute("student", student);
        return "StudentGroup";
    }

    @PostMapping("/students/mygroup/{id}")
    public String completeCourse(HttpServletResponse response, @PathVariable(name="id") int id, @RequestParam(name="rating", required = false) String rating, RedirectAttributes attributes, Model model) throws IOException, MessagingException, jakarta.mail.MessagingException {


        /*=-=-=-=-=-=-=-=-=-=-=-=-=-=-=*/
        Student student = studentService.findById(id);

        Group group = groupService.findById(student.getId_group().getId_group());

        ratingTeacher.checkRatingTeacher(rating, id, studentService, groupService, teacherService);



        String finalHtml = null;
        //List<Student> employeeList = studentService.findAllStudents();
        //dataContext = dataMapper.setData(student);
        Context dataContext = dataMapper.setData(studentService.findById(id));

        finalHtml = springTemplateEngine.process("Cert",dataContext);
        documentGenerator.htmlToPdf(finalHtml);

        //pdfController.generatePdf(response, springTemplateEngine, dataContext);

        if(student.getRating()>4){
            String mail = student.getId_user().getMail();
            String body = "Вы завершили курс "+group.getCourse().getCourse_name()+"\nВаш рейтинг составил "+student.getRating()+
                    "\nВ связи с этим наша компания выдает вам сертификат за отличную успеваемость\nСпасибо, что выбрали наш учебный цент";
            String subject = "IT Company Education Courses";
            emailSenderService.sendEmailWithAttachment(mail, subject, body, "employee3.pdf");

        }
        else{
            String mail = student.getId_user().getMail();
            String body = "Вы завершили курс "+group.getCourse().getCourse_name()+"\nВаш рейтинг составил "+studentService.findById(id).getRating()+
                    "\nВ связи с этим наша компания не выдает вам сертификат за отличную успеваемость\nСпасибо, что выбрали наш учебный цент";
            String subject = "IT Company Education Courses";
            emailSenderService.sendSimpleEmail(mail, subject, body);
        }
        group.setRecorded_count(group.getRecorded_count()+1);
        groupService.save(group);
        if(student.getCourses()==null){
            student.setCourses("");
        }
        StringBuilder crs = new StringBuilder(student.getCourses());
        student.setCourses(String.valueOf(crs.append(student.getId_group().getId_group()+",")));
        String[] courses = student.getCourses().split(",");
        for (int i = 0; i < courses.length; i++) {
            listLastGroups.add(courses[i]);
        }


        student.setId_group(null);
        model.addAttribute("student", student);
        attributes.addFlashAttribute("student", student);

        student.setRating(0);
        student.setCount_rating("");
        studentService.save(student);





        return "redirect:/student/"+student.getId_student();
    }
    @GetMapping("/students/lastMyGroups/{id}")
    public String findLastGroups(RedirectAttributes attributes,@PathVariable(name="id") int id_student, Model model){
        Student student = studentService.findById(id_student);
        /*if(student.getId_group()==null){
            attributes.addFlashAttribute("")
            return "redirect:/student/"+student.getId_student();

        }*/
        Group group = new Group();
        Course course = new Course();
        Skills skills = new Skills();
        skills.setName_skills("nothing");
        Language lang = new Language();
        lang.setName_language("nothing");
        course.setCourse_name("nothing");
        course.setId_skills(skills);
        course.setId_language(lang);
        group.setCourse(course);
        student.setId_group(group);
        List<Group> list = groupService.findAllGroups();
        if(student.getCourses()==null){
            student.setCourses("0,");
        }
        String grs = student.getCourses();
        String[] last = grs.split(",");

        /*String[] lastgr = (String[]) Arrays.stream(last).distinct().toArray();
         */
        Set<String> set = new HashSet<>();
        for (String i : last) {
            set.add(i);
        }

        String[] result = new String[set.size()];
        int k = 0;
        for (String i: set) {
            result[k++] = i;
        }
        List<Group> groupList = new ArrayList<>();
        for (int i = 0; i < result.length; i++) {
            for (int j = 0; j < list.size(); j++) {
                if(Integer.parseInt(result[i])==list.get(j).getId_group()){
                    groupList.add(list.get(j));
                }

            }

        }
        Teacher teacher = new Teacher();
        User user = new User();
        for (Group gr:groupList) {
            if(gr.getTeacher()==null){
                user.setSurname("not found");
               // user.setName("yet");
                teacher.setId_user(user);
                gr.setTeacher(teacher);
            }


        }
        if(groupList.size()==0){
            String err = "Никаких курсов не пройдено";
            attributes.addFlashAttribute("err", err);
            attributes.addFlashAttribute("student", student);
            model.addAttribute("student", student);
            return "redirect:/student/"+student.getId_student();
        }

        model.addAttribute("list", groupList);
        model.addAttribute("student",student);
        attributes.addFlashAttribute("student", student);

        return "LastGroup";
    }
    @PostMapping("/students/groups/{id_student}")
    public String findGroups(RedirectAttributes attributes,@PathVariable("id_student") int id_student, @RequestParam(required = false) String contact,  @RequestParam("groupTime") String groupTime,
                             @RequestParam Date date1,@RequestParam(value = "selected", required = false) List<Integer> selected,@RequestParam(value = "finding", required = false) List<Integer> finding,  Model model){
        //listGroups = se.getDbDataField();
        Student student = studentService.findById(id_student);
        model.addAttribute("student",student);
        attributes.addFlashAttribute("student", student);
        List<Group> lst = new ArrayList<>();

        if(contact.equals("3")){
            lst = groupRepository.findByDateStart(date1);
        } else if (contact.equals("2")) {
            lst = groupRepository.findByGroup_time(groupTime);

        }else if(contact.equals("4")){
            List<Course> allCourses = courseRepository.findBySkills(selected);
            lst = groupRepository.findByCourse(allCourses);

        }else if(contact.equals("5")){
            List<Course> allCourses = courseRepository.findByLang(finding);
            lst = groupRepository.findByCourse(allCourses);

        }
        if(lst.isEmpty()){
            String notFound = "We can not found this!!!";

            model.addAttribute("notFound", notFound);
            return "FindGroupsStudent";
        }
        else{
            Teacher teacher = new Teacher();
            User user = new User();
            for (Group gr:lst) {
                if(gr.getTeacher()==null){
                    user.setSurname("not found");
                    user.setName("yet");
                    teacher.setId_user(user);
                    gr.setTeacher(teacher);
                }


            }
            model.addAttribute("list", lst);
            return "FindGroupsStudent";
        }




       // return "FindGroupsStudent";
    }
}
