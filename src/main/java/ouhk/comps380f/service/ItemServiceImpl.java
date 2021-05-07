package ouhk.comps380f.service;

import java.io.IOException;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ouhk.comps380f.dao.AttachmentRepository;
import ouhk.comps380f.dao.ItemRepository;
import ouhk.comps380f.exception.AttachmentNotFound;
import ouhk.comps380f.exception.ItemNotFound;
import ouhk.comps380f.model.Attachment;
import ouhk.comps380f.model.Item;

@Service
public class ItemServiceImpl implements ItemService {

    @Resource
    private ItemRepository itemRepo;
    @Resource
    private AttachmentRepository attachmentRepo;

    @Override
    @Transactional
    public List<Item> getItems() {
        return itemRepo.findAll();
    }

    @Override
    @Transactional
    public Item getItem(long id) {
        return itemRepo.findById(id).orElse(null);
    }

    @Override
    @Transactional(rollbackFor = ItemNotFound.class)
    public void delete(long id) throws ItemNotFound {
        Item deletedItem = itemRepo.findById(id).orElse(null);
        if (deletedItem == null) {
            throw new ItemNotFound();
        }
        itemRepo.delete(deletedItem);
    }

    @Override
    @Transactional(rollbackFor = AttachmentNotFound.class)
    public void deleteAttachment(long itemId, String name) throws AttachmentNotFound {
        Item item = itemRepo.findById(itemId).orElse(null);
        for (Attachment attachment : item.getAttachments()) {
            if (attachment.getName().equals(name)) {
                item.deleteAttachment(attachment);
                itemRepo.save(item);
                return;
            }
        }
        throw new AttachmentNotFound();
    }

    
    @Override
    @Transactional
    public long createItem(String itemName, String itemDescription,
            String price, boolean availability, List<MultipartFile> attachments) throws IOException {
        Item item = new Item();
        item.setItemName(itemName);
        item.setItemDescription(itemDescription);
        item.setPrice(price);
        item.setAvailability(availability);
        for (MultipartFile filePart : attachments) {
            Attachment attachment = new Attachment();
            attachment.setName(filePart.getOriginalFilename());
            attachment.setMimeContentType(filePart.getContentType());
            attachment.setContents(filePart.getBytes());
            attachment.setItem(item);
            if (attachment.getName() != null && attachment.getName().length() > 0
                    && attachment.getContents() != null
                    && attachment.getContents().length > 0) {
                item.getAttachments().add(attachment);
            }
        }
        Item savedItem = itemRepo.save(item);
        return savedItem.getId();
    }

    @Override
    @Transactional(rollbackFor = ItemNotFound.class)
    public void updateItem(long id, String itemName,
            String itemDescription, String price, boolean availability, List<MultipartFile> attachments)
            throws IOException, ItemNotFound {
        Item updatedItem = itemRepo.findById(id).orElse(null);
        if (updatedItem == null) {
            throw new ItemNotFound();
        }
        updatedItem.setItemName(itemName);
        updatedItem.setItemDescription(itemDescription);
        updatedItem.setPrice(price);
        updatedItem.setAvailability(availability);
        for (MultipartFile filePart : attachments) {
            Attachment attachment = new Attachment();
            attachment.setName(filePart.getOriginalFilename());
            attachment.setMimeContentType(filePart.getContentType());
            attachment.setContents(filePart.getBytes());
            attachment.setItem(updatedItem);
            if (attachment.getName() != null && attachment.getName().length() > 0
                    && attachment.getContents() != null
                    && attachment.getContents().length > 0) {
                updatedItem.getAttachments().add(attachment);
            }
        }
        itemRepo.save(updatedItem);
    }
}
