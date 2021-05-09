
package ouhk.comps380f.service;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ouhk.comps380f.dao.CommentRepository;
import ouhk.comps380f.model.Comment;

@Service
public class CommentService {
    
    @Resource
    private CommentRepository commentRepo;

    
}
