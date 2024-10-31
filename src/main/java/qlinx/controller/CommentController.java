package qlinx.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import qlinx.entity.Comment;
import qlinx.repository.CommentRepository;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
public class CommentController {

    @Autowired
    private CommentRepository commentRepository;

    // 댓글 저장하기
    @PostMapping("save")
    public Comment addComment(@RequestBody Comment comment) {
        return commentRepository.save(comment);
    }

    // 모든 댓글 가져오기
    @GetMapping("getAll")
    public List<Comment> getAllComments() {
        return commentRepository.findAll();
    }
}
