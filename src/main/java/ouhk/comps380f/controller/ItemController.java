package ouhk.comps380f.controller;

import java.io.IOException;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.RedirectView;
import ouhk.comps380f.model.Attachment;
import ouhk.comps380f.model.Item;
import ouhk.comps380f.view.DownloadingView;

@Controller
@RequestMapping("/item")
public class ItemController {

    private volatile long ITEM_ID_SEQUENCE = 1;
    private Map<Long, Item> itemDatabase = new Hashtable<>();

    // Controller methods, Form object, ...
    @GetMapping(value = {"", "/list"})
    public String list(ModelMap model) {
        model.addAttribute("itemDatabase", itemDatabase);
        return "list";
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

    @PostMapping("/create")
    public View create(Form form) throws IOException {
        Item item = new Item();
        item.setId(this.getNextItemId());
        item.setItemName(form.getItemName());
        item.setItemDescription(form.getItemDescription());
        item.setPrice(form.getPrice());
        for (MultipartFile filePart : form.getAttachments()) {
            Attachment attachment = new Attachment();
            attachment.setName(filePart.getOriginalFilename());
            attachment.setMimeContentType(filePart.getContentType());
            attachment.setContents(filePart.getBytes());
            if (attachment.getName() != null && attachment.getName().length() > 0
                    && attachment.getContents() != null && attachment.getContents().length > 0) {
                item.addAttachment(attachment);
            }
        }
        this.itemDatabase.put(item.getId(), item);
        return new RedirectView("/item/view/" + item.getId(), true);
    }

    private synchronized long getNextItemId() {
        return this.ITEM_ID_SEQUENCE++;
    }

    @GetMapping("/view/{itemId}")
    public String view(@PathVariable("itemId") long itemId,
            ModelMap model) {
        Item item = this.itemDatabase.get(itemId);
        if (item == null) {
            return "redirect:/item/list";
        }
        model.addAttribute("itemId", itemId);
        model.addAttribute("item", item);
        return "view";
    }

    @GetMapping("/{itemId}/attachment/{attachment:.+}")
    public View download(@PathVariable("itemId") long itemId,
            @PathVariable("attachment") String name) {
        Item item = this.itemDatabase.get(itemId);
        if (item != null) {
            Attachment attachment = item.getAttachment(name);
            if (attachment != null) {
                return new DownloadingView(attachment.getName(),
                        attachment.getMimeContentType(), attachment.getContents());
            }
        }
        return new RedirectView("/item/list", true);
    }
    
      @GetMapping("/{itemId}/delete/{attachment:.+}")
    public String deleteAttachment(@PathVariable("itemId") long itemId,
            @PathVariable("attachment") String name) {
        Item item = this.itemDatabase.get(itemId);
        if (item != null) {
            if (item.hasAttachment(name)) {
                item.deleteAttachment(name);
            }
        }
        return "redirect:/item/edit/" + itemId;
    }

    @GetMapping("/edit/{itemId}")
    public ModelAndView showEdit(@PathVariable("itemId") long itemId) {
        Item item = this.itemDatabase.get(itemId);
        if (item == null) {
            return new ModelAndView(new RedirectView("/item/list", true));
        }
        ModelAndView modelAndView = new ModelAndView("edit");
        modelAndView.addObject("itemId", Long.toString(itemId));
        modelAndView.addObject("item", item);

        Form itemForm = new Form();
        
        itemForm.setItemName(itemForm.getItemName());
        itemForm.setItemDescription(itemForm.getItemDescription());
        itemForm.setPrice(itemForm.getPrice());
        
        modelAndView.addObject("itemForm", itemForm);

        return modelAndView;
    }

    @PostMapping("/edit/{itemId}")
    public String edit(@PathVariable("itemId") long itemId, Form form)
            throws IOException {
        Item item = this.itemDatabase.get(itemId);
        
        item.setItemName(form.getItemName());
        item.setItemDescription(form.getItemDescription());
        item.setPrice(form.getPrice());

        for (MultipartFile filePart : form.getAttachments()) {
            Attachment attachment = new Attachment();
            attachment.setName(filePart.getOriginalFilename());
            attachment.setMimeContentType(filePart.getContentType());
            attachment.setContents(filePart.getBytes());
            if (attachment.getName() != null && attachment.getName().length() > 0
                    && attachment.getContents() != null && attachment.getContents().length > 0) {
                item.addAttachment(attachment);
            }
        }
        this.itemDatabase.put(item.getId(), item);
        return "redirect:/item/view/" + item.getId();
    }

    @GetMapping("/delete/{itemId}")
    public String deleteItem(@PathVariable("itemId") long itemId) {
        if (this.itemDatabase.containsKey(itemId)) {
            this.itemDatabase.remove(itemId);
        }
        return "redirect:/item/list";
    }
    
    

}
