package com.lqf.fleamarket.service;

import com.lqf.fleamarket.controller.param.CommentReq;
import com.lqf.fleamarket.controller.param.CommentRespReq;
import com.lqf.fleamarket.domain.CommentDTO;
import com.lqf.fleamarket.domain.model.Comment;
import com.lqf.fleamarket.domain.model.CommentResp;

import java.util.List;


public interface CommentService {
    Comment createComment(CommentReq commentReq);
    Comment updateComment(long commentId, CommentReq commentReq);
    CommentResp createCommentResp(CommentRespReq commentRespReq);
    void deleteComment(long commentId);
    void deleteCommentResp(long responseId);
    List<CommentDTO> getCommentsByCommodityId(long commodityId);
    List<Comment> getCommentsByUserId(long userId);
    List<CommentResp> getCommentResps(long responseId);
}
