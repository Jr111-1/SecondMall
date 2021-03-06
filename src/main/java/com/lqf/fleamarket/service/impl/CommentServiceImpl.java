package com.lqf.fleamarket.service.impl;

import com.lqf.fleamarket.controller.param.CommentReq;
import com.lqf.fleamarket.controller.param.CommentRespReq;
import com.lqf.fleamarket.dao.entity.CommentEntity;
import com.lqf.fleamarket.dao.entity.CommentRespEntity;
import com.lqf.fleamarket.dao.entity.UserEntity;
import com.lqf.fleamarket.dao.repo.CommentRepository;
import com.lqf.fleamarket.dao.repo.CommentRespRepository;
import com.lqf.fleamarket.dao.repo.UserRepository;
import com.lqf.fleamarket.domain.CommentDTO;
import com.lqf.fleamarket.domain.model.Comment;
import com.lqf.fleamarket.domain.model.CommentResp;
import com.lqf.fleamarket.service.CommentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
@Slf4j
public class CommentServiceImpl implements CommentService {
    private CommentRepository commentRepository;
    private CommentRespRepository commentRespRepository;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    public void setCommentRespRepository(CommentRespRepository commentRespRepository){
        this.commentRespRepository = commentRespRepository;
    }

    @Autowired
    public void setCommentRepository(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    @Override
    public Comment createComment(CommentReq commentReq) {
        CommentEntity commentEntity = new CommentEntity();
        BeanUtils.copyProperties(commentReq, commentEntity);
        this.commentRepository.save(commentEntity);
        Comment comment = new Comment();
        BeanUtils.copyProperties(commentEntity, comment);
        UserEntity userEntity = this.userRepository.getOne(comment.getReviewerId());
        comment.setReviewerName(userEntity.getName());
        return comment;
    }

    /**
     * @param commentReq
     * @return
     */
    @Override
    public Comment updateComment(long commentId, CommentReq commentReq) {
        CommentEntity commentEntity = this.commentRepository.getOne(commentId);
        BeanUtils.copyProperties(commentReq, commentEntity);
        this.commentRepository.save(commentEntity);
        Comment comment = new Comment();
        BeanUtils.copyProperties(commentEntity, comment);
        return comment;
    }

    @Override
    public void deleteComment(long commentId) {
        this.commentRepository.deleteById(commentId);
    }

    /**
     * ????????????id ??????????????????????????????
     * @param commodityId
     * @return
     */
    @Override
    public List<CommentDTO> getCommentsByCommodityId(long commodityId) {
        List<CommentEntity> commentEntities = this.commentRepository.findAllByCommodityId(commodityId);//???????????????????????????
        List<CommentDTO> commentDTOList = commentEntities.stream().map(entity -> {
            CommentDTO commentDTO = new CommentDTO();
            BeanUtils.copyProperties(entity, commentDTO);
            UserEntity userEntity = this.userRepository.getOne(commentDTO.getReviewerId());
            commentDTO.setReviewerName(userEntity.getName());
            //????????????????????????????????????
            List<CommentRespEntity> commentRespEntities = this.commentRespRepository.findAllByCommentId(commentDTO.getId());
            List<CommentResp> commentResps = commentRespEntities.stream().map(resp -> {
                CommentResp commentResp = new CommentResp();
                BeanUtils.copyProperties(resp, commentResp);
                UserEntity userEntity1 = this.userRepository.getOne(commentResp.getReviewerId());
                commentResp.setReviewerName(userEntity1.getName());
                if (commentResp.getReplierId() != 0) {
                    userEntity1 = this.userRepository.getOne(commentResp.getReplierId());
                    commentResp.setReplierName(userEntity1.getName());
                }
                return commentResp;
            }).collect(Collectors.toList());
            commentDTO.setCommentRespList(commentResps);
            return commentDTO;
        }).collect(Collectors.toList());
// ????????????????????????????????????BeanUtils??????????????????DTO??????respList?????????commentResp??????commentRespEntity????????????
//        List<CommentEntity> commentEntities = this.commentRepository.findAllByCommodityId(commodityId);
//        List<CommentDTO> commentDTOList = commentEntities.stream().map(entity -> {
//            CommentDTO commentDTO = new CommentDTO();
//            BeanUtils.copyProperties(entity, commentDTO);
//            UserEntity userEntity = this.userRepository.getOne(entity.getReviewerId());
//            commentDTO.setReviewerName(userEntity.getName());   // ??????????????????????????????
//            return commentDTO;
//        }).collect(Collectors.toList());
        return commentDTOList;
    }

    @Override
    public List<Comment> getCommentsByUserId(long userId) {
        return null;
    }

    @Override
    public CommentResp createCommentResp(CommentRespReq commentRespReq) {
        CommentRespEntity commentRespEntity = new CommentRespEntity();
        BeanUtils.copyProperties(commentRespReq, commentRespEntity);
        this.commentRespRepository.save(commentRespEntity);
        CommentResp commentResp = new CommentResp();
        BeanUtils.copyProperties(commentRespEntity, commentResp);
        return commentResp;
    }

    @Override
    public void deleteCommentResp(long responseId) {
        this.commentRespRepository.deleteById(responseId);
    }

    @Override
    public List<CommentResp> getCommentResps(long responseId) {
        return null;
    }
}
