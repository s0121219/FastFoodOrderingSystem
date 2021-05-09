package ouhk.comps380f.controller;

import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.RedirectView;
import ouhk.comps380f.dao.CommentRepository;
import ouhk.comps380f.dao.ItemUserRepository;
import ouhk.comps380f.dao.UserOrderRepository;
import ouhk.comps380f.exception.AttachmentNotFound;
import ouhk.comps380f.exception.ItemNotFound;
import ouhk.comps380f.exception.UserOrderNotFound;
import ouhk.comps380f.model.Attachment;
import ouhk.comps380f.model.Comment;
import ouhk.comps380f.model.Item;
import ouhk.comps380f.model.ItemUser;
import ouhk.comps380f.model.UserOrder;
import ouhk.comps380f.service.AttachmentService;
import ouhk.comps380f.service.ItemService;
import ouhk.comps380f.service.UserOrderService;
import ouhk.comps380f.view.DownloadingView;

@Controller
@RequestMapping("/item")
public class ItemController {

    @Autowired
    private ItemService itemService;
    @Autowired
    private AttachmentService attachmentService;

    @Autowired
    private UserOrderService userOrderService;
    @Resource
    UserOrderRepository userOrderRepo;
    @Resource
    ItemUserRepository itemUserRepo;
    @Resource
    CommentRepository commentRepo;

    @GetMapping(value = {"", "/list"})
    @Transactional
    public String list(ModelMap model, Principal principal) {
        model.addAttribute("itemDatabase", itemService.getItems());
        ItemUser itemUser = itemUserRepo.findById(principal.getName()).orElse(null);
        List<ItemUser> itemUserList = new ArrayList<ItemUser>();
        itemUserList.add(itemUser);
        model.addAttribute("User", itemUserList);

//System.out.println(itemUser.getUsername());
        return "list";
    }

    @GetMapping({"/shoppingcart", "/shoppingcart?successful"})
    public String shoppingcart(ModelMap model, Principal principal) {
        model.addAttribute("shoppingcartDatabase", userOrderService.getUserOrderByUsername(principal.getName()));
        model.addAttribute("itemDatabase", itemService.getItems());
        return "shoppingCart";
    }

    @GetMapping("/checkout")
    @Transactional(rollbackFor = UserOrderNotFound.class)
    public String checkout(ModelMap model, Principal principal)
            throws UserOrderNotFound {

        for (UserOrder deletedUserOrder : userOrderService.getUserOrderByUsername(principal.getName())) {
            if (deletedUserOrder == null) {
                throw new UserOrderNotFound();
            }
            userOrderRepo.delete(deletedUserOrder);
        }

        return "redirect:/item/shoppingcart?successful";
    }

    @GetMapping("/create")
    public ModelAndView create() {
        return new ModelAndView("add", "itemForm", new Form());
    }

    public static class Form {

        private String itemName;
        private String itemDescription;
        private String price;
        private boolean availability;
        private List<MultipartFile> attachments;
        // Getters and Setters of customerName, subject, body, attachments

        public String getItemName() {
            return itemName;
        }

        public void setItemName(String itemName) {
            this.itemName = itemName;
        }

        public String getItemDescription() {
            return itemDescription;
        }

        public void setItemDescription(String itemDescription) {
            this.itemDescription = itemDescription;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public boolean isAvailability() {
            return availability;
        }

        public void setAvailability(boolean availability) {
            this.availability = availability;
        }

        public List<MultipartFile> getAttachments() {
            return attachments;
        }

        public void setAttachments(List<MultipartFile> attachments) {
            this.attachments = attachments;
        }

    }

    public static class CommentForm {

        private long id;
        private String content;
        private long itemId;

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public long getItemId() {
            return itemId;
        }

        public void setItemId(long itemId) {
            this.itemId = itemId;
        }

    }

    @PostMapping("/create")
    public String create(Form form, Principal principal) throws IOException {
        long itemId = itemService.createItem(form.getItemName(),
                form.getItemDescription(), form.getPrice(), form.isAvailability(), form.getAttachments());
        return "redirect:/item/view/" + itemId;
    }

    @GetMapping("/view/{itemId}")
    public ModelAndView showview(@PathVariable("itemId") long itemId,
            ModelMap model) {
        Item item = itemService.getItem(itemId);
        if (item == null) {
            return new ModelAndView(new RedirectView("/item/list", true));
        }
        ModelAndView modelAndView = new ModelAndView("view");
        
        modelAndView.addObject("item", item);
        
        CommentForm commentForm = new CommentForm();
        modelAndView.addObject("commentForm", commentForm);
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

    @PostMapping("/view/{itemId}")
    public String view_postcomment(@PathVariable("itemId") long itemId, CommentForm commentform)
            throws IOException {
        Item item = itemService.getItem(itemId);
        Comment comment = new Comment();
        comment.setContent(commentform.getContent());
        comment.setItem(item);
        commentRepo.save(comment);
        
        return "redirect:/item/view/" + itemId;

    }

    @GetMapping("/{itemId}/attachment/{attachment:.+}")
    public View download(@PathVariable("itemId") long itemId,
            @PathVariable("attachment") String name) {
        Attachment attachment = attachmentService.getAttachment(itemId, name);
        if (attachment != null) {
            return new DownloadingView(attachment.getName(),
                    attachment.getMimeContentType(), attachment.getContents());
        }
        return new RedirectView("/item/list", true);
    }

    @GetMapping("/{itemId}/delete/{attachment:.+}")
    public String deleteAttachment(@PathVariable("itemId") long itemId,
            @PathVariable("attachment") String name) throws AttachmentNotFound {
        itemService.deleteAttachment(itemId, name);
        return "redirect:/item/edit/" + itemId;
    }

    @GetMapping("/edit/{itemId}")
    public ModelAndView showEdit(@PathVariable("itemId") long itemId, HttpServletRequest request) {
        Item item = itemService.getItem(itemId);
        if (item == null) {
            return new ModelAndView(new RedirectView("/item/list", true));
        }

        ModelAndView modelAndView = new ModelAndView("edit");
        modelAndView.addObject("item", item);

        Form itemForm = new Form();

        itemForm.setItemName(itemForm.getItemName());
        itemForm.setItemDescription(itemForm.getItemDescription());
        itemForm.setPrice(itemForm.getPrice());
        itemForm.setAvailability(itemForm.isAvailability());

        modelAndView.addObject("itemForm", itemForm);
        return modelAndView;
    }

    @PostMapping("/edit/{itemId}")
    public String edit(@PathVariable("itemId") long itemId, Form form)
            throws IOException, ItemNotFound {
        Item item = itemService.getItem(itemId);
        if (item == null) {
            return "redirect:/item/list";
        }
        itemService.updateItem(itemId, form.getItemName(),
                form.getItemDescription(), form.getPrice(), form.isAvailability(), form.getAttachments());

        return "redirect:/item/view/" + itemId;
    }

    @GetMapping("/delete/{itemId}")
    public String deleteItem(@PathVariable("itemId") long itemId)
            throws ItemNotFound {
        itemService.delete(itemId);
        return "redirect:/item/list";
    }

    @GetMapping("/addShoppingcart/{itemId}")
    @Transactional
    public String addShoppingcart(@PathVariable("itemId") int itemId, Principal principal)
            throws IOException {
        UserOrder userOrder = new UserOrder(principal.getName(), itemId);
        userOrderRepo.save(userOrder);

        return "redirect:/item/list";
    }

}
