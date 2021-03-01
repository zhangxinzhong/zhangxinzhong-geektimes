package org.geekbang.projects.web.controller;

import org.geekbang.projects.domain.User;
import org.geekbang.projects.service.UserService;
import org.geekbang.projects.service.impl.UserServiceImpl;
import org.geektimes.web.mvc.controller.PageController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * 用户控制器
 *
 * @author: zhangxinzhong
 * @since: 1.0.0
 * @version: JDK8
 * @create: 2021-02-28 20:36
 **/
@Path("/user")
public class UserController implements PageController {
    Logger logger = Logger.getLogger(UserController.class.getName());


    private final UserService userService = new UserServiceImpl();

    @Path("/register")
    @GET
    public String toRegisterPage(HttpServletRequest request, HttpServletResponse response) throws Throwable {
        return "register-form.jsp";
    }

    @Path("/create")
    @POST
    public String create(HttpServletRequest request, HttpServletResponse response) throws Throwable {

        User user = builder(request);

        logger.log(Level.INFO, "进入 create :" + user);

        boolean isSuccess = userService.register(user);
        if (!isSuccess) {
            response.sendRedirect("register-form.jsp");
        }
        logger.log(Level.INFO,userService.queryAll().toString());
        return "success.jsp";
    }

    private User builder(HttpServletRequest request) {
        User user = new User();
        user.setName(request.getParameter("name"));
        user.setEmail(request.getParameter("email"));
        user.setPassword(request.getParameter("password"));
        user.setPhoneNumber(request.getParameter("phoneNumber"));

        return user;

    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Throwable {
        return null;
    }
}
