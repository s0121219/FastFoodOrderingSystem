package ouhk.comps380f.controller;

import java.io.IOException;
import java.security.Principal;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.RedirectView;
import ouhk.comps380f.dao.ItemUserRepository;
import ouhk.comps380f.model.ItemUser;
import ouhk.comps380f.service.ItemUserService;
import ouhk.comps380f.exception.UserNotFound;

@Controller
@RequestMapping("/user")
public class ItemUserController {

    @Resource
    ItemUserRepository itemUserRepo;

    @GetMapping("/list")
    public String list(ModelMap model) {
        model.addAttribute("itemUsers", itemUserRepo.findAll());
        return "listUser";
    }

    public static class Form {

        private String username;
        private String password;
        private String[] roles;
        private String fullname;
        private String phoneNumber;
        private String deliveryAddress;
        // ... getters and setters for each of the properties

        public String getFullname() {
            return fullname;
        }

        public void setFullname(String fullname) {
            this.fullname = fullname;
        }

        public String getPhoneNumber() {
            return phoneNumber;
        }

        public void setPhoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
        }

        public String getDeliveryAddress() {
            return deliveryAddress;
        }

        public void setDeliveryAddress(String deliveryAddress) {
            this.deliveryAddress = deliveryAddress;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String[] getRoles() {
            return roles;
        }

        public void setRoles(String[] roles) {
            this.roles = roles;
        }

    }

    @GetMapping("/create")
    public ModelAndView create() {
        return new ModelAndView("addUser", "itemUser", new Form());
    }

    @PostMapping("/create")
    public View create(Form form) throws IOException {
        ItemUser user = new ItemUser(form.getUsername(),
                form.getPassword(), form.getFullname(), form.getPhoneNumber(), form.getDeliveryAddress()
        );
        itemUserRepo.save(user);
        return new RedirectView("/user/list", true);
    }

    @GetMapping("/edit/{username}")
    public ModelAndView showEdit(@PathVariable("username") String username, HttpServletRequest request,Principal principal) {
        ItemUser itemUser = itemUserRepo.findById(username).orElse(null);
        if (itemUser == null) {
            return new ModelAndView(new RedirectView("/user", true));
        }
        itemUser.setPassword(itemUser.getPassword().replace("{noop}", ""));
        ModelAndView modelAndView = new ModelAndView("editUser");
        modelAndView.addObject("itemUser", itemUser);
        ItemUser currentUser = itemUserRepo.findById(principal.getName()).orElse(null);
        modelAndView.addObject("User", currentUser);

        Form userForm = new Form();

        userForm.setUsername(userForm.getUsername());
        userForm.setPassword(userForm.getPassword());
        userForm.setFullname(userForm.getFullname());
        userForm.setPhoneNumber(userForm.getPhoneNumber());
        userForm.setDeliveryAddress(userForm.getDeliveryAddress());

        modelAndView.addObject("userForm", userForm);
        return modelAndView;
    }

    @PostMapping("/edit/{username}")
    @Transactional(rollbackFor = UserNotFound.class)
    public String edit(@PathVariable("username") String username, Form form)
            throws IOException, UserNotFound {
        ItemUser updatedUser = itemUserRepo.findById(username).orElse(null);
        if (updatedUser == null) {
            return "redirect:/user/list";
        }

        updatedUser.setUsername(form.getUsername());
        updatedUser.setPassword("{noop}" + form.getPassword());
        updatedUser.setFullname(form.getFullname());
        updatedUser.setPhoneNumber(form.getPhoneNumber());
        updatedUser.setDeliveryAddress(form.getDeliveryAddress());

        itemUserRepo.save(updatedUser);

        return "redirect:/item";
    }

    @GetMapping("/delete/{username}")
    public View deleteUser(@PathVariable("username") String username) {
        itemUserRepo.delete(itemUserRepo.findById(username).orElse(null));
        return new RedirectView("/user/list", true);
    }
}
