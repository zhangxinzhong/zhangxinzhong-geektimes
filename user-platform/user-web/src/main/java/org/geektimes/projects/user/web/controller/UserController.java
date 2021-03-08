package org.geektimes.projects.user.web.controller;

import org.geektimes.context.ComponentContext;
import org.geektimes.projects.user.domain.User;
import org.geektimes.projects.user.service.UserService;
import org.geektimes.projects.user.validator.bean.validation.DelegatingValidator;
import org.geektimes.web.mvc.controller.PageController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * {@link User} controller
 *
 * @author: zhangxinzhong
 * @since: 1.0.0
 * @version: JDK8
 * @create: 2021-03-08 19:06
 **/
@Path("/user")
public class UserController implements PageController {
    Logger logger = Logger.getLogger(UserController.class.getName());



    @POST
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Throwable {

        return null;
    }

    @Path("/register")
    @GET
    public String toRegisterPage(HttpServletRequest request, HttpServletResponse response) throws Throwable {
        return "register-form.jsp";
    }

    @Path("/create")
    @POST
    public String create(HttpServletRequest request, HttpServletResponse response) throws Throwable {

        try {
            User user = builder(request);


            logger.log(Level.INFO, "进入 create :" + user);

            UserService userService = ComponentContext.getInstance().getComponent("bean/UserService");
            boolean isSuccess = userService.register(user);
            if (!isSuccess) {
                response.sendRedirect("register-form.jsp");
            }
            logger.log(Level.INFO, userService.queryUserById(user.getId()).toString());
            return "success.jsp";
        } catch (RuntimeException e) {
            logger.log(Level.SEVERE,e.getMessage());
            response.getWriter().println("参数校验失败");
            return "error.jsp";
        }
    }

    private User builder(HttpServletRequest request) {
        User user = new User();
        user.setName(request.getParameter("name"));
        user.setEmail(request.getParameter("email"));
        user.setPassword(request.getParameter("password"));
        user.setPhoneNumber(request.getParameter("phoneNumber"));

        return user;

    }
}
