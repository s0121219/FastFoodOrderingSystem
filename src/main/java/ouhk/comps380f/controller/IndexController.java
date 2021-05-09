package ouhk.comps380f.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.RedirectView;
import ouhk.comps380f.dao.CommentRepository;
import ouhk.comps380f.dao.ItemUserRepository;
import ouhk.comps380f.model.Attachment;
import ouhk.comps380f.model.Comment;
import ouhk.comps380f.model.Item;
import ouhk.comps380f.model.ItemUser;
import ouhk.comps380f.service.AttachmentService;
import ouhk.comps380f.service.ItemService;
import ouhk.comps380f.view.DownloadingView;

@Controller
@RequestMapping("/")
public class IndexController {

    @Autowired
    private ItemService itemService;
    @Autowired
    private AttachmentService attachmentService;

    @Resource
    ItemUserRepository itemUserRepo;
    @Resource
    CommentRepository commentRepo;

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
    
    @GetMapping
    public String index(ModelMap model) {
        model.addAttribute("itemDatabase", itemService.getItems());
        return "index";
    }

    @GetMapping("/index_view/{itemId}")
    public ModelAndView view(@PathVariable("itemId") long itemId) {
        Item item = itemService.getItem(itemId);
        if (item == null) {
            return new ModelAndView(new RedirectView("/index", true));
        }
        ModelAndView modelAndView = new ModelAndView("index_view");
         modelAndView.addObject("item", item);
         
         List<Comment> commentlist = new ArrayList<>();
        for(Comment comment:commentRepo.findAll())
        {
            if(comment.getItemId()==itemId)
            {
                commentlist.add(comment);
            }
        }
        
        modelAndView.addObject("comment",commentlist);
        return modelAndView;
    }

    @GetMapping("/{itemId}/attachment/{attachment:.+}")
    public View download(@PathVariable("itemId") long itemId,
            @PathVariable("attachment") String name) {
        Attachment attachment = attachmentService.getAttachment(itemId, name);
        if (attachment != null) {
            return new DownloadingView(attachment.getName(),
                    attachment.getMimeContentType(), attachment.getContents());
        }
        return new RedirectView("/", true);
    }

    @GetMapping("/register")
    public ModelAndView create() {
        return new ModelAndView("addUser", "itemUser", new Form());
    }

    @PostMapping("/register")
    public View create(Form form) throws IOException {
        ItemUser user = new ItemUser(form.getUsername(),
                form.getPassword(), form.getFullname(), form.getPhoneNumber(), form.getDeliveryAddress()
        );
        itemUserRepo.save(user);
        return new RedirectView("/", true);
    }
    

    @GetMapping("/login")
    public String login() {
        return "login";
    }

}
