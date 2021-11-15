package com.onnoa.shop.demo.video.mapper;

import com.onnoa.shop.demo.video.test.Article;

import java.util.List;
import java.util.Map;

/**
 * @className: ArticleMapper
 * @description:
 * @author: onnoA
 * @date: 2021/9/26
 **/
public interface ArticleMapper {

    Article selectById(long id);

    List<Article> selectArticleList(Map<String, Object> map);
}
